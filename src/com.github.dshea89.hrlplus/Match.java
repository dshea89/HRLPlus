package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

public class Match extends ProductionRule implements Serializable {
    public Match() {
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isCumulative() {
        return false;
    }

    public String getName() {
        return "match";
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Concept var3 = (Concept)var1.elementAt(0);
        int var4 = var3.arity;
        Vector var5 = var3.types;
        Vector var6 = new Vector();
        Vector var7 = new Vector();
        var7.addElement("0");
        Vector var8 = new Vector();
        var8.addElement("[0]");
        var6.addElement(var7);

        int var10;
        for(int var9 = 1; var9 < var4; ++var9) {
            var10 = var6.size();

            for(int var11 = 0; var11 < var10; ++var11) {
                Vector var12 = (Vector)var6.elementAt(0);

                for(int var13 = 0; var13 <= var9; ++var13) {
                    if (var13 < var9) {
                        int var14 = new Integer((String)var12.elementAt(var13));
                        if (((String)var5.elementAt(var9)).equals((String)var5.elementAt(var14))) {
                            Vector var15 = (Vector)var12.clone();
                            var15.addElement((String)var12.elementAt(var13));
                            if (!var8.contains(var15.toString())) {
                                var6.addElement(var15);
                                var8.addElement(var15.toString());
                            }
                        }
                    } else {
                        Vector var18 = (Vector)var12.clone();
                        var18.addElement(Integer.toString(var9));
                        if (!var8.contains(var18.toString())) {
                            var6.addElement(var18);
                            var8.addElement(var18.toString());
                        }
                    }
                }

                var6.removeElementAt(0);
            }
        }

        Vector var16 = new Vector();

        for(var10 = 0; var10 < var6.size(); ++var10) {
            Vector var17 = (Vector)var6.elementAt(var10);
            if (super.removeRepeatedElements(var17).size() <= this.arity_limit) {
                var16.addElement(var17);
            }
        }

        super.removeIdentityPermutations(var16);
        return var16;
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Vector var5 = new Vector();
        Concept var6 = (Concept)var1.elementAt(0);
        Vector var7 = var6.specifications;
        Enumeration var8 = var7.elements();
        Vector var9 = new Vector();
        int var10 = 0;

        int var11;
        int var13;
        for(var11 = 0; var11 < var2.size(); ++var11) {
            String var12 = (String)var2.elementAt(var11);
            if (new Integer(var12) == var11) {
                var9.addElement(Integer.toString(var10));
                ++var10;
            } else {
                var13 = new Integer(var12);
                var9.addElement(var9.elementAt(var13));
            }
        }

        while(var8.hasMoreElements()) {
            Specification var18 = (Specification)var8.nextElement();
            Vector var19 = var18.permutation;
            Specification var23 = var18.copy();
            Vector var14 = new Vector();

            for(int var15 = 0; var15 < var19.size(); ++var15) {
                int var16 = new Integer((String)var19.elementAt(var15));
                String var17 = (String)var9.elementAt(var16);
                var14.addElement(var17);
            }

            var23.permutation = var14;
            var5.addElement(var23);
        }

        for(var11 = 0; var11 < var5.size() - 1; ++var11) {
            Specification var21 = (Specification)var5.elementAt(var11);
            var13 = var11 + 1;

            while(var13 < var5.size()) {
                Specification var25 = (Specification)var5.elementAt(var13);
                if (var25.equals(var21)) {
                    var5.removeElementAt(var13);
                } else {
                    ++var13;
                }
            }
        }

        Vector var20 = new Vector();

        for(int var22 = 0; var22 < var6.functions.size(); ++var22) {
            Function var24 = (Function)var6.functions.elementAt(var22);
            Function var26 = var24.copy();
            var26.matchPermute(var2);
            if (!var20.contains(var26.writeFunction())) {
                if (!var26.outputAsInput()) {
                    var4.addElement(var26);
                }

                var20.addElement(var26.writeFunction());
            }
        }

        return var5;
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Datatable var5 = (Datatable)var1.elementAt(0);
        Datatable var6 = new Datatable();

        for(int var7 = 0; var7 < var5.size(); ++var7) {
            Row var8 = (Row)var5.elementAt(var7);
            String var9 = var8.entity;
            Tuples var10 = var8.tuples;
            Tuples var11 = new Tuples();

            for(int var12 = 0; var12 < var10.size(); ++var12) {
                Vector var13 = (Vector)((Vector)var10.elementAt(var12)).clone();
                var13.insertElementAt(var9, 0);
                boolean var14 = true;

                for(int var15 = 0; var14 && var15 < var13.size(); ++var15) {
                    int var16 = new Integer((String)var3.elementAt(var15));
                    if (!((String)var13.elementAt(var16)).equals((String)var13.elementAt(var15))) {
                        var14 = false;
                    }
                }

                if (var14) {
                    Vector var18 = this.removeMatchedColumns(var13, var3);
                    var18.removeElementAt(0);
                    var11.addElement(var18);
                }
            }

            Row var17 = new Row(var9, var11);
            var6.addElement(var17);
        }

        return var6;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Vector var3 = (Vector)((Concept)var1.elementAt(0)).types.clone();
        return this.removeMatchedColumns(var3, var2);
    }

    private Vector removeMatchedColumns(Vector var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            int var5 = new Integer((String)var2.elementAt(var4));
            if (var5 == var4) {
                var3.addElement(var1.elementAt(var4));
            }
        }

        return var3;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        Concept var5 = (Concept)var1.elementAt(0);
        if (var5.arity != 2 && var5.arity != 3) {
            return 0;
        } else {
            boolean var6 = true;
            int var7 = 0;

            int var8;
            for(var8 = 0; var8 < var3.size() && var6; ++var8) {
                String var9 = (String)var3.elementAt(var8);
                Row var10 = var5.calculateRow(var2, var9);
                boolean var11 = false;

                for(int var12 = 0; var12 < var10.tuples.size(); ++var12) {
                    Vector var13 = (Vector)var10.tuples.elementAt(var12);
                    if (var5.arity == 3 && ((String)var13.elementAt(0)).equals((String)var13.elementAt(1))) {
                        ++var7;
                        var11 = true;
                    }

                    if (var5.arity == 2 && ((String)var13.elementAt(0)).equals(var9)) {
                        ++var7;
                        var11 = true;
                    }
                }
            }

            if (var7 != 0 && var7 != var3.size()) {
                return 0;
            } else {
                int var15 = var4.size();

                for(var8 = 0; var8 < var4.size(); ++var8) {
                    String var16 = (String)var4.elementAt(var8);
                    Row var17 = var5.calculateRow(var2, var16);
                    boolean var18 = false;

                    for(int var19 = 0; var19 < var17.tuples.size(); ++var19) {
                        Vector var14 = (Vector)var17.tuples.elementAt(var19);
                        if (var5.arity == 3 && ((String)var14.elementAt(0)).equals((String)var14.elementAt(1))) {
                            var18 = true;
                        }

                        if (var5.arity == 2 && ((String)var14.elementAt(0)).equals(var16)) {
                            var18 = true;
                        }
                    }

                    if (var18 && var7 > 0) {
                        --var15;
                    }

                    if (!var18 && var7 == 0) {
                        --var15;
                    }
                }

                return var15;
            }
        }
    }
}