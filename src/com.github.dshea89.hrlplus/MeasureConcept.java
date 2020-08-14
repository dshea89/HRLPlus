package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class for measuring concepts.
 *
 * @author Simon Colton, started 23rd November 2000
 * @version 1.0 */

public class MeasureConcept extends Measure implements Serializable
{
    /** The list of positives for the positive applicabilities.
     */

    public Vector positives = new Vector();

    /** The list of negatives for the negative applicabilities.
     */

    public Vector negatives = new Vector();

    /** Whether or not to calculate the overall interestingness use a weighted sum.
     */

    public boolean use_weighted_sum = false;

    /** Whether or not to calculate the overall interestingness by keeping the
     * largest of the normalised values for each measure in the weighted sum.
     */

    public boolean keep_best = false;

    /** Whether or not to calculate the overall interestingness by keeping the
     * smallest of the normalised values for each measure in the weighted sum.
     */

    public boolean keep_worst = false;

    /** Whether or not to measure them at all
     */

    public boolean measure_concepts = true;

    /** The type of objects of interest in the categorisation to be learned.
     */

    public String object_types_to_learn = "";

    /** The weight for the invariance measure in the weighted sum.
     */

    public double invariance_weight = 0.5;

    /** The weight for the invariance measure in the weighted sum.
     */

    public double discrimination_weight = 0.5;

    /** The weight for the applicability measure in the weighted sum.
     */

    public double applicability_weight = 0.5;

    /** The weight for the coverage measure in the weighted sum.
     */

    public double coverage_weight = 0.5;

    /** The weight for the equivalence conjecture score in the weighted sum.
     */

    public double equiv_conj_score_weight = 0.5;

    /** The weight for the non-existence conjecture score in the weighted sum.
     */

    public double ne_conj_score_weight = 0.5;

    /** The weight for the prime implicate conjecture score in the weighted sum.
     */

    public double pi_conj_score_weight = 0.5;

    /** The weight for the implicate conjecture score in the weighted sum.
     */

    public double imp_conj_score_weight = 0.5;

    /** The weight for the children measure in the weighted sum.
     */

    /** The weight for the equivalence conjecture number in the weighted sum.
     */

    public double equiv_conj_num_weight = 0.5;

    /** The weight for the non-existence conjecture number in the weighted sum.
     */

    public double ne_conj_num_weight = 0.5;

    /** The weight for the positive applicability in the weighted sum.
     */

    public double positive_applicability_weight = 0.5;

    /** The weight for the positive applicability in the weighted sum.
     */

    public double negative_applicability_weight = 0.5;

    /** The weight for the prime implicate conjecture number in the weighted sum.
     */

    public double pi_conj_num_weight = 0.5;

    /** The weight for the implicate conjecture number in the weighted sum.
     */

    public double imp_conj_num_weight = 0.5;

    /** The weight for the children measure in the weighted sum.
     */

    public double children_score_weight = 0.5;

    /** The weight for the comprehensibility measure in the weighted sum.
     */

    public double comprehensibility_weight = 0.5;

    /** The weight for the comprehensibility measure in the weighted sum.
     */

    public double cross_domain_weight = 0.5;

    /** The weight for the highlight measure in the weighted sum.
     */

    public double highlight_weight = 0.5;

    /** The weight for the novelty measure in the weighted sum.
     */

    public double novelty_weight = 0.5;

    /** The weight for the parent measure in the weighted sum.
     */

    public double parent_weight = 0.5;

    /** The weight for the parsimony measure in the weighted sum.
     */

    public double parsimony_weight = 0.5;

    /** The weight for the predictive_power measure in the weighted sum.
     */

    public double predictive_power_weight = 0.5;

    /** The weight for the productivity measure in the weighted sum.
     */

    public double productivity_weight = 0.5;

    /** The weight for the steps involved measure in the weighted sum.
     */

    public double development_steps_num_weight = 0.5;

    /** The weight for the variety measure in the weighted sum.
     */

    public double variety_weight = 0.5;

    /** All the different equiv_conj scores so far.
     */

    public Vector all_equiv_conj_scores = new Vector();

    /** All the different ne_conj scores so far.
     */

    public Vector all_ne_conj_scores = new Vector();

    /** All the different invariance scores so far.
     */

    public Vector all_invariance_scores = new Vector();

    /** All the different discrimination scores so far.
     */

    public Vector all_discrimination_scores = new Vector();

    /** All the different imp_conj scores so far.
     */

    public Vector all_imp_conj_scores = new Vector();

    /** All the different pi_conj scores so far.
     */

    public Vector all_pi_conj_scores = new Vector();

    /** All the different applicabilities so far.
     */

    public Vector all_applicabilities = new Vector();

    /** All the different positive applicabilities so far.
     */

    public Vector all_positive_applicabilities = new Vector();

    /** All the different negative applicabilities so far.
     */

    public Vector all_negative_applicabilities = new Vector();

    /** All the different coverages so far.
     */

    public Vector all_coverages = new Vector();

    /** All the different comprehensibilities so far.
     */

    public Vector all_comprehensibilities = new Vector();

    /** All the different novelties so far.
     */

    public Vector all_novelties = new Vector();

    /** All the different parsimonies so far.
     */

    public Vector all_parsimonies = new Vector();

    /** All the different predictive_powers so far.
     */

