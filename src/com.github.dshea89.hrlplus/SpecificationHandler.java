package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

public class SpecificationHandler implements Serializable {
    public int max_num_of_rewrites = 1025;
    public Vector specifications = new Vector();
    public Vector concepts = new Vector();
    public Vector non_existences = new Vector();

    public SpecificationHandler() {
    }

    public Vector addSpecifications(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Specification var4 = (Specification)var1.elementAt(var3);
            Specification var5 = this.addSpecification(var4);
            if (!var2.contains(var5)) {
                int var6 = 0;

                boolean var7;
                for(var7 = false; var6 < var2.size() && !var7; ++var6) {
                    Specification var8 = (Specification)var2.elementAt(var6);
                    if (var5.id_number < var8.id_number) {
                        var2.insertElementAt(var5, var6);
                        var7 = true;
                    }
                }

                if (!var7) {
                    var2.addElement(var5);
                }
            }
        }

        var2.trimToSize();
        return var2;
    }

    public Specification addSpecification(Specification var1) {
        Specification var2;
        Specification var3;
        Specification var4;
        Specification var5;
        if (var1.type.equals("split") && var1.previous_specifications.size() == 1) {
            var2 = var1.copy();
            var3 = (Specification)var2.previous_specifications.elementAt(0);
            if (var3.type.equals("negate") && var3.previous_specifications.size() == 1) {
                var4 = (Specification)var3.previous_specifications.elementAt(0);
                var5 = var3.copy();
                var2.previous_specifications.removeAllElements();
                var2.previous_specifications.addElement(var4);
                var5.previous_specifications.removeAllElements();
                var5.previous_specifications.addElement(var2);
                var5.permutation = (Vector)var2.permutation.clone();
                Vector var14 = new Vector();

                for(int var7 = 0; var7 < var2.permutation.size(); ++var7) {
                    var14.addElement(Integer.toString(var5.permutation.indexOf((String)var2.permutation.elementAt(var7))));
                }

                var2.permutation = var14;
                Specification var15 = this.addSpecification(var5);
                return var15;
            }
        }

        if (var1.type.equals("negate") && var1.previous_specifications.size() == 1) {
            var2 = (Specification)var1.previous_specifications.elementAt(0);
            if (var2.type.equals("negate") && var2.previous_specifications.size() == 1 && var1.permutation.toString().equals(var2.permutation.toString())) {
                var3 = ((Specification)var2.previous_specifications.elementAt(0)).copy();
                Vector var12 = new Vector();

                for(int var13 = 0; var13 < var3.permutation.size(); ++var13) {
                    int var6 = new Integer((String)var3.permutation.elementAt(var13));
                    var12.addElement(var1.permutation.elementAt(var6));
                }

                var3.permutation = var12;
                return this.addSpecification(var3);
            }
        }

        if (var1.type.equals("negate")) {
            Vector var8 = new Vector();

            for(int var10 = 0; var10 < var1.previous_specifications.size(); ++var10) {
                var4 = (Specification)var1.previous_specifications.elementAt(var10);
                var8.addElement(this.addSpecification(var4));
            }

            var1.previous_specifications = var8;
        }

        boolean var9 = false;
        var3 = new Specification();
        Enumeration var11 = this.specifications.elements();

        while(!var9 && var11.hasMoreElements()) {
            var5 = (Specification)var11.nextElement();
            if (var5.equals(var1)) {
                var9 = true;
                var3 = var5;
            }
        }

        if (!var9) {
            var1.id_number = this.specifications.size();
            this.specifications.addElement(var1);
            var1.is_entity_instantiations = var1.isEntityInstantiations();
            var3 = var1;
        }

        var3.permutation.trimToSize();
        var3.fixed_values.trimToSize();
        var3.functions.trimToSize();
        var3.multiple_variable_columns.trimToSize();
        var3.multiple_types.trimToSize();
        var3.permutation.trimToSize();
        var3.previous_specifications.trimToSize();
        return var3;
    }

    public Concept checkForMatch(Concept var1, Step var2, String var3) {
        boolean var4 = false;
        Concept var5 = new Concept();

        for(int var6 = 0; var6 < this.concepts.size() && !var4; ++var6) {
            var5 = (Concept)this.concepts.elementAt(var6);
            if (var5.types.toString().equals(var1.types.toString())) {
                Vector var7 = (Vector)var5.conjectured_equivalent_concepts.clone();
                var7.insertElementAt(var5, 0);

                for(int var8 = 0; var8 < var7.size() && !var4; ++var8) {
                    Concept var9 = (Concept)var7.elementAt(var8);
                    int var10 = var9.isGeneralisationOf(var1);
                    if (var10 == 1) {
                        var4 = true;
                        var9.conjectured_equivalent_constructions.addElement(var2);
                        if (!var3.equals("")) {
                            var9.alternative_ids.addElement(var3);
                        }
                    }

                    if (!var4 && var9.skolemised_representation.ground_variables.equals(var1.skolemised_representation.ground_variables)) {
                        boolean var11 = this.leftSkolemisedImpliesRight(var9, var1, false);
                        if (var11) {
                            boolean var12 = this.leftSkolemisedImpliesRight(var1, var9, false);
                            if (var12) {
                                var4 = true;
                            }
                        }
                    }
                }
            }
        }

        return var4 ? var5 : null;
    }

    public boolean leftSkolemisedSubsumesRight(Implicate var1, Implicate var2, boolean var3) {
        Concept var4 = var1.premise_concept;
        Concept var5 = var2.premise_concept;
        Concept var6 = new Concept();
        Concept var7 = new Concept();
        var6.specifications.addElement(var1.goal);
        var6.setSkolemisedRepresentation();
        var7.specifications.addElement(var2.goal);
        var7.setSkolemisedRepresentation();
        Vector var8 = this.implyingRewrites(var5, var4, var3);
        Vector var9 = this.implyingRewrites(var6, var7, var3);
        if (var4.writeDefinition("otter").trim().equals("") && var5.writeDefinition("otter").trim().equals("") && !var9.isEmpty()) {
            return true;
        } else {
            for(int var10 = 0; var10 < var8.size(); ++var10) {
                Vector var11 = (Vector)var8.elementAt(var10);

                for(int var12 = 0; var12 < var9.size(); ++var12) {
                    Vector var13 = (Vector)var9.elementAt(var12);
                    if (this.reWriteImplicate(var11, var13, var4.skolemised_representation, var6.skolemised_representation, var5.skolemised_representation, var7.skolemised_representation)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean reWriteImplicate(Vector var1, Vector var2, SkolemisedRepresentation var3, SkolemisedRepresentation var4, SkolemisedRepresentation var5, SkolemisedRepresentation var6) {
        Vector var7 = new Vector();

        int var10;
        String var11;
        for(int var8 = 0; var8 < var2.size(); ++var8) {
            String var9 = (String)var2.elementAt(var8);
            var10 = var3.variables.indexOf(var9);
            if (var10 > 0) {
                var11 = (String)var1.elementAt(var10);
                var7.addElement(var11);
            } else {
                var7.addElement(var9);
            }
        }

        boolean var14 = true;

        int var12;
        for(int var15 = 0; var15 < var6.variables.size(); ++var15) {
            String var17 = (String)var6.variables.elementAt(var15);
            var11 = (String)var7.elementAt(var15);
            if (var17.indexOf("!") < 0 && !var17.equals(var11)) {
                if (var1.contains(var17)) {
                    var14 = false;
                    break;
                }

                var12 = var6.variables.indexOf(var11);
                if ((var12 < 0 || ((String)var7.elementAt(var12)).equals(var11)) && (var5.variables.contains(var11) || var6.variables.contains(var11))) {
                    var14 = false;
                    break;
                }
            }
        }

        if (var14) {
            return true;
        } else {
            Vector var16 = new Vector();

            for(var10 = 0; var10 < var1.size(); ++var10) {
                var11 = (String)var1.elementAt(var10);
                var12 = var6.variables.indexOf(var11);
                if (var12 > 0) {
                    String var13 = (String)var2.elementAt(var12);
                    var16.addElement(var13);
                } else {
                    var16.addElement(var11);
                }
            }

            var14 = true;

            String var18;
            for(var10 = 0; var10 < var6.variables.size(); ++var10) {
                var11 = (String)var6.variables.elementAt(var10);
                var18 = (String)var2.elementAt(var10);
                if (var11.indexOf("!") < 0 && !var11.equals(var18)) {
                    int var19 = var6.variables.indexOf(var18);
                    if ((var19 < 0 || ((String)var2.elementAt(var19)).equals(var18)) && (var5.variables.contains(var18) || var6.variables.contains(var18))) {
                        var14 = false;
                        break;
                    }
                }
            }

            for(var10 = 0; var10 < var3.variables.size(); ++var10) {
                var11 = (String)var3.variables.elementAt(var10);
                var18 = (String)var16.elementAt(var10);
                if (var11.indexOf("!") < 0 && !var11.equals(var18) && var2.contains(var11)) {
                    var14 = false;
                    break;
                }
            }

            return var14;
        }
    }

    public Vector implyingRewrites(Concept var1, Concept var2, boolean var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var2.skolemised_representation.ground_variables.size(); ++var5) {
            String var6 = (String)var2.skolemised_representation.ground_variables.elementAt(var5);
            if (!var1.skolemised_representation.ground_variables.contains(var6)) {
                return new Vector();
            }
        }

        Vector var12 = this.getReWrites(var2.skolemised_representation, var1.skolemised_representation, var3);
        if (var12 == null) {
            return new Vector();
        } else {
            for(int var13 = 0; var13 < var12.size() && var13 < this.max_num_of_rewrites; ++var13) {
                Vector var7 = (Vector)var12.elementAt(var13);
                Vector var8 = this.rewriteSkolemisation(var2.skolemised_representation, var7);
                boolean var9 = true;

                for(int var10 = 0; var10 < var8.size() && var9; ++var10) {
                    Vector var11 = (Vector)var8.elementAt(var10);
                    if (!var1.skolemised_representation.relation_columns.contains(var11)) {
                        var9 = false;
                    }
                }

                if (var9) {
                    var4.addElement(var7);
                }
            }

            var4.trimToSize();
            return var4;
        }
    }

    public boolean leftSkolemisedImpliesRight(Concept var1, Concept var2, boolean var3) {
        for(int var4 = 0; var4 < var2.skolemised_representation.ground_variables.size(); ++var4) {
            String var5 = (String)var2.skolemised_representation.ground_variables.elementAt(var4);
            if (!var1.skolemised_representation.ground_variables.contains(var5)) {
                return false;
            }
        }

        Vector var12 = this.getReWrites(var2.skolemised_representation, var1.skolemised_representation, var3);
        if (var12 == null) {
            return false;
        } else {
            boolean var13 = false;

            for(int var6 = 0; var6 < var12.size() && var6 < this.max_num_of_rewrites && !var13; ++var6) {
                Vector var7 = (Vector)var12.elementAt(var6);
                Vector var8 = this.rewriteSkolemisation(var2.skolemised_representation, var7);
                boolean var9 = true;

                for(int var10 = 0; var10 < var8.size() && var9; ++var10) {
                    Vector var11 = (Vector)var8.elementAt(var10);
                    if (!var1.skolemised_representation.relation_columns.contains(var11)) {
                        var9 = false;
                    }
                }

                if (var9) {
                    var13 = true;
                }
            }

            return var13;
        }
    }

    public Vector rewriteSkolemisation(SkolemisedRepresentation var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var1.relation_columns.size(); ++var4) {
            Vector var5 = (Vector)var1.relation_columns.elementAt(var4);
            Vector var6 = new Vector();
            var6.addElement(var5.elementAt(0));
            Vector var7 = (Vector)var5.elementAt(1);
            Vector var8 = new Vector();

            for(int var9 = 0; var9 < var7.size(); ++var9) {
                String var10 = (String)var7.elementAt(var9);
                String var11 = var10;
                if (!var10.substring(0, 1).equals(":")) {
                    int var12 = var1.variables.indexOf(var10);
                    var11 = (String)var2.elementAt(var12);
                }

                var8.addElement(var11);
            }

            var6.addElement(var8);
            var3.addElement(var6);
        }

        return var3;
    }

    public Vector getReWrites(SkolemisedRepresentation var1, SkolemisedRepresentation var2, boolean var3) {
        Vector var4 = new Vector();
        var4.addElement(new Vector());

        for(int var5 = 0; var5 < var1.variables.size(); ++var5) {
            String var6 = (String)var1.variables.elementAt(var5);
            if (!this.varType(var6).equals("gr")) {
                Vector var7 = new Vector();
                Vector var8 = (Vector)var1.variable_relation_position_hashtable.get(var6);
                if (var8 == null) {
                    return null;
                }

                int var9;
                for(var9 = 0; var9 < var8.size(); ++var9) {
                    String var10 = (String)var8.elementAt(var9);
                    Vector var11 = (Vector)var2.relation_position_hashtable.get(var10);
                    if (var11 == null) {
                        return null;
                    }

                    int var12;
                    if (var9 == 0) {
                        var7 = (Vector)var11.clone();
                        if (this.varType(var6).equals("var")) {
                            for(var12 = 0; var12 < var7.size(); ++var12) {
                                if (this.varType((String)var7.elementAt(var12)).equals("ex") && !var3) {
                                    var7.removeElementAt(var12);
                                    --var12;
                                }
                            }
                        }
                    } else {
                        for(var12 = 0; var12 < var7.size(); ++var12) {
                            if (!var11.contains((String)var7.elementAt(var12))) {
                                var7.removeElementAt(var12--);
                            }
                        }
                    }
                }

                var9 = var4.size();
                if (var7.isEmpty()) {
                    return null;
                }

                for(int var14 = 0; var14 < var9; ++var14) {
                    for(int var15 = 0; var15 < var7.size() && var15 < this.max_num_of_rewrites; ++var15) {
                        Vector var16 = (Vector)((Vector)var4.elementAt(0)).clone();
                        String var13 = (String)var7.elementAt(var15);
                        var16.addElement(var13);
                        var4.addElement(var16);
                        if (var4.size() > this.max_num_of_rewrites) {
                            return new Vector();
                        }
                    }

                    var4.removeElementAt(0);
                }
            }
        }

        var4.trimToSize();
        return var4;
    }

    private String varType(String var1) {
        if (var1.substring(0, 1).equals("!")) {
            return "ex";
        } else {
            return var1.substring(0, 1).equals(":") ? "gr" : "var";
        }
    }

    public Vector rejectSpecifications(String var1, Step var2, Concept var3, Theory var4) {
        Vector var5 = new Vector();
        var4.addToTimer("3.4.1 Checking for bad negation in specifications");
        boolean var6 = false;

        for(int var7 = 0; var7 < var3.specifications.size() && !var6; ++var7) {
            Specification var8 = (Specification)var3.specifications.elementAt(var7);
            if (var8.type.equals("negate")) {
                for(int var9 = 0; var9 < var3.specifications.size() && !var6; ++var9) {
                    Specification var10 = (Specification)var3.specifications.elementAt(var9);
                    if (!var10.type.equals("negate")) {
                        var6 = var8.isNegationOf(var10);
                    }
                }
            }
        }

        if (var6) {
            var5.addElement("specifications have bad negation");
            return var5;
        } else {
            var4.addToTimer("3.4.1 Checking for function conflicts in specifications");
            boolean var12 = false;

            int var13;
            for(var13 = 0; !var12 && var13 < var3.functions.size(); ++var13) {
                Function var14 = (Function)var3.functions.elementAt(var13);

                for(int var16 = var13 + 1; !var12 && var16 < var3.functions.size(); ++var16) {
                    Function var11 = (Function)var3.functions.elementAt(var16);
                    var12 = var14.hasConflictWith(var11);
                }
            }

            if (var12) {
                var5.addElement("new specifications have a function conflict");
                return var5;
            } else {
                var4.addToTimer("3.4.3 Checking if a previous concept has same specifications");
                Concept var15 = this.checkForMatch(var3, var2, var1);
                if (var15 != null) {
                    var5.addElement("old concept with same specifications");
                    var5.addElement(var15);
                    return var5;
                } else {
                    var4.addToTimer("3.4.4 Checking whether any non-existence conjectures rule the concept out");
                    var13 = 0;

                    boolean var17;
                    for(var17 = false; !var17 && var13 < this.non_existences.size(); ++var13) {
                        NonExists var18 = (NonExists)this.non_existences.elementAt(var13);
                        if (!var18.proof_status.equals("disproved") && !var18.concept.is_object_of_interest_concept && var18.concept.isGeneralisationOf(var3) >= 0) {
                            var17 = true;
                        }
                    }

                    if (var17) {
                        var5.addElement("non-existence conjecture rules the specifications out");
                        return var5;
                    } else {
                        var5.addElement("is good");
                        return var5;
                    }
                }
            }
        }
    }

    public boolean isTautology(Equivalence var1) {
        boolean var2 = false;

        for(int var3 = 0; var3 < 2; ++var3) {
            Step var4 = var1.lh_concept.construction;
            Step var5 = var1.rh_concept.construction;
            if (var3 == 1) {
                var4 = var1.rh_concept.construction;
                var5 = var1.lh_concept.construction;
            }

            if (var4.productionRule().getName().equals("forall") && var4.parameters().toString().equals("[s, 1]") && var5.productionRule().getName().equals("negate")) {
                Vector var6 = var4.conceptList();
                Concept var7 = (Concept)var6.elementAt(0);
                Concept var8 = (Concept)var6.elementAt(1);
                Concept var9 = (Concept)var6.elementAt(2);
                Vector var10 = var5.conceptList();
                Concept var11 = (Concept)var10.elementAt(0);
                Concept var12 = (Concept)var10.elementAt(1);
                if (var12 == var7 && var11.construction.productionRule().getName().equals("exists") && var11.construction.parameters().toString().equals("[1]")) {
                    Concept var13 = (Concept)var11.construction.conceptList().elementAt(0);
                    if (var13.construction.productionRule().getName().equals("negate")) {
                        Concept var14 = (Concept)var13.construction.conceptList().elementAt(0);
                        Concept var15 = (Concept)var13.construction.conceptList().elementAt(1);
                        if (var14 == var8 && var15 == var9) {
                            System.out.println(var1.writeConjecture("ascii"));
                        }
                    }
                }
            }
        }

        return var2;
    }
}
