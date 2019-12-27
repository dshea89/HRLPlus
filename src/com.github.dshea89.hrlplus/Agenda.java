package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A class representing an agenda of theory formation tasks to do. At present the only tasks are theory formation steps.
 */
public class Agenda implements Serializable {
    /**
     * The current step number.
     */
    public int step_number = 0;

    /**
     * Whether or not to use a segregated search.
     */
    public boolean use_segregated_search = false;

    /**
     * The segregation categorisation for this agenda.
     */
    public Categorisation segregation_categorisation = new Categorisation();

    /**
     * Whether to keep a record of the steps which weren't allowed for limit reasons.
     */
    public boolean keep_not_allowed_steps = false;

    /**
     * The hashtable of concepts in the theory, with the key being the id of the concept.
     */
    public Hashtable concept_hashtable = new Hashtable();

    /**
     * A vector of forced ids which resulted in a non-existence conjecture.
     */
    public Vector non_existent_concept_ids = new Vector();

    /**
     * A hashtable to stop repeated steps being carried out (when forced).
     */
    public Hashtable repeated_forced_steps = new Hashtable();

    /**
     * The list of steps which have been forced (conjecture driven concept formation).
     */
    public Vector forced_steps = new Vector();

    /**
     * A hashtable for looking up a concept which was given an id by the theory forcing that step.
     */
    public Hashtable concept_forced_names = new Hashtable();

    /**
     * The set of steps to force the theory to produce instead of the normal theory formation step (conjecture-driven concept formation).
     * These are not steps, but strings of the form: "NewConcept = PR OldConcept (OldConcept2) Parameterisation" and the agenda records the id of the new concept which is produced,
     * for use in later forced steps (or the id of a re-invented concept).
     */
    public Vector steps_to_force = new Vector();

    /**
     * A flag to tell the addConcept function that the concept is being added for a second time because the agenda is not being expanded automatically.
     */
    public boolean second_addition = false;

    /**
     * Whether to expand the agenda or not. If running in a breadth first search, then there is no need to put all pairs of concepts on the agenda,
     * rather this can be done when the concept reaches the top. This reduces the memory allocation required for the agenda.
     */
    public boolean expand_agenda = true;

    /**
     * Which tier of the agenda is currently being used
     */
    public int current_tier = 0;

    /**
     * Whether or not the search is tiered (in terms of the production rules)
     */
    public boolean is_tiered = false;

    /**
     * The list of tiers in the agenda.
     */
    public Vector agenda_tiers = new Vector();

    /**
     * The limit for the complexity of any concept which will be produced.
     */
    public int complexity_limit = 1000;

    /**
     * The limit for the number of items which can be stored on the agenda.
     */
    public int agenda_size_limit = 10000;

    /**
     * A search strategy - randomly permute the concepts.
     */
    public boolean random = false;

    /**
     * The type of objects of interest in the categorisation to be learned.
     */
    public String object_types_to_learn = "";

    /**
     * The list of entities which must be learned, eg. 2,3,5,7,...
     */
    public Vector positives_for_forward_lookahead = new Vector();

    /**
     * The list of entities which are not to be learned.
     */
    public Vector negatives_for_forward_lookahead = new Vector();

    /**
     * Whether or not to use the patterns heuristic (for identifyint a particular concept).
     */
    public boolean use_forward_lookahead = false;

    /**
     * Whether or not to use the scores for placing pattern-suggested items in the agenda.
     */
    public boolean use_scores = false;

    /**
     * A set of concepts, such as integer, which it is not worth using in a compose step with any other concept.
     * Usually the entity concepts.
     */
    public Vector dont_compose_with = new Vector();

    /**
     * The unary steps still to do in the agenda.
     */
    public Vector unary_steps = new Vector();

    /**
     * The binary steps still to do in the agenda.
     */
    public Vector binary_steps = new Vector();

    /**
     * The exhaustive list of steps in the agenda.
     */
    public Vector exhaustive_steps = new Vector();

    /**
     * The urgent unary steps still to do in the agenda.
     */
    public Vector urgent_unary_steps = new Vector();

    /**
     * The urgent binary steps still to do in the agenda.
     */
    public Vector urgent_binary_steps = new Vector();

    /**
     * The list of steps not allowed because of it breaking some limit.
     */
    public Vector not_allowed_steps = new Vector();

    /**
     * Whether or not to use a delayed best first search.
     */
    public boolean best_first_delayed = false;

    /**
     * The number of steps to delay the best first search for.
     */
    public int best_first_delay_steps = 0;

