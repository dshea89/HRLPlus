package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the forall production rule. This production
 * rule takes an old datatable as input and a set of parameterizations.
 * The parameterization is a list of columns which are to be #removed#
 * in the output datatable.
 *
 * @author Simon Colton, started 6th October 2000
 * @version 1.0 */

public class Forall extends ProductionRule implements Serializable
{
    /** A compose rule which will be used for this production rule.
     */

    public Compose compose = new Compose();

    /** An exists rule which will be used for this production rule.
     */

    public Exists exists = new Exists();

    /** Returns false as this is a unary production rule.
     */

    public boolean isBinary()
    {
        return true;
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean is_cumulative = false;

    /** Returns "forall" as that is the name of this production rule.
     */

    public String getName()
    {
        return "forall";
    }

    /** Given a vector of two concepts, this will return all the parameterisations for this
     * concept.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Vector output = new Vector();
        Concept lh_concept = (Concept)old_concepts.elementAt(0);
        Concept rh_concept = (Concept)old_concepts.elementAt(1);

        // Find the base concepts //

        Vector base_concepts = new Vector();
        for (int i=0; i<lh_concept.generalisations.size(); i++)
        {
            Concept base_concept = (Concept)lh_concept.generalisations.elementAt(i);
            base_concepts.addElement(base_concept);
        }
        for (int i=0; i<rh_concept.generalisations.size(); i++)
        {
            Concept base_concept = (Concept)rh_concept.generalisations.elementAt(i);
            if (!base_concepts.contains(base_concept))
                base_concepts.addElement(base_concept);
        }
        if (!base_concepts.contains(lh_concept))
            base_concepts.addElement(lh_concept);
        if (!base_concepts.contains(rh_concept))
            base_concepts.addElement(rh_concept);

        for (int i=0; i<base_concepts.size(); i++)
        {
            Concept base_concept = (Concept)base_concepts.elementAt(i);
            if (base_concept.arity<=arity_limit)
            {
                Vector lh_concepts = new Vector();
                lh_concepts.addElement(lh_concept);
                lh_concepts.addElement(base_concept);
                Vector rh_concepts = new Vector();
                rh_concepts.addElement(rh_concept);
                rh_concepts.addElement(base_concept);
                boolean old_cso = compose.subobject_overlap;
                compose.subobject_overlap = false;
                Vector lh_params = compose.allParameters(lh_concepts, theory);
                Vector rh_params = compose.allParameters(rh_concepts, theory);
                compose.subobject_overlap = old_cso;
                for (int j=0; j<lh_params.size(); j++)
                {
                    Vector lh_param = (Vector)lh_params.elementAt(j);
                    Vector lh_types = compose.transformTypes(lh_concepts, lh_param);
                    for (int k=0; k<rh_params.size(); k++)
                    {
                        Vector rh_param = (Vector)rh_params.elementAt(k);
                        Vector rh_types = compose.transformTypes(rh_concepts, rh_param);
                        if (lh_types.toString().equals(rh_types.toString()) &&
                                base_concept.arity < lh_param.size() &&
                                ((String)lh_param.elementAt(0)).equals("1") &&
                                ((String)rh_param.elementAt(0)).equals("1"))
                        {
                            Vector new_param1 = new Vector();
                            new_param1.addElement(base_concept);
                            new_param1.addElement(lh_param);
                            new_param1.addElement(rh_param);
                            output.addElement(new_param1);
                            Vector new_param2 = new Vector();
                            new_param2.addElement(base_concept);
                            new_param2.addElement(lh_param);
                            new_param2.addElement(rh_param);
                            new_param2.addElement("s");
                            output.addElement(new_param2);
                        }
                    }
                }
            }
        }
        Vector pruned_output = new Vector();
        Vector good_specs = new Vector();
        for (int i=0; i<output.size(); i++)
        {
            Vector param = (Vector)output.elementAt(i);
            addParametersIfOK(old_concepts, theory, param, pruned_output, good_specs);
        }
        return pruned_output;
    }

    private void addParametersIfOK(Vector concepts, Theory theory, Vector param, Vector pruned_output, Vector good_specs)
    {
        Vector specs = newSpecifications(concepts, param, theory, new Vector());
        Specification forall_spec = (Specification)specs.lastElement();
        for (int i=forall_spec.rh_starts; i<forall_spec.previous_specifications.size(); i++)
        {
            Specification rh_spec = (Specification)forall_spec.previous_specifications.elementAt(i);
            boolean in_lhs = false;
            for (int j=0; j<forall_spec.rh_starts && !in_lhs; j++)
            {
                Specification lh_spec = (Specification)forall_spec.previous_specifications.elementAt(j);
                if (lh_spec.equals(rh_spec))
                    in_lhs = true;
            }
            if (!in_lhs && !repeatedSpecifications(good_specs, specs))
            {
                good_specs.addElement(specs);
                pruned_output.addElement(param);
                break;
            }
        }
    }

    private boolean repeatedSpecifications(Vector good_specs, Vector specs)
    {
        boolean output = false;
        for (int i=0; i<good_specs.size() && !output; i++)
        {
            Vector already_specs = (Vector)good_specs.elementAt(i);
            if (already_specs.size()==specs.size())
            {
                boolean matching_specs = true;
                for (int j=0; j<already_specs.size() && matching_specs; j++)
                {
                    Specification already_spec = (Specification)already_specs.elementAt(j);
                    boolean spec_there = false;
                    for (int k=0; k<specs.size(); k++)
                    {
                        Specification spec = (Specification)specs.elementAt(k);
                        if (spec.equals(already_spec))
                            spec_there=true;
                    }
                    if (!spec_there)
                        matching_specs = false;
                }
                if (matching_specs)
                    output = true;
            }
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the forall production
     * rule.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Concept base_concept = (Concept)input_parameters.elementAt(0);
        Concept lh_concept = (Concept)input_concepts.elementAt(0);
        Concept rh_concept = (Concept)input_concepts.elementAt(1);
        Vector lh_params = (Vector)input_parameters.elementAt(1);
        Vector rh_params = (Vector)input_parameters.elementAt(2);

        if (input_parameters.size()==4)
        {
            lh_concept = (Concept)input_concepts.elementAt(1);
            rh_concept = (Concept)input_concepts.elementAt(0);
            lh_params = (Vector)input_parameters.elementAt(2);
            rh_params = (Vector)input_parameters.elementAt(1);
        }
        Vector lh_concepts = new Vector();
        Vector rh_concepts = new Vector();
        lh_concepts.addElement(lh_concept);
        lh_concepts.addElement(base_concept);
        rh_concepts.addElement(rh_concept);
        rh_concepts.addElement(base_concept);
        Vector lh_specifications = compose.newSpecifications(lh_concepts, lh_params, theory, new Vector());
        Vector rh_specifications = compose.newSpecifications(rh_concepts, rh_params, theory, new Vector());
        Vector all_positions = new Vector();
        Specification forall_specification = new Specification();
        forall_specification.type = "forall";
        Vector all_positions_used = new Vector();

        Vector lh_types = compose.transformTypes(lh_concepts, lh_params);
        Vector forall_columns = new Vector();
        for (int i=0; i<lh_params.size(); i++)
        {
            String col = (String)lh_params.elementAt(i);
            if (col.equals("0"))
            {
                forall_columns.addElement(Integer.toString(i));
                forall_specification.multiple_variable_columns.addElement(Integer.toString(i));
                forall_specification.multiple_types.addElement(lh_types.elementAt(i));
            }
        }

        for (int i=0; i<lh_specifications.size(); i++)
        {
            Specification lh_specification = (Specification)lh_specifications.elementAt(i);
            if (!lh_specification.involvesColumns(forall_columns))
            {
                Specification new_specification = lh_specification.copy();
                new_specification.permutation = new Vector();
                for (int j=0;j<lh_specification.permutation.size();j++)
                {
                    int number_of_columns_to_remove = 0;
                    int add_column =
                            (new Integer((String)lh_specification.permutation.elementAt(j))).intValue();
                    for (int k=0;k<forall_columns.size();k++)
                    {
                        int param = (new Integer((String)forall_columns.elementAt(k))).intValue();
                        if (param<add_column)
                            number_of_columns_to_remove++;
                    }
                    new_specification.permutation.
                            addElement(Integer.toString(add_column-number_of_columns_to_remove));
                }
                output.addElement(new_specification);
            }
            else
            {
                forall_specification.previous_specifications.addElement(lh_specification);
                for (int j=0;j<lh_specification.permutation.size();j++)
                {
                    String pos = (String)lh_specification.permutation.elementAt(j);
                    if (!all_positions_used.contains(pos))
                        all_positions_used.addElement(pos);
                }
                forall_specification.rh_starts++;
            }
        }

        for (int i=0; i<rh_specifications.size(); i++)
        {
            Specification rh_specification = (Specification)rh_specifications.elementAt(i);
            if (!rh_specification.involvesColumns(forall_columns))
            {
                Specification new_specification = rh_specification.copy();
                new_specification.permutation = new Vector();
                for (int j=0;j<rh_specification.permutation.size();j++)
                {
                    int number_of_columns_to_remove = 0;
                    int add_column =
                            (new Integer((String)rh_specification.permutation.elementAt(j))).intValue();
                    for (int k=0;k<forall_columns.size();k++)
                    {
                        int param = (new Integer((String)forall_columns.elementAt(k))).intValue();
                        if (param<add_column)
                            number_of_columns_to_remove++;
                    }
                    new_specification.permutation.
                            addElement(Integer.toString(add_column-number_of_columns_to_remove));
                }
                boolean already_there = false;
                for (int j=0; j<output.size() && !already_there; j++)
                {
                    Specification already_spec = (Specification)output.elementAt(j);
                    if (already_spec.equals(new_specification))
                        already_there = true;
                }
                if (!already_there)
                    output.addElement(new_specification);
            }
            else
            {
                forall_specification.previous_specifications.addElement(rh_specification);
                for (int j=0;j<rh_specification.permutation.size();j++)
                {
                    String pos = (String)rh_specification.permutation.elementAt(j);
                    if (!all_positions_used.contains(pos))
                        all_positions_used.addElement(pos);
                }
            }
        }

        int add_to_perm_pos = 0;
        for (int i=0;i<lh_types.size();i++)
        {
            String try_pos = Integer.toString(i);
            if (all_positions_used.contains(try_pos) && !forall_columns.contains(try_pos))
            {
                forall_specification.permutation.addElement(Integer.toString(add_to_perm_pos));
                add_to_perm_pos++;
            }
            if (!forall_columns.contains(try_pos) && !all_positions_used.contains(try_pos))
            {
                forall_specification.redundant_columns.addElement(Integer.toString(add_to_perm_pos));
                add_to_perm_pos++;
            }
        }
        output.addElement(forall_specification);
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector input_parameters, Vector all_concepts)
    {
        Datatable output = new Datatable();
        Concept base_concept = (Concept)input_parameters.elementAt(0);

        Concept lh_concept = (Concept)input_concepts.elementAt(0);
        Concept rh_concept = (Concept)input_concepts.elementAt(1);
        Vector lh_params = (Vector)input_parameters.elementAt(1);
        Vector rh_params = (Vector)input_parameters.elementAt(2);
        Datatable lh_datatable = (Datatable)input_datatables.elementAt(0);
        Datatable rh_datatable = (Datatable)input_datatables.elementAt(1);

        if (input_parameters.size()==4)
        {
            lh_concept = (Concept)input_concepts.elementAt(1);
            rh_concept = (Concept)input_concepts.elementAt(0);
            lh_params = (Vector)input_parameters.elementAt(2);
            rh_params = (Vector)input_parameters.elementAt(1);
            lh_datatable = (Datatable)input_datatables.elementAt(1);
            rh_datatable = (Datatable)input_datatables.elementAt(0);
        }

        Vector lh_concepts = new Vector();
        Vector rh_concepts = new Vector();
        lh_concepts.addElement(lh_concept);
        lh_concepts.addElement(base_concept);
        rh_concepts.addElement(rh_concept);
        rh_concepts.addElement(base_concept);

        Vector lh_datatables = new Vector();
        lh_datatables.addElement(lh_datatable);
        lh_datatables.addElement(base_concept.datatable);
        Vector rh_datatables = new Vector();
        rh_datatables.addElement(rh_datatable);
        rh_datatables.addElement(base_concept.datatable);

        Datatable lh_composed_datatable = compose.transformTable(lh_datatables, lh_concepts, lh_params, all_concepts);
        Datatable rh_composed_datatable = compose.transformTable(rh_datatables, rh_concepts, rh_params, all_concepts);

        Vector forall_columns = new Vector();
        for (int i=base_concept.arity; i<lh_params.size(); i++)
            forall_columns.addElement(Integer.toString(i));

        for (int i=0; i<lh_datatable.size(); i++)
        {
            Row lh_row = (Row)lh_datatable.elementAt(i);
            Row base_row = base_concept.calculateRow(all_concepts, lh_row.entity);
            Row new_row = new Row();
            new_row.entity = base_row.entity;
            for (int j=0; j<base_row.tuples.size(); j++)
            {
                Vector base_tuple = (Vector)base_row.tuples.elementAt(j);
                Vector all_lh_tuples = lh_composed_datatable.rowWithEntity(base_row.entity).tuples;
                Vector all_rh_tuples = rh_composed_datatable.rowWithEntity(base_row.entity).tuples;
                Vector tuples_extracted_from_lhs = getTuples(all_lh_tuples, lh_params, base_tuple);
                Vector tuples_extracted_from_rhs = getTuples(all_rh_tuples, rh_params, base_tuple);
                if (subsumptionOccurs(tuples_extracted_from_lhs, tuples_extracted_from_rhs))
                    new_row.tuples.addElement((Vector)base_tuple.clone());
            }
            output.addElement(new_row);
        }
        return output;
    }

    private boolean subsumptionOccurs(Vector lh_tuples, Vector rh_tuples)
    {
        Vector rh_strings = new Vector();
        for (int i=0; i<rh_tuples.size(); i++)
            rh_strings.addElement(((Vector)rh_tuples.elementAt(i)).toString());
        for (int i=0; i<lh_tuples.size(); i++)
        {
            Vector lh_tuple = (Vector)lh_tuples.elementAt(i);
            if (!rh_strings.contains(lh_tuple.toString()))
                return false;
        }
        return true;
    }

    private Vector getTuples(Vector all_tuples, Vector params, Vector base_tuple)
    {
        Vector output = new Vector();
        for (int i=0; i<all_tuples.size(); i++)
        {
            Vector tuple = (Vector)all_tuples.elementAt(i);
            Vector extracted_tuple = new Vector();
            boolean keep_tuple = true;
            for (int j=0; j<tuple.size() && keep_tuple; j++)
            {
                String tuple_element = (String)tuple.elementAt(j);
                String pos = (String)params.elementAt(j+1);
                if (!pos.equals("0"))
                {
                    int pos_int = (new Integer(pos)).intValue() - 2;
                    String base_element = (String)base_tuple.elementAt(pos_int);
                    if (!tuple_element.equals(base_element))
                        keep_tuple = false;
                }
                else
                    extracted_tuple.addElement(tuple_element);
            }
            if (keep_tuple)
                output.addElement(extracted_tuple);
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Concept base_concept = (Concept)parameters.elementAt(0);
        return (Vector)base_concept.types.clone();
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern.
     */
    // TODO: NEED TO IMPLEMENT THIS
    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        return 0;
    }

}
