package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;

/** A class for normalising and utilising the specifications of concepts.
 * @see Specification
 *
 * @author Simon Colton, started 18th June 2002
 * @version 1.0 */

public class SpecificationHandler implements Serializable
{
    /** The maximum number of rewrites to try.
     */

    public int max_num_of_rewrites = 1025;

    /** The set of specifications inherited from the theory.
     */

    public Vector specifications = new Vector();

    /** The set of concepts inherited from the theory.
     */

    public Vector concepts = new Vector();

    /** The set of non-existence conjectures inherited from the theory.
     */

    public Vector non_existences = new Vector();

    /** The simple constructor.
     */

    public SpecificationHandler()
    {
    }

    /** This adds a set of specifications to the database. If a specification is already
     * in the database, it sets the specification to the previous one. This
     * keeps the theory tidy, which improves efficiency when checking if one specification
     * is a generalisation of another. It also sorts the specifications in terms
     * of their position in the database.
     */

    public Vector addSpecifications(Vector specs)
    {
        Vector output = new Vector();
        for (int i=0;i<specs.size();i++)
        {
            Specification spec = (Specification)specs.elementAt(i);
            Specification new_spec = addSpecification(spec);
            if (!output.contains(new_spec))
            {
                int j=0;
                boolean placed = false;
                while (j<output.size() && !placed)
                {
                    Specification previous_spec = (Specification)output.elementAt(j);
                    if (new_spec.id_number < previous_spec.id_number)
                    {
                        output.insertElementAt(new_spec, j);
                        placed = true;
                    }
                    j++;
                }
                if (!placed)
                    output.addElement(new_spec);
            }
        }

        // Next remove any obvious existence specifications //
        // e.g., [a,b] : p(a,b) & exists c (p(a,c)) //

        output.trimToSize();
        return output;
    }

    /** This first normalises a specification, then checks whether it already has
     * it in the theory. If so, it returns that previous specification, if not, it
     * adds the new specfication to the set.
     */

    public Specification addSpecification(Specification spec)
    {
        // Normalise the specifications: change split:negate to negate:split //

        if (spec.type.equals("split") &&
                spec.previous_specifications.size()==1)
        {
            Specification split_spec = spec.copy();
            Specification negate_spec =
                    (Specification)split_spec.previous_specifications.elementAt(0);

            if (negate_spec.type.equals("negate") &&
                    negate_spec.previous_specifications.size()==1)
            {
                Specification base_spec =
                        (Specification)negate_spec.previous_specifications.elementAt(0);

                Specification replacement_spec = negate_spec.copy();

                split_spec.previous_specifications.removeAllElements();
                split_spec.previous_specifications.addElement(base_spec);
                replacement_spec.previous_specifications.removeAllElements();
                replacement_spec.previous_specifications.addElement(split_spec);

                replacement_spec.permutation = (Vector)split_spec.permutation.clone();
                Vector new_perm = new Vector();
                for (int i=0; i<split_spec.permutation.size(); i++)
                    new_perm.addElement(Integer.toString(replacement_spec.permutation.
                            indexOf((String)split_spec.permutation.elementAt(i))));
                split_spec.permutation = new_perm;

                Specification output = addSpecification(replacement_spec);
                return output;
            }
        }

        // Remove double negations //

        if (spec.type.equals("negate") &&
                spec.previous_specifications.size()==1)
        {
            Specification previous_spec = (Specification)spec.previous_specifications.elementAt(0);
            if (previous_spec.type.equals("negate") &&
                    previous_spec.previous_specifications.size()==1 &&
                    spec.permutation.toString().equals(previous_spec.permutation.toString()))
            {
                Specification base_spec = ((Specification)previous_spec.previous_specifications.elementAt(0)).copy();
                Vector new_perm = new Vector();
                for (int i=0; i<base_spec.permutation.size(); i++)
                {
                    int pos = (new Integer((String)base_spec.permutation.elementAt(i))).intValue();
                    new_perm.addElement(spec.permutation.elementAt(pos));
                }
                base_spec.permutation=new_perm;
                return addSpecification(base_spec);
            }
        }

        // Add in the previous specifications for negated specification //

        if (spec.type.equals("negate"))
        {
            Vector new_previous_specifications = new Vector();
            for (int i=0; i<spec.previous_specifications.size(); i++)
            {
                Specification previous_spec = (Specification)spec.previous_specifications.elementAt(i);
                new_previous_specifications.addElement(addSpecification(previous_spec));
            }
            spec.previous_specifications = new_previous_specifications;
        }

        boolean has_match = false;
        Specification output = new Specification();
        Enumeration old_specs = specifications.elements();
        while (!has_match && old_specs.hasMoreElements())
        {
            Specification old_spec = (Specification)old_specs.nextElement();
            if (old_spec.equals(spec))
            {
                has_match = true;
                output=old_spec;
            }
        }

        if (!has_match)
        {
            spec.id_number = specifications.size();
            specifications.addElement(spec);
            spec.is_entity_instantiations = spec.isEntityInstantiations();
            output = spec;
        }

        // Trim to size the vectors //

        output.permutation.trimToSize();
        output.fixed_values.trimToSize();
        output.functions.trimToSize();
        output.multiple_variable_columns.trimToSize();
        output.multiple_types.trimToSize();
        output.permutation.trimToSize();
        output.previous_specifications.trimToSize();
        return output;
    }

