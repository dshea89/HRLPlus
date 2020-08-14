package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.TextField;
import java.io.Serializable;

/** A class representing a near implication conjecture (P ~> Q) in the theory.
 * @author Alison Pease, started 28th March 2003
 * @version 1.0
 * @see Conjecture
 */

public class NearImplication extends Conjecture implements Serializable
{
    /** The left hand concept in this near_implication.
     */

    public Concept lh_concept = new Concept();

    /** The right hand concept in this near_implication.
     */

    public Concept rh_concept = new Concept();

    /** The percentage (as a decimal) of entities which match in this
     * near implication conjecture.
     */

    public double score = 0;

    /** The simple constructor for near implications.
     */

    public NearImplication()
    {
    }

    /** The default constructor for near implications.
     */

    public NearImplication(Concept given_lh_concept, Concept given_rh_concept,
                           Vector given_counterexamples, double given_score)
    {
        lh_concept = given_lh_concept;
        rh_concept = given_rh_concept;
        counterexamples = given_counterexamples;
        score = given_score;
    }


    /** Writes the conjecture as a string in the given language.
     */

    public String writeConjecture(String language)
    {
        Vector letters = lh_concept.definition_writer.lettersForTypes(lh_concept.types,language,new Vector());
        String lh_string = lh_concept.writeDefinition(language);
        String rh_string = rh_concept.writeDefinition(language);
        if (language.equals("ascii"))
            return writeAsciiConjecture(letters, lh_string, rh_string);
        if (language.equals("otter"))
            return writeOtterConjecture(letters, lh_string, rh_string);
        if (language.equals("tptp"))
            return writeTPTPConjecture(letters, lh_string, rh_string);
        if (language.equals("prolog"))
            return writePrologConjecture(letters, lh_string, rh_string);
        return "";
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
        return output + ": " + lh_string + " ~> " + rh_string;
    }

    private String writePrologConjecture(Vector letters, String lh_string, String rh_string)
    {
        String output = " for all ";
        for (int i=0; i<letters.size(); i++)
            output = output + (String)letters.elementAt(i) + " ";
        return output + ": " + lh_string + " ~> " + rh_string;
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
            output = output + "((" + lh_string + ") ~> (" + rh_string + "))";
        if (lh_string.equals(""))
            output = output + "(" + rh_string + ")";
        if (letters.size()>1 || use_entity_letter)
            output = output + ")";
        return output;
    }
}
