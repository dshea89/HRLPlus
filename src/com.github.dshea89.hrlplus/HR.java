package com.github.dshea89.hrlplus;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class HR extends Frame implements ActionListener, ItemListener, Serializable
{
    // The Agent name of HR (if applicable)

    public String my_agent_name = "";

    // The Version of HR //

    public String version_number = "2.2.1";

    // FRONT END //

    /** The screen buttons object for this front end.
     */

    public ScreenButtons screen_buttons = null;

    public Button start_over_button = new Button("Start Over");

    public Choice setup_choice = new Choice();
    public Choice application_choice = new Choice();
    public Choice view_choice = new Choice();
    public Choice debug_choice = new Choice();
    public Choice help_choice = new Choice();

    public Label menu_label = new Label("ABOUT");

    public CardLayout main_layout = new CardLayout();
    public CardLayout macro_text_layout = new CardLayout();

    public Panel about_panel = new Panel();
    public Panel interface_panel = new Panel();
    public Panel report_panel = new Panel();
    public Panel statistics_panel = new Panel();
    public Panel domain_panel = new Panel();
    public Panel search_panel = new Panel();
    public Panel proof_panel = new Panel();
    public Panel concepts_panel = new Panel();
    public Panel conjectures_panel = new Panel();
    public Panel main_panel = new Panel();
    public Panel entities_panel = new Panel();
    public Panel agenda_panel = new Panel();
    public Panel force_panel = new Panel();
    public Panel systemout_panel = new Panel();
    public Panel pseudo_code_panel = new Panel();
    public Panel learn_panel = new Panel();
    public Panel store_panel = new Panel();
    public Panel macro_panel = new Panel();
    public Panel read_panel = new Panel();
    public Panel help_panel = new Panel();
    public Panel predict_panel = new Panel();
    public Panel puzzle_panel = new Panel();
    public Panel reactions_panel = new Panel();
    public Panel lakatos_panel = new Panel(); //alisonp
    public Panel methods_panel = new Panel();
    public Panel macro_centre_panel = new Panel();

    // Main Buttons Panel //

    public Panel main_buttons_panel = new Panel();
    public Panel top_main_buttons_panel = new Panel();
    public Panel bottom_main_buttons_panel = new Panel();

    // Output Panel //

    public Panel output_panel = new Panel();
    public Panel top_output_panel = new Panel();
    public Panel mid_output_panel = new Panel();
    public Panel bottom_output_panel = new Panel();

    // Macro Panel //

    public Panel macro_text_panel = new Panel();
    public Panel hello_text_panel = new Panel();

    // INITIAL VARIABLES //

    public Theory theory = null;
    public ConfigHandler config_handler = new ConfigHandler();
    public HR hr = null;

    // Constructor //

    public HR()
    {
        // OS //

        theory = new Theory();

        // Fill in the screen menus //

        setup_choice.addItem("SETUP");
        setup_choice.addItem("MACRO");
        setup_choice.addItem("DOMAIN");
        setup_choice.addItem("SEARCH");
        setup_choice.addItem("PROOF");
        setup_choice.addItem("STORE");
        setup_choice.addItem("INTERFACE");
        setup_choice.addItem("REACT");
        setup_choice.addItem("LAKATOS"); //alisonp
        setup_choice.addItem("METHODS");

        application_choice.addItem("APPLICATIONS");
        application_choice.addItem("LEARN");
        application_choice.addItem("PREDICT");
        application_choice.addItem("PUZZLE");

        view_choice.addItem("VIEW THEORY");
        view_choice.addItem("CONCEPTS");
        view_choice.addItem("CONJECTURES");
        view_choice.addItem("ENTITIES");
        view_choice.addItem("REPORTS");
        view_choice.addItem("STATISTICS");

        debug_choice.addItem("DEBUG");
        debug_choice.addItem("AGENDA");
        debug_choice.addItem("FORCE");
        debug_choice.addItem("SYSTEMOUT");
        debug_choice.addItem("PSEUDOCODE");

        help_choice.addItem("HELP");
        help_choice.addItem("TOPICS");
        help_choice.addItem("ABOUT");

        // Menu Panel //

        Panel menu_panel = new Panel();
        menu_panel.setLayout(new GridLayout(1,6));
        menu_panel.add(menu_label);
        menu_panel.add(setup_choice);
        menu_panel.add(application_choice);
        menu_panel.add(view_choice);
        menu_panel.add(debug_choice);
        menu_panel.add(help_choice);

        // Settings Panel //

        domain_panel.setLayout(new GridLayout(2,3));

        // Domain Panel //

        Panel domain_panel1 = new Panel();
        Panel domain_panel2 = new Panel();
        Panel domain_panel2top = new Panel();
        Panel domain_panel3 = new Panel();
        Panel domain_panel3top = new Panel();
        Panel domain_panel4 = new Panel();
        Panel domain_panel5 = new Panel();
        Panel domain_panel6 = new Panel();

        domain_panel.add(domain_panel1);
        domain_panel.add(domain_panel2);
        domain_panel.add(domain_panel3);
        domain_panel.add(domain_panel4);
        domain_panel.add(domain_panel5);
        domain_panel.add(domain_panel6);

        domain_panel1.setLayout(new BorderLayout());
        domain_panel1.add("North",theory.front_end.domain_label);
        domain_panel1.add("Center",theory.front_end.domain_list);
        Panel temp_panelp = new Panel();
        temp_panelp.setLayout(new GridLayout(1,2));
        temp_panelp.add(theory.front_end.domain_none_button);
        temp_panelp.add(theory.front_end.domain_default_button);
        domain_panel1.add("South",temp_panelp);
        whiteButtons(theory.front_end.domain_none_button,
                theory.front_end.domain_default_button);

        domain_panel2.setLayout(new BorderLayout());
        domain_panel2.add("North",theory.front_end.initial_concepts_label);
        domain_panel2.add("Center",theory.front_end.initial_concepts_list);
        temp_panelp = new Panel();
        temp_panelp.setLayout(new GridLayout(1,3));
        temp_panelp.add(theory.front_end.initial_concepts_none_button);
        temp_panelp.add(theory.front_end.initial_concepts_all_button);
        domain_panel2.add("South",temp_panelp);
        grayButtons(theory.front_end.initial_concepts_none_button,
                theory.front_end.initial_concepts_all_button);

        domain_panel3.setLayout(new BorderLayout());
        domain_panel3.add("North",theory.front_end.highlight_label);
        domain_panel3.add("Center",theory.front_end.highlight_list);
        temp_panelp = new Panel();
        temp_panelp.setLayout(new GridLayout(1,3));
        temp_panelp.add(theory.front_end.highlight_none_button);
        temp_panelp.add(theory.front_end.highlight_all_button);
        domain_panel3.add("South",temp_panelp);
        whiteButtons(theory.front_end.highlight_none_button,
                theory.front_end.highlight_all_button);

        domain_panel4.setLayout(new BorderLayout());
        domain_panel4.add("North",theory.front_end.initial_entity_label);
        domain_panel4.add("Center",theory.front_end.initial_entity_list);
        temp_panelp = new Panel();
        temp_panelp.setLayout(new GridLayout(1,2));
        temp_panelp.add(theory.front_end.initial_entity_none_button);
        temp_panelp.add(theory.front_end.initial_entity_all_button);
        domain_panel4.add("South",temp_panelp);
        grayButtons(theory.front_end.initial_entity_none_button,
                theory.front_end.initial_entity_all_button);

        domain_panel5.setLayout(new BorderLayout());
        domain_panel5.add("North",theory.front_end.counterexample_label);
        domain_panel5.add("Center",theory.front_end.counterexample_list);
        temp_panelp = new Panel();
        temp_panelp.setLayout(new GridLayout(1,2));
        temp_panelp.add(theory.front_end.counterexample_none_button);
        temp_panelp.add(theory.front_end.counterexample_all_button);
        domain_panel5.add("South",temp_panelp);
        whiteButtons(theory.front_end.counterexample_none_button,
                theory.front_end.counterexample_all_button);

        domain_panel6.setLayout(new BorderLayout());
        domain_panel6.add("North",theory.front_end.split_values_label);
        domain_panel6.add("Center",theory.front_end.split_values_list);
        temp_panelp = new Panel();
        temp_panelp.setLayout(new GridLayout(1,2));
        temp_panelp.add(theory.front_end.split_values_none_button);
        temp_panelp.add(theory.front_end.split_values_all_button);
        domain_panel6.add("South",temp_panelp);
        grayButtons(theory.front_end.split_values_none_button,
                theory.front_end.split_values_all_button);

        // Proof Panel //

        proof_panel.setLayout(new GridLayout(4,1));
        Panel proof_panel1 = new Panel();
        Panel proof_panel2 = new Panel();
        Panel proof_panel3 = new Panel();
        Panel proof_panel4 = new Panel();

        proof_panel1.setLayout(new BorderLayout());
        Panel proof_panel1a = new Panel();
        proof_panel1a.setLayout(new GridLayout(5,1));
        proof_panel1a.add(theory.front_end.add_proof_tables_to_macro_button);
        proof_panel1a.add(theory.front_end.use_ground_instances_in_proving_check);
        proof_panel1a.add(theory.front_end.use_entity_letter_in_proving_check);
        JScrollPane proof_pane1 = new JScrollPane(theory.front_end.algebra_table);
        proof_panel1.add("North", new Label("Algebras"));
        proof_panel1.add("Center",proof_pane1);
        proof_panel1.add("West",proof_panel1a);

        proof_panel2.setLayout(new BorderLayout());
        JScrollPane proof_pane2 = new JScrollPane(theory.front_end.axiom_table);
        proof_panel2.add("North", new Label("Axioms"));
        proof_panel2.add("Center",proof_pane2);

        proof_panel3.setLayout(new BorderLayout());
        JScrollPane proof_pane3 = new JScrollPane(theory.front_end.file_prover_table);
        proof_panel3.add("North", new Label("File Provers"));
        proof_panel3.add("Center",proof_pane3);

        proof_panel4.setLayout(new BorderLayout());
        JScrollPane proof_pane4 = new JScrollPane(theory.front_end.other_prover_table);
        proof_panel4.add("North", new Label("Other Provers"));
        proof_panel4.add("Center",proof_pane4);

        proof_panel.add(proof_panel1);
        proof_panel.add(proof_panel2);
        proof_panel.add(proof_panel3);
        proof_panel.add(proof_panel4);

        // Search Panel //

        search_panel.setLayout(new GridLayout(1,4));
        Panel search_panel1 = new Panel();
        Panel search_panel1a = new Panel();
        search_panel1a.setLayout(new BorderLayout());
        search_panel1.setBackground(Color.white);
        search_panel1a.setBackground(Color.white);
        search_panel1a.add("North",search_panel1);

        GridBagLayout sp1 = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        search_panel1.setLayout(sp1);
        constraints.fill=GridBagConstraints.HORIZONTAL;

        Label strategy_label = new Label("STRATEGY");
        strategy_label.setForeground(Color.red);
        strategy_label.setAlignment(Label.CENTER);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp1.setConstraints(strategy_label, constraints);
        search_panel1.add(strategy_label);
        constraints.weightx=0.0;

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.agenda_limit_label, constraints);
        search_panel1.add(theory.front_end.agenda_limit_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.agenda_limit_text, constraints);
        search_panel1.add(theory.front_end.agenda_limit_text);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.complexity_label, constraints);
        search_panel1.add(theory.front_end.complexity_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.complexity_text, constraints);
        search_panel1.add(theory.front_end.complexity_text);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.tuple_product_limit_label, constraints);
        search_panel1.add(theory.front_end.tuple_product_limit_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.tuple_product_limit_text, constraints);
        search_panel1.add(theory.front_end.tuple_product_limit_text);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.compose_time_limit_label, constraints);
        search_panel1.add(theory.front_end.compose_time_limit_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.compose_time_limit_text, constraints);
        search_panel1.add(theory.front_end.compose_time_limit_text);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.interestingness_zero_min_label, constraints);
        search_panel1.add(theory.front_end.interestingness_zero_min_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.interestingness_zero_min_text, constraints);
        search_panel1.add(theory.front_end.interestingness_zero_min_text);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.best_first_delay_label, constraints);
        search_panel1.add(theory.front_end.best_first_delay_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.best_first_delay_text, constraints);
        search_panel1.add(theory.front_end.best_first_delay_text);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.gc_label, constraints);
        search_panel1.add(theory.front_end.gc_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.gc_text, constraints);
        search_panel1.add(theory.front_end.gc_text);

        sp1.setConstraints(theory.front_end.breadth_first_check, constraints);
        search_panel1.add(theory.front_end.breadth_first_check);

        sp1.setConstraints(theory.front_end.depth_first_check, constraints);
        search_panel1.add(theory.front_end.depth_first_check);

        sp1.setConstraints(theory.front_end.random_check, constraints);
        search_panel1.add(theory.front_end.random_check);

        sp1.setConstraints(theory.front_end.weighted_sum_check, constraints);
        search_panel1.add(theory.front_end.weighted_sum_check);

        sp1.setConstraints(theory.front_end.keep_best_check, constraints);
        search_panel1.add(theory.front_end.keep_best_check);

        sp1.setConstraints(theory.front_end.keep_worst_check, constraints);
        search_panel1.add(theory.front_end.keep_worst_check);

        sp1.setConstraints(theory.front_end.use_forward_lookahead_check, constraints);
        search_panel1.add(theory.front_end.use_forward_lookahead_check);

        sp1.setConstraints(theory.front_end.substitute_definitions_check, constraints);
        search_panel1.add(theory.front_end.substitute_definitions_check);

        sp1.setConstraints(theory.front_end.subobject_overlap_check, constraints);
        search_panel1.add(theory.front_end.subobject_overlap_check);

        sp1.setConstraints(theory.front_end.expand_agenda_check, constraints);
        search_panel1.add(theory.front_end.expand_agenda_check);

        Panel search_panel1d = new Panel();
        Panel search_panel1b = new Panel();
        Panel search_panel1c = new Panel();
        sp1.setConstraints(search_panel1d,constraints);
        search_panel1.add(search_panel1d);

        search_panel1d.setLayout(new GridLayout(1,1));
        search_panel1c.add(theory.front_end.update_front_end_check);
        search_panel1c.add(theory.front_end.update_front_end_button);

        search_panel1d.add(search_panel1c);

        Label required_label = new Label("Required:");
        sp1.setConstraints(required_label, constraints);
        search_panel1.add(required_label);

        constraints.gridwidth=1;
        sp1.setConstraints(theory.front_end.required_text, constraints);
        search_panel1.add(theory.front_end.required_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.required_choice, constraints);
        search_panel1.add(theory.front_end.required_choice);

        Panel search_panel2a = new Panel();
        search_panel2a.setLayout(new BorderLayout());
        Panel search_panel2 = new Panel();
        search_panel2a.add("North",search_panel2);
        search_panel2.setBackground(Color.lightGray);
        search_panel2a.setBackground(Color.lightGray);
        GridBagLayout sp2 = new GridBagLayout();
        constraints = new GridBagConstraints();
        search_panel2.setLayout(sp2);

        Label concept_weights_label = new Label("CONCEPTS");
        concept_weights_label.setAlignment(Label.CENTER);
        concept_weights_label.setForeground(Color.blue);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(concept_weights_label, constraints);
        search_panel2.add(concept_weights_label);

        constraints.weightx=0.0;
        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_applicability_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_applicability_weight_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_applicability_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_applicability_weight_text);

        constraints.weightx=0.0;
        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_coverage_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_coverage_weight_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_coverage_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_coverage_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_comprehensibility_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_comprehensibility_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_comprehensibility_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_comprehensibility_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_children_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_children_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_children_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_children_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_cross_domain_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_cross_domain_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_cross_domain_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_cross_domain_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_development_steps_num_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_development_steps_num_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_development_steps_num_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_development_steps_num_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_discrimination_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_discrimination_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_discrimination_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_discrimination_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_equiv_conj_score_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_equiv_conj_score_weight_label);
        constraints.gridwidth = 1;
        sp2.setConstraints(theory.front_end.concept_equiv_conj_score_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_equiv_conj_score_weight_text);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_equiv_conj_num_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_equiv_conj_num_weight_text);

        constraints.gridwidth = 1;
        sp2.setConstraints(theory.front_end.concept_highlight_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_highlight_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_highlight_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_highlight_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_imp_conj_score_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_imp_conj_score_weight_label);

        constraints.gridwidth = 1;
        sp2.setConstraints(theory.front_end.concept_imp_conj_score_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_imp_conj_score_weight_text);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_imp_conj_num_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_imp_conj_num_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_invariance_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_invariance_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_invariance_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_invariance_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_negative_applicability_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_negative_applicability_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_negative_applicability_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_negative_applicability_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_ne_conj_score_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_ne_conj_score_weight_label);
        constraints.gridwidth = 1;
        sp2.setConstraints(theory.front_end.concept_ne_conj_score_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_ne_conj_score_weight_text);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_ne_conj_num_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_ne_conj_num_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_novelty_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_novelty_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_novelty_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_novelty_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_parent_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_parent_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_parent_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_parent_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_parsimony_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_parsimony_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_parsimony_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_parsimony_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_pi_conj_score_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_pi_conj_score_weight_label);
        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_pi_conj_score_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_pi_conj_score_weight_text);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_pi_conj_num_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_pi_conj_num_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_positive_applicability_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_positive_applicability_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_positive_applicability_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_positive_applicability_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_predictive_power_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_predictive_power_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_predictive_power_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_predictive_power_weight_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_productivity_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_productivity_weight_label);
        constraints.gridwidth = 1;
        sp2.setConstraints(theory.front_end.concept_productivity_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_productivity_weight_text);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_default_productivity_text, constraints);
        search_panel2.add(theory.front_end.concept_default_productivity_text);

        constraints.gridwidth=1;
        sp2.setConstraints(theory.front_end.concept_variety_weight_label, constraints);
        search_panel2.add(theory.front_end.concept_variety_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp2.setConstraints(theory.front_end.concept_variety_weight_text, constraints);
        search_panel2.add(theory.front_end.concept_variety_weight_text);

        sp2.setConstraints(theory.front_end.normalise_concept_measures_check, constraints);
        search_panel2.add(theory.front_end.normalise_concept_measures_check);

        sp2.setConstraints(theory.front_end.reset_concept_weights_button, constraints);
        search_panel2.add(theory.front_end.reset_concept_weights_button);

        Panel sp2_bottom_panel = new Panel();
        sp2_bottom_panel.add(theory.front_end.measure_concepts_check);
        sp2_bottom_panel.add(theory.front_end.measure_concepts_button);

        sp2.setConstraints(sp2_bottom_panel, constraints);
        search_panel2.add(sp2_bottom_panel);

        Panel search_panel3a = new Panel();
        search_panel3a.setLayout(new BorderLayout());
        Panel search_panel3 = new Panel();
        search_panel3a.add("North",search_panel3);
        search_panel3.setBackground(Color.white);
        search_panel3a.setBackground(Color.white);
        GridBagLayout sp3 = new GridBagLayout();
        constraints = new GridBagConstraints();
        search_panel3.setLayout(sp3);

        Label conjecture_weights_label = new Label("CONJECTURES");
        conjecture_weights_label.setAlignment(Label.CENTER);
        conjecture_weights_label.setForeground(Color.red);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp3.setConstraints(conjecture_weights_label, constraints);
        search_panel3.add(conjecture_weights_label);

        constraints.weightx=0.0;
        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.conjecture_applicability_weight_label, constraints);
        search_panel3.add(theory.front_end.conjecture_applicability_weight_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.conjecture_applicability_weight_text, constraints);
        search_panel3.add(theory.front_end.conjecture_applicability_weight_text);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.conjecture_comprehensibility_weight_label, constraints);
        search_panel3.add(theory.front_end.conjecture_comprehensibility_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.conjecture_comprehensibility_weight_text, constraints);
        search_panel3.add(theory.front_end.conjecture_comprehensibility_weight_text);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.conjecture_surprisingness_weight_label, constraints);
        search_panel3.add(theory.front_end.conjecture_surprisingness_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.conjecture_surprisingness_weight_text, constraints);
        search_panel3.add(theory.front_end.conjecture_surprisingness_weight_text);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.conjecture_plausibility_weight_label, constraints);
        search_panel3.add(theory.front_end.conjecture_plausibility_weight_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.conjecture_plausibility_weight_text, constraints);
        search_panel3.add(theory.front_end.conjecture_plausibility_weight_text);

        sp3.setConstraints(theory.front_end.reset_conjecture_weights_button, constraints);
        search_panel3.add(theory.front_end.reset_conjecture_weights_button);

        Panel sp3_panel = new Panel();
        sp3.setConstraints(sp3_panel, constraints);
        sp3_panel.add(theory.front_end.measure_conjectures_check);
        sp3_panel.add(theory.front_end.measure_conjectures_button);
        search_panel3.add(sp3_panel);

        Label c_label = new Label("Methods");
        c_label.setForeground(Color.red);
        c_label.setAlignment(Label.CENTER);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(c_label, constraints);
        search_panel3.add(c_label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.make_equivalences_from_equality_check, constraints);
        search_panel3.add(theory.front_end.make_equivalences_from_equality_check);

        sp3.setConstraints(theory.front_end.make_equivalences_from_combination_check, constraints);
        search_panel3.add(theory.front_end.make_equivalences_from_combination_check);

        sp3.setConstraints(theory.front_end.make_non_exists_from_empty_check, constraints);
        search_panel3.add(theory.front_end.make_non_exists_from_empty_check);

        sp3.setConstraints(theory.front_end.extract_implications_from_equivalences_check, constraints);
        search_panel3.add(theory.front_end.extract_implications_from_equivalences_check);

        sp3.setConstraints(theory.front_end.make_implications_from_subsumptions_check, constraints);
        search_panel3.add(theory.front_end.make_implications_from_subsumptions_check);

        sp3.setConstraints(theory.front_end.make_implicates_from_subsumes_check, constraints);
        search_panel3.add(theory.front_end.make_implicates_from_subsumes_check);

        sp3.setConstraints(theory.front_end.extract_implicates_from_non_exists_check, constraints);
        search_panel3.add(theory.front_end.extract_implicates_from_non_exists_check);

        sp3.setConstraints(theory.front_end.extract_implicates_from_equivalences_check, constraints);
        search_panel3.add(theory.front_end.extract_implicates_from_equivalences_check);

        sp3.setConstraints(theory.front_end.extract_prime_implicates_from_implicates_check, constraints);
        search_panel3.add(theory.front_end.extract_prime_implicates_from_implicates_check);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.make_near_equivalences_check, constraints);
        search_panel3.add(theory.front_end.make_near_equivalences_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.near_equivalence_percent_text, constraints);
        search_panel3.add(theory.front_end.near_equivalence_percent_text);

        constraints.gridwidth=1; //alisonp ...
        sp3.setConstraints(theory.front_end.make_near_implications_check, constraints);
        search_panel3.add(theory.front_end.make_near_implications_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.near_implication_percent_text, constraints);
        search_panel3.add(theory.front_end.near_implication_percent_text);// ... alisonp

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.use_counterexample_barring_check, constraints);
        search_panel3.add(theory.front_end.use_counterexample_barring_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.counterexample_barring_num_text, constraints);
        search_panel3.add(theory.front_end.counterexample_barring_num_text);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.use_concept_barring_check, constraints);
        search_panel3.add(theory.front_end.use_concept_barring_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.concept_barring_num_text, constraints);
        search_panel3.add(theory.front_end.concept_barring_num_text);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.use_applicability_conj_check, constraints);
        search_panel3.add(theory.front_end.use_applicability_conj_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp3.setConstraints(theory.front_end.applicability_conj_num_text, constraints);
        search_panel3.add(theory.front_end.applicability_conj_num_text);

        constraints.gridwidth=1;
        sp3.setConstraints(theory.front_end.require_proof_in_subsumption_check, constraints);
        search_panel3.add(theory.front_end.require_proof_in_subsumption_check);




        Panel search_panel4a = new Panel();
        search_panel4a.setLayout(new BorderLayout());
        Panel search_panel4 = new Panel();
        search_panel4a.add("North",search_panel4);
        search_panel4.setBackground(Color.lightGray);
        search_panel4a.setBackground(Color.lightGray);
        GridBagLayout sp4 = new GridBagLayout();
        constraints = new GridBagConstraints();
        search_panel4.setLayout(sp4);

        Label pr_label = new Label("PRODUCTION RULES");
        pr_label.setAlignment(Label.CENTER);
        pr_label.setForeground(Color.blue);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        sp4.setConstraints(pr_label, constraints);
        search_panel4.add(pr_label);

        Label d_label = new Label("");
        Label arity_label = new Label("arity");
        Label level_label = new Label("level");

        constraints.gridwidth=1;
        sp4.setConstraints(d_label, constraints);
        search_panel4.add(d_label);

        sp4.setConstraints(arity_label, constraints);
        search_panel4.add(arity_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(level_label, constraints);
        search_panel4.add(level_label);

        //friday
        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.arithmeticb_check, constraints);
        search_panel4.add(theory.front_end.arithmeticb_check);

        //friday
        sp4.setConstraints(theory.front_end.arithmeticb_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.arithmeticb_arity_limit_text);

        //friday
        sp4.setConstraints(theory.front_end.arithmeticb_tier_text, constraints);
        search_panel4.add(theory.front_end.arithmeticb_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.compose_check, constraints);
        search_panel4.add(theory.front_end.compose_check);

        sp4.setConstraints(theory.front_end.compose_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.compose_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.compose_tier_text, constraints);
        search_panel4.add(theory.front_end.compose_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.disjunct_check, constraints);
        search_panel4.add(theory.front_end.disjunct_check);

        sp4.setConstraints(theory.front_end.disjunct_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.disjunct_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.disjunct_tier_text, constraints);
        search_panel4.add(theory.front_end.disjunct_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.embed_algebra_check, constraints);
        search_panel4.add(theory.front_end.embed_algebra_check);

        sp4.setConstraints(theory.front_end.embed_algebra_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.embed_algebra_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.embed_algebra_tier_text, constraints);
        search_panel4.add(theory.front_end.embed_algebra_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.embed_graph_check, constraints);
        search_panel4.add(theory.front_end.embed_graph_check);

        sp4.setConstraints(theory.front_end.embed_graph_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.embed_graph_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.embed_graph_tier_text, constraints);
        search_panel4.add(theory.front_end.embed_graph_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.equal_check, constraints);
        search_panel4.add(theory.front_end.equal_check);

        sp4.setConstraints(theory.front_end.equal_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.equal_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.equal_tier_text, constraints);
        search_panel4.add(theory.front_end.equal_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.entity_disjunct_check, constraints);
        search_panel4.add(theory.front_end.entity_disjunct_check);

        sp4.setConstraints(theory.front_end.entity_disjunct_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.entity_disjunct_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.entity_disjunct_tier_text, constraints);
        search_panel4.add(theory.front_end.entity_disjunct_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.exists_check, constraints);
        search_panel4.add(theory.front_end.exists_check);

        sp4.setConstraints(theory.front_end.exists_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.exists_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.exists_tier_text, constraints);
        search_panel4.add(theory.front_end.exists_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.forall_check, constraints);
        search_panel4.add(theory.front_end.forall_check);

        sp4.setConstraints(theory.front_end.forall_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.forall_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.forall_tier_text, constraints);
        search_panel4.add(theory.front_end.forall_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.match_check, constraints);
        search_panel4.add(theory.front_end.match_check);

        sp4.setConstraints(theory.front_end.match_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.match_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.match_tier_text, constraints);
        search_panel4.add(theory.front_end.match_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.negate_check, constraints);
        search_panel4.add(theory.front_end.negate_check);

        sp4.setConstraints(theory.front_end.negate_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.negate_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.negate_tier_text, constraints);
        search_panel4.add(theory.front_end.negate_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.record_check, constraints);
        search_panel4.add(theory.front_end.record_check);

        sp4.setConstraints(theory.front_end.record_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.record_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.record_tier_text, constraints);
        search_panel4.add(theory.front_end.record_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.size_check, constraints);
        search_panel4.add(theory.front_end.size_check);

        sp4.setConstraints(theory.front_end.size_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.size_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.size_tier_text, constraints);
        search_panel4.add(theory.front_end.size_tier_text);

        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.split_check, constraints);
        search_panel4.add(theory.front_end.split_check);

        sp4.setConstraints(theory.front_end.split_arity_limit_text, constraints);
        search_panel4.add(theory.front_end.split_arity_limit_text);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.split_tier_text, constraints);
        search_panel4.add(theory.front_end.split_tier_text);

        //friday
        constraints.gridwidth=1;
        sp4.setConstraints(theory.front_end.arithmeticb_operators_label, constraints);
        search_panel4.add(theory.front_end.arithmeticb_operators_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp4.setConstraints(theory.front_end.arithmeticb_operators_text, constraints);
        search_panel4.add(theory.front_end.arithmeticb_operators_text);
        //friday

        search_panel.add(search_panel1a);
        search_panel.add(search_panel2a);
        search_panel.add(search_panel3a);
        search_panel.add(search_panel4a);

        // Main Buttons Panel //

        main_buttons_panel.setLayout(new GridLayout(2,1));
        //main_buttons_panel.add("North",top_main_buttons_panel);
        //main_buttons_panel.add("Center",bottom_main_buttons_panel);

        top_main_buttons_panel.add(theory.front_end.statistics_label1);
        top_main_buttons_panel.add(theory.front_end.statistics_choice1);
        top_main_buttons_panel.add(theory.front_end.statistics_label2);
        top_main_buttons_panel.add(theory.front_end.statistics_choice2);
        top_main_buttons_panel.add(theory.front_end.statistics_label3);
        top_main_buttons_panel.add(theory.front_end.statistics_choice3);

        Panel bottom_macro_panel = new Panel();
        bottom_macro_panel.add(theory.front_end.quick_macro_choice);
        bottom_macro_panel.add(theory.front_end.save_macro_button);
        bottom_macro_panel.add(theory.front_end.record_macro_check);
        bottom_macro_panel.add(theory.front_end.save_macro_text);
        top_main_buttons_panel.add(bottom_macro_panel);

        theory.front_end.stop_button.setEnabled(false);
        theory.front_end.step_button.setEnabled(false);
        theory.front_end.start_button.setEnabled(true);

        // Statistics Panel //

        statistics_panel.setLayout(new BorderLayout());
        Panel statistics_panel_top = new Panel();
        Panel statistics_panel_top_left = new Panel();
        statistics_panel_top.setLayout(new GridLayout(1,4));
        statistics_panel_top_left.setLayout(new BorderLayout());
        statistics_panel_top.add(statistics_panel_top_left);
        statistics_panel_top.add(theory.front_end.statistics_choice_list);
        statistics_panel_top.add(theory.front_end.statistics_subchoice_list);
        statistics_panel_top.add(theory.front_end.statistics_methods_list);
        Panel statistics_buttons_panel = new Panel();
        statistics_buttons_panel.setLayout(new GridLayout(9,2));
        statistics_buttons_panel.add(theory.front_end.statistics_plot_button);
        statistics_buttons_panel.add(theory.front_end.statistics_compile_button);
        statistics_buttons_panel.add(theory.front_end.statistics_average_check);
        statistics_buttons_panel.add(theory.front_end.statistics_seperate_graphs_check);
        statistics_buttons_panel.add(theory.front_end.statistics_count_check);
        statistics_buttons_panel.add(new Label(""));
        statistics_buttons_panel.add(new Label("Calculation: "));
        statistics_buttons_panel.add(theory.front_end.statistics_calculation_text);
        statistics_buttons_panel.add(new Label("Prune: "));
        statistics_buttons_panel.add(theory.front_end.statistics_prune_text);
        statistics_buttons_panel.add(new Label("Sort: "));
        statistics_buttons_panel.add(theory.front_end.statistics_sort_text);
        statistics_buttons_panel.add(new Label("File: "));
        statistics_buttons_panel.add(theory.front_end.statistics_output_file_text);
        statistics_panel_top.add(statistics_buttons_panel);
        statistics_panel.add("North", statistics_panel_top);
        statistics_panel.add("Center", theory.statistics_handler.statistics_output_panel);
        statistics_panel_top_left.add("Center", theory.front_end.statistics_type_list);
        statistics_panel_top_left.add("South", statistics_buttons_panel);

        // Report Panel //

        report_panel.setLayout(new BorderLayout());
        Panel report_top_panel = new Panel();
        report_top_panel.setLayout(new BorderLayout());
        Panel report_top_mid_panel = new Panel();
        Panel report_top_right_panel = new Panel();
        report_top_mid_panel.setLayout(new GridLayout(3,1));
        report_top_mid_panel.add(theory.front_end.execute_report_button);
        report_top_mid_panel.add(theory.front_end.save_report_button);
        report_top_mid_panel.add(theory.front_end.save_report_name_text);
        report_top_right_panel.setLayout(new BorderLayout());
        report_top_right_panel.add("West",report_top_mid_panel);
        report_top_right_panel.add("Center",theory.front_end.reports_list);
        report_top_panel.add("West",theory.front_end.reports_to_execute_list);
        report_top_panel.add("Center",theory.front_end.report_instructions_text);
        report_top_panel.add("East",report_top_right_panel);

        report_panel.add("North", report_top_panel);
        JScrollPane jscp = new JScrollPane(theory.front_end.report_output_text);
        report_panel.add("Center", jscp);

        // Agenda Panel //

        agenda_panel.setLayout(new BorderLayout());
        Panel agenda_panel_top = new Panel();
        agenda_panel_top.add(theory.front_end.update_agenda_button);
        agenda_panel_top.add(theory.front_end.show_forced_steps_button);
        agenda_panel_top.add(theory.front_end.auto_update_agenda);
        agenda_panel_top.add(theory.front_end.auto_update_text);
        agenda_panel_top.add(theory.front_end.not_allowed_agenda_check);
        agenda_panel_top.add(theory.front_end.current_step_text);
        agenda_panel.add("North",agenda_panel_top);
        Panel agenda_panel_mid = new Panel();
        Panel agenda_panel_mid_right = new Panel();
        agenda_panel_mid_right.setLayout(new GridLayout(1,2));
        agenda_panel_mid.setLayout(new GridLayout(1,2));
        agenda_panel_mid.add(theory.front_end.agenda_list);
        agenda_panel_mid.add(agenda_panel_mid_right);
        agenda_panel_mid_right.add(theory.front_end.live_ordered_concept_list);
        agenda_panel_mid_right.add(theory.front_end.all_ordered_concept_list);
        agenda_panel.add("Center",agenda_panel_mid);
        agenda_panel.add("South",theory.front_end.agenda_concept_text);

        // SystemOut Panel //

        systemout_panel.setLayout(new BorderLayout());
        Panel systemout_panel_top = new Panel();
        systemout_panel_top.add(theory.front_end.systemout_button);
        systemout_panel_top.add(theory.front_end.debug_parameter_text);
        systemout_panel.add("North",systemout_panel_top);
        systemout_panel.add("Center",theory.front_end.systemout_text);

        // Pseudo Code Panel //

        pseudo_code_panel.setLayout(new BorderLayout());
        Panel pseudo_code_panel_top = new Panel();
        pseudo_code_panel_top.add(theory.front_end.pseudo_code_run_button);

        Panel pseudo_code_panel_bottom = new Panel();
        pseudo_code_panel_bottom.setLayout(new GridLayout(1,2));
        pseudo_code_panel_bottom.add(theory.front_end.pseudo_code_input_text);
        pseudo_code_panel_bottom.add(theory.front_end.pseudo_code_output_text);

        pseudo_code_panel.add("North", pseudo_code_panel_top);
        pseudo_code_panel.add("Center", pseudo_code_panel_bottom);

        // Force Panel //

        force_panel.setLayout(new GridLayout(2,1));
        Panel force_panel_top = new Panel();
        Panel force_panel_bottom = new Panel();
        force_panel_top.setLayout(new GridLayout(1,4));
        force_panel_top.add(theory.front_end.force_primary_concept_list);
        force_panel_top.add(theory.front_end.force_secondary_concept_list);
        force_panel_top.add(theory.front_end.force_prodrule_list);
        Panel right_force_panel = new Panel();
        right_force_panel.setLayout(new BorderLayout());
        right_force_panel.add("North", theory.front_end.force_button);
        right_force_panel.add("Center", theory.front_end.force_result_text);
        force_panel_top.add(right_force_panel);
        force_panel_bottom = new Panel();
        force_panel_bottom.setLayout(new BorderLayout());
        theory.front_end.force_string_text.setBackground(Color.orange);
        theory.front_end.force_string_text.setForeground(Color.blue);
        force_panel_bottom.add("North", theory.front_end.force_string_text);
        force_panel_bottom.add("Center", theory.front_end.force_parameter_list);
        force_panel.add(force_panel_top);
        force_panel.add(force_panel_bottom);

        // Concepts Panel //

        concepts_panel.setLayout(new BorderLayout());
        Panel temp_panelz = new Panel();
        Panel temp_panelz2 = new Panel();
        Panel temp_panelt = new Panel();
        temp_panelz2.setLayout(new GridLayout(3,1));
        temp_panelz.setLayout(new BorderLayout());
        temp_panelz2.add(theory.front_end.concept_formatting_list);
        temp_panelz2.add(theory.front_end.concept_pruning_list);
        temp_panelz2.add(theory.front_end.concept_sorting_list);
        temp_panelz.add("North",temp_panelz2);
        temp_panelz.add("Center",theory.front_end.concept_list);
        temp_panelz.add("South",theory.front_end.concept_list_number);
        concepts_panel.add("West",temp_panelz);
        Panel temp_panelq = new Panel();
        Panel temp_panelr = new Panel();
        Panel temp_panels = new Panel();
        Panel temp_panelu = new Panel();
        temp_panelq.setLayout(new BorderLayout());
        temp_panelq.add("North",theory.front_end.ancestor_list);
        JScrollPane jsp = new JScrollPane(theory.front_end.concept_text_box);
        temp_panelq.add("Center",jsp);
        temp_panelq.add("South",temp_panelt);
        temp_panelt.setLayout(new GridLayout(2,1));
        temp_panelt.add(theory.front_end.children_list);
        temp_panelt.add(temp_panelr);
        temp_panelr.setLayout(new GridLayout(3,1));
        temp_panelr.add(temp_panels);
        temp_panels.setLayout(new GridLayout(1,6));
        temp_panels.add(theory.front_end.calculate_entity1_text);
        temp_panels.add(theory.front_end.calculate_entity2_text);
        temp_panels.add(theory.front_end.calculate_button);
        temp_panels.add(theory.front_end.draw_concept_construction_history_button);
        temp_panels.add(theory.front_end.find_extra_conjectures_button);
        temp_panelr.add(theory.front_end.calculate_output_text);
        temp_panelr.add(temp_panelu);
        temp_panelu.setLayout(new BorderLayout());
        Panel temp_panelu2 = new Panel();
        temp_panelu2.setLayout(new GridLayout(1,2));
        temp_panelu2.add(theory.front_end.grep_concept_text);
        temp_panelu2.add(theory.front_end.condition_concept_text);
        temp_panelu.add("Center",temp_panelu2);
        temp_panelu.add("West",theory.front_end.grep_concept_button);
        temp_panelu.add("East",theory.front_end.condition_concept_button);
        theory.front_end.grep_concept_text.setBackground(Color.green);
        theory.front_end.condition_concept_text.setBackground(Color.yellow);
        concepts_panel.add("Center",temp_panelq);

        // Conjectures Panel //
        conjectures_panel.setLayout(new BorderLayout());
        temp_panelz = new Panel();
        temp_panelz2 = new Panel();
        temp_panelt = new Panel();
        temp_panelz2.setLayout(new GridLayout(3,1));
        temp_panelz.setLayout(new BorderLayout());
        temp_panelz2.add(theory.front_end.conjecture_formatting_list);
        temp_panelz2.add(theory.front_end.conjecture_pruning_list);
        temp_panelz2.add(theory.front_end.conjecture_sorting_list);
        temp_panelz.add("North",temp_panelz2);
        temp_panelz.add("Center",theory.front_end.conjecture_list);
        temp_panelz.add("South",theory.front_end.conjecture_list_number);
        conjectures_panel.add("West",temp_panelz);
        Panel rh_conjectures_panel = new Panel();

        rh_conjectures_panel.setLayout(new BorderLayout());
        JScrollPane jscp2 = new JScrollPane(theory.front_end.conjecture_text_box);
        rh_conjectures_panel.add("Center",jscp2);
        Panel conjectures_buttons_panel = new Panel();
        conjectures_buttons_panel.setLayout(new BorderLayout());
        Panel conjectures_buttons_panel_top = new Panel();
        conjectures_buttons_panel_top.add(theory.front_end.add_conjecture_as_axiom_button);
        conjectures_buttons_panel_top.add(theory.front_end.remove_conjecture_as_axiom_button);
        conjectures_buttons_panel_top.add(theory.front_end.re_prove_all_button);
        conjectures_buttons_panel_top.add(theory.front_end.draw_conjecture_construction_history_button);

        conjectures_buttons_panel_top.add(theory.front_end.test_conjecture_entity1_text);
        conjectures_buttons_panel_top.add(theory.front_end.test_conjecture_entity2_text);
        conjectures_buttons_panel_top.add(theory.front_end.test_conjecture_button);
        conjectures_buttons_panel_top.add(theory.front_end.test_conjecture_output_text);
        conjectures_buttons_panel_top.add(theory.front_end.add_counterexamples_found_from_testing_button);

        conjectures_buttons_panel.add("North",conjectures_buttons_panel_top);
        conjectures_buttons_panel.add("Center",theory.front_end.child_conjectures_list);
        Panel conjectures_buttons_panel_bottom = new Panel();
        conjectures_buttons_panel_bottom.setLayout(new BorderLayout());
        conjectures_buttons_panel_bottom.add("West",theory.front_end.grep_conjecture_button);
        conjectures_buttons_panel_bottom.add("East",theory.front_end.condition_conjecture_button);
        Panel temp_panelz3 = new Panel();
        temp_panelz3.setLayout(new GridLayout(1,2));
        temp_panelz3.add(theory.front_end.grep_conjecture_text);
        temp_panelz3.add(theory.front_end.condition_conjecture_text);
        conjectures_buttons_panel_bottom.add("Center",temp_panelz3);
        conjectures_buttons_panel.add("South",conjectures_buttons_panel_bottom);
        theory.front_end.grep_conjecture_text.setBackground(Color.green);
        theory.front_end.condition_conjecture_text.setBackground(Color.yellow);

        rh_conjectures_panel.add("North", theory.front_end.parent_conjectures_list);
        rh_conjectures_panel.add("South", conjectures_buttons_panel);
        conjectures_panel.add("Center", rh_conjectures_panel);

        // Entities Panel //
        entities_panel.setLayout(new BorderLayout());
        temp_panelz = new Panel();
        temp_panelz2 = new Panel();
        temp_panelt = new Panel();
        temp_panelz2.setLayout(new GridLayout(3,1));
        temp_panelz.setLayout(new BorderLayout());

        temp_panelz2.add(theory.front_end.entity_formatting_list);
        temp_panelz2.add(theory.front_end.entity_pruning_list);
        temp_panelz2.add(theory.front_end.entity_sorting_list);
        temp_panelz.add("North",temp_panelz2);
        temp_panelz.add("Center",theory.front_end.entity_list);
        entities_panel.add("West",temp_panelz);
        Panel temp_panelza = new Panel();
        Panel temp_panelzb = new Panel();
        Panel temp_panelzc = new Panel();
        temp_panelza.setLayout(new BorderLayout());
        temp_panelzb.setLayout(new BorderLayout());
        JScrollPane jspe = new JScrollPane(theory.front_end.entity_text_box);
        temp_panelza.add("Center",jspe);
        temp_panelza.add("South",temp_panelzb);
        temp_panelzb.add("North", temp_panelzc);
        temp_panelzb.add("South", theory.front_end.percent_categorisation_text);
        temp_panelzc.add(theory.front_end.percent_categorisation_button);
        temp_panelzc.add(theory.front_end.percent_down_button);
        temp_panelzc.add(theory.front_end.percent_categorisation_percent_text);
        temp_panelzc.add(theory.front_end.percent_up_button);
        entities_panel.add("Center",temp_panelza);

        // Macro Panel //

        macro_panel.setLayout(new BorderLayout());
        Panel macro_panel_left = new Panel();
        macro_panel_left.setLayout(new BorderLayout());
        macro_panel_left.add("North", theory.front_end.play_macro_button);
        macro_panel_left.add("Center", theory.front_end.macro_list);

        Panel macro_panel_mid = new Panel();
        macro_panel_mid.setLayout(new BorderLayout());
        macro_panel_mid.add("Center", macro_centre_panel);
        macro_panel_mid.add("South", theory.front_end.macro_error_text);

        macro_text_panel.setLayout(new BorderLayout());
        macro_text_panel.add("Center", theory.front_end.macro_text);
        hello_text_panel.setLayout(new BorderLayout());
        hello_text_panel.add("Center",theory.front_end.hello_text);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(theory.front_end.hello_text);
        hello_text_panel.add(scrollPane);

        macro_centre_panel.setLayout(macro_text_layout);
        macro_text_layout.addLayoutComponent(hello_text_panel, "hello_panel");
        macro_centre_panel.add("hello_panel", hello_text_panel);
        macro_centre_panel.add("macro_panel", macro_text_panel);
        macro_text_layout.show(macro_centre_panel, "hello_panel");

        Panel macro_panel_right = new Panel();
        macro_panel_right.setLayout(new BorderLayout());
        macro_panel_right.add("North", theory.front_end.restore_theory_button);
        macro_panel_right.add("Center", theory.front_end.stored_theories_list);

        macro_panel.add("East", macro_panel_right);
        macro_panel.add("West", macro_panel_left);
        macro_panel.add("Center", macro_panel_mid);

        // Reactions Panel //

        reactions_panel.setLayout(new BorderLayout());
        reactions_panel.add("Center", theory.front_end.reactions_text);
        Panel reactions_right_panel = new Panel();
        Panel reactions_right_top_panel = new Panel();
        Panel reactions_left_panel = new Panel();
        reactions_left_panel.setLayout(new BorderLayout());
        Panel reactions_left_panel_top = new Panel();
        reactions_left_panel_top.setLayout(new GridLayout(2,1));
        reactions_left_panel_top.add(theory.front_end.reaction_name_text);
        reactions_left_panel_top.add(theory.front_end.save_reaction_button);
        reactions_left_panel.add("North", reactions_left_panel_top);
        reactions_left_panel.add("Center", theory.front_end.reactions_list);
        reactions_panel.add("West", reactions_left_panel);
        reactions_right_top_panel.setLayout(new GridLayout(1,2));
        reactions_right_top_panel.add(theory.front_end.add_reaction_button);
        reactions_right_top_panel.add(theory.front_end.remove_reaction_button);
        reactions_right_panel.setLayout(new BorderLayout());
        reactions_right_panel.add("North", reactions_right_top_panel);
        reactions_right_panel.add("Center", theory.front_end.reactions_added_list);
        reactions_panel.add("East", reactions_right_panel);


        // alisonp
        // Lakatos Panel - alisonp//

        lakatos_panel.setLayout(new BorderLayout());
        Panel lakatos_panel_top = new Panel();
        //commented stuff here
        Panel lakatos_search_panel = new Panel(new BorderLayout());
        Panel bottom_lakatos_search_panel = new Panel(new GridLayout(1,5));
        bottom_lakatos_search_panel.add(theory.front_end.agent_search_button);
        bottom_lakatos_search_panel.add(theory.front_end.agent_search_text);
        lakatos_search_panel.add(BorderLayout.CENTER, theory.front_end.agent_search_output_list);
        lakatos_search_panel.add(BorderLayout.SOUTH, bottom_lakatos_search_panel);
        lakatos_panel_top.add(lakatos_search_panel);

        JTabbedPane tabbed_pane = theory.front_end.lakatos_tabbed_pane;

        //lakatos_panel.add("North", lakatos_panel_top);

        lakatos_panel.add("Center", theory.front_end.lakatos_tabbed_pane);
        tabbed_pane.add("Teacher", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 1", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 2", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 3", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 4", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 5", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 6", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 7", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 8", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 9", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Student 10", new AgentOutputPanel(tabbed_pane, false));
        tabbed_pane.add("Group", new AgentOutputPanel(tabbed_pane, true));

        /** end alisonp */


        // Methods Panel //
        methods_panel.setLayout(new BorderLayout());
        Panel methods_panel_top = new Panel();
        methods_panel_top.setLayout(new GridLayout(1,4));
        Panel methods_panel1 = new Panel();
        Panel methods_panel1a = new Panel();
        methods_panel1a.setLayout(new BorderLayout());
        methods_panel1.setBackground(Color.white);
        methods_panel1a.setBackground(Color.white);
        methods_panel1a.add("North",methods_panel1);

        GridBagLayout mp1 = new GridBagLayout();
        constraints = new GridBagConstraints();
        methods_panel1.setLayout(mp1);
        constraints.fill=GridBagConstraints.HORIZONTAL;

        Label methods_label = new Label("LAKATOS METHODS");
        //methods_label.setBackground(Color.lightGray);
        methods_label.setForeground(Color.red);
        methods_label.setAlignment(Label.CENTER);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        mp1.setConstraints(methods_label, constraints);
        methods_panel1.add(methods_label);
        constraints.weightx=0.0;

        //individual
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        //nstraints.gridwidth=1;
        mp1.setConstraints(theory.front_end.use_surrender_check, constraints);
        methods_panel1.add(theory.front_end.use_surrender_check);

        mp1.setConstraints(theory.front_end.use_strategic_withdrawal_check, constraints);
        methods_panel1.add(theory.front_end.use_strategic_withdrawal_check);

        mp1.setConstraints(theory.front_end.use_piecemeal_exclusion_check, constraints);
        methods_panel1.add(theory.front_end.use_piecemeal_exclusion_check);

        mp1.setConstraints(theory.front_end.use_monster_barring_check, constraints);
        methods_panel1.add(theory.front_end.use_monster_barring_check);

        mp1.setConstraints(theory.front_end.use_monster_adjusting_check, constraints);
        methods_panel1.add(theory.front_end.use_monster_adjusting_check);

        mp1.setConstraints(theory.front_end.use_lemma_incorporation_check, constraints);
        methods_panel1.add(theory.front_end.use_lemma_incorporation_check);

        mp1.setConstraints(theory.front_end.use_proofs_and_refutations_check, constraints);
        methods_panel1.add(theory.front_end.use_proofs_and_refutations_check);

        //communal
        mp1.setConstraints(theory.front_end.use_communal_piecemeal_exclusion_check, constraints);
        methods_panel1.add(theory.front_end.use_communal_piecemeal_exclusion_check);

        methods_panel_top.add(methods_panel1);

        // type of conjecture that the teacher requests

        /** 15/03/04 */
        Panel methods_panel3a = new Panel();
        methods_panel3a.setLayout(new BorderLayout());
        Panel methods_panel3 = new Panel();
        methods_panel3a.add("North", methods_panel3);
        methods_panel3.setBackground(Color.lightGray);
        methods_panel3a.setBackground(Color.lightGray);

        GridBagLayout mp3 = new GridBagLayout();
        constraints = new GridBagConstraints();
        methods_panel3.setLayout(mp3);


        Label conjecture_request_label = new Label("Conjecture request");
        conjecture_request_label.setAlignment(Label.CENTER);
        conjecture_request_label.setForeground(Color.red);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        mp3.setConstraints(conjecture_request_label, constraints);
        methods_panel3.add(conjecture_request_label);


        //conjecture request
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        mp3.setConstraints(theory.front_end.teacher_requests_conjecture_check, constraints);
        methods_panel3.add(theory.front_end.teacher_requests_conjecture_check);

        mp3.setConstraints(theory.front_end.teacher_requests_implication_check, constraints);
        methods_panel3.add(theory.front_end.teacher_requests_implication_check);

        mp3.setConstraints(theory.front_end.teacher_requests_nearimplication_check, constraints);
        methods_panel3.add(theory.front_end.teacher_requests_nearimplication_check);

        mp3.setConstraints(theory.front_end.teacher_requests_equivalence_check, constraints);
        methods_panel3.add(theory.front_end.teacher_requests_equivalence_check);

        mp3.setConstraints(theory.front_end.teacher_requests_nearequivalence_check, constraints);
        methods_panel3.add(theory.front_end.teacher_requests_nearequivalence_check);

        mp3.setConstraints(theory.front_end.teacher_requests_nonexists_check, constraints);
        methods_panel3.add(theory.front_end.teacher_requests_nonexists_check);

        methods_panel_top.add(methods_panel3);
        /** 15/03/04 */

        //**********************************


        Panel mp3_panel = new Panel();
        mp3.setConstraints(mp3_panel, constraints);
        //mp3_panel.add(theory.front_end.measure_conjectures_check);
        //mp3_panel.add(theory.front_end.measure_conjectures_button);
        methods_panel3.add(mp3_panel);

        Label disc_label = new Label("Discussion");
        disc_label.setForeground(Color.red);
        disc_label.setAlignment(Label.CENTER);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp3.setConstraints(disc_label, constraints);
        methods_panel3.add(disc_label);

        constraints.gridwidth=1;
        mp3.setConstraints(theory.front_end.max_num_independent_work_stages_check, constraints);
        methods_panel3.add(theory.front_end.max_num_independent_work_stages_check);


        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp3.setConstraints(theory.front_end.max_num_independent_work_stages_value_text, constraints);
        methods_panel3.add(theory.front_end.max_num_independent_work_stages_value_text);


        constraints.gridwidth=1;
        mp3.setConstraints(theory.front_end.threshold_to_add_conj_to_theory_check, constraints);
        methods_panel3.add(theory.front_end.threshold_to_add_conj_to_theory_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp3.setConstraints(theory.front_end.threshold_to_add_conj_to_theory_value_text, constraints);
        methods_panel3.add(theory.front_end.threshold_to_add_conj_to_theory_value_text);

        constraints.gridwidth=1;
        mp3.setConstraints(theory.front_end.threshold_to_add_concept_to_theory_check, constraints);
        methods_panel3.add(theory.front_end.threshold_to_add_concept_to_theory_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp3.setConstraints(theory.front_end.threshold_to_add_concept_to_theory_value_text, constraints);
        methods_panel3.add(theory.front_end.threshold_to_add_concept_to_theory_value_text);

        constraints.gridwidth=1;
        mp3.setConstraints(theory.front_end.num_independent_steps_check, constraints);
        methods_panel3.add(theory.front_end.num_independent_steps_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp3.setConstraints(theory.front_end.num_independent_steps_value_text, constraints);
        methods_panel3.add(theory.front_end.num_independent_steps_value_text);



        //add flags here

        //**********************************




        Panel methods_panel2a = new Panel();
        methods_panel2a.setLayout(new BorderLayout());
        Panel methods_panel2 = new Panel();
        methods_panel2a.add("North",methods_panel2);
        methods_panel2.setBackground(Color.white);
        methods_panel2a.setBackground(Color.white);

        GridBagLayout mp2 = new GridBagLayout();
        constraints = new GridBagConstraints();
        methods_panel2.setLayout(mp2);

        //surrender
        Label lakatos_variables_label = new Label("Surrender");
        lakatos_variables_label.setAlignment(Label.CENTER);
        lakatos_variables_label.setForeground(Color.red);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        mp2.setConstraints(lakatos_variables_label, constraints);
        methods_panel2.add(lakatos_variables_label);

        //constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridwidth=1;

        //constraints.weightx=0.0;
        //constraints.gridwidth=1;
        mp2.setConstraints(theory.front_end.number_modifications_check, constraints);
        methods_panel2.add(theory.front_end.number_modifications_check);



        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp2.setConstraints(theory.front_end.modifications_value_text, constraints);
        methods_panel2.add(theory.front_end.modifications_value_text);

        constraints.gridwidth=1;
        mp2.setConstraints(theory.front_end.interestingness_threshold_check, constraints);
        methods_panel2.add(theory.front_end.interestingness_threshold_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp2.setConstraints(theory.front_end.interestingness_threshold_value_text, constraints);
        methods_panel2.add(theory.front_end.interestingness_threshold_value_text);

        constraints.gridwidth=1;
        mp2.setConstraints(theory.front_end.plausibility_threshold_check, constraints);
        methods_panel2.add(theory.front_end.plausibility_threshold_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp2.setConstraints(theory.front_end.plausibility_threshold_value_text, constraints);
        methods_panel2.add(theory.front_end.plausibility_threshold_value_text);


        constraints.gridwidth=1;
        mp2.setConstraints(theory.front_end.domain_application_threshold_check, constraints);
        methods_panel2.add(theory.front_end.domain_application_threshold_check);


        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp2.setConstraints(theory.front_end.domain_app_threshold_value_text, constraints);
        methods_panel2.add(theory.front_end.domain_app_threshold_value_text);

        mp2.setConstraints(theory.front_end.compare_average_interestingness_check, constraints);
        methods_panel2.add(theory.front_end.compare_average_interestingness_check);


        Panel mp2_panel = new Panel();
        mp2.setConstraints(mp2_panel, constraints);
        //mp2_panel.add(theory.front_end.measure_conjectures_check);
        //mp2_panel.add(theory.front_end.measure_conjectures_button);
        methods_panel2.add(mp2_panel);

        Label eb_label = new Label("Exception-barring");
        //lakatos_variables_label.setAlignment(Label.CENTER);
        eb_label.setForeground(Color.red);
        eb_label.setAlignment(Label.CENTER);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp2.setConstraints(eb_label, constraints);
        methods_panel2.add(eb_label);


        // Label lakatos_variables_label_test = new Label("Exception-barring");
//     lakatos_variables_label_test.setAlignment(Label.CENTER);
//     lakatos_variables_label_test.setForeground(Color.red);
//     constraints.fill=GridBagConstraints.HORIZONTAL;
//     constraints.gridwidth = GridBagConstraints.REMAINDER;


        mp2.setConstraints(theory.front_end.use_counterexample_barring_check, constraints);
        methods_panel2.add(theory.front_end.use_counterexample_barring_check);

        mp2.setConstraints(theory.front_end.use_piecemeal_exclusion_check, constraints);
        methods_panel2.add(theory.front_end.use_piecemeal_exclusion_check);

        mp2.setConstraints(theory.front_end.use_strategic_withdrawal_check, constraints);
        methods_panel2.add(theory.front_end.use_strategic_withdrawal_check);

        methods_panel_top.add(methods_panel2);
        methods_panel.add(methods_panel1);
        methods_panel.add(methods_panel_top);

        //start here -- tuesday


        Panel methods_panel4a = new Panel();
        methods_panel4a.setLayout(new BorderLayout());
        Panel methods_panel4 = new Panel();
        methods_panel4a.add("North",methods_panel4);
        methods_panel4.setBackground(Color.lightGray);
        methods_panel4a.setBackground(Color.lightGray);

        GridBagLayout mp4 = new GridBagLayout();
        constraints = new GridBagConstraints();
        methods_panel4.setLayout(mp4);

        //Monster-barring
        Label lakatos_variables_label_mb = new Label("Monster-barring");
        lakatos_variables_label_mb.setAlignment(Label.CENTER);
        lakatos_variables_label_mb.setForeground(Color.red);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        mp4.setConstraints(lakatos_variables_label_mb, constraints);
        methods_panel4.add(lakatos_variables_label_mb);

        constraints.gridwidth = GridBagConstraints.REMAINDER;

        mp4.setConstraints(theory.front_end.use_breaks_conj_under_discussion_check, constraints);
        methods_panel4.add(theory.front_end.use_breaks_conj_under_discussion_check);
        //constraints.gridwidth=GridBagConstraints.REMAINDER;

        mp4.setConstraints(theory.front_end.accept_strictest_check, constraints);
        methods_panel4.add(theory.front_end.accept_strictest_check);

        constraints.gridwidth=1;
        mp4.setConstraints(theory.front_end.use_percentage_conj_broken_check, constraints);
        methods_panel4.add(theory.front_end.use_percentage_conj_broken_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp4.setConstraints(theory.front_end.monster_barring_min_text, constraints);
        methods_panel4.add(theory.front_end.monster_barring_min_text);


        constraints.gridwidth=1;
        mp4.setConstraints(theory.front_end.use_culprit_breaker_check, constraints);
        methods_panel4.add(theory.front_end.use_culprit_breaker_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp4.setConstraints(theory.front_end.monster_barring_culprit_min_text, constraints);
        methods_panel4.add(theory.front_end.monster_barring_culprit_min_text);

        mp4.setConstraints(theory.front_end.use_culprit_breaker_on_conj_check, constraints);
        methods_panel4.add(theory.front_end.use_culprit_breaker_on_conj_check);

        mp4.setConstraints(theory.front_end.use_culprit_breaker_on_all_check, constraints);
        methods_panel4.add(theory.front_end.use_culprit_breaker_on_all_check);


        //
        constraints.gridwidth=1;
        mp4.setConstraints(theory.front_end.monster_barring_type_check, constraints);
        methods_panel4.add(theory.front_end.monster_barring_type_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        mp4.setConstraints(theory.front_end.monster_barring_type_text, constraints);
        methods_panel4.add(theory.front_end.monster_barring_type_text);

        //


        methods_panel_top.add(methods_panel4);
        methods_panel.add(methods_panel1);
        methods_panel.add(methods_panel_top);

        //end here -- tuesday




        // mp2.setConstraints(theory.front_end.group_agenda_best_first_responses_check, constraints);
//     methods_panel2.add(theory.front_end.group_agenda_best_first_responses_check);

//     mp2.setConstraints(theory.front_end.group_agenda_best_first_agenda_check, constraints);
//     methods_panel2.add(theory.front_end.group_agenda_best_first_agenda_check);

//     mp2.setConstraints(theory.front_end.group_agenda_combine_responses_check, constraints);
//     methods_panel2.add(theory.front_end.group_agenda_combine_responses_check);

//     mp2.setConstraints(theory.front_end.group_agenda_depth_first_check, constraints);
//     methods_panel2.add(theory.front_end.group_agenda_depth_first_check);

//     mp2.setConstraints(theory.front_end.group_agenda_breadth_first_check, constraints);
//     methods_panel2.add(theory.front_end.group_agenda_breadth_first_check);

        // Store Panel //

        Panel store_panel1 = new Panel();
        Panel store_panel2 = new Panel();
        Panel store_panel3 = new Panel();

        Panel store_panel1a = new Panel();
        Panel store_panel2a = new Panel();
        Panel store_panel3a = new Panel();

        store_panel1a.setLayout(new BorderLayout());
        store_panel2a.setLayout(new BorderLayout());
        store_panel3a.setLayout(new BorderLayout());

        GridBagLayout stp1 = new GridBagLayout();
        store_panel1.setLayout(stp1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        Label store_panel1_label = new Label("During Theory Formation");
        store_panel1_label.setAlignment(Label.CENTER);
        store_panel1_label.setForeground(Color.blue);
        stp1.setConstraints(store_panel1_label, constraints);

        store_panel1.add(store_panel1_label);

        store_panel1.add(new Label(""));
        store_panel1.add(new Label("Condition"));
        Label dlabel1 = new Label("Output file");
        stp1.setConstraints(dlabel1, constraints);
        store_panel1.add(dlabel1);
        stp1.setConstraints(theory.front_end.keep_steps_check, constraints);
        store_panel1.add(theory.front_end.keep_steps_check);
        stp1.setConstraints(theory.front_end.store_all_conjectures_text, constraints);
        store_panel1.add(theory.front_end.store_all_conjectures_check);
        store_panel1.add(theory.front_end.store_all_conjectures_text);
        stp1.setConstraints(theory.front_end.keep_equivalences_file_text, constraints);
        store_panel1.add(theory.front_end.keep_equivalences_check);
        store_panel1.add(theory.front_end.keep_equivalences_condition_text);
        store_panel1.add(theory.front_end.keep_equivalences_file_text);
        stp1.setConstraints(theory.front_end.keep_non_exists_file_text, constraints);
        store_panel1.add(theory.front_end.keep_non_exists_check);
        store_panel1.add(theory.front_end.keep_non_exists_condition_text);
        store_panel1.add(theory.front_end.keep_non_exists_file_text);
        stp1.setConstraints(theory.front_end.keep_implications_file_text, constraints);
        store_panel1.add(theory.front_end.keep_implications_check);
        store_panel1.add(theory.front_end.keep_implications_condition_text);
        store_panel1.add(theory.front_end.keep_implications_file_text);
        stp1.setConstraints(theory.front_end.keep_implicates_file_text, constraints);
        store_panel1.add(theory.front_end.keep_implicates_check);
        store_panel1.add(theory.front_end.keep_implicates_condition_text);
        store_panel1.add(theory.front_end.keep_implicates_file_text);
        stp1.setConstraints(theory.front_end.keep_implicates_file_text, constraints);
        store_panel1.add(theory.front_end.keep_prime_implicates_check);
        store_panel1.add(theory.front_end.keep_prime_implicates_condition_text);
        store_panel1.add(theory.front_end.keep_prime_implicates_file_text);

        GridBagLayout stp2 = new GridBagLayout();
        store_panel2.setLayout(stp2);
        Label store_panel2_label = new Label("Save Theory");
        store_panel2_label.setAlignment(Label.CENTER);
        store_panel2_label.setForeground(Color.blue);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        stp2.setConstraints(store_panel2_label, constraints);
        store_panel2.add(store_panel2_label);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        stp2.setConstraints(theory.front_end.store_theory_name_text, constraints);
        store_panel2.add(theory.front_end.store_theory_button);
        store_panel2.add(theory.front_end.store_theory_name_text);

        stp2.setConstraints(theory.front_end.save_concept_construction_filename_text, constraints);
        store_panel2.add(theory.front_end.save_concept_construction_button);
        store_panel2.add(theory.front_end.save_concept_construction_concept_id_text);
        store_panel2.add(theory.front_end.save_concept_construction_filename_text);

        stp2.setConstraints(theory.front_end.save_conjecture_construction_filename_text, constraints);
        store_panel2.add(theory.front_end.save_conjecture_construction_button);
        store_panel2.add(theory.front_end.save_conjecture_construction_conjecture_id_text);
        store_panel2.add(theory.front_end.save_conjecture_construction_filename_text);

        GridBagLayout stp3 = new GridBagLayout();
        store_panel3.setLayout(stp3);
        Label store_panel3_label = new Label("Restore Theory");
        store_panel3_label.setAlignment(Label.CENTER);
        store_panel3_label.setForeground(Color.blue);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        stp3.setConstraints(store_panel3_label, constraints);
        store_panel3.add(store_panel3_label);

        stp3.setConstraints(theory.front_end.build_concept_filename_text, constraints);
        store_panel3.add(theory.front_end.build_concept_button);
        store_panel3.add(theory.front_end.build_concept_construction_concept_id_text);
        store_panel3.add(theory.front_end.build_concept_filename_text);

        stp3.setConstraints(theory.front_end.build_conjecture_filename_text, constraints);
        store_panel3.add(theory.front_end.build_conjecture_button);
        store_panel3.add(theory.front_end.build_conjecture_construction_conjecture_id_text);
        store_panel3.add(theory.front_end.build_conjecture_filename_text);

        store_panel1a.add("North", store_panel1);
        store_panel2a.add("North", store_panel2);
        store_panel3a.add("North", store_panel3);

        store_panel.setLayout(new GridLayout(3,1));
        store_panel.add(store_panel1a);
        store_panel.add(store_panel2a);
        store_panel.add(store_panel3a);

        // Interface Panel //

        sp1 = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.weightx=0.0;
        Panel interface_panel1 = new Panel();
        interface_panel1.setLayout(sp1);
        constraints.fill=GridBagConstraints.HORIZONTAL;

        interface_panel1.setBackground(Color.lightGray);

        Label mw_label = new Label("MATHWEB");
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(mw_label, constraints);
        interface_panel1.add(mw_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.mathweb_hostname_label, constraints);
        interface_panel1.add(theory.front_end.mathweb_hostname_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.mathweb_inout_socket_label, constraints);
        interface_panel1.add(theory.front_end.mathweb_inout_socket_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.mathweb_service_socket_label, constraints);
        interface_panel1.add(theory.front_end.mathweb_service_socket_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.use_mathweb_atp_service_check, constraints);
        interface_panel1.add(theory.front_end.use_mathweb_atp_service_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.use_mathweb_otter_check, constraints);
        interface_panel1.add(theory.front_end.use_mathweb_otter_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.mathweb_atp_require_all_check, constraints);
        interface_panel1.add(theory.front_end.mathweb_atp_require_all_check);

        Label provers_label = new Label("Provers to Use");
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(provers_label, constraints);
        interface_panel1.add(provers_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.use_bliksem_in_mathweb_check, constraints);
        interface_panel1.add(theory.front_end.use_bliksem_in_mathweb_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.use_e_in_mathweb_check, constraints);
        interface_panel1.add(theory.front_end.use_e_in_mathweb_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.use_otter_in_mathweb_check, constraints);
        interface_panel1.add(theory.front_end.use_otter_in_mathweb_check);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.use_spass_in_mathweb_check, constraints);
        interface_panel1.add(theory.front_end.use_spass_in_mathweb_check);

        Label sot_label = new Label("SYSTEMONTPTP");
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(sot_label, constraints);
        interface_panel1.add(sot_label);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.sot_submit_button, constraints);
        interface_panel1.add(theory.front_end.sot_submit_button);

        constraints.gridwidth=GridBagConstraints.REMAINDER;
        sp1.setConstraints(theory.front_end.sot_conjecture_number_text, constraints);
        interface_panel1.add(theory.front_end.sot_conjecture_number_text);

        interface_panel.setLayout(new BorderLayout());
        interface_panel.add("West", interface_panel1);
        interface_panel.add("Center", theory.front_end.sot_results_text);

        // Learn Panel //

        Panel learn_panel_top = new Panel();
        GridBagLayout lp1 = new GridBagLayout();
        learn_panel_top.setLayout(lp1);

        learn_panel_top.add(new Label("Gold standard categorisation:"));
        lp1.setConstraints(theory.front_end.gold_standard_categorisation_text, constraints);
        learn_panel_top.add(theory.front_end.gold_standard_categorisation_text);

        learn_panel_top.add(new Label("Concept object type:"));
        lp1.setConstraints(theory.front_end.object_types_to_learn_text, constraints);
        learn_panel_top.add(theory.front_end.object_types_to_learn_text);

        learn_panel_top.add(new Label("Coverage categorisation:"));
        lp1.setConstraints(theory.front_end.coverage_categorisation_text, constraints);
        learn_panel_top.add(theory.front_end.coverage_categorisation_text);

        learn_panel_top.add(new Label("Segregation categorisation:"));
        lp1.setConstraints(theory.front_end.segregation_categorisation_text, constraints);
        learn_panel_top.add(theory.front_end.segregation_categorisation_text);

        lp1.setConstraints(theory.front_end.segregated_search_check, constraints);
        learn_panel_top.add(theory.front_end.segregated_search_check);

        learn_panel.setLayout(new GridLayout());
        learn_panel.add("North", learn_panel_top);

        // Puzzle Panel //

        Panel puzzle_panel_left = new Panel();
        puzzle_panel_left.setLayout(new GridLayout(10,1));
        Label ooo_label = new Label("ooo choices size");
        puzzle_panel_left.add(ooo_label);
        puzzle_panel_left.add(theory.front_end.puzzle_ooo_choices_size_text);
        Label nis_label = new Label("nis choices size");
        puzzle_panel_left.add(nis_label);
        puzzle_panel_left.add(theory.front_end.puzzle_nis_choices_size_text);
        Label analogy_label = new Label("analogy choices size");
        puzzle_panel_left.add(analogy_label);
        puzzle_panel_left.add(theory.front_end.puzzle_analogy_choices_size_text);
        puzzle_panel_left.add(theory.front_end.puzzle_generate_button);
        puzzle_panel.setLayout(new BorderLayout());
        puzzle_panel.add("West",puzzle_panel_left);
        puzzle_panel.add("Center",theory.front_end.puzzle_output_text);

        // Predict Panel //
        predict_panel.setLayout(new BorderLayout());
        Panel predict_panel_main = new Panel();
        predict_panel_main.setLayout(new GridLayout(1,2));
        Panel predict_panel1 = new Panel();
        Panel predict_panel2 = new Panel();
        predict_panel1.setLayout(new GridLayout(20,1));
        predict_panel2.setLayout(new GridLayout(20,1));
        predict_panel_main.add(predict_panel1);
        predict_panel_main.add(predict_panel2);
        Panel top_panel_for_predict = new Panel();
        predict_panel.add("North",top_panel_for_predict);
        predict_panel.add("Center",predict_panel_main);
        predict_panel.add("South",theory.front_end.predict_explanation_list);

        top_panel_for_predict.setLayout(new GridLayout(2,1));
        Panel top_panel_for_predict_a = new Panel();
        Panel top_panel_for_predict_b = new Panel();
        top_panel_for_predict.add(top_panel_for_predict_a);
        top_panel_for_predict.add(top_panel_for_predict_b);

        top_panel_for_predict_a.add(theory.front_end.predict_all_button);
        top_panel_for_predict_a.add(theory.front_end.predict_incremental_steps_button);
        top_panel_for_predict_a.add(theory.front_end.predict_input_files_choice);
        top_panel_for_predict_a.add(theory.front_end.predict_entity_type_choice);
        top_panel_for_predict_a.add(theory.front_end.predict_method_choice);
        top_panel_for_predict_b.add(new Label("predict for:"));
        top_panel_for_predict_b.add(theory.front_end.predict_entity_text);
        top_panel_for_predict_b.add(theory.front_end.predict_button);
        top_panel_for_predict_b.add(theory.front_end.predict_min_percent_text);
        top_panel_for_predict_b.add(new Label("%"));
        top_panel_for_predict_b.add(new Label("steps:"));
        top_panel_for_predict_b.add(theory.front_end.predict_max_steps_text);
        top_panel_for_predict_b.add(theory.front_end.predict_use_negations_check);
        top_panel_for_predict_b.add(theory.front_end.predict_use_equivalences_check);

        Panel dummy_predict_panel1 = new Panel();
        dummy_predict_panel1.setLayout(new BorderLayout());
        dummy_predict_panel1.add("West", theory.front_end.predict_names_button1);
        dummy_predict_panel1.add("Center", theory.front_end.predict_values_text1);
        predict_panel1.add(dummy_predict_panel1);

        Panel dummy_predict_panel2 = new Panel();
        dummy_predict_panel2.setLayout(new BorderLayout());
        dummy_predict_panel2.add("West", theory.front_end.predict_names_button2);
        dummy_predict_panel2.add("Center", theory.front_end.predict_values_text2);
        predict_panel1.add(dummy_predict_panel2);

        Panel dummy_predict_panel3 = new Panel();
        dummy_predict_panel3.setLayout(new BorderLayout());
        dummy_predict_panel3.add("West", theory.front_end.predict_names_button3);
        dummy_predict_panel3.add("Center", theory.front_end.predict_values_text3);
        predict_panel1.add(dummy_predict_panel3);

        Panel dummy_predict_panel4 = new Panel();
        dummy_predict_panel4.setLayout(new BorderLayout());
        dummy_predict_panel4.add("West", theory.front_end.predict_names_button4);
        dummy_predict_panel4.add("Center", theory.front_end.predict_values_text4);
        predict_panel1.add(dummy_predict_panel4);

        Panel dummy_predict_panel5 = new Panel();
        dummy_predict_panel5.setLayout(new BorderLayout());
        dummy_predict_panel5.add("West", theory.front_end.predict_names_button5);
        dummy_predict_panel5.add("Center", theory.front_end.predict_values_text5);
        predict_panel1.add(dummy_predict_panel5);

        Panel dummy_predict_panel6 = new Panel();
        dummy_predict_panel6.setLayout(new BorderLayout());
        dummy_predict_panel6.add("West", theory.front_end.predict_names_button6);
        dummy_predict_panel6.add("Center", theory.front_end.predict_values_text6);
        predict_panel1.add(dummy_predict_panel6);

        Panel dummy_predict_panel7 = new Panel();
        dummy_predict_panel7.setLayout(new BorderLayout());
        dummy_predict_panel7.add("West", theory.front_end.predict_names_button7);
        dummy_predict_panel7.add("Center", theory.front_end.predict_values_text7);
        predict_panel1.add(dummy_predict_panel7);

        Panel dummy_predict_panel8 = new Panel();
        dummy_predict_panel8.setLayout(new BorderLayout());
        dummy_predict_panel8.add("West", theory.front_end.predict_names_button8);
        dummy_predict_panel8.add("Center", theory.front_end.predict_values_text8);
        predict_panel1.add(dummy_predict_panel8);

        Panel dummy_predict_panel9 = new Panel();
        dummy_predict_panel9.setLayout(new BorderLayout());
        dummy_predict_panel9.add("West", theory.front_end.predict_names_button9);
        dummy_predict_panel9.add("Center", theory.front_end.predict_values_text9);
        predict_panel1.add(dummy_predict_panel9);

        Panel dummy_predict_panel10 = new Panel();
        dummy_predict_panel10.setLayout(new BorderLayout());
        dummy_predict_panel10.add("West", theory.front_end.predict_names_button10);
        dummy_predict_panel10.add("Center", theory.front_end.predict_values_text10);
        predict_panel1.add(dummy_predict_panel10);

        Panel dummy_predict_panel11 = new Panel();
        dummy_predict_panel11.setLayout(new BorderLayout());
        dummy_predict_panel11.add("West", theory.front_end.predict_names_button11);
        dummy_predict_panel11.add("Center", theory.front_end.predict_values_text11);
        predict_panel1.add(dummy_predict_panel11);

        Panel dummy_predict_panel12 = new Panel();
        dummy_predict_panel12.setLayout(new BorderLayout());
        dummy_predict_panel12.add("West", theory.front_end.predict_names_button12);
        dummy_predict_panel12.add("Center", theory.front_end.predict_values_text12);
        predict_panel1.add(dummy_predict_panel12);

        Panel dummy_predict_panel13 = new Panel();
        dummy_predict_panel13.setLayout(new BorderLayout());
        dummy_predict_panel13.add("West", theory.front_end.predict_names_button13);
        dummy_predict_panel13.add("Center", theory.front_end.predict_values_text13);
        predict_panel1.add(dummy_predict_panel13);

        Panel dummy_predict_panel14 = new Panel();
        dummy_predict_panel14.setLayout(new BorderLayout());
        dummy_predict_panel14.add("West", theory.front_end.predict_names_button14);
        dummy_predict_panel14.add("Center", theory.front_end.predict_values_text14);
        predict_panel1.add(dummy_predict_panel14);

        Panel dummy_predict_panel15 = new Panel();
        dummy_predict_panel15.setLayout(new BorderLayout());
        dummy_predict_panel15.add("West", theory.front_end.predict_names_button15);
        dummy_predict_panel15.add("Center", theory.front_end.predict_values_text15);
        predict_panel1.add(dummy_predict_panel15);

        Panel dummy_predict_panel16 = new Panel();
        dummy_predict_panel16.setLayout(new BorderLayout());
        dummy_predict_panel16.add("West", theory.front_end.predict_names_button16);
        dummy_predict_panel16.add("Center", theory.front_end.predict_values_text16);
        predict_panel1.add(dummy_predict_panel16);

        Panel dummy_predict_panel17 = new Panel();
        dummy_predict_panel17.setLayout(new BorderLayout());
        dummy_predict_panel17.add("West", theory.front_end.predict_names_button17);
        dummy_predict_panel17.add("Center", theory.front_end.predict_values_text17);
        predict_panel1.add(dummy_predict_panel17);

        Panel dummy_predict_panel18 = new Panel();
        dummy_predict_panel18.setLayout(new BorderLayout());
        dummy_predict_panel18.add("West", theory.front_end.predict_names_button18);
        dummy_predict_panel18.add("Center", theory.front_end.predict_values_text18);
        predict_panel1.add(dummy_predict_panel18);

        Panel dummy_predict_panel19 = new Panel();
        dummy_predict_panel19.setLayout(new BorderLayout());
        dummy_predict_panel19.add("West", theory.front_end.predict_names_button19);
        dummy_predict_panel19.add("Center", theory.front_end.predict_values_text19);
        predict_panel1.add(dummy_predict_panel19);

        Panel dummy_predict_panel20 = new Panel();
        dummy_predict_panel20.setLayout(new BorderLayout());
        dummy_predict_panel20.add("West", theory.front_end.predict_names_button20);
        dummy_predict_panel20.add("Center", theory.front_end.predict_values_text20);
        predict_panel1.add(dummy_predict_panel20);

        Panel dummy_predict_panel21 = new Panel();
        dummy_predict_panel21.setLayout(new BorderLayout());
        dummy_predict_panel21.add("West", theory.front_end.predict_names_button21);
        dummy_predict_panel21.add("Center", theory.front_end.predict_values_text21);
        predict_panel2.add(dummy_predict_panel21);

        Panel dummy_predict_panel22 = new Panel();
        dummy_predict_panel22.setLayout(new BorderLayout());
        dummy_predict_panel22.add("West", theory.front_end.predict_names_button22);
        dummy_predict_panel22.add("Center", theory.front_end.predict_values_text22);
        predict_panel2.add(dummy_predict_panel22);

        Panel dummy_predict_panel23 = new Panel();
        dummy_predict_panel23.setLayout(new BorderLayout());
        dummy_predict_panel23.add("West", theory.front_end.predict_names_button23);
        dummy_predict_panel23.add("Center", theory.front_end.predict_values_text23);
        predict_panel2.add(dummy_predict_panel23);

        Panel dummy_predict_panel24 = new Panel();
        dummy_predict_panel24.setLayout(new BorderLayout());
        dummy_predict_panel24.add("West", theory.front_end.predict_names_button24);
        dummy_predict_panel24.add("Center", theory.front_end.predict_values_text24);
        predict_panel2.add(dummy_predict_panel24);

        Panel dummy_predict_panel25 = new Panel();
        dummy_predict_panel25.setLayout(new BorderLayout());
        dummy_predict_panel25.add("West", theory.front_end.predict_names_button25);
        dummy_predict_panel25.add("Center", theory.front_end.predict_values_text25);
        predict_panel2.add(dummy_predict_panel25);

        Panel dummy_predict_panel26 = new Panel();
        dummy_predict_panel26.setLayout(new BorderLayout());
        dummy_predict_panel26.add("West", theory.front_end.predict_names_button26);
        dummy_predict_panel26.add("Center", theory.front_end.predict_values_text26);
        predict_panel2.add(dummy_predict_panel26);

        Panel dummy_predict_panel27 = new Panel();
        dummy_predict_panel27.setLayout(new BorderLayout());
        dummy_predict_panel27.add("West", theory.front_end.predict_names_button27);
        dummy_predict_panel27.add("Center", theory.front_end.predict_values_text27);
        predict_panel2.add(dummy_predict_panel27);

        Panel dummy_predict_panel28 = new Panel();
        dummy_predict_panel28.setLayout(new BorderLayout());
        dummy_predict_panel28.add("West", theory.front_end.predict_names_button28);
        dummy_predict_panel28.add("Center", theory.front_end.predict_values_text28);
        predict_panel2.add(dummy_predict_panel28);

        Panel dummy_predict_panel29 = new Panel();
        dummy_predict_panel29.setLayout(new BorderLayout());
        dummy_predict_panel29.add("West", theory.front_end.predict_names_button29);
        dummy_predict_panel29.add("Center", theory.front_end.predict_values_text29);
        predict_panel2.add(dummy_predict_panel29);

        Panel dummy_predict_panel30 = new Panel();
        dummy_predict_panel30.setLayout(new BorderLayout());
        dummy_predict_panel30.add("West", theory.front_end.predict_names_button30);
        dummy_predict_panel30.add("Center", theory.front_end.predict_values_text30);
        predict_panel2.add(dummy_predict_panel30);

        Panel dummy_predict_panel31 = new Panel();
        dummy_predict_panel31.setLayout(new BorderLayout());
        dummy_predict_panel31.add("West", theory.front_end.predict_names_button31);
        dummy_predict_panel31.add("Center", theory.front_end.predict_values_text31);
        predict_panel2.add(dummy_predict_panel31);

        Panel dummy_predict_panel32 = new Panel();
        dummy_predict_panel32.setLayout(new BorderLayout());
        dummy_predict_panel32.add("West", theory.front_end.predict_names_button32);
        dummy_predict_panel32.add("Center", theory.front_end.predict_values_text32);
        predict_panel2.add(dummy_predict_panel32);

        Panel dummy_predict_panel33 = new Panel();
        dummy_predict_panel33.setLayout(new BorderLayout());
        dummy_predict_panel33.add("West", theory.front_end.predict_names_button33);
        dummy_predict_panel33.add("Center", theory.front_end.predict_values_text33);
        predict_panel2.add(dummy_predict_panel33);

        Panel dummy_predict_panel34 = new Panel();
        dummy_predict_panel34.setLayout(new BorderLayout());
        dummy_predict_panel34.add("West", theory.front_end.predict_names_button34);
        dummy_predict_panel34.add("Center", theory.front_end.predict_values_text34);
        predict_panel2.add(dummy_predict_panel34);

        Panel dummy_predict_panel35 = new Panel();
        dummy_predict_panel35.setLayout(new BorderLayout());
        dummy_predict_panel35.add("West", theory.front_end.predict_names_button35);
        dummy_predict_panel35.add("Center", theory.front_end.predict_values_text35);
        predict_panel2.add(dummy_predict_panel35);

        Panel dummy_predict_panel36 = new Panel();
        dummy_predict_panel36.setLayout(new BorderLayout());
        dummy_predict_panel36.add("West", theory.front_end.predict_names_button36);
        dummy_predict_panel36.add("Center", theory.front_end.predict_values_text36);
        predict_panel2.add(dummy_predict_panel36);

        Panel dummy_predict_panel37 = new Panel();
        dummy_predict_panel37.setLayout(new BorderLayout());
        dummy_predict_panel37.add("West", theory.front_end.predict_names_button37);
        dummy_predict_panel37.add("Center", theory.front_end.predict_values_text37);
        predict_panel2.add(dummy_predict_panel37);

        Panel dummy_predict_panel38 = new Panel();
        dummy_predict_panel38.setLayout(new BorderLayout());
        dummy_predict_panel38.add("West", theory.front_end.predict_names_button38);
        dummy_predict_panel38.add("Center", theory.front_end.predict_values_text38);
        predict_panel2.add(dummy_predict_panel38);

        Panel dummy_predict_panel39 = new Panel();
        dummy_predict_panel39.setLayout(new BorderLayout());
        dummy_predict_panel39.add("West", theory.front_end.predict_names_button39);
        dummy_predict_panel39.add("Center", theory.front_end.predict_values_text39);
        predict_panel2.add(dummy_predict_panel39);

        Panel dummy_predict_panel40 = new Panel();
        dummy_predict_panel40.setLayout(new BorderLayout());
        dummy_predict_panel40.add("West", theory.front_end.predict_names_button40);
        dummy_predict_panel40.add("Center", theory.front_end.predict_values_text40);
        predict_panel2.add(dummy_predict_panel40);

        // Help Panel //
        help_panel.setLayout(new BorderLayout());
        Panel help_panela = new Panel();
        help_panela.setLayout(new BorderLayout());
        help_panela.add("Center",theory.front_end.help_text);
        Panel help_panelb = new Panel();
        help_panelb.add(theory.front_end.save_help_button);
        help_panelb.add(theory.front_end.help_save_file_text);
        help_panela.add("North", help_panelb);
        help_panel.add("West", theory.front_end.help_list);
        help_panel.add("Center", help_panela);

        // Main Panel //
        main_panel.setLayout(main_layout);
        main_layout.addLayoutComponent(macro_panel, "macro panel");
        main_panel.add("macro panel",macro_panel);
        main_panel.add("domain panel",domain_panel);
        main_panel.add("report panel",report_panel);
        main_panel.add("statistics panel",statistics_panel);
        main_panel.add("search panel",search_panel);
        main_panel.add("proof panel",proof_panel);
        main_panel.add("concepts panel",concepts_panel);
        main_panel.add("conjectures panel",conjectures_panel);
        main_panel.add("entities panel",entities_panel);
        main_panel.add("agenda panel",agenda_panel);
        main_panel.add("force panel",force_panel);
        main_panel.add("pseudo_code panel",pseudo_code_panel);
        main_panel.add("systemout panel",systemout_panel);
        main_panel.add("learn panel",learn_panel);
        main_panel.add("predict panel",predict_panel);
        main_panel.add("puzzle panel",puzzle_panel);
        main_panel.add("reactions panel",reactions_panel);
        main_panel.add("lakatos panel", lakatos_panel); //alisonp
        main_panel.add("methods panel", methods_panel);
        main_panel.add("read panel",read_panel);
        main_panel.add("help panel",help_panel);
        main_panel.add("interface panel", interface_panel);
        main_panel.add("store panel", store_panel);

        // Set colours //

        top_main_buttons_panel.setBackground(new Color(100,100,170));
        bottom_main_buttons_panel.setBackground(new Color(100,100,120));

        theory.front_end.play_macro_button.setBackground(Color.red);
        theory.front_end.play_macro_button.setForeground(Color.white);
        theory.front_end.restore_theory_button.setBackground(Color.red);
        theory.front_end.restore_theory_button.setForeground(Color.white);
        theory.front_end.split_values_list.setBackground(Color.lightGray);
        theory.front_end.split_values_label.setBackground(Color.lightGray);
        theory.front_end.split_values_list.setForeground(Color.black);
        theory.front_end.split_values_label.setForeground(Color.black);
        theory.front_end.initial_entity_list.setBackground(Color.lightGray);
        theory.front_end.initial_entity_label.setBackground(Color.lightGray);
        theory.front_end.domain_label.setBackground(Color.white);
        theory.front_end.domain_label.setForeground(Color.black);
        theory.front_end.domain_list.setBackground(Color.white);
        theory.front_end.domain_list.setForeground(Color.black);
        theory.front_end.counterexample_list.setBackground(Color.white);
        theory.front_end.counterexample_list.setForeground(Color.black);
        theory.front_end.counterexample_label.setBackground(Color.white);
        theory.front_end.counterexample_label.setForeground(Color.black);
        theory.front_end.initial_concepts_list.setBackground(Color.lightGray);
        theory.front_end.initial_concepts_list.setForeground(Color.black);
        theory.front_end.initial_concepts_label.setBackground(Color.lightGray);
        theory.front_end.initial_concepts_label.setForeground(Color.black);
        theory.front_end.highlight_list.setBackground(Color.white);
        theory.front_end.highlight_list.setForeground(Color.black);
        theory.front_end.highlight_label.setBackground(Color.white);
        theory.front_end.highlight_label.setForeground(Color.black);
        theory.front_end.agenda_limit_label.setBackground(Color.white);
        theory.front_end.agenda_limit_label.setForeground(Color.black);
        theory.front_end.agenda_limit_text.setBackground(Color.white);
        theory.front_end.agenda_limit_text.setForeground(Color.black);
        theory.front_end.complexity_label.setBackground(Color.white);
        theory.front_end.complexity_label.setForeground(Color.black);
        theory.front_end.complexity_text.setBackground(Color.white);
        theory.front_end.complexity_text.setForeground(Color.black);
        theory.front_end.tuple_product_limit_label.setBackground(Color.white);
        theory.front_end.tuple_product_limit_label.setForeground(Color.black);
        theory.front_end.tuple_product_limit_text.setBackground(Color.white);
        theory.front_end.tuple_product_limit_text.setForeground(Color.black);
        theory.front_end.arity_limit_button.setForeground(Color.black);
        theory.front_end.arity_limit_text.setBackground(Color.white);
        theory.front_end.arity_limit_text.setForeground(Color.black);

        start_over_button.setBackground(new Color(125,100,220));
        start_over_button.setForeground(Color.white);
        theory.front_end.start_button.setBackground(new Color(125,100,210));
        theory.front_end.start_button.setForeground(Color.white);
        theory.front_end.stop_button.setBackground(new Color(125,100,220));
        theory.front_end.stop_button.setForeground(Color.white);
        theory.front_end.step_button.setBackground(new Color(125,100,220));
        theory.front_end.step_button.setForeground(Color.white);
        theory.front_end.status_label.setBackground(new Color(120,100,220));
        theory.front_end.status_label.setForeground(Color.yellow);
        theory.front_end.save_macro_button.setBackground(new Color(120,100,220));
        theory.front_end.save_macro_button.setForeground(Color.black);

        Panel bottom_main_buttons_panel_top = new Panel(new BorderLayout());
        bottom_main_buttons_panel.setLayout(new GridLayout(2,1));
        Panel bottom_main_left_panel = new Panel();
        bottom_main_left_panel.setLayout(new GridLayout(1,4));
        bottom_main_left_panel.add(start_over_button);
        bottom_main_left_panel.add(theory.front_end.start_button);
        bottom_main_left_panel.add(theory.front_end.stop_button);
        bottom_main_left_panel.add(theory.front_end.step_button);
        theory.front_end.step_button.setLabel("         Step          ");
        bottom_main_buttons_panel_top.add("West",bottom_main_left_panel);
        bottom_main_buttons_panel_top.add("Center",theory.front_end.status_label);
        bottom_main_buttons_panel_top.add("East",start_over_button);

        Panel bottom_main_buttons_panel_bottom = new Panel(new GridLayout(2,10));

        screen_buttons = new ScreenButtons();
        screen_buttons.concept_screen_button.addActionListener(this);
        screen_buttons.conjecture_screen_button.addActionListener(this);
        screen_buttons.entities_screen_button.addActionListener(this);
        screen_buttons.macro_screen_button.addActionListener(this);
        screen_buttons.domain_screen_button.addActionListener(this);
        screen_buttons.search_screen_button.addActionListener(this);
        screen_buttons.report_screen_button.addActionListener(this);
        screen_buttons.force_screen_button.addActionListener(this);
        screen_buttons.agenda_screen_button.addActionListener(this);
        screen_buttons.statistics_screen_button.addActionListener(this);
        screen_buttons.proof_screen_button.addActionListener(this);
        screen_buttons.store_screen_button.addActionListener(this);
        screen_buttons.interface_screen_button.addActionListener(this);
        screen_buttons.react_screen_button.addActionListener(this);
        screen_buttons.lakatos_screen_button.addActionListener(this);
        screen_buttons.lakatos_methods_screen_button.addActionListener(this);
        screen_buttons.learn_screen_button.addActionListener(this);
        screen_buttons.predict_screen_button.addActionListener(this);
        screen_buttons.systemout_screen_button.addActionListener(this);
        screen_buttons.topics_screen_button.addActionListener(this);
        screen_buttons.about_screen_button.addActionListener(this);

        bottom_main_buttons_panel_bottom.add(screen_buttons.lakatos_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.lakatos_methods_screen_button);//added
        bottom_main_buttons_panel_bottom.add(screen_buttons.concept_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.conjecture_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.entities_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.macro_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.domain_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.search_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.report_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.force_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.agenda_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.statistics_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.proof_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.store_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.interface_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.react_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.learn_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.predict_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.systemout_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.topics_screen_button);
        bottom_main_buttons_panel_bottom.add(screen_buttons.about_screen_button);

        bottom_main_buttons_panel.add(bottom_main_buttons_panel_top);
        bottom_main_buttons_panel.add(bottom_main_buttons_panel_bottom);
        main_buttons_panel.add(top_main_buttons_panel);
        main_buttons_panel.add(bottom_main_buttons_panel);

        // Add Item Listeners //

        setup_choice.addItemListener(this);
        application_choice.addItemListener(this);
        view_choice.addItemListener(this);
        debug_choice.addItemListener(this);
        help_choice.addItemListener(this);

        // Add Action Listeners //

        theory.front_end.store_theory_button.addActionListener(this);
        theory.front_end.restore_theory_button.addActionListener(this);
        start_over_button.addActionListener(this);

        // Overall Panel //

        setLayout(new BorderLayout());
        add("North", menu_panel);
        add("Center", main_panel);
        add("South", main_buttons_panel);
        theory.front_end.main_frame=this;
        theory.predict.main_frame=this;
        theory.graph_handler.main_frame = this;
        main_layout.show(main_panel, "macro panel");
    }

    private void whiteButtons(Button b1, Button b2)
    {
        b1.setBackground(Color.white);
        b1.setForeground(Color.black);
        b2.setBackground(Color.white);
        b2.setForeground(Color.black);
    }

    private void grayButtons(Button b1, Button b2)
    {
        b1.setBackground(Color.lightGray);
        b1.setForeground(Color.black);
        b2.setBackground(Color.lightGray);
        b2.setForeground(Color.black);
    }

    private void invertColour(Button button)
    {
        button.setBackground(Color.white);
        button.setForeground(Color.blue);
    }

    public void actionPerformed(ActionEvent e)
    {
        // Changing the screen //

        if (e.getSource()==screen_buttons.concept_screen_button)
            changeScreen("CONCEPTS");
        if (e.getSource()==screen_buttons.conjecture_screen_button)
            changeScreen("CONJECTURES");
        if (e.getSource()==screen_buttons.entities_screen_button)
            changeScreen("ENTITIES");
        if (e.getSource()==screen_buttons.macro_screen_button)
            changeScreen("MACRO");
        if (e.getSource()==screen_buttons.domain_screen_button)
            changeScreen("DOMAIN");
        if (e.getSource()==screen_buttons.search_screen_button)
            changeScreen("SEARCH");
        if (e.getSource()==screen_buttons.proof_screen_button)
            changeScreen("PROOF");
        if (e.getSource()==screen_buttons.store_screen_button)
            changeScreen("STORE");
        if (e.getSource()==screen_buttons.interface_screen_button)
            changeScreen("INTERFACE");
        if (e.getSource()==screen_buttons.react_screen_button)
            changeScreen("REACT");
        if (e.getSource()==screen_buttons.lakatos_screen_button)
            changeScreen("LAKATOS");
        if (e.getSource()==screen_buttons.lakatos_methods_screen_button)
            changeScreen("METHODS");
        if (e.getSource()==screen_buttons.learn_screen_button)
            changeScreen("LEARN");
        if (e.getSource()==screen_buttons.predict_screen_button)
            changeScreen("PREDICT");
        if (e.getSource()==screen_buttons.systemout_screen_button)
            changeScreen("SYSTEMOUT");
        if (e.getSource()==screen_buttons.pseudocode_screen_button)
            changeScreen("PSEUDOCODE");
        if (e.getSource()==screen_buttons.topics_screen_button)
            changeScreen("TOPICS");
        if (e.getSource()==screen_buttons.about_screen_button)
            changeScreen("ABOUT");
        if (e.getSource()==screen_buttons.report_screen_button)
            changeScreen("REPORTS");
        if (e.getSource()==screen_buttons.force_screen_button)
            changeScreen("FORCE");
        if (e.getSource()==screen_buttons.agenda_screen_button)
            changeScreen("AGENDA");
        if (e.getSource()==screen_buttons.statistics_screen_button)
            changeScreen("STATISTICS");

        // Restoring the theory //

        if (e.getSource()==theory.front_end.restore_theory_button &&
                theory.front_end.stored_theories_list.getSelectedItem()!=null)
        {
            theory.front_end.restore_theory_button.setLabel("Wait");
            try
            {
                String theory_file = theory.input_files_directory + theory.front_end.stored_theories_list.getSelectedItem();
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(theory_file));
                HR new_hr = (HR)o.readObject();
                new_hr.main_layout.show(new_hr.main_panel, "concepts panel");
                new_hr.menu_label.setText("CONCEPTS");
                new_hr.theory.front_end.store_theory_button.setLabel("Store theory");
                new_hr.setVisible(true);
                new_hr.addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        theory.storage_handler.closeObjectStreams();
                        System.exit(0);
                    }
                });

                o.close();
                dispose();
            }
            catch(Exception ex){System.out.println(ex);}
        }

        // Storing the theory //

        if (e.getSource()==theory.front_end.store_theory_button &&
                !(theory.front_end.store_theory_name_text.getText().equals("")))
        {
            theory.front_end.store_theory_button.setLabel("Wait");
            try
            {
                String theory_file = theory.input_files_directory + theory.front_end.store_theory_name_text.getText();
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(theory_file));
                o.writeObject(this);
                o.flush();
                o.close();
                theory.front_end.getTheoryFiles();
            }
            catch(Exception ex)
            {System.out.println(ex);}
            theory.front_end.store_theory_button.setLabel("Store theory");
        }

        if (e.getSource()==start_over_button)
        {
            start_over_button.setLabel("Wait");
            HR hr = new HR();
            hr.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            });
            for (int i=0; i<hr.theory.front_end.predict_names_buttons_vector.size(); i++)
                ((Button)hr.theory.front_end.predict_names_buttons_vector.elementAt(i)).addActionListener(hr.theory);
            hr.config_handler = config_handler;
            hr.config_handler.initialiseHR(hr);
            hr.theory.passOnSettings();
            hr.theory.front_end.init();
            hr.setVisible(true);
            dispose();
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        String screen = ((Choice)e.getSource()).getSelectedItem();
        ((Choice)e.getSource()).select(0);
        changeScreen(screen);
    }

    public void changeScreen(String screen)
    {
        String panel_name = "";
        if (screen.equals("ABOUT"))
        {
            ImageViewer logo_frame =
                    new ImageViewer((String)this.config_handler.values_table.get("input files directory"), "about.png", "HR " + this.version_number);
        }
        if (screen.equals("DOMAIN"))
        {
            panel_name = "domain panel";
            theory.front_end.getInputFiles();
        }
        if (screen.equals("SEARCH")) panel_name = "search panel";
        if (screen.equals("PROOF")) panel_name = "proof panel";
        if (screen.equals("CONCEPTS")) panel_name = "concepts panel";
        if (screen.equals("CONJECTURES")) panel_name = "conjectures panel";
        if (screen.equals("ENTITIES")) panel_name = "entities panel";
        if (screen.equals("AGENDA")) panel_name = "agenda panel";
        if (screen.equals("FORCE")) panel_name = "force panel";
        if (screen.equals("PSEUDOCODE")) panel_name = "pseudo_code panel";
        if (screen.equals("SYSTEMOUT")) panel_name = "systemout panel";
        if (screen.equals("LEARN")) panel_name = "learn panel";
        if (screen.equals("REPORTS")) panel_name = "report panel";
        if (screen.equals("STATISTICS")) panel_name = "statistics panel";
        if (screen.equals("MACRO"))	panel_name = "macro panel";
        if (screen.equals("PUZZLE")) panel_name = "puzzle panel";
        if (screen.equals("AGENCY")) panel_name = "agency panel";
        if (screen.equals("REACT")) panel_name = "reactions panel";
        if (screen.equals("LAKATOS")) panel_name = "lakatos panel";
        if (screen.equals("METHODS")) panel_name = "methods panel";
        if (screen.equals("PREDICT")) panel_name = "predict panel";
        if (screen.equals("TOPICS")) panel_name = "help panel";
        if (screen.equals("INTERFACE")) panel_name = "interface panel";
        if (screen.equals("STORE")) panel_name = "store panel";

        main_layout.show(main_panel, panel_name);
        if (!panel_name.equals(""))
        {
            menu_label.setText(screen);
        }
    }

    public static void main(String args[])
    {
        HR temp_hr = new HR();
        temp_hr.constructHR(args);
    }

    public HR constructHR(String args[]) {
        if (args.length==0) {
            System.out.println("HR version " + (new HR()).version_number);
            System.out.println("Usage: ");
            System.out.println("HR configuration_file (loads the given config file)");
            System.out.println("HR configuration_file macro macro_file argument#1 argument#2 ... (runs the given macro)");
            System.out.println("HR configuration_file restore theory_file (restores the give theory)");
            System.exit(0);
        }

        if (args.length==3 && args[1].equals("restore")) {
            ConfigHandler config_handler = new ConfigHandler();
            boolean read_config = config_handler.readUserConfiguration(args[0]);
            if (!read_config) {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }
            String theory_file_name = (String)config_handler.values_table.get("input files directory") + "/" + args[2];
            System.out.println("Restoring theory: " + theory_file_name);
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(theory_file_name));
                HR new_hr = (HR)o.readObject();
                new_hr.main_layout.show(new_hr.main_panel, "concepts panel");
                new_hr.menu_label.setText("CONCEPTS");
                new_hr.theory.front_end.store_theory_button.setLabel("Store theory");

                new_hr.setVisible(true);
                new_hr.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

                o.close();
            }
            catch(Exception e)
            {System.out.println("Sorry, couldn't load the theory:"); System.out.println(e);}
            return null;
        }

        else {
            ConfigHandler config_handler = new ConfigHandler();
            boolean read_config = config_handler.readUserConfiguration(args[0]);
            if (!read_config) {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }

            HR hr = new HR();
            hr.config_handler = config_handler;
            hr.config_handler.initialiseHR(hr);
            hr.theory.passOnSettings();
            hr.theory.front_end.init();

            for (int i=0; i<hr.theory.front_end.predict_names_buttons_vector.size(); i++)
                ((Button)hr.theory.front_end.predict_names_buttons_vector.elementAt(i)).addActionListener(hr.theory);

            if (args.length==1) {
                hr.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                hr.setVisible(true);
            }

            if (args.length>=3 && (args[1].equals("macro") || args[1].equals("vmacro"))) {
                String macro_file_name = hr.theory.input_files_directory + args[2];
                for (int i=2; i<args.length; i++)
                    hr.theory.command_line_arguments.addElement(args[i]);
                hr.theory.use_front_end = false;
                if (args[1].equals("vmacro")) {
                    hr.setVisible(true);
                    hr.theory.use_front_end = true;
                }
                else
                    hr.setVisible(false);

                hr.theory.loadMacro(macro_file_name);
                hr.theory.playMacro();
            }
            return hr;
        }
    }


    public HR constructHR_old(String args[])//friday 23rd
    {
        System.out.println("into constructHR. args.length is " + args.length);
        for (int i=0; i<args.length; i++)
            System.out.println("args["+i+"] is " + args[i]);


        if (args.length==0)
        {
            System.out.println("HR version " + (new HR()).version_number);
            System.out.println("Usage: ");
            System.out.println("HR configuration_file (loads the given config file)");
            System.out.println("HR configuration_file macro macro_file argument#1 argument#2 ... (runs the given macro)");
            System.out.println("HR configuration_file restore theory_file (restores the give theory)");
            System.exit(0);
        }

        if (args.length==3 && args[1].equals("restore"))
        {
            ConfigHandler config_handler = new ConfigHandler();
            boolean read_config = config_handler.readUserConfiguration(args[0]);
            if (!read_config)
            {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }
            String theory_file_name = (String)config_handler.values_table.get("input files directory") + "/" + args[2];
            System.out.println("Restoring theory: " + theory_file_name);
            try
            {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(theory_file_name));
                HR new_hr = (HR)o.readObject();
                new_hr.main_layout.show(new_hr.main_panel, "concepts panel");
                new_hr.menu_label.setText("CONCEPTS");
                new_hr.theory.front_end.store_theory_button.setLabel("Store theory");

                new_hr.setVisible(true);
                new_hr.addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        System.exit(0);
                    }
                });

                o.close();
            }
            catch(Exception e){System.out.println("Sorry, couldn't load the theory:"); System.out.println(e);}
            return null;
        }

        else
        {
            ConfigHandler config_handler = new ConfigHandler();
            boolean read_config = config_handler.readUserConfiguration(args[0]);
            if (!read_config)
            {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }

            HR hr = new HR();
            hr.config_handler = config_handler;
            hr.config_handler.initialiseHR(hr);
            hr.theory.passOnSettings();
            hr.theory.front_end.init();

            for (int i=0; i<hr.theory.front_end.predict_names_buttons_vector.size(); i++)
                ((Button)hr.theory.front_end.predict_names_buttons_vector.elementAt(i)).addActionListener(hr.theory);

            if (args.length==1)
            {
                System.out.println("here");
                hr.addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                hr.setVisible(true);
            }

            if (args.length>=3 && args[1].equals("macro"))
            {
                String macro_file_name = hr.theory.input_files_directory + args[2];
                for (int i=2; i<args.length; i++)
                    hr.theory.command_line_arguments.addElement(args[i]);
                hr.theory.use_front_end = false;
                hr.setVisible(false);
                System.out.println("Playing macro: " + macro_file_name);
                hr.theory.loadMacro(macro_file_name);
                hr.theory.playMacro();
                System.out.println("done macro");
            }
            return hr;
        }
    }
}
