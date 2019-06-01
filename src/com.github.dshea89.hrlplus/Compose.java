package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Compose extends ProductionRule implements Serializable {
    public boolean dont_swap = false;
    public boolean allow_repeated_specifications = false;
    boolean subobject_overlap = false;
    int time_limit = 10;
    int tuple_product_limit = 150000;

    public Compose() {
    }

    public boolean isBinary() {
        return true;
    }

    public String getName() {
        return "compose";
    }

    public boolean isCumulative() {
        return false;
    }

    public Vector allParameters(Vector var1, Theory var2) {
        this.parameter_failure_reason = "";
        Vector var3 = new Vector();
        Concept var4 = (Concept)var1.elementAt(0);
        Concept var5 = (Concept)var1.elementAt(1);
        int var6 = var4.datatable.number_of_tuples * var5.datatable.number_of_tuples;
        if (var6 > this.tuple_product_limit) {
            this.parameter_failure_reason = "t_prod (" + Integer.toString(var6) + ">" + Integer.toString(this.tuple_product_limit) + ")";
            return var3;
        } else {
            int var7 = var4.arity;
            int var8 = var5.arity;
            int var9 = var7 + var8 - 1;
            if (var9 > this.arity_limit) {
                var9 = this.arity_limit;
            }

            if (this.subobject_overlap && var7 > 1 && var8 > 1 && var9 > var7 + var8 - 2) {
                var9 = var7 + var8 - 2;
            }

            int var10 = var8;
            if (var8 < var7) {
                var10 = var7;
            }

            Vector var11 = var4.types;
            Vector var12 = var5.types;

            for(int var13 = var10; var13 <= var9; ++var13) {
                Vector var14 = this.getParameterisations(var13, var11, var12);

                for(int var15 = 0; var15 < var14.size(); ++var15) {
                    var3.addElement(var14.elementAt(var15));
                }
            }

            return var3;
        }
    }

    private Vector getParameterisations(int var1, Vector var2, Vector var3) {
        Vector var4 = new Vector();
        boolean var5 = false;
        Vector var6 = (Vector)var2.clone();
        Vector var7 = (Vector)var3.clone();
        if (var2.size() < var3.size()) {
            var5 = true;
            var6 = (Vector)var3.clone();
            var7 = (Vector)var2.clone();
        }

        int[] var8 = new int[var1];

        int var9;
        for(var9 = 0; var9 < var1; ++var9) {
            if (var9 < var6.size()) {
                var8[var9] = var9;
            } else {
                var8[var9] = -1;
            }
        }

        Vector var15;
        if (var5) {
            var15 = new Vector();
            var15.addElement("s");
            var4.addElement(var15);
        } else {
            var4.addElement(new Vector());
        }

        int var19;
        for(var9 = 0; var9 < var8.length; ++var9) {
            Vector var10 = new Vector();

            for(int var11 = 0; var11 < var4.size(); ++var11) {
                Vector var12 = (Vector)var4.elementAt(var11);
                if (var8[var9] >= 0) {
                    Vector var13 = (Vector)var12.clone();
                    var13.addElement("0");
                    var10.addElement(var13);
                }

                for(var19 = 0; var19 < var7.size(); ++var19) {
                    if (!var12.contains(Integer.toString(var19 + 1)) && (var8[var9] < 0 || var6.elementAt(var8[var9]).equals(var7.elementAt(var19))) && (var19 != 0 || var8[var9] >= 0)) {
                        Vector var14 = (Vector)var12.clone();
                        var14.addElement(Integer.toString(var19 + 1));
                        var10.addElement(var14);
                    }
                }
            }

            var4 = var10;
        }

        var15 = new Vector();

        for(int var16 = 0; var16 < var4.size(); ++var16) {
            Vector var17 = (Vector)var4.elementAt(var16);
            boolean var18 = true;

            for(var19 = 1; var19 <= var7.size() && var18; ++var19) {
                if (!var17.contains(Integer.toString(var19))) {
                    var18 = false;
                }
            }

            if (var18) {
                var15.addElement(var17);
            }
        }

        return var15;
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Vector var5 = (Vector)var2.clone();
        Vector var6 = var1;
        if (((String)var2.elementAt(0)).equals("s")) {
            var5.removeElementAt(0);
            var6 = super.swap(var1);
        }

        Concept var7 = (Concept)var6.elementAt(0);
        Concept var8 = (Concept)var6.elementAt(1);
        Vector var9 = var7.specifications;
        Vector var10 = var8.specifications;
        Vector var11 = (Vector)var9.clone();
        Vector var12 = new Vector();

        int var13;
        for(var13 = 0; var13 < var5.size(); ++var13) {
            int var14 = new Integer((String)var5.elementAt(var13));
            var12.addElement(Integer.toString(var14 - 1));
        }

        for(var13 = 0; var13 < var10.size(); ++var13) {
            Specification var20 = (Specification)var10.elementAt(var13);
            Vector var15 = var20.permutation;
            Vector var16 = new Vector();

            for(int var17 = 0; var17 < var15.size(); ++var17) {
                String var18 = (String)var15.elementAt(var17);
                if (var17 >= 0) {
                    int var19;
                    for(var19 = 0; var19 < var12.size() && !((String)var12.elementAt(var19)).equals(var18); ++var19) {
                        ;
                    }

                    var16.addElement(Integer.toString(var19));
                }
            }

            Specification var23 = var20.copy();
            var23.relations = var20.relations;
            var23.permutation = var16;
            int var24 = 0;
            if (this.allow_repeated_specifications) {
                if (this.dont_swap && ((String)var2.elementAt(0)).equals("s")) {
                    var11.insertElementAt(var23, var13);
                } else {
                    var11.addElement(var23);
                }
            } else {
                while(var24 < var9.size() && !var23.equals((Specification)var9.elementAt(var24))) {
                    ++var24;
                }

                if (var24 == var9.size()) {
                    var11.addElement(var23);
                }
            }
        }

        Function var21;
        for(var13 = 0; var13 < var7.functions.size(); ++var13) {
            var21 = (Function)var7.functions.elementAt(var13);
            var4.addElement(var21.copy());
        }

        for(var13 = 0; var13 < var8.functions.size(); ++var13) {
            var21 = (Function)var8.functions.elementAt(var13);
            Function var22 = var21.copy();
            var22.permute(var12);
            var4.addElement(var22);
        }

        return var11;
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        long var5 = System.currentTimeMillis() / 1000L;
        new Vector();
        new Vector();
        new Vector();
        Vector var7;
        Vector var8;
        Vector var9;
        if (((String)var3.elementAt(0)).equals("s")) {
            var9 = (Vector)var3.clone();
            var9.removeElementAt(0);
            var7 = super.swap(var2);
            var8 = super.swap(var1);
        } else {
            var7 = var2;
            var8 = var1;
            var9 = var3;
        }

        int var10 = var9.indexOf("1");
        Datatable var11 = (Datatable)var8.elementAt(0);
        Concept var12 = (Concept)var7.elementAt(0);
        int var13 = var12.arity;
        Concept var14 = (Concept)var7.elementAt(1);
        Datatable var15 = new Datatable();

        for(int var16 = 0; var16 < var11.size(); ++var16) {
            Tuples var17 = new Tuples();
            Row var18 = (Row)var11.elementAt(var16);
            Datatable var19 = new Datatable();
            var19.addElement(var18);
            Vector var20 = var19.toFlatTable();
            Vector var21 = new Vector();
            Row var22;
            if (var10 == 0) {
                var22 = var14.calculateRow(var4, var18.entity);
                Datatable var23 = new Datatable();
                var23.addElement(var22);
                var21 = var23.toFlatTable();
            }

            for(int var33 = 0; var33 < var20.size(); ++var33) {
                Vector var34 = (Vector)var20.elementAt(var33);
                if (var34.size() == var13) {
                    if (var10 > 0) {
                        String var24 = (String)var34.elementAt(var10);
                        Row var25 = var14.calculateRow(var4, var24);
                        Datatable var26 = new Datatable();
                        var26.addElement(var25);
                        var21 = var26.toFlatTable();
                    }

                    for(int var35 = 0; var35 < var21.size(); ++var35) {
                        long var36 = System.currentTimeMillis() / 1000L - var5;
                        if (var36 > (long)this.time_limit) {
                            return new Datatable();
                        }

                        Vector var27 = (Vector)var21.elementAt(var35);

                        int var28;
                        for(var28 = 0; var28 < var9.size(); ++var28) {
                            int var29;
                            if (var28 < var34.size()) {
                                var29 = var28;
                            } else {
                                var29 = -1;
                            }

                            int var30 = new Integer((String)var9.elementAt(var28)) - 1;
                            if (var30 >= 0 && var29 >= 0) {
                                String var31 = (String)var34.elementAt(var29);
                                String var32 = (String)var27.elementAt(var30);
                                if (!var31.equals(var32)) {
                                    break;
                                }
                            }
                        }

                        if (var28 == var9.size()) {
                            Vector var37 = this.composeTuples(var34, var27, var9);
                            var37.removeElementAt(0);
                            var17.addElement(var37);
                        }
                    }
                }
            }

            var22 = new Row(var18.entity, var17);
            var15.addElement(var22);
        }

        return var15;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        new Vector();
        new Vector();
        Vector var3;
        Vector var4;
        if (((String)var2.elementAt(0)).equals("s")) {
            var4 = (Vector)var2.clone();
            var4.removeElementAt(0);
            var3 = super.swap(var1);
        } else {
            var3 = var1;
            var4 = var2;
        }

        Vector var5 = (Vector)((Concept)var3.elementAt(0)).types.clone();
        Vector var6 = (Vector)((Concept)var3.elementAt(1)).types.clone();
        return this.composeTuples(var5, var6, var4);
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        Concept var5 = (Concept)var1.elementAt(0);
        Concept var6 = (Concept)var1.elementAt(1);
        if (var5.datatable.number_of_tuples * var6.datatable.number_of_tuples > this.tuple_product_limit) {
            return 0;
        } else {
            int var7 = var4.size();
            Row var13;
            boolean var32;
            Row var40;
            if (var5.arity == 1 && var6.arity == 1 && var5.object_type.equals(var6.object_type)) {
                boolean var29 = true;
                boolean var31 = true;
                var32 = true;

                int var34;
                String var38;
                for(var34 = 0; (var29 || var31 || var32) && var34 < var3.size(); ++var34) {
                    var38 = (String)var3.elementAt(var34);
                    var13 = var5.calculateRow(var2, var38);
                    var40 = var6.calculateRow(var2, var38);
                    if (var13.tuples.size() != var40.tuples.size()) {
                        var29 = false;
                        var31 = false;
                    }

                    if (var13.tuples.size() == var40.tuples.size() && !var13.tuples.isEmpty()) {
                        var32 = false;
                        var31 = false;
                    }

                    if (var13.tuples.size() == var40.tuples.size() && var13.tuples.isEmpty()) {
                        var32 = false;
                        var29 = false;
                    }
                }

                if (!var29 && !var31 && !var32) {
                    return 0;
                } else {
                    for(var34 = 0; var34 < var4.size(); ++var34) {
                        var38 = (String)var4.elementAt(var34);
                        var13 = var5.calculateRow(var2, var38);
                        var40 = var6.calculateRow(var2, var38);
                        if (var13.tuples.size() == var40.tuples.size() && var29 && !var13.tuples.isEmpty()) {
                            --var7;
                        }

                        if (var13.tuples.size() == var40.tuples.size() && var31 && var13.tuples.isEmpty()) {
                            --var7;
                        }

                        if (var13.tuples.size() != var40.tuples.size() && var32) {
                            --var7;
                        }
                    }

                    return var7;
                }
            } else {
                int var12;
                Vector var17;
                Vector var20;
                String var21;
                int var43;
                if (var5.arity == 2 && var6.arity == 2 && var5.object_type.equals(var6.object_type)) {
                    if (var5.id.equals(var6.id)) {
                        return 0;
                    } else {
                        String var8 = (String)var5.types.elementAt(1);
                        String var30 = (String)var6.types.elementAt(1);
                        if (!var8.equals(var30)) {
                            return 0;
                        } else {
                            var32 = true;
                            boolean var33 = true;

                            String var37;
                            Row var41;
                            Vector var42;
                            Vector var44;
                            for(var12 = 0; (var32 || var33) && var12 < var3.size(); ++var12) {
                                var37 = (String)var3.elementAt(var12);
                                var40 = var5.calculateRow(var2, var37);
                                var41 = var6.calculateRow(var2, var37);
                                var42 = new Vector();
                                var17 = new Vector();
                                var44 = new Vector();

                                for(var43 = 0; var43 < var40.tuples.size(); ++var43) {
                                    var20 = (Vector)var40.tuples.elementAt(var43);
                                    var21 = (String)var20.elementAt(0);
                                    var42.addElement(var21);
                                }

                                for(var43 = 0; var43 < var41.tuples.size(); ++var43) {
                                    var20 = (Vector)var41.tuples.elementAt(var43);
                                    var21 = (String)var20.elementAt(0);
                                    var17.addElement(var21);
                                    if (var42.contains(var21)) {
                                        var44.addElement(var21);
                                    }
                                }

                                if (var44.isEmpty()) {
                                    var32 = false;
                                } else {
                                    var33 = false;
                                }
                            }

                            if (!var32 && !var33) {
                                return 0;
                            } else {
                                for(var12 = 0; var12 < var4.size(); ++var12) {
                                    var37 = (String)var4.elementAt(var12);
                                    var40 = var5.calculateRow(var2, var37);
                                    var41 = var6.calculateRow(var2, var37);
                                    var42 = new Vector();
                                    var17 = new Vector();
                                    var44 = new Vector();

                                    for(var43 = 0; var43 < var40.tuples.size(); ++var43) {
                                        var20 = (Vector)var40.tuples.elementAt(var43);
                                        var21 = (String)var20.elementAt(0);
                                        var42.addElement(var21);
                                    }

                                    for(var43 = 0; var43 < var41.tuples.size(); ++var43) {
                                        var20 = (Vector)var41.tuples.elementAt(var43);
                                        var21 = (String)var20.elementAt(0);
                                        var17.addElement(var21);
                                        if (var42.contains(var21)) {
                                            var44.addElement(var21);
                                        }
                                    }

                                    if (var44.isEmpty() && var33) {
                                        --var7;
                                    }

                                    if (!var44.isEmpty() && var32) {
                                        --var7;
                                    }
                                }

                                return var7;
                            }
                        }
                    }
                } else if (var5.arity != var6.arity && var5.arity <= 2 && var6.arity <= 2) {
                    if (var5.object_type != var6.object_type) {
                        return 0;
                    } else if (var5.isGeneralisationOf(var6) != 0 && var6.isGeneralisationOf(var5) != 0) {
                        new Vector();
                        new Concept();
                        new Concept();
                        Concept var9;
                        Concept var10;
                        if (var5.arity < var6.arity) {
                            var9 = var5;
                            var10 = var6;
                        } else {
                            var9 = var6;
                            var10 = var5;
                        }

                        Concept var11 = new Concept();

                        for(var12 = 0; var12 < var10.datatable.size(); ++var12) {
                            var13 = (Row)var10.datatable.elementAt(var12);
                            Tuples var14 = new Tuples();
                            if (!var13.tuples.isEmpty()) {
                                var14.addElement(new Vector());
                            }

                            var11.datatable.addElement(new Row(var13.entity, var14));
                        }

                        var11.arity = 1;
                        Vector var35 = new Vector();
                        var35.addElement(var9);
                        var35.addElement(var11);
                        var7 = this.patternScore(var35, var2, var3, var4);
                        if (var7 == var4.size()) {
                            return var7;
                        } else {
                            boolean var36 = true;
                            boolean var39 = true;
                            boolean var15 = true;
                            boolean var16 = true;
                            var17 = new Vector();

                            int var18;
                            for(var18 = 0; var18 < var9.datatable.size(); ++var18) {
                                Row var19 = (Row)var9.datatable.elementAt(var18);
                                if (!var19.tuples.isEmpty()) {
                                    var17.addElement("[" + var19.entity + "]");
                                }
                            }

                            var18 = 0;
                            var43 = 0;

                            int var24;
                            for(var20 = new Vector(); (var36 || var39 || var15 || var16) && var18 < var3.size(); ++var18) {
                                var21 = (String)var3.elementAt(var18);
                                Row var22 = var10.calculateRow(var2, var21);
                                Vector var23 = new Vector();

                                for(var24 = 0; var24 < var22.tuples.size(); ++var24) {
                                    Vector var25 = (Vector)var22.tuples.elementAt(var24);
                                    String var26 = var25.toString();
                                    if (var17.contains(var26)) {
                                        var23.addElement(var26);
                                    }
                                }

                                if (var18 == 0) {
                                    var20 = (Vector)var23.clone();
                                    var43 = var23.size();
                                }

                                if (var18 > 0) {
                                    var24 = 0;

                                    while(var24 < var20.size()) {
                                        String var48 = (String)var20.elementAt(var24);
                                        if (!var23.contains(var48)) {
                                            var20.removeElementAt(var24);
                                        } else {
                                            ++var24;
                                        }
                                    }
                                }

                                if (var18 > 0 && var43 != var23.size()) {
                                    var15 = false;
                                }

                                if (var23.size() == 0) {
                                    var36 = false;
                                }

                                if (var23.size() > 0) {
                                    var39 = false;
                                }

                                if (var20.isEmpty()) {
                                    var16 = false;
                                }
                            }

                            if (!var36 && !var39 && !var15) {
                                return 0;
                            } else {
                                int var45 = 0;
                                int var46 = 0;
                                int var47 = 0;
                                var24 = 0;
                                if (var15) {
                                    var45 = var4.size();
                                }

                                if (var36) {
                                    var46 = var4.size();
                                }

                                if (var39) {
                                    var47 = var4.size();
                                }

                                if (var16) {
                                    var24 = var4.size();
                                }

                                for(var18 = 0; var18 < var4.size(); ++var18) {
                                    Row var49 = var10.calculateRow(var2, (String)var4.elementAt(var18));
                                    Vector var50 = new Vector();

                                    for(int var27 = 0; var27 < var49.tuples.size(); ++var27) {
                                        Vector var28 = (Vector)var49.tuples.elementAt(var27);
                                        if (var17.contains(var28.toString())) {
                                            var50.addElement(var28.toString());
                                        }

                                        if (var20.contains(var28.toString())) {
                                            var20.removeElement(var28.toString());
                                        }
                                    }

                                    if (var50.isEmpty()) {
                                        --var47;
                                    } else {
                                        --var46;
                                    }

                                    if (var50.size() == var43) {
                                        --var45;
                                    }

                                    if (var20.isEmpty()) {
                                        var24 = 0;
                                    }
                                }

                                var7 = var47;
                                if (var46 > var47) {
                                    var7 = var46;
                                }

                                if (var45 > var7) {
                                    var7 = var45;
                                }

                                if (var24 > var7) {
                                    var7 = var24;
                                }

                                return var7;
                            }
                        }
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        }
    }

    public Vector composeTuples(Vector var1, Vector var2, Vector var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var3.size(); ++var5) {
            int var6;
            if (var5 < var1.size()) {
                var6 = var5;
            } else {
                var6 = -1;
            }

            int var7 = new Integer((String)var3.elementAt(var5)) - 1;
            if (var6 >= 0) {
                var4.addElement(var1.elementAt(var6));
            } else {
                var4.addElement(var2.elementAt(var7));
            }
        }

        return var4;
    }
}
