package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class for writing definitions.
 *
 * @author Simon Colton, started 21st November 2001
 * @version 1.0 */

public class DefinitionWriter implements Serializable
{
    /** Whether to remove existence variables by writing them as the output of
     * a function.
     */

    public boolean remove_existence_variables = false;

    /** Whether or not to surround each letter by the @ sign. This is in order
     * to tell which letters have been added in.
     */

    public boolean surround_by_at_sign = false;



    public String writeDefinitionWithStartLetters(Concept concept, String language)
    {
        Vector letters = lettersForTypes(concept.types,language,new Vector());
        if (language.equals("prolog"))
            return letters.toString().toUpperCase() + " : " + writeDefinition(concept, language);
        return letters.toString() + " : " + writeDefinition(concept, language);
    }

    /** This will write the definition in the given language, omitting the initial
     * letters and using the letters supplied.
     */

    public String writeDefinition(Concept concept, String language, Vector letters)
    {
        String output = "";
        Vector all_letters = (Vector)letters.clone();
        String conjunction_sign = "";
        if (language.equals("ascii") || language.equals("otter") || language.equals("tptp"))
            conjunction_sign = " & ";
        if (language.equals("otter_demod"))
            conjunction_sign = ".\n";
        if (language.equals("prolog"))
            conjunction_sign = ", ";
        Vector specs_to_be_written = new Vector();
        specs_to_be_written = concept.specifications;

        Vector non_trivial_specs = new Vector();
        for (int i=0;i<specs_to_be_written.size();i++)
        {
            Specification specification =
                    (Specification)specs_to_be_written.elementAt(i);
            String spec_string = writeSpecification(specification,letters,all_letters,language,concept);
            if (!spec_string.equals(""))
                non_trivial_specs.addElement(spec_string);
        }
        for (int i=0; i<non_trivial_specs.size()-1; i++)
            output = output + (String)non_trivial_specs.elementAt(i) + conjunction_sign;
        if (!non_trivial_specs.isEmpty())
            output = output + (String)non_trivial_specs.lastElement();
        return output;
    }

    /** This will write the definition for this concept in the given language, but
     * will omit the initial letters (in the square brackets).
     */

    public String writeDefinition(Concept concept, String language)
    {
        String output = "";
        Vector letters = lettersForTypes(concept.types,language,new Vector());
        Vector all_letters = (Vector)letters.clone();
        String conjunction_sign = "";
        if (language.equals("ascii") || language.equals("otter") || language.equals("tptp"))
            conjunction_sign = " & ";
        if (language.equals("otter_demod"))
            conjunction_sign = ".\n";
        if (language.equals("prolog"))
            conjunction_sign = ", ";
        Vector specs_to_be_written = new Vector();
        specs_to_be_written = concept.specifications;

        Vector non_trivial_specs = new Vector();
        for (int i=0;i<specs_to_be_written.size();i++)
        {
            Specification specification =
                    (Specification)specs_to_be_written.elementAt(i);
            String spec_string = writeSpecification(specification,letters,all_letters,language,concept);
            if (!spec_string.equals(""))
                non_trivial_specs.addElement(spec_string);
        }
        for (int i=0; i<non_trivial_specs.size()-1; i++)
            output = output + (String)non_trivial_specs.elementAt(i) + conjunction_sign;
        if (!non_trivial_specs.isEmpty())
            output = output + (String)non_trivial_specs.lastElement();
        if (language.equals("otter_demod"))
            output = output + ".";
        return output;
    }

