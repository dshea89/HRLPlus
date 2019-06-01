package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Agenda implements Serializable {
    public int step_number = 0;
    public boolean use_segregated_search = false;
    public Categorisation segregation_categorisation = new Categorisation();
    public boolean keep_not_allowed_steps = false;
    public Hashtable concept_hashtable = new Hashtable();
    public Vector non_existent_concept_ids = new Vector();
    public Hashtable repeated_forced_steps = new Hashtable();
    public Vector forced_steps = new Vector();
    public Hashtable concept_forced_names = new Hashtable();
    public Vector steps_to_force = new Vector();
    public boolean second_addition = false;
    public boolean expand_agenda = true;
    public int current_tier = 0;
    public boolean is_tiered = false;
    public Vector agenda_tiers = new Vector();
    public int complexity_limit = 1000;
    public int agenda_size_limit = 10000;
    public boolean random = false;
    public String object_types_to_learn = "";
    public Vector positives_for_forward_lookahead = new Vector();
    public Vector negatives_for_forward_lookahead = new Vector();
    public boolean use_forward_lookahead = false;
    public boolean use_scores = false;
    public Vector dont_compose_with = new Vector();
    public Vector unary_steps = new Vector();
    public Vector binary_steps = new Vector();
    public Vector exhaustive_steps = new Vector();
    public Vector urgent_unary_steps = new Vector();
    public Vector urgent_binary_steps = new Vector();
    public Vector not_allowed_steps = new Vector();
    public boolean best_first_delayed = false;
    public int best_first_delay_steps = 0;
    public boolean best_first = false;
    public boolean depth_first = false;
    public Vector live_ordered_concepts = new Vector();
    public Vector all_ordered_concepts = new Vector();
    public Step forced_step = new Step();
    public boolean use_unary_rules = true;

    public Agenda() {
        for(int var1 = 0; var1 < 20; ++var1) {
            this.agenda_tiers.addElement(new Vector());
        }

    }

    public void addConcept(Concept var1, Vector var2, Theory var3) {
        this.concept_hashtable.put(var1.id, var1);
        if (!var1.dont_develop) {
            if (!this.second_addition) {
                this.addConceptToOrderedList(this.all_ordered_concepts, var1);
            }

            Step var4;
            Vector var5;
            if (var1.complexity >= this.complexity_limit) {
                if (this.keep_not_allowed_steps) {
                    var4 = new Step();
                    var5 = new Vector();
                    var5.addElement(var1);
                    var4.addElement(var5);
                    this.not_allowed_steps.addElement(var4);
                    var4.trimToSize();
                    this.not_allowed_steps.trimToSize();
                }

            } else {
                if (!this.second_addition) {
                    this.addConceptToOrderedList(this.live_ordered_concepts, var1);
                }

                int var7;
                Step var8;
                if (!this.use_forward_lookahead) {
                    if (this.use_unary_rules && !this.second_addition) {
                        var4 = new Step();
                        var5 = new Vector();
                        var5.addElement(var1);
                        var4.addElement(var5);
                        var5.trimToSize();
                        var4.trimToSize();
                        if (this.depth_first) {
                            this.exhaustive_steps.insertElementAt(var4, 0);
                        } else {
                            this.exhaustive_steps.addElement(var4);
                        }
                    }

                    if (!this.expand_agenda) {
                        return;
                    }

                    int var17 = this.itemsInAgenda();
                    int var19 = this.all_ordered_concepts.size();
                    if (var17 + this.all_ordered_concepts.size() > this.agenda_size_limit) {
                        var19 = this.agenda_size_limit - var17;
                    }

                    int var6 = 0;

                    for(var7 = 0; var7 < var19; ++var7) {
                        var8 = new Step();
                        Vector var9 = new Vector();
                        Concept var10 = (Concept)this.all_ordered_concepts.elementAt(var7);
                        if (!this.dont_compose_with.contains(var10.id) && !var10.dont_develop && !this.conceptsAreSegregated(var1, var10)) {
                            var9.addElement(var1);
                            var9.addElement(var10);
                            var9.trimToSize();
                            if (!this.live_ordered_concepts.contains(var10)) {
                                this.addConceptToOrderedList(this.live_ordered_concepts, var10);
                            }

                            var8.addElement(var9);
                            var8.trimToSize();
                            if (var1.complexityWith(var10) >= this.complexity_limit && this.keep_not_allowed_steps) {
                                this.not_allowed_steps.addElement(var8);
                            }

                            if (var1.complexityWith(var10) < this.complexity_limit && var1.position_in_theory >= var10.position_in_theory) {
                                if (this.second_addition) {
                                    this.exhaustive_steps.insertElementAt(var8, 1 + var6);
                                    ++var6;
                                } else if (this.depth_first) {
                                    this.exhaustive_steps.insertElementAt(var8, 0);
                                } else {
                                    this.exhaustive_steps.addElement(var8);
                                }
                            }
                        }
                    }
                }

                if (this.use_forward_lookahead) {
                    Vector var18 = new Vector();
                    var18.addElement(var1);
                    var5 = var3.unary_rules;
                    Vector var20 = var3.binary_rules;

                    for(var7 = 0; var7 < var5.size(); ++var7) {
                        var8 = new Step();
                        var8.addElement(var18);
                        ProductionRule var22 = (ProductionRule)var5.elementAt(var7);
                        int var24 = var22.patternScore(var18, var2, this.positives_for_forward_lookahead, this.negatives_for_forward_lookahead, this.object_types_to_learn);
                        if (var24 < this.negatives_for_forward_lookahead.size() && !this.use_scores) {
                            var24 = 0;
                        }

                        new Long((long)this.negatives_for_forward_lookahead.size());
                        Vector var12 = new Vector();
                        var12.addElement(var22);
                        var8.addElement(var12);
                        if (var24 == 0) {
                            this.exhaustive_steps.addElement(var8);
                        } else {
                            this.urgent_unary_steps.addElement(var8);
                        }
                    }

                    var7 = this.itemsInAgenda();
                    int var21 = var2.size();
                    if (var7 + var2.size() > this.agenda_size_limit) {
                        var21 = this.agenda_size_limit - var7;
                    }

                    for(int var23 = 0; var23 < var21; ++var23) {
                        Vector var25 = new Vector();
                        Concept var11 = (Concept)var2.elementAt(var23);
                        if (!this.dont_compose_with.contains(var11.id)) {
                            var25.addElement(var1);
                            var25.addElement(var11);
                            if (var1.complexityWith(var11) < this.complexity_limit && var1.position_in_theory >= var11.position_in_theory) {
                                for(int var26 = 0; var26 < var20.size(); ++var26) {
                                    ProductionRule var13 = (ProductionRule)var20.elementAt(var26);
                                    Step var14 = new Step();
                                    var14.addElement(var25);
                                    int var15 = var13.patternScore(var25, var2, this.positives_for_forward_lookahead, this.negatives_for_forward_lookahead, this.object_types_to_learn);
                                    Vector var16 = new Vector();
                                    if (var15 < this.negatives_for_forward_lookahead.size() && !this.use_scores) {
                                        var15 = 0;
                                    }

                                    var16.addElement(var13);
                                    var14.addElement(var16);
                                    if (var15 == 0) {
                                        this.exhaustive_steps.addElement(var14);
                                    } else {
                                        this.urgent_binary_steps.addElement(var14);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public boolean conceptsAreSegregated(Concept var1, Concept var2) {
        Vector var3 = new Vector();

        int var4;
        String var5;
        String var6;
        for(var4 = 0; var4 < var1.ancestor_ids.size(); ++var4) {
            var5 = (String)var1.ancestor_ids.elementAt(var4);
            var6 = this.segregationPosition(var5);
            if (!var3.contains(var6) && !var6.equals("")) {
                var3.addElement(var6);
            }
        }

        for(var4 = 0; var4 < var2.ancestor_ids.size(); ++var4) {
            var5 = (String)var2.ancestor_ids.elementAt(var4);
            var6 = this.segregationPosition(var5);
            if (!var3.contains(var6) && !var6.equals("")) {
                var3.addElement(var6);
            }

            if (var3.size() > 1) {
                return true;
            }
        }

        return false;
    }

    private String segregationPosition(String var1) {
        for(int var2 = 0; var2 < this.segregation_categorisation.size(); ++var2) {
            Vector var3 = (Vector)this.segregation_categorisation.elementAt(var2);
            if (var3.contains(var1)) {
                return Integer.toString(var2);
            }
        }

        return "";
    }

    public Step nextStep(Vector var1, Vector var2, Theory var3) {
        if (!this.steps_to_force.isEmpty()) {
            String var15 = (String)this.steps_to_force.elementAt(0);
            this.steps_to_force.removeElementAt(0);
            boolean var17 = true;
            Step var19 = this.getStepFromString(var15, var3);
            this.forced_steps.addElement(var15);
            if (var19 == null) {
                String var20 = var15.substring(0, var15.indexOf(" ="));
                this.non_existent_concept_ids.addElement(var20);
                return this.nextStep(var1, var2, var3);
            } else {
                Concept var18 = (Concept)this.repeated_forced_steps.get(var19.asString());
                if (var18 != null) {
                    if (!var19.concept_arising_name.equals("")) {
                        this.concept_forced_names.put(var19.concept_arising_name, var18);
                    }

                    return this.nextStep(var1, var2, var3);
                } else {
                    var19.forced = true;
                    var19.trimToSize();
                    return var19;
                }
            }
        } else {
            Step var4 = new Step();
            new Vector();
            boolean var6 = false;
            Vector var5;
            if (!this.urgent_unary_steps.isEmpty()) {
                var5 = this.urgent_unary_steps;
            } else if (!this.urgent_binary_steps.isEmpty()) {
                var5 = this.urgent_binary_steps;
            } else {
                var5 = this.exhaustive_steps;
            }

            Vector var7 = var3.concepts;
            Vector var9;
            int var16;
            if (var5.isEmpty()) {
                boolean var8 = false;

                for(var16 = 0; !var8 && var16 < this.agenda_tiers.size(); ++var16) {
                    var9 = (Vector)this.agenda_tiers.elementAt(var16);
                    if (!var9.isEmpty()) {
                        var8 = true;
                        var5 = var9;
                    }
                }

                if (var8 && (this.best_first || this.best_first_delayed) && var16 != this.current_tier) {
                    this.live_ordered_concepts = (Vector)this.all_ordered_concepts.clone();
                }

                this.current_tier = var16;
            } else {
                this.current_tier = 0;
            }

            if (var5.isEmpty()) {
                return var4;
            } else {
                Step var21 = new Step();
                var9 = new Vector();
                byte var10 = 0;
                Concept var11;
                if (!this.best_first && (!this.best_first_delayed || this.step_number < this.best_first_delay_steps)) {
                    if (this.random) {
                        int var22 = (new Double(Math.random() * (double)var5.size())).intValue();
                        var21 = (Step)var5.elementAt(var22);
                        var9 = (Vector)var21.elementAt(0);
                        var5.removeElementAt(var22);
                        var5.insertElementAt(var21, 0);
                        var10 = 0;
                    } else {
                        var21 = (Step)var5.elementAt(0);
                        var9 = (Vector)var21.elementAt(0);
                        var10 = 0;
                    }
                } else {
                    var16 = 0;
                    var11 = (Concept)this.live_ordered_concepts.elementAt(0);

                    boolean var12;
                    for(var12 = false; !var12 && var16 < var5.size(); ++var16) {
                        var21 = (Step)var5.elementAt(var16);
                        var9 = (Vector)var21.elementAt(0);
                        Concept var13 = (Concept)var9.elementAt(0);
                        if (var13 == var11) {
                            var5.removeElementAt(var16);
                            var5.insertElementAt(var21, 0);
                            var12 = true;
                            var10 = 0;
                        }

                        if (var9.size() == 2 && !var12) {
                            Concept var14 = (Concept)var9.elementAt(1);
                            if (var14 == var11) {
                                var5.removeElementAt(var16);
                                var5.insertElementAt(var21, 0);
                                var12 = true;
                                var10 = 0;
                            }
                        }
                    }

                    if (!var12) {
                        this.live_ordered_concepts.removeElementAt(0);
                        return this.nextStep(var1, var2, var3);
                    }
                }

                if (var21.size() == 1) {
                    if (var9.size() == 1) {
                        var11 = (Concept)var9.elementAt(0);
                        if (!this.expand_agenda) {
                            this.expand_agenda = true;
                            this.second_addition = true;
                            this.addConcept(var11, var7, var3);
                            this.second_addition = false;
                            this.expand_agenda = false;
                        }

                        if (var1.size() == 0) {
                            var5.removeElementAt(var10);
                        } else {
                            this.putIntoTiers(var21, var9, var1);
                            if (var21.size() == 1) {
                                var5.removeElementAt(var10);
                            }
                        }
                    }

                    if (var9.size() == 2) {
                        if (var2.size() == 0) {
                            var5.removeElementAt(var10);
                        } else {
                            this.putIntoTiers(var21, var9, var2);
                            if (var21.size() == 1) {
                                var5.removeElementAt(var10);
                            }
                        }
                    }

                    return this.nextStep(var1, var2, var3);
                } else {
                    Vector var23;
                    ProductionRule var24;
                    Vector var25;
                    if (var21.size() == 2) {
                        var23 = (Vector)var21.elementAt(1);
                        var24 = (ProductionRule)var23.elementAt(0);
                        var25 = var24.allParameters(var9, var3);
                        if (var25.size() > 0) {
                            var21.addElement(var25);
                        } else {
                            var23.removeElementAt(0);
                        }

                        if (var23.size() == 0) {
                            var5.removeElementAt(var10);
                        }

                        return this.nextStep(var1, var2, var3);
                    } else {
                        if (var21.size() == 3) {
                            var23 = (Vector)var21.elementAt(1);
                            var24 = (ProductionRule)var23.elementAt(0);
                            var25 = (Vector)var21.elementAt(2);
                            Vector var26 = (Vector)var25.elementAt(0);
                            var25.removeElementAt(0);
                            if (var25.size() == 0) {
                                var21.removeElementAt(2);
                                var23.removeElementAt(0);
                            }

                            if (var23.size() == 0) {
                                var5.removeElementAt(var10);
                            }

                            var4.addElement(var9);
                            var4.addElement(var24);
                            var4.addElement(var26);
                        }

                        var4.trimToSize();
                        return var4;
                    }
                }
            }
        }
    }

    public void putIntoTiers(Vector var1, Vector var2, Vector var3) {
        int var4 = 0;
        Vector var5 = (Vector)var3.clone();

        while(var4 < var5.size()) {
            ProductionRule var6 = (ProductionRule)var5.elementAt(var4);
            if (var6.tier > 0) {
                Vector var7 = (Vector)this.agenda_tiers.elementAt(var6.tier);
                Vector var8 = new Vector();
                var8.addElement(var6);
                Step var9 = new Step();
                var9.addElement(var2);
                var9.addElement(var8);
                var7.addElement(var9);
                var5.removeElementAt(var4);
            } else {
                ++var4;
            }
        }

        if (!var5.isEmpty()) {
            var1.addElement(var5);
        }

    }

    public int itemsInAgenda() {
        int var1 = this.unary_steps.size();
        var1 += this.binary_steps.size();
        var1 += this.exhaustive_steps.size();
        var1 += this.urgent_binary_steps.size();
        var1 += this.urgent_unary_steps.size();

        for(int var2 = 0; var2 < this.agenda_tiers.size(); ++var2) {
            var1 += ((Vector)this.agenda_tiers.elementAt(var2)).size();
        }

        return var1;
    }

    private void addConceptToOrderedList(Vector var1, Concept var2) {
        if (!this.best_first && !this.best_first_delayed) {
            var1.addElement(var2);
        } else {
            boolean var3 = false;

            for(int var4 = 0; var4 < var1.size() && !var3; ++var4) {
                Concept var5 = (Concept)var1.elementAt(var4);
                if (var2.interestingness > var5.interestingness) {
                    var1.insertElementAt(var2, var4);
                    var3 = true;
                }
            }

            if (!var3) {
                var1.addElement(var2);
            }
        }

        var1.trimToSize();
    }

    public void orderConcepts() {
        if (this.best_first || this.best_first_delayed) {
            Vector var1 = new Vector();
            Vector var2 = new Vector();

            for(int var3 = 0; var3 < this.all_ordered_concepts.size(); ++var3) {
                Concept var4 = (Concept)this.all_ordered_concepts.elementAt(var3);
                this.addConceptToOrderedList(var1, var4);
                if (this.live_ordered_concepts.contains(var4)) {
                    this.addConceptToOrderedList(var2, var4);
                }
            }

            this.all_ordered_concepts = var1;
            this.live_ordered_concepts = var2;
        }

        this.all_ordered_concepts.trimToSize();
        this.live_ordered_concepts.trimToSize();
    }

    public Vector listToComplete(boolean var1, int var2) {
        Vector var3 = new Vector();
        int var4 = 0;
        if (var1) {
            while(var3.size() < var2 && var4 < this.not_allowed_steps.size()) {
                this.addSteps(var3, (Step)this.not_allowed_steps.elementAt(var4), var2);
                ++var4;
            }
        }

        if (!var1) {
            while(var3.size() < var2 && var4 < this.exhaustive_steps.size()) {
                this.addSteps(var3, (Step)this.exhaustive_steps.elementAt(var4), var2);
                ++var4;
            }
        }

        if (!var1) {
            for(int var5 = 0; var5 < this.agenda_tiers.size(); ++var5) {
                for(Vector var6 = (Vector)this.agenda_tiers.elementAt(var5); var3.size() < var2 && var4 < var6.size(); ++var4) {
                    this.addSteps(var3, (Step)var6.elementAt(var4), var2);
                }
            }
        }

        return var3;
    }

    private void addSteps(Vector var1, Step var2, int var3) {
        Vector var4 = (Vector)var2.elementAt(0);
        Vector var5 = new Vector();

        for(int var6 = 0; var6 < var4.size(); ++var6) {
            var5.addElement(((Concept)var4.elementAt(var6)).id);
        }

        if (var2.size() == 1) {
            var1.addElement(var5.toString());
        }

        String var9;
        Vector var12;
        if (var2.size() == 2) {
            var12 = (Vector)var2.elementAt(1);
            new Vector();

            for(int var8 = 0; var8 < var12.size(); ++var8) {
                var9 = ((ProductionRule)var12.elementAt(var8)).getName();
                var1.addElement(var5 + " " + var9);
            }
        }

        if (var2.size() == 3) {
            var12 = (Vector)var2.elementAt(1);
            new Vector();
            Vector var13 = (Vector)var2.elementAt(2);
            var9 = ((ProductionRule)var12.elementAt(0)).getName();

            int var10;
            for(var10 = 0; var10 < var13.size() && var1.size() < var3; ++var10) {
                var1.addElement(var5.toString() + " " + var9 + " " + ((Vector)var13.elementAt(var10)).toString());
            }

            for(var10 = 1; var10 < var12.size() && var1.size() < var3; ++var10) {
                String var11 = ((ProductionRule)var12.elementAt(var10)).getName();
                var1.addElement(var5.toString() + " " + var11);
            }
        }

    }

    public void makeChanges() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.not_allowed_steps.size(); ++var2) {
            Step var3 = (Step)this.not_allowed_steps.elementAt(var2);
            Vector var4 = (Vector)var3.elementAt(0);
            Concept var5 = (Concept)var4.elementAt(0);
            boolean var6 = true;
            if (var5.complexity >= this.complexity_limit) {
                var6 = false;
                var1.addElement(var3);
            }

            if (var4.size() == 2) {
                Concept var7 = (Concept)var4.elementAt(1);
                if (var5.complexityWith(var7) >= this.complexity_limit) {
                    var6 = false;
                    var1.addElement(var3);
                }
            }

            if (var6) {
                this.exhaustive_steps.addElement(var3);
                if (var4.size() == 1 && !this.live_ordered_concepts.contains(var5)) {
                    this.addConceptToOrderedList(this.live_ordered_concepts, var5);
                }
            }
        }

        this.not_allowed_steps = var1;
    }

    public Step getStepFromString(String var1, Theory var2) {
        try {
            Step var3 = new Step();
            String var4 = var1.substring(0, var1.indexOf(" ="));
            var1 = var1.substring(var1.indexOf("=") + 2, var1.length());
            String var5 = "";
            if (var1.indexOf(":") >= 0) {
                var5 = var1.substring(var1.indexOf(":"), var1.length());
                var1 = var1.substring(0, var1.indexOf(":") - 1);
            }

            String var6 = var1.substring(var1.indexOf("["), var1.length());
            var1 = var1.substring(0, var1.indexOf("["));
            String var7 = var1.substring(0, var1.indexOf(" "));
            var1 = var1.substring(var1.indexOf(" ") + 1, var1.length());
            String var8 = var1.substring(0, var1.indexOf(" "));
            var1 = var1.substring(var1.indexOf(" "), var1.length());
            Vector var9 = new Vector();
            new ProductionRule();
            ProductionRule var10;
            if (!var1.equals(" ")) {
                String var11 = var1.substring(1, var1.length() - 1);
                Concept var12 = this.getConcept(var7, var2);
                if (var12 == null) {
                    return null;
                }

                var9.addElement(var12);
                Concept var13 = this.getConcept(var8, var2);
                if (var13 == null) {
                    return null;
                }

                var9.addElement(var13);
                var10 = var2.productionRuleFromName(var11);
            } else {
                Concept var14 = this.getConcept(var7, var2);
                if (var14 == null) {
                    System.out.println("getStepFromString - 3");
                    return null;
                }

                var9.addElement(var14);
                var10 = var2.productionRuleFromName(var8);
            }

            var3.addElement(var9);
            var3.addElement(var10);
            var3.addElement(this.getVector(var6, var10.getName(), var2));
            if (var5.indexOf("dont_develop") >= 0) {
                var3.dont_develop = true;
            }

            var3.concept_arising_name = var4;
            var3.trimToSize();
            return var3;
        } catch (Exception e) {
            return null;
        }
    }

    public Concept getConcept(String var1, Theory var2) {
        Concept var3 = var2.getConcept(var1);
        if (var3.id.equals("")) {
            return this.non_existent_concept_ids.contains(var1) ? null : (Concept)this.concept_forced_names.get(var1);
        } else {
            return var3;
        }
    }

    public Concept getConcept(String var1) {
        if (this.non_existent_concept_ids.contains(var1)) {
            return null;
        } else if (this.concept_forced_names.containsKey(var1)) {
            return (Concept)this.concept_forced_names.get(var1);
        } else {
            return this.concept_hashtable.containsKey(var1) ? (Concept)this.concept_hashtable.get(var1) : null;
        }
    }

    private Vector getVector(String var1, String var2, Theory var3) {
        Vector var4 = new Vector();
        var1 = var1.trim();
        if (var1.indexOf(", ") < 0) {
            String var5 = "";

            for(int var6 = 0; var6 < var1.length(); ++var6) {
                if (var1.substring(var6, var6 + 1).equals(",")) {
                    var5 = var5 + ", ";
                } else {
                    var5 = var5 + var1.substring(var6, var6 + 1);
                }
            }

            var1 = var5;
        }

        if (var2.equals("forall")) {
            Concept var7 = this.getConcept(var1.substring(1, var1.indexOf(",")), var3);
            var4.addElement(var7);
            var4.addElement(this.getVector(var1.substring(var1.indexOf(",") + 1, var1.indexOf("]") + 1), "", var3));
            var4.addElement(this.getVector(var1.substring(var1.indexOf("], [") + 3, var1.length() - 1), "", var3));
            return var4;
        } else if (var2.equals("split")) {
            var4.addElement(this.getVector(var1.substring(1, var1.indexOf("]") + 1), "", var3));
            var4.addElement(this.getVector(var1.substring(var1.indexOf("], [") + 3, var1.length() - 1), "", var3));
            return var4;
        } else {
            for(var1 = var1.substring(1, var1.length() - 1); var1.indexOf(", ") > 0; var1 = var1.substring(var1.indexOf(", ") + 2, var1.length())) {
                var4.addElement(var1.substring(0, var1.indexOf(", ")));
            }

            var4.addElement(var1);
            return var4;
        }
    }

    public void recordNonExistentConcept(Step var1) {
        if (!var1.concept_arising_name.equals("")) {
            this.non_existent_concept_ids.addElement(var1.concept_arising_name);
        }

    }

    public void recordForcedName(Step var1, Concept var2) {
        if (!var1.concept_arising_name.equals("")) {
            this.concept_forced_names.put(var1.concept_arising_name, var2);
            System.out.println("step.concept_arising_name is " + var1.concept_arising_name);
            System.out.println("putting " + var1.asString() + ", " + var2.writeDefinition());
            this.repeated_forced_steps.put(var1.asString(), var2);
        }

    }

    public void addForcedStep(String var1, boolean var2) {
        if (var2) {
            this.steps_to_force.insertElementAt(var1, 0);
        }

        this.steps_to_force.addElement(var1);
    }
}
