package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class MeasureConjecture extends Measure implements Serializable {
    public boolean measure_conjectures = true;
    public double applicability_weight = 0.5D;
    public double comprehensibility_weight = 0.5D;
    public double cross_domain_weight = 0.5D;
    public double surprisingness_weight = 0.5D;
    public double plausibility_weight = 0.5D;
    public Vector all_equiv_applicabilities = new Vector();
    public Vector all_implication_applicabilities = new Vector();
    public Vector all_implicate_applicabilities = new Vector();
    public Vector all_implicate_comprehensibilities = new Vector();
    public Vector all_equiv_comprehensibilities = new Vector();
    public Vector all_implication_comprehensibilities = new Vector();
    public Vector all_equiv_surprisingnesses = new Vector();
    public Vector all_implication_surprisingnesses = new Vector();
    public Vector all_ne_comprehensibilities = new Vector();
    public Vector all_ne_surprisingnesses = new Vector();

    public MeasureConjecture() {
    }

    public void measureConjecture(Equivalence var1, Vector var2) {
        if (this.measure_conjectures) {
            this.old_measures_have_been_updated = false;
            var1.arity = (double)var1.lh_concept.arity;
            var1.applicability = var1.lh_concept.applicability;
            var1.normalised_applicability = super.normalisedValue(var1.applicability, this.all_equiv_applicabilities);
            int var3;
            Conjecture var4;
            int var5;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var3 = 0; var3 < var2.size(); ++var3) {
                    var4 = (Conjecture)var2.elementAt(var3);
                    var5 = this.all_equiv_applicabilities.indexOf(Double.toString(var4.applicability));
                    var4.normalised_applicability = new Double((double)var5) / (new Double((double)this.all_equiv_applicabilities.size()) - 1.0D);
                }
            }

            var1.complexity = (double)(var1.lh_concept.complexity + var1.rh_concept.complexity);
            var1.comprehensibility = 1.0D / var1.complexity;
            var1.normalised_comprehensibility = super.normalisedValue(var1.comprehensibility, this.all_equiv_comprehensibilities);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var3 = 0; var3 < var2.size(); ++var3) {
                    var4 = (Conjecture)var2.elementAt(var3);
                    var5 = this.all_equiv_comprehensibilities.indexOf(Double.toString(var4.comprehensibility));
                    var4.normalised_comprehensibility = new Double((double)var5) / (new Double((double)this.all_equiv_comprehensibilities.size()) - 1.0D);
                }
            }

            double var11 = 0.0D;

            for(var5 = 0; var5 < var1.lh_concept.ancestor_ids.size(); ++var5) {
                if (!var1.rh_concept.ancestor_ids.contains(var1.lh_concept.ancestor_ids.elementAt(var5))) {
                    ++var11;
                }
            }

            for(var5 = 0; var5 < var1.rh_concept.ancestor_ids.size(); ++var5) {
                if (!var1.lh_concept.ancestor_ids.contains(var1.rh_concept.ancestor_ids.elementAt(var5))) {
                    ++var11;
                }
            }

            var1.surprisingness = var11;
            var1.normalised_surprisingness = super.normalisedValue(var11, this.all_equiv_surprisingnesses);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var5 = 0; var5 < var2.size(); ++var5) {
                    Conjecture var6 = (Conjecture)var2.elementAt(var5);
                    int var7 = this.all_equiv_surprisingnesses.indexOf(Double.toString(var6.surprisingness));
                    var6.normalised_surprisingness = new Double((double)var7) / (new Double((double)this.all_equiv_surprisingnesses.size()) - 1.0D);
                }
            }

            double var13 = 0.0D;
            Vector var12 = this.removeDuplicates(var1.lh_concept.getPositives(), var1.rh_concept.getPositives());
            var13 = (double)(var12.size() - var1.counterexamples.size()) / (double)var12.size();
            var1.plausibility = var13;
            int var8;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var8 = 0; var8 < var2.size(); ++var8) {
                    Conjecture var9 = (Conjecture)var2.elementAt(var8);
                    int var10 = this.all_equiv_surprisingnesses.indexOf(Double.toString(var9.surprisingness));
                    var9.normalised_surprisingness = new Double((double)var10) / (new Double((double)this.all_equiv_surprisingnesses.size()) - 1.0D);
                }
            }

            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(var8 = 0; var8 < var2.size(); ++var8) {
                    this.calculateOverallValue((Equivalence)var2.elementAt(var8));
                }
            }

        }
    }

    public void measureConjecture(NearEquivalence var1, Vector var2) {
        System.out.println("started measureConjecture on " + var1.writeConjecture());
        if (this.measure_conjectures) {
            this.old_measures_have_been_updated = false;
            var1.arity = (double)var1.lh_concept.arity;
            Vector var3 = this.removeDuplicates(var1.lh_concept.getPositives(), var1.rh_concept.getPositives());
            var1.applicability = (double)var3.size();
            var1.complexity = (double)(var1.lh_concept.complexity + var1.rh_concept.complexity);
            var1.comprehensibility = 1.0D / var1.complexity;
            double var4 = 0.0D;

            int var6;
            for(var6 = 0; var6 < var1.lh_concept.ancestor_ids.size(); ++var6) {
                if (!var1.rh_concept.ancestor_ids.contains(var1.lh_concept.ancestor_ids.elementAt(var6))) {
                    ++var4;
                }
            }

            for(var6 = 0; var6 < var1.rh_concept.ancestor_ids.size(); ++var6) {
                if (!var1.lh_concept.ancestor_ids.contains(var1.rh_concept.ancestor_ids.elementAt(var6))) {
                    ++var4;
                }
            }

            var1.surprisingness = var4;
            double var10 = 0.0D;
            Vector var8 = this.removeDuplicates(var1.lh_concept.getPositives(), var1.rh_concept.getPositives());
            var10 = (double)(var8.size() - var1.counterexamples.size()) / (double)var8.size();
            var1.plausibility = var10;
            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(int var9 = 0; var9 < var2.size(); ++var9) {
                    this.calculateOverallValue((NearEquivalence)var2.elementAt(var9));
                }
            }

        }
    }

    public void measureConjecture(Implication var1, Vector var2) {
        if (this.measure_conjectures) {
            this.old_measures_have_been_updated = false;
            var1.arity = (double)var1.lh_concept.arity;
            var1.applicability = var1.lh_concept.applicability;
            var1.normalised_applicability = super.normalisedValue(var1.applicability, this.all_implication_applicabilities);
            int var3;
            Conjecture var4;
            int var5;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var3 = 0; var3 < var2.size(); ++var3) {
                    var4 = (Conjecture)var2.elementAt(var3);
                    var5 = this.all_implication_applicabilities.indexOf(Double.toString(var4.applicability));
                    var4.normalised_applicability = new Double((double)var5) / (new Double((double)this.all_implication_applicabilities.size()) - 1.0D);
                }
            }

            var1.complexity = (double)(var1.lh_concept.complexity + var1.rh_concept.complexity);
            var1.comprehensibility = 1.0D / var1.complexity;
            var1.normalised_comprehensibility = super.normalisedValue(var1.comprehensibility, this.all_implication_comprehensibilities);
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var3 = 0; var3 < var2.size(); ++var3) {
                    var4 = (Conjecture)var2.elementAt(var3);
                    var5 = this.all_implication_comprehensibilities.indexOf(Double.toString(var4.comprehensibility));
                    var4.normalised_comprehensibility = new Double((double)var5) / (new Double((double)this.all_implication_comprehensibilities.size()) - 1.0D);
                }
            }

            double var11 = 0.0D;

            for(var5 = 0; var5 < var1.lh_concept.ancestor_ids.size(); ++var5) {
                if (!var1.rh_concept.ancestor_ids.contains(var1.lh_concept.ancestor_ids.elementAt(var5))) {
                    ++var11;
                }
            }

            for(var5 = 0; var5 < var1.rh_concept.ancestor_ids.size(); ++var5) {
                if (!var1.lh_concept.ancestor_ids.contains(var1.rh_concept.ancestor_ids.elementAt(var5))) {
                    ++var11;
                }
            }

            var1.surprisingness = var11;
            var1.normalised_surprisingness = super.normalisedValue(var11, this.all_implication_surprisingnesses);
            double var12 = 0.0D;
            Vector var7 = var1.lh_concept.getPositives();
            var12 = (double)(var7.size() - var1.counterexamples.size()) / (double)var7.size();
            var1.plausibility = var12;
            int var8;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var8 = 0; var8 < var2.size(); ++var8) {
                    Conjecture var9 = (Conjecture)var2.elementAt(var8);
                    int var10 = this.all_implication_surprisingnesses.indexOf(Double.toString(var9.surprisingness));
                    var9.normalised_surprisingness = new Double((double)var10) / (new Double((double)this.all_implication_surprisingnesses.size()) - 1.0D);
                }
            }

            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(var8 = 0; var8 < var2.size(); ++var8) {
                    this.calculateOverallValue((Implication)var2.elementAt(var8));
                }
            }

        }
    }

    public void measureConjecture(NearImplication var1, Vector var2) {
        if (this.measure_conjectures) {
            this.old_measures_have_been_updated = false;
            var1.arity = (double)var1.lh_concept.arity;
            var1.applicability = var1.lh_concept.applicability;
            var1.complexity = (double)(var1.lh_concept.complexity + var1.rh_concept.complexity);
            var1.comprehensibility = 1.0D / var1.complexity;
            double var3 = 0.0D;

            int var5;
            for(var5 = 0; var5 < var1.lh_concept.ancestor_ids.size(); ++var5) {
                if (!var1.rh_concept.ancestor_ids.contains(var1.lh_concept.ancestor_ids.elementAt(var5))) {
                    ++var3;
                }
            }

            for(var5 = 0; var5 < var1.rh_concept.ancestor_ids.size(); ++var5) {
                if (!var1.lh_concept.ancestor_ids.contains(var1.rh_concept.ancestor_ids.elementAt(var5))) {
                    ++var3;
                }
            }

            var1.surprisingness = var3;
            double var9 = 0.0D;
            Vector var7 = var1.lh_concept.getPositives();
            var9 = (double)(var7.size() - var1.counterexamples.size()) / (double)var7.size();
            var1.plausibility = var9;
            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(int var8 = 0; var8 < var2.size(); ++var8) {
                    this.calculateOverallValue((Implication)var2.elementAt(var8));
                }
            }

        }
    }

    public void calculateOverallValue(Equivalence var1) {
        var1.interestingness = var1.normalised_applicability * this.applicability_weight + var1.normalised_comprehensibility * this.comprehensibility_weight + var1.normalised_surprisingness * this.surprisingness_weight;
    }

    public void calculateOverallValue(NearEquivalence var1) {
        System.out.println("plausibility_weight is " + this.plausibility_weight);
        var1.interestingness = var1.normalised_applicability * this.applicability_weight + var1.normalised_comprehensibility * this.comprehensibility_weight + var1.normalised_surprisingness * this.surprisingness_weight + var1.plausibility * this.plausibility_weight;
    }

    public void calculateOverallValue(Implication var1) {
        var1.interestingness = var1.normalised_applicability * this.applicability_weight + var1.normalised_comprehensibility * this.comprehensibility_weight + var1.normalised_surprisingness * this.surprisingness_weight;
    }

    public void calculateOverallValue(NearImplication var1) {
        var1.interestingness = var1.normalised_applicability * this.applicability_weight + var1.normalised_comprehensibility * this.comprehensibility_weight + var1.normalised_surprisingness * this.surprisingness_weight + var1.plausibility * this.plausibility_weight;
    }

    public void measureConjecture(Implicate var1, Vector var2) {
        if (this.measure_conjectures) {
            this.old_measures_have_been_updated = false;
            var1.arity = (double)var1.premise_concept.arity;
            var1.applicability = var1.premise_concept.applicability;
            var1.normalised_applicability = super.normalisedValue(var1.applicability, this.all_implicate_applicabilities);
            int var3;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var3 = 0; var3 < var2.size(); ++var3) {
                    Conjecture var4 = (Conjecture)var2.elementAt(var3);
                    int var5 = this.all_implicate_applicabilities.indexOf(Double.toString(var4.applicability));
                    var4.normalised_applicability = new Double((double)var5) / (new Double((double)this.all_implicate_applicabilities.size()) - 1.0D);
                }
            }

            var1.complexity = (double)var1.premise_concept.complexity;
            if (var1.complexity == 0.0D) {
                var1.comprehensibility = 1.0D;
            } else {
                var1.comprehensibility = 1.0D / var1.complexity;
            }

            var1.normalised_comprehensibility = super.normalisedValue(var1.comprehensibility, this.all_implicate_comprehensibilities);
            if (!var1.parent_conjectures.isEmpty()) {
                Conjecture var6 = (Conjecture)var1.parent_conjectures.elementAt(0);
                var1.surprisingness = var6.surprisingness;
                var1.normalised_surprisingness = var6.normalised_surprisingness;
            }

            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(var3 = 0; var3 < var2.size(); ++var3) {
                    this.calculateOverallValue((Implicate)var2.elementAt(var3));
                }
            }

        }
    }

    public void calculateOverallValue(Implicate var1) {
        var1.interestingness = var1.normalised_applicability * this.applicability_weight + var1.normalised_surprisingness * this.surprisingness_weight + var1.normalised_comprehensibility * this.comprehensibility_weight;
    }

    public void measureConjecture(NonExists var1, Vector var2, Theory var3) {
        if (this.measure_conjectures) {
            this.old_measures_have_been_updated = false;
            var1.arity = (double)var1.concept.arity;
            var1.complexity = (double)var1.concept.complexity;
            var1.comprehensibility = 1.0D / var1.complexity;
            var1.normalised_comprehensibility = super.normalisedValue(var1.comprehensibility, this.all_ne_comprehensibilities);
            int var6;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(int var4 = 0; var4 < var2.size(); ++var4) {
                    Conjecture var5 = (Conjecture)var2.elementAt(var4);
                    var6 = this.all_ne_comprehensibilities.indexOf(Double.toString(var5.comprehensibility));
                    var5.normalised_comprehensibility = new Double((double)var6) / (new Double((double)this.all_ne_comprehensibilities.size()) - 1.0D);
                }
            }

            double var9 = 0.0D;

            for(var6 = 0; var6 < var1.concept.parents.size(); ++var6) {
                Concept var7 = (Concept)var1.concept.parents.elementAt(var6);
                var9 += var7.applicability;
            }

            if (var1.concept.parents.isEmpty()) {
                var1.surprisingness = 1.0D;
            } else {
                var1.surprisingness = var9 / (double)var1.concept.parents.size();
                var1.normalised_surprisingness = super.normalisedValue(var1.surprisingness, this.all_ne_surprisingnesses);
            }

            int var8;
            if (this.measures_need_updating) {
                this.old_measures_have_been_updated = true;

                for(var6 = 0; var6 < var2.size(); ++var6) {
                    Conjecture var10 = (Conjecture)var2.elementAt(var6);
                    var8 = this.all_ne_surprisingnesses.indexOf(Double.toString(var10.surprisingness));
                    var10.normalised_surprisingness = new Double((double)var8) / (new Double((double)this.all_ne_surprisingnesses.size()) - 1.0D);
                }
            }

            double var11 = 0.0D;
            var11 = (double)((var3.entities.size() - var1.counterexamples.size()) / var3.entities.size());
            var1.plausibility = var11;
            this.calculateOverallValue(var1);
            if (this.old_measures_have_been_updated) {
                for(var8 = 0; var8 < var2.size(); ++var8) {
                    this.calculateOverallValue((NonExists)var2.elementAt(var8));
                }
            }

        }
    }

    public void calculateOverallValue(NonExists var1) {
        var1.interestingness = var1.normalised_comprehensibility * this.comprehensibility_weight + var1.normalised_surprisingness * this.surprisingness_weight;
    }

    public void measureConjecture(Conjecture var1, Theory var2) {
        if (var1 instanceof Equivalence) {
            this.measureConjecture((Equivalence)var1, var2.equivalences);
        }

        if (var1 instanceof NearEquivalence) {
            this.measureConjecture((NearEquivalence)var1, var2.near_equivalences);
        }

        if (var1 instanceof Implication) {
            this.measureConjecture((Implication)var1, var2.implications);
        }

        if (var1 instanceof NearImplication) {
            this.measureConjecture((NearImplication)var1, var2.near_implications);
        }

        if (var1 instanceof Implicate) {
            this.measureConjecture((Implicate)var1, var2.implicates);
        }

        if (var1 instanceof NonExists) {
            this.measureConjecture((NonExists)var1, var2.non_existences, var2);
        }

    }

    public Vector removeDuplicates(Vector var1, Vector var2) {
        Vector var3 = new Vector();

        int var4;
        String var5;
        for(var4 = 0; var4 < var1.size(); ++var4) {
            var5 = (String)var1.elementAt(var4);
            boolean var6 = true;

            for(int var7 = 0; var7 < var2.size(); ++var7) {
                String var8 = (String)var2.elementAt(var7);
                if (var8.equals(var5)) {
                    var6 = false;
                    break;
                }
            }

            if (var6) {
                var3.addElement(var5);
            }
        }

        for(var4 = 0; var4 < var2.size(); ++var4) {
            var5 = (String)var2.elementAt(var4);
            var3.addElement(var5);
        }

        return var3;
    }
}