    private String writeSpecification(Specification specification, Vector letters,
                                      Vector all_letters, String language, Concept concept)
    {
        String output = "";
        String spec_output = "";
        String conjunction_sign = " & ";
        String implication_sign = " -> ";
        String disjunct_sign = " | ";
        String negation_sign = "-";
        if (language.equals("prolog"))
        {
            conjunction_sign = ", ";
            disjunct_sign = "; ";
            negation_sign = " \\+ ";
        }
        if (language.equals("tptp"))
        {
            negation_sign = "~";
            implication_sign = " => ";
        }
        if (specification.isMultiple())
        {
            Vector letters_for_specification = new Vector();

            // First permute the given letters for the specification

            for (int i=0;i<specification.permutation.size();i++)
            {
                int letter_pos = (new Integer((String)specification.permutation.elementAt(i))).intValue();
                //test here

                // String add_letter = new String();
// 	    if(letter_pos<letters.size())
// 	      if(0<=letter_pos)
// 	      add_letter = (String)letters.elementAt(letter_pos);
// 	    else
// 	      {System.out.println("System.out.println in writeSpecification in DefWriter");}

                String add_letter = (String)letters.elementAt(letter_pos);

                letters_for_specification.addElement(add_letter);
            }

            // Special case - embed graph specifications //

            if (specification.type.length()>10)
            {
                if (specification.type.substring(0,11).equals("embed graph"))
                {
                    output = "graph_embeded_by_concept"+
                            specification.type.substring(11,specification.type.length())
                            + "(" + (String)letters_for_specification.elementAt(0) + ", " +
                            (String)letters_for_specification.elementAt(1) + ")";
                    return output;
                }
            }

            // Next add in the dummy letters (will not be used) and additional letters

            Vector additional_letters = new Vector();
            if (!specification.type.equals("split"))
                additional_letters = lettersForTypes(specification.multiple_types, language, all_letters);
            int place_in_pos = 0;
            int number_used = 0;
            int additional_number = 0;

            while(number_used < specification.multiple_types.size() + specification.redundant_columns.size())
            {
                String place_string = Integer.toString(place_in_pos);
                if (specification.redundant_columns.contains(place_string))
                {
                    number_used++;
                    letters_for_specification.insertElementAt("%",place_in_pos+additional_number);
                }
                int additional_pos = specification.multiple_variable_columns.indexOf(place_string);
                String additional_letter = "";
                if (additional_pos>=0)
                {
                    if (specification.type.equals("split"))
                        additional_letter = (String)specification.fixed_values.elementAt(additional_pos);
                    else
                        additional_letter = (String)additional_letters.elementAt(additional_pos);
                    letters_for_specification.insertElementAt(additional_letter, place_in_pos);
                    number_used++;
                    additional_number++;
                }
                place_in_pos++;
            }

            if (specification.type.equals("exists") && remove_existence_variables &&
                    (language.equals("otter") || language.equals("otter_demod")))
                output = writeSpecificationRemovingExistenceVariables(specification, letters_for_specification,
                        all_letters, language, concept);

            if (!(output.equals("")))
                return output;

            // Write the previous specifications

            int rh_starts = specification.rh_starts;
            Vector specs_to_be_written = new Vector();
            for (int i=0;i<specification.previous_specifications.size();i++)
                specs_to_be_written.addElement(Integer.toString(i));

            String conjunction = "";
            String previous_spec = "";
            boolean used_implication_sign = false;
            boolean used_disjunct_sign = false;
            Vector non_trivial_specs = new Vector();
            boolean left_hand_bracket_needed = false;
            boolean right_hand_bracket_needed = false;
            String lhs_first_non_trivial_spec_type = "";
            String rhs_first_non_trivial_spec_type = "";
            for (int i=0;i<specs_to_be_written.size();i++)
            {
                Specification previous_specification =
                        (Specification)specification.previous_specifications.
                                elementAt((new Integer((String)specs_to_be_written.elementAt(i))).intValue());
                String spec = writeSpecification(previous_specification,
                        letters_for_specification,all_letters,language,concept);
                if (!spec.equals(""))
                {
                    if (non_trivial_specs.isEmpty() && previous_specification.previous_specifications.size()>1)
                        lhs_first_non_trivial_spec_type = previous_specification.type;
                    if (i==rh_starts && previous_specification.previous_specifications.size()>1)
                        rhs_first_non_trivial_spec_type = previous_specification.type;
                    if (!((language.equals("otter") || language.equals("otter_demod"))
                            && specification.type.equals("forall") &&
                            non_trivial_specs.contains(spec)))
                        non_trivial_specs.addElement(spec);
                }
                if (spec.equals("") && i<specification.rh_starts)
                    rh_starts--;
            }
            boolean need_bracket = false;
            if (language.equals("ascii") && specification.type.equals("forall"))
            {
                spec_output = spec_output + "(";
            }
            if ((language.equals("otter") || language.equals("otter_demod")) && specification.type.equals("forall"))
            {
                if (rh_starts==0)
                    used_implication_sign = true;
                spec_output = spec_output + "all ";
                for (int j=0; j<additional_letters.size(); j++)
                    spec_output = spec_output + (String)additional_letters.elementAt(j) + " ";
                spec_output = spec_output + "((";
                need_bracket = true;
            }
            if (language.equals("tptp") && specification.type.equals("forall"))
            {
                if (rh_starts==0)
                    used_implication_sign = true;
                spec_output = spec_output + "! [";
                for (int j=0; j<additional_letters.size()-1; j++)
                    spec_output = spec_output + (String)additional_letters.elementAt(j) + ",";
                spec_output = spec_output + (String)additional_letters.elementAt(additional_letters.size()-1);
                spec_output = spec_output + "] : ((";
                need_bracket = true;
            }
            for (int i=0; i<non_trivial_specs.size()-1; i++)
            {
                conjunction = conjunction_sign;
                if (specification.type.equals("forall") && i==rh_starts-1 && !used_implication_sign)
                {
                    conjunction = ")"+implication_sign+"(";
                    used_implication_sign = true;
                }
                if (specification.type.equals("disjunct") && i==rh_starts-1 && !used_disjunct_sign)
                {
                    conjunction = disjunct_sign;
                    if (rh_starts>1 || lhs_first_non_trivial_spec_type.equals("split"))
                    {
                        conjunction = ")"+conjunction;
                        left_hand_bracket_needed =true;
                    }
                    if (non_trivial_specs.size()-i > 2 || rhs_first_non_trivial_spec_type.equals("split"))
                    {
                        conjunction = conjunction + "(";
                        right_hand_bracket_needed = true;
                    }
                    used_disjunct_sign = true;
                }
                spec_output = spec_output + (String)non_trivial_specs.elementAt(i) + conjunction;
            }
            if (!non_trivial_specs.isEmpty())
                spec_output = spec_output + (String)non_trivial_specs.lastElement();
            if (need_bracket)
                spec_output = spec_output + ")";

            // Finish with the syntactic sugar which accompanies the definition.

            if (!non_trivial_specs.isEmpty())
            {
                if (specification.type.equals("split"))
                    output = spec_output;
                if (specification.type.equals("size"))
                {
                    int last_pos = (new Integer((String)specification.permutation.lastElement())).intValue();
                    String last_letter= (String)letters.elementAt(last_pos);
                    if (language.equals("ascii") ||
                            language.equals("prolog") ||
                            language.equals("otter") ||
                            language.equals("otter_demod") ||
                            language.equals("tptp"))
                    {
                        output = output + letters.elementAt(last_pos) + "=|{";
                        if (additional_letters.size()>1) output = output + "(";
                        for (int i=0;i<additional_letters.size();i++)
                        {
                            output = output + (String)additional_letters.elementAt(i);
                            if (i<additional_letters.size()-1) output = output + " ";
                        }
                        if (additional_letters.size()>1) output = output + ")";
                        output = output + ": " + spec_output + "}|";
                    }
                }

                if (specification.type.equals("exists"))
                {
                    if (language.equals("otter"))
                        output = output + "(";
                    if (language.equals("otter_demod"))
                        output = output + "(";
                    if (language.equals("ascii") || language.equals("otter") || language.equals("otter_demod"))
                        output = output + "exists ";
                    if (language.equals("tptp"))
                        output = output + "? [";
                    String connective = " ";
                    if (language.equals("tptp"))
                        connective = ",";
                    if (!language.equals("prolog"))
                    {
                        for (int i=0;i<additional_letters.size();i++)
                        {
                            if (language.equals("tptp") && i==additional_letters.size()-1)
                                connective="";
                            output = output + (String)additional_letters.elementAt(i) + connective;
                        }
                    }
                    if (language.equals("tptp"))
                        output = output + "] : ";
                    if (!language.equals("prolog"))
                        output = output + "(" + spec_output + ")";
                    else
                        output = spec_output;
                    if (language.equals("otter"))
                        output = output + ")";
                    if (language.equals("otter_demod"))
                        output = output + ")";
                }

                if (specification.type.equals("forall"))
                    output = output + "(" + spec_output + "))";

                if (specification.type.equals("disjunct"))
                {
                    output = output + "(" + spec_output + ")";
                    if (left_hand_bracket_needed)
                        output = "(" + output;
                    if (right_hand_bracket_needed)
                        output = output + ")";
                }

                if (specification.type.equals("negate"))
                    output = output + negation_sign + "(" + spec_output + ")";

                if (specification.type.equals("record"))
                    output = output + (String)letters.elementAt(0) + " sets record for: (" + spec_output + ")";
            }
        }
        else output = output + specification.writeDefinition(language, letters);

        return output;
    }

