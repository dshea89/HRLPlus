package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Negate extends ProductionRule implements Serializable {
    public Negate() {
    }

    public boolean isBinary() {
        return true;
    }

    public String getName() {
        return "negate";
    }

    public Vector allParameters(Vector var1, Theory var2) {
        return this.allParameters(var1);
    }

    public Vector allParameters(Vector var1) {
        Vector var2 = new Vector();
        Concept var3 = (Concept)var1.elementAt(0);
        if (var3.arity > this.arity_limit) {
            return new Vector();
        } else {
            Concept var4 = (Concept)var1.elementAt(1);
            if (var3 == var4) {
                return new Vector();
            } else {
                if (var3.generalisations.contains(var4) && var3.arity == var4.arity) {
                    var2.addElement(new Vector());
                }

                return var3.construction.productionRule().getName().equals("negate") && ((Concept)var3.construction.conceptList().elementAt(1)).id.equals(var4.id) ? new Vector() : var2;
            }
        }
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Vector var5 = new Vector();
        Concept var6 = (Concept)var1.elementAt(0);
        Vector var7 = var6.specifications;
        Concept var8 = (Concept)var1.elementAt(1);
        Vector var9 = var8.specifications;
        Specification var10 = new Specification();
        var10.type = "negate";
        if (var6.is_single_entity) {
            var5 = (Vector)var9.clone();
            Specification var16 = (Specification)var7.lastElement();
            var10.previous_specifications.addElement(var16);
            var10.permutation.addElement("0");
            var5.addElement(var10);
            return var5;
        } else {
            Vector var11 = new Vector();

            int var12;
            Specification var13;
            int var14;
            for(var12 = 0; var12 < var7.size(); ++var12) {
                var13 = (Specification)var7.elementAt(var12);
                if (var9.contains(var13)) {
                    var5.addElement(var13);
                } else {
                    for(var14 = 0; var14 < var13.permutation.size(); ++var14) {
                        String var15 = (String)var13.permutation.elementAt(var14);
                        if (!var11.contains(var15)) {
                            var11.addElement(var15);
                        }
                    }

                    var10.previous_specifications.addElement(var13.copy());
                }
            }

            for(var12 = 0; var12 < var6.arity; ++var12) {
                if (var11.contains(Integer.toString(var12))) {
                    var10.permutation.addElement(Integer.toString(var12));
                }
            }

            for(var12 = 0; var12 < var10.previous_specifications.size(); ++var12) {
                Vector var17 = new Vector();
                Specification var18 = (Specification)var10.previous_specifications.elementAt(var12);

                for(int var19 = 0; var19 < var18.permutation.size(); ++var19) {
                    var17.addElement(Integer.toString(var10.permutation.indexOf((String)var18.permutation.elementAt(var19))));
                }

                var18.permutation = var17;
            }

            for(var12 = 0; var12 < var5.size(); ++var12) {
                var13 = (Specification)var5.elementAt(var12);

                for(var14 = 0; var14 < var13.functions.size(); ++var14) {
                    var4.addElement(var13.functions.elementAt(var14));
                }
            }

            var5.addElement(var10);
            return var5;
        }
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Concept var5 = (Concept)var2.elementAt(0);
        Datatable var6 = new Datatable();
        Concept var7 = (Concept)var2.elementAt(1);
        Datatable var8 = (Datatable)var1.elementAt(0);

        for(int var9 = 0; var9 < var8.size(); ++var9) {
            Row var10 = new Row();
            Row var11 = (Row)var8.elementAt(var9);
            var10.entity = var11.entity;
            Row var12 = var7.calculateRow(var4, var11.entity);

            for(int var13 = 0; var13 < var12.tuples.size(); ++var13) {
                Vector var14 = (Vector)var12.tuples.elementAt(var13);
                String var15 = var14.toString();
                Vector var16 = new Vector();

                for(int var17 = 0; var17 < var11.tuples.size(); ++var17) {
                    var16.addElement(((Vector)var11.tuples.elementAt(var17)).toString());
                }

                if (!var16.contains(var15)) {
                    var10.tuples.addElement(var14);
                }
            }

            var6.addElement(var10);
        }

        return var6;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        return ((Concept)var1.elementAt(0)).types;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        Concept var5 = (Concept)var1.elementAt(0);
        if (this.allParameters(var1).isEmpty()) {
            return 0;
        } else {
            int var6 = 0;

            boolean var7;
            for(var7 = true; var7 && var6 < var3.size(); ++var6) {
                String var8 = (String)var3.elementAt(var6);
                Row var9 = var5.calculateRow(var2, var8);
                if (var9.tuples.size() > 0) {
                    var7 = false;
                }
            }

            if (!var7) {
                return 0;
            } else {
                int var11 = var4.size();

                for(var6 = 0; var6 < var4.size(); ++var6) {
                    String var12 = (String)var4.elementAt(var6);
                    Row var10 = var5.calculateRow(var2, var12);
                    if (var10.tuples.size() == 0) {
                        --var11;
                    }
                }

                return var11;
            }
        }
    }
}