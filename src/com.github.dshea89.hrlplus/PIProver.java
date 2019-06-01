package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class PIProver extends Prover implements Serializable {
    public int branches = 0;

    public PIProver() {
    }

    public boolean prove(Conjecture var1, Theory var2) {
        if (!(var1 instanceof Implicate)) {
            return false;
        } else {
            Implicate var3 = (Implicate)var1;
            Vector var4 = var2.prime_implicates;
            this.branches = 0;
            Hashtable var5 = new Hashtable();
            new Vector();
            Vector var7 = (Vector)var3.premise_concept.specifications.clone();
            int var8 = var7.size();
            int var9 = 0;
            boolean var10 = false;
            Vector var11 = new Vector();

            int var12;
            Vector var13;
            Vector var16;
            Vector var17;
            for(var12 = 0; var8 != var9 && !var10; var9 = var7.size()) {
                ++var12;
                var8 = var7.size();
                var13 = new Vector();

                int var14;
                for(var14 = 0; var14 < var4.size(); ++var14) {
                    Implicate var15 = (Implicate)var4.elementAt(var14);
                    if (var15 != var3 && this.containsSpecs(var7, var15.premise_concept.specifications)) {
                        var16 = (Vector)var5.get(var15.goal);
                        if (var16 == null) {
                            var17 = new Vector();
                            var17.addElement(var15);
                            var5.put(var15.goal, var17);
                        } else if (!var16.contains(var15)) {
                            var16.addElement(var15);
                        }

                        if (var15.goal != var3.goal) {
                            var13.addElement(var15.goal);
                        }

                        if (var15.goal == var3.goal) {
                            var10 = true;
                        }

                        if (!var11.contains(var15)) {
                            var11.addElement(var15);
                        }
                    }
                }

                for(var14 = 0; var14 < var13.size(); ++var14) {
                    if (!var7.contains(var13.elementAt(var14))) {
                        var7.addElement(var13.elementAt(var14));
                    }
                }
            }

            if (!var10) {
                return false;
            } else {
                var13 = new Vector();
                Vector var20 = this.getPisForGoal(var3.goal, var5);

                for(int var21 = 0; var21 < var20.size(); ++var21) {
                    var16 = new Vector();
                    var16.addElement("after here");
                    var16.addElement(var20.elementAt(var21));
                    var13.addElement(var16);
                }

                Vector var22 = new Vector();

                int var23;
                for(var23 = 0; var23 < var13.size(); ++var23) {
                    var17 = (Vector)var13.elementAt(var23);
                    Vector var18 = this.expandProof(var3, var17, var5, var12);

                    for(int var19 = 0; var19 < var18.size(); ++var19) {
                        var22.addElement(var18.elementAt(var19));
                    }
                }

                var3.proof = "";

                for(var23 = 0; var23 < var22.size(); ++var23) {
                    var3.proof = var3.proof + "\n\n" + var3.writeConjecture("otter") + "\n---------------\n";
                    var17 = (Vector)var22.elementAt(var23);

                    for(int var24 = 0; var24 < var17.size(); ++var24) {
                        Implicate var25 = (Implicate)var17.elementAt(var24);
                        var3.proof = var3.proof + "(" + var25.id + ") " + var25.writeConjecture("otter") + "\n";
                    }
                }

                return true;
            }
        }
    }

    public Vector expandProof(Implicate var1, Vector var2, Hashtable var3, int var4) {
        ++this.branches;
        Vector var5 = new Vector();
        --var4;
        boolean var6 = false;
        boolean var7 = false;

        int var24;
        for(var24 = 0; var24 < var2.size() && !var6; ++var24) {
            Object var8 = var2.elementAt(var24);
            if (var8 instanceof String) {
                var6 = true;
                var2.removeElementAt(var24);
            }
        }

        if (var4 == -1) {
            return var5;
        } else {
            Vector var25 = new Vector();
            Vector var9 = (Vector)var2.clone();
            var25.addElement(var9);

            int var10;
            for(var10 = var24 - 1; var10 < var2.size(); ++var10) {
                Implicate var11 = (Implicate)var2.elementAt(var10);

                for(int var12 = 0; var12 < var11.premise_concept.specifications.size(); ++var12) {
                    Vector var13 = new Vector();
                    Specification var14 = (Specification)var11.premise_concept.specifications.elementAt(var12);
                    new Vector();
                    int var16;
                    Vector var17;
                    if (var1.premise_concept.specifications.contains(var14)) {
                        for(var16 = 0; var16 < var25.size(); ++var16) {
                            var17 = (Vector)var25.elementAt(var16);
                            var13.addElement(var17);
                        }
                    } else {
                        if (!var9.contains("after here")) {
                            var9.addElement("after here");
                        }

                        Vector var15 = this.getPisForGoal(var14, var3);

                        for(var16 = 0; var16 < var25.size(); ++var16) {
                            var17 = (Vector)var25.elementAt(var16);

                            for(int var18 = 0; var18 < var15.size(); ++var18) {
                                Implicate var19 = (Implicate)var15.elementAt(var18);
                                Vector var20 = (Vector)var17.clone();
                                Vector var21 = new Vector();

                                for(int var22 = 0; var22 < var20.size(); ++var22) {
                                    Object var23 = var20.elementAt(var22);
                                    if (var23 instanceof Implicate) {
                                        var21.addElement(((Implicate)var23).goal);
                                    }
                                }

                                if (!var20.contains(var19) && !var21.contains(var14)) {
                                    var20.addElement(var19);
                                    var13.addElement(var20);
                                }
                            }
                        }
                    }

                    var25 = var13;
                }
            }

            for(var10 = 0; var10 < var25.size(); ++var10) {
                Vector var26 = (Vector)var25.elementAt(var10);
                if (var26.contains("after here")) {
                    Vector var27 = this.expandProof(var1, var26, var3, var4);

                    for(int var28 = 0; var28 < var27.size(); ++var28) {
                        var5.addElement(var27.elementAt(var28));
                    }
                } else {
                    var5.addElement(var26);
                }
            }

            return var5;
        }
    }

    private Vector getPisForGoal(Specification var1, Hashtable var2) {
        Vector var3 = new Vector();
        Vector var4 = (Vector)var2.get(var1);
        if (var4 != null) {
            for(int var5 = 0; var5 < var4.size(); ++var5) {
                Implicate var6 = (Implicate)var4.elementAt(var5);
                var3.addElement(var6);
            }
        }

        return var3;
    }

    private boolean containsSpecs(Vector var1, Vector var2) {
        boolean var3 = true;

        for(int var4 = 0; var4 < var2.size() && var3; ++var4) {
            Specification var5 = (Specification)var2.elementAt(var4);
            if (!var1.contains(var5)) {
                var3 = false;
            }
        }

        return var3;
    }
}
