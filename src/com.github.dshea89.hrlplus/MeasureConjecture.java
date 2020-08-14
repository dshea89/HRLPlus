package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class for measuring conjectures.
 *
 * @author Simon Colton, started 1st December 2000
 * @version 1.0 */

public class MeasureConjecture extends Measure implements Serializable
{
    /** Whether or not to measure the conjectures
     */

    public boolean measure_conjectures = true;

    /** The weight for the applicability measure in the weighted sum.
     */

    public double applicability_weight = 0.5;

    /** The weight for the comprehensibility measure in the weighted sum.
     */

    public double comprehensibility_weight = 0.5;

    /** The weight for the comprehensibility measure in the weighted sum.
     */

    public double cross_domain_weight = 0.5;

    /** The weight for the surprisingness measure in the weighted sum.
     */

    public double surprisingness_weight = 0.5;

    /** The weight for the plausibility measure in the weighted sum.
     */

    public double plausibility_weight = 0.5;

    /** All the different applicabilities so far for equivalence conjectures.
     */

    public Vector all_equiv_applicabilities = new Vector();

    /** All the different applicabilities so far for implication conjectures.
     */

    public Vector all_implication_applicabilities = new Vector();

    /** All the different applicabilities so far for implicate conjectures.
     */

    public Vector all_implicate_applicabilities = new Vector();

    /** All the different comprehensibilities so far for implicate conjectures.
     */

    public Vector all_implicate_comprehensibilities = new Vector();

    /** All the different comprehensibilities so far for equivalence conjectures.
     */

    public Vector all_equiv_comprehensibilities = new Vector();

    /** All the different comprehensibilities so far for implication conjectures.
     */

    public Vector all_implication_comprehensibilities = new Vector();

    /** All the different surprisingnesses so far for equivalence conjectures.
     */

    public Vector all_equiv_surprisingnesses = new Vector();

    /** All the different surprisingnesses so far for implication conjectures.
     */

    public Vector all_implication_surprisingnesses = new Vector();

    /** All the different comprehensibilities so far for non-exists conjectures.
     */

    public Vector all_ne_comprehensibilities = new Vector();

    /** All the different surprisingnesses so far for non-exists conjectures.
     */

    public Vector all_ne_surprisingnesses = new Vector();

    /** This calculates all the measures for a given Equivalence
     * conjecture. It also updates the measures for all the other
     * conjectures if needed.
     */

