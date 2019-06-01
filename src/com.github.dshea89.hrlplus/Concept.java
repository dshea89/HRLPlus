package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

public class Concept extends TheoryConstituent implements Serializable {
    public SkolemisedRepresentation skolemised_representation = new SkolemisedRepresentation();
    public Vector specification_strings_from_knuth_bendix = new Vector();
    public Vector markers = new Vector();
    public Vector analogy_puzzles = new Vector();
    public Vector nis_puzzles = new Vector();
    public Vector ooo_puzzles = new Vector();
    public Vector implications = new Vector();
    public Vector implicates = new Vector();
    public boolean use_top_substitutable = true;
    public Vector substitutable_concepts = new Vector();
    public Conjecture equivalence_conjecture = new Conjecture();
    public boolean is_entity_instantiations = false;
    public boolean is_single_entity = false;
    public DefinitionWriter definition_writer = new DefinitionWriter();
    public int step_number = 0;
    public SortableVector near_equivalences = new SortableVector();
    public SortableVector near_implications = new SortableVector();
    public SortableVector relevant_implicates = new SortableVector();
    public SortableVector relevant_equivalences = new SortableVector();
    public Concept entity_concept = null;
    public boolean is_java_enabled = false;
    public String object_type;
    public boolean is_object_of_interest_concept = false;
    public UserFunctions user_functions = new UserFunctions();
    public long datatable_construction_time = 0L;
    public boolean has_required_categorisation = false;
    public Vector functions = new Vector();
    public boolean dont_develop = false;
    public Vector conjectured_equivalent_concepts = new Vector();
    public Vector conjectured_equivalent_constructions = new Vector();
    public Vector ancestors = new Vector();
    public Vector ancestor_ids = new Vector();
    public Vector parents = new Vector();
    public Vector children = new Vector();
    public int arity = 0;
    public String domain = "";
    public boolean is_cross_domain = false;
    public double cross_domain_score = 0.0D;
    public boolean is_user_given;
    public Relation associated_relation;
    public Datatable additional_datatable = new Datatable();
    public int complexity = 0;
    public double applicability = 0.0D;
    public double positive_applicability = 0.0D;
    public double negative_applicability = 0.0D;
    public double normalised_positive_applicability = 0.0D;
    public double normalised_negative_applicability = 0.0D;
    public double predictive_power = 0.0D;
    public double normalised_predictive_power = 0.0D;
    public double normalised_applicability = 0.0D;
    public double coverage = 0.0D;
    public double normalised_coverage = 0.0D;
    public double equiv_conj_sum = 0.0D;
    public double ne_conj_sum = 0.0D;
    public double imp_conj_sum = 0.0D;
    public double pi_conj_sum = 0.0D;
    public Vector equiv_conjectures = new Vector();
    public Vector ne_conjectures = new Vector();
    public Vector imp_conjectures = new Vector();
    public Vector pi_conjectures = new Vector();
    public double equiv_conj_score = 0.0D;
    public double normalised_equiv_conj_score = 0.0D;
    public double pi_conj_score = 0.0D;
    public double normalised_pi_conj_score = 0.0D;
    public double ne_conj_score = 0.0D;
    public double normalised_ne_conj_score = 0.0D;
    public double imp_conj_score = 0.0D;
    public double normalised_imp_conj_score = 0.0D;
    public double normalised_equiv_conj_num = 0.0D;
    public double normalised_pi_conj_num = 0.0D;
    public double normalised_ne_conj_num = 0.0D;
    public double normalised_imp_conj_num = 0.0D;
    public double comprehensibility = 0.0D;
    public double normalised_comprehensibility = 0.0D;
    public double invariance = 0.0D;
    public double normalised_invariance_score = 0.0D;
    public double discrimination = 0.0D;
    public double normalised_discrimination_score = 0.0D;
    public double novelty = 0.0D;
    public double normalised_novelty = 0.0D;
    public double parsimony = 0.0D;
    public double normalised_parsimony = 0.0D;
    public double parent_score = 0.0D;
    public double children_score = 0.0D;
    public double productivity = 0.0D;
    public double normalised_productivity = 0.0D;
    public double variety = 0.0D;
    public double normalised_variety = 0.0D;
    public Step construction = new Step();
    public Categorisation categorisation = new Categorisation();
    public Datatable datatable = new Datatable();
    public boolean is_cumulative = false;
    public String name = "";
    public int position_in_theory = 0;
    public Vector specifications = new Vector();
    public Vector types = new Vector();
    public double highlight_score = 0.0D;
    public Vector generalisations = new Vector();
    public double interestingness = 0.0D;
    public double development_steps_num = 0.0D;
    public double normalised_development_steps_num = 0.0D;
    public double number_of_children = 0.0D;
    public Vector construction_history = new Vector();
    public Vector alternative_ids = new Vector();
    public boolean concept_to_cover_entities = false;
    public Vector entity_strings = new Vector();
    public String lakatos_method = "no";

    public Concept() {
    }

    public String writeDefinition(String var1, Vector var2) {
        return this.definition_writer.writeDefinition(this, var1, var2);
    }

    public String writeDefinitionWithAtSigns(String var1) {
        boolean var2 = this.definition_writer.surround_by_at_sign;
        this.definition_writer.surround_by_at_sign = true;
        String var3 = this.definition_writer.writeDefinition(this, var1);
        this.definition_writer.surround_by_at_sign = var2;
        return var3;
    }

    public String writeDefinition(String var1) {
        return !this.concept_to_cover_entities ? this.definition_writer.writeDefinition(this, var1) : this.definition_writer.writeDefinitionForGivenEntities(this, var1, this.entity_strings);
    }

    public String writeDefinition() {
        return this.writeDefinition("ascii");
    }

    public String toString() {
        return this.writeDefinition();
    }

    public String writeDefinitionWithStartLetters(String var1) {
        return this.definition_writer.writeDefinitionWithStartLetters(this, var1);
    }

    public Row calculateRow(Vector var1, String var2) {
        Row var3 = new Row();
        var3.entity = var2;
        int var4 = 0;

        Row var5;
        for(var5 = new Row(); var4 < this.datatable.size(); ++var4) {
            var5 = (Row)this.datatable.elementAt(var4);
            if (var5.entity.equals(var2)) {
                break;
            }
        }

        if (var4 < this.datatable.size()) {
            return var5;
        } else {
            for(var4 = 0; var4 < this.additional_datatable.size(); ++var4) {
                var5 = (Row)this.additional_datatable.elementAt(var4);
                if (var5.entity.equals(var2)) {
                    break;
                }
            }

            if (var4 < this.additional_datatable.size()) {
                return var5;
            } else {
                Tuples var17;
                Row var18;
                if (this.is_object_of_interest_concept) {
                    var17 = new Tuples();
                    var17.addElement(new Vector());
                    var18 = new Row();
                    var18.entity = var2;
                    var18.tuples = var17;
                    this.additional_datatable.addElement(var18);
                    return var18;
                } else if (this.is_user_given) {
                    var17 = this.user_functions.calculateTuples(this.id, var2);
                    var18 = new Row();
                    var18.entity = var2;
                    var18.tuples = var17;
                    this.additional_datatable.addElement(var18);
                    return var18;
                } else {
                    boolean var6 = false;
                    Vector var7 = (Vector)this.construction.elementAt(0);
                    ProductionRule var8 = (ProductionRule)this.construction.elementAt(1);
                    Vector var9 = (Vector)this.construction.elementAt(2);
                    Vector var10 = new Vector();

                    for(var4 = 0; var4 < var7.size(); ++var4) {
                        if (!var6) {
                            Concept var11 = (Concept)var7.elementAt(var4);
                            if (!var11.domain.equals(this.domain)) {
                                var10.addElement(var11.datatable);
                            }

                            if (var11.domain.equals(this.domain)) {
                                Datatable var12 = new Datatable();
                                if (this.is_cumulative) {
                                    int var13 = 1;

                                    for(int var14 = new Integer(var2); var13 < var14 && !var6; ++var13) {
                                        String var15 = Integer.toString(var13);
                                        Row var16 = var11.calculateRow(var1, var15);
                                        if (var16.is_void) {
                                            var6 = true;
                                        }

                                        var12.addElement(var16);
                                    }
                                }

                                Row var20 = var11.calculateRow(var1, var2);
                                if (var20.is_void) {
                                    var6 = true;
                                }

                                var12.addElement(var20);
                                var10.addElement(var12);
                            }
                        }
                    }

                    if (!var6) {
                        Datatable var19 = var8.transformTable(var10, var7, var9, var1);
                        var3 = var19.rowWithEntity(var2);
                    }

                    if (var6) {
                        return new Row(var2, "void");
                    } else {
                        this.additional_datatable.addElement(var3);
                        return var3;
                    }
                }
            }
        }
    }

    public int complexityWith(Concept var1) {
        int var2 = 0;

        int var3;
        for(var3 = this.ancestor_ids.size(); var2 < var1.ancestor_ids.size(); ++var2) {
            if (!this.ancestor_ids.contains((String)var1.ancestor_ids.elementAt(var2))) {
                ++var3;
            }
        }

        return var3;
    }

    public String fullDetails(String[] var1, int var2) {
        return this.fullDetails("ascii", var1, var2);
    }

    public String fullDetails(String var1, String[] var2, int var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var2.length; ++var5) {
            var4.addElement(var2[var5]);
        }