    /** This checks whether the specifications of the new concept are essentially the
     * same as, or are made redundant by an old concept in the theory, returning that
     * old concept if so, otherwise, returning null.
     */

    public Concept checkForMatch(Concept new_concept, Step step, String forced_id)
    {
        boolean has_match = false;
        Concept old_concept = new Concept();
        int previous_concept_number = 0;
        while (previous_concept_number < concepts.size() && !has_match)
        {
            old_concept = (Concept)concepts.elementAt(previous_concept_number);
            if (old_concept.types.toString().equals(new_concept.types.toString()))
            {
                Vector concepts_to_check = (Vector)old_concept.conjectured_equivalent_concepts.clone();
                concepts_to_check.insertElementAt(old_concept, 0);
                for (int i=0; i<concepts_to_check.size() && !has_match; i++)
                {
                    Concept previous_concept = (Concept)concepts_to_check.elementAt(i);
                    int is_generalisation = previous_concept.isGeneralisationOf(new_concept);
                    if (is_generalisation == 1)
                    {
                        has_match=true;
                        previous_concept.conjectured_equivalent_constructions.addElement(step);
                        if (!forced_id.equals(""))
                            previous_concept.alternative_ids.addElement(forced_id);
                    }
                    if (!has_match &&
                            previous_concept.skolemised_representation.ground_variables.equals(new_concept.skolemised_representation.ground_variables))
                    {
                        boolean left_implies_right = leftSkolemisedImpliesRight(previous_concept, new_concept, false);
                        if (left_implies_right)
                        {
                            boolean right_implies_left = leftSkolemisedImpliesRight(new_concept, previous_concept, false);
                            if (right_implies_left)
                                has_match = true;
                        }
                    }
                }
            }
            previous_concept_number++;
        }
        if (has_match)
            return old_concept;
        return null;
    }

  /*

a = theory.conjectures;
b = a.elementAt(23);
c = a.elementAt(24);
d = theory.specification_handler;
e = b.subsumes(c,d);
f = b.writeConjecture("otter");
g = c.writeConjecture("otter");

*/

    /** This checks whether a skolemised permutation can rewrite the lh_implicate in such
     * a way that it subsumes the rh_implicate.
     *
     * ie. given that:<p>
     *
     *  1.     A   =>   B        is already known (lh_implicate)<br>
     *  2.     C   =>   D        is the new conjecture (rh_implicate)<p>
     *
     *  and we can demonstrate:<p>
     *
     *  3.     C   =>   A        by skolemised re-writing<br>
     *  4.     B   =>   D        by skolemised re-writing<p>
     *
     *  then we have shown that 2. is redundant. There is one catch: the
     *  skolemised rewriting in 3. must be the same as in 4.
     */

