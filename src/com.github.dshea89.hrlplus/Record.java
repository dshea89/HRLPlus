package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the record production rule. This production
 * rule takes an old datatable as input and an empty paramaterization.
 * If the concept is a numerical function, it returns a new concept which
 * consists of the first entities which score higher for the function than
 * those before it.
 *
 * @author Simon Colton, started 13th May 2000
 * @version 1.0 */

public class Record extends ProductionRule implements Serializable
{
    /** Returns false as this is a unary production rule.
     */

    public boolean isBinary()
    {
        return false;
    }

    /** Whether or not this produces cumulative concepts. This is cumulative.
     * @see Concept
     */

    public boolean isCumulative()
    {
        return true;
    }

    /** Returns "record" as that is the name of this production rule.
     */

    public String getName()
    {
        return "record";
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule removes columns from the input datatable, then
     * removes any duplicated rows. The parameters details which columns are to be #removed#.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        Datatable output = new Datatable();
        int top_number = -1000000;
        for (int i=0; i<old_datatable.size(); i++)
        {
            Row row = (Row)old_datatable.elementAt(i);
            Tuples tuples = new Tuples();
            if (!row.tuples.isEmpty())
            {
                Vector tuple = (Vector)row.tuples.elementAt(0);
                int try_number = (new Integer((String)tuple.elementAt(0))).intValue();
                if (try_number > top_number)
                {
                    top_number = try_number;
                    tuples.addElement(new Vector());
                }
            }
            Row new_row = new Row(row.entity, tuples);
            output.addElement(new_row);
        }
        return output;
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all tuples of columns which do not contain the first column.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Vector output = new Vector();
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        if (!old_concept.domain.equals("number"))
            return output;
        if (old_concept.arity == 2)
        {
            String second_type = (String)old_concept.types.elementAt(0);
            if (((String)old_concept.types.elementAt(1)).equals("integer"))
                output.addElement(new Vector());
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the record production
     * rule.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Vector old_specifications = ((Concept)input_concepts.elementAt(0)).specifications;
        Specification record_specification = new Specification();
        record_specification.previous_specifications = old_specifications;
        record_specification.type = "record";
        record_specification.permutation.addElement("0");
        record_specification.multiple_variable_columns.addElement("1");
        record_specification.multiple_types.addElement("integer");
        output.addElement(record_specification);
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        Vector output = new Vector();
        output.addElement((String)old_types.elementAt(0));
        return(output);
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
        int score = 0;
        return score;
    }
}
