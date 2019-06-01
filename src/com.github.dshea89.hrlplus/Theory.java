package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Vector;

public class Theory implements ActionListener, ItemListener, FocusListener, KeyListener, Serializable {
    public boolean use_surrender = false;
    public boolean use_strategic_withdrawal = false;
    public boolean use_piecemeal_exclusion = false;
    public boolean use_monster_barring = false;
    public boolean use_monster_adjusting = false;
    public boolean use_lemma_incorporation = false;
    public boolean use_proofs_and_refutations = false;
    public boolean use_communal_piecemeal_exclusion = false;
    public boolean make_subsumption_implicates = false;
    public boolean require_proof_in_subsumption = true;
    public boolean skip_equivalence_conjectures = false;
    public Vector storage_vector1 = new Vector();
    public Vector storage_vector2 = new Vector();
    public Categorisation gold_standard_categorisation = new Categorisation();
    public Vector positives_for_learning = new Vector();
    public Vector negatives_for_learning = new Vector();
    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();
    public volatile int step_counter = 0;
    public TextHandler text_handler = new TextHandler();
    public StorageHandler storage_handler = new StorageHandler();
    public boolean check_for_gold_standard = false;
    public SpecificationHandler specification_handler = new SpecificationHandler();
    public Vector previous_steps = new Vector();
    public Reflection reflect = new Reflection();
    public TheoryFormationThread runner = new TheoryFormationThread();
    public String input_files_directory = "";
    public Maths maths = new Maths();
    public ReactionHandler react = new ReactionHandler();
    public Lakatos lakatos = new Lakatos();
    public GroupAgenda group_agenda = new GroupAgenda();
    public Vector command_line_arguments = new Vector();
    public Vector counterexamples_found_from_testing = new Vector();
    public Conjecture conjecture_chosen_from_front_end = null;
    public boolean allow_substitutions = false;
    public boolean use_applicability_conj = false;
    public int max_applicability_conj_number = 0;
    public boolean use_counterexample_barring = false;
    public boolean use_concept_barring = false;
    public int max_counterexample_bar_number = 0;
    public int max_concept_bar_number = 0;
    public StatisticsHandler statistics_handler = new StatisticsHandler();
    public GraphHandler graph_handler = new GraphHandler();
    public Debugger debugger = new Debugger();
    public Guide guider = new Guide();
    public ReportGenerator report_generator = new ReportGenerator();
    public MathWebHandler mathweb_handler = null;
    public SOTHandler sot_handler = new SOTHandler();
    public String macro_to_complete = "";
    public boolean is_running_silent = false;
    public MacroHandler macro_handler = new MacroHandler();
    boolean exit_after_macro = false;
    public Predict predict = new Predict();
    public Hashtable datatable_hashtable = new Hashtable();
    public HoldBackChecker hold_back_checker = new HoldBackChecker();
    public Vector domain_concepts = new Vector();
    public UserFunctions user_functions = new UserFunctions();
    public Read reader = new Read();
    public String operating_system = "windows";
    public Vector learned_concepts = new Vector();
    public Vector required_flat_categorisations = new Vector();
    public Vector required_categorisations = new Vector();
    public Timer timer = new Timer();
    public ExplanationHandler explanation_handler = new ExplanationHandler();
    public boolean extract_implications_from_equivalences = false;
    public boolean extract_implicates_from_non_exists = false;
    public boolean extract_implicates_from_equivalences = false;
    public boolean extract_prime_implicates_from_implicates = false;
    public boolean make_subsumption_implications = false;
    public boolean make_equivalences_from_equality = false;
    public boolean make_equivalences_from_combination = false;
    public boolean make_non_exists_from_empty = false;
    public boolean keep_steps = false;
    public boolean keep_equivalences = false;
    public boolean keep_non_existences = false;
    public boolean keep_implications = false;
    public boolean keep_implicates = false;
    public boolean keep_prime_implicates = false;
    public boolean teacher_requests_conjectures = true;
    public boolean teacher_requests_nonexists = false;
    public boolean teacher_requests_implication = false;
    public boolean teacher_requests_nearimplication = false;
    public boolean teacher_requests_equivalence = false;
    public boolean teacher_requests_nearequivalence = false;
    public boolean make_near_equivalences = false;
    public double near_equiv_percent = 0.0D;
    public boolean make_near_implications = false;
    public double near_imp_percent = 0.0D;
    public int complexity_limit = 1000;
    public int agenda_size_limit = 10000;
    public int tuple_number_limit = 1000;
    public int gc_when = 100000;
    public int steps_taken = 0;
    public boolean depth_first = true;
    public boolean random = true;
    public Agenda agenda = new Agenda();
    public MeasureConcept measure_concept = new MeasureConcept();
    public MeasureConjecture measure_conjecture = new MeasureConjecture();
    public Vector production_rules = new Vector();
    public Vector all_production_rules = new Vector();
    public Vector binary_rules = new Vector();
    public Vector unary_rules = new Vector();
    public Disjunct disjunct = new Disjunct();
    public Arithmeticb arithmeticb = new Arithmeticb();
    public Compose compose = new Compose();
    public EmbedGraph embed_graph = new EmbedGraph();
    public EmbedAlgebra embed_algebra = new EmbedAlgebra();
    public EntityDisjunct entity_disjunct = new EntityDisjunct();
    public Exists exists = new Exists();
    public Equal equal = new Equal();
    public Forall forall = new Forall();
    public Match match = new Match();
    public Negate negate = new Negate();
    public Record record = new Record();
    public Size size = new Size();
    public Split split = new Split();
    public Vector concepts = new Vector();
    public Vector specialisation_concepts = new Vector();
    public Vector conjectures = new Vector();
    public Vector near_equivalences = new Vector();
    public Vector near_implications = new Vector();
    public Vector equivalences = new Vector();
    public Vector non_existences = new Vector();
    public Vector prime_implicates = new Vector();
    public Vector implicates = new Vector();
    public Vector implications = new Vector();
    public Vector relations = new Vector();
    public Vector specifications = new Vector();
    public Vector categorisations = new Vector();
    public Hashtable flat_categorisations = new Hashtable();
    public Vector entities = new Vector();
    public Vector pseudo_entities = new Vector();
    public Vector pseudo_conjectures = new Vector();
    public Vector entity_names = new Vector();
    public FrontEnd front_end = new FrontEnd();
    public boolean use_front_end = true;
    public boolean stop_now = false;
    public String forced_id = "";
    public Vector entities_added = new Vector();
    public long run_start_time = 0L;
    public int seconds_elapsed = 0;
    public long time_already_run = 0L;
    public int max_step_counter = 1000;
    PuzzleGenerator puzzler = new PuzzleGenerator();

    public Theory() {
    }

    public void passOnSettings() {
        this.explanation_handler.storage_handler = this.storage_handler;
        this.explanation_handler.hold_back_checker = this.hold_back_checker;
        this.pseudo_code_interpreter.input_text_box = this.front_end.pseudo_code_input_text;
        this.pseudo_code_interpreter.output_text_box = this.front_end.pseudo_code_output_text;
        this.storage_handler.guider = this.guider;
        this.storage_handler.reflect = this.reflect;
        this.front_end.os = this.operating_system;
        this.production_rules.addElement(this.arithmeticb);
        this.production_rules.addElement(this.exists);
        this.production_rules.addElement(this.entity_disjunct);
        this.production_rules.addElement(this.equal);
        this.production_rules.addElement(this.embed_algebra);
        this.production_rules.addElement(this.embed_graph);
        this.production_rules.addElement(this.forall);
        this.production_rules.addElement(this.match);
        this.production_rules.addElement(this.size);
        this.production_rules.addElement(this.compose);
        this.production_rules.addElement(this.disjunct);
        this.production_rules.addElement(this.negate);
        this.production_rules.addElement(this.record);
        this.production_rules.addElement(this.split);
        this.forall.compose = this.compose;
        this.forall.exists = this.exists;
        this.binary_rules.addElement(this.arithmeticb);
        this.binary_rules.addElement(this.compose);
        this.binary_rules.addElement(this.disjunct);
        this.binary_rules.addElement(this.embed_graph);
        this.binary_rules.addElement(this.negate);
        this.binary_rules.addElement(this.forall);
        this.unary_rules.addElement(this.exists);
        this.unary_rules.addElement(this.entity_disjunct);
        this.unary_rules.addElement(this.equal);
        this.binary_rules.addElement(this.embed_algebra);
        this.unary_rules.addElement(this.match);
        this.unary_rules.addElement(this.size);
        this.unary_rules.addElement(this.split);
        this.unary_rules.addElement(this.record);
        this.macro_handler.front_end = this.front_end;
        this.macro_handler.addListeners(this);
        this.front_end.reader = this.reader;
        this.front_end.agenda = this.agenda;
        this.front_end.concepts = this.concepts;
        this.front_end.conjectures = this.conjectures;
        this.front_end.equivalences = this.equivalences;
        this.front_end.non_existences = this.non_existences;
        this.front_end.implicates = this.implicates;
        this.front_end.timer = this.timer;
        this.front_end.entities = this.entities;
        this.front_end.getMacroFiles();
        this.reader.user_functions = this.user_functions;
        this.explanation_handler.operating_system = this.operating_system;
        this.embed_algebra.mace.operating_system = this.operating_system;
        this.report_generator.report_output_text = this.front_end.report_output_text;
        this.report_generator.command_line_arguments = this.command_line_arguments;
        this.lakatos.agenda = this.agenda;
        this.lakatos.concepts = this.concepts;
        this.lakatos.equivalences = this.equivalences;
        this.lakatos.non_existences = this.non_existences;
        this.lakatos.implicates = this.implicates;
        this.lakatos.implications = this.implications;
        this.lakatos.entities = this.entities;
        this.lakatos.production_rules = this.production_rules;
        this.lakatos.front_end = this.front_end;
        this.lakatos.guider = this.guider;
        this.embed_algebra.input_files_directory = this.input_files_directory;
        this.report_generator.input_files_directory = this.input_files_directory;
        this.specification_handler.specifications = this.specifications;
        this.specification_handler.concepts = this.concepts;
        this.specification_handler.non_existences = this.non_existences;
        this.reflect.condition_shorthands.put("cross domain", "is_cross_domain == true");
        this.reflect.condition_shorthands.put("integer output", "types.size() == 2 && types.elementAt(1) == integer");
        this.reflect.condition_shorthands.put("specialisation", "types.size() == 1");
        this.reflect.condition_shorthands.put("element type", "types.size() == 2 && types.elementAt(1) == element");
        this.reflect.condition_shorthands.put("learned", "has_required_categorisation == true");
        this.reflect.condition_shorthands.put("arithmeticb", "productionRuleUsedName() == arithmeticb");
        this.reflect.condition_shorthands.put("compose", "productionRuleUsedName() == compose");
        this.reflect.condition_shorthands.put("disjunct", "productionRuleUsedName() == disjunct");
        this.reflect.condition_shorthands.put("embed_algebra", "productionRuleUsedName() == embed_algebra");
        this.reflect.condition_shorthands.put("embed_graph", "productionRuleUsedName() == embed_graph");
        this.reflect.condition_shorthands.put("equal", "productionRuleUsedName() == equal");
        this.reflect.condition_shorthands.put("exists", "productionRuleUsedName() == exists");
        this.reflect.condition_shorthands.put("entity_disjunct", "productionRuleUsedName() == entity_disjunct");
        this.reflect.condition_shorthands.put("forall", "productionRuleUsedName() == forall");
        this.reflect.condition_shorthands.put("concept_forced", "rh_concept.construction.forced == true");
        this.reflect.condition_shorthands.put("forced", "construction.forced == true");
        this.reflect.condition_shorthands.put("match", "productionRuleUsedName() == match");
        this.reflect.condition_shorthands.put("negate", "productionRuleUsedName() == negate");
        this.reflect.condition_shorthands.put("record", "productionRuleUsedName() == record");
        this.reflect.condition_shorthands.put("size", "productionRuleUsedName() == size");
        this.reflect.condition_shorthands.put("split", "productionRuleUsedName() == split");
        this.reflect.condition_shorthands.put("segregated", "involves_segregated_concepts == true");
        this.reflect.condition_shorthands.put("prime implicate", "is_prime_implicate == true");
        this.reflect.condition_shorthands.put("instantiation", "involves_instantiation == true");
        this.reflect.condition_shorthands.put("not_instantiation", "involves_instantiation == false");
        this.reflect.condition_shorthands.put("axiom", "proof_status == axiom");
        this.reflect.condition_shorthands.put("equivalence", "object_type == Equivalence");
        this.reflect.condition_shorthands.put("forced", "");
        this.reflect.condition_shorthands.put("implicate", "object_type == Implicate");
        this.reflect.condition_shorthands.put("near_equiv", "object_type == NearEquivalence");
        this.reflect.condition_shorthands.put("near_imp", "object_type == NearImplication");
        this.reflect.condition_shorthands.put("implication", "object_type == Implication");
        this.reflect.condition_shorthands.put("non-exists", "object_type == NonExists");
        this.reflect.condition_shorthands.put("not prime implicate", "is_prime_implicate == false");
        this.reflect.condition_shorthands.put("proved", "proof_status == proved");
        this.reflect.condition_shorthands.put("undetermined", "proof_status != proved && proof_status != disproved");
        this.reflect.condition_shorthands.put("disproved", "proof_status == disproved");
        this.reflect.condition_shorthands.put("sos", "proof_status == sos");
        this.reflect.condition_shorthands.put("time", "proof_status == time");
        this.reflect.condition_shorthands.put("syntax error", "proof_status == syntax error");
        this.reflect.value_shorthands.put("devel steps", "development_steps_num");
    }

    public void setSubobjectOverlap(boolean var1) {
        this.compose.subobject_overlap = var1;
    }

    public void setComplexityLimit(int var1) {
        this.complexity_limit = var1;
        this.agenda.complexity_limit = var1;
    }

    public void setAgendaSizeLimit(int var1) {
        this.agenda_size_limit = var1;
        this.agenda.agenda_size_limit = var1;
    }

    public void setDepthFirst(boolean var1) {
        this.depth_first = var1;
        this.agenda.depth_first = var1;
    }

    public void setRandom(boolean var1) {
        this.random = var1;
        this.agenda.random = var1;
    }

    public ProductionRule productionRuleFromName(String var1) {
        int var2;
        for(var2 = 0; var2 < this.all_production_rules.size() && !((ProductionRule)this.all_production_rules.elementAt(var2)).getName().equals(var1); ++var2) {
            ;
        }

        return (ProductionRule)this.all_production_rules.elementAt(var2);
    }

    public Concept getConcept(Vector var1) {
        this.specification_handler.addSpecifications(var1);
        boolean var3 = false;
        Concept var4 = null;

        for(int var5 = 0; var5 < this.concepts.size() && !var3; ++var5) {
            var4 = (Concept)this.concepts.elementAt(var5);
            boolean var6 = false;
            if (var4.specifications.size() != var1.size()) {
                var6 = true;
            }

            for(int var7 = 0; var7 < var4.specifications.size() && !var6; ++var7) {
                Specification var8 = (Specification)var4.specifications.elementAt(var7);
                if (var8 != (Specification)var1.elementAt(var7)) {
                    var6 = true;
                }
            }

            if (!var6) {
                var3 = true;
            }
        }

        return var4;
    }

    public Concept getConcept(String var1) {
        int var2 = 0;
        boolean var3 = false;

        Concept var4;
        for(var4 = new Concept(); var2 < this.concepts.size() && !var3; ++var2) {
            var4 = (Concept)this.concepts.elementAt(var2);
            if (var4.id.equals(var1)) {
                var3 = true;
            }

            if (var4.alternative_ids.contains(var1)) {
                var3 = true;
            }
        }

        return var3 ? var4 : new Concept();
    }

    public Conjecture getConjecture(String var1) {
        int var2 = 0;
        boolean var3 = false;

        Conjecture var4;
        for(var4 = new Conjecture(); var2 < this.conjectures.size() && !var3; ++var2) {
            var4 = (Conjecture)this.conjectures.elementAt(var2);
            if (var4.id.equals(var1)) {
                var3 = true;
            }
        }

        return var3 ? var4 : new Conjecture();
    }

    public Concept conceptFromName(String var1) {
        int var2;
        for(var2 = 0; var2 < this.concepts.size() && !((Concept)this.concepts.elementAt(var2)).name.equals(var1); ++var2) {
            ;
        }

        return var2 < this.concepts.size() ? (Concept)this.concepts.elementAt(var2) : new Concept();
    }

    public int numberOfConcepts() {
        return this.concepts.size();
    }

    public void addToTimer(String var1) {
        this.timer.addTo(var1);
        this.status(var1);
    }

    public void status(String var1) {
        this.front_end.status_label.setText(var1);
    }

    private void reCalculateProductivities(Vector var1) {
        if (this.measure_concept.measure_concepts) {
            boolean var2 = false;

            for(int var3 = 0; var3 < var1.size(); ++var3) {
                Concept var4 = (Concept)var1.elementAt(var3);
                boolean var5 = this.measure_concept.updateNormalisedProductivities(var4, false);
                if (var5) {
                    var2 = true;
                }
            }

            if (var2 && this.measure_concept.productivity_weight != 0.0D) {
                this.agenda.orderConcepts();
            }

        }
    }

