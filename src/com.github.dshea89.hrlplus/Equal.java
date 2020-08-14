package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class representing the equal production rule. This production
 * rule takes an old datatable as input and a set of parameterizations.
 *
 * @author Simon Colton, started 8th May 2002
 * @version 1.0 */

public class Equal extends ProductionRule implements Serializable
{
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

    /** Returns "equal" as that is the name of this production rule.
     */

    public String getName()
    {
        return "equal";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all tuples of columns which do not contain the first column.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        if (old_concept.arity > arity_limit)
            return new Vector();
        Vector params = super.allColumnTuples(old_concept.arity+1);
        Vector output = new Vector();
        for (int i=0; i<params.size(); i++)
        {
            Vector p = (Vector)params.elementAt(i);
            if (p.size()>1)
            {
                boolean no_good = false;
                Vector param = new Vector();
                String old_type = "";
                for (int j=0; j<p.size() && !no_good; j++)
                {
                    String colstring = (String)p.elementAt(j);
                    int colpos = (new Integer(colstring)).intValue() - 1;
                    String type = (String)old_concept.types.elementAt(colpos);
                    if (old_type.equals(""))
                        old_type = type;
                    else
                    if (!old_type.equals(type))
                        no_good = true;
                    param.addElement((new Integer(colpos)).toString());
                }
                if (!no_good)
                    output.addElement(param);
            }
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the equal production
     * rule.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector output = (Vector)old_concept.specifications.clone();

        Relation relation = new Relation();
        String relation_letters = "a";
        String relation_string = "@a@";
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l",
                "m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

        Vector permutation = new Vector();
        permutation.addElement("0");
        for (int i=1; i<input_parameters.size(); i++)
        {
            relation_letters = relation_letters + letters[i];
            relation_string = relation_string + "=@" + letters[i] + "@";
            permutation.addElement(Integer.toString(i));
        }
        relation.addDefinition(relation_letters, relation_string, "ascii");
        relation.name = "equal" + input_parameters.toString();
        Specification new_specification = new Specification();
        new_specification.addRelation(permutation, relation);
        new_specification.permutation = (Vector)input_parameters.clone();
        output.addElement(new_specification);
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule removes columns from the input datatable, then
     * removes any duplicated rows. The parameters details which columns are to be #removed#.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable output = new Datatable();
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        for (int i=0; i<old_datatable.size(); i++)
        {
            Row row = (Row)old_datatable.elementAt(i);
            Tuples new_tuples = new Tuples();
            for (int j=0; j<row.tuples.size(); j++)
            {
                boolean matches = true;
                Vector tuple = (Vector)row.tuples.elementAt(j);
                String global_match_string = "";
                for (int k=0; k<parameters.size() && matches; k++)
                {
                    String colstring = (String)parameters.elementAt(k);
                    String match_string = "";
                    if (colstring.equals("0"))
                        match_string = row.entity;
                    else
                    {
                        try {
                            int pos = (new Integer(colstring)).intValue() - 1;
                            match_string = (String)tuple.elementAt(pos);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                    if (k==0)
                        global_match_string = match_string;
                    if (k>0 && !match_string.equals(global_match_string))
                        matches = false;
                }
                if (matches==true)
                    new_tuples.addElement(tuple);
            }
            output.addElement(new Row(row.entity, new_tuples));
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        return old_types;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        int score = 0;
        return score;
    }
}
