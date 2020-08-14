package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import java.lang.String;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/** A class representing a mathematical theory produced by HR.
 * @author Simon Colton, started 13th December 1999
 * @version 1.0
 */

public class Theory implements ActionListener, ItemListener, FocusListener, KeyListener, Serializable
{

    /** Whether or not each Lakatos method should be used in this theory
     (alisonp - 20/11) */

    //individualtheory
    public boolean use_surrender = false; //change to true?
    public boolean use_strategic_withdrawal = false; //change to true?
    public boolean use_piecemeal_exclusion = false; //change to true?
    public boolean use_monster_barring = false;
    public boolean use_monster_adjusting = false;
    public boolean use_lemma_incorporation = false;
    public boolean use_proofs_and_refutations = false;

    //communal
    public boolean use_communal_piecemeal_exclusion = false; //this should be read in from the macro

    /** Whether or not to extract implicates from implications made
     * by subsumption.
     */

    public boolean make_subsumption_implicates = false;

    /** Whether to require that a previous conjecture is proved
     * before using it to subsume a new conjecture.
     */

    public boolean require_proof_in_subsumption = true;

    /** Whether or not to skip equivalence conjectures.
     */

    public boolean skip_equivalence_conjectures = false;

    /** A storage vector reserved for reactions.
     */

    public Vector storage_vector1 = new Vector();

    /** Another storage vector reserved for reactions.
     */

    public Vector storage_vector2 = new Vector();

    /** The gold standard categorisation for a learning task being
     * undertaken by this theory.
     */

    public Categorisation gold_standard_categorisation = new Categorisation();

    /** The positives for a (binary) gold_standard_categorisation.
     */

    public Vector positives_for_learning = new Vector();

    /** The negatives for a (binary) gold_standard_categorisation.
     */

    public Vector negatives_for_learning = new Vector();

    /** The pseudo code interpreter object for this theory.
     */

    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();

    /** A step counter, which can be reset to zero.
     */

    public volatile int step_counter = 0;

    /** The text handler object for this theory.
     */

    public TextHandler text_handler = new TextHandler();

    /** The storage handler object for this theory.
     */

    public StorageHandler storage_handler = new StorageHandler();

    /** Whether or not to check whether a concept achieves the gold standard
     * categorisation.
     */

    public boolean check_for_gold_standard = false;

    /** The specification handler for this theory.
     */

    public SpecificationHandler specification_handler = new SpecificationHandler();

    /** The steps already taken in the theory formation.
     */

    public Vector previous_steps = new Vector();

    /** The reflection object for this theory.
     */

    public Reflection reflect = new Reflection();

    /** The theory formation thread for this theory.
     */

    public TheoryFormationThread runner = new TheoryFormationThread();

    /** The directory where all the input files for this theory will be found.
     */

    public String input_files_directory = "";

    /** The maths object for this theory.
     */

    public Maths maths = new Maths();

    /** The react object for this theory.
     */

    public ReactionHandler react = new ReactionHandler();

    /** The lakatos object for this theory.
     */

    public Lakatos lakatos = new Lakatos();

    /** The group agenda object for this theory [only teacher should own] - alisonp.
     */

    public GroupAgenda group_agenda = new GroupAgenda();

    /** The variables passed from the command line (for running macros).
     */

    public Vector command_line_arguments = new Vector();

    /** The set of entities that have been found as counterexamples to the chosen
     * conjecture through testing of user-supplied objects.
     */

    public Vector counterexamples_found_from_testing = new Vector();

    /** The conjecture that has been chosen from the conjecture list on screen.
     */

    public Conjecture conjecture_chosen_from_front_end = null;

    /** Whether or not to allow concepts to be substituted with simpler ones.
     */

    public boolean allow_substitutions = false;

    /** Whether or not to use the applicability conjecture making method.
     */

    public boolean use_applicability_conj = false;

    /** The max number for the applicability conjecture making method.
     */

    public int max_applicability_conj_number = 0;

    /** Whether or not to use counterexample barring.
     */

    public boolean use_counterexample_barring = false;

    /** Whether or not to use concept barring.
     */

    public boolean use_concept_barring = false;

    /** The max number of counterexamples to bar in the counterexample barrring method
     * for improving conjectures.
     */

    public int max_counterexample_bar_number = 0;

    /** The max number of exceptions to consider for exception barring.
     */

    public int max_concept_bar_number = 0;

    /** The statistics handler object for this theory.
     */

    public StatisticsHandler statistics_handler = new StatisticsHandler();

    /** The graph handler object for this theory.
     */

    public GraphHandler graph_handler = new GraphHandler();

    /** The debugger object for this theory.
     */

    public Debugger debugger = new Debugger();

    /** The guide class for this theory.
     */

    public Guide guider = new Guide();

    /** The report generator for this theory.
     */

    public ReportGenerator report_generator = new ReportGenerator();

    /** The mathweb object for this theory.
     * @See MathWebHandler
     */

    public MathWebHandler mathweb_handler = null;

    /** The SystemOnTPTP handler for this theory.
     */

    public SOTHandler sot_handler = new SOTHandler();

    /** The macro string to complete after a wait command has been given
     */

    public String macro_to_complete = "";

    /** Whether this theory is running in batch mode (no front end)
     */

    public boolean is_running_silent = false;

    /** The GUI recorder (for macro) for this theory.
     */

    public MacroHandler macro_handler = new MacroHandler();

    /** a flag to tell it to exit after macro */
    boolean exit_after_macro = false;

    /** The prediction object for this theory.
     * @see Predict
     */

    public Predict predict = new Predict();

    /** The hastable for checking if a datatable is already in the theory.
     */

    public Hashtable datatable_hashtable = new Hashtable();

    /** The counterexample generator for this theory.
     */

    public HoldBackChecker hold_back_checker = new HoldBackChecker();

    /** The set of concepts which have been read in from files
     * (not necessarily in the theory)
     */

    public Vector domain_concepts = new Vector();

    /** The set of user-defined functions for this theory.
     */

    public UserFunctions user_functions = new UserFunctions();

    /** The Reader for files in HR's syntax
     */

    public Read reader = new Read();

    /** The name of the operating system HR will work on.
     */

    public String operating_system = "windows";

    /** A (possibly empty) set of concepts which HR has learned for a given categorisation.
     */

    public Vector learned_concepts = new Vector();

    /** The categorisations HR was asked to learn a concepts for (flattened).
     */

    public Vector required_flat_categorisations = new Vector();

    /** The categorisations HR was asked to learn a concepts for.
     */

    public Vector required_categorisations = new Vector();

    /** The timer object for this theory.
     */

    public Timer timer = new Timer();

    /** The explanation handler (for proving and disproving conjectures)
     * for this theory.
     */

    public ExplanationHandler explanation_handler = new ExplanationHandler();

    /** Whether to extract implication conjectures from the equivalence conjectures
     */

    public boolean extract_implications_from_equivalences = false;

    /** Whether to extract implicates conjectures from the non-existence conjectures
     */

    public boolean extract_implicates_from_non_exists = false;

    /** Whether or not to extract implicates from implication theorems
     */

    public boolean extract_implicates_from_equivalences = false;

    /** Whether or not to extract prime implicates from implicate theorems
     */

    public boolean extract_prime_implicates_from_implicates = false;

    /** Whether or not to look for subsumption implicate conjectures
     */

    public boolean make_subsumption_implications = false;

    /** Whether to make equivalence conjectures by looking for equal concepts
     */

    public boolean make_equivalences_from_equality = false;

    /** Whether to make equivalence conjectures by combination.
     * e.g., c <-> d and c <-> e gives d <-> e.
     */

    public boolean make_equivalences_from_combination = false;

    /** Whether to make non-existence conjectures when a concept has no examples
     */

    public boolean make_non_exists_from_empty = false;

    /** Whether to keep details of old steps.
     */

    public boolean keep_steps = false;

    /** Whether to keep equivalence conjectures
     */

    public boolean keep_equivalences = false;

    /** Whether to keep non-existence conjectures
     */

    public boolean keep_non_existences = false;

    /** Whether to keep implication conjectures
     */

    public boolean keep_implications = false;

    /** Whether to keep implicate conjectures
     */

    public boolean keep_implicates = false;

    /** Whether to keep prime implicate conjectures
     */

    public boolean keep_prime_implicates = false;

    /** Whether the teacher requests conjectures (no type specified)
     */

    public boolean teacher_requests_conjectures = true;//11/03/04


    /** Whether the teacher requests nonexists conjectures
     */

    public boolean teacher_requests_nonexists = false;//11/03/04

    /** Whether the teacher requests implication conjectures
     */

    public boolean teacher_requests_implication = false;//11/03/04

    /** Whether the teacher requests nearimplication conjectures
     */

    public boolean teacher_requests_nearimplication = false;//11/03/04

    /** Whether the teacher requests equivalence conjectures
     */

    public boolean teacher_requests_equivalence = false;//11/03/04

    /** Whether the teacher requests nearequivalence conjectures
     */

    public boolean teacher_requests_nearequivalence = false;//11/03/04


    /** Whether to make near-equivalence conjectures
     */

    public boolean make_near_equivalences = false;

    /** The minimum percent for near equivalences
     */

    public double near_equiv_percent = 0;

    /** Whether to make near-implication conjectures - alisonp
     */

    public boolean make_near_implications = false;

    /** The minimum percent for near implications - alisonp
     */

    public double near_imp_percent = 0;

    /** The limit for the complexity of any concept in the theory.
     * The default is 1000 - any complexity is allowed.
     */

    public int complexity_limit = 1000;

    /** The limit for the number of items on the agenda.
     * The default is 10000, but this may be too small.
     */

    public int agenda_size_limit = 10000;

    /** The limit for the number of tuples in a particular concept. The number of
     * tuples can balloon with repeated compose steps, so huge concepts are discarded.
     */

    public int tuple_number_limit = 1000;

    /** After how many steps a garbage collection should occur.
     * The default is 1000.
     */

    public int gc_when = 100000;

    /** The number of theory formation steps which have occurred so far.
     */

    public int steps_taken = 0;

    /** A search tactic - depth first exhaustive search. Default is breadth first.
     */

    public boolean depth_first = true;

    /** A search tactic - random search.
     */

    public boolean random = true;

    /** The agenda for the theory.
     * @see Agenda
     */

    public Agenda agenda = new Agenda();

    /** The measuring concepts object for the theory. This is where all
     * the code for measuring concepts stored.
     */

    public MeasureConcept measure_concept = new MeasureConcept();

    /** The measuring conjectures object for the theory. This is where all
     * the code for measuring conjectures is stored.
     */

    public MeasureConjecture measure_conjecture = new MeasureConjecture();

    /** The production rules which will be used in this theory.
     */

    public Vector production_rules = new Vector();

    /** All the production rules, regardless of whether they
     * will be used in the theory.
     */

    public Vector all_production_rules = new Vector();

    /** The set of binary production rules available to this theory.
     */

    public Vector binary_rules = new Vector();

    /** The set of unary production rules available to this theory.
     */

    public Vector unary_rules = new Vector();

    /** The disjunct production rules which this theory will use.
     */

    public Disjunct disjunct = new Disjunct();

    /** The arithmeticb production rules which this theory will use. //friday
     */

    public Arithmeticb arithmeticb = new Arithmeticb();


    /** The compose production rule which this theory will use.
     */

    public Compose compose = new Compose();

    /** The embed graph production rule which this theory will use.
     */

    public EmbedGraph embed_graph = new EmbedGraph();

    /** The embed algebra production rule which this theory will use.
     */

    public EmbedAlgebra embed_algebra = new EmbedAlgebra();

    /** The entity disjunct production rule which this theory will use.
     */

    public EntityDisjunct entity_disjunct = new EntityDisjunct();

    /** The exists production rule which this theory will use.
     */

    public Exists exists = new Exists();

    /** The equal production rule which this theory will use.
     */

    public Equal equal = new Equal();

    /** The forall production rule which this theory will use.
     */

    public Forall forall = new Forall();

    /** The match production rule which this theory will use.
     */
    public Match match = new Match();

    /** The negate production rule which this theory will use.
     */

    public Negate negate = new Negate();

    /** The record production rule which this theory will use.
     */

    public Record record = new Record();

    /** The size production rule which this theory will use.
     */

    public Size size = new Size();

    /** The split production rule which this theory will use.
     */

    public Split split = new Split();

    /** The set of concepts in the theory.
     * @see Concept
     */

    public Vector concepts = new Vector();

    /** The set of entity concepts in the theory. (entity concepts have arity 1).
     * @see Concept
     */

    public Vector specialisation_concepts = new Vector();

    /** The set of conjectures in the theory.
     * @see Conjecture
     */

    public Vector conjectures = new Vector();

    /** The set of near equivalence conjectures in the theory.
     */

    public Vector near_equivalences = new Vector();

    /** The set of near implication conjectures in the theory. alisonp
     */

    public Vector near_implications = new Vector();

    /** The set of equivalence conjectures in the theory.
     * @see Equivalence
     */

    public Vector equivalences = new Vector();

    /** The set of non-existence conjectures in the theory.
     * @see NonExists
     */

    public Vector non_existences = new Vector();

    /** The set of prime implicates in the theory.
     */

    public Vector prime_implicates = new Vector();

    /** The set of implicate conjectures in the theory.
     * @see Implicate
     */

    public Vector implicates = new Vector();

    /** The set of implication conjectures in the theory.
     * @see Implication
     */

    public Vector implications = new Vector();

    /** The set of relations in the theory.
     * @see Relation
     */

    public Vector relations = new Vector();

    /** The set of specifications in the theory.
     * @see Specification
     */

    public Vector specifications = new Vector();

    /** The set of categorisations in the theory
     */

    public Vector categorisations = new Vector();

    /** The set of categorisations in the theory flattened to a string.
     * This is done for efficiency reasons in an agency.
     */

    public Hashtable flat_categorisations = new Hashtable();

    /** The set of objects of interest (entities) which are in the theory.
     */

    public Vector entities = new Vector();

    /** The set of pseudo objects of interest (entities) which are in the theory.
     */

    public Vector pseudo_entities = new Vector();

    /** The set of pseudo conjectures in the theory.
     */

    public Vector pseudo_conjectures = new Vector();


    /** The set of names of the objects of interest (entities) which are in the theory.
     */

    public Vector entity_names = new Vector();

    /** A frontend for the theory. The frontend contains various AWT
     * components for displaying information about the theory.
     * @see FrontEnd
     */

    public FrontEnd front_end = new FrontEnd();

    /** Whether or not to output information to the frontend.
     * @see FrontEnd
     */

    public boolean use_front_end = true;

    /** A flag to tell stop the theory formation.  */

    public boolean stop_now = false;

    /** The id of the concept which is being forced through
     */

    public String forced_id = "";

    /** The entities that have been added as counterexamples to conjectures.
     */

    public Vector entities_added = new Vector();

    /** The time the present run was started.
     */

    public long run_start_time = 0;

    /** The seconds which have already elapsed.
     */
    public int seconds_elapsed = 0;

    /** The time already on the clock.
     */

    public long time_already_run = 0;

    /** The Puzzle Object for this Theory
     */

    PuzzleGenerator puzzler = new PuzzleGenerator();

    /** Constructs an empty theory. Adds all possible production rules to the list of those
     * which can be used.
     */

    public Theory()
    {
    }

    public void passOnSettings()
    {
        explanation_handler.storage_handler = storage_handler;
        explanation_handler.hold_back_checker = hold_back_checker;
        pseudo_code_interpreter.input_text_box = front_end.pseudo_code_input_text;
        pseudo_code_interpreter.output_text_box = front_end.pseudo_code_output_text;
        storage_handler.guider = guider;
        storage_handler.reflect = reflect;
        front_end.os = operating_system;
        production_rules.addElement(arithmeticb);//friday
        production_rules.addElement(exists);
        production_rules.addElement(entity_disjunct);
        production_rules.addElement(equal);
        production_rules.addElement(embed_algebra);
        production_rules.addElement(embed_graph);
        production_rules.addElement(forall);
        production_rules.addElement(match);
        production_rules.addElement(size);
        production_rules.addElement(compose);
        production_rules.addElement(disjunct);
        production_rules.addElement(negate);
        production_rules.addElement(record);
        production_rules.addElement(split);
        forall.compose = compose;
        forall.exists = exists;
        binary_rules.addElement(arithmeticb);//friday
        binary_rules.addElement(compose);
        binary_rules.addElement(disjunct);
        binary_rules.addElement(embed_graph);
        binary_rules.addElement(negate);
        binary_rules.addElement(forall);
        unary_rules.addElement(exists);
        unary_rules.addElement(entity_disjunct);
        unary_rules.addElement(equal);
        binary_rules.addElement(embed_algebra);
        unary_rules.addElement(match);
        unary_rules.addElement(size);
        unary_rules.addElement(split);
        unary_rules.addElement(record);
        macro_handler.front_end = front_end;
        macro_handler.addListeners(this);
        front_end.reader = reader;
        front_end.agenda = agenda;
        front_end.concepts = concepts;
        front_end.conjectures = conjectures;
        front_end.equivalences = equivalences;
        front_end.non_existences = non_existences;
        front_end.implicates = implicates;
        front_end.timer = timer;
        front_end.entities = entities;
        front_end.getMacroFiles();
        reader.user_functions = user_functions;
        explanation_handler.operating_system = operating_system;
        embed_algebra.mace.operating_system = operating_system;
        report_generator.report_output_text = front_end.report_output_text;
        report_generator.command_line_arguments = command_line_arguments;
        lakatos.agenda = agenda;
        lakatos.concepts = concepts;
        lakatos.equivalences = equivalences;
        lakatos.non_existences = non_existences;
        lakatos.implicates = implicates;
        lakatos.implications = implications;
        lakatos.entities = entities;
        lakatos.production_rules = production_rules;
        lakatos.front_end = front_end;
        lakatos.guider = guider;
        embed_algebra.input_files_directory = input_files_directory;
        report_generator.input_files_directory = input_files_directory;
        specification_handler.specifications = specifications;
        specification_handler.concepts = concepts;
        specification_handler.non_existences = non_existences;

        reflect.condition_shorthands.put("cross domain", "is_cross_domain == true");
        reflect.condition_shorthands.put("integer output", "types.size() == 2 && types.elementAt(1) == integer");
        reflect.condition_shorthands.put("specialisation", "types.size() == 1");
        reflect.condition_shorthands.put("element type", "types.size() == 2 && types.elementAt(1) == element");
        reflect.condition_shorthands.put("learned", "has_required_categorisation == true");
        reflect.condition_shorthands.put("arithmeticb", "productionRuleUsedName() == arithmeticb");//friday
        reflect.condition_shorthands.put("compose", "productionRuleUsedName() == compose");
        reflect.condition_shorthands.put("disjunct", "productionRuleUsedName() == disjunct");
        reflect.condition_shorthands.put("embed_algebra", "productionRuleUsedName() == embed_algebra");
        reflect.condition_shorthands.put("embed_graph", "productionRuleUsedName() == embed_graph");
        reflect.condition_shorthands.put("equal", "productionRuleUsedName() == equal");
        reflect.condition_shorthands.put("exists", "productionRuleUsedName() == exists");
        reflect.condition_shorthands.put("entity_disjunct", "productionRuleUsedName() == entity_disjunct");
        reflect.condition_shorthands.put("forall", "productionRuleUsedName() == forall");
        reflect.condition_shorthands.put("concept_forced", "rh_concept.construction.forced == true");
        reflect.condition_shorthands.put("forced", "construction.forced == true");
        reflect.condition_shorthands.put("match", "productionRuleUsedName() == match");
        reflect.condition_shorthands.put("negate", "productionRuleUsedName() == negate");
        reflect.condition_shorthands.put("record", "productionRuleUsedName() == record");
        reflect.condition_shorthands.put("size", "productionRuleUsedName() == size");
        reflect.condition_shorthands.put("split", "productionRuleUsedName() == split");

        reflect.condition_shorthands.put("segregated", "involves_segregated_concepts == true");
        reflect.condition_shorthands.put("prime implicate", "is_prime_implicate == true");
        reflect.condition_shorthands.put("instantiation", "involves_instantiation == true");
        reflect.condition_shorthands.put("not_instantiation", "involves_instantiation == false");
        reflect.condition_shorthands.put("axiom", "proof_status == axiom");
        reflect.condition_shorthands.put("equivalence", "object_type == Equivalence");
        reflect.condition_shorthands.put("forced", "");
        reflect.condition_shorthands.put("implicate", "object_type == Implicate");
        reflect.condition_shorthands.put("near_equiv", "object_type == NearEquivalence");
        reflect.condition_shorthands.put("near_imp", "object_type == NearImplication"); //alisonp
        reflect.condition_shorthands.put("implication", "object_type == Implication");
        reflect.condition_shorthands.put("non-exists", "object_type == NonExists");
        reflect.condition_shorthands.put("not prime implicate", "is_prime_implicate == false");
        reflect.condition_shorthands.put("proved", "proof_status == proved");
        reflect.condition_shorthands.put("undetermined", "proof_status != proved && proof_status != disproved");
        reflect.condition_shorthands.put("disproved", "proof_status == disproved");
        reflect.condition_shorthands.put("sos", "proof_status == sos");
        reflect.condition_shorthands.put("time", "proof_status == time");
        reflect.condition_shorthands.put("syntax error", "proof_status == syntax error");

        reflect.value_shorthands.put("devel steps", "development_steps_num");
    }

    /** Tells the compose production rule that some non-trivial overlapping of the
     * subobjects of the two concepts must occur.
     */

    public void setSubobjectOverlap(boolean tf)
    {
        compose.subobject_overlap = tf;
    }

    /** Sets the complexity limit for the theory and passes this information on to the
     * agenda.
     */

    public void setComplexityLimit(int c)
    {
        complexity_limit=c;
        agenda.complexity_limit=c;
    }

    /** Sets the limit for the number of items on the agenda, and passes this information on
     * to the agenda.
     */

    public void setAgendaSizeLimit(int a)
    {
        agenda_size_limit=a;
        agenda.agenda_size_limit=a;
    }

    /** Chooses the search strategy of depth first exhaustive search.
     */

    public void setDepthFirst(boolean b)
    {
        depth_first = b;
        agenda.depth_first = b;
    }

    /** Chooses the search strategy of random exhaustive search.
     */

    public void setRandom(boolean b)
    {
        random = b;
        agenda.random = b;
    }

