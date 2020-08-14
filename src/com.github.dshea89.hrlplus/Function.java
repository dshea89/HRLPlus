package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class for objects depicting how a specification can be thought of as a function.
 *
 * @see Specification
 * @author Simon Colton, started 3rd January 2000
 * @version 1.0 */

public class Function implements Serializable
{
    /** Whether or not this function is negated
     */

    public boolean is_negated = false;

    /** The name of this function.
     */

    public String name = "";

    /** The set of input column numbers which never get instantiated. They may get
     * altered by the compose rule, though.
     */

    public Vector original_input_columns = new Vector();

    /** The set of output column numbers which never get instantiated. They may get
     * altered by the compose rule, though.
     */

    public Vector original_output_columns = new Vector();

    /** The set of input column numbers. These get instantiated.
     */

    public Vector input_columns = new Vector();

    /** The set of output column numbers. These get instantiated.
     */

    public Vector output_columns = new Vector();

    /** The simple constructor
     */

    public Function()
    {
    }

    /** Builds a new Function with the given input and output columns.
     */

    public Function(String given_name, Vector i_columns, Vector o_columns)
    {
        name = given_name;
        input_columns = i_columns;
        output_columns = o_columns;
        original_input_columns = (Vector)i_columns.clone();
        original_output_columns = (Vector)o_columns.clone();
    }

    public Function copy()
    {
        Function output = new Function(name, (Vector)input_columns.clone(), (Vector)output_columns.clone());
        output.original_input_columns = original_input_columns;
        output.original_output_columns = original_output_columns;
        output.name = name;
        output.is_negated = is_negated;
        return output;
    }

    /** This writes the function like this [i1,v2,i3]=[o1,o2] where the i's are the
     * input columns and the o's are the output columns and the v's are the values which
     * have already been instantiated.
     */

    public String writeFunction()
    {
        String output = name+"(";
        for (int i=0; i<input_columns.size(); i++)
        {
            String ic = (String)input_columns.elementAt(i);
            output = output + ic;
            if (!(i==input_columns.size()-1))
                output = output + ",";
        }
        output=output+")";
        if (is_negated)
            output = output + "!";
        output = output + "=";
        if (output_columns.size()>1)
            output = output + "(";
        for (int i=0; i<output_columns.size(); i++)
        {
            String oc = (String)output_columns.elementAt(i);
            output = output + oc;
            if (!(i==output_columns.size()-1))
                output = output + ",";
        }
        if (output_columns.size()>1)
            output = output + ")";
        return output;
    }

    /** Given the parameters as given to the compose rule, this changes the input and output
     * columns in this function with respect to those parameters.
     */

    public void permute(Vector permutation)
    {
        Vector new_input_columns = (Vector)input_columns.clone();
        Vector new_output_columns = (Vector)output_columns.clone();
        Vector new_original_input_columns = (Vector)original_input_columns.clone();
        Vector new_original_output_columns = (Vector)original_output_columns.clone();
        for (int i=0; i<permutation.size(); i++)
        {
            String place = (String)permutation.elementAt(i);
            if (!place.equals("-1"))
            {
                for (int j=0; j<input_columns.size(); j++)
                {
                    String input_column = (String)input_columns.elementAt(j);
                    if (input_column.equals(place))
                        new_input_columns.setElementAt(Integer.toString(i),j);
                }
                for (int j=0; j<output_columns.size(); j++)
                {
                    String output_column = (String)output_columns.elementAt(j);
                    if (output_column.equals(place))
                        new_output_columns.setElementAt(Integer.toString(i),j);
                }
                for (int j=0; j<original_input_columns.size(); j++)
                {
                    String original_input_column = (String)original_input_columns.elementAt(j);
                    if (original_input_column.equals(place))
                        new_original_input_columns.setElementAt(Integer.toString(i),j);
                }
                for (int j=0; j<original_output_columns.size(); j++)
                {
                    String original_output_column = (String)original_output_columns.elementAt(j);
                    if (original_output_column.equals(place))
                        new_original_output_columns.setElementAt(Integer.toString(i),j);
                }
            }
        }
        input_columns = new_input_columns;
        output_columns = new_output_columns;
        original_input_columns = new_original_input_columns;
        original_output_columns = new_original_output_columns;
    }

    /** This permutes the input and output function with respect to the parameters
     * from a match production rule step.
     */

