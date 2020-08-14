package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.awt.TextField;
import java.io.Serializable;

/** A class representing an implication where the left hand side is a concept,
 * as is the right hand side. In the implicate class, the right hand side is
 * a specification, not a full concept. ConceptImplication object can be disproved
 * by HR, which is not true of implicates.
 *
 * @author Simon Colton, started 11th December 1999
 * @version 1.0
 * @see Conjecture
 */

public class Implication extends Conjecture implements Serializable
{
    /** The percentage of entities for which this conjecture is true.
     */

    public Double score = new Double(0);

    /** The concept on the left hand side of the conjecture.
     */

    public Concept lh_concept = new Concept();

    /** The concept on the right hand side of the conjecture.
     */

    public Concept rh_concept = new Concept();

    /** Trivial constructor
     */

    public Implication()
    {
    }

    /** Constructor given the left hand and right hand concepts and the id number.
     * Both concepts are in the theory.
     */

    public Implication(Concept lh, Concept rh, String id_number)
    {
        lh_concept = lh;
        rh_concept = rh;
        if (lh_concept.is_entity_instantiations || rh_concept.is_entity_instantiations)
            involves_instantiation = true;
        id = id_number;
        type = "concept_implication";
        object_type = lh_concept.object_type;
    }

    /** This calculates the surprisingness of this implication.
     */

    public double surprisingness()
    {
        double output = 0;
        double shared = 0;
        for (int i=0; i<lh_concept.ancestor_ids.size(); i++)
        {
            if (!rh_concept.ancestor_ids.contains(lh_concept.ancestor_ids.elementAt(i)))
                output++;
        }
        for (int i=0; i<rh_concept.ancestor_ids.size(); i++)
        {
            if (!lh_concept.ancestor_ids.contains(rh_concept.ancestor_ids.elementAt(i)))
                output++;
            else
                shared++;
        }
        output = output / (lh_concept.ancestor_ids.size() + rh_concept.ancestor_ids.size() - shared);
        return output;
    }

    /** Writes the conjecture as a string in the given language.
     */

    public String writeConjecture(String language)
    {
        String output = "";
        Vector letters = lh_concept.definition_writer.lettersForTypes(lh_concept.types,language,new Vector());

        boolean old_b1 = rh_concept.definition_writer.remove_existence_variables;
        boolean old_b2 = rh_concept.definition_writer.remove_existence_variables;

        if (simplify_definitions)
        {
            lh_concept.definition_writer.remove_existence_variables = true;
            rh_concept.definition_writer.remove_existence_variables = true;
        }

        String lh_string = lh_concept.writeDefinition(language);
        String rh_string = rh_concept.writeDefinition(language);
        if (language.equals("ascii"))
            output = writeAsciiConjecture(letters, lh_string, rh_string);
        if (language.equals("otter"))
            output = writeOtterConjecture(letters, lh_string, rh_string);
        if (language.equals("tptp"))
            output = writeTPTPConjecture(letters, lh_string, rh_string);
        if (language.equals("prolog"))
            output = writePrologConjecture(letters, lh_string, rh_string);

        lh_concept.definition_writer.remove_existence_variables = old_b1;
        rh_concept.definition_writer.remove_existence_variables = old_b2;

        return output;
    }

    private String writePrologConjecture(Vector letters, String lh_string, String rh_string)
    {
        String output = " for all ";
        for (int i=0; i<letters.size(); i++)
            output = output + (String)letters.elementAt(i) + " ";
        return output + ": " + lh_string + " => " + rh_string;
    }

