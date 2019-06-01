package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class MeasureConcept extends Measure implements Serializable {
    public Vector positives = new Vector();
    public Vector negatives = new Vector();
    public boolean use_weighted_sum = false;
    public boolean keep_best = false;
    public boolean keep_worst = false;
    public boolean measure_concepts = true;
    public String object_types_to_learn = "";
    public double invariance_weight = 0.5D;
    public double discrimination_weight = 0.5D;
    public double applicability_weight = 0.5D;
    public double coverage_weight = 0.5D;
    public double equiv_conj_score_weight = 0.5D;
    public double ne_conj_score_weight = 0.5D;
    public double pi_conj_score_weight = 0.5D;
    public double imp_conj_score_weight = 0.5D;
    public double equiv_conj_num_weight = 0.5D;
    public double ne_conj_num_weight = 0.5D;
    public double positive_applicability_weight = 0.5D;
    public double negative_applicability_weight = 0.5D;
    public double pi_conj_num_weight = 0.5D;
    public double imp_conj_num_weight = 0.5D;
    public double children_score_weight = 0.5D;
    public double comprehensibility_weight = 0.5D;
    public double cross_domain_weight = 0.5D;
    public double highlight_weight = 0.5D;
    public double novelty_weight = 0.5D;
    public double parent_weight = 0.5D;
    public double parsimony_weight = 0.5D;
    public double predictive_power_weight = 0.5D;
    public double productivity_weight = 0.5D;
    public double development_steps_num_weight = 0.5D;
    public double variety_weight = 0.5D;
    public Vector all_equiv_conj_scores = new Vector();
    public Vector all_ne_conj_scores = new Vector();
    public Vector all_invariance_scores = new Vector();
    public Vector all_discrimination_scores = new Vector();
    public Vector all_imp_conj_scores = new Vector();
    public Vector all_pi_conj_scores = new Vector();
    public Vector all_applicabilities = new Vector();
    public Vector all_positive_applicabilities = new Vector();
    public Vector all_negative_applicabilities = new Vector();
    public Vector all_coverages = new Vector();
    public Vector all_comprehensibilities = new Vector();
    public Vector all_novelties = new Vector();
    public Vector all_parsimonies = new Vector();
    public Vector all_predictive_powers = new Vector();
    public Vector sorted_equiv_conj_scores = new Vector();
    public Vector sorted_equiv_conj_nums = new Vector();
    public Hashtable equiv_conj_scores_hashtable = new Hashtable();
    public Hashtable equiv_conj_nums_hashtable = new Hashtable();
    public Vector sorted_ne_conj_scores = new Vector();
    public Hashtable ne_conj_scores_hashtable = new Hashtable();
    public Vector sorted_ne_conj_nums = new Vector();
    public Hashtable ne_conj_nums_hashtable = new Hashtable();
    public Vector sorted_imp_conj_scores = new Vector();
    public Hashtable imp_conj_scores_hashtable = new Hashtable();
    public Vector sorted_imp_conj_nums = new Vector();
    public Hashtable imp_conj_nums_hashtable = new Hashtable();
    public Vector sorted_pi_conj_scores = new Vector();
    public Hashtable pi_conj_scores_hashtable = new Hashtable();
    public Vector sorted_pi_conj_nums = new Vector();
    public Hashtable pi_conj_nums_hashtable = new Hashtable();
    public Vector sorted_productivities = new Vector();
    public Vector sorted_developments = new Vector();
    public Hashtable productivities_hashtable = new Hashtable();
    public Hashtable developments_hashtable = new Hashtable();
    public Vector all_varieties = new Vector();
    public double default_productivity = 0.0D;
    public Categorisation coverage_categorisation = new Categorisation();
    public Categorisation gold_standard_categorisation = new Categorisation();
    public Vector gold_standard_same_pairs = new Vector();
    public Vector gold_standard_different_pairs = new Vector();
    public Vector gold_standard_entity_names = new Vector();

    public MeasureConcept() {
    }

    public void measureConcept(Concept var1, Vector var2, boolean var3) {
        if (this.measure_concepts) {
            this.old_measures_have_been_updated = true;
            this.measures_need_updating = false;
            var1.applicability = var1.datatable.applicability();
            var1.normalised_applicability = super.normalisedValue(var1.applicability, this.all_applicabilities);
            int var4;
            Concept var5;
            int var6;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var4 = 0; var4 < var2.size(); ++var4) {
                    var5 = (Concept)var2.elementAt(var4);
                    var6 = this.all_applicabilities.indexOf(Double.toString(var5.applicability));
                    var5.normalised_applicability = new Double((double)var6) / (new Double((double)this.all_applicabilities.size()) - 1.0D);
                }
            }

            if (!this.positives.isEmpty()) {
                var1.positive_applicability = var1.datatable.applicability(this.positives);
            } else {
                var1.positive_applicability = 0.0D;
            }

            var1.normalised_positive_applicability = super.normalisedValue(var1.positive_applicability, this.all_positive_applicabilities);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var4 = 0; var4 < var2.size(); ++var4) {
                    var5 = (Concept)var2.elementAt(var4);
                    var6 = this.all_positive_applicabilities.indexOf(Double.toString(var5.positive_applicability));
                    var5.normalised_positive_applicability = new Double((double)var6) / (new Double((double)this.all_positive_applicabilities.size()) - 1.0D);
                }
            }

            if (!this.negatives.isEmpty()) {
                var1.negative_applicability = var1.datatable.applicability(this.negatives);
            } else {
                var1.negative_applicability = 0.0D;
            }

            var1.normalised_negative_applicability = super.normalisedValue(var1.negative_applicability, this.all_negative_applicabilities);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var4 = 0; var4 < var2.size(); ++var4) {
                    var5 = (Concept)var2.elementAt(var4);
                    var6 = this.all_negative_applicabilities.indexOf(Double.toString(var5.negative_applicability));
                    var5.normalised_negative_applicability = new Double((double)var6) / (new Double((double)this.all_negative_applicabilities.size()) - 1.0D);
                }
            }

            if (!var1.domain.equals(this.object_types_to_learn)) {
                var1.predictive_power = 0.0D;
            } else {
                var1.predictive_power = (var1.positive_applicability * (double)this.positives.size() + (1.0D - var1.negative_applicability) * (double)this.negatives.size()) / (double)(this.positives.size() + this.negatives.size());
            }

            var1.normalised_predictive_power = super.normalisedValue(var1.predictive_power, this.all_predictive_powers);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var4 = 0; var4 < var2.size(); ++var4) {
                    var5 = (Concept)var2.elementAt(var4);
                    var6 = this.all_predictive_powers.indexOf(Double.toString(var5.predictive_power));
                    var5.predictive_power = new Double((double)var6) / (new Double((double)this.all_predictive_powers.size()) - 1.0D);
                }
            }

            var1.coverage = 0.0D;
            var1.normalised_coverage = 0.0D;
            if (!this.coverage_categorisation.isEmpty()) {
                var1.coverage = var1.datatable.coverage(this.coverage_categorisation);
                var1.normalised_coverage = super.normalisedValue(var1.coverage, this.all_coverages);
                if (this.measures_need_updating) {
                    this.old_measures_have_been_updated = true;

                    for(var4 = 0; var4 < var2.size(); ++var4) {
                        var5 = (Concept)var2.elementAt(var4);
                        var6 = this.all_coverages.indexOf(Double.toString(var5.coverage));
                        var5.normalised_coverage = new Double((double)var6) / (new Double((double)this.all_coverages.size()) - 1.0D);
                    }
                }
            }

            double var13 = 0.0D;

            for(var6 = 0; var6 < var1.children.size(); ++var6) {
                var13 += ((Concept)var1.children.elementAt(var6)).interestingness;
            }

            if (var1.children.size() == 0) {
                var1.children_score = 0.0D;
            } else {
                var1.children_score = var13 / (double)var1.children.size();
            }

            var1.comprehensibility = 1.0D / new Double((double)var1.complexity);
            var1.normalised_comprehensibility = super.normalisedValue(var1.comprehensibility, this.all_comprehensibilities);
            Concept var7;
            int var8;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var6 = 0; var6 < var2.size(); ++var6) {
                    var7 = (Concept)var2.elementAt(var6);
                    var8 = this.all_comprehensibilities.indexOf(Double.toString(var7.comprehensibility));
                    var7.normalised_comprehensibility = new Double((double)var8) / (new Double((double)this.all_comprehensibilities.size()) - 1.0D);
                }
            }

            this.addConceptToHashtable(new Double(0.0D), this.equiv_conj_scores_hashtable, this.sorted_equiv_conj_scores, var1, "equiv_conj_score");
            this.addConceptToHashtable(new Double(0.0D), this.ne_conj_scores_hashtable, this.sorted_ne_conj_scores, var1, "ne_conj_score");
            this.addConceptToHashtable(new Double(0.0D), this.imp_conj_scores_hashtable, this.sorted_imp_conj_scores, var1, "imp_conj_score");
            this.addConceptToHashtable(new Double(0.0D), this.pi_conj_scores_hashtable, this.sorted_pi_conj_scores, var1, "pi_conj_score");
            this.addConceptToHashtable(new Double(0.0D), this.equiv_conj_nums_hashtable, this.sorted_equiv_conj_nums, var1, "equiv_conj_num");
            this.addConceptToHashtable(new Double(0.0D), this.ne_conj_nums_hashtable, this.sorted_ne_conj_nums, var1, "ne_conj_num");
            this.addConceptToHashtable(new Double(0.0D), this.imp_conj_nums_hashtable, this.sorted_imp_conj_nums, var1, "imp_conj_num");
            this.addConceptToHashtable(new Double(0.0D), this.pi_conj_nums_hashtable, this.sorted_pi_conj_nums, var1, "pi_conj_num");
            if (var1.is_cross_domain) {
                var1.cross_domain_score = 1.0D;
            } else {
                var1.cross_domain_score = 0.0D;
            }

            if (var3) {
                this.updateNormalisedDevelopments(var1, true);
            }

            var1.novelty = var1.categorisation.novelty;
            var1.normalised_novelty = super.normalisedValue(var1.novelty, this.all_novelties);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var6 = 0; var6 < var2.size(); ++var6) {
                    var7 = (Concept)var2.elementAt(var6);
                    var8 = this.all_novelties.indexOf(Double.toString(var7.novelty));
                    var7.normalised_novelty = new Double((double)var8) / (new Double((double)this.all_novelties.size()) - 1.0D);
                }
            }

            if (var1.domain.equals(this.object_types_to_learn)) {
                var1.invariance = var1.categorisation.invarianceWith(this.gold_standard_entity_names, this.gold_standard_same_pairs);
            } else {
                var1.invariance = 0.0D;
            }

            var1.normalised_invariance_score = super.normalisedValue(var1.invariance, this.all_invariance_scores);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var6 = 0; var6 < var2.size(); ++var6) {
                    var7 = (Concept)var2.elementAt(var6);
                    var8 = this.all_invariance_scores.indexOf(Double.toString(var7.invariance));
                    var7.normalised_invariance_score = new Double((double)var8) / (new Double((double)this.all_invariance_scores.size()) - 1.0D);
                }
            }

            if (var1.domain.equals(this.object_types_to_learn)) {
                var1.discrimination = var1.categorisation.discriminationWith(this.gold_standard_entity_names, this.gold_standard_different_pairs);
            } else {
                var1.discrimination = 0.0D;
            }

            var1.normalised_discrimination_score = super.normalisedValue(var1.discrimination, this.all_discrimination_scores);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var6 = 0; var6 < var2.size(); ++var6) {
                    var7 = (Concept)var2.elementAt(var6);
                    var8 = this.all_discrimination_scores.indexOf(Double.toString(var7.discrimination));
                    var7.normalised_discrimination_score = new Double((double)var8) / (new Double((double)this.all_discrimination_scores.size()) - 1.0D);
                }
            }

            double var15 = 0.0D;

            for(var8 = 0; var8 < var1.parents.size(); ++var8) {
                var15 += ((Concept)var1.parents.elementAt(var8)).interestingness;
            }

            if (var1.parents.size() == 0) {
                var1.parent_score = 0.0D;
            } else {
                var1.parent_score = var15 / (double)var1.parents.size();
            }

            double var14 = var1.datatable.fullSize();
            if (var14 == 0.0D) {
                var1.parsimony = 1.0D;
            } else {
                var1.parsimony = 1.0D / var14;
            }

            var1.normalised_parsimony = super.normalisedValue(var1.parsimony, this.all_parsimonies);
            int var10;
            Concept var11;
            int var12;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var10 = 0; var10 < var2.size(); ++var10) {
                    var11 = (Concept)var2.elementAt(var10);
                    var12 = this.all_parsimonies.indexOf(Double.toString(var11.parsimony));
                    var11.normalised_parsimony = new Double((double)var12) / (new Double((double)this.all_parsimonies.size()) - 1.0D);
                }
            }

            if (var3) {
                this.updateNormalisedProductivities(var1, true);
            }

            var1.variety = new Double((double)var1.categorisation.size());
            var1.normalised_variety = super.normalisedValue(var1.variety, this.all_varieties);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var10 = 0; var10 < var2.size(); ++var10) {
                    var11 = (Concept)var2.elementAt(var10);
                    var12 = this.all_varieties.indexOf(Double.toString(var11.variety));
                    var11.normalised_variety = new Double((double)var12) / (new Double((double)this.all_varieties.size()) - 1.0D);
                }
            }

            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(var10 = 0; var10 < var2.size(); ++var10) {
                    this.calculateOverallValue((Concept)var2.elementAt(var10));
                }
            }

        }
    }

    public void calculateOverallValue(Concept var1) {
        if (this.use_weighted_sum) {
            var1.interestingness = var1.normalised_applicability * this.applicability_weight + var1.coverage * this.coverage_weight + var1.normalised_comprehensibility * this.comprehensibility_weight + var1.children_score * this.children_score_weight + var1.normalised_equiv_conj_score * this.equiv_conj_score_weight + var1.normalised_ne_conj_score * this.ne_conj_score_weight + var1.normalised_invariance_score * this.invariance_weight + var1.normalised_discrimination_score * this.discrimination_weight + var1.normalised_imp_conj_score * this.imp_conj_score_weight + var1.normalised_pi_conj_score * this.pi_conj_score_weight + var1.normalised_equiv_conj_num * this.equiv_conj_num_weight + var1.normalised_ne_conj_num * this.ne_conj_num_weight + var1.normalised_negative_applicability * this.negative_applicability_weight + var1.normalised_positive_applicability * this.positive_applicability_weight + var1.normalised_imp_conj_num * this.imp_conj_num_weight + var1.normalised_pi_conj_num * this.pi_conj_num_weight + var1.cross_domain_score * this.cross_domain_weight + var1.normalised_development_steps_num * this.development_steps_num_weight + var1.highlight_score * this.highlight_weight + var1.parent_score * this.parent_weight + var1.normalised_predictive_power * this.predictive_power_weight + var1.normalised_parsimony * this.parsimony_weight + var1.normalised_productivity * this.productivity_weight + var1.normalised_novelty * this.novelty_weight + var1.normalised_variety * this.variety_weight;
        }

        if (this.keep_best) {
            var1.interestingness = 0.0D;
            if (var1.normalised_applicability > var1.interestingness && this.applicability_weight > 0.0D) {
                var1.interestingness = var1.normalised_applicability;
            }

            if (var1.normalised_positive_applicability > var1.interestingness && this.positive_applicability_weight > 0.0D) {
                var1.interestingness = var1.normalised_positive_applicability;
            }

            if (var1.normalised_negative_applicability > var1.interestingness && this.negative_applicability_weight > 0.0D) {
                var1.interestingness = var1.normalised_negative_applicability;
            }

            if (var1.coverage > var1.interestingness && this.coverage_weight > 0.0D) {
                var1.interestingness = var1.coverage;
            }

            if (var1.normalised_comprehensibility > var1.interestingness && this.comprehensibility_weight > 0.0D) {
                var1.interestingness = var1.normalised_comprehensibility;
            }

            if (var1.children_score > var1.interestingness && this.children_score_weight > 0.0D) {
                var1.interestingness = var1.children_score;
            }

            if (var1.normalised_equiv_conj_score > var1.interestingness && this.equiv_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_equiv_conj_score;
            }

            if (var1.normalised_ne_conj_score > var1.interestingness && this.ne_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_ne_conj_score;
            }

            if (var1.normalised_invariance_score > var1.interestingness && this.invariance_weight > 0.0D) {
                var1.interestingness = var1.normalised_invariance_score;
            }

            if (var1.normalised_discrimination_score > var1.interestingness && this.discrimination_weight > 0.0D) {
                var1.interestingness = var1.normalised_discrimination_score;
            }

            if (var1.normalised_imp_conj_score > var1.interestingness && this.imp_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_imp_conj_score;
            }

            if (var1.normalised_pi_conj_score > var1.interestingness && this.pi_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_pi_conj_score;
            }

            if (var1.normalised_equiv_conj_num > var1.interestingness && this.equiv_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_equiv_conj_num;
            }

            if (var1.normalised_ne_conj_num > var1.interestingness && this.ne_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_ne_conj_num;
            }

            if (var1.normalised_imp_conj_num > var1.interestingness && this.imp_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_imp_conj_num;
            }

            if (var1.normalised_pi_conj_num > var1.interestingness && this.pi_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_pi_conj_num;
            }

            if (var1.cross_domain_score > var1.interestingness && this.cross_domain_weight > 0.0D) {
                var1.interestingness = var1.cross_domain_score;
            }

            if (var1.normalised_development_steps_num > var1.interestingness && this.development_steps_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_development_steps_num;
            }

            if (var1.highlight_score > var1.interestingness && this.highlight_weight > 0.0D) {
                var1.interestingness = var1.highlight_score;
            }

            if (var1.parent_score > var1.interestingness && this.parent_weight > 0.0D) {
                var1.interestingness = var1.parent_score;
            }

            if (var1.normalised_parsimony > var1.interestingness && this.parsimony_weight > 0.0D) {
                var1.interestingness = var1.normalised_parsimony;
            }

            if (var1.normalised_predictive_power > var1.interestingness && this.predictive_power_weight > 0.0D) {
                var1.interestingness = var1.normalised_predictive_power;
            }

            if (var1.normalised_productivity > var1.interestingness && this.productivity_weight > 0.0D) {
                var1.interestingness = var1.normalised_productivity;
            }

            if (var1.normalised_novelty > var1.interestingness && this.novelty_weight > 0.0D) {
                var1.interestingness = var1.normalised_novelty;
            }

            if (var1.normalised_variety > var1.interestingness && this.variety_weight > 0.0D) {
                var1.interestingness = var1.normalised_variety;
            }
        }

        if (this.keep_worst) {
            var1.interestingness = 1.0D;
            if (var1.normalised_applicability < var1.interestingness && this.applicability_weight > 0.0D) {
                var1.interestingness = var1.normalised_applicability;
            }

            if (var1.coverage < var1.interestingness && this.coverage_weight > 0.0D) {
                var1.interestingness = var1.coverage;
            }

            if (var1.normalised_comprehensibility < var1.interestingness && this.comprehensibility_weight > 0.0D) {
                var1.interestingness = var1.normalised_comprehensibility;
            }

            if (var1.children_score < var1.interestingness && this.children_score_weight > 0.0D) {
                var1.interestingness = var1.children_score;
            }

            if (var1.normalised_equiv_conj_score < var1.interestingness && this.equiv_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_equiv_conj_score;
            }

            if (var1.normalised_ne_conj_score < var1.interestingness && this.ne_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_ne_conj_score;
            }

            if (var1.normalised_invariance_score < var1.interestingness && this.invariance_weight > 0.0D) {
                var1.interestingness = var1.normalised_invariance_score;
            }

            if (var1.normalised_discrimination_score < var1.interestingness && this.discrimination_weight > 0.0D) {
                var1.interestingness = var1.normalised_discrimination_score;
            }

            if (var1.normalised_imp_conj_score < var1.interestingness && this.imp_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_imp_conj_score;
            }

            if (var1.normalised_pi_conj_score < var1.interestingness && this.pi_conj_score_weight > 0.0D) {
                var1.interestingness = var1.normalised_pi_conj_score;
            }

            if (var1.normalised_equiv_conj_num < var1.interestingness && this.equiv_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_equiv_conj_num;
            }

            if (var1.normalised_ne_conj_num < var1.interestingness && this.ne_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_ne_conj_num;
            }

            if (var1.normalised_imp_conj_num < var1.interestingness && this.imp_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_imp_conj_num;
            }

            if (var1.normalised_pi_conj_num < var1.interestingness && this.pi_conj_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_pi_conj_num;
            }

            if (var1.cross_domain_score < var1.interestingness && this.cross_domain_weight > 0.0D) {
                var1.interestingness = var1.cross_domain_score;
            }

            if (var1.normalised_development_steps_num < var1.interestingness && this.development_steps_num_weight > 0.0D) {
                var1.interestingness = var1.normalised_development_steps_num;
            }

            if (var1.highlight_score < var1.interestingness && this.highlight_weight > 0.0D) {
                var1.interestingness = var1.highlight_score;
            }

            if (var1.parent_score < var1.interestingness && this.parent_weight > 0.0D) {
                var1.interestingness = var1.parent_score;
            }

            if (var1.normalised_parsimony < var1.interestingness && this.parsimony_weight > 0.0D) {
                var1.interestingness = var1.normalised_parsimony;
            }

            if (var1.normalised_predictive_power < var1.interestingness && this.predictive_power_weight > 0.0D) {
                var1.interestingness = var1.normalised_predictive_power;
            }

            if (var1.normalised_productivity < var1.interestingness && this.productivity_weight > 0.0D) {
                var1.interestingness = var1.normalised_productivity;
            }

            if (var1.normalised_novelty < var1.interestingness && this.novelty_weight > 0.0D) {
                var1.interestingness = var1.normalised_novelty;
            }

            if (var1.normalised_variety < var1.interestingness && this.variety_weight > 0.0D) {
                var1.interestingness = var1.normalised_variety;
            }
        }

        if (var1.interestingness > 0.0D && var1.interestingness <= this.interestingness_zero_min) {
            var1.interestingness = 0.0D;
        }

    }

    private void addConceptToHashtable(Double var1, Hashtable var2, Vector var3, Concept var4, String var5) {
        Vector var6 = (Vector)var2.get(var1);
        if (var6 == null) {
            Vector var7 = new Vector();
            var7.addElement(var4);
            var7.trimToSize();
            var2.put(var1, var7);
            boolean var8 = false;
            double var9 = var1;

            for(int var11 = 0; var11 < var3.size() && !var8; ++var11) {
                double var12 = (Double)var3.elementAt(var11);
                if (var12 == var9) {
                    var8 = true;
                }

                if (var12 > var9) {
                    var3.insertElementAt(var1, var11);
                    var3.trimToSize();
                    var8 = true;
                }
            }

            if (!var8) {
                var3.addElement(var1);
                var3.trimToSize();
            }

            this.reCalculateConceptValues(var2, var3, var5);
        } else {
            var6.addElement(var4);
            var6.trimToSize();
        }

    }

    private void reCalculateConceptValues(Hashtable var1, Vector var2, String var3) {
        double var4 = new Double((double)(var2.size() - 1));

        for(int var6 = 0; var6 < var2.size(); ++var6) {
            Double var7 = (Double)var2.elementAt(var6);
            Vector var8 = (Vector)var1.get(var7);
            double var9 = new Double((double)var6);

            for(int var11 = 0; var11 < var8.size(); ++var11) {
                Concept var12 = (Concept)var8.elementAt(var11);
                double var13 = 0.0D;
                if (var2.size() == 1) {
                    var13 = 1.0D;
                } else {
                    var13 = var9 / var4;
                }

                if (var3.equals("equiv_conj_score")) {
                    var12.normalised_equiv_conj_score = var13;
                }

                if (var3.equals("ne_conj_score")) {
                    var12.normalised_ne_conj_score = var13;
                }

                if (var3.equals("imp_conj_score")) {
                    var12.normalised_imp_conj_score = var13;
                }

                if (var3.equals("pi_conj_score")) {
                    var12.normalised_pi_conj_score = var13;
                }

                if (var3.equals("equiv_conj_num")) {
                    var12.normalised_equiv_conj_num = var13;
                }

                if (var3.equals("ne_conj_num")) {
                    var12.normalised_ne_conj_num = var13;
                }

                if (var3.equals("imp_conj_num")) {
                    var12.normalised_imp_conj_num = var13;
                }

                if (var3.equals("pi_conj_num")) {
                    var12.normalised_pi_conj_num = var13;
                }
            }
        }

    }

    public boolean updateEquivConjectureScore(Concept var1, Equivalence var2) {
        if (!this.measure_concepts) {
            return false;
        } else {
            boolean var3 = false;
            double var4 = var1.equiv_conj_score;
            var1.equiv_conjectures.addElement(var2);
            var1.equiv_conj_sum += var2.interestingness;
            var1.equiv_conj_score = var1.equiv_conj_sum / new Double((double)var1.equiv_conjectures.size());
            var3 = this.updateNormalisedConjectureScores(var4, var1.equiv_conj_score, var1, this.equiv_conj_scores_hashtable, this.sorted_equiv_conj_scores, "equiv_conj_score");
            double var6 = new Double((double)(var1.equiv_conjectures.size() - 1));
            double var8 = new Double((double)var1.equiv_conjectures.size());
            boolean var10 = this.updateNormalisedConjectureScores(var6, var8, var1, this.equiv_conj_nums_hashtable, this.sorted_equiv_conj_nums, "equiv_conj_num");
            if (var3 || var10) {
                var3 = true;
            }

            return var3;
        }
    }

    public boolean updateNeConjectureScore(Vector var1, NonExists var2) {
        if (!this.measure_concepts) {
            return false;
        } else {
            boolean var3 = false;

            for(int var4 = 0; var4 < var1.size(); ++var4) {
                Concept var5 = (Concept)var1.elementAt(var4);
                double var6 = var5.ne_conj_score;
                var5.ne_conjectures.addElement(var2);
                var5.ne_conj_sum += var2.interestingness;
                var5.ne_conj_score = var5.ne_conj_sum / new Double((double)var5.ne_conjectures.size());
                boolean var8 = this.updateNormalisedConjectureScores(var6, var5.ne_conj_score, var5, this.ne_conj_scores_hashtable, this.sorted_ne_conj_scores, "ne_conj_score");
                double var9 = new Double((double)(var5.ne_conjectures.size() - 1));
                double var11 = new Double((double)var5.ne_conjectures.size());
                boolean var13 = this.updateNormalisedConjectureScores(var9, var11, var5, this.ne_conj_nums_hashtable, this.sorted_ne_conj_nums, "ne_conj_num");
                if (var8 || var13) {
                    var3 = true;
                }
            }

            return var3;
        }
    }

    public boolean updateImpConjectureScore(Implicate var1) {
        boolean var2 = false;
        Concept var3 = var1.premise_concept;
        double var4 = var3.imp_conj_score;
        var3.imp_conjectures.addElement(var1);
        var3.imp_conj_sum += var1.interestingness;
        var3.imp_conj_score = var3.imp_conj_sum / new Double((double)var3.imp_conjectures.size());
        var2 = this.updateNormalisedConjectureScores(var4, var3.imp_conj_score, var3, this.imp_conj_scores_hashtable, this.sorted_imp_conj_scores, "imp_conj_score");
        double var6 = new Double((double)(var3.imp_conjectures.size() - 1));
        double var8 = new Double((double)var3.imp_conjectures.size());
        boolean var10 = this.updateNormalisedConjectureScores(var6, var8, var3, this.imp_conj_nums_hashtable, this.sorted_imp_conj_nums, "imp_conj_num");
        if (var2 || var10) {
            var2 = true;
        }

        return var2;
    }

    public boolean updateNormalisedConjectureScores(double var1, double var3, Concept var5, Hashtable var6, Vector var7, String var8) {
        if (var1 == var3) {
            return false;
        } else {
            boolean var9 = false;
            Double var10 = new Double(var1);
            Vector var11 = (Vector)var6.get(var10);
            var11.removeElement(var5);
            if (var11.isEmpty()) {
                var6.remove(var10);
                var9 = true;
                var7.removeElement(var10);
            }

            Double var12 = new Double(var3);
            Vector var13 = (Vector)var6.get(var12);
            if (var13 == null) {
                Vector var14 = new Vector();
                var14.addElement(var5);
                var6.put(var12, var14);
                var9 = true;
                boolean var15 = false;

                for(int var16 = 0; var16 < var7.size() && !var15; ++var16) {
                    double var17 = (Double)var7.elementAt(var16);
                    if (var17 == var3) {
                        var15 = true;
                    }

                    if (var17 > var3) {
                        var7.insertElementAt(var12, var16);
                        var15 = true;
                    }
                }

                if (!var15) {
                    var7.addElement(var12);
                }
            } else if (!var13.contains(var5)) {
                var13.addElement(var5);
            }

            double var19 = 0.0D;
            double var20 = new Double((double)(var7.size() - 1));
            if (var20 == 0.0D) {
                var19 = 1.0D;
            } else {
                var19 = (double)var7.indexOf(var12) / var20;
            }

            if (var8.equals("equiv_conj_score")) {
                var5.normalised_equiv_conj_score = var19;
            }

            if (var8.equals("ne_conj_score")) {
                var5.normalised_ne_conj_score = var19;
            }

            if (var8.equals("imp_conj_score")) {
                var5.normalised_imp_conj_score = var19;
            }

            if (var8.equals("pi_conj_score")) {
                var5.normalised_pi_conj_score = var19;
            }

            if (var8.equals("equiv_conj_num")) {
                var5.normalised_equiv_conj_num = var19;
            }

            if (var8.equals("ne_conj_num")) {
                var5.normalised_ne_conj_num = var19;
            }

            if (var8.equals("imp_conj_num")) {
                var5.normalised_imp_conj_num = var19;
            }

            if (var8.equals("pi_conj_num")) {
                var5.normalised_pi_conj_num = var19;
            }

            if (var9) {
                this.reCalculateConceptValues(var6, var7, var8);
            }

            return var9;
        }
    }

    public boolean updatePiConjectureScore(Implicate var1) {
        boolean var2 = false;
        return var2;
    }

    public boolean updateNormalisedProductivities(Concept var1, boolean var2) {
        boolean var3 = false;
        Double var4;
        Vector var5;
        if (var2) {
            var1.productivity = this.default_productivity;
        } else {
            var4 = new Double(var1.productivity);
            var5 = (Vector)this.productivities_hashtable.get(var4);
            var5.removeElement(var1);
            if (var5.isEmpty()) {
                this.productivities_hashtable.remove(var4);
                var3 = true;
                this.sorted_productivities.removeElement(var4);
            }

            var1.productivity = var1.number_of_children / var1.development_steps_num;
        }

        var4 = new Double(var1.productivity);
        var5 = (Vector)this.productivities_hashtable.get(var4);
        int var8;
        if (var5 == null) {
            Vector var6 = new Vector();
            var6.addElement(var1);
            this.productivities_hashtable.put(var4, var6);
            var3 = true;
            boolean var7 = false;

            for(var8 = 0; var8 < this.sorted_productivities.size() && !var7; ++var8) {
                double var9 = (Double)this.sorted_productivities.elementAt(var8);
                if (var9 == var1.productivity) {
                    var7 = true;
                }

                if (var9 > var1.productivity) {
                    this.sorted_productivities.insertElementAt(var4, var8);
                    var7 = true;
                }
            }

            if (!var7) {
                this.sorted_productivities.addElement(var4);
            }
        } else if (!var5.contains(var1)) {
            var5.addElement(var1);
        }

        if (var3) {
            double var15 = new Double((double)this.sorted_productivities.size()) - 1.0D;

            for(var8 = 0; var8 < this.sorted_productivities.size(); ++var8) {
                Double var16 = (Double)this.sorted_productivities.elementAt(var8);
                Vector var10 = (Vector)this.productivities_hashtable.get(var16);
                double var11 = new Double((double)var8);

                for(int var13 = 0; var13 < var10.size(); ++var13) {
                    Concept var14 = (Concept)var10.elementAt(var13);
                    if (this.sorted_productivities.size() == 1) {
                        var14.normalised_productivity = 1.0D;
                    } else {
                        var14.normalised_productivity = var11 / var15;
                    }
                }
            }
        }

        return var3;
    }

    public boolean updateNormalisedDevelopments(Concept var1, boolean var2) {
        boolean var3 = false;
        Double var4;
        Vector var5;
        if (var2) {
            var1.development_steps_num = 0.0D;
        } else {
            var4 = new Double(var1.development_steps_num - 1.0D);
            if (!this.developments_hashtable.contains(var4)) {
                return false;
            }
            var5 = (Vector)this.developments_hashtable.get(var4);
            var5.removeElement(var1);
            if (var5.isEmpty()) {
                this.developments_hashtable.remove(var4);
                var3 = true;
                this.sorted_developments.removeElement(var4);
            }

            ++var1.development_steps_num;
        }

        var4 = new Double(var1.development_steps_num);
        if (!this.developments_hashtable.contains(var4)) {
            return false;
        }
        var5 = (Vector)this.developments_hashtable.get(var4);
        int var8;
        if (var5 == null) {
            Vector var6 = new Vector();
            var6.addElement(var1);
            this.developments_hashtable.put(var4, var6);
            var3 = true;
            boolean var7 = false;

            for(var8 = 0; var8 < this.sorted_developments.size() && !var7; ++var8) {
                double var9 = (Double)this.sorted_developments.elementAt(var8);
                if (var9 == var1.development_steps_num) {
                    var7 = true;
                }

                if (var9 > var1.development_steps_num) {
                    this.sorted_developments.insertElementAt(var4, var8);
                    var7 = true;
                }
            }

            if (!var7) {
                this.sorted_developments.addElement(var4);
            }
        } else if (!var5.contains(var1)) {
            var5.addElement(var1);
        }

        if (var3) {
            double var15 = new Double((double)this.sorted_developments.size()) - 1.0D;

            for(var8 = 0; var8 < this.sorted_developments.size(); ++var8) {
                Double var16 = (Double)this.sorted_developments.elementAt(var8);
                Vector var10 = (Vector)this.developments_hashtable.get(var16);
                double var11 = new Double((double)var8);

                for(int var13 = 0; var13 < var10.size(); ++var13) {
                    Concept var14 = (Concept)var10.elementAt(var13);
                    if (this.sorted_developments.size() == 1) {
                        var14.normalised_development_steps_num = 1.0D;
                    } else {
                        var14.normalised_development_steps_num = var11 / var15;
                    }
                }
            }
        }

        return var3;
    }

    public void getInvDiscPairs() {
        this.gold_standard_entity_names = this.gold_standard_categorisation.getEntities();
        this.gold_standard_categorisation.getCatPoses(this.gold_standard_entity_names);

        for(int var1 = 0; var1 < this.gold_standard_entity_names.size(); ++var1) {
            for(int var2 = var1 + 1; var2 < this.gold_standard_entity_names.size(); ++var2) {
                int[] var3 = new int[]{var1, var2};
                if (this.gold_standard_categorisation.cat_pos[var1] == this.gold_standard_categorisation.cat_pos[var2]) {
                    this.gold_standard_same_pairs.addElement(var3);
                } else {
                    this.gold_standard_different_pairs.addElement(var3);
                }
            }
        }

    }
}