    public boolean leftSkolemisedSubsumesRight(Implicate lh_implicate, Implicate rh_implicate, boolean allow_all)
    {
        Concept a = lh_implicate.premise_concept;
        Concept c = rh_implicate.premise_concept;
        Concept b = new Concept();
        Concept d = new Concept();
        b.specifications.addElement(lh_implicate.goal);
        b.setSkolemisedRepresentation();
        d.specifications.addElement(rh_implicate.goal);
        d.setSkolemisedRepresentation();

        Vector c_a_rewrites = implyingRewrites(c,a,allow_all);
        Vector b_d_rewrites = implyingRewrites(b,d,allow_all);

        // Special case: if the Otter lhs of both lh_implicate and rh_implicate
        // is empty, then just check if b_d_rewrites is non-empty.

        if (a.writeDefinition("otter").trim().equals("") &&
                c.writeDefinition("otter").trim().equals("") &&
                !b_d_rewrites.isEmpty())
            return true;

        for (int i=0; i<c_a_rewrites.size(); i++)
        {
            Vector c_a_rewrite = (Vector)c_a_rewrites.elementAt(i);
            for (int j=0; j<b_d_rewrites.size(); j++)
            {
                Vector b_d_rewrite = (Vector)b_d_rewrites.elementAt(j);
                if (reWriteImplicate(c_a_rewrite, b_d_rewrite, a.skolemised_representation,
                        b.skolemised_representation, c.skolemised_representation,
                        d.skolemised_representation))
                    return true;
            }
        }
        return false;
    }

    public boolean reWriteImplicate(Vector c_a_rewrite, Vector b_d_rewrite,
                                    SkolemisedRepresentation a, SkolemisedRepresentation b,
                                    SkolemisedRepresentation c, SkolemisedRepresentation d)
    {
        // First see if rewriting a to be implied by c, followed by
        // rewriting d to be implied by b is a good rewrite.

        // Re-write the second re-write in light of the first.

        Vector changed_b_d_rewrite = new Vector();
        for (int i=0; i<b_d_rewrite.size(); i++)
        {
            String rw = (String)b_d_rewrite.elementAt(i);
            int pos = a.variables.indexOf(rw);
            if (pos > 0)
            {
                String new_pos = (String)c_a_rewrite.elementAt(pos);
                changed_b_d_rewrite.addElement(new_pos);
            }
            else
                changed_b_d_rewrite.addElement(rw);
        }

        // Make sure the changed b_d_rewrite doesn't break these rules:
        // (i) It can't change any non-skolem letters of C which a variable of A is
        //     permuted to.
        // (ii) It can't change any non-skolem letters of D to non-skolem letters
        //      which appear in the variables of C or D, unless that other
        //      letter is also moved on.

        boolean perm_ok = true;
        for (int i=0; i<d.variables.size(); i++)
        {
            String d_var = (String)d.variables.elementAt(i);
            String changed_d_var = (String)changed_b_d_rewrite.elementAt(i);
            if (d_var.indexOf("!")<0 && !d_var.equals(changed_d_var))
            {
                if (c_a_rewrite.contains(d_var))
                {
                    perm_ok = false;
                    break;
                }
                int cdv_pos = d.variables.indexOf(changed_d_var);
                if ((cdv_pos < 0 || ((String)changed_b_d_rewrite.elementAt(cdv_pos)).equals(changed_d_var))
                        && (c.variables.contains(changed_d_var) || d.variables.contains(changed_d_var)))
                {
                    perm_ok = false;
                    break;
                }
            }
        }

        if (perm_ok)
            return true;

        // Second see if rewriting d to be implied by b, followed by
        // rewriting a to be implied by c is a good rewrite.

        // Re-write the second re-write in light of the first.

        Vector changed_c_a_rewrite = new Vector();
        for (int i=0; i<c_a_rewrite.size(); i++)
        {
            String rw = (String)c_a_rewrite.elementAt(i);
            int pos = d.variables.indexOf(rw);
            if (pos > 0)
            {
                String new_pos = (String)b_d_rewrite.elementAt(pos);
                changed_c_a_rewrite.addElement(new_pos);
            }
            else
                changed_c_a_rewrite.addElement(rw);
        }

        // Make sure the original b_d_rewrite doesn't break this rule:
        // (i) It can't change any non-skolem letters of D to non-skolem letters
        //     which appear in the variables of C or D, unless that other letter
        //     is also moved on.

        perm_ok = true;
        for (int i=0; i<d.variables.size(); i++)
        {
            String d_var = (String)d.variables.elementAt(i);
            String changed_d_var = (String)b_d_rewrite.elementAt(i);
            if (d_var.indexOf("!")<0 && !d_var.equals(changed_d_var))
            {
                int cdv_pos = d.variables.indexOf(changed_d_var);
                if ((cdv_pos < 0 || ((String)b_d_rewrite.elementAt(cdv_pos)).equals(changed_d_var))
                        && (c.variables.contains(changed_d_var) || d.variables.contains(changed_d_var)))
                {
                    perm_ok = false;
                    break;
                }
            }
        }

        // Make sure the changed c_a_rewrite doesn't break this rule:
        // (i) It can't change any non-skolem letters of B which a variable of D is
        //     permuted to.

        for (int i=0; i<a.variables.size(); i++)
        {
            String a_var = (String)a.variables.elementAt(i);
            String changed_a_var = (String)changed_c_a_rewrite.elementAt(i);
            if (a_var.indexOf("!")<0 && !a_var.equals(changed_a_var))
            {
                if (b_d_rewrite.contains(a_var))
                {
                    perm_ok = false;
                    break;
                }
            }
        }

        if (perm_ok)
            return true;

        return false;
    }