    /** Given the name of a production rule, this returns the production rule with that name
     * which is being used in the theory.
     */

    public ProductionRule productionRuleFromName(String name)
    {
        int i=0;
        while(i<all_production_rules.size()
                && !((ProductionRule)all_production_rules.elementAt(i)).getName().equals(name)) i++;
        return ((ProductionRule)all_production_rules.elementAt(i));
    }

    /** Given the specifications of a concept, this returns the concept from the theory
     * with those specifications (or null otherwise).
     */

    public Concept getConcept(Vector specifications)
    {
        Vector sorted_specs = specification_handler.addSpecifications(specifications);
        boolean found = false;
        Concept output = null;
        for (int i=0; i<concepts.size() && !found; i++)
        {
            output = (Concept)concepts.elementAt(i);
            boolean reject = false;
            if (output.specifications.size()!=specifications.size())
                reject = true;
            for (int j=0; j<output.specifications.size() && !reject; j++)
            {
                Specification spec = (Specification)output.specifications.elementAt(j);
                if (spec!=((Specification)specifications.elementAt(j)))
                    reject = true;
            }
            if (!reject)
                found = true;
        }
        return output;
    }

    /** Given the identity number of a concept, this returns the concept from the theory with
     * that id.
     */

    public Concept getConcept(String idnum)
    {
        int i=0;
        boolean found = false;
        Concept output = new Concept();
        while (i<concepts.size() && !found)
        {
            output = (Concept)concepts.elementAt(i);
            if (output.id.equals(idnum))
                found = true;
            if (output.alternative_ids.contains(idnum))
                found = true;
            i++;
        }
        if (found)
            return output;
        return(new Concept());
    }

    /** Given the identity number of a conjecture, this returns the conjecture
     * from the theory with that id.
     */

    public Conjecture getConjecture(String idnum)
    {
        int i=0;
        boolean found = false;
        Conjecture output = new Conjecture();
        while (i<conjectures.size() && !found)
        {
            output = (Conjecture)conjectures.elementAt(i);
            if (output.id.equals(idnum))
                found = true;
            i++;
        }
        if (found)
            return output;
        return(new Conjecture());
    }

    /** Given the name of a concept, this returns the concept from the theory with that name.
     */

    public Concept conceptFromName(String name)
    {
        int i=0;
        while (i<concepts.size() && !((Concept)concepts.elementAt(i)).name.equals(name))
            i++;
        if (i<concepts.size())
            return((Concept)concepts.elementAt(i));
        return(new Concept());
    }

    /** This returns the number of concepts in the theory.
     */

    public int numberOfConcepts()
    {
        return concepts.size();
    }

    /** Time a part of the step
     */

    public void addToTimer(String task)
    {
        timer.addTo(task);
        status(task);
    }

    /** Change the status label.
     */

    public void status(String status)
    {
        front_end.status_label.setText(status);
    }

    /** This recalculates the productivities of all the concepts given a set
     * of concepts which might have changed productivities.
     */

    private void reCalculateProductivities(Vector changed_concepts)
    {
        if (measure_concept.measure_concepts == false)
            return;

        boolean need_to_reorder = false;
        for (int i=0; i<changed_concepts.size(); i++)
        {
            Concept concept = (Concept)changed_concepts.elementAt(i);
            boolean see_if = measure_concept.updateNormalisedProductivities(concept, false);
            if (see_if)
                need_to_reorder = true;
        }

        if (need_to_reorder && measure_concept.productivity_weight!=0.0)
            agenda.orderConcepts();
    }

    /** This adds a set of implicates to the theory, checking whether each one
     * is subsumed by one already in the theory. It also extracts prime implicates,
     * if these have been requested.
     */

    public Vector addImplicates(Vector implicates_to_add, Conjecture parent_conjecture, String timer_num)
    {
        Vector output = new Vector();
        boolean need_to_reorder_agenda = false;

        for (int i=0; i<implicates_to_add.size(); i++)
        {
            Implicate new_implicate = (Implicate)implicates_to_add.elementAt(i);
            Specification new_spec = specification_handler.addSpecification(new_implicate.goal);
            new_implicate.goal = new_spec;
            new_implicate.when_constructed = seconds_elapsed;

            // X.1 Check whether an implicate has been seen already or a more general one has been seen

            addToTimer(timer_num + ".1 Checking whether an implicate has been seen already or a more general one has been seen");
            if (!explanation_handler.conjectureSeenAlready(new_implicate) &&
                    explanation_handler.getSubsumingImplicate(new_implicate, this,
                            require_proof_in_subsumption)==null)
            {
                // X.2 Adding implicate to the theory

                addToTimer(timer_num + ".2 Adding implicate to the theory");
                explanation_handler.addConjecture(new_implicate);
                Vector prime_implicates_arising = new Vector();
                new_implicate.premise_concept.implicates.addElement(new_implicate);

                if (parent_conjecture.proof_status.equals("proved"))
                {
                    // X.3 Deal with a new implicate proved by its parent

                    addToTimer(timer_num + ".3 Dealing with a new implicate proved by its parent");
                    new_implicate.proof_status = "proved";
                    new_implicate.proof = "Proof of parent conjecture: \n" + parent_conjecture.proof;
                    new_implicate.explained_by = "Proof of parent";
                    new_implicate.proof_length = parent_conjecture.proof_length;
                    new_implicate.proof_level = parent_conjecture.proof_level;
                    if (parent_conjecture.is_trivially_true)
                    {
                        new_implicate.is_trivially_true = true;
                        new_implicate.explained_by = "being trivial";
                    }
                }
                else
                {
                    // X.4 Attempting to settle a new implicate

                    addToTimer(timer_num + ".4 Attempting to settle a new implicate");
                    explanation_handler.explainConjecture(new_implicate, this, timer_num + ".4");
                }

                // X.5 Handle the storage of a new implicate

                addToTimer(timer_num + ".5 Handling the storage of a new implicate");

                if (new_implicate.proof_status.equals("disproved"))
                {
                    for (int j=0; j<new_implicate.counterexamples.size(); j++)
                        output.addElement(new_implicate.counterexamples.elementAt(j));
                }

                // X.6 Extract the prime implicates from this conjecture //

                addToTimer(timer_num + ".6 Extracting prime implicates");

                if (extract_prime_implicates_from_implicates)
                {
                    // X.7 First generate the possible prime implicates //

                    addToTimer(timer_num + ".7 Generating possible prime implicates");
                    Vector possible_prime_implicates =
                            new_implicate.possiblePrimeImplicates();

                    // Set the new_implicate to be a prime implicate (this may change later)

                    new_implicate.is_prime_implicate = true;

                    // Now, run through the PIs and see if any can be proved.

                    for (int j=0; j<possible_prime_implicates.size(); j++)
                    {
                        Implicate new_prime_implicate = (Implicate)possible_prime_implicates.elementAt(j);
                        new_prime_implicate.parent_conjectures.addElement(parent_conjecture);
                        new_prime_implicate.parent_conjectures.addElement(new_implicate);
                        new_prime_implicate.when_constructed = seconds_elapsed;
                        new_implicate.child_conjectures.addElement(new_prime_implicate);

                        // X.8 Check whether this implicate has already been found by HR.
                        // Also checks whether a previous pi subsumes this one.

                        addToTimer(timer_num + ".8 Checking whether a PI candidate has been seen before");
                        boolean already_seen = false;
                        String previous_result = explanation_handler.previousResult(new_prime_implicate);
                        if (previous_result==null)
                        {
                            Implicate subsuming_implicate =
                                    explanation_handler.getSubsumingImplicate(new_prime_implicate, this, require_proof_in_subsumption);
                            if (subsuming_implicate!=null)
                            {
                                already_seen = true;
                                new_implicate.is_prime_implicate = false;
                                explanation_handler.addConjecture(new_prime_implicate);
                            }
                        }
                        else
                        {
                            already_seen = true;
                            if (previous_result.equals("proved"))
                                new_implicate.is_prime_implicate = false;
                        }

                        if (!already_seen)
                        {
                            // X.9 Add prime implicate candidate to the theory

                            addToTimer(timer_num + ".9 Adding prime implicate candidate to the theory");
                            explanation_handler.addConjecture(new_prime_implicate);

                            // X.10 The implicate is not subsumed, so now we try and prove it

                            addToTimer(timer_num + ".10 Attempting to prove a possible prime implicate");
                            explanation_handler.explainConjecture(new_prime_implicate, this, timer_num + ".10");
                            if (new_prime_implicate.proof_status.equals("disproved"))
                            {
                                for (int k=0; k<new_implicate.counterexamples.size(); k++)
                                    output.addElement(new_implicate.counterexamples.elementAt(k));
                            }

                            // If the prime implicate is indeed true, then we need to handle it.

                            if (new_prime_implicate.proof_status.equals("proved"))
                            {
                                new_prime_implicate.is_prime_implicate = true;
                                new_implicate.is_prime_implicate = false;
                                prime_implicates_arising.addElement(new_prime_implicate);
                                new_prime_implicate.id = Integer.toString(numberOfConjectures());
                            }
                        }
                    }
                }

                // If the new implicate is still a prime implicate, then we must handle it.

                if (new_implicate.is_prime_implicate ||
                        (!new_implicate.is_prime_implicate && keep_implicates))
                    prime_implicates_arising.insertElementAt(new_implicate,0);

                // X.11 Now add all the prime implicates to the theory.

                if (keep_prime_implicates || keep_implicates)
                {
                    addToTimer(timer_num + ".11 Adding set of prime implicates to the theory");
                    for (int j=0; j<prime_implicates_arising.size(); j++)
                    {
                        Implicate pi = (Implicate)prime_implicates_arising.elementAt(j);
                        pi.id=Integer.toString(numberOfConjectures());
                        pi.checkSegregation(agenda);
                        implicates.addElement(pi);
                        prime_implicates.addElement(pi);
                        conjectures.addElement(pi);
                        measure_conjecture.measureConjecture(pi, implicates);
                        updateFrontEnd(pi);
                        if (measure_concept.updateImpConjectureScore(pi))
                            need_to_reorder_agenda = true;
                    }
                }
            }
        }

        // X.12 Reordering the agenda

        if (need_to_reorder_agenda)
        {
            addToTimer(timer_num + ".12 Reordering the agenda after adding an implicate");
            agenda.orderConcepts();
        }
        return output;
    }

    /** The next theory formation step is carried out, and the resulting concept or conjecture
     * is dealt with.
     */

    // boolean perform_test = true;

