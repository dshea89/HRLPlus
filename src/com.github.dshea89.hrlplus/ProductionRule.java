package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A super class for all the production rules. It has methods which need to be available
 * to all production rules.
 * @author Simon Colton, started 12th December 1999
 * @version 1.0
 */

public class ProductionRule implements Serializable
{

    /** How many new functions have been invented. //friday
     */

    public int number_of_new_functions = 0;

    /** A string which displays a reason why the PR returned an empty set of
     * parameterizations.
     */

    public String parameter_failure_reason = "";

    /** The arity limit for this production rule (i.e. the maximum arity of the concepts
     * it is allowed to produce).
     */

    public int arity_limit = 3;

    /** The tier (in terms of the agenda) of this production rule
     */

    public int tier = 0;

    /** The time when the stopwatch was started.
     */

    public long stopwatch_starting_time = 0;

    /** Returns true if the production rule requires two concepts as input.
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

    /** Returns the name of the production rule.
     */

    public String getName()
    {
        return("");
    }

    /** Transforms the datatables given to it into a new datatable.
     */

    public Datatable transformTable(Vector old_datatables, Vector old_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        return(null);
    }

    /** Produces the object types for the columns of the new datatable produced.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        return(null);
    }

    public Vector allParameters(Vector concept_list, Theory theory)
    {
        Vector output = new Vector();
        Vector parameter = new Vector();
        parameter.addElement("not yet");
        output.addElement(parameter);
        return output;
    }


    /** This checks whether the given permutation is the identity permutation.
     */

    public boolean isIdentityPermutation(Vector parameters)
    {
        int i=0;
        while(i<parameters.size())
        {
            int entry = new Integer((String)parameters.elementAt(i)).intValue();
            if (entry!=i) break;
            i++;
        }
        if (i==parameters.size()) return true;
        else return false;
    }

    /** This produces new specifications for a new concept.
     */

    public Vector newSpecifications(Vector concept_list, Vector parameters, Theory theory, Vector new_functions)
    {
        return (new Vector());
    }

    /** This removes any permutations which are the identity permutation from a list of
     * permutations.
     */

    public void removeIdentityPermutations(Vector parameters)
    {
        int i=0;
        while (i<parameters.size())
        {
            if (isIdentityPermutation((Vector)parameters.elementAt(i)))
                parameters.removeElementAt(i);
            else i++;
        }
    }

    /** This removes the given columns from a datatable.
     */

    public Datatable removeColumns(Datatable old_datatable, Vector parameters)
    {
        Datatable output = new Datatable();
        for (int i=0;i<old_datatable.size();i++)
        {
            Row row = (Row)old_datatable.elementAt(i);
            String entity = row.entity;
            Vector tuples = row.tuples;
            Tuples new_tuples = new Tuples();
            for (int j=0;j<tuples.size();j++)
            {
                Vector tuple=(Vector)((Vector)tuples.elementAt(j)).clone();
                tuple.insertElementAt(entity,0);
                Vector new_tuple = removeColumns(tuple,parameters);
                new_tuple.removeElementAt(0);
                new_tuples.addElement(new_tuple);
            }
            new_tuples.removeDuplicates();
            Row new_row = new Row(entity,new_tuples);
            if(new_tuples.size()>0) output.addElement(new_row);
        }
        return output;
    }

    /** This interchanges the elements of a vector with two elements.
     */

    public Vector swap(Vector concepts)
    {
        Vector output = new Vector();
        output.addElement(concepts.elementAt(1));
        output.addElement(concepts.elementAt(0));
        return output;
    }

    /** This returns a new vector with the repeated elements removed.
     */

    public Vector removeRepeatedElements(Vector input)
    {
        Vector output = new Vector();
        for (int i=0;i<input.size();i++)
        {
            if (!output.contains(input.elementAt(i)))
                output.addElement(input.elementAt(i));
        }
        return output;
    }

    /** This returns all possible tuples of columns which do not include
     * column, given the arity of the original concept.
     */

