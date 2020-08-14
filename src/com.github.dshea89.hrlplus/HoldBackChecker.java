package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.util.Hashtable;
import java.io.Serializable;

/** A class for checking whether any data held back by the user (but specified as
 * available for counterexamples) breaks a given conjecture.
 *
 * @author Simon Colton, started 1st September 2001
 * @version 1.0 */

public class HoldBackChecker extends DataGenerator implements Serializable
{
    /** The hashtable of counterexamples.
     */

    public Hashtable counterexamples = new Hashtable();

    /** This adds a counterexample for the particular domain (key).
     */

    public void addCounterexample(String key, String counterexample)
    {
        if (counterexamples.get(key)==null)
        {
            Vector c_vector = new Vector();
            c_vector.addElement(counterexample);
            counterexamples.put(key,c_vector);
        }
        else
        {
            Vector c_vector = (Vector)counterexamples.get(key);
            c_vector.addElement(counterexample);
        }
    }

    /** This checks whether the given conjecture is broken by any counterexamples
     * held back by the user. It stops if it finds num_required counterexamples.
     */

    public Vector counterexamplesFor(Conjecture conjecture, Theory theory, int num_required)
    {
        Vector output = new Vector();

        // Deals with equivalence conjectures //

        if (conjecture instanceof Equivalence)
        {
            String entity_name = "";
            Equivalence equiv = (Equivalence)conjecture;
            String entity_type = (String)equiv.lh_concept.types.elementAt(0);
            Vector possible_counterexamples = (Vector)counterexamples.get(entity_type);
            if (possible_counterexamples==null || possible_counterexamples.isEmpty())
                return new Vector();
            int i=0;
            while (i<possible_counterexamples.size() && num_required > output.size())
            {
                entity_name = (String)possible_counterexamples.elementAt(i);
                Tuples lh_tuples = equiv.lh_concept.calculateRow(theory.concepts, entity_name).tuples;
                lh_tuples.sort();
                Tuples rh_tuples = equiv.rh_concept.calculateRow(theory.concepts, entity_name).tuples;
                rh_tuples.sort();
                String lh_row = lh_tuples.toString();
                String rh_row = rh_tuples.toString();
                if (!lh_row.equals(rh_row))
                {
                    Entity entity = new Entity();
                    entity.name = entity_name;
                    entity.conjecture = conjecture;
                    output.addElement(entity);
                    possible_counterexamples.removeElementAt(i);
                    i--;
                }
                i++;
            }
        }

        // Deals with implication conjectures //

        if (conjecture instanceof Implication)
        {
            Implication implication = (Implication)conjecture;
            String entity_name = "";
            String entity_type = (String)implication.lh_concept.types.elementAt(0);
            Vector possible_counterexamples = (Vector)counterexamples.get(entity_type);
            if (possible_counterexamples==null || possible_counterexamples.isEmpty())
                return new Vector();
            int i=0;
            while (i<possible_counterexamples.size() && num_required > output.size())
            {
                entity_name = (String)possible_counterexamples.elementAt(i);
                Tuples lh_tuples = implication.lh_concept.calculateRow(theory.concepts, entity_name).tuples;
                Tuples rh_tuples = implication.rh_concept.calculateRow(theory.concepts, entity_name).tuples;
                if (!rh_tuples.subsumes(lh_tuples))
                {
                    Entity entity = new Entity();
                    entity.name = entity_name;
                    entity.conjecture = conjecture;
                    possible_counterexamples.removeElementAt(i);
                    output.addElement(entity);
                    i--;
                }
                i++;
            }
        }

        // Deals with non-existence conjectures //

        if (conjecture instanceof NonExists)
        {
            NonExists non_exists = (NonExists)conjecture;
            String entity_name = "";
            String entity_type = (String)non_exists.concept.types.elementAt(0);
            Vector possible_counterexamples = (Vector)counterexamples.get(entity_type);
            if (possible_counterexamples == null || possible_counterexamples.isEmpty())
                return new Vector();
            int i=0;
            while (i<possible_counterexamples.size() && num_required > output.size())
            {
                entity_name = (String)possible_counterexamples.elementAt(i);
                Tuples tuples = non_exists.concept.calculateRow(theory.concepts, entity_name).tuples;
                if (!tuples.isEmpty())
                {
                    possible_counterexamples.removeElementAt(i);
                    Entity entity = new Entity();
                    entity.name = entity_name;
                    entity.conjecture = conjecture;
                    output.addElement(entity);
                    i--;
                }
                i++;
            }
        }
        return output;
    }

}