    public Vector addImplicates(Vector var1, Conjecture var2, String var3) {
        Vector var4 = new Vector();
        boolean var5 = false;

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            Implicate var7 = (Implicate)var1.elementAt(var6);
            Specification var8 = this.specification_handler.addSpecification(var7.goal);
            var7.goal = var8;
            var7.when_constructed = (long)this.seconds_elapsed;
            this.addToTimer(var3 + ".1 Checking whether an implicate has been seen already or a more general one has been seen");
            if (!this.explanation_handler.conjectureSeenAlready(var7) && this.explanation_handler.getSubsumingImplicate(var7, this, this.require_proof_in_subsumption) == null) {
                this.addToTimer(var3 + ".2 Adding implicate to the theory");
                this.explanation_handler.addConjecture(var7);
                Vector var9 = new Vector();
                var7.premise_concept.implicates.addElement(var7);
                if (var2.proof_status.equals("proved")) {
                    this.addToTimer(var3 + ".3 Dealing with a new implicate proved by its parent");
                    var7.proof_status = "proved";
                    var7.proof = "Proof of parent conjecture: \n" + var2.proof;
                    var7.explained_by = "Proof of parent";
                    var7.proof_length = var2.proof_length;
                    var7.proof_level = var2.proof_level;
                    if (var2.is_trivially_true) {
                        var7.is_trivially_true = true;
                        var7.explained_by = "being trivial";
                    }
                } else {
                    this.addToTimer(var3 + ".4 Attempting to settle a new implicate");
                    this.explanation_handler.explainConjecture(var7, this, var3 + ".4");
                }

                this.addToTimer(var3 + ".5 Handling the storage of a new implicate");
                int var10;
                if (var7.proof_status.equals("disproved")) {
                    for(var10 = 0; var10 < var7.counterexamples.size(); ++var10) {
                        var4.addElement(var7.counterexamples.elementAt(var10));
                    }
                }

                this.addToTimer(var3 + ".6 Extracting prime implicates");
                if (this.extract_prime_implicates_from_implicates) {
                    this.addToTimer(var3 + ".7 Generating possible prime implicates");
                    Vector var16 = var7.possiblePrimeImplicates();
                    var7.is_prime_implicate = true;

                    for(int var11 = 0; var11 < var16.size(); ++var11) {
                        Implicate var12 = (Implicate)var16.elementAt(var11);
                        var12.parent_conjectures.addElement(var2);
                        var12.parent_conjectures.addElement(var7);
                        var12.when_constructed = (long)this.seconds_elapsed;
                        var7.child_conjectures.addElement(var12);
                        this.addToTimer(var3 + ".8 Checking whether a PI candidate has been seen before");
                        boolean var13 = false;
                        String var14 = this.explanation_handler.previousResult(var12);
                        if (var14 == null) {
                            Implicate var15 = this.explanation_handler.getSubsumingImplicate(var12, this, this.require_proof_in_subsumption);
                            if (var15 != null) {
                                var13 = true;
                                var7.is_prime_implicate = false;
                                this.explanation_handler.addConjecture(var12);
                            }
                        } else {
                            var13 = true;
                            if (var14.equals("proved")) {
                                var7.is_prime_implicate = false;
                            }
                        }

                        if (!var13) {
                            this.addToTimer(var3 + ".9 Adding prime implicate candidate to the theory");
                            this.explanation_handler.addConjecture(var12);
                            this.addToTimer(var3 + ".10 Attempting to prove a possible prime implicate");
                            this.explanation_handler.explainConjecture(var12, this, var3 + ".10");
                            if (var12.proof_status.equals("disproved")) {
                                for(int var18 = 0; var18 < var7.counterexamples.size(); ++var18) {
                                    var4.addElement(var7.counterexamples.elementAt(var18));
                                }
                            }

                            if (var12.proof_status.equals("proved")) {
                                var12.is_prime_implicate = true;
                                var7.is_prime_implicate = false;
                                var9.addElement(var12);
                                var12.id = Integer.toString(this.numberOfConjectures());
                            }
                        }
                    }
                }

                if (var7.is_prime_implicate || !var7.is_prime_implicate && this.keep_implicates) {
                    var9.insertElementAt(var7, 0);
                }

                if (this.keep_prime_implicates || this.keep_implicates) {
                    this.addToTimer(var3 + ".11 Adding set of prime implicates to the theory");

                    for(var10 = 0; var10 < var9.size(); ++var10) {
                        Implicate var17 = (Implicate)var9.elementAt(var10);
                        var17.id = Integer.toString(this.numberOfConjectures());
                        var17.checkSegregation(this.agenda);
                        this.implicates.addElement(var17);
                        this.prime_implicates.addElement(var17);
                        this.conjectures.addElement(var17);
                        this.measure_conjecture.measureConjecture(var17, this.implicates);
                        this.updateFrontEnd((Conjecture)var17);
                        if (this.measure_concept.updateImpConjectureScore(var17)) {
                            var5 = true;
                        }
                    }
                }
            }
        }

        if (var5) {
            this.addToTimer(var3 + ".12 Reordering the agenda after adding an implicate");
            this.agenda.orderConcepts();
        }

