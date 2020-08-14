package com.github.dshea89.hrlplus;

import java.util.Enumeration;
import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the match production rule. This production
 * rule takes an old datatable as input and a set of
 * parameterizations, and extracts rows where certain columns match
 * into the new datatable. The parameterization dictates which
 * columns have to match. For example parameters [0,1,1] state that
 * columns 1 and 2 should match.
 *
 * @author Simon Colton, started 3rd January 1999
 * @version 1.0 */

public class Match extends ProductionRule implements Serializable
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
        return false;
    }

    /** Returns "match" as that is the name of this production rule.
     */

    public String getName()
    {
        return "match";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all possible ways to match the columns, noting that two columns
     * of different types cannot be matched.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Concept concept = (Concept)old_concepts.elementAt(0);
        int arity = concept.arity;
        Vector types = concept.types;
        Vector output = new Vector();
        Vector first_vector = new Vector();
        first_vector.addElement("0");
        Vector output_strings = new Vector();
        output_strings.addElement("[0]");
        output.addElement(first_vector);
        for (int i=1;i<arity;i++)
        {
            int l=output.size();
            for (int j=0;j<l;j++)
            {
                Vector old_tuple = (Vector)output.elementAt(0);
                for (int k=0;k<=i;k++)
                {
                    if (k<i)
                    {
                        int pos = new Integer((String)old_tuple.elementAt(k)).intValue();
                        if (((String)types.elementAt(i)).equals((String)types.elementAt(pos)))
                        {
                            Vector new_tuple = (Vector)old_tuple.clone();
                            new_tuple.addElement((String)old_tuple.elementAt(k));
                            if(!output_strings.contains(new_tuple.toString()))
                            {
                                output.addElement(new_tuple);
                                output_strings.addElement(new_tuple.toString());
                            }
                        }
                    }
                    else
                    {
                        Vector new_tuple = (Vector)old_tuple.clone();
                        new_tuple.addElement(Integer.toString(i));
                        if(!output_strings.contains(new_tuple.toString()))
                        {
                            output.addElement(new_tuple);
                            output_strings.addElement(new_tuple.toString());
                        }
                    }
                }
                output.removeElementAt(0);
            }
        }
        Vector final_output = new Vector();
        for (int i=0; i<output.size(); i++)
        {
            Vector p = (Vector)output.elementAt(i);
            if (super.removeRepeatedElements(p).size() <= arity_limit)
                final_output.addElement(p);
        }
        super.removeIdentityPermutations(final_output);
        return final_output;
    }

    /** This produces the new specifications for concepts output using the exists production
     * rule. To do this, it performs the matching on the permutations of the previous
     * specifications. Must be careful to change resulting permutations such as [0,0,2]
     * to [0,0,1]. Note also that this may cause repeated specifications which should be
     * removed.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector old_specifications = old_concept.specifications;
        Enumeration e = old_specifications.elements();
        Vector changed_parameters = new Vector();
        int add_in = 0;
        for (int i=0;i<input_parameters.size();i++)
        {
            String column = (String)input_parameters.elementAt(i);
            if (((new Integer(column)).intValue())==i)
            {
                changed_parameters.addElement(Integer.toString(add_in));
                add_in++;
            }
            else
            {
                int pos = (new Integer(column)).intValue();
                changed_parameters.addElement(changed_parameters.elementAt(pos));
            }
        }
        while (e.hasMoreElements())
        {
            Specification old_specification = (Specification)e.nextElement();
            Vector permutation = old_specification.permutation;
            Specification new_specification = old_specification.copy();
            Vector new_permutation = new Vector();
            for (int i=0; i<permutation.size(); i++)
            {
                int pos = (new Integer((String)permutation.elementAt(i))).intValue();
                String add_to_perm = (String)changed_parameters.elementAt(pos);
                new_permutation.addElement(add_to_perm);
            }
            new_specification.permutation = new_permutation;
            output.addElement(new_specification);
        }
        for (int i=0;i<output.size()-1;i++)
        {
            Specification first_spec = (Specification)output.elementAt(i);
            int j=i+1;
            while (j<output.size())
            {
                Specification second_spec = (Specification)output.elementAt(j);
                if (second_spec.equals(first_spec))
                    output.removeElementAt(j);
                else j++;
            }
        }

        // Finally, get the new functions.

        Vector temp_strings = new Vector();
        for (int i=0;i<old_concept.functions.size();i++)
        {
            Function function = (Function)old_concept.functions.elementAt(i);
            Function new_function = function.copy();
            new_function.matchPermute(input_parameters);
            if (!temp_strings.contains(new_function.writeFunction()))
            {
                if (!new_function.outputAsInput())
                    new_functions.addElement(new_function);
                temp_strings.addElement(new_function.writeFunction());
            }
        }
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule extracts rows from the input datatable where the columns
     * prescribed by the parameters match. It then optimises the rows by removing the
     * latter of two matching columns.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
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
                boolean match_test = true;
                int k=0;
                while (match_test && k<tuple.size())
                {
                    int match_row = new Integer((String)parameters.elementAt(k)).intValue();
                    if (!((String)tuple.elementAt(match_row)).equals((String)tuple.elementAt(k)))
                        match_test=false;
                    k++;
                }
                if (match_test)
                {
                    Vector tuple_to_add = removeMatchedColumns(tuple,parameters);
                    tuple_to_add.removeElementAt(0);
                    new_tuples.addElement(tuple_to_add);
                }
            }
            Row new_row = new Row(entity,new_tuples);
            output.addElement(new_row);
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        return(removeMatchedColumns(old_types,parameters));
    }

    private Vector removeMatchedColumns(Vector tuple, Vector parameters)
    {
        Vector output = new Vector();
        for (int i=0;i<parameters.size();i++)
        {
            int match_pos = new Integer((String)parameters.elementAt(i)).intValue();
            if (match_pos == i)
            {
                try {
                    output.addElement(tuple.elementAt(i));
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }
        return output;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern. The pattern for the match
     * production rule is that each of the entities has a row where entries in
     * the tuples match.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        Concept c = (Concept)concept_list.elementAt(0);
        if (c.arity!=2 && c.arity!=3) return 0;
        boolean pattern_holds = true;
        int num_with_pattern = 0;
        int i=0;
        while (i<entity_list.size() && pattern_holds)
        {
            String entity = (String)entity_list.elementAt(i);
            Row row = c.calculateRow(all_concepts,entity);
            boolean has_matcher = false;
            for (int j=0;j<row.tuples.size();j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                if (c.arity==3 &&
                        ((String)tuple.elementAt(0)).equals((String)tuple.elementAt(1)))
                {
                    num_with_pattern++;
                    has_matcher = true;
                }
                if (c.arity==2 &&
                        ((String)tuple.elementAt(0)).equals(entity))
                {
                    num_with_pattern++;
                    has_matcher = true;
                }
            }
            i++;
        }
        if (num_with_pattern!=0 && num_with_pattern!=entity_list.size())
            return 0;
        int score = non_entity_list.size();
        for (i=0;i<non_entity_list.size();i++)
        {
            String entity = (String)non_entity_list.elementAt(i);
            Row row = c.calculateRow(all_concepts,entity);
            boolean has_matcher = false;
            for (int j=0;j<row.tuples.size();j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                if (c.arity==3 &&
                        ((String)tuple.elementAt(0)).equals((String)tuple.elementAt(1)))
                    has_matcher = true;
                if (c.arity==2 &&
                        ((String)tuple.elementAt(0)).equals(entity))
                    has_matcher = true;
            }
            if (has_matcher && num_with_pattern>0)
                score--;
            if (!has_matcher && num_with_pattern==0)
                score--;
        }
        return score;
    }
}