    /**
     * Whether or not to use the interestingness of concepts to order the agenda line in the lists to complete (i.e., a best first search).
     */
    public boolean best_first = false;

    /**
     * Whether to do a depth first search - each new concept is explored fully before moving on to the next one.
     * If this is set to false, HR assumes a depth first (or best first) search.
     */
    public boolean depth_first = false;

    /**
     * The list of concepts ordered in terms of their overall interestingness measure (those which have agenda items left).
     */
    public Vector live_ordered_concepts = new Vector();

    /**
     * The list of concepts ordered in terms of their overall interestingness measure (those which have agenda items left).
     */
    public Vector all_ordered_concepts = new Vector();

    /**
     * The construction step to be forced next.
     */
    public Step forced_step = new Step();

    /**
     * Whether to allow the use of unary rules. The unary rules may be turned off in an agency environment to stop the repetition of steps.
     */
    public boolean use_unary_rules = true;

    /**
     * Default constructor - adds 20 tiers to the agenda
     */
    public Agenda() {
        for(int var1 = 0; var1 < 20; ++var1) {
            this.agenda_tiers.addElement(new Vector());
        }

    }

    /**
     * Adds all possible steps involving the given concept to the agenda.
     */
    public void addConcept(Concept concept, Vector all_concepts, Theory theory) {
        this.concept_hashtable.put(concept.id, concept);
        if (!concept.dont_develop) {
            if (!this.second_addition) {
                this.addConceptToOrderedList(this.all_ordered_concepts, concept);
            }

            Step var4;
            Vector var5;
            if (concept.complexity >= this.complexity_limit) {
                if (this.keep_not_allowed_steps) {
                    var4 = new Step();
                    var5 = new Vector();
                    var5.addElement(concept);
                    var4.addElement(var5);
                    this.not_allowed_steps.addElement(var4);
                    var4.trimToSize();
                    this.not_allowed_steps.trimToSize();
                }

            } else {
                if (!this.second_addition) {
                    this.addConceptToOrderedList(this.live_ordered_concepts, concept);
                }

                int var7;
                Step var8;
                if (!this.use_forward_lookahead) {
                    if (this.use_unary_rules && !this.second_addition) {
                        var4 = new Step();
                        var5 = new Vector();
                        var5.addElement(concept);
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
                        if (!this.dont_compose_with.contains(var10.id) && !var10.dont_develop && !this.conceptsAreSegregated(concept, var10)) {
                            var9.addElement(concept);
                            var9.addElement(var10);
                            var9.trimToSize();
                            if (!this.live_ordered_concepts.contains(var10)) {
                                this.addConceptToOrderedList(this.live_ordered_concepts, var10);
                            }

                            var8.addElement(var9);
                            var8.trimToSize();
                            if (concept.complexityWith(var10) >= this.complexity_limit && this.keep_not_allowed_steps) {
                                this.not_allowed_steps.addElement(var8);
                            }

                            if (concept.complexityWith(var10) < this.complexity_limit && concept.position_in_theory >= var10.position_in_theory) {
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
                    var18.addElement(concept);
                    var5 = theory.unary_rules;
                    Vector var20 = theory.binary_rules;

                    for(var7 = 0; var7 < var5.size(); ++var7) {
                        var8 = new Step();
                        var8.addElement(var18);
                        ProductionRule var22 = (ProductionRule)var5.elementAt(var7);
                        int var24 = var22.patternScore(var18, all_concepts, this.positives_for_forward_lookahead, this.negatives_for_forward_lookahead, this.object_types_to_learn);
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
                    int var21 = all_concepts.size();
                    if (var7 + all_concepts.size() > this.agenda_size_limit) {
                        var21 = this.agenda_size_limit - var7;
                    }

                    for(int var23 = 0; var23 < var21; ++var23) {
                        Vector var25 = new Vector();
                        Concept var11 = (Concept)all_concepts.elementAt(var23);
                        if (!this.dont_compose_with.contains(var11.id)) {
                            var25.addElement(concept);
                            var25.addElement(var11);
                            if (concept.complexityWith(var11) < this.complexity_limit && concept.position_in_theory >= var11.position_in_theory) {
                                for(int var26 = 0; var26 < var20.size(); ++var26) {
                                    ProductionRule var13 = (ProductionRule)var20.elementAt(var26);
                                    Step var14 = new Step();
                                    var14.addElement(var25);
                                    int var15 = var13.patternScore(var25, all_concepts, this.positives_for_forward_lookahead, this.negatives_for_forward_lookahead, this.object_types_to_learn);
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

    /**
     * This checks whether the two given concepts have segregated ancestors.
     */
    public boolean conceptsAreSegregated(Concept c1, Concept c2) {
        Vector var3 = new Vector();

        int var4;
        String var5;
        String var6;
        for(var4 = 0; var4 < c1.ancestor_ids.size(); ++var4) {
            var5 = (String)c1.ancestor_ids.elementAt(var4);
            var6 = this.segregationPosition(var5);
            if (!var3.contains(var6) && !var6.equals("")) {
                var3.addElement(var6);
            }
        }

        for(var4 = 0; var4 < c2.ancestor_ids.size(); ++var4) {
            var5 = (String)c2.ancestor_ids.elementAt(var4);
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

    /**
     * Returns the next valid step at the top of the agenda.
     */
    public Step nextStep(Vector unary_rules, Vector binary_rules, Theory theory) {
        if (!this.steps_to_force.isEmpty()) {
            String var15 = (String)this.steps_to_force.elementAt(0);
            this.steps_to_force.removeElementAt(0);
            boolean var17 = true;
            Step var19 = this.getStepFromString(var15, theory);
            this.forced_steps.addElement(var15);
            if (var19 == null) {
                String var20 = var15.substring(0, var15.indexOf(" ="));
                this.non_existent_concept_ids.addElement(var20);
                return this.nextStep(unary_rules, binary_rules, theory);
            } else {
                Concept var18 = (Concept)this.repeated_forced_steps.get(var19.asString());
                if (var18 != null) {
                    if (!var19.concept_arising_name.equals("")) {
                        this.concept_forced_names.put(var19.concept_arising_name, var18);
                    }

                    return this.nextStep(unary_rules, binary_rules, theory);
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

            Vector var7 = theory.concepts;
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
                        return this.nextStep(unary_rules, binary_rules, theory);
                    }
                }

                if (var21.size() == 1) {
                    if (var9.size() == 1) {
                        var11 = (Concept)var9.elementAt(0);
                        if (!this.expand_agenda) {
                            this.expand_agenda = true;
                            this.second_addition = true;
                            this.addConcept(var11, var7, theory);
                            this.second_addition = false;
                            this.expand_agenda = false;
                        }

                        if (unary_rules.size() == 0) {
                            var5.removeElementAt(var10);
                        } else {
                            this.putIntoTiers(var21, var9, unary_rules);
                            if (var21.size() == 1) {
                                var5.removeElementAt(var10);
                            }
                        }
                    }

                    if (var9.size() == 2) {
                        if (binary_rules.size() == 0) {
                            var5.removeElementAt(var10);
                        } else {
                            this.putIntoTiers(var21, var9, binary_rules);
                            if (var21.size() == 1) {
                                var5.removeElementAt(var10);
                            }
                        }
                    }

                    return this.nextStep(unary_rules, binary_rules, theory);
                } else {
                    Vector var23;
                    ProductionRule var24;
                    Vector var25;
                    if (var21.size() == 2) {
                        var23 = (Vector)var21.elementAt(1);
                        var24 = (ProductionRule)var23.elementAt(0);
                        var25 = var24.allParameters(var9, theory);
                        if (var25.size() > 0) {
                            var21.addElement(var25);
                        } else {
                            var23.removeElementAt(0);
                        }

                        if (var23.size() == 0) {
                            var5.removeElementAt(var10);
                        }

                        return this.nextStep(unary_rules, binary_rules, theory);
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

    /**
     * This splits the agenda items into tiers.
     */
    public void putIntoTiers(Vector agenda_line, Vector concept_list, Vector pr_list) {
        int var4 = 0;
        Vector var5 = (Vector)pr_list.clone();

        while(var4 < var5.size()) {
            ProductionRule var6 = (ProductionRule)var5.elementAt(var4);
            if (var6.tier > 0) {
                Vector var7 = (Vector)this.agenda_tiers.elementAt(var6.tier);
                Vector var8 = new Vector();
                var8.addElement(var6);
                Step var9 = new Step();
                var9.addElement(concept_list);
                var9.addElement(var8);
                var7.addElement(var9);
                var5.removeElementAt(var4);
            } else {
                ++var4;
            }
        }

        if (!var5.isEmpty()) {
            agenda_line.addElement(var5);
        }

    }

    /**
     * Counts how many agenda items there are in the set of lists to complete.
     */
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

    /**
     * To force the agenda to order its concepts - after a new concept has been added.
     */
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

    /**
     * This collates the top n agenda items into one vector.
     */
    public Vector listToComplete(boolean look_at_not_allowed, int vector_size) {
        Vector var3 = new Vector();
        int var4 = 0;
        if (look_at_not_allowed) {
            while(var3.size() < vector_size && var4 < this.not_allowed_steps.size()) {
                this.addSteps(var3, (Step)this.not_allowed_steps.elementAt(var4), vector_size);
                ++var4;
            }
        }

        if (!look_at_not_allowed) {
            while(var3.size() < vector_size && var4 < this.exhaustive_steps.size()) {
                this.addSteps(var3, (Step)this.exhaustive_steps.elementAt(var4), vector_size);
                ++var4;
            }
        }

        if (!look_at_not_allowed) {
            for(int var5 = 0; var5 < this.agenda_tiers.size(); ++var5) {
                for(Vector var6 = (Vector)this.agenda_tiers.elementAt(var5); var3.size() < vector_size && var4 < var6.size(); ++var4) {
                    this.addSteps(var3, (Step)var6.elementAt(var4), vector_size);
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

    /**
     * When a change has been made to the search settings, some not-allowed steps may be allowed now, so they are added to the agenda.
     * Other changes also need to be made.
     */
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

    /**
     * Given a string of the form "Newconcept = PR oldconcept (oldconcept2) parameters", this function turns it into a step.
     */
    public Step getStepFromString(String step, Theory theory) {
        try {
            Step var3 = new Step();
            String var4 = step.substring(0, step.indexOf(" ="));
            step = step.substring(step.indexOf("=") + 2, step.length());
            String var5 = "";
            if (step.indexOf(":") >= 0) {
                var5 = step.substring(step.indexOf(":"), step.length());
                step = step.substring(0, step.indexOf(":") - 1);
            }

            String var6 = step.substring(step.indexOf("["), step.length());
            step = step.substring(0, step.indexOf("["));
            String var7 = step.substring(0, step.indexOf(" "));
            step = step.substring(step.indexOf(" ") + 1, step.length());
            String var8 = step.substring(0, step.indexOf(" "));
            step = step.substring(step.indexOf(" "), step.length());
            Vector var9 = new Vector();
            new ProductionRule();
            ProductionRule var10;
            if (!step.equals(" ")) {
                String var11 = step.substring(1, step.length() - 1);
                Concept var12 = this.getConcept(var7, theory);
                if (var12 == null) {
                    return null;
                }

                var9.addElement(var12);
                Concept var13 = this.getConcept(var8, theory);
                if (var13 == null) {
                    return null;
                }

                var9.addElement(var13);
                var10 = theory.productionRuleFromName(var11);
            } else {
                Concept var14 = this.getConcept(var7, theory);
                if (var14 == null) {
                    System.out.println("getStepFromString - 3");
                    return null;
                }

                var9.addElement(var14);
                var10 = theory.productionRuleFromName(var8);
            }

            var3.addElement(var9);
            var3.addElement(var10);
            var3.addElement(this.getVector(var6, var10.getName(), theory));
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

    public Concept getConcept(String id, Theory theory) {
        Concept var3 = theory.getConcept(id);
        if (var3.id.equals("")) {
            return this.non_existent_concept_ids.contains(id) ? null : (Concept)this.concept_forced_names.get(id);
        } else {
            return var3;
        }
    }

    public Concept getConcept(String id) {
        if (this.non_existent_concept_ids.contains(id)) {
            return null;
        } else if (this.concept_forced_names.containsKey(id)) {
            return (Concept)this.concept_forced_names.get(id);
        } else {
            return this.concept_hashtable.containsKey(id) ? (Concept)this.concept_hashtable.get(id) : null;
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

    public void recordNonExistentConcept(Step step) {
        if (!step.concept_arising_name.equals("")) {
            this.non_existent_concept_ids.addElement(step.concept_arising_name);
        }

    }

    public void recordForcedName(Step step, Concept concept) {
        if (!step.concept_arising_name.equals("")) {
            this.concept_forced_names.put(step.concept_arising_name, concept);
            System.out.println("step.concept_arising_name is " + step.concept_arising_name);
            System.out.println("putting " + step.asString() + ", " + concept.writeDefinition());
            this.repeated_forced_steps.put(step.asString(), concept);
        }

    }

    /**
     * This adds a forced step to the agenda, unless the step has already been forced, in which case it add to the concepts and names hashtable,
     * so that the result of performing the step can be found.
     */
    public void addForcedStep(String step_string, boolean to_top) {
        if (to_top) {
            this.steps_to_force.insertElementAt(step_string, 0);
        }

        this.steps_to_force.addElement(step_string);
    }
}
