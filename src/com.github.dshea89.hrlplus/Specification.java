package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the specification of a relation within a concept. It details how
 * the relation is applied to the objects in the concept.
 */

public class Specification implements Serializable
{
    /** Whether or not this specification is an instantiation of an entity, e.g., a=4.
     */

    public boolean is_single_entity = false;

    /** Whether or not this is a set of instantiations of an entity, e.g,, a=4 or a=5.
     */

    public boolean is_entity_instantiations = false;

    /** The number of this specification. The specifications in every concept and
     * every multiple specification are ordered so that it is easier to compare them.
     */

    public int id_number = 0;

    /** The set of function details for this specification.
     * @see Function
     */

    public Vector functions = new Vector();

    /** Whether this is a forall, exists, disjunct, split or negate type specification.
     */

    public String type = "";

    /** The set of columns which need to be added back in to enable the previous
     * specifications to be written out.
     */

    public Vector multiple_variable_columns = new Vector();

    /** In multiple specifications, there will be some columns
     * (variables) which are not used in any of the previous
     * specifications, which this vector holds. This is required to make
     * multiple specifications work.
     */

    public Vector redundant_columns = new Vector();

    /** The types of the variables which need to be added back in for multiple
     * specifications.
     */

    public Vector multiple_types = new Vector();

    /** The set of previous specifications (if any) by which the quantified variables
     * are related.
     */

    public Vector previous_specifications = new Vector();

    /** The set of fixed values which are going to be added in instead of variables.
     * These come about whenever the split production rule is used.
     */

    public Vector fixed_values = new Vector();

    /** The relations which holds within the specification. eg. P(a,b). Note that at present
     * this will contain only one relation, but has been left as a vector for future developments.
     */

    public Vector relations = new Vector();

    /** The way in which the variables are to be permuted before applying the relation.
     * For example, given variables [a,b,c] and permutation [2,1] with relation P(a,b)
     * then this specification says that P(c,b) must hold.
     */

    public Vector permutation = new Vector();

    /** In specifications of the forall type, there is a left hand and a right hand
     * side. This marks where the right hand starts.
     */

    public int rh_starts = 0;

    /** A simple constructor.
     */

    public Specification()
    {
    }

    /** A constructor which also sets the type of the specification.
     */

    public Specification(String t)
    {
        type = t;
    }

    /** A constructor which also sets the relations and the permutation.
     */

    public Specification(String perm, Relation relation)
    {
        permutation = getVector(perm);
        addRelation(permutation, relation);
    }

    /** This check whether this specification is a set of entity instantiations,
     * e.g, a=4 or a=5.
     */

    public boolean isEntityInstantiations()
    {
        if (is_entity_instantiations)
            return true;
        if (type.equals(""))
            return is_single_entity;
        boolean output = false;
        for (int i=0; i<previous_specifications.size() && !output; i++)
        {
            Specification previous_spec = (Specification)previous_specifications.elementAt(i);
            output = previous_spec.is_entity_instantiations;
        }
        return output;
    }

    public void addRelation(Vector permutation, Relation relation)
    {
        relations.addElement(relation);
        for (int i=0; i<relation.function_columns.size(); i++)
        {
            Vector fc = (Vector)relation.function_columns.elementAt(i);
            Vector lh = (Vector)fc.elementAt(0);
            Vector rh = (Vector)fc.elementAt(1);
            Vector new_lh = new Vector();
            for (int j=0; j<lh.size(); j++)
            {
                String col = (String)lh.elementAt(j);
                int pos = (new Integer(col)).intValue();
                String new_pos = (String)permutation.elementAt(pos);
                new_lh.addElement(new_pos);
            }
            Vector new_rh = new Vector();
            for (int j=0; j<rh.size(); j++)
            {
                String col = (String)rh.elementAt(j);
                int pos = (new Integer(col)).intValue();
                String new_pos = (String)permutation.elementAt(pos);
                new_rh.addElement(new_pos);
            }
            Function function = new Function(relation.name, new_lh, new_rh);
            functions.addElement(function);
        }
    }

    /** This returns a specification which is a clone of the present one.
     * Note that relations is not cloned (only a copy of pointers is cloned).
     * Also, previous specifications is not cloned.
     */

    public Specification copy()
    {
        Specification output = new Specification();
        output.id_number = id_number;
        output.relations = relations;
        output.permutation = (Vector)permutation.clone();
        output.type = type;
        output.fixed_values = (Vector)fixed_values.clone();
        output.multiple_variable_columns = (Vector)multiple_variable_columns.clone();
        output.multiple_types = (Vector)multiple_types.clone();
        output.previous_specifications = (Vector)previous_specifications.clone();
        output.redundant_columns = (Vector)redundant_columns.clone();
        output.functions = (Vector)functions.clone();
        output.rh_starts = rh_starts;
        return output;
    }

