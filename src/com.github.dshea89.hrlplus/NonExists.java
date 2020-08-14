package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.TextField;
import java.io.Serializable;

/** A class representing a non-existence conjecture in the theory.
 * @author Simon Colton, started 5th October 2000
 * @version 1.0
 * @see Conjecture
 */

public class NonExists extends Conjecture implements Serializable
{
    /** The concept which is conjectured to have no examples.
     */

    public Concept concept = new Concept();

    /** The simple constructor
     */

    public NonExists()
    {
    }

    /** Constructor given the concept and the id number.
     */

    public NonExists(Concept c, String id_number)
    {
        concept = c;
        if (concept.is_entity_instantiations)
            involves_instantiation = true;
        id = id_number;
        type = "non-exists";
        object_type = concept.object_type;
    }

    /** Returns the domain of the left hand concept.
     */

    public String getDomain()
    {
        return concept.domain;
    }

    /** Writes the conjecture as a string in the given language.
     */

    public String writeConjecture(String language)
    {
        Vector letters = concept.definition_writer.lettersForTypes(concept.types,"ascii",new Vector());
        String output = "";

        if (language.equals("ascii"))
        {
            output = "not exists " +
                    letters.toString().substring(1,letters.toString().length()-1) + " s.t. ( ";
            output = output + concept.writeDefinition(language)+" ) ";
        }

        if (language.equals("prolog"))
        {
            output = "not exists " +
                    letters.toString().substring(1,letters.toString().length()-1).toUpperCase() + " s.t. ( ";
            output = output + concept.writeDefinition(language)+" ) ";
        }

        if (language.equals("tptp")) {
            String conceptDefinition = this.concept.writeDefinition(language);
            String interimOutput = "[";

            for(int i = 1; i < letters.size() - 1; ++i) {
                interimOutput = interimOutput + ((String)letters.elementAt(i)).toUpperCase() + ",";
            }

            if (letters.size() > 1) {
                interimOutput = interimOutput + ((String)letters.elementAt(letters.size() - 1)).toUpperCase();
            }

            interimOutput = interimOutput + "]";
            output = "";
            if (!conceptDefinition.trim().equals("") && !conceptDefinition.trim().equals("()") && !conceptDefinition.trim().equals("(())")) {
                if (interimOutput.equals("[]")) {
                    output = "input_formula(conjecture" + this.id + ",conjecture,(\n     ~(" + conceptDefinition + "))).";
                } else {
                    output = "input_formula(conjecture" + this.id + ",conjecture,(\n     ~? " + interimOutput + " : \n      (" + conceptDefinition + "))).";
                }
            }
        }

        if (language.equals("otter"))
        {
            if (letters.size()>1 || use_entity_letter)
            {
                output = "-(exists ";
                int start_pos = 1;
                if (use_entity_letter)
                    start_pos = 0;
                for (int i=start_pos; i<letters.size(); i++)
                    output = output + (String)letters.elementAt(i) + " ";
                output = output + "(" + concept.writeDefinition(language) + "))";
            }
            else
            {
                if (!concept.writeDefinition(language).equals(""))
                    output = "-(" + concept.writeDefinition(language) + ")";
            }
        }

        return output;
    }

    /** Checks whether this conjecture subsumes the given conjecture.
     */

    public boolean subsumes(NonExists other_conjecture)
    {
        boolean output = true;
        if (other_conjecture.type.equals("non-exists"))
        {
            int i=0;
            while (output && i<concept.specifications.size())
            {
                Specification specification = (Specification)concept.specifications.elementAt(i);
                if (!other_conjecture.concept.specifications.contains(specification))
                    output = false;
                i++;
            }
        }
        return output;
    }

    /** Produces a set of implicate conjectures by negating one specification
     * which becomes the goal, with the others making up the premises.
     */

    public Vector implicates(Vector concepts)
    {
        Vector output = new Vector();
        for (int i=0; i<concept.specifications.size(); i++)
        {
            Vector slim_specifications = (Vector)concept.specifications.clone();
            slim_specifications.removeElementAt(i);
            boolean has_match = false;
            Concept lh_concept = new Concept();
            for (int j=0; j<concepts.size() && !has_match; j++)
            {
                lh_concept = (Concept)concepts.elementAt(j);
                has_match = true;
                if (lh_concept.specifications.size()==slim_specifications.size())
                {
                    for (int k=0; k<lh_concept.specifications.size() && has_match; k++)
                    {
                        Specification spec = (Specification)lh_concept.specifications.elementAt(k);
                        if (!spec.equals((Specification)slim_specifications.elementAt(k)))
                            has_match=false;
                    }
                }
                else
                    has_match=false;
            }
            if (has_match)
            {
                Specification specification = ((Specification)concept.specifications.elementAt(i)).copy();
                Specification negated_specification = new Specification();
                if (!specification.type.equals("negate"))
                {
                    negated_specification.type="negate";
                    negated_specification.previous_specifications.addElement(specification);
                    negated_specification.permutation = (Vector)specification.permutation.clone();

                    Vector new_perm = new Vector();
                    for (int j=0; j<specification.permutation.size(); j++)
                        new_perm.addElement(Integer.toString(negated_specification.permutation.
                                indexOf((String)specification.permutation.elementAt(j))));
                    specification.permutation=new_perm;
                    Implicate implicate = new Implicate(lh_concept, negated_specification, new Step());
                    implicate.overriding_types = (Vector)concept.types.clone();
                    output.addElement(implicate);
                }
                else
                {
                    for (int j=0; j<specification.previous_specifications.size(); j++)
                    {
                        Vector new_perm = new Vector();
                        Specification previous_spec =
                                ((Specification)specification.previous_specifications.elementAt(j)).copy();
                        for (int k=0; k<previous_spec.permutation.size(); k++)
                        {
                            int pos = (new Integer((String)previous_spec.permutation.elementAt(k))).intValue();
                            new_perm.addElement(specification.permutation.elementAt(pos));
                        }
                        previous_spec.permutation = new_perm;
                        Implicate implicate = new Implicate(lh_concept, previous_spec, new Step());
                        implicate.overriding_types = (Vector)concept.types.clone();
                        output.addElement(implicate);
                    }
                }
            }
        }
        return output;
    }
}