    public String writeSpecificationRemovingExistenceVariables(Specification specification, Vector letters,
                                                               Vector all_letters, String language, Concept concept)
    {
        boolean made_substitution = false;
        Vector specs_used_for_substitution = new Vector();
        Vector letters_with_substitutions = (Vector)letters.clone();
        Vector all_spec_equality_vectors = new Vector();
        Vector ex_var_columns_substituted = new Vector();
        for (int i=0; i < specification.multiple_variable_columns.size(); i++)
        {
            Vector spec_equality_vector = new Vector();
            all_spec_equality_vectors.addElement(spec_equality_vector);
            String ex_var_column_string = (String)specification.multiple_variable_columns.elementAt(i);
            int ex_var_column = (new Integer(ex_var_column_string)).intValue();
            boolean found_substitution = false;
            for (int j=0; j<specification.previous_specifications.size() && !found_substitution; j++)
            {
                Specification previous_spec =
                        (Specification)specification.previous_specifications.elementAt(j);
                if (previous_spec.isMultiple()==false)
                {
                    Vector perm = previous_spec.permutation;

                    Vector ex_var_positions_for_prev_spec = new Vector();
                    for (int k=0; k<perm.size(); k++)
                    {
                        String pos_string = (String)perm.elementAt(k);
                        int pos = (new Integer(pos_string)).intValue();
                        if (pos==ex_var_column)
                            ex_var_positions_for_prev_spec.addElement(Integer.toString(k));
                    }

                    if (ex_var_positions_for_prev_spec.size()==1)
                    {
                        for (int k=0; k<previous_spec.functions.size(); k++)
                        {
                            Function function = (Function)previous_spec.functions.elementAt(k);
                            Vector input_columns = (Vector)function.input_columns.clone();
                            Vector output_columns = (Vector)function.output_columns.clone();

                            for (int l=0; l<input_columns.size(); l++)
                            {
                                String ic = (String)input_columns.elementAt(l);
                                if (!(ic.substring(0,1)).equals(":"))
                                {
                                    int pos = (new Integer(ic)).intValue();
                                    String new_ic = (String)perm.elementAt(pos);
                                    input_columns.setElementAt(new_ic, l);
                                }
                            }

                            for (int l=0; l<output_columns.size(); l++)
                            {
                                String oc = (String)output_columns.elementAt(l);
                                if (!(oc.substring(0,1)).equals(":"))
                                {
                                    int pos = (new Integer(oc)).intValue();
                                    String new_oc = (String)perm.elementAt(pos);
                                    output_columns.setElementAt(new_oc, l);
                                }
                            }
                            if (!input_columns.contains(ex_var_column_string) && output_columns.size()==1 &&
                                    ((String)output_columns.elementAt(0)).equals(ex_var_column_string))
                            {
                                String definition_string = writeSpecification(previous_spec, letters_with_substitutions,
                                        all_letters, language, concept);
                                Relation relation = (Relation)previous_spec.relations.elementAt(0);
                                Definition defn = (Definition)relation.definitions.elementAt(0);

                                int equals_pos = defn.text.indexOf("=");
                                if (equals_pos>0)
                                {
                                    String substitution_string = "";
                                    String ex_var_pos_in_def = "@"+(String)ex_var_positions_for_prev_spec.elementAt(0)+"@";
                                    if (defn.text.indexOf(ex_var_pos_in_def)<equals_pos)
                                        substitution_string =
                                                definition_string.substring(definition_string.indexOf("=")+1, definition_string.length());
                                    else
                                        substitution_string =
                                                definition_string.substring(0,definition_string.indexOf("="));

                                    letters_with_substitutions.setElementAt("("+substitution_string+")", ex_var_column);
                                    ex_var_columns_substituted.addElement(ex_var_column_string);
                                    made_substitution = true;
                                    spec_equality_vector.addElement(previous_spec);
                                    specs_used_for_substitution.addElement(previous_spec);
                                    found_substitution = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!made_substitution)
            return "";

        String existence_letters_left = "";
        for (int i=0; i<specification.multiple_variable_columns.size(); i++)
        {
            String m_col = (String)specification.multiple_variable_columns.elementAt(i);
            if (!ex_var_columns_substituted.contains(m_col))
            {
                int pos = (new Integer(m_col)).intValue();
                String add_letter = (String)letters.elementAt(pos);
                existence_letters_left = existence_letters_left + add_letter + " ";
            }
        }

        String output = "";
        if (!existence_letters_left.equals(""))
            output = "exists "+existence_letters_left + "(";

        String quantified_specifications_string = "";

        for (int i=0; i<specification.previous_specifications.size(); i++)
        {
            Specification prev_spec = (Specification)specification.previous_specifications.elementAt(i);
            if (!specs_used_for_substitution.contains(prev_spec))
            {
                String written_prev_spec = writeSpecification(prev_spec, letters_with_substitutions,
                        all_letters, language, concept);
                if (!written_prev_spec.equals("") && i>0 &&
                        !quantified_specifications_string.equals(""))
                    quantified_specifications_string = quantified_specifications_string + " & ";

                quantified_specifications_string =
                        quantified_specifications_string + written_prev_spec;
            }
        }
        output = output + quantified_specifications_string;
        if (!existence_letters_left.equals(""))
            output = output + ")";
        return output;
    }

    /** Returns correct letters for the given types, choice of language and letters already used.
     */

    public Vector lettersForTypes(Vector input_types, String language, Vector letters_already)
    {
        String at_sign = "";
        if (surround_by_at_sign)
            at_sign = "@";
        Vector output = new Vector();
        for (int i=0; i<input_types.size();i++)
        {
            String type = (String)input_types.elementAt(i);
            String new_letter = "";
            if (language.equals("ascii") || language.equals("otter") ||
                    language.equals("prolog") || language.equals("tptp") || language.equals("otter_demod"))
            {
                String[] all_letters = {"","a","b","c","d","e","f","g","h",
                        "i","j","k","l","m","n","o","p","q",
                        "r","s","t","u","v","w","x","y","z"};

                String[] all_upper_letters = {"","A","B","C","D","E","F","G","H",
                        "I","J","K","L","M","N","O","P","Q",
                        "R","S","T","U","V","W","X","Y","Z"};

                int pos = 0;
                boolean found = false;
                for (int j=0; j<all_letters.length && !found; j++)
                {
                    for (int k=1; k<all_letters.length && !found; k++)
                    {
                        new_letter = all_letters[j]+all_letters[k];
                        if (language.equals("tptp") || language.equals("prolog"))
                            new_letter = all_upper_letters[j]+all_upper_letters[k];
                        if (!letters_already.contains(new_letter))
                            found=true;
                    }
                }
            }
            letters_already.addElement(new_letter);
            output.addElement(at_sign + new_letter + at_sign);
        }
        return output;
    }

    /** Returns a clone of the definition writer  - alisonp*/

    public Object clone()
    {
        DefinitionWriter definition_writer = new DefinitionWriter();
        definition_writer.remove_existence_variables = this.remove_existence_variables;
        definition_writer.surround_by_at_sign = this.surround_by_at_sign;
        return definition_writer;
    }

    /** Returns a string definition of the basic objects (eg. integers)
     which are equal to one of the elements in the vector
     entity_strings, given entity_strings = [1,2,3] returns
     "[a]: a=1 or a=2 or a=3" */

    public String writeDefinitionForGivenEntities(Concept concept, String language, Vector entity_strings)
    {
        String conjunction_sign = "";
        String disjunct_sign = " | ";

        if (language.equals("ascii") || language.equals("otter") || language.equals("tptp"))
            conjunction_sign = " & ";
        if (language.equals("otter_demod"))
            conjunction_sign = ".\n";
        if (language.equals("prolog"))
        {
            conjunction_sign = ", ";
            disjunct_sign = "; ";
        }

        String output = writeDefinition(concept,language);
        if(!(entity_strings.isEmpty()))
            output = output + conjunction_sign+"a = " + (String)entity_strings.elementAt(0);
        for(int i=1; i<entity_strings.size(); i++)
        {
            String current_entity = (String)entity_strings.elementAt(i);
            output = output+disjunct_sign+"a = "+current_entity;

        }
        //System.out.println(" output is now " + output);
        return output;
    }
}
