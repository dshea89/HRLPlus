package com.github.dshea89.hrlplus;

import java.awt.*;
import java.util.Vector;
import java.util.Hashtable;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

/** A class containing various AWT components for displaying information
 * about the theory.
 * @author Simon Colton, started 8th August 1999
 * @version 1.0
 */

public class FrontEnd implements ItemListener, ActionListener, FocusListener, Serializable
{
    /** The agent name of the HR which owns this front end.
     */

    public String my_agent_name = "";

    /** The hashtable of grep start and end positions for the concepts.
     */

    public Hashtable concept_grep_positions = new Hashtable();

    /** The hashtable of grep start and end positions for the conjectures.
     */

    public Hashtable conjecture_grep_positions = new Hashtable();

    /** The reflection object for the front end.
     */

    public Reflection reflect = new Reflection();

    /** The directory where all the input files for this theory will be found.
     */

    public String input_files_directory = "";

    /** The version number of HR at present.
     */

    public String version_number = "???";

    /** A thread object for calculating a sequence of integers.
     */

    public SequenceCalculationThread sequence_calculation_thread = new SequenceCalculationThread();

    /** The number of decimal places to output the measures to.
     */

    public int decimal_places = 2;

    /** The operating system
     */

    public String os = "unix";

    /** A text box for the garbage collection.
     */

    public Label gc_label = new Label("gc steps");
    public TextField gc_text = new TextField("100000");

    /** Pseudo-code controls.
     */

    public TextArea pseudo_code_input_text = new TextArea();
    public TextArea pseudo_code_output_text = new TextArea();
    public Button pseudo_code_run_button = new Button("Run Pseudo Code");

    /** Proof controls
     */

    public Button add_proof_tables_to_macro_button = new Button("Add to macro");

    public String[][] file_prover_data = new String[20][4];
    public String[] file_prover_headers =
            {"Try Position", "File Name", "Try Condition", "Operation Substitution"};
    public JTable file_prover_table = new JTable(file_prover_data, file_prover_headers);

    public String[][] other_prover_data = new String[20][6];
    public String[] other_prover_headers =
            {"Try Position", "Prover Name", "Try Condition", "Algebra", "Execution Parameters", "Setup Name"};
    public JTable other_prover_table = new JTable(other_prover_data, other_prover_headers);

    public String[][] algebra_data = new String[250][3];
    public String[] algebra_headers =
            {"Algebra Name", "Axioms in Algebra", "Embed Algebra"};
    public JTable algebra_table = new JTable(algebra_data, algebra_headers);

    public String[][] axiom_data = new String[250][3];
    public String[] axiom_headers =
            {"Axiom Name", "Axioms String"};
    public JTable axiom_table = new JTable(axiom_data, axiom_headers);

    /** Learning controls
     */

    public TextField gold_standard_categorisation_text = new TextField(50);
    public TextField coverage_categorisation_text = new TextField(50);
    public TextField object_types_to_learn_text = new TextField(50);
    public Checkbox segregated_search_check = new Checkbox("segregated search", false);
    public TextField segregation_categorisation_text = new TextField(50);

    /** Storing the theory controls.
     */

    public TextField store_theory_name_text = new TextField(17);
    public Button store_theory_button = new Button("Store theory");
    public Button restore_theory_button = new Button("Restore theory");
    public SortableList stored_theories_list = new SortableList();

    public Button save_concept_construction_button = new Button("Store concept");
    public TextField save_concept_construction_filename_text = new TextField(17);
    public TextField save_concept_construction_concept_id_text = new TextField(5);
    public TextField build_concept_construction_concept_id_text = new TextField(5);
    public Button build_concept_button = new Button("Build concept");
    public TextField build_concept_filename_text = new TextField(17);

    public Button save_conjecture_construction_button = new Button("Store conjecture");
    public TextField save_conjecture_construction_filename_text = new TextField(17);
    public TextField save_conjecture_construction_conjecture_id_text = new TextField(5);
    public TextField build_conjecture_construction_conjecture_id_text = new TextField(5);
    public Button build_conjecture_button = new Button("Build conjecture");
    public TextField build_conjecture_filename_text = new TextField(17);

    /** System out controls (for debugging purposes only)
     */

    public Button systemout_button = new Button("run function");
    public TextField debug_parameter_text = new TextField(25);
    public TextArea systemout_text = new TextArea();

    /** Reaction directory
     */

    public TextField reaction_name_text = new TextField(12);
    public SortableList reactions_list = new SortableList();
    public TextArea reactions_text = new TextArea();
    public Button add_reaction_button = new Button("Add reaction");
    public Button remove_reaction_button = new Button("Remove reaction");
    public Button save_reaction_button = new Button("Save reaction");
    public List reactions_added_list = new List();

    /** Puzzle generation
     */

    public TextArea puzzle_output_text = new TextArea();
    public Button puzzle_generate_button = new Button("Generate");
    public TextField puzzle_ooo_choices_size_text = new TextField("4");
    public TextField puzzle_nis_choices_size_text = new TextField("7");
    public TextField puzzle_analogy_choices_size_text = new TextField("4");

    /** Forcing a theory formation step
     */

    public TextField force_string_text = new TextField(57);
    public List force_primary_concept_list = new List();
    public List force_secondary_concept_list = new List();
    public List force_prodrule_list = new List();
    public List force_parameter_list = new List();
    public Button force_button = new Button("Force Step");
    public TextArea force_result_text = new TextArea();

    /** Report generator
     */

    public TextArea report_instructions_text = new TextArea(17,30);
    public JEditorPane report_output_text = new JEditorPane("text/html","");
    public Button execute_report_button = new Button("Execute reports");
    public Button save_report_button = new Button("Save report");
    public SortableList reports_to_execute_list = new SortableList(17);
    public SortableList reports_list = new SortableList(17);
    public TextField save_report_name_text = new TextField(17);

    /** SystemOnTPTP interface
     */

    public TextField sot_conjecture_number_text = new TextField(4);
    public TextArea sot_results_text = new TextArea();
    public Button sot_submit_button = new Button("Submit");

    /** Mathweb interface
     */

    public Label mathweb_inout_socket_label = new Label("              ");
    public Label mathweb_service_socket_label = new Label("              ");
    public Label mathweb_hostname_label = new Label("               ");
    public Checkbox use_e_in_mathweb_check = new Checkbox("E",true);
    public Checkbox use_bliksem_in_mathweb_check = new Checkbox("Bliksem",true);
    public Checkbox use_otter_in_mathweb_check = new Checkbox("Otter",true);
    public Checkbox use_spass_in_mathweb_check = new Checkbox("Spass",true);

    /** Collating statistics and drawing plots
     */

    public List statistics_type_list = new List(8);
    public SortableList statistics_choice_list = new SortableList(20);
    public SortableList statistics_subchoice_list = new SortableList(20);
    public SortableList statistics_methods_list = new SortableList(20);
    public Button statistics_plot_button = new Button("Plot");
    public Button statistics_compile_button = new Button("Compile");
    public Checkbox statistics_average_check = new Checkbox("average");
    public Checkbox statistics_seperate_graphs_check = new Checkbox("seperate graphs");
    public Checkbox statistics_count_check = new Checkbox("count values");
    public TextField statistics_calculation_text = new TextField("");
    public TextField statistics_prune_text = new TextField("");
    public TextField statistics_sort_text = new TextField("");
    public TextField statistics_output_file_text = new TextField("");

    /** Predicting values for concepts.
     */

    public Choice predict_method_choice = new Choice();
    public Checkbox predict_use_negations_check = new Checkbox("negations", false);
    public Checkbox predict_use_equivalences_check = new Checkbox("equivalences",true);
    public TextField predict_min_percent_text = new TextField("0   ");
    public TextField predict_max_steps_text = new TextField("100000");
    public Button predict_all_button = new Button("Predict all");
    public Vector predict_names_buttons_vector = new Vector();
    public Vector predict_values_texts_vector = new Vector();
    public Button predict_button = new Button("Predict");
    public Choice predict_entity_type_choice = new Choice();
    public Choice predict_input_files_choice = new Choice();
    public TextField predict_entity_text = new TextField(10);
    public List predict_explanation_list = new List(10);
    public Button predict_incremental_steps_button = new Button("Increment");

    public TextField predict_values_text1 = new TextField();
    public TextField predict_values_text2 = new TextField();
    public TextField predict_values_text3 = new TextField();
    public TextField predict_values_text4 = new TextField();
    public TextField predict_values_text5 = new TextField();
    public TextField predict_values_text6 = new TextField();
    public TextField predict_values_text7 = new TextField();
    public TextField predict_values_text8 = new TextField();
    public TextField predict_values_text9 = new TextField();
    public TextField predict_values_text10 = new TextField();

    public TextField predict_values_text11 = new TextField();
    public TextField predict_values_text12 = new TextField();
    public TextField predict_values_text13 = new TextField();
    public TextField predict_values_text14 = new TextField();
    public TextField predict_values_text15 = new TextField();
    public TextField predict_values_text16 = new TextField();
    public TextField predict_values_text17 = new TextField();
    public TextField predict_values_text18 = new TextField();
    public TextField predict_values_text19 = new TextField();
    public TextField predict_values_text20 = new TextField();

    public TextField predict_values_text21 = new TextField();
    public TextField predict_values_text22 = new TextField();
    public TextField predict_values_text23 = new TextField();
    public TextField predict_values_text24 = new TextField();
    public TextField predict_values_text25 = new TextField();
    public TextField predict_values_text26 = new TextField();
    public TextField predict_values_text27 = new TextField();
    public TextField predict_values_text28 = new TextField();
    public TextField predict_values_text29 = new TextField();
    public TextField predict_values_text30 = new TextField();

    public TextField predict_values_text31 = new TextField();
    public TextField predict_values_text32 = new TextField();
    public TextField predict_values_text33 = new TextField();
    public TextField predict_values_text34 = new TextField();
    public TextField predict_values_text35 = new TextField();
    public TextField predict_values_text36 = new TextField();
    public TextField predict_values_text37 = new TextField();
    public TextField predict_values_text38 = new TextField();
    public TextField predict_values_text39 = new TextField();
    public TextField predict_values_text40 = new TextField();

    public Button predict_names_button1 = new Button("                                ");
    public Button predict_names_button2 = new Button("                                ");
    public Button predict_names_button3 = new Button("                                ");
    public Button predict_names_button4 = new Button("                                ");
    public Button predict_names_button5 = new Button("                                ");
    public Button predict_names_button6 = new Button("                                ");
    public Button predict_names_button7 = new Button("                                ");
    public Button predict_names_button8 = new Button("                                ");
    public Button predict_names_button9 = new Button("                                ");
    public Button predict_names_button10 = new Button("                                ");

    public Button predict_names_button11 = new Button("                                ");
    public Button predict_names_button12 = new Button("                                ");
    public Button predict_names_button13 = new Button("                                ");
    public Button predict_names_button14 = new Button("                                ");
    public Button predict_names_button15 = new Button("                                ");
    public Button predict_names_button16 = new Button("                                ");
    public Button predict_names_button17 = new Button("                                ");
    public Button predict_names_button18 = new Button("                                ");
    public Button predict_names_button19 = new Button("                                ");
    public Button predict_names_button20 = new Button("                                ");

    public Button predict_names_button21 = new Button("                                ");
    public Button predict_names_button22 = new Button("                                ");
    public Button predict_names_button23 = new Button("                                ");
    public Button predict_names_button24 = new Button("                                ");
    public Button predict_names_button25 = new Button("                                ");
    public Button predict_names_button26 = new Button("                                ");
    public Button predict_names_button27 = new Button("                                ");
    public Button predict_names_button28 = new Button("                                ");
    public Button predict_names_button29 = new Button("                                ");
    public Button predict_names_button30 = new Button("                                ");

