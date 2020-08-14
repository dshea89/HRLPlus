package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/**
 * A class representing a relation between objects and subobjects in the theory.
 *
 * @author Simon Colton, started 11th December 1999.
 * @version 1.0
 * @see Concept
 * @see Definition
 */

public class Relation implements Serializable
{
    /** The set of function columns for this relation. These are pairs of vectors of
     * function columns such as [0,1],[2] which says that 0,1 variables are input and
     * variable 2 is output.
     */

    public Vector function_columns = new Vector();

    /** The set of definitions which are associated with this relation. The definitions
     * may be different ways of writing the same relation in the same language, or may
     * come from different languages, such as ASCII and LaTeX.
     * @see Definition
     */

    public Vector definitions = new Vector();

    /** The name of this relation.
     */

    public String name = "";

    /** Indicates whether the relation represents the objects of interest, such as graphs,
     * integers, groups, etc. Default is false.
     */

    public boolean is_object_of_interest = false;

    /** Creates a new relation.
     */

    public Relation()
    {
    }

    /** Checks whether the given relation equals this one.
     */

    public boolean equals(Relation other)
    {
        if (other.name.equals(name))
            return true;
        return false;
    }

    /** Creates a new relation with the given columns as possible output
     * columns if viewed as a function.
     */

    public Relation(Vector function_columns)
    {
        if (!function_columns.isEmpty())
        {
            for (int i=0; i<function_columns.size(); i++)
            {
                String function_column_string = (String)function_columns.elementAt(i);
                addFunctionColumns(function_column_string);
            }
        }
    }

    /** Adds a definition to the list_of_definitions.
     * see Definition
     */

    public void addDefinition(String input_text, String language)
    {
        Definition definition = new Definition(input_text, language);
        definitions.addElement(definition);
    }

    /** Adds a definition to the list_of_definitions.
     * see Definition
     */

    public void addDefinition(String letters, String input_text, String language)
    {
        Definition definition = new Definition(letters, input_text, language);
        definitions.addElement(definition);
    }

    /** Returns the first definition of the given language for this relation, or an empty
     * definition if there  are no such definitions.
     */

    public Definition getDefinition(String language)
    {
        for (int i=0; i<definitions.size(); i++)
        {
            Definition definition = (Definition)definitions.elementAt(i);
            if (definition.language.equals(language))
                return definition;
        }
        if (definitions.size()>0)
            return (Definition)definitions.elementAt(0);
        return (new Definition("",language));
    }

    /** This takes strings of the form "x,y,z-a,b,c" and says that this concept can
     * be thought of as a function with input columns xyz and output columns abc.
     */

    public void addFunctionColumns(String s)
    {
        Vector input_columns = new Vector();
        Vector output_columns = new Vector();
        int pos = 0;
        String num = "";
        Vector add_vector = input_columns;
        while (pos < s.length())
        {
            String add_bit = s.substring(pos,pos+1);
            if (add_bit.equals(","))
            {
                add_vector.addElement(num);
                num="";
            }
            if (add_bit.equals("="))
            {
                add_vector.addElement(num);
                num="";
                add_vector = output_columns;
            }
            if (!add_bit.equals("=") && !add_bit.equals(","))
                num=num+add_bit;
            pos++;
        }
        add_vector.addElement(num);
        Vector fc = new Vector();
        fc.addElement(input_columns);
        fc.addElement(output_columns);
        function_columns.addElement(fc);
    }
}
