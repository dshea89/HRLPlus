package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Relation implements Serializable {
    public Vector function_columns = new Vector();
    public Vector definitions = new Vector();
    public String name = "";
    public boolean is_object_of_interest = false;

    public Relation() {
    }

    public boolean equals(Relation var1) {
        return var1.name.equals(this.name);
    }

    public Relation(Vector var1) {
        if (!var1.isEmpty()) {
            for(int var2 = 0; var2 < var1.size(); ++var2) {
                String var3 = (String)var1.elementAt(var2);
                this.addFunctionColumns(var3);
            }
        }

    }

    public void addDefinition(String var1, String var2) {
        Definition var3 = new Definition(var1, var2);
        this.definitions.addElement(var3);
    }

    public void addDefinition(String var1, String var2, String var3) {
        Definition var4 = new Definition(var1, var2, var3);
        this.definitions.addElement(var4);
    }

    public Definition getDefinition(String var1) {
        for(int var2 = 0; var2 < this.definitions.size(); ++var2) {
            Definition var3 = (Definition)this.definitions.elementAt(var2);
            if (var3.language.equals(var1)) {
                return var3;
            }
        }

        if (this.definitions.size() > 0) {
            return (Definition)this.definitions.elementAt(0);
        } else {
            return new Definition("", var1);
        }
    }

    public void addFunctionColumns(String var1) {
        Vector var2 = new Vector();
        Vector var3 = new Vector();
        int var4 = 0;
        String var5 = "";

        Vector var6;
        for(var6 = var2; var4 < var1.length(); ++var4) {
            String var7 = var1.substring(var4, var4 + 1);
            if (var7.equals(",")) {
                var6.addElement(var5);
                var5 = "";
            }

            if (var7.equals("=")) {
                var6.addElement(var5);
                var5 = "";
                var6 = var3;
            }

            if (!var7.equals("=") && !var7.equals(",")) {
                var5 = var5 + var7;
            }
        }

        var6.addElement(var5);
        Vector var8 = new Vector();
        var8.addElement(var2);
        var8.addElement(var3);
        this.function_columns.addElement(var8);
    }
}
