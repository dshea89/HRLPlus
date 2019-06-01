package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class ProductionRule implements Serializable {
    public int number_of_new_functions = 0;
    public String parameter_failure_reason = "";
    public int arity_limit = 3;
    public int tier = 0;
    public long stopwatch_starting_time = 0L;

    public ProductionRule() {
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isCumulative() {
        return false;
    }

    public String getName() {
        return "";
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        return null;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        return null;
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Vector var3 = new Vector();
        Vector var4 = new Vector();
        var4.addElement("not yet");
        var3.addElement(var4);
        return var3;
    }

    public boolean isIdentityPermutation(Vector var1) {
        int var2;
        for(var2 = 0; var2 < var1.size(); ++var2) {
            int var3 = new Integer((String)var1.elementAt(var2));
            if (var3 != var2) {
                break;
            }
        }

        return var2 == var1.size();
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        return new Vector();
    }

    public void removeIdentityPermutations(Vector var1) {
        int var2 = 0;

        while(var2 < var1.size()) {
            if (this.isIdentityPermutation((Vector)var1.elementAt(var2))) {
                var1.removeElementAt(var2);
            } else {
                ++var2;
            }
        }

    }

    public Datatable removeColumns(Datatable var1, Vector var2) {
        Datatable var3 = new Datatable();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Row var5 = (Row)var1.elementAt(var4);
            String var6 = var5.entity;
            Tuples var7 = var5.tuples;
            Tuples var8 = new Tuples();

            for(int var9 = 0; var9 < var7.size(); ++var9) {
                Vector var10 = (Vector)((Vector)var7.elementAt(var9)).clone();
                var10.insertElementAt(var6, 0);
                Vector var11 = this.removeColumns(var10, var2);
                var11.removeElementAt(0);
                var8.addElement(var11);
            }

            var8.removeDuplicates();
            Row var12 = new Row(var6, var8);
            if (var8.size() > 0) {
                var3.addElement(var12);
            }
        }

        return var3;
    }

    public Vector swap(Vector var1) {
        Vector var2 = new Vector();
        var2.addElement(var1.elementAt(1));
        var2.addElement(var1.elementAt(0));
        return var2;
    }

    public Vector removeRepeatedElements(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            if (!var2.contains(var1.elementAt(var3))) {
                var2.addElement(var1.elementAt(var3));
            }
        }

        return var2;
    }

    public Vector allColumnTuples(int var1) {
        Vector var2 = new Vector();
        var2.addElement(new Vector());
        int var3 = 0;

        for(int var4 = 1; var4 < var1; ++var4) {
            int var5 = var2.size();

            for(int var6 = var3; var6 < var5; ++var6) {
                var3 = var5;
                Vector var7 = (Vector)var2.elementAt(var6);
                int var8 = 1;
                if (!var7.isEmpty()) {
                    var8 = new Integer((String)var7.lastElement());
                }

                for(int var9 = var8 + 1; var9 <= var1; ++var9) {
                    new Vector();
                    if (!var7.contains(Integer.toString(var9 - 1))) {
                        Vector var10 = (Vector)var7.clone();
                        var10.addElement(Integer.toString(var9 - 1));
                        var2.addElement(var10);
                    }
                }
            }
        }

        var2.removeElementAt(0);
        return var2;
    }

    public Vector removeColumns(Vector var1, Vector var2) {
        for(int var3 = 0; var3 < var2.size(); ++var3) {
            int var4 = new Integer((String)var2.elementAt(var3));
            var1.removeElementAt(var4 - var3);
        }

        return var1;
    }

    public Vector keepColumns(Vector var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            int var5 = new Integer((String)var2.elementAt(var4));
            var3.addElement(var1.elementAt(var5));
        }

        return var3;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4, String var5) {
        return !this.object_type_after_step(var1).equals(var5) ? 0 : this.patternScore(var1, var2, var3, var4);
    }

    public String object_type_after_step(Vector var1) {
        Concept var2 = (Concept)var1.elementAt(0);
        return var2.object_type;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        return 0;
    }

    public void startStopWatch() {
        this.stopwatch_starting_time = new Long(System.currentTimeMillis());
    }

    public long stopWatchTime() {
        long var1 = new Long(System.currentTimeMillis());
        return var1 - this.stopwatch_starting_time;
    }

    public Concept getConceptFromSpecs(Vector var1, Vector var2, String var3) {
        Concept var4 = null;
        Vector var5 = this.sortSpecs(var1);
        boolean var6 = false;

        for(int var7 = 0; var7 < var2.size() && !var6; ++var7) {
            var4 = (Concept)var2.elementAt(var7);
            if (var3.equals(var4.types.toString()) && var4.specifications.size() == var1.size()) {
                int var8;
                for(var8 = 0; var8 < var5.size(); ++var8) {
                    Specification var9 = (Specification)var5.elementAt(var8);

                    int var10;
                    for(var10 = 0; var10 < var4.specifications.size(); ++var10) {
                        Specification var11 = (Specification)var4.specifications.elementAt(var10);
                        if (var11.equals(var9)) {
                            break;
                        }
                    }

                    if (var10 == var4.specifications.size()) {
                        break;
                    }
                }

                if (var8 == var1.size()) {
                    var6 = true;
                }
            }
        }

        return var4;
    }

    public Vector sortSpecs(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Specification var4 = (Specification)var1.elementAt(var3);
            if (!var2.contains(var4)) {
                int var5 = 0;

                boolean var6;
                for(var6 = false; var5 < var2.size() && !var6; ++var5) {
                    Specification var7 = (Specification)var2.elementAt(var5);
                    if (var4.id_number < var7.id_number) {
                        var2.insertElementAt(var4, var5);
                        var6 = true;
                    }
                }

                if (!var6) {
                    var2.addElement(var4);
                }
            }
        }

        return var2;
    }
}