        if (var1.equals("html")) {
            return this.fullHTMLDetails(var4, var3);
        } else {
            return var1.equals("ascii") ? this.fullASCIIDetails(var4, var3) : "";
        }
    }

    public String fullHTMLDetails(Vector var1, int var2) {
        String var3 = "";
        if (var1.contains("id")) {
            var3 = var3 + "<b>" + this.id + "</b>";
        }

        if (var1.contains("name") && !this.name.equals("")) {
            var3 = var3 + " &nbsp;&nbsp;<b>\"" + this.name + "\"</b>";
        }

        if (var1.contains("id") || var1.contains("name")) {
            var3 = var3 + "\n";
        }

        int var4 = 0;
        String var5 = "";
        if (var1.contains("simplified def")) {
            ++var4;
        }

        if (var1.contains("ascii def")) {
            ++var4;
            var5 = "ascii";
        }

        if (var1.contains("otter def")) {
            ++var4;
            var5 = "otter";
        }

        if (var1.contains("prolog def")) {
            ++var4;
            var5 = "prolog";
        }

        if (var1.contains("tptp def")) {
            ++var4;
            var5 = "tptp";
        }

        if (var1.contains("skolemised def")) {
            ++var4;
        }

        String var6 = "<li>";
        if (var4 <= 1) {
            var6 = "";
        } else {
            var5 = "ascii";
        }

        String var7 = "";
        if (var1.contains("simplified def")) {
            boolean var8 = this.definition_writer.remove_existence_variables;
            this.definition_writer.remove_existence_variables = true;
            var7 = var7 + var6 + "<b>" + this.replaceLTForHTML(this.writeDefinitionWithStartLetters("otter")) + "</b>\n";
            this.definition_writer.remove_existence_variables = var8;
        }

        if (var1.contains("ascii def")) {
            var7 = var7 + var6 + "<b>" + this.replaceLTForHTML(this.writeDefinitionWithStartLetters("ascii")) + "</b>\n";
        }

        if (var1.contains("otter def")) {
            var7 = var7 + var6 + "<b>" + this.replaceLTForHTML(this.writeDefinitionWithStartLetters("otter")) + "</b>\n";
        }

        if (var1.contains("prolog def")) {
            var7 = var7 + var6 + "<b>" + this.replaceLTForHTML(this.writeDefinitionWithStartLetters("prolog")) + "</b>\n";
        }

        if (var1.contains("tptp def")) {
            var7 = var7 + var6 + "<b>" + this.replaceLTForHTML(this.writeDefinitionWithStartLetters("tptp")) + "</b>\n";
        }

        if (var1.contains("skolemised def")) {
            var7 = var7 + var6 + "<b>" + this.replaceLTForHTML(this.writeSkolemisedRepresentation()) + "</b>\n";
        }

        if (var4 > 1) {
            var3 = var3 + "<ul>" + var7 + "</ul>\n";
        } else if (var4 == 1) {
            var3 = var3 + "<br><table border=1 cellpadding=3 fgcolor=white bgcolor=\"#ffff99\"><tr><td><font color=blue>" + var7 + "</font></td></tr></table><br>\n";
        }

        if (!var3.equals("")) {
            var3 = var3 + "<hr>\n";
        }

        String var16 = var3;
        var3 = var3 + "\n<table border=0><tr><td align=center><font size=4 color=red>Measures</font></td>";
        var3 = var3 + "<td align=center><font size=4 color=green>Examples</font></td></tr>\n";
        var3 = var3 + "<tr valign=top><td><table border=1><tr><td>Measure</td><td>Value</td><td>Normalised</td></tr>\n";
        if (var1.contains("total score")) {
            var3 = var3 + "<tr><td>Total score</td><td>" + this.decimalPlaces(this.interestingness, var2) + "</td><td></td></tr>\n";
        }

        if (var1.contains("arity")) {
            var3 = var3 + "<tr><td>Arity</td><td>" + this.decimalPlaces(this.arity, var2) + "</td><td></td></tr>\n";
        }

        if (var1.contains("applicability")) {
            var3 = var3 + "<tr><td>Applicability</td><td>" + this.decimalPlaces(this.applicability, var2) + "</td><td>" + this.decimalPlaces(this.normalised_applicability, var2) + "</td></tr>\n";
        }

        if (var1.contains("child num")) {
            var3 = var3 + "<tr><td>Children number</td><td>" + this.decimalPlaces(this.number_of_children, var2) + "</td><td></td></tr>\n";
        }

        if (var1.contains("child score")) {
            var3 = var3 + "<tr><td>Children score</td><td>" + this.decimalPlaces(this.children_score, var2) + "</td><td>" + this.decimalPlaces(this.children_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("comprehens")) {
            var3 = var3 + "<tr><td>Comprehensibility</td><td>" + this.decimalPlaces(this.comprehensibility, var2) + "</td><td>" + this.decimalPlaces(this.normalised_comprehensibility, var2) + "</td></tr>\n";
        }

        if (var1.contains("complexity")) {
            var3 = var3 + "<tr><td>Complexity</td><td>" + this.decimalPlaces(this.complexity, var2) + "</td><td></td></tr>\n";
        }

        if (var1.contains("coverage")) {
            var3 = var3 + "<tr><td>Coverage</td><td>" + this.decimalPlaces(this.coverage, var2) + "</td><td>" + this.decimalPlaces(this.normalised_coverage, var2) + "</td></tr>\n";
        }

        if (var1.contains("devel steps")) {
            var3 = var3 + "<tr><td>Development steps</td><td>" + this.decimalPlaces(this.development_steps_num, var2) + "</td><td>" + this.decimalPlaces(this.normalised_development_steps_num, var2) + "</td></tr>\n";
        }

        if (var1.contains("discrimination")) {
            var3 = var3 + "<tr><td>Discrimination</td><td>" + this.decimalPlaces(this.discrimination, var2) + "</td><td>" + this.decimalPlaces(this.normalised_discrimination_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("equiv conj score")) {
            var3 = var3 + "<tr><td>Equiv Conjecture score</td><td>" + this.decimalPlaces(this.equiv_conj_score, var2) + "</td><td>" + this.decimalPlaces(this.normalised_equiv_conj_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("equiv conj num")) {
            var3 = var3 + "<tr><td>Equiv Conjecture num</td><td>" + this.decimalPlaces(this.equiv_conjectures.size(), var2) + "</td><td>" + this.decimalPlaces(this.normalised_equiv_conj_num, var2) + "</td></tr>\n";
        }

        if (var1.contains("highlight")) {
            var3 = var3 + "<tr><td>Highlight</td><td>" + this.decimalPlaces(this.highlight_score, var2) + "</td><td></td></tr>\n";
        }

        if (var1.contains("ne conj score")) {
            var3 = var3 + "<tr><td>Non-exists Conjecture score</td><td>" + this.decimalPlaces(this.ne_conj_score, var2) + "</td><td>" + this.decimalPlaces(this.normalised_ne_conj_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("ne conj num")) {
            var3 = var3 + "<tr><td>Non-exists Conjecture num</td><td>" + this.decimalPlaces(this.ne_conjectures.size(), var2) + "</td><td>" + this.decimalPlaces(this.normalised_ne_conj_num, var2) + "</td></tr>\n";
        }

        if (var1.contains("neg applic")) {
            var3 = var3 + "<tr><td>Negative Applicability</td><td>" + this.decimalPlaces(this.negative_applicability, var2) + "</td><td>" + this.decimalPlaces(this.normalised_negative_applicability, var2) + "</td></tr>\n";
        }

        if (var1.contains("imp conj score")) {
            var3 = var3 + "<tr><td>Implicate Conjecture score</td><td>" + this.decimalPlaces(this.imp_conj_score, var2) + "</td><td>" + this.decimalPlaces(this.normalised_imp_conj_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("imp conj num")) {
            var3 = var3 + "<tr><td>Implicate Conjecture num</td><td>" + this.decimalPlaces(this.imp_conjectures.size(), var2) + "</td><td>" + this.decimalPlaces(this.normalised_imp_conj_num, var2) + "</td></tr>\n";
        }

        if (var1.contains("invariance")) {
            var3 = var3 + "<tr><td>Invariance</td><td>" + this.decimalPlaces(this.invariance, var2) + "</td><td>" + this.decimalPlaces(this.normalised_invariance_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("novelty")) {
            var3 = var3 + "<tr><td>Novelty</td><td>" + this.decimalPlaces(this.novelty, var2) + "</td><td>" + this.decimalPlaces(this.normalised_novelty, var2) + "</td></tr>\n";
        }

        if (var1.contains("parent score")) {
            var3 = var3 + "<tr><td>Parent score</td><td>" + this.decimalPlaces(this.parent_score, var2) + "<td></td></td></tr>\n";
        }

        if (var1.contains("parsimony")) {
            var3 = var3 + "<tr><td>Parsimony</td><td>" + this.decimalPlaces(this.parsimony, var2) + "</td><td>" + this.decimalPlaces(this.normalised_parsimony, var2) + "</td></tr>\n";
        }

        if (var1.contains("pi conj score")) {
            var3 = var3 + "<tr><td>PI Conjecture score</td><td>" + this.decimalPlaces(this.pi_conj_score, var2) + "</td><td>" + this.decimalPlaces(this.normalised_pi_conj_score, var2) + "</td></tr>\n";
        }

        if (var1.contains("pi conj num")) {
            var3 = var3 + "<tr><td>PI Conjecture num</td><td>" + this.decimalPlaces(this.pi_conjectures.size(), var2) + "</td><td>" + this.decimalPlaces(this.normalised_pi_conj_num, var2) + "</td></tr>\n";
        }

        if (var1.contains("pos applic")) {
            var3 = var3 + "<tr><td>Positive Applicability</td><td>" + this.decimalPlaces(this.positive_applicability, var2) + "</td><td>" + this.decimalPlaces(this.normalised_positive_applicability, var2) + "</td></tr>\n";
        }

        if (var1.contains("pred power")) {
            var3 = var3 + "<tr><td>Predictive Power</td><td>" + this.decimalPlaces(this.predictive_power, var2) + "</td><td></td></tr>\n";
        }

        if (var1.contains("productivity")) {
            var3 = var3 + "<tr><td>Productivity</td><td>" + this.decimalPlaces(this.productivity, var2) + "</td><td>" + this.decimalPlaces(this.normalised_productivity, var2) + "</td></tr>\n";
        }

        if (var1.contains("variety")) {
            var3 = var3 + "<tr><td>Variety</td><td>" + this.decimalPlaces(this.variety, var2) + "</td><td>" + this.decimalPlaces(this.normalised_variety, var2) + "</td></tr>\n";
        }

        var3 = var3 + "</table></td><td>";
        if (var1.contains("examples")) {
            var3 = var3 + this.prettyPrintExamplesHTML(this.datatable) + "\n";
        }

        if (var1.contains("datatable") && var1.contains("examples")) {
            var3 = var3 + "\n<hr>\n";
        }

        if (var1.contains("datatable")) {
            var3 = var3 + "The datatable is:<pre>\n" + this.datatable.toTable() + "</pre>\n";
        }

        if (var1.contains("add examples")) {
            if (this.additional_datatable.isEmpty()) {
                var3 = var3 + "No additional entities for this concept.<br>\n";
            } else {
                var3 = var3 + "The additional table is:\n";
                var3 = var3 + "<pre>" + this.additional_datatable.toTable() + "</pre>\n";
            }
        }

        var3 = var3 + "</td></tr></table><p>";
        if (!var16.equals(var3)) {
            var3 = var3 + "<hr>\n";
        }

        var16 = var3;
        var3 = var3 + "<b><u>Construction details</b></u><br>\n";
        if (var1.contains("const step")) {
            if (this.is_user_given) {
                var3 = var3 + "This concept was supplied by the user.<br>\n";
            } else {
                var3 = var3 + "Constructed with this step: " + this.construction.asString() + ".<br>\n";
            }
        }

        if (var1.contains("ancestors")) {
            var3 = var3 + "The ancestors of this concept are:" + this.ancestor_ids.toString() + "<br>\n";
        }

        String var9;
        if (var1.contains("const time")) {
            var9 = "s";
            String var10 = Long.toString(this.when_constructed).trim();
            if (var10.equals("1")) {
                var9 = "";
            }

            var3 = var3 + "Constructed after " + Long.toString(this.when_constructed) + " second" + var9 + ", " + Integer.toString(this.step_number) + " steps.<br>\n";
        }

        if (var1.contains("cross domain")) {
            var9 = "";
            if (!this.is_cross_domain) {
                var9 = "not ";
            }

            var3 = var3 + "This is " + var9 + "a cross domain concept.<br>\n";
        }

        if (var1.contains("entity instant")) {
            var9 = "";
            if (!this.is_entity_instantiations) {
                var9 = "not ";
            }

            var3 = var3 + "This is " + var9 + "an instantiation of entities.<br>\n";
        }

        if (var1.contains("types")) {
            var3 = var3 + "The object types in the datatable are: " + this.types.toString() + "<br>\n";
        }

        if (var1.contains("categorisation")) {
            var3 = var3 + "The categorisation this concept achieves is:<br>\n";
            var3 = var3 + this.categorisation.toString() + "<br>\n";
        }

        if (!var16.equals(var3)) {
            var3 = var3 + "<hr>\n";
        }

        if (!var3.equals(var3)) {
            var3 = var3 + "<hr>\n";
        }

        var16 = var3;
        var3 = var3 + "<b><u>Conjectures about this concept:</u></b><br>\n";
        int var21;
        if (var1.contains("implications")) {
            if (this.implications.size() > 0) {
                var3 = var3 + "Concepts implied by this concept:<br>\n";

                Implication var17;
                for(var21 = 0; var21 < this.implications.size(); ++var21) {
                    var17 = (Implication)this.implications.elementAt(var21);
                    if (var17.lh_concept == this) {
                        var3 = var3 + var17.rh_concept.id + " " + var17.rh_concept.writeDefinitionWithStartLetters(var5) + " [" + var17.id + "]<br>\n";
                    }
                }

                var3 = var3 + "\nConcepts which imply this concept:<br>\n";

                for(var21 = 0; var21 < this.implications.size(); ++var21) {
                    var17 = (Implication)this.implications.elementAt(var21);
                    if (var17.rh_concept == this) {
                        var3 = var3 + var17.lh_concept.id + " " + var17.lh_concept.writeDefinitionWithStartLetters(var5) + " [" + var17.id + "]<br>\n";
                    }
                }

                var3 = var3 + "\n\n";
            } else {
                var3 = var3 + "There are no concept implications involving this concept.<br>\n\n";
            }
        }

        if (var1.contains("generalisations")) {
            var3 = var3 + "The generalisations of this concept are:<br>\n" + this.writeGeneralisationsHTML(var5) + "\n";
        }

        if (var1.contains("alt defs")) {
            if (this.conjectured_equivalent_concepts.isEmpty()) {
                var3 = var3 + "There are no alternative definitions for this concept.<br>\n";
            } else {
                var3 = var3 + "The alternative definitions of this concept are:<br>\n" + this.allDefinitionsHTML(var5);
            }
        }

        if (var1.contains("rel equivs")) {
            if (this.relevant_equivalences.isEmpty()) {
                var3 = var3 + "There are no relevant equivalences for this concept.<br>\n";
            } else {
                var3 = var3 + "The relevant equivalences for this concept are:<br>\n";

                for(var21 = 0; var21 < this.relevant_equivalences.size(); ++var21) {
                    Equivalence var18 = (Equivalence)this.relevant_equivalences.elementAt(var21);
                    var3 = var3 + this.replaceLTForHTML(var18.writeConjecture(var5)) + "<br>\n";
                }
            }
        }

        Implicate var19;
        if (var1.contains("rel imps")) {
            if (this.relevant_implicates.isEmpty()) {
                var3 = var3 + "There are no relevant implicates for this concept.<br>\n";
            } else {
                var3 = var3 + "The relevant implicates for this concept are:<br>\n";

                for(var21 = 0; var21 < this.relevant_implicates.size(); ++var21) {
                    var19 = (Implicate)this.relevant_implicates.elementAt(var21);
                    var3 = var3 + this.replaceLTForHTML(var19.writeConjecture(var5) + "\n");
                }
            }
        }

        if (var1.contains("near equivs")) {
            if (this.near_equivalences.isEmpty()) {
                var3 = var3 + "\nThere are no near-equivalent concepts.<br>\n";
            } else {
                var3 = var3 + "\nThe near-equivalence concepts are:<br>\n";

                for(var21 = 0; var21 < this.near_equivalences.size(); ++var21) {
                    NearEquivalence var20 = (NearEquivalence)this.near_equivalences.elementAt(var21);
                    var3 = var3 + this.decimalPlaces(var20.score, 2) + ":" + var20.rh_concept.id + ". " + this.replaceLTForHTML(var20.rh_concept.writeDefinitionWithStartLetters(var5)) + var20.counterexamples.toString() + "<br>\n";
                }
            }
        }

        if (var1.contains("implied specs")) {
            if (this.implicates.size() > 0) {
                var3 = var3 + "These specifications are implied in addition:\n";

                for(var21 = 0; var21 < this.implicates.size(); ++var21) {
                    var19 = (Implicate)this.implicates.elementAt(var21);
                    var3 = var3 + this.replaceLTForHTML(var19.writeGoal("otter")) + "<br>\n";
                }
            } else {
                var3 = var3 + "There are no specifications implied in addition<br>\n";
            }

            var9 = "";
            Vector var22 = new Vector();

            for(int var11 = 0; var11 < this.generalisations.size(); ++var11) {
                Concept var12 = (Concept)this.generalisations.elementAt(var11);

                for(int var13 = 0; var13 < var12.implicates.size(); ++var13) {
                    Implicate var14 = (Implicate)var12.implicates.elementAt(var13);
                    String var15 = var14.writeGoal("otter");
                    if (!var22.contains(var15)) {
                        var9 = var9 + var14.writeGoal("otter") + "<br>\n";
                        var22.addElement(var15);
                    }
                }
            }

            if (!var9.equals("")) {
                var3 = var3 + "The specifications implied by the generalisations are:\n" + var9;
            }
        }

        if (!var16.equals(var3)) {
            var3 = var3 + "<hr>\n";
        }

        var3 = var3 + "<b><u>Internal details</u></b><br>\n";
        if (var1.contains("functions")) {
            if (this.functions.isEmpty()) {
                var3 = var3 + "There are no function specifications for this concept.<br>\n";
            } else {
                var3 = var3 + "The function specifications for this concept are:<br>\n";

                for(var21 = 0; var21 < this.functions.size(); ++var21) {
                    Function var23 = (Function)this.functions.elementAt(var21);
                    var3 = var3 + var23.writeFunction();
                    if (var21 < this.functions.size() - 1) {
                        var3 = var3 + " & ";
                    }
                }

                var3 = var3 + "<br>";
            }
        }

        if (var1.contains("specifications")) {
            var3 = var3 + "These are the specifications inside the concept definition:<br>\n";
            var3 = var3 + "\n" + this.writeSpecDetailsHTML();
        }

        return var3;
    }

    public String fullASCIIDetails(Vector var1, int var2) {
        String var3 = "";
        if (var1.contains("id")) {
            var3 = var3 + this.id;
        }

        if (var1.contains("name")) {
            var3 = var3 + " " + this.name + " " + this.toString();
        }

        if (var1.contains("id") || var1.contains("name")) {
            var3 = var3 + "\n";
        }

        if (var1.contains("simplified def")) {
            boolean var4 = this.definition_writer.remove_existence_variables;
            this.definition_writer.remove_existence_variables = true;
            var3 = var3 + this.writeDefinitionWithStartLetters("otter") + "\n";
            this.definition_writer.remove_existence_variables = var4;
        }

        if (var1.contains("ascii def")) {
            var3 = var3 + this.writeDefinitionWithStartLetters("ascii") + "\n";
        }

        if (var1.contains("otter def")) {
            var3 = var3 + this.writeDefinitionWithStartLetters("otter") + "\n";
        }

        if (var1.contains("prolog def")) {
            var3 = var3 + this.writeDefinitionWithStartLetters("prolog") + "\n";
        }

        if (var1.contains("tptp def")) {
            var3 = var3 + this.writeDefinitionWithStartLetters("tptp") + "\n";
        }

        if (var1.contains("skolemised def")) {
            var3 = var3 + this.writeSkolemisedRepresentation() + "\n";
        }

        if (!var3.equals("")) {
            var3 = var3 + "-----------------\n";
        }

        if (var1.contains("total score")) {
            var3 = var3 + "Total score = " + this.decimalPlaces(this.interestingness, var2) + "\n";
        }

        if (var1.contains("arity")) {
            var3 = var3 + "Arity = " + this.decimalPlaces(this.arity, var2) + "\n";
        }

        if (var1.contains("applicability")) {
            var3 = var3 + "Applicability = " + this.decimalPlaces(this.applicability, var2) + " " + this.decimalPlaces(this.normalised_applicability, var2) + ")\n";
        }

        if (var1.contains("child num")) {
            var3 = var3 + "Children number = " + this.decimalPlaces(this.number_of_children, var2) + "\n";
        }

        if (var1.contains("child score")) {
            var3 = var3 + "Children score = " + this.decimalPlaces(this.children_score, var2) + " (" + this.decimalPlaces(this.children_score, var2) + ")\n";
        }

        if (var1.contains("comprehens")) {
            var3 = var3 + "Comprehensibility = " + this.decimalPlaces(this.comprehensibility, var2) + " (" + this.decimalPlaces(this.normalised_comprehensibility, var2) + ")\n";
        }

        if (var1.contains("complexity")) {
            var3 = var3 + "Complexity = " + this.decimalPlaces(this.complexity, var2) + "\n";
        }

        if (var1.contains("coverage")) {
            var3 = var3 + "Coverage = " + this.decimalPlaces(this.coverage, var2) + " (" + this.decimalPlaces(this.normalised_coverage, var2) + ")\n";
        }

        if (var1.contains("devel steps")) {
            var3 = var3 + "Development steps = " + this.decimalPlaces(this.development_steps_num, var2) + " (" + this.decimalPlaces(this.normalised_development_steps_num, var2) + ")\n";
        }

        if (var1.contains("discrimination")) {
            var3 = var3 + "Discrimination = " + this.decimalPlaces(this.discrimination, var2) + " (" + this.decimalPlaces(this.normalised_discrimination_score, var2) + ")\n";
        }

        if (var1.contains("equiv conj score")) {
            var3 = var3 + "Equiv Conjecture score = " + this.decimalPlaces(this.equiv_conj_score, var2) + " (" + this.decimalPlaces(this.normalised_equiv_conj_score, var2) + ")\n";
        }

        if (var1.contains("equiv conj num")) {
            var3 = var3 + "Equiv Conjecture num = " + this.decimalPlaces(this.equiv_conjectures.size(), var2) + " (" + this.decimalPlaces(this.normalised_equiv_conj_num, var2) + ")\n";
        }

        if (var1.contains("highlight")) {
            var3 = var3 + "Highlight = " + this.decimalPlaces(this.highlight_score, var2) + "\n";
        }

        if (var1.contains("ne conj score")) {
            var3 = var3 + "Non-exists Conjecture score = " + this.decimalPlaces(this.ne_conj_score, var2) + " (" + this.decimalPlaces(this.normalised_ne_conj_score, var2) + ")\n";
        }

        if (var1.contains("ne conj num")) {
            var3 = var3 + "Non-exists Conjecture num = " + this.decimalPlaces(this.ne_conjectures.size(), var2) + " (" + this.decimalPlaces(this.normalised_ne_conj_num, var2) + ")\n";
        }

        if (var1.contains("neg applic")) {
            var3 = var3 + "Negative Applicability = " + this.decimalPlaces(this.negative_applicability, var2) + " (" + this.decimalPlaces(this.normalised_negative_applicability, var2) + ")\n";
        }

        if (var1.contains("imp conj score")) {
            var3 = var3 + "Implicate Conjecture score = " + this.decimalPlaces(this.imp_conj_score, var2) + " (" + this.decimalPlaces(this.normalised_imp_conj_score, var2) + ")\n";
        }

        if (var1.contains("imp conj num")) {
            var3 = var3 + "Implicate Conjecture num = " + this.decimalPlaces(this.imp_conjectures.size(), var2) + " (" + this.decimalPlaces(this.normalised_imp_conj_num, var2) + ")\n";
        }

        if (var1.contains("invariance")) {
            var3 = var3 + "Invariance = " + this.decimalPlaces(this.invariance, var2) + " (" + this.decimalPlaces(this.normalised_invariance_score, var2) + ")\n";
        }

        if (var1.contains("novelty")) {
            var3 = var3 + "Novelty = " + this.decimalPlaces(this.novelty, var2) + " (" + this.decimalPlaces(this.normalised_novelty, var2) + ")\n";
        }

        if (var1.contains("parent score")) {
            var3 = var3 + "Parent score = " + this.decimalPlaces(this.parent_score, var2) + " (" + this.decimalPlaces(this.parent_score, var2) + ")\n";
        }

        if (var1.contains("parsimony")) {
            var3 = var3 + "Parsimony = " + this.decimalPlaces(this.parsimony, var2) + " (" + this.decimalPlaces(this.normalised_parsimony, var2) + ")\n";
        }

        if (var1.contains("pi conj score")) {
            var3 = var3 + "PI Conjecture score = " + this.decimalPlaces(this.pi_conj_score, var2) + " (" + this.decimalPlaces(this.normalised_pi_conj_score, var2) + ")\n";
        }

        if (var1.contains("pi conj num")) {
            var3 = var3 + "PI Conjecture num = " + this.decimalPlaces(this.pi_conjectures.size(), var2) + " (" + this.decimalPlaces(this.normalised_pi_conj_num, var2) + ")\n";
        }

        if (var1.contains("pos applic")) {
            var3 = var3 + "Positive Applicability = " + this.decimalPlaces(this.positive_applicability, var2) + " (" + this.decimalPlaces(this.normalised_positive_applicability, var2) + ")\n";
        }

        if (var1.contains("pred power")) {
            var3 = var3 + "Predictive Power = " + this.decimalPlaces(this.predictive_power, var2) + "\n";
        }

        if (var1.contains("productivity")) {
            var3 = var3 + "Productivity = " + this.decimalPlaces(this.productivity, var2) + " (" + this.decimalPlaces(this.normalised_productivity, var2) + ")\n";
        }

        if (var1.contains("variety")) {
            var3 = var3 + "Variety = " + this.decimalPlaces(this.variety, var2) + " (" + this.decimalPlaces(this.normalised_variety, var2) + ")\n";
        }

        if (!var3.equals(var3)) {
            var3 = var3 + "-----------------\n";
        }

        if (var1.contains("const step")) {
            if (this.is_user_given) {
                var3 = var3 + "This concept was supplied by the user.\n";
            } else {
                var3 = var3 + "Constructed with this step: " + this.construction.asString() + ".\n";
            }
        }

        if (var1.contains("ancestors")) {
            var3 = var3 + "The ancestors of this concept are:" + this.ancestor_ids.toString() + "\n";
        }

        String var5;
        if (var1.contains("const time")) {
            var5 = "s";
            String var6 = Long.toString(this.when_constructed);
            if (var6.equals("1")) {
                var5 = "";
            }

            var3 = var3 + "Constructed after " + Long.toString(this.when_constructed) + " second" + var5 + ", " + Integer.toString(this.step_number) + " steps.\n";
        }

        if (var1.contains("cross domain")) {
            var5 = "";
            if (!this.is_cross_domain) {
                var5 = "not ";
            }

            var3 = var3 + "This is " + var5 + "a cross domain concept.\n";
        }

        if (var1.contains("entity instant")) {
            var5 = "";
            if (!this.is_entity_instantiations) {
                var5 = "not ";
            }

            var3 = var3 + "This is " + var5 + "an instantiation of entities.\n";
        }

        if (!var3.equals(var3)) {
            var3 = var3 + "-----------------\n";
        }

        if (var1.contains("examples")) {
            var3 = var3 + this.prettyPrintExamples(this.datatable) + "\n";
        }

        if (var1.contains("datatable")) {
            var3 = var3 + "The datatable is:\n" + this.datatable.toTable() + "\n";
        }

        if (var1.contains("types")) {
            var3 = var3 + "The object types in the datatable are: " + this.types.toString() + "\n";
        }

        int var15;
        if (var1.contains("functions")) {
            if (this.functions.isEmpty()) {
                var3 = var3 + "There are no function specifications for this concept.\n";
            } else {
                var3 = var3 + "The function specifications for this concept are:\n";

                for(var15 = 0; var15 < this.functions.size(); ++var15) {
                    Function var12 = (Function)this.functions.elementAt(var15);
                    var3 = var3 + var12.writeFunction();
                    if (var15 < this.functions.size() - 1) {
                        var3 = var3 + " & ";
                    }
                }

                var3 = var3 + "\n\n";
            }
        }

        if (var1.contains("categorisation")) {
            var3 = var3 + "The categorisation the examples of this concept achieves is:\n";
            var3 = var3 + this.categorisation.toString() + "\n\n";
        }

        if (var1.contains("add examples")) {
            if (this.additional_datatable.isEmpty()) {
                var3 = var3 + "There are no additional entities for this concept.\n";
            } else {
                var3 = var3 + "From the additional table:\n";
                var3 = var3 + this.prettyPrintExamples(this.additional_datatable) + "\n";
            }
        }

        if (!var3.equals(var3)) {
            var3 = var3 + "-----------------\n";
        }

        if (var1.contains("implications")) {
            if (this.implications.size() > 0) {
                var3 = var3 + "Concepts implied by this concept:\n";

                Implication var13;
                for(var15 = 0; var15 < this.implications.size(); ++var15) {
                    var13 = (Implication)this.implications.elementAt(var15);
                    if (var13.lh_concept == this) {
                        var3 = var3 + var13.rh_concept.id + " " + var13.rh_concept.writeDefinitionWithStartLetters("otter") + " [" + var13.id + "]\n";
                    }
                }

                var3 = var3 + "\nConcepts which imply this concept:\n";

                for(var15 = 0; var15 < this.implications.size(); ++var15) {
                    var13 = (Implication)this.implications.elementAt(var15);
                    if (var13.rh_concept == this) {
                        var3 = var3 + var13.lh_concept.id + " " + var13.lh_concept.writeDefinitionWithStartLetters("otter") + " [" + var13.id + "]\n";
                    }
                }

                var3 = var3 + "\n\n";
            } else {
                var3 = var3 + "There are no concept implications involving this concept\n\n";
            }
        }

        if (var1.contains("generalisations")) {
            var3 = var3 + "The generalisations of this concept are:\n" + this.writeGeneralisations("ascii") + "\n";
        }

        if (var1.contains("alt defs ascii")) {
            if (this.conjectured_equivalent_concepts.isEmpty()) {
                var3 = var3 + "There are no alternative definitions for this concept.\n";
            } else {
                var3 = var3 + "The alternative definitions of this concept are:\n" + this.allDefinitions("ascii");
            }
        }

        if (var1.contains("alt defs prolog")) {
            if (this.conjectured_equivalent_concepts.isEmpty()) {
                var3 = var3 + "There are no alternative definitions for this concept.\n";
            } else {
                var3 = var3 + "The alternative definitions of this concept are:\n" + this.allDefinitions("prolog");
            }
        }

        Equivalence var14;
        if (var1.contains("rel equivs prolog")) {
            if (this.relevant_equivalences.isEmpty()) {
                var3 = var3 + "There are no relevant equivalences for this concept.\n";
            } else {
                var3 = var3 + "The relevant equivalences for this concept are:\n";

                for(var15 = 0; var15 < this.relevant_equivalences.size(); ++var15) {
                    var14 = (Equivalence)this.relevant_equivalences.elementAt(var15);
                    var3 = var3 + var14.writeConjecture("prolog") + "\n";
                }
            }
        }

        if (var1.contains("rel equivs ascii")) {
            if (this.relevant_equivalences.isEmpty()) {
                var3 = var3 + "There are no relevant equivalences for this concept.\n";
            } else {
                var3 = var3 + "The relevant equivalences for this concept are:\n";

                for(var15 = 0; var15 < this.relevant_equivalences.size(); ++var15) {
                    var14 = (Equivalence)this.relevant_equivalences.elementAt(var15);
                    var3 = var3 + var14.writeConjecture("ascii") + "\n";
                }
            }
        }

        Implicate var16;
        if (var1.contains("rel imps prolog")) {
            if (this.relevant_implicates.isEmpty()) {
                var3 = var3 + "There are no relevant implicates for this concept.\n";
            } else {
                var3 = var3 + "The relevant implicates for this concept are:\n";

                for(var15 = 0; var15 < this.relevant_implicates.size(); ++var15) {
                    var16 = (Implicate)this.relevant_implicates.elementAt(var15);
                    var3 = var3 + var16.writeConjecture("prolog") + "\n";
                }
            }
        }

        if (var1.contains("rel imps ascii")) {
            if (this.relevant_implicates.isEmpty()) {
                var3 = var3 + "There are no relevant implicates for this concept.\n";
            } else {
                var3 = var3 + "The relevant implicates for this concept are:\n";

                for(var15 = 0; var15 < this.relevant_implicates.size(); ++var15) {
                    var16 = (Implicate)this.relevant_implicates.elementAt(var15);
                    var3 = var3 + var16.writeConjecture("ascii") + "\n";
                }
            }
        }

        NearEquivalence var17;
        if (var1.contains("near equivs prolog")) {
            if (this.near_equivalences.isEmpty()) {
                var3 = var3 + "\nThere are no near-equivalent concepts.\n";
            } else {
                var3 = var3 + "\nThe near-equivalence concepts are:\n";

                for(var15 = 0; var15 < this.near_equivalences.size(); ++var15) {
                    var17 = (NearEquivalence)this.near_equivalences.elementAt(var15);
                    var3 = var3 + this.decimalPlaces(var17.score, 2) + ":" + var17.rh_concept.id + ". " + var17.rh_concept.writeDefinitionWithStartLetters("prolog") + var17.counterexamples.toString() + "\n";
                }
            }
        }

        if (var1.contains("near equivs ascii")) {
            if (this.near_equivalences.isEmpty()) {
                var3 = var3 + "\nThere are no near-equivalent concepts.\n";
            } else {
                var3 = var3 + "\nThe near-equivalence concepts are:\n";

                for(var15 = 0; var15 < this.near_equivalences.size(); ++var15) {
                    var17 = (NearEquivalence)this.near_equivalences.elementAt(var15);
                    var3 = var3 + this.decimalPlaces(var17.score, 2) + ":" + var17.rh_concept.id + ". " + var17.rh_concept.writeDefinitionWithStartLetters("ascii") + var17.counterexamples.toString() + "\n";
                }
            }
        }

        if (!var3.equals(var3)) {
            var3 = var3 + "-----------------\n";
        }

        if (var1.contains("implied specs")) {
            if (this.implicates.size() > 0) {
                var3 = var3 + "These specifications are implied in addition:\n";

                for(var15 = 0; var15 < this.implicates.size(); ++var15) {
                    var16 = (Implicate)this.implicates.elementAt(var15);
                    var3 = var3 + var16.writeGoal("otter") + "\n";
                }
            } else {
                var3 = var3 + "There are no specifications implied in addition\n";
            }

            var5 = "";
            Vector var18 = new Vector();

            for(int var7 = 0; var7 < this.generalisations.size(); ++var7) {
                Concept var8 = (Concept)this.generalisations.elementAt(var7);

                for(int var9 = 0; var9 < var8.implicates.size(); ++var9) {
                    Implicate var10 = (Implicate)var8.implicates.elementAt(var9);
                    String var11 = var10.writeGoal("otter");
                    if (!var18.contains(var11)) {
                        var5 = var5 + var10.writeGoal("otter") + "\n";
                        var18.addElement(var11);
                    }
                }
            }

            if (!var5.equals("")) {
                var3 = var3 + "The specifications implied by the generalisations are:\n" + var5;
            }
        }

        if (!var3.equals(var3)) {
            var3 = var3 + "-----------------\n";
        }

        if (var1.contains("specifications")) {
            var3 = var3 + "These are the specifications inside the concept definition:\n";
            var3 = var3 + "\n" + this.writeSpecDetails();
        }

        if (var1.contains("anc defs prolog")) {
            var3 = var3 + "The ancestors of this concept are:\n";
            var3 = var3 + this.writeAncestorsAndEquivalents("prolog");
        }

        return var3;
    }

    public String prettyPrintExamplesHTML(Datatable var1) {
        String var2 = "";
        int var3;
        Row var4;
        if (this.arity == 1) {
            var2 = var2 + "<table border=0><tr><td>The positives are:\n<ol>\n";

            for(var3 = 0; var3 < var1.size(); ++var3) {
                var4 = (Row)var1.elementAt(var3);
                if (!var4.tuples.isEmpty()) {
                    var2 = var2 + "<li>" + var4.entity + "\n";
                }
            }

            var2 = var2 + "\n</ol></td><td>&nbsp;</td><td>";
            var2 = var2 + "The negatives are:\n<ol>\n";

            for(var3 = 0; var3 < var1.size(); ++var3) {
                var4 = (Row)var1.elementAt(var3);
                if (var4.tuples.isEmpty()) {
                    var2 = var2 + "<li>" + var4.entity + "\n";
                }
            }

            var2 = var2 + "</td></tr></table>\n";
        } else {
            for(var3 = 0; var3 < var1.size(); ++var3) {
                var4 = (Row)var1.elementAt(var3);
                var2 = var2 + "f(" + var4.entity + ")=";
                var2 = var2 + var4.tuples.toString();
                var2 = var2 + "<br>";
            }
        }

        return var2;
    }

    public String prettyPrintExamples(Datatable var1) {
        String var2 = "";
        if (this.arity == 1) {
            var2 = var2 + "The " + (String)this.types.elementAt(0) + "s with this property are:\n";
            boolean var3 = false;

            int var4;
            Row var5;
            for(var4 = 0; var4 < var1.size(); ++var4) {
                var5 = (Row)var1.elementAt(var4);
                if (!var5.tuples.isEmpty()) {
                    if (var3) {
                        var2 = var2 + ", ";
                    }

                    var2 = var2 + var5.entity;
                    var3 = true;
                }
            }

            var2 = var2 + "\n";
            var2 = var2 + "The " + (String)this.types.elementAt(0) + "s without this property are:\n";
            var3 = false;

            for(var4 = 0; var4 < var1.size(); ++var4) {
                var5 = (Row)var1.elementAt(var4);
                if (var5.tuples.isEmpty()) {
                    if (var3) {
                        var2 = var2 + ", ";
                    }

                    var2 = var2 + var5.entity;
                    var3 = true;
                }
            }

            var2 = var2 + "\n";
        } else {
            var2 = var2 + "As a function, this concept has these values:\n";

            for(int var6 = 0; var6 < var1.size(); ++var6) {
                Row var7 = (Row)var1.elementAt(var6);
                var2 = var2 + "f(" + var7.entity + ")=";
                var2 = var2 + var7.tuples.toString();
                var2 = var2 + "\n";
            }
        }

        return var2;
    }

    public String writeSpecDetails() {
        String var1 = "";

        for(int var2 = 0; var2 < this.specifications.size(); ++var2) {
            Specification var3 = (Specification)this.specifications.elementAt(var2);
            var1 = var1 + var3.fullDetails() + "\n---------------------\n";
        }

        return var1;
    }

    public String writeSpecDetailsHTML() {
        String var1 = "";

        for(int var2 = 0; var2 < this.specifications.size(); ++var2) {
            Specification var3 = (Specification)this.specifications.elementAt(var2);
            var1 = var1 + "<pre>" + var3.fullDetails() + "</pre><table width=100><tr><td><hr></td></tr></table>\n";
        }

        return var1;
    }

    public String allDefinitions(String var1) {
        String var2 = "";
        String var3 = "";

        for(int var4 = 0; var4 < this.conjectured_equivalent_concepts.size(); ++var4) {
            Concept var5 = (Concept)this.conjectured_equivalent_concepts.elementAt(var4);
            Conjecture var6 = var5.equivalence_conjecture;
            if (this.isGeneralisationOf(var5) < 0) {
                var2 = var2 + "(" + var6.id + ") " + var5.writeDefinition(var1);
                var2 = var2 + " [" + var6.proof_status + "]\n";
            } else {
                var3 = var3 + "(" + var6.id + ") " + var5.writeDefinition(var1);
                var3 = var3 + " [" + var6.proof_status + "]\n";
            }
        }

        if (!var3.equals("")) {
            var2 = var2 + "--\n" + var3;
        }

        return var2;
    }

    public String allDefinitionsHTML(String var1) {
        String var2 = "";
        String var3 = "";

        for(int var4 = 0; var4 < this.conjectured_equivalent_concepts.size(); ++var4) {
            Concept var5 = (Concept)this.conjectured_equivalent_concepts.elementAt(var4);
            Conjecture var6 = var5.equivalence_conjecture;
            if (this.isGeneralisationOf(var5) < 0) {
                var2 = var2 + "(" + var6.id + ") " + var5.writeDefinition(var1);
                var2 = var2 + " [" + var6.proof_status + "]<br>\n";
            } else {
                var3 = var3 + "(" + var6.id + ") " + var5.writeDefinition(var1);
                var3 = var3 + " [" + var6.proof_status + "]<br>\n";
            }
        }

        if (!var3.equals("")) {
            var2 = var2 + "--<br>\n" + var3;
        }

        return var2;
    }

    public String writeGeneralisations(String var1) {
        String var2 = "";

        for(int var3 = 0; var3 < this.generalisations.size(); ++var3) {
            Concept var4 = (Concept)this.generalisations.elementAt(var3);
            var2 = var2 + var4.id + ". " + var4.writeDefinition(var1) + "\n";
        }

        return var2;
    }

    public String writeGeneralisationsHTML(String var1) {
        String var2 = "";

        for(int var3 = 0; var3 < this.generalisations.size(); ++var3) {
            Concept var4 = (Concept)this.generalisations.elementAt(var3);
            var2 = var2 + var4.id + ". " + var4.writeDefinition(var1) + "<br>\n";
        }

        return var2;
    }

    public String writeAncestors(String var1, Theory var2) {
        String var3 = "";

        for(int var4 = 0; var4 < this.ancestor_ids.size(); ++var4) {
            String var5 = (String)this.ancestor_ids.elementAt(var4);
            Concept var6 = var2.getConcept(var5);
            var3 = var3 + var6.id + ". " + var6.writeDefinition(var1) + "\n";
        }

        return var3;
    }

    public Sequence asSequence() {
        Sequence var1 = new Sequence();
        int var2;
        Row var3;
        Tuples var4;
        if (this.arity == 1) {
            for(var2 = 0; var2 < this.datatable.size(); ++var2) {
                var3 = (Row)this.datatable.elementAt(var2);
                var4 = var3.tuples;
                if (var4.size() > 0) {
                    var1.addElement(var3.entity);
                }
            }
        }

        if (this.arity == 2) {
            for(var2 = 0; var2 < this.datatable.size(); ++var2) {
                var3 = (Row)this.datatable.elementAt(var2);
                var4 = var3.tuples;

                for(int var5 = 0; var5 < var4.size(); ++var5) {
                    var1.addElement((String)((Vector)var4.elementAt(var5)).elementAt(0));
                }
            }
        }

        return var1;
    }

    public int isGeneralisationOf(Concept var1) {
        byte var2 = 0;
        Vector var3 = var1.specifications;
        if (this.specifications.size() > var3.size()) {
            return -1;
        } else {
            for(int var4 = 0; var2 > -1 && var4 < this.specifications.size(); ++var4) {
                if (!var3.contains(this.specifications.elementAt(var4))) {
                    var2 = -1;
                }
            }

            if (this.specifications.size() == var3.size() && var2 == 0) {
                var2 = 1;
            }

            return var2;
        }
    }

    public void updateDatatable(Vector var1, Entity var2) {
        Tuples var3 = (Tuples)var2.concept_data.get(this.name);
        if (var3 != null) {
            Row var4 = new Row(var2.name, var3);
            this.datatable.addElement(var4);
        } else {
            this.calculateRow(var1, var2.name);
        }

        boolean var7 = false;

        for(int var5 = 0; var5 < this.additional_datatable.size() && !var7; ++var5) {
            Row var6 = (Row)this.additional_datatable.elementAt(var5);
            if (var6.entity.equals(var2.name)) {
                var7 = true;
                this.datatable.addElement(var6);
                this.additional_datatable.removeElementAt(var5);
            }
        }

    }

    public Concept mostSpecificGeneralisation(Vector var1, Vector var2) {
        Vector var3 = new Vector();
        Vector var4 = new Vector();
        Enumeration var5 = this.specifications.elements();

        Specification var6;
        while(var5.hasMoreElements()) {
            var6 = (Specification)var5.nextElement();
            if (!var6.involvesColumns(var2)) {
                var4.addElement(var6);
            }
        }

        var5 = var4.elements();

        int var8;
        int var10;
        while(var5.hasMoreElements()) {
            var6 = (Specification)var5.nextElement();
            Specification var7 = var6.copy();
            var7.permutation = new Vector();

            for(var8 = 0; var8 < var6.permutation.size(); ++var8) {
                int var9 = 0;
                var10 = new Integer((String)var6.permutation.elementAt(var8));

                for(int var11 = 0; var11 < var2.size(); ++var11) {
                    int var12 = new Integer((String)var2.elementAt(var11));
                    if (var12 < var10) {
                        ++var9;
                    }
                }

                var7.permutation.addElement(Integer.toString(var10 - var9));
            }

            var3.addElement(var7);
        }

        var5 = var1.elements();
        boolean var13 = false;
        Concept var14 = new Concept();

        while(var5.hasMoreElements() && !var13) {
            var14 = (Concept)var5.nextElement();
            if (var14.specifications.size() == var3.size()) {
                for(var8 = 0; var8 < var3.size(); ++var8) {
                    Specification var15 = (Specification)var3.elementAt(var8);

                    for(var10 = 0; var10 < var14.specifications.size(); ++var10) {
                        Specification var16 = (Specification)var14.specifications.elementAt(var10);
                        if (var16.equals(var15)) {
                            break;
                        }
                    }

                    if (var10 == var14.specifications.size()) {
                        break;
                    }
                }

                if (var8 == var3.size()) {
                    var13 = true;
                }
            }
        }

        if (!var13) {
            var14 = new Concept();
        }

        if (var14.arity != this.arity - var2.size()) {
            var14 = new Concept();
        }

        return var14;
    }

    public String firstOrderRepresentation() {
        int var1 = 0;
        String var2 = "";
        String var3 = Integer.toString(this.arity) + "tuple(" + this.id + ",";

        for(int var4 = 0; var4 < this.datatable.size(); ++var4) {
            Row var5 = (Row)this.datatable.elementAt(var4);

            for(int var6 = 0; var6 < var5.tuples.size(); ++var6) {
                String var7 = var3 + var5.entity;
                Vector var8 = (Vector)var5.tuples.elementAt(var6);

                for(int var9 = 0; var9 < var8.size(); ++var9) {
                    var7 = var7 + "," + (String)var8.elementAt(var9);
                }

                var7 = var7 + ").";
                var2 = var2 + var7;
                ++var1;
                if (var1 == 5) {
                    var2 = var2 + "\n";
                    var1 = 0;
                } else {
                    var2 = var2 + " ";
                }
            }
        }

        return var2;
    }

    public void addAdditionalRow(String var1, String var2) {
        this.additional_datatable.addEmptyRow(var1);
        if (!var2.equals("[]")) {
            if (var2.equals("[[]]")) {
                Row var6 = this.additional_datatable.rowWithEntity(var1);
                var6.tuples.addElement(new Vector());
            } else {
                if (var2.substring(0, 2).equals("[[")) {
                    var2 = var2.substring(1, var2.length());
                }

                for(int var3 = var2.indexOf("]"); var3 >= 0; var3 = var2.indexOf("]")) {
                    String var4 = var2.substring(0, var3 + 1);
                    Vector var5 = this.getTuple(var4);
                    var5.insertElementAt(var1, 0);
                    this.additional_datatable.addTuple(var5);
                    if (var2.indexOf("[", var3) < 0) {
                        break;
                    }

                    var2 = var2.substring(var2.indexOf("[", var3), var2.length());
                }

            }
        }
    }

    private Vector getTuple(String var1) {
        Vector var2 = new Vector();
        var1 = var1.substring(1, var1.length() - 1);

        for(int var3 = var1.indexOf(","); var3 > 0; var3 = var1.indexOf(",")) {
            var2.addElement(var1.substring(0, var3).trim());
            var1 = var1.substring(var3 + 1, var1.length());
            if (var1.substring(0, 1).equals(" ")) {
                var1 = var1.substring(1, var1.length());
            }
        }

        if (!var1.equals("") || !var1.equals(" ")) {
            var2.addElement(var1);
        }

        return var2;
    }

    public void getNearEquivalences(Theory var1, Vector var2, double var3) {
        this.near_equivalences.removeAllElements();

        for(int var5 = 0; var5 < var2.size(); ++var5) {
            Concept var6 = (Concept)var2.elementAt(var5);
            if (var6.types.toString().equals(this.types.toString()) && var6 != this) {
                new Vector();
                Vector var8 = new Vector();
                double var9 = this.datatable.quickPercentageMatchWith(var1, var6.datatable, var8);
                if (var9 >= var3 / 100.0D) {
                    NearEquivalence var11 = new NearEquivalence(this, var6, var8, var9);
                    new NearEquivalence(var6, this, var8, var9);
                    this.near_equivalences.addElement(var11, "score");
                    boolean var13 = false;

                    for(int var14 = 0; var14 < var6.near_equivalences.size() && !var13; ++var14) {
                        NearEquivalence var15 = (NearEquivalence)var6.near_equivalences.elementAt(var14);
                        if (var15.lh_concept == var6 && var15.rh_concept == this) {
                            var13 = true;
                        }
                    }

                    if (!var13) {
                        var6.near_equivalences.addElement(var11, "score");
                    }
                }
            }
        }

    }

    public void getNearImplications(Theory var1, Vector var2, double var3) {
        this.near_implications.removeAllElements();

        for(int var5 = 0; var5 < var2.size(); ++var5) {
            Concept var6 = (Concept)var2.elementAt(var5);
            if (var6.types.toString().equals(this.types.toString()) && var6 != this) {
                Vector var7 = new Vector();
                double var8 = this.datatable.quickPercentageMatchWith(var1, var6.datatable, var7);
                if (var8 >= var3 / 100.0D) {
                    NearEquivalence var10 = new NearEquivalence(this, var6, var7, var8);
                    new NearEquivalence(var6, this, var7, var8);
                    this.near_equivalences.addElement(var10, "score");
                    boolean var12 = false;

                    for(int var13 = 0; var13 < var6.near_equivalences.size() && !var12; ++var13) {
                        NearEquivalence var14 = (NearEquivalence)var6.near_equivalences.elementAt(var13);
                        if (var14.lh_concept == var6 && var14.rh_concept == this) {
                            var12 = true;
                        }
                    }

                    if (!var12) {
                        var6.near_equivalences.addElement(var10, "score");
                    }
                }
            }
        }

    }

    public void getSubsumptionNearImplications(Vector var1, double var2) {
        this.near_implications.removeAllElements();
        double var4 = 0.0D;

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            Concept var7 = (Concept)var1.elementAt(var6);
            if (var7.arity == this.arity && !var7.is_entity_instantiations) {
                Vector var8;
                boolean var9;
                NearImplication var10;
                int var12;
                if (var7.isGeneralisationOf(this) == -1) {
                    var8 = var7.empiricallySubsumes(this, var2);
                    if (var8 != null && var8.size() != 0) {
                        var9 = false;
                        if (this.arity != 0 && this.arity != 1) {
                            var12 = this.datatable.size();
                        } else {
                            var12 = this.getPositives().size();
                        }

                        var4 = (double)(var12 - var8.size());
                        var4 /= (double)var12;
                        var4 *= 100.0D;
                        if (var4 >= var2) {
                            var10 = new NearImplication(this, var7, var8, var4);
                            this.near_implications.addElement(var10, "score");
                        }
                    }
                }

                if (this.isGeneralisationOf(var7) == -1) {
                    var8 = this.empiricallySubsumes(var7, var2);
                    if (var8 != null && var8.size() != 0) {
                        var9 = false;
                        if (var7.arity != 0 && var7.arity != 1) {
                            var12 = var7.datatable.size();
                        } else {
                            var12 = var7.getPositives().size();
                        }

                        var4 = (double)(var12 - var8.size());
                        var4 /= (double)var12;
                        var4 *= 100.0D;
                        if (var4 >= var2) {
                            var10 = new NearImplication(var7, this, var8, var4);
                            this.near_implications.addElement(var10, "score");
                        }
                    }
                }

                boolean var11 = false;

                for(var12 = 0; var12 < var7.near_implications.size() && !var11; ++var12) {
                    var10 = (NearImplication)var7.near_implications.elementAt(var12);
                    if (var10.lh_concept == var7 && var10.rh_concept == this) {
                        var11 = true;
                    }
                }
            }
        }

    }

    public Vector empiricallySubsumes(Concept var1, double var2) {
        if (this.arity != var1.arity) {
            return null;
        } else if (!this.types.toString().equals(var1.types.toString())) {
            return null;
        } else {
            Vector var4 = new Vector();
            int var5 = 0;
            int var6 = 0;

            for(int var7 = 0; var7 < this.datatable.size(); ++var7) {
                Row var8 = (Row)this.datatable.elementAt(var7);
                Row var9 = (Row)var1.datatable.elementAt(var7);
                if (!var8.tuples.subsumes(var9.tuples)) {
                    var4.addElement(new Entity(var8.entity));
                }

                if (!var8.tuples.isEmpty()) {
                    ++var5;
                }

                if (!var9.tuples.isEmpty()) {
                    ++var6;
                }
            }

            double var10 = 0.0D;
            if (var5 != 0) {
                var10 = (double)((var5 - var4.size()) / var5 * 100);
            } else {
                if (var6 == 0) {
                    return var4;
                }

                if (var6 != 0) {
                    return null;
                }
            }

            if (var10 > var2) {
                return null;
            } else {
                return var4;
            }
        }
    }

    public void reEvaluateNearEquivalences(Theory var1, double var2) {
        SortableVector var4 = new SortableVector();

        for(int var5 = 0; var5 < this.near_equivalences.size(); ++var5) {
            NearEquivalence var6 = (NearEquivalence)this.near_equivalences.elementAt(var5);
            Vector var7 = new Vector();
            double var8 = this.datatable.quickPercentageMatchWith(var1, var6.rh_concept.datatable, var7);
            if (var8 >= var2) {
                NearEquivalence var10 = new NearEquivalence(this, var6.rh_concept, var7, var8);
                var4.addElement(var10, "score");
            }
        }

        this.near_equivalences = var4;
    }

    public void reEvaluateNearImplications(Theory var1, double var2) {
        SortableVector var4 = new SortableVector();

        for(int var5 = 0; var5 < this.near_implications.size(); ++var5) {
            NearImplication var6 = (NearImplication)this.near_implications.elementAt(var5);
            Vector var7 = new Vector();
            double var8 = this.datatable.quickPercentageMatchWith(var1, var6.rh_concept.datatable, var7);
            if (var8 >= var2) {
                NearImplication var10 = new NearImplication(this, var6.rh_concept, var7, var8);
                var4.addElement(var10, "score");
            }
        }

        this.near_implications = var4;
    }

    public Vector empiricallySubsumes(Concept var1, int var2) {
        if (this.arity != var1.arity) {
            return null;
        } else if (!this.types.toString().equals(var1.types.toString())) {
            return null;
        } else {
            Vector var3 = new Vector();

            for(int var4 = 0; var4 < this.datatable.size() && var3.size() <= var2; ++var4) {
                Row var5 = (Row)this.datatable.elementAt(var4);
                Row var6 = (Row)var1.datatable.elementAt(var4);
                if (!var5.tuples.subsumes(var6.tuples)) {
                    var3.addElement(var5.entity);
                }
            }

            return var3.size() > var2 ? null : var3;
        }
    }

    public String productionRuleUsedName() {
        if (this.is_user_given) {
            return "user given";
        } else {
            ProductionRule var1 = (ProductionRule)this.construction.elementAt(1);
            return var1.getName();
        }
    }

    public Vector getSubsumptionImplications(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);
            if (!var4.is_entity_instantiations) {
                Vector var5;
                Implication var6;
                if (var4.isGeneralisationOf(this) == -1) {
                    var5 = var4.empiricallySubsumes(this, 0);
                    if (var5 != null && var5.size() == 0) {
                        var6 = new Implication();
                        var6.lh_concept = this;
                        var6.rh_concept = var4;
                        var2.addElement(var6);
                    }
                }

                if (this.isGeneralisationOf(var4) == -1) {
                    var5 = this.empiricallySubsumes(var4, 0);
                    if (var5 != null && var5.size() == 0) {
                        var6 = new Implication();
                        var6.lh_concept = var4;
                        var6.rh_concept = this;
                        var2.addElement(var6);
                    }
                }
            }
        }

        return var2;
    }

    public void recalculateSpecifications(Theory var1, boolean var2) {
        ProductionRule var3 = this.construction.productionRule();
        Vector var4 = this.construction.parameters();
        this.specifications = var3.newSpecifications(this.parents, var4, var1, new Vector());
        if (var2) {
            for(int var5 = 0; var5 < this.children.size(); ++var5) {
                Concept var6 = (Concept)this.children.elementAt(var5);
                var6.recalculateSpecifications(var1, true);
            }
        }

    }

    public Concept getSubstitutableConcept() {
        return this.use_top_substitutable && !this.substitutable_concepts.isEmpty() ? (Concept)this.substitutable_concepts.elementAt(0) : this;
    }

    public Vector getAncestors(Theory var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < this.ancestor_ids.size(); ++var3) {
            var2.addElement(var1.getConcept((String)this.ancestor_ids.elementAt(var3)));
        }

        return var2;
    }

    public String writeAncestorsAndEquivalents(String var1) {
        String var2 = "";

        for(int var3 = 0; var3 < this.ancestors.size(); ++var3) {
            Concept var4 = (Concept)this.ancestors.elementAt(var3);
            var2 = var2 + "--\n";
            var2 = var2 + var4.id + ". " + var4.writeDefinitionWithStartLetters(var1) + "\n";
            if (var4.conjectured_equivalent_concepts.size() > 0) {
                var2 = var2 + "Alternative definitions:\n";
            }

            for(int var5 = 0; var5 < var4.conjectured_equivalent_concepts.size(); ++var5) {
                Concept var6 = (Concept)var4.conjectured_equivalent_concepts.elementAt(var5);
                var2 = var2 + var6.id + ". " + var6.writeDefinitionWithStartLetters(var1) + "\n";
            }
        }

        return var2;
    }

    public void getRelevantEquivalences(Theory var1) {
        this.relevant_equivalences = new SortableVector();
        Vector var2 = new Vector();
        Concept var3 = this;

        int var4;
        Concept var5;
        for(var4 = 0; var4 < var1.concepts.size(); ++var4) {
            var5 = (Concept)var1.concepts.elementAt(var4);
            Vector var6 = (Vector)var5.conjectured_equivalent_concepts.clone();
            var6.insertElementAt(var5, 0);
            boolean var7 = false;
            new Vector();

            int var9;
            Concept var10;
            for(var9 = 0; var9 < var6.size(); ++var9) {
                var10 = (Concept)var6.elementAt(var9);
                if (var1.specification_handler.leftSkolemisedImpliesRight(var3, var10, true) || var1.specification_handler.leftSkolemisedImpliesRight(var10, var3, true)) {
                    var7 = true;
                    break;
                }
            }

            if (var7) {
                Vector var8 = new Vector();

                for(var9 = 1; var9 < var6.size(); ++var9) {
                    var10 = (Concept)var6.elementAt(var9);
                    Equivalence var11 = new Equivalence(var5, var10, "");
                    var8.addElement(var11);
                    String var12 = var11.writeConjecture("prolog");
                    if (!var2.contains(var12)) {
                        var2.addElement(var12);
                        this.relevant_equivalences.addElement(var11);
                    }
                }
            }
        }

        for(var4 = 0; var4 < this.conjectured_equivalent_concepts.size(); ++var4) {
            var5 = (Concept)this.conjectured_equivalent_concepts.elementAt(var4);
            var5.getRelevantEquivalences(var1);

            for(int var13 = 0; var13 < var5.relevant_equivalences.size(); ++var13) {
                Equivalence var14 = (Equivalence)var5.relevant_equivalences.elementAt(var13);
                String var15 = var14.writeConjecture("prolog");
                if (!var2.contains(var15)) {
                    var2.addElement(var15);
                    this.relevant_equivalences.addElement(var14);
                }
            }
        }

    }

    public void getRelevantImplicates(Theory var1) {
        this.relevant_implicates = new SortableVector();
        Vector var2 = var1.implicates;
        new Vector();
        SortableVector var4 = new SortableVector();
        Vector var5 = (Vector)this.conjectured_equivalent_concepts.clone();
        var5.insertElementAt(this, 0);
        int var6 = var5.size();

        int var7;
        Concept var8;
        int var9;
        for(var7 = 0; var7 < var6; ++var7) {
            var8 = (Concept)var5.elementAt(var7);

            for(var9 = 0; var9 < var8.ancestors.size(); ++var9) {
                var5.addElement(var8.ancestors.elementAt(var9));
            }
        }

        for(var7 = 0; var7 < var5.size(); ++var7) {
            var8 = (Concept)var5.elementAt(var7);

            for(var9 = 0; var9 < var2.size(); ++var9) {
                Implicate var10 = (Implicate)var2.elementAt(var9);
                if (var1.specification_handler.leftSkolemisedImpliesRight(var8, var10.premise_concept, true)) {
                    String var11 = var10.writeConjecture("prolog");
                    boolean var12 = false;

                    for(int var13 = 0; var13 < var4.size(); ++var13) {
                        Implicate var14 = (Implicate)var4.elementAt(var13);
                        if (var14.subsumes(var10)) {
                            var12 = true;
                        }

                        if (var10.subsumes(var14)) {
                            var4.removeElementAt(var13);
                            --var13;
                        }
                    }

                    if (!var12) {
                        var4.addElement(var10, "writeConjecture(prolog)");
                    }

                    var2.removeElementAt(var9);
                    --var9;
                }
            }
        }

        for(var7 = 0; var7 < var4.size(); ++var7) {
            Implicate var15 = (Implicate)var4.elementAt(var7);
            this.relevant_implicates.addElement(var15);
        }

    }

    public Vector sharedSpecifications(Concept var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < this.specifications.size(); ++var3) {
            Specification var4 = (Specification)this.specifications.elementAt(var3);
            boolean var5 = false;

            for(int var6 = 0; var6 < var1.specifications.size() && !var5; ++var6) {
                Specification var7 = (Specification)var1.specifications.elementAt(var6);
                if (var7 == var4) {
                    var2.addElement(var4);
                    var5 = true;
                }
            }
        }

        return var2;
    }

    public Vector positives() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.datatable.size(); ++var2) {
            Row var3 = (Row)this.datatable.elementAt(var2);
            if (!var3.tuples.isEmpty()) {
                var1.addElement(var3.entity);
            }
        }

        return var1;
    }

    public Vector positiveEntities() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.datatable.size(); ++var2) {
            Row var3 = (Row)this.datatable.elementAt(var2);
            if (!var3.tuples.isEmpty()) {
                Entity var4 = new Entity(var3.entity);
                var1.addElement(var4);
            }
        }

        return var1;
    }

    public Vector negatives() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.datatable.size(); ++var2) {
            Row var3 = (Row)this.datatable.elementAt(var2);
            if (var3.tuples.isEmpty()) {
                var1.addElement(var3.entity);
            }
        }

        return var1;
    }

    public Vector getPositives() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.datatable.size(); ++var2) {
            Row var3 = (Row)this.datatable.elementAt(var2);
            if (!var3.tuples.isEmpty()) {
                var1.addElement(var3.entity);
            }
        }

        return var1;
    }

    public Vector getSubset(Vector var1) {
        Vector var2 = new Vector();
        Vector var3 = this.getPositives();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            String var5 = (String)var1.elementAt(var4);
            if (var3.contains(var5)) {
                var2.addElement(var5);
            }
        }

        return var2;
    }

    public boolean sameValueOfEntity(Row var1) {
        boolean var2 = false;
        int var3 = 0;
        new Row();

        while(var3 < this.datatable.size()) {
            Row var4 = (Row)this.datatable.elementAt(var3);
            if (var4.equals(var1)) {
                var2 = true;
                break;
            }

            ++var3;
        }

        return var2;
    }

    public boolean positivesContains(Vector var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            String var3 = (String)var1.elementAt(var2);
            Row var4 = this.datatable.rowWithEntity(var3);
            if (var4.tuples.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean positivesContains(String var1) {
        Row var2 = this.datatable.rowWithEntity(var1);
        return !var2.tuples.isEmpty();
    }

    public boolean positivesContainOneOf(Vector var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            String var3 = (String)var1.elementAt(var2);
            Row var4 = this.datatable.rowWithEntity(var3);
            if (!var4.tuples.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public Vector sharedEntities(Concept var1) {
        Vector var2 = this.positives();
        Vector var3 = var1.positives();
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var2.size(); ++var5) {
            String var6 = (String)var2.elementAt(var5);
            if (var3.contains(var6)) {
                var4.addElement(var6);
            }
        }

        return var4;
    }

    public String writeSkolemisedRepresentation() {
        String var1 = this.skolemised_representation.relation_columns.toString();
        return var1;
    }

    public void setSkolemisedRepresentation() {
        for(int var1 = 0; var1 < this.specifications.size(); ++var1) {
            Specification var2 = (Specification)this.specifications.elementAt(var1);
            Vector var3 = var2.skolemisedRepresentation();

            for(int var4 = 0; var4 < var3.size(); ++var4) {
                Vector var5 = (Vector)var3.elementAt(var4);
                this.skolemised_representation.relation_columns.addElement(var5);
                String var6 = (String)var5.elementAt(0);
                Vector var7 = (Vector)var5.elementAt(1);

                for(int var8 = 0; var8 < var7.size(); ++var8) {
                    String var9 = (String)var7.elementAt(var8);
                    if (var9.substring(0, 1).equals(":")) {
                        if (!this.skolemised_representation.ground_variables.contains(var9)) {
                            this.skolemised_representation.ground_variables.addElement(var9, "toString()");
                        }
                    } else if (!this.skolemised_representation.variables.contains(var9)) {
                        this.skolemised_representation.variables.addElement(var9);
                    }

                    String var10 = var6 + "(" + Integer.toString(var8) + ")";
                    Vector var11 = (Vector)this.skolemised_representation.relation_position_hashtable.get(var10);
                    if (var11 == null) {
                        var11 = new Vector();
                        this.skolemised_representation.relation_position_hashtable.put(var10, var11);
                    }

                    if (!var11.contains(var9)) {
                        var11.addElement(var9);
                    }

                    Vector var12 = (Vector)this.skolemised_representation.variable_relation_position_hashtable.get(var9);
                    if (var12 == null) {
                        var12 = new Vector();
                        this.skolemised_representation.variable_relation_position_hashtable.put(var9, var12);
                    }

                    if (!var12.contains(var10)) {
                        var12.addElement(var10);
                    }
                }

                Vector var13 = (Vector)this.skolemised_representation.relation_hashtable.get(var6);
                if (var13 == null) {
                    var13 = new Vector();
                    this.skolemised_representation.relation_hashtable.put(var6, var13);
                }

                if (!var13.contains(var7)) {
                    var13.addElement(var7);
                }
            }
        }

    }

    public Vector getConjectures(Theory var1, Concept var2) {
        Vector var3 = new Vector();
        double var6;
        if (var2.types.toString().equals(this.types.toString()) && var2 != this) {
            new Vector();
            Vector var5 = new Vector();
            var6 = this.datatable.quickPercentageMatchWith(var1, var2.datatable, var5);
            NearEquivalence var8 = new NearEquivalence(this, var2, var5, var6);
            new NearEquivalence(var2, this, var5, var6);
            var3.addElement(var8);
        }

        if (var2.arity == this.arity) {
            double var4 = 0.0D;
            var6 = 0.0D;
            if (!var2.is_entity_instantiations) {
                boolean var9;
                NearImplication var10;
                Vector var11;
                int var13;
                if (var2.isGeneralisationOf(this) == -1) {
                    var11 = var2.empiricallySubsumes(this, var6);
                    if (var11 != null && var11.size() != 0) {
                        var9 = false;
                        if (this.arity != 0 && this.arity != 1) {
                            var13 = this.datatable.size();
                        } else {
                            var13 = this.getPositives().size();
                        }

                        var4 = (double)(var13 - var11.size());
                        var4 /= (double)var13;
                        var4 *= 100.0D;
                        if (var4 >= var6) {
                            var10 = new NearImplication(this, var2, var11, var4);
                            var3.addElement(var10);
                        }
                    }
                }

                if (this.isGeneralisationOf(var2) == -1) {
                    var11 = this.empiricallySubsumes(var2, var6);
                    if (var11 != null && var11.size() != 0) {
                        var9 = false;
                        if (var2.arity != 0 && var2.arity != 1) {
                            var13 = var2.datatable.size();
                        } else {
                            var13 = var2.getPositives().size();
                        }

                        var4 = (double)(var13 - var11.size());
                        var4 /= (double)var13;
                        var4 *= 100.0D;
                        if (var4 >= var6) {
                            var10 = new NearImplication(var2, this, var11, var4);
                            var3.addElement(var10);
                        }
                    }
                }

                Implication var12;
                if (var2.isGeneralisationOf(this) == 0) {
                    var12 = new Implication(this, var2, "dummy_id");
                    var3.addElement(var12);
                }

                if (this.isGeneralisationOf(var2) == 0) {
                    var12 = new Implication(var2, this, "dummy_id");
                    var3.addElement(var12);
                }

                if (var2.isGeneralisationOf(this) == 1) {
                    Equivalence var14 = new Equivalence(var2, this, "dummy_id");
                    var3.addElement(var14);
                }
            }
        }

        return var3;
    }

    public TheoryConstituent reconstructConcept_old(Theory var1, AgentWindow var2) {
        var2.writeToFrontEnd("started reconstructConcept(" + this.writeDefinition() + ")");
        Vector var3 = this.construction_history;
        var2.writeToFrontEnd("steps_to_force are " + var3);
        Vector var4 = new Vector();
        String var5 = "";
        if (this.construction_history.isEmpty()) {
            return this;
        } else {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                Vector var7 = (Vector)var3.elementAt(var6);
                String var8 = (String)var7.elementAt(0);
                String var9 = (String)var7.elementAt(1);
                Vector var10 = (Vector)var7.elementAt(2);
                Vector var11 = (Vector)var7.elementAt(3);
                String var12 = "";

                for(int var13 = 0; var13 < var10.size(); ++var13) {
                    var12 = var12 + (String)var10.elementAt(var13) + " ";
                }

                String var20 = var11.toString();
                String var14 = new String();
                int var15 = var20.length();
                int var16 = 0;

                try {
                    while(0 <= var16 && var16 < var15) {
                        var16 = var20.indexOf("], [");
                        var14 = var14 + var20.substring(0, var16) + "],[";
                        var20 = var20.substring(var16 + 4, var15);
                        var15 = var20.length();
                    }

                    var14 = var14 + var20;
                } catch (Exception var18) {
                    var14 = var14 + var20;
                }

                var5 = var8 + " = " + var12 + var9 + " " + var14.trim();
                var4.addElement(var5.trim());
            }

            var2.writeToFrontEnd("step_strings are " + var4);
            TheoryConstituent var19 = var1.guider.forceSteps(var4, var1);
            var2.writeToFrontEnd("finished reconstructConcept(" + this.writeDefinition() + ")");
            if (!(var19 instanceof Concept)) {
                var2.writeToFrontEnd("returning " + var19);
            }

            if (var19 instanceof Concept) {
                var2.writeToFrontEnd("returning " + ((Concept)var19).writeDefinition());
            }

            return var19;
        }
    }

    public TheoryConstituent reconstructConcept(Theory var1, AgentWindow var2) {
        var2.writeToFrontEnd("started reconstructConcept(" + this.writeDefinition() + ")");
        Vector var3 = this.construction_history;
        Vector var4 = new Vector();
        String var5 = "";
        if (this.construction_history.isEmpty()) {
            return this;
        } else {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                Vector var7 = (Vector)var3.elementAt(var6);
                String var8 = (String)var7.elementAt(0);
                String var9 = (String)var7.elementAt(1);
                Vector var10 = (Vector)var7.elementAt(2);
                Vector var11 = (Vector)var7.elementAt(3);
                String var12 = "";

                for(int var13 = 0; var13 < var10.size(); ++var13) {
                    var12 = var12 + (String)var10.elementAt(var13) + " ";
                }

                String var25 = var11.toString();
                String var14 = new String();
                int var15 = var25.length();
                int var16 = 0;

                try {
                    while(0 <= var16 && var16 < var15) {
                        var16 = var25.indexOf("], [");
                        var14 = var14 + var25.substring(0, var16) + "],[";
                        var25 = var25.substring(var16 + 4, var15);
                        var15 = var25.length();
                    }

                    var14 = var14 + var25;
                } catch (Exception var18) {
                    var14 = var14 + var25;
                }

                var5 = var8 + " = " + var12 + var9 + " " + var14.trim();
                var4.addElement(var5.trim());
            }

            var2.writeToFrontEnd("step_strings are " + var4);
            TheoryConstituent var19 = var1.guider.forceSteps(var4, var1);
            var2.writeToFrontEnd("here -- so far reconstructConcept gives " + var19.writeTheoryConstituent());
            if (var19 instanceof Concept) {
                if (((Concept)var19).writeDefinition().equals(this.writeDefinition())) {
                    return (Concept)var19;
                }
            } else {
                for(int var20 = 0; var20 < var1.conjectures.size(); ++var20) {
                    Conjecture var21 = (Conjecture)var1.conjectures.elementAt(var20);
                    if (var21 instanceof Equivalence) {
                        Equivalence var22 = (Equivalence)var21;
                        if (var22.lh_concept.writeDefinition().equals(this.writeDefinition())) {
                            return var22.lh_concept;
                        }

                        if (var22.rh_concept.writeDefinition().equals(this.writeDefinition())) {
                            return var22.rh_concept;
                        }
                    }

                    if (var21 instanceof Implication) {
                        Implication var23 = (Implication)var21;
                        if (var23.lh_concept.writeDefinition().equals(this.writeDefinition())) {
                            return var23.lh_concept;
                        }

                        if (var23.rh_concept.writeDefinition().equals(this.writeDefinition())) {
                            return var23.rh_concept;
                        }
                    }

                    if (var21 instanceof NonExists) {
                        NonExists var24 = (NonExists)var21;
                        if (var24.concept.writeDefinition().equals(this.writeDefinition())) {
                            return var24.concept;
                        }
                    }
                }
            }

            return var19;
        }
    }

    public boolean equals(Object var1) {
        String var2 = this.writeDefinition("ascii");
        if (var1 instanceof String) {
            if (((String)var1).equals("") && var2.equals("")) {
                return true;
            }

            if (((String)var1).equals(var2)) {
                return true;
            }
        }

        if (var1 instanceof Concept) {
            Concept var3 = (Concept)var1;
            String var4 = var3.writeDefinition("ascii");
            if (var4.equals("") && var2.equals("")) {
                return true;
            }

            if (var4.equals(var2)) {
                return true;
            }
        } else {
            System.out.println("Warning: Concept equals method - not a string or concept");
        }

        return false;
    }

    public Vector nonLemConcept() {
        Vector var1 = new Vector();
        Vector var2 = new Vector();
        Vector var3 = new Vector();
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < this.datatable.size(); ++var5) {
            Row var6 = (Row)this.datatable.elementAt(var5);
            String var7 = var6.tuples.toString();
            if (var6.tuples.isEmpty()) {
                var2.addElement(var6.entity);
            }

            if (!var6.tuples.isEmpty()) {
                if (var7.equals("[[]]")) {
                    var3.addElement(var6.entity);
                }

                if (!var7.equals("[[]]")) {
                    var4.addElement(var6.entity);
                }
            }
        }

        var1.addElement(var2);
        var1.addElement(var3);
        var1.addElement(var4);
        return var1;
    }

    public Concept findObjectOfInterestConcept(Vector var1) {
        byte var2 = 0;
        if (var2 < var1.size()) {
            Concept var3 = (Concept)var1.elementAt(var2);
            if (var3.is_object_of_interest_concept && var3.domain.equals(this.domain)) {
                return var3;
            }
        }

        Concept var4 = new Concept();
        return var4;
    }

    public boolean theoryContainsConcept(Vector var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            Concept var3 = (Concept)var1.elementAt(var2);
            if (this.writeDefinition().equals(var3.writeDefinition())) {
                return true;
            }
        }

        return false;
    }
}
