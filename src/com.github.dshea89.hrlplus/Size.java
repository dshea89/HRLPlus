package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;

/** A class representing the size production rule. This production
 * rule takes an old datatable as input and a set of parameterizations.
 * The parameterization is a list of columns which are to be counted over.
 * In effect, this production rule counts the size of a set of subobject,
 * or pairs of subobjects etc. which are associated with an object of interest
 * or object of interest and subobject etc.
 *
 * @author Simon Colton, started 3rd January 1999
 * @version 1.0 */

public class Size extends ProductionRule implements Serializable
{
    /** The integer relation to add to the specifications.
     */

    public Relation integer_relation = null;

    /** How many new functions have been invented.
     */

    public int number_of_new_functions = 0;

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

    /** Returns "size" as that is the name of this production rule.
     */

    public String getName()
    {
        return "size";
    }

    /** The numbers to go up to (when inventing a datatable).
     */

    public int top_number = 10;

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all tuples of columns which do not contain the first column.
     * However, if removing a column means that a variable is left with no specifications
     * about it, this cannot happen. For instance, given the concept [n,a,b] : a|n & b in dig(a),
     * we cannot remove column 1 (letter a) as this leaves variable b completely unrelated to
     * n (so we cannot find all such b to be input to the function). Also, it is not a good idea
     * to count the size of the output set of a function (which will always be 1), as this leads
     * to very dull concepts.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        Vector params = super.allColumnTuples(old_concept.arity);
        Vector output = new Vector();
        for (int i=0; i<params.size(); i++)
        {
            Vector p = (Vector)params.elementAt(i);

            // THIS IS A FORBIDDEN PATH //

            boolean discard = false;
            int j=0;
            while (!discard && j<old_concept.functions.size())
            {
                Function function = (Function)old_concept.functions.elementAt(j);
                if (function.output_columns.toString().equals(p.toString()))
                    discard = true;
                j++;
            }

            if (old_concept.arity - p.size() < arity_limit && !discard)
                output.addElement(p);
        }
        return output;
    }

    /** This produces the new specifications for concepts output using
     * the size production rule. To do this, it first finds which old
     * specifications do not involve any of the columns which are being
     * summed over, and these specifications are added. Note that it may
     * be necessary to alter the permutations for these. Then, a single
     * new specification is added which is the multiple specification
     * over which the set size is taken. ie.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector old_specifications = old_concept.specifications;
        Vector old_types = ((Concept)input_concepts.elementAt(0)).types;
        Vector all_positions_used = new Vector();
        Specification multiple_specification = new Specification();
        multiple_specification.type = "size";
        multiple_specification.multiple_variable_columns = input_parameters;

        // First make the types for the multiple specification.

        for (int i=0;i<input_parameters.size();i++)
        {
            multiple_specification.multiple_types.addElement(old_types.
                    elementAt((new Integer((String)input_parameters.elementAt(i))).intValue()));
        }

        // Next run through all the old specifications, and add them to the new specifications
        // if they are not involved in the summation, or add them to the multiple specification
        // otherwise.

        for (int i=0;i<old_specifications.size();i++)
        {
            Specification old_specification = (Specification)old_specifications.elementAt(i);
            int j=0;

            // Work out whether the old specification involves any variables which are to be
            // summed over.

            boolean involves_removed_columns = old_specification.involvesColumns(input_parameters);

            // First case - old specification does not involve a variable which is involved in the sum
            // Must change the parameterisation to reflect the loss of columns.

            if (!involves_removed_columns)
            {
                Specification new_specification = old_specification.copy();
                new_specification.permutation = new Vector();
                for (j=0;j<old_specification.permutation.size();j++)
                {
                    int number_of_columns_to_remove = 0;
                    int add_column =
                            (new Integer((String)old_specification.permutation.elementAt(j))).intValue();
                    for (int k=0;k<input_parameters.size();k++)
                    {
                        int param =
                                (new Integer((String)input_parameters.elementAt(k))).intValue();
                        if (param<add_column) number_of_columns_to_remove++;
                    }
                    new_specification.permutation.
                            addElement(Integer.toString(add_column-number_of_columns_to_remove));
                }
                output.addElement(new_specification);
            }

            // Second case - old specification gets added to previous
            // specifications of the multiple specification, because they
            // involve some of the removed columns.

            if (involves_removed_columns)
            {
                multiple_specification.previous_specifications.addElement(old_specification);
                Vector old_spec_perm = old_specification.permutation;
                for (j=0;j<old_spec_perm.size();j++)
                {
                    String pos = (String)old_spec_perm.elementAt(j);
                    if (!all_positions_used.contains(pos)) all_positions_used.addElement(pos);
                }
            }
        }
        int add_to_perm_pos = 0;

        // Finally, must specify which columns are used (put in the permutation) and
        // which columns are redundant. All these columns will be added in so that the
        // previous specifications can be used later.

        for (int i=0;i<old_types.size();i++)
        {
            String try_pos = Integer.toString(i);
            if (all_positions_used.contains(try_pos) && !input_parameters.contains(try_pos))
            {
                multiple_specification.permutation.addElement(Integer.toString(add_to_perm_pos));
                add_to_perm_pos++;
            }
            if (!input_parameters.contains(try_pos) && !all_positions_used.contains(try_pos))
            {
                multiple_specification.redundant_columns.addElement(Integer.toString(add_to_perm_pos));
                add_to_perm_pos++;
            }
        }

        // Don't forget to add in the last column to the permutation. This is the new column
        // which performs the calculation.

        multiple_specification.permutation.addElement(Integer.toString(add_to_perm_pos));

        // Add in the specification that this is an integer //

        if (integer_relation==null)
        {
            integer_relation = new Relation();
            integer_relation.addDefinition("a","@a@ is an integer");
        }
        Specification integer_specification =
                new Specification("["+multiple_specification.permutation.lastElement()+"]", integer_relation);
        output.addElement(integer_specification);
        output.addElement(multiple_specification);

        // Finally, add any functions in which do not involve any columns which have been
        // removed. Adjust the columns accordingly.

        for (int i=0;i<old_concept.functions.size();i++)
        {
            Function function = (Function)old_concept.functions.elementAt(i);
            Function new_function = function.copy();
            if (!new_function.containsAColumnFrom(input_parameters))
            {
                new_function.removeHoles(input_parameters);
                new_functions.addElement(new_function);
            }
        }

        // And add a new function, where the last column of the new concept is the output.

        Vector types = transformTypes(input_concepts, input_parameters);
        Vector new_input_columns = new Vector();
        Vector new_output_columns = new Vector();
        for (int i=0;i<types.size()-1;i++)
            new_input_columns.addElement(Integer.toString(i));
        new_output_columns.addElement(Integer.toString(types.size()-1));
        Function size_function = new Function("f"+Integer.toString(number_of_new_functions++),
                new_input_columns, new_output_columns);
        new_functions.addElement(size_function);
        multiple_specification.functions.addElement(size_function);
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. The parameters will detail a set of columns which are to be removed,
     * with the remaining rows being counted to see how many times they appear. Then
     * one copy of each repeated row is kept along with the number of times it appeared
     * tagged on as another column. This is how the production rule used to work. However,
     * this produced a function which only took as input a tuple which was guaranteed to
     * have a non-zero output. To make this a complete function is a tricky process.
     * Firstly, the specifications which do #not# involve any of the columns which are to
     * be removed are found. Then, the old concept is found which has exactly these
     * specifications. Then, for each row in the old concept's datatable, the number of
     * times it appears in the given datatable is calculated and tagged on as the final column.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        Datatable output = new Datatable();
        Vector old_specifications = ((Concept)input_concepts.elementAt(0)).specifications;
        Vector input_specifications = new Vector();
        Vector input_specifications_before_permutation_change = new Vector();

        // First work out which specifications are only valid of the input to the function.

        Enumeration e = old_specifications.elements();
        while (e.hasMoreElements())
        {
            Specification old_specification = (Specification)e.nextElement();
            if (!old_specification.involvesColumns(parameters))
                input_specifications_before_permutation_change.addElement(old_specification);
        }

        // Next change the permutations of the input specifications to reflect the removal
        // of the columns.

        e = input_specifications_before_permutation_change.elements();
        while (e.hasMoreElements())
        {
            Specification old_specification = (Specification)e.nextElement();
            Specification new_specification = old_specification.copy();
            new_specification.permutation = new Vector();
            for (int j=0;j<old_specification.permutation.size();j++)
            {
                int number_of_columns_to_remove = 0;
                int add_column =
                        (new Integer((String)old_specification.permutation.elementAt(j))).intValue();
                for (int k=0;k<parameters.size();k++)
                {
                    int param =
                            (new Integer((String)parameters.elementAt(k))).intValue();
                    if (param<add_column) number_of_columns_to_remove++;
                }
                new_specification.permutation.
                        addElement(Integer.toString(add_column-number_of_columns_to_remove));
            }
            input_specifications.addElement(new_specification);
        }

        // Next find the concept with these specifications.

        Vector new_types = transformTypes(input_concepts, parameters);
        new_types.removeElementAt(new_types.size()-1);
        String n_types = new_types.toString();
        e = all_concepts.elements();
        boolean found = false;
        Concept input_concept = new Concept();
        while (e.hasMoreElements() && !found)
        {
            input_concept = (Concept)e.nextElement();
            if (n_types.equals(input_concept.types.toString()) &&
                    input_concept.specifications.size()==input_specifications.size())
            {
                int i=0;
                while (i<input_specifications.size())
                {
                    Specification spec = (Specification)input_specifications.elementAt(i);
                    int j=0;
                    while (j<input_concept.specifications.size())
                    {
                        Specification check_spec =
                                (Specification)input_concept.specifications.elementAt(j);
                        if (check_spec.equals(spec)) break;
                        j++;
                    }
                    if (j==input_concept.specifications.size()) break;
                    i++;
                }
                if (i==input_specifications.size()) found=true;
            }
        }

        boolean using_made_integer_table = false;
        Datatable int_table = new Datatable();
        if (!found)
        {
            if (new_types.size()==2 && ((String)new_types.elementAt(1)).equals("integer"))
            {
                for (int i=0;i<old_datatable.size();i++)
                {
                    Row row = (Row)old_datatable.elementAt(i);
                    Row new_row = new Row();
                    Tuples combined_tuples = new Tuples();
                    for (int k=0;k<=top_number;k++)
                    {
                        Vector tuple = new Vector();
                        tuple.addElement(Integer.toString(k));
                        combined_tuples.addElement(tuple);
                    }
                    int_table.addElement(new Row(row.entity,combined_tuples));
                }
                using_made_integer_table = true;
            }
            else
                return new Datatable();
        }

        // Finally, put together the new datatable. Do this by taking each row of the input_concept's
        // datatable and seeing how many times it appears in the given table.

        // First work out how the columns are to match up.

        Vector matching_positions = new Vector();
        for (int i=1;i<old_concept.arity;i++)
        {
            String pos = Integer.toString(i);
            if (!parameters.contains(pos)) matching_positions.addElement(Integer.toString(i-1));
        }

        // Now run through the input concept's table.

        Enumeration run_through = old_datatable.elements();
        while (run_through.hasMoreElements())
        {
            Row corresponding_row = (Row)run_through.nextElement();
            Row input_row = new Row();
            if (using_made_integer_table)
                input_row = int_table.rowWithEntity(corresponding_row.entity);
            else
                input_row = input_concept.calculateRow(all_concepts,corresponding_row.entity);
            Row new_row = new Row();
            new_row.entity = input_row.entity;
            for (int i=0; i<input_row.tuples.size(); i++)
            {
                Vector input_tuple = (Vector)input_row.tuples.elementAt(i);
                int count = 0;
                for (int j=0; j<corresponding_row.tuples.size(); j++)
                {
                    Vector corresponding_tuple = (Vector)corresponding_row.tuples.elementAt(j);
                    int k=0;
                    boolean is_match = true;
                    while (k<matching_positions.size() && is_match)
                    {
                        String input_element = (String)input_tuple.elementAt(k);
                        int position =
                                (new Integer((String)matching_positions.elementAt(k))).intValue();
                        try {
                            String match_element = (String)corresponding_tuple.elementAt(position);
                            if (!input_element.equals(match_element)) is_match = false;
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            is_match = false;
                        }
                        k++;
                    }
                    if (is_match) count++;
                }
                Vector new_tuple = (Vector)input_tuple.clone();
                new_tuple.addElement(Integer.toString(count));
                new_row.tuples.addElement(new_tuple);
            }
            output.addElement(new_row);
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        Vector new_types = super.removeColumns(old_types,parameters);
        new_types.addElement("integer");
        return new_types;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern. The pattern for the size
     * rule is that each set of tuples is the same size.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        Concept c = (Concept)concept_list.elementAt(0);
        int entity_score = 0;
        int non_entity_score = non_entity_list.size();
        Vector slots = new Vector();
        Integer chosen_size = new Integer(0);
        for (int i=0; i<entity_list.size(); i++)
        {
            String entity = (String)entity_list.elementAt(i);
            Row row = c.calculateRow(all_concepts,entity);
            Integer tuple_size = new Integer(row.tuples.size());
            boolean found_slot = false;
            int j=0;
            while (!found_slot && j<slots.size())
            {
                Vector slot = (Vector)slots.elementAt(j);
                if (slot.contains(tuple_size))
                {
                    slot.addElement(tuple_size);
                    if (slot.size() > entity_score)
                    {
                        entity_score = slot.size();
                        chosen_size = tuple_size;
                    }
                }
                j++;
            }
            if (!found_slot)
            {
                Vector new_slot = new Vector();
                new_slot.addElement(tuple_size);
                slots.addElement(new_slot);
                if (1>entity_score);
                entity_score = 1;
                chosen_size = tuple_size;
            }
            i++;
        }
        for (int i=0;i<non_entity_list.size();i++)
        {
            String entity = (String)non_entity_list.elementAt(i);
            Row row = c.calculateRow(all_concepts,entity);
            if (row.tuples.size()==chosen_size.intValue())
                non_entity_score--;
        }
        Float f = new Float((entity_score + non_entity_score)/2);
        int score = f.intValue();
        return score;
    }
}
