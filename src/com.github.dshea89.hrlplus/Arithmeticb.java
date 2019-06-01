package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Arithmeticb extends ProductionRule implements Serializable {
    public boolean use_same_concept_twice = false;
    public Exists exists = new Exists();
    public Compose compose = new Compose();
    public String operators_allowed = "+*d";
    public boolean is_cumulative = false;

    public Arithmeticb() {
    }

    public boolean isBinary() {
        return true;
    }

    public String getName() {
        return "arithmeticb";
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Concept var3 = (Concept)var1.elementAt(0);
        Concept var4 = (Concept)var1.elementAt(1);
        if (var3 == var4 && !this.use_same_concept_twice) {
            return new Vector();
        } else if (var3.arity == 2 && var4.arity == 2) {
            if (var3.types.elementAt(1).equals("integer") && var4.types.elementAt(1).equals("integer")) {
                int var5;
                for(var5 = 0; var5 < var3.datatable.size(); ++var5) {
                    if (((Row)var3.datatable.elementAt(var5)).tuples.isEmpty()) {
                        return new Vector();
                    }
                }

                for(var5 = 0; var5 < var4.datatable.size(); ++var5) {
                    if (((Row)var4.datatable.elementAt(var5)).tuples.isEmpty()) {
                        return new Vector();
                    }
                }

                boolean var12 = true;
                boolean var6 = false;
                boolean var7 = false;
                Vector var8 = new Vector();

                int var9;
                Function var10;
                for(var9 = 0; var9 < var3.functions.size(); ++var9) {
                    var10 = (Function)var3.functions.elementAt(var9);
                    if (var10.output_columns.toString().equals("[1]")) {
                        var6 = true;
                    }
                }

                for(var9 = 0; var9 < var4.functions.size(); ++var9) {
                    var10 = (Function)var4.functions.elementAt(var9);
                    if (var10.output_columns.toString().equals("[1]")) {
                        var7 = true;
                    }
                }

                if (var6 && var7) {
                    var12 = false;
                }

                if (var3.arity != 2 || !var3.types.elementAt(1).equals("integer") || var4.arity != 2 || !var4.types.elementAt(1).equals("integer")) {
                    var12 = true;
                }

                if (!var12) {
                    for(var9 = 0; var9 < this.operators_allowed.length(); ++var9) {
                        String var13 = this.operators_allowed.substring(var9, var9 + 1);
                        Vector var11 = new Vector();
                        var11.addElement(var13);
                        var8.addElement(var11);
                    }
                }

                return var8;
            } else {
                return new Vector();
            }
        } else {
            return new Vector();
        }
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        String var5 = (String)var2.elementAt(0);
        Concept var6 = (Concept)var1.elementAt(0);
        Concept var7 = (Concept)var1.elementAt(1);
        Vector var8 = new Vector();
        Vector var9 = new Vector();
        var8.addElement(var6);
        var9.addElement(var7);
        Vector var10 = new Vector();
        var10.addElement("1");
        var10.addElement("0");
        var10.addElement("2");
        Vector var11 = this.compose.newSpecifications(var1, var10, var3, new Vector());
        Relation var12 = new Relation();
        Vector var13 = new Vector();
        var13.addElement("1");
        var13.addElement("2");
        var13.addElement("3");
        if (!var5.equals("d")) {
            var12.addDefinition("abc", "@c@=@a@" + var5 + "@b@", "ascii");
        } else {
            var12.addDefinition("abc", "@c@=(f" + var6.id + ",f" + var7.id + ")(@a@,@b@)", "ascii");
        }

        var12.name = var5 + var2.toString();
        Specification var14 = new Specification();
        Vector var15 = new Vector();
        var15.addElement("0");
        var15.addElement("1");
        var15.addElement("2");
        var14.addRelation(var15, var12);
        var14.permutation = (Vector)var13.clone();
        var11.addElement(var14);
        Concept var16 = new Concept();
        var16.types.addElement(var6.types.elementAt(0));
        var16.types.addElement("integer");
        var16.types.addElement("integer");
        var16.types.addElement("integer");
        var16.arity = 4;
        var16.specifications = var11;
        Vector var17 = new Vector();
        var17.addElement(var16);
        Vector var18 = new Vector();
        var18.addElement("1");
        var18.addElement("2");
        Vector var19 = this.exists.newSpecifications(var17, var18, var3, new Vector());
        Specification var20 = (Specification)var19.elementAt(0);
        Vector var21 = new Vector();
        var21.addElement("0");
        Vector var22 = new Vector();
        var22.addElement("1");
        Function var23 = new Function("fa" + Integer.toString(this.number_of_new_functions++), var21, var22);
        var4.addElement(var23);
        var20.functions.addElement(var23);
        return var19;
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Datatable var5 = (Datatable)var1.elementAt(0);
        Datatable var6 = (Datatable)var1.elementAt(1);
        Concept var7 = (Concept)var2.elementAt(0);
        Concept var8 = (Concept)var2.elementAt(1);
        Datatable var9 = new Datatable();
        String var10 = (String)var3.elementAt(0);

        for(int var11 = 0; var11 < var5.size(); ++var11) {
            Row var12 = (Row)var5.elementAt(var11);
            Row var13 = (Row)var6.elementAt(var11);
            if (!var12.tuples.isEmpty() && !var13.tuples.isEmpty()) {
                String var14 = (String)((Vector)var12.tuples.elementAt(0)).elementAt(0);
                String var15 = (String)((Vector)var13.tuples.elementAt(0)).elementAt(0);
                Tuples var16 = new Tuples();
                Vector var17 = new Vector();
                if (!var14.equals("infinity") && !var15.equals("infinity")) {
                    double var18 = new Double(var14);
                    double var20 = new Double(var15);
                    double var22 = 0.0D;
                    if (var10.equals("+")) {
                        var22 = var18 + var20;
                    }

                    if (var10.equals("*")) {
                        var22 = var18 * var20;
                    }

                    if (var10.equals("-")) {
                        var22 = var18 - var20;
                    }

                    if (var10.equals("/")) {
                        var22 = var18 / var20;
                    }

                    if (var10.equals("d")) {
                        var22 = (double)this.dirichletConvolution(var7, var8, var11 + 1, var4);
                    }

                    String var24 = Double.toString(var22);
                    if (var24.indexOf(".0") == var24.length() - 2) {
                        var24 = var24.substring(0, var24.indexOf(".0"));
                    }

                    var17.addElement(var24);
                } else {
                    var17.addElement("infinity");
                }

                var16.addElement(var17);
                Row var25 = new Row(var12.entity, var16);
                var9.addElement(var25);
            }
        }

        return var9;
    }

    public int dirichletConvolution(Concept var1, Concept var2, int var3, Vector var4) {
        int var5 = 0;

        for(int var6 = 1; var6 <= var3 / 2; ++var6) {
            double var7 = (double)(var3 / var6);
            if (var7 == Math.floor(var7)) {
                int var9 = (new Double(var7)).intValue();
                int var10 = this.calculateFunction(var1, var6, var4) * this.calculateFunction(var2, var9, var4);
                var5 += var10;
            }
        }

        var5 += this.calculateFunction(var1, var3, var4) * this.calculateFunction(var2, 1, var4);
        return var5;
    }

    int calculateFunction(Concept var1, int var2, Vector var3) {
        try {
            Row var4 = var1.calculateRow(var3, (new Integer(var2)).toString());
            String var5 = (String)((Vector)var4.tuples.elementAt(0)).elementAt(0);
            Integer var6 = new Integer(var5);
            return var6;
        } catch (Exception var7) {
            System.out.println(var7);
            System.out.println(var1.id);
            System.out.println(var2);
            return 0;
        }
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Vector var3 = (Vector)((Concept)var1.elementAt(0)).types.clone();
        return var3;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        byte var5 = 0;
        return var5;
    }
}