    public Step theoryFormationStep()
    {
        //test - thurs
        //System.out.println("Thursday - testForce");
        //TheoryConstituent tc_test = lakatos.testForce(concepts,this, perform_test);
        //System.out.println("Thursday - done testForce - got " + tc_test);

        /***********************/
        // STAGE ONE           //
        // INITIALISATION      //
        /***********************/

        // 1.1 Initialisation //

        addToTimer("1.1 Initialisation of step");
        Concept new_concept = new Concept();
        steps_taken++;
        step_counter++;
        agenda.step_number = steps_taken;
        seconds_elapsed = (new Long(((System.currentTimeMillis() - run_start_time)/1000))).intValue();

        // 1.2 Decide whether to do a garbage collection //

        addToTimer("1.2 Garbage collection");
        Double st = new Double(steps_taken);
        double d=(st.doubleValue())/gc_when;
        if(d==Math.floor(d))
        {
            Runtime rt = Runtime.getRuntime();
            rt.runFinalization();
            rt.gc();
        }

        if(step_counter==1000)
        {
            step_counter=0;
            System.out.println(steps_taken);
            Runtime rt = Runtime.getRuntime();
            rt.gc();
            System.out.println("Total mem: " + (rt.totalMemory()/1024) + "k");
            System.out.println("Concepts = " + Integer.toString(concepts.size()));
            System.out.println("-----------------");
        }

        /************************/
        // STAGE TWO            //
        // GET STEP FROM AGENDA //
        /************************/

        // 2.1 Get step from agenda //

        addToTimer("2.1 Getting next step from agenda");
        Step step = agenda.nextStep(unary_rules,binary_rules,this);
        //System.out.println("Step from agenda: "+ step.asString());
        step.id = Integer.toString(steps_taken);
        if (keep_steps)
            previous_steps.addElement(step);

        // 2.2 React to the new step //

        addToTimer("2.2 Reacting to a new step");
        react.reactTo(step, "new_step");

        // 2.3 Update the front end agenda screen //

        addToTimer("2.3 Updating the front end agenda screen");
        if (front_end.auto_update_agenda.getState())
            updateAgendaFrontEnd((new Integer(front_end.auto_update_text.getText())).intValue());

        // POSSIBLE END POINT //
        // If there are no more steps left return exhausted search in the stats. //

        if (step.size()==0)
        {
            step.result_of_step_explanation = "exhausted search";
            updateFrontEnd();
            return step;
        }

        // 2.4 Getting step details //

        addToTimer("2.4 Getting step details");
        front_end.current_step_text.setText(step.asString());
        Vector parent_concepts = (Vector)((Vector)step.elementAt(0)).clone();
        ProductionRule prod_rule = (ProductionRule)step.elementAt(1);
        Vector parameters = (Vector)step.elementAt(2);

        // 2.5 Update the number of steps which the parent concepts
        // have been involved in. Also check whether the concept
        // is cross domain.

        addToTimer("2.5 Updating parents number of steps involved");
        Vector temp_domains = new Vector();
        for (int i=0; i<parent_concepts.size(); i++)
        {
            Concept parent = (Concept)parent_concepts.elementAt(i);
            if (parent.is_cross_domain)
                new_concept.is_cross_domain = true;
            parent.development_steps_num++;
            if (!temp_domains.contains(parent.domain))
                temp_domains.addElement(parent.domain);
            if (front_end.measure_concepts_check.getState())
                measure_concept.updateNormalisedDevelopments(parent, false);
        }
        if (temp_domains.size()>1)
            new_concept.is_cross_domain = true;

        // 2.6 Use the top substitutable parent concepts if there are some

        addToTimer("2.6 Using substitutable parent concepts");
        for (int i=0; i<parent_concepts.size(); i++)
        {
            Concept parent = (Concept)parent_concepts.elementAt(i);
            parent_concepts.setElementAt(parent.getSubstitutableConcept(), i);
        }

        /***********************/
        // STAGE THREE         //
        // GENERATE NEW SPECS  //
        /***********************/

        // 3.1 Get the types (of objects in the datatable) of the new concept //

        addToTimer("3.1 Getting new types");
        Vector new_types = prod_rule.transformTypes(parent_concepts,parameters);
        String new_domain = (String)new_types.elementAt(0);
        new_concept.types = new_types;

        // 3.2 Generate new specifications //

        addToTimer("3.2 Generating new specifications");
        Vector new_functions = new Vector();
        Vector new_specifications =
                prod_rule.newSpecifications(parent_concepts,parameters,this,new_functions);
        new_concept.specifications = specification_handler.addSpecifications(new_specifications);
        new_concept.setSkolemisedRepresentation();
        new_concept.parents = parent_concepts;

        // 3.3 Generate new functions //

        addToTimer("3.3 Generating new functions");
        Vector function_strings = new Vector();
        for (int i=0; i<new_functions.size(); i++)
        {
            Function f = (Function)new_functions.elementAt(i);
            if (!function_strings.contains(f.writeFunction()))
            {
                new_concept.functions.addElement(f);
                function_strings.addElement(f.writeFunction());
            }
        }

        // 3.4 See if the new concept has exactly the same specifications as an old concept,
        // or it has non-existent or inconsistent specifications.
        // For example, non-existent specs are: f(a) = b & f(a) = c where c!=b.
        // The code for this is in the deduction class.

        addToTimer("3.4 Checking the specifications for no-goods");
        Vector is_no_good_vector =
                specification_handler.rejectSpecifications(forced_id, step, new_concept, this);
        String is_no_good = (String)is_no_good_vector.elementAt(0);

        // POSSIBLE END POINT //
        // 3.5 Return if the concept is no good.

        if (!is_no_good.equals("is good"))
        {
            addToTimer("3.5 Returning: syntactically no good (or equivalent) concept");
            step.result_of_step_explanation = is_no_good;
            new_concept.types = prod_rule.transformTypes(parent_concepts,parameters);

            if (is_no_good_vector.size()==2)
            {
                // System.out.println("is_no_good_vector is " + is_no_good_vector);
// 	    System.out.println("2nd element is " + ((Concept)is_no_good_vector.elementAt(1)).writeDefinition());
// 	    System.out.println("is_no_good is " + is_no_good);
// 	    System.out.println("sun night: 1");
                agenda.recordForcedName(step, (Concept)is_no_good_vector.elementAt(1));
            }
            if (is_no_good_vector.size()>1 && is_no_good.equals("old concept with same specifications"))
                step.result_of_step = (Concept)is_no_good_vector.elementAt(1);

            // 3.6 Recalculate productivities in light of failed step //

            addToTimer("3.6 Measuring productivity of parent concepts after rejected specification step");
            reCalculateProductivities(parent_concepts);

            // 3.7 Update the front end //

            addToTimer("3.7 Updating front end after rejected specification step");
            updateFrontEnd();
            return step;
        }

        // BY THIS STAGE, WE KNOW THAT THE CONCEPT IS //
        // A SYNTACTICALLY DIFFERENT CONCEPT TO THE REST //
        // AND NOT SYNTACTICALLY NON-EXISTENT //

        /**************************/
        // STAGE FOUR             //
        // GENERATE NEW EXAMPLES  //
        /**************************/

        // 4.1 Generate new datatable //

        addToTimer("4.1 Generating new datatable");
        Vector parent_datatables = new Vector();
        for(int i=0;i<parent_concepts.size();i++)
            parent_datatables.addElement(((Concept)parent_concepts.elementAt(i)).datatable);

        prod_rule.startStopWatch();
        Datatable new_table = prod_rule.transformTable(parent_datatables, parent_concepts,
                parameters, concepts);
        new_table.trimRowsToSize();
        new_concept.datatable_construction_time = prod_rule.stopWatchTime();
        Concept first_parent = (Concept)parent_concepts.elementAt(0); //take out - alisonp


        // POSSIBLE END POINT //
        // 4.2 If new table is empty, this step could not be performed //

        if (new_table.size()==0)
        {
            addToTimer("4.2 Returning: couldn't perform step");
            step.result_of_step_explanation = "couldn't perform step";

            // 4.3 Recalculate productivities in light of failed step //

            addToTimer("4.3 Measuring productivity of parent concepts");
            reCalculateProductivities(parent_concepts);

            // 4.4 Update the front end //

            addToTimer("4.4 Updating front end");
            updateFrontEnd();
            return step;
        }

        // 4.5 Get new ID for the concept //

        addToTimer("4.5 Getting new ID");
        String new_id = "";
        if (forced_id.equals(""))
        {
            new_id = Integer.toString(concepts.size()+1)+"_0";
            new_id = new_domain.substring(0,1) + new_id;
        }
        else
            new_id = forced_id;

        // 4.6 Compliment the datatable //

        addToTimer("4.6 Complimenting the table");
        complimentTable(new_table,new_types);

        /**************************/
        // STAGE FIVE             //
        // NON-EXISTENCE CONJ.    //
        /**************************/

        // 5.1 Get ancestors of the new concepts //

        // Collate the ancestors of the new concept. Also add in the
        // construction steps from the old concepts into the construction
        // history of the new concepts. This will be needed for the new concept,
        // or for an equivalence or non-exists conjecture (to work out surprisingness,
        // complexity, etc.)

        addToTimer("5.1 Getting ancestors of new concept");
        Vector flat_old_concepts = new Vector();
        Vector construction_history = new Vector();
        Concept old_concept = new Concept();
        for(int i=0;i<parent_concepts.size();i++)
        {
            old_concept = (Concept)parent_concepts.elementAt(i);
            flat_old_concepts.addElement(old_concept.id);
            Vector old_ancestor_ids = old_concept.ancestor_ids;
            for(int j=0;j<old_ancestor_ids.size();j++)
            {
                String ancestor_id = (String)old_ancestor_ids.elementAt(j);
                if (!new_concept.ancestor_ids.contains(ancestor_id))
                    new_concept.ancestor_ids.addElement(ancestor_id);
                Concept ancestor = getConcept(ancestor_id);
                if (!new_concept.ancestors.contains(ancestor))
                    new_concept.ancestors.addElement(ancestor);
                for (int k=0; k<ancestor.construction_history.size();k++)
                {
                    Vector flat_step = (Vector)ancestor.construction_history.elementAt(k);
                    if (!construction_history.contains(flat_step))
                        construction_history.addElement(flat_step);
                }
            }
        }

        Vector this_flat_step = new Vector();
        this_flat_step.addElement(new_id);
        this_flat_step.addElement(prod_rule.getName());
        this_flat_step.addElement(flat_old_concepts);
        this_flat_step.addElement(parameters);
        construction_history.addElement(this_flat_step);
        new_concept.construction_history = construction_history;

        new_concept.complexity = new_concept.ancestor_ids.size()+1;

        boolean non_existence = false;
        if (make_non_exists_from_empty)
            non_existence = new_table.allEmptyTuples(this);

        Vector new_entities = new Vector();

        // 5.2 Construct the non-existence conjecture if there are no examples for concept

        if (non_existence)
        {
            // 5.2 Make the non-existence conjecture //

            addToTimer("5.2 Making non-existence conjecture");
            new_concept.types = new_types;
            new_concept.object_type = (String)new_types.elementAt(0);
            new_concept.construction = step;
            new_concept.is_user_given = false;
            new_concept.step_number = steps_taken;
            new_concept.domain = new_domain;
            new_concept.parents = parent_concepts;
            NonExists non_existence_conjecture =
                    new NonExists(new_concept,Integer.toString(numberOfConjectures()));
            non_existence_conjecture.step = step;
            step.result_of_step = non_existence_conjecture;
            non_existence_conjecture.when_constructed = seconds_elapsed;

            // 5.3 Check for previous non-existence which is the same //

            addToTimer("5.3 Checking for previous non-existence which is the same as the new one");
            if (keep_non_existences &&
                    !explanation_handler.conjectureSeenAlready(non_existence_conjecture))
            {
                explanation_handler.addConjecture(non_existence_conjecture);

                // 5.4 Add the new non-existence conjecture to the theory

                addToTimer("5.4 Adding a new non-existence conjecture to the theory");
                new_entities = addNonExistsToTheory(non_existence_conjecture, new_concept);
            }

            // 5.5 Add counterexamples to the theory as new objects of interest

            if (!new_entities.isEmpty())
            {
                addToTimer("5.5 Adding a new entity to the theory (from counterexample to non-exists)");
                new_concept.datatable = new_table;
                for (int i=0; i<new_entities.size(); i++)
                {
                    Entity new_entity = (Entity)new_entities.elementAt(i);
                    addNewEntityToTheory(new_entity, new_concept.domain, "5.5");
                    new_concept.updateDatatable(concepts, new_entity);
                }
                new_entities = new Vector();
                non_existence = false;
            }

            // 5.6 Tell the agenda that a (possibly) forced step resulted in a non-existence conjecture

            if (new_entities.isEmpty())
            {
                addToTimer("5.6 Telling agenda about a non-existence conjecture");
                agenda.recordNonExistentConcept(step);
            }
        }

        /**************************/
        // STAGE SIX              //
        // EQUIVALENCE CONJECTURE //
        /**************************/

        // 6.1 Identify whether the new datatable is the same as that of an old concept //

        addToTimer("6.1 Checking for match with previous datatables");
        for(int j=0; j<new_concept.specifications.size(); j++)
        {
            Specification spec = (Specification)new_concept.specifications.elementAt(j);

        }
        boolean equivalence = false;
        String first_tuples = "";
        Concept same_datatable_concept = null;
        //System.out.println("make_equivalences_from_equality is " + make_equivalences_from_equality);

        if (!non_existence && make_equivalences_from_equality)
        {
            first_tuples = new_table.firstTuples();
            Vector same_datatable_concept_vector = (Vector)datatable_hashtable.get(first_tuples);
            if (same_datatable_concept_vector!=null)
            {
                for (int i=0; i<same_datatable_concept_vector.size(); i++)
                {
                    Concept possibility = (Concept)same_datatable_concept_vector.elementAt(i);
                    if (possibility.datatable.isIdenticalTo(this, new_table))
                    {
                        if (possibility.types.toString().equals(new_types.toString()))
                        {
                            for(int j=0; j<possibility.specifications.size(); j++)
                            {
                                Specification spec = (Specification)specifications.elementAt(j);
                                spec.type.equals("negate");
                            }
                            same_datatable_concept = possibility;
                            break;
                        }
                    }
                }
            }
        }

        if (same_datatable_concept!=null)
            same_datatable_concept = same_datatable_concept.getSubstitutableConcept();

        if (same_datatable_concept!=null && skip_equivalence_conjectures)
        {
            step.result_of_step_explanation = "Skipped equiv conjecture";
            return step;
        }

        Equivalence equiv_conj = new Equivalence();
        if(!non_existence && same_datatable_concept!=null)
        {
            // 6.2 Make an equivalence conjecture //

            addToTimer("6.2 Making an equivalence conjecture");
            equivalence = true;
            new_concept.types = new_types;
            new_concept.object_type = (String)new_types.elementAt(0);
            new_concept.construction = step;
            new_concept.is_user_given = false;
            new_concept.step_number = steps_taken;
            new_concept.domain = (String)new_types.elementAt(0);
            new_concept.applicability=same_datatable_concept.applicability;
            new_concept.variety=same_datatable_concept.variety;
            equiv_conj =
                    new Equivalence(same_datatable_concept,new_concept,Integer.toString(numberOfConjectures()));

            // 6.3 Check whether we've already seen this conjecture //

            addToTimer("6.3 Checking for repetition of equivalence conjecture");
            int i=0;

            // POSSIBLE END POINT //
            // 6.4 Return if the equivalence conjecture is a repetition //

            if (keep_equivalences && explanation_handler.conjectureSeenAlready(equiv_conj))
            {
                addToTimer("6.4 Returning: equivalence conjecture was repeated");
                step.result_of_step_explanation = "repeat equivalence conjecture formed";
                //System.out.println("sun night: 2");
                agenda.recordForcedName(step, same_datatable_concept);

                // 6.5 Recalculate productivities in light of failed step //

                addToTimer("6.5 Measuring productivity of parent concepts");
                reCalculateProductivities(parent_concepts);

                // 6.6 Update the front end //

                addToTimer("6.6 Updating front end");
                updateFrontEnd();
                return step;
            }

            // POSSIBLE END POINT //
            // 6.7 Return if the equivalence conjecture is trivial //

            addToTimer("6.7 Checking if an equivalence conjecture is trivial");
            if (equiv_conj.isTrivial())
            {
                step.result_of_step_explanation = "trivial equivalence conjecture formed";
                //System.out.println("sun night: 3");
                agenda.recordForcedName(step, same_datatable_concept);

                // 6.8 Recalculate productivities in light of failed step //

                addToTimer("6.8 Measuring productivity of parent concepts");
                reCalculateProductivities(parent_concepts);

                // 6.9 Update the front end //

                addToTimer("6.9 Updating front end");
                updateFrontEnd();
                return step;
            }

            // POSSIBLE END POINT //
            // 6.10 Return if the equivalence conjecture is a tautology //

            addToTimer("6.10 Checking if a conjecture is a tautology");
            if (specification_handler.isTautology(equiv_conj))
            {
                step.result_of_step_explanation = "equivalence conjecture shown to be a tautology";
                //System.out.println("sun night: 4");
                agenda.recordForcedName(step, same_datatable_concept);

                // 6.11 Recalculate productivities in light of failed step //

                addToTimer("6.11 Measuring productivity of parent concepts");
                reCalculateProductivities(parent_concepts);

                // 6.12 Update the front end //

                addToTimer("6.12 Updating front end");
                updateFrontEnd();
                return step;
            }

            // 6.13 Add equivalence conjecture to theory //

            addToTimer("6.13 Adding an equivalence conjecture to the theory");
            step.result_of_step_explanation = "new equivalence conjecture formed";
            step.result_of_step = equiv_conj;
            equiv_conj.step = step;
            equiv_conj.when_constructed = seconds_elapsed;

            Vector counterexamples_from_equivalence =
                    addEquivalenceToTheory(equiv_conj, same_datatable_concept, new_concept);

            for (i=0; i<counterexamples_from_equivalence.size(); i++)
                new_entities.addElement(counterexamples_from_equivalence.elementAt(i));
        }

        /**************************/
        // STAGE SEVEN            //
        // COUNTEREXAMPLE         //
        /**************************/

        // If a new entity has been found as a counterexample, then we must update the
        // datatable and measures of each old concept. We must also recalculate the datatable
        // for the new concept (this is done later, though).

        // 7.1 Add counterexamples to the theory as a new object of interest

        addToTimer("7.1 Adding new entities to the theory (from counterexample to equivalence)");
        for (int i=0; i<new_entities.size(); i++)
        {
            Entity new_entity = (Entity)new_entities.elementAt(i);
            addNewEntityToTheory(new_entity, new_concept.domain, "7.1");
        }

        // POSSIBLE END POINT //
        // 7.2 Get more concept details

        addToTimer("7.2 Getting more concept details");
        if (step.productionRule().getName().equals("split"))
        {
            Vector columns = (Vector)step.parameters().elementAt(0);
            if (columns.size()==1 && ((String)columns.elementAt(0)).equals("0"))
            {
                new_concept.is_single_entity = true;
                new_concept.is_entity_instantiations = true;
            }
        }

        if (!new_concept.is_entity_instantiations)
        {
            new_concept.is_entity_instantiations = true;
            for (int i=0; i<parent_concepts.size(); i++)
            {
                Concept parent = (Concept)parent_concepts.elementAt(i);
                parent.children.addElement(new_concept);
                if (!parent.is_entity_instantiations)
                    new_concept.is_entity_instantiations = false;
            }
        }

        if (step.productionRule().getName().equals("negate") &&
                !new_concept.is_entity_instantiations)
        {
            Concept parent1 = (Concept)parent_concepts.elementAt(0);
            Concept parent2 = (Concept)parent_concepts.elementAt(1);
            if (parent1.entity_concept==parent2 && parent1.is_entity_instantiations)
                new_concept.is_entity_instantiations = true;
        }

        boolean substitute_concept = false;

        // 7.3 Substitute concept definition

        if (equivalence && allow_substitutions)
        {
            addToTimer("7.3 Substituting a concept definition");
            if (same_datatable_concept.complexity > new_concept.complexity &&
                    !new_concept.is_entity_instantiations)
                substitute_concept = true;
            if (!same_datatable_concept.is_entity_instantiations && new_concept.is_entity_instantiations)
                substitute_concept = true;
            if (same_datatable_concept.is_entity_instantiations && new_concept.is_entity_instantiations &&
                    same_datatable_concept.complexity > new_concept.complexity)
                substitute_concept = true;
        }

        // Returning the new equivalence conjecture

        if (equivalence && new_entities.isEmpty())
        {
            boolean matched = false;
            addToTimer("7.5 Adding alternative constructions to old concept in an equivalence conjecture");
            if (!keep_equivalences)
            {
                String new_concept_def = new_concept.writeDefinition("ascii");
                for (int i=0; i<same_datatable_concept.conjectured_equivalent_concepts.size() && !matched; i++)
                {
                    Concept alt_concept =
                            (Concept)same_datatable_concept.conjectured_equivalent_concepts.elementAt(i);
                    if (alt_concept.writeDefinition("ascii").equals(new_concept_def))
                        matched = true;
                }
            }

            if (!matched)
            {
                if (!skip_equivalence_conjectures)
                    same_datatable_concept.conjectured_equivalent_constructions.addElement(step);
                new_concept.datatable.setNumberOfTuples();
                new_concept.datatable = new_table;
                if (!skip_equivalence_conjectures)
                    same_datatable_concept.conjectured_equivalent_concepts.addElement(new_concept);
                new_concept.equivalence_conjecture = equiv_conj;
                String old_id = same_datatable_concept.id;
                if (old_id.indexOf("_")>=0)
                    old_id = same_datatable_concept.id.substring(0,same_datatable_concept.id.indexOf("_"));
                if (!substitute_concept)
                {
                    new_id = old_id + "(" +
                            Integer.toString(same_datatable_concept.conjectured_equivalent_concepts.size()+1)+")";
                }
                else
                {
                    new_id = old_id + "_"+
                            Integer.toString(same_datatable_concept.substitutable_concepts.size()+1);
                }
                new_concept.id = new_id;
                if (substitute_concept)
                    same_datatable_concept.substitutable_concepts.addElement(new_concept);
            }
            //System.out.println("sun night: 5");
            agenda.recordForcedName(step, same_datatable_concept);

            if (!forced_id.equals(""))
                new_concept.alternative_ids.addElement(forced_id);

            // 7.6 Recalculate productivities in light of failed (conjecture) step //

            addToTimer("7.6 Measuring productivity of parent concepts after equivalence conjecture formed");
            reCalculateProductivities(parent_concepts);

            // 7.7 Update the front end //

            if (!substitute_concept)
            {
                addToTimer("7.7 Updating front end after equivalence conjecture formed");
                updateFrontEnd();
                return step;
            }
        }

        // POSSIBLE END POINT //

        if (non_existence && new_entities.isEmpty())
        {
            step.result_of_step_explanation = "non-existence conjecture formed";

            // 7.8 Recalculate productivities in light of failed step //

            addToTimer("7.8 Measuring productivity of parent concepts");
            reCalculateProductivities(parent_concepts);

            // 7.9 Update the front end //

            addToTimer("7.9 Updating front end");
            updateFrontEnd();
            return step;
        }

        /**************************/
        // STAGE EIGHT            //
        // BUILD NEW CONCEPT      //
        /**************************/

        // 8.1 Calculate additional properties of the concept //

        addToTimer("8.1 Calculating additional details about a new concept");
        new_concept.ancestor_ids.addElement(new_id);
        if (prod_rule.isCumulative())
            new_concept.is_cumulative = true;
        new_concept.when_constructed = seconds_elapsed;
        new_concept.object_type = (String)new_types.elementAt(0);
        new_concept.entity_concept = conceptFromName(new_concept.object_type);
        new_concept.arity = new_types.size();
        new_concept.step_number = steps_taken;
        new_concept.construction = step;
        new_concept.is_user_given = false;
        new_concept.position_in_theory = concepts.size() + 1;
        new_concept.user_functions = user_functions;
        new_concept.domain = (String)new_types.elementAt(0);
        new_concept.types = new_types;
        new_concept.id = new_id;
        new_concept.datatable = new_table;
        if (step.dont_develop)
            new_concept.dont_develop = true;

        for (int i=0; i<new_entities.size(); i++)
        {
            Entity new_entity = (Entity)new_entities.elementAt(i);
            new_concept.updateDatatable(concepts, new_entity);
        }

        // 8.2 Finding the generalisations of the new concept //

        addToTimer("8.2 Finding the generalisations of a new concept");
        new_concept.generalisations = new Vector();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept generalisation = (Concept)concepts.elementAt(i);
            if (generalisation.isGeneralisationOf(new_concept)==0)
                new_concept.generalisations.addElement(generalisation);
        }

        // 8.3 Add the categorisation the concept produces to the list //

        addToTimer("8.4 Adding the categorisation of a new concept");
        addCategorisation(new_concept);

        // 8.4 Measure the interestingness of the new concept //

        addToTimer("8.4 Measuring the interestingness of a new concept");
        measure_concept.measureConcept(new_concept, concepts, true);

        // 8.5 Update the interestingness of the parents //

        if (front_end.measure_concepts_check.getState())
        {
            // 8.6 Update the number of steps which the old concepts have been involved in (which
            // resulted in a new concept.

            addToTimer("8.6 Updating the number of concept-producing steps of the parents of a new concept");
            for (int i=0; i<parent_concepts.size(); i++)
            {
                Concept parent = (Concept)parent_concepts.elementAt(i);
                parent.number_of_children++;
            }

            // 8.7 Recalculate productivities in light of new concept //

            addToTimer("8.7 Measuring productivity of parent concepts");
            reCalculateProductivities(parent_concepts);

            // 8.8 Update the interestingness of the parents //

            addToTimer("8.8 Update the interestingness of the parents of a new concept");
            for (int i=0; i<parent_concepts.size(); i++)
            {
                Concept parent = (Concept)parent_concepts.elementAt(i);
                measure_concept.measureConcept(parent, concepts, false);
            }
        }

        /**************************/
        // STAGE NINE             //
        // MORE CONJECTURES       //
        /**************************/

        if (!substitute_concept)
        {
            // 9.1 Make subsumption conjectures about the new concept

            if (make_subsumption_implications && !new_concept.is_entity_instantiations)
            {
                addToTimer("9.1 Making subsumption conjectures for a new concept");
                Vector new_entities_from_subsumption = makeSubsumptionConjectures(new_concept);

                // 9.2 Add new entities to theory (from subsumption conjectures)

                addToTimer("9.2 Adding new entities from subsumption conjecture making");
                for (int i=0; i<new_entities_from_subsumption.size(); i++)
                {
                    Entity new_entity = (Entity)new_entities_from_subsumption.elementAt(i);
                    addNewEntityToTheory(new_entity, new_concept.domain, "9.2");
                    new_concept.updateDatatable(concepts, new_entity);
                }
            }

            // 9.3 Make an applicability conjecture for the concept if possible

            addToTimer("9.3 Making an applicability conjecture for the concept (if possible)");
            if (use_applicability_conj && !new_concept.is_entity_instantiations)
                makeApplicabilityConjectures(new_concept);

            if (make_near_equivalences)
            {
                // 9.4 Finding near-equivalences //

                addToTimer("9.4 Finding near-equivalences of a new concept");
                new_concept.getNearEquivalences(this, concepts, near_equiv_percent);
                if (make_near_equivalences)
                {
                    for (int i=0; i<new_concept.near_equivalences.size(); i++)
                    {
                        NearEquivalence near_equiv = (NearEquivalence)new_concept.near_equivalences.elementAt(i);
                        near_equiv.checkSegregation(agenda);
                        near_equivalences.addElement(near_equiv);
                        near_equiv.id = Integer.toString(numberOfConjectures());
                        conjectures.addElement(new_concept.near_equivalences.elementAt(i));
                    }
                }
            }


            if (make_near_implications)
            {
                // 9.5 Finding near-implications - alisonp//

                addToTimer("9.5 Finding near-implications of a new concept");
                new_concept.getSubsumptionNearImplications(concepts, near_imp_percent);


                if (make_near_implications)
                {
                    for (int i=0; i<new_concept.near_implications.size(); i++)
                    {
                        NearImplication near_imp = (NearImplication)new_concept.near_implications.elementAt(i);
                        near_imp.checkSegregation(agenda);
                        near_implications.addElement(near_imp);
                        near_imp.id = Integer.toString(numberOfConjectures());
                        conjectures.addElement(new_concept.near_implications.elementAt(i));
                    }
                }
            }


            // 9.6 Perform Lakatos-style concept barring //

            /**
             addToTimer("9.5 Performing Lakatos-style concept barring");
             if (use_concept_barring)
             lakatos.performConceptBarring(new_concept);

             // 9.6 Perform strategic withdrawal (alisonp added to test method)

             addToTimer("9.6 Performing Strategic Withdrawal");
             if(use_strategic_withdrawal)
             {
             System.out.println("starting Strategic Withdrawal");
             for(int i=0; i<conjectures.size(); i++)
             {
             Conjecture conj = (Conjecture)conjectures.elementAt(i);
             if(conj instanceof NearEquivalence)
             {

             NearEquivalence conj_to_modify = (NearEquivalence)conjectures.elementAt(i);
             System.out.println("starting Strategic Withdrawal on ");
             System.out.println(conj_to_modify.id + ":" + conj_to_modify.writeConjecture("ascii") + " (this is a NearEquivalence)");
             Vector modifications =  lakatos.strategicWithdrawal(conj_to_modify,this);
             //System.out.println("we modify it with the following: ");
             if(modifications.isEmpty())
             System.out.println("no modifications to be had, I'm afraid");
             System.out.println("MODIFICATIONS:");
             for(int j=0; j< modifications.size(); j++)
             {
             Conjecture conj_to_add  = (Conjecture)modifications.elementAt(j);
             System.out.println(conj_to_modify.id + ":" + conj_to_add.writeConjecture("ascii") + " (is a modification) ");
             conjectures.addElement(conj_to_add);//should check for duplicates
             }
             System.out.println("finished Strategic Withdrawal on this conjecture");
             System.out.println("  ****************************\n");
             }
             }
             System.out.println("finished Strategic Withdrawal");
             System.out.println("  ****************************\n");
             }

             // 9.7 Perform piecemeal exclusion (alisonp added to test method)

             addToTimer("9.7 Performing Piecemeal Exclusion");
             if(use_piecemeal_exclusion)
             {
             System.out.println("starting Piecemeal Exclusion");
             for(int i=0; i<conjectures.size(); i++)
             {
             Conjecture conj = (Conjecture)conjectures.elementAt(i);
             if(conj instanceof NearEquivalence)
             {

             NearEquivalence conj_to_modify = (NearEquivalence)conjectures.elementAt(i);
             System.out.println("starting Piecemeal Exclusion on ");
             System.out.println(conj_to_modify.id + ":" + conj_to_modify.writeConjecture("ascii") + " (this is a NearEquivalence)");
             Vector modifications =  lakatos.piecemealExclusion(conj_to_modify);
             //System.out.println("we modify it with the following: ");
             if(modifications.isEmpty())
             System.out.println("no modifications to be had, I'm afraid");
             System.out.println("MODIFICATIONS:");
             for(int j=0; j< modifications.size(); j++)
             {
             Conjecture conj_to_add  = (Conjecture)modifications.elementAt(j);
             System.out.println(conj_to_modify.id + ":" + conj_to_add.writeConjecture("ascii") + " (is a modification) ");
             conjectures.addElement(conj_to_add);
             }
             System.out.println("finished Strategic Withdrawal on this conjecture");
             System.out.println("  ****************************\n");
             }
             }
             System.out.println("finished Strategic Withdrawal");
             System.out.println("  ****************************\n");
             //lakatos.piecemealExclusion();
             } */
        }


        /**************************/
        // STAGE TEN              //
        // ADD CONCEPT TO THEORY  //
        /**************************/

        // 10.1 React to new concept before adding it to the theory //

        addToTimer("10.1 Reacting to a concept before it is added to the theory");;
        react.reactTo(new_concept, "new_concept_before_adding_to_agenda");

        // 10.2 Add concept to theory //

        addToTimer("10.2 Adding a new concept to theory");
        step.result_of_step_explanation = "new concept formed";
        step.result_of_step = new_concept;
        concepts.addElement(new_concept);

        if (new_concept.arity==1)
            specialisation_concepts.addElement(new_concept);

        // 10.3 Add Agenda Steps for the new concept and re-order the agenda //

        if (substitute_concept)
            new_concept.dont_develop = true;

        if (!substitute_concept)
        {
            addToTimer("10.3 Adding a new concept to the agenda");
            agenda.addConcept(new_concept,concepts,this);
            agenda.orderConcepts();

            // 10.4 Check whether this concept achieves a categorisation required by the user //

            addToTimer("10.4 Checking whether a required concept has been learned");
            if (check_for_gold_standard && hasAchievedCategorisation(new_concept.categorisation))
            {
                learned_concepts.addElement(new_concept);
                new_concept.has_required_categorisation = true;
            }

            // 10.5 Get the first tuples for the new datatable //

            addToTimer("10.5 Getting the first tuples for a new datatable");
            first_tuples = new_table.firstTuples();

            // 10.6 Add the new datatable to the hashtable //

            addToTimer("10.6 Adding a new concept's datatable to hashtable");
            Vector same_datatable_concept_vector = (Vector)datatable_hashtable.get(first_tuples);
            if (same_datatable_concept_vector==null)
            {
                same_datatable_concept_vector = new Vector();
                datatable_hashtable.put(first_tuples, same_datatable_concept_vector);
            }
            same_datatable_concept_vector.addElement(new_concept);
        }

        // 10.7 Update the front end with new concept //

        addToTimer("10.7 Updating front end after new concept formed");
        updateFrontEnd(new_concept);

        // 10.8 Substitute a definition //

        if (!substitute_concept)
        {
            addToTimer("10.8 Substituting a definition");
            //System.out.println("sun night: 6");
            agenda.recordForcedName(step, new_concept);
        }

        // 10.9 Reacting to the addition of a new concept //

        addToTimer("10.9 Reacting to a new concept");
        react.reactTo(new_concept, "new_concept");