    public void measureConjecture(Equivalence conjecture, Vector conjectures)
    {
        if (!measure_conjectures)
            return;
        old_measures_have_been_updated = false;

        // Arity //

        conjecture.arity = conjecture.lh_concept.arity;

        // Applicability //

        conjecture.applicability = conjecture.lh_concept.applicability;
        conjecture.normalised_applicability = super.normalisedValue(conjecture.applicability, all_equiv_applicabilities);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_equiv_applicabilities.indexOf(Double.toString(old_conjecture.applicability));
                old_conjecture.normalised_applicability =
                        (new Double(pos)).doubleValue()/((new Double(all_equiv_applicabilities.size())).doubleValue() - 1);
            }
        }

        // Comprehensibility //

        conjecture.complexity = conjecture.lh_concept.complexity + conjecture.rh_concept.complexity;
        conjecture.comprehensibility = 1/conjecture.complexity;
        conjecture.normalised_comprehensibility =
                super.normalisedValue(conjecture.comprehensibility, all_equiv_comprehensibilities);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_equiv_comprehensibilities.indexOf(Double.toString(old_conjecture.comprehensibility));
                old_conjecture.normalised_comprehensibility =
                        (new Double(pos)).doubleValue()/((new Double(all_equiv_comprehensibilities.size())).doubleValue() - 1);
            }
        }

        // Surprisingness //

        double surprisingness = 0;
        for (int i=0; i<conjecture.lh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.rh_concept.ancestor_ids.contains(conjecture.lh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }
        for (int i=0; i<conjecture.rh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.lh_concept.ancestor_ids.contains(conjecture.rh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }

        conjecture.surprisingness = surprisingness;
        conjecture.normalised_surprisingness = super.normalisedValue(surprisingness, all_equiv_surprisingnesses);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_equiv_surprisingnesses.indexOf(Double.toString(old_conjecture.surprisingness));
                old_conjecture.normalised_surprisingness =
                        (new Double(pos)).doubleValue()/((new Double(all_equiv_surprisingnesses.size())).doubleValue() - 1);
            }
        }

        // Plausibility //

        double plausibility = 0;
        Vector entities_conj_discusses =  removeDuplicates(conjecture.lh_concept.getPositives(), conjecture.rh_concept.getPositives());
        try {
            plausibility = ((double)(entities_conj_discusses.size() - conjecture.counterexamples.size()))/((double)entities_conj_discusses.size());
        } catch (ArithmeticException ignored) {
        }
        conjecture.plausibility = plausibility;

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_equiv_surprisingnesses.indexOf(Double.toString(old_conjecture.surprisingness));
                old_conjecture.normalised_surprisingness =
                        (new Double(pos)).doubleValue()/((new Double(all_equiv_surprisingnesses.size())).doubleValue() - 1);
            }
        }

        calculateOverallValue(conjecture);

        // If any measures have changed for any old conjecture, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<conjectures.size(); i++)
                calculateOverallValue((Equivalence)conjectures.elementAt(i));
        }
    }


    /** This calculates all the measures for a given NearEquivalence
     * conjecture. It also updates the measures for all the other
     * conjectures if needed. It does not calculate the normalised
     * value.
     */

    public void measureConjecture(NearEquivalence conjecture, Vector conjectures)
    {
        System.out.println("started measureConjecture on " + conjecture.writeConjecture());
        if (!measure_conjectures)
            return;
        old_measures_have_been_updated = false;

        // Arity //

        conjecture.arity = conjecture.lh_concept.arity;

        // Applicability //

        Vector num_entities = removeDuplicates(conjecture.lh_concept.getPositives(),conjecture.rh_concept.getPositives());
        conjecture.applicability = num_entities.size();


        // Comprehensibility //

        conjecture.complexity = conjecture.lh_concept.complexity + conjecture.rh_concept.complexity;
        conjecture.comprehensibility = 1/conjecture.complexity;

        // Surprisingness //

        double surprisingness = 0;
        for (int i=0; i<conjecture.lh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.rh_concept.ancestor_ids.contains(conjecture.lh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }
        for (int i=0; i<conjecture.rh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.lh_concept.ancestor_ids.contains(conjecture.rh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }

        conjecture.surprisingness = surprisingness;

        // Plausibility //

        double plausibility = 0;
        Vector entities_conj_discusses = removeDuplicates(conjecture.lh_concept.getPositives(), conjecture.rh_concept.getPositives());
        plausibility = ((double)(entities_conj_discusses.size() - conjecture.counterexamples.size()))/((double)entities_conj_discusses.size());
        conjecture.plausibility = plausibility;

        calculateOverallValue(conjecture);

        // If any measures have changed for any old conjecture, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<conjectures.size(); i++)
                calculateOverallValue((NearEquivalence)conjectures.elementAt(i));
        }
    }





    /** This calculates all the measures for a given Implication
     * conjecture. It also updates the measures for all the other
     * conjectures if needed.
     */

    public void measureConjecture(Implication conjecture, Vector conjectures)
    {
        if (!measure_conjectures)
            return;
        old_measures_have_been_updated = false;

        // Arity //

        conjecture.arity = conjecture.lh_concept.arity;

        // Applicability //

        conjecture.applicability = conjecture.lh_concept.applicability;
        conjecture.normalised_applicability = super.normalisedValue(conjecture.applicability, all_implication_applicabilities);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_implication_applicabilities.indexOf(Double.toString(old_conjecture.applicability));
                old_conjecture.normalised_applicability =
                        (new Double(pos)).doubleValue()/((new Double(all_implication_applicabilities.size())).doubleValue() - 1);
            }
        }

        // Comprehensibility //

        conjecture.complexity = conjecture.lh_concept.complexity + conjecture.rh_concept.complexity;
        conjecture.comprehensibility = 1/conjecture.complexity;
        conjecture.normalised_comprehensibility =
                super.normalisedValue(conjecture.comprehensibility, all_implication_comprehensibilities);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_implication_comprehensibilities.indexOf(Double.toString(old_conjecture.comprehensibility));
                old_conjecture.normalised_comprehensibility =
                        (new Double(pos)).doubleValue()/((new Double(all_implication_comprehensibilities.size())).doubleValue() - 1);
            }
        }

        // Surprisingness //

        double surprisingness = 0;
        for (int i=0; i<conjecture.lh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.rh_concept.ancestor_ids.contains(conjecture.lh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }
        for (int i=0; i<conjecture.rh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.lh_concept.ancestor_ids.contains(conjecture.rh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }

        conjecture.surprisingness = surprisingness;
        conjecture.normalised_surprisingness = super.normalisedValue(surprisingness, all_implication_surprisingnesses);

        // Plausibility //

        double plausibility = 0;
        Vector entities_conj_discusses = conjecture.lh_concept.getPositives();
        try {
            plausibility = ((double) (entities_conj_discusses.size() - conjecture.counterexamples.size())) / ((double) entities_conj_discusses.size());
        } catch (ArithmeticException ignored) {
        }
        conjecture.plausibility = plausibility;

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_implication_surprisingnesses.indexOf(Double.toString(old_conjecture.surprisingness));
                old_conjecture.normalised_surprisingness =
                        (new Double(pos)).doubleValue()/((new Double(all_implication_surprisingnesses.size())).doubleValue() - 1);
            }
        }

        calculateOverallValue(conjecture);

        // If any measures have changed for any old conjecture, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<conjectures.size(); i++)
                calculateOverallValue((Implication)conjectures.elementAt(i));
        }
    }


    /** This calculates all the measures for a given NearImplication
     * conjecture. It also updates the measures for all the other
     * conjectures if needed. It does not calculate the normalised
     * value.
     */

    public void measureConjecture(NearImplication conjecture, Vector conjectures)
    {
        if (!measure_conjectures)
            return;
        old_measures_have_been_updated = false;

        // Arity //

        conjecture.arity = conjecture.lh_concept.arity;

        // Applicability //

        conjecture.applicability = conjecture.lh_concept.applicability;

        // Comprehensibility //

        conjecture.complexity = conjecture.lh_concept.complexity + conjecture.rh_concept.complexity;
        conjecture.comprehensibility = 1/conjecture.complexity;

        // Surprisingness //

        double surprisingness = 0;
        for (int i=0; i<conjecture.lh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.rh_concept.ancestor_ids.contains(conjecture.lh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }
        for (int i=0; i<conjecture.rh_concept.ancestor_ids.size(); i++)
        {
            if (!conjecture.lh_concept.ancestor_ids.contains(conjecture.rh_concept.ancestor_ids.elementAt(i)))
                surprisingness++;
        }

        conjecture.surprisingness = surprisingness;

        // Plausibility //

        double plausibility = 0;
        Vector entities_conj_discusses = conjecture.lh_concept.getPositives();
        plausibility = ((double)(entities_conj_discusses.size() - conjecture.counterexamples.size()))/((double)entities_conj_discusses.size());
        conjecture.plausibility = plausibility;


        calculateOverallValue(conjecture);

        // If any measures have changed for any old conjecture, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<conjectures.size(); i++)
                calculateOverallValue((Implication)conjectures.elementAt(i));
        }
    }




    /** This calculates the overall worth of an equivalence conjecture.
     */

    public void calculateOverallValue(Equivalence conjecture)
    {
        conjecture.interestingness =
                (conjecture.normalised_applicability * applicability_weight) +
                        (conjecture.normalised_comprehensibility * comprehensibility_weight) +
                        (conjecture.normalised_surprisingness * surprisingness_weight);
    }

    /** This calculates the overall worth of a near-equivalence conjecture.
     */

    public void calculateOverallValue(NearEquivalence conjecture)
    {
        System.out.println("plausibility_weight is " + plausibility_weight);

        conjecture.interestingness =
                (conjecture.normalised_applicability * applicability_weight) +
                        (conjecture.normalised_comprehensibility * comprehensibility_weight) +
                        (conjecture.normalised_surprisingness * surprisingness_weight) +
                        (conjecture.plausibility * plausibility_weight);
    }

    /** This calculates the overall worth of an implication conjecture.
     */

    public void calculateOverallValue(Implication conjecture)
    {
        conjecture.interestingness =
                (conjecture.normalised_applicability * applicability_weight) +
                        (conjecture.normalised_comprehensibility * comprehensibility_weight) +
                        (conjecture.normalised_surprisingness * surprisingness_weight);
    }

    /** This calculates the overall worth of a near-implication conjecture.
     */

    public void calculateOverallValue(NearImplication conjecture)
    {
        conjecture.interestingness =
                (conjecture.normalised_applicability * applicability_weight) +
                        (conjecture.normalised_comprehensibility * comprehensibility_weight) +
                        (conjecture.normalised_surprisingness * surprisingness_weight) +
                        (conjecture.plausibility * plausibility_weight);
    }

    /** This calculates all the measures for a given implicate
     * conjecture. It also updates the measures for all the other
     * conjectures if needed.
     */

    public void measureConjecture(Implicate conjecture, Vector conjectures)
    {
        if (!measure_conjectures)
            return;
        old_measures_have_been_updated = false;

        // Arity //

        conjecture.arity = conjecture.premise_concept.arity;

        // Applicability //

        conjecture.applicability = conjecture.premise_concept.applicability;
        conjecture.normalised_applicability = super.normalisedValue(conjecture.applicability, all_implicate_applicabilities);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_implicate_applicabilities.indexOf(Double.toString(old_conjecture.applicability));
                old_conjecture.normalised_applicability =
                        (new Double(pos)).doubleValue()/((new Double(all_implicate_applicabilities.size())).doubleValue() - 1);
            }
        }

        // Comprehensibility //

        conjecture.complexity = conjecture.premise_concept.complexity;
        if (conjecture.complexity==0)
            conjecture.comprehensibility = 1;
        else
            conjecture.comprehensibility = 1/conjecture.complexity;
        conjecture.normalised_comprehensibility =
                super.normalisedValue(conjecture.comprehensibility, all_implicate_comprehensibilities);

        // Surprisingness //

        if (!conjecture.parent_conjectures.isEmpty())
        {
            Conjecture parent = (Conjecture)conjecture.parent_conjectures.elementAt(0);
            conjecture.surprisingness = parent.surprisingness;
            conjecture.normalised_surprisingness = parent.normalised_surprisingness;
        }

        calculateOverallValue(conjecture);

        // If any measures have changed for any old conjecture, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<conjectures.size(); i++)
                calculateOverallValue((Implicate)conjectures.elementAt(i));
        }
    }

    /** This calculates the overall worth of an implicate conjecture.
     */

    public void calculateOverallValue(Implicate conjecture)
    {
        conjecture.interestingness =
                (conjecture.normalised_applicability * applicability_weight) +
                        (conjecture.normalised_surprisingness * surprisingness_weight) +
                        (conjecture.normalised_comprehensibility * comprehensibility_weight);
    }

    /** This calculates all the measures for a given non existence
     * conjecture. It also updates the measures for all the other
     * conjectures if needed.
     */

    public void measureConjecture(NonExists conjecture, Vector conjectures, Theory theory)
    {
        if (!measure_conjectures)
            return;
        old_measures_have_been_updated = false;

        // Arity //

        conjecture.arity = conjecture.concept.arity;

        // Comprehensibility //

        conjecture.complexity = conjecture.concept.complexity;
        conjecture.comprehensibility = 1/conjecture.complexity;
        conjecture.normalised_comprehensibility =
                super.normalisedValue(conjecture.comprehensibility, all_ne_comprehensibilities);

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_ne_comprehensibilities.indexOf(Double.toString(old_conjecture.comprehensibility));
                old_conjecture.normalised_comprehensibility =
                        (new Double(pos)).doubleValue()/((new Double(all_ne_comprehensibilities.size())).doubleValue() - 1);
            }
        }

        // Surprisingness //

        double surprisingness = 0;
        for (int i=0; i<conjecture.concept.parents.size(); i++)
        {
            Concept parent = (Concept)conjecture.concept.parents.elementAt(i);
            surprisingness = surprisingness + parent.applicability;
        }

        if (conjecture.concept.parents.isEmpty())
            conjecture.surprisingness = 1;
        else
        {
            conjecture.surprisingness = surprisingness/conjecture.concept.parents.size();
            conjecture.normalised_surprisingness =
                    super.normalisedValue(conjecture.surprisingness, all_ne_surprisingnesses);
        }

        if (measures_need_updating)
        {
            old_measures_have_been_updated = true;
            for (int i=0; i<conjectures.size(); i++)
            {
                Conjecture old_conjecture = (Conjecture)conjectures.elementAt(i);
                int pos = all_ne_surprisingnesses.indexOf(Double.toString(old_conjecture.surprisingness));
                old_conjecture.normalised_surprisingness =
                        (new Double(pos)).doubleValue()/((new Double(all_ne_surprisingnesses.size())).doubleValue() - 1);
            }
        }

        //plausibility
        double plausibility = 0;
        plausibility = (theory.entities.size() - conjecture.counterexamples.size())/theory.entities.size();
        conjecture.plausibility = plausibility;



        calculateOverallValue(conjecture);

        // If any measures have changed for any old conjecture, we need to calculate the
        // overall scores for every concept.

        if (old_measures_have_been_updated)
        {
            for (int i=0; i<conjectures.size(); i++)
                calculateOverallValue((NonExists)conjectures.elementAt(i));
        }
    }

    /** This calculates the overall worth of an non-exists conjecture.
     */

    public void calculateOverallValue(NonExists conjecture)
    {
        conjecture.interestingness =
                (conjecture.normalised_comprehensibility * comprehensibility_weight) +
                        (conjecture.normalised_surprisingness * surprisingness_weight);
    }

    /** This calls the right method to calculate all the measures for a
     * given conjecture. It also updates the measures for all the other
     * conjectures if needed.
     */

    public void measureConjecture(Conjecture conjecture, Theory theory)
    {
        if(conjecture instanceof Equivalence)
            measureConjecture((Equivalence)conjecture, theory.equivalences);

        if(conjecture instanceof NearEquivalence)
            measureConjecture((NearEquivalence)conjecture, theory.near_equivalences);

        if(conjecture instanceof Implication)
            measureConjecture((Implication)conjecture, theory.implications);

        if(conjecture instanceof NearImplication)
            measureConjecture((NearImplication)conjecture, theory.near_implications);

        if(conjecture instanceof Implicate)
            measureConjecture((Implicate)conjecture, theory.implicates);

        if(conjecture instanceof NonExists)
            measureConjecture((NonExists)conjecture, theory.non_existences, theory);
    }

    /** Given two vectors of strings, removes the duplicates and
     * returns -- needs testing (alisonp)*/
    public Vector removeDuplicates(Vector vector1, Vector vector2)
    {
        Vector output = new Vector();
        for(int i=0;i<vector1.size();i++)
        {
            String entity1 = (String)vector1.elementAt(i);
            boolean add_entity = true;
            for(int j=0;j<vector2.size();j++)
            {
                String entity2 = (String)vector2.elementAt(j);
                if(entity2.equals(entity1))
                {
                    add_entity = false;
                    break;
                }
            }
            if(add_entity)
                output.addElement(entity1);
        }
        for(int i=0;i<vector2.size();i++)
        {
            String entity = (String)vector2.elementAt(i);
            output.addElement(entity);
        }
        return output;
    }
}