    /** This finds all the rewrites of the first concept which enable it to imply
     * the second concept.
     */

    public Vector implyingRewrites(Concept lh_concept, Concept rh_concept, boolean allow_all)
    {
        Vector output = new Vector();

        // Return an empty vector if the concepts contain different ground variables

        for (int i=0; i<rh_concept.skolemised_representation.ground_variables.size(); i++)
        {
            String gv = (String)rh_concept.skolemised_representation.ground_variables.elementAt(i);
            if (!lh_concept.skolemised_representation.ground_variables.contains(gv))
                return new Vector();
        }

        Vector rh_re_writes =
                getReWrites(rh_concept.skolemised_representation, lh_concept.skolemised_representation, allow_all);
        if (rh_re_writes==null)
            return new Vector();

        for (int i=0; i<rh_re_writes.size() && i<max_num_of_rewrites; i++)
        {
            Vector rh_re_write = (Vector)rh_re_writes.elementAt(i);
            Vector rh_skol_rewritten = rewriteSkolemisation(rh_concept.skolemised_representation, rh_re_write);
            boolean all_there = true;
            for (int j=0; j<rh_skol_rewritten.size() && all_there; j++)
            {
                Vector rh_rel_col = (Vector)rh_skol_rewritten.elementAt(j);
                if (!lh_concept.skolemised_representation.relation_columns.contains(rh_rel_col))
                    all_there = false;
            }
            if (all_there)
                output.addElement(rh_re_write);
        }
        output.trimToSize();
        return output;
    }

    /** This checks whether the skolemised version of the previous concept makes
     * all the specifications in the new concept redundant. That is, the new concept
     * says nothing more than the old concept.
     */

    public boolean leftSkolemisedImpliesRight(Concept lh_concept, Concept rh_concept, boolean allow_all)
    {
        for (int i=0; i<rh_concept.skolemised_representation.ground_variables.size(); i++)
        {
            String gv = (String)rh_concept.skolemised_representation.ground_variables.elementAt(i);
            if (!lh_concept.skolemised_representation.ground_variables.contains(gv))
                return false;
        }

        Vector rh_re_writes =
                getReWrites(rh_concept.skolemised_representation, lh_concept.skolemised_representation, allow_all);
        if (rh_re_writes==null)
            return false;

        boolean rh_is_rewritable = false;
        for (int i=0; i<rh_re_writes.size() && i<max_num_of_rewrites && !rh_is_rewritable; i++)
        {
            Vector rh_re_write = (Vector)rh_re_writes.elementAt(i);
            Vector rh_skol_rewritten = rewriteSkolemisation(rh_concept.skolemised_representation, rh_re_write);
            boolean all_there = true;
            for (int j=0; j<rh_skol_rewritten.size() && all_there; j++)
            {
                Vector rh_rel_col = (Vector)rh_skol_rewritten.elementAt(j);
                if (!lh_concept.skolemised_representation.relation_columns.contains(rh_rel_col))
                    all_there = false;
            }
            if (all_there)
                rh_is_rewritable = true;
        }

        if (!rh_is_rewritable)
            return false;

        return true;
    }

    /** Re-writes the skolemised representation with the given rewrite rule.
     */