    public Vector allColumnTuples(int arity)
    {
        Vector output = new Vector();
        output.addElement(new Vector());
        int old_output_size = 0;
        for (int i=1;i<arity;i++)
        {
            int l = output.size();
            for (int j=old_output_size;j<l;j++)
            {
                old_output_size = l;
                Vector old_tuple = (Vector)output.elementAt(j);
                int start_int = 1;
                if (!old_tuple.isEmpty())
                    start_int = new Integer((String)old_tuple.lastElement()).intValue();
                for (int k=start_int+1;k<=arity;k++)
                {
                    Vector new_tuple = new Vector();
                    if (!old_tuple.contains(Integer.toString(k-1)))
                    {
                        new_tuple = (Vector)old_tuple.clone();
                        new_tuple.addElement(Integer.toString(k-1));
                        output.addElement(new_tuple);
                    }
                }
            }
        }
        output.removeElementAt(0);
        return output;
    }

    /** This removes the given columns from the given tuple.
     */

    public Vector removeColumns(Vector tuple, Vector parameters)
    {
        for(int i=0;i<parameters.size();i++)
        {
            try {
                int j=new Integer((String)parameters.elementAt(i)).intValue();
                tuple.removeElementAt(j-i);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return tuple;
    }

    /** This keeps only the given columns from the given tuple.
     * Columns are assumed to be in the correct order.
     */

    public Vector keepColumns(Vector tuple, Vector parameters)
    {
        Vector output = new Vector();
        for(int i=0;i<parameters.size();i++)
        {
            int pos=(new Integer((String)parameters.elementAt(i))).intValue();
            output.addElement(tuple.elementAt(pos));
        }
        return output;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list,
                            String object_types)
    {
        if (!((object_type_after_step(concept_list)).equals(object_types)))
            return 0;
        return patternScore(concept_list, all_concepts, entity_list, non_entity_list);
    }

    /** Returns the type of objects of interest which will be involved in the
     * resulting concept.
     */

    public String object_type_after_step(Vector concept_list)
    {
        Concept concept = (Concept)concept_list.elementAt(0);
        return concept.object_type;
    }

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        return 0;
    }

    /** This starts a stopwatch (to be used to stop datatable constructions
     * which are taking too long).
     */

    public void startStopWatch()
    {
        stopwatch_starting_time = (new Long(System.currentTimeMillis())).longValue();
    }

    /** This tells how long (in milliseconds) that the stopwatch has been
     * running.
     */

    public long stopWatchTime()
    {
        long current_time = (new Long(System.currentTimeMillis())).longValue();
        return current_time - stopwatch_starting_time;
    }

    /** Finds a concept in the given concept with matching specifications
     */

    public Concept getConceptFromSpecs(Vector specs, Vector concepts, String types)
    {
        Concept concept = null;
        Vector sorted_specs = sortSpecs(specs);
        boolean found = false;
        for (int i=0; i<concepts.size() && !found; i++)
        {
            concept = (Concept)concepts.elementAt(i);
            if (types.equals(concept.types.toString()) &&
                    concept.specifications.size()==specs.size())
            {
                int j=0;
                while (j<sorted_specs.size())
                {
                    Specification spec = (Specification)sorted_specs.elementAt(j);
                    int k=0;
                    while (k<concept.specifications.size())
                    {
                        Specification check_spec =
                                (Specification)concept.specifications.elementAt(k);
                        if (check_spec.equals(spec)) break;
                        k++;
                    }
                    if (k==concept.specifications.size())
                        break;
                    j++;
                }
                if (j==specs.size())
                    found=true;
            }
        }
        return concept;
    }

    public Vector sortSpecs(Vector specs)
    {
        Vector output = new Vector();
        for (int i=0;i<specs.size();i++)
        {
            Specification spec = (Specification)specs.elementAt(i);
            if (!output.contains(spec))
            {
                int j=0;
                boolean placed = false;
                while (j<output.size() && !placed)
                {
                    Specification previous_spec = (Specification)output.elementAt(j);
                    if (spec.id_number < previous_spec.id_number)
                    {
                        output.insertElementAt(spec, j);
                        placed = true;
                    }
                    j++;
                }
                if (!placed)
                    output.addElement(spec);
            }
        }
        return output;

    }
}
