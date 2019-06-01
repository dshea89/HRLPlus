package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.Frame;
import java.awt.List;
import java.awt.TextField;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Predict implements Serializable {
    public Vector given_outliers = new Vector();
    public Vector not_given_outliers = new Vector();
    public Hashtable prediction_scores = new Hashtable();
    public boolean use_equivalences = true;
    public boolean use_negation = false;
    public Frame main_frame = new Frame();
    public Vector step_results = new Vector();
    public double average_of_ranges = 0.0D;
    public boolean making_multiple_predictions = false;
    Vector relations = new Vector();
    int previous_number_of_concepts = -1;
    Vector already_have_near_conjectures = new Vector();
    public int trial_number = 0;
    public Vector read_in_concepts = new Vector();

    public Predict() {
    }

    public void Predict() {
    }

    public Vector makePredictions(String var1, String var2, Vector var3, Vector var4, double var5, String var7, Vector var8, Vector var9, Vector var10, List var11, int var12) {
        Vector var13 = new Vector();
        this.average_of_ranges = 0.0D;

        int var16;
        Concept var17;
        for(int var14 = 0; var14 < var8.size(); ++var14) {
            Concept var15 = (Concept)var8.elementAt(var14);
            var15.additional_datatable.removeRow("px");

            for(var16 = 0; var16 < var15.conjectured_equivalent_concepts.size(); ++var16) {
                var17 = (Concept)var15.conjectured_equivalent_concepts.elementAt(var16);
                var17.additional_datatable.removeRow("px");
            }
        }

        if (!this.making_multiple_predictions) {
            var11.removeAll();
        }

        Vector var41 = new Vector();
        Vector var42 = new Vector();

        int var18;
        String var19;
        String var20;
        for(var16 = 0; var16 < var8.size(); ++var16) {
            var17 = (Concept)var8.elementAt(var16);
            if (var17.is_user_given) {
                for(var18 = 0; var18 < var3.size(); ++var18) {
                    var19 = (String)var4.elementAt(var18);
                    var20 = (String)var3.elementAt(var18);
                    if (var20.equals(var17.name) && var19.equals("")) {
                        var41.addElement(var17);
                        var42.addElement(var10.elementAt(var18));
                    }
                }
            }
        }

        for(var16 = 0; var16 < var8.size(); ++var16) {
            var17 = (Concept)var8.elementAt(var16);
            if (var17.is_user_given) {
                for(var18 = 0; var18 < var3.size(); ++var18) {
                    var19 = (String)var3.elementAt(var18);
                    var20 = (String)var4.elementAt(var18);
                    if (var19.equals(var17.name) && !var20.equals("")) {
                        var17.addAdditionalRow("px", var20);
                    }
                }
            }
        }

        for(var16 = 0; var16 < var41.size(); ++var16) {
            new Tuples();
            Concept var44 = (Concept)var41.elementAt(var16);
            if (!this.making_multiple_predictions) {
                var11.addItem("Predicting for " + var44.name);
            }

            Vector var45 = new Vector();
            Concept var46 = null;
            if (var44.arity == 2) {
                int var21;
                for(var21 = 0; var21 < var8.size() && var46 == null; ++var21) {
                    Concept var22 = (Concept)var8.elementAt(var21);
                    if (var22.name.equals(var44.types.elementAt(var44.arity - 1))) {
                        var46 = var22;
                    }
                }

                for(var21 = 0; var21 < var46.datatable.size(); ++var21) {
                    var45.addElement(((Row)var46.datatable.elementAt(var21)).entity);
                }
            }

            if (var44.arity == 1) {
                var45.removeAllElements();
                var45.addElement("yes");
                var45.addElement("no");
            }

            Vector var47 = new Vector();
            Vector var48 = new Vector();

            for(int var23 = 0; var23 < var8.size(); ++var23) {
                Concept var24 = (Concept)var8.elementAt(var23);
                if (var24 != var44 && var24.arity == 1 && ((String)var24.types.elementAt(0)).equals(var44.types.elementAt(0))) {
                    boolean var25 = false;

                    int var26;
                    for(var26 = 0; var26 < var41.size() && !var25; ++var26) {
                        if (var24.ancestor_ids.contains(((Concept)var41.elementAt(var26)).id)) {
                            var25 = true;
                        }
                    }

                    if (!var25 && var24.calculateRow(var8, "px").tuples.size() == 1 && (var24.step_number <= var12 || var12 < 0)) {
                        var47.addElement(var24);
                    }

                    if (!var25 && var24.calculateRow(var8, "px").tuples.size() == 0 && (var24.step_number <= var12 || var12 < 0)) {
                        var48.addElement(var24);
                    }

                    if (this.use_equivalences) {
                        for(var26 = 0; var26 < var24.conjectured_equivalent_concepts.size(); ++var26) {
                            Concept var27 = (Concept)var24.conjectured_equivalent_concepts.elementAt(var26);
                            var25 = false;

                            for(int var28 = 0; var28 < var41.size() && !var25; ++var28) {
                                if (var27.ancestor_ids.contains(((Concept)var41.elementAt(var28)).id)) {
                                    var25 = true;
                                }
                            }

                            if (var27.calculateRow(var8, "px").tuples.size() == 1 && !var25 && (var27.step_number <= var12 || var12 < 0)) {
                                var47.addElement(var27);
                                if (var24.id.equals("a14")) {
                                    var27.id = "lookie";
                                }
                            }

                            if (var27.calculateRow(var8, "px").tuples.size() == 0 && !var25 && (var27.step_number <= var12 || var12 < 0)) {
                                var48.addElement(var27);
                            }
                        }
                    }
                }
            }

            Vector var49 = new Vector();

            for(int var50 = 0; var50 < var45.size(); ++var50) {
                Vector var52 = new Vector();
                String var53 = (String)var45.elementAt(var50);

                for(int var54 = 0; var54 < var44.datatable.size(); ++var54) {
                    Row var57 = (Row)var44.datatable.elementAt(var54);
                    if (!var57.entity.equals(var2)) {
                        if (var44.arity == 1 && var53.equals("yes") && !var57.tuples.isEmpty()) {
                            var52.addElement(var57.entity);
                        }

                        if (var44.arity == 1 && var53.equals("no") && var57.tuples.isEmpty()) {
                            var52.addElement(var57.entity);
                        }

                        if (var44.arity == 2 && var57.tuples.toString().indexOf("[" + var53 + "]") >= 0) {
                            var52.addElement(var57.entity);
                        }
                    }
                }

                double var56 = 0.0D;
                double var29 = 0.0D;

                int var31;
                double var32;
                Concept var34;
                int var35;
                Row var36;
                int var37;
                String var38;
                Vector var39;
                String var40;
                double var61;
                double var62;
                for(var31 = 0; var31 < var47.size(); ++var31) {
                    var32 = 0.0D;
                    var34 = (Concept)var47.elementAt(var31);

                    for(var35 = 0; var35 < var34.datatable.size(); ++var35) {
                        var36 = (Row)var34.datatable.elementAt(var35);
                        if (!var36.tuples.isEmpty() && !var36.entity.equals(var2)) {
                            ++var32;
                        }
                    }

                    if (var32 <= 0.0D) {
                        ++var29;
                    } else {
                        var61 = 0.0D;

                        for(var37 = 0; var37 < var52.size(); ++var37) {
                            var38 = (String)var52.elementAt(var37);
                            if (!var34.datatable.rowWithEntity(var38).tuples.isEmpty()) {
                                ++var61;
                            }
                        }

                        var62 = var61 / var32;
                        if (!this.making_multiple_predictions && (var62 >= var5 || var62 <= 1.0D - var5)) {
                            var39 = var44.definition_writer.lettersForTypes(var44.types, "prolog", new Vector());
                            var40 = (String)var39.elementAt(0) + ",";
                            var11.addItem(var34.id + ". P(" + var44.name + "(" + var40 + var53 + ") | " + var34.writeDefinition("prolog") + ") = " + Double.toString(var62));
                        }

                        if (var62 < var5 && var62 > 1.0D - var5) {
                            ++var29;
                        } else {
                            var56 += var61 / var32;
                        }
                    }
                }

                if (this.use_negation) {
                    for(var31 = 0; var31 < var48.size(); ++var31) {
                        var32 = 0.0D;
                        var34 = (Concept)var48.elementAt(var31);

                        for(var35 = 0; var35 < var34.datatable.size(); ++var35) {
                            var36 = (Row)var34.datatable.elementAt(var35);
                            if (var36.tuples.isEmpty() && !var36.entity.equals(var2)) {
                                ++var32;
                            }
                        }

                        if (var32 <= 0.0D) {
                            ++var29;
                        } else {
                            var61 = 0.0D;

                            for(var37 = 0; var37 < var52.size(); ++var37) {
                                var38 = (String)var52.elementAt(var37);
                                if (var34.datatable.rowWithEntity(var38).tuples.isEmpty()) {
                                    ++var61;
                                }
                            }

                            var62 = var61 / var32;
                            if (!this.making_multiple_predictions && (var62 >= var5 || var62 <= 1.0D - var5)) {
                                var39 = var44.definition_writer.lettersForTypes(var44.types, "prolog", new Vector());
                                var40 = (String)var39.elementAt(0) + ",";
                                var11.addItem(var34.id + ".P(" + var44.name + "(" + var40 + var53 + ") | -(" + var34.writeDefinition("prolog") + ")) = " + Double.toString(var62));
                            }

                            if (var62 < var5 && var62 > 1.0D - var5) {
                                ++var29;
                            } else {
                                var56 += var61 / var32;
                            }
                        }
                    }
                }

                if (this.use_negation) {
                    var56 /= (double)(var47.size() + var48.size()) - var29;
                } else {
                    var56 /= (double)var47.size() - var29;
                }

                var49.addElement(new Double(var56));
            }

            double var51 = 100.0D;
            double var55 = 0.0D;
            String var58 = "";
            if (!this.making_multiple_predictions) {
                var11.addItem(var44.name, 0);
            }

            boolean var59 = false;

            Double var30;
            int var60;
            for(var60 = 0; var60 < var49.size(); ++var60) {
                var30 = (Double)var49.elementAt(var60);
                if (var30 > var55) {
                    var55 = var30;
                    var58 = (String)var45.elementAt(var60);
                }

                if (var30 < var51) {
                    var51 = var30;
                }
            }

            if (this.making_multiple_predictions) {
                var11.addItem(var58);
            }

            for(var60 = 0; var60 < var49.size(); ++var60) {
                var30 = (Double)var49.elementAt(var60);
                if (this.making_multiple_predictions) {
                    var11.addItem((String)var45.elementAt(var60) + "  [" + var30.toString() + "]");
                } else {
                    var11.addItem((String)var45.elementAt(var60) + "  [" + var30.toString() + "]", 1 + var60);
                }
            }

            if (this.making_multiple_predictions) {
                var11.addItem("------------------------------------");
            } else {
                var11.addItem("------------------------------------", 1 + var60);
                ((TextField)var42.elementAt(var16)).setText(var58);
            }

            var13.addElement(var58);
            this.average_of_ranges += var55 - var51;
        }

        double var43 = new Double((double)var41.size());
        this.average_of_ranges /= var43;
        return var13;
    }

    public void makeAllPredictions(String var1, double var2, String var4, Vector var5, Vector var6, Vector var7, List var8, int var9, boolean var10) {
        Vector var11 = this.getNames(var6);
        Vector var12 = this.getValues(var7, var6);
        this.given_outliers = new Vector();
        this.not_given_outliers = new Vector();
        Vector var13 = new Vector();
        Vector var14 = new Vector();

        for(int var15 = 0; var15 < var5.size(); ++var15) {
            Concept var16 = (Concept)var5.elementAt(var15);
            if (var16.is_user_given) {
                for(int var17 = 0; var17 < var11.size(); ++var17) {
                    String var18 = (String)var12.elementAt(var17);
                    String var19 = (String)var11.elementAt(var17);
                    if (var19.equals(var16.name) && var18.equals("")) {
                        var13.addElement(var16);
                        var14.addElement(Integer.toString(var17));
                    }
                }
            }
        }

        Concept var44 = new Concept();
        if (!var13.isEmpty()) {
            var44 = (Concept)var13.elementAt(0);
        }

        double var45 = 0.0D;
        var8.removeAll();
        this.making_multiple_predictions = true;
        double[] var46 = new double[var13.size()];
        double[] var47 = new double[var13.size()];
        double var20 = 0.0D;
        double var22 = 0.0D;

        for(int var24 = 0; var24 < var13.size(); ++var24) {
            var46[var24] = 0.0D;
            var47[var24] = 0.0D;
        }

        Vector var48 = new Vector();

        int var27;
        for(int var25 = 0; var25 < this.read_in_concepts.size(); ++var25) {
            Concept var26 = (Concept)this.read_in_concepts.elementAt(var25);
            if (var26.object_type.equals(var4) && var26.is_object_of_interest_concept) {
                for(var27 = 0; var27 < var26.datatable.size(); ++var27) {
                    Row var28 = (Row)var26.datatable.elementAt(var27);
                    var48.addElement(var28.entity);
                }
            }
        }

        var8.removeAll();
        double var49 = 0.0D;
        var27 = 0;
        int var50 = 0;

        int var29;
        String var30;
        for(var29 = 0; var29 < var48.size(); ++var29) {
            var12 = new Vector();
            var30 = (String)var48.elementAt(var29);
            var8.addItem(var30);
            Vector var31 = new Vector();

            for(int var32 = 0; var32 < this.read_in_concepts.size(); ++var32) {
                Concept var33 = (Concept)this.read_in_concepts.elementAt(var32);
                if (var33.object_type.equals(var4)) {
                    Tuples var34 = var33.datatable.rowWithEntity(var30).tuples;

                    for(int var35 = 0; var35 < var11.size(); ++var35) {
                        String var36 = (String)var11.elementAt(var35);
                        if (var36.equals(var33.name)) {
                            if (!var14.contains(Integer.toString(var35))) {
                                var12.addElement(var34.toString());
                            } else {
                                var12.addElement("");
                                String var37 = var34.toString();
                                if (var37.equals("[]")) {
                                    var37 = "no";
                                }

                                if (var37.equals("[[]]")) {
                                    var37 = "yes";
                                }

                                if (!var37.equals("yes") && !var37.equals("no")) {
                                    var37 = var37.substring(2, var37.length() - 2);
                                }

                                var31.addElement(var37);
                            }
                        }
                    }
                }
            }

            Vector var52 = this.makePredictions(var1, var30, var11, var12, var2, var4, var5, var6, var7, var8, var9);
            boolean var54 = false;
            if (var44.datatable.hasEntity(var30)) {
                var54 = true;
                ++var49;
            }

            if (var54) {
                var20 += this.average_of_ranges;
            } else {
                var22 += this.average_of_ranges;
            }

            for(int var56 = 0; var56 < var52.size(); ++var56) {
                String var58 = (String)var52.elementAt(var56);
                if (var58.equals((String)var31.elementAt(var56))) {
                    if (var54) {
                        ++var47[var56];
                        ++var27;
                    } else {
                        ++var46[var56];
                        ++var50;
                    }
                } else if (var54) {
                    this.given_outliers.addElement(var30);
                } else {
                    this.not_given_outliers.addElement(var30);
                }
            }

            this.main_frame.setTitle(Integer.toString(var29 + 1) + "/" + Integer.toString(var48.size()) + "[" + Integer.toString(var50) + "," + Integer.toString(this.not_given_outliers.size()) + "][" + Integer.toString(var27) + "," + Integer.toString(this.given_outliers.size()) + "]");
        }

        for(var29 = 0; var29 < var13.size(); ++var29) {
            var30 = (String)var14.elementAt(var29);
            int var51 = new Integer(var30);
            TextField var53 = (TextField)var7.elementAt(var51);
            Double var55 = new Double((double)var48.size() - var49);
            double var57 = var46[var29] / var55;
            double var59 = var47[var29] / var49;
            String var38 = Double.toString(var59 * 100.0D);
            String var39 = Double.toString(var57 * 100.0D);
            if (var38.length() >= 5) {
                var38 = var38.substring(0, 5);
            }

            if (var39.length() >= 5) {
                var39 = var39.substring(0, 5);
            }

            String var40 = Double.toString(var20);
            String var41 = Double.toString(var22);
            if (var40.length() >= 5) {
                var40 = var40.substring(0, 5);
            }

            if (var41.length() >= 5) {
                var41 = var41.substring(0, 5);
            }

            if (var10) {
                var53.setText(var39 + "%, " + var38 + "%, " + var41 + ", " + var40);
            } else {
                var53.setText("");
            }

            Concept var42 = (Concept)var13.elementAt(var29);
            var8.addItem("Prediction for " + var42.id + "(" + var42.name + ")");
            var8.addItem("Prediction accuracy for those not supplied: " + var39 + "%", 1);
            var8.addItem("Prediction accuracy for those supplied: " + var38 + "%", 2);
            Hashtable var43 = new Hashtable();
            var43.put("concept.id", var42.id);
            var43.put("concept.name", var42.name);
            var43.put("steps for concepts", new Integer(var9));
            var43.put("percent correct for given", var38);
            var43.put("percent correct for not given", var39);
            var43.put("given outliers", this.given_outliers);
            var43.put("not given outliers", this.not_given_outliers);
            this.step_results.addElement(var43);
            System.out.println("steps: " + Integer.toString(var9) + " = " + var38 + " " + var39);
        }

        this.making_multiple_predictions = false;
        var8.addItem("given outliers:" + this.given_outliers.toString(), 0);
        var8.addItem("not given outliers:" + this.not_given_outliers.toString(), 0);
    }

    public void makeIncrementalPredictions(String var1, double var2, String var4, Vector var5, Vector var6, Vector var7, List var8, int var9, int var10) {
        this.step_results.clear();

        for(int var11 = 0; var11 <= var10; var11 += var9) {
            this.makeAllPredictions(var1, var2, var4, var5, var6, var7, var8, var11, false);
        }

        this.makeAllPredictions(var1, var2, var4, var5, var6, var7, var8, var10, false);
    }

    public Vector getNames(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Button var4 = (Button)var1.elementAt(var3);
            if (!var4.getLabel().equals("")) {
                var2.addElement(var4.getLabel());
            }
        }

        return var2;
    }

    public Vector getValues(Vector var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Button var5 = (Button)var2.elementAt(var4);
            TextField var6 = (TextField)var1.elementAt(var4);
            if (!var5.getLabel().equals("")) {
                var3.addElement(var6.getText());
            }
        }

        return var3;
    }
}