        // POSSIBLE END POINT //
        // Returning with a new concept //

        return step;
    }

    public boolean hasAchievedCategorisation(Categorisation new_cat)
    {
        if (required_flat_categorisations.contains(new_cat.toString()))
            return true;
        for (int i=0; i<required_categorisations.size(); i++)
        {
            Categorisation req_cat = (Categorisation)required_categorisations.elementAt(i);
            if (req_cat.compliesWith(new_cat))
                return true;
        }
        return false;
    }

    public void makeApplicabilityConjectures(Concept new_concept)
    {
        addToTimer("9.3 Making applicability conjectures");
        Vector positive_entities = new Vector();
        Vector splits = new Vector();
        for (int i=0; i<new_concept.datatable.size() &&
                positive_entities.size()<= max_applicability_conj_number; i++)
        {
            Row row = (Row)new_concept.datatable.elementAt(i);
            if (!row.tuples.isEmpty())
                positive_entities.addElement(row.entity);
        }
        if (positive_entities.size() <= max_applicability_conj_number)
        {
            for (int i=0; i<positive_entities.size(); i++)
            {
                agenda.addForcedStep("app_split" + steps_taken + "_" +
                        Integer.toString(i) + " = " +
                        new_concept.entity_concept.id + " split [[0], [" +
                        (String)positive_entities.elementAt(i)+"]] : dont_develop", false);

                splits.addElement("app_split" + steps_taken  + "_" + Integer.toString(i));
            }

            if (splits.size() >= 2)
            {
                agenda.addForcedStep("app_disj" + steps_taken + "_1 = " + "app_split" +
                        steps_taken + "_0 app_split" + steps_taken +
                        "_1 disjunct [] : dont_develop", false);
                for (int i=2; i<splits.size(); i++)
                {
                    agenda.addForcedStep("app_disj" + steps_taken + "_" + Integer.toString(i) +
                            " = app_disj" + steps_taken + "_"+Integer.toString(i-1) + " " +
                            (String)splits.elementAt(i) + " disjunct [] : dont_develop", false);
                }
            }
        }
    }

    public Vector makeSubsumptionConjectures(Concept new_concept)
    {
        Vector output = new Vector();

        // Find the subsuming concepts

        addToTimer("9.1.1 Finding subsuming concepts");
        Vector possible_implications =
                new_concept.getSubsumptionImplications(concepts);

        // Attempt to prove the subsuming implications

        addToTimer("9.1.2 Attempting to prove subsuming implications");

        for (int i=0; i<possible_implications.size(); i++)
        {
            Implication implication = (Implication)possible_implications.elementAt(i);
            if (!explanation_handler.conjectureSeenAlready(implication))
            {
                explanation_handler.addConjecture(implication);
                explanation_handler.explainConjecture(implication, this, "9.1.2");
                for (int j=0; j<implication.counterexamples.size(); j++)
                    output.addElement(implication.counterexamples.elementAt(j));
                new_concept.implications.addElement(implication);
                if (keep_implications)
                {
                    implication.id = Integer.toString(conjectures.size());
                    measure_conjecture.measureConjecture(implication, implications);
                    implication.checkSegregation(agenda);
                    conjectures.addElement(implication);
                    implications.addElement(implication);
                    updateFrontEnd(implication);
                }
                if (make_subsumption_implicates)
                {
                    // 9.1.3 Extract implicates from the subsuming implicates

                    addToTimer("9.1.3 Extracting implicates from subsuming implicates");
                    Vector new_implicates = implication.implicates(specification_handler);
                    for (int j=0; j<new_implicates.size(); j++)
                    {
                        Implicate imp = (Implicate)new_implicates.elementAt(j);
                        implication.child_conjectures.addElement(imp);
                        imp.parent_conjectures.addElement(implication);
                    }
                    if (keep_implicates)
                    {
                        Vector implicate_counterexamples = addImplicates(new_implicates, implication, "9.1.3");
                        for (int j=0; j<implicate_counterexamples.size(); j++)
                            output.addElement(implicate_counterexamples.elementAt(j));
                    }
                }
            }
        }
        return output;
    }


    /** Adds a new entity to the theory, recalculating concept examples, categorisations, etc.
     */

    public void addNewEntityToTheory(Entity entity, String add_domain, String timer_num)
    {
        // X.1 Check whether the entity is already in the theory
        // System.out.println("started addNewEntityToTheory("+entity.toString()+", "+add_domain+", "+timer_num+")");
        addToTimer(timer_num + ".1 Checking whether the entity is already in the theory");
        //System.out.println("entity.concept_data is " + entity.concept_data);
        //System.out.println("entity.concept_data.isEmpty() is "+ entity.concept_data.isEmpty());
        if (entity.concept_data!=null && !entity.concept_data.isEmpty())
        {
            for (int i=0; i<entities.size(); i++)
            {
                Entity old_entity = (Entity)entities.elementAt(i);
                //System.out.println("old_entity is "+ old_entity.toString());
                boolean test = old_entity.concept_data.toString().equals(entity.concept_data.toString());
                //System.out.println("old_entity.concept_data.toString().equals(entity.concept_data.toString()) is " + test);
                if (old_entity.concept_data.toString().equals(entity.concept_data.toString()))
                {
                    int pos = entity.conjecture.counterexamples.indexOf(entity);
                    entity.conjecture.counterexamples.setElementAt(old_entity, pos);
                    //System.out.println("breaking here");
                    return;
                }
            }
        }
        //System.out.println("entity.name is " + entity.name);
        if (entity.name.equals(""))
            entity.name = "new_" + add_domain + Integer.toString(entities.size());

        entity.id = entity.name;
        // System.out.println("entities_added is " +entities_added);
        //System.out.println("entities_added.contains(entity.name) is " + entities_added.contains(entity.name));
        if (entities_added.contains(entity.name))
            return;
        entities.addElement(entity);
        entity_names.addElement(entity);
        entities_added.addElement(entity.name);

        // X.2 Update the datatables, categorisations and same datatable
        // hashtable entries of the concepts in the theory

        addToTimer(timer_num + ".2 Updating datatables, categorisations and same datatable hashtable with a new entity (from counterexample)");
        categorisations.removeAllElements();
        Vector changed_first_tuples = new Vector();
        datatable_hashtable = new Hashtable();
        for (int conc_num=0;conc_num<concepts.size();conc_num++)
        {
            Concept c = (Concept)concepts.elementAt(conc_num);
            if (c.domain.equals(add_domain))
            {
                c.updateDatatable(concepts, entity);
                String first_tuples = c.datatable.firstTuples();
                addCategorisation(c);
                Vector same_first_tuple_concepts =
                        (Vector)datatable_hashtable.get(first_tuples);
                if (same_first_tuple_concepts!=null)
                    same_first_tuple_concepts.addElement(c);
                else
                {
                    same_first_tuple_concepts = new Vector();
                    same_first_tuple_concepts.addElement(c);
                    datatable_hashtable.put(first_tuples, same_first_tuple_concepts);
                }
            }
        }

        // X.3 Re-measure the concepts after the introduction of a new entity //

        addToTimer(timer_num + ".3 Re-measuring the concepts after the introduction of a new entity");

        if (front_end.measure_concepts_check.getState())
        {
            for (int conc_num=0;conc_num<concepts.size();conc_num++)
            {
                Concept c = (Concept)concepts.elementAt(conc_num);
                measure_concept.measureConcept(c, concepts, false);
            }

            // X.4 Re-order the agenda in light of new measures //

            addToTimer(timer_num + ".5 Re-ordering the agenda after the introduction of a new entity");
            agenda.orderConcepts();
        }

        // X.5 Re-evaluate the near-equivalences //

        addToTimer(timer_num + ".5 Re-evaluating the near-equivalences of concepts");
        for (int i=0;i<concepts.size();i++)
        {
            Concept c = (Concept)concepts.elementAt(i);
            c.reEvaluateNearEquivalences(this, near_equiv_percent);
        }

        // X.6 Re-evaluate the near-implications - alisonp //

        addToTimer(timer_num + ".6 Re-evaluating the near-implications of concepts");
        for (int i=0;i<concepts.size();i++)
        {
            Concept c = (Concept)concepts.elementAt(i);
            c.reEvaluateNearImplications(this, near_imp_percent);
        }


        // X.7 Add the new entity to the front_end //

        addToTimer(timer_num + ".7 Adding a new entity to the front_end");
        front_end.addEntity(entity);

        // X.8 Update the ground instances for the axioms //

        addToTimer(timer_num + ".8 Updating the ground instances for the prover");
        explanation_handler.updateGroundInstances(this);

    }

    /** This will start a thread running until the given number of
     * things (either "steps", "categorisations", "concepts" or "conjectures") have
     * been found.
     */

    public void formTheoryUntil(int number_required, String thing_required)
    {
        if (!thing_required.equals("learned concepts"))
            check_for_gold_standard = false;
        runner = new TheoryFormationThread(number_required, thing_required, this);
        runner.start();
    }

    /** This completely stops the current theory formation thread.
     */

    public void stopIt()
    {
        stop_now = true;
        runner.stop();
        addToTimer("Killed");
    }

    /** This returns the number of conjectures in the theory.
     */

    public int numberOfConjectures()
    {
        return conjectures.size();
    }

    /** This updates the front end and includes adding a new concept to the
     * concept list.
     */

    public void updateFrontEnd(Concept c)
    {
        if (front_end.update_front_end_check.getState())
        {
            front_end.addObjectToList(c, c.id,
                    front_end.concept_list,
                    front_end.concept_pruning_list.getSelectedItems(),
                    front_end.concept_sorting_list.getSelectedItem());
            front_end.force_primary_concept_list.addItem(c.id);
            front_end.force_secondary_concept_list.addItem(c.id);
            updateFrontEnd();
        }
    }

    /** This updates the front end and includes adding a new conjecture to the
     * concept list.
     */

    public void updateFrontEnd(Conjecture c)
    {
        if (front_end.update_front_end_check.getState())
        {
            front_end.addObjectToList(c, c.id,
                    front_end.conjecture_list,
                    front_end.conjecture_pruning_list.getSelectedItems(),
                    front_end.conjecture_sorting_list.getSelectedItem());
            updateFrontEnd();
        }
    }

    /** This updates the front end and includes removing an entity to
     * the entity list. 1) need to get working with entity. 2) need to
     * remove rather than add the entity
     */

    public void updateFrontEnd(Entity e)
    {
        if (front_end.update_front_end_check.getState())
        {
            front_end.addEntity(e);
            front_end.removeEntity(e);

            //front_end.force_primary_concept_list.addItem(e.id);
            //front_end.force_secondary_concept_list.addItem(e.id);
            updateFrontEnd();
        }
    }



    /** This updates the number of concepts, etc. text  boxes.
     */

    public void updateFrontEnd()
    {
        if (!front_end.update_front_end_check.getState())
            return;
        if (use_front_end)
        {
            front_end.concept_list_number.setText("number: " +
                    Integer.toString(front_end.concept_list.getItemCount()));
            front_end.conjecture_list_number.setText("number: " +
                    Integer.toString(front_end.conjecture_list.getItemCount()));
            if (run_start_time>0)
                seconds_elapsed = (new Long(((System.currentTimeMillis() - run_start_time)/1000))).intValue();
            else
                seconds_elapsed = 0;
            Vector statistics = new Vector();
            statistics.addElement(Integer.toString(steps_taken));
            statistics.addElement(Integer.toString(concepts.size()));
            statistics.addElement(Integer.toString(conjectures.size()));
            statistics.addElement(Integer.toString(equivalences.size()));
            statistics.addElement(Integer.toString(non_existences.size()));
            statistics.addElement(Integer.toString(prime_implicates.size()));
            statistics.addElement(Integer.toString(categorisations.size()));
            statistics.addElement(Integer.toString(specialisation_concepts.size()));
            statistics.addElement(Integer.toString(entities_added.size()));
            statistics.addElement(Integer.toString(agenda.itemsInAgenda()));
            statistics.addElement(Long.toString(seconds_elapsed));
            front_end.statistics = statistics;
            front_end.update();
        }
    }

    /** This adds in blank tuples if an entity is not present.
     * the table is assumed to already be in the correct order.
     */

    public void complimentTable(Datatable table, Vector types)
    {
        String entity_type = (String)types.elementAt(0);
        Datatable entity_datatable = conceptFromName(entity_type).datatable;
        String other_entity = "";
        for (int i=0;i<entity_datatable.size();i++)
        {
            String entity = ((Row)entity_datatable.elementAt(i)).entity;
            if (i<table.size())
                other_entity = ((Row)table.elementAt(i)).entity;
            if (i>= table.size() || !entity.equals(other_entity))
            {
                Row new_row =  new Row();
                new_row.entity = entity;
                new_row.tuples = new Tuples();
                if (i<table.size()) table.insertElementAt(new_row,i);
                else table.addElement(new_row);
            }
        }
    }

    /** This generates a definition for a supposed concept with a particular construction.
     */

    public String generateDefinition(Concept concept, Step step, String language)
    {
        Concept dummy = new Concept();
        dummy.types = concept.types;
        dummy.specifications =
                (step.productionRule()).newSpecifications(step.conceptList(), step.parameters(), this, new Vector());
        return dummy.writeDefinition(language);
    }

    /** This adds the categorisation for the concept to the set of categorisations.
     * Note that it adds a categorisation onto the concept itself. It also calculates
     * the novelty of the categorisation, and the invariance and discrimination with
     * respect to the gold standard categorisation supplied by the user.
     */

    public void addCategorisation(Concept concept)
    {
        Categorisation categorisation = new Categorisation();
        Vector flat_tuples = new Vector();
        for (int i=0;i<concept.datatable.size();i++)
        {
            Row row = (Row)concept.datatable.elementAt(i);
            String flat_tuple = row.tuples.toString();
            int pos = flat_tuples.indexOf(flat_tuple);
            if (pos==-1)
            {
                flat_tuples.addElement(flat_tuple);
                Vector new_cat = new Vector();
                new_cat.addElement(row.entity);
                categorisation.addElement(new_cat);
            }
            else
            {
                Vector old_cat = (Vector)categorisation.elementAt(pos);
                old_cat.addElement(row.entity);
            }
        }
        String flat_cat = categorisation.toString();
        Categorisation old_categorisation = (Categorisation)flat_categorisations.get(flat_cat);
        if (old_categorisation==null)
        {
            categorisations.addElement(categorisation);
            flat_categorisations.put(flat_cat, categorisation);
            categorisation.appearances = 1;
        }
        else
        {
            categorisation = old_categorisation;
            categorisation.appearances++;
        }
        concept.categorisation = categorisation;
        double one = (new Double(1)).doubleValue();
        double app = (new Double(categorisation.appearances)).doubleValue();
        categorisation.novelty = one/app;
    }

    private double valueFromDoubleField(TextField tf)
    {
        if (tf.getText().equals(""))
            return 0;
        try
        {
            double output = (new Double(tf.getText())).doubleValue();
            tf.setBackground(Color.white);
            return output;
        }
        catch (Exception e)
        {
            tf.setBackground(Color.red);
        }
        return 0;
    }

    private int valueFromIntField(TextField tf)
    {
        if (tf.getText().equals(""))
            return 0;
        try
        {
            int output = (new Double(tf.getText())).intValue();
            tf.setBackground(Color.white);
            return output;
        }
        catch (Exception e)
        {
            tf.setBackground(Color.red);
        }
        return 0;
    }


    private String valueFromStringField(TextField tf)
    {
        if (tf.getText().equals(""))
            return "";
        try
        {
            String output = (new String(tf.getText()));//.StringValue()
            tf.setBackground(Color.white);
            return output;
        }
        catch (Exception e)
        {
            tf.setBackground(Color.red);
        }
        return "";
    }


    /** This gets the settings from the front end. This is used either before a theory has been
     * formed, or after it has been running, but the user makes some changes and continues
     * the session.
     */

    public void getFrontEndSettings()
    {
        // Get the weights for the weighted sum //

        measure_conjecture.measure_conjectures = front_end.measure_conjectures_check.getState();
        measure_concept.measure_concepts = front_end.measure_concepts_check.getState();
        measure_concept.applicability_weight = valueFromDoubleField(front_end.concept_applicability_weight_text);
        measure_concept.invariance_weight = valueFromDoubleField(front_end.concept_invariance_weight_text);
        measure_concept.discrimination_weight = valueFromDoubleField(front_end.concept_discrimination_weight_text);
        measure_concept.equiv_conj_score_weight = valueFromDoubleField(front_end.concept_equiv_conj_score_weight_text);
        measure_concept.ne_conj_score_weight = valueFromDoubleField(front_end.concept_ne_conj_score_weight_text);
        measure_concept.imp_conj_score_weight = valueFromDoubleField(front_end.concept_imp_conj_score_weight_text);
        measure_concept.pi_conj_score_weight = valueFromDoubleField(front_end.concept_pi_conj_score_weight_text);
        measure_concept.equiv_conj_num_weight = valueFromDoubleField(front_end.concept_equiv_conj_num_weight_text);
        measure_concept.ne_conj_num_weight = valueFromDoubleField(front_end.concept_ne_conj_num_weight_text);
        measure_concept.imp_conj_num_weight = valueFromDoubleField(front_end.concept_imp_conj_num_weight_text);
        measure_concept.pi_conj_num_weight = valueFromDoubleField(front_end.concept_pi_conj_num_weight_text);
        measure_concept.coverage_weight = valueFromDoubleField(front_end.concept_coverage_weight_text);
        measure_concept.comprehensibility_weight = valueFromDoubleField(front_end.concept_comprehensibility_weight_text);
        measure_concept.cross_domain_weight = valueFromDoubleField(front_end.concept_cross_domain_weight_text);
        measure_concept.highlight_weight = valueFromDoubleField(front_end.concept_highlight_weight_text);
        measure_concept.novelty_weight = valueFromDoubleField(front_end.concept_novelty_weight_text);
        measure_concept.parent_weight = valueFromDoubleField(front_end.concept_parent_weight_text);
        measure_concept.children_score_weight = valueFromDoubleField(front_end.concept_children_weight_text);
        measure_concept.parsimony_weight = valueFromDoubleField(front_end.concept_parsimony_weight_text);
        measure_concept.predictive_power_weight = valueFromDoubleField(front_end.concept_predictive_power_weight_text);
        measure_concept.productivity_weight = valueFromDoubleField(front_end.concept_productivity_weight_text);
        measure_concept.development_steps_num_weight = valueFromDoubleField(front_end.concept_development_steps_num_weight_text);
        measure_concept.default_productivity = valueFromDoubleField(front_end.concept_default_productivity_text);
        measure_concept.variety_weight = valueFromDoubleField(front_end.concept_variety_weight_text);
        measure_concept.positive_applicability_weight = valueFromDoubleField(front_end.concept_positive_applicability_weight_text);
        measure_concept.negative_applicability_weight = valueFromDoubleField(front_end.concept_negative_applicability_weight_text);

        measure_conjecture.surprisingness_weight = valueFromDoubleField(front_end.conjecture_surprisingness_weight_text);
        measure_conjecture.applicability_weight = valueFromDoubleField(front_end.conjecture_applicability_weight_text);
        measure_conjecture.comprehensibility_weight = valueFromDoubleField(front_end.conjecture_comprehensibility_weight_text);
        measure_conjecture.plausibility_weight = valueFromDoubleField(front_end.conjecture_plausibility_weight_text);

        // Set limits //

        setComplexityLimit(valueFromIntField(front_end.complexity_text));
        setAgendaSizeLimit(valueFromIntField(front_end.agenda_limit_text));
        compose.tuple_product_limit = (valueFromIntField(front_end.tuple_product_limit_text));
        compose.time_limit = (valueFromIntField(front_end.compose_time_limit_text));
        arithmeticb.operators_allowed = front_end.arithmeticb_operators_text.getText().trim();//friday

        // Set search strategy //

        measure_concept.use_weighted_sum = front_end.weighted_sum_check.getState();
        measure_concept.keep_best = front_end.keep_best_check.getState();
        measure_concept.keep_worst = front_end.keep_worst_check.getState();

        if (measure_concept.use_weighted_sum ||
                measure_concept.keep_best ||
                measure_concept.keep_worst)
            agenda.best_first = true;

        measure_concept.normalise_values = front_end.normalise_concept_measures_check.getState();
        measure_concept.interestingness_zero_min = valueFromDoubleField(front_end.interestingness_zero_min_text);
        agenda.best_first_delay_steps = valueFromIntField(front_end.best_first_delay_text);
        if (agenda.best_first_delay_steps > 0)
        {
            agenda.best_first_delayed = true;
            agenda.best_first = false;
        }

        setDepthFirst(front_end.depth_first_check.getState());
        setRandom(front_end.random_check.getState());
        setSubobjectOverlap(front_end.subobject_overlap_check.getState());
        agenda.expand_agenda = front_end.expand_agenda_check.getState();
        agenda.use_forward_lookahead = front_end.use_forward_lookahead_check.getState();
        gc_when = valueFromIntField(front_end.gc_text);

        // Set conjecture making strategy //

        make_equivalences_from_equality = front_end.make_equivalences_from_equality_check.getState();
        make_equivalences_from_combination = front_end.make_equivalences_from_combination_check.getState();
        make_non_exists_from_empty = front_end.make_non_exists_from_empty_check.getState();
        keep_steps = front_end.keep_steps_check.getState();
        keep_equivalences = front_end.keep_equivalences_check.getState();
        keep_non_existences = front_end.keep_non_exists_check.getState();
        keep_implications = front_end.keep_implications_check.getState();
        keep_implicates = front_end.keep_implicates_check.getState();
        keep_prime_implicates = front_end.keep_prime_implicates_check.getState();
        extract_implications_from_equivalences = front_end.extract_implications_from_equivalences_check.getState();
        extract_implicates_from_non_exists = front_end.extract_implicates_from_non_exists_check.getState();
        make_subsumption_implications = front_end.make_implications_from_subsumptions_check.getState();
        make_subsumption_implicates = front_end.make_implicates_from_subsumes_check.getState();
        extract_implicates_from_equivalences = front_end.extract_implicates_from_equivalences_check.getState();
        extract_prime_implicates_from_implicates = front_end.extract_prime_implicates_from_implicates_check.getState();

        teacher_requests_conjectures = front_end.teacher_requests_conjecture_check.getState();
        teacher_requests_nonexists = front_end.teacher_requests_nonexists_check.getState();//11/03/04
        teacher_requests_implication = front_end.teacher_requests_implication_check.getState();//11/03/04
        teacher_requests_nearimplication = front_end.teacher_requests_nearimplication_check.getState();//11/03/04
        teacher_requests_equivalence = front_end.teacher_requests_equivalence_check.getState();//11/03/04
        teacher_requests_nearequivalence = front_end.teacher_requests_nearequivalence_check.getState();//11/03/04

        make_near_equivalences = front_end.make_near_equivalences_check.getState();
        near_equiv_percent = valueFromIntField(front_end.near_equivalence_percent_text);
        make_near_implications = front_end.make_near_implications_check.getState(); //alisonp
        near_imp_percent = valueFromIntField(front_end.near_implication_percent_text);//alisonp

        max_counterexample_bar_number = valueFromIntField(front_end.counterexample_barring_num_text);
        max_concept_bar_number = valueFromIntField(front_end.concept_barring_num_text);
        use_counterexample_barring = front_end.use_counterexample_barring_check.getState();
        use_concept_barring = front_end.use_concept_barring_check.getState();
        use_applicability_conj = front_end.use_applicability_conj_check.getState();
        max_applicability_conj_number = valueFromIntField(front_end.applicability_conj_num_text);
        allow_substitutions = front_end.substitute_definitions_check.getState();
        explanation_handler.use_ground_instances = front_end.use_ground_instances_in_proving_check.getState();
        explanation_handler.use_entity_letter = front_end.use_entity_letter_in_proving_check.getState();
        require_proof_in_subsumption = front_end.require_proof_in_subsumption_check.getState();


        //alisonp - set lakatos methods used by hr //
        use_surrender = front_end.use_surrender_check.getState();
        use_strategic_withdrawal = front_end.use_strategic_withdrawal_check.getState();
        use_piecemeal_exclusion = front_end.use_piecemeal_exclusion_check.getState();
        use_monster_barring = front_end.use_monster_barring_check.getState();
        use_monster_adjusting = front_end.use_monster_adjusting_check.getState();
        use_lemma_incorporation = front_end.use_lemma_incorporation_check.getState();
        use_proofs_and_refutations = front_end.use_proofs_and_refutations_check.getState();
        use_communal_piecemeal_exclusion = front_end.use_communal_piecemeal_exclusion_check.getState();

        //alisonp - set discussion search //
        // group_agenda.best_first_responses = front_end.group_agenda_best_first_responses_check.getState();
//     group_agenda.best_first_agenda = front_end.group_agenda_best_first_agenda_check.getState();
//     group_agenda.combine_responses = front_end.group_agenda_combine_responses_check.getState();
//     group_agenda.depth_first = front_end.group_agenda_depth_first_check.getState();
//     group_agenda.breadth_first = front_end.group_agenda_breadth_first_check.getState();

        // - set the lakatos variables //

        lakatos.test_max_num_independent_work_stages = front_end.max_num_independent_work_stages_check.getState();
        lakatos.max_num_independent_work_stages = valueFromIntField(front_end.max_num_independent_work_stages_value_text);

        lakatos.use_threshold_to_add_conj_to_theory = front_end.threshold_to_add_conj_to_theory_check.getState();
        lakatos.use_threshold_to_add_concept_to_theory = front_end.threshold_to_add_concept_to_theory_check.getState();

        lakatos.threshold_to_add_conj_to_theory = valueFromDoubleField(front_end.threshold_to_add_conj_to_theory_value_text);
        lakatos.threshold_to_add_concept_to_theory = valueFromDoubleField(front_end.threshold_to_add_concept_to_theory_value_text);

        lakatos.test_num_independent_steps  = front_end.num_independent_steps_check.getState();
        lakatos.num_independent_steps   = valueFromIntField(front_end.num_independent_steps_value_text);

        lakatos.test_number_modifications_surrender = front_end.number_modifications_check.getState();
        lakatos.test_interestingness_surrender = front_end.interestingness_threshold_check.getState();
        lakatos.compare_average_interestingness_surrender = front_end.compare_average_interestingness_check.getState();
        lakatos.test_plausibility_surrender = front_end.plausibility_threshold_check.getState();
        lakatos.test_domain_application_surrender = front_end.domain_application_threshold_check.getState();

        lakatos.number_modifications_surrender  = valueFromIntField(front_end.modifications_value_text);
        lakatos.interestingness_th_surrender = valueFromDoubleField(front_end.interestingness_threshold_value_text);
        lakatos.plausibility_th_surrender = valueFromDoubleField(front_end.plausibility_threshold_value_text);//alisonp
        lakatos.domain_application_th_surrender = valueFromDoubleField(front_end.domain_app_threshold_value_text);


        lakatos.use_breaks_conj_under_discussion = front_end.use_breaks_conj_under_discussion_check.getState();
        lakatos.accept_strictest = front_end.accept_strictest_check.getState();
        lakatos.use_percentage_conj_broken = front_end.use_percentage_conj_broken_check.getState();
        lakatos.use_culprit_breaker = front_end.use_culprit_breaker_check.getState();
        lakatos.use_culprit_breaker_on_conj = front_end.use_culprit_breaker_on_conj_check.getState();
        lakatos.use_culprit_breaker_on_all = front_end.use_culprit_breaker_on_all_check.getState();
        //lakatos.monster_barring_type = front_end.monster_barring_type_check.getState();

        lakatos.monster_barring_culprit_min  = valueFromIntField(front_end.monster_barring_culprit_min_text);
        lakatos.monster_barring_min = valueFromDoubleField(front_end.monster_barring_min_text);
        lakatos.monster_barring_type = valueFromStringField(front_end.monster_barring_type_text);


        lakatos.use_counterexample_barring = front_end.use_counterexample_barring_check.getState();


        //end alisonp

        // Get the production rules //

        all_production_rules = new Vector();
        production_rules = new Vector();
        unary_rules = new Vector();
        binary_rules = new Vector();

        all_production_rules.addElement(arithmeticb);
        all_production_rules.addElement(exists);
        all_production_rules.addElement(equal);
        all_production_rules.addElement(entity_disjunct);
        all_production_rules.addElement(embed_graph);
        all_production_rules.addElement(embed_algebra);
        all_production_rules.addElement(forall);
        all_production_rules.addElement(match);
        all_production_rules.addElement(size);
        all_production_rules.addElement(compose);
        all_production_rules.addElement(disjunct);
        all_production_rules.addElement(negate);
        all_production_rules.addElement(record);
        all_production_rules.addElement(split);

        if (front_end.exists_check.getState())
        {
            unary_rules.addElement(exists);
            production_rules.addElement(exists);
        }
        if (front_end.arithmeticb_check.getState())//friday..
        {
            binary_rules.addElement(arithmeticb);
            production_rules.addElement(arithmeticb);
        }//..friday
        if (front_end.equal_check.getState())
        {
            unary_rules.addElement(equal);
            production_rules.addElement(equal);
        }
        if (front_end.embed_graph_check.getState())
        {
            unary_rules.addElement(embed_graph);
            production_rules.addElement(embed_graph);
        }
        if (front_end.embed_algebra_check.getState())
        {
            unary_rules.addElement(embed_algebra);
            production_rules.addElement(embed_algebra);
        }
        if (front_end.forall_check.getState())
        {
            binary_rules.addElement(forall);
            production_rules.addElement(forall);
        }
        if (front_end.match_check.getState())
        {
            unary_rules.addElement(match);
            production_rules.addElement(match);
        }
        if (front_end.size_check.getState())
        {
            unary_rules.addElement(size);
            production_rules.addElement(size);
        }
        if (front_end.compose_check.getState())
        {
            binary_rules.addElement(compose);
            production_rules.addElement(compose);
        }
        if (front_end.disjunct_check.getState())
        {
            binary_rules.addElement(disjunct);
            production_rules.addElement(disjunct);
        }
        if (front_end.negate_check.getState())
        {
            binary_rules.addElement(negate);
            production_rules.addElement(negate);
        }
        if (front_end.record_check.getState())
        {
            unary_rules.addElement(record);
            production_rules.addElement(record);
        }
        if (front_end.split_check.getState())
        {
            unary_rules.addElement(split);
            production_rules.addElement(split);
        }

        arithmeticb.arity_limit = valueFromIntField(front_end.arithmeticb_arity_limit_text);//friday
        equal.arity_limit = valueFromIntField(front_end.equal_arity_limit_text);
        entity_disjunct.arity_limit = valueFromIntField(front_end.entity_disjunct_arity_limit_text);
        exists.arity_limit = valueFromIntField(front_end.exists_arity_limit_text);
        embed_graph.arity_limit = valueFromIntField(front_end.embed_graph_arity_limit_text);
        embed_algebra.arity_limit = valueFromIntField(front_end.embed_algebra_arity_limit_text);
        forall.arity_limit = valueFromIntField(front_end.forall_arity_limit_text);
        match.arity_limit = valueFromIntField(front_end.match_arity_limit_text);
        size.arity_limit = valueFromIntField(front_end.size_arity_limit_text);
        compose.arity_limit = valueFromIntField(front_end.compose_arity_limit_text);
        disjunct.arity_limit = valueFromIntField(front_end.disjunct_arity_limit_text);
        negate.arity_limit = valueFromIntField(front_end.negate_arity_limit_text);
        record.arity_limit = valueFromIntField(front_end.record_arity_limit_text);
        split.arity_limit = valueFromIntField(front_end.split_arity_limit_text);

        arithmeticb.tier = valueFromIntField(front_end.arithmeticb_tier_text);//friday
        equal.tier = valueFromIntField(front_end.equal_tier_text);
        exists.tier = valueFromIntField(front_end.exists_tier_text);
        entity_disjunct.tier = valueFromIntField(front_end.entity_disjunct_tier_text);
        embed_graph.tier = valueFromIntField(front_end.embed_graph_tier_text);
        embed_algebra.tier = valueFromIntField(front_end.embed_algebra_tier_text);
        forall.tier = valueFromIntField(front_end.forall_tier_text);
        match.tier = valueFromIntField(front_end.match_tier_text);
        size.tier = valueFromIntField(front_end.size_tier_text);
        compose.tier = valueFromIntField(front_end.compose_tier_text);
        disjunct.tier = valueFromIntField(front_end.disjunct_tier_text);
        negate.tier = valueFromIntField(front_end.negate_tier_text);
        record.tier = valueFromIntField(front_end.record_tier_text);
        split.tier = valueFromIntField(front_end.split_tier_text);

        // Get the split values //

        split.allowed_values.removeAllElements();
        String[] all_split_values = front_end.split_values_list.getSelectedItems();
        for (int i=0; i<all_split_values.length; i++)
        {
            if (all_split_values[i].substring(0,4).equals("all:"))
                split.empirical_allowed_types.addElement(all_split_values[i].substring(5,all_split_values[i].length()-1));
            else
                split.allowed_values.addElement(all_split_values[i]);
        }

        // Get the learning categorisations //

        if (!front_end.coverage_categorisation_text.getText().equals(""))
            measure_concept.coverage_categorisation =
                    new Categorisation(front_end.coverage_categorisation_text.getText());

        if (!front_end.segregation_categorisation_text.getText().equals(""))
            agenda.segregation_categorisation =
                    new Categorisation(front_end.segregation_categorisation_text.getText());

        agenda.use_segregated_search = front_end.segregated_search_check.getState();

        if (!front_end.gold_standard_categorisation_text.getText().equals(""))
        {
            gold_standard_categorisation = new Categorisation(front_end.gold_standard_categorisation_text.getText());
            measure_concept.gold_standard_categorisation = gold_standard_categorisation;

            measure_concept.positives = (Vector)gold_standard_categorisation.elementAt(0);
            measure_concept.negatives = (Vector)gold_standard_categorisation.elementAt(1);

            positives_for_learning = (Vector)gold_standard_categorisation.elementAt(0);
            negatives_for_learning = (Vector)gold_standard_categorisation.elementAt(1);

            for (int i=0; i<measure_concept.positives.size(); i++)
                agenda.positives_for_forward_lookahead.addElement((String)measure_concept.positives.elementAt(i));
            for (int i=0; i<measure_concept.negatives.size(); i++)
                agenda.negatives_for_forward_lookahead.addElement((String)measure_concept.negatives.elementAt(i));
            String flat_cat = measure_concept.gold_standard_categorisation.toString();
            if (!required_flat_categorisations.contains(flat_cat))
            {
                required_categorisations.addElement(measure_concept.gold_standard_categorisation);
                required_flat_categorisations.addElement(flat_cat);
            }
            agenda.object_types_to_learn = front_end.object_types_to_learn_text.getText().trim();
            measure_concept.object_types_to_learn = front_end.object_types_to_learn_text.getText().trim();
        }

        // Make changes to the agenda //

        agenda.makeChanges();
    }

    /** This gets the initial concepts chosen from the front end
     */

    public void initialiseTheory()
    {
        getFrontEndSettings();

        if (!measure_concept.gold_standard_categorisation.isEmpty())
            measure_concept.getInvDiscPairs();
        front_end.concept_list.removeAll();
        front_end.entity_list.removeAll();
        front_end.force_primary_concept_list.removeAll();
        front_end.force_secondary_concept_list.removeAll();
        concepts.removeAllElements();

        // Open the storage files //

        String equiv_store_file = front_end.keep_equivalences_file_text.getText();
        String non_exists_store_file = front_end.keep_non_exists_file_text.getText();
        String implication_store_file = front_end.keep_implications_file_text.getText();
        String implicate_store_file = front_end.keep_implicates_file_text.getText();
        String prime_implicate_store_file = front_end.keep_prime_implicates_file_text.getText();
        String equiv_condition = front_end.keep_equivalences_condition_text.getText();
        String non_exists_condition = front_end.keep_non_exists_condition_text.getText();
        String implication_condition = front_end.keep_implications_condition_text.getText();
        String implicate_condition = front_end.keep_implicates_condition_text.getText();
        String prime_implicate_condition = front_end.keep_prime_implicates_condition_text.getText();
        storage_handler.openStorageStream("instanceof Equivalence", equiv_store_file);
        storage_handler.openStorageStream("instanceof NonExists", non_exists_store_file);
        storage_handler.openStorageStream("instanceof Implication", implication_store_file);
        storage_handler.openStorageStream("instanceof Implicate", implicate_store_file);
        storage_handler.openStorageStream("instanceof Implicate", prime_implicate_store_file);

        if (front_end.store_all_conjectures_check.getState())
        {
            storage_handler.openStorageStream("instanceof Conjecture", front_end.store_all_conjectures_text.getText());
            explanation_handler.store_conjectures = true;
        }

        // Put the entities in first //

        String[] chosen_entities = front_end.initial_entity_list.getSelectedItems();
        datatable_hashtable = new Hashtable();
        entities.removeAllElements();
        Vector entity_strings = new Vector();
        for (int i=0; i<chosen_entities.length; i++)
        {
            String entity_string = chosen_entities[i];
            entity_string =
                    entity_string.substring(entity_string.indexOf(":")+1,entity_string.length());
            Entity entity = new Entity(entity_string);
            entity_strings.addElement(entity_string);
            entities.addElement(entity);
            front_end.entity_list.addItem(entity_string);
            entity_names.addElement(entity_string);
        }

        // Next load the concepts for the domain and update the front end.

        for (int i=0; i<domain_concepts.size(); i++)
        {
            if (front_end.initial_concepts_list.isIndexSelected(i))
            {
                Concept concept = (Concept)domain_concepts.elementAt(i);
                concepts.addElement(concept);
                concept.specifications = specification_handler.addSpecifications(concept.specifications);
                concept.position_in_theory = concepts.size()-1;
                front_end.concept_list.addItem(concept.id);
                front_end.force_primary_concept_list.addItem(concept.id);
                front_end.force_secondary_concept_list.addItem(concept.id);
            }
        }

        // Get the counterexamples //

        String[] chosen_counterexamples = front_end.counterexample_list.getSelectedItems();
        hold_back_checker.counterexamples.clear();
        Vector counterexamples = new Vector();
        for (int i=0; i<chosen_counterexamples.length; i++)
        {
            String counterexample = chosen_counterexamples[i];
            String c_type = counterexample.substring(0,counterexample.indexOf(":"));
            counterexample = counterexample.substring(counterexample.indexOf(":")+1,counterexample.length());
            hold_back_checker.addCounterexample(c_type, counterexample);
            counterexamples.addElement(counterexample);
        }

        // Add the counterexamples to the user_given concepts' additional datatable //
        // While we're there, get the entity concepts //
        // While we're there, find which concept is the integer concept, if any //

        Concept integer_concept = null;
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.id.equals("int001"))
                integer_concept = concept;
            for (int j=0; j<concept.datatable.size(); j++)
            {
                Row row = (Row)concept.datatable.elementAt(j);
                if (!entity_strings.contains(row.entity))
                {
                    concept.datatable.removeElementAt(j);
                    j--;
                }
                if (counterexamples.contains(row.entity) &&
                        !entity_strings.contains(row.entity))
                    concept.additional_datatable.addElement(row);
            }
        }

        /* Measure the concepts now they're all there */

        measure_concept.equiv_conj_scores_hashtable.put(new Double(0), new Vector());
        measure_concept.ne_conj_scores_hashtable.put(new Double(0), new Vector());
        measure_concept.imp_conj_scores_hashtable.put(new Double(0), new Vector());
        measure_concept.pi_conj_scores_hashtable.put(new Double(0), new Vector());

        measure_concept.equiv_conj_nums_hashtable.put(new Double(0), new Vector());
        measure_concept.ne_conj_nums_hashtable.put(new Double(0), new Vector());
        measure_concept.imp_conj_nums_hashtable.put(new Double(0), new Vector());
        measure_concept.pi_conj_nums_hashtable.put(new Double(0), new Vector());

        measure_concept.sorted_equiv_conj_scores = new Vector();
        measure_concept.sorted_ne_conj_scores = new Vector();
        measure_concept.sorted_imp_conj_scores = new Vector();
        measure_concept.sorted_pi_conj_scores = new Vector();

        measure_concept.sorted_equiv_conj_scores.addElement(new Double(0));
        measure_concept.sorted_ne_conj_scores.addElement(new Double(0));
        measure_concept.sorted_imp_conj_scores.addElement(new Double(0));
        measure_concept.sorted_pi_conj_scores.addElement(new Double(0));

        measure_concept.sorted_equiv_conj_nums = new Vector();
        measure_concept.sorted_ne_conj_nums = new Vector();
        measure_concept.sorted_imp_conj_nums = new Vector();
        measure_concept.sorted_pi_conj_nums = new Vector();

        measure_concept.sorted_equiv_conj_nums.addElement(new Double(0));
        measure_concept.sorted_ne_conj_nums.addElement(new Double(0));
        measure_concept.sorted_imp_conj_nums.addElement(new Double(0));
        measure_concept.sorted_pi_conj_nums.addElement(new Double(0));

        categorisations = new Vector();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            concept.entity_concept = conceptFromName(concept.object_type);

            concept.generalisations = new Vector();
            for (int j=0; j<concepts.size(); j++)
            {
                Concept generalisation = (Concept)concepts.elementAt(j);
                if (generalisation.isGeneralisationOf(concept)==0)
                    concept.generalisations.addElement(generalisation);
            }
            addCategorisation(concept);
            measure_concept.measureConcept(concept, concepts, true);
            Vector v1 = (Vector)measure_concept.equiv_conj_scores_hashtable.get(new Double(0));
            v1.addElement(concept);
            Vector v2 = (Vector)measure_concept.ne_conj_scores_hashtable.get(new Double(0));
            v2.addElement(concept);
            Vector v3 = (Vector)measure_concept.imp_conj_scores_hashtable.get(new Double(0));
            v3.addElement(concept);
            Vector v4 = (Vector)measure_concept.pi_conj_scores_hashtable.get(new Double(0));
            v4.addElement(concept);

            Vector v1a = (Vector)measure_concept.equiv_conj_nums_hashtable.get(new Double(0));
            v1a.addElement(concept);
            Vector v2a = (Vector)measure_concept.ne_conj_nums_hashtable.get(new Double(0));
            v2a.addElement(concept);
            Vector v3a = (Vector)measure_concept.imp_conj_nums_hashtable.get(new Double(0));
            v3a.addElement(concept);
            Vector v4a = (Vector)measure_concept.pi_conj_nums_hashtable.get(new Double(0));
            v4a.addElement(concept);
        }

        // Set the highlighted concepts (and pass concepts to front end at same time)

        String[] highlights = front_end.highlight_list.getSelectedItems();
        for (int i=0; i<highlights.length; i++)
        {
            String concept_id = highlights[i].substring(0, highlights[i].indexOf(":"));
            Concept concept = getConcept(concept_id);
            concept.highlight_score = 1.0;
        }

        // React to the new concepts

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            react.reactTo(concept,"user_given_concept");
        }

        // Add the concepts to the agenda and their datatables to the hashtable //

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            agenda.addConcept(concept, concepts, this);
            String first_tuples = concept.datatable.firstTuples();
            Vector same_datatable_concept_vector = (Vector)datatable_hashtable.get(first_tuples);
            if (same_datatable_concept_vector==null)
            {
                same_datatable_concept_vector = new Vector();
                datatable_hashtable.put(first_tuples, same_datatable_concept_vector);
            }
            same_datatable_concept_vector.addElement(concept);
        }

        // Find the integer relation for the size production rule

        for (int i=0; i<specifications.size(); i++)
        {
            Specification spec = (Specification)specifications.elementAt(i);
            Relation relation = (Relation)spec.relations.elementAt(0);
            if (relation.name.equals("integer"))
                size.integer_relation = relation;
        }

        // Initialise the explanation handler

        explanation_handler.initialiseExplainers(this);

        // Check for equivalences, non-existences and implications in the user-given concepts

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);

            if (make_subsumption_implications)
            {
                Vector new_entities_from_subsumption = makeSubsumptionConjectures(concept);
                for (int j=0; j<new_entities_from_subsumption.size(); j++)
                {
                    Entity entity = (Entity)new_entities_from_subsumption.elementAt(j);
                    addNewEntityToTheory(entity, concept.domain, "initial");
                }
            }

            if (concept.datatable.isEmpty() && make_non_exists_from_empty)
            {
                NonExists non_exists = new NonExists(concept, Integer.toString(conjectures.size()));
                non_exists.step = new Step();
                non_exists.when_constructed = 0;
                Vector new_entities = addNonExistsToTheory(non_exists, concept);

                for (int k=0; k<new_entities.size(); k++)
                {
                    Entity new_entity = (Entity)new_entities.elementAt(k);
                    addNewEntityToTheory(new_entity, concept.domain, "initial");
                }
            }

            if (make_equivalences_from_equality)
            {
                for (int j=i+1; j<concepts.size(); j++)
                {
                    Concept other_concept = (Concept)concepts.elementAt(j);
                    if (other_concept.arity==concept.arity && concept.datatable.isIdenticalTo(this, other_concept.datatable))
                    {
                        Equivalence equiv = new Equivalence(concept, other_concept, Integer.toString(conjectures.size()));
                        equiv.step = new Step();
                        equiv.when_constructed = 0;
                        Vector new_entities = addEquivalenceToTheory(equiv, concept, other_concept);
                        for (int k=0; k<new_entities.size(); k++)
                        {
                            Entity new_entity = (Entity)new_entities.elementAt(k);
                            addNewEntityToTheory(new_entity, concept.domain, "initial");
                        }
                    }
                }
            }
        }
    }

    /** This adds the given non_existence_conjecture to the theory.
     */

    public Vector addNonExistsToTheory(NonExists non_existence_conjecture, Concept new_concept)
    {
        Vector output = new Vector();

        if (keep_non_existences)
        {
            // 5.4.1 Check the segregration status of a non-existence conjecture

            addToTimer("5.4.1 Checking the segregration status of a non-existence conjecture");
            non_existence_conjecture.checkSegregation(agenda);
            non_existences.addElement(non_existence_conjecture);

            // 5.4.2 Handle the storage of the non-existence conjecture

            addToTimer("5.4.1 Handling the storage of a non-existence conjecture");
            conjectures.addElement(non_existence_conjecture);
        }

        // 5.4.3 Look for a proof to non-existence conjecture //

        addToTimer("5.4.3 Attempting to prove non-existence conjecture");
        boolean is_proved = false;

        explanation_handler.explainConjecture(non_existence_conjecture, this, "5.4.3");
        if (!non_existence_conjecture.proof_status.equals("proved"))
            output = (Vector)non_existence_conjecture.counterexamples.clone();

        // 5.4.4 Update the front end with the non-existence conjecture //

        addToTimer("5.4.4 Updating front end with non-existence conjecture");
        if (keep_non_existences)
            updateFrontEnd(non_existence_conjecture);

        // 5.4.5 Extract implicates from non-existence conjecture //

        if (extract_implicates_from_non_exists && !non_existence_conjecture.proof_status.equals("disproved"))
        {
            // 5.4.5 Extract the implicates //

            addToTimer("5.4.5 Extracting implicates from a non-existence conjecture");
            Vector implicates_from_non_exists = non_existence_conjecture.implicates(concepts);
            for (int j=0; j<implicates_from_non_exists.size(); j++)
            {
                Implicate new_implicate = (Implicate)implicates_from_non_exists.elementAt(j);
                non_existence_conjecture.child_conjectures.addElement(new_implicate);
                new_implicate.parent_conjectures.addElement(non_existence_conjecture);
                new_implicate.when_constructed = seconds_elapsed;
            }

            // 5.4.6 Add implicates from non-existence conjecture to the theory //

            addToTimer("5.4.6 Adding extracted implicates from a non-existence conjecture");
            Vector imp_counterexamples = addImplicates(implicates_from_non_exists, non_existence_conjecture, "5.4.6");
            for (int i=0; i<imp_counterexamples.size(); i++)
                output.addElement(imp_counterexamples.elementAt(i));
        }

        // 5.4.7 Measure the interestingness of the non-existence conjecture //

        addToTimer("5.4.7 Measuring a non-existence conjecture");
        measure_conjecture.measureConjecture(non_existence_conjecture, non_existences, this);

        // 5.4.8 Update the interestingness of the concepts which led to the non_existence conjecture

        addToTimer("5.4.8 Measuring the non-existence score of parent concepts");
        boolean need_to_reorder_agenda =
                measure_concept.updateNeConjectureScore(new_concept.parents, non_existence_conjecture);

        // 5.4.9 Re-order the agenda after a non-existence conjecture
        if (need_to_reorder_agenda)
        {
            addToTimer("5.4.9 Re-ordering the agenda after the addition of a non-existence conjecture");
            agenda.orderConcepts();
        }

        return output;
    }

    /** This adds the given equivalence conjecture to the theory.
     */

    public Vector addEquivalenceToTheory(Equivalence equiv_conj, Concept same_datatable_concept, Concept new_concept)
    {
        // 6.13.1 Add equivalence conjecture to the explanation handler

        addToTimer("6.14.1 Adding equivalence conjecture to the explanation handler");
        if (keep_equivalences)
            explanation_handler.addConjecture(equiv_conj);

        Vector output = new Vector();
        boolean need_to_reorder_agenda = false;
        if (keep_equivalences)
        {

            // 6.14.2 Check the segregation status of equivalence conjecture

            addToTimer("6.14.2 Checking the segregation status of an equivalence conjecture");
            equiv_conj.checkSegregation(agenda);
            equivalences.addElement(equiv_conj);

            // 6.14.3 Handling the storage of an equivalence conjecture

            addToTimer("6.14.3 Handling the storage of an equivalence conjecture");
            conjectures.addElement(equiv_conj);
        }

        // 6.8 Measuring the equivalence conjecture //

        addToTimer("6.14.4 Measuring an equivalence conjecture");
        measure_conjecture.measureConjecture(equiv_conj, equivalences);

        // 6.14.5 Look for a proof or counterexamples to equivalence conjecture //

        addToTimer("6.14.5 Attempting to settle equivalence conjecture");
        explanation_handler.explainConjecture(equiv_conj, this, "6.14.5");

        if (!equiv_conj.proof_status.equals("proved"))
            output = (Vector)equiv_conj.counterexamples.clone();

        if (keep_equivalences)
        {
            // 6.14.6 Add equivalence conjecture to front end //

            addToTimer("6.14.6 Adding equivalence conjecture to front end");
            updateFrontEnd(equiv_conj);
        }

        // 6.14.7 Make all the additional equivalence conjectures from the //
        // alternative concept definitions //

        addToTimer("6.14.7 Making additional equivalences from the alternative concepts");

        if (output.isEmpty())
        {
            Vector new_equivalence_conjectures = new Vector();
            new_equivalence_conjectures.addElement(equiv_conj);

            // 6.14.8 Update the equivalence conjecture score of the old concept //

            addToTimer("6.14.8 Updating the equivalence conjecture score of a concept");
            need_to_reorder_agenda =
                    measure_concept.updateEquivConjectureScore(same_datatable_concept, equiv_conj);

            if (make_equivalences_from_combination)
            {
                for (int i=0; i<same_datatable_concept.conjectured_equivalent_concepts.size(); i++)
                {
                    // 6.14.9 Make Equivalences from combination //

                    addToTimer("6.14.9 Making equivalences from combination");
                    Concept alt_concept =
                            (Concept)same_datatable_concept.conjectured_equivalent_concepts.elementAt(i);
                    Equivalence new_equiv_conj =
                            new Equivalence(alt_concept,new_concept,Integer.toString(numberOfConjectures()));
                    explanation_handler.explainConjecture(new_equiv_conj, this, "6.14.9");
                    if (keep_equivalences)
                    {
                        // 6.14.10 Check the segregation status of an equivalence conjecture from combination

                        addToTimer("6.14.10 Check the segregation status of an equivalence conjecture from combination");
                        new_equiv_conj.checkSegregation(agenda);
                        equivalences.addElement(new_equiv_conj);

                        // 6.14.11 Store the equivalence conjecture from combination

                        addToTimer("6.14.11 Dealing with the storage of an equivalence conjecture from combination");
                        conjectures.addElement(new_equiv_conj);

                        // 6.14.12 Measure an equivalence conjecture from combination

                        addToTimer("6.14.12 Measuring an equivalence conjecture from combination");
                        measure_conjecture.measureConjecture(new_equiv_conj, equivalences);

                        // 6.14.13 Update the front end with equivalence conjecture from combination

                        addToTimer("6.14.13 Updating the front end with equivalence conjecture from combination");
                        updateFrontEnd(new_equiv_conj);
                    }

                    new_equivalence_conjectures.addElement(new_equiv_conj);

                    if (!new_equiv_conj.proof_status.equals("proved"))
                    {
                        for (int j=0; j<new_equiv_conj.counterexamples.size(); j++)
                        {
                            Entity entity = (Entity)new_equiv_conj.counterexamples.elementAt(j);
                            output.addElement(entity);
                        }
                    }

                    // 6.16 Update the interestingness of the concepts which led to the equivalence conjecture

                    addToTimer("6.14.14 Measuring the equivalence score of equivalent concepts");
                    boolean need_to_reorder =
                            measure_concept.updateEquivConjectureScore(same_datatable_concept, new_equiv_conj);
                    if (need_to_reorder)
                        need_to_reorder_agenda = true;
                }
            }

            if (extract_implications_from_equivalences && keep_implications)
            {
                // 6.14.15 Extract implications from the equivalence conjectures
                addToTimer("6.14.15 Extracting implications from an equivalence conjecture");
                for (int i=0; i<new_equivalence_conjectures.size(); i++)
                {
                    Equivalence equiv = (Equivalence)new_equivalence_conjectures.elementAt(i);
                    Implication implication1 =
                            new Implication(equiv.lh_concept, equiv.rh_concept, Integer.toString(conjectures.size()));
                    implication1.when_constructed = seconds_elapsed;
                    implication1.checkSegregation(agenda);
                    conjectures.addElement(implication1);
                    Implication implication2 =
                            new Implication(equiv.rh_concept, equiv.lh_concept, Integer.toString(conjectures.size()));
                    implication2.checkSegregation(agenda);
                    implication2.when_constructed = seconds_elapsed;
                    if (equiv.proof_status.equals("proved"))
                    {
                        implication1.proof_status = "proved";
                        implication1.explained_by = "Proof of parent";
                        implication1.proof = "Proof of parent:\n" + equiv.proof;
                        implication2.proof_status = "proved";
                        implication2.explained_by = "Proof of parent";
                        implication2.proof = "Proof of parent:\n" + equiv.proof;
                    }

                    conjectures.addElement(implication2);
                    implications.addElement(implication1);
                    implications.addElement(implication2);
                    measure_conjecture.measureConjecture(implication1, implications);
                    measure_conjecture.measureConjecture(implication2, implications);
                    updateFrontEnd(implication1);
                    updateFrontEnd(implication2);
                }
            }

            if (extract_implicates_from_equivalences)
            {
                // 6.14.16 Extract implicates from the equivalence conjectures

                for (int i=0; i<new_equivalence_conjectures.size(); i++)
                {
                    addToTimer("6.14.16 Extracting implicates from an equivalence conjecture");
                    Equivalence equiv = (Equivalence)new_equivalence_conjectures.elementAt(i);
                    Vector new_implicates = equiv_conj.implicates(specification_handler);
                    for (int j=0; j<new_implicates.size(); j++)
                    {
                        Implicate new_implicate = (Implicate)new_implicates.elementAt(j);
                        equiv.child_conjectures.addElement(new_implicate);
                        new_implicate.parent_conjectures.addElement(equiv);
                    }

                    // 6.14.17 Add the implicates to the theory

                    addToTimer("6.14.17 Adding implicates from an equivalence conjecture to the theory");
                    Vector implicate_counterexamples = addImplicates(new_implicates, equiv, "6.14.17");
                    for (int j=0; j<implicate_counterexamples.size(); j++)
                    {
                        Entity entity = (Entity)implicate_counterexamples.elementAt(j);
                        output.addElement(entity);
                    }
                }
            }
        }

        // 6.14.17 Re-order the agenda after adding an equivalence conjecture

        if (need_to_reorder_agenda)
        {
            addToTimer("6.14.18 Re-ordering the agenda after adding an equivalence conjecture");
            agenda.orderConcepts();
        }
        return output;
    }

    public void keyPressed(KeyEvent e)
    {
        // Fill in details of an object about which some values are to be predicted //

        if (e.getSource()==front_end.predict_entity_text)
        {
            if (e.getKeyCode()==10)
            {
                for (int i=0; i<predict.read_in_concepts.size(); i++)
                {
                    Concept concept = (Concept)predict.read_in_concepts.elementAt(i);
                    if (concept.object_type.equals(front_end.predict_entity_type_choice.getSelectedItem()))
                    {
                        Vector tuples = concept.datatable.rowWithEntity(front_end.predict_entity_text.getText()).tuples;
                        for (int j=0; j<front_end.predict_names_buttons_vector.size(); j++)
                        {
                            String concept_in_textfield =
                                    ((Button)front_end.predict_names_buttons_vector.elementAt(j)).getLabel();
                            if (concept_in_textfield.equals(concept.name))
                            {
                                TextField predict_values_text = (TextField)front_end.predict_values_texts_vector.elementAt(j);
                                predict_values_text.setText(tuples.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        // Recording a new macro //

        if (e.getSource()==front_end.record_macro_check)
        {
            front_end.main_frame.macro_text_layout.show(front_end.main_frame.macro_centre_panel, "macro_panel");
        }

        // Load a macro file //

        if (e.getSource()==front_end.macro_list)
        {
            front_end.macro_text.setText("");
            String fn = input_files_directory + front_end.macro_list.getSelectedItem();
            front_end.main_frame.macro_text_layout.show(front_end.main_frame.macro_centre_panel, "macro_panel");
            loadMacro(fn);
        }

        // Loading plot choices

        if (e.getSource()==front_end.statistics_type_list)
        {
            statistics_handler.addChoices(front_end.statistics_type_list.getSelectedItem(),
                    front_end.statistics_choice_list,
                    front_end.statistics_subchoice_list,
                    front_end.statistics_methods_list, timer);
        }

        // Looking at a concept in the agenda

        if (e.getSource()==front_end.all_ordered_concept_list)
        {
            String id = front_end.all_ordered_concept_list.getSelectedItem();
            Concept concept = getConcept(id);
            front_end.agenda_concept_text.setText(id+". "+concept.writeDefinitionWithStartLetters("otter"));
        }

        if (e.getSource()==front_end.live_ordered_concept_list)
        {
            String id = front_end.live_ordered_concept_list.getSelectedItem();
            Concept concept = getConcept(id);
            front_end.agenda_concept_text.setText(id+". "+concept.writeDefinitionWithStartLetters("otter"));
        }

        // Loading a reaction from a file

        if (e.getSource()==front_end.reactions_list)
        {
            String reaction_name = front_end.reactions_list.getSelectedItem();
            front_end.reaction_name_text.setText(reaction_name);
            String fn = input_files_directory + reaction_name;
            front_end.loadReaction(fn);
        }

        // Conjecture has been chosen from the list //

        if (e.getSource()==front_end.conjecture_list)
        {
            String selected_id = front_end.conjecture_list.getSelectedItem();
            conjecture_chosen_from_front_end = getConjecture(selected_id);
            front_end.updateConjecture(conjecture_chosen_from_front_end);
        }

        // Quick Macro Load and Play //

        if (e.getSource()==front_end.quick_macro_choice)
        {
            String macro_name = front_end.quick_macro_choice.getSelectedItem();
            if (!macro_name.equals("macro"))
            {
                front_end.macro_text.setText("");
                String fn = input_files_directory + macro_name;
                loadMacro(fn);
                playMacro();
                front_end.save_macro_text.setText(macro_name);
            }
        }

        // Display possible parameterisations for a chosen concept and
        // prodrule (for the forced step functionality)

        if (e.getSource()==front_end.force_prodrule_list)
        {
            guider.loadParameterisations(this);
            guider.writeForceString(this);
        }

        if (e.getSource()==front_end.force_primary_concept_list)
        {
            front_end.deselectAll(front_end.force_secondary_concept_list);
            front_end.deselectAll(front_end.force_prodrule_list);
            front_end.force_parameter_list.removeAll();
            guider.writeForceString(this);
        }

        if (e.getSource()==front_end.force_secondary_concept_list)
        {
            front_end.deselectAll(front_end.force_prodrule_list);
            front_end.force_parameter_list.removeAll();
            guider.writeForceString(this);
        }

        if (e.getSource()==front_end.force_parameter_list)
        {
            guider.writeForceString(this);
        }

        if (e.getSource()==front_end.predict_input_files_choice)
        {
            String input_file_name = input_files_directory + front_end.predict_input_files_choice.getSelectedItem();
            Vector input_information = reader.readFile(input_file_name, "");
            predict.read_in_concepts = (Vector)input_information.elementAt(0);
            predict.relations = (Vector)input_information.elementAt(5);
        }

        if (e.getSource()==front_end.predict_entity_type_choice)
        {
            for (int i=0; i<front_end.predict_names_buttons_vector.size(); i++)
            {
                Button but = (Button)front_end.predict_names_buttons_vector.elementAt(i);
                but.setLabel("");
            }
            String entity_type_choice = front_end.predict_entity_type_choice.getSelectedItem();
            int pos = 0;
            for (int i=0; i<concepts.size(); i++)
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if (concept.is_user_given &&
                        concept.object_type.equals(entity_type_choice))
                {
                    Button but = (Button)front_end.predict_names_buttons_vector.elementAt(pos);
                    but.setLabel(concept.name);
                    pos++;
                }
            }
        }

        if (e.getSource()==front_end.domain_list)
        {
            String domain = front_end.domain_list.getSelectedItem();
            String[] selectedItems = this.front_end.domain_list.getSelectedItems();
            for (String selectedItem : selectedItems) {
                if (selectedItem.contains(".")) {
                    domain = selectedItem.substring(0, selectedItem.indexOf("."));
                }
                String domain_file_name = input_files_directory + selectedItem;
                Vector domain_information = reader.readFile(domain_file_name, "");
                front_end.addDomainInformation(domain_information);
                Vector new_domain_concepts = (Vector) domain_information.elementAt(0);
                for (int i = 0; i < new_domain_concepts.size(); i++)
                    domain_concepts.addElement((Concept) new_domain_concepts.elementAt(i));
                for (int i = 0; i < front_end.initial_concepts_list.getItemCount(); i++)
                    front_end.initial_concepts_list.select(i);
                for (int i = 0; i < front_end.initial_entity_list.getItemCount(); i++)
                    front_end.initial_entity_list.select(i);
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        // Saving the proof tables to the macro //

        if (e.getSource()==front_end.add_proof_tables_to_macro_button)
        {
            macro_handler.saveTableToMacro(front_end.axiom_table);
            macro_handler.saveTableToMacro(front_end.algebra_table);
            macro_handler.saveTableToMacro(front_end.file_prover_table);
            macro_handler.saveTableToMacro(front_end.other_prover_table);
        }

        // Running a piece of pseudo-code //

        if (e.getSource()==front_end.pseudo_code_run_button)
        {
            Hashtable original_alias_hashtable = new Hashtable();
            original_alias_hashtable.put("theory", this);
            String pseudo_code = front_end.pseudo_code_input_text.getText();
            pseudo_code_interpreter.runPseudoCode(pseudo_code, original_alias_hashtable);
        }

        // Saving a construction history of a concept to a file //

        if (e.getSource()==front_end.save_concept_construction_button)
        {
            Concept concept = getConcept(front_end.save_concept_construction_concept_id_text.getText().trim());
            if (!concept.id.equals(""))
            {
                String filename = front_end.save_concept_construction_filename_text.getText().trim();
                storage_handler.writeConstructionToFile(filename, concept);
            }
        }

        // Loading a concept from a construction history file //

        if (e.getSource()==front_end.build_concept_button)
        {
            String filename = front_end.build_concept_filename_text.getText().trim();
            storage_handler.buildConstructionFromFile(filename, this);
        }

        // Saving a construction history of a conjecture to a file //

        if (e.getSource()==front_end.save_conjecture_construction_button)
        {
            Equivalence conjecture =
                    (Equivalence)getConjecture(front_end.save_conjecture_construction_conjecture_id_text.getText().trim());
            if (!conjecture.id.equals(""))
            {
                String filename = front_end.save_conjecture_construction_filename_text.getText().trim();
                storage_handler.writeConstructionToFile(filename, conjecture);
            }
        }

        // Loading a conjecture from a construction history file //

        if (e.getSource()==front_end.build_conjecture_button)
        {
            String filename = front_end.build_conjecture_filename_text.getText().trim();
            storage_handler.buildConstructionFromFile(filename, this);
        }

        // Compiling a graph

        if (e.getSource()==front_end.statistics_compile_button)
        {
            Vector plot_choices = new Vector();
            String[] pcs = front_end.statistics_choice_list.getSelectedItems();
            for (int i=0; i<pcs.length; i++)
            {
                if (pcs[i].equals("id"))
                    plot_choices.insertElementAt(pcs[i],0);
                else
                    plot_choices.addElement(pcs[i]);
            }
            String[] pscs = front_end.statistics_subchoice_list.getSelectedItems();
            for (int i=0; i<pscs.length; i++)
                plot_choices.addElement(pscs[i]);
            statistics_handler.compileData(front_end.statistics_type_list.getSelectedItem(),
                    plot_choices, front_end.statistics_average_check.getState(),
                    front_end.statistics_count_check.getState(),
                    this, front_end.statistics_prune_text.getText(),
                    front_end.statistics_calculation_text.getText(),
                    front_end.statistics_sort_text.getText(),
                    front_end.statistics_output_file_text.getText());
        }

        //  Plotting a graph

        if (e.getSource()==front_end.statistics_plot_button)
        {
            graph_handler.plotGraphs(front_end.statistics_seperate_graphs_check.getState());
        }

        // Searching (Grep) for a string over the concept details.

        if (e.getSource()==front_end.grep_concept_button)
        {
            front_end.concept_grep_positions.clear();
            String grep_string = front_end.grep_concept_text.getText().trim();
            Vector concept_ids = new Vector();
            for (int i=0; i<front_end.concept_list.getItemCount(); i++)
            {
                Concept concept = getConcept(front_end.concept_list.getItem(i));
                String details = concept.fullDetails("html", front_end.concept_formatting_list.getSelectedItems(),
                        front_end.decimal_places);
                Vector grep_positions = new Vector();
                if (text_handler.getGrepPositions(grep_string, details, grep_positions, 0) == true)
                {
                    front_end.concept_grep_positions.put(concept.id, grep_positions);
                    concept_ids.addElement(concept.id);
                }
            }
            front_end.concept_list.removeAll();
            for (int i=0; i<concept_ids.size(); i++)
                front_end.concept_list.addItem((String)concept_ids.elementAt(i));
            front_end.concept_list_number.setText("number: " +
                    Integer.toString(front_end.concept_list.getItemCount()));
        }

        // Keeping all concepts which satisfy a particular condition //

        if (e.getSource()==front_end.condition_concept_button)
        {
            String condition_string = front_end.condition_concept_text.getText().trim();
            Vector concept_ids = new Vector();
            for (int i=0; i<front_end.concept_list.getItemCount(); i++)
            {
                Concept concept = getConcept(front_end.concept_list.getItem(i));
                if (reflect.checkCondition(concept, condition_string))
                    concept_ids.addElement(concept.id);
            }
            front_end.concept_list.removeAll();
            for (int i=0; i<concept_ids.size(); i++)
                front_end.concept_list.addItem((String)concept_ids.elementAt(i));
            front_end.concept_list_number.setText("number: " +
                    Integer.toString(front_end.concept_list.getItemCount()));
        }

        // Searching (Grep) for a string over the conjecture details.

        if (e.getSource()==front_end.grep_conjecture_button)
        {
            front_end.conjecture_grep_positions.clear();
            String grep_string = front_end.grep_conjecture_text.getText().trim();
            Vector conjecture_ids = new Vector();
            for (int i=0; i<front_end.conjecture_list.getItemCount(); i++)
            {
                Conjecture conjecture = getConjecture(front_end.conjecture_list.getItem(i));
                String details = conjecture.fullHTMLDetails(front_end.conjecture_formatting_list.getSelectedItems(),
                        front_end.decimal_places,
                        front_end.concept_formatting_list.getSelectedItems());
                Vector grep_positions = new Vector();
                if (text_handler.getGrepPositions(grep_string, details, grep_positions, 0) == true)
                {
                    conjecture_ids.addElement(conjecture.id);
                    front_end.conjecture_grep_positions.put(conjecture.id, grep_positions);
                }
            }
            front_end.conjecture_list.removeAll();
            for (int i=0; i<conjecture_ids.size(); i++)
                front_end.conjecture_list.addItem((String)conjecture_ids.elementAt(i));
            front_end.conjecture_list_number.setText("number: " +
                    Integer.toString(front_end.conjecture_list.getItemCount()));
        }

        // Keeping all conjectures which satisfy a particular condition //

        if (e.getSource()==front_end.condition_conjecture_button)
        {
            String condition_string = front_end.condition_conjecture_text.getText().trim();
            Vector conjecture_ids = new Vector();
            for (int i=0; i<front_end.conjecture_list.getItemCount(); i++)
            {
                Conjecture conjecture = getConjecture(front_end.conjecture_list.getItem(i));
                if (reflect.checkCondition(conjecture, condition_string))
                    conjecture_ids.addElement(conjecture.id);
            }
            front_end.conjecture_list.removeAll();
            for (int i=0; i<conjecture_ids.size(); i++)
                front_end.conjecture_list.addItem((String)conjecture_ids.elementAt(i));
            front_end.conjecture_list_number.setText("number: " +
                    Integer.toString(front_end.conjecture_list.getItemCount()));
        }

        // Adding a reaction to those to react to

        if (e.getSource()==front_end.add_reaction_button)
        {
            String file_name = front_end.reactions_list.getSelectedItem();
            front_end.reactions_added_list.addItem(file_name);
            react.addReaction(file_name, front_end.reactions_text.getText(), this);
        }
        // Remove a reaction to those to react to

        if (e.getSource()==front_end.remove_reaction_button)
        {
            String file_name = front_end.reactions_added_list.getSelectedItem();
            front_end.reactions_added_list.remove(file_name);
            react.removeReaction(file_name);
        }

        // Saving an altered reaction

        if (e.getSource()==front_end.save_reaction_button)
        {
            try
            {
                String file_name = front_end.reaction_name_text.getText();
                if (file_name!=null)
                {
                    String fn = input_files_directory + file_name;
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fn)));
                    out.println(front_end.reactions_text.getText());
                    out.close();
                    front_end.getReactionFiles();
                }
            }
            catch(Exception ex){System.out.println(ex);}
        }

        // Trying a user-given possible counterexample (or set of counterexamples) to
        // test the truth of a conjecture.

        if (e.getSource()==front_end.test_conjecture_button)
        {
            Conjecture conj = conjecture_chosen_from_front_end;
            if (conj!=null)
            {
                if (!front_end.test_conjecture_entity2_text.getText().equals(""))
                {
                    int lh_value = valueFromIntField(front_end.test_conjecture_entity1_text);
                    int rh_value = valueFromIntField(front_end.test_conjecture_entity2_text);
                    for (int i=lh_value; i<=rh_value; i++)
                    {
                        //System.out.println("i'm in theory -- help!");
                        //System.out.println(i);
                        front_end.test_conjecture_entity1_text.setText(Integer.toString(i));
                        boolean bc = conj.conjectureBrokenByEntity(Integer.toString(i), concepts);
                        if (bc)
                        {
                            front_end.test_conjecture_output_text.setText("counter: " + Integer.toString(i));
                            front_end.test_conjecture_entity1_text.setText(Integer.toString(lh_value));
                            conj.proof_status = "disproved";
                            Entity counter = new Entity(Integer.toString(i));
                            conj.counterexamples.addElement(counter);
                            conj.explained_by = "User-given counterexample";
                            return;
                        }
                    }
                    front_end.test_conjecture_output_text.setText("No counter between " +
                            Integer.toString(lh_value) + " and " +
                            Integer.toString(rh_value));
                    front_end.test_conjecture_entity1_text.setText(Integer.toString(lh_value));
                }
            }
            else
            {
                String test_value = front_end.test_conjecture_entity1_text.getText();
                boolean bc = conj.conjectureBrokenByEntity(test_value, concepts);
                if (bc)
                    front_end.test_conjecture_output_text.setText("counter: " + test_value);
                else
                    front_end.test_conjecture_output_text.setText("not a counterexample");
            }
        }

        if (e.getSource()==front_end.add_counterexamples_found_from_testing_button)
        {
        }

        // Re-proving all the conjectures (possibly in the light of //
        // additional axioms //

        if (e.getSource()==front_end.re_prove_all_button)
        {
            int num_proved = 0;
            for (int i=0; i<conjectures.size(); i++)
            {
                front_end.re_prove_all_button.setLabel(Integer.toString(i)+"/"+
                        Integer.toString(conjectures.size())+"/"+
                        Integer.toString(num_proved));
                Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
                if (!conjecture.proof_status.equals("proved") && !conjecture.proof_status.equals("axiom"))
                    explanation_handler.explainConjecture(conjecture, this, "Re-proving");
                if (conjecture.proof_status.equals("proved"))
                    num_proved++;
            }
            front_end.re_prove_all_button.setLabel("Re-prove all");
        }

        // Adding a conjecture as an axiom //

        if (e.getSource()==front_end.add_conjecture_as_axiom_button)
        {
            if (conjecture_chosen_from_front_end!=null)
                explanation_handler.addConjectureAsAxiom(conjecture_chosen_from_front_end);
        }

        // Forcing a string to be put in the forced steps part of the agenda and carried out //

        if (e.getSource()==front_end.force_button)
        {
            try
            {
                Step step = guider.forceStep(front_end.force_string_text.getText(), this);
                front_end.deselectAll(front_end.force_primary_concept_list);
                front_end.deselectAll(front_end.force_secondary_concept_list);
                front_end.deselectAll(front_end.force_prodrule_list);
                front_end.force_parameter_list.removeAll();
                guider.writeForceString(this);

                front_end.force_result_text.setText(step.result_of_step_explanation);
                if (!step.result_of_step.id.equals(""))
                    front_end.force_result_text.append("\n" + step.result_of_step.id);
            }
            catch(Exception ex)
            {
                front_end.force_result_text.setText("problem with step:\n");
                front_end.force_result_text.append(ex.getMessage()+"\n"+ex.toString());
                ex.printStackTrace();
            }
        }

        // Updating the agenda on screen //

        if (e.getSource()==front_end.update_agenda_button)
        {
            int val = (new Integer(front_end.auto_update_text.getText())).intValue();
            if (val<0)
                val = 0;
            updateAgendaFrontEnd(val);
        }

        // Showing the forced steps //

        if (e.getSource()==front_end.show_forced_steps_button)
        {
            front_end.agenda_list.removeAll();
            for (int i=0; i<agenda.forced_steps.size(); i++)
            {
                String step_string = (String)agenda.forced_steps.elementAt(i);
                Step step = agenda.getStepFromString(step_string, this);
                if (step!=null)
                {
                    String step_as_string = step.asString();
                    if (!step.concept_arising_name.equals(""))
                    {
                        step_as_string = step.concept_arising_name + " = " + step_as_string;
                        if (agenda.getConcept(step.concept_arising_name)!=null)
                            step_as_string = agenda.getConcept(step.concept_arising_name).id + " = " +  step_as_string;
                    }

                    front_end.agenda_list.addItem(step_as_string);
                }
            }
        }

        // Computing near equivalences //

        if (e.getSource()==front_end.find_extra_conjectures_button)
        {
            Concept concept = getConcept(front_end.concept_list.getSelectedItem());
            if (concept!=null)
            {
                Double d = new Double(front_end.near_equivalence_percent_text.getText());
                concept.getNearEquivalences(this, concepts, d.doubleValue());
            }
            //	concept.getSubsumptionNearImplications(concepts, d.doubleValue()); //alisonp
            concept.getRelevantImplicates(this);
            concept.getRelevantEquivalences(this);
            front_end.updateConcept();
        }

        // Computing near implications - alisonp//

        if (e.getSource()==front_end.find_extra_conjectures_button)
        {
            Concept concept = getConcept(front_end.concept_list.getSelectedItem());
            if (concept!=null)
            {
                Double d = new Double(front_end.near_implication_percent_text.getText());
                concept.getSubsumptionNearImplications(concepts, d.doubleValue());
            }
            //concept.getRelevantImplicates(this);
            front_end.updateConcept();
        }



        // Drawing the construction history graph of a concept //

        if (e.getSource()==front_end.draw_concept_construction_history_button)
        {
            Concept concept = getConcept(front_end.concept_list.getSelectedItem());
            if (concept!=null)
                graph_handler.drawConstructionHistory(concept, this, "prolog");
        }

        // Drawing the construction history of a graph //

        if (e.getSource()==front_end.draw_conjecture_construction_history_button)
        {
            Conjecture conjecture = conjecture_chosen_from_front_end;
            if (conjecture!=null)
                graph_handler.drawConstructionHistory(conjecture, this, "prolog");
        }

        // Submitting a conjecture to SystemOnTPTP //

        if (e.getSource()==front_end.sot_submit_button)
        {
            Conjecture conj = new Conjecture();
            String reqd_conj_number = front_end.sot_conjecture_number_text.getText();
            System.out.println(reqd_conj_number);
            if (reqd_conj_number.length()>5 && reqd_conj_number.substring(0,5).equals("tptp:"))
            {
                String tptp_number = reqd_conj_number.substring(5,reqd_conj_number.length());
                front_end.sot_results_text.setText("Submitting " + tptp_number + " to SystemOnTPTP\n\n");
                sot_handler.submitTPTP(tptp_number, front_end.sot_results_text);
                front_end.sot_results_text.append("---- finished ----");
            }
            else
            {
                for (int i=0; i<conjectures.size() && !conj.id.equals(reqd_conj_number); i++)
                    conj = (Conjecture)conjectures.elementAt(i);
                if (!conj.id.equals(""))
                {
                    front_end.sot_results_text.setText("Submitting: \n" +
                            conj.writeConjecture("tptp") + " \nto SystemOnTPTP\n\n");
                    sot_handler.submit(conj, front_end.sot_results_text);
                    front_end.sot_results_text.append("---- finished ----");
                }
            }
        }

        if (e.getSource()==front_end.puzzle_generate_button)
        {
            front_end.puzzle_output_text.setText("");
            int ooo_choices_size = (new Integer(front_end.puzzle_ooo_choices_size_text.getText())).intValue();
            int nis_choices_size = (new Integer(front_end.puzzle_nis_choices_size_text.getText())).intValue();
            int analogy_choices_size = (new Integer(front_end.puzzle_analogy_choices_size_text.getText())).intValue();
            puzzler.generatePuzzles(front_end.puzzle_output_text, ooo_choices_size,
                    nis_choices_size, analogy_choices_size, concepts);
        }

        // Run the debugging function //

        if (e.getSource()==front_end.systemout_button)
        {
            debugger.runFunction(this, front_end.debug_parameter_text.getText());
        }

        // Make a new report generator //

        if (e.getSource()==front_end.execute_report_button)
        {
            front_end.execute_report_button.setLabel("Please Wait");
            report_generator.runReports(this, front_end.reports_to_execute_list.getSelectedItems());
            front_end.execute_report_button.setLabel("Execute reports");
        }

        // Make predictions //

        if (e.getSource()==front_end.predict_all_button)
        {
            int max_steps = valueFromIntField(front_end.predict_max_steps_text);
            double percent = valueFromDoubleField(front_end.predict_min_percent_text)/100;
            predict.use_negation = front_end.predict_use_negations_check.getState();
            predict.use_equivalences = front_end.predict_use_equivalences_check.getState();
            predict.makeAllPredictions(front_end.predict_method_choice.getSelectedItem(),
                    percent, front_end.predict_entity_type_choice.getSelectedItem(),
                    concepts, front_end.predict_names_buttons_vector,
                    front_end.predict_values_texts_vector,
                    front_end.predict_explanation_list, max_steps, false);
        }

        if (e.getSource()==front_end.predict_incremental_steps_button)
        {
            int increment_steps = valueFromIntField(front_end.predict_max_steps_text);
            double percent = valueFromDoubleField(front_end.predict_min_percent_text)/100;
            predict.use_negation = front_end.predict_use_negations_check.getState();
            predict.use_equivalences = front_end.predict_use_equivalences_check.getState();
            predict.makeIncrementalPredictions(front_end.predict_method_choice.getSelectedItem(),
                    percent, front_end.predict_entity_type_choice.getSelectedItem(),
                    concepts, front_end.predict_names_buttons_vector,
                    front_end.predict_values_texts_vector,
                    front_end.predict_explanation_list,
                    increment_steps, steps_taken);
        }

        for (int i=0; i<front_end.predict_names_buttons_vector.size(); i++)
        {
            Button but = (Button)front_end.predict_names_buttons_vector.elementAt(i);
            int max_steps = valueFromIntField(front_end.predict_max_steps_text);
            double percent = valueFromDoubleField(front_end.predict_min_percent_text)/100;
            predict.use_negation = front_end.predict_use_negations_check.getState();
            predict.use_equivalences = front_end.predict_use_equivalences_check.getState();
            if (e.getSource()==but)
            {
                TextField clear_text = (TextField)front_end.predict_values_texts_vector.elementAt(i);
                clear_text.setText("");
                Vector names = predict.getNames(front_end.predict_names_buttons_vector);
                Vector values = predict.getValues(front_end.predict_values_texts_vector,
                        front_end.predict_names_buttons_vector);
                predict.makePredictions(front_end.predict_method_choice.getSelectedItem(),
                        front_end.predict_entity_text.getText(), names, values, percent,
                        front_end.predict_entity_type_choice.getSelectedItem(),
                        concepts, front_end.predict_names_buttons_vector,
                        front_end.predict_values_texts_vector,
                        front_end.predict_explanation_list, max_steps);
                break;
            }
        }

        if (e.getSource()==front_end.predict_button)
        {
            int max_steps = valueFromIntField(front_end.predict_max_steps_text);
            double percent = valueFromDoubleField(front_end.predict_min_percent_text)/100;
            Vector names = predict.getNames(front_end.predict_names_buttons_vector);
            Vector values = predict.getValues(front_end.predict_values_texts_vector,
                    front_end.predict_names_buttons_vector);
            predict.use_negation = front_end.predict_use_negations_check.getState();
            predict.use_equivalences = front_end.predict_use_equivalences_check.getState();
            predict.makePredictions(front_end.predict_method_choice.getSelectedItem(),
                    front_end.predict_entity_text.getText(), names, values, percent,
                    front_end.predict_entity_type_choice.getSelectedItem(),
                    concepts, front_end.predict_names_buttons_vector,
                    front_end.predict_values_texts_vector,
                    front_end.predict_explanation_list, max_steps);
        }

        // Update the front end //

        if (e.getSource()==front_end.update_front_end_button)
        {
            boolean old_state = front_end.update_front_end_check.getState();
            front_end.update_front_end_check.setState(true);
            updateFrontEnd();
            front_end.update_front_end_check.setState(old_state);
        }

        // Measure the concepts //

        if (e.getSource()==front_end.measure_concepts_button)
        {
            boolean old_state = measure_concept.measure_concepts;
            measure_concept.measure_concepts = true;
            for (int conc_num=0;conc_num<concepts.size();conc_num++)
            {
                Concept c = (Concept)concepts.elementAt(conc_num);
                measure_concept.measureConcept(c, concepts, false);
            }
            measure_concept.measure_concepts = old_state;
        }

        if (e.getSource()==front_end.initial_concepts_all_button)
        {
            for (int i=0; i<front_end.initial_concepts_list.getItemCount(); i++)
                front_end.initial_concepts_list.select(i);
        }

        if (e.getSource()==front_end.initial_concepts_none_button)
        {
            for (int i=0; i<front_end.initial_concepts_list.getItemCount(); i++)
                front_end.initial_concepts_list.deselect(i);
        }

        // Play macro //

        if (e.getSource()==front_end.play_macro_button)
        {
            macro_to_complete = front_end.macro_text.getText();
            playMacro();
        }

        // Start/Continue/Load Button //

        if (e.getSource() == front_end.start_button)
        {
            String button_title = front_end.start_button.getLabel();
            if (button_title.equals("Load"))
            {
                initialiseTheory();
                front_end.start_button.setLabel("Start");
                status("Ready to start");
                return;
            }
            time_already_run = time_already_run + seconds_elapsed;
            seconds_elapsed = 0;
            run_start_time = System.currentTimeMillis();
            if (button_title.equals("Start"))
            {
                front_end.start_button.setEnabled(false);
                front_end.stop_button.setEnabled(true);
                front_end.step_button.setEnabled(false);
                stop_now = false;
                int number_to_complete =
                        (new Integer(front_end.required_text.getText())).intValue();
                formTheoryUntil(number_to_complete,front_end.required_choice.getSelectedItem());
            }
            if (button_title.equals("Continue"))
            {
                front_end.start_button.setEnabled(false);
                front_end.stop_button.setEnabled(true);
                front_end.step_button.setEnabled(false);
                stop_now = false;
                use_front_end = true;
                getFrontEndSettings();
                int number_to_complete =
                        (new Integer(front_end.required_text.getText())).intValue();
                formTheoryUntil(number_to_complete,front_end.required_choice.getSelectedItem());
            }
        }

        if (e.getSource() == front_end.stop_button)
        {
            status("Stopping");
            String button_title = front_end.stop_button.getLabel();
            if (button_title.equals("Stop"))
            {
                macro_to_complete = "";
                front_end.macro_text.select(0,0);
                front_end.start_button.setLabel("Continue");
                front_end.stop_button.setLabel("Kill");
                stop_now = true;
            }
            if (button_title.equals("Kill"))
            {
                front_end.stop_button.setLabel("Stop");
                stopIt();
                front_end.stop_button.setEnabled(false);
                front_end.start_button.setEnabled(true);
                front_end.step_button.setEnabled(true);
            }
        }

        // Step Button //

        if (e.getSource()==front_end.step_button)
        {
            theoryFormationStep();
        }
    }

    private void updateAgendaFrontEnd(int list_size)
    {
        front_end.live_ordered_concept_list.clear();
        front_end.all_ordered_concept_list.clear();
        for (int i=0; i<agenda.live_ordered_concepts.size() && i<=list_size; i++)
            front_end.live_ordered_concept_list.addItem(((Concept)agenda.live_ordered_concepts.elementAt(i)).id);
        for (int i=0; i<agenda.all_ordered_concepts.size() && i<=list_size; i++)
            front_end.all_ordered_concept_list.addItem(((Concept)agenda.all_ordered_concepts.elementAt(i)).id);
        if (list_size>0)
        {
            front_end.agenda_list.removeAll();
            for (int i=0; i<agenda.steps_to_force.size() && i<list_size; i++)
            {
                String step_to_force = (String)agenda.steps_to_force.elementAt(i);
                front_end.agenda_list.addItem(step_to_force);
            }
            Vector list_to_complete = agenda.listToComplete(front_end.not_allowed_agenda_check.getState(),
                    list_size - agenda.steps_to_force.size());
            for (int i=0; i<list_to_complete.size(); i++)
                front_end.agenda_list.addItem((String)list_to_complete.elementAt(i));
        }
    }

    /** Connects to MathWeb
     */

    public void connectToMathWeb(String[] args)
    {
        String hostname = args[1];
        String inout_port_string = args[2];
        String service_port_string = args[3];

        int inout_port = Integer.parseInt(inout_port_string);
        int service_port = Integer.parseInt(service_port_string);

        front_end.mathweb_hostname_label.setText("host:"+hostname);
        front_end.mathweb_inout_socket_label.setText("inout socket:"+inout_port_string);
        front_end.mathweb_service_socket_label.setText("service socket:"+service_port_string);

        mathweb_handler = null;
        try
        {
            InetAddress host = InetAddress.getByName(hostname);
            Socket inout= new Socket(host, inout_port);
            mathweb_handler = new MathWebHandler(host, service_port);
        }
        catch (UnknownHostException e)
        {
            System.out.println("Unknown host: " + hostname);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("Could not connect to MathWeb port: " + service_port_string);
            System.exit(1);
        }

        if (mathweb_handler == null)
        {
            System.out.println("HR unable to connect to the MathWeb");
            System.exit(1);
        }
    }


    /** Loads the macro into the textbox and into macro_to_complete.
     */

    public void loadMacro(String fn) {
        front_end.save_macro_text.setText(front_end.macro_list.getSelectedItem());
        try {
            BufferedReader in = new BufferedReader(new FileReader(fn));
            String s = in.readLine();
            String mt = s + "\n";
            while (s!=null) {
                s = in.readLine();
                if (s!=null)
                    mt = mt + s + "\n";
            }
            in.close();
            front_end.macro_text.setText(mt);
        }
        catch (Exception ex)
        {}
        macro_to_complete = front_end.macro_text.getText();
    }

    /** Plays the macro which is present in the front end macro text box.
     */

    public void playMacro() {
        status("Playing macro");
        macro_handler.playMacro(this);
        status("Played macro");
    }



    /** Loads the macro into the textbox and into macro_to_complete.
     */

    public void loadMacro_old(String fn)//friday 23rd
    {
        front_end.save_macro_text.setText(front_end.macro_list.getSelectedItem());
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fn));
            String s = in.readLine();
            while (!(s==null))
            {
                front_end.macro_text.append(s+"\n");
                s = in.readLine();
            }
            in.close();
            if (!s.equals(""))
                front_end.macro_text.append(s+"\n");
        }
        catch (Exception ex){}
        macro_to_complete = front_end.macro_text.getText();
    }

    /** Plays the macro which is present in the front end macro text box.
     */

    public void playMacro_old()//friday 23rd
    {
        status("Playing macro");
        macro_handler.playMacro(this);
        status("Played macro");
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void focusGained(FocusEvent e)
    {
    }

    public void focusLost(FocusEvent e)
    {
    }

    //alisonp
    public void addEntityToTheory(Vector entity_vector, Theory theory, AgentWindow window)
    {
        window.writeToFrontEnd("started addEntityToTheory on " + entity_vector);
        window.writeToFrontEnd("entities currently are " + entities);
        Concept domain_concept = new Concept();
        for(int i=0; i<concepts.size();i++)
        {
            Concept current_concept = (Concept)concepts.elementAt(i);
            if (current_concept.is_object_of_interest_concept)
            {
                domain_concept = current_concept;
                break;
            }
        }

        for(int i=0; i<entity_vector.size();i++)
        {
            Object object = (Object)entity_vector.elementAt(i);
            window.writeToFrontEnd("IN Theory - object is " + object);
            Entity entity = (Entity)entity_vector.elementAt(i);//class cast exception - this isn't an entity
            addNewEntityToTheory(entity, domain_concept.domain, "");
        }

        window.writeToFrontEnd("finished addEntityToTheory");
        window.writeToFrontEnd("entities currently are " + entities);

    }

    /** Returns the object of interest concept in the theory. If there
     * is more than one then it returns the first one in the concepts
     * list */
    public Concept getObjectOfInterestConcept()
    {
        Concept output = new Concept();
        for(int i=0; i<concepts.size();i++)
        {
            Concept current_concept = (Concept)concepts.elementAt(i);
            if (current_concept.is_object_of_interest_concept)
            {
                output = current_concept;
                break;
            }
        }
        return output;
    }




    /** Downgrades the entities in the entity_vector from entities to
     pseudo entities - by removing it from the list of entities and
     putting it into the list of pseudo_entities. This means that when
     generating conjectures, if the entity is a counterexample, this
     will be ignored - so if it is the only counterexample then an
     equivalence will be formed, if it is one of a list of
     counterexamples then a near_equivalence or near_implication will be
     formed, and the entity not included in the percentage of
     counterexamples; and if it is the only example of a concept then a
     non-exists conjecture will be formed. Occurrences of the entity are
     also deleted from all concept datatables. */


    //should really go into Lakatos code
    public void downgradeEntityToPseudoEntity(Entity entity, AgentWindow window)
    {

        System.out.println(" started : downgradeEntityToPseudoEntity");
        for(int i=0;i<entities.size();i++)
        {
            Entity current_entity = (Entity)entities.elementAt(i);

            System.out.println("1) domain of entity " +  current_entity.name + " is " +  current_entity.domain);
        }


        System.out.println("entity.domain is " + entity.domain);

        for(int i=0;i<entities.size();i++)
        {
            Entity current_entity = (Entity)entities.elementAt(i);
            if((current_entity.name).equals(entity.name))
            {
                entities.removeElementAt(i);
                break;
            }
        }

        pseudo_entities.addElement(entity);

        if (front_end.update_front_end_check.getState())
        {
            front_end.removeEntity(entity);
            updateFrontEnd();
        }

        addToTimer("Updating datatables, categorisations and same datatable hashtable after downgrading an entity to pseudo-entity");
        categorisations.removeAllElements();
        Vector changed_first_tuples = new Vector();
        datatable_hashtable = new Hashtable();
        for (int conc_num=0;conc_num<concepts.size();conc_num++)
        {
            Concept c = (Concept)concepts.elementAt(conc_num);
            System.out.println("\n\n datatable for " + c.writeDefinition() + " is:");
            System.out.println(c.datatable.toTable());
            c.datatable.removeEntity(entity.name);

            //go through each of the Tuples for each row and take out any
            //tuple which contains this entity
            for(int i=0;i<c.datatable.size();i++)
            {
                Row row = (Row)c.datatable.elementAt(i);

                for(int j=0;j<row.tuples.size();j++)//here
                {
                    Vector tuple = (Vector)row.tuples.elementAt(j);
                    for(int k=0;k<tuple.size();k++)
                    {
                        String s = (String)tuple.elementAt(k);
                        if(s.equals(entity.name))
                        {
                            row.tuples.removeElementAt(j);
                            j--;
                            break;
                        }
                    }
                }
            }

            String first_tuples = c.datatable.firstTuples();
            addCategorisation(c);
            Vector same_first_tuple_concepts =
                    (Vector)datatable_hashtable.get(first_tuples);
            if (same_first_tuple_concepts!=null)
                same_first_tuple_concepts.addElement(c);
            else
            {
                same_first_tuple_concepts = new Vector();
                same_first_tuple_concepts.addElement(c);
                datatable_hashtable.put(first_tuples, same_first_tuple_concepts);
            }

            System.out.println("NEW datatable for " + c.writeDefinition() + " is:");
            System.out.println(c.datatable.toTable());
        }
    }

    /** Upgrades the entity to being a pseudo entity by adding it to the
     * list. */

    public void upgradeEntityToPseudoEntity(Entity entity)
    {

        for(int i=0;i<pseudo_entities.size();i++)
        {
            Entity current_entity = (Entity)pseudo_entities.elementAt(i);
            if((current_entity.name).equals(entity.name))
                return;
        }

        pseudo_entities.addElement(entity);
    }



    /** Downgrades the conjecture to a pseudo conjecture */

    public void downgradeConjectureToPseudoConjecture(Conjecture conjecture)
    {

        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conj = (Conjecture)conjectures.elementAt(i);
            if(conj.writeConjecture().equals(conjecture.writeConjecture()))
                conjectures.removeElementAt(i);
        }
        pseudo_conjectures.addElement(conjecture);
    }



    //addNewEntityToTheory(Entity entity, String add_domain, String timer_num)

    public void testForConjecture(TheoryConstituent tc)
    {
        System.out.println("-------- hurray!!!!!!");
    }



    /** A simple method for adding Implications to the theory. May be
     * incomplete.
     */

    public Vector addImplicationToTheory(Implication implication, Concept lh_concept, Concept rh_concept)
    {
        implication.checkSegregation(agenda);
        implications.addElement(implication);
        conjectures.addElement(implication);
        return implication.counterexamples;
    }

    /** A simple method for adding NearEquivalences to the theory. May
     * be incomplete.
     */

    public Vector addNearEquivalenceToTheory(NearEquivalence nequiv)
    {
        nequiv.checkSegregation(agenda); //may need omitting
        near_equivalences.addElement(nequiv);
        conjectures.addElement(nequiv);
        return nequiv.counterexamples;
    }


    /** A simple method for adding NearImplications to the theory. May be
     * incomplete.
     */

    public Vector addNearImplicationToTheory(NearImplication nimp)
    {
        nimp.checkSegregation(agenda);//may need omitting
        near_implications.addElement(nimp);
        conjectures.addElement(nimp);
        return nimp.counterexamples;
    }


    /** returns a vector of counterexamples to the conj
     */

    public Vector addConjectureToTheory(Conjecture conj)
    {

        if(conj instanceof NonExists)
            return (addNonExistsToTheory((NonExists)conj, ((NonExists)conj).concept));

        if(conj instanceof Equivalence)
            return (addEquivalenceToTheory((Equivalence)conj, ((Equivalence)conj).lh_concept, ((Equivalence)conj).rh_concept));

        if(conj instanceof NearEquivalence)
            return (addNearEquivalenceToTheory((NearEquivalence)conj));

        if(conj instanceof Implication)
            return (addImplicationToTheory((Implication)conj, ((Implication)conj).lh_concept, ((Implication)conj).rh_concept));

        if(conj instanceof NearImplication)
            return (addNearImplicationToTheory((NearImplication)conj));
        return (new Vector());
    }

    /** add a new concept to the theory

     */
    public void addConceptToTheory(Concept concept)
    {
        agenda.addConcept(concept,this.concepts,this);
    }


    /** generate a report of the theory and return as a String --
     * alisonp
     */

    public String theoryReport()
    {
        boolean extra_info = true;
        boolean print_lakatos_theory = true;

        //use the default weights for measuring the interestingness of the
        //conjectures and concepts for outputting the reports
        boolean use_default_weights_of_half = false; //note -- this was used on false conjectures
        if(use_default_weights_of_half)
        {
            //couldn't find measure_concept.complexity_weight = 0.5;
            measure_concept.applicability_weight = 0.5;
            measure_concept.comprehensibility_weight = 0.5;
            measure_concept.novelty_weight = 0.5;
            measure_concept.parsimony_weight = 0.5;

            measure_conjecture.applicability_weight = 0.5;
            measure_conjecture.comprehensibility_weight = 0.5;
            measure_conjecture.surprisingness_weight = 0.5;
            measure_conjecture.plausibility_weight = 0.5;
        }

        boolean use_default_weights_of_one = true;
        if(use_default_weights_of_one)
        {
            //couldn't find measure_concept.complexity_weight = 0.5;
            measure_concept.applicability_weight = 1;
            measure_concept.comprehensibility_weight = 1;
            measure_concept.novelty_weight = 1;
            measure_concept.parsimony_weight = 1;

            measure_conjecture.applicability_weight = 1;
            measure_conjecture.comprehensibility_weight = 1;
            measure_conjecture.surprisingness_weight = 1;
            measure_conjecture.plausibility_weight = 1;
        }

        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            measure_concept.measureConcept(concept, concepts, false);
        }
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            measure_conjecture.measureConjecture(conjecture, this);
        }

        double av_interestingness = averageInterestingnessOfConcepts();
        double av_applicability = averageApplicabilityOfConcepts();
        double av_complexity = averageComplexityOfConcepts();
        double av_novelty = averageNoveltyOfConcepts();
        double av_parsimony = averageParsimonyOfConcepts();
        double av_comprehensibility = averageComprehensibilityOfConcepts();
        double av_applicability_conj = averageApplicabilityOfConjectures();
        double av_comprehensibility_conj = averageComprehensibilityOfConjectures();
        double av_surprisingness_conj = averageSurprisingnessOfConjectures();
        double av_plausibility_conj = averagePlausibilityOfConjectures();
        double av_interestingness_conj = averageInterestingnessOfConjectures();
        double av_interestingness_near_conj = averageInterestingnessOfNearConjectures();

        int dp = 2;
        TheoryConstituent tc = new TheoryConstituent();

        String output = new String();
        String macro_chosen = front_end.macro_list.getSelectedItem();
        output = "\n\n\n                                REPORT                  ";
        output = output + "\n\nMacro: " + macro_chosen;
        output = output + "\n\nNum steps: " + steps_taken;
        output = output + "\n\n// Concepts //\n\n\n";

        //report on concepts
        output = output + "concept    arity    app     comp   nov     pars    comp    interest    lakatos_method"
                + "\n-------------------------------------------------------------------------------------" + "\n"
                + "average     --      "
                + tc.decimalPlaces(av_applicability, dp) +"    "
                + tc.decimalPlaces(av_comprehensibility, dp)+"    "
                + tc.decimalPlaces(av_novelty, dp) +"    "
                +tc.decimalPlaces(av_parsimony, dp)+"    "
                +tc.decimalPlaces(av_comprehensibility, dp) +"    "
                +tc.decimalPlaces(av_interestingness, dp) +"               --     "
                +"\n-------------------------------------------------------------------------------------";

        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            String space = new String();
            if(concept.id.length()==7)
                space = "    ";
            if(concept.id.length()==6)
                space = "     ";
            if(concept.id.length()==5)
                space = "      ";
            if(concept.id.length()==4)
                space = "       ";

            output = output + "\n" + concept.id + space + concept.arity + "        " + concept.decimalPlaces(concept.applicability, dp)
                    + "    " + concept.decimalPlaces(concept.comprehensibility, dp) + "    " + concept.decimalPlaces(concept.novelty, dp) + "    "
                    + concept.decimalPlaces(concept.parsimony, dp) + "    " +concept.decimalPlaces(concept.comprehensibility, dp)
                    + "    " +concept.decimalPlaces(concept.interestingness, dp) + "               " + concept.lakatos_method;
        }

        output =  output + "\n\n------------------------------------------------------------------------------------";

        //report on conjectures
        output = output + "\n\n\n\n\n// Conjectures //\n\n\n";
        output = output + "conjecture    arity    app     compr    surp    plaus   interest    lakatos_method"
                + "\n------------------------------------------------------------------------------------" + "\n"
                + "average       --       "
                + tc.decimalPlaces(av_applicability_conj, dp) +"    "
                + tc.decimalPlaces(av_comprehensibility_conj, dp)+"    "
                + tc.decimalPlaces(av_surprisingness_conj, dp) +"    "
                + tc.decimalPlaces(av_plausibility_conj, dp) +"    "
                + tc.decimalPlaces(av_interestingness_conj, dp) +"              --     "
                +"\n------------------------------------------------------------------------------------";



        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            String space1 = new String();
            String space2 = new String();
            String space3 = new String();
            String space4 = new String();
            if(conjecture.id.length()==3)
                space1 = "           ";
            if(conjecture.id.length()==2)
                space1 = "            ";
            if(conjecture.id.length()==1)
                space1 = "             ";

            if((conjecture.decimalPlaces(conjecture.applicability, dp)).length() == 4)
                space2 = "        ";
            if((conjecture.decimalPlaces(conjecture.applicability, dp)).length() == 5)
                space2 = "       ";

            if((conjecture.decimalPlaces(conjecture.comprehensibility, dp)).length() == 4)
                space3 = "    ";
            if((conjecture.decimalPlaces(conjecture.comprehensibility, dp)).length() == 5)
                space3 = "   ";

            if((conjecture.decimalPlaces(conjecture.surprisingness, dp)).length() == 4)
                space4 = "    ";
            if((conjecture.decimalPlaces(conjecture.surprisingness, dp)).length() == 5)
                space4 = "   ";


            output = output + "\n"
                    + conjecture.id + space1
                    + (int)(conjecture.arity)+ space2
                    + conjecture.decimalPlaces(conjecture.applicability, dp) + space3
                    + conjecture.decimalPlaces(conjecture.comprehensibility, dp) + space4
                    + conjecture.decimalPlaces(conjecture.surprisingness, dp) + "    "
                    + conjecture.decimalPlaces(conjecture.plausibility, dp) + "    "
                    + conjecture.decimalPlaces(conjecture.interestingness, dp) + "              "
                    + conjecture.lakatos_method;
        }

        output =  output + "\n\n------------------------------------------------------------------------------------";

        //entities
        output =  output + "\n\nMy entities are:\n";
        for(int i=0;i<entities.size();i++)
        {
            Entity entity = (Entity)entities.elementAt(i);
            if(i<entities.size()-1)
                output =  output + entity.name + ", ";
            if(i==entities.size()-1)
                output =  output + entity.name + ".";
        }

        if(pseudo_entities.isEmpty())
            output =  output + "\n\nI do not have any pseudo-entities.";
        else
        {
            output =  output + "\n\nMy pseudo-entities are:\n";
            for(int i=0;i<pseudo_entities.size();i++)
            {
                Entity entity = (Entity)pseudo_entities.elementAt(i);
                if(i<pseudo_entities.size()-1)
                    output =  output + entity.name + ", ";
                if(i==pseudo_entities.size())
                    output =  output + entity.name + ".";
            }
        }

        if(extra_info)
        {
            int lakatos_concepts = 0;
            int lakatos_conjectures = 0;
            double interestingness_lakatos_conj_so_far = 0.0;

            for(int i=0;i<concepts.size();i++)
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if(!concept.lakatos_method.equals("no"))
                    lakatos_concepts++;
            }

            for(int i=0;i<conjectures.size();i++)
            {
                Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
                if(!conjecture.lakatos_method.equals("no"))
                {
                    interestingness_lakatos_conj_so_far = interestingness_lakatos_conj_so_far + conjecture.interestingness;
                    lakatos_conjectures++;
                }
            }
            int prop_lakatos_concepts = lakatos_concepts / concepts.size();
            int prop_lakatos_conjectures = lakatos_conjectures / conjectures.size();
            double av_interestingness_lakatos_conj = interestingness_lakatos_conj_so_far/lakatos_conjectures;

            output = output + "\nNumber of concepts in the theory: " + concepts.size() + ", of which "
                    + lakatos_concepts + " were lakatos concepts";
            output = output + "\nNumber of conjectures in the theory: " + conjectures.size() + ", of which "
                    + lakatos_conjectures + " were lakatos conjectures";
            output = output + "\nNumber of near conjectures in the theory is " + numberNearConjectures();
            output = output + "\nAverage interestingness of near-conjectures is " + tc.decimalPlaces(av_interestingness_near_conj, dp);
            output = output + "\nAverage interestingness of lakatos conjectures is " + tc.decimalPlaces(av_interestingness_lakatos_conj, dp);
            output = output + "\nThe proportion of concepts produced using lakatos methods is "
                    + lakatos_concepts + "/" + concepts.size();
            output = output + "\nThe proportion of conjectures produced using lakatos methods is "
                    + lakatos_conjectures + "/" + conjectures.size();
        }

        boolean print_conj_info = false;;
        if(print_conj_info)
        {
            for(int i=0;i<conjectures.size();i++)
            {
                Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
                output = output + conjecture.printConjectureInformation(measure_conjecture);
            }
        }


        if(print_lakatos_theory)
        {
            output = output + "\n\nHere are all of my concepts, conjectures and entities which have been produced by Lakatos methods:";

            for(int i=0;i<concepts.size();i++)
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if(!(concept.lakatos_method.equals("no")))
                    output = output + "\n" + concept.writeDefinition() + "was found by " + concept.lakatos_method;
            }

            for(int i=0;i<conjectures.size();i++)
            {
                Conjecture conj = (Conjecture)conjectures.elementAt(i);
                if(!(conj.lakatos_method.equals("no")))
                    output = output + "\n" + conj.writeConjecture() + "was found by " + conj.lakatos_method;
            }

            for(int i=0;i<entities.size();i++)
            {
                Entity entity = (Entity)entities.elementAt(i);
                if(!(entity.lakatos_method.equals("no")))
                    output = output + "\n" + entity.toString() + "was found by " + entity.lakatos_method;
            }

            for(int i=0;i<pseudo_entities.size();i++)
            {
                Entity entity = (Entity)pseudo_entities.elementAt(i);
                if(!(entity.lakatos_method.equals("no")))
                    output = output + "\n" + entity.toString() + "was found by " + entity.lakatos_method;
            }

        }
        output = output + "\nThe end";
        return output;
    }


    public double averageInterestingnessOfConcepts()
    {
        double output = 0;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            output = output + concept.interestingness;
        }
        output = output/concepts.size();
        return output;
    }


    public double averageApplicabilityOfConcepts()
    {
        double output = 0;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            output = output + concept.applicability;
        }
        output = output/concepts.size();
        return output;
    }

    public double averageComprehensibilityOfConcepts()
    {
        double output = 0;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            output = output + concept.comprehensibility;
        }
        output = output/concepts.size();
        return output;
    }



    public double averageNoveltyOfConcepts()
    {
        double output = 0;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            output = output + concept.novelty;
        }
        output = output/concepts.size();
        return output;
    }


    public double averageParsimonyOfConcepts()
    {
        double output = 0;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            output = output + concept.parsimony;
        }
        output = output/concepts.size();
        return output;
    }


    public double averageComplexityOfConcepts()
    {
        double output = 0;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            output = output + concept.complexity;
        }
        output = output/concepts.size();
        return output;
    }

    public double averageInterestingnessOfConjectures()
    {
        double output = 0;
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            output = output + conjecture.interestingness;
        }
        output = output/conjectures.size();
        return output;
    }

    public double averageApplicabilityOfConjectures()
    {
        double output = 0;
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            output = output + conjecture.applicability;
        }
        output = output/conjectures.size();
        return output;
    }

    public double averageComprehensibilityOfConjectures()
    {
        double output = 0;
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            output = output + conjecture.comprehensibility;
        }
        output = output/conjectures.size();
        return output;
    }

    public double averageSurprisingnessOfConjectures()
    {
        double output = 0;
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            output = output + conjecture.surprisingness;
        }
        output = output/conjectures.size();
        return output;
    }

    public double averagePlausibilityOfConjectures()
    {
        double output = 0;
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            output = output + conjecture.plausibility;
        }
        output = output/conjectures.size();
        return output;
    }

    public double averageInterestingnessOfNearConjectures()
    {
        double output = 0;
        for(int i=0;i<near_equivalences.size();i++)
        {
            NearEquivalence conjecture = (NearEquivalence)near_equivalences.elementAt(i);
            output = output + conjecture.interestingness;
        }

        for(int i=0;i<near_implications.size();i++)
        {
            NearImplication conjecture = (NearImplication)near_implications.elementAt(i);
            output = output + conjecture.interestingness;
        }


        output = output/(near_equivalences.size() + near_implications.size());
        return output;
    }

    /** Returns the number of near conjectures in the theory*/

    public int numberNearConjectures()
    {
        int num_near_conjs = 0;
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            if(!conjecture.counterexamples.isEmpty())
            {
                System.out.println("Conjecture " + conjecture.id + ": " + conjecture.writeConjecture()
                        + " has these counterexamples\n" + conjecture.counterexamples
                        +"\nSo it is counted as a nearConjecture");
                num_near_conjs++;
            }
        }
        return num_near_conjs;
    }
}