        return var4;
    }

    public Step theoryFormationStep() {
        this.addToTimer("1.1 Initialisation of step");
        Concept var1 = new Concept();
        ++this.steps_taken;
        ++this.step_counter;
        this.agenda.step_number = this.steps_taken;
        this.seconds_elapsed = (new Long((System.currentTimeMillis() - this.run_start_time) / 1000L)).intValue();
        this.addToTimer("1.2 Garbage collection");
        Double var2 = new Double((double)this.steps_taken);
        double var3 = var2 / (double)this.gc_when;
        Runtime var5;
        if (var3 == Math.floor(var3)) {
            var5 = Runtime.getRuntime();
            var5.runFinalization();
            var5.gc();
        }

        if (this.max_step_counter > 0 && this.step_counter == this.max_step_counter) {
            this.step_counter = 0;
            System.out.println(this.steps_taken);
            var5 = Runtime.getRuntime();
            var5.gc();
            System.out.println("Total mem: " + var5.totalMemory() / 1024L + "k");
            System.out.println("Concepts = " + Integer.toString(this.concepts.size()));
            System.out.println("-----------------");
        }

        this.addToTimer("2.1 Getting next step from agenda");
        Step var36 = this.agenda.nextStep(this.unary_rules, this.binary_rules, this);
        var36.id = Integer.toString(this.steps_taken);
        if (this.keep_steps) {
            this.previous_steps.addElement(var36);
        }

        this.addToTimer("2.2 Reacting to a new step");
        this.react.reactTo(var36, "new_step");
        this.addToTimer("2.3 Updating the front end agenda screen");
        if (this.front_end.auto_update_agenda.getState()) {
            this.updateAgendaFrontEnd(new Integer(this.front_end.auto_update_text.getText()));
        }

        if (var36.size() == 0) {
            var36.result_of_step_explanation = "exhausted search";
            this.updateFrontEnd();
            return var36;
        } else {
            this.addToTimer("2.4 Getting step details");
            this.front_end.current_step_text.setText(var36.asString());
            Vector var6 = (Vector)((Vector)var36.elementAt(0)).clone();
            ProductionRule var7 = (ProductionRule)var36.elementAt(1);
            Vector var8 = (Vector)var36.elementAt(2);
            this.addToTimer("2.5 Updating parents number of steps involved");
            Vector var9 = new Vector();

            int var10;
            Concept var11;
            for(var10 = 0; var10 < var6.size(); ++var10) {
                var11 = (Concept)var6.elementAt(var10);
                if (var11.is_cross_domain) {
                    var1.is_cross_domain = true;
                }

                ++var11.development_steps_num;
                if (!var9.contains(var11.domain)) {
                    var9.addElement(var11.domain);
                }

                if (this.front_end.measure_concepts_check.getState()) {
                    this.measure_concept.updateNormalisedDevelopments(var11, false);
                }
            }

            if (var9.size() > 1) {
                var1.is_cross_domain = true;
            }

            this.addToTimer("2.6 Using substitutable parent concepts");

            for(var10 = 0; var10 < var6.size(); ++var10) {
                var11 = (Concept)var6.elementAt(var10);
                var6.setElementAt(var11.getSubstitutableConcept(), var10);
            }

            this.addToTimer("3.1 Getting new types");
            Vector var37 = var7.transformTypes(var6, var8);
            String var38 = (String)var37.elementAt(0);
            var1.types = var37;
            this.addToTimer("3.2 Generating new specifications");
            Vector var12 = new Vector();
            Vector var13 = var7.newSpecifications(var6, var8, this, var12);
            var1.specifications = this.specification_handler.addSpecifications(var13);
            var1.setSkolemisedRepresentation();
            var1.parents = var6;
            this.addToTimer("3.3 Generating new functions");
            Vector var14 = new Vector();

            for(int var15 = 0; var15 < var12.size(); ++var15) {
                Function var16 = (Function)var12.elementAt(var15);
                if (!var14.contains(var16.writeFunction())) {
                    var1.functions.addElement(var16);
                    var14.addElement(var16.writeFunction());
                }
            }

            this.addToTimer("3.4 Checking the specifications for no-goods");
            Vector var39 = this.specification_handler.rejectSpecifications(this.forced_id, var36, var1, this);
            String var40 = (String)var39.elementAt(0);
            if (!var40.equals("is good")) {
                this.addToTimer("3.5 Returning: syntactically no good (or equivalent) concept");
                var36.result_of_step_explanation = var40;
                var1.types = var7.transformTypes(var6, var8);
                if (var39.size() == 2) {
                    this.agenda.recordForcedName(var36, (Concept)var39.elementAt(1));
                }

                if (var39.size() > 1 && var40.equals("old concept with same specifications")) {
                    var36.result_of_step = (Concept)var39.elementAt(1);
                }

                this.addToTimer("3.6 Measuring productivity of parent concepts after rejected specification step");
                this.reCalculateProductivities(var6);
                this.addToTimer("3.7 Updating front end after rejected specification step");
                this.updateFrontEnd();
                return var36;
            } else {
                this.addToTimer("4.1 Generating new datatable");
                Vector var17 = new Vector();

                for(int var18 = 0; var18 < var6.size(); ++var18) {
                    var17.addElement(((Concept)var6.elementAt(var18)).datatable);
                }

                var7.startStopWatch();
                Datatable var41 = var7.transformTable(var17, var6, var8, this.concepts);
                var41.trimRowsToSize();
                var1.datatable_construction_time = var7.stopWatchTime();
                Concept var19 = (Concept)var6.elementAt(0);
                if (var41.size() == 0) {
                    this.addToTimer("4.2 Returning: couldn't perform step");
                    var36.result_of_step_explanation = "couldn't perform step";
                    this.addToTimer("4.3 Measuring productivity of parent concepts");
                    this.reCalculateProductivities(var6);
                    this.addToTimer("4.4 Updating front end");
                    this.updateFrontEnd();
                    return var36;
                } else {
                    this.addToTimer("4.5 Getting new ID");
                    String var20 = "";
                    if (this.forced_id.equals("")) {
                        var20 = Integer.toString(this.concepts.size() + 1) + "_0";
                        var20 = var38.substring(0, 1) + var20;
                    } else {
                        var20 = this.forced_id;
                    }

                    this.addToTimer("4.6 Complimenting the table");
                    this.complimentTable(var41, var37);
                    this.addToTimer("5.1 Getting ancestors of new concept");
                    Vector var21 = new Vector();
                    Vector var22 = new Vector();
                    new Concept();

                    Vector var30;
                    for(int var24 = 0; var24 < var6.size(); ++var24) {
                        Concept var23 = (Concept)var6.elementAt(var24);
                        var21.addElement(var23.id);
                        Vector var25 = var23.ancestor_ids;

                        for(int var26 = 0; var26 < var25.size(); ++var26) {
                            String var27 = (String)var25.elementAt(var26);
                            if (!var1.ancestor_ids.contains(var27)) {
                                var1.ancestor_ids.addElement(var27);
                            }

                            Concept var28 = this.getConcept(var27);
                            if (!var1.ancestors.contains(var28)) {
                                var1.ancestors.addElement(var28);
                            }

                            for(int var29 = 0; var29 < var28.construction_history.size(); ++var29) {
                                var30 = (Vector)var28.construction_history.elementAt(var29);
                                if (!var22.contains(var30)) {
                                    var22.addElement(var30);
                                }
                            }
                        }
                    }

                    Vector var42 = new Vector();
                    var42.addElement(var20);
                    var42.addElement(var7.getName());
                    var42.addElement(var21);
                    var42.addElement(var8);
                    var22.addElement(var42);
                    var1.construction_history = var22;
                    var1.complexity = var1.ancestor_ids.size() + 1;
                    boolean var43 = false;
                    if (this.make_non_exists_from_empty) {
                        var43 = var41.allEmptyTuples(this);
                    }

                    Vector var44 = new Vector();
                    if (var43) {
                        this.addToTimer("5.2 Making non-existence conjecture");
                        var1.types = var37;
                        var1.object_type = (String)var37.elementAt(0);
                        var1.construction = var36;
                        var1.is_user_given = false;
                        var1.step_number = this.steps_taken;
                        var1.domain = var38;
                        var1.parents = var6;
                        NonExists var45 = new NonExists(var1, Integer.toString(this.numberOfConjectures()));
                        var45.step = var36;
                        var36.result_of_step = var45;
                        var45.when_constructed = (long)this.seconds_elapsed;
                        this.addToTimer("5.3 Checking for previous non-existence which is the same as the new one");
                        if (this.keep_non_existences && !this.explanation_handler.conjectureSeenAlready(var45)) {
                            this.explanation_handler.addConjecture(var45);
                            this.addToTimer("5.4 Adding a new non-existence conjecture to the theory");
                            var44 = this.addNonExistsToTheory(var45, var1);
                        }

                        if (!var44.isEmpty()) {
                            this.addToTimer("5.5 Adding a new entity to the theory (from counterexample to non-exists)");
                            var1.datatable = var41;

                            for(int var48 = 0; var48 < var44.size(); ++var48) {
                                Entity var51 = (Entity)var44.elementAt(var48);
                                this.addNewEntityToTheory(var51, var1.domain, "5.5");
                                var1.updateDatatable(this.concepts, var51);
                            }

                            var44 = new Vector();
                            var43 = false;
                        }

                        if (var44.isEmpty()) {
                            this.addToTimer("5.6 Telling agenda about a non-existence conjecture");
                            this.agenda.recordNonExistentConcept(var36);
                        }
                    }

                    this.addToTimer("6.1 Checking for match with previous datatables");

                    for(int var46 = 0; var46 < var1.specifications.size(); ++var46) {
                        Specification var49 = (Specification)var1.specifications.elementAt(var46);
                    }

                    boolean var47 = false;
                    String var50 = "";
                    Concept var52 = null;
                    int var31;
                    Concept var32;
                    int var33;
                    if (!var43 && this.make_equivalences_from_equality) {
                        var50 = var41.firstTuples();
                        var30 = (Vector)this.datatable_hashtable.get(var50);
                        if (var30 != null) {
                            for(var31 = 0; var31 < var30.size(); ++var31) {
                                var32 = (Concept)var30.elementAt(var31);
                                if (var32.datatable.isIdenticalTo(this, var41) && var32.types.toString().equals(var37.toString())) {
                                    for(var33 = 0; var33 < var32.specifications.size(); ++var33) {
                                        Specification var34 = (Specification)this.specifications.elementAt(var33);
                                        var34.type.equals("negate");
                                    }

                                    var52 = var32;
                                    break;
                                }
                            }
                        }
                    }

                    if (var52 != null) {
                        var52 = var52.getSubstitutableConcept();
                    }

                    if (var52 != null && this.skip_equivalence_conjectures) {
                        var36.result_of_step_explanation = "Skipped equiv conjecture";
                        return var36;
                    } else {
                        Equivalence var53 = new Equivalence();
                        boolean var54;
                        Vector var55;
                        if (!var43 && var52 != null) {
                            this.addToTimer("6.2 Making an equivalence conjecture");
                            var47 = true;
                            var1.types = var37;
                            var1.object_type = (String)var37.elementAt(0);
                            var1.construction = var36;
                            var1.is_user_given = false;
                            var1.step_number = this.steps_taken;
                            var1.domain = (String)var37.elementAt(0);
                            var1.applicability = var52.applicability;
                            var1.variety = var52.variety;
                            var53 = new Equivalence(var52, var1, Integer.toString(this.numberOfConjectures()));
                            this.addToTimer("6.3 Checking for repetition of equivalence conjecture");
                            var54 = false;
                            if (this.keep_equivalences && this.explanation_handler.conjectureSeenAlready(var53)) {
                                this.addToTimer("6.4 Returning: equivalence conjecture was repeated");
                                var36.result_of_step_explanation = "repeat equivalence conjecture formed";
                                this.agenda.recordForcedName(var36, var52);
                                this.addToTimer("6.5 Measuring productivity of parent concepts");
                                this.reCalculateProductivities(var6);
                                this.addToTimer("6.6 Updating front end");
                                this.updateFrontEnd();
                                return var36;
                            }

                            this.addToTimer("6.7 Checking if an equivalence conjecture is trivial");
                            if (var53.isTrivial()) {
                                var36.result_of_step_explanation = "trivial equivalence conjecture formed";
                                this.agenda.recordForcedName(var36, var52);
                                this.addToTimer("6.8 Measuring productivity of parent concepts");
                                this.reCalculateProductivities(var6);
                                this.addToTimer("6.9 Updating front end");
                                this.updateFrontEnd();
                                return var36;
                            }

                            this.addToTimer("6.10 Checking if a conjecture is a tautology");
                            if (this.specification_handler.isTautology(var53)) {
                                var36.result_of_step_explanation = "equivalence conjecture shown to be a tautology";
                                this.agenda.recordForcedName(var36, var52);
                                this.addToTimer("6.11 Measuring productivity of parent concepts");
                                this.reCalculateProductivities(var6);
                                this.addToTimer("6.12 Updating front end");
                                this.updateFrontEnd();
                                return var36;
                            }

                            this.addToTimer("6.13 Adding an equivalence conjecture to the theory");
                            var36.result_of_step_explanation = "new equivalence conjecture formed";
                            var36.result_of_step = var53;
                            var53.step = var36;
                            var53.when_constructed = (long)this.seconds_elapsed;
                            var55 = this.addEquivalenceToTheory(var53, var52, var1);

                            for(var31 = 0; var31 < var55.size(); ++var31) {
                                var44.addElement(var55.elementAt(var31));
                            }
                        }

                        this.addToTimer("7.1 Adding new entities to the theory (from counterexample to equivalence)");

                        for(var31 = 0; var31 < var44.size(); ++var31) {
                            Entity var56 = (Entity)var44.elementAt(var31);
                            this.addNewEntityToTheory(var56, var1.domain, "7.1");
                        }

                        this.addToTimer("7.2 Getting more concept details");
                        if (var36.productionRule().getName().equals("split")) {
                            Vector var57 = (Vector)var36.parameters().elementAt(0);
                            if (var57.size() == 1 && ((String)var57.elementAt(0)).equals("0")) {
                                var1.is_single_entity = true;
                                var1.is_entity_instantiations = true;
                            }
                        }

                        if (!var1.is_entity_instantiations) {
                            var1.is_entity_instantiations = true;

                            for(var31 = 0; var31 < var6.size(); ++var31) {
                                var32 = (Concept)var6.elementAt(var31);
                                var32.children.addElement(var1);
                                if (!var32.is_entity_instantiations) {
                                    var1.is_entity_instantiations = false;
                                }
                            }
                        }

                        if (var36.productionRule().getName().equals("negate") && !var1.is_entity_instantiations) {
                            Concept var60 = (Concept)var6.elementAt(0);
                            var32 = (Concept)var6.elementAt(1);
                            if (var60.entity_concept == var32 && var60.is_entity_instantiations) {
                                var1.is_entity_instantiations = true;
                            }
                        }

                        var54 = false;
                        if (var47 && this.allow_substitutions) {
                            this.addToTimer("7.3 Substituting a concept definition");
                            if (var52.complexity > var1.complexity && !var1.is_entity_instantiations) {
                                var54 = true;
                            }

                            if (!var52.is_entity_instantiations && var1.is_entity_instantiations) {
                                var54 = true;
                            }

                            if (var52.is_entity_instantiations && var1.is_entity_instantiations && var52.complexity > var1.complexity) {
                                var54 = true;
                            }
                        }

                        if (var47 && var44.isEmpty()) {
                            boolean var58 = false;
                            this.addToTimer("7.5 Adding alternative constructions to old concept in an equivalence conjecture");
                            String var59;
                            if (!this.keep_equivalences) {
                                var59 = var1.writeDefinition("ascii");

                                for(int var61 = 0; var61 < var52.conjectured_equivalent_concepts.size() && !var58; ++var61) {
                                    Concept var35 = (Concept)var52.conjectured_equivalent_concepts.elementAt(var61);
                                    if (var35.writeDefinition("ascii").equals(var59)) {
                                        var58 = true;
                                    }
                                }
                            }

                            if (!var58) {
                                if (!this.skip_equivalence_conjectures) {
                                    var52.conjectured_equivalent_constructions.addElement(var36);
                                }

                                var1.datatable.setNumberOfTuples();
                                var1.datatable = var41;
                                if (!this.skip_equivalence_conjectures) {
                                    var52.conjectured_equivalent_concepts.addElement(var1);
                                }

                                var1.equivalence_conjecture = var53;
                                var59 = var52.id;
                                if (var59.indexOf("_") >= 0) {
                                    var59 = var52.id.substring(0, var52.id.indexOf("_"));
                                }

                                if (!var54) {
                                    var20 = var59 + "(" + Integer.toString(var52.conjectured_equivalent_concepts.size() + 1) + ")";
                                } else {
                                    var20 = var59 + "_" + Integer.toString(var52.substitutable_concepts.size() + 1);
                                }

                                var1.id = var20;
                                if (var54) {
                                    var52.substitutable_concepts.addElement(var1);
                                }
                            }

                            this.agenda.recordForcedName(var36, var52);
                            if (!this.forced_id.equals("")) {
                                var1.alternative_ids.addElement(this.forced_id);
                            }

                            this.addToTimer("7.6 Measuring productivity of parent concepts after equivalence conjecture formed");
                            this.reCalculateProductivities(var6);
                            if (!var54) {
                                this.addToTimer("7.7 Updating front end after equivalence conjecture formed");
                                this.updateFrontEnd();
                                return var36;
                            }
                        }

                        if (var43 && var44.isEmpty()) {
                            var36.result_of_step_explanation = "non-existence conjecture formed";
                            this.addToTimer("7.8 Measuring productivity of parent concepts");
                            this.reCalculateProductivities(var6);
                            this.addToTimer("7.9 Updating front end");
                            this.updateFrontEnd();
                            return var36;
                        } else {
                            this.addToTimer("8.1 Calculating additional details about a new concept");
                            var1.ancestor_ids.addElement(var20);
                            if (var7.isCumulative()) {
                                var1.is_cumulative = true;
                            }

                            var1.when_constructed = (long)this.seconds_elapsed;
                            var1.object_type = (String)var37.elementAt(0);
                            var1.entity_concept = this.conceptFromName(var1.object_type);
                            var1.arity = var37.size();
                            var1.step_number = this.steps_taken;
                            var1.construction = var36;
                            var1.is_user_given = false;
                            var1.position_in_theory = this.concepts.size() + 1;
                            var1.user_functions = this.user_functions;
                            var1.domain = (String)var37.elementAt(0);
                            var1.types = var37;
                            var1.id = var20;
                            var1.datatable = var41;
                            if (var36.dont_develop) {
                                var1.dont_develop = true;
                            }

                            int var62;
                            for(var62 = 0; var62 < var44.size(); ++var62) {
                                Entity var64 = (Entity)var44.elementAt(var62);
                                var1.updateDatatable(this.concepts, var64);
                            }

                            this.addToTimer("8.2 Finding the generalisations of a new concept");
                            var1.generalisations = new Vector();

                            Concept var65;
                            for(var62 = 0; var62 < this.concepts.size(); ++var62) {
                                var65 = (Concept)this.concepts.elementAt(var62);
                                if (var65.isGeneralisationOf(var1) == 0) {
                                    var1.generalisations.addElement(var65);
                                }
                            }

                            this.addToTimer("8.4 Adding the categorisation of a new concept");
                            this.addCategorisation(var1);
                            this.addToTimer("8.4 Measuring the interestingness of a new concept");
                            this.measure_concept.measureConcept(var1, this.concepts, true);
                            if (this.front_end.measure_concepts_check.getState()) {
                                this.addToTimer("8.6 Updating the number of concept-producing steps of the parents of a new concept");

                                for(var62 = 0; var62 < var6.size(); ++var62) {
                                    var65 = (Concept)var6.elementAt(var62);
                                    ++var65.number_of_children;
                                }

                                this.addToTimer("8.7 Measuring productivity of parent concepts");
                                this.reCalculateProductivities(var6);
                                this.addToTimer("8.8 Update the interestingness of the parents of a new concept");

                                for(var62 = 0; var62 < var6.size(); ++var62) {
                                    var65 = (Concept)var6.elementAt(var62);
                                    this.measure_concept.measureConcept(var65, this.concepts, false);
                                }
                            }

                            if (!var54) {
                                if (this.make_subsumption_implications && !var1.is_entity_instantiations) {
                                    this.addToTimer("9.1 Making subsumption conjectures for a new concept");
                                    var55 = this.makeSubsumptionConjectures(var1);
                                    this.addToTimer("9.2 Adding new entities from subsumption conjecture making");

                                    for(var33 = 0; var33 < var55.size(); ++var33) {
                                        Entity var63 = (Entity)var55.elementAt(var33);
                                        this.addNewEntityToTheory(var63, var1.domain, "9.2");
                                        var1.updateDatatable(this.concepts, var63);
                                    }
                                }

                                this.addToTimer("9.3 Making an applicability conjecture for the concept (if possible)");
                                if (this.use_applicability_conj && !var1.is_entity_instantiations) {
                                    this.makeApplicabilityConjectures(var1);
                                }

                                if (this.make_near_equivalences) {
                                    this.addToTimer("9.4 Finding near-equivalences of a new concept");
                                    var1.getNearEquivalences(this, this.concepts, this.near_equiv_percent);
                                    if (this.make_near_equivalences) {
                                        for(var62 = 0; var62 < var1.near_equivalences.size(); ++var62) {
                                            NearEquivalence var66 = (NearEquivalence)var1.near_equivalences.elementAt(var62);
                                            var66.checkSegregation(this.agenda);
                                            this.near_equivalences.addElement(var66);
                                            var66.id = Integer.toString(this.numberOfConjectures());
                                            this.conjectures.addElement(var1.near_equivalences.elementAt(var62));
                                        }
                                    }
                                }

                                if (this.make_near_implications) {
                                    this.addToTimer("9.5 Finding near-implications of a new concept");
                                    var1.getSubsumptionNearImplications(this.concepts, this.near_imp_percent);
                                    if (this.make_near_implications) {
                                        for(var62 = 0; var62 < var1.near_implications.size(); ++var62) {
                                            NearImplication var67 = (NearImplication)var1.near_implications.elementAt(var62);
                                            var67.checkSegregation(this.agenda);
                                            this.near_implications.addElement(var67);
                                            var67.id = Integer.toString(this.numberOfConjectures());
                                            this.conjectures.addElement(var1.near_implications.elementAt(var62));
                                        }
                                    }
                                }
                            }

                            this.addToTimer("10.1 Reacting to a concept before it is added to the theory");
                            this.react.reactTo(var1, "new_concept_before_adding_to_agenda");
                            this.addToTimer("10.2 Adding a new concept to theory");
                            var36.result_of_step_explanation = "new concept formed";
                            var36.result_of_step = var1;
                            this.concepts.addElement(var1);
                            if (var1.arity == 1) {
                                this.specialisation_concepts.addElement(var1);
                            }

                            if (var54) {
                                var1.dont_develop = true;
                            }

                            if (!var54) {
                                this.addToTimer("10.3 Adding a new concept to the agenda");
                                this.agenda.addConcept(var1, this.concepts, this);
                                this.agenda.orderConcepts();
                                this.addToTimer("10.4 Checking whether a required concept has been learned");
                                if (this.check_for_gold_standard && this.hasAchievedCategorisation(var1.categorisation)) {
                                    this.learned_concepts.addElement(var1);
                                    var1.has_required_categorisation = true;
                                }

                                this.addToTimer("10.5 Getting the first tuples for a new datatable");
                                var50 = var41.firstTuples();
                                this.addToTimer("10.6 Adding a new concept's datatable to hashtable");
                                var55 = (Vector)this.datatable_hashtable.get(var50);
                                if (var55 == null) {
                                    var55 = new Vector();
                                    this.datatable_hashtable.put(var50, var55);
                                }

                                var55.addElement(var1);
                            }

                            this.addToTimer("10.7 Updating front end after new concept formed");
                            this.updateFrontEnd(var1);
                            if (!var54) {
                                this.addToTimer("10.8 Substituting a definition");
                                this.agenda.recordForcedName(var36, var1);
                            }

                            this.addToTimer("10.9 Reacting to a new concept");
                            this.react.reactTo(var1, "new_concept");
                            return var36;
                        }
                    }
                }
            }
        }
    }

    public boolean hasAchievedCategorisation(Categorisation var1) {
        if (this.required_flat_categorisations.contains(var1.toString())) {
            return true;
        } else {
            for(int var2 = 0; var2 < this.required_categorisations.size(); ++var2) {
                Categorisation var3 = (Categorisation)this.required_categorisations.elementAt(var2);
                if (var3.compliesWith(var1)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void makeApplicabilityConjectures(Concept var1) {
        this.addToTimer("9.3 Making applicability conjectures");
        Vector var2 = new Vector();
        Vector var3 = new Vector();

        int var4;
        for(var4 = 0; var4 < var1.datatable.size() && var2.size() <= this.max_applicability_conj_number; ++var4) {
            Row var5 = (Row)var1.datatable.elementAt(var4);
            if (!var5.tuples.isEmpty()) {
                var2.addElement(var5.entity);
            }
        }

        if (var2.size() <= this.max_applicability_conj_number) {
            for(var4 = 0; var4 < var2.size(); ++var4) {
                this.agenda.addForcedStep("app_split" + this.steps_taken + "_" + Integer.toString(var4) + " = " + var1.entity_concept.id + " split [[0], [" + (String)var2.elementAt(var4) + "]] : dont_develop", false);
                var3.addElement("app_split" + this.steps_taken + "_" + Integer.toString(var4));
            }

            if (var3.size() >= 2) {
                this.agenda.addForcedStep("app_disj" + this.steps_taken + "_1 = " + "app_split" + this.steps_taken + "_0 app_split" + this.steps_taken + "_1 disjunct [] : dont_develop", false);

                for(var4 = 2; var4 < var3.size(); ++var4) {
                    this.agenda.addForcedStep("app_disj" + this.steps_taken + "_" + Integer.toString(var4) + " = app_disj" + this.steps_taken + "_" + Integer.toString(var4 - 1) + " " + (String)var3.elementAt(var4) + " disjunct [] : dont_develop", false);
                }
            }
        }

    }

    public Vector makeSubsumptionConjectures(Concept var1) {
        Vector var2 = new Vector();
        this.addToTimer("9.1.1 Finding subsuming concepts");
        Vector var3 = var1.getSubsumptionImplications(this.concepts);
        this.addToTimer("9.1.2 Attempting to prove subsuming implications");

        for(int var4 = 0; var4 < var3.size(); ++var4) {
            Implication var5 = (Implication)var3.elementAt(var4);
            if (!this.explanation_handler.conjectureSeenAlready(var5)) {
                this.explanation_handler.addConjecture(var5);
                this.explanation_handler.explainConjecture(var5, this, "9.1.2");

                for(int var6 = 0; var6 < var5.counterexamples.size(); ++var6) {
                    var2.addElement(var5.counterexamples.elementAt(var6));
                }

                var1.implications.addElement(var5);
                if (this.keep_implications) {
                    var5.id = Integer.toString(this.conjectures.size());
                    this.measure_conjecture.measureConjecture(var5, this.implications);
                    var5.checkSegregation(this.agenda);
                    this.conjectures.addElement(var5);
                    this.implications.addElement(var5);
                    this.updateFrontEnd((Conjecture)var5);
                }

                if (this.make_subsumption_implicates) {
                    this.addToTimer("9.1.3 Extracting implicates from subsuming implicates");
                    Vector var9 = var5.implicates(this.specification_handler);

                    for(int var7 = 0; var7 < var9.size(); ++var7) {
                        Implicate var8 = (Implicate)var9.elementAt(var7);
                        var5.child_conjectures.addElement(var8);
                        var8.parent_conjectures.addElement(var5);
                    }

                    if (this.keep_implicates) {
                        Vector var10 = this.addImplicates(var9, var5, "9.1.3");

                        for(int var11 = 0; var11 < var10.size(); ++var11) {
                            var2.addElement(var10.elementAt(var11));
                        }
                    }
                }
            }
        }

        return var2;
    }

    public void addNewEntityToTheory(Entity var1, String var2, String var3) {
        this.addToTimer(var3 + ".1 Checking whether the entity is already in the theory");
        if (var1.concept_data != null && !var1.concept_data.isEmpty()) {
            for(int var4 = 0; var4 < this.entities.size(); ++var4) {
                Entity var5 = (Entity)this.entities.elementAt(var4);
                boolean var6 = var5.concept_data.toString().equals(var1.concept_data.toString());
                if (var5.concept_data.toString().equals(var1.concept_data.toString())) {
                    int var7 = var1.conjecture.counterexamples.indexOf(var1);
                    var1.conjecture.counterexamples.setElementAt(var5, var7);
                    return;
                }
            }
        }

        if (var1.name.equals("")) {
            var1.name = "new_" + var2 + Integer.toString(this.entities.size());
        }

        var1.id = var1.name;
        if (!this.entities_added.contains(var1.name)) {
            this.entities.addElement(var1);
            this.entity_names.addElement(var1);
            this.entities_added.addElement(var1.name);
            this.addToTimer(var3 + ".2 Updating datatables, categorisations and same datatable hashtable with a new entity (from counterexample)");
            this.categorisations.removeAllElements();
            new Vector();
            this.datatable_hashtable = new Hashtable();

            int var9;
            Concept var10;
            for(var9 = 0; var9 < this.concepts.size(); ++var9) {
                var10 = (Concept)this.concepts.elementAt(var9);
                if (var10.domain.equals(var2)) {
                    var10.updateDatatable(this.concepts, var1);
                    String var11 = var10.datatable.firstTuples();
                    this.addCategorisation(var10);
                    Vector var8 = (Vector)this.datatable_hashtable.get(var11);
                    if (var8 != null) {
                        var8.addElement(var10);
                    } else {
                        var8 = new Vector();
                        var8.addElement(var10);
                        this.datatable_hashtable.put(var11, var8);
                    }
                }
            }

            this.addToTimer(var3 + ".3 Re-measuring the concepts after the introduction of a new entity");
            if (this.front_end.measure_concepts_check.getState()) {
                for(var9 = 0; var9 < this.concepts.size(); ++var9) {
                    var10 = (Concept)this.concepts.elementAt(var9);
                    this.measure_concept.measureConcept(var10, this.concepts, false);
                }

                this.addToTimer(var3 + ".5 Re-ordering the agenda after the introduction of a new entity");
                this.agenda.orderConcepts();
            }

            this.addToTimer(var3 + ".5 Re-evaluating the near-equivalences of concepts");

            for(var9 = 0; var9 < this.concepts.size(); ++var9) {
                var10 = (Concept)this.concepts.elementAt(var9);
                var10.reEvaluateNearEquivalences(this, this.near_equiv_percent);
            }

            this.addToTimer(var3 + ".6 Re-evaluating the near-implications of concepts");

            for(var9 = 0; var9 < this.concepts.size(); ++var9) {
                var10 = (Concept)this.concepts.elementAt(var9);
                var10.reEvaluateNearImplications(this, this.near_imp_percent);
            }

            this.addToTimer(var3 + ".7 Adding a new entity to the front_end");
            this.front_end.addEntity(var1);
            this.addToTimer(var3 + ".8 Updating the ground instances for the prover");
            this.explanation_handler.updateGroundInstances(this);
        }
    }

    public void formTheoryUntil(int var1, String var2) {
        if (!var2.equals("learned concepts")) {
            this.check_for_gold_standard = false;
        }

        this.runner = new TheoryFormationThread(var1, var2, this);
        this.runner.start();
    }

    public void stopIt() {
        this.stop_now = true;
        this.runner.stop();
        this.addToTimer("Killed");
    }

    public int numberOfConjectures() {
        return this.conjectures.size();
    }

    public void updateFrontEnd(Concept var1) {
        if (this.front_end.update_front_end_check.getState()) {
            this.front_end.addObjectToList(var1, var1.id, this.front_end.concept_list, this.front_end.concept_pruning_list.getSelectedItems(), this.front_end.concept_sorting_list.getSelectedItem());
            this.front_end.force_primary_concept_list.addItem(var1.id);
            this.front_end.force_secondary_concept_list.addItem(var1.id);
            this.updateFrontEnd();
        }

    }

    public void updateFrontEnd(Conjecture var1) {
        if (this.front_end.update_front_end_check.getState()) {
            this.front_end.addObjectToList(var1, var1.id, this.front_end.conjecture_list, this.front_end.conjecture_pruning_list.getSelectedItems(), this.front_end.conjecture_sorting_list.getSelectedItem());
            this.updateFrontEnd();
        }

    }

    public void updateFrontEnd(Entity var1) {
        if (this.front_end.update_front_end_check.getState()) {
            this.front_end.addEntity(var1);
            this.front_end.removeEntity(var1);
            this.updateFrontEnd();
        }

    }

    public void updateFrontEnd() {
        if (this.front_end.update_front_end_check.getState()) {
            if (this.use_front_end) {
                this.front_end.concept_list_number.setText("number: " + Integer.toString(this.front_end.concept_list.getItemCount()));
                this.front_end.conjecture_list_number.setText("number: " + Integer.toString(this.front_end.conjecture_list.getItemCount()));
                if (this.run_start_time > 0L) {
                    this.seconds_elapsed = (new Long((System.currentTimeMillis() - this.run_start_time) / 1000L)).intValue();
                } else {
                    this.seconds_elapsed = 0;
                }

                Vector var1 = new Vector();
                var1.addElement(Integer.toString(this.steps_taken));
                var1.addElement(Integer.toString(this.concepts.size()));
                var1.addElement(Integer.toString(this.conjectures.size()));
                var1.addElement(Integer.toString(this.equivalences.size()));
                var1.addElement(Integer.toString(this.non_existences.size()));
                var1.addElement(Integer.toString(this.prime_implicates.size()));
                var1.addElement(Integer.toString(this.categorisations.size()));
                var1.addElement(Integer.toString(this.specialisation_concepts.size()));
                var1.addElement(Integer.toString(this.entities_added.size()));
                var1.addElement(Integer.toString(this.agenda.itemsInAgenda()));
                var1.addElement(Long.toString((long)this.seconds_elapsed));
                this.front_end.statistics = var1;
                this.front_end.update();
            }

        }
    }

    public void complimentTable(Datatable var1, Vector var2) {
        String var3 = (String)var2.elementAt(0);
        Datatable var4 = this.conceptFromName(var3).datatable;
        String var5 = "";

        for(int var6 = 0; var6 < var4.size(); ++var6) {
            String var7 = ((Row)var4.elementAt(var6)).entity;
            if (var6 < var1.size()) {
                var5 = ((Row)var1.elementAt(var6)).entity;
            }

            if (var6 >= var1.size() || !var7.equals(var5)) {
                Row var8 = new Row();
                var8.entity = var7;
                var8.tuples = new Tuples();
                if (var6 < var1.size()) {
                    var1.insertElementAt(var8, var6);
                } else {
                    var1.addElement(var8);
                }
            }
        }

    }

    public String generateDefinition(Concept var1, Step var2, String var3) {
        Concept var4 = new Concept();
        var4.types = var1.types;
        var4.specifications = var2.productionRule().newSpecifications(var2.conceptList(), var2.parameters(), this, new Vector());
        return var4.writeDefinition(var3);
    }

    public void addCategorisation(Concept var1) {
        Categorisation var2 = new Categorisation();
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var1.datatable.size(); ++var4) {
            Row var5 = (Row)var1.datatable.elementAt(var4);
            String var6 = var5.tuples.toString();
            int var7 = var3.indexOf(var6);
            Vector var8;
            if (var7 == -1) {
                var3.addElement(var6);
                var8 = new Vector();
                var8.addElement(var5.entity);
                var2.addElement(var8);
            } else {
                var8 = (Vector)var2.elementAt(var7);
                var8.addElement(var5.entity);
            }
        }

        String var10 = var2.toString();
        Categorisation var11 = (Categorisation)this.flat_categorisations.get(var10);
        if (var11 == null) {
            this.categorisations.addElement(var2);
            this.flat_categorisations.put(var10, var2);
            var2.appearances = 1;
        } else {
            var2 = var11;
            ++var11.appearances;
        }

        var1.categorisation = var2;
        double var12 = new Double(1.0D);
        double var13 = new Double((double)var2.appearances);
        var2.novelty = var12 / var13;
    }

    private double valueFromDoubleField(TextField var1) {
        if (var1.getText().equals("")) {
            return 0.0D;
        } else {
            try {
                double var2 = new Double(var1.getText());
                var1.setBackground(Color.white);
                return var2;
            } catch (Exception var4) {
                var1.setBackground(Color.red);
                return 0.0D;
            }
        }
    }

    private int valueFromIntField(TextField var1) {
        if (var1.getText().equals("")) {
            return 0;
        } else {
            try {
                int var2 = (new Double(var1.getText())).intValue();
                var1.setBackground(Color.white);
                return var2;
            } catch (Exception var3) {
                var1.setBackground(Color.red);
                return 0;
            }
        }
    }

    private String valueFromStringField(TextField var1) {
        if (var1.getText().equals("")) {
            return "";
        } else {
            try {
                String var2 = new String(var1.getText());
                var1.setBackground(Color.white);
                return var2;
            } catch (Exception var3) {
                var1.setBackground(Color.red);
                return "";
            }
        }
    }

    public void getFrontEndSettings() {
        this.measure_conjecture.measure_conjectures = this.front_end.measure_conjectures_check.getState();
        this.measure_concept.measure_concepts = this.front_end.measure_concepts_check.getState();
        this.measure_concept.applicability_weight = this.valueFromDoubleField(this.front_end.concept_applicability_weight_text);
        this.measure_concept.invariance_weight = this.valueFromDoubleField(this.front_end.concept_invariance_weight_text);
        this.measure_concept.discrimination_weight = this.valueFromDoubleField(this.front_end.concept_discrimination_weight_text);
        this.measure_concept.equiv_conj_score_weight = this.valueFromDoubleField(this.front_end.concept_equiv_conj_score_weight_text);
        this.measure_concept.ne_conj_score_weight = this.valueFromDoubleField(this.front_end.concept_ne_conj_score_weight_text);
        this.measure_concept.imp_conj_score_weight = this.valueFromDoubleField(this.front_end.concept_imp_conj_score_weight_text);
        this.measure_concept.pi_conj_score_weight = this.valueFromDoubleField(this.front_end.concept_pi_conj_score_weight_text);
        this.measure_concept.equiv_conj_num_weight = this.valueFromDoubleField(this.front_end.concept_equiv_conj_num_weight_text);
        this.measure_concept.ne_conj_num_weight = this.valueFromDoubleField(this.front_end.concept_ne_conj_num_weight_text);
        this.measure_concept.imp_conj_num_weight = this.valueFromDoubleField(this.front_end.concept_imp_conj_num_weight_text);
        this.measure_concept.pi_conj_num_weight = this.valueFromDoubleField(this.front_end.concept_pi_conj_num_weight_text);
        this.measure_concept.coverage_weight = this.valueFromDoubleField(this.front_end.concept_coverage_weight_text);
        this.measure_concept.comprehensibility_weight = this.valueFromDoubleField(this.front_end.concept_comprehensibility_weight_text);
        this.measure_concept.cross_domain_weight = this.valueFromDoubleField(this.front_end.concept_cross_domain_weight_text);
        this.measure_concept.highlight_weight = this.valueFromDoubleField(this.front_end.concept_highlight_weight_text);
        this.measure_concept.novelty_weight = this.valueFromDoubleField(this.front_end.concept_novelty_weight_text);
        this.measure_concept.parent_weight = this.valueFromDoubleField(this.front_end.concept_parent_weight_text);
        this.measure_concept.children_score_weight = this.valueFromDoubleField(this.front_end.concept_children_weight_text);
        this.measure_concept.parsimony_weight = this.valueFromDoubleField(this.front_end.concept_parsimony_weight_text);
        this.measure_concept.predictive_power_weight = this.valueFromDoubleField(this.front_end.concept_predictive_power_weight_text);
        this.measure_concept.productivity_weight = this.valueFromDoubleField(this.front_end.concept_productivity_weight_text);
        this.measure_concept.development_steps_num_weight = this.valueFromDoubleField(this.front_end.concept_development_steps_num_weight_text);
        this.measure_concept.default_productivity = this.valueFromDoubleField(this.front_end.concept_default_productivity_text);
        this.measure_concept.variety_weight = this.valueFromDoubleField(this.front_end.concept_variety_weight_text);
        this.measure_concept.positive_applicability_weight = this.valueFromDoubleField(this.front_end.concept_positive_applicability_weight_text);
        this.measure_concept.negative_applicability_weight = this.valueFromDoubleField(this.front_end.concept_negative_applicability_weight_text);
        this.measure_conjecture.surprisingness_weight = this.valueFromDoubleField(this.front_end.conjecture_surprisingness_weight_text);
        this.measure_conjecture.applicability_weight = this.valueFromDoubleField(this.front_end.conjecture_applicability_weight_text);
        this.measure_conjecture.comprehensibility_weight = this.valueFromDoubleField(this.front_end.conjecture_comprehensibility_weight_text);
        this.measure_conjecture.plausibility_weight = this.valueFromDoubleField(this.front_end.conjecture_plausibility_weight_text);
        this.setComplexityLimit(this.valueFromIntField(this.front_end.complexity_text));
        this.setAgendaSizeLimit(this.valueFromIntField(this.front_end.agenda_limit_text));
        this.compose.tuple_product_limit = this.valueFromIntField(this.front_end.tuple_product_limit_text);
        this.compose.time_limit = this.valueFromIntField(this.front_end.compose_time_limit_text);
        this.arithmeticb.operators_allowed = this.front_end.arithmeticb_operators_text.getText().trim();
        this.measure_concept.use_weighted_sum = this.front_end.weighted_sum_check.getState();
        this.measure_concept.keep_best = this.front_end.keep_best_check.getState();
        this.measure_concept.keep_worst = this.front_end.keep_worst_check.getState();
        if (this.measure_concept.use_weighted_sum || this.measure_concept.keep_best || this.measure_concept.keep_worst) {
            this.agenda.best_first = true;
        }

        this.measure_concept.normalise_values = this.front_end.normalise_concept_measures_check.getState();
        this.measure_concept.interestingness_zero_min = this.valueFromDoubleField(this.front_end.interestingness_zero_min_text);
        this.agenda.best_first_delay_steps = this.valueFromIntField(this.front_end.best_first_delay_text);
        if (this.agenda.best_first_delay_steps > 0) {
            this.agenda.best_first_delayed = true;
            this.agenda.best_first = false;
        }

        this.setDepthFirst(this.front_end.depth_first_check.getState());
        this.setRandom(this.front_end.random_check.getState());
        this.setSubobjectOverlap(this.front_end.subobject_overlap_check.getState());
        this.agenda.expand_agenda = this.front_end.expand_agenda_check.getState();
        this.agenda.use_forward_lookahead = this.front_end.use_forward_lookahead_check.getState();
        this.gc_when = this.valueFromIntField(this.front_end.gc_text);
        this.make_equivalences_from_equality = this.front_end.make_equivalences_from_equality_check.getState();
        this.make_equivalences_from_combination = this.front_end.make_equivalences_from_combination_check.getState();
        this.make_non_exists_from_empty = this.front_end.make_non_exists_from_empty_check.getState();
        this.keep_steps = this.front_end.keep_steps_check.getState();
        this.keep_equivalences = this.front_end.keep_equivalences_check.getState();
        this.keep_non_existences = this.front_end.keep_non_exists_check.getState();
        this.keep_implications = this.front_end.keep_implications_check.getState();
        this.keep_implicates = this.front_end.keep_implicates_check.getState();
        this.keep_prime_implicates = this.front_end.keep_prime_implicates_check.getState();
        this.extract_implications_from_equivalences = this.front_end.extract_implications_from_equivalences_check.getState();
        this.extract_implicates_from_non_exists = this.front_end.extract_implicates_from_non_exists_check.getState();
        this.make_subsumption_implications = this.front_end.make_implications_from_subsumptions_check.getState();
        this.make_subsumption_implicates = this.front_end.make_implicates_from_subsumes_check.getState();
        this.extract_implicates_from_equivalences = this.front_end.extract_implicates_from_equivalences_check.getState();
        this.extract_prime_implicates_from_implicates = this.front_end.extract_prime_implicates_from_implicates_check.getState();
        this.teacher_requests_conjectures = this.front_end.teacher_requests_conjecture_check.getState();
        this.teacher_requests_nonexists = this.front_end.teacher_requests_nonexists_check.getState();
        this.teacher_requests_implication = this.front_end.teacher_requests_implication_check.getState();
        this.teacher_requests_nearimplication = this.front_end.teacher_requests_nearimplication_check.getState();
        this.teacher_requests_equivalence = this.front_end.teacher_requests_equivalence_check.getState();
        this.teacher_requests_nearequivalence = this.front_end.teacher_requests_nearequivalence_check.getState();
        this.make_near_equivalences = this.front_end.make_near_equivalences_check.getState();
        this.near_equiv_percent = (double)this.valueFromIntField(this.front_end.near_equivalence_percent_text);
        this.make_near_implications = this.front_end.make_near_implications_check.getState();
        this.near_imp_percent = (double)this.valueFromIntField(this.front_end.near_implication_percent_text);
        this.max_counterexample_bar_number = this.valueFromIntField(this.front_end.counterexample_barring_num_text);
        this.max_concept_bar_number = this.valueFromIntField(this.front_end.concept_barring_num_text);
        this.use_counterexample_barring = this.front_end.use_counterexample_barring_check.getState();
        this.use_concept_barring = this.front_end.use_concept_barring_check.getState();
        this.use_applicability_conj = this.front_end.use_applicability_conj_check.getState();
        this.max_applicability_conj_number = this.valueFromIntField(this.front_end.applicability_conj_num_text);
        this.allow_substitutions = this.front_end.substitute_definitions_check.getState();
        this.explanation_handler.use_ground_instances = this.front_end.use_ground_instances_in_proving_check.getState();
        this.explanation_handler.use_entity_letter = this.front_end.use_entity_letter_in_proving_check.getState();
        this.require_proof_in_subsumption = this.front_end.require_proof_in_subsumption_check.getState();
        this.use_surrender = this.front_end.use_surrender_check.getState();
        this.use_strategic_withdrawal = this.front_end.use_strategic_withdrawal_check.getState();
        this.use_piecemeal_exclusion = this.front_end.use_piecemeal_exclusion_check.getState();
        this.use_monster_barring = this.front_end.use_monster_barring_check.getState();
        this.use_monster_adjusting = this.front_end.use_monster_adjusting_check.getState();
        this.use_lemma_incorporation = this.front_end.use_lemma_incorporation_check.getState();
        this.use_proofs_and_refutations = this.front_end.use_proofs_and_refutations_check.getState();
        this.use_communal_piecemeal_exclusion = this.front_end.use_communal_piecemeal_exclusion_check.getState();
        this.lakatos.test_max_num_independent_work_stages = this.front_end.max_num_independent_work_stages_check.getState();
        this.lakatos.max_num_independent_work_stages = this.valueFromIntField(this.front_end.max_num_independent_work_stages_value_text);
        this.lakatos.use_threshold_to_add_conj_to_theory = this.front_end.threshold_to_add_conj_to_theory_check.getState();
        this.lakatos.use_threshold_to_add_concept_to_theory = this.front_end.threshold_to_add_concept_to_theory_check.getState();
        this.lakatos.threshold_to_add_conj_to_theory = this.valueFromDoubleField(this.front_end.threshold_to_add_conj_to_theory_value_text);
        this.lakatos.threshold_to_add_concept_to_theory = this.valueFromDoubleField(this.front_end.threshold_to_add_concept_to_theory_value_text);
        this.lakatos.test_num_independent_steps = this.front_end.num_independent_steps_check.getState();
        this.lakatos.num_independent_steps = this.valueFromIntField(this.front_end.num_independent_steps_value_text);
        this.lakatos.test_number_modifications_surrender = this.front_end.number_modifications_check.getState();
        this.lakatos.test_interestingness_surrender = this.front_end.interestingness_threshold_check.getState();
        this.lakatos.compare_average_interestingness_surrender = this.front_end.compare_average_interestingness_check.getState();
        this.lakatos.test_plausibility_surrender = this.front_end.plausibility_threshold_check.getState();
        this.lakatos.test_domain_application_surrender = this.front_end.domain_application_threshold_check.getState();
        this.lakatos.number_modifications_surrender = this.valueFromIntField(this.front_end.modifications_value_text);
        this.lakatos.interestingness_th_surrender = this.valueFromDoubleField(this.front_end.interestingness_threshold_value_text);
        this.lakatos.plausibility_th_surrender = this.valueFromDoubleField(this.front_end.plausibility_threshold_value_text);
        this.lakatos.domain_application_th_surrender = this.valueFromDoubleField(this.front_end.domain_app_threshold_value_text);
        this.lakatos.use_breaks_conj_under_discussion = this.front_end.use_breaks_conj_under_discussion_check.getState();
        this.lakatos.accept_strictest = this.front_end.accept_strictest_check.getState();
        this.lakatos.use_percentage_conj_broken = this.front_end.use_percentage_conj_broken_check.getState();
        this.lakatos.use_culprit_breaker = this.front_end.use_culprit_breaker_check.getState();
        this.lakatos.use_culprit_breaker_on_conj = this.front_end.use_culprit_breaker_on_conj_check.getState();
        this.lakatos.use_culprit_breaker_on_all = this.front_end.use_culprit_breaker_on_all_check.getState();
        this.lakatos.monster_barring_culprit_min = this.valueFromIntField(this.front_end.monster_barring_culprit_min_text);
        this.lakatos.monster_barring_min = this.valueFromDoubleField(this.front_end.monster_barring_min_text);
        this.lakatos.monster_barring_type = this.valueFromStringField(this.front_end.monster_barring_type_text);
        this.lakatos.use_counterexample_barring = this.front_end.use_counterexample_barring_check.getState();
        this.all_production_rules = new Vector();
        this.production_rules = new Vector();
        this.unary_rules = new Vector();
        this.binary_rules = new Vector();
        this.all_production_rules.addElement(this.arithmeticb);
        this.all_production_rules.addElement(this.exists);
        this.all_production_rules.addElement(this.equal);
        this.all_production_rules.addElement(this.entity_disjunct);
        this.all_production_rules.addElement(this.embed_graph);
        this.all_production_rules.addElement(this.embed_algebra);
        this.all_production_rules.addElement(this.forall);
        this.all_production_rules.addElement(this.match);
        this.all_production_rules.addElement(this.size);
        this.all_production_rules.addElement(this.compose);
        this.all_production_rules.addElement(this.disjunct);
        this.all_production_rules.addElement(this.negate);
        this.all_production_rules.addElement(this.record);
        this.all_production_rules.addElement(this.split);
        if (this.front_end.exists_check.getState()) {
            this.unary_rules.addElement(this.exists);
            this.production_rules.addElement(this.exists);
        }

        if (this.front_end.arithmeticb_check.getState()) {
            this.binary_rules.addElement(this.arithmeticb);
            this.production_rules.addElement(this.arithmeticb);
        }

        if (this.front_end.equal_check.getState()) {
            this.unary_rules.addElement(this.equal);
            this.production_rules.addElement(this.equal);
        }

        if (this.front_end.embed_graph_check.getState()) {
            this.unary_rules.addElement(this.embed_graph);
            this.production_rules.addElement(this.embed_graph);
        }

        if (this.front_end.embed_algebra_check.getState()) {
            this.unary_rules.addElement(this.embed_algebra);
            this.production_rules.addElement(this.embed_algebra);
        }

        if (this.front_end.forall_check.getState()) {
            this.binary_rules.addElement(this.forall);
            this.production_rules.addElement(this.forall);
        }

        if (this.front_end.match_check.getState()) {
            this.unary_rules.addElement(this.match);
            this.production_rules.addElement(this.match);
        }

        if (this.front_end.size_check.getState()) {
            this.unary_rules.addElement(this.size);
            this.production_rules.addElement(this.size);
        }

        if (this.front_end.compose_check.getState()) {
            this.binary_rules.addElement(this.compose);
            this.production_rules.addElement(this.compose);
        }

        if (this.front_end.disjunct_check.getState()) {
            this.binary_rules.addElement(this.disjunct);
            this.production_rules.addElement(this.disjunct);
        }

        if (this.front_end.negate_check.getState()) {
            this.binary_rules.addElement(this.negate);
            this.production_rules.addElement(this.negate);
        }

        if (this.front_end.record_check.getState()) {
            this.unary_rules.addElement(this.record);
            this.production_rules.addElement(this.record);
        }

        if (this.front_end.split_check.getState()) {
            this.unary_rules.addElement(this.split);
            this.production_rules.addElement(this.split);
        }

        this.arithmeticb.arity_limit = this.valueFromIntField(this.front_end.arithmeticb_arity_limit_text);
        this.equal.arity_limit = this.valueFromIntField(this.front_end.equal_arity_limit_text);
        this.entity_disjunct.arity_limit = this.valueFromIntField(this.front_end.entity_disjunct_arity_limit_text);
        this.exists.arity_limit = this.valueFromIntField(this.front_end.exists_arity_limit_text);
        this.embed_graph.arity_limit = this.valueFromIntField(this.front_end.embed_graph_arity_limit_text);
        this.embed_algebra.arity_limit = this.valueFromIntField(this.front_end.embed_algebra_arity_limit_text);
        this.forall.arity_limit = this.valueFromIntField(this.front_end.forall_arity_limit_text);
        this.match.arity_limit = this.valueFromIntField(this.front_end.match_arity_limit_text);
        this.size.arity_limit = this.valueFromIntField(this.front_end.size_arity_limit_text);
        this.compose.arity_limit = this.valueFromIntField(this.front_end.compose_arity_limit_text);
        this.disjunct.arity_limit = this.valueFromIntField(this.front_end.disjunct_arity_limit_text);
        this.negate.arity_limit = this.valueFromIntField(this.front_end.negate_arity_limit_text);
        this.record.arity_limit = this.valueFromIntField(this.front_end.record_arity_limit_text);
        this.split.arity_limit = this.valueFromIntField(this.front_end.split_arity_limit_text);
        this.arithmeticb.tier = this.valueFromIntField(this.front_end.arithmeticb_tier_text);
        this.equal.tier = this.valueFromIntField(this.front_end.equal_tier_text);
        this.exists.tier = this.valueFromIntField(this.front_end.exists_tier_text);
        this.entity_disjunct.tier = this.valueFromIntField(this.front_end.entity_disjunct_tier_text);
        this.embed_graph.tier = this.valueFromIntField(this.front_end.embed_graph_tier_text);
        this.embed_algebra.tier = this.valueFromIntField(this.front_end.embed_algebra_tier_text);
        this.forall.tier = this.valueFromIntField(this.front_end.forall_tier_text);
        this.match.tier = this.valueFromIntField(this.front_end.match_tier_text);
        this.size.tier = this.valueFromIntField(this.front_end.size_tier_text);
        this.compose.tier = this.valueFromIntField(this.front_end.compose_tier_text);
        this.disjunct.tier = this.valueFromIntField(this.front_end.disjunct_tier_text);
        this.negate.tier = this.valueFromIntField(this.front_end.negate_tier_text);
        this.record.tier = this.valueFromIntField(this.front_end.record_tier_text);
        this.split.tier = this.valueFromIntField(this.front_end.split_tier_text);
        this.split.allowed_values.removeAllElements();
        String[] var1 = this.front_end.split_values_list.getSelectedItems();

        int var2;
        for(var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2].substring(0, 4).equals("all:")) {
                this.split.empirical_allowed_types.addElement(var1[var2].substring(5, var1[var2].length() - 1));
            } else {
                this.split.allowed_values.addElement(var1[var2]);
            }
        }

        if (!this.front_end.coverage_categorisation_text.getText().equals("")) {
            this.measure_concept.coverage_categorisation = new Categorisation(this.front_end.coverage_categorisation_text.getText());
        }

        if (!this.front_end.segregation_categorisation_text.getText().equals("")) {
            this.agenda.segregation_categorisation = new Categorisation(this.front_end.segregation_categorisation_text.getText());
        }

        this.agenda.use_segregated_search = this.front_end.segregated_search_check.getState();
        if (!this.front_end.gold_standard_categorisation_text.getText().equals("")) {
            this.gold_standard_categorisation = new Categorisation(this.front_end.gold_standard_categorisation_text.getText());
            this.measure_concept.gold_standard_categorisation = this.gold_standard_categorisation;
            this.measure_concept.positives = (Vector)this.gold_standard_categorisation.elementAt(0);
            this.measure_concept.negatives = (Vector)this.gold_standard_categorisation.elementAt(1);
            this.positives_for_learning = (Vector)this.gold_standard_categorisation.elementAt(0);
            this.negatives_for_learning = (Vector)this.gold_standard_categorisation.elementAt(1);

            for(var2 = 0; var2 < this.measure_concept.positives.size(); ++var2) {
                this.agenda.positives_for_forward_lookahead.addElement((String)this.measure_concept.positives.elementAt(var2));
            }

            for(var2 = 0; var2 < this.measure_concept.negatives.size(); ++var2) {
                this.agenda.negatives_for_forward_lookahead.addElement((String)this.measure_concept.negatives.elementAt(var2));
            }

            String var3 = this.measure_concept.gold_standard_categorisation.toString();
            if (!this.required_flat_categorisations.contains(var3)) {
                this.required_categorisations.addElement(this.measure_concept.gold_standard_categorisation);
                this.required_flat_categorisations.addElement(var3);
            }

            this.agenda.object_types_to_learn = this.front_end.object_types_to_learn_text.getText().trim();
            this.measure_concept.object_types_to_learn = this.front_end.object_types_to_learn_text.getText().trim();
        }

        this.agenda.makeChanges();
    }

    public void initialiseTheory() {
        this.getFrontEndSettings();
        if (!this.measure_concept.gold_standard_categorisation.isEmpty()) {
            this.measure_concept.getInvDiscPairs();
        }

        this.front_end.concept_list.removeAll();
        this.front_end.entity_list.removeAll();
        this.front_end.force_primary_concept_list.removeAll();
        this.front_end.force_secondary_concept_list.removeAll();
        this.concepts.removeAllElements();
        String var1 = this.front_end.keep_equivalences_file_text.getText();
        String var2 = this.front_end.keep_non_exists_file_text.getText();
        String var3 = this.front_end.keep_implications_file_text.getText();
        String var4 = this.front_end.keep_implicates_file_text.getText();
        String var5 = this.front_end.keep_prime_implicates_file_text.getText();
        String var6 = this.front_end.keep_equivalences_condition_text.getText();
        String var7 = this.front_end.keep_non_exists_condition_text.getText();
        String var8 = this.front_end.keep_implications_condition_text.getText();
        String var9 = this.front_end.keep_implicates_condition_text.getText();
        String var10 = this.front_end.keep_prime_implicates_condition_text.getText();
        this.storage_handler.openStorageStream("instanceof Equivalence", var1);
        this.storage_handler.openStorageStream("instanceof NonExists", var2);
        this.storage_handler.openStorageStream("instanceof Implication", var3);
        this.storage_handler.openStorageStream("instanceof Implicate", var4);
        this.storage_handler.openStorageStream("instanceof Implicate", var5);
        if (this.front_end.store_all_conjectures_check.getState()) {
            this.storage_handler.openStorageStream("instanceof Conjecture", this.front_end.store_all_conjectures_text.getText());
            this.explanation_handler.store_conjectures = true;
        }

        String[] var11 = this.front_end.initial_entity_list.getSelectedItems();
        this.datatable_hashtable = new Hashtable();
        this.entities.removeAllElements();
        Vector var12 = new Vector();

        int var13;
        Entity var15;
        for(var13 = 0; var13 < var11.length; ++var13) {
            String var14 = var11[var13];
            var14 = var14.substring(var14.indexOf(":") + 1, var14.length());
            var15 = new Entity(var14);
            var12.addElement(var14);
            this.entities.addElement(var15);
            this.front_end.entity_list.addItem(var14);
            this.entity_names.addElement(var14);
        }

        for(var13 = 0; var13 < this.domain_concepts.size(); ++var13) {
            if (this.front_end.initial_concepts_list.isIndexSelected(var13)) {
                Concept var27 = (Concept)this.domain_concepts.elementAt(var13);
                this.concepts.addElement(var27);
                var27.specifications = this.specification_handler.addSpecifications(var27.specifications);
                var27.position_in_theory = this.concepts.size() - 1;
                this.front_end.concept_list.addItem(var27.id);
                this.front_end.force_primary_concept_list.addItem(var27.id);
                this.front_end.force_secondary_concept_list.addItem(var27.id);
            }
        }

        String[] var26 = this.front_end.counterexample_list.getSelectedItems();
        this.hold_back_checker.counterexamples.clear();
        Vector var28 = new Vector();

        for(int var29 = 0; var29 < var26.length; ++var29) {
            String var16 = var26[var29];
            String var17 = var16.substring(0, var16.indexOf(":"));
            var16 = var16.substring(var16.indexOf(":") + 1, var16.length());
            this.hold_back_checker.addCounterexample(var17, var16);
            var28.addElement(var16);
        }

        var15 = null;

        int var18;
        int var30;
        Concept var31;
        for(var30 = 0; var30 < this.concepts.size(); ++var30) {
            var31 = (Concept)this.concepts.elementAt(var30);
            if (var31.id.equals("int001")) {
                ;
            }

            for(var18 = 0; var18 < var31.datatable.size(); ++var18) {
                Row var19 = (Row)var31.datatable.elementAt(var18);
                if (!var12.contains(var19.entity)) {
                    var31.datatable.removeElementAt(var18);
                    --var18;
                }

                if (var28.contains(var19.entity) && !var12.contains(var19.entity)) {
                    var31.additional_datatable.addElement(var19);
                }
            }
        }

        this.measure_concept.equiv_conj_scores_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.ne_conj_scores_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.imp_conj_scores_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.pi_conj_scores_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.equiv_conj_nums_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.ne_conj_nums_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.imp_conj_nums_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.pi_conj_nums_hashtable.put(new Double(0.0D), new Vector());
        this.measure_concept.sorted_equiv_conj_scores = new Vector();
        this.measure_concept.sorted_ne_conj_scores = new Vector();
        this.measure_concept.sorted_imp_conj_scores = new Vector();
        this.measure_concept.sorted_pi_conj_scores = new Vector();
        this.measure_concept.sorted_equiv_conj_scores.addElement(new Double(0.0D));
        this.measure_concept.sorted_ne_conj_scores.addElement(new Double(0.0D));
        this.measure_concept.sorted_imp_conj_scores.addElement(new Double(0.0D));
        this.measure_concept.sorted_pi_conj_scores.addElement(new Double(0.0D));
        this.measure_concept.sorted_equiv_conj_nums = new Vector();
        this.measure_concept.sorted_ne_conj_nums = new Vector();
        this.measure_concept.sorted_imp_conj_nums = new Vector();
        this.measure_concept.sorted_pi_conj_nums = new Vector();
        this.measure_concept.sorted_equiv_conj_nums.addElement(new Double(0.0D));
        this.measure_concept.sorted_ne_conj_nums.addElement(new Double(0.0D));
        this.measure_concept.sorted_imp_conj_nums.addElement(new Double(0.0D));
        this.measure_concept.sorted_pi_conj_nums.addElement(new Double(0.0D));
        this.categorisations = new Vector();

        Vector var20;
        Vector var22;
        Concept var34;
        Vector var35;
        for(var30 = 0; var30 < this.concepts.size(); ++var30) {
            var31 = (Concept)this.concepts.elementAt(var30);
            var31.entity_concept = this.conceptFromName(var31.object_type);
            var31.generalisations = new Vector();

            for(var18 = 0; var18 < this.concepts.size(); ++var18) {
                var34 = (Concept)this.concepts.elementAt(var18);
                if (var34.isGeneralisationOf(var31) == 0) {
                    var31.generalisations.addElement(var34);
                }
            }

            this.addCategorisation(var31);
            this.measure_concept.measureConcept(var31, this.concepts, true);
            Vector var36 = (Vector)this.measure_concept.equiv_conj_scores_hashtable.get(new Double(0.0D));
            var36.addElement(var31);
            var35 = (Vector)this.measure_concept.ne_conj_scores_hashtable.get(new Double(0.0D));
            var35.addElement(var31);
            var20 = (Vector)this.measure_concept.imp_conj_scores_hashtable.get(new Double(0.0D));
            var20.addElement(var31);
            Vector var21 = (Vector)this.measure_concept.pi_conj_scores_hashtable.get(new Double(0.0D));
            var21.addElement(var31);
            var22 = (Vector)this.measure_concept.equiv_conj_nums_hashtable.get(new Double(0.0D));
            var22.addElement(var31);
            Vector var23 = (Vector)this.measure_concept.ne_conj_nums_hashtable.get(new Double(0.0D));
            var23.addElement(var31);
            Vector var24 = (Vector)this.measure_concept.imp_conj_nums_hashtable.get(new Double(0.0D));
            var24.addElement(var31);
            Vector var25 = (Vector)this.measure_concept.pi_conj_nums_hashtable.get(new Double(0.0D));
            var25.addElement(var31);
        }

        String[] var32 = this.front_end.highlight_list.getSelectedItems();

        int var33;
        for(var33 = 0; var33 < var32.length; ++var33) {
            String var37 = var32[var33].substring(0, var32[var33].indexOf(":"));
            var34 = this.getConcept(var37);
            var34.highlight_score = 1.0D;
        }

        Concept var39;
        for(var33 = 0; var33 < this.concepts.size(); ++var33) {
            var39 = (Concept)this.concepts.elementAt(var33);
            this.react.reactTo(var39, "user_given_concept");
        }

        for(var33 = 0; var33 < this.concepts.size(); ++var33) {
            var39 = (Concept)this.concepts.elementAt(var33);
            this.agenda.addConcept(var39, this.concepts, this);
            String var38 = var39.datatable.firstTuples();
            var20 = (Vector)this.datatable_hashtable.get(var38);
            if (var20 == null) {
                var20 = new Vector();
                this.datatable_hashtable.put(var38, var20);
            }

            var20.addElement(var39);
        }

        for(var33 = 0; var33 < this.specifications.size(); ++var33) {
            Specification var41 = (Specification)this.specifications.elementAt(var33);
            Relation var40 = (Relation)var41.relations.elementAt(0);
            if (var40.name.equals("integer")) {
                this.size.integer_relation = var40;
            }
        }

        this.explanation_handler.initialiseExplainers(this);

        for(var33 = 0; var33 < this.concepts.size(); ++var33) {
            var39 = (Concept)this.concepts.elementAt(var33);
            if (this.make_subsumption_implications) {
                var35 = this.makeSubsumptionConjectures(var39);

                for(int var42 = 0; var42 < var35.size(); ++var42) {
                    Entity var44 = (Entity)var35.elementAt(var42);
                    this.addNewEntityToTheory(var44, var39.domain, "initial");
                }
            }

            if (var39.datatable.isEmpty() && this.make_non_exists_from_empty) {
                NonExists var43 = new NonExists(var39, Integer.toString(this.conjectures.size()));
                var43.step = new Step();
                var43.when_constructed = 0L;
                var20 = this.addNonExistsToTheory(var43, var39);

                for(int var45 = 0; var45 < var20.size(); ++var45) {
                    Entity var47 = (Entity)var20.elementAt(var45);
                    this.addNewEntityToTheory(var47, var39.domain, "initial");
                }
            }

            if (this.make_equivalences_from_equality) {
                for(int var46 = var33 + 1; var46 < this.concepts.size(); ++var46) {
                    Concept var48 = (Concept)this.concepts.elementAt(var46);
                    if (var48.arity == var39.arity && var39.datatable.isIdenticalTo(this, var48.datatable)) {
                        Equivalence var49 = new Equivalence(var39, var48, Integer.toString(this.conjectures.size()));
                        var49.step = new Step();
                        var49.when_constructed = 0L;
                        var22 = this.addEquivalenceToTheory(var49, var39, var48);

                        for(int var50 = 0; var50 < var22.size(); ++var50) {
                            Entity var51 = (Entity)var22.elementAt(var50);
                            this.addNewEntityToTheory(var51, var39.domain, "initial");
                        }
                    }
                }
            }
        }

    }

    public Vector addNonExistsToTheory(NonExists var1, Concept var2) {
        Vector var3 = new Vector();
        if (this.keep_non_existences) {
            this.addToTimer("5.4.1 Checking the segregration status of a non-existence conjecture");
            var1.checkSegregation(this.agenda);
            this.non_existences.addElement(var1);
            this.addToTimer("5.4.1 Handling the storage of a non-existence conjecture");
            this.conjectures.addElement(var1);
        }

        this.addToTimer("5.4.3 Attempting to prove non-existence conjecture");
        boolean var4 = false;
        this.explanation_handler.explainConjecture(var1, this, "5.4.3");
        if (!var1.proof_status.equals("proved")) {
            var3 = (Vector)var1.counterexamples.clone();
        }

        this.addToTimer("5.4.4 Updating front end with non-existence conjecture");
        if (this.keep_non_existences) {
            this.updateFrontEnd((Conjecture)var1);
        }

        if (this.extract_implicates_from_non_exists && !var1.proof_status.equals("disproved")) {
            this.addToTimer("5.4.5 Extracting implicates from a non-existence conjecture");
            Vector var5 = var1.implicates(this.concepts);

            for(int var6 = 0; var6 < var5.size(); ++var6) {
                Implicate var7 = (Implicate)var5.elementAt(var6);
                var1.child_conjectures.addElement(var7);
                var7.parent_conjectures.addElement(var1);
                var7.when_constructed = (long)this.seconds_elapsed;
            }

            this.addToTimer("5.4.6 Adding extracted implicates from a non-existence conjecture");
            Vector var9 = this.addImplicates(var5, var1, "5.4.6");

            for(int var10 = 0; var10 < var9.size(); ++var10) {
                var3.addElement(var9.elementAt(var10));
            }
        }

        this.addToTimer("5.4.7 Measuring a non-existence conjecture");
        this.measure_conjecture.measureConjecture(var1, this.non_existences, this);
        this.addToTimer("5.4.8 Measuring the non-existence score of parent concepts");
        boolean var8 = this.measure_concept.updateNeConjectureScore(var2.parents, var1);
        if (var8) {
            this.addToTimer("5.4.9 Re-ordering the agenda after the addition of a non-existence conjecture");
            this.agenda.orderConcepts();
        }

        return var3;
    }

    public Vector addEquivalenceToTheory(Equivalence var1, Concept var2, Concept var3) {
        this.addToTimer("6.14.1 Adding equivalence conjecture to the explanation handler");
        if (this.keep_equivalences) {
            this.explanation_handler.addConjecture(var1);
        }

        Vector var4 = new Vector();
        boolean var5 = false;
        if (this.keep_equivalences) {
            this.addToTimer("6.14.2 Checking the segregation status of an equivalence conjecture");
            var1.checkSegregation(this.agenda);
            this.equivalences.addElement(var1);
            this.addToTimer("6.14.3 Handling the storage of an equivalence conjecture");
            this.conjectures.addElement(var1);
        }

        this.addToTimer("6.14.4 Measuring an equivalence conjecture");
        this.measure_conjecture.measureConjecture(var1, this.equivalences);
        this.addToTimer("6.14.5 Attempting to settle equivalence conjecture");
        this.explanation_handler.explainConjecture(var1, this, "6.14.5");
        if (!var1.proof_status.equals("proved")) {
            var4 = (Vector)var1.counterexamples.clone();
        }

        if (this.keep_equivalences) {
            this.addToTimer("6.14.6 Adding equivalence conjecture to front end");
            this.updateFrontEnd((Conjecture)var1);
        }

        this.addToTimer("6.14.7 Making additional equivalences from the alternative concepts");
        if (var4.isEmpty()) {
            Vector var6 = new Vector();
            var6.addElement(var1);
            this.addToTimer("6.14.8 Updating the equivalence conjecture score of a concept");
            var5 = this.measure_concept.updateEquivConjectureScore(var2, var1);
            int var7;
            int var10;
            if (this.make_equivalences_from_combination) {
                for(var7 = 0; var7 < var2.conjectured_equivalent_concepts.size(); ++var7) {
                    this.addToTimer("6.14.9 Making equivalences from combination");
                    Concept var8 = (Concept)var2.conjectured_equivalent_concepts.elementAt(var7);
                    Equivalence var9 = new Equivalence(var8, var3, Integer.toString(this.numberOfConjectures()));
                    this.explanation_handler.explainConjecture(var9, this, "6.14.9");
                    if (this.keep_equivalences) {
                        this.addToTimer("6.14.10 Check the segregation status of an equivalence conjecture from combination");
                        var9.checkSegregation(this.agenda);
                        this.equivalences.addElement(var9);
                        this.addToTimer("6.14.11 Dealing with the storage of an equivalence conjecture from combination");
                        this.conjectures.addElement(var9);
                        this.addToTimer("6.14.12 Measuring an equivalence conjecture from combination");
                        this.measure_conjecture.measureConjecture(var9, this.equivalences);
                        this.addToTimer("6.14.13 Updating the front end with equivalence conjecture from combination");
                        this.updateFrontEnd((Conjecture)var9);
                    }

                    var6.addElement(var9);
                    if (!var9.proof_status.equals("proved")) {
                        for(var10 = 0; var10 < var9.counterexamples.size(); ++var10) {
                            Entity var11 = (Entity)var9.counterexamples.elementAt(var10);
                            var4.addElement(var11);
                        }
                    }

                    this.addToTimer("6.14.14 Measuring the equivalence score of equivalent concepts");
                    boolean var16 = this.measure_concept.updateEquivConjectureScore(var2, var9);
                    if (var16) {
                        var5 = true;
                    }
                }
            }

            Equivalence var13;
            if (this.extract_implications_from_equivalences && this.keep_implications) {
                this.addToTimer("6.14.15 Extracting implications from an equivalence conjecture");

                for(var7 = 0; var7 < var6.size(); ++var7) {
                    var13 = (Equivalence)var6.elementAt(var7);
                    Implication var14 = new Implication(var13.lh_concept, var13.rh_concept, Integer.toString(this.conjectures.size()));
                    var14.when_constructed = (long)this.seconds_elapsed;
                    var14.checkSegregation(this.agenda);
                    this.conjectures.addElement(var14);
                    Implication var17 = new Implication(var13.rh_concept, var13.lh_concept, Integer.toString(this.conjectures.size()));
                    var17.checkSegregation(this.agenda);
                    var17.when_constructed = (long)this.seconds_elapsed;
                    if (var13.proof_status.equals("proved")) {
                        var14.proof_status = "proved";
                        var14.explained_by = "Proof of parent";
                        var14.proof = "Proof of parent:\n" + var13.proof;
                        var17.proof_status = "proved";
                        var17.explained_by = "Proof of parent";
                        var17.proof = "Proof of parent:\n" + var13.proof;
                    }

                    this.conjectures.addElement(var17);
                    this.implications.addElement(var14);
                    this.implications.addElement(var17);
                    this.measure_conjecture.measureConjecture(var14, this.implications);
                    this.measure_conjecture.measureConjecture(var17, this.implications);
                    this.updateFrontEnd((Conjecture)var14);
                    this.updateFrontEnd((Conjecture)var17);
                }
            }

            if (this.extract_implicates_from_equivalences) {
                for(var7 = 0; var7 < var6.size(); ++var7) {
                    this.addToTimer("6.14.16 Extracting implicates from an equivalence conjecture");
                    var13 = (Equivalence)var6.elementAt(var7);
                    Vector var15 = var1.implicates(this.specification_handler);

                    for(var10 = 0; var10 < var15.size(); ++var10) {
                        Implicate var18 = (Implicate)var15.elementAt(var10);
                        var13.child_conjectures.addElement(var18);
                        var18.parent_conjectures.addElement(var13);
                    }

                    this.addToTimer("6.14.17 Adding implicates from an equivalence conjecture to the theory");
                    Vector var20 = this.addImplicates(var15, var13, "6.14.17");

                    for(int var19 = 0; var19 < var20.size(); ++var19) {
                        Entity var12 = (Entity)var20.elementAt(var19);
                        var4.addElement(var12);
                    }
                }
            }
        }

        if (var5) {
            this.addToTimer("6.14.18 Re-ordering the agenda after adding an equivalence conjecture");
            this.agenda.orderConcepts();
        }

        return var4;
    }

    public void keyPressed(KeyEvent var1) {
        if (var1.getSource() == this.front_end.predict_entity_text && var1.getKeyCode() == 10) {
            for(int var2 = 0; var2 < this.predict.read_in_concepts.size(); ++var2) {
                Concept var3 = (Concept)this.predict.read_in_concepts.elementAt(var2);
                if (var3.object_type.equals(this.front_end.predict_entity_type_choice.getSelectedItem())) {
                    Tuples var4 = var3.datatable.rowWithEntity(this.front_end.predict_entity_text.getText()).tuples;

                    for(int var5 = 0; var5 < this.front_end.predict_names_buttons_vector.size(); ++var5) {
                        String var6 = ((Button)this.front_end.predict_names_buttons_vector.elementAt(var5)).getLabel();
                        if (var6.equals(var3.name)) {
                            TextField var7 = (TextField)this.front_end.predict_values_texts_vector.elementAt(var5);
                            var7.setText(var4.toString());
                        }
                    }
                }
            }
        }

    }

    public void itemStateChanged(ItemEvent var1) {
        if (var1.getSource() == this.front_end.record_macro_check) {
            this.front_end.main_frame.macro_text_layout.show(this.front_end.main_frame.macro_centre_panel, "macro_panel");
        }

        String var2;
        if (var1.getSource() == this.front_end.macro_list) {
            this.front_end.macro_text.setText("");
            var2 = this.input_files_directory + this.front_end.macro_list.getSelectedItem();
            this.front_end.main_frame.macro_text_layout.show(this.front_end.main_frame.macro_centre_panel, "macro_panel");
            this.loadMacro(var2);
        }

        if (var1.getSource() == this.front_end.statistics_type_list) {
            this.statistics_handler.addChoices(this.front_end.statistics_type_list.getSelectedItem(), this.front_end.statistics_choice_list, this.front_end.statistics_subchoice_list, this.front_end.statistics_methods_list, this.timer);
        }

        Concept var3;
        if (var1.getSource() == this.front_end.all_ordered_concept_list) {
            var2 = this.front_end.all_ordered_concept_list.getSelectedItem();
            var3 = this.getConcept(var2);
            this.front_end.agenda_concept_text.setText(var2 + ". " + var3.writeDefinitionWithStartLetters("otter"));
        }

        if (var1.getSource() == this.front_end.live_ordered_concept_list) {
            var2 = this.front_end.live_ordered_concept_list.getSelectedItem();
            var3 = this.getConcept(var2);
            this.front_end.agenda_concept_text.setText(var2 + ". " + var3.writeDefinitionWithStartLetters("otter"));
        }

        String var7;
        if (var1.getSource() == this.front_end.reactions_list) {
            var2 = this.front_end.reactions_list.getSelectedItem();
            this.front_end.reaction_name_text.setText(var2);
            var7 = this.input_files_directory + var2;
            this.front_end.loadReaction(var7);
        }

        if (var1.getSource() == this.front_end.conjecture_list) {
            var2 = this.front_end.conjecture_list.getSelectedItem();
            this.conjecture_chosen_from_front_end = this.getConjecture(var2);
            this.front_end.updateConjecture(this.conjecture_chosen_from_front_end);
        }

        if (var1.getSource() == this.front_end.quick_macro_choice) {
            var2 = this.front_end.quick_macro_choice.getSelectedItem();
            if (!var2.equals("macro")) {
                this.front_end.macro_text.setText("");
                var7 = this.input_files_directory + var2;
                this.loadMacro(var7);
                this.playMacro();
                this.front_end.save_macro_text.setText(var2);
            }
        }

        if (var1.getSource() == this.front_end.force_prodrule_list) {
            this.guider.loadParameterisations(this);
            this.guider.writeForceString(this);
        }

        if (var1.getSource() == this.front_end.force_primary_concept_list) {
            this.front_end.deselectAll(this.front_end.force_secondary_concept_list);
            this.front_end.deselectAll(this.front_end.force_prodrule_list);
            this.front_end.force_parameter_list.removeAll();
            this.guider.writeForceString(this);
        }

        if (var1.getSource() == this.front_end.force_secondary_concept_list) {
            this.front_end.deselectAll(this.front_end.force_prodrule_list);
            this.front_end.force_parameter_list.removeAll();
            this.guider.writeForceString(this);
        }

        if (var1.getSource() == this.front_end.force_parameter_list) {
            this.guider.writeForceString(this);
        }

        if (var1.getSource() == this.front_end.predict_input_files_choice) {
            var2 = this.input_files_directory + this.front_end.predict_input_files_choice.getSelectedItem();
            Vector var8 = this.reader.readFile(var2, "");
            this.predict.read_in_concepts = (Vector)var8.elementAt(0);
            this.predict.relations = (Vector)var8.elementAt(5);
        }

        if (var1.getSource() == this.front_end.predict_entity_type_choice) {
            for(int var9 = 0; var9 < this.front_end.predict_names_buttons_vector.size(); ++var9) {
                Button var10 = (Button)this.front_end.predict_names_buttons_vector.elementAt(var9);
                var10.setLabel("");
            }

            var2 = this.front_end.predict_entity_type_choice.getSelectedItem();
            int var12 = 0;

            for(int var4 = 0; var4 < this.concepts.size(); ++var4) {
                Concept var5 = (Concept)this.concepts.elementAt(var4);
                if (var5.is_user_given && var5.object_type.equals(var2)) {
                    Button var6 = (Button)this.front_end.predict_names_buttons_vector.elementAt(var12);
                    var6.setLabel(var5.name);
                    ++var12;
                }
            }
        }

        if (var1.getSource() == this.front_end.domain_list) {
            var2 = this.front_end.domain_list.getSelectedItem();
            if (var2.indexOf(".") >= 0) {
                var2 = var2.substring(0, var2.indexOf("."));
            }

            var7 = this.input_files_directory + this.front_end.domain_list.getSelectedItem();
            Vector var11 = this.reader.readFile(var7, "");
            this.front_end.addDomainInformation(var11);
            Vector var13 = (Vector)var11.elementAt(0);

            int var14;
            for(var14 = 0; var14 < var13.size(); ++var14) {
                this.domain_concepts.addElement((Concept)var13.elementAt(var14));
            }

            for(var14 = 0; var14 < this.front_end.initial_concepts_list.getItemCount(); ++var14) {
                this.front_end.initial_concepts_list.select(var14);
            }

            for(var14 = 0; var14 < this.front_end.initial_entity_list.getItemCount(); ++var14) {
                this.front_end.initial_entity_list.select(var14);
            }
        }

    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.front_end.add_proof_tables_to_macro_button) {
            this.macro_handler.saveTableToMacro(this.front_end.axiom_table);
            this.macro_handler.saveTableToMacro(this.front_end.algebra_table);
            this.macro_handler.saveTableToMacro(this.front_end.file_prover_table);
            this.macro_handler.saveTableToMacro(this.front_end.other_prover_table);
        }

        String var3;
        if (var1.getSource() == this.front_end.pseudo_code_run_button) {
            Hashtable var2 = new Hashtable();
            var2.put("theory", this);
            var3 = this.front_end.pseudo_code_input_text.getText();
            this.pseudo_code_interpreter.runPseudoCode(var3, var2);
        }

        Concept var12;
        if (var1.getSource() == this.front_end.save_concept_construction_button) {
            var12 = this.getConcept(this.front_end.save_concept_construction_concept_id_text.getText().trim());
            if (!var12.id.equals("")) {
                var3 = this.front_end.save_concept_construction_filename_text.getText().trim();
                this.storage_handler.writeConstructionToFile(var3, var12);
            }
        }

        String var13;
        if (var1.getSource() == this.front_end.build_concept_button) {
            var13 = this.front_end.build_concept_filename_text.getText().trim();
            this.storage_handler.buildConstructionFromFile(var13, this);
        }

        if (var1.getSource() == this.front_end.save_conjecture_construction_button) {
            Equivalence var14 = (Equivalence)this.getConjecture(this.front_end.save_conjecture_construction_conjecture_id_text.getText().trim());
            if (!var14.id.equals("")) {
                var3 = this.front_end.save_conjecture_construction_filename_text.getText().trim();
                this.storage_handler.writeConstructionToFile(var3, var14);
            }
        }

        if (var1.getSource() == this.front_end.build_conjecture_button) {
            var13 = this.front_end.build_conjecture_filename_text.getText().trim();
            this.storage_handler.buildConstructionFromFile(var13, this);
        }

        int var4;
        int var5;
        if (var1.getSource() == this.front_end.statistics_compile_button) {
            Vector var15 = new Vector();
            String[] var16 = this.front_end.statistics_choice_list.getSelectedItems();

            for(var4 = 0; var4 < var16.length; ++var4) {
                if (var16[var4].equals("id")) {
                    var15.insertElementAt(var16[var4], 0);
                } else {
                    var15.addElement(var16[var4]);
                }
            }

            String[] var18 = this.front_end.statistics_subchoice_list.getSelectedItems();

            for(var5 = 0; var5 < var18.length; ++var5) {
                var15.addElement(var18[var5]);
            }

            this.statistics_handler.compileData(this.front_end.statistics_type_list.getSelectedItem(), var15, this.front_end.statistics_average_check.getState(), this.front_end.statistics_count_check.getState(), this, this.front_end.statistics_prune_text.getText(), this.front_end.statistics_calculation_text.getText(), this.front_end.statistics_sort_text.getText(), this.front_end.statistics_output_file_text.getText());
        }

        if (var1.getSource() == this.front_end.statistics_plot_button) {
            this.graph_handler.plotGraphs(this.front_end.statistics_seperate_graphs_check.getState());
        }

        String var6;
        Vector var7;
        Vector var17;
        Concept var19;
        if (var1.getSource() == this.front_end.grep_concept_button) {
            this.front_end.concept_grep_positions.clear();
            var13 = this.front_end.grep_concept_text.getText().trim();
            var17 = new Vector();

            for(var4 = 0; var4 < this.front_end.concept_list.getItemCount(); ++var4) {
                var19 = this.getConcept(this.front_end.concept_list.getItem(var4));
                var6 = var19.fullDetails("html", this.front_end.concept_formatting_list.getSelectedItems(), this.front_end.decimal_places);
                var7 = new Vector();
                if (this.text_handler.getGrepPositions(var13, var6, var7, 0)) {
                    this.front_end.concept_grep_positions.put(var19.id, var7);
                    var17.addElement(var19.id);
                }
            }

            this.front_end.concept_list.removeAll();

            for(var4 = 0; var4 < var17.size(); ++var4) {
                this.front_end.concept_list.addItem((String)var17.elementAt(var4));
            }

            this.front_end.concept_list_number.setText("number: " + Integer.toString(this.front_end.concept_list.getItemCount()));
        }

        if (var1.getSource() == this.front_end.condition_concept_button) {
            var13 = this.front_end.condition_concept_text.getText().trim();
            var17 = new Vector();

            for(var4 = 0; var4 < this.front_end.concept_list.getItemCount(); ++var4) {
                var19 = this.getConcept(this.front_end.concept_list.getItem(var4));
                if (this.reflect.checkCondition(var19, var13)) {
                    var17.addElement(var19.id);
                }
            }

            this.front_end.concept_list.removeAll();

            for(var4 = 0; var4 < var17.size(); ++var4) {
                this.front_end.concept_list.addItem((String)var17.elementAt(var4));
            }

            this.front_end.concept_list_number.setText("number: " + Integer.toString(this.front_end.concept_list.getItemCount()));
        }

        Conjecture var21;
        if (var1.getSource() == this.front_end.grep_conjecture_button) {
            this.front_end.conjecture_grep_positions.clear();
            var13 = this.front_end.grep_conjecture_text.getText().trim();
            var17 = new Vector();

            for(var4 = 0; var4 < this.front_end.conjecture_list.getItemCount(); ++var4) {
                var21 = this.getConjecture(this.front_end.conjecture_list.getItem(var4));
                var6 = var21.fullHTMLDetails(this.front_end.conjecture_formatting_list.getSelectedItems(), this.front_end.decimal_places, this.front_end.concept_formatting_list.getSelectedItems());
                var7 = new Vector();
                if (this.text_handler.getGrepPositions(var13, var6, var7, 0)) {
                    var17.addElement(var21.id);
                    this.front_end.conjecture_grep_positions.put(var21.id, var7);
                }
            }

            this.front_end.conjecture_list.removeAll();

            for(var4 = 0; var4 < var17.size(); ++var4) {
                this.front_end.conjecture_list.addItem((String)var17.elementAt(var4));
            }

            this.front_end.conjecture_list_number.setText("number: " + Integer.toString(this.front_end.conjecture_list.getItemCount()));
        }

        if (var1.getSource() == this.front_end.condition_conjecture_button) {
            var13 = this.front_end.condition_conjecture_text.getText().trim();
            var17 = new Vector();

            for(var4 = 0; var4 < this.front_end.conjecture_list.getItemCount(); ++var4) {
                var21 = this.getConjecture(this.front_end.conjecture_list.getItem(var4));
                if (this.reflect.checkCondition(var21, var13)) {
                    var17.addElement(var21.id);
                }
            }

            this.front_end.conjecture_list.removeAll();

            for(var4 = 0; var4 < var17.size(); ++var4) {
                this.front_end.conjecture_list.addItem((String)var17.elementAt(var4));
            }

            this.front_end.conjecture_list_number.setText("number: " + Integer.toString(this.front_end.conjecture_list.getItemCount()));
        }

        if (var1.getSource() == this.front_end.add_reaction_button) {
            var13 = this.front_end.reactions_list.getSelectedItem();
            this.front_end.reactions_added_list.addItem(var13);
            this.react.addReaction(var13, this.front_end.reactions_text.getText(), this);
        }

        if (var1.getSource() == this.front_end.remove_reaction_button) {
            var13 = this.front_end.reactions_added_list.getSelectedItem();
            this.front_end.reactions_added_list.remove(var13);
            this.react.removeReaction(var13);
        }

        if (var1.getSource() == this.front_end.save_reaction_button) {
            try {
                var13 = this.front_end.reaction_name_text.getText();
                if (var13 != null) {
                    var3 = this.input_files_directory + var13;
                    PrintWriter var35 = new PrintWriter(new BufferedWriter(new FileWriter(var3)));
                    var35.println(this.front_end.reactions_text.getText());
                    var35.close();
                    this.front_end.getReactionFiles();
                }
            } catch (Exception var11) {
                System.out.println(var11);
            }
        }

        int var20;
        Conjecture var22;
        if (var1.getSource() == this.front_end.test_conjecture_button) {
            var22 = this.conjecture_chosen_from_front_end;
            if (var22 != null) {
                if (!this.front_end.test_conjecture_entity2_text.getText().equals("")) {
                    var20 = this.valueFromIntField(this.front_end.test_conjecture_entity1_text);
                    var4 = this.valueFromIntField(this.front_end.test_conjecture_entity2_text);

                    for(var5 = var20; var5 <= var4; ++var5) {
                        this.front_end.test_conjecture_entity1_text.setText(Integer.toString(var5));
                        boolean var24 = var22.conjectureBrokenByEntity(Integer.toString(var5), this.concepts);
                        if (var24) {
                            this.front_end.test_conjecture_output_text.setText("counter: " + Integer.toString(var5));
                            this.front_end.test_conjecture_entity1_text.setText(Integer.toString(var20));
                            var22.proof_status = "disproved";
                            Entity var27 = new Entity(Integer.toString(var5));
                            var22.counterexamples.addElement(var27);
                            var22.explained_by = "User-given counterexample";
                            return;
                        }
                    }

                    this.front_end.test_conjecture_output_text.setText("No counter between " + Integer.toString(var20) + " and " + Integer.toString(var4));
                    this.front_end.test_conjecture_entity1_text.setText(Integer.toString(var20));
                }
            } else {
                var3 = this.front_end.test_conjecture_entity1_text.getText();
                boolean var36 = var22.conjectureBrokenByEntity(var3, this.concepts);
                if (var36) {
                    this.front_end.test_conjecture_output_text.setText("counter: " + var3);
                } else {
                    this.front_end.test_conjecture_output_text.setText("not a counterexample");
                }
            }
        }

        if (var1.getSource() == this.front_end.add_counterexamples_found_from_testing_button) {
            ;
        }

        int var23;
        if (var1.getSource() == this.front_end.re_prove_all_button) {
            var23 = 0;

            for(var20 = 0; var20 < this.conjectures.size(); ++var20) {
                this.front_end.re_prove_all_button.setLabel(Integer.toString(var20) + "/" + Integer.toString(this.conjectures.size()) + "/" + Integer.toString(var23));
                Conjecture var37 = (Conjecture)this.conjectures.elementAt(var20);
                if (!var37.proof_status.equals("proved") && !var37.proof_status.equals("axiom")) {
                    this.explanation_handler.explainConjecture(var37, this, "Re-proving");
                }

                if (var37.proof_status.equals("proved")) {
                    ++var23;
                }
            }

            this.front_end.re_prove_all_button.setLabel("Re-prove all");
        }

        if (var1.getSource() == this.front_end.add_conjecture_as_axiom_button && this.conjecture_chosen_from_front_end != null) {
            this.explanation_handler.addConjectureAsAxiom(this.conjecture_chosen_from_front_end);
        }

        if (var1.getSource() == this.front_end.force_button) {
            try {
                Step var26 = this.guider.forceStep(this.front_end.force_string_text.getText(), this);
                this.front_end.deselectAll(this.front_end.force_primary_concept_list);
                this.front_end.deselectAll(this.front_end.force_secondary_concept_list);
                this.front_end.deselectAll(this.front_end.force_prodrule_list);
                this.front_end.force_parameter_list.removeAll();
                this.guider.writeForceString(this);
                this.front_end.force_result_text.setText(var26.result_of_step_explanation);
                if (!var26.result_of_step.id.equals("")) {
                    this.front_end.force_result_text.append("\n" + var26.result_of_step.id);
                }
            } catch (Exception var10) {
                this.front_end.force_result_text.setText("problem with step:\n");
                this.front_end.force_result_text.append(var10.getMessage() + "\n" + var10.toString());
                var10.printStackTrace();
            }
        }

        if (var1.getSource() == this.front_end.update_agenda_button) {
            var23 = new Integer(this.front_end.auto_update_text.getText());
            if (var23 < 0) {
                var23 = 0;
            }

            this.updateAgendaFrontEnd(var23);
        }

        if (var1.getSource() == this.front_end.show_forced_steps_button) {
            this.front_end.agenda_list.removeAll();

            for(var23 = 0; var23 < this.agenda.forced_steps.size(); ++var23) {
                var3 = (String)this.agenda.forced_steps.elementAt(var23);
                Step var38 = this.agenda.getStepFromString(var3, this);
                if (var38 != null) {
                    String var29 = var38.asString();
                    if (!var38.concept_arising_name.equals("")) {
                        var29 = var38.concept_arising_name + " = " + var29;
                        if (this.agenda.getConcept(var38.concept_arising_name) != null) {
                            var29 = this.agenda.getConcept(var38.concept_arising_name).id + " = " + var29;
                        }
                    }

                    this.front_end.agenda_list.addItem(var29);
                }
            }
        }

        Double var28;
        if (var1.getSource() == this.front_end.find_extra_conjectures_button) {
            var12 = this.getConcept(this.front_end.concept_list.getSelectedItem());
            if (var12 != null) {
                var28 = new Double(this.front_end.near_equivalence_percent_text.getText());
                var12.getNearEquivalences(this, this.concepts, var28);
            }

            var12.getRelevantImplicates(this);
            var12.getRelevantEquivalences(this);
            this.front_end.updateConcept();
        }

        if (var1.getSource() == this.front_end.find_extra_conjectures_button) {
            var12 = this.getConcept(this.front_end.concept_list.getSelectedItem());
            if (var12 != null) {
                var28 = new Double(this.front_end.near_implication_percent_text.getText());
                var12.getSubsumptionNearImplications(this.concepts, var28);
            }

            this.front_end.updateConcept();
        }

        if (var1.getSource() == this.front_end.draw_concept_construction_history_button) {
            var12 = this.getConcept(this.front_end.concept_list.getSelectedItem());
            if (var12 != null) {
                this.graph_handler.drawConstructionHistory(var12, this, "prolog");
            }
        }

        if (var1.getSource() == this.front_end.draw_conjecture_construction_history_button) {
            var22 = this.conjecture_chosen_from_front_end;
            if (var22 != null) {
                this.graph_handler.drawConstructionHistory(var22, this, "prolog");
            }
        }

        if (var1.getSource() == this.front_end.sot_submit_button) {
            var22 = new Conjecture();
            var3 = this.front_end.sot_conjecture_number_text.getText();
            System.out.println(var3);
            if (var3.length() > 5 && var3.substring(0, 5).equals("tptp:")) {
                String var39 = var3.substring(5, var3.length());
                this.front_end.sot_results_text.setText("Submitting " + var39 + " to SystemOnTPTP\n\n");
                this.sot_handler.submitTPTP(var39, this.front_end.sot_results_text);
                this.front_end.sot_results_text.append("---- finished ----");
            } else {
                for(var4 = 0; var4 < this.conjectures.size() && !var22.id.equals(var3); ++var4) {
                    var22 = (Conjecture)this.conjectures.elementAt(var4);
                }

                if (!var22.id.equals("")) {
                    this.front_end.sot_results_text.setText("Submitting: \n" + var22.writeConjecture("tptp") + " \nto SystemOnTPTP\n\n");
                    this.sot_handler.submit(var22, this.front_end.sot_results_text);
                    this.front_end.sot_results_text.append("---- finished ----");
                }
            }
        }

        if (var1.getSource() == this.front_end.puzzle_generate_button) {
            this.front_end.puzzle_output_text.setText("");
            var23 = new Integer(this.front_end.puzzle_ooo_choices_size_text.getText());
            var20 = new Integer(this.front_end.puzzle_nis_choices_size_text.getText());
            var4 = new Integer(this.front_end.puzzle_analogy_choices_size_text.getText());
            this.puzzler.generatePuzzles(this.front_end.puzzle_output_text, var23, var20, var4, this.concepts);
        }

        if (var1.getSource() == this.front_end.systemout_button) {
            this.debugger.runFunction(this, this.front_end.debug_parameter_text.getText());
        }

        if (var1.getSource() == this.front_end.execute_report_button) {
            this.front_end.execute_report_button.setLabel("Please Wait");
            this.report_generator.runReports(this, this.front_end.reports_to_execute_list.getSelectedItems());
            this.front_end.execute_report_button.setLabel("Execute reports");
        }

        double var31;
        if (var1.getSource() == this.front_end.predict_all_button) {
            var23 = this.valueFromIntField(this.front_end.predict_max_steps_text);
            var31 = this.valueFromDoubleField(this.front_end.predict_min_percent_text) / 100.0D;
            this.predict.use_negation = this.front_end.predict_use_negations_check.getState();
            this.predict.use_equivalences = this.front_end.predict_use_equivalences_check.getState();
            this.predict.makeAllPredictions(this.front_end.predict_method_choice.getSelectedItem(), var31, this.front_end.predict_entity_type_choice.getSelectedItem(), this.concepts, this.front_end.predict_names_buttons_vector, this.front_end.predict_values_texts_vector, this.front_end.predict_explanation_list, var23, false);
        }

        if (var1.getSource() == this.front_end.predict_incremental_steps_button) {
            var23 = this.valueFromIntField(this.front_end.predict_max_steps_text);
            var31 = this.valueFromDoubleField(this.front_end.predict_min_percent_text) / 100.0D;
            this.predict.use_negation = this.front_end.predict_use_negations_check.getState();
            this.predict.use_equivalences = this.front_end.predict_use_equivalences_check.getState();
            this.predict.makeIncrementalPredictions(this.front_end.predict_method_choice.getSelectedItem(), var31, this.front_end.predict_entity_type_choice.getSelectedItem(), this.concepts, this.front_end.predict_names_buttons_vector, this.front_end.predict_values_texts_vector, this.front_end.predict_explanation_list, var23, this.steps_taken);
        }

        for(var23 = 0; var23 < this.front_end.predict_names_buttons_vector.size(); ++var23) {
            Button var34 = (Button)this.front_end.predict_names_buttons_vector.elementAt(var23);
            var4 = this.valueFromIntField(this.front_end.predict_max_steps_text);
            double var32 = this.valueFromDoubleField(this.front_end.predict_min_percent_text) / 100.0D;
            this.predict.use_negation = this.front_end.predict_use_negations_check.getState();
            this.predict.use_equivalences = this.front_end.predict_use_equivalences_check.getState();
            if (var1.getSource() == var34) {
                TextField var30 = (TextField)this.front_end.predict_values_texts_vector.elementAt(var23);
                var30.setText("");
                Vector var8 = this.predict.getNames(this.front_end.predict_names_buttons_vector);
                Vector var9 = this.predict.getValues(this.front_end.predict_values_texts_vector, this.front_end.predict_names_buttons_vector);
                this.predict.makePredictions(this.front_end.predict_method_choice.getSelectedItem(), this.front_end.predict_entity_text.getText(), var8, var9, var32, this.front_end.predict_entity_type_choice.getSelectedItem(), this.concepts, this.front_end.predict_names_buttons_vector, this.front_end.predict_values_texts_vector, this.front_end.predict_explanation_list, var4);
                break;
            }
        }

        if (var1.getSource() == this.front_end.predict_button) {
            var23 = this.valueFromIntField(this.front_end.predict_max_steps_text);
            var31 = this.valueFromDoubleField(this.front_end.predict_min_percent_text) / 100.0D;
            Vector var33 = this.predict.getNames(this.front_end.predict_names_buttons_vector);
            Vector var25 = this.predict.getValues(this.front_end.predict_values_texts_vector, this.front_end.predict_names_buttons_vector);
            this.predict.use_negation = this.front_end.predict_use_negations_check.getState();
            this.predict.use_equivalences = this.front_end.predict_use_equivalences_check.getState();
            this.predict.makePredictions(this.front_end.predict_method_choice.getSelectedItem(), this.front_end.predict_entity_text.getText(), var33, var25, var31, this.front_end.predict_entity_type_choice.getSelectedItem(), this.concepts, this.front_end.predict_names_buttons_vector, this.front_end.predict_values_texts_vector, this.front_end.predict_explanation_list, var23);
        }

        boolean var40;
        if (var1.getSource() == this.front_end.update_front_end_button) {
            var40 = this.front_end.update_front_end_check.getState();
            this.front_end.update_front_end_check.setState(true);
            this.updateFrontEnd();
            this.front_end.update_front_end_check.setState(var40);
        }

        if (var1.getSource() == this.front_end.measure_concepts_button) {
            var40 = this.measure_concept.measure_concepts;
            this.measure_concept.measure_concepts = true;

            for(var20 = 0; var20 < this.concepts.size(); ++var20) {
                Concept var41 = (Concept)this.concepts.elementAt(var20);
                this.measure_concept.measureConcept(var41, this.concepts, false);
            }

            this.measure_concept.measure_concepts = var40;
        }

        if (var1.getSource() == this.front_end.initial_concepts_all_button) {
            for(var23 = 0; var23 < this.front_end.initial_concepts_list.getItemCount(); ++var23) {
                this.front_end.initial_concepts_list.select(var23);
            }
        }

        if (var1.getSource() == this.front_end.initial_concepts_none_button) {
            for(var23 = 0; var23 < this.front_end.initial_concepts_list.getItemCount(); ++var23) {
                this.front_end.initial_concepts_list.deselect(var23);
            }
        }

        if (var1.getSource() == this.front_end.play_macro_button) {
            this.macro_to_complete = this.front_end.macro_text.getText();
            this.playMacro();
        }

        if (var1.getSource() == this.front_end.start_button) {
            var13 = this.front_end.start_button.getLabel();
            if (var13.equals("Load")) {
                this.initialiseTheory();
                this.front_end.start_button.setLabel("Start");
                this.status("Ready to start");
                return;
            }

            this.time_already_run += (long)this.seconds_elapsed;
            this.seconds_elapsed = 0;
            this.run_start_time = System.currentTimeMillis();
            if (var13.equals("Start")) {
                this.front_end.start_button.setEnabled(false);
                this.front_end.stop_button.setEnabled(true);
                this.front_end.step_button.setEnabled(false);
                this.stop_now = false;
                var20 = new Integer(this.front_end.required_text.getText());
                this.formTheoryUntil(var20, this.front_end.required_choice.getSelectedItem());
            }

            if (var13.equals("Continue")) {
                this.front_end.start_button.setEnabled(false);
                this.front_end.stop_button.setEnabled(true);
                this.front_end.step_button.setEnabled(false);
                this.stop_now = false;
                this.use_front_end = true;
                this.getFrontEndSettings();
                var20 = new Integer(this.front_end.required_text.getText());
                this.formTheoryUntil(var20, this.front_end.required_choice.getSelectedItem());
            }
        }

        if (var1.getSource() == this.front_end.stop_button) {
            this.status("Stopping");
            var13 = this.front_end.stop_button.getLabel();
            if (var13.equals("Stop")) {
                this.macro_to_complete = "";
                this.front_end.macro_text.select(0, 0);
                this.front_end.start_button.setLabel("Continue");
                this.front_end.stop_button.setLabel("Kill");
                this.stop_now = true;
            }

            if (var13.equals("Kill")) {
                this.front_end.stop_button.setLabel("Stop");
                this.stopIt();
                this.front_end.stop_button.setEnabled(false);
                this.front_end.start_button.setEnabled(true);
                this.front_end.step_button.setEnabled(true);
            }
        }

        if (var1.getSource() == this.front_end.step_button) {
            this.theoryFormationStep();
        }

    }

    private void updateAgendaFrontEnd(int var1) {
        this.front_end.live_ordered_concept_list.clear();
        this.front_end.all_ordered_concept_list.clear();

        int var2;
        for(var2 = 0; var2 < this.agenda.live_ordered_concepts.size() && var2 <= var1; ++var2) {
            this.front_end.live_ordered_concept_list.addItem(((Concept)this.agenda.live_ordered_concepts.elementAt(var2)).id);
        }

        for(var2 = 0; var2 < this.agenda.all_ordered_concepts.size() && var2 <= var1; ++var2) {
            this.front_end.all_ordered_concept_list.addItem(((Concept)this.agenda.all_ordered_concepts.elementAt(var2)).id);
        }

        if (var1 > 0) {
            this.front_end.agenda_list.removeAll();

            for(var2 = 0; var2 < this.agenda.steps_to_force.size() && var2 < var1; ++var2) {
                String var3 = (String)this.agenda.steps_to_force.elementAt(var2);
                this.front_end.agenda_list.addItem(var3);
            }

            Vector var5 = this.agenda.listToComplete(this.front_end.not_allowed_agenda_check.getState(), var1 - this.agenda.steps_to_force.size());

            for(int var4 = 0; var4 < var5.size(); ++var4) {
                this.front_end.agenda_list.addItem((String)var5.elementAt(var4));
            }
        }

    }

    public void connectToMathWeb(String[] var1) {
        String var2 = var1[1];
        String var3 = var1[2];
        String var4 = var1[3];
        int var5 = Integer.parseInt(var3);
        int var6 = Integer.parseInt(var4);
        this.front_end.mathweb_hostname_label.setText("host:" + var2);
        this.front_end.mathweb_inout_socket_label.setText("inout socket:" + var3);
        this.front_end.mathweb_service_socket_label.setText("service socket:" + var4);
        this.mathweb_handler = null;

        try {
            InetAddress var7 = InetAddress.getByName(var2);
            new Socket(var7, var5);
            this.mathweb_handler = new MathWebHandler(var7, var6);
        } catch (UnknownHostException var9) {
            System.out.println("Unknown host: " + var2);
            System.exit(1);
        } catch (IOException var10) {
            System.out.println("Could not connect to MathWeb port: " + var4);
            System.exit(1);
        }

        if (this.mathweb_handler == null) {
            System.out.println("HR unable to connect to the MathWeb");
            System.exit(1);
        }

    }

    public void loadMacro(String var1) {
        this.front_end.save_macro_text.setText(this.front_end.macro_list.getSelectedItem());

        try {
            BufferedReader var2 = new BufferedReader(new FileReader(var1));
            String var3 = var2.readLine();
            String var4 = var3 + "\n";

            while(var3 != null) {
                var3 = var2.readLine();
                if (var3 != null) {
                    var4 = var4 + var3 + "\n";
                }
            }

            var2.close();
            this.front_end.macro_text.setText(var4);
        } catch (Exception var5) {
            ;
        }

        this.macro_to_complete = this.front_end.macro_text.getText();
    }

    public void playMacro() {
        this.status("Playing macro");
        this.macro_handler.playMacro(this);
        this.status("Played macro");
    }

    public void loadMacro_old(String var1) {
        this.front_end.save_macro_text.setText(this.front_end.macro_list.getSelectedItem());

        try {
            BufferedReader var2 = new BufferedReader(new FileReader(var1));

            String var3;
            for(var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
                this.front_end.macro_text.append(var3 + "\n");
            }

            var2.close();
            if (!var3.equals("")) {
                this.front_end.macro_text.append(var3 + "\n");
            }
        } catch (Exception var4) {
            ;
        }

        this.macro_to_complete = this.front_end.macro_text.getText();
    }

    public void playMacro_old() {
        this.status("Playing macro");
        this.macro_handler.playMacro(this);
        this.status("Played macro");
    }

    public void keyReleased(KeyEvent var1) {
    }

    public void keyTyped(KeyEvent var1) {
    }

    public void focusGained(FocusEvent var1) {
    }

    public void focusLost(FocusEvent var1) {
    }

    public void addEntityToTheory(Vector var1, Theory var2, AgentWindow var3) {
        var3.writeToFrontEnd("started addEntityToTheory on " + var1);
        var3.writeToFrontEnd("entities currently are " + this.entities);
        Concept var4 = new Concept();

        int var5;
        for(var5 = 0; var5 < this.concepts.size(); ++var5) {
            Concept var6 = (Concept)this.concepts.elementAt(var5);
            if (var6.is_object_of_interest_concept) {
                var4 = var6;
                break;
            }
        }

        for(var5 = 0; var5 < var1.size(); ++var5) {
            Object var8 = var1.elementAt(var5);
            var3.writeToFrontEnd("IN Theory - object is " + var8);
            Entity var7 = (Entity)var1.elementAt(var5);
            this.addNewEntityToTheory(var7, var4.domain, "");
        }

        var3.writeToFrontEnd("finished addEntityToTheory");
        var3.writeToFrontEnd("entities currently are " + this.entities);
    }

    public Concept getObjectOfInterestConcept() {
        Concept var1 = new Concept();

        for(int var2 = 0; var2 < this.concepts.size(); ++var2) {
            Concept var3 = (Concept)this.concepts.elementAt(var2);
            if (var3.is_object_of_interest_concept) {
                var1 = var3;
                break;
            }
        }

        return var1;
    }

    public void downgradeEntityToPseudoEntity(Entity var1, AgentWindow var2) {
        System.out.println(" started : downgradeEntityToPseudoEntity");

        int var3;
        Entity var4;
        for(var3 = 0; var3 < this.entities.size(); ++var3) {
            var4 = (Entity)this.entities.elementAt(var3);
            System.out.println("1) domain of entity " + var4.name + " is " + var4.domain);
        }

        System.out.println("entity.domain is " + var1.domain);

        for(var3 = 0; var3 < this.entities.size(); ++var3) {
            var4 = (Entity)this.entities.elementAt(var3);
            if (var4.name.equals(var1.name)) {
                this.entities.removeElementAt(var3);
                break;
            }
        }

        this.pseudo_entities.addElement(var1);
        if (this.front_end.update_front_end_check.getState()) {
            this.front_end.removeEntity(var1);
            this.updateFrontEnd();
        }

        this.addToTimer("Updating datatables, categorisations and same datatable hashtable after downgrading an entity to pseudo-entity");
        this.categorisations.removeAllElements();
        new Vector();
        this.datatable_hashtable = new Hashtable();

        for(int var12 = 0; var12 < this.concepts.size(); ++var12) {
            Concept var5 = (Concept)this.concepts.elementAt(var12);
            System.out.println("\n\n datatable for " + var5.writeDefinition() + " is:");
            System.out.println(var5.datatable.toTable());
            var5.datatable.removeEntity(var1.name);

            for(int var6 = 0; var6 < var5.datatable.size(); ++var6) {
                Row var7 = (Row)var5.datatable.elementAt(var6);

                for(int var8 = 0; var8 < var7.tuples.size(); ++var8) {
                    Vector var9 = (Vector)var7.tuples.elementAt(var8);

                    for(int var10 = 0; var10 < var9.size(); ++var10) {
                        String var11 = (String)var9.elementAt(var10);
                        if (var11.equals(var1.name)) {
                            var7.tuples.removeElementAt(var8);
                            --var8;
                            break;
                        }
                    }
                }
            }

            String var13 = var5.datatable.firstTuples();
            this.addCategorisation(var5);
            Vector var14 = (Vector)this.datatable_hashtable.get(var13);
            if (var14 != null) {
                var14.addElement(var5);
            } else {
                var14 = new Vector();
                var14.addElement(var5);
                this.datatable_hashtable.put(var13, var14);
            }

            System.out.println("NEW datatable for " + var5.writeDefinition() + " is:");
            System.out.println(var5.datatable.toTable());
        }

    }

    public void upgradeEntityToPseudoEntity(Entity var1) {
        for(int var2 = 0; var2 < this.pseudo_entities.size(); ++var2) {
            Entity var3 = (Entity)this.pseudo_entities.elementAt(var2);
            if (var3.name.equals(var1.name)) {
                return;
            }
        }

        this.pseudo_entities.addElement(var1);
    }

    public void downgradeConjectureToPseudoConjecture(Conjecture var1) {
        for(int var2 = 0; var2 < this.conjectures.size(); ++var2) {
            Conjecture var3 = (Conjecture)this.conjectures.elementAt(var2);
            if (var3.writeConjecture().equals(var1.writeConjecture())) {
                this.conjectures.removeElementAt(var2);
            }
        }

        this.pseudo_conjectures.addElement(var1);
    }

    public void testForConjecture(TheoryConstituent var1) {
        System.out.println("-------- hurray!!!!!!");
    }

    public Vector addImplicationToTheory(Implication var1, Concept var2, Concept var3) {
        var1.checkSegregation(this.agenda);
        this.implications.addElement(var1);
        this.conjectures.addElement(var1);
        return var1.counterexamples;
    }

    public Vector addNearEquivalenceToTheory(NearEquivalence var1) {
        var1.checkSegregation(this.agenda);
        this.near_equivalences.addElement(var1);
        this.conjectures.addElement(var1);
        return var1.counterexamples;
    }

    public Vector addNearImplicationToTheory(NearImplication var1) {
        var1.checkSegregation(this.agenda);
        this.near_implications.addElement(var1);
        this.conjectures.addElement(var1);
        return var1.counterexamples;
    }

    public Vector addConjectureToTheory(Conjecture var1) {
        if (var1 instanceof NonExists) {
            return this.addNonExistsToTheory((NonExists)var1, ((NonExists)var1).concept);
        } else if (var1 instanceof Equivalence) {
            return this.addEquivalenceToTheory((Equivalence)var1, ((Equivalence)var1).lh_concept, ((Equivalence)var1).rh_concept);
        } else if (var1 instanceof NearEquivalence) {
            return this.addNearEquivalenceToTheory((NearEquivalence)var1);
        } else if (var1 instanceof Implication) {
            return this.addImplicationToTheory((Implication)var1, ((Implication)var1).lh_concept, ((Implication)var1).rh_concept);
        } else {
            return var1 instanceof NearImplication ? this.addNearImplicationToTheory((NearImplication)var1) : new Vector();
        }
    }

    public void addConceptToTheory(Concept var1) {
        this.agenda.addConcept(var1, this.concepts, this);
    }

    public String theoryReport() {
        boolean var1 = true;
        boolean var2 = true;
        boolean var3 = false;
        if (var3) {
            this.measure_concept.applicability_weight = 0.5D;
            this.measure_concept.comprehensibility_weight = 0.5D;
            this.measure_concept.novelty_weight = 0.5D;
            this.measure_concept.parsimony_weight = 0.5D;
            this.measure_conjecture.applicability_weight = 0.5D;
            this.measure_conjecture.comprehensibility_weight = 0.5D;
            this.measure_conjecture.surprisingness_weight = 0.5D;
            this.measure_conjecture.plausibility_weight = 0.5D;
        }

        boolean var4 = true;
        if (var4) {
            this.measure_concept.applicability_weight = 1.0D;
            this.measure_concept.comprehensibility_weight = 1.0D;
            this.measure_concept.novelty_weight = 1.0D;
            this.measure_concept.parsimony_weight = 1.0D;
            this.measure_conjecture.applicability_weight = 1.0D;
            this.measure_conjecture.comprehensibility_weight = 1.0D;
            this.measure_conjecture.surprisingness_weight = 1.0D;
            this.measure_conjecture.plausibility_weight = 1.0D;
        }

        int var5;
        for(var5 = 0; var5 < this.concepts.size(); ++var5) {
            Concept var6 = (Concept)this.concepts.elementAt(var5);
            this.measure_concept.measureConcept(var6, this.concepts, false);
        }

        for(var5 = 0; var5 < this.conjectures.size(); ++var5) {
            Conjecture var42 = (Conjecture)this.conjectures.elementAt(var5);
            this.measure_conjecture.measureConjecture(var42, this);
        }

        double var41 = this.averageInterestingnessOfConcepts();
        double var7 = this.averageApplicabilityOfConcepts();
        double var9 = this.averageComplexityOfConcepts();
        double var11 = this.averageNoveltyOfConcepts();
        double var13 = this.averageParsimonyOfConcepts();
        double var15 = this.averageComprehensibilityOfConcepts();
        double var17 = this.averageApplicabilityOfConjectures();
        double var19 = this.averageComprehensibilityOfConjectures();
        double var21 = this.averageSurprisingnessOfConjectures();
        double var23 = this.averagePlausibilityOfConjectures();
        double var25 = this.averageInterestingnessOfConjectures();
        double var27 = this.averageInterestingnessOfNearConjectures();
        byte var29 = 2;
        TheoryConstituent var30 = new TheoryConstituent();
        new String();
        String var32 = this.front_end.macro_list.getSelectedItem();
        String var31 = "\n\n\n                                REPORT                  ";
        var31 = var31 + "\n\nMacro: " + var32;
        var31 = var31 + "\n\nNum steps: " + this.steps_taken;
        var31 = var31 + "\n\n// Concepts //\n\n\n";
        var31 = var31 + "concept    arity    app     comp   nov     pars    comp    interest    lakatos_method" + "\n-------------------------------------------------------------------------------------" + "\n" + "average     --      " + var30.decimalPlaces(var7, var29) + "    " + var30.decimalPlaces(var15, var29) + "    " + var30.decimalPlaces(var11, var29) + "    " + var30.decimalPlaces(var13, var29) + "    " + var30.decimalPlaces(var15, var29) + "    " + var30.decimalPlaces(var41, var29) + "               --     " + "\n-------------------------------------------------------------------------------------";

        int var33;
        String var35;
        for(var33 = 0; var33 < this.concepts.size(); ++var33) {
            Concept var34 = (Concept)this.concepts.elementAt(var33);
            var35 = new String();
            if (var34.id.length() == 7) {
                var35 = "    ";
            }

            if (var34.id.length() == 6) {
                var35 = "     ";
            }

            if (var34.id.length() == 5) {
                var35 = "      ";
            }

            if (var34.id.length() == 4) {
                var35 = "       ";
            }

            var31 = var31 + "\n" + var34.id + var35 + var34.arity + "        " + var34.decimalPlaces(var34.applicability, var29) + "    " + var34.decimalPlaces(var34.comprehensibility, var29) + "    " + var34.decimalPlaces(var34.novelty, var29) + "    " + var34.decimalPlaces(var34.parsimony, var29) + "    " + var34.decimalPlaces(var34.comprehensibility, var29) + "    " + var34.decimalPlaces(var34.interestingness, var29) + "               " + var34.lakatos_method;
        }

        var31 = var31 + "\n\n------------------------------------------------------------------------------------";
        var31 = var31 + "\n\n\n\n\n// Conjectures //\n\n\n";
        var31 = var31 + "conjecture    arity    app     compr    surp    plaus   interest    lakatos_method" + "\n------------------------------------------------------------------------------------" + "\n" + "average       --       " + var30.decimalPlaces(var17, var29) + "    " + var30.decimalPlaces(var19, var29) + "    " + var30.decimalPlaces(var21, var29) + "    " + var30.decimalPlaces(var23, var29) + "    " + var30.decimalPlaces(var25, var29) + "              --     " + "\n------------------------------------------------------------------------------------";

        for(var33 = 0; var33 < this.conjectures.size(); ++var33) {
            Conjecture var43 = (Conjecture)this.conjectures.elementAt(var33);
            var35 = new String();
            String var36 = new String();
            String var37 = new String();
            String var38 = new String();
            if (var43.id.length() == 3) {
                var35 = "           ";
            }

            if (var43.id.length() == 2) {
                var35 = "            ";
            }

            if (var43.id.length() == 1) {
                var35 = "             ";
            }

            if (var43.decimalPlaces(var43.applicability, var29).length() == 4) {
                var36 = "        ";
            }

            if (var43.decimalPlaces(var43.applicability, var29).length() == 5) {
                var36 = "       ";
            }

            if (var43.decimalPlaces(var43.comprehensibility, var29).length() == 4) {
                var37 = "    ";
            }

            if (var43.decimalPlaces(var43.comprehensibility, var29).length() == 5) {
                var37 = "   ";
            }

            if (var43.decimalPlaces(var43.surprisingness, var29).length() == 4) {
                var38 = "    ";
            }

            if (var43.decimalPlaces(var43.surprisingness, var29).length() == 5) {
                var38 = "   ";
            }

            var31 = var31 + "\n" + var43.id + var35 + (int)var43.arity + var36 + var43.decimalPlaces(var43.applicability, var29) + var37 + var43.decimalPlaces(var43.comprehensibility, var29) + var38 + var43.decimalPlaces(var43.surprisingness, var29) + "    " + var43.decimalPlaces(var43.plausibility, var29) + "    " + var43.decimalPlaces(var43.interestingness, var29) + "              " + var43.lakatos_method;
        }

        var31 = var31 + "\n\n------------------------------------------------------------------------------------";
        var31 = var31 + "\n\nMy entities are:\n";

        Entity var44;
        for(var33 = 0; var33 < this.entities.size(); ++var33) {
            var44 = (Entity)this.entities.elementAt(var33);
            if (var33 < this.entities.size() - 1) {
                var31 = var31 + var44.name + ", ";
            }

            if (var33 == this.entities.size() - 1) {
                var31 = var31 + var44.name + ".";
            }
        }

        if (this.pseudo_entities.isEmpty()) {
            var31 = var31 + "\n\nI do not have any pseudo-entities.";
        } else {
            var31 = var31 + "\n\nMy pseudo-entities are:\n";

            for(var33 = 0; var33 < this.pseudo_entities.size(); ++var33) {
                var44 = (Entity)this.pseudo_entities.elementAt(var33);
                if (var33 < this.pseudo_entities.size() - 1) {
                    var31 = var31 + var44.name + ", ";
                }

                if (var33 == this.pseudo_entities.size()) {
                    var31 = var31 + var44.name + ".";
                }
            }
        }

        int var45;
        if (var1) {
            var33 = 0;
            var45 = 0;
            double var48 = 0.0D;

            int var47;
            for(var47 = 0; var47 < this.concepts.size(); ++var47) {
                Concept var49 = (Concept)this.concepts.elementAt(var47);
                if (!var49.lakatos_method.equals("no")) {
                    ++var33;
                }
            }

            for(var47 = 0; var47 < this.conjectures.size(); ++var47) {
                Conjecture var51 = (Conjecture)this.conjectures.elementAt(var47);
                if (!var51.lakatos_method.equals("no")) {
                    var48 += var51.interestingness;
                    ++var45;
                }
            }

            int var10000 = var33 / this.concepts.size();
            var10000 = var45 / this.conjectures.size();
            double var39 = var48 / (double)var45;
            var31 = var31 + "\nNumber of concepts in the theory: " + this.concepts.size() + ", of which " + var33 + " were lakatos concepts";
            var31 = var31 + "\nNumber of conjectures in the theory: " + this.conjectures.size() + ", of which " + var45 + " were lakatos conjectures";
            var31 = var31 + "\nNumber of near conjectures in the theory is " + this.numberNearConjectures();
            var31 = var31 + "\nAverage interestingness of near-conjectures is " + var30.decimalPlaces(var27, var29);
            var31 = var31 + "\nAverage interestingness of lakatos conjectures is " + var30.decimalPlaces(var39, var29);
            var31 = var31 + "\nThe proportion of concepts produced using lakatos methods is " + var33 + "/" + this.concepts.size();
            var31 = var31 + "\nThe proportion of conjectures produced using lakatos methods is " + var45 + "/" + this.conjectures.size();
        }

        boolean var46 = false;
        Conjecture var50;
        if (var46) {
            for(var45 = 0; var45 < this.conjectures.size(); ++var45) {
                var50 = (Conjecture)this.conjectures.elementAt(var45);
                var31 = var31 + var50.printConjectureInformation(this.measure_conjecture);
            }
        }

        if (var2) {
            var31 = var31 + "\n\nHere are all of my concepts, conjectures and entities which have been produced by Lakatos methods:";

            for(var45 = 0; var45 < this.concepts.size(); ++var45) {
                Concept var52 = (Concept)this.concepts.elementAt(var45);
                if (!var52.lakatos_method.equals("no")) {
                    var31 = var31 + "\n" + var52.writeDefinition() + "was found by " + var52.lakatos_method;
                }
            }

            for(var45 = 0; var45 < this.conjectures.size(); ++var45) {
                var50 = (Conjecture)this.conjectures.elementAt(var45);
                if (!var50.lakatos_method.equals("no")) {
                    var31 = var31 + "\n" + var50.writeConjecture() + "was found by " + var50.lakatos_method;
                }
            }

            Entity var53;
            for(var45 = 0; var45 < this.entities.size(); ++var45) {
                var53 = (Entity)this.entities.elementAt(var45);
                if (!var53.lakatos_method.equals("no")) {
                    var31 = var31 + "\n" + var53.toString() + "was found by " + var53.lakatos_method;
                }
            }

            for(var45 = 0; var45 < this.pseudo_entities.size(); ++var45) {
                var53 = (Entity)this.pseudo_entities.elementAt(var45);
                if (!var53.lakatos_method.equals("no")) {
                    var31 = var31 + "\n" + var53.toString() + "was found by " + var53.lakatos_method;
                }
            }
        }

        var31 = var31 + "\nThe end";
        return var31;
    }

    public double averageInterestingnessOfConcepts() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            var1 += var4.interestingness;
        }

        var1 /= (double)this.concepts.size();
        return var1;
    }

    public double averageApplicabilityOfConcepts() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            var1 += var4.applicability;
        }

        var1 /= (double)this.concepts.size();
        return var1;
    }

    public double averageComprehensibilityOfConcepts() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            var1 += var4.comprehensibility;
        }

        var1 /= (double)this.concepts.size();
        return var1;
    }

    public double averageNoveltyOfConcepts() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            var1 += var4.novelty;
        }

        var1 /= (double)this.concepts.size();
        return var1;
    }

    public double averageParsimonyOfConcepts() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            var1 += var4.parsimony;
        }

        var1 /= (double)this.concepts.size();
        return var1;
    }

    public double averageComplexityOfConcepts() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            var1 += (double)var4.complexity;
        }

        var1 /= (double)this.concepts.size();
        return var1;
    }

    public double averageInterestingnessOfConjectures() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.conjectures.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.conjectures.elementAt(var3);
            var1 += var4.interestingness;
        }

        var1 /= (double)this.conjectures.size();
        return var1;
    }

    public double averageApplicabilityOfConjectures() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.conjectures.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.conjectures.elementAt(var3);
            var1 += var4.applicability;
        }

        var1 /= (double)this.conjectures.size();
        return var1;
    }

    public double averageComprehensibilityOfConjectures() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.conjectures.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.conjectures.elementAt(var3);
            var1 += var4.comprehensibility;
        }

        var1 /= (double)this.conjectures.size();
        return var1;
    }

    public double averageSurprisingnessOfConjectures() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.conjectures.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.conjectures.elementAt(var3);
            var1 += var4.surprisingness;
        }

        var1 /= (double)this.conjectures.size();
        return var1;
    }

    public double averagePlausibilityOfConjectures() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.conjectures.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.conjectures.elementAt(var3);
            var1 += var4.plausibility;
        }

        var1 /= (double)this.conjectures.size();
        return var1;
    }

    public double averageInterestingnessOfNearConjectures() {
        double var1 = 0.0D;

        int var3;
        for(var3 = 0; var3 < this.near_equivalences.size(); ++var3) {
            NearEquivalence var4 = (NearEquivalence)this.near_equivalences.elementAt(var3);
            var1 += var4.interestingness;
        }

        for(var3 = 0; var3 < this.near_implications.size(); ++var3) {
            NearImplication var5 = (NearImplication)this.near_implications.elementAt(var3);
            var1 += var5.interestingness;
        }

        var1 /= (double)(this.near_equivalences.size() + this.near_implications.size());
        return var1;
    }

    public int numberNearConjectures() {
        int var1 = 0;

        for(int var2 = 0; var2 < this.conjectures.size(); ++var2) {
            Conjecture var3 = (Conjecture)this.conjectures.elementAt(var2);
            if (!var3.counterexamples.isEmpty()) {
                System.out.println("Conjecture " + var3.id + ": " + var3.writeConjecture() + " has these counterexamples\n" + var3.counterexamples + "\nSo it is counted as a nearConjecture");
                ++var1;
            }
        }

        return var1;
    }
}