    public void matchPermute(Vector old_permutation)
    {
        Vector new_input_columns = (Vector)input_columns.clone();
        Vector new_output_columns = (Vector)output_columns.clone();
        Vector new_original_input_columns = (Vector)original_input_columns.clone();
        Vector new_original_output_columns = (Vector)original_output_columns.clone();
        Vector permutation = (Vector)old_permutation.clone();
        Vector moved_columns = new Vector();
        for (int i=0; i<permutation.size(); i++)
        {
            String column = (String)permutation.elementAt(i);
            String pos = Integer.toString(i);
            int ppos = (new Integer(pos)).intValue();
            int pcol = (new Integer(column)).intValue();
            int take_off = 0;
            if (!column.equals(pos))
                moved_columns.addElement(pos);
            else
            {
                for (int j=0; j<moved_columns.size(); j++)
                {
                    String moved = (String)moved_columns.elementAt(j);
                    int mpos = (new Integer(moved)).intValue();
                    if (mpos < ppos)
                        take_off++;
                }
            }
            String final_column = Integer.toString(pcol - take_off);
            permutation.setElementAt(final_column,i);
        }

        for (int i=0; i<permutation.size(); i++)
        {
            String place = Integer.toString(i);
            String move_to = (String)permutation.elementAt(i);
            for (int j=0; j<input_columns.size(); j++)
            {
                String input_column = (String)input_columns.elementAt(j);
                if (input_column.equals(place))
                    new_input_columns.setElementAt(move_to,j);
            }
            for (int j=0; j<output_columns.size(); j++)
            {
                String output_column = (String)output_columns.elementAt(j);
                if (output_column.equals(place))
                    new_output_columns.setElementAt(move_to,j);
            }
            for (int j=0; j<original_input_columns.size(); j++)
            {
                String original_input_column = (String)original_input_columns.elementAt(j);
                if (original_input_column.equals(place))
                    new_original_input_columns.setElementAt(move_to,j);
            }
            for (int j=0; j<original_output_columns.size(); j++)
            {
                String original_output_column = (String)original_output_columns.elementAt(j);
                if (original_output_column.equals(place))
                    new_original_output_columns.setElementAt(move_to,j);
            }
        }

        input_columns = new_input_columns;
        output_columns = new_output_columns;
        original_input_columns = new_original_input_columns;
        original_output_columns = new_original_output_columns;
        int i=0;
        Vector temp_vector = new Vector();
        while (i<input_columns.size())
        {
            String input_column = (String)input_columns.elementAt(i);
            if (temp_vector.contains(input_column))
                input_columns.removeElementAt(i);
            else
            {
                temp_vector.addElement(input_column);
                i++;
            }
        }
        temp_vector = new Vector();
        while (i<original_input_columns.size())
        {
            String input_column = (String)original_input_columns.elementAt(i);
            if (temp_vector.contains(input_column))
                original_input_columns.removeElementAt(i);
            else
            {
                temp_vector.addElement(input_column);
                i++;
            }
        }
    }

    /** This checks whether this function is in conflict with the given function.
     * To be in conflict, they must have the same input columns, but different (instantiated)
     * output columns.
     */

    public boolean hasConflictWith(Function other_function)
    {
        if (!(name.equals(other_function.name)))
            return false;

        if (!(input_columns.toString().equals(other_function.input_columns.toString())))
            return false;

        if (!(output_columns.size()==other_function.output_columns.size()))
            return false;

        int i=0;
        boolean output = false;
        while (!output && i<output_columns.size())
        {
            String this_output_column = (String)output_columns.elementAt(i);
            String that_output_column = (String)other_function.output_columns.elementAt(i);
            if (this_output_column.substring(0,1).equals(":") &&
                    that_output_column.substring(0,1).equals(":") &&
                    !(this_output_column.toString().equals(that_output_column.toString())))
                output = true;
            i++;
        }
        return output;
    }

    /** This checks whether any of the given columns appear in the input or output columns
     * of this function.
     */

    public boolean containsAColumnFrom(Vector columns)
    {
        boolean output = false;
        int i=0;
        while (!output && i<columns.size())
        {
            String column = (String)columns.elementAt(i);
            if (input_columns.contains(column) || output_columns.contains(column))
                output = true;
            i++;
        }
        return output;
    }

    /** This checks whether any of the given columns appear in the output columns
     * of this function.
     */

    public boolean containsAnOutputColumnFrom(Vector columns)
    {
        boolean output = false;
        int i=0;
        while (!output && i<columns.size())
        {
            String column = (String)columns.elementAt(i);
            if (output_columns.contains(column))
                output = true;
            i++;
        }
        return output;
    }

    /** This checks whether any of the given columns appear in the input columns
     * of this function.
     */

    public boolean containsAnInputColumnFrom(Vector columns)
    {
        boolean output = false;
        int i=0;
        while (!output && i<columns.size())
        {
            String column = (String)columns.elementAt(i);
            if (input_columns.contains(column))
                output = true;
            i++;
        }
        return output;
    }

    /** This adjusts the input and output columns to reflect the removal of
     * the given columns from the parent concept.
     */

    public void removeHoles(Vector columns)
    {
        input_columns = holeRemoved(input_columns, columns);
        original_input_columns = holeRemoved(original_input_columns, columns);
        output_columns = holeRemoved(output_columns, columns);
        original_output_columns = holeRemoved(original_output_columns, columns);
    }

    private Vector holeRemoved(Vector input_columns, Vector columns)
    {
        Vector output = new Vector();
        for (int i=0;i<input_columns.size();i++)
        {
            String input_column = (String)input_columns.elementAt(i);
            if (!input_column.substring(0,1).equals(":"))
            {
                int take_off = 0;
                int input_pos = (new Integer(input_column)).intValue();
                for (int j=0; j<columns.size(); j++)
                {
                    String column = (String)columns.elementAt(j);
                    int column_pos = (new Integer(column)).intValue();
                    if (column_pos<input_pos)
                        take_off++;
                }
                String new_input_column = Integer.toString(input_pos-take_off);
                output.addElement(new_input_column);
            }
            else
                output.addElement(input_column);
        }
        return output;
    }

    /** This checks whether an output from this function
     * is actually also an input to the function.
     */

    public boolean outputAsInput()
    {
        boolean output = false;
        for (int i=0; i<output_columns.size() && !output; i++)
        {
            String output_column = (String)output_columns.elementAt(i);
            if (input_columns.contains(output_column))
                output=true;
        }
        return output;
    }
}