    public Vector rewriteSkolemisation(SkolemisedRepresentation skol, Vector re_write)
    {
        Vector output = new Vector();
        for (int i=0; i<skol.relation_columns.size(); i++)
        {
            Vector rel_col = (Vector)skol.relation_columns.elementAt(i);
            Vector new_rel_col = new Vector();
            new_rel_col.addElement(rel_col.elementAt(0));
            Vector cols = (Vector)rel_col.elementAt(1);
            Vector new_cols = new Vector();
            for (int j=0; j<cols.size(); j++)
            {
                String col = (String)cols.elementAt(j);
                String new_col = col;
                if (!col.substring(0,1).equals(":"))
                {
                    int ind = skol.variables.indexOf(col);
                    new_col = (String)re_write.elementAt(ind);
                }
                new_cols.addElement(new_col);
            }
            new_rel_col.addElement(new_cols);
            output.addElement(new_rel_col);
        }
        return output;
    }

    /** This finds the possible re-writes for turning the given (lh)
     * skolemised representation into the (rh) skolemised
     * representation. This doesn't guarantee that the rewrite will be
     * successful. Returns null if it can't find a way of re-writing a
     * particular variable.
     */

    public Vector getReWrites(SkolemisedRepresentation lh_skol, SkolemisedRepresentation rh_skol, boolean allow_all)
    {
        Vector output_vectors = new Vector();
        output_vectors.addElement(new Vector());
        for (int i=0; i<lh_skol.variables.size(); i++)
        {
            String var = (String)lh_skol.variables.elementAt(i);
            if (!varType(var).equals("gr"))
            {
                Vector rewrite_vars = new Vector();
                Vector rel_positions = (Vector)lh_skol.variable_relation_position_hashtable.get(var);
                if (rel_positions==null)
                    return null;
                for (int j=0; j<rel_positions.size(); j++)
                {
                    String rel_position = (String)rel_positions.elementAt(j);
                    Vector poss_vars = (Vector)rh_skol.relation_position_hashtable.get(rel_position);
                    if (poss_vars==null)
                        return null;
                    if (j==0)
                    {
                        rewrite_vars = (Vector)poss_vars.clone();
                        if (varType(var).equals("var"))
                        {
                            for (int k=0; k<rewrite_vars.size(); k++)
                            {
                                if (varType((String)rewrite_vars.elementAt(k)).equals("ex") && !allow_all)
                                {
                                    rewrite_vars.removeElementAt(k);
                                    k--;
                                }
                            }
                        }
                    }
                    else
                    {
                        for (int k=0; k<rewrite_vars.size(); k++)
                            if (!poss_vars.contains((String)rewrite_vars.elementAt(k)))
                                rewrite_vars.removeElementAt(k--);
                    }
                }
                int old_size = output_vectors.size();
                if (rewrite_vars.isEmpty())
                    return null;
                for (int j=0; j<old_size; j++)
                {
                    for (int k=0; k<rewrite_vars.size() && k<max_num_of_rewrites; k++)
                    {
                        Vector new_output_vector =
                                (Vector)((Vector)output_vectors.elementAt(0)).clone();
                        String rewrite_var = (String)rewrite_vars.elementAt(k);
                        new_output_vector.addElement(rewrite_var);
                        output_vectors.addElement(new_output_vector);
                        if (output_vectors.size() > max_num_of_rewrites)
                            return new Vector();
                    }
                    output_vectors.removeElementAt(0);
                }
            }
        }
        output_vectors.trimToSize();
        return output_vectors;
    }

    private String varType(String s)
    {
        if (s.substring(0,1).equals("!"))
            return "ex";
        if (s.substring(0,1).equals(":"))
            return "gr";
        return "var";
    }

    /** This checks whether the specifications of a given new concept can be
     * rejected for one of many reasons.
     */

