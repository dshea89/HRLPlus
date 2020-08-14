package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the embed graph production rule.
 *
 * @author Simon Colton, started 14th December 2000
 * @version 1.0 */

public class EmbedGraph extends ProductionRule implements Serializable
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

    /** Returns "graph" as that is the name of this production rule.
     */

    public String getName()
    {
        return "embed_graph";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. The parameterisations are single words llike "graph" to indicate that a
     * graph is embedded in this concept.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Vector output = new Vector();
        try {
            Concept old_concept = (Concept)old_concepts.elementAt(0);
            Concept embed_concept = (Concept)old_concepts.elementAt(1);
            if (old_concept.arity==3 && embed_concept.id.equals("G01"))
            {
                String first_type = (String)old_concept.types.elementAt(1);
                String second_type = (String)old_concept.types.elementAt(2);
                if (first_type.equals(second_type))
                    output.addElement(new Vector());
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the embed production
     * rule.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Specification output_spec = new Specification();
        output_spec.permutation.addElement("0");
        output_spec.permutation.addElement("1");
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        output_spec.type = "embed graph "+old_concept.id;
        Vector output = new Vector();
        output.addElement(output_spec);
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable output = new Datatable();
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        for (int i=0;i<old_datatable.size();i++)
        {
            Row row = (Row)old_datatable.elementAt(i);
            Row new_row = new Row();
            new_row.entity = row.entity;
            Vector elements_seen = new Vector();
            Vector letters_used = new Vector();
            String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZ1234567890";
            String nodes = "";
            String edges = "[";
            for (int j=0; j<row.tuples.size(); j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                String left_element = (String)tuple.elementAt(0);
                String right_element = (String)tuple.elementAt(1);
                String left_node = "";
                String right_node = "";
                int left_pos = elements_seen.indexOf(left_element);
                if (left_pos>=0)
                    left_node = (String)letters_used.elementAt(left_pos);
                if (!elements_seen.contains(left_element))
                {
                    elements_seen.addElement(left_element);
                    left_node = letters.substring(0,1);
                    letters_used.addElement(left_node);
                    letters = letters.substring(1,letters.length());
                    nodes = nodes + left_node;
                }
                int right_pos = elements_seen.indexOf(right_element);
                if (right_pos>=0)
                    right_node = (String)letters_used.elementAt(right_pos);
                if (!elements_seen.contains(right_element))
                {
                    elements_seen.addElement(right_element);
                    right_node = letters.substring(0,1);
                    letters_used.addElement(right_node);
                    letters = letters.substring(1,letters.length());
                    nodes = nodes + right_node;
                }
                edges = edges + left_node + right_node;
                if (j<row.tuples.size()-1)
                    edges = edges + ",";
            }
            Vector new_tuple = new Vector();
            new_tuple.addElement(nodes+edges+"]");
            new_row.tuples.addElement(new_tuple);
            output.addElement(new_row);
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector output = new Vector();
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        output.addElement(old_concept.domain);
        output.addElement("graph");
        return output;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        return 0;
    }
}
