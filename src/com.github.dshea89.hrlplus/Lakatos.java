package com.github.dshea89.hrlplus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Lakatos implements Serializable {
    FrontEnd front_end = new FrontEnd();
    Guide guider = new Guide();
    Agenda agenda = new Agenda();
    Vector concepts = new Vector();
    Vector equivalences = new Vector();
    Vector non_existences = new Vector();
    Vector implicates = new Vector();
    Vector implications = new Vector();
    Vector entities = new Vector();
    Vector production_rules = new Vector();
    public StorageHandler storage_handler = new StorageHandler();
    public boolean use_threshold_to_add_conj_to_theory = false;
    public boolean use_threshold_to_add_concept_to_theory = false;
    public double threshold_to_add_conj_to_theory = 0.0D;
    public double threshold_to_add_concept_to_theory = 0.0D;
    public boolean test_max_num_independent_work_stages = true;
    public int max_num_independent_work_stages = 10;
    public boolean test_num_independent_steps = true;
    public int num_independent_steps = 10;
    public boolean test_number_modifications_surrender = false;
    public boolean test_interestingness_surrender = false;
    public boolean compare_average_interestingness_surrender = false;
    public boolean test_plausibility_surrender = false;
    public boolean test_domain_application_surrender = false;
    public int number_modifications_surrender = 0;
    public double interestingness_th_surrender = 0.0D;
    public double plausibility_th_surrender = 0.0D;
    public double domain_application_th_surrender = 0.0D;
    double monster_barring_min = 0.0D;
    String monster_barring_type = "vaguetospecific";
    int monster_barring_culprit_min = 0;
    boolean use_breaks_conj_under_discussion = false;
    boolean accept_strictest = true;
    boolean use_percentage_conj_broken = false;
    boolean use_culprit_breaker = false;
    boolean use_culprit_breaker_on_conj = false;
    boolean use_culprit_breaker_on_all = false;
    boolean use_counterexample_barring = false;
    boolean use_piecemeal_exclusion = false;

    public Lakatos() {
    }

    public void findLakatosVariables() {
        String var1 = this.front_end.force_parameter_list.getSelectedItem();
    }

    public void performConceptBarring(Concept var1) {
        if (var1.arity == 1) {
            for(int var2 = 0; var2 < var1.near_equivalences.size(); ++var2) {
                NearEquivalence var3 = (NearEquivalence)var1.near_equivalences.elementAt(var2);
                Concept var4 = var3.rh_concept;
                Vector var5 = var3.counterexamples;
                new Vector();
                Vector var7 = new Vector();
                Vector var8 = new Vector();

                for(int var9 = 0; var9 < var5.size(); ++var9) {
                    String var10 = (String)var5.elementAt(var9);
                    Vector var11 = new Vector();
                    var11.addElement(var10);
                    if (var1.positivesContains(var11)) {
                        var7.addElement(var10);
                    } else {
                        var8.addElement(var10);
                    }
                }

                if (var8.size() == 0) {
                    this.makeConceptForEquivConj(var1, var4, var5);
                } else if (var7.size() == 0) {
                    this.makeConceptForEquivConj(var4, var1, var5);
                } else {
                    this.makeConceptForImpConj(var1, var4, var7);
                    this.makeConceptForImpConj(var4, var1, var8);
                }
            }
        }

    }

    public void makeConceptForEquivConj(Concept var1, Concept var2, Vector var3) {
        Tuples var4 = new Tuples();

        for(int var5 = 0; var5 < this.concepts.size(); ++var5) {
            Concept var6 = (Concept)this.concepts.elementAt(var5);
            if (var6.arity == 1 && var6.positivesContains(var3)) {
                Vector var7 = new Vector();
                var7.addElement(Double.toString(var6.applicability));
                var7.addElement(var6);
                var4.addElement(var7);
            }
        }

        var4.sort();
        Vector var17 = (Vector)var4.elementAt(0);
        double var18 = new Double((double)var3.size());
        double var8 = new Double((double)this.entities.size());
        double var10 = var18 / var8;
        Concept var12 = (Concept)var17.elementAt(1);
        String var13 = (String)var17.elementAt(0);
        if (var13.equals(Double.toString(var10))) {
            Concept var14 = new Concept();

            for(int var15 = 0; var15 < var12.generalisations.size(); ++var15) {
                var14 = (Concept)var12.generalisations.elementAt(var15);
                if (var14.is_object_of_interest_concept) {
                    break;
                }
            }

            String var19 = "id_of_dummy1 = " + var12.id + " " + var14.id + " negate [] ";
            this.agenda.addForcedStep(var19, false);
            String var16 = "compose_id_of_dummy2 = id_of_dummy1 " + var1.id + " compose " + "[1]" + " : dont_develop";
            this.agenda.addForcedStep(var16, false);
        }

    }

    public void makeConceptForImpConj(Concept var1, Concept var2, Vector var3) {
        Tuples var4 = new Tuples();

        for(int var5 = 0; var5 < this.concepts.size(); ++var5) {
            Concept var6 = (Concept)this.concepts.elementAt(var5);
            if (var6.arity == 1 && var6.positivesContains(var3)) {
                Vector var7 = new Vector();
                var7.addElement(Double.toString(var6.applicability));
                var7.addElement(var6);
                var4.addElement(var7);
            }
        }

        var4.sort();
        Vector var17 = (Vector)var4.elementAt(0);
        double var18 = new Double((double)var3.size());
        double var8 = new Double((double)this.entities.size());
        double var10 = var18 / var8;
        Concept var12 = (Concept)var17.elementAt(1);
        String var13 = (String)var17.elementAt(0);
        if (var13.equals(Double.toString(var10))) {
            Concept var14 = new Concept();

            for(int var15 = 0; var15 < var12.generalisations.size(); ++var15) {
                var14 = (Concept)var12.generalisations.elementAt(var15);
                if (var14.is_object_of_interest_concept) {
                    break;
                }
            }

            String var19 = "id_of_dummy1 = " + var12.id + " " + var14.id + " negate [] ";
            this.agenda.addForcedStep(var19, false);
            String var16 = "compose_id_of_dummy2 = id_of_dummy1 " + var1.id + " compose " + "[1]" + " : dont_develop";
            this.agenda.addForcedStep(var16, false);
        }

    }

    public Vector getNConceptsWhichCoverGivenEntities(Vector var1, int var2) {
        Vector var3 = new Vector();

        int var4;
        for(var4 = 0; var4 < this.concepts.size(); ++var4) {
            for(int var5 = 0; var5 < var2; ++var5) {
                Concept var6 = (Concept)this.concepts.elementAt(var4);
                if (var6.positivesContains(var1)) {
                    var3.addElement(var6);
                }
            }
        }

        for(var4 = 0; var4 < var3.size(); ++var4) {
            Concept var7 = (Concept)var3.elementAt(var4);
        }

        return var3;
    }

    public Vector surrender(Conjecture var1) {
        Vector var2 = new Vector();
        String var3 = var1.writeConjecture("ascii") + " is not worth modifying";
        var2.addElement(var3);
        return var2;
    }

    public Vector piecemealExclusion(Conjecture var1, Theory var2, AgentWindow var3) {
        System.out.println("___________________________ \n\n");
        System.out.println("started piecemealExclusion on " + var1.writeConjecture("ascii"));
        Vector var4 = new Vector();
        Concept var6;
        Concept var7;
        boolean var8;
        boolean var9;
        Vector var21;
        if (var1 instanceof NearEquivalence) {
            NearEquivalence var5 = (NearEquivalence)var1;
            var6 = var5.lh_concept;
            var7 = var5.rh_concept;
            var3.writeToFrontEnd("The counterexamples are " + var5.counterexamples);
            var4 = this.piecemealExclusion1("equiv", var5.counterexamples, var6, var7, var2, var3);
            System.out.println("MONDAY ~ 5 output is " + var4);
            var8 = var4.isEmpty();
            var9 = var5.counterexamples.size() < 3;
            if (var8 && var9) {
                System.out.println("MONDAY ~ 6 -- trying counter-barring");
                var21 = this.performCounterexampleBarring("equiv", var6, var7, var5.counterexamples, var2, var3);
                System.out.println("new_output is " + var21);
                return var21;
            }
        }

        if (var1 instanceof NearImplication) {
            NearImplication var11 = (NearImplication)var1;
            var6 = var11.lh_concept;
            var7 = var11.rh_concept;
            var3.writeToFrontEnd("The counterexamples are " + var11.counterexamples);
            var4 = this.piecemealExclusion1("imp", var11.counterexamples, var6, var7, var2, var3);
            var8 = var4.isEmpty();
            var9 = var11.counterexamples.size() < 3;
            if (var8 && var9) {
                System.out.println("MONDAY ~ 6 -- trying counter-barring");
                var21 = this.performCounterexampleBarring("imp", var6, var7, var11.counterexamples, var2, var3);
                System.out.println("new_output is " + var21);
                return var21;
            }
        }

        String var15;
        if (var1 instanceof Equivalence) {
            Equivalence var12 = (Equivalence)var1;
            var15 = var1.writeConjecture("ascii") + " holds for all my examples";
            System.out.println("conj_to_modify is an Equiv, so returning response_to_conj = " + var15);
            var4.addElement(var15);
        }

        if (var1 instanceof Implication) {
            Implication var13 = (Implication)var1;
            var15 = var1.writeConjecture("ascii") + " holds for all my examples";
            System.out.println("conj_to_modify is an Implication, so returning response_to_conj = " + var15);
            var4.addElement(var15);
        }

        if (var1 instanceof NonExists) {
            var3.writeToFrontEnd("Got a NonExists - just going to modify it now... ");
            NonExists var14 = (NonExists)var1;
            var6 = var14.concept;
            var3.writeToFrontEnd("the id of the concept_with_counters is " + var6.id);
            Vector var16 = var6.positiveEntities();
            Vector var17 = this.fromEntitiesToStrings(var16);
            if (var17.isEmpty()) {
                String var18 = var1.writeConjecture("ascii") + " holds for all my examples";
                var4.addElement(var18);
            } else {
                TheoryConstituent var19 = this.forceStepForPiecemealExclusionAvoidingConcept(var6, var17, var2, var3);
                if (var19 instanceof Conjecture) {
                    var4.addElement((Conjecture)var19);
                } else {
                    Concept var10 = new Concept();
                    var4 = this.performCounterexampleBarring("nexists", var6, var10, var16, var2, var3);
                }

                if (var19 instanceof Conjecture) {
                    Conjecture var20 = (Conjecture)var19;
                    var2.measure_conjecture.measureConjecture(var20, var2);
                    var3.writeToFrontEnd("After modifying, the interestingness of the modification is " + var20.interestingness);
                }

                var3.writeToFrontEnd("Finished modifying it - got " + var19.writeTheoryConstituent());
            }
        }

        return var4;
    }

    public Vector piecemealExclusion1(String var1, Vector var2, Concept var3, Concept var4, Theory var5, AgentWindow var6) {
        Vector var7 = new Vector();
        Vector var8 = this.fromEntitiesToStrings(var2);
        boolean var9 = var3.positivesContainOneOf(var8);
        boolean var10 = var4.positivesContainOneOf(var8);
        if (var1.equals("imp")) {
            boolean var11 = var3.positivesContainOneOf(var8);
            if (var11) {
                return this.getConjectureFromCountersOneSide(var1, var3, var4, var2, var5, var6);
            }
        }

        if (var9 && !var10) {
            var7 = this.getConjectureFromCountersOneSide(var1, var3, var4, var2, var5, var6);
        }

        if (!var9 && var10) {
            var7 = this.getConjectureFromCountersOneSide(var1, var4, var3, var2, var5, var6);
        }

        return var9 && var10 ? var7 : var7;
    }

    public Vector getConjectureFromCountersOneSide(String var1, Concept var2, Concept var3, Vector var4, Theory var5, AgentWindow var6) {
        Vector var7 = new Vector();
        Vector var8 = this.fromEntitiesToStrings(var4);
        TheoryConstituent var9 = this.forceStepForPiecemealExclusion(var2, var8, var5, var6);
        String var10 = var9.writeTheoryConstituent();
        System.out.println("tues ~~~~~~~tc_string is " + var10);
        if (!var10.equals("")) {
            var7.addElement(var9);
        } else {
            System.out.println("tues -- ");
            var7 = this.performCounterexampleBarring(var1, var2, var3, var4, var5, var6);
        }

        return var7;
    }

    public Vector performCounterexampleBarring(String var1, Concept var2, Concept var3, Vector var4, Theory var5, AgentWindow var6) {
        Vector var7 = new Vector();
        if (!this.use_counterexample_barring) {
            return var7;
        } else {
            System.out.println("I couldn't find a concept, so trying counterexample-barring");
            var6.writeToFrontEnd("I couldn't find a concept, so trying counterexample-barring");
            var5.make_equivalences_from_equality = false;
            Concept var8 = this.makeConceptFromVector1(var4, var5);
            var8.lakatos_method = "counterexample-barring";
            boolean var9 = false;

            Concept var11;
            for(int var10 = 0; var10 < var5.concepts.size(); ++var10) {
                var11 = (Concept)var5.concepts.elementAt(var10);
                if (var11.id == var8.id) {
                    var9 = true;
                    break;
                }

                for(int var12 = 0; var12 < var11.conjectured_equivalent_concepts.size(); ++var12) {
                    Concept var13 = (Concept)var11.conjectured_equivalent_concepts.elementAt(var12);
                    if (var13.id == var8.id) {
                        var9 = true;
                        break;
                    }
                }
            }

            var6.writeToFrontEnd("done the first theory formation step: got concept_to_exclude: " + var8.writeDefinition("otter"));
            var6.writeToFrontEnd("concept_to_exclude.id is " + var8.id);
            Vector var20 = new Vector();

            for(int var21 = 0; var21 < var4.size(); ++var21) {
                Entity var22 = (Entity)var4.elementAt(var21);
                var20.addElement(var22.toString());
            }

            var11 = this.findObjectOfInterestConcept(var5.concepts, var20);
            String var23 = "id_of_dummy1 = " + var8.id + " " + var11.id + " negate [] ";
            System.out.println("doing the second theory formation step: got core_concept " + var11.writeDefinition("otter"));
            System.out.println("core_concept.id is " + var11.id);
            System.out.println("agenda_line1 is " + var23);
            var6.writeToFrontEnd("doing the second theory formation step: got core_concept " + var11.writeDefinition("otter"));
            var6.writeToFrontEnd("core_concept.id is " + var11.id);
            var6.writeToFrontEnd("agenda_line1 is " + var23);
            this.agenda.addForcedStep(var23, false);
            Step var24 = this.guider.forceStep(var23, var5);
            new TheoryConstituent();
            TheoryConstituent var14 = var24.result_of_step;
            System.out.println("2nd step: result_of_step_explanation is " + var24.result_of_step_explanation);
            var6.writeToFrontEnd("2nd step: result_of_step_explanation is " + var24.result_of_step_explanation);
            if (var14 instanceof Concept) {
                Concept var15 = (Concept)var14;
                System.out.println("done the second theory formation step: got new_concept: " + var15.writeDefinition());
                System.out.println("new_concept.id is " + var15.id);
                String var16 = "id_of_dummy1 = " + var15.id + " " + var2.id + " compose [1] ";
                System.out.println("now starting the third theory formation step, with agenda_line: " + var16);
                var6.writeToFrontEnd("done the second theory formation step: got new_concept: " + var15.writeDefinition());
                var6.writeToFrontEnd("new_concept.id is " + var15.id);
                var6.writeToFrontEnd("now starting the third theory formation step, with agenda_line: " + var16);
                Step var17 = this.guider.forceStep(var16, var5);
                System.out.println("3rd step: result_of_step_explanation is " + var17.result_of_step_explanation);
                var6.writeToFrontEnd("3rd step: result_of_step_explanation is " + var17.result_of_step_explanation);
                new TheoryConstituent();
                TheoryConstituent var18 = var17.result_of_step;
                if (var18 instanceof Conjecture) {
                    System.out.println("the result of forced step2 was a conjecture");
                    Conjecture var19 = (Conjecture)var18;
                    System.out.println("new_conj is " + var19.writeConjecture());
                }

                System.out.println("3rd step: tc_dummy2 is  " + var18.writeTheoryConstituent());
                var6.writeToFrontEnd("3rd step: tc_dummy2 is  " + var18.writeTheoryConstituent());
                if (var18 instanceof Concept && var1.equals("equiv")) {
                    System.out.println("tc_dummy2 is a Concept");
                    Equivalence var28 = new Equivalence();
                    var28.lh_concept = (Concept)var18;
                    var28.rh_concept = var3;
                    var6.writeToFrontEnd("formed new concept P and -C, i.e. " + var28.rh_concept.writeDefinition("ascii"));
                    var7.addElement(var28);
                    System.out.println("YO  - new equiv is " + var28.writeConjecture("ascii"));
                }

                if (var18 instanceof Concept && var1.equals("imp")) {
                    System.out.println("tc_dummy2 is a Concept");
                    Implication var29 = new Implication();
                    var29.lh_concept = (Concept)var18;
                    var29.rh_concept = var3;
                    System.out.println("imp.lh_concept is " + var29.lh_concept.writeDefinition("ascii"));
                    System.out.println("imp.rh_concept is " + var29.rh_concept.writeDefinition("ascii"));
                    var6.writeToFrontEnd("formed new concept P and -C, i.e. " + var29.lh_concept.writeDefinition("ascii"));
                    var7.addElement(var29);
                    System.out.println("YO  - new imp is " + var29.writeConjecture("ascii"));
                }

                NonExists var30;
                if (var18 instanceof Concept && var1.equals("nonexists")) {
                    System.out.println("TESTME - tc_dummy2 is a Concept");
                    var30 = new NonExists();
                    var30.concept = (Concept)var18;
                    var7.addElement(var30);
                }

                if (var18 instanceof NonExists) {
                    var30 = (NonExists)var18;
                    var7.addElement(var30);
                }
            }

            if (var14 instanceof Conjecture) {
                Conjecture var25 = (Conjecture)var14;
                var7.addElement(var25);
            }

            var5.make_equivalences_from_equality = var5.front_end.make_equivalences_from_equality_check.getState();
            if (!var7.isEmpty()) {
                for(int var26 = 0; var26 < var7.size(); ++var26) {
                    Conjecture var27 = (Conjecture)var7.elementAt(var26);
                    var27.lakatos_method = "counterexample_barring";
                }
            }

            return var7;
        }
    }

    public Vector piecemealExclusion(Conjecture var1, Concept var2, Concept var3, Theory var4, AgentWindow var5) {
        Vector var6 = new Vector();
        new Vector();
        if (var1 instanceof NearEquivalence) {
            NearEquivalence var8 = (NearEquivalence)var1;
            Concept var9 = var8.lh_concept;
            Concept var10 = var8.rh_concept;
            Vector var11 = this.fromEntitiesToStrings(var8.counterexamples);
            boolean var12 = var9.positivesContainOneOf(var11);
            boolean var13 = var10.positivesContainOneOf(var11);
            System.out.println("counters_on_right is " + var13);
            Object var14 = null;
            Object var15 = null;
            Vector var16;
            TheoryConstituent var17;
            String var18;
            if (var12 && var13) {
                var16 = var2.getSubset(var11);
                var5.writeToFrontEnd("counters_on_lhs are " + var16);
                var17 = this.forceStepForPiecemealExclusion(var2, var16, var4, var5);
                var18 = var17.writeTheoryConstituent();
                Vector var19 = var3.getSubset(var11);
                var5.writeToFrontEnd("counters_on_rhs are " + var19);
                TheoryConstituent var20 = this.forceStepForPiecemealExclusion(var3, var16, var4, var5);
                String var21 = var20.writeTheoryConstituent();
                if (!var18.equals("")) {
                    var6.addElement(var17);
                }

                if (!var21.equals("")) {
                    var6.addElement(var20);
                }

                if (var18.equals("") && var21.equals("")) {
                    Concept var22 = this.makeConceptFromVector(var16, var4);
                    Concept var23 = this.makeConceptFromVector(var19, var4);
                    var5.writeToFrontEnd("looking for a concept to cover the lhs counters - got " + var22.writeDefinition("ascii"));
                    var5.writeToFrontEnd("looking for a concept to cover the rhs counters - got " + var23.writeDefinition("ascii"));
                    new Concept();

                    for(int var25 = 0; var25 < var4.concepts.size(); ++var25) {
                        Concept var26 = (Concept)var4.concepts.elementAt(var25);
                        if (var26.position_in_theory == 0) {
                            break;
                        }
                    }

                    String var40 = "id_of_dummy1 = " + var22.id + " " + var2.id + " negate [] ";
                    String var41 = "id_of_dummy2 = " + var23.id + " " + var3.id + " negate [] ";
                    new TheoryConstituent();
                    new TheoryConstituent();
                    this.agenda.addForcedStep(var40, false);
                    Step var29 = this.guider.forceStep(var40, var4);
                    TheoryConstituent var27 = var29.result_of_step;
                    this.agenda.addForcedStep(var41, false);
                    Step var30 = this.guider.forceStep(var41, var4);
                    TheoryConstituent var28 = var30.result_of_step;
                    if (var27 instanceof Concept && var28 instanceof Concept) {
                        Equivalence var31 = new Equivalence();
                        var31.lh_concept = (Concept)var27;
                        var31.rh_concept = (Concept)var28;
                        var5.writeToFrontEnd("formed new concept: P and -C1, i.e. " + var31.lh_concept.writeDefinition("ascii"));
                        var5.writeToFrontEnd("formed new concept: Q and -C2, i.e. " + var31.rh_concept.writeDefinition("ascii"));
                        var6.addElement(var31);
                    }
                }
            }

            Concept var34;
            String var35;
            TheoryConstituent var36;
            Step var37;
            Equivalence var38;
            if (var12 && !var13) {
                var16 = this.fromEntitiesToStrings(var8.counterexamples);
                var5.writeToFrontEnd("just got counters on left: " + var16);
                var17 = this.forceStepForPiecemealExclusion(var2, var16, var4, var5);
                var18 = var17.writeTheoryConstituent();
                if (!var18.equals("")) {
                    var6.addElement(var17);
                } else {
                    var34 = this.makeConceptFromVector1(var8.counterexamples, var4);
                    var34.lakatos_method = "piecemeal-exclusion";
                    var5.writeToFrontEnd(" MONDAY ---- 1: looking for a concept to cover the counters - got " + var34.writeDefinition("ascii"));
                    var35 = "id_of_dummy = " + var34.id + " " + var2.id + " negate [] ";
                    new TheoryConstituent();
                    this.agenda.addForcedStep(var35, false);
                    var37 = this.guider.forceStep(var35, var4);
                    var36 = var37.result_of_step;
                    if (var36 instanceof Concept) {
                        var38 = new Equivalence();
                        var38.lh_concept = (Concept)var36;
                        var5.writeToFrontEnd("formed new concept P and -C, i.e. " + var38.lh_concept.writeDefinition("ascii"));
                        var38.rh_concept = var3;
                        var6.addElement(var38);
                    }
                }
            }

            if (!var12 && var13) {
                var16 = this.fromEntitiesToStrings(var8.counterexamples);
                var5.writeToFrontEnd("just got counters on right: " + var16);
                var17 = this.forceStepForPiecemealExclusion(var3, var16, var4, var5);
                var18 = var17.writeTheoryConstituent();
                if (!var18.equals("")) {
                    var6.addElement(var17);
                } else {
                    var34 = this.makeConceptFromVector1(var8.counterexamples, var4);
                    var34.lakatos_method = "piecemeal-exclusion";
                    var5.writeToFrontEnd("---------couldn't find a concept, so trying counterexample-barring - made concept " + var34.writeDefinition("ascii"));
                    var35 = "id_of_dummy = " + var34.id + " " + var3.id + " negate [] ";
                    new TheoryConstituent();
                    this.agenda.addForcedStep(var35, false);
                    var37 = this.guider.forceStep(var35, var4);
                    var36 = var37.result_of_step;
                    if (var36 instanceof Concept) {
                        var38 = new Equivalence();
                        var38.lh_concept = var2;
                        var38.rh_concept = (Concept)var36;
                        var5.writeToFrontEnd("formed new concept P and -C, i.e. " + var38.rh_concept.writeDefinition("ascii"));
                        var6.addElement(var38);
                    }

                    if (var36 instanceof Conjecture) {
                        Conjecture var39 = (Conjecture)var36;
                        var6.addElement(var39);
                    }
                }
            }
        }

        var5.writeToFrontEnd("formed new conjectures:");

        for(int var32 = 0; var32 < var6.size(); ++var32) {
            if (!(var6.elementAt(var32) instanceof Conjecture)) {
                var5.writeToFrontEnd(var6.elementAt(var32) + "is not a conj");
            }

            Conjecture var33 = (Conjecture)var6.elementAt(var32);
            var5.writeToFrontEnd(var33.writeConjecture("ascii"));
        }

        return var6;
    }

    public TheoryConstituent forceStepForPiecemealExclusionAvoidingConcept(Concept var1, Vector var2, Theory var3, AgentWindow var4) {
        TheoryConstituent var5 = new TheoryConstituent();
        Concept var6 = this.getNewConceptWhichExactlyCoversGivenEntities(var2, var1);
        if (var6.writeDefinition("ascii").equals("")) {
            return var5;
        } else {
            var4.writeToFrontEnd("MONDAY ------------------  2: looking for a concept to cover the counters - got " + var6.writeDefinition("ascii"));
            String var7 = "id_of_dummy = " + var6.id + " " + var1.id + " negate [] ";
            this.agenda.addForcedStep(var7, false);
            Step var8 = this.guider.forceStep(var7, var3);
            var5 = var8.result_of_step;
            return var5;
        }
    }

    public TheoryConstituent forceStepForPiecemealExclusion(Concept var1, Vector var2, Theory var3, AgentWindow var4) {
        TheoryConstituent var5 = new TheoryConstituent();
        Concept var6 = this.getConceptWhichExactlyCoversGivenEntities(var2);
        if (var6.writeDefinition("ascii").equals("")) {
            return var5;
        } else {
            var4.writeToFrontEnd("MONDAY  --------------  3: looking for a concept to cover the counters - got " + var6.writeDefinition("ascii"));
            var4.writeToFrontEnd("want to form the concept " + var1.writeDefinition() + " and not " + var6.writeDefinition());
            Concept var14;
            if (var1.is_object_of_interest_concept) {
                String var7 = "id_of_dummy = " + var6.id + " " + var1.id + " negate [] ";
                var4.writeToFrontEnd("MONDAY  -------------- agenda_line is " + var7);
                this.agenda.addForcedStep(var7, false);
                Step var8 = this.guider.forceStep(var7, var3);
                var5 = var8.result_of_step;
            } else {
                var14 = new Concept();

                for(int var15 = 0; var15 < this.concepts.size(); ++var15) {
                    Concept var9 = (Concept)this.concepts.elementAt(var15);
                    boolean var10 = var9.domain.equals(var6.domain);
                    if (var9.is_object_of_interest_concept && var10) {
                        var14 = var9;
                        break;
                    }
                }

                String var17 = "id_of_dummy1 = " + var6.id + " " + var14.id + " negate [] ";
                this.agenda.addForcedStep(var17, false);
                Step var18 = this.guider.forceStep(var17, var3);
                new TheoryConstituent();
                TheoryConstituent var19 = var18.result_of_step;
                if (var19 instanceof Concept) {
                    Concept var11 = (Concept)var19;
                    var11.lakatos_method = "piecemeal_exclusion";
                    String var12 = "id_of_dummy2 = " + var1.id + " " + var11.id + " compose [1] ";
                    Step var13 = this.guider.forceStep(var12, var3);
                    var5 = var13.result_of_step;
                }
            }

            if (var5 instanceof Conjecture) {
                Conjecture var16 = (Conjecture)var5;
            }

            if (var5 instanceof Concept) {
                var4.writeToFrontEnd("MONDAY  - we've got a concept:");
                var14 = (Concept)var5;
                var14.lakatos_method = "piecemeal_exclusion";
                var4.writeToFrontEnd(var14.writeDefinition());
                System.out.println("MONDAY(here) -------------- concept output is " + var14.writeDefinition());
            }

            var4.writeToFrontEnd("MONDAY  -------------- output is " + var5);
            if (var5 instanceof TheoryConstituent) {
                var4.writeToFrontEnd("MONDAY  -------------- output is " + var5.writeTheoryConstituent());
            }

            return var5;
        }
    }

    public Concept makeConceptForPiecemealExclusion(Concept var1, Vector var2, Theory var3) {
        System.out.println("started makeConceptForPiecemealExclusion");
        Concept var4 = new Concept();
        System.out.println("concept_for_pm_ex is " + var4.writeDefinition("ascii"));
        Concept var5 = this.getConceptWhichExactlyCoversGivenEntities(var2);
        String var6 = "id_of_dummy1 = " + var5.id + " " + var1.id + " negate [] ";
        System.out.println("agenda_line1 is " + var6);
        this.agenda.addForcedStep(var6, false);
        Step var7 = this.guider.forceStep(var6, var3);
        System.out.println("forcedstep was :" + var7);
        System.out.println("forcedstep.result_of_step_explanation was :" + var7.result_of_step_explanation);
        TheoryConstituent var8 = var7.result_of_step;
        System.out.println("tc_result_of_step was :" + var8);
        System.out.println("finished makeConceptForPiecemealExclusion");
        if (var4.writeDefinition("ascii").equals("")) {
            System.out.println("concept_for_pm_ex is null (probably haven't done step yet)");
        }

        System.out.println("concept_for_pm_ex is " + var4.writeDefinition("ascii"));
        return var4;
    }

    public Vector strategicWithdrawal(Conjecture var1, Theory var2, AgentWindow var3) {
        System.out.println("___________________________ \n\n");
        System.out.println("started strategicWithdrawal");
        System.out.println("conj_to_modify is " + var1.id + ": " + var1.writeConjecture("ascii") + ", ");
        Vector var4 = new Vector();
        Concept var6;
        Concept var7;
        if (var1 instanceof NearEquivalence) {
            System.out.println("starting with a NearEquivalence");
            NearEquivalence var5 = (NearEquivalence)var1;
            var6 = var5.lh_concept;
            var7 = var5.rh_concept;
            var4 = this.strategicWithdrawal((NearEquivalence)var1, var6, var7, var2, var3);
        }

        if (var1 instanceof Equivalence) {
            System.out.println("starting with a Equivalence");
            Equivalence var8 = (Equivalence)var1;
            var6 = var8.lh_concept;
            var7 = var8.rh_concept;
            var4 = this.strategicWithdrawal(var8, var6, var7, var2, var3);
        }

        if (var1 instanceof NearImplication) {
            System.out.println("starting with an Implication");
            NearImplication var9 = (NearImplication)var1;
            var6 = var9.lh_concept;
            var7 = var9.rh_concept;
            var4 = this.strategicWithdrawal(var9, var6, var7, var2, var3);
        }

        if (var1 instanceof Implication) {
            System.out.println("starting with an Implication");
            Implication var10 = (Implication)var1;
            var6 = var10.lh_concept;
            var7 = var10.rh_concept;
            var4 = this.strategicWithdrawal(var10, var6, var7, var2, var3);
        }

        if (var1 instanceof NonExists) {
            System.out.println("starting with a NonExists");
            NonExists var11 = (NonExists)var1;
            var6 = var11.concept;
            var4 = this.strategicWithdrawal(var11, var6, var2, var3);
        }

        System.out.println("finished strategicWithdrawal");
        System.out.println("___________________________ \n\n");
        return var4;
    }

    public Vector strategicWithdrawal(Conjecture var1, Concept var2, Theory var3, AgentWindow var4) {
        Vector var5 = new Vector();
        new String();
        String var6;
        if (var1.getCountersToConjecture().isEmpty()) {
            var6 = var1.writeConjecture() + " holds for all my examples";
        } else {
            var6 = "cannot perform strategic withdrawal on a nonexists conjecture, sorry.";
        }

        var4.writeToFrontEnd("MONDAY -- my response_to_conj is " + var6);
        var5.addElement(var6);
        return var5;
    }

    public Vector strategicWithdrawal(Conjecture var1, Concept var2, Concept var3, Theory var4, AgentWindow var5) {
        Vector var6 = new Vector();
        new Vector();
        String var9;
        if (var1 instanceof Equivalence) {
            Equivalence var8 = (Equivalence)var1;
            var9 = var1.writeConjecture("ascii") + " holds for all my examples";
            System.out.println("OI, YOU ---- conj_to_modify is an Equiv, so returning response_to_conj = " + var9);
            var6.addElement(var9);
        }

        if (var1 instanceof NearEquivalence) {
            NearEquivalence var21 = (NearEquivalence)var1;
            Concept var23 = var21.lh_concept;
            Concept var10 = var21.rh_concept;
            System.out.println("counters are: " + var21.counterexamples);
            Vector var11 = this.fromEntitiesToStrings(var21.counterexamples);
            boolean var12 = var23.positivesContainOneOf(var11);
            System.out.println("counters to reconstructed conjecture are " + var21.counterexamples);
            System.out.println("counters_on_left is " + var12);
            boolean var13 = var10.positivesContainOneOf(var11);
            System.out.println("counters_on_right is " + var13);
            var2.getSubset(var11);
            this.getPositives(var21.counterexamples, var2);
            Vector var16 = var23.getSubset(var10.getPositives());
            Concept var17;
            Conjecture var18;
            Conjecture var19;
            if (var12 && var13) {
                var5.writeToFrontEnd("got counters on both sides: " + var11);
                var17 = this.getConceptWhichExactlyCoversGivenEntities(var16);
                var17.lakatos_method = "strategic-withdrawal";
                if (var17.writeDefinition("ascii").equals("")) {
                    return var6;
                }

                var5.writeToFrontEnd("looking for a concept to cover the positives, i.e." + var16);
                var5.writeToFrontEnd("found " + var17.writeDefinition("ascii"));
                var18 = this.makeImpConj(var17, var3);
                var19 = this.makeImpConj(var17, var2);
                var6.addElement(var18);
                var6.addElement(var19);
            } else {
                Conjecture var20;
                if (var12) {
                    var5.writeToFrontEnd("just got counters on left: " + var11);
                    var17 = this.getNewConceptWhichExactlyCoversGivenEntities(var16, var10);
                    var5.writeToFrontEnd("looking for a concept to cover the positives, i.e." + var16);
                    var5.writeToFrontEnd("found " + var17.writeDefinition("ascii"));
                    if (var17.writeDefinition("ascii").equals("")) {
                        return var6;
                    }

                    var18 = this.makeEquivConj(var17, var3);
                    var19 = this.makeImpConj(var17, var3);
                    var20 = this.makeImpConj(var17, var2);
                    var6.addElement(var18);
                    var6.addElement(var19);
                    var6.addElement(var20);
                } else if (var13) {
                    var5.writeToFrontEnd("just got counters on right: " + var11);
                    var17 = this.getNewConceptWhichExactlyCoversGivenEntities(var16, var23);
                    var5.writeToFrontEnd("looking for a concept to cover the positives, i.e." + var16);
                    var5.writeToFrontEnd("found " + var17.writeDefinition("ascii"));
                    if (var17.writeDefinition("ascii").equals("")) {
                        return var6;
                    }

                    var18 = this.makeEquivConj(var17, var2);
                    var19 = this.makeImpConj(var17, var2);
                    var20 = this.makeImpConj(var17, var3);
                    var6.addElement(var18);
                    var6.addElement(var19);
                    var6.addElement(var20);
                }
            }
        }

        if (var6.isEmpty()) {
            System.out.println("output from SW is EMPTY");
        } else {
            System.out.println("output from SW is ");
            var5.writeToFrontEnd("output from SW is ");

            for(int var22 = 0; var22 < var6.size(); ++var22) {
                if (var6.elementAt(var22) instanceof Conjecture) {
                    Conjecture var24 = (Conjecture)var6.elementAt(var22);
                    System.out.println(var24.id + ": " + var24.writeConjecture("ascii") + ", ");
                    var5.writeToFrontEnd(var24.id + ": " + var24.writeConjecture("ascii") + ", ");
                }

                if (var6.elementAt(var22) instanceof String) {
                    var9 = (String)var6.elementAt(var22);
                    System.out.println(var9);
                    var5.writeToFrontEnd(var9);
                }
            }
        }

        return var6;
    }

    public Request monsterBarring(Entity var1, String var2, Theory var3, Conjecture var4, String var5, AgentWindow var6) {
        Request var7 = new Request();
        var6.writeToFrontEnd("started monsterBarring(" + var1.name + ", " + var2 + ")");
        if (var5.equals("vaguetovague")) {
            var7 = this.monsterBarringVagueToVague(var1, var2, var3, var4, var6);
        }

        if (var5.equals("vaguetospecific")) {
            var7 = this.monsterBarringVagueToSpecific(var1, var2, var3, var4, var6);
        }

        if (var5.equals("specifictospecific")) {
            var7 = this.monsterBarringSpecificToSpecific(var1, var2, var3, var4, var6);
        }

        return var7;
    }

    public Request monsterBarringVagueToVague(Entity var1, String var2, Theory var3, Conjecture var4, AgentWindow var5) {
        Request var6 = new Request();
        var6.motivation = new Motivation(var1, "monster-barring");
        var6.motivation.conjecture_under_discussion = var4;
        var6.string_object = var1.toString() + " is breaking many of my conjectures. It would be neater to bar it from the concept of " + var2 + " than from all my conjectures";
        var5.writeToFrontEnd("request at the end of m-b is " + var6.toString());
        return var6;
    }

    public Request monsterBarringVagueToSpecific(Entity var1, String var2, Theory var3, Conjecture var4, AgentWindow var5) {
        Request var6 = new Request();
        Vector var7 = new Vector();
        Concept var8 = this.getSpecificFromVagueDefinition(var1, false, var2, var3, var4, var5);
        var7.addElement(var8);
        var6.motivation = new Motivation(var1, "monster-barring");
        var5.writeToFrontEnd("\n\n\n\n\n\n *** just setting the conjecture_under_discussion ");
        var6.motivation.conjecture_under_discussion = var4;
        var6.string_object = var1.toString() + " is not a " + var2 + ". A " + var2 + " is something which satisfies " + var8.writeDefinition();
        var6.theory_constituent_vector = var7;
        var5.writeToFrontEnd("request at the end of m-b is " + var6.toString());
        return var6;
    }

    public Request monsterBarringSpecificToSpecific(Entity var1, String var2, Theory var3, Conjecture var4, AgentWindow var5) {
        return new Request();
    }

    public Concept getSpecificFromVagueDefinition(Entity var1, boolean var2, String var3, Theory var4, Conjecture var5, AgentWindow var6) {
        Concept var7 = new Concept();
        Vector var8 = new Vector();

        for(int var9 = 0; var9 < var4.entities.size(); ++var9) {
            Entity var10 = (Entity)var4.entities.elementAt(var9);
            var8.addElement(var10.toString());
        }

        Concept var19 = var4.getObjectOfInterestConcept();
        this.generateDefinitionGivenEntities(var8, var19, var1.toString());
        Vector var11 = this.findConjecturedEquivalenceConcepts(var4.conjectures, var19);
        boolean var12 = false;
        boolean var13 = true;
        int var14;
        Concept var15;
        if (var12) {
            this.addConjecturedEquivalentConceptsToTheory(var11, var4);
            var6.writeToFrontEnd("conjectured_equivalent_concepts are: ");

            for(var14 = 0; var14 < var11.size(); ++var14) {
                var15 = (Concept)var11.elementAt(var14);
                var6.writeToFrontEnd(var15.writeDefinition("ascii"));
            }

            var4.make_equivalences_from_equality = false;
            var7 = this.makeConjunctionFromVector(var11, var4);
            var4.make_equivalences_from_equality = var4.front_end.make_equivalences_from_equality_check.getState();
            var6.writeToFrontEnd("in method1; new_suggested_concept is " + var7.writeDefinition());
        }

        if (var13) {
            var6.writeToFrontEnd("the monster_entity is " + var1);
            var6.writeToFrontEnd("conjectured_equivalent_concepts.size() is " + var11.size());

            Row var16;
            for(var14 = 0; var14 < var4.concepts.size(); ++var14) {
                var15 = (Concept)var4.concepts.elementAt(var14);
                var6.writeToFrontEnd("the " + var14 + "th concept in the theory is " + var15.writeDefinition());
                var16 = var15.calculateRow(var4.concepts, var1.toString());
                var6.writeToFrontEnd("row.entity is " + var16.entity);
                var6.writeToFrontEnd("row.tuples are " + var16.tuples);
            }

            for(var14 = 0; var14 < var11.size(); ++var14) {
                var15 = (Concept)var11.elementAt(var14);
                var6.writeToFrontEnd("the " + var14 + "th conjectured equivalent concept is " + var15.writeDefinition());
                var16 = var15.calculateRow(var4.concepts, var1.toString());
                var6.writeToFrontEnd("row.entity is " + var16.entity);
                var6.writeToFrontEnd("row.tuples are " + var16.tuples);
                boolean var18 = var16.tuples.isEmpty();
                var6.writeToFrontEnd("cover_monster is " + var2);
                var6.writeToFrontEnd("row.tuples.isEmpty() is " + var18);
                if (!var2 && !var16.tuples.isEmpty()) {
                    var6.writeToFrontEnd("1");
                    var11.removeElementAt(var14);
                    --var14;
                }

                if (var2 && var16.tuples.isEmpty()) {
                    var6.writeToFrontEnd("2");
                    var11.removeElementAt(var14);
                    --var14;
                }
            }

            var6.writeToFrontEnd("after check one, conjectured_equivalent_concepts are " + var11);
            var6.writeToFrontEnd("now going through the concepts on the conj to check they aren't the same ");
            Vector var22 = var5.conceptsInConjecture();

            int var20;
            Concept var21;
            for(var20 = 0; var20 < var11.size(); ++var20) {
                var21 = (Concept)var11.elementAt(var20);
                var6.writeToFrontEnd("our " + var20 + "th  conjectured_equivalent_concept is " + var21.writeDefinition());

                for(int var17 = 0; var17 < var22.size(); ++var17) {
                    Concept var23 = (Concept)var22.elementAt(var17);
                    if (var21.equals(var23.writeDefinition())) {
                        var6.writeToFrontEnd("current_concept is " + var21.writeDefinition() + " and concept is " + var23.writeDefinition());
                        var6.writeToFrontEnd("current_concept.equals(concept.writeDefinition()) is " + var21.equals(var23.writeDefinition()));
                        var6.writeToFrontEnd("3: conjectured_equivalent_concepts is " + var11);
                        var11.removeElementAt(var20);
                        var6.writeToFrontEnd("4: conjectured_equivalent_concepts is NOW " + var11);
                        --var20;
                    }
                }
            }

            var6.writeToFrontEnd("conjectured_equivalent_concepts are now: ");

            for(var20 = 0; var20 < var11.size(); ++var20) {
                var21 = (Concept)var11.elementAt(var20);
                var6.writeToFrontEnd(var21.writeDefinition());
            }

            var7 = this.getMostInterestingConcept(var11);
        }

        return var7;
    }

    public void addConjecturedEquivalentConceptsToTheory(Vector var1, Theory var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);
            boolean var5 = false;

            for(int var6 = 0; var6 < var2.concepts.size(); ++var6) {
                Concept var7 = (Concept)var2.concepts.elementAt(var6);
                if (var7.id.equals(var4.id)) {
                    var5 = true;
                    break;
                }
            }

            if (!var5) {
                var2.concepts.addElement(var4);
            }
        }

    }

    public boolean entityCoveredByAllConcepts(Vector var1, Entity var2, AgentWindow var3) {
        boolean var4 = false;

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            Concept var6 = (Concept)var1.elementAt(var5);

            for(int var7 = 0; var7 < var6.datatable.size(); ++var7) {
                Row var8 = (Row)var6.datatable.elementAt(var7);
                if (var8.entity.equals(var2.toString()) && !var8.tuples.isEmpty()) {
                    var4 = true;
                    break;
                }
            }

            if (!var4) {
                break;
            }
        }

        return var4;
    }

    public Concept makeConjunctionFromVector(Vector var1, Theory var2) {
        Concept var3 = new Concept();
        Vector var4 = new Vector();
        new Vector();

        while(!var1.isEmpty()) {
            if (var1.size() == 1) {
                var3 = (Concept)var1.elementAt(0);
                break;
            }

            if (var1.size() > 1) {
                Concept var6 = (Concept)var1.elementAt(0);
                Concept var7 = (Concept)var1.elementAt(1);
                var1.removeElementAt(0);
                var1.removeElementAt(0);
                var4.addElement(var6);
                var4.addElement(var7);
                var2.compose.allParameters(var4, var2);
                String var8 = "id_of_dummy1 = " + var6.id + " " + var7.id + " compose [1] ";
                Step var9 = this.guider.forceStep(var8, var2);
                new TheoryConstituent();
                TheoryConstituent var10 = var9.result_of_step;
                if (var10 instanceof Concept) {
                    Concept var11 = (Concept)var10;
                    var11.lakatos_method = "piecemeal_exclusion";
                    var1.insertElementAt(var11, 0);
                }
            }
        }

        return var3;
    }

    public TheoryConstituent testForce(Vector var1, Theory var2) {
        TheoryConstituent var3 = new TheoryConstituent();
        new Vector();
        Vector var5 = new Vector();

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            Concept var7 = (Concept)var1.elementAt(var6);
            if (var7.id.equals("group001")) {
                for(int var8 = 0; var8 < var1.size(); ++var8) {
                    Concept var9 = (Concept)var1.elementAt(var8);
                    if (var9.id.equals("g17_0")) {
                        var5.addElement(var7);
                        var5.addElement(var9);
                        var2.compose.allParameters(var5, var2);
                        String var10 = "id_of_dummy1 = " + var7.id + " " + var9.id + " compose [1] ";
                        Step var11 = this.guider.forceStep(var10, var2);
                        new TheoryConstituent();
                        TheoryConstituent var12 = var11.result_of_step;
                        if (var12 instanceof Concept) {
                            Concept var13 = (Concept)var12;
                            var1.insertElementAt(var13, 0);
                        } else if (var12 instanceof Equivalence) {
                            var1.insertElementAt(var7, 0);
                        } else if (var12 instanceof Conjecture) {
                            System.out.println(((Conjecture)var12).writeConjecture("ascii"));
                        } else if (var12 instanceof Entity) {
                            System.out.println(((Entity)var12).toString());
                        } else {
                            System.out.println("haven't got a theory constituent");
                        }
                        break;
                    }
                }
            }
        }

        return var3;
    }

    public Vector useMaceToGenerateCounters(Vector var1, String var2, AgentWindow var3) {
        Vector var4 = new Vector();
        String var5 = "-(" + var2 + ").";
        Vector var6 = new Vector();
        Vector var7 = new Vector();
        byte var8 = 8;
        byte var9 = 10;
        Mace var10 = new Mace();

        try {
            PrintWriter var11 = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            var11.println("set(auto).\nformula_list(usable).");

            for(int var12 = 0; var12 < var1.size(); ++var12) {
                var11.println((String)var1.elementAt(var12));
            }

            var11.println(var5);
            var11.println("end_of_list.");
            var11.close();
        } catch (Exception var17) {
            ;
        }

        int var18;
        for(var18 = 0; var18 < var8; ++var18) {
            try {
                Process var19 = Runtime.getRuntime().exec("./call_mace " + Integer.toString(var18) + " " + Integer.toString(var9));
                int var13 = var19.waitFor();
                var19.destroy();
                Hashtable var14 = this.getDataHashtable(var10, var3);
                if (var14 != null) {
                    String var15 = (String)var14.get("repn");
                    var6.addElement(var15);
                    var4.addElement(var14);
                }
            } catch (Exception var16) {
                ;
            }
        }

        for(var18 = 0; var18 < var6.size(); ++var18) {
            String var20 = (String)var6.elementAt(var18);
            var7.addElement(new Entity(var20));
        }

        var3.writeToFrontEnd("entities_contradicting_conjecture are " + var6);
        return var7;
    }

    public void getPositivesAndNegatives(Vector var1, String var2, AgentWindow var3) {
        Vector var4 = new Vector();
        String var5 = "-(all b c (b * c = c * b)).";
        Vector var6 = new Vector();
        Vector var7 = new Vector();
        byte var8 = 8;
        byte var9 = 10;
        Mace var10 = new Mace();

        PrintWriter var11;
        int var12;
        try {
            var11 = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            var11.println("set(auto).\nformula_list(usable).");

            for(var12 = 0; var12 < var1.size(); ++var12) {
                var11.println((String)var1.elementAt(var12));
            }

            var11.println(var5);
            var11.println("end_of_list.");
            var11.close();
        } catch (Exception var19) {
            ;
        }

        int var13;
        Hashtable var14;
        String var15;
        int var20;
        Process var21;
        for(var20 = 0; var20 < var8; ++var20) {
            try {
                var21 = Runtime.getRuntime().exec("./call_mace " + Integer.toString(var20) + " " + Integer.toString(var9));
                var13 = var21.waitFor();
                var21.destroy();
                var14 = this.getDataHashtable(var10, var3);
                if (var14 != null) {
                    var15 = (String)var14.get("repn");
                    var7.addElement(var15);
                    var4.addElement(var14);
                }
            } catch (Exception var17) {
                ;
            }
        }

        try {
            var11 = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            var11.println("set(auto).\nformula_list(usable).");

            for(var12 = 0; var12 < var1.size(); ++var12) {
                var11.println((String)var1.elementAt(var12));
            }

            var11.println(var2);
            var11.println("end_of_list.");
            var11.close();
        } catch (Exception var18) {
            ;
        }

        for(var20 = 0; var20 < var8; ++var20) {
            try {
                var21 = Runtime.getRuntime().exec("./call_mace " + Integer.toString(var20) + " " + Integer.toString(var9));
                var13 = var21.waitFor();
                var21.destroy();
                var14 = this.getDataHashtable(var10, var3);
                if (var14 != null) {
                    var15 = (String)var14.get("repn");
                    var6.addElement(var15);
                    var4.addElement(var14);
                }
            } catch (Exception var16) {
                var16.printStackTrace();
            }
        }

        var3.writeToFrontEnd("entities_supporting_conjecture are " + var6);
        var3.writeToFrontEnd("entities_contradicting_conjecture are " + var7);
    }

    public Hashtable getDataHashtable(Mace var1, AgentWindow var2) {
        String var3 = "";
        Hashtable var4 = var1.readModelFromMace("delmodel.out");
        Vector var5 = (Vector)var4.get("*");
        Vector var6 = (Vector)var4.get("+");
        if (var5 == null) {
            return null;
        } else {
            int var7;
            for(var7 = 0; var7 < var5.size(); ++var7) {
                var3 = var3 + (String)((Vector)var5.elementAt(var7)).elementAt(2);
            }

            if (var6 != null) {
                var3 = var3 + ":";

                for(var7 = 0; var7 < var6.size(); ++var7) {
                    var3 = var3 + (String)((Vector)var6.elementAt(var7)).elementAt(2);
                }
            }

            var4.put("repn", var3);
            return var4;
        }
    }

    public Vector getAxiomsForMace(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);
            String var5 = var4.writeDefinition("otter") + ".";
            var2.addElement(var5);
        }

        return var2;
    }

    public Concept getMostInterestingConcept(Vector var1) {
        Concept var2 = new Concept();
        double var3 = 0.0D;

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            Concept var6 = (Concept)var1.elementAt(var5);
            if (var6.interestingness >= var3) {
                var2 = var6;
            }
        }

        return var2;
    }

    public Vector findConjecturedEquivalenceConcepts(Vector var1, Concept var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Conjecture var5 = (Conjecture)var1.elementAt(var4);
            if (var5 instanceof Equivalence) {
                Equivalence var6 = (Equivalence)var5;
                if (var6.lh_concept.equals(var2)) {
                    var3.addElement(var6.rh_concept);
                }
            }
        }

        return var3;
    }

    public double percentageConjecturesBrokenByEntity(Theory var1, Entity var2, AgentWindow var3) {
        int var6 = 0;

        for(int var7 = 0; var7 < var1.conjectures.size(); ++var7) {
            Conjecture var8 = (Conjecture)var1.conjectures.elementAt(var7);
            if (var8.conjectureBrokenByEntity(var2.toString(), var1.concepts)) {
                var3.writeToFrontEnd(var2.toString() + " is a counter to " + var8.writeConjecture());
                ++var6;
            }
        }

        double var4 = (double)(var6 * 100 / var1.conjectures.size());
        return var4;
    }

    public String conjectureBrokenByCulpritEntity(Theory var1, AgentWindow var2, int var3) {
        int var4 = var1.conjectures.size();
        int var5 = var3 / 100 * var4;
        int var6 = 0;

        for(int var7 = 0; var6 < var5 && var7 < var4; ++var7) {
            Conjecture var8 = (Conjecture)var1.conjectures.elementAt(var7);
            String var9 = this.conjectureBrokenByCulpritEntity(var1, var8, var2);
            if (var9.equals("yes")) {
                ++var6;
            }
        }

        var2.writeToFrontEnd("The culprit entity forces other entities into being counterexamples for " + var6 + " of my conjectures");
        boolean var10 = var6 > var5;
        boolean var11 = var6 <= var5;
        if (var10) {
            var2.writeToFrontEnd(var6 + " is greater than " + var5 + ", which is the minimum number of conjectures neededed, so I'm going to perform monster-barring");
        }

        if (var11) {
            var2.writeToFrontEnd(var6 + " is less than or equal to " + var5 + ", which is the minimum number of conjectures neededed, so I'm not going to perform monster-barring");
        }

        return var6 > var5 ? "yes" : "no";
    }

    public String conjectureBrokenByCulpritEntity(Theory var1, Conjecture var2, AgentWindow var3) {
        String var4 = "no";
        var3.writeToFrontEnd("just started conjectureBrokenByCulpritEntity");
        Conjecture var5 = var2.reconstructConjecture(var1, var3);
        if (var5 instanceof NonExists) {
            NonExists var6 = (NonExists)var5;
            var3.writeToFrontEnd("got a nonExists conj - " + var6.writeConjecture("ascii"));
            var3.writeToFrontEnd("ne_conj.getCountersToConjecture() are " + var6.getCountersToConjecture());
            var3.writeToFrontEnd("ne_conj.concept.datatable.toTable() is " + var6.concept.datatable.toTable());
            var3.writeToFrontEnd("ne_conj.concept.datatable.isEmpty() is " + var6.concept.datatable.isEmpty());
            if (!var6.concept.datatable.isEmpty()) {
                var3.writeToFrontEnd("its datatable isn't empty");
                Row var7 = (Row)var6.concept.datatable.elementAt(0);
                Vector var8 = (Vector)var7.tuples.elementAt(0);
                var3.writeToFrontEnd("vector_in_tuple is " + var8);
                if (!var8.isEmpty()) {
                    String var9 = (String)var8.elementAt(0);
                    var3.writeToFrontEnd("entity_on_row_0 is " + var9);
                    var6.concept.datatable.sameExampleForEveryEntity(var1, var9, var3);
                    var4 = var9;
                }
            }
        }

        var3.writeToFrontEnd("just finished conjectureBrokenByCulpritEntity... returning " + var4);
        return var4;
    }

    public boolean entityIsCulpritBreaker(Theory var1, Conjecture var2, AgentWindow var3, Entity var4) {
        Conjecture var5 = var2.reconstructConjecture(var1, var3);
        Vector var6 = var5.getCountersToConjecture();
        int var7 = var6.size();
        var3.writeToFrontEnd("counters_without_culprit are " + var6);
        var3.writeToFrontEnd("counters_without_culprit.size() is " + var6.size());
        var3.writeToFrontEnd("theory.entities.size() is " + var1.entities.size());
        Conjecture var8 = var2.reconstructConjectureWithNewEntity(var1, var3, var4);
        Vector var9 = var8.getCountersToConjecture();
        int var10 = var9.size();
        var3.writeToFrontEnd("counters_with_culprit are " + var9);
        var3.writeToFrontEnd("counters_with_culprit.size() is " + var9.size());
        boolean var11 = var7 + 1 < var10;
        var3.writeToFrontEnd("num_counters_without_culprit+1<num_counters_with_culprit is " + var11);
        return var7 + 1 < var10;
    }

    public TheoryConstituent generateDefinitionGivenEntities(Vector var1, Concept var2, String var3) {
        TheoryConstituent var4 = new TheoryConstituent();
        Concept var5 = this.getNewConceptWhichExactlyCoversGivenEntities(var1, var2);
        return (TheoryConstituent)(var5.positivesContains(var3) ? var4 : var5);
    }

    public ProofScheme lemmaIncorporation(Theory var1, ProofScheme var2, AgentWindow var3) {
        var3.writeToFrontEnd("The initial proofscheme is:");
        var3.writeToFrontEnd(var2);

        int var4;
        for(var4 = 0; var4 < var2.conj.counterexamples.size(); ++var4) {
            Entity var5 = (Entity)var2.conj.counterexamples.elementAt(var4);
            System.out.println(var5.toString());
        }

        for(var4 = 0; var4 < var2.proof_vector.size(); ++var4) {
            Conjecture var10 = (Conjecture)var2.proof_vector.elementAt(var4);
        }

        ProofScheme var9 = new ProofScheme();
        Vector var11 = var2.conj.counterexamples;
        boolean var6 = !var11.isEmpty();
        Conjecture var7;
        boolean var8;
        if (var6) {
            var7 = this.getFaultyLemma(var2, var11, var1);
            var8 = !var7.writeConjecture().equals("");
            if (var8) {
                var9 = this.globalAndLocalLemmaIncorporation(var1, var2, var7, var3);
            } else {
                var9 = this.hiddenLemmaIncorporation_new(var1, var2, var3);
            }
        }

        if (!var6) {
            var7 = this.countersInProofScheme(var2);
            var8 = !var7.writeConjecture().equals("");
            if (!var8) {
                System.out.println("I don't know of any global nor local counters, so I'm going to return the same proof I was given.");
            }

            if (var8) {
                var9 = this.onlyLocalLemmaIncorporation(var1, var2, var7, var3);
            }
        }

        if (var9.isEmpty()) {
            var3.writeToFrontEnd("I can't find anything wrong with the proof scheme, so I'm returning that one");
            return var2;
        } else {
            System.out.println("finishing lemma-incorporation - returning");
            System.out.println("improved_proofscheme.conj is " + var9.conj.writeConjecture());
            System.out.println("improved_proofscheme.conj.counterexamples are: ");

            int var12;
            for(var12 = 0; var12 < var9.conj.counterexamples.size(); ++var12) {
                Entity var13 = (Entity)var9.conj.counterexamples.elementAt(var12);
                System.out.println(var13.toString());
            }

            System.out.println("improved_proofscheme.proof_vector is:");

            for(var12 = 0; var12 < var9.proof_vector.size(); ++var12) {
                Conjecture var14 = (Conjecture)var9.proof_vector.elementAt(var12);
                System.out.println("(" + var12 + ") " + var14.writeConjecture());
            }

            var3.writeToFrontEnd("The improved proofscheme is:");
            var3.writeToFrontEnd(var9);
            return var9;
        }
    }

    public ProofScheme globalAndLocalLemmaIncorporation(Theory var1, ProofScheme var2, Conjecture var3, AgentWindow var4) {
        System.out.println("starting globalAndLocalLemmaIncorporation ");
        ProofScheme var5 = new ProofScheme();
        var5.proof_vector = (Vector)var2.proof_vector.clone();
        Conjecture var6 = var2.conj;
        System.out.println("globalAndLocal l-inc -- global_conj is " + var6.writeConjecture());
        System.out.println("globalAndLocal l-inc -- faulty_lemma is " + var3.writeConjecture());
        Vector var7 = new Vector();

        for(int var8 = 0; var8 < var6.counterexamples.size(); ++var8) {
            Entity var9 = (Entity)var6.counterexamples.elementAt(var8);
            var7.addElement(var9.toString());
        }

        Concept var17 = this.findObjectOfInterestConcept(var1.concepts, var7);
        System.out.println("globalAndLocal l-inc -- core_concept is " + var17.writeDefinition());
        String var11;
        int var12;
        Step var13;
        TheoryConstituent var14;
        Concept var15;
        Implication var16;
        NearImplication var18;
        String var20;
        if (var6 instanceof NearImplication && var3 instanceof NearImplication) {
            System.out.println("l-inc: got 2 nimps");
            var18 = (NearImplication)var6;
            NearImplication var10 = (NearImplication)var3;
            var11 = " compose [1";

            for(var12 = 1; var12 < var10.rh_concept.arity; ++var12) {
                var11 = var11 + ",0";
            }

            var11 = var11 + "] ";
            System.out.println("production_rule is " + var11);
            var20 = "id_of_dummy1 = " + var10.rh_concept.id + " " + var17.id + var11;
            System.out.println("agenda_line1 is " + var20);
            var13 = this.guider.forceStep(var20, var1);
            new TheoryConstituent();
            var14 = var13.result_of_step;
            System.out.println("globalAndLocal l-inc: tc_dummy1 is " + var14.writeTheoryConstituent());
            if (var14 instanceof Concept) {
                System.out.println("globalAndLocal l-inc: tc_dummy1 is a concept");
                var15 = (Concept)var14;
                var15.lakatos_method = "lemma-incorporation";
                var16 = new Implication();
                var16.lh_concept = var15;
                var16.rh_concept = var18.rh_concept;
                System.out.println("globalAndLocal l-inc: just made a new conjecture - imp=" + var16.writeConjecture());
                var5.conj = var16;
                System.out.println("globalAndLocal l-inc: setting the new proofscheme to have our new imp ");
            }
        }

        if (var6 instanceof NearImplication && var3 instanceof NearEquivalence) {
            System.out.println("l-inc: got a nimp and a neq");
            var18 = (NearImplication)var6;
            NearEquivalence var19 = (NearEquivalence)var3;
            var11 = " compose [1";

            for(var12 = 1; var12 < var19.rh_concept.arity; ++var12) {
                var11 = var11 + ",0";
            }

            var11 = var11 + "] ";
            System.out.println("production_rule is " + var11);
            var20 = "id_of_dummy1 = " + var19.rh_concept.id + " " + var17.id + var11;
            System.out.println("agenda_line1 is " + var20);
            var13 = this.guider.forceStep(var20, var1);
            new TheoryConstituent();
            var14 = var13.result_of_step;
            System.out.println("globalAndLocal l-inc: tc_dummy1 is " + var14.writeTheoryConstituent());
            if (var14 instanceof Concept) {
                System.out.println("globalAndLocal l-inc: tc_dummy1 is a concept");
                var15 = (Concept)var14;
                var15.lakatos_method = "lemma-incorporation";
                var16 = new Implication();
                var16.lh_concept = var15;
                var16.rh_concept = var18.rh_concept;
                System.out.println("globalAndLocal l-inc: just made a new conjecture - imp=" + var16.writeConjecture());
                var5.conj = var16;
                System.out.println("globalAndLocal l-inc: setting the new proofscheme to have our new imp ");
            }
        }

        System.out.println("finishing globalAndLocalLemmaIncorporation - returning " + var5.conj.writeConjecture());
        return var5;
    }

    public ProofScheme hiddenLemmaIncorporation_new(Theory var1, ProofScheme var2, AgentWindow var3) {
        new ProofScheme();
        System.out.println("into hiddenLemmaIncorporation");
        Entity var5 = new Entity();
        Object var6 = new Conjecture();
        Conjecture var7 = new Conjecture();
        ProofScheme var8 = new ProofScheme();
        boolean var9 = false;
        if (var2.conj.counterexamples.size() == 1) {
            var5 = (Entity)var2.conj.counterexamples.elementAt(0);
        }

        System.out.println("Tuesday morning -- got global_counter = " + var5.name);

        int var10;
        Conjecture var11;
        for(var10 = 0; var10 < var2.proof_vector.size(); ++var10) {
            var11 = (Conjecture)var2.proof_vector.elementAt(var10);
            if (var11 instanceof Implication) {
                System.out.println("Tues morning.............");
                Implication var12 = (Implication)var11;
                var7 = this.surprisingEntity(var5, var12, var1, var3);
                System.out.println("Given global_counterexample = " + var5.name + ", and imp =" + var12.writeConjecture() + ":  ");
                System.out.println("surprisingness 1 test returns " + var7.writeConjecture());
                if (var7 != null) {
                    var6 = var12;
                    var9 = true;
                    break;
                }
            }
        }

        if (!var9) {
            for(var10 = 0; var10 < var2.proof_vector.size(); ++var10) {
                var11 = (Conjecture)var2.proof_vector.elementAt(var10);
                boolean var13 = this.antecedentFailure(var5, var11);
                if (var13) {
                    var6 = var11;
                    var7 = this.fixHiddenFaultyLemma(var11, var2, var1);
                    var9 = true;
                    break;
                }
            }
        }

        if (var9) {
            var8 = this.createNewProofScheme((Conjecture)var6, var7, var2);
        }

        System.out.println("look - i've got to here now. i'm gonna perform globalAndLocal on " + var7.writeConjecture());
        ProofScheme var4 = this.globalAndLocalLemmaIncorporation(var1, var8, var7, var3);
        System.out.println("\n\nimproved_proofscheme is: " + var4.writeProofScheme());
        return var4;
    }

    public ProofScheme hiddenLemmaIncorporation(Theory var1, ProofScheme var2, AgentWindow var3) {
        new ProofScheme();
        System.out.println("into hiddenLemmaIncorporation");
        Conjecture var5 = this.identifyHiddenFaultyLemma(var2);
        System.out.println("The first lemma in which the problem entities occur, is: " + var5.writeConjecture());
        System.out.println("**************************** found the hidden fucking lemma");
        Conjecture var6 = this.fixHiddenFaultyLemma(var5, var2, var1);
        System.out.println("explicit_lemma is " + var6.writeConjecture());
        ProofScheme var7 = this.createNewProofScheme(var5, var6, var2);
        System.out.println("\n\nintermediate_proofscheme is: " + var7.writeProofScheme());
        System.out.println("look - i've got to here now. i'm gonna perform globalAndLocal on " + var6.writeConjecture());
        ProofScheme var4 = this.globalAndLocalLemmaIncorporation(var1, var7, var6, var3);
        System.out.println("\n\nimproved_proofscheme is: " + var4.writeProofScheme());
        return var4;
    }

    public ProofScheme createNewProofScheme(Conjecture var1, Conjecture var2, ProofScheme var3) {
        for(int var4 = 0; var4 < var3.proof_vector.size(); ++var4) {
            Conjecture var5 = (Conjecture)var3.proof_vector.elementAt(var4);
            if (var5.equals(var1)) {
                var3.proof_vector.removeElementAt(var4);
                var3.proof_vector.insertElementAt(var2, var4);
            }
        }

        return var3;
    }

    public Conjecture fixHiddenFaultyLemma(Conjecture var1, ProofScheme var2, Theory var3) {
        Conjecture var4 = new Conjecture();
        Conjecture var5 = this.getFirstConjInProofSchemeWithCounters(var2);
        Vector var6 = var5.counterexamples;
        new Concept();
        Concept var8 = new Concept();
        Vector var9 = var1.conceptsInConjecture();
        boolean var10 = false;
        boolean var11 = false;
        Vector var12 = var2.conj.counterexamples;
        System.out.println("starting fixHiddenFaultyLemma. at the moment we have hidden_faulty_lemma is " + var1.writeConjecture());
        System.out.println("is this by chance the same as the thing you just tried??? ");
        Concept var7 = this.getConceptWhoseAncestorsHaveEntities(var1, var6);

        int var13;
        Concept var14;
        for(var13 = 0; var13 < var9.size(); ++var13) {
            var14 = (Concept)var9.elementAt(var13);
            if (var14.writeDefinition().equals(var7.writeDefinition())) {
                if (var13 == 0 && var9.size() == 2) {
                    var11 = true;
                    var8 = (Concept)var9.elementAt(1);
                    break;
                }

                if (var13 == 1) {
                    var10 = true;
                    var8 = (Concept)var9.elementAt(0);
                    break;
                }
            }
        }

        for(var13 = 0; var13 < var3.concepts.size(); ++var13) {
            var14 = (Concept)var3.concepts.elementAt(var13);
            Vector var15 = this.fromStringsToEntities(var3, var14.negatives());
            boolean var16 = this.vectorsAreEqual(var15, var6);
            if (var16) {
                for(int var17 = 0; var17 < var3.concepts.size(); ++var17) {
                    Concept var18 = (Concept)var3.concepts.elementAt(var17);
                    String var19 = "id_of_dummy1 = " + var18.id + " " + var14.id + " compose [0,1] ";
                    Step var20 = this.guider.forceStep(var19, var3);
                    new TheoryConstituent();
                    TheoryConstituent var21 = var20.result_of_step;
                    if (var21 instanceof Concept) {
                        Concept var22 = (Concept)var21;
                        var22.lakatos_method = "lemma_incorporation";
                        String var23 = "id_of_dummy2 = " + var22.id + " exists [1] ";
                        Step var24 = this.guider.forceStep(var23, var3);
                        new TheoryConstituent();
                        TheoryConstituent var25 = var24.result_of_step;
                        if (var25 instanceof Concept) {
                            Concept var26 = (Concept)var25;
                            var26.lakatos_method = "lemma-incorporation";
                            Vector var27 = this.fromStringsToEntities(var3, var26.negatives());
                            boolean var28 = this.vectorsAreEqual(var27, var12);
                            if (var28) {
                                double var29 = 0.0D;
                                NearImplication var32;
                                if (var1 instanceof Implication) {
                                    if (var10) {
                                        return new NearImplication(var8, var26, var12, var29);
                                    }

                                    if (var11) {
                                        var32 = new NearImplication(var26, var8, var12, var29);
                                        return var32;
                                    }
                                }

                                if (var1 instanceof NearImplication) {
                                    if (var10) {
                                        return new NearImplication(var8, var26, var12, var29);
                                    }

                                    if (var11) {
                                        var32 = new NearImplication(var26, var8, var12, var29);
                                        return var32;
                                    }
                                }

                                if (var1 instanceof NearEquivalence) {
                                    NearEquivalence var31;
                                    if (var10) {
                                        var31 = new NearEquivalence(var8, var26, var12, var29);
                                        return var31;
                                    }

                                    if (var11) {
                                        var31 = new NearEquivalence(var26, var8, var12, var29);
                                        return var31;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return var4;
    }

    public Conjecture identifyHiddenFaultyLemma(ProofScheme var1) {
        Conjecture var2 = new Conjecture();
        Conjecture var3 = this.getFirstConjInProofSchemeWithCounters(var1);
        boolean var4 = !var3.writeConjecture().equals("");
        Vector var5;
        boolean var6;
        boolean var15;
        if (var4) {
            var5 = var3.counterexamples;
            var6 = false;
            if (var6) {
                Conjecture var7 = this.getConjWithEntities(var1.proof_vector, var5);
                boolean var8 = !var7.writeConjecture().equals("");
                if (var8) {
                    var2 = var7;
                }
            }

            var15 = true;
            if (var15) {
                Conjecture var16 = this.getConjWhoseAncestorsHaveEntities(var1.proof_vector, var5);
                boolean var9 = !var16.writeConjecture().equals("");
                if (var9) {
                    var2 = var16;
                }
            }
        }

        if (!var4) {
            System.out.println("none of the lemmas have counterexamples");
            var5 = var1.conj.counterexamples;
            System.out.println("starting lem test here");
            var6 = false;
            var15 = false;

            Vector var10;
            int var11;
            Concept var12;
            int var17;
            Conjecture var18;
            for(var17 = 0; var17 < var1.proof_vector.size(); ++var17) {
                var18 = (Conjecture)var1.proof_vector.elementAt(var17);
                var10 = var18.conceptsInConjecture();

                for(var11 = 0; var11 < var10.size(); ++var11) {
                    var12 = (Concept)var10.elementAt(var11);
                    System.out.println(" current_concept is " + var12.writeDefinition());
                    System.out.println(var12.datatable.toTable());
                    var6 = this.got_non_lem_concept(var12, var5);
                    if (var6) {
                        System.out.println("1 -- returning " + var18.writeConjecture() + " as the faulty lemma");
                        return var18;
                    }
                }
            }

            for(var17 = 0; var17 < var1.proof_vector.size(); ++var17) {
                var18 = (Conjecture)var1.proof_vector.elementAt(var17);
                var10 = var18.conceptsInConjecture();

                for(var11 = 0; var11 < var10.size(); ++var11) {
                    var12 = (Concept)var10.elementAt(var11);

                    for(int var13 = 0; var13 < var12.ancestors.size(); ++var13) {
                        Concept var14 = (Concept)var12.ancestors.elementAt(var13);
                        var6 = this.got_non_lem_concept(var14, var5);
                        if (var6) {
                            System.out.println("2 -- returning " + var18.writeConjecture() + " as the faulty lemma");
                            System.out.println("found this one through the ancestor thing");
                            return var18;
                        }
                    }
                }
            }
        }

        return var2;
    }

    public boolean got_non_lem_concept(Concept var1, Vector var2) {
        Vector var3 = var1.nonLemConcept();
        System.out.println(" starting got_non_lem_concept");
        System.out.println("got concept: " + var1.writeDefinition());
        System.out.println("and counters_to_global: " + var2);
        System.out.println("non_lem_concept vector is " + var3 + "\n\n\n");
        if (((Vector)var3.elementAt(1)).isEmpty() && !((Vector)var3.elementAt(2)).isEmpty()) {
            System.out.println(" 1 got_non_lem_concept");

            for(int var4 = 0; var4 < var2.size(); ++var4) {
                Entity var5 = (Entity)var2.elementAt(var4);
                System.out.println(" 2 got_non_lem_concept");
                System.out.println("counter is " + var5.name);
                System.out.println("non_lem_concept_vector.elementAt(0) is " + var3.elementAt(0));
                boolean var6 = ((Vector)var3.elementAt(0)).contains(var5);
                System.out.println("contains_counter is " + var6);
                Vector var7 = (Vector)var3.elementAt(0);
                if (!this.contains(var7, var5)) {
                    System.out.println(" 3 got_non_lem_concept");
                    return false;
                }
            }

            System.out.println(" 4 we wanna be here --- returning true for concept: " + var1.writeDefinition());
            System.out.println("and counters_to_global: " + var2);
            return true;
        } else {
            return false;
        }
    }

    public boolean contains(Vector var1, Entity var2) {
        System.out.println("testing to see whether " + var1 + " contains " + var2.name);

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            String var4 = (String)var1.elementAt(var3);
            boolean var5 = var2.name.equals(var4);
            System.out.println("counter is " + var2.name);
            System.out.println("entity is " + var4);
            System.out.println("(counter.name).equals(entity) is " + var5);
            if (var2.name.equals(var4)) {
                System.out.println("returning true");
                return true;
            }
        }

        System.out.println("returning false");
        return false;
    }

    public Conjecture getFirstConjInProofSchemeWithCounters(ProofScheme var1) {
        Conjecture var2 = new Conjecture();

        for(int var3 = 0; var3 < var1.proof_vector.size(); ++var3) {
            Conjecture var4 = (Conjecture)var1.proof_vector.elementAt(var3);
            if (!var4.counterexamples.isEmpty()) {
                return var4;
            }
        }

        return var2;
    }

    public Conjecture getConjWithEntities(Vector var1, Vector var2) {
        Conjecture var3 = new Conjecture();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Conjecture var5 = (Conjecture)var1.elementAt(var4);
            Vector var6 = var5.conceptsInConjecture();
            boolean var7 = this.currentConjGotProblemEntities(var6, var2);
            if (var7) {
                return var5;
            }
        }

        return var3;
    }

    public Concept getConceptWhoseAncestorsHaveEntities(Conjecture var1, Vector var2) {
        Concept var3 = new Concept();
        Vector var4 = var1.conceptsInConjecture();

        for(int var5 = 0; var5 < var4.size(); ++var5) {
            Concept var6 = (Concept)var4.elementAt(var5);

            for(int var7 = 0; var7 < var6.ancestors.size(); ++var7) {
                Concept var8 = (Concept)var6.ancestors.elementAt(var7);

                for(int var9 = 0; var9 < var8.datatable.size(); ++var9) {
                    Row var10 = (Row)var8.datatable.elementAt(var9);

                    for(int var11 = 0; var11 < var2.size(); ++var11) {
                        Entity var12 = (Entity)var2.elementAt(var11);
                        boolean var13 = var10.entity.toString().equals(var12.toString());
                        if (var13) {
                            return var6;
                        }
                    }
                }
            }
        }

        System.out.println();
        return var3;
    }

    public Conjecture getConjWhoseAncestorsHaveEntities(Vector var1, Vector var2) {
        Conjecture var3 = new Conjecture();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Conjecture var5 = (Conjecture)var1.elementAt(var4);
            Vector var6 = var5.conceptsInConjecture();
            Vector var7 = new Vector();

            for(int var8 = 0; var8 < var6.size(); ++var8) {
                Concept var9 = (Concept)var6.elementAt(var8);

                for(int var10 = 0; var10 < var9.ancestors.size(); ++var10) {
                    Concept var11 = (Concept)var9.ancestors.elementAt(var10);
                    var7.addElement(var11);
                }
            }

            boolean var12 = this.currentConjGotProblemEntities(var7, var2);
            if (var12) {
                return var5;
            }
        }

        return var3;
    }

    public boolean currentConjGotProblemEntities(Vector var1, Vector var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);

            for(int var5 = 0; var5 < var4.datatable.size(); ++var5) {
                Row var6 = (Row)var4.datatable.elementAt(var5);

                for(int var7 = 0; var7 < var2.size(); ++var7) {
                    Entity var8 = (Entity)var2.elementAt(var7);
                    boolean var9 = var6.entity.toString().equals(var8.toString());
                    if (var9) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public ProofScheme onlyLocalLemmaIncorporation(Theory var1, ProofScheme var2, Conjecture var3, AgentWindow var4) {
        System.out.println("starting onlyLocalLemmaIncorporation");
        ProofScheme var5 = new ProofScheme();
        Vector var6 = var3.counterexamples;
        this.fromEntitiesToStrings(var6);
        System.out.println("faulty_lemma is " + var3.writeConjecture());
        System.out.println("faulty_lemma.counterexamples are " + var3.counterexamples);
        Vector var8 = this.piecemealExclusion(var3, var1, var4);
        System.out.println("poss_pm_conjectures.size() is " + var8.size());

        for(int var9 = 0; var9 < var8.size(); ++var9) {
            TheoryConstituent var10 = (TheoryConstituent)var8.elementAt(var9);
            System.out.println("the " + var9 + " th the theory constituent is " + var10.writeTheoryConstituent());
            if (var10 instanceof Conjecture) {
                Conjecture var17 = (Conjecture)var10;
                return this.createNewProofScheme(var3, var17, var2);
            }

            if (var10 instanceof Concept) {
                Concept var11 = (Concept)var10;
                var11.lakatos_method = "lemma-incorporation";
                Vector var14;
                if (var3 instanceof NearImplication) {
                    NearImplication var12 = (NearImplication)var3;
                    Implication var13 = new Implication(var11, var12.lh_concept, "lemma_inc_conj");
                    var14 = var13.counterexamples;
                    if (var14.isEmpty()) {
                        return this.createNewProofScheme(var3, var13, var2);
                    }
                }

                if (var3 instanceof NearEquivalence) {
                    NearEquivalence var19 = (NearEquivalence)var3;
                    new Equivalence(var11, var19.lh_concept, "lemma_inc_conj");
                    var14 = var19.counterexamples;
                    if (var14.isEmpty()) {
                        return this.createNewProofScheme(var3, var19, var2);
                    }
                }
            }
        }

        Vector var15 = this.strategicWithdrawal(var3, var1, var4);

        for(int var16 = 0; var16 < var15.size(); ++var16) {
            TheoryConstituent var18 = (TheoryConstituent)var15.elementAt(var16);
            if (var18 instanceof Conjecture) {
                Conjecture var20 = (Conjecture)var18;
                return this.createNewProofScheme(var3, var20, var2);
            }
        }

        return var5;
    }

    public Conjecture countersInProofScheme(ProofScheme var1) {
        Conjecture var2 = new Conjecture();

        for(int var3 = 0; var3 < var1.proof_vector.size(); ++var3) {
            Conjecture var4 = (Conjecture)var1.proof_vector.elementAt(var3);
            if (!var4.counterexamples.isEmpty()) {
                return var4;
            }
        }

        return var2;
    }

    public Conjecture getFaultyLemma(ProofScheme var1, Vector var2, Theory var3) {
        System.out.println("in getFaultyLemma");
        System.out.println("The counters to the global conj are: " + var2);
        Conjecture var4 = new Conjecture();

        for(int var5 = 0; var5 < var1.proof_vector.size(); ++var5) {
            Conjecture var6 = (Conjecture)var1.proof_vector.elementAt(var5);
            System.out.println("got " + var6.writeConjecture());
            System.out.println("conj.counterexamples are " + var6.counterexamples);
            if (!var6.counterexamples.isEmpty()) {
                boolean var7 = this.vectorsAreEqual(var2, var6.counterexamples);
                if (var7) {
                    System.out.println("getFaultyLemma .... in here");
                    return var6;
                }
            }
        }

        return var4;
    }

    public Concept getConceptWhichExactlyCoversGivenEntities(Vector var1) {
        Concept var2 = new Concept();
        Tuples var3 = new Tuples();

        for(int var4 = 0; var4 < this.concepts.size(); ++var4) {
            Concept var5 = (Concept)this.concepts.elementAt(var4);
            if (var5.arity == 1 && var5.positivesContains(var1)) {
                Vector var6 = new Vector();
                var6.addElement(Double.toString(var5.applicability));
                var6.addElement(var5);
                var3.addElement(var6);
            }
        }

        var3.sort();
        if (var3.isEmpty()) {
            return var2;
        } else {
            Vector var15 = (Vector)var3.elementAt(0);
            double var16 = new Double((double)var1.size());
            boolean var7 = true;
            int var8 = 0;
            if (var7) {
                Concept var9 = this.findObjectOfInterestConcept(this.concepts, var1);
                var8 = var9.getPositives().size();
            }

            if (!var7) {
                var8 = this.entities.size();
            }

            double var17 = new Double((double)var8);
            double var11 = var16 / var17;
            Concept var13 = (Concept)var15.elementAt(1);
            String var14 = (String)var15.elementAt(0);
            if (var14.equals(Double.toString(var11))) {
                return var13;
            } else {
                return var2;
            }
        }
    }

    public Concept getNewConceptWhichExactlyCoversGivenEntities(Vector var1, Concept var2) {
        Concept var3 = new Concept();
        Tuples var4 = new Tuples();

        int var5;
        for(var5 = 0; var5 < this.concepts.size(); ++var5) {
            Concept var6 = (Concept)this.concepts.elementAt(var5);
            if (var6.arity == 1 && var6.positivesContains(var1)) {
                Vector var7 = new Vector();
                var7.addElement(Double.toString(var6.applicability));
                var7.addElement(var6);
                var4.addElement(var7);
            }
        }

        var4.sort();

        for(var5 = 0; var5 < var4.size(); ++var5) {
            Vector var15 = (Vector)var4.elementAt(var5);
            double var16 = new Double((double)var1.size());
            double var9 = new Double((double)this.entities.size());
            double var11 = var16 / var9;
            Concept var13 = (Concept)var15.elementAt(1);
            if (!var13.writeDefinition("ascii").equals(var2.writeDefinition("ascii"))) {
                String var14 = (String)var15.elementAt(0);
                if (var14.equals(Double.toString(var11))) {
                    return var13;
                }
            }
        }

        return var3;
    }

    public Concept makeConceptForST(Concept var1, Concept var2, Vector var3) {
        new Concept();
        Tuples var5 = new Tuples();

        Concept var4;
        for(int var6 = 0; var6 < this.concepts.size(); ++var6) {
            var4 = (Concept)this.concepts.elementAt(var6);
            if (var4.arity == 1 && var4.positivesContains(var3)) {
                Vector var7 = new Vector();
                var7.addElement(Double.toString(var4.applicability));
                var7.addElement(var4);
                var5.addElement(var7);
            }
        }

        var5.sort();
        Vector var14 = (Vector)var5.elementAt(0);
        double var15 = new Double((double)var3.size());
        double var9 = new Double((double)this.entities.size());
        double var11 = var15 / var9;
        var4 = (Concept)var14.elementAt(1);
        String var13 = (String)var14.elementAt(0);
        if (var13.equals(Double.toString(var11))) {
            return var4;
        } else {
            return var4;
        }
    }

    public Vector getUnionOfConcepts(Concept var1, Concept var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var1.datatable.size(); ++var4) {
            Row var5 = (Row)var1.datatable.elementAt(var4);
            if (var2.sameValueOfEntity(var5)) {
                var3.addElement(var5.entity);
            }
        }

        return var3;
    }

    public Vector getPositives(Vector var1, Concept var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.datatable.size(); ++var4) {
            Row var5 = (Row)var2.datatable.elementAt(var4);
            if (!var1.contains(var5.entity)) {
                var3.addElement(var5.entity);
            }
        }

        return var3;
    }

    public Conjecture makeImpConj(Concept var1, Concept var2) {
        String var3 = "dummy_id";
        Implication var4 = new Implication(var1, var2, var3);
        return var4;
    }

    public Conjecture makeEquivConj(Concept var1, Concept var2) {
        String var3 = "dummy_id";
        Equivalence var4 = new Equivalence(var1, var2, var3);
        return var4;
    }

    public Vector fromEntitiesToStrings(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Entity var4 = (Entity)var1.elementAt(var3);
            String var5 = var4.toString();
            var2.addElement(var5);
        }

        return var2;
    }

    public Vector fromStringsToEntities(Theory var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            String var5 = (String)var2.elementAt(var4);

            for(int var6 = 0; var6 < var1.entities.size(); ++var6) {
                Entity var7 = (Entity)var1.entities.elementAt(var6);
                if (var7.name.equals(var5)) {
                    var3.addElement(var7);
                    break;
                }
            }
        }

        return var3;
    }

    public Vector getBreakers(Concept var1, Vector var2, Vector var3) {
        Vector var4 = new Vector();
        Vector var5 = (Vector)var1.categorisation.elementAt(0);
        Vector var6 = (Vector)var1.categorisation.elementAt(1);

        int var7;
        String var8;
        for(var7 = 0; var7 < var2.size(); ++var7) {
            var8 = (String)var2.elementAt(var7);
            if (!var1.positivesContains(var8)) {
                var4.addElement(var8);
            }
        }

        for(var7 = 0; var7 < var3.size(); ++var7) {
            var8 = (String)var3.elementAt(var7);
            if (var1.positivesContains(var8)) {
                var4.addElement(var8);
            }
        }

        return var4;
    }

    public Conjecture constructNearEquivalence(Concept var1, Concept var2) {
        new Conjecture();
        Vector var4 = var1.positives();
        Vector var5 = var1.negatives();
        Vector var6 = var2.positives();
        Vector var7 = var2.negatives();
        Vector var8 = new Vector();

        int var11;
        String var12;
        for(var11 = 0; var11 < var4.size(); ++var11) {
            var12 = (String)var4.elementAt(var11);
            if (!var6.contains(var12)) {
                var8.addElement(var12);
            }
        }

        for(var11 = 0; var11 < var5.size(); ++var11) {
            var12 = (String)var5.elementAt(var11);
            if (var6.contains(var12)) {
                var8.addElement(var12);
            }
        }

        var11 = var1.sharedEntities(var2).size();
        double var9 = (double)(var11 / (var11 + var8.size()));
        Object var3;
        if (var9 == 1.0D) {
            var3 = new Equivalence(var1, var2, "dummy_id");
        } else {
            var3 = new NearEquivalence(var1, var2, var8, var9);
        }

        return (Conjecture)var3;
    }

    public SortableVector getNearConceptsForEntities(Theory var1, Vector var2, String var3, double var4) {
        SortableVector var6 = new SortableVector();
        Concept var7 = var1.conceptFromName(var3);
        Vector var8 = new Vector();
        double var9 = 0.0D;

        int var11;
        for(var11 = 0; var11 < var7.datatable.size(); ++var11) {
            Row var12 = (Row)var7.datatable.elementAt(var11);
            if (!var2.contains(var12.entity)) {
                var8.addElement(var12.entity);
            }

            ++var9;
        }

        for(var11 = 0; var11 < var1.concepts.size(); ++var11) {
            Concept var20 = (Concept)var1.concepts.elementAt(var11);
            if (var20.domain.equals(var3)) {
                Vector var13 = var20.getPositives();
                Vector var14 = new Vector();

                int var15;
                String var16;
                for(var15 = 0; var15 < var2.size(); ++var15) {
                    var16 = (String)var2.elementAt(var15);
                    if (!var13.contains(var16)) {
                        var14.addElement(var16);
                    }
                }

                for(var15 = 0; var15 < var8.size(); ++var15) {
                    var16 = (String)var8.elementAt(var15);
                    if (var13.contains(var16)) {
                        var14.addElement(var16);
                    }
                }

                double var21 = new Double((double)var14.size());
                double var17 = 1.0D - var21 / var9;
                if (var17 >= var4 / 100.0D) {
                    NearEquivalence var19 = new NearEquivalence(var20, (Concept)null, var14, var17);
                    var6.addElement(var19, "score");
                }
            }
        }

        return var6;
    }

    public Concept makeConceptFromVector1(Vector var1, Theory var2) {
        Vector var3 = this.fromEntitiesToStrings(var1);
        Concept var4 = new Concept();
        Concept var5 = this.findObjectOfInterestConcept(var2.concepts, var3);
        String var6 = "forcedstep = " + var5.id + " entity_disjunct " + var3;
        this.agenda.addForcedStep(var6, false);
        Step var7 = this.guider.forceStep(var6, var2);
        if (var7.result_of_step instanceof Concept) {
            var4 = (Concept)var7.result_of_step;
        }

        if (var7.result_of_step instanceof Equivalence) {
            Equivalence var8 = (Equivalence)var7.result_of_step;
            var4 = ((Equivalence)var7.result_of_step).rh_concept;
        }

        var4.arity = var5.arity;
        var4.types = var5.types;

        for(int var13 = 0; var13 < var4.specifications.size(); ++var13) {
            Specification var9 = (Specification)var4.specifications.elementAt(var13);
            String var10 = var9.writeSpec();
        }

        Datatable var14 = var5.datatable.copy();

        for(int var15 = 0; var15 < var14.size(); ++var15) {
            Row var17 = (Row)var14.elementAt(var15);
            if (!var3.contains(var17.entity)) {
                var14.removeElementAt(var15);
                Tuples var11 = new Tuples();
                Row var12 = new Row(var17.entity, var11);
                var14.insertElementAt(var12, var15);
            }
        }

        var4.datatable = var14;
        Categorisation var16 = (Categorisation)var5.categorisation.clone();

        int var18;
        for(var18 = 0; var18 < var16.size(); ++var18) {
            Vector var19 = (Vector)var16.elementAt(var18);

            for(int var21 = 0; var21 < var19.size(); ++var21) {
                if (var3.contains((String)var19.elementAt(var21))) {
                    var19.removeElementAt(var21);
                }
            }
        }

        var16.addElement(var3);
        var4.categorisation = var16;
        var4.concept_to_cover_entities = false;
        var4.entity_strings = var3;

        for(var18 = 0; var18 < var5.specifications.size(); ++var18) {
            Specification var20 = (Specification)var5.specifications.elementAt(var18);
            String var22 = var20.writeSpec();
        }

        var2.concepts.addElement(var4);
        return var4;
    }

    public Concept findObjectOfInterestConcept(Vector var1, Vector var2) {
        Concept var3 = new Concept();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Concept var5 = (Concept)var1.elementAt(var4);
            if (var5.is_object_of_interest_concept) {
                boolean var6 = true;

                for(int var7 = 0; var7 < var2.size(); ++var7) {
                    String var8 = (String)var2.elementAt(var7);
                    if (!var5.datatable.hasEntity(var8)) {
                        var6 = false;
                        break;
                    }
                }

                if (var6) {
                    var3 = var5;
                    break;
                }
            }
        }

        return var3;
    }

    public void test_equals(Theory var1, AgentWindow var2) {
        Tuples var3 = new Tuples();
        Tuples var4 = new Tuples();

        for(int var5 = 0; var5 < var1.concepts.size(); ++var5) {
            Concept var6 = (Concept)var1.concepts.elementAt(var5);
            if (var6.is_user_given && var6.id.equals("poly006")) {
                int var7;
                for(var7 = 0; var7 < var6.datatable.size(); ++var7) {
                    Row var8 = (Row)var6.datatable.elementAt(var7);
                    if (var8.entity.equals("tetrahedron")) {
                        var3 = var8.tuples;
                        break;
                    }
                }

                for(var7 = 0; var7 < var1.concepts.size(); ++var7) {
                    Concept var11 = (Concept)var1.concepts.elementAt(var7);
                    if (var11.is_user_given && var11.id.equals("graph004")) {
                        var4 = ((Row)var11.datatable.elementAt(0)).tuples;
                    }

                    for(int var9 = 0; var9 < var3.size(); ++var9) {
                        boolean var10 = var3.toString().equals(var4.toString());
                        var2.writeToFrontEnd("poly_row_tuples.toString() is " + var3.toString());
                        var2.writeToFrontEnd("graph_row_tuples.toString() is " + var4.toString());
                        var2.writeToFrontEnd("poly_row_tuples.equals(graph_row_tuples) is " + var10);
                    }
                }
            }
        }

    }

    public Concept test(Vector var1, Theory var2) {
        System.out.println("1 started test");
        Concept var3 = new Concept();

        for(int var4 = 0; var4 < var2.concepts.size(); ++var4) {
            Concept var5 = (Concept)var2.concepts.elementAt(var4);
            if (var5.is_user_given && var5.id.equals("poly004")) {
                for(int var6 = 0; var6 < var5.datatable.size(); ++var6) {
                    Row var7 = (Row)var5.datatable.elementAt(var6);
                    if (!var7.tuples.isEmpty()) {
                        Vector var8 = (Vector)var7.tuples.elementAt(0);
                        String var9 = (String)var8.elementAt(0);
                        Vector var10 = new Vector();
                        var10.addElement(var9);
                        String var11 = "forcedstep = poly009 entity_disjunct " + var10;
                        this.agenda.addForcedStep(var11, false);
                        Step var12 = this.guider.forceStep(var11, var2);
                        if (var12.result_of_step instanceof Concept) {
                            var3 = (Concept)var12.result_of_step;
                        }

                        if (var12.result_of_step instanceof Equivalence) {
                            var3 = ((Equivalence)var12.result_of_step).rh_concept;
                        }

                        if (var12.result_of_step instanceof NonExists) {
                            var3 = ((NonExists)var12.result_of_step).concept;
                        }

                        return var3;
                    }
                }
            }
        }

        return var3;
    }

    public void test1(Vector var1, Theory var2) {
        Vector var3 = new Vector();
        new Concept();

        for(int var5 = 0; var5 < var2.concepts.size(); ++var5) {
            Concept var6 = (Concept)var2.concepts.elementAt(var5);
            if (var6.is_user_given && var6.id.equals("poly009") && !var6.datatable.isEmpty()) {
                Row var7 = (Row)var6.datatable.elementAt(0);
                var3.addElement(var7.entity.toString());
            }
        }

        String var8 = "forcedstep = poly009 entity_disjunct " + var3;
        this.agenda.addForcedStep(var8, false);
        Step var9 = this.guider.forceStep(var8, var2);
        Concept var4;
        if (var9.result_of_step instanceof Concept) {
            var4 = (Concept)var9.result_of_step;
        }

        if (var9.result_of_step instanceof Equivalence) {
            var4 = ((Equivalence)var9.result_of_step).rh_concept;
        }

        if (var9.result_of_step instanceof NonExists) {
            var4 = ((NonExists)var9.result_of_step).concept;
        }

    }

    public boolean vectorsAreEqual(Vector var1, Vector var2) {
        int var3;
        Entity var4;
        boolean var5;
        int var6;
        Entity var7;
        for(var3 = 0; var3 < var1.size(); ++var3) {
            var4 = (Entity)var1.elementAt(var3);
            var5 = false;

            for(var6 = 0; var6 < var2.size(); ++var6) {
                var7 = (Entity)var2.elementAt(var6);
                if (var4.equals(var7)) {
                    var5 = true;
                    break;
                }
            }

            if (!var5) {
                return false;
            }
        }

        for(var3 = 0; var3 < var2.size(); ++var3) {
            var4 = (Entity)var2.elementAt(var3);
            var5 = false;

            for(var6 = 0; var6 < var1.size(); ++var6) {
                var7 = (Entity)var1.elementAt(var6);
                if (var4.equals(var7)) {
                    var5 = true;
                    break;
                }
            }

            if (!var5) {
                return false;
            }
        }

        return true;
    }

    public Concept makeConceptFromVector(Vector var1, Theory var2) {
        Concept var3 = new Concept();
        Concept var4 = new Concept();
        var4.id = "dummy_id";

        for(int var5 = 0; var5 < var2.concepts.size(); ++var5) {
            Concept var6 = (Concept)var2.concepts.elementAt(var5);
            if (var6.position_in_theory == 0) {
                System.out.println("got first concept");
                var3 = var6;
                break;
            }
        }

        var4.arity = var3.arity;
        var4.types = var3.types;
        var4.specifications = (Vector)var3.specifications.clone();
        Datatable var10 = var3.datatable.copy();

        for(int var11 = 0; var11 < var10.size(); ++var11) {
            Row var7 = (Row)var10.elementAt(var11);
            if (!var1.contains(var7.entity)) {
                var10.removeElementAt(var11);
                Tuples var8 = new Tuples();
                Row var9 = new Row(var7.entity, var8);
                var10.insertElementAt(var9, var11);
            }
        }

        var4.datatable = var10;
        Categorisation var12 = (Categorisation)var3.categorisation.clone();

        int var13;
        for(var13 = 0; var13 < var12.size(); ++var13) {
            Vector var14 = (Vector)var12.elementAt(var13);

            for(int var16 = 0; var16 < var14.size(); ++var16) {
                if (var1.contains((String)var14.elementAt(var16))) {
                    var14.removeElementAt(var16);
                }
            }
        }

        var12.addElement(var1);
        var4.categorisation = var12;
        var4.concept_to_cover_entities = true;
        var4.entity_strings = var1;
        var4.definition_writer = (DefinitionWriter)var3.definition_writer.clone();
        System.out.println("core_concept.specifications is " + var3.specifications);

        for(var13 = 0; var13 < var3.specifications.size(); ++var13) {
            Specification var15 = (Specification)var3.specifications.elementAt(var13);
            String var17 = var15.writeSpec();
            System.out.println("core_concept.specifications.elementAt(" + var13 + ") is: " + var17);
        }

        var2.concepts.addElement(var4);
        return var4;
    }

    public void lemmaIncorporation_old(Theory var1, AgentWindow var2) {
        var2.writeToFrontEnd("starting lemmaIncorporation");
        Datatable var3 = new Datatable();
        new Datatable();
        new Datatable();
        Datatable var6 = new Datatable();
        new Datatable();
        new Datatable();
        Datatable var9 = new Datatable();

        for(int var10 = 0; var10 < var1.concepts.size(); ++var10) {
            Concept var11 = (Concept)var1.concepts.elementAt(var10);
            if (var11.is_user_given && var11.id.equals("int001")) {
                var3 = var11.datatable;
            }

            Datatable var12;
            if (var11.is_user_given && var11.id.equals("poly002")) {
                var12 = var11.datatable.copy();
            }

            if (var11.is_user_given && var11.id.equals("poly003")) {
                var12 = var11.datatable.copy();
            }

            if (var11.is_user_given && var11.id.equals("poly004")) {
                var12 = var11.datatable.copy();
                var6 = var12;
            }

            if (var11.is_user_given && var11.id.equals("poly005")) {
                var12 = var11.datatable.copy();
            }

            if (var11.is_user_given && var11.id.equals("poly006")) {
                var12 = var11.datatable.copy();
            }

            if (var11.is_user_given && var11.id.equals("poly001")) {
                var12 = var11.datatable.copy();
                var9 = var12;
            }
        }

        var2.writeToFrontEnd("polys_datatable is " + var9.toTable());
        var2.writeToFrontEnd("faces_datatable is " + var6.toTable());
        var2.writeToFrontEnd("integers_datatable is " + var3.toTable());
        var2.writeToFrontEnd("finishing lemmaIncorporation");
    }

    public Conjecture getConjWithEntitiesBackUp(Vector var1, Vector var2) {
        Conjecture var3 = new Conjecture();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Conjecture var5 = (Conjecture)var1.elementAt(var4);
            Vector var6 = var5.conceptsInConjecture();

            for(int var7 = 0; var7 < var6.size(); ++var7) {
                Concept var8 = (Concept)var6.elementAt(var7);

                for(int var9 = 0; var9 < var8.datatable.size(); ++var9) {
                    Row var10 = (Row)var8.datatable.elementAt(var9);
                    boolean var11 = false;

                    for(int var12 = 0; var12 < var2.size(); ++var12) {
                        Entity var13 = (Entity)var2.elementAt(var12);
                        boolean var14 = var10.entity.toString().equals(var13.toString());
                        if (var14) {
                            var11 = true;
                            break;
                        }
                    }

                    if (var11) {
                        return var5;
                    }
                }
            }
        }

        return var3;
    }

    public void writeProofSchemeWithDatatables(ProofScheme var1) {
        System.out.println("proofscheme.proof_vector, complete with datatables,  is:");

        for(int var2 = 0; var2 < var1.proof_vector.size(); ++var2) {
            Conjecture var3 = (Conjecture)var1.proof_vector.elementAt(var2);
            System.out.println("\n\n\n\n(" + var2 + ") " + var3.writeConjecture());
            Concept var4 = new Concept();
            Concept var5 = new Concept();
            if (var3 instanceof Implication) {
                Implication var6 = (Implication)var3;
                var4 = var6.lh_concept;
                var5 = var6.rh_concept;
            }

            if (var3 instanceof NearImplication) {
                NearImplication var7 = (NearImplication)var3;
                var4 = var7.lh_concept;
                var5 = var7.rh_concept;
            }

            if (var3 instanceof Equivalence) {
                Equivalence var8 = (Equivalence)var3;
                var4 = var8.lh_concept;
                var5 = var8.rh_concept;
            }

            if (var3 instanceof NearEquivalence) {
                NearEquivalence var9 = (NearEquivalence)var3;
                var4 = var9.lh_concept;
                var5 = var9.rh_concept;
            }

            System.out.println("lhconcept is " + var4.writeDefinition());
            System.out.println("Its datatable is: ");
            System.out.println(var4.datatable.toTable());
            System.out.println("rhconcept is " + var5.writeDefinition());
            System.out.println("Its datatable is: ");
            System.out.println(var5.datatable.toTable());
        }

        System.out.println("   ***************         ");
    }

    public Conjecture testForRelatedCounter(ProofScheme var1, Vector var2) {
        for(int var3 = 0; var3 < var2.size(); ++var3) {
            Entity var4 = (Entity)var2.elementAt(var3);
            String var5 = var4.name;
            Conjecture var6 = this.getFirstConjInProofSchemeWithCounters(var1);
            System.out.println("first_lemma_with_counters is " + var6.writeConjecture());
            boolean var7 = !var6.writeConjecture().equals("");
            if (var7) {
                Vector var8 = var6.counterexamples;

                for(int var9 = 0; var9 < this.concepts.size(); ++var9) {
                    Concept var10 = (Concept)this.concepts.elementAt(var9);
                    Row var11 = var10.datatable.rowWithEntity(var5);
                    if (var11 != null) {
                        Vector var12 = new Vector();
                        var12.addElement(var8);

                        for(int var13 = 0; var13 < var8.size(); ++var13) {
                            Entity var14 = (Entity)var8.elementAt(var13);

                            for(int var15 = 0; var15 < var11.tuples.size(); ++var15) {
                                Vector var16 = (Vector)var11.tuples.elementAt(var15);

                                for(int var17 = 0; var17 < var16.size(); ++var17) {
                                    String var18 = (String)var16.elementAt(var17);
                                    boolean var19 = var18.equals(var14.name);
                                    System.out.println("the answer is " + var19);
                                    if (var19) {
                                        Conjecture var20 = this.firstConjectureInvolvingConcept(var1, var10);
                                        return var20;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return new Conjecture();
    }

    public Conjecture firstConjectureInvolvingConcept(ProofScheme var1, Concept var2) {
        int var3;
        Conjecture var4;
        Vector var5;
        int var6;
        Concept var7;
        for(var3 = 0; var3 < var1.proof_vector.size(); ++var3) {
            var4 = (Conjecture)var1.proof_vector.elementAt(var3);
            var5 = var4.conceptsInConjecture();

            for(var6 = 0; var6 < var5.size(); ++var6) {
                var7 = (Concept)var5.elementAt(var6);
                if (var7.writeDefinition().equals(var2.writeDefinition())) {
                    return var4;
                }
            }
        }

        for(var3 = 0; var3 < var1.proof_vector.size(); ++var3) {
            var4 = (Conjecture)var1.proof_vector.elementAt(var3);
            var5 = var4.conceptsInConjecture();

            for(var6 = 0; var6 < var5.size(); ++var6) {
                var7 = (Concept)var5.elementAt(var6);

                for(int var8 = 0; var8 < var7.ancestors.size(); ++var8) {
                    Concept var9 = (Concept)var7.ancestors.elementAt(var8);
                    if (var9.writeDefinition().equals(var2.writeDefinition())) {
                        return var4;
                    }
                }
            }
        }

        return new Conjecture();
    }

    public Conjecture surprisingEntity(Entity var1, Implication var2, Theory var3, AgentWindow var4) {
        System.out.println("testing surprisingEntity -- 1");
        System.out.println("we wanna know whether the " + var1.name + " is surprising wrt the conjecture " + var2.writeConjecture());
        Conjecture var5 = new Conjecture();
        Vector var6 = var2.rh_concept.ancestors;
        var6.addElement(var2.rh_concept);
        int var7 = var6.size() - 1;
        System.out.println("imp.rh_concept.arity is " + var2.rh_concept.arity);

        while(var7 >= 0) {
            Concept var8 = (Concept)var6.elementAt(var7);
            System.out.println("imp_concept is " + var8.id + ": " + var8.writeDefinition());

            for(int var9 = 0; var9 < this.concepts.size(); ++var9) {
                Concept var10 = (Concept)this.concepts.elementAt(var9);
                System.out.println("new_concept.arity is " + var10.arity);
                String var11 = " compose [0";

                for(int var12 = 1; var12 < var8.arity; ++var12) {
                    var11 = var11 + ",1";
                }

                var11 = var11 + "] ";
                String var23 = "id_of_dummy1 = " + var8.id + " " + var10.id + var11;
                System.out.println("agenda_line is " + var23);
                Step var13 = this.guider.forceStep(var23, var3);
                new TheoryConstituent();
                TheoryConstituent var14 = var13.result_of_step;
                if (var14 instanceof Concept) {
                    Concept var15 = (Concept)var14;
                    System.out.println("composed_concept is " + var15.writeDefinition());
                    new Implication();
                    Implication var16;
                    if (var15.arity == var2.lh_concept.arity) {
                        Conjecture var17 = this.makeImpConj(var2.lh_concept, (Concept)var14);
                        var16 = (Implication)var17;
                        var5 = var16.reconstructConjecture(var3, var4);
                    } else {
                        for(int var24 = var15.arity - var2.lh_concept.arity; var24 > 0; --var24) {
                            String var18 = "id_of_dummy1 = " + var15.id + " exists [1] ";
                            System.out.println("agenda_line1 is " + var18);
                            Step var19 = this.guider.forceStep(var18, var3);
                            new TheoryConstituent();
                            TheoryConstituent var20 = var19.result_of_step;
                            System.out.println("tc_dummy1 is " + var20);
                            if (!(var20 instanceof Concept)) {
                                break;
                            }

                            System.out.println("~~~~~~~~~~~~~~~~~~~~ in here ~~~~~~~~~~~~~~~~~~~~~");
                            if (((Concept)var20).arity == var2.lh_concept.arity) {
                                System.out.println("~~~~~~~ got what we wanted!");
                                System.out.println("ie, " + ((Concept)var20).writeDefinition());
                                Conjecture var21 = this.makeImpConj(var2.lh_concept, (Concept)var20);
                                var16 = (Implication)var21;
                                var5 = var16.reconstructConjecture_old(var3, var4);
                                System.out.println("just made reconstructed_conj = " + var5.writeConjecture());
                                System.out.println("reconstructed_conj.counterexamples.size() is " + var5.counterexamples.size());
                                System.out.println("reconstructed_conj.counterexamples are " + var5.counterexamples);
                                boolean var22 = var1.entitiesContains(var5.counterexamples);
                                System.out.println("entity.entitiesContains(new_imp.counterexamples) is " + var22);
                            }
                        }
                    }

                    if (var5.counterexamples.size() == 1 && var1.entitiesContains(var5.counterexamples)) {
                        System.out.println("~~~~~~~~~~~~~~~~~~~~ now we really got what we wanted...  ~~~~~~~~~~~~~~~~~~~~~");
                        System.out.println("done surprisingEntity on entity = " + var1.name + ", and imp = " + var2.writeConjecture());
                        System.out.println("new_concept is " + var10.writeDefinition() + ", and reconstructed_conj is " + var5.writeConjecture());
                        return var5;
                    }
                }
            }

            --var7;
        }

        System.out.println("testing surprisingEntity -- 7 -- returning null conj");
        return var5;
    }

    public boolean antecedentFailure(Entity var1, Conjecture var2) {
        new Concept();
        Concept var4 = new Concept();
        Vector var5 = new Vector();
        var5.addElement(var1.name);
        Concept var3;
        if (var2 instanceof Implication) {
            Implication var9 = (Implication)var2;
            var3 = var9.lh_concept;
            return !var3.positivesContains(var5);
        } else if (var2 instanceof NearImplication) {
            NearImplication var8 = (NearImplication)var2;
            var3 = var8.lh_concept;
            return !var3.positivesContains(var5);
        } else {
            if (var2 instanceof Equivalence) {
                Equivalence var6 = (Equivalence)var2;
                var3 = var6.lh_concept;
                if (!var3.positivesContains(var5)) {
                    return !var4.positivesContains(var5);
                }
            }

            if (var2 instanceof NearEquivalence) {
                NearEquivalence var7 = (NearEquivalence)var2;
                var3 = var7.lh_concept;
                if (!var3.positivesContains(var5)) {
                    return !var4.positivesContains(var5);
                }
            }

            return false;
        }
    }
}