    public Button predict_names_button31 = new Button("                                ");
    public Button predict_names_button32 = new Button("                                ");
    public Button predict_names_button33 = new Button("                                ");
    public Button predict_names_button34 = new Button("                                ");
    public Button predict_names_button35 = new Button("                                ");
    public Button predict_names_button36 = new Button("                                ");
    public Button predict_names_button37 = new Button("                                ");
    public Button predict_names_button38 = new Button("                                ");
    public Button predict_names_button39 = new Button("                                ");
    public Button predict_names_button40 = new Button("                                ");

    /** A text area for holding help information.
     */

    public TextArea help_text = new TextArea(50,50);
    public SortableList help_list = new SortableList();
    public Button save_help_button = new Button("Save help");
    public TextField help_save_file_text = new TextField(50);

    /** A button to prune (cull) the population of concepts in terms of the
     * interestingness measure.
     */

    public Button cull_population_button = new Button("Cull");
    public Checkbox use_evolution_check = new Checkbox("Use evolution", false);
    public TextField evolve_population_size_text = new TextField("0");
    public TextField evolve_number_of_population_text = new TextField("0");
    public TextField cull_steps_text = new TextField("0");

    /** Whether or not actions upon the FrontEnd are being recorded.
     */

    public Choice quick_macro_choice = new Choice();
    public Checkbox record_macro_check = new Checkbox("record");
    public Button play_macro_button = new Button("Play macro");
    public Button save_macro_button = new Button("Save");
    public TextField save_macro_text = new TextField(12);
    public JEditorPane hello_text = new JEditorPane("text/html","");
    public SortableList macro_list = new SortableList(20);
    public TextArea macro_text = new TextArea(50,50);
    public TextField macro_error_text = new TextField(50);

    /** The timer inherited from the theory.
     */

    public Timer timer = new Timer();

    /** A list for the processes which are timed.
     */

    public List process_list = new List();

    /** A pointer to the main frame into which the front end components will
     * be placed.
     */

    public HR main_frame = null;

    /** A check box for whether to include ground data as axioms in
     * theorem proving.
     */

    public Checkbox use_ground_instances_in_proving_check = new Checkbox("ground in proving",false);
    public Checkbox use_entity_letter_in_proving_check = new Checkbox("entity in proving",false);

    /** A checkbox group for the proof strategy (in terms of which
     * provers are used in which order).
     */

    public CheckboxGroup proofs_group = new CheckboxGroup();
    /** A check box for whether to use the Mathweb ATP service
     * (i.e. all the provers are used)
     */

    public Checkbox use_mathweb_atp_service_check =
            new Checkbox("use mathweb atp", proofs_group, false);

    /** A check box for whether the mathweb provers are to be used until
     * one produces an answer, or until all have produced an answer
     */

    public Checkbox mathweb_atp_require_all_check = new Checkbox("require all atp", false);

    /** A check box for whether to use only Otter in the Mathweb service
     * (This may be quicker as TPTP2x does not need to be used then).
     */

    public Checkbox use_mathweb_otter_check =
            new Checkbox("use mathweb otter", proofs_group, false);

    /** A check box for whether to expand the agenda
     */

    public Checkbox expand_agenda_check = new Checkbox("expand agenda",true);

    /** A check box for whether to make equivalence conjectures by finding concepts
     * with equal examples to the new concept.
     */

    public Checkbox make_equivalences_from_equality_check = new Checkbox("equiv from equality",true);

    /** A check box for whether to make equivalences from combination,
     * e.g., c <-> d and c <-> e gives d <-> e.
     */

    public Checkbox make_equivalences_from_combination_check = new Checkbox("equiv from combination",false);

    /** A check box for whether to make non-existence conjectures or not when
     * a concept has no examples.
     */

    public Checkbox make_non_exists_from_empty_check = new Checkbox("non-exists from empty",true);

    /** A check box for whether to extract implicates from subsumption
     * implication conjectures.
     */

    public Checkbox make_implicates_from_subsumes_check = new Checkbox("implicates from subsumes",false);

    /** A text field for forcing conjectures.
     */
    public TextField force_conjecture_text = new TextField(1);

    /** A check box for whether to store all conjectures made.
     */

    public Checkbox store_all_conjectures_check = new Checkbox("store all conjectures", false);
    public TextField store_all_conjectures_text = new TextField(17);

    /** A check box for whether to keep equivalence conjectures or not
     */

    public Checkbox keep_equivalences_check = new Checkbox("keep equivalences",true);
    public TextField keep_equivalences_file_text = new TextField(17);
    public TextField keep_equivalences_condition_text = new TextField(17);

    public Checkbox require_proof_in_subsumption_check = new Checkbox("require proof (subsume)", true);

    /** A check box for whether to keep old steps or not.
     */

    public Checkbox keep_steps_check = new Checkbox("keep steps",true);

    /** A check box for whether to keep non-existence conjectures or not
     */

    public Checkbox keep_non_exists_check = new Checkbox("keep non-exists",true);
    public TextField keep_non_exists_file_text = new TextField(17);
    public TextField keep_non_exists_condition_text = new TextField(17);

    /** A check box for whether or not to make implicate conjectures from the equivalence
     * conjectures.
     */

    public Checkbox extract_implications_from_equivalences_check = new Checkbox("implication from equiv",false);

    /** A check box for whether or not to make implicate conjectures from the non-existence
     * conjectures.
     */

    public Checkbox extract_implicates_from_non_exists_check = new Checkbox("implicate from non-exists",false);

    /** Whether or not to keep implication conjectures which are made.
     */

    public Checkbox keep_implications_check = new Checkbox("keep implications",true);
    public TextField keep_implications_file_text = new TextField(17);
    public TextField keep_implications_condition_text = new TextField(17);

    /** Whether or not to keep implicate conjectures which are made.
     */

    public Checkbox keep_implicates_check = new Checkbox("keep implicates",true);
    public TextField keep_implicates_file_text = new TextField(17);
    public TextField keep_implicates_condition_text = new TextField(17);

    /** Whether or not to keep prime implicate conjectures which are made.
     */

    public Checkbox keep_prime_implicates_check = new Checkbox("keep prime implicates",true);
    public TextField keep_prime_implicates_file_text = new TextField(17);
    public TextField keep_prime_implicates_condition_text = new TextField(17);

    /** A check box for whether or not to make implicate conjectures from the non-existence
     * conjectures.
     */

    public Checkbox make_implications_from_subsumptions_check = new Checkbox("implication from subsume",false);

    /** A check box for whether or not to make implicate conjectures from the non-existence
     * conjectures.
     */

    public Checkbox extract_prime_implicates_from_implicates_check = new Checkbox("implicate from prime",false);

    /** A check box for whether or not to make prime implicate conjectures from the implicate
     * conjectures.
     */

    public Checkbox extract_implicates_from_equivalences_check = new Checkbox("implicate from equivalences",false);

    /** alisonp added LAKATOS and GROUPAGENDA buttons */

    public JTabbedPane lakatos_tabbed_pane = new JTabbedPane();

    /** The search list for agent output. */

    public List agent_search_output_list = new List(10);

    /** A button for starting an agent search session. */

    public Button agent_search_button = new Button("Search");

    /** A textfield for the agent search string. */

    public TextField agent_search_text = new TextField(30);

    /** Check boxes for whether to use individual methods */

    public Checkbox use_surrender_check = new Checkbox("surrender",false);
    public Checkbox use_strategic_withdrawal_check = new Checkbox("strategic withdrawal",false);
    public Checkbox use_piecemeal_exclusion_check = new Checkbox("piecemeal exclusion",false);
    public Checkbox use_monster_barring_check = new Checkbox("monster barring",false);
    public Checkbox use_monster_adjusting_check = new Checkbox("monster adjusting",false);
    public Checkbox use_lemma_incorporation_check = new Checkbox("lemma incorporation",false);
    public Checkbox use_proofs_and_refutations_check = new Checkbox("proofs and refutations",false);

    public Checkbox teacher_requests_conjecture_check = new Checkbox("conjecture",false);
    public Checkbox teacher_requests_equivalence_check = new Checkbox("equivalence",false);
    public Checkbox teacher_requests_implication_check = new Checkbox("implication",false);
    public Checkbox teacher_requests_nonexists_check = new Checkbox("nonexists",false);

    public Checkbox teacher_requests_nearimplication_check = new Checkbox("nearimplication",false);
    public Checkbox teacher_requests_nearequivalence_check = new Checkbox("nearequivalence",false);


    /** Check boxes for how to use the discussion */
    public Checkbox num_independent_steps_check = new Checkbox("length independent work phase",true);
    public TextField  num_independent_steps_value_text = new TextField("10");

    public Checkbox max_num_independent_work_stages_check = new Checkbox("number independent work phases",true);
    public Label max_num_independent_work_stages_value_label = new Label("number independent work phases");
    public TextField  max_num_independent_work_stages_value_text = new TextField("10");


    public Checkbox threshold_to_add_conj_to_theory_check = new Checkbox("t'hold to add conj to theory",false);
    public Label threshold_to_add_conj_to_theory_label = new Label("t'hold to add conj to theory");
    public TextField threshold_to_add_conj_to_theory_value_text = new TextField("0.0");

    public Checkbox threshold_to_add_concept_to_theory_check = new Checkbox("t'hold to add concept to theory",false);
    public Label threshold_to_add_concept_to_theory_label = new Label("t'hold to add concept to theory");
    public TextField threshold_to_add_concept_to_theory_value_text = new TextField("0.0");
    /** Check boxes for whether to use communal methods */

    public Checkbox use_communal_piecemeal_exclusion_check = new Checkbox("communal piecemeal exclusion",false);


    // /** Check boxes for how the discussion should proceed */

//   public Checkbox group_agenda_best_first_responses_check = new Checkbox("group best first responses",false);
//   public Checkbox group_agenda_best_first_agenda_check = new Checkbox("group best first agenda",false);
//   public Checkbox group_agenda_combine_responses_check = new Checkbox("group combine responses",false);
//   public Checkbox group_agenda_depth_first_check = new Checkbox("group depth first",true);
//   public Checkbox group_agenda_breadth_first_check = new Checkbox("group breadth first",false);

//   /** end alisonp */

    /** Check boxes for surrender variables */

    public Checkbox number_modifications_check = new Checkbox("number modifications",false);
    public Checkbox interestingness_threshold_check = new Checkbox("interestingness threshold",false);
    public Checkbox compare_average_interestingness_check = new Checkbox("compare average interestingness",false);
    public Checkbox plausibility_threshold_check = new Checkbox("plausibility threshold",false);
    public Checkbox domain_application_threshold_check = new Checkbox("domain of application threshold",false);

    /** Check boxes for exception-barring variables */
    public Checkbox use_counterexample_barring_check = new Checkbox("use counterexample barring",false);
    //public Checkbox use_piecemeal_exclusion_check = new Checkbox("use piecemeal exclusion",false);
    //public Checkbox use_strategic_withdrawal_check = new Checkbox("use strategic withdrawal",false);


    /** A textbox for the number of modifications.
     */
    public Label modifications_value_label = new Label("num modifications");
    public TextField  modifications_value_text = new TextField("0");

    /** A textbox for the interestingness threshold.
     */

    public Label interestingness_threshold_value_label = new Label("int t'hold");
    public TextField interestingness_threshold_value_text = new TextField("0.0");


    /** A textbox for the plausibility threshold.
     */

    public Label plausibility_threshold_value_label = new Label("plaus t'hold");
    public TextField plausibility_threshold_value_text = new TextField("0.0");


    /** A textbox for the domain of application threshold.
     */

    public Label domain_app_threshold_value_label = new Label("dom appln t'hold");
    public TextField domain_app_threshold_value_text = new TextField("0.0");


    /** Check boxes for monster-barring variables */

