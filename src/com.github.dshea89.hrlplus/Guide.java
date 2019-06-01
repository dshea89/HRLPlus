package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Guide implements Serializable {
    public Guide() {
    }

    public TheoryConstituent forceSteps(Vector var1, Theory var2) {
        System.out.println("doing forceSteps(" + var1 + ")");
        Object var3 = new TheoryConstituent();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            String var5 = (String)var1.elementAt(var4);
            Step var6 = var2.agenda.getStepFromString(var5, var2);
            System.out.println("current step_string is " + var5);

            try {
                Concept var7 = (Concept)var2.agenda.repeated_forced_steps.get(var6.asString());
                if (var7 == null) {
                    System.out.println("concept_produced was null so doing more things...");
                    var2.agenda.steps_to_force.addElement(var5);
                    var2.agenda.addForcedStep(var5, true);
                    Step var8 = var2.theoryFormationStep();
                    var3 = var8.result_of_step;
                    System.out.println("a - obj is " + ((TheoryConstituent)var3).writeTheoryConstituent());
                } else {
                    System.out.println("concept_produced was NOT null");
                    var3 = var7;
                    System.out.println("we produced: obj " + ((Concept)var7).writeDefinition());
                }

                System.out.println("down here: in guide........\n\n");
            } catch (Exception var9) {
                ;
            }
        }

        return (TheoryConstituent)var3;
    }

    public void addStep(String var1, String var2, String var3, String var4, String var5, Theory var6) {
        String var7 = var1 + " = " + var2;
        if (!var3.equals("")) {
            var7 = var7 + " " + var3;
        }

        var7 = var7 + " " + var4 + " " + var5;
        var6.agenda.addForcedStep(var7, false);
    }

    public Step forceStep(String var1, String var2, String var3, String var4, String var5, Theory var6) {
        String var7 = var1 + " = " + var2;
        if (!var3.equals("")) {
            var7 = var7 + " " + var3;
        }

        var7 = var7 + " " + var4 + " " + var5;
        Step var8 = this.forceStep(var7, var6);
        return var8;
    }

    public Step forceStep(String var1, Theory var2) {
        Step var3 = var2.agenda.getStepFromString(var1, var2);
        Step var4 = new Step();

        try {
            Concept var5 = (Concept)var2.agenda.repeated_forced_steps.get(var3.asString());
            if (var5 == null) {
                var2.agenda.steps_to_force.addElement(var1);
                var2.agenda.addForcedStep(var1, true);
                var4 = var2.theoryFormationStep();
                if (var4.result_of_step instanceof Concept) {
                    Concept var6 = (Concept)var4.result_of_step;
                }
            } else {
                var4.result_of_step_explanation = "Step already carried out";
            }
        } catch (Exception var7) {
            System.out.println("HERE -- Exception is " + var7);
            var7.printStackTrace(System.out);
        }

        return var4;
    }

    public void loadParameterisations(Theory var1) {
        var1.front_end.force_parameter_list.removeAll();
        if (var1.front_end.force_primary_concept_list.getSelectedIndex() >= 0) {
            Concept var2 = var1.getConcept(var1.front_end.force_primary_concept_list.getSelectedItem());
            Concept var3 = new Concept();
            if (var1.front_end.force_secondary_concept_list.getSelectedIndex() >= 0) {
                var3 = var1.getConcept(var1.front_end.force_secondary_concept_list.getSelectedItem());
            }

            Vector var4 = new Vector();
            var4.addElement(var2);
            var4.addElement(var3);
            ProductionRule var5 = null;

            for(int var6 = 0; var6 < var1.all_production_rules.size() && var5 == null; ++var6) {
                ProductionRule var7 = (ProductionRule)var1.all_production_rules.elementAt(var6);
                if (var7.getName().equals(var1.front_end.force_prodrule_list.getSelectedItem())) {
                    var5 = var7;
                }
            }

            if (var5 != null) {
                Vector var11 = var5.allParameters(var4, var1);

                for(int var12 = 0; var12 < var11.size(); ++var12) {
                    Vector var8 = (Vector)var11.elementAt(var12);
                    String var9 = (new Step()).writeParameters(var8);
                    Concept var10 = new Concept();
                    var10.types = var5.transformTypes(var4, var8);
                    var10.specifications = var5.newSpecifications(var4, var8, var1, new Vector());
                    var9 = var9 + ": " + var10.writeDefinitionWithStartLetters("ascii");
                    var1.front_end.force_parameter_list.addItem(var9);
                }

                if (var11.isEmpty()) {
                    var1.front_end.force_parameter_list.addItem(var5.parameter_failure_reason);
                }
            }

        }
    }

    public void writeForceString(Theory var1) {
        String var2 = var1.front_end.force_primary_concept_list.getSelectedItem();
        String var3 = var1.front_end.force_secondary_concept_list.getSelectedItem();
        String var4 = var1.front_end.force_prodrule_list.getSelectedItem();
        String var5 = var1.front_end.force_parameter_list.getSelectedItem();
        if (var2 == null) {
            var1.front_end.force_string_text.setText("step = ");
        } else if (var3 == null) {
            if (var4 != null) {
                if (var5 == null) {
                    var1.front_end.force_string_text.setText("step = " + var2 + " " + var4);
                } else {
                    var5 = var5.substring(0, var5.indexOf(":"));
                    var1.front_end.force_string_text.setText("step = " + var2 + " " + var4 + " " + var5);
                }
            } else {
                var1.front_end.force_string_text.setText("step = " + var2);
            }

        } else if (var4 == null) {
            var1.front_end.force_string_text.setText("step = " + var2 + " " + var3);
        } else if (var5 == null) {
            if (var3 == null) {
                var1.front_end.force_string_text.setText("step = " + var2 + " " + var3 + " " + var4);
            }

        } else {
            var5 = var5.substring(0, var5.indexOf(":"));
            var1.front_end.force_string_text.setText("step = " + var2 + " " + var3 + " " + var4 + " " + var5);
        }
    }

    public void addCounterexampleBarringSteps(Theory var1, Concept var2, Vector var3, String var4) {
        String var5 = "";
        Vector var6 = new Vector();

        for(int var7 = 0; var7 < var3.size(); ++var7) {
            var1.agenda.addForcedStep("split" + var4 + "_" + Integer.toString(var7) + " = " + var2.entity_concept.id + " split [[0], [" + (String)var3.elementAt(var7) + "]] : dont_develop", false);
            var6.addElement("split" + var4 + "_" + Integer.toString(var7));
        }

        boolean var10 = false;
        if (var6.size() >= 2) {
            var1.agenda.addForcedStep("disj" + var4 + "_1 = " + "split" + var4 + "_0 split" + var4 + "_1 disjunct [] : dont_develop", false);
            var5 = "disj" + var4 + "_1";

            for(int var8 = 2; var8 < var6.size(); ++var8) {
                var1.agenda.addForcedStep("disj" + var4 + "_" + Integer.toString(var8) + " = disj" + var4 + "_" + Integer.toString(var8 - 1) + " " + (String)var6.elementAt(var8) + " disjunct [] : dont_develop", false);
                var5 = "disj" + var4 + "_" + Integer.toString(var8);
            }
        } else {
            var5 = "split" + var4 + "_0";
        }

        var1.agenda.addForcedStep("negate_" + var4 + " = " + var5 + " " + var2.entity_concept.id + " negate [] : dont_develop", false);
        String var11 = "[";

        for(int var9 = 0; var9 < var2.arity - 1; ++var9) {
            var11 = var11 + Integer.toString(var9 + 1) + ", ";
        }

        var11 = var11 + Integer.toString(var2.arity) + "]";
        var1.agenda.addForcedStep("compose_" + var4 + " = " + "negate_" + var4 + " " + var2.id + " compose " + var11 + " : dont_develop", false);
    }
}