    public Vector all_predictive_powers = new Vector();

    /** The equivalence conj scores so far (sorted).
     */

    public Vector sorted_equiv_conj_scores = new Vector();

    /** The equivalence conj numbers so far (sorted).
     */

    public Vector sorted_equiv_conj_nums = new Vector();

    /** A hashtable for the equiv_conj scores
     */

    public Hashtable equiv_conj_scores_hashtable = new Hashtable();

    /** A hashtable for the equiv_conj numbers scores
     */

    public Hashtable equiv_conj_nums_hashtable = new Hashtable();

    /** The non-existence conj scores so far (sorted).
     */

    public Vector sorted_ne_conj_scores = new Vector();

    /** A hashtable for the ne_conj scores
     */

    public Hashtable ne_conj_scores_hashtable = new Hashtable();

    /** The non-existence conj numbers scores so far (sorted).
     */

    public Vector sorted_ne_conj_nums = new Vector();

    /** A hashtable for the ne_conj numbers scores
     */

    public Hashtable ne_conj_nums_hashtable = new Hashtable();

    /** The implicate conj scores so far (sorted).
     */

    public Vector sorted_imp_conj_scores = new Vector();

    /** A hashtable for the equiv_conj scores
     */

    public Hashtable imp_conj_scores_hashtable = new Hashtable();

    /** The implicate conj scores so far (sorted).
     */

    public Vector sorted_imp_conj_nums = new Vector();

    /** A hashtable for the equiv_conj numbers scores
     */

    public Hashtable imp_conj_nums_hashtable = new Hashtable();

    /** The prime implicate conj scores so far (sorted).
     */

    public Vector sorted_pi_conj_scores = new Vector();

    /** A hashtable for the pi_conj scores
     */

    public Hashtable pi_conj_scores_hashtable = new Hashtable();

    /** The prime implicate conj scores so far (sorted).
     */

    public Vector sorted_pi_conj_nums = new Vector();

    /** A hashtable for the pi_conj number scores
     */

    public Hashtable pi_conj_nums_hashtable = new Hashtable();

    /** All the different productivities so far (sorted).
     */

    public Vector sorted_productivities = new Vector();

    /** All the different developments so far (sorted).
     */

    public Vector sorted_developments = new Vector();

    /** A hashtable for the productivities
     */

    public Hashtable productivities_hashtable = new Hashtable();

    /** A hashtable for the development numbers
     */

    public Hashtable developments_hashtable = new Hashtable();

    /** All the different varieties so far.
     */

    public Vector all_varieties = new Vector();

    /** The default for the productivity measure.
     */

    public double default_productivity = 0.0;

    /** The user specified coverage categorisation for use in the coverage measure.
     */

    public Categorisation coverage_categorisation = new Categorisation();

    /** The user specified gold standard categorisation for use in
     * the invariance and discrimination measures.
     */

    public Categorisation gold_standard_categorisation = new Categorisation();

    /** The set of pairs of entities which are categorised the same by the
     * gold standard categorisations.
     */

    public Vector gold_standard_same_pairs = new Vector();

    /** The set of pairs of entities which are categorised the same by the
     * gold standard categorisations.
     */

    public Vector gold_standard_different_pairs = new Vector();

    /** The set of entity names which appear in the gold standard categorisation.
     */

    public Vector gold_standard_entity_names = new Vector();

    /** This calculates all the measures for each concept. It also
     * updates the measures for all the other concepts if needed.
     */