    public Checkbox use_breaks_conj_under_discussion_check = new Checkbox("use breaks conj",false);
    public Checkbox accept_strictest_check = new Checkbox("accept strictest",false);
    public Checkbox use_percentage_conj_broken_check = new Checkbox("use % of conjs broken",false);
    public Checkbox use_culprit_breaker_check = new Checkbox("use culprit breaker",false);
    public Checkbox use_culprit_breaker_on_conj_check = new Checkbox("use culprit breaker on one conj",false);
    public Checkbox use_culprit_breaker_on_all_check = new Checkbox("use culprit breaker on all conj",false);

    /** A textbox for the monster barring culprit min.
     */
    public Label monster_barring_culprit_min_label = new Label("monster barring culprit min");
    public TextField monster_barring_culprit_min_text = new TextField("0.0");


    public Checkbox monster_barring_type_check = new Checkbox("monster barring type",true);


    /** A textbox for the monster barring type
     */
    public Label monster_barring_type_label = new Label("monster barring type");
    public TextField monster_barring_type_text = new TextField("vaguetospecific");

    /** A textbox for the monster barring min .
     */

    public Label monster_barring_min_label = new Label("monster barring min");
    public TextField monster_barring_min_text = new TextField("0");


    /** end alisonp */


    /** The start button inherited from the top label.
     */

    public Button start_button = new Button("Load");

    /** The stop button inherited from the top label.
     */

    public Button stop_button = new Button("Stop");

    /** The step button inherited from the top label.
     */

    public Button step_button = new Button("Step");

    /** The label displaying what HR is doing at the moment.
     */

    public Label status_label = new Label("Waiting");

    /** A button for setting all the concept measure weights to zero
     */

    public Button reset_concept_weights_button = new Button("Reset");

    /** A button for setting all the conjecture measure weights to zero
     */

    public Button reset_conjecture_weights_button = new Button("Reset");

    /** The text_box for the concept applicability weight in the weighted sum.
     */

    public TextField concept_applicability_weight_text = new TextField("0.0");
    public Label concept_applicability_weight_label = new Label("applicability");

    /** The text_box for the concept coverage weight in the weighted sum.
     */

    public TextField concept_coverage_weight_text = new TextField("0.0");
    public Label concept_coverage_weight_label = new Label("coverage");

    /** The text_box for the conjecture applicability weight in the weighted sum.
     */

    public TextField conjecture_applicability_weight_text = new TextField("0.0");
    public Label conjecture_applicability_weight_label = new Label("applicability");

    /** The text_box for the concept comprehensibility weight in the weighted sum.
     */

    public TextField concept_comprehensibility_weight_text = new TextField("0.0");
    public Label concept_comprehensibility_weight_label = new Label("comprehensibility");

    /** The text_box for the concept equivalence conjecture score weight in the weighted sum.
     */

    public TextField concept_equiv_conj_score_weight_text = new TextField("0.0");
    public Label concept_equiv_conj_score_weight_label = new Label("equiv score/num");
    public TextField concept_equiv_conj_num_weight_text = new TextField("0.0");

    /** The text_box for the concept prime implicate conjecture score weight in the weighted sum.
     */

    public TextField concept_pi_conj_score_weight_text = new TextField("0.0");
    public Label concept_pi_conj_score_weight_label = new Label("pi score/num");
    public TextField concept_pi_conj_num_weight_text = new TextField("0.0");

    /** The text_box for the concept non-existence conjecture score weight in the weighted sum.
     */

    public TextField concept_ne_conj_score_weight_text = new TextField("0.0");
    public Label concept_ne_conj_score_weight_label = new Label("nex score/num");
    public TextField concept_ne_conj_num_weight_text = new TextField("0.0");

    /** The text_box for the concept implicate conjecture score weight in the weighted sum.
     */

    public TextField concept_imp_conj_score_weight_text = new TextField("0.0");
    public Label concept_imp_conj_score_weight_label = new Label("imp score/num");
    public TextField concept_imp_conj_num_weight_text = new TextField("0.0");

    /** The text_box for the conjecture comprehensibility weight in the weighted sum.
     */

    public TextField conjecture_comprehensibility_weight_text = new TextField("0.0");
    public Label conjecture_comprehensibility_weight_label = new Label("comprehensibility");

    /** The text_box for the cross domain weight in the weighted sum.
     */

    public TextField concept_cross_domain_weight_text = new TextField("0.0");
    public Label concept_cross_domain_weight_label = new Label("cross domain");

    /** The text_box for the highlight weight in the weighted sum.
     */

    public TextField concept_highlight_weight_text = new TextField("0.0");
    public Label concept_highlight_weight_label = new Label("highlight");

    /** The text_box for the novelty weight in the weighted sum.
     */

    public TextField concept_novelty_weight_text = new TextField("0.0");
    public Label concept_novelty_weight_label = new Label("novelty");

    /** The text_box for the parent weight in the weighted sum.
     */

    public TextField concept_parent_weight_text = new TextField("0.0");
    public Label concept_parent_weight_label = new Label("parents");

    /** The text_box for the children weight in the weighted sum.
     */

    public TextField concept_children_weight_text = new TextField("0.0");
    public Label concept_children_weight_label = new Label("children");

    /** The text_box for the parsimony weight in the weighted sum.
     */

    public TextField concept_parsimony_weight_text = new TextField("0.0");
    public Label concept_parsimony_weight_label = new Label("parsimony");

    /** The text_box for the predictive power weight in the weighted sum.
     */

    public TextField concept_predictive_power_weight_text = new TextField("0.0");
    public Label concept_predictive_power_weight_label = new Label("pred power");

    /** The text_box for the productivity weight in the weighted sum.
     */

    public TextField concept_productivity_weight_text = new TextField("0.0");
    public TextField concept_default_productivity_text = new TextField("0.0");
    public Label concept_productivity_weight_label = new Label("productivity/def");

    /** The text_box for the steps involved weight in the weight sum.
     */

    public TextField concept_development_steps_num_weight_text = new TextField("0.0");
    public Label concept_development_steps_num_weight_label = new Label("devel steps");

    /** The text_box for the conjecture surprisingness weight in the weighted sum.
     */

    public TextField conjecture_surprisingness_weight_text = new TextField("0.0");
    public Label conjecture_surprisingness_weight_label = new Label("surprisingness");


    /** The text_box for the conjecture plausibility weight in the weighted sum.
     */

    public TextField conjecture_plausibility_weight_text = new TextField("0.0");
    public Label conjecture_plausibility_weight_label = new Label("plausibility");

    /** The text_box for the variety weight in the weighted sum.
     */

    public TextField concept_variety_weight_text = new TextField("0.0");
    public Label concept_variety_weight_label = new Label("variety");

    /** The text_box for the invariance weight in the weighted sum.
     */

    public TextField concept_invariance_weight_text = new TextField("0.0");
    public Label concept_invariance_weight_label = new Label("invariance");

    /** The checkbox for whether or not to use the forward look ahead mechanism.
     */

    public Checkbox use_forward_lookahead_check = new Checkbox("forward look ahead");

    /** The checkbox for whether to normalise interestingness measures.
     */

    public Checkbox normalise_concept_measures_check = new Checkbox("Normalise", true);

    /** The text_box for the discrimination weight in the weighted sum.
     */

    public TextField concept_discrimination_weight_text = new TextField("0.0");
    public Label concept_discrimination_weight_label = new Label("discrimination");

    /** The text_box for the positive applicability weight in the weighted sum.
     */

    public TextField concept_positive_applicability_weight_text = new TextField("0.0");
    public Label concept_positive_applicability_weight_label = new Label("pos applic");

    /** The text_box for the negative applicability weight in the weighted sum.
     */

    public TextField concept_negative_applicability_weight_text = new TextField("0.0");
    public Label concept_negative_applicability_weight_label = new Label("neg applic");

    /** Whether or not to measure the concepts.
     */

    public Checkbox measure_concepts_check = new Checkbox("",true);
    public Button measure_concepts_button = new Button("Measure concepts");

    /** Whether or not to measure the conjectures..
     */

    public Checkbox measure_conjectures_check = new Checkbox("",true);
    public Button measure_conjectures_button = new Button("Measure conjectures");

    /** Whether or not to update the frontend automatically.
     */

    public Checkbox update_front_end_check = new Checkbox("",true);
    public Button update_front_end_button = new Button("Update Front End");

    /** A textbox for the complexity limit. Default is 6.
     */

    public Label complexity_label = new Label("complexity limit:");
    public TextField complexity_text = new TextField("6",3);

    /** A choice of what is required from the theory (e.g. steps, concepts,
     * seconds, etc.)
     */

    public Choice required_choice = new Choice();
    public TextField required_text = new TextField("1000");

    /** A textbox for the number of seconds otter is allowed.
     */

    public Label otter_time_limit_label = new Label("otter time:");
    public TextField otter_time_limit_text = new TextField("10");

    /** A textbox for the cutoff point for near-equivalences.
     */

    public Label near_equivalence_percent_label = new Label("near equiv %");
    public TextField near_equivalence_percent_text = new TextField("100");

    /** A textbox for the cutoff point for near-implications. alisonp
     */

    public Label near_implication_percent_label = new Label("near imp %");
    public TextField near_implication_percent_text = new TextField("100");

    /** A textbox for the minimum score before which interestingness is set to zero.
     */

    public Label interestingness_zero_min_label = new Label("interest to 0 min %");
    public TextField interestingness_zero_min_text = new TextField("0");

    /** A textbox for the number of steps to wait before a best first search.
     */

    public Label best_first_delay_label = new Label("best first delay");
    public TextField best_first_delay_text = new TextField("0");

    /** A checkbox for whether to use counterexamplebarring for improved
     * conjecture making.
     */

    public Checkbox use_counter_barring_check = new Checkbox("counter barring");
    public Checkbox use_concept_barring_check = new Checkbox("concept barring");
    public TextField counterexample_barring_num_text = new TextField(4);
    public TextField concept_barring_num_text = new TextField(4);

    /** A checkbox for whether to use the applicability conjecture
     * making method.
     */

    public Checkbox use_applicability_conj_check = new Checkbox("applicability");
    public TextField applicability_conj_num_text = new TextField(4);

    /** A textbox for the agenda limit. Default is 100000.
     */

    public Label agenda_limit_label = new Label("agenda limit:");
    public TextField agenda_limit_text = new TextField("100000");

    /** A textbox for the arity limit. Default is 3.
     */

    public Button arity_limit_button = new Button("arity limit:");
    public TextField arity_limit_text = new TextField("3");

    /** A textbox for the possible parameters for the ArithmeticB PR //friday
     */

    public TextField arithmeticb_operators_text = new TextField("+*d");
    public Label arithmeticb_operators_label = new Label("arithmeticb operators");



    /** A textbox for the tuple product limit. Default is 1000000
     */

    public Label tuple_product_limit_label = new Label("t-prod limit");
    public TextField tuple_product_limit_text = new TextField("1000000");

    /** A textbox for the compose time limit. Default is 10 seconds.
     */

    public Label compose_time_limit_label = new Label("comp time limit");
    public TextField compose_time_limit_text = new TextField("10");

    /** A checkboxgroup for the type of search.
     */

    public CheckboxGroup search_group = new CheckboxGroup();

    /** A checkbox for whether to find the parameterisations for the
     * split production rule by looking at what is in the table, or
     * what is in the allowed values.
     * @see Split
     */

    public Checkbox use_split_empirically_for_learning_check = new Checkbox("split empirical",true);

    /** A checkbox for whether to find the parameterisations for the
     * split production rule by looking at what is in the table, or
     * what is in the allowed values, specifically for when learning a concept.
     * @see Split
     */

    public Checkbox use_split_empirically_check = new Checkbox("split empirical",false);

    /** A checkbox for whether near-equivalences should be made.
     */

    public Checkbox make_near_equivalences_check = new Checkbox("make near-equivs",false);

    /** A checkbox for whether near-implications should be made. alisonp
     */