    public Vector rejectSpecifications(String forced_id, Step step, Concept new_concept, Theory theory)
    {
        Vector output = new Vector();

        // 3.4.1 First check for p(a) and -p(a) //

        theory.addToTimer("3.4.1 Checking for bad negation in specifications");
        boolean has_bad_negation = false;
        for (int i=0; i<new_concept.specifications.size() && !has_bad_negation; i++)
        {
            Specification spec = (Specification)new_concept.specifications.elementAt(i);
            if (spec.type.equals("negate"))
            {
                for (int j=0; j<new_concept.specifications.size() && !has_bad_negation; j++)
                {
                    Specification other_spec = (Specification)new_concept.specifications.elementAt(j);
                    if (!other_spec.type.equals("negate"))
                        has_bad_negation = spec.isNegationOf(other_spec);
                }
            }
        }
        if (has_bad_negation)
        {
            output.addElement("specifications have bad negation");
            return output;
        }

        // 3.4.2 Next check whether there are any conflicting functions, i.e. something like
        // f(a,b) = 3 and f(a,b) = 4.

        theory.addToTimer("3.4.1 Checking for function conflicts in specifications");
        boolean has_conflict = false;
        int i=0;
        while (!has_conflict && i<new_concept.functions.size())
        {
            Function first_function = (Function)new_concept.functions.elementAt(i);
            int j=i+1;
            while (!has_conflict && j<new_concept.functions.size())
            {
                Function second_function = (Function)new_concept.functions.elementAt(j);
                has_conflict = first_function.hasConflictWith(second_function);
                j++;
            }
            i++;
        }
        if (has_conflict)
        {
            output.addElement("new specifications have a function conflict");
            return output;
        }

        // 3.4.3 Next check whether any old concept has the same specifications //

        theory.addToTimer("3.4.3 Checking if a previous concept has same specifications");
        Concept old_concept = checkForMatch(new_concept, step, forced_id);
        if (old_concept!=null)
        {
            output.addElement("old concept with same specifications");
            output.addElement(old_concept);
            return output;
        }

        // 3.4.4 Next check whether any non-existence conjecture rules this concept out.

        theory.addToTimer("3.4.4 Checking whether any non-existence conjectures rule the concept out");
        i=0;
        boolean non_existent = false;
        while (!non_existent && i<non_existences.size())
        {
            NonExists conjecture = (NonExists)non_existences.elementAt(i);
            if (!conjecture.proof_status.equals("disproved") &&
                    !conjecture.concept.is_object_of_interest_concept &&
                    conjecture.concept.isGeneralisationOf(new_concept)>=0)
                non_existent = true;

            i++;
        }
        if (non_existent)
        {
            output.addElement("non-existence conjecture rules the specifications out");
            return output;
        }
        output.addElement("is good");
        return output;
    }

    /** Checks whether an equivalence conjecture is a tautology, based on how it was constructed.
     */

    public boolean isTautology(Equivalence conjecture)
    {
        boolean output = false;

        /** forall A, P(A) <-> not (exists A (not P(A))) **/
        /** not (exists A (not P(A))) <-> forall A, P(A) **/

        for (int i=0; i<2; i++)
        {
            Step lh_construction = conjecture.lh_concept.construction;
            Step rh_construction = conjecture.rh_concept.construction;
            if (i==1)
            {
                lh_construction = conjecture.rh_concept.construction;
                rh_construction = conjecture.lh_concept.construction;
            }

            if (lh_construction.productionRule().getName().equals("forall") &&
                    lh_construction.parameters().toString().equals("[s, 1]") &&
                    rh_construction.productionRule().getName().equals("negate"))
            {
                Vector lh_concepts = lh_construction.conceptList();
                Concept lh_concept0 = (Concept)lh_concepts.elementAt(0);
                Concept lh_concept1 = (Concept)lh_concepts.elementAt(1);
                Concept lh_concept2 = (Concept)lh_concepts.elementAt(2);

                Vector rh_concepts = rh_construction.conceptList();
                Concept rh_concept0 = (Concept)rh_concepts.elementAt(0);
                Concept should_be_lh_concept0 = (Concept)rh_concepts.elementAt(1);

                if (should_be_lh_concept0==lh_concept0 &&
                        rh_concept0.construction.productionRule().getName().equals("exists") &&
                        rh_concept0.construction.parameters().toString().equals("[1]"))
                {
                    Concept rh_concept1 = (Concept)rh_concept0.construction.conceptList().elementAt(0);
                    if (rh_concept1.construction.productionRule().getName().equals("negate"))
                    {
                        Concept should_be_lh_concept1 = (Concept)(rh_concept1.construction.conceptList()).elementAt(0);
                        Concept should_be_lh_concept2 = (Concept)(rh_concept1.construction.conceptList()).elementAt(1);
                        if (should_be_lh_concept1==lh_concept1 &&
                                should_be_lh_concept2==lh_concept2)
                            System.out.println(conjecture.writeConjecture("ascii"));
                    }
                }
            }
        }
        return output;
    }
}
