package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the exists production rule. This production
 * rule takes an old datatable as input and a set of parameterizations.
 * The parameterization is a list of columns which are to be #removed#
 * in the output datatable.
 *
 * @author Simon Colton, started 1st September 2001
 * @version 1.0 */

public class Disjunct extends ProductionRule implements Serializable
{
    /** Returns true as this is a binary production rule.
     */

    public boolean isBinary()
    {
        return true;
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean is_cumulative = false;

    /** Returns "disjunct" as that is the name of this production rule.
     */

    public String getName()
    {
        return "disjunct";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all tuples of columns which do not contain the first column.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Vector output = new Vector();
        Concept primary_concept = (Concept)old_concepts.elementAt(0);
        Concept secondary_concept = (Concept)old_concepts.elementAt(1);
        if (primary_concept==secondary_concept)
            return output;
        if (primary_concept.arity > arity_limit)
            return output;
        if (primary_concept.isGeneralisationOf(secondary_concept)>=0 ||
                secondary_concept.isGeneralisationOf(primary_concept)>=0)
            return output;
        if (primary_concept.types.toString().equals(secondary_concept.types.toString()))
            output.addElement(new Vector());
        return output;
    }

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters, Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Concept primary_concept = (Concept)input_concepts.elementAt(0);
        Concept secondary_concept = (Concept)input_concepts.elementAt(1);

        // Add in the old specifications which appear in both concepts //

        Vector primary_specs = (Vector)primary_concept.specifications.clone();
        Vector secondary_specs = (Vector)secondary_concept.specifications.clone();

        for (int i=0; i<primary_specs.size(); i++)
        {
            Specification primary_spec = (Specification)primary_specs.elementAt(i);
            int ind = secondary_specs.indexOf(primary_spec);
            if (ind >= 0)
            {
                primary_specs.removeElementAt(i);
                secondary_specs.removeElementAt(ind);
                output.addElement(primary_spec);
            }
        }

        // Construct the new disjunct specification //

        Specification disjunct_spec = new Specification();
        disjunct_spec.type = "disjunct";
        disjunct_spec.rh_starts = primary_specs.size();
        disjunct_spec.previous_specifications = primary_specs;
        for (int i=0; i<secondary_specs.size(); i++)
            disjunct_spec.previous_specifications.addElement(secondary_specs.elementAt(i));
        Vector columns_used = new Vector();
        for (int i=0; i<disjunct_spec.previous_specifications.size(); i++)
        {
            Specification prev_spec =
                    (Specification)disjunct_spec.previous_specifications.elementAt(i);
            for (int j=0; j<prev_spec.permutation.size(); j++)
            {
                String column = (String)prev_spec.permutation.elementAt(j);
                if (!columns_used.contains(column))
                    columns_used.addElement(column);
            }
        }
        for (int i=0; i<primary_concept.arity; i++)
        {
            String column = Integer.toString(i);
            if (columns_used.contains(column))
                disjunct_spec.permutation.addElement(column);
            else
                disjunct_spec.redundant_columns.addElement(column);
        }
        output.addElement(disjunct_spec);
        return output;
    }

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable output = new Datatable();
        Datatable primary_datatable = (Datatable)input_datatables.elementAt(0);
        Datatable secondary_datatable = (Datatable)input_datatables.elementAt(1);
        for (int i=0; i<primary_datatable.size(); i++)
        {
            try {
                Row primary_row = (Row)primary_datatable.elementAt(i);
                Row secondary_row = (Row)secondary_datatable.elementAt(i);
                output.addElement(new Row(primary_row.entity, makeDisjunctRow(primary_row, secondary_row)));
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return output;
    }

    private Tuples makeDisjunctRow(Row primary_row, Row secondary_row)
    {
        Tuples new_tuples = new Tuples();
        Vector tuple_strings = new Vector();
        for (int i=0; i<primary_row.tuples.size(); i++)
        {
            Vector tuple = (Vector)primary_row.tuples.elementAt(i);
            new_tuples.addElement((Vector)tuple.clone());
            tuple_strings.addElement(tuple.toString());
        }
        for (int i=0; i<secondary_row.tuples.size(); i++)
        {
            Vector tuple = (Vector)secondary_row.tuples.elementAt(i);
            String tuple_string = tuple.toString();
            if (!tuple_strings.contains(tuple_string))
            {
                new_tuples.addElement((Vector)tuple.clone());
                tuple_strings.addElement(tuple_string);
            }
        }
        return new_tuples;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Concept primary_concept = (Concept)old_concepts.elementAt(0);
        return primary_concept.types;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        Concept primary_concept = (Concept)concept_list.elementAt(0);
        Concept secondary_concept = (Concept)concept_list.elementAt(1);
        String first_entity = (String)entity_list.elementAt(0);
        Row primary_first_row = primary_concept.datatable.rowWithEntity(first_entity);
        Row secondary_first_row = secondary_concept.datatable.rowWithEntity(first_entity);
        Tuples first_tuples = makeDisjunctRow(primary_first_row, secondary_first_row);
        int score = non_entity_list.size();
        int i=1;
        while (score>0 && i<first_tuples.size())
        {
            Vector tuple = (Vector)first_tuples.elementAt(i);
            int j=1;
            while (score>0 && j<entity_list.size())
            {
                String entity = (String)entity_list.elementAt(j);
                Row primary_row = primary_concept.datatable.rowWithEntity(entity);
                if (!primary_row.tuples.contains(tuple))
                {
                    Row secondary_row = primary_concept.datatable.rowWithEntity(entity);
                    if (!secondary_row.tuples.contains(tuple))
                        score=0;
                }
                j++;
            }
            i++;
        }
        return score;
    }
}