    public Checkbox make_near_implications_check = new Checkbox("make near-imps",false);

    /** A checkbox for whether simpler definitions should replace
     * less simple ones
     */

    public Checkbox substitute_definitions_check = new Checkbox("substitute definitions",false);

    /** A checkbox for whether HR should do a depth first exhaustive search.
     */

    public Checkbox depth_first_check = new Checkbox("depth first", search_group, false);

    /** A checkbox for whether HR should do a breadth first exhaustive search.
     */

    public Checkbox breadth_first_check = new Checkbox("breadth first", search_group, true);

    /** A checkbox for whether HR should use interestingness to sort
     * the concepts.
     */

    public Checkbox weighted_sum_check = new Checkbox("weighted_sum",search_group,false);
    public Checkbox keep_worst_check = new Checkbox("keep worst",search_group,false);
    public Checkbox keep_best_check = new Checkbox("keep best",search_group,false);

    /** A checkbox for whether HR should do a random search.
     */

    public Checkbox random_check = new Checkbox("random", search_group, false);

    /** A checkbox for whether HR allows non-obvious subobject overlap.
     */

    public Checkbox subobject_overlap_check = new Checkbox("subobject overlap",true);

    /** A list box for the name of the domain to work in.
     */

    public Label domain_label = new Label("domains:");
    public SortableList domain_list = new SortableList(0, false);
    public Button domain_none_button = new Button("None");
    public Button domain_default_button = new Button("Default");

    /** A list box for the initial entities in a domain.
     */

    public Label initial_entity_label = new Label("entities:");
    public List initial_entity_list = new List();
    public Button initial_entity_none_button = new Button("None");
    public Button initial_entity_all_button = new Button("All");

    /** Choice box for the list of statistics.
     * We provide three, because it is often interesting to see
     * more than one statistic on screen.
     */

    public Label statistics_label1 = new Label("           ");
    public Label statistics_label2 = new Label("           ");
    public Label statistics_label3 = new Label("           ");
    public Choice statistics_choice1 = new Choice();
    public Choice statistics_choice2 = new Choice();
    public Choice statistics_choice3 = new Choice();

    /** A list box for the highlighted concepts.
     */

    public Label highlight_label = new Label("highlight:");
    public List highlight_list = new List();
    public Button highlight_none_button = new Button("None");
    public Button highlight_all_button = new Button("All");

    /** A list box for the counterexamples.
     */

    public Label counterexample_label = new Label("counterexamples:");
    public List counterexample_list = new List();
    public Button counterexample_none_button = new Button("None");
    public Button counterexample_all_button = new Button("All");

    /** A list box for the initial concepts.
     */

    public Label initial_concepts_label = new Label("initial concepts:");
    public List initial_concepts_list = new List();
    public Button initial_concepts_none_button = new Button("None");
    public Button initial_concepts_all_button = new Button("All");

    /** Checkboxes for the production rules.
     */

    public Checkbox arithmeticb_check = new Checkbox("arithmeticb", true);//friday
    public Checkbox compose_check = new Checkbox("compose", true);
    public Checkbox disjunct_check = new Checkbox("disjunct", true);
    public Checkbox embed_algebra_check = new Checkbox("embed_algebra", true);
    public Checkbox embed_graph_check = new Checkbox("embed_graph", true);
    public Checkbox entity_disjunct_check = new Checkbox("entity_disjunct", true);
    public Checkbox exists_check = new Checkbox("exists", true);
    public Checkbox equal_check = new Checkbox("equal", true);
    public Checkbox forall_check = new Checkbox("forall", true);
    public Checkbox match_check = new Checkbox("match", true);
    public Checkbox negate_check = new Checkbox("negate", true);
    public Checkbox record_check = new Checkbox("record", true);
    public Checkbox size_check = new Checkbox("size", true);
    public Checkbox split_check = new Checkbox("split", true);

    /** Textboxes for the arity limit for the production rules
     */

    public TextField compose_arity_limit_text = new TextField("4");
    public TextField disjunct_arity_limit_text = new TextField("4");
    public TextField embed_graph_arity_limit_text = new TextField("4");
    public TextField embed_algebra_arity_limit_text = new TextField("4");
    public TextField exists_arity_limit_text = new TextField("4");
    public TextField entity_disjunct_arity_limit_text = new TextField("4");
    public TextField arithmeticb_arity_limit_text = new TextField("4");//friday
    public TextField equal_arity_limit_text = new TextField("4");
    public TextField forall_arity_limit_text = new TextField("4");
    public TextField match_arity_limit_text = new TextField("4");
    public TextField negate_arity_limit_text = new TextField("4");
    public TextField record_arity_limit_text = new TextField("4");
    public TextField size_arity_limit_text = new TextField("4");
    public TextField split_arity_limit_text = new TextField("4");

    /** Textboxes for the level of the production rules
     */

    public TextField compose_tier_text = new TextField("0");
    public TextField disjunct_tier_text = new TextField("0");
    public TextField embed_graph_tier_text = new TextField("0");
    public TextField embed_algebra_tier_text = new TextField("0");
    public TextField exists_tier_text = new TextField("0");
    public TextField entity_disjunct_tier_text = new TextField("0");
    public TextField arithmeticb_tier_text = new TextField("0");//friday
    public TextField equal_tier_text = new TextField("0");
    public TextField forall_tier_text = new TextField("0");
    public TextField match_tier_text = new TextField("0");
    public TextField negate_tier_text = new TextField("0");
    public TextField record_tier_text = new TextField("0");
    public TextField size_tier_text = new TextField("0");
    public TextField split_tier_text = new TextField("0");

    /** A list box for the split values.
     */

    public Label split_values_label = new Label("split values:");
    public List split_values_list = new List();
    public Button split_values_none_button = new Button("None");
    public Button split_values_all_button = new Button("All");

    /** A list box for the ancestors of the chosen concept.
     */

    public List ancestor_list = new List();

    /** A list box for the children of the chosen concept.
     */

    public List children_list = new List();

    /** A Button for computing near-equivalences.
     */

    public Button find_extra_conjectures_button = new Button("Extra conjs");

    /** The calculate button.
     */

    public Button calculate_button = new Button("Calculate");
    public TextField calculate_entity1_text = new TextField("");
    public TextField calculate_entity2_text = new TextField("");
    public TextField calculate_output_text = new TextField("");

    /** The conjecture checking.
     */

    public Button test_conjecture_button = new Button("Test");
    public TextField test_conjecture_entity1_text = new TextField("lh test");
    public TextField test_conjecture_entity2_text = new TextField("rh test");
    public TextField test_conjecture_output_text = new TextField("output from test...      ");
    public Button add_counterexamples_found_from_testing_button = new Button("Add");

    /** A list for the agenda.
     */

    public List agenda_list = new List();
    public Button update_agenda_button = new Button("Update");
    public Button show_forced_steps_button = new Button("Show Forced");
    public Checkbox auto_update_agenda = new Checkbox("auto update", false);
    public TextField auto_update_text = new TextField("20");
    public List live_ordered_concept_list = new List();
    public List all_ordered_concept_list = new List();
    public Checkbox not_allowed_agenda_check = new Checkbox("not allowed",false);
    public TextField current_step_text = new TextField("                                         ");
    public TextField agenda_concept_text = new TextField("");

    /** A vector for storing initial concept ids.
     */

    public Vector ids = new Vector();

    /** A vector of information to display.
     */

    public Vector statistics = new Vector();

    /** The set of entities inherited from the theory.
     */

    public Vector entities = new Vector();

    /** The set of concepts inherited from the theory.
     */

    public Vector concepts = new Vector();

    /** The set of conjectures inherited from the theory.
     */

    public Vector conjectures = new Vector();

    /** The Reader inherited from the theory
     */

    Read reader = new Read();

    /** The agenda inherited from the theory.
     */

    public Agenda agenda = new Agenda();

    /** The set of equivalece conjectures from the theory
     */

    public Vector equivalences = new Vector();

    /** The set of non-existence conjectures from the theory
     */

    public Vector non_existences = new Vector();

    /** The set of implicates conjectures from the theory
     */

    public Vector implicates = new Vector();

    /** A text field for details about particular concepts.
     */

    public JEditorPane concept_text_box = new JEditorPane("text/html","");

    /** A text field for details about particular conjectures.
     */

    public JEditorPane conjecture_text_box = new JEditorPane("text/html","");

    /** A list for the entities in the theory.
     */

    public List entity_list = new List();
    public Button percent_categorisation_button = new Button("%Cat");
    public TextField percent_categorisation_text = new TextField();
    public TextField percent_categorisation_percent_text = new TextField(50);
    public Button percent_up_button = new Button(">");
    public Button percent_down_button = new Button("<");

    /** A list for the ways of sorting the entities in the theory.
     */

    public List entity_sorting_list = new List();

    /** A list for the ways of formatting the entities in the theory.
     */

    public List entity_formatting_list = new List();

    /** A list for the ways of pruning the entities in the theory.
     */

    public List entity_pruning_list = new List();

    /** A text box for viewing the entities in the theory.
     */

    public JEditorPane entity_text_box = new JEditorPane("text/html","");

    /** A button for drawing the construction history of concepts.
     */

    public Button draw_concept_construction_history_button = new Button("Draw graph");

    /** A button for drawing the construction history of conjectures.
     */

    public Button draw_conjecture_construction_history_button = new Button("Draw graph");

    /** A button for attempting again to prove (open) conjectures in a theory.
     */

    public Button re_prove_all_button = new Button("Re-prove all");

    /** A button for adding a conjecture as an axiom.
     */

    public Button add_conjecture_as_axiom_button = new Button("Add as axiom");

    /** A button for removing a conjecture as an axiom.
     */

    public Button remove_conjecture_as_axiom_button = new Button("Remove as axiom");

    /** A list for the concepts in the theory.
     */

    public SortableList concept_list = new SortableList();
    public Label concept_list_number = new Label("");
    public TextField grep_concept_text = new TextField();
    public Button grep_concept_button = new Button("Grep");
    public Button restore_concept_button = new Button("Restore");
    public Button condition_concept_button = new Button("Condition");
    public TextField condition_concept_text = new TextField();

    /** A list for the conjectures in the theory.
     */

    public SortableList conjecture_list = new SortableList();
    public List parent_conjectures_list = new List();
    public List child_conjectures_list = new List();
    public Label conjecture_list_number = new Label("");
    public Button grep_conjecture_button = new Button("Grep");
    public Button restore_conjecture_button = new Button("Restore");
    public TextField grep_conjecture_text = new TextField();
    public Button condition_conjecture_button = new Button("Condition");
    public TextField condition_conjecture_text = new TextField();

    /** A list for the ways of sorting the concepts in the concept list.
     */

    public List concept_sorting_list = new List();

    /** A list for the ways of pruning the concepts in the concept list.
     */

    public List concept_pruning_list = new List();

    /** A list for the attributes of concepts to display.
     */

    public List concept_formatting_list = new List();

    /** A list for the ways of sorting the conjectures in the concept list.
     */

    public List conjecture_sorting_list = new List();

    /** A list for the ways of pruning the conjectures in the concept list.
     */

    public List conjecture_pruning_list = new List();

    /** The user-chosen categorisation.
     */

    /** A list for the attributes of conjectures to display.
     */

    public List conjecture_formatting_list = new List();

    public Categorisation user_chosen_categorisation = null;

    /** Initialisation of the Front End
     */