    private String writeTPTPConjecture(Vector letters, String lh_string, String rh_string)
    {
        if (lh_string.equals("") && rh_string.equals(""))
            return "";
        String letters_string = "[";
        for (int i=1; i<letters.size()-1; i++)
            letters_string=letters_string + (String)letters.elementAt(i)+",";
        if (letters.size()>1)
            letters_string=letters_string+(String)letters.elementAt(letters.size()-1);
        letters_string=letters_string + "]";
        String output = "";
        boolean printed_with_an_empty_side = false;
        if (lh_string.trim().equals("") || lh_string.trim().equals("()") || lh_string.trim().equals("(())"))
        {
            if (letters_string.equals("[]"))
                output = "input_formula(conjecture"+id+",conjecture,(\n     ("+rh_string+"))).";
            else
                output = "input_formula(conjecture"+id+",conjecture,(\n     ! "+letters_string+
                        " : \n      ("+rh_string+"))).";
            printed_with_an_empty_side = true;
        }

        if (rh_string.trim().equals("") || rh_string.trim().equals("()") || rh_string.trim().equals("(())"))
        {
            if (letters_string.equals("[]"))
                output = "input_formula(conjecture"+id+",conjecture,(\n     ("+lh_string+"))).";
            else
                output = "input_formula(conjecture"+id+",conjecture,(\n     ! "+letters_string+
                        " : \n      ("+lh_string+"))).";
            printed_with_an_empty_side = true;
        }

        if (!printed_with_an_empty_side)
        {
            if (letters_string.equals("[]"))
                output = "input_formula(conjecture"+id+",conjecture,(\n     (("+lh_string+")) \n       => ("+rh_string+"))).";
            else
                output = "input_formula(conjecture"+id+",conjecture,(\n     ! "+letters_string+
                        " : \n      (("+lh_string+" )\n       => ("+rh_string+")))).";
        }
        return output;
    }

    private String writeAsciiConjecture(Vector letters, String lh_string, String rh_string)
    {
        String output = " for all ";
        for (int i=0; i<letters.size(); i++)
            output = output + (String)letters.elementAt(i) + " ";
        return output + ": " + lh_string + " -> " + rh_string;
    }

    private String writeOtterConjecture(Vector letters, String lh_string, String rh_string)
    {
        String output = "";
        if (lh_string.equals("") && rh_string.equals(""))
            return "";
        if (rh_string.equals(""))
            return writeOtterConjecture(letters, rh_string, lh_string);
        if (letters.size()>1 || use_entity_letter)
            output = output + "all ";
        int start_pos = 1;
        if (use_entity_letter)
            start_pos = 0;
        for (int i=start_pos; i<letters.size(); i++)
            output = output + (String)letters.elementAt(i) + " ";
        if (letters.size()>1 || use_entity_letter)
            output = output + "(";
        if (!lh_string.equals("") && !rh_string.equals(""))
            output = output + "((" + lh_string + ") -> (" + rh_string + "))";
        if (lh_string.equals(""))
            output = output + "(" + rh_string + ")";
        if (letters.size()>1 || use_entity_letter)
            output = output + ")";
        return output;
    }


    /** This returns the vector of implicates extracted from this equivalence conjecture.
     * It uses the specification handler to check whether the goal specification of each
     * is not implied (after skolemisation) by the premise concept.
     */

    public Vector implicates(SpecificationHandler specification_handler)
    {
        Vector output = new Vector();
        for (int i=0; i<rh_concept.specifications.size(); i++)
        {
            Specification specification = (Specification)rh_concept.specifications.elementAt(i);
            if (!lh_concept.specifications.contains(specification))
            {
                Concept goal_concept = new Concept();
                goal_concept.specifications.addElement(specification);
                goal_concept.setSkolemisedRepresentation();
                if (!goal_concept.skolemised_representation.variables.isEmpty() &&
                        !specification_handler.leftSkolemisedImpliesRight(lh_concept, goal_concept, false))
                {
                    Implicate implicate = new Implicate(lh_concept, specification, step);
                    implicate.when_constructed = when_constructed;
                    implicate.proof_status = proof_status;
                    output.addElement(implicate);
                }
            }
        }
        return output;
    }

    //dec 2006
    /**This checks whether there are any (known) counterexamples in the datatables of the
     * concepts conjectured to be implication, and adds them to the counterexamples if they
     * are not already in there.
     */

    public Vector knownCounterexamples()
    {
        Vector counters = new Vector();

        Vector left = lh_concept.positiveEntities();
        Vector right = rh_concept.positiveEntities();

        for (int i=0; i<left.size(); i++)
        {
            Entity entity = (Entity)left.elementAt(i);
            boolean got_entity = false;
            for (int j=0; j<right.size(); j++)
            {
                Entity other_entity = (Entity)right.elementAt(j);
                if((entity.toString()).equals(other_entity.toString()))
                {
                    got_entity = true;
                    break;
                }
            }
            if(!got_entity)
                counters.addElement(entity);

            //if(!right.contains(entity))
            // counters.addElement(entity);
        }

        return counters;

    }

}