    /** This tests whether this specification has the same permutation, relations,
     * quantification and negation state as the given specifications.
     */

    public boolean equals(Specification other)
    {
        // First check the types are the same.

        if (!type.equals(other.type))
            return false;

        if (!(rh_starts==other.rh_starts))
            return false;

        // Next check whether all the associated vectors of strings are the same.

        if (!compareStringVectors(permutation,other.permutation))
            return false;
        if (!compareStringVectors(multiple_variable_columns,other.multiple_variable_columns))
            return false;
        if (!compareStringVectors(multiple_types,other.multiple_types))
            return false;
        if (!compareStringVectors(redundant_columns,other.redundant_columns))
            return false;
        if (!compareStringVectors(fixed_values,other.fixed_values))
            return false;

        boolean output = true;

        // Next check that the sets of relations are the same.

        if (relations.size()!=other.relations.size()) return false;
        int i=0;
        while (i<relations.size() && output)
        {
            Relation relation = (Relation)relations.elementAt(i);
            Relation other_relation = (Relation)other.relations.elementAt(i);
            if (!relation.equals(other_relation)) output=false;
            i++;
        }
        if (!output) return false;

        // Finally check that the previous specifications are the same. This works
        // by calling equals recursively.

        i=0;
        if (previous_specifications.size()!=other.previous_specifications.size())
            return false;
        while (i<previous_specifications.size() && output)
        {
            if (!((Specification)previous_specifications.elementAt(i)).
                    equals((Specification)other.previous_specifications.elementAt(i)))
                output = false;
            i++;
        }
        return output;
    }

    private boolean compareStringVectors(Vector vector1, Vector vector2)
    {
        if (vector1.size()!=vector2.size()) return false;
        int i=0;
        while (i<vector1.size())
        {
            if (!((String)vector1.elementAt(i)).equals((String)vector2.elementAt(i)))
                return false;
            i++;
        }
        return true;
    }

    /** Checks whether the specification is a quantification or not.
     */

    public boolean isMultiple()
    {
        if (type.equals("")) return false;
        return true;
    }

    /** This will permute the letters given to it and write the first definition for the
     * specification. Note that it must have all the quantified letters also.
     */

    public String writeDefinition(String language, Vector letters)
    {
        String output = "";
        Vector permuted_letters = permute(letters);
        Relation relation = (Relation)relations.elementAt(0);
        Definition definition = relation.getDefinition(language);
        output = definition.write(permuted_letters);
        return output;
    }

    // This permutes the letters to fit the relation.

    private Vector permute(Vector input_vector)
    {
        Vector output = new Vector();
        for (int i=0;i<permutation.size();i++)
        {
            int position = ((new Integer((String)permutation.elementAt(i))).intValue());
            output.addElement(input_vector.elementAt(position));
        }    return output;
    }

    /** Checks whether the specification involves any of the given
     * columns. This just involves checking whether the permutation contains any
     * of the columns in the given vector.  */

    public boolean involvesColumns(Vector columns)
    {
        boolean output = false;
        int pos = 0;
        while (!output && pos < permutation.size())
        {
            String column = (String)permutation.elementAt(pos);
            if (columns.contains(column))
                output = true;
            pos++;
        }
        return output;
    }

    private Vector getVector(String s)
    {
        Vector output = new Vector();
        String add_in = "";
        for (int i=1;i<s.length()-1;i++)
        {
            String letter = s.substring(i,i+1);
            if (letter.equals(","))
            {
                output.addElement(add_in);
                add_in = "";
            }
            else
                add_in = add_in + letter;
        }
        output.addElement(add_in);
        return output;
    }

    /** This will write the specification without permuting the letters - it will
     * also choose the letters as a, b, c, d, etc.
     */

    public String writeSpec()
    {
        Vector letters = new Vector();
        letters.addElement("a");
        letters.addElement("b");
        letters.addElement("c");
        letters.addElement("d");
        letters.addElement("e");
        letters.addElement("f");
        letters.addElement("g");
        if (relations.isEmpty())
            return "no relations";
        Relation rel = (Relation)relations.elementAt(0);
        if (rel.definitions.isEmpty())
            return "no definitions in relation 1";
        Definition def = (Definition)rel.definitions.elementAt(0);
        return def.write(letters);
    }

    /** Whether or not this is a negation of the given specification.
     */

    public boolean isNegationOf(Specification other)
    {
        if (!type.equals("negate"))
            return false;
        if (previous_specifications.size()==0)
            return false;
        Specification previous = (Specification)previous_specifications.elementAt(0);
        return (other==previous);
    }

    /** Returns a string of the details of this specification
     */

    public String fullDetails()
    {
        return fullDetails("");
    }