    public void init()
    {
        concept_list.sort_items = false;
        conjecture_list.sort_items = false;
        statistics_subchoice_list.setMultipleMode(true);
        statistics_choice_list.setMultipleMode(true);
        entity_formatting_list.setMultipleMode(true);
        conjecture_formatting_list.setMultipleMode(true);
        concept_formatting_list.setMultipleMode(true);
        reports_to_execute_list.setMultipleMode(true);
        highlight_list.setMultipleMode(true);
        concept_pruning_list.setMultipleMode(true);
        conjecture_pruning_list.setMultipleMode(true);
        initial_concepts_list.setMultipleMode(true);
        split_values_list.setMultipleMode(true);
        initial_entity_list.setMultipleMode(true);
        counterexample_list.setMultipleMode(true);

        status_label.setAlignment(Label.LEFT);
        predict_entity_type_choice.add("                          ");
        predict_method_choice.add("bayesian average");
        predict_method_choice.add("max near-equiv");
        predict_method_choice.add("average near-equiv");
        predict_method_choice.add("max near-imp");//alisonp
        predict_method_choice.add("average near-imp");//alisonp

        predict_names_buttons_vector.removeAllElements();
        predict_values_texts_vector.removeAllElements();

        predict_names_buttons_vector.addElement(predict_names_button1);
        predict_names_buttons_vector.addElement(predict_names_button2);
        predict_names_buttons_vector.addElement(predict_names_button3);
        predict_names_buttons_vector.addElement(predict_names_button4);
        predict_names_buttons_vector.addElement(predict_names_button5);
        predict_names_buttons_vector.addElement(predict_names_button6);
        predict_names_buttons_vector.addElement(predict_names_button7);
        predict_names_buttons_vector.addElement(predict_names_button8);
        predict_names_buttons_vector.addElement(predict_names_button9);
        predict_names_buttons_vector.addElement(predict_names_button10);

        predict_names_buttons_vector.addElement(predict_names_button11);
        predict_names_buttons_vector.addElement(predict_names_button12);
        predict_names_buttons_vector.addElement(predict_names_button13);
        predict_names_buttons_vector.addElement(predict_names_button14);
        predict_names_buttons_vector.addElement(predict_names_button15);
        predict_names_buttons_vector.addElement(predict_names_button16);
        predict_names_buttons_vector.addElement(predict_names_button17);
        predict_names_buttons_vector.addElement(predict_names_button18);
        predict_names_buttons_vector.addElement(predict_names_button19);
        predict_names_buttons_vector.addElement(predict_names_button20);

        predict_names_buttons_vector.addElement(predict_names_button21);
        predict_names_buttons_vector.addElement(predict_names_button22);
        predict_names_buttons_vector.addElement(predict_names_button23);
        predict_names_buttons_vector.addElement(predict_names_button24);
        predict_names_buttons_vector.addElement(predict_names_button25);
        predict_names_buttons_vector.addElement(predict_names_button26);
        predict_names_buttons_vector.addElement(predict_names_button27);
        predict_names_buttons_vector.addElement(predict_names_button28);
        predict_names_buttons_vector.addElement(predict_names_button29);
        predict_names_buttons_vector.addElement(predict_names_button30);

        predict_names_buttons_vector.addElement(predict_names_button31);
        predict_names_buttons_vector.addElement(predict_names_button32);
        predict_names_buttons_vector.addElement(predict_names_button33);
        predict_names_buttons_vector.addElement(predict_names_button34);
        predict_names_buttons_vector.addElement(predict_names_button35);
        predict_names_buttons_vector.addElement(predict_names_button36);
        predict_names_buttons_vector.addElement(predict_names_button37);
        predict_names_buttons_vector.addElement(predict_names_button38);
        predict_names_buttons_vector.addElement(predict_names_button39);
        predict_names_buttons_vector.addElement(predict_names_button40);

        predict_values_texts_vector.addElement(predict_values_text1);
        predict_values_texts_vector.addElement(predict_values_text2);
        predict_values_texts_vector.addElement(predict_values_text3);
        predict_values_texts_vector.addElement(predict_values_text4);
        predict_values_texts_vector.addElement(predict_values_text5);
        predict_values_texts_vector.addElement(predict_values_text6);
        predict_values_texts_vector.addElement(predict_values_text7);
        predict_values_texts_vector.addElement(predict_values_text8);
        predict_values_texts_vector.addElement(predict_values_text9);
        predict_values_texts_vector.addElement(predict_values_text10);

        predict_values_texts_vector.addElement(predict_values_text11);
        predict_values_texts_vector.addElement(predict_values_text12);
        predict_values_texts_vector.addElement(predict_values_text13);
        predict_values_texts_vector.addElement(predict_values_text14);
        predict_values_texts_vector.addElement(predict_values_text15);
        predict_values_texts_vector.addElement(predict_values_text16);
        predict_values_texts_vector.addElement(predict_values_text17);
        predict_values_texts_vector.addElement(predict_values_text18);
        predict_values_texts_vector.addElement(predict_values_text19);
        predict_values_texts_vector.addElement(predict_values_text20);

        predict_values_texts_vector.addElement(predict_values_text21);
        predict_values_texts_vector.addElement(predict_values_text22);
        predict_values_texts_vector.addElement(predict_values_text23);
        predict_values_texts_vector.addElement(predict_values_text24);
        predict_values_texts_vector.addElement(predict_values_text25);
        predict_values_texts_vector.addElement(predict_values_text26);
        predict_values_texts_vector.addElement(predict_values_text27);
        predict_values_texts_vector.addElement(predict_values_text28);
        predict_values_texts_vector.addElement(predict_values_text29);
        predict_values_texts_vector.addElement(predict_values_text30);

        predict_values_texts_vector.addElement(predict_values_text31);
        predict_values_texts_vector.addElement(predict_values_text32);
        predict_values_texts_vector.addElement(predict_values_text33);
        predict_values_texts_vector.addElement(predict_values_text34);
        predict_values_texts_vector.addElement(predict_values_text35);
        predict_values_texts_vector.addElement(predict_values_text36);
        predict_values_texts_vector.addElement(predict_values_text37);
        predict_values_texts_vector.addElement(predict_values_text38);
        predict_values_texts_vector.addElement(predict_values_text39);
        predict_values_texts_vector.addElement(predict_values_text40);

        force_prodrule_list.addItem("arithmeticb");//friday
        force_prodrule_list.addItem("compose");
        force_prodrule_list.addItem("disjunct");
        force_prodrule_list.addItem("embed_graph");
        force_prodrule_list.addItem("embed_algebra");
        force_prodrule_list.addItem("entity_disjunct");
        force_prodrule_list.addItem("equal");
        force_prodrule_list.addItem("exists");
        force_prodrule_list.addItem("forall");
        force_prodrule_list.addItem("match");
        force_prodrule_list.addItem("negate");
        force_prodrule_list.addItem("record");
        force_prodrule_list.addItem("size");
        force_prodrule_list.addItem("split");

        required_choice.addItem("steps");
        required_choice.addItem("learned concepts");
        required_choice.addItem("seconds");
        required_choice.addItem("concepts");
        required_choice.addItem("conjectures");
        required_choice.addItem("pis");

        entity_sorting_list.add("distinctiveness");
        entity_sorting_list.add("average sims");

        entity_formatting_list.add("representation");
        entity_formatting_list.add("ascii conjecture");
        entity_formatting_list.add("otter conjecture");
        entity_formatting_list.add("prolog conjecture");
        entity_formatting_list.add("tptp conjecture");
        entity_formatting_list.add("related");
        entity_formatting_list.add("distinctive");

        entity_formatting_list.select(0);
        entity_formatting_list.select(1);

        concept_formatting_list.add("id");
        concept_formatting_list.add("name");
        concept_formatting_list.add("simplified def");
        concept_formatting_list.add("ascii def");
        concept_formatting_list.add("otter def");
        concept_formatting_list.add("prolog def");
        concept_formatting_list.add("tptp def");
        concept_formatting_list.add("skolemised def");
        concept_formatting_list.add("total score");
        concept_formatting_list.add("applicability");
        concept_formatting_list.add("child num");
        concept_formatting_list.add("child score");
        concept_formatting_list.add("complexity");
        concept_formatting_list.add("comprehens");
        concept_formatting_list.add("coverage");
        concept_formatting_list.add("devel steps");
        concept_formatting_list.add("discrimination");
        concept_formatting_list.add("equiv conj score");
        concept_formatting_list.add("equiv conj num");
        concept_formatting_list.add("highlight");
        concept_formatting_list.add("imp conj score");
        concept_formatting_list.add("imp conj num");
        concept_formatting_list.add("invariance");
        concept_formatting_list.add("neg applic");
        concept_formatting_list.add("ne conj score");
        concept_formatting_list.add("ne conj num");
        concept_formatting_list.add("novelty");
        concept_formatting_list.add("parent score");
        concept_formatting_list.add("parsimony");
        concept_formatting_list.add("pi conj score");
        concept_formatting_list.add("pi conj num");
        concept_formatting_list.add("productivity");
        concept_formatting_list.add("pos applic");
        concept_formatting_list.add("pred power");
        concept_formatting_list.add("variety");
        concept_formatting_list.add("examples");
        concept_formatting_list.add("datatable");
        concept_formatting_list.add("types");
        concept_formatting_list.add("functions");
        concept_formatting_list.add("const step");
        concept_formatting_list.add("arity");
        concept_formatting_list.add("const time");
        concept_formatting_list.add("ancestors");
        concept_formatting_list.add("categorisation");
        concept_formatting_list.add("cross domain");
        concept_formatting_list.add("entity instant");
        concept_formatting_list.add("add examples");
        concept_formatting_list.add("implications");
        concept_formatting_list.add("generalisations");
        concept_formatting_list.add("alt defs");
        concept_formatting_list.add("rel equivs");
        concept_formatting_list.add("rel imps");
        concept_formatting_list.add("near equivs");
        concept_formatting_list.add("near imps"); //alisonp
        concept_formatting_list.add("implied specs");
        concept_formatting_list.add("specifications");

        concept_pruning_list.add("cross domain");
        concept_pruning_list.add("integer output");
        concept_pruning_list.add("specialisation");
        concept_pruning_list.add("element type");
        concept_pruning_list.add("learned");
        concept_pruning_list.add("arithmeticb");//friday
        concept_pruning_list.add("compose");
        concept_pruning_list.add("disjunct");
        concept_pruning_list.add("embed_algebra");
        concept_pruning_list.add("embed_graph");
        concept_pruning_list.add("equal");
        concept_pruning_list.add("exists");
        concept_pruning_list.add("forall");
        concept_pruning_list.add("lakatos_method");
        concept_pruning_list.add("forced");
        concept_pruning_list.add("match");
        concept_pruning_list.add("negate");
        concept_pruning_list.add("record");
        concept_pruning_list.add("size");
        concept_pruning_list.add("split");

        concept_sorting_list.add("restore");
        concept_sorting_list.add("interestingness");
        concept_sorting_list.add("applicability");
        concept_sorting_list.add("coverage");
        concept_sorting_list.add("children_score");
        concept_sorting_list.add("complexity");
        concept_sorting_list.add("comprehensibility");
        concept_sorting_list.add("devel steps");
        concept_sorting_list.add("novelty");
        concept_sorting_list.add("parent_score");
        concept_sorting_list.add("parsimony");
        concept_sorting_list.add("predictive_power");
        concept_sorting_list.add("productivity");
        concept_sorting_list.add("variety");

        conjecture_formatting_list.add("id");
        conjecture_formatting_list.add("simplified format");
        conjecture_formatting_list.add("ascii format");
        conjecture_formatting_list.add("otter format");
        conjecture_formatting_list.add("prolog format");
        conjecture_formatting_list.add("tptp format");
        conjecture_formatting_list.add("proof_status");
        conjecture_formatting_list.add("explained_by");
        conjecture_formatting_list.add("proof");
        conjecture_formatting_list.add("counter");
        conjecture_formatting_list.add("total score");
        conjecture_formatting_list.add("arity");
        conjecture_formatting_list.add("applicability");
        conjecture_formatting_list.add("complexity");
        conjecture_formatting_list.add("comprehensibility");
        conjecture_formatting_list.add("surprisingness");
        conjecture_formatting_list.add("plausibility");
        conjecture_formatting_list.add("const time");
        conjecture_formatting_list.add("step");
        conjecture_formatting_list.add("lh_concept");
        conjecture_formatting_list.add("rh_concept");

        conjecture_pruning_list.add("segregated");
        conjecture_pruning_list.add("prime implicate");
        conjecture_pruning_list.add("instantiation");
        conjecture_pruning_list.add("not_instantiation");
        conjecture_pruning_list.add("axiom");
        conjecture_pruning_list.add("equivalence");
        conjecture_pruning_list.add("concept_forced");
        conjecture_pruning_list.add("implicate");
        conjecture_pruning_list.add("implication");
        conjecture_pruning_list.add("lakatos_method");
        conjecture_pruning_list.add("near_equiv");
        conjecture_pruning_list.add("near_imp");//alisonp
        conjecture_pruning_list.add("non-exists");
        conjecture_pruning_list.add("not prime implicate");
        conjecture_pruning_list.add("proved");
        conjecture_pruning_list.add("undetermined");
        conjecture_pruning_list.add("disproved");
        conjecture_pruning_list.add("sos");
        conjecture_pruning_list.add("time");
        conjecture_pruning_list.add("syntax error");

        conjecture_sorting_list.add("restore");
        conjecture_sorting_list.add("interestingness");
        conjecture_sorting_list.add("mw_max_ptime");
        conjecture_sorting_list.add("mw_av_ptime");
        conjecture_sorting_list.add("mw_min_ptime");
        conjecture_sorting_list.add("mw_diff_ptime");
        conjecture_sorting_list.add("wc_proof_time");
        conjecture_sorting_list.add("proof_time");
        conjecture_sorting_list.add("proof_length");
        conjecture_sorting_list.add("surprisingness");
        conjecture_sorting_list.add("plausibility");
        conjecture_sorting_list.add("applicability");
        conjecture_sorting_list.add("arity");

        statistics_label1.setAlignment(Label.RIGHT);
        statistics_label2.setAlignment(Label.RIGHT);
        statistics_label3.setAlignment(Label.RIGHT);

        statistics_choice1.add("steps");
        statistics_choice1.add("concepts");
        statistics_choice1.add("conjectures");
        statistics_choice1.add("eq-conjectures");
        statistics_choice1.add("ne-conjectures");
        statistics_choice1.add("prime implicates");
        statistics_choice1.add("categorisations");
        statistics_choice1.add("specialisations");
        statistics_choice1.add("counterexamples");
        statistics_choice1.add("agenda items");
        statistics_choice1.add("seconds");
        statistics_choice2.add("steps");
        statistics_choice2.add("concepts");
        statistics_choice2.add("conjectures");
        statistics_choice2.add("eq-conjectures");
        statistics_choice2.add("ne-conjectures");
        statistics_choice2.add("prime implicates");
        statistics_choice2.add("categorisations");
        statistics_choice2.add("specialisations");
        statistics_choice2.add("counterexamples");
        statistics_choice2.add("agenda items");
        statistics_choice2.add("seconds");
        statistics_choice3.add("steps");
        statistics_choice3.add("concepts");
        statistics_choice3.add("conjectures");
        statistics_choice3.add("eq-conjectures");
        statistics_choice3.add("ne-conjectures");
        statistics_choice3.add("prime implicates");
        statistics_choice3.add("categorisations");
        statistics_choice3.add("specialisations");
        statistics_choice3.add("counterexamples");
        statistics_choice3.add("agenda items");
        statistics_choice3.add("seconds");

        statistics_choice1.select(0);
        statistics_choice2.select(1);
        statistics_choice3.select(2);

        statistics_type_list.addItem("concept versus");
        statistics_type_list.addItem("equivalence versus");
        statistics_type_list.addItem("nonexists versus");
        statistics_type_list.addItem("implicate versus");
        statistics_type_list.addItem("near_equiv versus");
        statistics_type_list.addItem("near_imp versus"); //alisonp
        statistics_type_list.addItem("step versus");
        statistics_type_list.addItem("entity versus");
        statistics_type_list.addItem("process times");

        concept_formatting_list.select(0);
        concept_formatting_list.select(1);
        concept_formatting_list.select(3);
        concept_formatting_list.select(8);
        concept_formatting_list.select(9);
        concept_formatting_list.select(12);
        concept_formatting_list.select(15);
        concept_formatting_list.select(26);
        concept_formatting_list.select(31);
        concept_formatting_list.select(34);
        concept_formatting_list.select(35);
        concept_formatting_list.select(39);
        concept_formatting_list.select(40);
        concept_formatting_list.select(41);
        concept_formatting_list.select(49);

        conjecture_formatting_list.select(0);
        conjecture_formatting_list.select(2);
        conjecture_formatting_list.select(10);
        conjecture_formatting_list.select(11);
        conjecture_formatting_list.select(12);
        conjecture_formatting_list.select(13);
        conjecture_formatting_list.select(15);
        conjecture_formatting_list.select(16);
        conjecture_formatting_list.select(17);
        conjecture_formatting_list.select(18);
        conjecture_formatting_list.select(19);

        setHelloText();
        getHelpFiles();
        getMacroFiles();
        getInputFiles();
        getReportFiles();
        getReactionFiles();
        getTheoryFiles();
    }

