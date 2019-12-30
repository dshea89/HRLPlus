package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class FrontEnd implements ItemListener, ActionListener, FocusListener, Serializable {
    public String my_agent_name = "";
    public Hashtable concept_grep_positions = new Hashtable();
    public Hashtable conjecture_grep_positions = new Hashtable();
    public Reflection reflect = new Reflection();
    public String input_files_directory = "";
    public String version_number = "???";
    public SequenceCalculationThread sequence_calculation_thread = new SequenceCalculationThread();
    public int decimal_places = 2;
    public String os = "unix";
    public Label gc_label = new Label("gc steps");
    public TextField gc_text = new TextField("100000");
    public TextArea pseudo_code_input_text = new TextArea();
    public TextArea pseudo_code_output_text = new TextArea();
    public Button pseudo_code_run_button = new Button("Run Pseudo Code");
    public Button add_proof_tables_to_macro_button = new Button("Add to macro");
    public String[][] file_prover_data = new String[20][4];
    public String[] file_prover_headers = new String[]{"Try Position", "File Name", "Try Condition", "Operation Substitution"};
    public JTable file_prover_table;
    public String[][] other_prover_data;
    public String[] other_prover_headers;
    public JTable other_prover_table;
    public String[][] algebra_data;
    public String[] algebra_headers;
    public JTable algebra_table;
    public String[][] axiom_data;
    public String[] axiom_headers;
    public JTable axiom_table;
    public TextField gold_standard_categorisation_text;
    public TextField coverage_categorisation_text;
    public TextField object_types_to_learn_text;
    public Checkbox segregated_search_check;
    public TextField segregation_categorisation_text;
    public TextField store_theory_name_text;
    public Button store_theory_button;
    public Button restore_theory_button;
    public SortableList stored_theories_list;
    public Button save_concept_construction_button;
    public TextField save_concept_construction_filename_text;
    public TextField save_concept_construction_concept_id_text;
    public TextField build_concept_construction_concept_id_text;
    public Button build_concept_button;
    public TextField build_concept_filename_text;
    public Button save_conjecture_construction_button;
    public TextField save_conjecture_construction_filename_text;
    public TextField save_conjecture_construction_conjecture_id_text;
    public TextField build_conjecture_construction_conjecture_id_text;
    public Button build_conjecture_button;
    public TextField build_conjecture_filename_text;
    public Button systemout_button;
    public TextField debug_parameter_text;
    public TextArea systemout_text;
    public TextField reaction_name_text;
    public SortableList reactions_list;
    public TextArea reactions_text;
    public Button add_reaction_button;
    public Button remove_reaction_button;
    public Button save_reaction_button;
    public List reactions_added_list;
    public TextArea puzzle_output_text;
    public Button puzzle_generate_button;
    public TextField puzzle_ooo_choices_size_text;
    public TextField puzzle_nis_choices_size_text;
    public TextField puzzle_analogy_choices_size_text;
    public TextField force_string_text;
    public List force_primary_concept_list;
    public List force_secondary_concept_list;
    public List force_prodrule_list;
    public List force_parameter_list;
    public Button force_button;
    public TextArea force_result_text;
    public TextArea report_instructions_text;
    public JEditorPane report_output_text;
    public Button execute_report_button;
    public Button save_report_button;
    public SortableList reports_to_execute_list;
    public SortableList reports_list;
    public TextField save_report_name_text;
    public TextField sot_conjecture_number_text;
    public TextArea sot_results_text;
    public Button sot_submit_button;
    public Label mathweb_inout_socket_label;
    public Label mathweb_service_socket_label;
    public Label mathweb_hostname_label;
    public Checkbox use_e_in_mathweb_check;
    public Checkbox use_bliksem_in_mathweb_check;
    public Checkbox use_otter_in_mathweb_check;
    public Checkbox use_spass_in_mathweb_check;
    public List statistics_type_list;
    public SortableList statistics_choice_list;
    public SortableList statistics_subchoice_list;
    public SortableList statistics_methods_list;
    public Button statistics_plot_button;
    public Button statistics_compile_button;
    public Checkbox statistics_average_check;
    public Checkbox statistics_seperate_graphs_check;
    public Checkbox statistics_count_check;
    public TextField statistics_calculation_text;
    public TextField statistics_prune_text;
    public TextField statistics_sort_text;
    public TextField statistics_output_file_text;
    public Choice predict_method_choice;
    public Checkbox predict_use_negations_check;
    public Checkbox predict_use_equivalences_check;
    public TextField predict_min_percent_text;
    public TextField predict_max_steps_text;
    public Button predict_all_button;
    public Vector predict_names_buttons_vector;
    public Vector predict_values_texts_vector;
    public Button predict_button;
    public Choice predict_entity_type_choice;
    public Choice predict_input_files_choice;
    public TextField predict_entity_text;
    public List predict_explanation_list;
    public Button predict_incremental_steps_button;
    public TextField predict_values_text1;
    public TextField predict_values_text2;
    public TextField predict_values_text3;
    public TextField predict_values_text4;
    public TextField predict_values_text5;
    public TextField predict_values_text6;
    public TextField predict_values_text7;
    public TextField predict_values_text8;
    public TextField predict_values_text9;
    public TextField predict_values_text10;
    public TextField predict_values_text11;
    public TextField predict_values_text12;
    public TextField predict_values_text13;
    public TextField predict_values_text14;
    public TextField predict_values_text15;
    public TextField predict_values_text16;
    public TextField predict_values_text17;
    public TextField predict_values_text18;
    public TextField predict_values_text19;
    public TextField predict_values_text20;
    public TextField predict_values_text21;
    public TextField predict_values_text22;
    public TextField predict_values_text23;
    public TextField predict_values_text24;
    public TextField predict_values_text25;
    public TextField predict_values_text26;
    public TextField predict_values_text27;
    public TextField predict_values_text28;
    public TextField predict_values_text29;
    public TextField predict_values_text30;
    public TextField predict_values_text31;
    public TextField predict_values_text32;
    public TextField predict_values_text33;
    public TextField predict_values_text34;
    public TextField predict_values_text35;
    public TextField predict_values_text36;
    public TextField predict_values_text37;
    public TextField predict_values_text38;
    public TextField predict_values_text39;
    public TextField predict_values_text40;
    public Button predict_names_button1;
    public Button predict_names_button2;
    public Button predict_names_button3;
    public Button predict_names_button4;
    public Button predict_names_button5;
    public Button predict_names_button6;
    public Button predict_names_button7;
    public Button predict_names_button8;
    public Button predict_names_button9;
    public Button predict_names_button10;
    public Button predict_names_button11;
    public Button predict_names_button12;
    public Button predict_names_button13;
    public Button predict_names_button14;
    public Button predict_names_button15;
    public Button predict_names_button16;
    public Button predict_names_button17;
    public Button predict_names_button18;
    public Button predict_names_button19;
    public Button predict_names_button20;
    public Button predict_names_button21;
    public Button predict_names_button22;
    public Button predict_names_button23;
    public Button predict_names_button24;
    public Button predict_names_button25;
    public Button predict_names_button26;
    public Button predict_names_button27;
    public Button predict_names_button28;
    public Button predict_names_button29;
    public Button predict_names_button30;
    public Button predict_names_button31;
    public Button predict_names_button32;
    public Button predict_names_button33;
    public Button predict_names_button34;
    public Button predict_names_button35;
    public Button predict_names_button36;
    public Button predict_names_button37;
    public Button predict_names_button38;
    public Button predict_names_button39;
    public Button predict_names_button40;
    public TextArea help_text;
    public SortableList help_list;
    public Button save_help_button;
    public TextField help_save_file_text;
    public Button cull_population_button;
    public Checkbox use_evolution_check;
    public TextField evolve_population_size_text;
    public TextField evolve_number_of_population_text;
    public TextField cull_steps_text;
    public Choice quick_macro_choice;
    public Checkbox record_macro_check;
    public Button play_macro_button;
    public Button save_macro_button;
    public TextField save_macro_text;
    public JEditorPane hello_text;
    public SortableList macro_list;
    public TextArea macro_text;
    public TextField macro_error_text;
    public Timer timer;
    public List process_list;
    public HR main_frame;
    public Checkbox use_ground_instances_in_proving_check;
    public Checkbox use_entity_letter_in_proving_check;
    public CheckboxGroup proofs_group;
    public Checkbox use_mathweb_atp_service_check;
    public Checkbox mathweb_atp_require_all_check;
    public Checkbox use_mathweb_otter_check;
    public Checkbox expand_agenda_check;
    public Checkbox make_equivalences_from_equality_check;
    public Checkbox make_equivalences_from_combination_check;
    public Checkbox make_non_exists_from_empty_check;
    public Checkbox make_implicates_from_subsumes_check;
    public TextField force_conjecture_text;
    public Checkbox store_all_conjectures_check;
    public TextField store_all_conjectures_text;
    public Checkbox keep_equivalences_check;
    public TextField keep_equivalences_file_text;
    public TextField keep_equivalences_condition_text;
    public Checkbox require_proof_in_subsumption_check;
    public Checkbox keep_steps_check;
    public Checkbox keep_non_exists_check;
    public TextField keep_non_exists_file_text;
    public TextField keep_non_exists_condition_text;
    public Checkbox extract_implications_from_equivalences_check;
    public Checkbox extract_implicates_from_non_exists_check;
    public Checkbox keep_implications_check;
    public TextField keep_implications_file_text;
    public TextField keep_implications_condition_text;
    public Checkbox keep_implicates_check;
    public TextField keep_implicates_file_text;
    public TextField keep_implicates_condition_text;
    public Checkbox keep_prime_implicates_check;
    public TextField keep_prime_implicates_file_text;
    public TextField keep_prime_implicates_condition_text;
    public Checkbox make_implications_from_subsumptions_check;
    public Checkbox extract_prime_implicates_from_implicates_check;
    public Checkbox extract_implicates_from_equivalences_check;
    public JTabbedPane lakatos_tabbed_pane;
    public List agent_search_output_list;
    public Button agent_search_button;
    public TextField agent_search_text;
    public Checkbox use_surrender_check;
    public Checkbox use_strategic_withdrawal_check;
    public Checkbox use_piecemeal_exclusion_check;
    public Checkbox use_monster_barring_check;
    public Checkbox use_monster_adjusting_check;
    public Checkbox use_lemma_incorporation_check;
    public Checkbox use_proofs_and_refutations_check;
    public Checkbox teacher_requests_conjecture_check;
    public Checkbox teacher_requests_equivalence_check;
    public Checkbox teacher_requests_implication_check;
    public Checkbox teacher_requests_nonexists_check;
    public Checkbox teacher_requests_nearimplication_check;
    public Checkbox teacher_requests_nearequivalence_check;
    public Checkbox num_independent_steps_check;
    public TextField num_independent_steps_value_text;
    public Checkbox max_num_independent_work_stages_check;
    public Label max_num_independent_work_stages_value_label;
    public TextField max_num_independent_work_stages_value_text;
    public Checkbox threshold_to_add_conj_to_theory_check;
    public Label threshold_to_add_conj_to_theory_label;
    public TextField threshold_to_add_conj_to_theory_value_text;
    public Checkbox threshold_to_add_concept_to_theory_check;
    public Label threshold_to_add_concept_to_theory_label;
    public TextField threshold_to_add_concept_to_theory_value_text;
    public Checkbox use_communal_piecemeal_exclusion_check;
    public Checkbox number_modifications_check;
    public Checkbox interestingness_threshold_check;
    public Checkbox compare_average_interestingness_check;
    public Checkbox plausibility_threshold_check;
    public Checkbox domain_application_threshold_check;
    public Checkbox use_counterexample_barring_check;
    public Label modifications_value_label;
    public TextField modifications_value_text;
    public Label interestingness_threshold_value_label;
    public TextField interestingness_threshold_value_text;
    public Label plausibility_threshold_value_label;
    public TextField plausibility_threshold_value_text;
    public Label domain_app_threshold_value_label;
    public TextField domain_app_threshold_value_text;
    public Checkbox use_breaks_conj_under_discussion_check;
    public Checkbox accept_strictest_check;
    public Checkbox use_percentage_conj_broken_check;
    public Checkbox use_culprit_breaker_check;
    public Checkbox use_culprit_breaker_on_conj_check;
    public Checkbox use_culprit_breaker_on_all_check;
    public Label monster_barring_culprit_min_label;
    public TextField monster_barring_culprit_min_text;
    public Checkbox monster_barring_type_check;
    public Label monster_barring_type_label;
    public TextField monster_barring_type_text;
    public Label monster_barring_min_label;
    public TextField monster_barring_min_text;
    public Button start_button;
    public Button stop_button;
    public Button step_button;
    public Label status_label;
    public Button reset_concept_weights_button;
    public Button reset_conjecture_weights_button;
    public TextField concept_applicability_weight_text;
    public Label concept_applicability_weight_label;
    public TextField concept_coverage_weight_text;
    public Label concept_coverage_weight_label;
    public TextField conjecture_applicability_weight_text;
    public Label conjecture_applicability_weight_label;
    public TextField concept_comprehensibility_weight_text;
    public Label concept_comprehensibility_weight_label;
    public TextField concept_equiv_conj_score_weight_text;
    public Label concept_equiv_conj_score_weight_label;
    public TextField concept_equiv_conj_num_weight_text;
    public TextField concept_pi_conj_score_weight_text;
    public Label concept_pi_conj_score_weight_label;
    public TextField concept_pi_conj_num_weight_text;
    public TextField concept_ne_conj_score_weight_text;
    public Label concept_ne_conj_score_weight_label;
    public TextField concept_ne_conj_num_weight_text;
    public TextField concept_imp_conj_score_weight_text;
    public Label concept_imp_conj_score_weight_label;
    public TextField concept_imp_conj_num_weight_text;
    public TextField conjecture_comprehensibility_weight_text;
    public Label conjecture_comprehensibility_weight_label;
    public TextField concept_cross_domain_weight_text;
    public Label concept_cross_domain_weight_label;
    public TextField concept_highlight_weight_text;
    public Label concept_highlight_weight_label;
    public TextField concept_novelty_weight_text;
    public Label concept_novelty_weight_label;
    public TextField concept_parent_weight_text;
    public Label concept_parent_weight_label;
    public TextField concept_children_weight_text;
    public Label concept_children_weight_label;
    public TextField concept_parsimony_weight_text;
    public Label concept_parsimony_weight_label;
    public TextField concept_predictive_power_weight_text;
    public Label concept_predictive_power_weight_label;
    public TextField concept_productivity_weight_text;
    public TextField concept_default_productivity_text;
    public Label concept_productivity_weight_label;
    public TextField concept_development_steps_num_weight_text;
    public Label concept_development_steps_num_weight_label;
    public TextField conjecture_surprisingness_weight_text;
    public Label conjecture_surprisingness_weight_label;
    public TextField conjecture_plausibility_weight_text;
    public Label conjecture_plausibility_weight_label;
    public TextField concept_variety_weight_text;
    public Label concept_variety_weight_label;
    public TextField concept_invariance_weight_text;
    public Label concept_invariance_weight_label;
    public Checkbox use_forward_lookahead_check;
    public Checkbox normalise_concept_measures_check;
    public TextField concept_discrimination_weight_text;
    public Label concept_discrimination_weight_label;
    public TextField concept_positive_applicability_weight_text;
    public Label concept_positive_applicability_weight_label;
    public TextField concept_negative_applicability_weight_text;
    public Label concept_negative_applicability_weight_label;
    public Checkbox measure_concepts_check;
    public Button measure_concepts_button;
    public Checkbox measure_conjectures_check;
    public Button measure_conjectures_button;
    public Checkbox update_front_end_check;
    public Button update_front_end_button;
    public Label complexity_label;
    public TextField complexity_text;
    public Choice required_choice;
    public TextField required_text;
    public Label otter_time_limit_label;
    public TextField otter_time_limit_text;
    public Label near_equivalence_percent_label;
    public TextField near_equivalence_percent_text;
    public Label near_implication_percent_label;
    public TextField near_implication_percent_text;
    public Label interestingness_zero_min_label;
    public TextField interestingness_zero_min_text;
    public Label best_first_delay_label;
    public TextField best_first_delay_text;
    public Checkbox use_counter_barring_check;
    public Checkbox use_concept_barring_check;
    public TextField counterexample_barring_num_text;
    public TextField concept_barring_num_text;
    public Checkbox use_applicability_conj_check;
    public TextField applicability_conj_num_text;
    public Label agenda_limit_label;
    public TextField agenda_limit_text;
    public Button arity_limit_button;
    public TextField arity_limit_text;
    public TextField arithmeticb_operators_text;
    public Label arithmeticb_operators_label;
    public Label tuple_product_limit_label;
    public TextField tuple_product_limit_text;
    public Label compose_time_limit_label;
    public TextField compose_time_limit_text;
    public CheckboxGroup search_group;
    public Checkbox use_split_empirically_for_learning_check;
    public Checkbox use_split_empirically_check;
    public Checkbox make_near_equivalences_check;
    public Checkbox make_near_implications_check;
    public Checkbox substitute_definitions_check;
    public Checkbox depth_first_check;
    public Checkbox breadth_first_check;
    public Checkbox weighted_sum_check;
    public Checkbox keep_worst_check;
    public Checkbox keep_best_check;
    public Checkbox random_check;
    public Checkbox subobject_overlap_check;
    public Label domain_label;
    public SortableList domain_list;
    public Button domain_none_button;
    public Button domain_default_button;
    public Label initial_entity_label;
    public List initial_entity_list;
    public Button initial_entity_none_button;
    public Button initial_entity_all_button;
    public Label statistics_label1;
    public Label statistics_label2;
    public Label statistics_label3;
    public Choice statistics_choice1;
    public Choice statistics_choice2;
    public Choice statistics_choice3;
    public Label highlight_label;
    public List highlight_list;
    public Button highlight_none_button;
    public Button highlight_all_button;
    public Label counterexample_label;
    public List counterexample_list;
    public Button counterexample_none_button;
    public Button counterexample_all_button;
    public Label initial_concepts_label;
    public List initial_concepts_list;
    public Button initial_concepts_none_button;
    public Button initial_concepts_all_button;
    public Checkbox arithmeticb_check;
    public Checkbox compose_check;
    public Checkbox disjunct_check;
    public Checkbox embed_algebra_check;
    public Checkbox embed_graph_check;
    public Checkbox entity_disjunct_check;
    public Checkbox exists_check;
    public Checkbox equal_check;
    public Checkbox forall_check;
    public Checkbox match_check;
    public Checkbox negate_check;
    public Checkbox record_check;
    public Checkbox size_check;
    public Checkbox split_check;
    public TextField compose_arity_limit_text;
    public TextField disjunct_arity_limit_text;
    public TextField embed_graph_arity_limit_text;
    public TextField embed_algebra_arity_limit_text;
    public TextField exists_arity_limit_text;
    public TextField entity_disjunct_arity_limit_text;
    public TextField arithmeticb_arity_limit_text;
    public TextField equal_arity_limit_text;
    public TextField forall_arity_limit_text;
    public TextField match_arity_limit_text;
    public TextField negate_arity_limit_text;
    public TextField record_arity_limit_text;
    public TextField size_arity_limit_text;
    public TextField split_arity_limit_text;
    public TextField compose_tier_text;
    public TextField disjunct_tier_text;
    public TextField embed_graph_tier_text;
    public TextField embed_algebra_tier_text;
    public TextField exists_tier_text;
    public TextField entity_disjunct_tier_text;
    public TextField arithmeticb_tier_text;
    public TextField equal_tier_text;
    public TextField forall_tier_text;
    public TextField match_tier_text;
    public TextField negate_tier_text;
    public TextField record_tier_text;
    public TextField size_tier_text;
    public TextField split_tier_text;
    public Label split_values_label;
    public List split_values_list;
    public Button split_values_none_button;
    public Button split_values_all_button;
    public List ancestor_list;
    public List children_list;
    public Button find_extra_conjectures_button;
    public Button calculate_button;
    public TextField calculate_entity1_text;
    public TextField calculate_entity2_text;
    public TextField calculate_output_text;
    public Button test_conjecture_button;
    public TextField test_conjecture_entity1_text;
    public TextField test_conjecture_entity2_text;
    public TextField test_conjecture_output_text;
    public Button add_counterexamples_found_from_testing_button;
    public List agenda_list;
    public Button update_agenda_button;
    public Button show_forced_steps_button;
    public Checkbox auto_update_agenda;
    public TextField auto_update_text;
    public List live_ordered_concept_list;
    public List all_ordered_concept_list;
    public Checkbox not_allowed_agenda_check;
    public TextField current_step_text;
    public TextField agenda_concept_text;
    public Vector ids;
    public Vector statistics;
    public Vector entities;
    public Vector concepts;
    public Vector conjectures;
    Read reader;
    public Agenda agenda;
    public Vector equivalences;
    public Vector non_existences;
    public Vector implicates;
    public JEditorPane concept_text_box;
    public JEditorPane conjecture_text_box;
    public List entity_list;
    public Button percent_categorisation_button;
    public TextField percent_categorisation_text;
    public TextField percent_categorisation_percent_text;
    public Button percent_up_button;
    public Button percent_down_button;
    public List entity_sorting_list;
    public List entity_formatting_list;
    public List entity_pruning_list;
    public JEditorPane entity_text_box;
    public Button draw_concept_construction_history_button;
    public Button draw_conjecture_construction_history_button;
    public Button re_prove_all_button;
    public Button add_conjecture_as_axiom_button;
    public Button remove_conjecture_as_axiom_button;
    public SortableList concept_list;
    public Label concept_list_number;
    public TextField grep_concept_text;
    public Button grep_concept_button;
    public Button restore_concept_button;
    public Button condition_concept_button;
    public TextField condition_concept_text;
    public SortableList conjecture_list;
    public List parent_conjectures_list;
    public List child_conjectures_list;
    public Label conjecture_list_number;
    public Button grep_conjecture_button;
    public Button restore_conjecture_button;
    public TextField grep_conjecture_text;
    public Button condition_conjecture_button;
    public TextField condition_conjecture_text;
    public List concept_sorting_list;
    public List concept_pruning_list;
    public List concept_formatting_list;
    public List conjecture_sorting_list;
    public List conjecture_pruning_list;
    public List conjecture_formatting_list;
    public Categorisation user_chosen_categorisation;

    public FrontEnd() {
        this.file_prover_table = new JTable(this.file_prover_data, this.file_prover_headers);
        this.other_prover_data = new String[20][6];
        this.other_prover_headers = new String[]{"Try Position", "Prover Name", "Try Condition", "Algebra", "Execution Parameters", "Setup Name"};
        this.other_prover_table = new JTable(this.other_prover_data, this.other_prover_headers);
        this.algebra_data = new String[250][3];
        this.algebra_headers = new String[]{"Algebra Name", "Axioms in Algebra", "Embed Algebra"};
        this.algebra_table = new JTable(this.algebra_data, this.algebra_headers);
        this.axiom_data = new String[250][3];
        this.axiom_headers = new String[]{"Axiom Name", "Axioms String"};
        this.axiom_table = new JTable(this.axiom_data, this.axiom_headers);
        this.gold_standard_categorisation_text = new TextField(50);
        this.coverage_categorisation_text = new TextField(50);
        this.object_types_to_learn_text = new TextField(50);
        this.segregated_search_check = new Checkbox("segregated search", false);
        this.segregation_categorisation_text = new TextField(50);
        this.store_theory_name_text = new TextField(17);
        this.store_theory_button = new Button("Store theory");
        this.restore_theory_button = new Button("Restore theory");
        this.stored_theories_list = new SortableList();
        this.save_concept_construction_button = new Button("Store concept");
        this.save_concept_construction_filename_text = new TextField(17);
        this.save_concept_construction_concept_id_text = new TextField(5);
        this.build_concept_construction_concept_id_text = new TextField(5);
        this.build_concept_button = new Button("Build concept");
        this.build_concept_filename_text = new TextField(17);
        this.save_conjecture_construction_button = new Button("Store conjecture");
        this.save_conjecture_construction_filename_text = new TextField(17);
        this.save_conjecture_construction_conjecture_id_text = new TextField(5);
        this.build_conjecture_construction_conjecture_id_text = new TextField(5);
        this.build_conjecture_button = new Button("Build conjecture");
        this.build_conjecture_filename_text = new TextField(17);
        this.systemout_button = new Button("run function");
        this.debug_parameter_text = new TextField(25);
        this.systemout_text = new TextArea();
        this.reaction_name_text = new TextField(12);
        this.reactions_list = new SortableList();
        this.reactions_text = new TextArea();
        this.add_reaction_button = new Button("Add reaction");
        this.remove_reaction_button = new Button("Remove reaction");
        this.save_reaction_button = new Button("Save reaction");
        this.reactions_added_list = new List();
        this.puzzle_output_text = new TextArea();
        this.puzzle_generate_button = new Button("Generate");
        this.puzzle_ooo_choices_size_text = new TextField("4");
        this.puzzle_nis_choices_size_text = new TextField("7");
        this.puzzle_analogy_choices_size_text = new TextField("4");
        this.force_string_text = new TextField(57);
        this.force_primary_concept_list = new List();
        this.force_secondary_concept_list = new List();
        this.force_prodrule_list = new List();
        this.force_parameter_list = new List();
        this.force_button = new Button("Force Step");
        this.force_result_text = new TextArea();
        this.report_instructions_text = new TextArea(17, 30);
        this.report_output_text = new JEditorPane("text/html", "");
        this.execute_report_button = new Button("Execute reports");
        this.save_report_button = new Button("Save report");
        this.reports_to_execute_list = new SortableList(17);
        this.reports_list = new SortableList(17);
        this.save_report_name_text = new TextField(17);
        this.sot_conjecture_number_text = new TextField(4);
        this.sot_results_text = new TextArea();
        this.sot_submit_button = new Button("Submit");
        this.mathweb_inout_socket_label = new Label("              ");
        this.mathweb_service_socket_label = new Label("              ");
        this.mathweb_hostname_label = new Label("               ");
        this.use_e_in_mathweb_check = new Checkbox("E", true);
        this.use_bliksem_in_mathweb_check = new Checkbox("Bliksem", true);
        this.use_otter_in_mathweb_check = new Checkbox("Otter", true);
        this.use_spass_in_mathweb_check = new Checkbox("Spass", true);
        this.statistics_type_list = new List(8);
        this.statistics_choice_list = new SortableList(20);
        this.statistics_subchoice_list = new SortableList(20);
        this.statistics_methods_list = new SortableList(20);
        this.statistics_plot_button = new Button("Plot");
        this.statistics_compile_button = new Button("Compile");
        this.statistics_average_check = new Checkbox("average");
        this.statistics_seperate_graphs_check = new Checkbox("seperate graphs");
        this.statistics_count_check = new Checkbox("count values");
        this.statistics_calculation_text = new TextField("");
        this.statistics_prune_text = new TextField("");
        this.statistics_sort_text = new TextField("");
        this.statistics_output_file_text = new TextField("");
        this.predict_method_choice = new Choice();
        this.predict_use_negations_check = new Checkbox("negations", false);
        this.predict_use_equivalences_check = new Checkbox("equivalences", true);
        this.predict_min_percent_text = new TextField("0   ");
        this.predict_max_steps_text = new TextField("100000");
        this.predict_all_button = new Button("Predict all");
        this.predict_names_buttons_vector = new Vector();
        this.predict_values_texts_vector = new Vector();
        this.predict_button = new Button("Predict");
        this.predict_entity_type_choice = new Choice();
        this.predict_input_files_choice = new Choice();
        this.predict_entity_text = new TextField(10);
        this.predict_explanation_list = new List(10);
        this.predict_incremental_steps_button = new Button("Increment");
        this.predict_values_text1 = new TextField();
        this.predict_values_text2 = new TextField();
        this.predict_values_text3 = new TextField();
        this.predict_values_text4 = new TextField();
        this.predict_values_text5 = new TextField();
        this.predict_values_text6 = new TextField();
        this.predict_values_text7 = new TextField();
        this.predict_values_text8 = new TextField();
        this.predict_values_text9 = new TextField();
        this.predict_values_text10 = new TextField();
        this.predict_values_text11 = new TextField();
        this.predict_values_text12 = new TextField();
        this.predict_values_text13 = new TextField();
        this.predict_values_text14 = new TextField();
        this.predict_values_text15 = new TextField();
        this.predict_values_text16 = new TextField();
        this.predict_values_text17 = new TextField();
        this.predict_values_text18 = new TextField();
        this.predict_values_text19 = new TextField();
        this.predict_values_text20 = new TextField();
        this.predict_values_text21 = new TextField();
        this.predict_values_text22 = new TextField();
        this.predict_values_text23 = new TextField();
        this.predict_values_text24 = new TextField();
        this.predict_values_text25 = new TextField();
        this.predict_values_text26 = new TextField();
        this.predict_values_text27 = new TextField();
        this.predict_values_text28 = new TextField();
        this.predict_values_text29 = new TextField();
        this.predict_values_text30 = new TextField();
        this.predict_values_text31 = new TextField();
        this.predict_values_text32 = new TextField();
        this.predict_values_text33 = new TextField();
        this.predict_values_text34 = new TextField();
        this.predict_values_text35 = new TextField();
        this.predict_values_text36 = new TextField();
        this.predict_values_text37 = new TextField();
        this.predict_values_text38 = new TextField();
        this.predict_values_text39 = new TextField();
        this.predict_values_text40 = new TextField();
        this.predict_names_button1 = new Button("                                ");
        this.predict_names_button2 = new Button("                                ");
        this.predict_names_button3 = new Button("                                ");
        this.predict_names_button4 = new Button("                                ");
        this.predict_names_button5 = new Button("                                ");
        this.predict_names_button6 = new Button("                                ");
        this.predict_names_button7 = new Button("                                ");
        this.predict_names_button8 = new Button("                                ");
        this.predict_names_button9 = new Button("                                ");
        this.predict_names_button10 = new Button("                                ");
        this.predict_names_button11 = new Button("                                ");
        this.predict_names_button12 = new Button("                                ");
        this.predict_names_button13 = new Button("                                ");
        this.predict_names_button14 = new Button("                                ");
        this.predict_names_button15 = new Button("                                ");
        this.predict_names_button16 = new Button("                                ");
        this.predict_names_button17 = new Button("                                ");
        this.predict_names_button18 = new Button("                                ");
        this.predict_names_button19 = new Button("                                ");
        this.predict_names_button20 = new Button("                                ");
        this.predict_names_button21 = new Button("                                ");
        this.predict_names_button22 = new Button("                                ");
        this.predict_names_button23 = new Button("                                ");
        this.predict_names_button24 = new Button("                                ");
        this.predict_names_button25 = new Button("                                ");
        this.predict_names_button26 = new Button("                                ");
        this.predict_names_button27 = new Button("                                ");
        this.predict_names_button28 = new Button("                                ");
        this.predict_names_button29 = new Button("                                ");
        this.predict_names_button30 = new Button("                                ");
        this.predict_names_button31 = new Button("                                ");
        this.predict_names_button32 = new Button("                                ");
        this.predict_names_button33 = new Button("                                ");
        this.predict_names_button34 = new Button("                                ");
        this.predict_names_button35 = new Button("                                ");
        this.predict_names_button36 = new Button("                                ");
        this.predict_names_button37 = new Button("                                ");
        this.predict_names_button38 = new Button("                                ");
        this.predict_names_button39 = new Button("                                ");
        this.predict_names_button40 = new Button("                                ");
        this.help_text = new TextArea(50, 50);
        this.help_list = new SortableList();
        this.save_help_button = new Button("Save help");
        this.help_save_file_text = new TextField(50);
        this.cull_population_button = new Button("Cull");
        this.use_evolution_check = new Checkbox("Use evolution", false);
        this.evolve_population_size_text = new TextField("0");
        this.evolve_number_of_population_text = new TextField("0");
        this.cull_steps_text = new TextField("0");
        this.quick_macro_choice = new Choice();
        this.record_macro_check = new Checkbox("record");
        this.play_macro_button = new Button("Play macro");
        this.save_macro_button = new Button("Save");
        this.save_macro_text = new TextField(12);
        this.hello_text = new JEditorPane("text/html", "");
        this.macro_list = new SortableList(20);
        this.macro_text = new TextArea(50, 50);
        this.macro_error_text = new TextField(50);
        this.timer = new Timer();
        this.process_list = new List();
        this.main_frame = null;
        this.use_ground_instances_in_proving_check = new Checkbox("ground in proving", false);
        this.use_entity_letter_in_proving_check = new Checkbox("entity in proving", false);
        this.proofs_group = new CheckboxGroup();
        this.use_mathweb_atp_service_check = new Checkbox("use mathweb atp", this.proofs_group, false);
        this.mathweb_atp_require_all_check = new Checkbox("require all atp", false);
        this.use_mathweb_otter_check = new Checkbox("use mathweb otter", this.proofs_group, false);
        this.expand_agenda_check = new Checkbox("expand agenda", true);
        this.make_equivalences_from_equality_check = new Checkbox("equiv from equality", true);
        this.make_equivalences_from_combination_check = new Checkbox("equiv from combination", false);
        this.make_non_exists_from_empty_check = new Checkbox("non-exists from empty", true);
        this.make_implicates_from_subsumes_check = new Checkbox("implicates from subsumes", false);
        this.force_conjecture_text = new TextField(1);
        this.store_all_conjectures_check = new Checkbox("store all conjectures", false);
        this.store_all_conjectures_text = new TextField(17);
        this.keep_equivalences_check = new Checkbox("keep equivalences", true);
        this.keep_equivalences_file_text = new TextField(17);
        this.keep_equivalences_condition_text = new TextField(17);
        this.require_proof_in_subsumption_check = new Checkbox("require proof (subsume)", true);
        this.keep_steps_check = new Checkbox("keep steps", true);
        this.keep_non_exists_check = new Checkbox("keep non-exists", true);
        this.keep_non_exists_file_text = new TextField(17);
        this.keep_non_exists_condition_text = new TextField(17);
        this.extract_implications_from_equivalences_check = new Checkbox("implication from equiv", false);
        this.extract_implicates_from_non_exists_check = new Checkbox("implicate from non-exists", false);
        this.keep_implications_check = new Checkbox("keep implications", true);
        this.keep_implications_file_text = new TextField(17);
        this.keep_implications_condition_text = new TextField(17);
        this.keep_implicates_check = new Checkbox("keep implicates", true);
        this.keep_implicates_file_text = new TextField(17);
        this.keep_implicates_condition_text = new TextField(17);
        this.keep_prime_implicates_check = new Checkbox("keep prime implicates", true);
        this.keep_prime_implicates_file_text = new TextField(17);
        this.keep_prime_implicates_condition_text = new TextField(17);
        this.make_implications_from_subsumptions_check = new Checkbox("implication from subsume", false);
        this.extract_prime_implicates_from_implicates_check = new Checkbox("implicate from prime", false);
        this.extract_implicates_from_equivalences_check = new Checkbox("implicate from equivalences", false);
        this.lakatos_tabbed_pane = new JTabbedPane();
        this.agent_search_output_list = new List(10);
        this.agent_search_button = new Button("Search");
        this.agent_search_text = new TextField(30);
        this.use_surrender_check = new Checkbox("surrender", false);
        this.use_strategic_withdrawal_check = new Checkbox("strategic withdrawal", false);
        this.use_piecemeal_exclusion_check = new Checkbox("piecemeal exclusion", false);
        this.use_monster_barring_check = new Checkbox("monster barring", false);
        this.use_monster_adjusting_check = new Checkbox("monster adjusting", false);
        this.use_lemma_incorporation_check = new Checkbox("lemma incorporation", false);
        this.use_proofs_and_refutations_check = new Checkbox("proofs and refutations", false);
        this.teacher_requests_conjecture_check = new Checkbox("conjecture", false);
        this.teacher_requests_equivalence_check = new Checkbox("equivalence", false);
        this.teacher_requests_implication_check = new Checkbox("implication", false);
        this.teacher_requests_nonexists_check = new Checkbox("nonexists", false);
        this.teacher_requests_nearimplication_check = new Checkbox("nearimplication", false);
        this.teacher_requests_nearequivalence_check = new Checkbox("nearequivalence", false);
        this.num_independent_steps_check = new Checkbox("length independent work phase", true);
        this.num_independent_steps_value_text = new TextField("10");
        this.max_num_independent_work_stages_check = new Checkbox("number independent work phases", true);
        this.max_num_independent_work_stages_value_label = new Label("number independent work phases");
        this.max_num_independent_work_stages_value_text = new TextField("10");
        this.threshold_to_add_conj_to_theory_check = new Checkbox("t'hold to add conj to theory", false);
        this.threshold_to_add_conj_to_theory_label = new Label("t'hold to add conj to theory");
        this.threshold_to_add_conj_to_theory_value_text = new TextField("0.0");
        this.threshold_to_add_concept_to_theory_check = new Checkbox("t'hold to add concept to theory", false);
        this.threshold_to_add_concept_to_theory_label = new Label("t'hold to add concept to theory");
        this.threshold_to_add_concept_to_theory_value_text = new TextField("0.0");
        this.use_communal_piecemeal_exclusion_check = new Checkbox("communal piecemeal exclusion", false);
        this.number_modifications_check = new Checkbox("number modifications", false);
        this.interestingness_threshold_check = new Checkbox("interestingness threshold", false);
        this.compare_average_interestingness_check = new Checkbox("compare average interestingness", false);
        this.plausibility_threshold_check = new Checkbox("plausibility threshold", false);
        this.domain_application_threshold_check = new Checkbox("domain of application threshold", false);
        this.use_counterexample_barring_check = new Checkbox("use counterexample barring", false);
        this.modifications_value_label = new Label("num modifications");
        this.modifications_value_text = new TextField("0");
        this.interestingness_threshold_value_label = new Label("int t'hold");
        this.interestingness_threshold_value_text = new TextField("0.0");
        this.plausibility_threshold_value_label = new Label("plaus t'hold");
        this.plausibility_threshold_value_text = new TextField("0.0");
        this.domain_app_threshold_value_label = new Label("dom appln t'hold");
        this.domain_app_threshold_value_text = new TextField("0.0");
        this.use_breaks_conj_under_discussion_check = new Checkbox("use breaks conj", false);
        this.accept_strictest_check = new Checkbox("accept strictest", false);
        this.use_percentage_conj_broken_check = new Checkbox("use % of conjs broken", false);
        this.use_culprit_breaker_check = new Checkbox("use culprit breaker", false);
        this.use_culprit_breaker_on_conj_check = new Checkbox("use culprit breaker on one conj", false);
        this.use_culprit_breaker_on_all_check = new Checkbox("use culprit breaker on all conj", false);
        this.monster_barring_culprit_min_label = new Label("monster barring culprit min");
        this.monster_barring_culprit_min_text = new TextField("0.0");
        this.monster_barring_type_check = new Checkbox("monster barring type", true);
        this.monster_barring_type_label = new Label("monster barring type");
        this.monster_barring_type_text = new TextField("vaguetospecific");
        this.monster_barring_min_label = new Label("monster barring min");
        this.monster_barring_min_text = new TextField("0");
        this.start_button = new Button("Load");
        this.stop_button = new Button("Stop");
        this.step_button = new Button("Step");
        this.status_label = new Label("Waiting");
        this.reset_concept_weights_button = new Button("Reset");
        this.reset_conjecture_weights_button = new Button("Reset");
        this.concept_applicability_weight_text = new TextField("0.0");
        this.concept_applicability_weight_label = new Label("applicability");
        this.concept_coverage_weight_text = new TextField("0.0");
        this.concept_coverage_weight_label = new Label("coverage");
        this.conjecture_applicability_weight_text = new TextField("0.0");
        this.conjecture_applicability_weight_label = new Label("applicability");
        this.concept_comprehensibility_weight_text = new TextField("0.0");
        this.concept_comprehensibility_weight_label = new Label("comprehensibility");
        this.concept_equiv_conj_score_weight_text = new TextField("0.0");
        this.concept_equiv_conj_score_weight_label = new Label("equiv score/num");
        this.concept_equiv_conj_num_weight_text = new TextField("0.0");
        this.concept_pi_conj_score_weight_text = new TextField("0.0");
        this.concept_pi_conj_score_weight_label = new Label("pi score/num");
        this.concept_pi_conj_num_weight_text = new TextField("0.0");
        this.concept_ne_conj_score_weight_text = new TextField("0.0");
        this.concept_ne_conj_score_weight_label = new Label("nex score/num");
        this.concept_ne_conj_num_weight_text = new TextField("0.0");
        this.concept_imp_conj_score_weight_text = new TextField("0.0");
        this.concept_imp_conj_score_weight_label = new Label("imp score/num");
        this.concept_imp_conj_num_weight_text = new TextField("0.0");
        this.conjecture_comprehensibility_weight_text = new TextField("0.0");
        this.conjecture_comprehensibility_weight_label = new Label("comprehensibility");
        this.concept_cross_domain_weight_text = new TextField("0.0");
        this.concept_cross_domain_weight_label = new Label("cross domain");
        this.concept_highlight_weight_text = new TextField("0.0");
        this.concept_highlight_weight_label = new Label("highlight");
        this.concept_novelty_weight_text = new TextField("0.0");
        this.concept_novelty_weight_label = new Label("novelty");
        this.concept_parent_weight_text = new TextField("0.0");
        this.concept_parent_weight_label = new Label("parents");
        this.concept_children_weight_text = new TextField("0.0");
        this.concept_children_weight_label = new Label("children");
        this.concept_parsimony_weight_text = new TextField("0.0");
        this.concept_parsimony_weight_label = new Label("parsimony");
        this.concept_predictive_power_weight_text = new TextField("0.0");
        this.concept_predictive_power_weight_label = new Label("pred power");
        this.concept_productivity_weight_text = new TextField("0.0");
        this.concept_default_productivity_text = new TextField("0.0");
        this.concept_productivity_weight_label = new Label("productivity/def");
        this.concept_development_steps_num_weight_text = new TextField("0.0");
        this.concept_development_steps_num_weight_label = new Label("devel steps");
        this.conjecture_surprisingness_weight_text = new TextField("0.0");
        this.conjecture_surprisingness_weight_label = new Label("surprisingness");
        this.conjecture_plausibility_weight_text = new TextField("0.0");
        this.conjecture_plausibility_weight_label = new Label("plausibility");
        this.concept_variety_weight_text = new TextField("0.0");
        this.concept_variety_weight_label = new Label("variety");
        this.concept_invariance_weight_text = new TextField("0.0");
        this.concept_invariance_weight_label = new Label("invariance");
        this.use_forward_lookahead_check = new Checkbox("forward look ahead");
        this.normalise_concept_measures_check = new Checkbox("Normalise", true);
        this.concept_discrimination_weight_text = new TextField("0.0");
        this.concept_discrimination_weight_label = new Label("discrimination");
        this.concept_positive_applicability_weight_text = new TextField("0.0");
        this.concept_positive_applicability_weight_label = new Label("pos applic");
        this.concept_negative_applicability_weight_text = new TextField("0.0");
        this.concept_negative_applicability_weight_label = new Label("neg applic");
        this.measure_concepts_check = new Checkbox("", true);
        this.measure_concepts_button = new Button("Measure concepts");
        this.measure_conjectures_check = new Checkbox("", true);
        this.measure_conjectures_button = new Button("Measure conjectures");
        this.update_front_end_check = new Checkbox("", true);
        this.update_front_end_button = new Button("Update Front End");
        this.complexity_label = new Label("complexity limit:");
        this.complexity_text = new TextField("6", 3);
        this.required_choice = new Choice();
        this.required_text = new TextField("1000");
        this.otter_time_limit_label = new Label("otter time:");
        this.otter_time_limit_text = new TextField("10");
        this.near_equivalence_percent_label = new Label("near equiv %");
        this.near_equivalence_percent_text = new TextField("100");
        this.near_implication_percent_label = new Label("near imp %");
        this.near_implication_percent_text = new TextField("100");
        this.interestingness_zero_min_label = new Label("interest to 0 min %");
        this.interestingness_zero_min_text = new TextField("0");
        this.best_first_delay_label = new Label("best first delay");
        this.best_first_delay_text = new TextField("0");
        this.use_counter_barring_check = new Checkbox("counter barring");
        this.use_concept_barring_check = new Checkbox("concept barring");
        this.counterexample_barring_num_text = new TextField(4);
        this.concept_barring_num_text = new TextField(4);
        this.use_applicability_conj_check = new Checkbox("applicability");
        this.applicability_conj_num_text = new TextField(4);
        this.agenda_limit_label = new Label("agenda limit:");
        this.agenda_limit_text = new TextField("100000");
        this.arity_limit_button = new Button("arity limit:");
        this.arity_limit_text = new TextField("3");
        this.arithmeticb_operators_text = new TextField("+*d");
        this.arithmeticb_operators_label = new Label("arithmeticb operators");
        this.tuple_product_limit_label = new Label("t-prod limit");
        this.tuple_product_limit_text = new TextField("1000000");
        this.compose_time_limit_label = new Label("comp time limit");
        this.compose_time_limit_text = new TextField("10");
        this.search_group = new CheckboxGroup();
        this.use_split_empirically_for_learning_check = new Checkbox("split empirical", true);
        this.use_split_empirically_check = new Checkbox("split empirical", false);
        this.make_near_equivalences_check = new Checkbox("make near-equivs", false);
        this.make_near_implications_check = new Checkbox("make near-imps", false);
        this.substitute_definitions_check = new Checkbox("substitute definitions", false);
        this.depth_first_check = new Checkbox("depth first", this.search_group, false);
        this.breadth_first_check = new Checkbox("breadth first", this.search_group, true);
        this.weighted_sum_check = new Checkbox("weighted_sum", this.search_group, false);
        this.keep_worst_check = new Checkbox("keep worst", this.search_group, false);
        this.keep_best_check = new Checkbox("keep best", this.search_group, false);
        this.random_check = new Checkbox("random", this.search_group, false);
        this.subobject_overlap_check = new Checkbox("subobject overlap", true);
        this.domain_label = new Label("domains:");
        this.domain_list = new SortableList(0, false);
        this.domain_none_button = new Button("None");
        this.domain_default_button = new Button("Default");
        this.initial_entity_label = new Label("entities:");
        this.initial_entity_list = new List();
        this.initial_entity_none_button = new Button("None");
        this.initial_entity_all_button = new Button("All");
        this.statistics_label1 = new Label("           ");
        this.statistics_label2 = new Label("           ");
        this.statistics_label3 = new Label("           ");
        this.statistics_choice1 = new Choice();
        this.statistics_choice2 = new Choice();
        this.statistics_choice3 = new Choice();
        this.highlight_label = new Label("highlight:");
        this.highlight_list = new List();
        this.highlight_none_button = new Button("None");
        this.highlight_all_button = new Button("All");
        this.counterexample_label = new Label("counterexamples:");
        this.counterexample_list = new List();
        this.counterexample_none_button = new Button("None");
        this.counterexample_all_button = new Button("All");
        this.initial_concepts_label = new Label("initial concepts:");
        this.initial_concepts_list = new List();
        this.initial_concepts_none_button = new Button("None");
        this.initial_concepts_all_button = new Button("All");
        this.arithmeticb_check = new Checkbox("arithmeticb", true);
        this.compose_check = new Checkbox("compose", true);
        this.disjunct_check = new Checkbox("disjunct", true);
        this.embed_algebra_check = new Checkbox("embed_algebra", true);
        this.embed_graph_check = new Checkbox("embed_graph", true);
        this.entity_disjunct_check = new Checkbox("entity_disjunct", true);
        this.exists_check = new Checkbox("exists", true);
        this.equal_check = new Checkbox("equal", true);
        this.forall_check = new Checkbox("forall", true);
        this.match_check = new Checkbox("match", true);
        this.negate_check = new Checkbox("negate", true);
        this.record_check = new Checkbox("record", true);
        this.size_check = new Checkbox("size", true);
        this.split_check = new Checkbox("split", true);
        this.compose_arity_limit_text = new TextField("4");
        this.disjunct_arity_limit_text = new TextField("4");
        this.embed_graph_arity_limit_text = new TextField("4");
        this.embed_algebra_arity_limit_text = new TextField("4");
        this.exists_arity_limit_text = new TextField("4");
        this.entity_disjunct_arity_limit_text = new TextField("4");
        this.arithmeticb_arity_limit_text = new TextField("4");
        this.equal_arity_limit_text = new TextField("4");
        this.forall_arity_limit_text = new TextField("4");
        this.match_arity_limit_text = new TextField("4");
        this.negate_arity_limit_text = new TextField("4");
        this.record_arity_limit_text = new TextField("4");
        this.size_arity_limit_text = new TextField("4");
        this.split_arity_limit_text = new TextField("4");
        this.compose_tier_text = new TextField("0");
        this.disjunct_tier_text = new TextField("0");
        this.embed_graph_tier_text = new TextField("0");
        this.embed_algebra_tier_text = new TextField("0");
        this.exists_tier_text = new TextField("0");
        this.entity_disjunct_tier_text = new TextField("0");
        this.arithmeticb_tier_text = new TextField("0");
        this.equal_tier_text = new TextField("0");
        this.forall_tier_text = new TextField("0");
        this.match_tier_text = new TextField("0");
        this.negate_tier_text = new TextField("0");
        this.record_tier_text = new TextField("0");
        this.size_tier_text = new TextField("0");
        this.split_tier_text = new TextField("0");
        this.split_values_label = new Label("split values:");
        this.split_values_list = new List();
        this.split_values_none_button = new Button("None");
        this.split_values_all_button = new Button("All");
        this.ancestor_list = new List();
        this.children_list = new List();
        this.find_extra_conjectures_button = new Button("Extra conjs");
        this.calculate_button = new Button("Calculate");
        this.calculate_entity1_text = new TextField("");
        this.calculate_entity2_text = new TextField("");
        this.calculate_output_text = new TextField("");
        this.test_conjecture_button = new Button("Test");
        this.test_conjecture_entity1_text = new TextField("lh test");
        this.test_conjecture_entity2_text = new TextField("rh test");
        this.test_conjecture_output_text = new TextField("output from test...      ");
        this.add_counterexamples_found_from_testing_button = new Button("Add");
        this.agenda_list = new List();
        this.update_agenda_button = new Button("Update");
        this.show_forced_steps_button = new Button("Show Forced");
        this.auto_update_agenda = new Checkbox("auto update", false);
        this.auto_update_text = new TextField("20");
        this.live_ordered_concept_list = new List();
        this.all_ordered_concept_list = new List();
        this.not_allowed_agenda_check = new Checkbox("not allowed", false);
        this.current_step_text = new TextField("                                         ");
        this.agenda_concept_text = new TextField("");
        this.ids = new Vector();
        this.statistics = new Vector();
        this.entities = new Vector();
        this.concepts = new Vector();
        this.conjectures = new Vector();
        this.reader = new Read();
        this.agenda = new Agenda();
        this.equivalences = new Vector();
        this.non_existences = new Vector();
        this.implicates = new Vector();
        this.concept_text_box = new JEditorPane("text/html", "");
        this.conjecture_text_box = new JEditorPane("text/html", "");
        this.entity_list = new List();
        this.percent_categorisation_button = new Button("%Cat");
        this.percent_categorisation_text = new TextField();
        this.percent_categorisation_percent_text = new TextField(50);
        this.percent_up_button = new Button(">");
        this.percent_down_button = new Button("<");
        this.entity_sorting_list = new List();
        this.entity_formatting_list = new List();
        this.entity_pruning_list = new List();
        this.entity_text_box = new JEditorPane("text/html", "");
        this.draw_concept_construction_history_button = new Button("Draw graph");
        this.draw_conjecture_construction_history_button = new Button("Draw graph");
        this.re_prove_all_button = new Button("Re-prove all");
        this.add_conjecture_as_axiom_button = new Button("Add as axiom");
        this.remove_conjecture_as_axiom_button = new Button("Remove as axiom");
        this.concept_list = new SortableList();
        this.concept_list_number = new Label("");
        this.grep_concept_text = new TextField();
        this.grep_concept_button = new Button("Grep");
        this.restore_concept_button = new Button("Restore");
        this.condition_concept_button = new Button("Condition");
        this.condition_concept_text = new TextField();
        this.conjecture_list = new SortableList();
        this.parent_conjectures_list = new List();
        this.child_conjectures_list = new List();
        this.conjecture_list_number = new Label("");
        this.grep_conjecture_button = new Button("Grep");
        this.restore_conjecture_button = new Button("Restore");
        this.grep_conjecture_text = new TextField();
        this.condition_conjecture_button = new Button("Condition");
        this.condition_conjecture_text = new TextField();
        this.concept_sorting_list = new List();
        this.concept_pruning_list = new List();
        this.concept_formatting_list = new List();
        this.conjecture_sorting_list = new List();
        this.conjecture_pruning_list = new List();
        this.conjecture_formatting_list = new List();
        this.user_chosen_categorisation = null;
    }

    public void init() {
        this.concept_list.sort_items = false;
        this.conjecture_list.sort_items = false;
        this.statistics_subchoice_list.setMultipleMode(true);
        this.statistics_choice_list.setMultipleMode(true);
        this.entity_formatting_list.setMultipleMode(true);
        this.conjecture_formatting_list.setMultipleMode(true);
        this.concept_formatting_list.setMultipleMode(true);
        this.reports_to_execute_list.setMultipleMode(true);
        this.highlight_list.setMultipleMode(true);
        this.concept_pruning_list.setMultipleMode(true);
        this.conjecture_pruning_list.setMultipleMode(true);
        this.initial_concepts_list.setMultipleMode(true);
        this.split_values_list.setMultipleMode(true);
        this.initial_entity_list.setMultipleMode(true);
        this.counterexample_list.setMultipleMode(true);
        this.status_label.setAlignment(0);
        this.predict_entity_type_choice.add("                          ");
        this.predict_method_choice.add("bayesian average");
        this.predict_method_choice.add("max near-equiv");
        this.predict_method_choice.add("average near-equiv");
        this.predict_method_choice.add("max near-imp");
        this.predict_method_choice.add("average near-imp");
        this.predict_names_buttons_vector.removeAllElements();
        this.predict_values_texts_vector.removeAllElements();
        this.predict_names_buttons_vector.addElement(this.predict_names_button1);
        this.predict_names_buttons_vector.addElement(this.predict_names_button2);
        this.predict_names_buttons_vector.addElement(this.predict_names_button3);
        this.predict_names_buttons_vector.addElement(this.predict_names_button4);
        this.predict_names_buttons_vector.addElement(this.predict_names_button5);
        this.predict_names_buttons_vector.addElement(this.predict_names_button6);
        this.predict_names_buttons_vector.addElement(this.predict_names_button7);
        this.predict_names_buttons_vector.addElement(this.predict_names_button8);
        this.predict_names_buttons_vector.addElement(this.predict_names_button9);
        this.predict_names_buttons_vector.addElement(this.predict_names_button10);
        this.predict_names_buttons_vector.addElement(this.predict_names_button11);
        this.predict_names_buttons_vector.addElement(this.predict_names_button12);
        this.predict_names_buttons_vector.addElement(this.predict_names_button13);
        this.predict_names_buttons_vector.addElement(this.predict_names_button14);
        this.predict_names_buttons_vector.addElement(this.predict_names_button15);
        this.predict_names_buttons_vector.addElement(this.predict_names_button16);
        this.predict_names_buttons_vector.addElement(this.predict_names_button17);
        this.predict_names_buttons_vector.addElement(this.predict_names_button18);
        this.predict_names_buttons_vector.addElement(this.predict_names_button19);
        this.predict_names_buttons_vector.addElement(this.predict_names_button20);
        this.predict_names_buttons_vector.addElement(this.predict_names_button21);
        this.predict_names_buttons_vector.addElement(this.predict_names_button22);
        this.predict_names_buttons_vector.addElement(this.predict_names_button23);
        this.predict_names_buttons_vector.addElement(this.predict_names_button24);
        this.predict_names_buttons_vector.addElement(this.predict_names_button25);
        this.predict_names_buttons_vector.addElement(this.predict_names_button26);
        this.predict_names_buttons_vector.addElement(this.predict_names_button27);
        this.predict_names_buttons_vector.addElement(this.predict_names_button28);
        this.predict_names_buttons_vector.addElement(this.predict_names_button29);
        this.predict_names_buttons_vector.addElement(this.predict_names_button30);
        this.predict_names_buttons_vector.addElement(this.predict_names_button31);
        this.predict_names_buttons_vector.addElement(this.predict_names_button32);
        this.predict_names_buttons_vector.addElement(this.predict_names_button33);
        this.predict_names_buttons_vector.addElement(this.predict_names_button34);
        this.predict_names_buttons_vector.addElement(this.predict_names_button35);
        this.predict_names_buttons_vector.addElement(this.predict_names_button36);
        this.predict_names_buttons_vector.addElement(this.predict_names_button37);
        this.predict_names_buttons_vector.addElement(this.predict_names_button38);
        this.predict_names_buttons_vector.addElement(this.predict_names_button39);
        this.predict_names_buttons_vector.addElement(this.predict_names_button40);
        this.predict_values_texts_vector.addElement(this.predict_values_text1);
        this.predict_values_texts_vector.addElement(this.predict_values_text2);
        this.predict_values_texts_vector.addElement(this.predict_values_text3);
        this.predict_values_texts_vector.addElement(this.predict_values_text4);
        this.predict_values_texts_vector.addElement(this.predict_values_text5);
        this.predict_values_texts_vector.addElement(this.predict_values_text6);
        this.predict_values_texts_vector.addElement(this.predict_values_text7);
        this.predict_values_texts_vector.addElement(this.predict_values_text8);
        this.predict_values_texts_vector.addElement(this.predict_values_text9);
        this.predict_values_texts_vector.addElement(this.predict_values_text10);
        this.predict_values_texts_vector.addElement(this.predict_values_text11);
        this.predict_values_texts_vector.addElement(this.predict_values_text12);
        this.predict_values_texts_vector.addElement(this.predict_values_text13);
        this.predict_values_texts_vector.addElement(this.predict_values_text14);
        this.predict_values_texts_vector.addElement(this.predict_values_text15);
        this.predict_values_texts_vector.addElement(this.predict_values_text16);
        this.predict_values_texts_vector.addElement(this.predict_values_text17);
        this.predict_values_texts_vector.addElement(this.predict_values_text18);
        this.predict_values_texts_vector.addElement(this.predict_values_text19);
        this.predict_values_texts_vector.addElement(this.predict_values_text20);
        this.predict_values_texts_vector.addElement(this.predict_values_text21);
        this.predict_values_texts_vector.addElement(this.predict_values_text22);
        this.predict_values_texts_vector.addElement(this.predict_values_text23);
        this.predict_values_texts_vector.addElement(this.predict_values_text24);
        this.predict_values_texts_vector.addElement(this.predict_values_text25);
        this.predict_values_texts_vector.addElement(this.predict_values_text26);
        this.predict_values_texts_vector.addElement(this.predict_values_text27);
        this.predict_values_texts_vector.addElement(this.predict_values_text28);
        this.predict_values_texts_vector.addElement(this.predict_values_text29);
        this.predict_values_texts_vector.addElement(this.predict_values_text30);
        this.predict_values_texts_vector.addElement(this.predict_values_text31);
        this.predict_values_texts_vector.addElement(this.predict_values_text32);
        this.predict_values_texts_vector.addElement(this.predict_values_text33);
        this.predict_values_texts_vector.addElement(this.predict_values_text34);
        this.predict_values_texts_vector.addElement(this.predict_values_text35);
        this.predict_values_texts_vector.addElement(this.predict_values_text36);
        this.predict_values_texts_vector.addElement(this.predict_values_text37);
        this.predict_values_texts_vector.addElement(this.predict_values_text38);
        this.predict_values_texts_vector.addElement(this.predict_values_text39);
        this.predict_values_texts_vector.addElement(this.predict_values_text40);
        this.force_prodrule_list.addItem("arithmeticb");
        this.force_prodrule_list.addItem("compose");
        this.force_prodrule_list.addItem("disjunct");
        this.force_prodrule_list.addItem("embed_graph");
        this.force_prodrule_list.addItem("embed_algebra");
        this.force_prodrule_list.addItem("entity_disjunct");
        this.force_prodrule_list.addItem("equal");
        this.force_prodrule_list.addItem("exists");
        this.force_prodrule_list.addItem("forall");
        this.force_prodrule_list.addItem("match");
        this.force_prodrule_list.addItem("negate");
        this.force_prodrule_list.addItem("record");
        this.force_prodrule_list.addItem("size");
        this.force_prodrule_list.addItem("split");
        this.required_choice.addItem("steps");
        this.required_choice.addItem("learned concepts");
        this.required_choice.addItem("seconds");
        this.required_choice.addItem("concepts");
        this.required_choice.addItem("conjectures");
        this.required_choice.addItem("pis");
        this.entity_sorting_list.add("distinctiveness");
        this.entity_sorting_list.add("average sims");
        this.entity_formatting_list.add("representation");
        this.entity_formatting_list.add("ascii conjecture");
        this.entity_formatting_list.add("otter conjecture");
        this.entity_formatting_list.add("prolog conjecture");
        this.entity_formatting_list.add("tptp conjecture");
        this.entity_formatting_list.add("related");
        this.entity_formatting_list.add("distinctive");
        this.entity_formatting_list.select(0);
        this.entity_formatting_list.select(1);
        this.concept_formatting_list.add("id");
        this.concept_formatting_list.add("name");
        this.concept_formatting_list.add("simplified def");
        this.concept_formatting_list.add("ascii def");
        this.concept_formatting_list.add("otter def");
        this.concept_formatting_list.add("prolog def");
        this.concept_formatting_list.add("tptp def");
        this.concept_formatting_list.add("skolemised def");
        this.concept_formatting_list.add("total score");
        this.concept_formatting_list.add("applicability");
        this.concept_formatting_list.add("child num");
        this.concept_formatting_list.add("child score");
        this.concept_formatting_list.add("complexity");
        this.concept_formatting_list.add("comprehens");
        this.concept_formatting_list.add("coverage");
        this.concept_formatting_list.add("devel steps");
        this.concept_formatting_list.add("discrimination");
        this.concept_formatting_list.add("equiv conj score");
        this.concept_formatting_list.add("equiv conj num");
        this.concept_formatting_list.add("highlight");
        this.concept_formatting_list.add("imp conj score");
        this.concept_formatting_list.add("imp conj num");
        this.concept_formatting_list.add("invariance");
        this.concept_formatting_list.add("neg applic");
        this.concept_formatting_list.add("ne conj score");
        this.concept_formatting_list.add("ne conj num");
        this.concept_formatting_list.add("novelty");
        this.concept_formatting_list.add("parent score");
        this.concept_formatting_list.add("parsimony");
        this.concept_formatting_list.add("pi conj score");
        this.concept_formatting_list.add("pi conj num");
        this.concept_formatting_list.add("productivity");
        this.concept_formatting_list.add("pos applic");
        this.concept_formatting_list.add("pred power");
        this.concept_formatting_list.add("variety");
        this.concept_formatting_list.add("examples");
        this.concept_formatting_list.add("datatable");
        this.concept_formatting_list.add("types");
        this.concept_formatting_list.add("functions");
        this.concept_formatting_list.add("const step");
        this.concept_formatting_list.add("arity");
        this.concept_formatting_list.add("const time");
        this.concept_formatting_list.add("ancestors");
        this.concept_formatting_list.add("categorisation");
        this.concept_formatting_list.add("cross domain");
        this.concept_formatting_list.add("entity instant");
        this.concept_formatting_list.add("add examples");
        this.concept_formatting_list.add("implications");
        this.concept_formatting_list.add("generalisations");
        this.concept_formatting_list.add("alt defs");
        this.concept_formatting_list.add("rel equivs");
        this.concept_formatting_list.add("rel imps");
        this.concept_formatting_list.add("near equivs");
        this.concept_formatting_list.add("near imps");
        this.concept_formatting_list.add("implied specs");
        this.concept_formatting_list.add("specifications");
        this.concept_pruning_list.add("cross domain");
        this.concept_pruning_list.add("integer output");
        this.concept_pruning_list.add("specialisation");
        this.concept_pruning_list.add("element type");
        this.concept_pruning_list.add("learned");
        this.concept_pruning_list.add("arithmeticb");
        this.concept_pruning_list.add("compose");
        this.concept_pruning_list.add("disjunct");
        this.concept_pruning_list.add("embed_algebra");
        this.concept_pruning_list.add("embed_graph");
        this.concept_pruning_list.add("equal");
        this.concept_pruning_list.add("exists");
        this.concept_pruning_list.add("forall");
        this.concept_pruning_list.add("lakatos_method");
        this.concept_pruning_list.add("forced");
        this.concept_pruning_list.add("match");
        this.concept_pruning_list.add("negate");
        this.concept_pruning_list.add("record");
        this.concept_pruning_list.add("size");
        this.concept_pruning_list.add("split");
        this.concept_sorting_list.add("restore");
        this.concept_sorting_list.add("interestingness");
        this.concept_sorting_list.add("applicability");
        this.concept_sorting_list.add("coverage");
        this.concept_sorting_list.add("children_score");
        this.concept_sorting_list.add("complexity");
        this.concept_sorting_list.add("comprehensibility");
        this.concept_sorting_list.add("devel steps");
        this.concept_sorting_list.add("novelty");
        this.concept_sorting_list.add("parent_score");
        this.concept_sorting_list.add("parsimony");
        this.concept_sorting_list.add("predictive_power");
        this.concept_sorting_list.add("productivity");
        this.concept_sorting_list.add("variety");
        this.conjecture_formatting_list.add("id");
        this.conjecture_formatting_list.add("simplified format");
        this.conjecture_formatting_list.add("ascii format");
        this.conjecture_formatting_list.add("otter format");
        this.conjecture_formatting_list.add("prolog format");
        this.conjecture_formatting_list.add("tptp format");
        this.conjecture_formatting_list.add("proof_status");
        this.conjecture_formatting_list.add("explained_by");
        this.conjecture_formatting_list.add("proof");
        this.conjecture_formatting_list.add("counter");
        this.conjecture_formatting_list.add("total score");
        this.conjecture_formatting_list.add("arity");
        this.conjecture_formatting_list.add("applicability");
        this.conjecture_formatting_list.add("complexity");
        this.conjecture_formatting_list.add("comprehensibility");
        this.conjecture_formatting_list.add("surprisingness");
        this.conjecture_formatting_list.add("plausibility");
        this.conjecture_formatting_list.add("const time");
        this.conjecture_formatting_list.add("step");
        this.conjecture_formatting_list.add("lh_concept");
        this.conjecture_formatting_list.add("rh_concept");
        this.conjecture_pruning_list.add("segregated");
        this.conjecture_pruning_list.add("prime implicate");
        this.conjecture_pruning_list.add("instantiation");
        this.conjecture_pruning_list.add("not_instantiation");
        this.conjecture_pruning_list.add("axiom");
        this.conjecture_pruning_list.add("equivalence");
        this.conjecture_pruning_list.add("concept_forced");
        this.conjecture_pruning_list.add("implicate");
        this.conjecture_pruning_list.add("implication");
        this.conjecture_pruning_list.add("lakatos_method");
        this.conjecture_pruning_list.add("near_equiv");
        this.conjecture_pruning_list.add("near_imp");
        this.conjecture_pruning_list.add("non-exists");
        this.conjecture_pruning_list.add("not prime implicate");
        this.conjecture_pruning_list.add("proved");
        this.conjecture_pruning_list.add("undetermined");
        this.conjecture_pruning_list.add("disproved");
        this.conjecture_pruning_list.add("sos");
        this.conjecture_pruning_list.add("time");
        this.conjecture_pruning_list.add("syntax error");
        this.conjecture_sorting_list.add("restore");
        this.conjecture_sorting_list.add("interestingness");
        this.conjecture_sorting_list.add("mw_max_ptime");
        this.conjecture_sorting_list.add("mw_av_ptime");
        this.conjecture_sorting_list.add("mw_min_ptime");
        this.conjecture_sorting_list.add("mw_diff_ptime");
        this.conjecture_sorting_list.add("wc_proof_time");
        this.conjecture_sorting_list.add("proof_time");
        this.conjecture_sorting_list.add("proof_length");
        this.conjecture_sorting_list.add("surprisingness");
        this.conjecture_sorting_list.add("plausibility");
        this.conjecture_sorting_list.add("applicability");
        this.conjecture_sorting_list.add("arity");
        this.statistics_label1.setAlignment(2);
        this.statistics_label2.setAlignment(2);
        this.statistics_label3.setAlignment(2);
        this.statistics_choice1.add("steps");
        this.statistics_choice1.add("concepts");
        this.statistics_choice1.add("conjectures");
        this.statistics_choice1.add("eq-conjectures");
        this.statistics_choice1.add("ne-conjectures");
        this.statistics_choice1.add("prime implicates");
        this.statistics_choice1.add("categorisations");
        this.statistics_choice1.add("specialisations");
        this.statistics_choice1.add("counterexamples");
        this.statistics_choice1.add("agenda items");
        this.statistics_choice1.add("seconds");
        this.statistics_choice2.add("steps");
        this.statistics_choice2.add("concepts");
        this.statistics_choice2.add("conjectures");
        this.statistics_choice2.add("eq-conjectures");
        this.statistics_choice2.add("ne-conjectures");
        this.statistics_choice2.add("prime implicates");
        this.statistics_choice2.add("categorisations");
        this.statistics_choice2.add("specialisations");
        this.statistics_choice2.add("counterexamples");
        this.statistics_choice2.add("agenda items");
        this.statistics_choice2.add("seconds");
        this.statistics_choice3.add("steps");
        this.statistics_choice3.add("concepts");
        this.statistics_choice3.add("conjectures");
        this.statistics_choice3.add("eq-conjectures");
        this.statistics_choice3.add("ne-conjectures");
        this.statistics_choice3.add("prime implicates");
        this.statistics_choice3.add("categorisations");
        this.statistics_choice3.add("specialisations");
        this.statistics_choice3.add("counterexamples");
        this.statistics_choice3.add("agenda items");
        this.statistics_choice3.add("seconds");
        this.statistics_choice1.select(0);
        this.statistics_choice2.select(1);
        this.statistics_choice3.select(2);
        this.statistics_type_list.addItem("concept versus");
        this.statistics_type_list.addItem("equivalence versus");
        this.statistics_type_list.addItem("nonexists versus");
        this.statistics_type_list.addItem("implicate versus");
        this.statistics_type_list.addItem("near_equiv versus");
        this.statistics_type_list.addItem("near_imp versus");
        this.statistics_type_list.addItem("step versus");
        this.statistics_type_list.addItem("entity versus");
        this.statistics_type_list.addItem("process times");
        this.concept_formatting_list.select(0);
        this.concept_formatting_list.select(1);
        this.concept_formatting_list.select(3);
        this.concept_formatting_list.select(8);
        this.concept_formatting_list.select(9);
        this.concept_formatting_list.select(12);
        this.concept_formatting_list.select(15);
        this.concept_formatting_list.select(26);
        this.concept_formatting_list.select(31);
        this.concept_formatting_list.select(34);
        this.concept_formatting_list.select(35);
        this.concept_formatting_list.select(39);
        this.concept_formatting_list.select(40);
        this.concept_formatting_list.select(41);
        this.concept_formatting_list.select(49);
        this.conjecture_formatting_list.select(0);
        this.conjecture_formatting_list.select(2);
        this.conjecture_formatting_list.select(10);
        this.conjecture_formatting_list.select(11);
        this.conjecture_formatting_list.select(12);
        this.conjecture_formatting_list.select(13);
        this.conjecture_formatting_list.select(15);
        this.conjecture_formatting_list.select(16);
        this.conjecture_formatting_list.select(17);
        this.conjecture_formatting_list.select(18);
        this.conjecture_formatting_list.select(19);
        this.setHelloText();
        this.getHelpFiles();
        this.getMacroFiles();
        this.getInputFiles();
        this.getReportFiles();
        this.getReactionFiles();
        this.getTheoryFiles();
    }

    private void setHelloText() {
        String var1 = "<html><body bgcolor=\"FFFF99\"><center><b><font size=5 color=blue>The</font><font size=8 color=red>";
        var1 = var1 + " HR </font><font size=5 color=blue>System</font></b><p>\n";
        var1 = var1 + " version " + this.version_number + "<br>&copy;2002 Simon Colton<br>www.dai.ed.ac.uk/~simonco/research/hr<br>";
        var1 = var1 + "Developed at the Universities of Edinburgh and York, UK<br>\n";
        var1 = var1 + "<table border=0 cellspacing=3><tr valign=top><td align=center>\n";
        var1 = var1 + "<font color=green><b><u>Main Developer</u></b><br>Simon Colton</font></td>\n";
        var1 = var1 + "<td>&nbsp;&nbsp;&nbsp;<td align=center>";
        var1 = var1 + "<font color=red><b><u>Research Team</u></b><br>Alan Bundy<br>Toby Walsh</font>";
        var1 = var1 + "</td><td>&nbsp;&nbsp;</td><td align=center>";
        var1 = var1 + "<font color=blue><b><u>Development Team</u></b><br>Roy McCasland<br>Alison Pease<br>\n";
        var1 = var1 + "Graham Steel<br>J&uuml;rgen Zimmer</font>";
        var1 = var1 + "</td><td>&nbsp;&nbsp;</td><td align=center>";
        var1 = var1 + "<font color=black><b><u>Research Partners</u></b><br>Andreas Meier<br>Ian Miguel<br>Volker Sorge<br>Geoff Sutcliffe</font>";
        var1 = var1 + "</td></tr></table>\n";
        var1 = var1 + "<table width=85% cellspacing=10 cellpadding=10 border=2 bgcolor=\"FFFF60\"><tr valign=top>";
        var1 = var1 + "<td width=33%><b><u><font color=red><center>TO USE HR</u></b><ul><li>";
        var1 = var1 + "Choose a macro from the left hand list, then click on the <font color=red>red</font> play macro button.</li>\n";
        var1 = var1 + "<p><li>Restore an entire theory by choosing it from the right hand list and clicking";
        var1 = var1 + " on the <font color=red>red</font> restore theory button</li>\n";
        var1 = var1 + "<p><li>Start with a new domain file in the DOMAIN screen from the SETUP drop down list above.</li></ul>\n";
        var1 = var1 + "</td><td width=33%><b><u><font color=green><center>TO LEARN ABOUT USING HR</u></b><ul><li>";
        var1 = var1 + "Choose a tutorial macro from the left hand list (marked with a #) ";
        var1 = var1 + "and click on the <font color=red>red</font> play macro button.</li>\n";
        var1 = var1 + "<p><li>Read some help topics from the TOPICS screen in the HELP drop down list above.</li>\n";
        var1 = var1 + "<p><li>Read the manual at: www.dai.ed.ac.uk/<br>~simonco/research/hr</li></ul>\n";
        var1 = var1 + "</td><td width=33%><b><u><font color=blue><center>ABOUT THE HR PROJECT</u></b><ul><li>";
        var1 = var1 + "Aim: to automated scientific theory formation and apply this to discovery tasks</li>\n";
        var1 = var1 + "<p><li>Started in 1997 in the Mathematical Reasoning Group, University of Edinburgh</li>\n";
        var1 = var1 + "<p><li>Read the Springer book \"Automated Theory Formation in Pure Mathematics\" by Simon Colton, ISBN: 1-85233-609-9</li></ul>\n";
        var1 = var1 + "</td></tr></table>\n";
        var1 = var1 + "</body></html>\n";
        this.hello_text.setText(var1);
    }

    private void fillListBox(List var1, Vector var2) {
        var1.removeAll();

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            String var4 = (String)var2.elementAt(var3);
            if (!var4.substring(1, 2).equals(":")) {
                var1.addItem(var4);
            } else {
                var1.addItem(var4.substring(2, var4.length()));
            }

            if (var4.substring(0, 1).equals("y")) {
                var1.select(var3);
            }
        }

    }

    public void extendSequence(Concept var1, Button var2) {
        this.calculate_output_text.setText("");
        int var3 = new Integer(this.calculate_entity1_text.getText().trim());
        int var4 = new Integer(this.calculate_entity2_text.getText().trim());
        this.sequence_calculation_thread = new SequenceCalculationThread(var3, var4, var1, this.calculate_output_text, this.calculate_button, this.concepts);
        this.sequence_calculation_thread.start();
    }

    public double getConceptCategoryPercent(String var1, String var2) {
        double var3 = 0.0D;

        for(int var5 = 0; var5 < this.concepts.size(); ++var5) {
            Concept var6 = (Concept)this.concepts.elementAt(var5);
            Categorisation var7 = var6.categorisation;
            boolean var8 = false;

            for(int var9 = 0; var9 < var7.size() && !var8; ++var9) {
                Vector var10 = (Vector)var7.elementAt(var9);
                String var11 = var10.toString();
                if (var11.indexOf(var1) > -1 && var11.indexOf(var2) > -1) {
                    ++var3;
                    var8 = true;
                }
            }
        }

        return new Double(100.0D * var3 / (double)this.concepts.size());
    }

    public void updatePercentCategorisation() {
        Vector var1 = new Vector();
        Vector var2 = new Vector();
        var2.addElement(((Entity)this.entities.elementAt(0)).name);
        var1.addElement(var2);
        double var3 = new Double(this.percent_categorisation_percent_text.getText());

        for(int var5 = 1; var5 < this.entities.size(); ++var5) {
            String var6 = ((Entity)this.entities.elementAt(var5)).name;
            boolean var7 = true;

            for(int var8 = 0; var8 < var1.size(); ++var8) {
                boolean var9 = true;

                for(int var10 = 0; var10 < var2.size() && var9; ++var10) {
                    String var11 = (String)var2.elementAt(var10);
                    if (this.getConceptCategoryPercent(var6, var11) < var3) {
                        var9 = false;
                    }
                }

                if (var9 && !var2.contains(var6)) {
                    var2.addElement(var6);
                    var7 = false;
                }
            }

            if (var7) {
                Vector var12 = new Vector();
                var12.addElement(var6);
                var1.addElement(var12);
            }
        }

        this.percent_categorisation_text.setText(var1.toString());
    }

    public void actionPerformed(ActionEvent var1) {
        String var2;
        String var6;
        if (var1.getSource() == this.agent_search_button) {
            var2 = this.agent_search_text.getText();
            this.agent_search_output_list.clear();

            for(int var3 = 0; var3 < this.lakatos_tabbed_pane.getTabCount(); ++var3) {
                AgentOutputPanel var4 = (AgentOutputPanel)this.lakatos_tabbed_pane.getComponentAt(var3);
                JTextArea var5 = var4.agent_output_text;
                var6 = var5.getText();

                for(int var7 = var6.indexOf(var2); var7 > 0; var7 = var6.indexOf(var2, var7 + 1)) {
                    this.agent_search_output_list.add(this.lakatos_tabbed_pane.getTitleAt(var3) + ":" + Integer.toString(var7));
                }
            }
        }

        if (var1.getSource() == this.save_report_button) {
            try {
                var2 = this.save_report_name_text.getText();
                if (!var2.equals("")) {
                    String var12 = this.input_files_directory + var2;
                    PrintWriter var14 = new PrintWriter(new BufferedWriter(new FileWriter(var12)));
                    var14.println(this.report_instructions_text.getText().trim());
                    var14.close();
                    this.getReportFiles();
                }
            } catch (Exception var8) {
                System.out.println(var8);
            }
        }

        PrintWriter var13;
        String var15;
        int var17;
        if (var1.getSource() == this.save_help_button) {
            try {
                var2 = this.input_files_directory + this.help_save_file_text.getText();
                var13 = new PrintWriter(new BufferedWriter(new FileWriter(var2)));

                for(var15 = this.help_text.getText().trim() + "\n"; var15.indexOf("\n") > -1; var15 = var15.substring(var17 + 1, var15.length())) {
                    var17 = var15.indexOf("\n");
                    var13.println(var15.substring(0, var17));
                }

                var13.close();
                this.getHelpFiles();
            } catch (Exception var10) {
                System.out.println(var10);
            }
        }

        if (var1.getSource() == this.percent_categorisation_button) {
            this.percent_categorisation_text.setText("Please wait");
            this.updatePercentCategorisation();
        }

        int var11;
        if (var1.getSource() == this.percent_up_button) {
            this.percent_categorisation_text.setText("Please wait");
            if (this.percent_categorisation_percent_text.getText().equals("")) {
                this.percent_categorisation_percent_text.setText("40");
            }

            var11 = new Integer(this.percent_categorisation_percent_text.getText());
            var11 += 10;
            if (var11 > 100) {
                var11 = 100;
            }

            this.percent_categorisation_percent_text.setText(Integer.toString(var11));
            this.updatePercentCategorisation();
        }

        if (var1.getSource() == this.percent_down_button) {
            this.percent_categorisation_text.setText("Please wait");
            if (this.percent_categorisation_percent_text.getText().equals("")) {
                this.percent_categorisation_percent_text.setText("60");
            }

            var11 = new Integer(this.percent_categorisation_percent_text.getText());
            var11 -= 10;
            if (var11 < 1) {
                var11 = 1;
            }

            this.percent_categorisation_percent_text.setText(Integer.toString(var11));
            this.updatePercentCategorisation();
        }

        if (var1.getSource() == this.reset_concept_weights_button) {
            this.concept_applicability_weight_text.setText("0.0");
            this.concept_coverage_weight_text.setText("0.0");
            this.concept_comprehensibility_weight_text.setText("0.0");
            this.concept_cross_domain_weight_text.setText("0.0");
            this.concept_highlight_weight_text.setText("0.0");
            this.concept_novelty_weight_text.setText("0.0");
            this.concept_parent_weight_text.setText("0.0");
            this.concept_children_weight_text.setText("0.0");
            this.concept_parsimony_weight_text.setText("0.0");
            this.concept_productivity_weight_text.setText("0.0");
            this.concept_development_steps_num_weight_text.setText("0.0");
            this.concept_default_productivity_text.setText("0.0");
            this.concept_variety_weight_text.setText("0.0");
        }

        if (var1.getSource() == this.reset_conjecture_weights_button) {
            this.conjecture_applicability_weight_text.setText("0.0");
            this.conjecture_comprehensibility_weight_text.setText("0.0");
            this.conjecture_surprisingness_weight_text.setText("0.0");
            this.conjecture_plausibility_weight_text.setText("0.0");
        }

        if (var1.getSource() == this.save_macro_button) {
            try {
                var2 = this.input_files_directory + this.save_macro_text.getText();
                var13 = new PrintWriter(new BufferedWriter(new FileWriter(var2)));

                for(var15 = this.macro_text.getText().trim() + "\n"; var15.indexOf("\n") > -1; var15 = var15.substring(var17 + 1, var15.length())) {
                    var17 = var15.indexOf("\n");
                    var13.println(var15.substring(0, var17));
                }

                var13.close();
                this.getMacroFiles();
            } catch (Exception var9) {
                System.out.println(var9);
            }
        }

        if (var1.getSource() == this.calculate_button) {
            if (!this.calculate_button.getLabel().equals("Calculate")) {
                this.sequence_calculation_thread.must_stop = true;
            } else {
                var11 = 0;
                boolean var16 = false;
                Concept var18 = new Concept();

                for(String var19 = this.concept_list.getSelectedItem(); !var16 && var11 < this.concepts.size(); ++var11) {
                    var18 = (Concept)this.concepts.elementAt(var11);
                    if (var18.id.equals(var19)) {
                        var16 = true;
                    }
                }

                var6 = "";
                if (this.calculate_entity2_text.getText().equals("")) {
                    this.calculate_button.setLabel("PLEASE WAIT");
                    String var20 = this.calculate_entity1_text.getText();
                    var6 = var18.calculateRow(this.concepts, var20).tuples.toString();
                    var6 = var6.substring(1, var6.length() - 1);
                    if (var18.arity == 1) {
                        if (var6.equals("[]")) {
                            var6 = "yes";
                        }

                        if (var6.equals("")) {
                            var6 = "no";
                        }
                    } else if (var6.equals("")) {
                        var6 = "empty";
                    }

                    this.calculate_output_text.setText(var6);
                } else {
                    this.extendSequence(var18, this.calculate_button);
                }

                this.calculate_button.setLabel("Calculate");
            }
        }

        if (var1.getSource() == this.domain_none_button) {
            this.deselectAll(this.initial_concepts_list);
            this.deselectAll(this.initial_entity_list);
            this.deselectAll(this.counterexample_list);
            this.deselectAll(this.split_values_list);
        }

        if (var1.getSource() == this.highlight_none_button) {
            this.deselectAll(this.highlight_list);
        }

        if (var1.getSource() == this.initial_entity_none_button) {
            this.deselectAll(this.initial_entity_list);
        }

        if (var1.getSource() == this.counterexample_none_button) {
            this.deselectAll(this.counterexample_list);
        }

        if (var1.getSource() == this.split_values_none_button) {
            this.deselectAll(this.split_values_list);
        }

        if (var1.getSource() == this.highlight_all_button) {
            this.selectAll(this.highlight_list);
        }

        if (var1.getSource() == this.initial_entity_all_button) {
            this.selectAll(this.initial_entity_list);
        }

        if (var1.getSource() == this.counterexample_all_button) {
            this.selectAll(this.counterexample_list);
        }

        if (var1.getSource() == this.split_values_all_button) {
            this.selectAll(this.split_values_list);
        }

    }

    public void deselectAll(List var1) {
        for(int var2 = 0; var2 < var1.getItemCount(); ++var2) {
            var1.deselect(var2);
        }

    }

    private void selectAll(List var1) {
        for(int var2 = 0; var2 < var1.getItemCount(); ++var2) {
            var1.select(var2);
        }

    }

    public void updateConjecture(Conjecture var1) {
        if (!var1.is_removed) {
            String var2 = var1.fullHTMLDetails(this.conjecture_formatting_list.getSelectedItems(), this.decimal_places, this.concept_formatting_list.getSelectedItems());
            this.conjecture_text_box.setText(var2);
        }

        this.parent_conjectures_list.removeAll();
        this.child_conjectures_list.removeAll();

        Conjecture var3;
        int var4;
        for(var4 = 0; var4 < var1.parent_conjectures.size(); ++var4) {
            var3 = (Conjecture)var1.parent_conjectures.elementAt(var4);
            this.parent_conjectures_list.addItem(var3.writeConjecture("ascii"));
        }

        for(var4 = 0; var4 < var1.child_conjectures.size(); ++var4) {
            var3 = (Conjecture)var1.child_conjectures.elementAt(var4);
            this.child_conjectures_list.addItem(var3.writeConjecture("ascii"));
        }

        Vector var5 = (Vector)this.conjecture_grep_positions.get(var1.id);
        if (var5 != null) {
            this.conjecture_text_box.setSelectionStart(new Integer((String)var5.elementAt(0)));
            this.conjecture_text_box.setSelectionEnd(new Integer((String)var5.elementAt(1)));
        } else {
            this.conjecture_text_box.setCaretPosition(0);
        }

    }

    public Concept updateConcept() {
        int var1 = 0;
        boolean var2 = false;
        Concept var3 = new Concept();

        String var4;
        for(var4 = this.concept_list.getSelectedItem(); !var2 && var1 < this.concepts.size(); ++var1) {
            var3 = (Concept)this.concepts.elementAt(var1);
            if (var3.id.equals(var4)) {
                var2 = true;
            }
        }

        String var5 = var3.fullDetails("html", this.concept_formatting_list.getSelectedItems(), this.decimal_places);
        this.concept_text_box.setText(var5);
        this.ancestor_list.removeAll();
        this.children_list.removeAll();

        for(var1 = 0; var1 < this.concepts.size(); ++var1) {
            Concept var6 = (Concept)this.concepts.elementAt(var1);
            if (var3.ancestor_ids.contains(var6.id)) {
                this.ancestor_list.addItem(var6.id + " " + var6.writeDefinition("ascii"));
            }

            if (var3.children.contains(var6)) {
                this.children_list.addItem(var6.id + " " + var6.writeDefinition("ascii"));
            }
        }

        Vector var7 = (Vector)this.concept_grep_positions.get(var4);
        if (var7 != null) {
            this.concept_text_box.setCaretPosition(new Integer((String)var7.elementAt(0)));
            this.concept_text_box.setSelectedTextColor(Color.red);
            this.concept_text_box.setSelectionStart(new Integer((String)var7.elementAt(0)));
            this.concept_text_box.setSelectionEnd(new Integer((String)var7.elementAt(1)));
        } else {
            this.concept_text_box.setCaretPosition(0);
        }

        return var3;
    }

    public void addObjectToList(Object var1, String var2, SortableList var3, String[] var4, String var5) {
        String var6 = "";
        if (var5 != null && !var5.equals("") && !var5.equals("restore")) {
            var6 = this.reflect.getValue(var1, var5).toString();
        }

        boolean var7 = true;

        for(int var8 = 0; var8 < var4.length && var7; ++var8) {
            if (!this.reflect.checkCondition(var1, var4[var8])) {
                var7 = false;
            }
        }

        if (var7) {
            var3.addItem(var2, var6);
        }

    }

    public void pruneConceptList() {
        this.concept_list.clear();
        String[] var1 = this.concept_pruning_list.getSelectedItems();
        String var2 = this.concept_sorting_list.getSelectedItem();

        for(int var3 = 0; var3 < this.concepts.size(); ++var3) {
            Concept var4 = (Concept)this.concepts.elementAt(var3);
            this.addObjectToList(var4, var4.id, this.concept_list, var1, var2);
        }

        this.concept_list_number.setText("number: " + Integer.toString(this.concept_list.getItemCount()));
    }

    public void pruneConjectureList() {
        this.conjecture_list.clear();
        String[] var1 = this.conjecture_pruning_list.getSelectedItems();
        String var2 = this.conjecture_sorting_list.getSelectedItem();

        for(int var3 = 0; var3 < this.conjectures.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.conjectures.elementAt(var3);
            this.addObjectToList(var4, var4.id, this.conjecture_list, var1, var2);
        }

        this.conjecture_list_number.setText("number: " + Integer.toString(this.conjecture_list.getItemCount()));
    }

    public void focusGained(FocusEvent var1) {
    }

    public void focusLost(FocusEvent var1) {
    }

    public void itemStateChanged(ItemEvent var1) {
        String var2;
        String var3;
        String var4;
        int var5;
        int var6;
        int var7;
        if (var1.getSource() == this.agent_search_output_list) {
            var2 = this.agent_search_output_list.getSelectedItem();
            var3 = var2.substring(0, var2.indexOf(":"));
            var4 = var2.substring(var2.indexOf(":") + 1, var2.length());
            var5 = new Integer(var4);
            var6 = var5 + this.agent_search_text.getText().length();

            for(var7 = 0; var7 < this.lakatos_tabbed_pane.getTabCount(); ++var7) {
                if (this.lakatos_tabbed_pane.getTitleAt(var7).equals(var3)) {
                    this.lakatos_tabbed_pane.setSelectedIndex(var7);
                    AgentOutputPanel var8 = (AgentOutputPanel)this.lakatos_tabbed_pane.getComponentAt(var7);
                    var8.agent_output_text.select(var5, var6);
                }
            }
        }

        if (var1.getSource() == this.help_list) {
            this.help_text.setText("");
            var2 = this.input_files_directory + this.help_list.getSelectedItem();

            try {
                BufferedReader var15 = new BufferedReader(new FileReader(var2));

                for(var4 = var15.readLine(); var4 != null; var4 = var15.readLine()) {
                    this.help_text.append(var4 + "\n");
                }

                var15.close();
                if (!var4.equals("")) {
                    this.help_text.append(var4 + "\n");
                }
            } catch (Exception var13) {
                ;
            }
        }

        int var19;
        if (var1.getSource() == this.entity_sorting_list) {
            Vector var14 = new Vector();
            Vector var17 = new Vector();

            for(var19 = 0; var19 < this.concepts.size(); ++var19) {
                Concept var20 = (Concept)this.concepts.elementAt(var19);
                Categorisation var22 = var20.categorisation;

                for(var7 = 0; var7 < var22.size(); ++var7) {
                    Vector var28 = (Vector)var22.elementAt(var7);

                    for(int var9 = 0; var9 < var28.size(); ++var9) {
                        String var10 = (String)var28.elementAt(var9);
                        int var11 = var14.indexOf(var10);
                        int var12;
                        if (var11 > -1) {
                            var12 = (Integer)var17.elementAt(var11);
                            if (this.entity_sorting_list.getSelectedItem().equals("average sims")) {
                                var12 += var28.size();
                            }

                            if (this.entity_sorting_list.getSelectedItem().equals("distinctiveness") && var28.size() == 1) {
                                ++var12;
                            }

                            var17.setElementAt(new Integer(var12), var11);
                        } else {
                            var12 = 0;
                            if (this.entity_sorting_list.getSelectedItem().equals("average sims")) {
                                var12 = var28.size();
                            }

                            if (this.entity_sorting_list.getSelectedItem().equals("distinctiveness") && var28.size() == 1) {
                                var12 = 1;
                            }

                            var17.addElement(new Integer(var12));
                            var14.addElement(var10);
                        }
                    }
                }
            }

            this.entity_list.removeAll();

            for(var19 = 0; var19 < var17.size(); ++var19) {
                var5 = (Integer)var17.elementAt(var19);
                var6 = 0;

                boolean var27;
                for(var27 = false; var6 < this.entity_list.getItemCount() && !var27; ++var6) {
                    String var29 = this.entity_list.getItem(var6);
                    String var31 = var29.substring(var29.lastIndexOf(" ") + 1, var29.length());
                    int var33 = new Integer(var31);
                    if (var5 < var33 && this.entity_sorting_list.getSelectedItem().equals("average sims") || var5 > var33 && this.entity_sorting_list.getSelectedItem().equals("distinctiveness")) {
                        this.entity_list.addItem((String)var14.elementAt(var19) + " " + Integer.toString(var5), var6);
                        var27 = true;
                    }
                }

                if (!var27) {
                    this.entity_list.addItem((String)var14.elementAt(var19) + " " + Integer.toString(var5));
                }
            }

            if (this.entity_sorting_list.getSelectedItem().equals("average sims")) {
                for(var19 = 0; var19 < this.entity_list.getItemCount(); ++var19) {
                    String var21 = this.entity_list.getItem(var19);
                    String var25 = var21.substring(var21.lastIndexOf(" ") + 1, var21.length());
                    double var30 = new Double(var25);
                    double var32 = var30 / (double)this.concepts.size();
                    this.entity_list.replaceItem(var21.substring(0, var21.lastIndexOf(" ") + 1) + Double.toString(var32), var19);
                }
            }
        }

        if (var1.getSource() == this.reports_list) {
            this.report_instructions_text.setText("");
            var2 = this.reports_list.getSelectedItem();
            var3 = this.input_files_directory + var2;
            this.loadReport(var3);
            this.save_report_name_text.setText(var2);
        }

        if (var1.getSource() == this.entity_list) {
            int var16 = 0;
            boolean var18 = false;
            var4 = this.entity_list.getSelectedItem();
            if (var4.lastIndexOf(" ") > -1) {
                var4 = var4.substring(0, var4.lastIndexOf(" "));
            }

            new Entity();

            for(; !var18 && var16 < this.entities.size(); ++var16) {
                Entity var23 = (Entity)this.entities.elementAt(var16);
                if (var23.name.equals(var4)) {
                    var18 = true;
                    this.entity_text_box.setText(var23.fullHTMLDetails(this.concepts, this.entity_formatting_list.getSelectedItems()));
                }
            }
        }

        if (var1.getSource() == this.concept_pruning_list) {
            this.pruneConceptList();
        }

        if (var1.getSource() == this.concept_sorting_list) {
            this.pruneConceptList();
        }

        if (var1.getSource() == this.conjecture_pruning_list) {
            this.pruneConjectureList();
        }

        if (var1.getSource() == this.conjecture_sorting_list) {
            this.pruneConjectureList();
        }

        boolean var24;
        Concept var26;
        if (var1.getSource() == this.ancestor_list) {
            var2 = this.ancestor_list.getSelectedItem();
            var3 = var2.substring(0, var2.indexOf(" "));
            var19 = 0;

            for(var24 = false; !var24 && var19 < this.concepts.size(); ++var19) {
                var26 = (Concept)this.concepts.elementAt(var19);
                if (var26.id.equals(var3)) {
                    this.concept_text_box.setText(var26.fullDetails("html", this.concept_formatting_list.getSelectedItems(), this.decimal_places));
                }
            }
        }

        if (var1.getSource() == this.children_list) {
            var2 = this.children_list.getSelectedItem();
            var3 = var2.substring(0, var2.indexOf(" "));
            var19 = 0;

            for(var24 = false; !var24 && var19 < this.concepts.size(); ++var19) {
                var26 = (Concept)this.concepts.elementAt(var19);
                if (var26.id.equals(var3)) {
                    this.concept_text_box.setText(var26.fullDetails("html", this.concept_formatting_list.getSelectedItems(), this.decimal_places));
                }
            }
        }

        if (var1.getSource() == this.concept_list) {
            this.updateConcept();
        }

        if (var1.getSource() == this.statistics_choice1 || var1.getSource() == this.statistics_choice2 || var1.getSource() == this.statistics_choice3) {
            this.update();
        }

    }

    public void addDomainInformation(Vector var1) {
        Vector var2 = (Vector)var1.elementAt(0);
        Vector var3 = (Vector)var1.elementAt(1);
        Vector var4 = (Vector)var1.elementAt(2);
        Vector var5 = (Vector)var1.elementAt(3);
        Vector var6 = (Vector)var1.elementAt(4);
        this.predict_entity_type_choice.removeAll();
        this.predict_entity_type_choice.addItem("Choose entity type");

        int var7;
        for(var7 = 0; var7 < var6.size(); ++var7) {
            this.predict_entity_type_choice.addItem((String)var6.elementAt(var7));
        }

        for(var7 = 0; var7 < var2.size(); ++var7) {
            Concept var8 = (Concept)var2.elementAt(var7);
            this.initial_concepts_list.addItem(var8.name);
            this.highlight_list.addItem(var8.id + ":" + var8.name);
        }

        Vector var11 = new Vector();

        for(int var12 = 0; var12 < var3.size(); ++var12) {
            String var9 = (String)var3.elementAt(var12);
            this.initial_entity_list.addItem(var9);
            this.counterexample_list.addItem(var9);
            this.split_values_list.addItem(var9);
            String var10 = var9.substring(0, var9.indexOf(":"));
            if (!var11.contains(var10)) {
                var11.addElement(var10);
                this.split_values_list.addItem("all: " + var10 + "s", 0);
            }
        }

        if (!var11.contains("integer")) {
            this.split_values_list.addItem("all: integers", 0);
        }

    }

    public void update() {
        if (!this.statistics.isEmpty()) {
            String var1 = (String)this.statistics.elementAt(this.statistics_choice1.getSelectedIndex());
            this.main_frame.setTitle(this.my_agent_name + " HR" + this.version_number + ": " + var1 + " " + this.statistics_choice1.getSelectedItem());
            this.statistics_label1.setText(var1);
            this.statistics_label2.setText((String)this.statistics.elementAt(this.statistics_choice2.getSelectedIndex()));
            this.statistics_label3.setText((String)this.statistics.elementAt(this.statistics_choice3.getSelectedIndex()));
        }

    }

    public void addEntity(Entity var1) {
        this.entity_list.addItem(var1.name);
    }

    public void removeEntity(Entity var1) {
        this.entity_list.remove(var1.name);
    }

    public Vector getSelectedItems(List var1) {
        Vector var2 = new Vector();
        String[] var3 = var1.getSelectedItems();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            var2.addElement(var3[var4]);
        }

        return var2;
    }

    public void getMacroFiles() {
        File var1 = new File(this.input_files_directory);
        String[] var2 = var1.list();
        String var3 = this.quick_macro_choice.getSelectedItem();
        String var4 = this.macro_list.getSelectedItem();
        this.macro_list.removeAll();
        this.quick_macro_choice.removeAll();
        this.quick_macro_choice.addItem("macro");

        for(int var5 = 0; var5 < var2.length; ++var5) {
            if (var2[var5].indexOf(".hrm") > 0) {
                this.macro_list.addItem(var2[var5]);
                this.quick_macro_choice.addItem(var2[var5]);
                if (var2[var5].equals(var3)) {
                    this.quick_macro_choice.select(var5 + 1);
                }

                if (var2[var5].equals(var4)) {
                    this.macro_list.select(var5);
                }
            }
        }

    }

    public void getReportFiles() {
        this.getDirectoryFiles(this.input_files_directory, this.reports_to_execute_list, "hrr");
        this.getDirectoryFiles(this.input_files_directory, this.reports_list, "hrr");
    }

    public void getTheoryFiles() {
        this.getDirectoryFiles(this.input_files_directory, this.stored_theories_list, "hrs");
    }

    private void getDirectoryFiles(String var1, List var2, String var3) {
        File var4 = new File(var1);
        String[] var5 = var4.list();
        String[] var6 = var2.getSelectedItems();
        var2.removeAll();

        int var7;
        for(var7 = 0; var7 < var5.length; ++var7) {
            if (var5[var7].indexOf("." + var3) > 0) {
                var2.addItem(var5[var7]);
            }
        }

        for(var7 = 0; var7 < var2.getItemCount(); ++var7) {
            for(int var8 = 0; var8 < var6.length; ++var8) {
                if (var6[var8].equals(var2.getItem(var7))) {
                    var2.select(var7);
                }
            }
        }

    }

    public void getInputFiles() {
        File var1 = new File(this.input_files_directory);
        String[] var2 = var1.list();
        this.domain_list.removeAll();
        this.predict_input_files_choice.removeAll();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var2[var3].indexOf(".hrd") > 0) {
                this.domain_list.addItem(var2[var3]);
                this.predict_input_files_choice.addItem(var2[var3]);
            }
        }

    }

    public void getReactionFiles() {
        this.getDirectoryFiles(this.input_files_directory, this.reactions_list, "hre");
    }

    public void getHelpFiles() {
        this.getDirectoryFiles(this.input_files_directory, this.help_list, "hrh");
    }

    public void loadReaction(String var1) {
        try {
            this.reactions_text.setText("");
            BufferedReader var2 = new BufferedReader(new FileReader(var1));

            for(String var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
                this.reactions_text.append(var3 + "\n");
            }

            var2.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

    }

    public void loadReport(String var1) {
        try {
            BufferedReader var2 = new BufferedReader(new FileReader(var1));

            String var3;
            for(var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
                this.report_instructions_text.append(var3 + "\n");
            }

            var2.close();
            if (!var3.equals("")) {
                this.report_instructions_text.append(var3 + "\n");
            }
        } catch (Exception var4) {
            ;
        }

    }
}
