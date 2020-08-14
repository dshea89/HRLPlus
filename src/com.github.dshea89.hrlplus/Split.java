package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;

/** A class representing the split production rule. This production
 * rule takes an old datatable as input and a set of parameterizations.
 * The parameterization is a pair of vectors where the first dictates which
 * columns are looked at and the second dictates which values are to be looked
 * for. Then, all rows where the values specified are in the columns specified
 * are extracted into the new table. Then the columns which have been specified
 * are removed as we know what is in them. We call the columns to be looked at
 * the split columns and the values to be looked for the split values.
 *
 * @author Simon Colton, started 9th January 2000
 * @version 1.0 */

public class Split extends ProductionRule implements Serializable
{
    /** Which concepts have had their split values extracted.
     */

    public Vector extracted_split_values_from = new Vector();

    /** Which types are OK for empirical splitting.
     */

    public Vector empirical_allowed_types = new Vector();

    /** The number of values which can be instantiated in one go.
     */

    public int tuple_size_limit = 1;

    /** As allowed values but with the column types taken into account.
     */

    public Vector fixed_allowed_values = new Vector();

    /** The user can specify which split values can be looked for (usually restricted to
     * small numbers like 1, 2 and 3.
     */

    public Vector allowed_values = new Vector();

    /** A pair of <concept, parameterisation> which should be returned along with the
     * parameterisations for that particular concept. (i.e. for learning purposes).
     */

    public Vector special_parameterisations = new Vector();

    /** Returns false as this is a unary production rule.
     */