    private void setHelloText()
    {
        String hello_string = "<html><body bgcolor=\"FFFF99\"><center><b><font size=5 color=blue>The</font><font size=8 color=red>";
        hello_string += " HR </font><font size=5 color=blue>System</font></b><p>\n";
        hello_string += " version " + version_number + "<br>&copy;2002 Simon Colton<br>www.dai.ed.ac.uk/~simonco/research/hr<br>";
        hello_string += "Developed at the Universities of Edinburgh and York, UK<br>\n";
        hello_string += "<table border=0 cellspacing=3><tr valign=top><td align=center>\n";
        hello_string += "<font color=green><b><u>Main Developer</u></b><br>Simon Colton</font></td>\n";
        hello_string += "<td>&nbsp;&nbsp;&nbsp;<td align=center>";
        hello_string += "<font color=red><b><u>Research Team</u></b><br>Alan Bundy<br>Toby Walsh</font>";
        hello_string += "</td><td>&nbsp;&nbsp;</td><td align=center>";
        hello_string += "<font color=blue><b><u>Development Team</u></b><br>Roy McCasland<br>Alison Pease<br>\n";
        hello_string += "Graham Steel<br>J&uuml;rgen Zimmer</font>";
        hello_string += "</td><td>&nbsp;&nbsp;</td><td align=center>";
        hello_string += "<font color=black><b><u>Research Partners</u></b><br>Andreas Meier<br>Ian Miguel<br>Volker Sorge<br>Geoff Sutcliffe</font>";
        hello_string += "</td></tr></table>\n";
        hello_string += "<table width=85% cellspacing=10 cellpadding=10 border=2 bgcolor=\"FFFF60\"><tr valign=top>";
        hello_string += "<td width=33%><b><u><font color=red><center>TO USE HR</u></b><ul><li>";
        hello_string += "Choose a macro from the left hand list, then click on the <font color=red>red</font> play macro button.</li>\n";
        hello_string += "<p><li>Restore an entire theory by choosing it from the right hand list and clicking";
        hello_string += " on the <font color=red>red</font> restore theory button</li>\n";
        hello_string += "<p><li>Start with a new domain file in the DOMAIN screen from the SETUP drop down list above.</li></ul>\n";
        hello_string += "</td><td width=33%><b><u><font color=green><center>TO LEARN ABOUT USING HR</u></b><ul><li>";
        hello_string += "Choose a tutorial macro from the left hand list (marked with a #) ";
        hello_string += "and click on the <font color=red>red</font> play macro button.</li>\n";
        hello_string += "<p><li>Read some help topics from the TOPICS screen in the HELP drop down list above.</li>\n";
        hello_string += "<p><li>Read the manual at: www.dai.ed.ac.uk/<br>~simonco/research/hr</li></ul>\n";
        hello_string += "</td><td width=33%><b><u><font color=blue><center>ABOUT THE HR PROJECT</u></b><ul><li>";
        hello_string += "Aim: to automated scientific theory formation and apply this to discovery tasks</li>\n";
        hello_string += "<p><li>Started in 1997 in the Mathematical Reasoning Group, University of Edinburgh</li>\n";
        hello_string += "<p><li>Read the Springer book \"Automated Theory Formation in Pure Mathematics\" by Simon Colton, ISBN: 1-85233-609-9</li></ul>\n";
        hello_string += "</td></tr></table>\n";
        hello_string += "</body></html>\n";
        hello_text.setText(hello_string);
    }

    private void fillListBox(List list, Vector values)
    {
        list.removeAll();
        for (int i=0;i<values.size();i++)
        {
            String value = (String)values.elementAt(i);
            if (!value.substring(1,2).equals(":"))
                list.addItem(value);
            else
                list.addItem(value.substring(2,value.length()));
            if (value.substring(0,1).equals("y"))
                list.select(i);
        }
    }

    public void extendSequence(Concept concept, Button button)
    {
        calculate_output_text.setText("");
        int bottom_limit = new Integer((String)calculate_entity1_text.getText().trim()).intValue();
        int top_limit =
                new Integer((String)calculate_entity2_text.getText().trim()).intValue();
        sequence_calculation_thread =
                new SequenceCalculationThread(bottom_limit, top_limit, concept,
                        calculate_output_text, calculate_button, concepts);
        sequence_calculation_thread.start();
    }

