package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class representing the exists production rule. This production
 * rule takes an old datatable as input and a set of parameterizations.
 * The parameterization is a list of columns which are to be #removed#
 * in the output datatable.
 *
 * @author Simon Colton, started 3rd January 2000
 * @version 1.0 */

public class Exists extends ProductionRule implements Serializable
{
    /** Whether or not to merge previous existence specifications when producing
     * the specifications for a new concept.
     */

    public boolean merge_previous_exists = true;

    /** Returns false as this is a unary production rule.
     */

    public boolean isBinary()
    {
        return false;
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean is_cumulative = false;

    /** Returns "exists" as that is the name of this production rule.
     */

    public String getName()
    {
        return "exists";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all tuples of columns which do not contain the first column.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        Vector params = super.allColumnTuples(old_concept.arity);
        Vector output = new Vector();
        for (int i=0; i<params.size(); i++)
        {
            Vector p = (Vector)params.elementAt(i);

            if (old_concept.arity - p.size() <= arity_limit)
                output.addElement(p);
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the exists production
     * rule. To do this, it first finds which old specifications do not involve any of the
     * quantified variables, and these specifications are added. Note that it may be
     * necessary to alter the permutations for these. Then, a single new specification is
     * added which is the quantification of the variables over the set of old specification
     * in which the quantified variables are involved.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector old_specifications = new Vector();
        old_specifications = old_concept.specifications;
        Vector old_types = old_concept.types;
        Vector all_positions_used = new Vector();
        Specification quantified_specification = new Specification();
        quantified_specification.type = "exists";
        quantified_specification.multiple_variable_columns = (Vector)input_parameters.clone();

        // First make the quantified types for the quantified specification.

        for (int i=0;i<input_parameters.size();i++)
        {
            quantified_specification.multiple_types.addElement(old_types.
                    elementAt((new Integer((String)input_parameters.elementAt(i))).intValue()));
        }

        // Next run through all the old specifications, and add them to the new specifications
        // if they are not involved in the quantification, or add them to the quantified specification
        // otherwise.

        for (int i=0;i<old_specifications.size();i++)
        {
            Specification old_specification = (Specification)old_specifications.elementAt(i);

            // Work out whether the old specification involves any variables which are to be
            // quantified.

            boolean involves_removed_columns = old_specification.involvesColumns(input_parameters);

            // Second case - old specification does not involve a variable which is to be quantified.
            // Must change the permutation to reflect the loss of columns.

            if (!involves_removed_columns)
            {
                Specification new_specification = old_specification.copy();
                new_specification.permutation = new Vector();
                for (int j=0;j<old_specification.permutation.size();j++)
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

            // Third case - old specification gets added to previous specifications of the
            // quantified specification.

            if (involves_removed_columns)
            {
                quantified_specification.previous_specifications.addElement(old_specification);
                for (int j=0;j<old_specification.permutation.size();j++)
                {
                    String pos = (String)old_specification.permutation.elementAt(j);
                    if (!all_positions_used.contains(pos))
                        all_positions_used.addElement(pos);
                }
            }
        }
        int add_to_perm_pos = 0;

        // Now we specify which columns are used (put in the permutation) and
        // which columns are redundant. All these columns will be added in so that the
        // previous specifications can be used later.

        for (int i=0;i<old_types.size();i++)
        {
            String try_pos = Integer.toString(i);
            if (all_positions_used.contains(try_pos) && !input_parameters.contains(try_pos))
            {
                quantified_specification.permutation.addElement(Integer.toString(add_to_perm_pos));
                add_to_perm_pos++;
            }
            if (!input_parameters.contains(try_pos) && !all_positions_used.contains(try_pos))
            {
                quantified_specification.redundant_columns.addElement(Integer.toString(add_to_perm_pos));
                add_to_perm_pos++;
            }
        }

        // We now collect together the existence specifications which are old specifications
        // of the new existence specification, so that only one existence is required.

        if (merge_previous_exists)
        {
            int exists_pos = old_concept.arity - input_parameters.size() +
                    quantified_specification.multiple_variable_columns.size();
            Hashtable column_hashtable = new Hashtable();

            for (int i=0; i<quantified_specification.previous_specifications.size(); i++)
            {
                Specification prev_exists_spec =
                        (Specification)quantified_specification.previous_specifications.elementAt(i);
                if (prev_exists_spec.type.equals("exists"))
                {
                    for (int j=0; j<prev_exists_spec.permutation.size(); j++)
                    {
                        int old_pos_int = j;
                        String old_pos = Integer.toString(j);
                        for (int k=0; k<prev_exists_spec.multiple_variable_columns.size(); k++)
                        {
                            String mvc_pos = (String)prev_exists_spec.multiple_variable_columns.elementAt(k);
                            int mvc_pos_int = (new Integer(mvc_pos)).intValue();
                            if (mvc_pos_int<=old_pos_int)
                                old_pos_int++;
                        }
                        old_pos = Integer.toString(old_pos_int);
                        String new_pos = (String)prev_exists_spec.permutation.elementAt(j);
                        column_hashtable.put(old_pos, new_pos);
                    }

                    // Add the existence variables from the previous existence specification
                    // to those of the top level (new) existence specification. Remember which
                    // variables become which new ones.

                    for (int j=0; j<prev_exists_spec.multiple_variable_columns.size(); j++)
                    {
                        String old_pos = (String)prev_exists_spec.multiple_variable_columns.elementAt(j);
                        String new_pos = Integer.toString(exists_pos);
                        column_hashtable.put(old_pos, new_pos);
                        quantified_specification.multiple_variable_columns.addElement(new_pos);
                        quantified_specification.multiple_types.addElement(prev_exists_spec.multiple_types.elementAt(j));
                        exists_pos++;
                    }

                    // Add the specifications from the previous existence specification to
                    // the top level (new) existence specification. Change their permutations
                    // according to the hashtable. Change their functions according to the hashtable.

                    for (int j=0; j<prev_exists_spec.previous_specifications.size(); j++)
                    {
                        Specification prev_exists_prev_spec =
                                (Specification)prev_exists_spec.previous_specifications.elementAt(j);
                        Vector new_prev_perm = new Vector();
                        for (int k=0; k<prev_exists_prev_spec.permutation.size(); k++)
                        {
                            String old_pos = (String)prev_exists_prev_spec.permutation.elementAt(k);
                            String new_pos = (String)column_hashtable.get(old_pos);
                            new_prev_perm.addElement(new_pos);
                        }

                        Specification new_prev_exists_prev_spec = prev_exists_prev_spec.copy();
                        new_prev_exists_prev_spec.permutation = new_prev_perm;
                        quantified_specification.previous_specifications.addElement(new_prev_exists_prev_spec);
                    }

                    quantified_specification.previous_specifications.removeElementAt(i);
                    i--;
                }
            }
        }

        output.addElement(quantified_specification);

        // Finally, add any functions which do not involve any columns which have been
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
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule removes columns from the input datatable, then
     * removes any duplicated rows. The parameters details which columns are to be #removed#.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        return super.removeColumns(old_datatable, parameters);
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        return(super.removeColumns(old_types,parameters));
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern. The pattern for the exists
     * production rule is that each of the entities has a non-empty row of tuples. The
     * fewer the non-entities that have a non-empty row, the better. Alternatively, if
     * none of the entities to learn has a non-empty row of tuples, this is also a
     * pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        Concept c = (Concept)concept_list.elementAt(0);
        int i=0;
        boolean empty_pattern = false;
        boolean full_pattern = false;
        String entity = (String)entity_list.elementAt(0);
        Row row = c.calculateRow(all_concepts,entity);
        if (row.tuples.size()==0) empty_pattern = true;
        else full_pattern = true;
        while ((full_pattern || empty_pattern) && i<entity_list.size())
        {
            entity = (String)entity_list.elementAt(i);
            row = c.calculateRow(all_concepts,entity);
            if (row.tuples.size()==0)
                full_pattern = false;
            else
                empty_pattern = false;
            i++;
        }
        if (!full_pattern && !empty_pattern) return 0;
        int score = non_entity_list.size();
        for (i=0;i<non_entity_list.size();i++)
        {
            entity = (String)non_entity_list.elementAt(i);
            row = c.calculateRow(all_concepts,entity);
            if (row.tuples.size()>0 && full_pattern)
                score--;
            if (row.tuples.size()==0 && empty_pattern)
                score--;
        }
        return score;
    }
}
