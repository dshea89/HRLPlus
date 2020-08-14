package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** EntityDisjunct Production Rule
 *
 * @author Alison Pease and Simon Colton, started 25th March 2003
 * @version 1.0 */

public class EntityDisjunct extends ProductionRule implements Serializable
{
    /** The maximum number of entities allowed in the disjunction.
     */

    public int max_num_entities_in_disjunction = 3;

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


    /** Whether or not this is a (possibly single) disjunction or conjunction
     * [or combo] of single_entity concepts. alisonp @see Concept
     */

    public boolean is_entity_instantiations = true;

    /** Returns "entity_disjunct" as that is the name of this production rule.
     */

    public String getName()
    {
        return "entity_disjunct";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Vector output = new Vector();
        return output;
    }

    /** This
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector old_specifications = new Vector();
        old_specifications = old_concept.specifications;
        Vector old_types = old_concept.types;
        String old_object_type = (String)old_types.elementAt(0);
        Concept oo_concept = null;
        for (int i=0; i<theory.concepts.size(); i++)
        {
            oo_concept = (Concept)theory.concepts.elementAt(i);
            if (oo_concept.is_object_of_interest_concept &&
                    oo_concept.types.toString().equals("["+old_object_type+"]"))
                break;
        }

        if (oo_concept==null)
            return new Vector();
        Vector oo_concept_vector = new Vector();
        oo_concept_vector.addElement(oo_concept);
        Vector dummy_concepts_to_disjoin = new Vector();
        for (int i=0; i<input_parameters.size(); i++)
        {
            Vector split_params = new Vector();
            Vector lh_params = new Vector();
            Vector rh_params = new Vector();
            split_params.addElement(lh_params);
            split_params.addElement(rh_params);
            lh_params.addElement("0");
            rh_params.addElement((String)input_parameters.elementAt(i));
            String entity_to_split_on = (String)input_parameters.elementAt(i);
            Concept dummy_concept = new Concept();
            dummy_concept.specifications =
                    theory.split.newSpecifications(oo_concept_vector, split_params, theory, new Vector());
            dummy_concept.arity = 1;
            dummy_concept.types = (Vector)oo_concept.types.clone();
            dummy_concept.id = "ed_dummy";
            dummy_concepts_to_disjoin.addElement(dummy_concept);
        }

        Concept cumulative_dummy_concept = (Concept)dummy_concepts_to_disjoin.elementAt(0);
        for (int i=1; i<dummy_concepts_to_disjoin.size(); i++)
        {
            Concept next_concept_to_disjoin = (Concept)dummy_concepts_to_disjoin.elementAt(i);
            cumulative_dummy_concept = disjoinDummyConcepts(cumulative_dummy_concept, next_concept_to_disjoin, theory);
        }
        return cumulative_dummy_concept.specifications;
    }

    public Concept disjoinDummyConcepts(Concept cumulative_concept, Concept next_concept, Theory theory)
    {
        Vector concepts_to_disjoin = new Vector();
        concepts_to_disjoin.addElement(cumulative_concept);
        concepts_to_disjoin.addElement(next_concept);
        Concept output_concept = new Concept();
        output_concept.specifications =
                theory.disjunct.newSpecifications(concepts_to_disjoin, new Vector(), theory, new Vector());
        output_concept.arity = 1;
        output_concept.types = (Vector)cumulative_concept.types.clone();
        return output_concept;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule removes columns from the input datatable, then
     * removes any duplicated rows. The parameters details which columns are to be #removed#.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable old_table = (Datatable)input_datatables.elementAt(0);
        Datatable output = new Datatable();
        for (int i=0; i<old_table.size(); i++)
        {
            Row old_row = (Row)old_table.elementAt(i);
            Tuples tuples = new Tuples();
            if (parameters.contains(old_row.entity))
                tuples.addElement(new Vector());
            Row new_row = new Row(old_row.entity, tuples);
            output.addElement(new_row);
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        Vector output = new Vector();
        output.addElement(old_concept.types.elementAt(0));
        return output;
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
        return 0;
    }
}
