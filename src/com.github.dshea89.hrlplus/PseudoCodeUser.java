package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;
import javax.swing.JOptionPane;

/** A superclass for objects which use a pseudo-code handler object to
 * function (reactions, reports, etc.)
 *
 * @author Simon Colton, started 28th June 2002
 * @version 1.0 */

public class PseudoCodeUser implements Serializable
{
    /** The hashtable of original aliases.
     */

    public Hashtable original_alias_hashtable = new Hashtable();

    /** The pseudo-code text which this reaction executes.
     */

    public Vector pseudo_code_lines = new Vector();

    /** The identifier of this pseudo-code user.
     */

    public String id = "";

    /** The pseudo-code interpreter for this reaction.
     */

    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();

    /** This goes through the pseudo_code_lines and changes any occurrences of
     * $1, $2, etc., with the values in the given vector.
     */

    public void changeCommandLineArguments(Vector command_line_values)
    {
        Hashtable already_specified_vars = new Hashtable();
        for (int i=0; i<pseudo_code_lines.size(); i++)
        {
            String pseudo_code_line = (String)pseudo_code_lines.elementAt(i);
            for (int j=0; j<10; j++)
            {
                String dollar = "$" + Integer.toString(j);
                int ind = pseudo_code_line.indexOf(dollar);
                while (ind>=0)
                {
                    String command_line_value = getCommandLineValue(command_line_values, j, already_specified_vars);
                    pseudo_code_line = pseudo_code_line.substring(0, ind) + command_line_value +
                            pseudo_code_line.substring(ind + dollar.length(), pseudo_code_line.length());
                    ind = pseudo_code_line.indexOf(dollar);
                }
                pseudo_code_lines.setElementAt(pseudo_code_line, i);
            }
        }
    }

    public String getCommandLineValue(Vector vals, int pos, Hashtable already_specified_vars)
    {
        if (pos < vals.size())
            return (String)vals.elementAt(pos);
        String int_string = Integer.toString(pos);

        String output = (String)already_specified_vars.get(int_string);
        if (output!=null)
            return output;
        output = JOptionPane.showInputDialog("Please input a value for $" + Integer.toString(pos));
        already_specified_vars.put(int_string, output);
        return output;
    }

    /** This takes the given string and adds each line into the pseudo_code_lines
     * vector.
     */

    public void getCodeLines(String pseudo_code)
    {
        pseudo_code = pseudo_code.trim()+"\n";
        int i=0;
        while (pseudo_code.indexOf("\n")>=0)
        {
            String pseudo_code_line = pseudo_code.substring(0, pseudo_code.indexOf("\n"));
            pseudo_code_lines.addElement(pseudo_code_line);
            pseudo_code = pseudo_code.substring(pseudo_code.indexOf("\n")+1, pseudo_code.length());
        }
    }
}
