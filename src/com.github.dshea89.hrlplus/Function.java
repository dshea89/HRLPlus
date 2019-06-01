package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Function implements Serializable {
    public boolean is_negated = false;
    public String name = "";
    public Vector original_input_columns = new Vector();
    public Vector original_output_columns = new Vector();
    public Vector input_columns = new Vector();
    public Vector output_columns = new Vector();

    public Function() {
    }

    public Function(String var1, Vector var2, Vector var3) {
        this.name = var1;
        this.input_columns = var2;
        this.output_columns = var3;
        this.original_input_columns = (Vector)var2.clone();
        this.original_output_columns = (Vector)var3.clone();
    }

    public Function copy() {
        Function var1 = new Function(this.name, (Vector)this.input_columns.clone(), (Vector)this.output_columns.clone());
        var1.original_input_columns = this.original_input_columns;
        var1.original_output_columns = this.original_output_columns;
        var1.name = this.name;
        var1.is_negated = this.is_negated;
        return var1;
    }

    public String writeFunction() {
        String var1 = this.name + "(";

        int var2;
        String var3;
        for(var2 = 0; var2 < this.input_columns.size(); ++var2) {
            var3 = (String)this.input_columns.elementAt(var2);
            var1 = var1 + var3;
            if (var2 != this.input_columns.size() - 1) {
                var1 = var1 + ",";
            }
        }

        var1 = var1 + ")";
        if (this.is_negated) {
            var1 = var1 + "!";
        }

        var1 = var1 + "=";
        if (this.output_columns.size() > 1) {
            var1 = var1 + "(";
        }

        for(var2 = 0; var2 < this.output_columns.size(); ++var2) {
            var3 = (String)this.output_columns.elementAt(var2);
            var1 = var1 + var3;
            if (var2 != this.output_columns.size() - 1) {
                var1 = var1 + ",";
            }
        }

        if (this.output_columns.size() > 1) {
            var1 = var1 + ")";
        }

        return var1;
    }

    public void permute(Vector var1) {
        Vector var2 = (Vector)this.input_columns.clone();
        Vector var3 = (Vector)this.output_columns.clone();
        Vector var4 = (Vector)this.original_input_columns.clone();
        Vector var5 = (Vector)this.original_output_columns.clone();

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            String var7 = (String)var1.elementAt(var6);
            if (!var7.equals("-1")) {
                int var8;
                String var9;
                for(var8 = 0; var8 < this.input_columns.size(); ++var8) {
                    var9 = (String)this.input_columns.elementAt(var8);
                    if (var9.equals(var7)) {
                        var2.setElementAt(Integer.toString(var6), var8);
                    }
                }

                for(var8 = 0; var8 < this.output_columns.size(); ++var8) {
                    var9 = (String)this.output_columns.elementAt(var8);
                    if (var9.equals(var7)) {
                        var3.setElementAt(Integer.toString(var6), var8);
                    }
                }

                for(var8 = 0; var8 < this.original_input_columns.size(); ++var8) {
                    var9 = (String)this.original_input_columns.elementAt(var8);
                    if (var9.equals(var7)) {
                        var4.setElementAt(Integer.toString(var6), var8);
                    }
                }

                for(var8 = 0; var8 < this.original_output_columns.size(); ++var8) {
                    var9 = (String)this.original_output_columns.elementAt(var8);
                    if (var9.equals(var7)) {
                        var5.setElementAt(Integer.toString(var6), var8);
                    }
                }
            }
        }

        this.input_columns = var2;
        this.output_columns = var3;
        this.original_input_columns = var4;
        this.original_output_columns = var5;
    }

    public void matchPermute(Vector var1) {
        Vector var2 = (Vector)this.input_columns.clone();
        Vector var3 = (Vector)this.output_columns.clone();
        Vector var4 = (Vector)this.original_input_columns.clone();
        Vector var5 = (Vector)this.original_output_columns.clone();
        Vector var6 = (Vector)var1.clone();
        Vector var7 = new Vector();

        int var8;
        String var9;
        String var10;
        int var11;
        for(var8 = 0; var8 < var6.size(); ++var8) {
            var9 = (String)var6.elementAt(var8);
            var10 = Integer.toString(var8);
            var11 = new Integer(var10);
            int var12 = new Integer(var9);
            int var13 = 0;
            if (!var9.equals(var10)) {
                var7.addElement(var10);
            } else {
                for(int var14 = 0; var14 < var7.size(); ++var14) {
                    String var15 = (String)var7.elementAt(var14);
                    int var16 = new Integer(var15);
                    if (var16 < var11) {
                        ++var13;
                    }
                }
            }

            String var19 = Integer.toString(var12 - var13);
            var6.setElementAt(var19, var8);
        }

        for(var8 = 0; var8 < var6.size(); ++var8) {
            var9 = Integer.toString(var8);
            var10 = (String)var6.elementAt(var8);

            String var18;
            for(var11 = 0; var11 < this.input_columns.size(); ++var11) {
                var18 = (String)this.input_columns.elementAt(var11);
                if (var18.equals(var9)) {
                    var2.setElementAt(var10, var11);
                }
            }

            for(var11 = 0; var11 < this.output_columns.size(); ++var11) {
                var18 = (String)this.output_columns.elementAt(var11);
                if (var18.equals(var9)) {
                    var3.setElementAt(var10, var11);
                }
            }

            for(var11 = 0; var11 < this.original_input_columns.size(); ++var11) {
                var18 = (String)this.original_input_columns.elementAt(var11);
                if (var18.equals(var9)) {
                    var4.setElementAt(var10, var11);
                }
            }

            for(var11 = 0; var11 < this.original_output_columns.size(); ++var11) {
                var18 = (String)this.original_output_columns.elementAt(var11);
                if (var18.equals(var9)) {
                    var5.setElementAt(var10, var11);
                }
            }
        }

        this.input_columns = var2;
        this.output_columns = var3;
        this.original_input_columns = var4;
        this.original_output_columns = var5;
        var8 = 0;
        Vector var17 = new Vector();

        while(var8 < this.input_columns.size()) {
            var10 = (String)this.input_columns.elementAt(var8);
            if (var17.contains(var10)) {
                this.input_columns.removeElementAt(var8);
            } else {
                var17.addElement(var10);
                ++var8;
            }
        }

        var17 = new Vector();

        while(var8 < this.original_input_columns.size()) {
            var10 = (String)this.original_input_columns.elementAt(var8);
            if (var17.contains(var10)) {
                this.original_input_columns.removeElementAt(var8);
            } else {
                var17.addElement(var10);
                ++var8;
            }
        }

    }

    public boolean hasConflictWith(Function var1) {
        if (!this.name.equals(var1.name)) {
            return false;
        } else if (!this.input_columns.toString().equals(var1.input_columns.toString())) {
            return false;
        } else if (this.output_columns.size() != var1.output_columns.size()) {
            return false;
        } else {
            int var2 = 0;

            boolean var3;
            for(var3 = false; !var3 && var2 < this.output_columns.size(); ++var2) {
                String var4 = (String)this.output_columns.elementAt(var2);
                String var5 = (String)var1.output_columns.elementAt(var2);
                if (var4.substring(0, 1).equals(":") && var5.substring(0, 1).equals(":") && !var4.toString().equals(var5.toString())) {
                    var3 = true;
                }
            }

            return var3;
        }
    }

    public boolean containsAColumnFrom(Vector var1) {
        boolean var2 = false;

        for(int var3 = 0; !var2 && var3 < var1.size(); ++var3) {
            String var4 = (String)var1.elementAt(var3);
            if (this.input_columns.contains(var4) || this.output_columns.contains(var4)) {
                var2 = true;
            }
        }

        return var2;
    }

    public boolean containsAnOutputColumnFrom(Vector var1) {
        boolean var2 = false;

        for(int var3 = 0; !var2 && var3 < var1.size(); ++var3) {
            String var4 = (String)var1.elementAt(var3);
            if (this.output_columns.contains(var4)) {
                var2 = true;
            }
        }

        return var2;
    }

    public boolean containsAnInputColumnFrom(Vector var1) {
        boolean var2 = false;

        for(int var3 = 0; !var2 && var3 < var1.size(); ++var3) {
            String var4 = (String)var1.elementAt(var3);
            if (this.input_columns.contains(var4)) {
                var2 = true;
            }
        }

        return var2;
    }

    public void removeHoles(Vector var1) {
        this.input_columns = this.holeRemoved(this.input_columns, var1);
        this.original_input_columns = this.holeRemoved(this.original_input_columns, var1);
        this.output_columns = this.holeRemoved(this.output_columns, var1);
        this.original_output_columns = this.holeRemoved(this.original_output_columns, var1);
    }

    private Vector holeRemoved(Vector var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            String var5 = (String)var1.elementAt(var4);
            if (var5.substring(0, 1).equals(":")) {
                var3.addElement(var5);
            } else {
                int var6 = 0;
                int var7 = new Integer(var5);

                for(int var8 = 0; var8 < var2.size(); ++var8) {
                    String var9 = (String)var2.elementAt(var8);
                    int var10 = new Integer(var9);
                    if (var10 < var7) {
                        ++var6;
                    }
                }

                String var11 = Integer.toString(var7 - var6);
                var3.addElement(var11);
            }
        }

        return var3;
    }

    public boolean outputAsInput() {
        boolean var1 = false;

        for(int var2 = 0; var2 < this.output_columns.size() && !var1; ++var2) {
            String var3 = (String)this.output_columns.elementAt(var2);
            if (this.input_columns.contains(var3)) {
                var1 = true;
            }
        }

        return var1;
    }
}
