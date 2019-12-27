package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * A superclass for objects which use a pseudo-code handler object to function (reactions, reports, etc.)
 */
public class PseudoCodeUser implements Serializable {
    /**
     * The hashtable of original aliases.
     */
    public Hashtable original_alias_hashtable = new Hashtable();

    /**
     * The pseudo-code text which this reaction executes.
     */
    public Vector pseudo_code_lines = new Vector();

    /**
     * The identifier of this pseudo-code user.
     */
    public String id = "";

    /**
     * The pseudo-code interpreter for this reaction.
     */
    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();

    public PseudoCodeUser() {
    }

    /**
     * This goes through the pseudo_code_lines and changes any occurrences of $1, $2, etc., with the values in the given vector.
     */
    public void changeCommandLineArguments(Vector command_line_values) {
        Hashtable var2 = new Hashtable();

        for(int var3 = 0; var3 < this.pseudo_code_lines.size(); ++var3) {
            String var4 = (String)this.pseudo_code_lines.elementAt(var3);

            for(int var5 = 0; var5 < 10; ++var5) {
                String var6 = "$" + Integer.toString(var5);

                for(int var7 = var4.indexOf(var6); var7 >= 0; var7 = var4.indexOf(var6)) {
                    String var8 = this.getCommandLineValue(command_line_values, var5, var2);
                    var4 = var4.substring(0, var7) + var8 + var4.substring(var7 + var6.length(), var4.length());
                }

                this.pseudo_code_lines.setElementAt(var4, var3);
            }
        }

    }

    public String getCommandLineValue(Vector vals, int pos, Hashtable already_specified_vars) {
        if (pos < vals.size()) {
            return (String)vals.elementAt(pos);
        } else {
            String var4 = Integer.toString(pos);
            String var5 = (String)already_specified_vars.get(var4);
            if (var5 != null) {
                return var5;
            } else {
                var5 = JOptionPane.showInputDialog("Please input a value for $" + Integer.toString(pos));
                already_specified_vars.put(var4, var5);
                return var5;
            }
        }
    }

    /**
     * This takes the given string and adds each line into the pseudo_code_lines vector.
     */
    public void getCodeLines(String pseudo_code) {
        pseudo_code = pseudo_code.trim() + "\n";

        for(boolean var2 = false; pseudo_code.indexOf("\n") >= 0; pseudo_code = pseudo_code.substring(pseudo_code.indexOf("\n") + 1, pseudo_code.length())) {
            String var3 = pseudo_code.substring(0, pseudo_code.indexOf("\n"));
            this.pseudo_code_lines.addElement(var3);
        }

    }
}
