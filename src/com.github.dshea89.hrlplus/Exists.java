package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Exists extends ProductionRule implements Serializable {
    public boolean merge_previous_exists = true;
    public boolean is_cumulative = false;

    public Exists() {
    }

    public boolean isBinary() {
        return false;
    }

    public String getName() {
        return "exists";
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Concept var3 = (Concept)var1.elementAt(0);
        Vector var4 = super.allColumnTuples(var3.arity);
        Vector var5 = new Vector();

        for(int var6 = 0; var6 < var4.size(); ++var6) {
            Vector var7 = (Vector)var4.elementAt(var6);
            if (var3.arity - var7.size() <= this.arity_limit) {
                var5.addElement(var7);
            }
        }

        return var5;
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Vector var5 = new Vector();
        Concept var6 = (Concept)var1.elementAt(0);
        new Vector();
        Vector var7 = var6.specifications;
        Vector var8 = var6.types;
        Vector var9 = new Vector();
        Specification var10 = new Specification();
        var10.type = "exists";
        var10.multiple_variable_columns = (Vector)var2.clone();

        int var11;
        for(var11 = 0; var11 < var2.size(); ++var11) {
            var10.multiple_types.addElement(var8.elementAt(new Integer((String)var2.elementAt(var11))));
        }

        int var16;
        int var17;
        int var19;
        int var26;
        for(var11 = 0; var11 < var7.size(); ++var11) {
            Specification var12 = (Specification)var7.elementAt(var11);
            boolean var13 = var12.involvesColumns(var2);
            if (!var13) {
                Specification var14 = var12.copy();
                var14.permutation = new Vector();
                int var15 = 0;

                while(true) {
                    if (var15 >= var12.permutation.size()) {
                        var5.addElement(var14);
                        break;
                    }

                    var16 = 0;
                    var17 = new Integer((String)var12.permutation.elementAt(var15));

                    for(int var18 = 0; var18 < var2.size(); ++var18) {
                        var19 = new Integer((String)var2.elementAt(var18));
                        if (var19 < var17) {
                            ++var16;
                        }
                    }

                    var14.permutation.addElement(Integer.toString(var17 - var16));
                    ++var15;
                }
            }

            if (var13) {
                var10.previous_specifications.addElement(var12);

                for(var26 = 0; var26 < var12.permutation.size(); ++var26) {
                    String var27 = (String)var12.permutation.elementAt(var26);
                    if (!var9.contains(var27)) {
                        var9.addElement(var27);
                    }
                }
            }
        }

        var11 = 0;

        int var22;
        for(var22 = 0; var22 < var8.size(); ++var22) {
            String var23 = Integer.toString(var22);
            if (var9.contains(var23) && !var2.contains(var23)) {
                var10.permutation.addElement(Integer.toString(var11));
                ++var11;
            }

            if (!var2.contains(var23) && !var9.contains(var23)) {
                var10.redundant_columns.addElement(Integer.toString(var11));
                ++var11;
            }
        }

        if (this.merge_previous_exists) {
            var22 = var6.arity - var2.size() + var10.multiple_variable_columns.size();
            Hashtable var24 = new Hashtable();

            for(var26 = 0; var26 < var10.previous_specifications.size(); ++var26) {
                Specification var28 = (Specification)var10.previous_specifications.elementAt(var26);
                if (var28.type.equals("exists")) {
                    String var20;
                    String var32;
                    for(var16 = 0; var16 < var28.permutation.size(); ++var16) {
                        var17 = var16;
                        var32 = Integer.toString(var16);

                        for(var19 = 0; var19 < var28.multiple_variable_columns.size(); ++var19) {
                            var20 = (String)var28.multiple_variable_columns.elementAt(var19);
                            int var21 = new Integer(var20);
                            if (var21 <= var17) {
                                ++var17;
                            }
                        }

                        var32 = Integer.toString(var17);
                        String var34 = (String)var28.permutation.elementAt(var16);
                        var24.put(var32, var34);
                    }

                    for(var16 = 0; var16 < var28.multiple_variable_columns.size(); ++var16) {
                        String var30 = (String)var28.multiple_variable_columns.elementAt(var16);
                        var32 = Integer.toString(var22);
                        var24.put(var30, var32);
                        var10.multiple_variable_columns.addElement(var32);
                        var10.multiple_types.addElement(var28.multiple_types.elementAt(var16));
                        ++var22;
                    }

                    for(var16 = 0; var16 < var28.previous_specifications.size(); ++var16) {
                        Specification var31 = (Specification)var28.previous_specifications.elementAt(var16);
                        Vector var33 = new Vector();

                        for(var19 = 0; var19 < var31.permutation.size(); ++var19) {
                            var20 = (String)var31.permutation.elementAt(var19);
                            String var36 = (String)var24.get(var20);
                            var33.addElement(var36);
                        }

                        Specification var35 = var31.copy();
                        var35.permutation = var33;
                        var10.previous_specifications.addElement(var35);
                    }

                    var10.previous_specifications.removeElementAt(var26);
                    --var26;
                }
            }
        }

        var5.addElement(var10);

        for(var22 = 0; var22 < var6.functions.size(); ++var22) {
            Function var25 = (Function)var6.functions.elementAt(var22);
            Function var29 = var25.copy();
            if (!var29.containsAColumnFrom(var2)) {
                var29.removeHoles(var2);
                var4.addElement(var29);
            }
        }

        return var5;
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Datatable var5 = (Datatable)var1.elementAt(0);
        return super.removeColumns(var5, var3);
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Vector var3 = (Vector)((Concept)var1.elementAt(0)).types.clone();
        return super.removeColumns(var3, var2);
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        Concept var5 = (Concept)var1.elementAt(0);
        int var6 = 0;
        boolean var7 = false;
        boolean var8 = false;
        String var9 = (String)var3.elementAt(0);
        Row var10 = var5.calculateRow(var2, var9);
        if (var10.tuples.size() == 0) {
            var7 = true;
        } else {
            var8 = true;
        }

        for(; (var8 || var7) && var6 < var3.size(); ++var6) {
            var9 = (String)var3.elementAt(var6);
            var10 = var5.calculateRow(var2, var9);
            if (var10.tuples.size() == 0) {
                var8 = false;
            } else {
                var7 = false;
            }
        }

        if (!var8 && !var7) {
            return 0;
        } else {
            int var11 = var4.size();

            for(var6 = 0; var6 < var4.size(); ++var6) {
                var9 = (String)var4.elementAt(var6);
                var10 = var5.calculateRow(var2, var9);
                if (var10.tuples.size() > 0 && var8) {
                    --var11;
                }

                if (var10.tuples.size() == 0 && var7) {
                    --var11;
                }
            }

            return var11;
        }
    }
}