    public double getConceptCategoryPercent(String first, String second)
    {
        double num  = 0;
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            Vector cat = concept.categorisation;
            boolean are_pair = false;
            int j=0;
            while (j<cat.size() && !are_pair)
            {
                Vector category = (Vector)cat.elementAt(j);
                String cstring = category.toString();
                if (cstring.indexOf(first)>-1 && cstring.indexOf(second)>-1)
                {
                    num++;
                    are_pair = true;
                }
                j++;
            }
        }
        return (new Double(100*num/concepts.size())).doubleValue();
    }

    public void updatePercentCategorisation()
    {
        Vector categorisation = new Vector();
        Vector category = new Vector();
        category.addElement(((Entity)entities.elementAt(0)).name);
        categorisation.addElement(category);
        double percent = (new Double(percent_categorisation_percent_text.getText())).doubleValue();
        for (int i=1; i<entities.size(); i++)
        {
            String entity = ((Entity)entities.elementAt(i)).name;
            boolean never_put_in = true;
            for (int j=0; j<categorisation.size(); j++)
            {
                boolean put_in = true;
                int k=0;
                while (k<category.size() && put_in)
                {
                    String old_ent = (String)category.elementAt(k);
                    if (getConceptCategoryPercent(entity,old_ent)<percent)
                        put_in = false;
                    k++;
                }
                if (put_in && !category.contains(entity))
                {
                    category.addElement(entity);
                    never_put_in = false;
                }
            }
            if (never_put_in)
            {
                Vector new_category = new Vector();
                new_category.addElement(entity);
                categorisation.addElement(new_category);
            }
        }
        percent_categorisation_text.setText(categorisation.toString());
    }

    public void actionPerformed(ActionEvent e)
    {
        // Searching through agent output. //

        if (e.getSource()==agent_search_button){
            String search_string = agent_search_text.getText();
            agent_search_output_list.clear();
            for (int pane_num = 0; pane_num < lakatos_tabbed_pane.getTabCount(); pane_num++){
                AgentOutputPanel agent_panel = (AgentOutputPanel)lakatos_tabbed_pane.getComponentAt(pane_num);
                JTextArea text_area = agent_panel.agent_output_text;
                String text_to_search = text_area.getText();
                int pos = text_to_search.indexOf(search_string);
                while (pos > 0){
                    agent_search_output_list.add(lakatos_tabbed_pane.getTitleAt(pane_num) + ":" + Integer.toString(pos));
                    pos = text_to_search.indexOf(search_string, pos+1);
                }
            }
        }

        // Saving a report file //

        if (e.getSource()==save_report_button)
        {
            try
            {
                String file_name = save_report_name_text.getText();
                if (!file_name.equals(""))
                {
                    String fn = input_files_directory + file_name;
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fn)));
                    out.println(report_instructions_text.getText().trim());
                    out.close();
                    getReportFiles();
                }
            }
            catch(Exception ex){System.out.println(ex);}
        }

        // Saving a help file //

        if (e.getSource()==save_help_button)
        {
            try
            {
                String fn = input_files_directory + help_save_file_text.getText();
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fn)));
                String m = help_text.getText().trim()+"\n";
                while (m.indexOf("\n")>-1)
                {
                    int p = m.indexOf("\n");
                    out.println(m.substring(0,p));
                    m=m.substring(p+1,m.length());
                }
                out.close();
                getHelpFiles();
            }
            catch(Exception ex){System.out.println(ex);}
        }

        // Getting the percentage categorisation //

        if (e.getSource()==percent_categorisation_button)
        {
            percent_categorisation_text.setText("Please wait");
            updatePercentCategorisation();
        }

        if (e.getSource()==percent_up_button)
        {
            percent_categorisation_text.setText("Please wait");
            if (percent_categorisation_percent_text.getText().equals(""))
                percent_categorisation_percent_text.setText("40");
            int percent = (new Integer(percent_categorisation_percent_text.getText())).intValue();
            percent = percent + 10;
            if (percent > 100)
                percent = 100;
            percent_categorisation_percent_text.setText(Integer.toString(percent));
            updatePercentCategorisation();
        }

        if (e.getSource()==percent_down_button)
        {
            percent_categorisation_text.setText("Please wait");
            if (percent_categorisation_percent_text.getText().equals(""))
                percent_categorisation_percent_text.setText("60");
            int percent = (new Integer(percent_categorisation_percent_text.getText())).intValue();
            percent = percent - 10;
            if (percent < 1)
                percent = 1;
            percent_categorisation_percent_text.setText(Integer.toString(percent));
            updatePercentCategorisation();
        }

        // Resetting all concept weights //

        if (e.getSource()==reset_concept_weights_button)
        {
            concept_applicability_weight_text.setText("0.0");
            concept_coverage_weight_text.setText("0.0");
            concept_comprehensibility_weight_text.setText("0.0");
            concept_cross_domain_weight_text.setText("0.0");
            concept_highlight_weight_text.setText("0.0");
            concept_novelty_weight_text.setText("0.0");
            concept_parent_weight_text.setText("0.0");
            concept_children_weight_text.setText("0.0");
            concept_parsimony_weight_text.setText("0.0");
            concept_productivity_weight_text.setText("0.0");
            concept_development_steps_num_weight_text.setText("0.0");
            concept_default_productivity_text.setText("0.0");
            concept_variety_weight_text.setText("0.0");
        }

        if (e.getSource()==reset_conjecture_weights_button)
        {
            conjecture_applicability_weight_text.setText("0.0");
            conjecture_comprehensibility_weight_text.setText("0.0");
            conjecture_surprisingness_weight_text.setText("0.0");
            conjecture_plausibility_weight_text.setText("0.0");
        }

        // Saving a macro //

        if (e.getSource()==save_macro_button)
        {
            try
            {
                String fn = input_files_directory + save_macro_text.getText();
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fn)));
                String m = macro_text.getText().trim()+"\n";
                while (m.indexOf("\n")>-1)
                {
                    int p = m.indexOf("\n");
                    out.println(m.substring(0,p));
                    m=m.substring(p+1,m.length());
                }
                out.close();
                getMacroFiles();
            }
            catch(Exception ex){System.out.println(ex);}
        }

        // Calculation //

        if (e.getSource() == calculate_button)
        {
            if (!calculate_button.getLabel().equals("Calculate"))
            {
                sequence_calculation_thread.must_stop = true;
            }
            else
            {
                int i=0;
                boolean found = false;
                Concept chosen_concept = new Concept();
                String chosen_id = concept_list.getSelectedItem();
                while (!found && i<concepts.size())
                {
                    chosen_concept = (Concept)concepts.elementAt(i);
                    if (chosen_concept.id.equals(chosen_id))
                        found=true;
                    i++;
                }
                String output = "";
                if (calculate_entity2_text.getText().equals(""))
                {
                    calculate_button.setLabel("PLEASE WAIT");
                    String entity = calculate_entity1_text.getText();
                    output = chosen_concept.calculateRow(concepts, entity).tuples.toString();
                    output = output.substring(1,output.length()-1);
                    if (chosen_concept.arity==1)
                    {
                        if (output.equals("[]"))
                            output = "yes";
                        if (output.equals(""))
                            output = "no";
                    }
                    else
                    {
                        if (output.equals(""))
                            output = "empty";
                    }
                    calculate_output_text.setText(output);
                }
                else
                    extendSequence(chosen_concept, calculate_button);
                calculate_button.setLabel("Calculate");
            }
        }

        // Setting selection buttons //

        if (e.getSource() == domain_none_button)
        {
            deselectAll(initial_concepts_list);
            deselectAll(initial_entity_list);
            deselectAll(counterexample_list);
            deselectAll(split_values_list);
        }

        if (e.getSource() == highlight_none_button)
        {
            deselectAll(highlight_list);
        }

        if (e.getSource() == initial_entity_none_button)
        {
            deselectAll(initial_entity_list);
        }

        if (e.getSource() == counterexample_none_button)
        {
            deselectAll(counterexample_list);
        }

        if (e.getSource() == split_values_none_button)
        {
            deselectAll(split_values_list);
        }

        if (e.getSource() == highlight_all_button)
        {
            selectAll(highlight_list);
        }

        if (e.getSource() == initial_entity_all_button)
        {
            selectAll(initial_entity_list);
        }

        if (e.getSource() == counterexample_all_button)
        {
            selectAll(counterexample_list);
        }

        if (e.getSource() == split_values_all_button)
        {
            selectAll(split_values_list);
        }
    }

    public void deselectAll(List list)
    {
        for (int i=0; i<list.getItemCount(); i++)
            list.deselect(i);
    }

    private void selectAll(List list)
    {
        for (int i=0; i<list.getItemCount(); i++)
            list.select(i);
    }

    public void updateConjecture(Conjecture chosen_conjecture)
    {
        if (!chosen_conjecture.is_removed)
        {
            String details = chosen_conjecture.fullHTMLDetails(conjecture_formatting_list.getSelectedItems(),
                    decimal_places,
                    concept_formatting_list.getSelectedItems());
            conjecture_text_box.setText(details);
        }
        parent_conjectures_list.removeAll();
        child_conjectures_list.removeAll();
        for (int i=0; i<chosen_conjecture.parent_conjectures.size(); i++)
        {
            Conjecture parent_conj = (Conjecture)chosen_conjecture.parent_conjectures.elementAt(i);
            parent_conjectures_list.addItem(parent_conj.writeConjecture("ascii"));
        }
        for (int i=0; i<chosen_conjecture.child_conjectures.size(); i++)
        {
            Conjecture child_conj = (Conjecture)chosen_conjecture.child_conjectures.elementAt(i);
            child_conjectures_list.addItem(child_conj.writeConjecture("ascii"));
        }
        Vector grep_positions = (Vector)conjecture_grep_positions.get(chosen_conjecture.id);
        if (grep_positions != null)
        {
            conjecture_text_box.setSelectionStart((new Integer((String)grep_positions.elementAt(0))).intValue());
            conjecture_text_box.setSelectionEnd((new Integer((String)grep_positions.elementAt(1))).intValue());
        }
        else
        {
            conjecture_text_box.setCaretPosition(0);
        }
    }

    /** This updates the concept on screen which is chosen from the concept list.
     */

    public Concept updateConcept()
    {
        int i=0;
        boolean found = false;
        Concept chosen_concept = new Concept();
        String chosen_id = concept_list.getSelectedItem();
        while (!found && i<concepts.size())
        {
            chosen_concept = (Concept)concepts.elementAt(i);
            if (chosen_concept.id.equals(chosen_id))
                found=true;
            i++;
        }
        String details =
                chosen_concept.fullDetails("html",concept_formatting_list.getSelectedItems(), decimal_places);
        concept_text_box.setText(details);

        ancestor_list.removeAll();
        children_list.removeAll();
        for (i=0; i<concepts.size(); i++)
        {
            Concept c = (Concept)concepts.elementAt(i);
            if (chosen_concept.ancestor_ids.contains(c.id))
                ancestor_list.addItem(c.id+" "+c.writeDefinition("ascii"));
            if (chosen_concept.children.contains(c))
                children_list.addItem(c.id+" "+c.writeDefinition("ascii"));
        }
        Vector grep_positions = (Vector)concept_grep_positions.get(chosen_id);
        if (grep_positions != null)
        {
            concept_text_box.setCaretPosition((new Integer((String)grep_positions.elementAt(0))).intValue());
            concept_text_box.setSelectedTextColor(Color.red);
            concept_text_box.setSelectionStart((new Integer((String)grep_positions.elementAt(0))).intValue());
            concept_text_box.setSelectionEnd((new Integer((String)grep_positions.elementAt(1))).intValue());
        }
        else
        {
            concept_text_box.setCaretPosition(0);
        }
        return chosen_concept;
    }

    public void addObjectToList(Object obj, String obj_string, SortableList list,
                                String[] pruning_condition, String sort_string)
    {
        String sort_value = "";
        if (sort_string!=null && !sort_string.equals("") && !sort_string.equals("restore"))
            sort_value = reflect.getValue(obj, sort_string).toString();

        boolean add_to_list = true;
        for (int i=0; i<pruning_condition.length && add_to_list==true; i++)
        {
            if (reflect.checkCondition(obj, pruning_condition[i])==false)
                add_to_list = false;
        }
        if (add_to_list)
            list.addItem(obj_string, sort_value);
    }

    public void pruneConceptList()
    {
        concept_list.clear();
        String[] pruning_conditions = concept_pruning_list.getSelectedItems();
        String sort_string = concept_sorting_list.getSelectedItem();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            addObjectToList(concept, concept.id, concept_list,
                    pruning_conditions, sort_string);
        }
        concept_list_number.setText("number: " + Integer.toString(concept_list.getItemCount()));
    }

    public void pruneConjectureList()
    {
        conjecture_list.clear();
        String[] pruning_conditions = conjecture_pruning_list.getSelectedItems();
        String sort_string = conjecture_sorting_list.getSelectedItem();
        for (int i=0; i<conjectures.size(); i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            addObjectToList(conjecture, conjecture.id, conjecture_list,
                    pruning_conditions, sort_string);
        }
        conjecture_list_number.setText("number: " + Integer.toString(conjecture_list.getItemCount()));
    }

    public void focusGained(FocusEvent e)
    {
    }

    public void focusLost(FocusEvent e)
    {
    }

    public void itemStateChanged(ItemEvent e)
    {
        // Finding agent search strings.

        if (e.getSource()==agent_search_output_list){
            String chosen = agent_search_output_list.getSelectedItem();
            String tab_name = chosen.substring(0, chosen.indexOf(":"));
            String position = chosen.substring(chosen.indexOf(":")+1, chosen.length());
            int start_pos = (new Integer(position)).intValue();
            int end_pos = start_pos + agent_search_text.getText().length();
            for (int tab_num = 0; tab_num < lakatos_tabbed_pane.getTabCount(); tab_num++){
                if (lakatos_tabbed_pane.getTitleAt(tab_num).equals(tab_name)){
                    lakatos_tabbed_pane.setSelectedIndex(tab_num);
                    AgentOutputPanel panel = (AgentOutputPanel)lakatos_tabbed_pane.getComponentAt(tab_num);
                    //panel.agent_output_text.requestFocus();
                    panel.agent_output_text.select(start_pos, end_pos);
                }
            }
        }

        // Load a help file //

        if (e.getSource()==help_list)
        {
            help_text.setText("");
            String fn = input_files_directory + help_list.getSelectedItem();
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(fn));
                String s = in.readLine();
                while (!(s==null))
                {
                    help_text.append(s+"\n");
                    s = in.readLine();
                }
                in.close();
                if (!s.equals(""))
                    help_text.append(s+"\n");
            }
            catch (Exception ex){}
        }

        // Sort the entities //

        if (e.getSource()==entity_sorting_list)
        {
            Vector temp_entities = new Vector();
            Vector scores = new Vector();
            for (int i=0; i<concepts.size(); i++)
            {
                Concept concept = (Concept)concepts.elementAt(i);
                Vector categorisation = concept.categorisation;
                for (int j=0; j<categorisation.size(); j++)
                {
                    Vector category = (Vector)categorisation.elementAt(j);
                    for (int k=0; k<category.size(); k++)
                    {
                        String entity = (String)category.elementAt(k);
                        int index = temp_entities.indexOf(entity);
                        if (index>-1)
                        {
                            int score = ((Integer)scores.elementAt(index)).intValue();
                            if (entity_sorting_list.getSelectedItem().equals("average sims"))
                                score = score + category.size();
                            if (entity_sorting_list.getSelectedItem().equals("distinctiveness") &&
                                    category.size()==1)
                                score++;
                            scores.setElementAt(new Integer(score), index);
                        }
                        else
                        {
                            int score = 0;
                            if (entity_sorting_list.getSelectedItem().equals("average sims"))
                                score = category.size();
                            if (entity_sorting_list.getSelectedItem().equals("distinctiveness") &&
                                    category.size()==1)
                                score = 1;
                            scores.addElement(new Integer(score));
                            temp_entities.addElement(entity);
                        }
                    }
                }
            }

            entity_list.removeAll();
            for (int i=0; i<scores.size(); i++)
            {
                int score = ((Integer)scores.elementAt(i)).intValue();
                int j=0;
                boolean placed = false;
                while(j<entity_list.getItemCount() && !placed)
                {
                    String item = entity_list.getItem(j);
                    String score_string = item.substring(item.lastIndexOf(" ")+1, item.length());
                    int other_score = (new Integer(score_string)).intValue();
                    if ((score < other_score && entity_sorting_list.getSelectedItem().equals("average sims")) ||
                            (score > other_score && entity_sorting_list.getSelectedItem().equals("distinctiveness")))
                    {
                        entity_list.addItem((String)temp_entities.elementAt(i)+" "+Integer.toString(score),j);
                        placed=true;
                    }
                    j++;
                }
                if (!placed)
                    entity_list.addItem((String)temp_entities.elementAt(i)+" "+Integer.toString(score));
            }

            if (entity_sorting_list.getSelectedItem().equals("average sims"))
            {
                for (int i=0; i<entity_list.getItemCount(); i++)
                {
                    String item = entity_list.getItem(i);
                    String score_string = item.substring(item.lastIndexOf(" ")+1, item.length());
                    double score = (new Double(score_string)).doubleValue();
                    double new_score = score/concepts.size();
                    entity_list.replaceItem(item.substring(0, item.lastIndexOf(" ")+1)+Double.toString(new_score), i);
                }
            }
        }

        // Load a report file //

        if (e.getSource()==reports_list)
        {
            report_instructions_text.setText("");
            String filename = reports_list.getSelectedItem();
            String fn = input_files_directory + filename;
            loadReport(fn);
            save_report_name_text.setText(filename);
        }

        if (e.getSource()==entity_list)
        {
            int i=0;
            boolean found = false;
            String ent = entity_list.getSelectedItem();
            if (ent.lastIndexOf(" ")>-1)
                ent = ent.substring(0, ent.lastIndexOf(" "));
            Entity chosen_entity = new Entity();
            while (!found && i<entities.size())
            {
                chosen_entity = (Entity)entities.elementAt(i);
                if (chosen_entity.name.equals(ent))
                {
                    found = true;
                    entity_text_box.setText(chosen_entity.fullHTMLDetails(concepts, entity_formatting_list.getSelectedItems()));
                }
                i++;
            }
        }

        if (e.getSource()==concept_pruning_list)
        {
            pruneConceptList();
        }

        if (e.getSource()==concept_sorting_list)
        {
            pruneConceptList();
        }

        if (e.getSource()==conjecture_pruning_list)
        {
            pruneConjectureList();
        }

        if (e.getSource()==conjecture_sorting_list)
        {
            pruneConjectureList();
        }

        if (e.getSource()==ancestor_list)
        {
            String s = ancestor_list.getSelectedItem();
            String chosen_concept = s.substring(0, s.indexOf(" "));
            int i=0;
            boolean found = false;
            while (!found && i<concepts.size())
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if (concept.id.equals(chosen_concept))
                    concept_text_box.setText(
                            concept.fullDetails("html",concept_formatting_list.getSelectedItems(), decimal_places));
                i++;
            }
        }

        if (e.getSource()==children_list)
        {
            String s = children_list.getSelectedItem();
            String chosen_concept = s.substring(0, s.indexOf(" "));
            int i=0;
            boolean found = false;
            while (!found && i<concepts.size())
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if (concept.id.equals(chosen_concept))
                    concept_text_box.setText(
                            concept.fullDetails("html",concept_formatting_list.getSelectedItems(), decimal_places));
                i++;
            }
        }

        if (e.getSource()==concept_list)
        {
            updateConcept();
        }

        if (e.getSource()==statistics_choice1||
                e.getSource()==statistics_choice2||
                e.getSource()==statistics_choice3)
            update();
    }

    /** Adding the domain information returned by the Reader
     */

    public void addDomainInformation(Vector domain_information)
    {
        Vector domain_concepts = (Vector)domain_information.elementAt(0);
        Vector domain_objects_of_interest = (Vector)domain_information.elementAt(1);
        Vector domain_axioms = (Vector)domain_information.elementAt(2);
        Vector domain_specifications = (Vector)domain_information.elementAt(3);
        Vector domain_entity_type_names = (Vector)domain_information.elementAt(4);

        predict_entity_type_choice.removeAll();
        predict_entity_type_choice.addItem("Choose entity type");
        for (int i=0; i<domain_entity_type_names.size(); i++)
            predict_entity_type_choice.addItem((String)domain_entity_type_names.elementAt(i));

        for (int i=0; i<domain_concepts.size(); i++)
        {
            Concept concept = (Concept)domain_concepts.elementAt(i);
            initial_concepts_list.addItem(concept.name);
            highlight_list.addItem(concept.id+":"+concept.name);
        }

        Vector object_types = new Vector();
        for (int i=0; i<domain_objects_of_interest.size(); i++)
        {
            String object_of_interest = (String)domain_objects_of_interest.elementAt(i);
            initial_entity_list.addItem(object_of_interest);
            counterexample_list.addItem(object_of_interest);
            split_values_list.addItem(object_of_interest);
            String object_type = object_of_interest.substring(0, object_of_interest.indexOf(":"));
            if (!object_types.contains(object_type))
            {
                object_types.addElement(object_type);
                split_values_list.addItem("all: "+object_type+"s",0);
            }
        }
        if (!object_types.contains("integer"))
            split_values_list.addItem("all: integers",0);
    }

    /** Change the values in the various text boxes, etc., using the statistics
     * vector supplied by the over-lying theory.
     */

    public void update()
    {
        if (!statistics.isEmpty())
        {
            String first_stats = (String)statistics.elementAt(statistics_choice1.getSelectedIndex());
            main_frame.setTitle(my_agent_name + " HR" + version_number + ": " +first_stats+" "+statistics_choice1.getSelectedItem());
            statistics_label1.setText(first_stats);
            statistics_label2.setText((String)statistics.
                    elementAt(statistics_choice2.getSelectedIndex()));
            statistics_label3.setText((String)statistics.
                    elementAt(statistics_choice3.getSelectedIndex()));
        }
    }

    /** This adds an entity to the list.
     */

    public void addEntity(Entity entity)
    {
        entity_list.addItem(entity.name);
    }

    /** This removes an entity from the list.
     */

    public void removeEntity(Entity entity)
    {
        entity_list.remove(entity.name);
    }

    /** Returns a vector of the selected items in the given list.
     */

    public Vector getSelectedItems(List list)
    {
        Vector output = new Vector();
        String[] s = list.getSelectedItems();
        for(int i=0;i<s.length;i++)
            output.addElement(s[i]);
        return output;
    }

    /** This gets the macro file names from the macros directory
     * and puts them into macro_list list.
     */

    public void getMacroFiles()
    {
        File file = new File(input_files_directory);
        String[] macros = file.list();
        String quick_chosen = quick_macro_choice.getSelectedItem();
        String macro_chosen = macro_list.getSelectedItem();
        macro_list.removeAll();
        quick_macro_choice.removeAll();
        quick_macro_choice.addItem("macro");
        for (int i=0; i<macros.length; i++)
        {
            if (macros[i].indexOf(".hrm")>0)
            {
                macro_list.addItem(macros[i]);
                quick_macro_choice.addItem(macros[i]);
                if (macros[i].equals(quick_chosen))
                    quick_macro_choice.select(i+1);
                if (macros[i].equals(macro_chosen))
                    macro_list.select(i);
            }
        }
    }

    /** This gets the report file names from the reports director
     * and puts them into report_list list.
     */

    public void getReportFiles()
    {
        getDirectoryFiles(input_files_directory, reports_to_execute_list, "hrr");
        getDirectoryFiles(input_files_directory, reports_list, "hrr");
    }

    public void getTheoryFiles()
    {
        getDirectoryFiles(input_files_directory, stored_theories_list, "hrs");
    }

    private void getDirectoryFiles(String directory, List list, String file_extension)
    {
        File file = new File(directory);
        String[] file_names = file.list();
        String[] chosen = list.getSelectedItems();
        list.removeAll();
        for (int i=0; i<file_names.length; i++)
        {
            if (file_names[i].indexOf("." + file_extension)>0)
                list.addItem(file_names[i]);
        }
        for (int i=0; i<list.getItemCount(); i++)
            for (int j=0; j<chosen.length; j++)
                if (chosen[j].equals(list.getItem(i)))
                    list.select(i);
    }

    /** This gets the input file names from the inputs directory
     * and puts them into input_list list.
     */

    public void getInputFiles()
    {
        File file = new File(input_files_directory);
        String[] domains = file.list();
        domain_list.removeAll();
        predict_input_files_choice.removeAll();
        for (int i=0; i<domains.length; i++)
        {
            if (domains[i].indexOf(".hrd")>0)
            {
                domain_list.addItem(domains[i]);
                predict_input_files_choice.addItem(domains[i]);
            }
        }
    }


    /** This gets the reactions from the reactions directory.
     */

    public void getReactionFiles()
    {
        getDirectoryFiles(input_files_directory, reactions_list, "hre");
    }

    /** This gets the help file names from the help directory and
     * puts them into the help list.
     */

    public void getHelpFiles()
    {
        getDirectoryFiles(input_files_directory, help_list, "hrh");
    }

    /** Loads the macro into the textbox.
     */

    public void loadReaction(String fn)
    {
        try
        {
            reactions_text.setText("");
            BufferedReader in = new BufferedReader(new FileReader(fn));
            String s = in.readLine();
            while (!(s==null))
            {
                reactions_text.append(s+"\n");
                s = in.readLine();
            }
            in.close();
        }
        catch (Exception ex){System.out.println(ex);}
    }

    /** Loads the report into the textbox.
     */

    public void loadReport(String fn)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fn));
            String s = in.readLine();
            while (!(s==null))
            {
                report_instructions_text.append(s+"\n");
                s = in.readLine();
            }
            in.close();
            if (!s.equals(""))
                report_instructions_text.append(s+"\n");
        }
        catch (Exception ex){}
    }
}