    public void measureConcept(Concept concept, Vector concepts, boolean concept_is_new)
    {
        if (!measure_concepts)
            return;

        old_measures_have_been_updated = true;
        measures_need_updating = false;

        // Applicability //

        concept.applicability = concept.datatable.applicability();
        concept.normalised_applicability =
                super.normalisedValue(concept.applicability, all_applicabilities);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_applicabilities.indexOf(Double.toString(old_concept.applicability));
                old_concept.normalised_applicability =
                        (new Double(pos)).doubleValue()/((new Double(all_applicabilities.size())).doubleValue() - 1);
            }
        }

        // Positive Applicability //

        if (!positives.isEmpty())
            concept.positive_applicability = concept.datatable.applicability(positives);
        else
            concept.positive_applicability = 0;
        concept.normalised_positive_applicability =
                super.normalisedValue(concept.positive_applicability, all_positive_applicabilities);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_positive_applicabilities.indexOf(Double.toString(old_concept.positive_applicability));
                old_concept.normalised_positive_applicability =
                        (new Double(pos)).doubleValue()/((new Double(all_positive_applicabilities.size())).doubleValue() - 1);
            }
        }

        // Negative Applicability //

        if (!negatives.isEmpty())
            concept.negative_applicability = concept.datatable.applicability(negatives);
        else
            concept.negative_applicability = 0;
        concept.normalised_negative_applicability =
                super.normalisedValue(concept.negative_applicability, all_negative_applicabilities);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_negative_applicabilities.indexOf(Double.toString(old_concept.negative_applicability));
                old_concept.normalised_negative_applicability =
                        (new Double(pos)).doubleValue()/((new Double(all_negative_applicabilities.size())).doubleValue() - 1);
            }
        }

        // Predictive Power (for boolean predictions) //

        if (!concept.domain.equals(object_types_to_learn))
            concept.predictive_power = 0;
        else
            concept.predictive_power =
                    (concept.positive_applicability * positives.size() + (1-concept.negative_applicability) * negatives.size())/
                            (positives.size() + negatives.size());
        concept.normalised_predictive_power =
                super.normalisedValue(concept.predictive_power, all_predictive_powers);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_predictive_powers.indexOf(Double.toString(old_concept.predictive_power));
                old_concept.predictive_power =
                        (new Double(pos)).doubleValue()/((new Double(all_predictive_powers.size())).doubleValue() - 1);
            }
        }

        // Coverage //

        concept.coverage = 0;
        concept.normalised_coverage = 0;

        if (!coverage_categorisation.isEmpty())
        {
            concept.coverage = concept.datatable.coverage(coverage_categorisation);
            concept.normalised_coverage =
                    super.normalisedValue(concept.coverage, all_coverages);
            if (measures_need_updating)
            {
                old_measures_have_been_updated = true;
                for (int i=0; i<concepts.size(); i++)
                {
                    Concept old_concept = (Concept)concepts.elementAt(i);
                    int pos = all_coverages.indexOf(Double.toString(old_concept.coverage));
                    old_concept.normalised_coverage =
                            (new Double(pos)).doubleValue()/((new Double(all_coverages.size())).doubleValue() - 1);
                }
            }
        }

        // Children //

        double children_score = 0.0;
        for (int i=0; i<concept.children.size(); i++)
            children_score = children_score + ((Concept)concept.children.elementAt(i)).interestingness;
        if (concept.children.size()==0)
            concept.children_score = 0;
        else
            concept.children_score = children_score/concept.children.size();

        // Comprehensibility //

        concept.comprehensibility = 1/(new Double(concept.complexity)).doubleValue();
        concept.normalised_comprehensibility =
                super.normalisedValue(concept.comprehensibility, all_comprehensibilities);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_comprehensibilities.indexOf(Double.toString(old_concept.comprehensibility));
                old_concept.normalised_comprehensibility =
                        (new Double(pos)).doubleValue()/((new Double(all_comprehensibilities.size())).doubleValue() - 1);
            }
        }

        // Conjecture scores and numbers //

        // These are all set to 0 by default, which is fine. However, we need to make
        // sure that 0 is in the hashtables for the conjecture scores and numbers.

        addConceptToHashtable(new Double(0), equiv_conj_scores_hashtable,
                sorted_equiv_conj_scores, concept, "equiv_conj_score");
        addConceptToHashtable(new Double(0), ne_conj_scores_hashtable,
                sorted_ne_conj_scores, concept, "ne_conj_score");
        addConceptToHashtable(new Double(0), imp_conj_scores_hashtable,
                sorted_imp_conj_scores, concept, "imp_conj_score");
        addConceptToHashtable(new Double(0), pi_conj_scores_hashtable,
                sorted_pi_conj_scores, concept, "pi_conj_score");
        addConceptToHashtable(new Double(0), equiv_conj_nums_hashtable,
                sorted_equiv_conj_nums, concept, "equiv_conj_num");
        addConceptToHashtable(new Double(0), ne_conj_nums_hashtable,
                sorted_ne_conj_nums, concept, "ne_conj_num");
        addConceptToHashtable(new Double(0), imp_conj_nums_hashtable,
                sorted_imp_conj_nums, concept, "imp_conj_num");
        addConceptToHashtable(new Double(0), pi_conj_nums_hashtable,
                sorted_pi_conj_nums, concept, "pi_conj_num");

        // Cross Domain //

        if (concept.is_cross_domain)
            concept.cross_domain_score = 1;
        else
            concept.cross_domain_score = 0;

        // Development steps - note that these are also updated after every step.

        if (concept_is_new)
            updateNormalisedDevelopments(concept, true);

        // Novelty //

        concept.novelty = concept.categorisation.novelty;
        concept.normalised_novelty =
                super.normalisedValue(concept.novelty, all_novelties);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_novelties.indexOf(Double.toString(old_concept.novelty));
                old_concept.normalised_novelty =
                        (new Double(pos)).doubleValue()/((new Double(all_novelties.size())).doubleValue() - 1);
            }
        }

        // Invariance //

        if (concept.domain.equals(object_types_to_learn))
            concept.invariance =
                    concept.categorisation.invarianceWith(gold_standard_entity_names, gold_standard_same_pairs);
        else
            concept.invariance = 0;
        concept.normalised_invariance_score =
                super.normalisedValue(concept.invariance, all_invariance_scores);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_invariance_scores.indexOf(Double.toString(old_concept.invariance));
                old_concept.normalised_invariance_score =
                        (new Double(pos)).doubleValue()/((new Double(all_invariance_scores.size())).doubleValue() - 1);
            }
        }

        // Discrimination //

        if (concept.domain.equals(object_types_to_learn))
            concept.discrimination =
                    concept.categorisation.discriminationWith(gold_standard_entity_names, gold_standard_different_pairs);
        else
            concept.discrimination = 0;
        concept.normalised_discrimination_score =
                super.normalisedValue(concept.discrimination, all_discrimination_scores);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_discrimination_scores.indexOf(Double.toString(old_concept.discrimination));
                old_concept.normalised_discrimination_score =
                        (new Double(pos)).doubleValue()/((new Double(all_discrimination_scores.size())).doubleValue() - 1);
            }
        }

        // Parents //

        double parent_score = 0.0;
        for (int i=0; i<concept.parents.size(); i++)
            parent_score = parent_score + ((Concept)concept.parents.elementAt(i)).interestingness;
        if (concept.parents.size()==0)
            concept.parent_score = 0;
        else
            concept.parent_score = parent_score/concept.parents.size();

        // Parsimony //

        double datatable_size = concept.datatable.fullSize();
        if (datatable_size==0)
            concept.parsimony = 1;
        else
            concept.parsimony = 1/datatable_size;
        concept.normalised_parsimony =
                super.normalisedValue(concept.parsimony, all_parsimonies);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_parsimonies.indexOf(Double.toString(old_concept.parsimony));
                old_concept.normalised_parsimony =
                        (new Double(pos)).doubleValue()/((new Double(all_parsimonies.size())).doubleValue() - 1);
            }
        }

        // Productivity - note that these are also updated after every step.

        if (concept_is_new)
            updateNormalisedProductivities(concept, true);

        // Variety //

        concept.variety = (new Double(concept.categorisation.size())).doubleValue();
        concept.normalised_variety =
                super.normalisedValue(concept.variety, all_varieties);
        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept old_concept = (Concept)concepts.elementAt(i);
                int pos = all_varieties.indexOf(Double.toString(old_concept.variety));
                old_concept.normalised_variety =
                        (new Double(pos)).doubleValue()/((new Double(all_varieties.size())).doubleValue() - 1);
            }
        }

        calculateOverallValue(concept);

        // If any measures have changed for any old concept, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<concepts.size(); i++)
                calculateOverallValue((Concept)concepts.elementAt(i));
        }
    }

    /** This calcualtes the overall worth of the concept, a number which should be between
     * 0 and 1 (if the weights have been set to numbers between 0 and 1).
     */

    public void calculateOverallValue(Concept concept)
    {
        if (use_weighted_sum)
        {
            concept.interestingness =
                    (concept.normalised_applicability * applicability_weight) +
                            (concept.coverage * coverage_weight) +
                            (concept.normalised_comprehensibility * comprehensibility_weight) +
                            (concept.children_score * children_score_weight) +
                            (concept.normalised_equiv_conj_score * equiv_conj_score_weight) +
                            (concept.normalised_ne_conj_score * ne_conj_score_weight) +
                            (concept.normalised_invariance_score * invariance_weight) +
                            (concept.normalised_discrimination_score * discrimination_weight) +
                            (concept.normalised_imp_conj_score * imp_conj_score_weight) +
                            (concept.normalised_pi_conj_score * pi_conj_score_weight) +
                            (concept.normalised_equiv_conj_num * equiv_conj_num_weight) +
                            (concept.normalised_ne_conj_num * ne_conj_num_weight) +
                            (concept.normalised_negative_applicability * negative_applicability_weight) +
                            (concept.normalised_positive_applicability * positive_applicability_weight) +
                            (concept.normalised_imp_conj_num * imp_conj_num_weight) +
                            (concept.normalised_pi_conj_num * pi_conj_num_weight) +
                            (concept.cross_domain_score * cross_domain_weight) +
                            (concept.normalised_development_steps_num * development_steps_num_weight) +
                            (concept.highlight_score * highlight_weight) +
                            (concept.parent_score * parent_weight) +
                            (concept.normalised_predictive_power * predictive_power_weight) +
                            (concept.normalised_parsimony * parsimony_weight) +
                            (concept.normalised_productivity * productivity_weight) +
                            (concept.normalised_novelty * novelty_weight) +
                            (concept.normalised_variety * variety_weight);
        }

        if (keep_best)
        {
            concept.interestingness = 0;
            if (concept.normalised_applicability > concept.interestingness && applicability_weight > 0)
                concept.interestingness = concept.normalised_applicability;
            if (concept.normalised_positive_applicability > concept.interestingness && positive_applicability_weight > 0)
                concept.interestingness = concept.normalised_positive_applicability;
            if (concept.normalised_negative_applicability > concept.interestingness && negative_applicability_weight > 0)
                concept.interestingness = concept.normalised_negative_applicability;
            if (concept.coverage > concept.interestingness && coverage_weight > 0)
                concept.interestingness = concept.coverage;
            if (concept.normalised_comprehensibility > concept.interestingness && comprehensibility_weight > 0)
                concept.interestingness = concept.normalised_comprehensibility;
            if (concept.children_score > concept.interestingness && children_score_weight > 0)
                concept.interestingness = concept.children_score;
            if (concept.normalised_equiv_conj_score > concept.interestingness && equiv_conj_score_weight > 0)
                concept.interestingness = concept.normalised_equiv_conj_score;
            if (concept.normalised_ne_conj_score > concept.interestingness && ne_conj_score_weight > 0)
                concept.interestingness = concept.normalised_ne_conj_score;
            if (concept.normalised_invariance_score > concept.interestingness && invariance_weight > 0)
                concept.interestingness = concept.normalised_invariance_score;
            if (concept.normalised_discrimination_score > concept.interestingness && discrimination_weight > 0)
                concept.interestingness = concept.normalised_discrimination_score;
            if (concept.normalised_imp_conj_score > concept.interestingness && imp_conj_score_weight > 0)
                concept.interestingness = concept.normalised_imp_conj_score;
            if (concept.normalised_pi_conj_score > concept.interestingness && pi_conj_score_weight > 0)
                concept.interestingness = concept.normalised_pi_conj_score;
            if (concept.normalised_equiv_conj_num > concept.interestingness && equiv_conj_num_weight > 0)
                concept.interestingness = concept.normalised_equiv_conj_num;
            if (concept.normalised_ne_conj_num > concept.interestingness && ne_conj_num_weight > 0)
                concept.interestingness = concept.normalised_ne_conj_num;
            if (concept.normalised_imp_conj_num > concept.interestingness && imp_conj_num_weight > 0)
                concept.interestingness = concept.normalised_imp_conj_num;
            if (concept.normalised_pi_conj_num > concept.interestingness && pi_conj_num_weight > 0)
                concept.interestingness = concept.normalised_pi_conj_num;
            if (concept.cross_domain_score > concept.interestingness && cross_domain_weight > 0)
                concept.interestingness = concept.cross_domain_score;
            if (concept.normalised_development_steps_num > concept.interestingness && development_steps_num_weight > 0)
                concept.interestingness = concept.normalised_development_steps_num;
            if (concept.highlight_score > concept.interestingness && highlight_weight > 0)
                concept.interestingness = concept.highlight_score;
            if (concept.parent_score > concept.interestingness && parent_weight > 0)
                concept.interestingness = concept.parent_score;
            if (concept.normalised_parsimony > concept.interestingness && parsimony_weight > 0)
                concept.interestingness = concept.normalised_parsimony;
            if (concept.normalised_predictive_power > concept.interestingness && predictive_power_weight > 0)
                concept.interestingness = concept.normalised_predictive_power;
            if (concept.normalised_productivity > concept.interestingness && productivity_weight > 0)
                concept.interestingness = concept.normalised_productivity;
            if (concept.normalised_novelty > concept.interestingness && novelty_weight > 0)
                concept.interestingness = concept.normalised_novelty;
            if (concept.normalised_variety > concept.interestingness && variety_weight > 0)
                concept.interestingness = concept.normalised_variety;
        }

        if (keep_worst)
        {
            concept.interestingness = 1;
            if (concept.normalised_applicability < concept.interestingness && applicability_weight > 0)
                concept.interestingness = concept.normalised_applicability;
            if (concept.coverage < concept.interestingness && coverage_weight > 0)
                concept.interestingness = concept.coverage;
            if (concept.normalised_comprehensibility < concept.interestingness && comprehensibility_weight > 0)
                concept.interestingness = concept.normalised_comprehensibility;
            if (concept.children_score < concept.interestingness && children_score_weight > 0)
                concept.interestingness = concept.children_score;
            if (concept.normalised_equiv_conj_score < concept.interestingness && equiv_conj_score_weight > 0)
                concept.interestingness = concept.normalised_equiv_conj_score;
            if (concept.normalised_ne_conj_score < concept.interestingness && ne_conj_score_weight > 0)
                concept.interestingness = concept.normalised_ne_conj_score;
            if (concept.normalised_invariance_score < concept.interestingness && invariance_weight > 0)
                concept.interestingness = concept.normalised_invariance_score;
            if (concept.normalised_discrimination_score < concept.interestingness && discrimination_weight > 0)
                concept.interestingness = concept.normalised_discrimination_score;
            if (concept.normalised_imp_conj_score < concept.interestingness && imp_conj_score_weight > 0)
                concept.interestingness = concept.normalised_imp_conj_score;
            if (concept.normalised_pi_conj_score < concept.interestingness && pi_conj_score_weight > 0)
                concept.interestingness = concept.normalised_pi_conj_score;
            if (concept.normalised_equiv_conj_num < concept.interestingness && equiv_conj_num_weight > 0)
                concept.interestingness = concept.normalised_equiv_conj_num;
            if (concept.normalised_ne_conj_num < concept.interestingness && ne_conj_num_weight > 0)
                concept.interestingness = concept.normalised_ne_conj_num;
            if (concept.normalised_imp_conj_num < concept.interestingness && imp_conj_num_weight > 0)
                concept.interestingness = concept.normalised_imp_conj_num;
            if (concept.normalised_pi_conj_num < concept.interestingness && pi_conj_num_weight > 0)
                concept.interestingness = concept.normalised_pi_conj_num;
            if (concept.cross_domain_score < concept.interestingness && cross_domain_weight > 0)
                concept.interestingness = concept.cross_domain_score;
            if (concept.normalised_development_steps_num < concept.interestingness && development_steps_num_weight > 0)
                concept.interestingness = concept.normalised_development_steps_num;
            if (concept.highlight_score < concept.interestingness && highlight_weight > 0)
                concept.interestingness = concept.highlight_score;
            if (concept.parent_score < concept.interestingness && parent_weight > 0)
                concept.interestingness = concept.parent_score;
            if (concept.normalised_parsimony < concept.interestingness && parsimony_weight > 0)
                concept.interestingness = concept.normalised_parsimony;
            if (concept.normalised_predictive_power < concept.interestingness && predictive_power_weight > 0)
                concept.interestingness = concept.normalised_predictive_power;
            if (concept.normalised_productivity < concept.interestingness && productivity_weight > 0)
                concept.interestingness = concept.normalised_productivity;
            if (concept.normalised_novelty < concept.interestingness && novelty_weight > 0)
                concept.interestingness = concept.normalised_novelty;
            if (concept.normalised_variety < concept.interestingness && variety_weight > 0)
                concept.interestingness = concept.normalised_variety;
        }

        if (concept.interestingness > 0 && concept.interestingness <= interestingness_zero_min)
            concept.interestingness = 0;
    }

    private void addConceptToHashtable(Double value, Hashtable hashtable, Vector sorted,
                                       Concept concept, String type)
    {
        Vector concepts_with = (Vector)hashtable.get(value);
        if (concepts_with==null)
        {
            Vector new_vector = new Vector();
            new_vector.addElement(concept);
            new_vector.trimToSize();
            hashtable.put(value, new_vector);
            boolean placed = false;
            double new_sc = value.doubleValue();
            for (int i=0; i<sorted.size() && !placed; i++)
            {
                double already_score = ((Double)sorted.elementAt(i)).doubleValue();
                if (already_score==new_sc)
                    placed = true;
                if (already_score>new_sc)
                {
                    sorted.insertElementAt(value,i);
                    sorted.trimToSize();
                    placed = true;
                }
            }
            if (!placed)
            {
                sorted.addElement(value);
                sorted.trimToSize();
            }
            reCalculateConceptValues(hashtable, sorted, type);
        }
        else
        {
            concepts_with.addElement(concept);
            concepts_with.trimToSize();
        }
    }

    private void reCalculateConceptValues(Hashtable hashtable, Vector sorted, String type)
    {
        double size = (new Double(sorted.size()-1)).doubleValue();
        for (int i=0; i<sorted.size(); i++)
        {
            Double de = (Double)sorted.elementAt(i);
            Vector concepts_with = (Vector)hashtable.get(de);
            double position = (new Double(i)).doubleValue();
            for (int j=0; j<concepts_with.size(); j++)
            {
                Concept c = (Concept)concepts_with.elementAt(j);
                double new_value = 0;
                if (sorted.size()==1)
                    new_value = 1;
                else
                    new_value = position/size;

                if (type.equals("equiv_conj_score"))
                    c.normalised_equiv_conj_score=new_value;
                if (type.equals("ne_conj_score"))
                    c.normalised_ne_conj_score=new_value;
                if (type.equals("imp_conj_score"))
                    c.normalised_imp_conj_score=new_value;
                if (type.equals("pi_conj_score"))
                    c.normalised_pi_conj_score=new_value;

                if (type.equals("equiv_conj_num"))
                    c.normalised_equiv_conj_num=new_value;
                if (type.equals("ne_conj_num"))
                    c.normalised_ne_conj_num=new_value;
                if (type.equals("imp_conj_num"))
                    c.normalised_imp_conj_num=new_value;
                if (type.equals("pi_conj_num"))
                    c.normalised_pi_conj_num=new_value;
            }
        }

    }

    /** This updates the equivalence conjecture score of the concept in the light
     * of an equivalence conjecture involving the concept.
     */

    public boolean updateEquivConjectureScore(Concept concept_to_update, Equivalence conjecture)
    {
        if (!measure_concepts)
            return false;
        boolean output = false;
        double old_score = concept_to_update.equiv_conj_score;
        concept_to_update.equiv_conjectures.addElement(conjecture);
        concept_to_update.equiv_conj_sum =
                concept_to_update.equiv_conj_sum + conjecture.interestingness;

        concept_to_update.equiv_conj_score =
                concept_to_update.equiv_conj_sum/
                        (new Double(concept_to_update.equiv_conjectures.size())).doubleValue();

        output = updateNormalisedConjectureScores(old_score, concept_to_update.equiv_conj_score,
                concept_to_update, equiv_conj_scores_hashtable,
                sorted_equiv_conj_scores, "equiv_conj_score");

        double old_num = (new Double(concept_to_update.equiv_conjectures.size()-1)).doubleValue();
        double new_num = (new Double(concept_to_update.equiv_conjectures.size())).doubleValue();
        boolean output2 = updateNormalisedConjectureScores(old_num, new_num,
                concept_to_update, equiv_conj_nums_hashtable,
                sorted_equiv_conj_nums, "equiv_conj_num");

        if (output==true || output2==true)
            output = true;
        return output;
    }

    /** This updates the non-existence conjecture score of the concept in the light
     * of an equivalence conjecture involving the concept.
     */

    public boolean updateNeConjectureScore(Vector concepts_to_update, NonExists conjecture)
    {
        if (!measure_concepts)
            return false;
        boolean output = false;
        for (int i=0; i<concepts_to_update.size(); i++)
        {
            Concept concept_to_update = (Concept)concepts_to_update.elementAt(i);
            double old_score = concept_to_update.ne_conj_score;
            concept_to_update.ne_conjectures.addElement(conjecture);
            concept_to_update.ne_conj_sum =
                    concept_to_update.ne_conj_sum + conjecture.interestingness;

            concept_to_update.ne_conj_score =
                    concept_to_update.ne_conj_sum/
                            (new Double(concept_to_update.ne_conjectures.size())).doubleValue();

            boolean outp = updateNormalisedConjectureScores(old_score, concept_to_update.ne_conj_score,
                    concept_to_update, ne_conj_scores_hashtable,
                    sorted_ne_conj_scores, "ne_conj_score");
            double old_num = (new Double(concept_to_update.ne_conjectures.size()-1)).doubleValue();
            double new_num = (new Double(concept_to_update.ne_conjectures.size())).doubleValue();
            boolean outp2 = updateNormalisedConjectureScores(old_num, new_num,
                    concept_to_update, ne_conj_nums_hashtable,
                    sorted_ne_conj_nums, "ne_conj_num");
            if (outp==true || outp2==true)
                output = true;
        }
        return output;
    }

    /** This updates the implicate conjecture score of the concept in the light
     * of an equivalence conjecture involving the concept.
     */

    public boolean updateImpConjectureScore(Implicate conjecture)
    {
        boolean output = false;
        Concept concept_to_update = conjecture.premise_concept;
        double old_score = concept_to_update.imp_conj_score;

        concept_to_update.imp_conjectures.addElement(conjecture);
        concept_to_update.imp_conj_sum =
                concept_to_update.imp_conj_sum + conjecture.interestingness;

        concept_to_update.imp_conj_score =
                concept_to_update.imp_conj_sum/
                        (new Double(concept_to_update.imp_conjectures.size())).doubleValue();

        output = updateNormalisedConjectureScores(old_score, concept_to_update.imp_conj_score,
                concept_to_update, imp_conj_scores_hashtable,
                sorted_imp_conj_scores, "imp_conj_score");

        double old_num = (new Double(concept_to_update.imp_conjectures.size()-1)).doubleValue();
        double new_num = (new Double(concept_to_update.imp_conjectures.size())).doubleValue();
        boolean output2 = updateNormalisedConjectureScores(old_num, new_num,
                concept_to_update, imp_conj_nums_hashtable,
                sorted_imp_conj_nums, "imp_conj_num");

        if (output==true || output2==true)
            output = true;

        return output;
    }

    public boolean updateNormalisedConjectureScores(double old_sc, double new_sc, Concept concept,
                                                    Hashtable hashtable, Vector sorted, String type)
    {
        if (old_sc==new_sc)
            return false;
        boolean need_to_recalculate_normalised = false;
        Double old_score = new Double(old_sc);
        Vector old_concepts_with_score = (Vector)hashtable.get(old_score);

        old_concepts_with_score.removeElement(concept);
        if (old_concepts_with_score.isEmpty())
        {
            hashtable.remove(old_score);
            need_to_recalculate_normalised = true;
            sorted.removeElement(old_score);
        }

        Double new_score = new Double(new_sc);
        Vector concepts_with_score = (Vector)hashtable.get(new_score);

        if (concepts_with_score==null)
        {
            Vector new_lot = new Vector();
            new_lot.addElement(concept);
            hashtable.put(new_score,new_lot);
            need_to_recalculate_normalised = true;
            boolean placed = false;
            for (int i=0; i<sorted.size() && !placed; i++)
            {
                double already_score = ((Double)sorted.elementAt(i)).doubleValue();
                if (already_score==new_sc)
                    placed = true;
                if (already_score>new_sc)
                {
                    sorted.insertElementAt(new_score,i);
                    placed = true;
                }
            }
            if (!placed)
                sorted.addElement(new_score);
        }
        else
        {
            if (!concepts_with_score.contains(concept))
                concepts_with_score.addElement(concept);
        }

        double new_val = 0;
        double size = (new Double(sorted.size()-1)).doubleValue();
        if (size==0)
            new_val = 1;
        else
            new_val = (sorted.indexOf(new_score))/size;

        if (type.equals("equiv_conj_score"))
            concept.normalised_equiv_conj_score=new_val;
        if (type.equals("ne_conj_score"))
            concept.normalised_ne_conj_score=new_val;
        if (type.equals("imp_conj_score"))
            concept.normalised_imp_conj_score=new_val;
        if (type.equals("pi_conj_score"))
            concept.normalised_pi_conj_score=new_val;

        if (type.equals("equiv_conj_num"))
            concept.normalised_equiv_conj_num=new_val;
        if (type.equals("ne_conj_num"))
            concept.normalised_ne_conj_num=new_val;
        if (type.equals("imp_conj_num"))
            concept.normalised_imp_conj_num=new_val;
        if (type.equals("pi_conj_num"))
            concept.normalised_pi_conj_num=new_val;

        if (need_to_recalculate_normalised)
            reCalculateConceptValues(hashtable, sorted, type);

        return need_to_recalculate_normalised;
    }

    /** This updates the equivalence conjecture score of the concept in the light
     * of an equivalence conjecture involving the concept.
     */

    public boolean updatePiConjectureScore(Implicate conjecture)
    {
        boolean output = false;
        return output;
    }

    /** This updates all the productivities of the concepts in the light of a new step.
     */

    public boolean updateNormalisedProductivities(Concept concept, boolean is_new_concept)
    {
        boolean need_to_recalculate_normalised = false;
        if (is_new_concept)
            concept.productivity = default_productivity;
        else
        {
            Double old_productivity = new Double(concept.productivity);
            Vector old_concepts_with_prod = (Vector)productivities_hashtable.get(old_productivity);
            old_concepts_with_prod.removeElement(concept);
            if (old_concepts_with_prod.isEmpty())
            {
                productivities_hashtable.remove(old_productivity);
                need_to_recalculate_normalised = true;
                sorted_productivities.removeElement(old_productivity);
            }
            concept.productivity = concept.number_of_children/concept.development_steps_num;
        }

        Double new_productivity = new Double(concept.productivity);
        Vector concepts_with_prod = (Vector)productivities_hashtable.get(new_productivity);
        if (concepts_with_prod==null)
        {
            Vector new_lot = new Vector();
            new_lot.addElement(concept);
            productivities_hashtable.put(new_productivity,new_lot);
            need_to_recalculate_normalised = true;
            boolean placed = false;
            for (int i=0; i<sorted_productivities.size() && !placed; i++)
            {
                double old_prod = ((Double)sorted_productivities.elementAt(i)).doubleValue();
                if (old_prod==concept.productivity)
                    placed = true;
                if (old_prod>concept.productivity)
                {
                    sorted_productivities.insertElementAt(new_productivity,i);
                    placed = true;
                }
            }
            if (!placed)
                sorted_productivities.addElement(new_productivity);
        }
        else
        {
            if (!concepts_with_prod.contains(concept))
                concepts_with_prod.addElement(concept);
        }

        if (need_to_recalculate_normalised)
        {
            double size = (new Double(sorted_productivities.size())).doubleValue() - 1;
            for (int i=0; i<sorted_productivities.size(); i++)
            {
                Double pr = (Double)sorted_productivities.elementAt(i);
                Vector concepts_with_pty = (Vector)productivities_hashtable.get(pr);
                double position = (new Double(i)).doubleValue();
                for (int j=0; j<concepts_with_pty.size(); j++)
                {
                    Concept c = (Concept)concepts_with_pty.elementAt(j);
                    if (sorted_productivities.size()==1)
                        c.normalised_productivity=1;
                    else
                        c.normalised_productivity = position/size;
                }
            }
        }
        return need_to_recalculate_normalised;
    }

    public boolean updateNormalisedDevelopments(Concept concept, boolean is_new_concept)
    {
        boolean need_to_recalculate_normalised = false;
        if (is_new_concept)
            concept.development_steps_num = 0;
        else
        {
            Double old_development_steps_num = new Double(concept.development_steps_num-1);
            Vector old_concepts_with_devel = (Vector)developments_hashtable.get(old_development_steps_num);
            old_concepts_with_devel.removeElement(concept);
            if (old_concepts_with_devel.isEmpty())
            {
                developments_hashtable.remove(old_development_steps_num);
                need_to_recalculate_normalised = true;
                sorted_developments.removeElement(old_development_steps_num);
            }
            concept.development_steps_num++;
        }

        Double new_development_steps_num = new Double(concept.development_steps_num);
        Vector concepts_with_devel = (Vector)developments_hashtable.get(new_development_steps_num);
        if (concepts_with_devel==null)
        {
            Vector new_lot = new Vector();
            new_lot.addElement(concept);
            developments_hashtable.put(new_development_steps_num,new_lot);
            need_to_recalculate_normalised = true;
            boolean placed = false;
            for (int i=0; i<sorted_developments.size() && !placed; i++)
            {
                double old_devel = ((Double)sorted_developments.elementAt(i)).doubleValue();
                if (old_devel==concept.development_steps_num)
                    placed = true;
                if (old_devel>concept.development_steps_num)
                {
                    sorted_developments.insertElementAt(new_development_steps_num,i);
                    placed = true;
                }
            }
            if (!placed)
                sorted_developments.addElement(new_development_steps_num);
        }
        else
        {
            if (!concepts_with_devel.contains(concept))
                concepts_with_devel.addElement(concept);
        }

        if (need_to_recalculate_normalised)
        {
            double size = (new Double(sorted_developments.size())).doubleValue() - 1;
            for (int i=0; i<sorted_developments.size(); i++)
            {
                Double de = (Double)sorted_developments.elementAt(i);
                Vector concepts_with_pty = (Vector)developments_hashtable.get(de);
                double position = (new Double(i)).doubleValue();
                for (int j=0; j<concepts_with_pty.size(); j++)
                {
                    Concept c = (Concept)concepts_with_pty.elementAt(j);
                    if (sorted_developments.size()==1)
                        c.normalised_development_steps_num=1;
                    else
                        c.normalised_development_steps_num = position/size;
                }
            }
        }
        return need_to_recalculate_normalised;
    }

    /** This correlates the pairs of entities (given) that are categorised
     * as the same/different by the gold standard categorisation.
     */

    public void getInvDiscPairs()
    {
        gold_standard_entity_names = gold_standard_categorisation.getEntities();
        gold_standard_categorisation.getCatPoses(gold_standard_entity_names);
        for (int i=0; i<gold_standard_entity_names.size(); i++)
        {
            for (int j=i+1; j<gold_standard_entity_names.size(); j++)
            {
                int[] e_pair = new int[2];
                e_pair[0] = i;
                e_pair[1] = j;
                if (gold_standard_categorisation.cat_pos[i]==gold_standard_categorisation.cat_pos[j])
                    gold_standard_same_pairs.addElement(e_pair);
                else
                    gold_standard_different_pairs.addElement(e_pair);
            }
        }
    }
}
