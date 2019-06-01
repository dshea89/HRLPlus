package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;

public class PseudoCodeUser implements Serializable {
    public Hashtable original_alias_hashtable = new Hashtable();
    public Vector pseudo_code_lines = new Vector();
    public String id = "";
    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();

    public PseudoCodeUser() {
    }

    public void changeCommandLineArguments(Vector var1) {
        Hashtable var2 = new Hashtable();

        for(int var3 = 0; var3 < this.pseudo_code_lines.size(); ++var3) {
            String var4 = (String)this.pseudo_code_lines.elementAt(var3);

            for(int var5 = 0; var5 < 10; ++var5) {
                String var6 = "$" + Integer.toString(var5);

                for(int var7 = var4.indexOf(var6); var7 >= 0; var7 = var4.indexOf(var6)) {
                    String var8 = this.getCommandLineValue(var1, var5, var2);
                    var4 = var4.substring(0, var7) + var8 + var4.substring(var7 + var6.length(), var4.length());
                }

                this.pseudo_code_lines.setElementAt(var4, var3);
            }
        }

    }

    public String getCommandLineValue(Vector var1, int var2, Hashtable var3) {
        if (var2 < var1.size()) {
            return (String)var1.elementAt(var2);
        } else {
            String var4 = Integer.toString(var2);
            String var5 = (String)var3.get(var4);
            if (var5 != null) {
                return var5;
            } else {
                var5 = JOptionPane.showInputDialog("Please input a value for $" + Integer.toString(var2));
                var3.put(var4, var5);
                return var5;
            }
        }
    }

    public void getCodeLines(String var1) {
        var1 = var1.trim() + "\n";

        for(boolean var2 = false; var1.indexOf("\n") >= 0; var1 = var1.substring(var1.indexOf("\n") + 1, var1.length())) {
            String var3 = var1.substring(0, var1.indexOf("\n"));
            this.pseudo_code_lines.addElement(var3);
        }

    }
}