    public boolean isBinary()
    {
        return false;
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean isCumulative()
    {
        return false;
    }

    /** Returns "split" as that is the name of this production rule.
     */

    public String getName()
    {
        return "split";
    }

    private Vector allParametersForTypes(Vector types, int old_arity, int arity_limit)
    {
        // See if the set has already been calculated and stored //

        boolean found = false;
        int i=0;
        String present_types_string = types.toString();
        Vector output = new Vector();
        while (!found && i<fixed_allowed_values.size())
        {
            String old_types_string =
                    (String)((Vector)fixed_allowed_values.elementAt(i)).elementAt(0);
            if (old_types_string.equals(present_types_string))
            {
                found = true;
                Vector temp_output = (Vector)((Vector)fixed_allowed_values.elementAt(i)).elementAt(1);
                for (int j=0; j<temp_output.size(); j++)
                {
                    Vector par = (Vector)temp_output.elementAt(j);
                    Vector new_par = new Vector();
                    new_par.addElement((Vector)((Vector)par.elementAt(0)).clone());
                    new_par.addElement((Vector)((Vector)par.elementAt(1)).clone());
                    output.addElement(new_par);
                }
            }
            i++;
        }

        if (found)
            return output;

        // Not already stored, so must be calculated //

        Vector add_to_fixed_allowed_values = new Vector();
        add_to_fixed_allowed_values.addElement(types.toString());
        Vector parameters = new Vector();
        Vector all_column_tuples = super.allColumnTuples(types.size());
        for (i=0; i<all_column_tuples.size();i++)
        {
            Vector sub_types = new Vector();
            Vector columns = (Vector)all_column_tuples.elementAt(i);
            if (columns.size() <= tuple_size_limit)
            {
                for (int j=0;j<columns.size();j++)
                {
                    String column = (String)columns.elementAt(j);
                    int pos = (new Integer(column)).intValue();
                    String type = (String)types.elementAt(pos);
                    sub_types.addElement(type);
                }

                Vector split_values = allParametersGivenTypes(sub_types);
                for (int j=0;j<split_values.size();j++)
                {
                    Vector new_split_values = (Vector)split_values.elementAt(j);
                    Vector new_parameter = new Vector();
                    new_parameter.addElement(columns);
                    new_parameter.addElement(new_split_values);
                    parameters.addElement(new_parameter);
                }
            }
        }
        output = new Vector();
        for (int j=0; j<parameters.size(); j++)
        {
            Vector par = (Vector)parameters.elementAt(j);
            Vector new_par = new Vector();
            new_par.addElement((Vector)((Vector)par.elementAt(0)).clone());
            new_par.addElement((Vector)((Vector)par.elementAt(1)).clone());
            output.addElement(new_par);
        }
        add_to_fixed_allowed_values.addElement(parameters);
        fixed_allowed_values.addElement(add_to_fixed_allowed_values);
        return output;
    }

    private Vector allParametersGivenTypes(Vector types)
    {
        Vector split_values_split = new Vector();
        String first_type = (String)types.elementAt(0);
        Vector first_split_values = getSplitValuesForType(first_type);
        Vector output = new Vector();
        for (int i=0;i<first_split_values.size();i++)
        {
            String split_value = (String)first_split_values.elementAt(i);
            Vector output_param = new Vector();
            output_param.addElement(split_value);
            output.addElement(output_param);
        }

        for (int i=1;i<types.size();i++)
        {
            String type = (String)types.elementAt(i);
            split_values_split.addElement(getSplitValuesForType(type));
        }

        for (int i=1;i<types.size();i++)
        {
            Vector new_output = new Vector();
            Vector additional_values = (Vector)split_values_split.elementAt(i-1);
            for (int j=0; j<output.size(); j++)
            {
                Vector partial_params = (Vector)output.elementAt(j);
                for (int k=0; k<additional_values.size(); k++)
                {
                    Vector new_partial_params = (Vector)partial_params.clone();
                    new_partial_params.addElement((String)additional_values.elementAt(k));
                    new_output.addElement(new_partial_params);
                }
            }
            output = new_output;
        }
        return output;
    }

    private Vector getSplitValuesForType(String type)
    {
        Vector output = new Vector();
        for (int i=0;i<allowed_values.size();i++)
        {
            String allowed = (String)allowed_values.elementAt(i);
            if (allowed.indexOf(type+":")>-1)
                output.addElement(allowed.substring(allowed.lastIndexOf(":")+1,allowed.length()));
        }
        return output;
    }

    /** Given a vector of one concept, this will return all the
     * parameterisations for this concept. If the find_empirically flag
     * is true, then it will return all tuples of columns and values
     * (found empirically) in those columns for the old concept. For
     * example, it will not generate parameters which look for integers
     * with 7 divisors if there are none in the old table. If the flag is
     * is negative, then the parameters are constructed from the allowed
     * values only.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        Vector types = old_concept.types;

        if (!extracted_split_values_from.contains(old_concept.id))
        {
            extracted_split_values_from.addElement(old_concept.id);
            for (int i=0; i<types.size(); i++)
            {
                String type = (String)types.elementAt(i);
                if (empirical_allowed_types.contains(type))
                {
                    for (int j=0; j<old_concept.datatable.size(); j++)
                    {
                        Row row = (Row)old_concept.datatable.elementAt(j);
                        if (i==0 && !allowed_values.contains(type+":"+row.entity))
                            allowed_values.addElement(type+":"+row.entity);
                        if (i>0)
                        {
                            for (int k=0; k<row.tuples.size(); k++)
                            {
                                Vector tuple = (Vector)row.tuples.elementAt(k);
                                String possibility = (String)tuple.elementAt(i-1);
                                if (!allowed_values.contains(type+":"+possibility))
                                    allowed_values.addElement(type+":"+possibility);
                            }
                        }
                    }
                }
            }
        }

        Vector output = allParametersForTypes(types, old_concept.arity, arity_limit);

        for (int i=0; i<special_parameterisations.size(); i++)
        {
            Vector sp = (Vector)special_parameterisations.elementAt(i);
            if (((String)sp.elementAt(0)).equals(old_concept.id))
                output.addElement(sp.elementAt(1));
        }
        Vector final_output = new Vector();
        Vector output_strings = new Vector();
        for (int i=0; i<output.size(); i++)
        {
            Vector p = (Vector)output.elementAt(i);
            if (old_concept.arity - ((Vector)p.elementAt(0)).size() <= arity_limit && !output_strings.contains(p.toString()))
            {
                final_output.addElement(p);
                output_strings.addElement(p.toString());
            }
        }
        return final_output;
    }

    /** This produces the new specifications for concepts output using the size production
     * rule. To do this, it first finds which old specifications do not involve any of the
     * split variables, and these specifications are added. Note that it may be
     * necessary to alter the permutations for these. Then, a single new specification is
     * added which specifies how the variables should be fixed.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Vector old_specifications = ((Concept)input_concepts.elementAt(0)).specifications;
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector old_types = old_concept.types;
        Vector all_positions_used = new Vector();
        Vector instantiated_specifications = new Vector();
        Vector split_columns = (Vector)input_parameters.elementAt(0);
        Vector split_values = (Vector)input_parameters.elementAt(1);

        // Special case: if the column to split on is the first (zero), then put
        // empty vectors into all the other columns.

        if (split_columns.size()==1 && split_columns.elementAt(0).equals("0"))
        {
            output = (Vector)old_specifications.clone();
            String value = (String)split_values.elementAt(0);
            Specification single_spec = new Specification();
            Vector permutation = new Vector();
            permutation.addElement("0");
            Relation relation = new Relation();
            relation.addDefinition("a","@a@="+value,"ascii");
            relation.name = old_concept.id+"="+value;
            single_spec.relations.addElement(relation);
            single_spec.permutation = permutation;
            Vector input_cols = new Vector();
            input_cols.addElement("0");
            Vector output_cols = new Vector();
            output_cols.addElement(":"+value+":");
            Function function = new Function("isinstantiated", input_cols, output_cols);
            single_spec.functions.addElement(function);
            single_spec.is_single_entity = true;
            output.addElement(single_spec);
            return output;
        }

        // Next run through all the old specifications and add them to the new specifications
        // if they are not involved in the split, or add them to the split specification
        // otherwise.

        for (int i=0;i<old_specifications.size();i++)
        {
            Specification old_specification = (Specification)old_specifications.elementAt(i);
            // Work out whether the old specification involves any variables which are to be
            // fixed.

            boolean involves_removed_columns = old_specification.involvesColumns(split_columns);

            // First case - old specification does not involve a variable which is to be fixed.
            // Must change the parameterisation to reflect the loss of columns.

            if (!involves_removed_columns)
            {
                Specification new_specification = old_specification.copy();
                new_specification.permutation = new Vector();
                for (int j=0;j<old_specification.permutation.size();j++)
                {
                    int number_of_columns_to_remove = 0;
                    int add_column =
                            (new Integer((String)old_specification.permutation.elementAt(j))).intValue();
                    for (int k=0;k<split_columns.size();k++)
                    {
                        int param =
                                (new Integer((String)split_columns.elementAt(k))).intValue();
                        if (param<add_column) number_of_columns_to_remove++;
                    }
                    new_specification.permutation.
                            addElement(Integer.toString(add_column-number_of_columns_to_remove));
                }
                output.addElement(new_specification);
            }

            // Second case - old specification gets added to previous specifications of the
            // split specification.

            if (involves_removed_columns)
            {
                // First split the parameters into split columns and split values.

                Specification split_specification = new Specification();
                split_specification.type = "split";
                split_specification.multiple_variable_columns = (Vector)split_columns.clone();
                split_specification.fixed_values = (Vector)split_values.clone();

                // Next make the multiple types for the split specification.

                for (int j=0;j<split_columns.size();j++)
                {
                    split_specification.multiple_types.addElement(old_types.
                            elementAt((new Integer((String)split_columns.elementAt(j))).intValue()));
                }

                split_specification.previous_specifications.addElement(old_specification);
                Vector old_spec_perm = old_specification.permutation;
                for (int j=0;j<old_spec_perm.size();j++)
                {
                    String pos = (String)old_spec_perm.elementAt(j);
                    if (!all_positions_used.contains(pos)) all_positions_used.addElement(pos);
                }
                instantiated_specifications.addElement(split_specification);

                output.addElement(split_specification);
            }
        }

        // Next, must specify which columns are used (put in the permutation) and
        // which columns are redundant. All these columns will be added in so that the
        // previous specifications can be used later.

        for (int i=0; i<instantiated_specifications.size(); i++)
        {
            Specification split_specification = (Specification)instantiated_specifications.elementAt(i);
            int add_to_perm_pos = 0;
            for (int j=0;j<old_types.size();j++)
            {
                String try_pos = Integer.toString(j);
                if (all_positions_used.contains(try_pos) && !split_columns.contains(try_pos))
                {
                    split_specification.permutation.addElement(Integer.toString(add_to_perm_pos));
                    add_to_perm_pos++;
                }
                if (!split_columns.contains(try_pos) && !all_positions_used.contains(try_pos))
                {
                    split_specification.redundant_columns.addElement(Integer.toString(add_to_perm_pos));
                    add_to_perm_pos++;
                }
            }
        }

        // Do the functions for the instantiated specifications //
        // Add them to the functions for the new concept //

        Vector total_function_strings = new Vector();
        int take_off = 0;
        for (int i=0; i<instantiated_specifications.size(); i++)
        {
            Specification split_specification = (Specification)instantiated_specifications.elementAt(i);
            for (int j=0; j<split_specification.previous_specifications.size(); j++)
            {
                Specification previous_spec = (Specification)split_specification.previous_specifications.elementAt(j);
                Vector function_strings = new Vector();
                for (int k=0; k<previous_spec.functions.size(); k++)
                {
                    Vector instantiated_columns = new Vector();
                    Function function = ((Function)previous_spec.functions.elementAt(j)).copy();

                    for (int l=0; l<function.input_columns.size(); l++)
                    {
                        String input_column = (String)function.input_columns.elementAt(l);
                        String original_input_column = (String)function.original_input_columns.elementAt(l);
                        if (!input_column.substring(0,1).equals(":"))
                        {
                            int ind = split_columns.indexOf(input_column);
                            if (ind >= 0)
                            {
                                String f_split_value = ":"+(String)split_values.elementAt(ind)+":";
                                function.input_columns.setElementAt(f_split_value,l);
                                instantiated_columns.addElement(original_input_column);
                            }
                        }
                    }

                    for (int l=0; l<function.output_columns.size(); l++)
                    {
                        String output_column = (String)function.output_columns.elementAt(l);
                        String original_output_column = (String)function.original_output_columns.elementAt(l);
                        if (!output_column.substring(0,1).equals(":"))
                        {
                            int ind = split_columns.indexOf(output_column);
                            if (ind >= 0)
                            {
                                String f_split_value = ":"+(String)split_values.elementAt(ind)+":";
                                function.output_columns.setElementAt(f_split_value,l);
                                instantiated_columns.addElement(original_output_column);
                            }
                        }
                    }

                    for (int l=0; l<function.input_columns.size(); l++)
                    {
                        String input_column = (String)function.input_columns.elementAt(l);
                        String original_input_column = (String)function.original_input_columns.elementAt(l);
                        if (!input_column.substring(0,1).equals(":"))
                        {
                            int this_pos = (new Integer(original_input_column)).intValue();
                            take_off = 0;
                            for (int m=0; m<instantiated_columns.size(); m++)
                            {
                                String instantiated_column = (String)instantiated_columns.elementAt(m);
                                int instantiated_pos = (new Integer(instantiated_column)).intValue();
                                if (instantiated_pos < this_pos)
                                    take_off++;
                            }
                            String final_input_column =
                                    Integer.toString((new Integer(input_column)).intValue() - take_off);
                            function.input_columns.setElementAt(final_input_column,l);
                        }
                    }

                    for (int l=0; l<function.output_columns.size(); l++)
                    {
                        String output_column = (String)function.output_columns.elementAt(l);
                        String original_output_column = (String)function.original_output_columns.elementAt(l);
                        if (!output_column.substring(0,1).equals(":"))
                        {
                            int this_pos = (new Integer(original_output_column)).intValue();
                            take_off = 0;
                            for (int m=0; m<instantiated_columns.size(); m++)
                            {
                                String instantiated_column = (String)instantiated_columns.elementAt(m);
                                int instantiated_pos = (new Integer(instantiated_column)).intValue();
                                if (instantiated_pos < this_pos)
                                    take_off++;
                            }
                            String final_output_column =
                                    Integer.toString((new Integer(output_column)).intValue() - take_off);
                            function.output_columns.setElementAt(final_output_column,l);
                        }
                    }

                    String written_function = function.writeFunction();
                    if (!function_strings.contains(written_function))
                    {
                        split_specification.functions.addElement(function);
                        function_strings.addElement(written_function);
                    }
                    if (!total_function_strings.contains(written_function))
                    {
                        new_functions.addElement(function);
                        total_function_strings.addElement(written_function);
                    }
                }
            }
        }

        // Finally generate the new functions with appropriate instantiation.

        take_off = 0;

        for (int i=0;i<old_concept.functions.size();i++)
        {
            Vector instantiated_columns = new Vector();
            Function old_function = (Function)old_concept.functions.elementAt(i);
            Function function = old_function.copy();
            for (int j=0; j<function.input_columns.size(); j++)
            {
                String input_column = (String)function.input_columns.elementAt(j);
                String original_input_column = (String)function.original_input_columns.elementAt(j);
                if (!input_column.substring(0,1).equals(":"))
                {
                    int ind = split_columns.indexOf(input_column);
                    if (ind >= 0)
                    {
                        String f_split_value = ":"+(String)split_values.elementAt(ind)+":";
                        function.input_columns.setElementAt(f_split_value,j);
                        instantiated_columns.addElement(original_input_column);
                    }
                }
            }
            for (int j=0; j<function.output_columns.size(); j++)
            {
                String output_column = (String)function.output_columns.elementAt(j);
                String original_output_column = (String)function.original_output_columns.elementAt(j);
                if (!output_column.substring(0,1).equals(":"))
                {
                    int ind = split_columns.indexOf(output_column);
                    if (ind >= 0)
                    {
                        String f_split_value = ":"+(String)split_values.elementAt(ind)+":";
                        function.output_columns.setElementAt(f_split_value,j);
                        instantiated_columns.addElement(original_output_column);
                    }
                }
            }
            for (int j=0; j<function.input_columns.size(); j++)
            {
                String input_column = (String)function.input_columns.elementAt(j);
                String original_input_column = (String)function.original_input_columns.elementAt(j);
                if (!input_column.substring(0,1).equals(":"))
                {
                    int this_pos = (new Integer(original_input_column)).intValue();
                    take_off = 0;
                    for (int k=0; k<instantiated_columns.size(); k++)
                    {
                        String instantiated_column = (String)instantiated_columns.elementAt(k);
                        int instantiated_pos = (new Integer(instantiated_column)).intValue();
                        if (instantiated_pos < this_pos)
                            take_off++;
                    }
                    String final_input_column =
                            Integer.toString((new Integer(input_column)).intValue() - take_off);
                    function.input_columns.setElementAt(final_input_column,j);
                }
            }
            for (int j=0; j<function.output_columns.size(); j++)
            {
                String output_column = (String)function.output_columns.elementAt(j);
                String original_output_column = (String)function.original_output_columns.elementAt(j);
                if (!output_column.substring(0,1).equals(":"))
                {
                    int this_pos = (new Integer(original_output_column)).intValue();
                    take_off = 0;
                    for (int k=0; k<instantiated_columns.size(); k++)
                    {
                        String instantiated_column = (String)instantiated_columns.elementAt(k);
                        int instantiated_pos = (new Integer(instantiated_column)).intValue();
                        if (instantiated_pos < this_pos)
                            take_off++;
                    }
                    String final_output_column =
                            Integer.toString((new Integer(output_column)).intValue() - take_off);
                    function.output_columns.setElementAt(final_output_column,j);
                }
            }

            String written_function = function.writeFunction();
            if (!total_function_strings.contains(written_function))
            {
                new_functions.addElement(function);
                total_function_strings.addElement(written_function);
            }
        }
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule extracts rows where the split values are found in
     * the split columns into the new table. Then the split columns are removed from each
     * new row.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        // Special case: if the column to split on is the first (zero), then put
        // empty vectors into all the other columns.

        Vector columns = (Vector)parameters.elementAt(0);
        Vector values = (Vector)parameters.elementAt(1);
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        Datatable output = new Datatable();
        if (columns.size()==1 && columns.elementAt(0).equals("0"))
        {
            String entity_to_keep = (String)values.elementAt(0);
            for (int i=0; i<old_datatable.size(); i++)
            {
                Row row = (Row)old_datatable.elementAt(i);
                Row new_row = new Row(row.entity, new Tuples());
                if (row.entity.equals(entity_to_keep))
                    new_row.tuples.addElement(new Vector());
                output.addElement(new_row);
            }
            return output;
        }

        Vector tuple_columns = new Vector();
        for (int i=0;i<columns.size();i++)
        {
            int x = (new Integer((String)columns.elementAt(i))).intValue() - 1;
            tuple_columns.addElement(Integer.toString(x));
        }
        for (int i=0;i<old_datatable.size();i++)
        {
            Row row = (Row)old_datatable.elementAt(i);
            Vector tuples = row.tuples;
            Tuples new_tuples = new Tuples();
            for (int j=0;j<tuples.size();j++)
            {
                Vector tuple=(Vector)((Vector)tuples.elementAt(j)).clone();
                int k=0;
                while (k<columns.size() &&
                        ((String)tuple.elementAt((new Integer((String)columns.elementAt(k))).
                                intValue()-1)).equals
                                ((String)values.elementAt(k))) k++;
                if (k==columns.size())
                {
                    new_tuples.addElement(super.removeColumns(tuple,tuple_columns));
                }
            }
            Row new_row = new Row(row.entity,new_tuples);
            output.addElement(new_row);
        }
        return output;
    }


    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        // Special case: if the column to split on is the first (zero)

        Concept old_concept = (Concept)old_concepts.elementAt(0);
        Vector columns = (Vector)parameters.elementAt(0);
        Vector values = (Vector)parameters.elementAt(1);
        if (columns.size()==1 && columns.elementAt(0).equals("0"))
            return old_concept.types;

        Vector old_types = (Vector)old_concept.types.clone();
        return(super.removeColumns(old_types, columns));
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern. The pattern for the split
     * rule is that either (i) a column contains the same values for all tuples for
     * all chosen entities or (ii) a column contains the same values for at least one
     * tuples for all chosen entities.
     * The algorithm for computing the score is as follows:
     *
     *  Let max_score = 0
     *  For each possible choice of columns
     *    Build the entire set of tuples from the non-entity-list
     *    Build an initial set of possible tuples using the tuples from the first
     *         entity in the list to learn (remembering to cut them down)
     *         note - don't allow a possible tuple into the set if it is in the non-entity-list
     *    For each entity in the list to learn except the first
     *      remove entity tuple from the possible set if it is not found in the
     *        tuples for the entity
     *      stop if the list becomes empty
     *    Next
     *    For each possible tuple left in the set
     *      Make the score = number of entities in non-entity-list
     *      For each entity in the non-entity list
     *        if the possible tuple is in the tuples for the non-entity
     *           then score=score-1
     *      Next
     *    if score = number of entities in the non-entity-list
     *      then return score immediately
     *    if score > max_score
     *      then let max_score = score
     *    Next
     *  Next
     *  return max_score
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        // Return the top score if there is a pattern for the non_entities //

        if (patternScore2(concept_list,all_concepts,non_entity_list,entity_list)==entity_list.size())
            return non_entity_list.size();
        return patternScore2(concept_list,all_concepts,entity_list,non_entity_list);
    }

    private int patternScore2(Vector concept_list, Vector all_concepts,
                              Vector entity_list, Vector non_entity_list)
    {
        if (entity_list.size()==0)
            return 0;
        Concept c = (Concept)concept_list.elementAt(0);

        if (c.arity==1) return 0;

        int max_score = 0;
        // First get the set of possible column numbers //

        Vector all_column_tuples = super.allColumnTuples(c.arity);

        // Now, loop through the set of column tuples //

        int ct = 0;
        while(ct < all_column_tuples.size() && max_score!=non_entity_list.size())
        {
            Vector parameters = (Vector)all_column_tuples.elementAt(ct);

            // First get the set of non_entity_tuples //

            Vector non_entity_tuples = new Vector();
            for (int nen = 0; nen < non_entity_list.size(); nen++)
            {
                String non_entity = (String)non_entity_list.elementAt(nen);
                Row row = c.calculateRow(all_concepts,non_entity);
                Vector tuples = row.tuples;
                for (int tn=0;tn<tuples.size();tn++)
                {
                    Vector tuple=(Vector)((Vector)tuples.elementAt(tn)).clone();
                    if (passesNonEmpiricalTest(tuple, c.types))
                    {
                        tuple.insertElementAt(non_entity,0);
                        Vector new_tuple = super.keepColumns(tuple,parameters);
                        String new_tuple_string = new_tuple.toString();
                        if (!non_entity_tuples.contains(new_tuple_string))
                            non_entity_tuples.addElement(new_tuple_string);
                    }
                }
            }

            // Find the set of possible entity tuples. These must appear in the tuples for the
            // first entity to learn, so construct them from there.

            String first_entity = (String)entity_list.elementAt(0);
            Row first_row = c.calculateRow(all_concepts,first_entity);
            Vector first_tuples = first_row.tuples;
            Vector entity_tuples = new Vector();
            Vector actual_entity_tuples = new Vector();
            Vector copy_entity_tuples = new Vector();

            for (int tn=0;tn<first_tuples.size();tn++)
            {
                Vector tuple=(Vector)((Vector)first_tuples.elementAt(tn)).clone();
                if (passesNonEmpiricalTest(tuple, c.types))
                {
                    tuple.insertElementAt(first_entity,0);
                    Vector new_tuple = super.keepColumns(tuple,parameters);
                    String new_tuple_string = new_tuple.toString();
                    if (!entity_tuples.contains(new_tuple_string) &&
                            !non_entity_tuples.contains(new_tuple_string))
                    {
                        copy_entity_tuples.addElement(new_tuple_string);
                        entity_tuples.addElement(new_tuple_string);
                        actual_entity_tuples.addElement(new_tuple);
                    }
                }
            }

            // Next get the set of entity-tuples which appear at least once in the tuples
            // for every entity to learn, but do not appear in the non_entity_tuples.

            int en = 1;
            while (en < entity_list.size() && !entity_tuples.isEmpty())
            {
                Vector present_tuples = new Vector();
                String entity = (String)entity_list.elementAt(en);
                Row row = c.calculateRow(all_concepts,entity);
                Vector tuples = row.tuples;
                for (int tn=0;tn<tuples.size();tn++)
                {
                    Vector tuple=(Vector)((Vector)tuples.elementAt(tn)).clone();
                    tuple.insertElementAt(entity,0);
                    Vector new_tuple = super.keepColumns(tuple,parameters);
                    String new_tuple_string = new_tuple.toString();
                    if (!present_tuples.contains(new_tuple_string))
                        present_tuples.addElement(new_tuple_string);
                }
                // Remove any entity tuples from the list of possibles if they do not appear in
                // the present set of tuples.

                int pn=0;
                while (pn<entity_tuples.size())
                {
                    String tuple=(String)entity_tuples.elementAt(pn);
                    if (!present_tuples.contains(tuple))
                    {
                        entity_tuples.removeElementAt(pn);
                        actual_entity_tuples.removeElementAt(pn);
                        copy_entity_tuples.removeElementAt(pn);
                    }
                    else
                        pn++;
                }
                en++;
            }

            if (!entity_tuples.isEmpty())
            {
                max_score = non_entity_list.size();
                String winning_tuple = (String)entity_tuples.elementAt(0);
                int index = copy_entity_tuples.indexOf(winning_tuple);
                Vector actual_winning_tuple = (Vector)actual_entity_tuples.elementAt(index);
                Vector final_parameters = new Vector();
                final_parameters.addElement(parameters);
                final_parameters.addElement(actual_winning_tuple);
                Vector must_do = new Vector();
                must_do.addElement(c.id);
                must_do.addElement(final_parameters);
                special_parameterisations.addElement(must_do);
            }

            ct++;
        }
        return max_score;
    }

    private boolean passesNonEmpiricalTest(Vector tuple, Vector types)
    {
        boolean output = true;
        for (int i=0; i<tuple.size() && output; i++)
        {
            String type = (String)types.elementAt(i+1);
            if (!empirical_allowed_types.contains(type) &&
                    !allowed_values.contains(type+(String)tuple.elementAt(i)))
                output = false;
        }
        return output;
    }
}