    private String fullDetails(String indent)
    {
        String output = "";
        output = indent+id_number+"  ("+type+")"+" "+toString()+"\n"+indent+writeSpec()+"\n";
        output = output + indent + "multiple_variable_columns="+multiple_variable_columns.toString()+"\n";
        output = output + indent + "redundant_columns="+redundant_columns.toString()+"\n";
        output = output + indent + "multiple_types="+multiple_types.toString()+"\n";
        output = output + indent + "fixed_values="+fixed_values.toString()+"\n";
        output = output + indent + "permutation="+permutation.toString()+"\n";
        output = output + indent + "rh_starts="+Integer.toString(rh_starts)+"\n";
        output = output + indent + "is_single_entity="+(new Boolean(is_single_entity)).toString()+"\n";
        output = output + indent + "is_entity_instantiations="+(new Boolean(is_entity_instantiations)).toString()+"\n";
        for (int i=0; i<functions.size(); i++)
            output = output + indent + "function: "+((Function)functions.elementAt(i)).writeFunction()+"\n";
        if (isMultiple())
        {
            indent = indent + "   ";
            for (int i=0; i<previous_specifications.size(); i++)
            {
                Specification spec = (Specification)previous_specifications.elementAt(i);
                output = output + "\n" + spec.fullDetails(indent);
            }
        }
        return output;
    }

    /** Which columns have been used at the base (i.e. not used via the multiple
     * specifications).
     */

    public Vector columns_used_at_base()
    {
        if (!isMultiple())
            return permutation;
        Vector output = new Vector();
        for (int i=0; i<previous_specifications.size(); i++)
        {
            Specification spec = (Specification)previous_specifications.elementAt(i);
            Vector old_columns = spec.columns_used_at_base();
            for (int j=0; j<old_columns.size(); j++)
                if (!output.contains((String)old_columns.elementAt(j)))
                    output.addElement((String)old_columns.elementAt(j));
        }
        return output;
    }

    public Vector skolemisedRepresentation()
    {
        Vector output = new Vector();
        if (type.equals("forall") || type.equals("negate") || type.equals("size") || type.equals("disjunct"))
        {
            String rel_name = Integer.toString(id_number);
            Vector skol_rep = new Vector();
            skol_rep.addElement(rel_name);
            skol_rep.addElement((Vector)permutation.clone());
            skol_rep.trimToSize();
            output.addElement(skol_rep);
        }
        if (type.equals("exists") || type.equals("split"))
        {
            for (int i=0; i<previous_specifications.size(); i++)
            {
                Specification prev_spec = (Specification)previous_specifications.elementAt(i);
                Vector prev_skol_reps = prev_spec.skolemisedRepresentation();
                for (int j=0; j<prev_skol_reps.size(); j++)
                {
                    Vector prev_skol_rep = (Vector)prev_skol_reps.elementAt(j);
                    String rel_name = (String)prev_skol_rep.elementAt(0);
                    Vector cols = (Vector)prev_skol_rep.elementAt(1);
                    Vector new_perm = (Vector)permutation.clone();
                    for (int k=0; k<multiple_variable_columns.size(); k++)
                    {
                        String change_string = "!" + Integer.toString(id_number) + "_" + Integer.toString(k) + "!";
                        if (type.equals("split"))
                            change_string = ":" + (String)fixed_values.elementAt(k) + ":";
                        int pos = (new Integer((String)multiple_variable_columns.elementAt(k))).intValue();
                        if (pos>=new_perm.size())
                            new_perm.addElement(change_string);
                        else
                            new_perm.insertElementAt(change_string, pos);
                    }
                    for (int k=0; k<redundant_columns.size(); k++)
                    {
                        int pos = (new Integer((String)redundant_columns.elementAt(k))).intValue();
                        if (pos>=new_perm.size())
                            new_perm.addElement("@r@");
                        else
                            new_perm.insertElementAt("@r@", pos);
                    }
                    for (int k=0; k<cols.size(); k++)
                    {
                        String pos_string = (String)cols.elementAt(k);
                        if (!pos_string.substring(0,1).equals(":") &&
                                !pos_string.substring(0,1).equals("!"))
                        {
                            int pos = (new Integer(pos_string)).intValue();
                            cols.setElementAt(new_perm.elementAt(pos),k);
                        }
                    }
                    output.addElement(prev_skol_rep);
                }
            }
        }
        if (relations.isEmpty())
            return output;
        Relation rel = (Relation)relations.elementAt(0);
        if (type.equals(""))
        {
            Vector skol_rep = new Vector();
            skol_rep.addElement(rel.name);
            skol_rep.addElement((Vector)permutation.clone());
            skol_rep.trimToSize();
            output.addElement(skol_rep);
        }
        output.trimToSize();
        return output;
    }
}
