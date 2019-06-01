package com.github.dshea89.hrlplus;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class HR extends Frame implements ActionListener, ItemListener, Serializable {
    public String my_agent_name = "";
    public String version_number = "2.3.0";
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
    public Panel lakatos_panel = new Panel();
    public Panel methods_panel = new Panel();
    public Panel macro_centre_panel = new Panel();
    public Panel main_buttons_panel = new Panel();
    public Panel top_main_buttons_panel = new Panel();
    public Panel bottom_main_buttons_panel = new Panel();
    public Panel output_panel = new Panel();
    public Panel top_output_panel = new Panel();
    public Panel mid_output_panel = new Panel();
    public Panel bottom_output_panel = new Panel();
    public Panel macro_text_panel = new Panel();
    public Panel hello_text_panel = new Panel();
    public Theory theory = null;
    public ConfigHandler config_handler = new ConfigHandler();
    public HR hr = null;

    public HR() {
        this.theory = new Theory();
        this.setup_choice.addItem("SETUP");
        this.setup_choice.addItem("MACRO");
        this.setup_choice.addItem("DOMAIN");
        this.setup_choice.addItem("SEARCH");
        this.setup_choice.addItem("PROOF");
        this.setup_choice.addItem("STORE");
        this.setup_choice.addItem("INTERFACE");
        this.setup_choice.addItem("REACT");
        this.setup_choice.addItem("LAKATOS");
        this.setup_choice.addItem("METHODS");
        this.application_choice.addItem("APPLICATIONS");
        this.application_choice.addItem("LEARN");
        this.application_choice.addItem("PREDICT");
        this.application_choice.addItem("PUZZLE");
        this.view_choice.addItem("VIEW THEORY");
        this.view_choice.addItem("CONCEPTS");
        this.view_choice.addItem("CONJECTURES");
        this.view_choice.addItem("ENTITIES");
        this.view_choice.addItem("REPORTS");
        this.view_choice.addItem("STATISTICS");
        this.debug_choice.addItem("DEBUG");
        this.debug_choice.addItem("AGENDA");
        this.debug_choice.addItem("FORCE");
        this.debug_choice.addItem("SYSTEMOUT");
        this.debug_choice.addItem("PSEUDOCODE");
        this.help_choice.addItem("HELP");
        this.help_choice.addItem("TOPICS");
        this.help_choice.addItem("ABOUT");
        Panel var1 = new Panel();
        var1.setLayout(new GridLayout(1, 6));
        var1.add(this.menu_label);
        var1.add(this.setup_choice);
        var1.add(this.application_choice);
        var1.add(this.view_choice);
        var1.add(this.debug_choice);
        var1.add(this.help_choice);
        this.domain_panel.setLayout(new GridLayout(2, 3));
        Panel var2 = new Panel();
        Panel var3 = new Panel();
        new Panel();
        Panel var5 = new Panel();
        new Panel();
        Panel var7 = new Panel();
        Panel var8 = new Panel();
        Panel var9 = new Panel();
        this.domain_panel.add(var2);
        this.domain_panel.add(var3);
        this.domain_panel.add(var5);
        this.domain_panel.add(var7);
        this.domain_panel.add(var8);
        this.domain_panel.add(var9);
        var2.setLayout(new BorderLayout());
        var2.add("North", this.theory.front_end.domain_label);
        var2.add("Center", this.theory.front_end.domain_list);
        Panel var10 = new Panel();
        var10.setLayout(new GridLayout(1, 2));
        var10.add(this.theory.front_end.domain_none_button);
        var10.add(this.theory.front_end.domain_default_button);
        var2.add("South", var10);
        this.whiteButtons(this.theory.front_end.domain_none_button, this.theory.front_end.domain_default_button);
        var3.setLayout(new BorderLayout());
        var3.add("North", this.theory.front_end.initial_concepts_label);
        var3.add("Center", this.theory.front_end.initial_concepts_list);
        var10 = new Panel();
        var10.setLayout(new GridLayout(1, 3));
        var10.add(this.theory.front_end.initial_concepts_none_button);
        var10.add(this.theory.front_end.initial_concepts_all_button);
        var3.add("South", var10);
        this.grayButtons(this.theory.front_end.initial_concepts_none_button, this.theory.front_end.initial_concepts_all_button);
        var5.setLayout(new BorderLayout());
        var5.add("North", this.theory.front_end.highlight_label);
        var5.add("Center", this.theory.front_end.highlight_list);
        var10 = new Panel();
        var10.setLayout(new GridLayout(1, 3));
        var10.add(this.theory.front_end.highlight_none_button);
        var10.add(this.theory.front_end.highlight_all_button);
        var5.add("South", var10);
        this.whiteButtons(this.theory.front_end.highlight_none_button, this.theory.front_end.highlight_all_button);
        var7.setLayout(new BorderLayout());
        var7.add("North", this.theory.front_end.initial_entity_label);
        var7.add("Center", this.theory.front_end.initial_entity_list);
        var10 = new Panel();
        var10.setLayout(new GridLayout(1, 2));
        var10.add(this.theory.front_end.initial_entity_none_button);
        var10.add(this.theory.front_end.initial_entity_all_button);
        var7.add("South", var10);
        this.grayButtons(this.theory.front_end.initial_entity_none_button, this.theory.front_end.initial_entity_all_button);
        var8.setLayout(new BorderLayout());
        var8.add("North", this.theory.front_end.counterexample_label);
        var8.add("Center", this.theory.front_end.counterexample_list);
        var10 = new Panel();
        var10.setLayout(new GridLayout(1, 2));
        var10.add(this.theory.front_end.counterexample_none_button);
        var10.add(this.theory.front_end.counterexample_all_button);
        var8.add("South", var10);
        this.whiteButtons(this.theory.front_end.counterexample_none_button, this.theory.front_end.counterexample_all_button);
        var9.setLayout(new BorderLayout());
        var9.add("North", this.theory.front_end.split_values_label);
        var9.add("Center", this.theory.front_end.split_values_list);
        var10 = new Panel();
        var10.setLayout(new GridLayout(1, 2));
        var10.add(this.theory.front_end.split_values_none_button);
        var10.add(this.theory.front_end.split_values_all_button);
        var9.add("South", var10);
        this.grayButtons(this.theory.front_end.split_values_none_button, this.theory.front_end.split_values_all_button);
        this.proof_panel.setLayout(new GridLayout(4, 1));
        Panel var11 = new Panel();
        Panel var12 = new Panel();
        Panel var13 = new Panel();
        Panel var14 = new Panel();
        var11.setLayout(new BorderLayout());
        Panel var15 = new Panel();
        var15.setLayout(new GridLayout(5, 1));
        var15.add(this.theory.front_end.add_proof_tables_to_macro_button);
        var15.add(this.theory.front_end.use_ground_instances_in_proving_check);
        var15.add(this.theory.front_end.use_entity_letter_in_proving_check);
        JScrollPane var16 = new JScrollPane(this.theory.front_end.algebra_table);
        var11.add("North", new Label("Algebras"));
        var11.add("Center", var16);
        var11.add("West", var15);
        var12.setLayout(new BorderLayout());
        JScrollPane var17 = new JScrollPane(this.theory.front_end.axiom_table);
        var12.add("North", new Label("Axioms"));
        var12.add("Center", var17);
        var13.setLayout(new BorderLayout());
        JScrollPane var18 = new JScrollPane(this.theory.front_end.file_prover_table);
        var13.add("North", new Label("File Provers"));
        var13.add("Center", var18);
        var14.setLayout(new BorderLayout());
        JScrollPane var19 = new JScrollPane(this.theory.front_end.other_prover_table);
        var14.add("North", new Label("Other Provers"));
        var14.add("Center", var19);
        this.proof_panel.add(var11);
        this.proof_panel.add(var12);
        this.proof_panel.add(var13);
        this.proof_panel.add(var14);
        this.search_panel.setLayout(new GridLayout(1, 4));
        Panel var20 = new Panel();
        Panel var21 = new Panel();
        var21.setLayout(new BorderLayout());
        var20.setBackground(Color.white);
        var21.setBackground(Color.white);
        var21.add("North", var20);
        GridBagLayout var22 = new GridBagLayout();
        GridBagConstraints var23 = new GridBagConstraints();
        var20.setLayout(var22);
        var23.fill = 2;
        Label var24 = new Label("STRATEGY");
        var24.setForeground(Color.red);
        var24.setAlignment(1);
        var23.gridwidth = 0;
        var22.setConstraints(var24, var23);
        var20.add(var24);
        var23.weightx = 0.0D;
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.agenda_limit_label, var23);
        var20.add(this.theory.front_end.agenda_limit_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.agenda_limit_text, var23);
        var20.add(this.theory.front_end.agenda_limit_text);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.complexity_label, var23);
        var20.add(this.theory.front_end.complexity_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.complexity_text, var23);
        var20.add(this.theory.front_end.complexity_text);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.tuple_product_limit_label, var23);
        var20.add(this.theory.front_end.tuple_product_limit_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.tuple_product_limit_text, var23);
        var20.add(this.theory.front_end.tuple_product_limit_text);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.compose_time_limit_label, var23);
        var20.add(this.theory.front_end.compose_time_limit_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.compose_time_limit_text, var23);
        var20.add(this.theory.front_end.compose_time_limit_text);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.interestingness_zero_min_label, var23);
        var20.add(this.theory.front_end.interestingness_zero_min_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.interestingness_zero_min_text, var23);
        var20.add(this.theory.front_end.interestingness_zero_min_text);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.best_first_delay_label, var23);
        var20.add(this.theory.front_end.best_first_delay_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.best_first_delay_text, var23);
        var20.add(this.theory.front_end.best_first_delay_text);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.gc_label, var23);
        var20.add(this.theory.front_end.gc_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.gc_text, var23);
        var20.add(this.theory.front_end.gc_text);
        var22.setConstraints(this.theory.front_end.breadth_first_check, var23);
        var20.add(this.theory.front_end.breadth_first_check);
        var22.setConstraints(this.theory.front_end.depth_first_check, var23);
        var20.add(this.theory.front_end.depth_first_check);
        var22.setConstraints(this.theory.front_end.random_check, var23);
        var20.add(this.theory.front_end.random_check);
        var22.setConstraints(this.theory.front_end.weighted_sum_check, var23);
        var20.add(this.theory.front_end.weighted_sum_check);
        var22.setConstraints(this.theory.front_end.keep_best_check, var23);
        var20.add(this.theory.front_end.keep_best_check);
        var22.setConstraints(this.theory.front_end.keep_worst_check, var23);
        var20.add(this.theory.front_end.keep_worst_check);
        var22.setConstraints(this.theory.front_end.use_forward_lookahead_check, var23);
        var20.add(this.theory.front_end.use_forward_lookahead_check);
        var22.setConstraints(this.theory.front_end.substitute_definitions_check, var23);
        var20.add(this.theory.front_end.substitute_definitions_check);
        var22.setConstraints(this.theory.front_end.subobject_overlap_check, var23);
        var20.add(this.theory.front_end.subobject_overlap_check);
        var22.setConstraints(this.theory.front_end.expand_agenda_check, var23);
        var20.add(this.theory.front_end.expand_agenda_check);
        Panel var25 = new Panel();
        new Panel();
        Panel var27 = new Panel();
        var22.setConstraints(var25, var23);
        var20.add(var25);
        var25.setLayout(new GridLayout(1, 1));
        var27.add(this.theory.front_end.update_front_end_check);
        var27.add(this.theory.front_end.update_front_end_button);
        var25.add(var27);
        Label var28 = new Label("Required:");
        var22.setConstraints(var28, var23);
        var20.add(var28);
        var23.gridwidth = 1;
        var22.setConstraints(this.theory.front_end.required_text, var23);
        var20.add(this.theory.front_end.required_text);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.required_choice, var23);
        var20.add(this.theory.front_end.required_choice);
        Panel var29 = new Panel();
        var29.setLayout(new BorderLayout());
        Panel var30 = new Panel();
        var29.add("North", var30);
        var30.setBackground(Color.lightGray);
        var29.setBackground(Color.lightGray);
        GridBagLayout var31 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var30.setLayout(var31);
        Label var32 = new Label("CONCEPTS");
        var32.setAlignment(1);
        var32.setForeground(Color.blue);
        var23.fill = 2;
        var23.gridwidth = 0;
        var31.setConstraints(var32, var23);
        var30.add(var32);
        var23.weightx = 0.0D;
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_applicability_weight_label, var23);
        var30.add(this.theory.front_end.concept_applicability_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_applicability_weight_text, var23);
        var30.add(this.theory.front_end.concept_applicability_weight_text);
        var23.weightx = 0.0D;
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_coverage_weight_label, var23);
        var30.add(this.theory.front_end.concept_coverage_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_coverage_weight_text, var23);
        var30.add(this.theory.front_end.concept_coverage_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_comprehensibility_weight_label, var23);
        var30.add(this.theory.front_end.concept_comprehensibility_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_comprehensibility_weight_text, var23);
        var30.add(this.theory.front_end.concept_comprehensibility_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_children_weight_label, var23);
        var30.add(this.theory.front_end.concept_children_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_children_weight_text, var23);
        var30.add(this.theory.front_end.concept_children_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_cross_domain_weight_label, var23);
        var30.add(this.theory.front_end.concept_cross_domain_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_cross_domain_weight_text, var23);
        var30.add(this.theory.front_end.concept_cross_domain_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_development_steps_num_weight_label, var23);
        var30.add(this.theory.front_end.concept_development_steps_num_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_development_steps_num_weight_text, var23);
        var30.add(this.theory.front_end.concept_development_steps_num_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_discrimination_weight_label, var23);
        var30.add(this.theory.front_end.concept_discrimination_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_discrimination_weight_text, var23);
        var30.add(this.theory.front_end.concept_discrimination_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_equiv_conj_score_weight_label, var23);
        var30.add(this.theory.front_end.concept_equiv_conj_score_weight_label);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_equiv_conj_score_weight_text, var23);
        var30.add(this.theory.front_end.concept_equiv_conj_score_weight_text);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_equiv_conj_num_weight_text, var23);
        var30.add(this.theory.front_end.concept_equiv_conj_num_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_highlight_weight_label, var23);
        var30.add(this.theory.front_end.concept_highlight_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_highlight_weight_text, var23);
        var30.add(this.theory.front_end.concept_highlight_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_imp_conj_score_weight_label, var23);
        var30.add(this.theory.front_end.concept_imp_conj_score_weight_label);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_imp_conj_score_weight_text, var23);
        var30.add(this.theory.front_end.concept_imp_conj_score_weight_text);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_imp_conj_num_weight_text, var23);
        var30.add(this.theory.front_end.concept_imp_conj_num_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_invariance_weight_label, var23);
        var30.add(this.theory.front_end.concept_invariance_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_invariance_weight_text, var23);
        var30.add(this.theory.front_end.concept_invariance_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_negative_applicability_weight_label, var23);
        var30.add(this.theory.front_end.concept_negative_applicability_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_negative_applicability_weight_text, var23);
        var30.add(this.theory.front_end.concept_negative_applicability_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_ne_conj_score_weight_label, var23);
        var30.add(this.theory.front_end.concept_ne_conj_score_weight_label);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_ne_conj_score_weight_text, var23);
        var30.add(this.theory.front_end.concept_ne_conj_score_weight_text);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_ne_conj_num_weight_text, var23);
        var30.add(this.theory.front_end.concept_ne_conj_num_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_novelty_weight_label, var23);
        var30.add(this.theory.front_end.concept_novelty_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_novelty_weight_text, var23);
        var30.add(this.theory.front_end.concept_novelty_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_parent_weight_label, var23);
        var30.add(this.theory.front_end.concept_parent_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_parent_weight_text, var23);
        var30.add(this.theory.front_end.concept_parent_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_parsimony_weight_label, var23);
        var30.add(this.theory.front_end.concept_parsimony_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_parsimony_weight_text, var23);
        var30.add(this.theory.front_end.concept_parsimony_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_pi_conj_score_weight_label, var23);
        var30.add(this.theory.front_end.concept_pi_conj_score_weight_label);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_pi_conj_score_weight_text, var23);
        var30.add(this.theory.front_end.concept_pi_conj_score_weight_text);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_pi_conj_num_weight_text, var23);
        var30.add(this.theory.front_end.concept_pi_conj_num_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_positive_applicability_weight_label, var23);
        var30.add(this.theory.front_end.concept_positive_applicability_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_positive_applicability_weight_text, var23);
        var30.add(this.theory.front_end.concept_positive_applicability_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_predictive_power_weight_label, var23);
        var30.add(this.theory.front_end.concept_predictive_power_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_predictive_power_weight_text, var23);
        var30.add(this.theory.front_end.concept_predictive_power_weight_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_productivity_weight_label, var23);
        var30.add(this.theory.front_end.concept_productivity_weight_label);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_productivity_weight_text, var23);
        var30.add(this.theory.front_end.concept_productivity_weight_text);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_default_productivity_text, var23);
        var30.add(this.theory.front_end.concept_default_productivity_text);
        var23.gridwidth = 1;
        var31.setConstraints(this.theory.front_end.concept_variety_weight_label, var23);
        var30.add(this.theory.front_end.concept_variety_weight_label);
        var23.gridwidth = 0;
        var31.setConstraints(this.theory.front_end.concept_variety_weight_text, var23);
        var30.add(this.theory.front_end.concept_variety_weight_text);
        var31.setConstraints(this.theory.front_end.normalise_concept_measures_check, var23);
        var30.add(this.theory.front_end.normalise_concept_measures_check);
        var31.setConstraints(this.theory.front_end.reset_concept_weights_button, var23);
        var30.add(this.theory.front_end.reset_concept_weights_button);
        Panel var33 = new Panel();
        var33.add(this.theory.front_end.measure_concepts_check);
        var33.add(this.theory.front_end.measure_concepts_button);
        var31.setConstraints(var33, var23);
        var30.add(var33);
        Panel var34 = new Panel();
        var34.setLayout(new BorderLayout());
        Panel var35 = new Panel();
        var34.add("North", var35);
        var35.setBackground(Color.white);
        var34.setBackground(Color.white);
        GridBagLayout var36 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var35.setLayout(var36);
        Label var37 = new Label("CONJECTURES");
        var37.setAlignment(1);
        var37.setForeground(Color.red);
        var23.fill = 2;
        var23.gridwidth = 0;
        var36.setConstraints(var37, var23);
        var35.add(var37);
        var23.weightx = 0.0D;
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.conjecture_applicability_weight_label, var23);
        var35.add(this.theory.front_end.conjecture_applicability_weight_label);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.conjecture_applicability_weight_text, var23);
        var35.add(this.theory.front_end.conjecture_applicability_weight_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.conjecture_comprehensibility_weight_label, var23);
        var35.add(this.theory.front_end.conjecture_comprehensibility_weight_label);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.conjecture_comprehensibility_weight_text, var23);
        var35.add(this.theory.front_end.conjecture_comprehensibility_weight_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.conjecture_surprisingness_weight_label, var23);
        var35.add(this.theory.front_end.conjecture_surprisingness_weight_label);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.conjecture_surprisingness_weight_text, var23);
        var35.add(this.theory.front_end.conjecture_surprisingness_weight_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.conjecture_plausibility_weight_label, var23);
        var35.add(this.theory.front_end.conjecture_plausibility_weight_label);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.conjecture_plausibility_weight_text, var23);
        var35.add(this.theory.front_end.conjecture_plausibility_weight_text);
        var36.setConstraints(this.theory.front_end.reset_conjecture_weights_button, var23);
        var35.add(this.theory.front_end.reset_conjecture_weights_button);
        Panel var38 = new Panel();
        var36.setConstraints(var38, var23);
        var38.add(this.theory.front_end.measure_conjectures_check);
        var38.add(this.theory.front_end.measure_conjectures_button);
        var35.add(var38);
        Label var39 = new Label("Methods");
        var39.setForeground(Color.red);
        var39.setAlignment(1);
        var23.gridwidth = 0;
        var36.setConstraints(var39, var23);
        var35.add(var39);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.make_equivalences_from_equality_check, var23);
        var35.add(this.theory.front_end.make_equivalences_from_equality_check);
        var36.setConstraints(this.theory.front_end.make_equivalences_from_combination_check, var23);
        var35.add(this.theory.front_end.make_equivalences_from_combination_check);
        var36.setConstraints(this.theory.front_end.make_non_exists_from_empty_check, var23);
        var35.add(this.theory.front_end.make_non_exists_from_empty_check);
        var36.setConstraints(this.theory.front_end.extract_implications_from_equivalences_check, var23);
        var35.add(this.theory.front_end.extract_implications_from_equivalences_check);
        var36.setConstraints(this.theory.front_end.make_implications_from_subsumptions_check, var23);
        var35.add(this.theory.front_end.make_implications_from_subsumptions_check);
        var36.setConstraints(this.theory.front_end.make_implicates_from_subsumes_check, var23);
        var35.add(this.theory.front_end.make_implicates_from_subsumes_check);
        var36.setConstraints(this.theory.front_end.extract_implicates_from_non_exists_check, var23);
        var35.add(this.theory.front_end.extract_implicates_from_non_exists_check);
        var36.setConstraints(this.theory.front_end.extract_implicates_from_equivalences_check, var23);
        var35.add(this.theory.front_end.extract_implicates_from_equivalences_check);
        var36.setConstraints(this.theory.front_end.extract_prime_implicates_from_implicates_check, var23);
        var35.add(this.theory.front_end.extract_prime_implicates_from_implicates_check);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.make_near_equivalences_check, var23);
        var35.add(this.theory.front_end.make_near_equivalences_check);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.near_equivalence_percent_text, var23);
        var35.add(this.theory.front_end.near_equivalence_percent_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.make_near_implications_check, var23);
        var35.add(this.theory.front_end.make_near_implications_check);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.near_implication_percent_text, var23);
        var35.add(this.theory.front_end.near_implication_percent_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.use_counterexample_barring_check, var23);
        var35.add(this.theory.front_end.use_counterexample_barring_check);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.counterexample_barring_num_text, var23);
        var35.add(this.theory.front_end.counterexample_barring_num_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.use_concept_barring_check, var23);
        var35.add(this.theory.front_end.use_concept_barring_check);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.concept_barring_num_text, var23);
        var35.add(this.theory.front_end.concept_barring_num_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.use_applicability_conj_check, var23);
        var35.add(this.theory.front_end.use_applicability_conj_check);
        var23.gridwidth = 0;
        var36.setConstraints(this.theory.front_end.applicability_conj_num_text, var23);
        var35.add(this.theory.front_end.applicability_conj_num_text);
        var23.gridwidth = 1;
        var36.setConstraints(this.theory.front_end.require_proof_in_subsumption_check, var23);
        var35.add(this.theory.front_end.require_proof_in_subsumption_check);
        Panel var40 = new Panel();
        var40.setLayout(new BorderLayout());
        Panel var41 = new Panel();
        var40.add("North", var41);
        var41.setBackground(Color.lightGray);
        var40.setBackground(Color.lightGray);
        GridBagLayout var42 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var41.setLayout(var42);
        Label var43 = new Label("PRODUCTION RULES");
        var43.setAlignment(1);
        var43.setForeground(Color.blue);
        var23.fill = 2;
        var23.gridwidth = 0;
        var42.setConstraints(var43, var23);
        var41.add(var43);
        Label var44 = new Label("");
        Label var45 = new Label("arity");
        Label var46 = new Label("level");
        var23.gridwidth = 1;
        var42.setConstraints(var44, var23);
        var41.add(var44);
        var42.setConstraints(var45, var23);
        var41.add(var45);
        var23.gridwidth = 0;
        var42.setConstraints(var46, var23);
        var41.add(var46);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.arithmeticb_check, var23);
        var41.add(this.theory.front_end.arithmeticb_check);
        var42.setConstraints(this.theory.front_end.arithmeticb_arity_limit_text, var23);
        var41.add(this.theory.front_end.arithmeticb_arity_limit_text);
        var42.setConstraints(this.theory.front_end.arithmeticb_tier_text, var23);
        var41.add(this.theory.front_end.arithmeticb_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.compose_check, var23);
        var41.add(this.theory.front_end.compose_check);
        var42.setConstraints(this.theory.front_end.compose_arity_limit_text, var23);
        var41.add(this.theory.front_end.compose_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.compose_tier_text, var23);
        var41.add(this.theory.front_end.compose_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.disjunct_check, var23);
        var41.add(this.theory.front_end.disjunct_check);
        var42.setConstraints(this.theory.front_end.disjunct_arity_limit_text, var23);
        var41.add(this.theory.front_end.disjunct_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.disjunct_tier_text, var23);
        var41.add(this.theory.front_end.disjunct_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.embed_algebra_check, var23);
        var41.add(this.theory.front_end.embed_algebra_check);
        var42.setConstraints(this.theory.front_end.embed_algebra_arity_limit_text, var23);
        var41.add(this.theory.front_end.embed_algebra_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.embed_algebra_tier_text, var23);
        var41.add(this.theory.front_end.embed_algebra_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.embed_graph_check, var23);
        var41.add(this.theory.front_end.embed_graph_check);
        var42.setConstraints(this.theory.front_end.embed_graph_arity_limit_text, var23);
        var41.add(this.theory.front_end.embed_graph_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.embed_graph_tier_text, var23);
        var41.add(this.theory.front_end.embed_graph_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.equal_check, var23);
        var41.add(this.theory.front_end.equal_check);
        var42.setConstraints(this.theory.front_end.equal_arity_limit_text, var23);
        var41.add(this.theory.front_end.equal_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.equal_tier_text, var23);
        var41.add(this.theory.front_end.equal_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.entity_disjunct_check, var23);
        var41.add(this.theory.front_end.entity_disjunct_check);
        var42.setConstraints(this.theory.front_end.entity_disjunct_arity_limit_text, var23);
        var41.add(this.theory.front_end.entity_disjunct_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.entity_disjunct_tier_text, var23);
        var41.add(this.theory.front_end.entity_disjunct_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.exists_check, var23);
        var41.add(this.theory.front_end.exists_check);
        var42.setConstraints(this.theory.front_end.exists_arity_limit_text, var23);
        var41.add(this.theory.front_end.exists_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.exists_tier_text, var23);
        var41.add(this.theory.front_end.exists_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.forall_check, var23);
        var41.add(this.theory.front_end.forall_check);
        var42.setConstraints(this.theory.front_end.forall_arity_limit_text, var23);
        var41.add(this.theory.front_end.forall_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.forall_tier_text, var23);
        var41.add(this.theory.front_end.forall_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.match_check, var23);
        var41.add(this.theory.front_end.match_check);
        var42.setConstraints(this.theory.front_end.match_arity_limit_text, var23);
        var41.add(this.theory.front_end.match_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.match_tier_text, var23);
        var41.add(this.theory.front_end.match_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.negate_check, var23);
        var41.add(this.theory.front_end.negate_check);
        var42.setConstraints(this.theory.front_end.negate_arity_limit_text, var23);
        var41.add(this.theory.front_end.negate_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.negate_tier_text, var23);
        var41.add(this.theory.front_end.negate_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.record_check, var23);
        var41.add(this.theory.front_end.record_check);
        var42.setConstraints(this.theory.front_end.record_arity_limit_text, var23);
        var41.add(this.theory.front_end.record_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.record_tier_text, var23);
        var41.add(this.theory.front_end.record_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.size_check, var23);
        var41.add(this.theory.front_end.size_check);
        var42.setConstraints(this.theory.front_end.size_arity_limit_text, var23);
        var41.add(this.theory.front_end.size_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.size_tier_text, var23);
        var41.add(this.theory.front_end.size_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.split_check, var23);
        var41.add(this.theory.front_end.split_check);
        var42.setConstraints(this.theory.front_end.split_arity_limit_text, var23);
        var41.add(this.theory.front_end.split_arity_limit_text);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.split_tier_text, var23);
        var41.add(this.theory.front_end.split_tier_text);
        var23.gridwidth = 1;
        var42.setConstraints(this.theory.front_end.arithmeticb_operators_label, var23);
        var41.add(this.theory.front_end.arithmeticb_operators_label);
        var23.gridwidth = 0;
        var42.setConstraints(this.theory.front_end.arithmeticb_operators_text, var23);
        var41.add(this.theory.front_end.arithmeticb_operators_text);
        this.search_panel.add(var21);
        this.search_panel.add(var29);
        this.search_panel.add(var34);
        this.search_panel.add(var40);
        this.main_buttons_panel.setLayout(new GridLayout(2, 1));
        this.top_main_buttons_panel.add(this.theory.front_end.statistics_label1);
        this.top_main_buttons_panel.add(this.theory.front_end.statistics_choice1);
        this.top_main_buttons_panel.add(this.theory.front_end.statistics_label2);
        this.top_main_buttons_panel.add(this.theory.front_end.statistics_choice2);
        this.top_main_buttons_panel.add(this.theory.front_end.statistics_label3);
        this.top_main_buttons_panel.add(this.theory.front_end.statistics_choice3);
        Panel var47 = new Panel();
        var47.add(this.theory.front_end.quick_macro_choice);
        var47.add(this.theory.front_end.save_macro_button);
        var47.add(this.theory.front_end.record_macro_check);
        var47.add(this.theory.front_end.save_macro_text);
        this.top_main_buttons_panel.add(var47);
        this.theory.front_end.stop_button.setEnabled(false);
        this.theory.front_end.step_button.setEnabled(false);
        this.theory.front_end.start_button.setEnabled(true);
        this.statistics_panel.setLayout(new BorderLayout());
        Panel var48 = new Panel();
        Panel var49 = new Panel();
        var48.setLayout(new GridLayout(1, 4));
        var49.setLayout(new BorderLayout());
        var48.add(var49);
        var48.add(this.theory.front_end.statistics_choice_list);
        var48.add(this.theory.front_end.statistics_subchoice_list);
        var48.add(this.theory.front_end.statistics_methods_list);
        Panel var50 = new Panel();
        var50.setLayout(new GridLayout(9, 2));
        var50.add(this.theory.front_end.statistics_plot_button);
        var50.add(this.theory.front_end.statistics_compile_button);
        var50.add(this.theory.front_end.statistics_average_check);
        var50.add(this.theory.front_end.statistics_seperate_graphs_check);
        var50.add(this.theory.front_end.statistics_count_check);
        var50.add(new Label(""));
        var50.add(new Label("Calculation: "));
        var50.add(this.theory.front_end.statistics_calculation_text);
        var50.add(new Label("Prune: "));
        var50.add(this.theory.front_end.statistics_prune_text);
        var50.add(new Label("Sort: "));
        var50.add(this.theory.front_end.statistics_sort_text);
        var50.add(new Label("File: "));
        var50.add(this.theory.front_end.statistics_output_file_text);
        var48.add(var50);
        this.statistics_panel.add("North", var48);
        this.statistics_panel.add("Center", this.theory.statistics_handler.statistics_output_panel);
        var49.add("Center", this.theory.front_end.statistics_type_list);
        var49.add("South", var50);
        this.report_panel.setLayout(new BorderLayout());
        Panel var51 = new Panel();
        var51.setLayout(new BorderLayout());
        Panel var52 = new Panel();
        Panel var53 = new Panel();
        var52.setLayout(new GridLayout(3, 1));
        var52.add(this.theory.front_end.execute_report_button);
        var52.add(this.theory.front_end.save_report_button);
        var52.add(this.theory.front_end.save_report_name_text);
        var53.setLayout(new BorderLayout());
        var53.add("West", var52);
        var53.add("Center", this.theory.front_end.reports_list);
        var51.add("West", this.theory.front_end.reports_to_execute_list);
        var51.add("Center", this.theory.front_end.report_instructions_text);
        var51.add("East", var53);
        this.report_panel.add("North", var51);
        JScrollPane var54 = new JScrollPane(this.theory.front_end.report_output_text);
        this.report_panel.add("Center", var54);
        this.agenda_panel.setLayout(new BorderLayout());
        Panel var55 = new Panel();
        var55.add(this.theory.front_end.update_agenda_button);
        var55.add(this.theory.front_end.show_forced_steps_button);
        var55.add(this.theory.front_end.auto_update_agenda);
        var55.add(this.theory.front_end.auto_update_text);
        var55.add(this.theory.front_end.not_allowed_agenda_check);
        var55.add(this.theory.front_end.current_step_text);
        this.agenda_panel.add("North", var55);
        Panel var56 = new Panel();
        Panel var57 = new Panel();
        var57.setLayout(new GridLayout(1, 2));
        var56.setLayout(new GridLayout(1, 2));
        var56.add(this.theory.front_end.agenda_list);
        var56.add(var57);
        var57.add(this.theory.front_end.live_ordered_concept_list);
        var57.add(this.theory.front_end.all_ordered_concept_list);
        this.agenda_panel.add("Center", var56);
        this.agenda_panel.add("South", this.theory.front_end.agenda_concept_text);
        this.systemout_panel.setLayout(new BorderLayout());
        Panel var58 = new Panel();
        var58.add(this.theory.front_end.systemout_button);
        var58.add(this.theory.front_end.debug_parameter_text);
        this.systemout_panel.add("North", var58);
        this.systemout_panel.add("Center", this.theory.front_end.systemout_text);
        this.pseudo_code_panel.setLayout(new BorderLayout());
        Panel var59 = new Panel();
        var59.add(this.theory.front_end.pseudo_code_run_button);
        Panel var60 = new Panel();
        var60.setLayout(new GridLayout(1, 2));
        var60.add(this.theory.front_end.pseudo_code_input_text);
        var60.add(this.theory.front_end.pseudo_code_output_text);
        this.pseudo_code_panel.add("North", var59);
        this.pseudo_code_panel.add("Center", var60);
        this.force_panel.setLayout(new GridLayout(2, 1));
        Panel var61 = new Panel();
        new Panel();
        var61.setLayout(new GridLayout(1, 4));
        var61.add(this.theory.front_end.force_primary_concept_list);
        var61.add(this.theory.front_end.force_secondary_concept_list);
        var61.add(this.theory.front_end.force_prodrule_list);
        Panel var63 = new Panel();
        var63.setLayout(new BorderLayout());
        var63.add("North", this.theory.front_end.force_button);
        var63.add("Center", this.theory.front_end.force_result_text);
        var61.add(var63);
        Panel var62 = new Panel();
        var62.setLayout(new BorderLayout());
        this.theory.front_end.force_string_text.setBackground(Color.orange);
        this.theory.front_end.force_string_text.setForeground(Color.blue);
        var62.add("North", this.theory.front_end.force_string_text);
        var62.add("Center", this.theory.front_end.force_parameter_list);
        this.force_panel.add(var61);
        this.force_panel.add(var62);
        this.concepts_panel.setLayout(new BorderLayout());
        Panel var64 = new Panel();
        Panel var65 = new Panel();
        Panel var66 = new Panel();
        var65.setLayout(new GridLayout(3, 1));
        var64.setLayout(new BorderLayout());
        var65.add(this.theory.front_end.concept_formatting_list);
        var65.add(this.theory.front_end.concept_pruning_list);
        var65.add(this.theory.front_end.concept_sorting_list);
        var64.add("North", var65);
        var64.add("Center", this.theory.front_end.concept_list);
        var64.add("South", this.theory.front_end.concept_list_number);
        this.concepts_panel.add("West", var64);
        Panel var67 = new Panel();
        Panel var68 = new Panel();
        Panel var69 = new Panel();
        Panel var70 = new Panel();
        var67.setLayout(new BorderLayout());
        var67.add("North", this.theory.front_end.ancestor_list);
        JScrollPane var71 = new JScrollPane(this.theory.front_end.concept_text_box);
        var67.add("Center", var71);
        var67.add("South", var66);
        var66.setLayout(new GridLayout(2, 1));
        var66.add(this.theory.front_end.children_list);
        var66.add(var68);
        var68.setLayout(new GridLayout(3, 1));
        var68.add(var69);
        var69.setLayout(new GridLayout(1, 6));
        var69.add(this.theory.front_end.calculate_entity1_text);
        var69.add(this.theory.front_end.calculate_entity2_text);
        var69.add(this.theory.front_end.calculate_button);
        var69.add(this.theory.front_end.draw_concept_construction_history_button);
        var69.add(this.theory.front_end.find_extra_conjectures_button);
        var68.add(this.theory.front_end.calculate_output_text);
        var68.add(var70);
        var70.setLayout(new BorderLayout());
        Panel var72 = new Panel();
        var72.setLayout(new GridLayout(1, 2));
        var72.add(this.theory.front_end.grep_concept_text);
        var72.add(this.theory.front_end.condition_concept_text);
        var70.add("Center", var72);
        var70.add("West", this.theory.front_end.grep_concept_button);
        var70.add("East", this.theory.front_end.condition_concept_button);
        this.theory.front_end.grep_concept_text.setBackground(Color.green);
        this.theory.front_end.condition_concept_text.setBackground(Color.yellow);
        this.concepts_panel.add("Center", var67);
        this.conjectures_panel.setLayout(new BorderLayout());
        var64 = new Panel();
        var65 = new Panel();
        new Panel();
        var65.setLayout(new GridLayout(3, 1));
        var64.setLayout(new BorderLayout());
        var65.add(this.theory.front_end.conjecture_formatting_list);
        var65.add(this.theory.front_end.conjecture_pruning_list);
        var65.add(this.theory.front_end.conjecture_sorting_list);
        var64.add("North", var65);
        var64.add("Center", this.theory.front_end.conjecture_list);
        var64.add("South", this.theory.front_end.conjecture_list_number);
        this.conjectures_panel.add("West", var64);
        Panel var73 = new Panel();
        var73.setLayout(new BorderLayout());
        JScrollPane var74 = new JScrollPane(this.theory.front_end.conjecture_text_box);
        var73.add("Center", var74);
        Panel var75 = new Panel();
        var75.setLayout(new BorderLayout());
        Panel var76 = new Panel();
        var76.add(this.theory.front_end.add_conjecture_as_axiom_button);
        var76.add(this.theory.front_end.remove_conjecture_as_axiom_button);
        var76.add(this.theory.front_end.re_prove_all_button);
        var76.add(this.theory.front_end.draw_conjecture_construction_history_button);
        var76.add(this.theory.front_end.test_conjecture_entity1_text);
        var76.add(this.theory.front_end.test_conjecture_entity2_text);
        var76.add(this.theory.front_end.test_conjecture_button);
        var76.add(this.theory.front_end.test_conjecture_output_text);
        var76.add(this.theory.front_end.add_counterexamples_found_from_testing_button);
        var75.add("North", var76);
        var75.add("Center", this.theory.front_end.child_conjectures_list);
        Panel var77 = new Panel();
        var77.setLayout(new BorderLayout());
        var77.add("West", this.theory.front_end.grep_conjecture_button);
        var77.add("East", this.theory.front_end.condition_conjecture_button);
        Panel var78 = new Panel();
        var78.setLayout(new GridLayout(1, 2));
        var78.add(this.theory.front_end.grep_conjecture_text);
        var78.add(this.theory.front_end.condition_conjecture_text);
        var77.add("Center", var78);
        var75.add("South", var77);
        this.theory.front_end.grep_conjecture_text.setBackground(Color.green);
        this.theory.front_end.condition_conjecture_text.setBackground(Color.yellow);
        var73.add("North", this.theory.front_end.parent_conjectures_list);
        var73.add("South", var75);
        this.conjectures_panel.add("Center", var73);
        this.entities_panel.setLayout(new BorderLayout());
        var64 = new Panel();
        var65 = new Panel();
        new Panel();
        var65.setLayout(new GridLayout(3, 1));
        var64.setLayout(new BorderLayout());
        var65.add(this.theory.front_end.entity_formatting_list);
        var65.add(this.theory.front_end.entity_pruning_list);
        var65.add(this.theory.front_end.entity_sorting_list);
        var64.add("North", var65);
        var64.add("Center", this.theory.front_end.entity_list);
        this.entities_panel.add("West", var64);
        Panel var79 = new Panel();
        Panel var80 = new Panel();
        Panel var81 = new Panel();
        var79.setLayout(new BorderLayout());
        var80.setLayout(new BorderLayout());
        JScrollPane var82 = new JScrollPane(this.theory.front_end.entity_text_box);
        var79.add("Center", var82);
        var79.add("South", var80);
        var80.add("North", var81);
        var80.add("South", this.theory.front_end.percent_categorisation_text);
        var81.add(this.theory.front_end.percent_categorisation_button);
        var81.add(this.theory.front_end.percent_down_button);
        var81.add(this.theory.front_end.percent_categorisation_percent_text);
        var81.add(this.theory.front_end.percent_up_button);
        this.entities_panel.add("Center", var79);
        this.macro_panel.setLayout(new BorderLayout());
        Panel var83 = new Panel();
        var83.setLayout(new BorderLayout());
        var83.add("North", this.theory.front_end.play_macro_button);
        var83.add("Center", this.theory.front_end.macro_list);
        Panel var84 = new Panel();
        var84.setLayout(new BorderLayout());
        var84.add("Center", this.macro_centre_panel);
        var84.add("South", this.theory.front_end.macro_error_text);
        this.macro_text_panel.setLayout(new BorderLayout());
        this.macro_text_panel.add("Center", this.theory.front_end.macro_text);
        this.hello_text_panel.setLayout(new BorderLayout());
        this.hello_text_panel.add("Center", this.theory.front_end.hello_text);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(this.theory.front_end.hello_text);
        this.hello_text_panel.add(scrollPane);
        this.macro_centre_panel.setLayout(this.macro_text_layout);
        this.macro_text_layout.addLayoutComponent(this.hello_text_panel, "hello_panel");
        this.macro_centre_panel.add("hello_panel", this.hello_text_panel);
        this.macro_centre_panel.add("macro_panel", this.macro_text_panel);
        this.macro_text_layout.show(this.macro_centre_panel, "hello_panel");
        Panel var85 = new Panel();
        var85.setLayout(new BorderLayout());
        var85.add("North", this.theory.front_end.restore_theory_button);
        var85.add("Center", this.theory.front_end.stored_theories_list);
        this.macro_panel.add("East", var85);
        this.macro_panel.add("West", var83);
        this.macro_panel.add("Center", var84);
        this.reactions_panel.setLayout(new BorderLayout());
        this.reactions_panel.add("Center", this.theory.front_end.reactions_text);
        Panel var86 = new Panel();
        Panel var87 = new Panel();
        Panel var88 = new Panel();
        var88.setLayout(new BorderLayout());
        Panel var89 = new Panel();
        var89.setLayout(new GridLayout(2, 1));
        var89.add(this.theory.front_end.reaction_name_text);
        var89.add(this.theory.front_end.save_reaction_button);
        var88.add("North", var89);
        var88.add("Center", this.theory.front_end.reactions_list);
        this.reactions_panel.add("West", var88);
        var87.setLayout(new GridLayout(1, 2));
        var87.add(this.theory.front_end.add_reaction_button);
        var87.add(this.theory.front_end.remove_reaction_button);
        var86.setLayout(new BorderLayout());
        var86.add("North", var87);
        var86.add("Center", this.theory.front_end.reactions_added_list);
        this.reactions_panel.add("East", var86);
        this.lakatos_panel.setLayout(new BorderLayout());
        Panel var90 = new Panel();
        Panel var91 = new Panel(new BorderLayout());
        Panel var92 = new Panel(new GridLayout(1, 5));
        var92.add(this.theory.front_end.agent_search_button);
        var92.add(this.theory.front_end.agent_search_text);
        var91.add("Center", this.theory.front_end.agent_search_output_list);
        var91.add("South", var92);
        var90.add(var91);
        JTabbedPane var93 = this.theory.front_end.lakatos_tabbed_pane;
        this.lakatos_panel.add("Center", this.theory.front_end.lakatos_tabbed_pane);
        var93.add("Teacher", new AgentOutputPanel(var93, false));
        var93.add("Student 1", new AgentOutputPanel(var93, false));
        var93.add("Student 2", new AgentOutputPanel(var93, false));
        var93.add("Student 3", new AgentOutputPanel(var93, false));
        var93.add("Student 4", new AgentOutputPanel(var93, false));
        var93.add("Student 5", new AgentOutputPanel(var93, false));
        var93.add("Student 6", new AgentOutputPanel(var93, false));
        var93.add("Student 7", new AgentOutputPanel(var93, false));
        var93.add("Student 8", new AgentOutputPanel(var93, false));
        var93.add("Student 9", new AgentOutputPanel(var93, false));
        var93.add("Student 10", new AgentOutputPanel(var93, false));
        var93.add("Group", new AgentOutputPanel(var93, true));
        this.methods_panel.setLayout(new BorderLayout());
        Panel var94 = new Panel();
        var94.setLayout(new GridLayout(1, 4));
        Panel var95 = new Panel();
        Panel var96 = new Panel();
        var96.setLayout(new BorderLayout());
        var95.setBackground(Color.white);
        var96.setBackground(Color.white);
        var96.add("North", var95);
        GridBagLayout var97 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var95.setLayout(var97);
        var23.fill = 2;
        Label var98 = new Label("LAKATOS METHODS");
        var98.setForeground(Color.red);
        var98.setAlignment(1);
        var23.gridwidth = 0;
        var97.setConstraints(var98, var23);
        var95.add(var98);
        var23.weightx = 0.0D;
        var23.gridwidth = 0;
        var97.setConstraints(this.theory.front_end.use_surrender_check, var23);
        var95.add(this.theory.front_end.use_surrender_check);
        var97.setConstraints(this.theory.front_end.use_strategic_withdrawal_check, var23);
        var95.add(this.theory.front_end.use_strategic_withdrawal_check);
        var97.setConstraints(this.theory.front_end.use_piecemeal_exclusion_check, var23);
        var95.add(this.theory.front_end.use_piecemeal_exclusion_check);
        var97.setConstraints(this.theory.front_end.use_monster_barring_check, var23);
        var95.add(this.theory.front_end.use_monster_barring_check);
        var97.setConstraints(this.theory.front_end.use_monster_adjusting_check, var23);
        var95.add(this.theory.front_end.use_monster_adjusting_check);
        var97.setConstraints(this.theory.front_end.use_lemma_incorporation_check, var23);
        var95.add(this.theory.front_end.use_lemma_incorporation_check);
        var97.setConstraints(this.theory.front_end.use_proofs_and_refutations_check, var23);
        var95.add(this.theory.front_end.use_proofs_and_refutations_check);
        var97.setConstraints(this.theory.front_end.use_communal_piecemeal_exclusion_check, var23);
        var95.add(this.theory.front_end.use_communal_piecemeal_exclusion_check);
        var94.add(var95);
        Panel var99 = new Panel();
        var99.setLayout(new BorderLayout());
        Panel var100 = new Panel();
        var99.add("North", var100);
        var100.setBackground(Color.lightGray);
        var99.setBackground(Color.lightGray);
        GridBagLayout var101 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var100.setLayout(var101);
        Label var102 = new Label("Conjecture request");
        var102.setAlignment(1);
        var102.setForeground(Color.red);
        var23.fill = 2;
        var23.gridwidth = 0;
        var101.setConstraints(var102, var23);
        var100.add(var102);
        var23.gridwidth = 0;
        var101.setConstraints(this.theory.front_end.teacher_requests_conjecture_check, var23);
        var100.add(this.theory.front_end.teacher_requests_conjecture_check);
        var101.setConstraints(this.theory.front_end.teacher_requests_implication_check, var23);
        var100.add(this.theory.front_end.teacher_requests_implication_check);
        var101.setConstraints(this.theory.front_end.teacher_requests_nearimplication_check, var23);
        var100.add(this.theory.front_end.teacher_requests_nearimplication_check);
        var101.setConstraints(this.theory.front_end.teacher_requests_equivalence_check, var23);
        var100.add(this.theory.front_end.teacher_requests_equivalence_check);
        var101.setConstraints(this.theory.front_end.teacher_requests_nearequivalence_check, var23);
        var100.add(this.theory.front_end.teacher_requests_nearequivalence_check);
        var101.setConstraints(this.theory.front_end.teacher_requests_nonexists_check, var23);
        var100.add(this.theory.front_end.teacher_requests_nonexists_check);
        var94.add(var100);
        Panel var103 = new Panel();
        var101.setConstraints(var103, var23);
        var100.add(var103);
        Label var104 = new Label("Discussion");
        var104.setForeground(Color.red);
        var104.setAlignment(1);
        var23.gridwidth = 0;
        var101.setConstraints(var104, var23);
        var100.add(var104);
        var23.gridwidth = 1;
        var101.setConstraints(this.theory.front_end.max_num_independent_work_stages_check, var23);
        var100.add(this.theory.front_end.max_num_independent_work_stages_check);
        var23.gridwidth = 0;
        var101.setConstraints(this.theory.front_end.max_num_independent_work_stages_value_text, var23);
        var100.add(this.theory.front_end.max_num_independent_work_stages_value_text);
        var23.gridwidth = 1;
        var101.setConstraints(this.theory.front_end.threshold_to_add_conj_to_theory_check, var23);
        var100.add(this.theory.front_end.threshold_to_add_conj_to_theory_check);
        var23.gridwidth = 0;
        var101.setConstraints(this.theory.front_end.threshold_to_add_conj_to_theory_value_text, var23);
        var100.add(this.theory.front_end.threshold_to_add_conj_to_theory_value_text);
        var23.gridwidth = 1;
        var101.setConstraints(this.theory.front_end.threshold_to_add_concept_to_theory_check, var23);
        var100.add(this.theory.front_end.threshold_to_add_concept_to_theory_check);
        var23.gridwidth = 0;
        var101.setConstraints(this.theory.front_end.threshold_to_add_concept_to_theory_value_text, var23);
        var100.add(this.theory.front_end.threshold_to_add_concept_to_theory_value_text);
        var23.gridwidth = 1;
        var101.setConstraints(this.theory.front_end.num_independent_steps_check, var23);
        var100.add(this.theory.front_end.num_independent_steps_check);
        var23.gridwidth = 0;
        var101.setConstraints(this.theory.front_end.num_independent_steps_value_text, var23);
        var100.add(this.theory.front_end.num_independent_steps_value_text);
        Panel var105 = new Panel();
        var105.setLayout(new BorderLayout());
        Panel var106 = new Panel();
        var105.add("North", var106);
        var106.setBackground(Color.white);
        var105.setBackground(Color.white);
        GridBagLayout var107 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var106.setLayout(var107);
        Label var108 = new Label("Surrender");
        var108.setAlignment(1);
        var108.setForeground(Color.red);
        var23.fill = 2;
        var23.gridwidth = 0;
        var107.setConstraints(var108, var23);
        var106.add(var108);
        var23.gridwidth = 1;
        var107.setConstraints(this.theory.front_end.number_modifications_check, var23);
        var106.add(this.theory.front_end.number_modifications_check);
        var23.gridwidth = 0;
        var107.setConstraints(this.theory.front_end.modifications_value_text, var23);
        var106.add(this.theory.front_end.modifications_value_text);
        var23.gridwidth = 1;
        var107.setConstraints(this.theory.front_end.interestingness_threshold_check, var23);
        var106.add(this.theory.front_end.interestingness_threshold_check);
        var23.gridwidth = 0;
        var107.setConstraints(this.theory.front_end.interestingness_threshold_value_text, var23);
        var106.add(this.theory.front_end.interestingness_threshold_value_text);
        var23.gridwidth = 1;
        var107.setConstraints(this.theory.front_end.plausibility_threshold_check, var23);
        var106.add(this.theory.front_end.plausibility_threshold_check);
        var23.gridwidth = 0;
        var107.setConstraints(this.theory.front_end.plausibility_threshold_value_text, var23);
        var106.add(this.theory.front_end.plausibility_threshold_value_text);
        var23.gridwidth = 1;
        var107.setConstraints(this.theory.front_end.domain_application_threshold_check, var23);
        var106.add(this.theory.front_end.domain_application_threshold_check);
        var23.gridwidth = 0;
        var107.setConstraints(this.theory.front_end.domain_app_threshold_value_text, var23);
        var106.add(this.theory.front_end.domain_app_threshold_value_text);
        var107.setConstraints(this.theory.front_end.compare_average_interestingness_check, var23);
        var106.add(this.theory.front_end.compare_average_interestingness_check);
        Panel var109 = new Panel();
        var107.setConstraints(var109, var23);
        var106.add(var109);
        Label var110 = new Label("Exception-barring");
        var110.setForeground(Color.red);
        var110.setAlignment(1);
        var23.gridwidth = 0;
        var107.setConstraints(var110, var23);
        var106.add(var110);
        var107.setConstraints(this.theory.front_end.use_counterexample_barring_check, var23);
        var106.add(this.theory.front_end.use_counterexample_barring_check);
        var107.setConstraints(this.theory.front_end.use_piecemeal_exclusion_check, var23);
        var106.add(this.theory.front_end.use_piecemeal_exclusion_check);
        var107.setConstraints(this.theory.front_end.use_strategic_withdrawal_check, var23);
        var106.add(this.theory.front_end.use_strategic_withdrawal_check);
        var94.add(var106);
        this.methods_panel.add(var95);
        this.methods_panel.add(var94);
        Panel var111 = new Panel();
        var111.setLayout(new BorderLayout());
        Panel var112 = new Panel();
        var111.add("North", var112);
        var112.setBackground(Color.lightGray);
        var111.setBackground(Color.lightGray);
        GridBagLayout var113 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var112.setLayout(var113);
        Label var114 = new Label("Monster-barring");
        var114.setAlignment(1);
        var114.setForeground(Color.red);
        var23.fill = 2;
        var23.gridwidth = 0;
        var113.setConstraints(var114, var23);
        var112.add(var114);
        var23.gridwidth = 0;
        var113.setConstraints(this.theory.front_end.use_breaks_conj_under_discussion_check, var23);
        var112.add(this.theory.front_end.use_breaks_conj_under_discussion_check);
        var113.setConstraints(this.theory.front_end.accept_strictest_check, var23);
        var112.add(this.theory.front_end.accept_strictest_check);
        var23.gridwidth = 1;
        var113.setConstraints(this.theory.front_end.use_percentage_conj_broken_check, var23);
        var112.add(this.theory.front_end.use_percentage_conj_broken_check);
        var23.gridwidth = 0;
        var113.setConstraints(this.theory.front_end.monster_barring_min_text, var23);
        var112.add(this.theory.front_end.monster_barring_min_text);
        var23.gridwidth = 1;
        var113.setConstraints(this.theory.front_end.use_culprit_breaker_check, var23);
        var112.add(this.theory.front_end.use_culprit_breaker_check);
        var23.gridwidth = 0;
        var113.setConstraints(this.theory.front_end.monster_barring_culprit_min_text, var23);
        var112.add(this.theory.front_end.monster_barring_culprit_min_text);
        var113.setConstraints(this.theory.front_end.use_culprit_breaker_on_conj_check, var23);
        var112.add(this.theory.front_end.use_culprit_breaker_on_conj_check);
        var113.setConstraints(this.theory.front_end.use_culprit_breaker_on_all_check, var23);
        var112.add(this.theory.front_end.use_culprit_breaker_on_all_check);
        var23.gridwidth = 1;
        var113.setConstraints(this.theory.front_end.monster_barring_type_check, var23);
        var112.add(this.theory.front_end.monster_barring_type_check);
        var23.gridwidth = 0;
        var113.setConstraints(this.theory.front_end.monster_barring_type_text, var23);
        var112.add(this.theory.front_end.monster_barring_type_text);
        var94.add(var112);
        this.methods_panel.add(var95);
        this.methods_panel.add(var94);
        Panel var115 = new Panel();
        Panel var116 = new Panel();
        Panel var117 = new Panel();
        Panel var118 = new Panel();
        Panel var119 = new Panel();
        Panel var120 = new Panel();
        var118.setLayout(new BorderLayout());
        var119.setLayout(new BorderLayout());
        var120.setLayout(new BorderLayout());
        GridBagLayout var121 = new GridBagLayout();
        var115.setLayout(var121);
        var23.gridwidth = 0;
        Label var122 = new Label("During Theory Formation");
        var122.setAlignment(1);
        var122.setForeground(Color.blue);
        var121.setConstraints(var122, var23);
        var115.add(var122);
        var115.add(new Label(""));
        var115.add(new Label("Condition"));
        Label var123 = new Label("Output file");
        var121.setConstraints(var123, var23);
        var115.add(var123);
        var121.setConstraints(this.theory.front_end.keep_steps_check, var23);
        var115.add(this.theory.front_end.keep_steps_check);
        var121.setConstraints(this.theory.front_end.store_all_conjectures_text, var23);
        var115.add(this.theory.front_end.store_all_conjectures_check);
        var115.add(this.theory.front_end.store_all_conjectures_text);
        var121.setConstraints(this.theory.front_end.keep_equivalences_file_text, var23);
        var115.add(this.theory.front_end.keep_equivalences_check);
        var115.add(this.theory.front_end.keep_equivalences_condition_text);
        var115.add(this.theory.front_end.keep_equivalences_file_text);
        var121.setConstraints(this.theory.front_end.keep_non_exists_file_text, var23);
        var115.add(this.theory.front_end.keep_non_exists_check);
        var115.add(this.theory.front_end.keep_non_exists_condition_text);
        var115.add(this.theory.front_end.keep_non_exists_file_text);
        var121.setConstraints(this.theory.front_end.keep_implications_file_text, var23);
        var115.add(this.theory.front_end.keep_implications_check);
        var115.add(this.theory.front_end.keep_implications_condition_text);
        var115.add(this.theory.front_end.keep_implications_file_text);
        var121.setConstraints(this.theory.front_end.keep_implicates_file_text, var23);
        var115.add(this.theory.front_end.keep_implicates_check);
        var115.add(this.theory.front_end.keep_implicates_condition_text);
        var115.add(this.theory.front_end.keep_implicates_file_text);
        var121.setConstraints(this.theory.front_end.keep_implicates_file_text, var23);
        var115.add(this.theory.front_end.keep_prime_implicates_check);
        var115.add(this.theory.front_end.keep_prime_implicates_condition_text);
        var115.add(this.theory.front_end.keep_prime_implicates_file_text);
        GridBagLayout var124 = new GridBagLayout();
        var116.setLayout(var124);
        Label var125 = new Label("Save Theory");
        var125.setAlignment(1);
        var125.setForeground(Color.blue);
        var23.gridwidth = 0;
        var124.setConstraints(var125, var23);
        var116.add(var125);
        var23.gridwidth = 0;
        var124.setConstraints(this.theory.front_end.store_theory_name_text, var23);
        var116.add(this.theory.front_end.store_theory_button);
        var116.add(this.theory.front_end.store_theory_name_text);
        var124.setConstraints(this.theory.front_end.save_concept_construction_filename_text, var23);
        var116.add(this.theory.front_end.save_concept_construction_button);
        var116.add(this.theory.front_end.save_concept_construction_concept_id_text);
        var116.add(this.theory.front_end.save_concept_construction_filename_text);
        var124.setConstraints(this.theory.front_end.save_conjecture_construction_filename_text, var23);
        var116.add(this.theory.front_end.save_conjecture_construction_button);
        var116.add(this.theory.front_end.save_conjecture_construction_conjecture_id_text);
        var116.add(this.theory.front_end.save_conjecture_construction_filename_text);
        GridBagLayout var126 = new GridBagLayout();
        var117.setLayout(var126);
        Label var127 = new Label("Restore Theory");
        var127.setAlignment(1);
        var127.setForeground(Color.blue);
        var23.gridwidth = 0;
        var126.setConstraints(var127, var23);
        var117.add(var127);
        var126.setConstraints(this.theory.front_end.build_concept_filename_text, var23);
        var117.add(this.theory.front_end.build_concept_button);
        var117.add(this.theory.front_end.build_concept_construction_concept_id_text);
        var117.add(this.theory.front_end.build_concept_filename_text);
        var126.setConstraints(this.theory.front_end.build_conjecture_filename_text, var23);
        var117.add(this.theory.front_end.build_conjecture_button);
        var117.add(this.theory.front_end.build_conjecture_construction_conjecture_id_text);
        var117.add(this.theory.front_end.build_conjecture_filename_text);
        var118.add("North", var115);
        var119.add("North", var116);
        var120.add("North", var117);
        this.store_panel.setLayout(new GridLayout(3, 1));
        this.store_panel.add(var118);
        this.store_panel.add(var119);
        this.store_panel.add(var120);
        var22 = new GridBagLayout();
        var23 = new GridBagConstraints();
        var23.weightx = 0.0D;
        Panel var128 = new Panel();
        var128.setLayout(var22);
        var23.fill = 2;
        var128.setBackground(Color.lightGray);
        Label var129 = new Label("MATHWEB");
        var23.gridwidth = 0;
        var22.setConstraints(var129, var23);
        var128.add(var129);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.mathweb_hostname_label, var23);
        var128.add(this.theory.front_end.mathweb_hostname_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.mathweb_inout_socket_label, var23);
        var128.add(this.theory.front_end.mathweb_inout_socket_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.mathweb_service_socket_label, var23);
        var128.add(this.theory.front_end.mathweb_service_socket_label);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.use_mathweb_atp_service_check, var23);
        var128.add(this.theory.front_end.use_mathweb_atp_service_check);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.use_mathweb_otter_check, var23);
        var128.add(this.theory.front_end.use_mathweb_otter_check);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.mathweb_atp_require_all_check, var23);
        var128.add(this.theory.front_end.mathweb_atp_require_all_check);
        Label var130 = new Label("Provers to Use");
        var23.gridwidth = 0;
        var22.setConstraints(var130, var23);
        var128.add(var130);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.use_bliksem_in_mathweb_check, var23);
        var128.add(this.theory.front_end.use_bliksem_in_mathweb_check);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.use_e_in_mathweb_check, var23);
        var128.add(this.theory.front_end.use_e_in_mathweb_check);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.use_otter_in_mathweb_check, var23);
        var128.add(this.theory.front_end.use_otter_in_mathweb_check);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.use_spass_in_mathweb_check, var23);
        var128.add(this.theory.front_end.use_spass_in_mathweb_check);
        Label var131 = new Label("SYSTEMONTPTP");
        var23.gridwidth = 0;
        var22.setConstraints(var131, var23);
        var128.add(var131);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.sot_submit_button, var23);
        var128.add(this.theory.front_end.sot_submit_button);
        var23.gridwidth = 0;
        var22.setConstraints(this.theory.front_end.sot_conjecture_number_text, var23);
        var128.add(this.theory.front_end.sot_conjecture_number_text);
        this.interface_panel.setLayout(new BorderLayout());
        this.interface_panel.add("West", var128);
        this.interface_panel.add("Center", this.theory.front_end.sot_results_text);
        Panel var132 = new Panel();
        GridBagLayout var133 = new GridBagLayout();
        var132.setLayout(var133);
        var132.add(new Label("Gold standard categorisation:"));
        var133.setConstraints(this.theory.front_end.gold_standard_categorisation_text, var23);
        var132.add(this.theory.front_end.gold_standard_categorisation_text);
        var132.add(new Label("Concept object type:"));
        var133.setConstraints(this.theory.front_end.object_types_to_learn_text, var23);
        var132.add(this.theory.front_end.object_types_to_learn_text);
        var132.add(new Label("Coverage categorisation:"));
        var133.setConstraints(this.theory.front_end.coverage_categorisation_text, var23);
        var132.add(this.theory.front_end.coverage_categorisation_text);
        var132.add(new Label("Segregation categorisation:"));
        var133.setConstraints(this.theory.front_end.segregation_categorisation_text, var23);
        var132.add(this.theory.front_end.segregation_categorisation_text);
        var133.setConstraints(this.theory.front_end.segregated_search_check, var23);
        var132.add(this.theory.front_end.segregated_search_check);
        this.learn_panel.setLayout(new GridLayout());
        this.learn_panel.add("North", var132);
        Panel var134 = new Panel();
        var134.setLayout(new GridLayout(10, 1));
        Label var135 = new Label("ooo choices size");
        var134.add(var135);
        var134.add(this.theory.front_end.puzzle_ooo_choices_size_text);
        Label var136 = new Label("nis choices size");
        var134.add(var136);
        var134.add(this.theory.front_end.puzzle_nis_choices_size_text);
        Label var137 = new Label("analogy choices size");
        var134.add(var137);
        var134.add(this.theory.front_end.puzzle_analogy_choices_size_text);
        var134.add(this.theory.front_end.puzzle_generate_button);
        this.puzzle_panel.setLayout(new BorderLayout());
        this.puzzle_panel.add("West", var134);
        this.puzzle_panel.add("Center", this.theory.front_end.puzzle_output_text);
        this.predict_panel.setLayout(new BorderLayout());
        Panel var138 = new Panel();
        var138.setLayout(new GridLayout(1, 2));
        Panel var139 = new Panel();
        Panel var140 = new Panel();
        var139.setLayout(new GridLayout(20, 1));
        var140.setLayout(new GridLayout(20, 1));
        var138.add(var139);
        var138.add(var140);
        Panel var141 = new Panel();
        this.predict_panel.add("North", var141);
        this.predict_panel.add("Center", var138);
        this.predict_panel.add("South", this.theory.front_end.predict_explanation_list);
        var141.setLayout(new GridLayout(2, 1));
        Panel var142 = new Panel();
        Panel var143 = new Panel();
        var141.add(var142);
        var141.add(var143);
        var142.add(this.theory.front_end.predict_all_button);
        var142.add(this.theory.front_end.predict_incremental_steps_button);
        var142.add(this.theory.front_end.predict_input_files_choice);
        var142.add(this.theory.front_end.predict_entity_type_choice);
        var142.add(this.theory.front_end.predict_method_choice);
        var143.add(new Label("predict for:"));
        var143.add(this.theory.front_end.predict_entity_text);
        var143.add(this.theory.front_end.predict_button);
        var143.add(this.theory.front_end.predict_min_percent_text);
        var143.add(new Label("%"));
        var143.add(new Label("steps:"));
        var143.add(this.theory.front_end.predict_max_steps_text);
        var143.add(this.theory.front_end.predict_use_negations_check);
        var143.add(this.theory.front_end.predict_use_equivalences_check);
        Panel var144 = new Panel();
        var144.setLayout(new BorderLayout());
        var144.add("West", this.theory.front_end.predict_names_button1);
        var144.add("Center", this.theory.front_end.predict_values_text1);
        var139.add(var144);
        Panel var145 = new Panel();
        var145.setLayout(new BorderLayout());
        var145.add("West", this.theory.front_end.predict_names_button2);
        var145.add("Center", this.theory.front_end.predict_values_text2);
        var139.add(var145);
        Panel var146 = new Panel();
        var146.setLayout(new BorderLayout());
        var146.add("West", this.theory.front_end.predict_names_button3);
        var146.add("Center", this.theory.front_end.predict_values_text3);
        var139.add(var146);
        Panel var147 = new Panel();
        var147.setLayout(new BorderLayout());
        var147.add("West", this.theory.front_end.predict_names_button4);
        var147.add("Center", this.theory.front_end.predict_values_text4);
        var139.add(var147);
        Panel var148 = new Panel();
        var148.setLayout(new BorderLayout());
        var148.add("West", this.theory.front_end.predict_names_button5);
        var148.add("Center", this.theory.front_end.predict_values_text5);
        var139.add(var148);
        Panel var149 = new Panel();
        var149.setLayout(new BorderLayout());
        var149.add("West", this.theory.front_end.predict_names_button6);
        var149.add("Center", this.theory.front_end.predict_values_text6);
        var139.add(var149);
        Panel var150 = new Panel();
        var150.setLayout(new BorderLayout());
        var150.add("West", this.theory.front_end.predict_names_button7);
        var150.add("Center", this.theory.front_end.predict_values_text7);
        var139.add(var150);
        Panel var151 = new Panel();
        var151.setLayout(new BorderLayout());
        var151.add("West", this.theory.front_end.predict_names_button8);
        var151.add("Center", this.theory.front_end.predict_values_text8);
        var139.add(var151);
        Panel var152 = new Panel();
        var152.setLayout(new BorderLayout());
        var152.add("West", this.theory.front_end.predict_names_button9);
        var152.add("Center", this.theory.front_end.predict_values_text9);
        var139.add(var152);
        Panel var153 = new Panel();
        var153.setLayout(new BorderLayout());
        var153.add("West", this.theory.front_end.predict_names_button10);
        var153.add("Center", this.theory.front_end.predict_values_text10);
        var139.add(var153);
        Panel var154 = new Panel();
        var154.setLayout(new BorderLayout());
        var154.add("West", this.theory.front_end.predict_names_button11);
        var154.add("Center", this.theory.front_end.predict_values_text11);
        var139.add(var154);
        Panel var155 = new Panel();
        var155.setLayout(new BorderLayout());
        var155.add("West", this.theory.front_end.predict_names_button12);
        var155.add("Center", this.theory.front_end.predict_values_text12);
        var139.add(var155);
        Panel var156 = new Panel();
        var156.setLayout(new BorderLayout());
        var156.add("West", this.theory.front_end.predict_names_button13);
        var156.add("Center", this.theory.front_end.predict_values_text13);
        var139.add(var156);
        Panel var157 = new Panel();
        var157.setLayout(new BorderLayout());
        var157.add("West", this.theory.front_end.predict_names_button14);
        var157.add("Center", this.theory.front_end.predict_values_text14);
        var139.add(var157);
        Panel var158 = new Panel();
        var158.setLayout(new BorderLayout());
        var158.add("West", this.theory.front_end.predict_names_button15);
        var158.add("Center", this.theory.front_end.predict_values_text15);
        var139.add(var158);
        Panel var159 = new Panel();
        var159.setLayout(new BorderLayout());
        var159.add("West", this.theory.front_end.predict_names_button16);
        var159.add("Center", this.theory.front_end.predict_values_text16);
        var139.add(var159);
        Panel var160 = new Panel();
        var160.setLayout(new BorderLayout());
        var160.add("West", this.theory.front_end.predict_names_button17);
        var160.add("Center", this.theory.front_end.predict_values_text17);
        var139.add(var160);
        Panel var161 = new Panel();
        var161.setLayout(new BorderLayout());
        var161.add("West", this.theory.front_end.predict_names_button18);
        var161.add("Center", this.theory.front_end.predict_values_text18);
        var139.add(var161);
        Panel var162 = new Panel();
        var162.setLayout(new BorderLayout());
        var162.add("West", this.theory.front_end.predict_names_button19);
        var162.add("Center", this.theory.front_end.predict_values_text19);
        var139.add(var162);
        Panel var163 = new Panel();
        var163.setLayout(new BorderLayout());
        var163.add("West", this.theory.front_end.predict_names_button20);
        var163.add("Center", this.theory.front_end.predict_values_text20);
        var139.add(var163);
        Panel var164 = new Panel();
        var164.setLayout(new BorderLayout());
        var164.add("West", this.theory.front_end.predict_names_button21);
        var164.add("Center", this.theory.front_end.predict_values_text21);
        var140.add(var164);
        Panel var165 = new Panel();
        var165.setLayout(new BorderLayout());
        var165.add("West", this.theory.front_end.predict_names_button22);
        var165.add("Center", this.theory.front_end.predict_values_text22);
        var140.add(var165);
        Panel var166 = new Panel();
        var166.setLayout(new BorderLayout());
        var166.add("West", this.theory.front_end.predict_names_button23);
        var166.add("Center", this.theory.front_end.predict_values_text23);
        var140.add(var166);
        Panel var167 = new Panel();
        var167.setLayout(new BorderLayout());
        var167.add("West", this.theory.front_end.predict_names_button24);
        var167.add("Center", this.theory.front_end.predict_values_text24);
        var140.add(var167);
        Panel var168 = new Panel();
        var168.setLayout(new BorderLayout());
        var168.add("West", this.theory.front_end.predict_names_button25);
        var168.add("Center", this.theory.front_end.predict_values_text25);
        var140.add(var168);
        Panel var169 = new Panel();
        var169.setLayout(new BorderLayout());
        var169.add("West", this.theory.front_end.predict_names_button26);
        var169.add("Center", this.theory.front_end.predict_values_text26);
        var140.add(var169);
        Panel var170 = new Panel();
        var170.setLayout(new BorderLayout());
        var170.add("West", this.theory.front_end.predict_names_button27);
        var170.add("Center", this.theory.front_end.predict_values_text27);
        var140.add(var170);
        Panel var171 = new Panel();
        var171.setLayout(new BorderLayout());
        var171.add("West", this.theory.front_end.predict_names_button28);
        var171.add("Center", this.theory.front_end.predict_values_text28);
        var140.add(var171);
        Panel var172 = new Panel();
        var172.setLayout(new BorderLayout());
        var172.add("West", this.theory.front_end.predict_names_button29);
        var172.add("Center", this.theory.front_end.predict_values_text29);
        var140.add(var172);
        Panel var173 = new Panel();
        var173.setLayout(new BorderLayout());
        var173.add("West", this.theory.front_end.predict_names_button30);
        var173.add("Center", this.theory.front_end.predict_values_text30);
        var140.add(var173);
        Panel var174 = new Panel();
        var174.setLayout(new BorderLayout());
        var174.add("West", this.theory.front_end.predict_names_button31);
        var174.add("Center", this.theory.front_end.predict_values_text31);
        var140.add(var174);
        Panel var175 = new Panel();
        var175.setLayout(new BorderLayout());
        var175.add("West", this.theory.front_end.predict_names_button32);
        var175.add("Center", this.theory.front_end.predict_values_text32);
        var140.add(var175);
        Panel var176 = new Panel();
        var176.setLayout(new BorderLayout());
        var176.add("West", this.theory.front_end.predict_names_button33);
        var176.add("Center", this.theory.front_end.predict_values_text33);
        var140.add(var176);
        Panel var177 = new Panel();
        var177.setLayout(new BorderLayout());
        var177.add("West", this.theory.front_end.predict_names_button34);
        var177.add("Center", this.theory.front_end.predict_values_text34);
        var140.add(var177);
        Panel var178 = new Panel();
        var178.setLayout(new BorderLayout());
        var178.add("West", this.theory.front_end.predict_names_button35);
        var178.add("Center", this.theory.front_end.predict_values_text35);
        var140.add(var178);
        Panel var179 = new Panel();
        var179.setLayout(new BorderLayout());
        var179.add("West", this.theory.front_end.predict_names_button36);
        var179.add("Center", this.theory.front_end.predict_values_text36);
        var140.add(var179);
        Panel var180 = new Panel();
        var180.setLayout(new BorderLayout());
        var180.add("West", this.theory.front_end.predict_names_button37);
        var180.add("Center", this.theory.front_end.predict_values_text37);
        var140.add(var180);
        Panel var181 = new Panel();
        var181.setLayout(new BorderLayout());
        var181.add("West", this.theory.front_end.predict_names_button38);
        var181.add("Center", this.theory.front_end.predict_values_text38);
        var140.add(var181);
        Panel var182 = new Panel();
        var182.setLayout(new BorderLayout());
        var182.add("West", this.theory.front_end.predict_names_button39);
        var182.add("Center", this.theory.front_end.predict_values_text39);
        var140.add(var182);
        Panel var183 = new Panel();
        var183.setLayout(new BorderLayout());
        var183.add("West", this.theory.front_end.predict_names_button40);
        var183.add("Center", this.theory.front_end.predict_values_text40);
        var140.add(var183);
        this.help_panel.setLayout(new BorderLayout());
        Panel var184 = new Panel();
        var184.setLayout(new BorderLayout());
        var184.add("Center", this.theory.front_end.help_text);
        Panel var185 = new Panel();
        var185.add(this.theory.front_end.save_help_button);
        var185.add(this.theory.front_end.help_save_file_text);
        var184.add("North", var185);
        this.help_panel.add("West", this.theory.front_end.help_list);
        this.help_panel.add("Center", var184);
        this.main_panel.setLayout(this.main_layout);
        this.main_layout.addLayoutComponent(this.macro_panel, "macro panel");
        this.main_panel.add("macro panel", this.macro_panel);
        this.main_panel.add("domain panel", this.domain_panel);
        this.main_panel.add("report panel", this.report_panel);
        this.main_panel.add("statistics panel", this.statistics_panel);
        this.main_panel.add("search panel", this.search_panel);
        this.main_panel.add("proof panel", this.proof_panel);
        this.main_panel.add("concepts panel", this.concepts_panel);
        this.main_panel.add("conjectures panel", this.conjectures_panel);
        this.main_panel.add("entities panel", this.entities_panel);
        this.main_panel.add("agenda panel", this.agenda_panel);
        this.main_panel.add("force panel", this.force_panel);
        this.main_panel.add("pseudo_code panel", this.pseudo_code_panel);
        this.main_panel.add("systemout panel", this.systemout_panel);
        this.main_panel.add("learn panel", this.learn_panel);
        this.main_panel.add("predict panel", this.predict_panel);
        this.main_panel.add("puzzle panel", this.puzzle_panel);
        this.main_panel.add("reactions panel", this.reactions_panel);
        this.main_panel.add("lakatos panel", this.lakatos_panel);
        this.main_panel.add("methods panel", this.methods_panel);
        this.main_panel.add("read panel", this.read_panel);
        this.main_panel.add("help panel", this.help_panel);
        this.main_panel.add("interface panel", this.interface_panel);
        this.main_panel.add("store panel", this.store_panel);
        this.top_main_buttons_panel.setBackground(new Color(100, 100, 170));
        this.bottom_main_buttons_panel.setBackground(new Color(100, 100, 120));
        this.theory.front_end.play_macro_button.setBackground(Color.red);
        this.theory.front_end.play_macro_button.setForeground(Color.white);
        this.theory.front_end.restore_theory_button.setBackground(Color.red);
        this.theory.front_end.restore_theory_button.setForeground(Color.white);
        this.theory.front_end.split_values_list.setBackground(Color.lightGray);
        this.theory.front_end.split_values_label.setBackground(Color.lightGray);
        this.theory.front_end.split_values_list.setForeground(Color.black);
        this.theory.front_end.split_values_label.setForeground(Color.black);
        this.theory.front_end.initial_entity_list.setBackground(Color.lightGray);
        this.theory.front_end.initial_entity_label.setBackground(Color.lightGray);
        this.theory.front_end.domain_label.setBackground(Color.white);
        this.theory.front_end.domain_label.setForeground(Color.black);
        this.theory.front_end.domain_list.setBackground(Color.white);
        this.theory.front_end.domain_list.setForeground(Color.black);
        this.theory.front_end.counterexample_list.setBackground(Color.white);
        this.theory.front_end.counterexample_list.setForeground(Color.black);
        this.theory.front_end.counterexample_label.setBackground(Color.white);
        this.theory.front_end.counterexample_label.setForeground(Color.black);
        this.theory.front_end.initial_concepts_list.setBackground(Color.lightGray);
        this.theory.front_end.initial_concepts_list.setForeground(Color.black);
        this.theory.front_end.initial_concepts_label.setBackground(Color.lightGray);
        this.theory.front_end.initial_concepts_label.setForeground(Color.black);
        this.theory.front_end.highlight_list.setBackground(Color.white);
        this.theory.front_end.highlight_list.setForeground(Color.black);
        this.theory.front_end.highlight_label.setBackground(Color.white);
        this.theory.front_end.highlight_label.setForeground(Color.black);
        this.theory.front_end.agenda_limit_label.setBackground(Color.white);
        this.theory.front_end.agenda_limit_label.setForeground(Color.black);
        this.theory.front_end.agenda_limit_text.setBackground(Color.white);
        this.theory.front_end.agenda_limit_text.setForeground(Color.black);
        this.theory.front_end.complexity_label.setBackground(Color.white);
        this.theory.front_end.complexity_label.setForeground(Color.black);
        this.theory.front_end.complexity_text.setBackground(Color.white);
        this.theory.front_end.complexity_text.setForeground(Color.black);
        this.theory.front_end.tuple_product_limit_label.setBackground(Color.white);
        this.theory.front_end.tuple_product_limit_label.setForeground(Color.black);
        this.theory.front_end.tuple_product_limit_text.setBackground(Color.white);
        this.theory.front_end.tuple_product_limit_text.setForeground(Color.black);
        this.theory.front_end.arity_limit_button.setForeground(Color.black);
        this.theory.front_end.arity_limit_text.setBackground(Color.white);
        this.theory.front_end.arity_limit_text.setForeground(Color.black);
        this.start_over_button.setBackground(new Color(125, 100, 220));
        this.start_over_button.setForeground(Color.white);
        this.theory.front_end.start_button.setBackground(new Color(125, 100, 210));
        this.theory.front_end.start_button.setForeground(Color.white);
        this.theory.front_end.stop_button.setBackground(new Color(125, 100, 220));
        this.theory.front_end.stop_button.setForeground(Color.white);
        this.theory.front_end.step_button.setBackground(new Color(125, 100, 220));
        this.theory.front_end.step_button.setForeground(Color.white);
        this.theory.front_end.status_label.setBackground(new Color(120, 100, 220));
        this.theory.front_end.status_label.setForeground(Color.yellow);
        this.theory.front_end.save_macro_button.setBackground(new Color(120, 100, 220));
        this.theory.front_end.save_macro_button.setForeground(Color.black);
        Panel var186 = new Panel(new BorderLayout());
        this.bottom_main_buttons_panel.setLayout(new GridLayout(2, 1));
        Panel var187 = new Panel();
        var187.setLayout(new GridLayout(1, 4));
        var187.add(this.start_over_button);
        var187.add(this.theory.front_end.start_button);
        var187.add(this.theory.front_end.stop_button);
        var187.add(this.theory.front_end.step_button);
        this.theory.front_end.step_button.setLabel("         Step          ");
        var186.add("West", var187);
        var186.add("Center", this.theory.front_end.status_label);
        var186.add("East", this.start_over_button);
        Panel var188 = new Panel(new GridLayout(2, 10));
        this.screen_buttons = new ScreenButtons();
        this.screen_buttons.concept_screen_button.addActionListener(this);
        this.screen_buttons.conjecture_screen_button.addActionListener(this);
        this.screen_buttons.entities_screen_button.addActionListener(this);
        this.screen_buttons.macro_screen_button.addActionListener(this);
        this.screen_buttons.domain_screen_button.addActionListener(this);
        this.screen_buttons.search_screen_button.addActionListener(this);
        this.screen_buttons.report_screen_button.addActionListener(this);
        this.screen_buttons.force_screen_button.addActionListener(this);
        this.screen_buttons.agenda_screen_button.addActionListener(this);
        this.screen_buttons.statistics_screen_button.addActionListener(this);
        this.screen_buttons.proof_screen_button.addActionListener(this);
        this.screen_buttons.store_screen_button.addActionListener(this);
        this.screen_buttons.interface_screen_button.addActionListener(this);
        this.screen_buttons.react_screen_button.addActionListener(this);
        this.screen_buttons.lakatos_screen_button.addActionListener(this);
        this.screen_buttons.lakatos_methods_screen_button.addActionListener(this);
        this.screen_buttons.learn_screen_button.addActionListener(this);
        this.screen_buttons.predict_screen_button.addActionListener(this);
        this.screen_buttons.systemout_screen_button.addActionListener(this);
        this.screen_buttons.topics_screen_button.addActionListener(this);
        this.screen_buttons.about_screen_button.addActionListener(this);
        var188.add(this.screen_buttons.lakatos_screen_button);
        var188.add(this.screen_buttons.lakatos_methods_screen_button);
        var188.add(this.screen_buttons.concept_screen_button);
        var188.add(this.screen_buttons.conjecture_screen_button);
        var188.add(this.screen_buttons.entities_screen_button);
        var188.add(this.screen_buttons.macro_screen_button);
        var188.add(this.screen_buttons.domain_screen_button);
        var188.add(this.screen_buttons.search_screen_button);
        var188.add(this.screen_buttons.report_screen_button);
        var188.add(this.screen_buttons.force_screen_button);
        var188.add(this.screen_buttons.agenda_screen_button);
        var188.add(this.screen_buttons.statistics_screen_button);
        var188.add(this.screen_buttons.proof_screen_button);
        var188.add(this.screen_buttons.store_screen_button);
        var188.add(this.screen_buttons.interface_screen_button);
        var188.add(this.screen_buttons.react_screen_button);
        var188.add(this.screen_buttons.learn_screen_button);
        var188.add(this.screen_buttons.predict_screen_button);
        var188.add(this.screen_buttons.systemout_screen_button);
        var188.add(this.screen_buttons.topics_screen_button);
        var188.add(this.screen_buttons.about_screen_button);
        this.bottom_main_buttons_panel.add(var186);
        this.bottom_main_buttons_panel.add(var188);
        this.main_buttons_panel.add(this.top_main_buttons_panel);
        this.main_buttons_panel.add(this.bottom_main_buttons_panel);
        this.setup_choice.addItemListener(this);
        this.application_choice.addItemListener(this);
        this.view_choice.addItemListener(this);
        this.debug_choice.addItemListener(this);
        this.help_choice.addItemListener(this);
        this.theory.front_end.store_theory_button.addActionListener(this);
        this.theory.front_end.restore_theory_button.addActionListener(this);
        this.start_over_button.addActionListener(this);
        this.setLayout(new BorderLayout());
        this.add("North", var1);
        this.add("Center", this.main_panel);
        this.add("South", this.main_buttons_panel);
        this.theory.front_end.main_frame = this;
        this.theory.predict.main_frame = this;
        this.theory.graph_handler.main_frame = this;
        this.main_layout.show(this.main_panel, "macro panel");
    }

    private void whiteButtons(Button var1, Button var2) {
        var1.setBackground(Color.white);
        var1.setForeground(Color.black);
        var2.setBackground(Color.white);
        var2.setForeground(Color.black);
    }

    private void grayButtons(Button var1, Button var2) {
        var1.setBackground(Color.lightGray);
        var1.setForeground(Color.black);
        var2.setBackground(Color.lightGray);
        var2.setForeground(Color.black);
    }

    private void invertColour(Button var1) {
        var1.setBackground(Color.white);
        var1.setForeground(Color.blue);
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.screen_buttons.concept_screen_button) {
            this.changeScreen("CONCEPTS");
        }

        if (var1.getSource() == this.screen_buttons.conjecture_screen_button) {
            this.changeScreen("CONJECTURES");
        }

        if (var1.getSource() == this.screen_buttons.entities_screen_button) {
            this.changeScreen("ENTITIES");
        }

        if (var1.getSource() == this.screen_buttons.macro_screen_button) {
            this.changeScreen("MACRO");
        }

        if (var1.getSource() == this.screen_buttons.domain_screen_button) {
            this.changeScreen("DOMAIN");
        }

        if (var1.getSource() == this.screen_buttons.search_screen_button) {
            this.changeScreen("SEARCH");
        }

        if (var1.getSource() == this.screen_buttons.proof_screen_button) {
            this.changeScreen("PROOF");
        }

        if (var1.getSource() == this.screen_buttons.store_screen_button) {
            this.changeScreen("STORE");
        }

        if (var1.getSource() == this.screen_buttons.interface_screen_button) {
            this.changeScreen("INTERFACE");
        }

        if (var1.getSource() == this.screen_buttons.react_screen_button) {
            this.changeScreen("REACT");
        }

        if (var1.getSource() == this.screen_buttons.lakatos_screen_button) {
            this.changeScreen("LAKATOS");
        }

        if (var1.getSource() == this.screen_buttons.lakatos_methods_screen_button) {
            this.changeScreen("METHODS");
        }

        if (var1.getSource() == this.screen_buttons.learn_screen_button) {
            this.changeScreen("LEARN");
        }

        if (var1.getSource() == this.screen_buttons.predict_screen_button) {
            this.changeScreen("PREDICT");
        }

        if (var1.getSource() == this.screen_buttons.systemout_screen_button) {
            this.changeScreen("SYSTEMOUT");
        }

        if (var1.getSource() == this.screen_buttons.pseudocode_screen_button) {
            this.changeScreen("PSEUDOCODE");
        }

        if (var1.getSource() == this.screen_buttons.topics_screen_button) {
            this.changeScreen("TOPICS");
        }

        if (var1.getSource() == this.screen_buttons.about_screen_button) {
            this.changeScreen("ABOUT");
        }

        if (var1.getSource() == this.screen_buttons.report_screen_button) {
            this.changeScreen("REPORTS");
        }

        if (var1.getSource() == this.screen_buttons.force_screen_button) {
            this.changeScreen("FORCE");
        }

        if (var1.getSource() == this.screen_buttons.agenda_screen_button) {
            this.changeScreen("AGENDA");
        }

        if (var1.getSource() == this.screen_buttons.statistics_screen_button) {
            this.changeScreen("STATISTICS");
        }

        String var2;
        if (var1.getSource() == this.theory.front_end.restore_theory_button && this.theory.front_end.stored_theories_list.getSelectedItem() != null) {
            this.theory.front_end.restore_theory_button.setLabel("Wait");

            try {
                var2 = this.theory.input_files_directory + this.theory.front_end.stored_theories_list.getSelectedItem();
                ObjectInputStream var3 = new ObjectInputStream(new FileInputStream(var2));
                HR var4 = (HR)var3.readObject();
                var4.main_layout.show(var4.main_panel, "concepts panel");
                var4.menu_label.setText("CONCEPTS");
                var4.theory.front_end.store_theory_button.setLabel("Store theory");
                var4.setVisible(true);
                var4.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent var1) {
                        HR.this.theory.storage_handler.closeObjectStreams();
                        System.exit(0);
                    }
                });
                var3.close();
                this.dispose();
            } catch (Exception var6) {
                System.out.println(var6);
            }
        }

        if (var1.getSource() == this.theory.front_end.store_theory_button && !this.theory.front_end.store_theory_name_text.getText().equals("")) {
            this.theory.front_end.store_theory_button.setLabel("Wait");

            try {
                var2 = this.theory.input_files_directory + this.theory.front_end.store_theory_name_text.getText();
                ObjectOutputStream var8 = new ObjectOutputStream(new FileOutputStream(var2));
                var8.writeObject(this);
                var8.flush();
                var8.close();
                this.theory.front_end.getTheoryFiles();
            } catch (Exception var5) {
                System.out.println(var5);
            }

            this.theory.front_end.store_theory_button.setLabel("Store theory");
        }

        if (var1.getSource() == this.start_over_button) {
            this.start_over_button.setLabel("Wait");
            HR var7 = new HR();
            var7.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent var1) {
                    System.exit(0);
                }
            });

            for(int var9 = 0; var9 < var7.theory.front_end.predict_names_buttons_vector.size(); ++var9) {
                ((Button)var7.theory.front_end.predict_names_buttons_vector.elementAt(var9)).addActionListener(var7.theory);
            }

            var7.config_handler = this.config_handler;
            var7.config_handler.initialiseHR(var7);
            var7.theory.passOnSettings();
            var7.theory.front_end.init();
            var7.setVisible(true);
            this.dispose();
        }

    }

    public void itemStateChanged(ItemEvent var1) {
        String var2 = ((Choice)var1.getSource()).getSelectedItem();
        ((Choice)var1.getSource()).select(0);
        this.changeScreen(var2);
    }

    public void changeScreen(String var1) {
        String var2 = "";
        if (var1.equals("ABOUT")) {
            new ImageViewer((String)this.config_handler.values_table.get("input files directory"), "about.png", "HR " + this.version_number);
        }

        if (var1.equals("DOMAIN")) {
            var2 = "domain panel";
            this.theory.front_end.getInputFiles();
        }

        if (var1.equals("SEARCH")) {
            var2 = "search panel";
        }

        if (var1.equals("PROOF")) {
            var2 = "proof panel";
        }

        if (var1.equals("CONCEPTS")) {
            var2 = "concepts panel";
        }

        if (var1.equals("CONJECTURES")) {
            var2 = "conjectures panel";
        }

        if (var1.equals("ENTITIES")) {
            var2 = "entities panel";
        }

        if (var1.equals("AGENDA")) {
            var2 = "agenda panel";
        }

        if (var1.equals("FORCE")) {
            var2 = "force panel";
        }

        if (var1.equals("PSEUDOCODE")) {
            var2 = "pseudo_code panel";
        }

        if (var1.equals("SYSTEMOUT")) {
            var2 = "systemout panel";
        }

        if (var1.equals("LEARN")) {
            var2 = "learn panel";
        }

        if (var1.equals("REPORTS")) {
            var2 = "report panel";
        }

        if (var1.equals("STATISTICS")) {
            var2 = "statistics panel";
        }

        if (var1.equals("MACRO")) {
            var2 = "macro panel";
        }

        if (var1.equals("PUZZLE")) {
            var2 = "puzzle panel";
        }

        if (var1.equals("AGENCY")) {
            var2 = "agency panel";
        }

        if (var1.equals("REACT")) {
            var2 = "reactions panel";
        }

        if (var1.equals("LAKATOS")) {
            var2 = "lakatos panel";
        }

        if (var1.equals("METHODS")) {
            var2 = "methods panel";
        }

        if (var1.equals("PREDICT")) {
            var2 = "predict panel";
        }

        if (var1.equals("TOPICS")) {
            var2 = "help panel";
        }

        if (var1.equals("INTERFACE")) {
            var2 = "interface panel";
        }

        if (var1.equals("STORE")) {
            var2 = "store panel";
        }

        this.main_layout.show(this.main_panel, var2);
        if (!var2.equals("")) {
            this.menu_label.setText(var1);
        }

    }

    public static void main(String[] var0) {
        HR var1 = new HR();
        var1.constructHR(var0);
    }

    public HR constructHR(String[] var1) {
        if (var1.length == 0) {
            System.out.println("HR version " + (new HR()).version_number);
            System.out.println("Usage: ");
            System.out.println("HR configuration_file (loads the given config file)");
            System.out.println("HR configuration_file macro macro_file argument#1 argument#2 ... (runs the given macro)");
            System.out.println("HR configuration_file restore theory_file (restores the give theory)");
            System.exit(0);
        }

        ConfigHandler var2;
        boolean var3;
        if (var1.length == 3 && var1[1].equals("restore")) {
            var2 = new ConfigHandler();
            var3 = var2.readUserConfiguration(var1[0]);
            if (!var3) {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }

            String var8 = (String)var2.values_table.get("input files directory") + "/" + var1[2];
            System.out.println("Restoring theory: " + var8);

            try {
                ObjectInputStream var10 = new ObjectInputStream(new FileInputStream(var8));
                HR var11 = (HR)var10.readObject();
                var11.main_layout.show(var11.main_panel, "concepts panel");
                var11.menu_label.setText("CONCEPTS");
                var11.theory.front_end.store_theory_button.setLabel("Store theory");
                var11.setVisible(true);
                var11.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent var1) {
                        System.exit(0);
                    }
                });
                var10.close();
            } catch (Exception var7) {
                System.out.println("Sorry, couldn't load the theory:");
                System.out.println(var7);
            }

            return null;
        } else {
            var2 = new ConfigHandler();
            var3 = var2.readUserConfiguration(var1[0]);
            if (!var3) {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }

            HR var4 = new HR();
            var4.config_handler = var2;
            var4.config_handler.initialiseHR(var4);
            var4.theory.passOnSettings();
            var4.theory.front_end.init();

            for(int var5 = 0; var5 < var4.theory.front_end.predict_names_buttons_vector.size(); ++var5) {
                ((Button)var4.theory.front_end.predict_names_buttons_vector.elementAt(var5)).addActionListener(var4.theory);
            }

            if (var1.length == 1) {
                var4.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent var1) {
                        System.exit(0);
                    }
                });
                var4.setVisible(true);
            }

            if (var1.length >= 3 && (var1[1].equals("macro") || var1[1].equals("vmacro"))) {
                String var9 = var4.theory.input_files_directory + var1[2];

                for(int var6 = 2; var6 < var1.length; ++var6) {
                    var4.theory.command_line_arguments.addElement(var1[var6]);
                }

                var4.theory.use_front_end = false;
                if (var1[1].equals("vmacro")) {
                    var4.setVisible(true);
                    var4.theory.use_front_end = true;
                } else {
                    var4.setVisible(false);
                }

                var4.theory.loadMacro(var9);
                var4.theory.playMacro();
            }

            return var4;
        }
    }

    public HR constructHR_old(String[] var1) {
        System.out.println("into constructHR. args.length is " + var1.length);

        for(int var2 = 0; var2 < var1.length; ++var2) {
            System.out.println("args[" + var2 + "] is " + var1[var2]);
        }

        if (var1.length == 0) {
            System.out.println("HR version " + (new HR()).version_number);
            System.out.println("Usage: ");
            System.out.println("HR configuration_file (loads the given config file)");
            System.out.println("HR configuration_file macro macro_file argument#1 argument#2 ... (runs the given macro)");
            System.out.println("HR configuration_file restore theory_file (restores the give theory)");
            System.exit(0);
        }

        boolean var3;
        ConfigHandler var8;
        if (var1.length == 3 && var1[1].equals("restore")) {
            var8 = new ConfigHandler();
            var3 = var8.readUserConfiguration(var1[0]);
            if (!var3) {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }

            String var9 = (String)var8.values_table.get("input files directory") + "/" + var1[2];
            System.out.println("Restoring theory: " + var9);

            try {
                ObjectInputStream var11 = new ObjectInputStream(new FileInputStream(var9));
                HR var12 = (HR)var11.readObject();
                var12.main_layout.show(var12.main_panel, "concepts panel");
                var12.menu_label.setText("CONCEPTS");
                var12.theory.front_end.store_theory_button.setLabel("Store theory");
                var12.setVisible(true);
                var12.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent var1) {
                        System.exit(0);
                    }
                });
                var11.close();
            } catch (Exception var7) {
                System.out.println("Sorry, couldn't load the theory:");
                System.out.println(var7);
            }

            return null;
        } else {
            var8 = new ConfigHandler();
            var3 = var8.readUserConfiguration(var1[0]);
            if (!var3) {
                System.out.println("Cannot read configuration file - must exit, sorry");
                System.exit(0);
            }

            HR var4 = new HR();
            var4.config_handler = var8;
            var4.config_handler.initialiseHR(var4);
            var4.theory.passOnSettings();
            var4.theory.front_end.init();

            for(int var5 = 0; var5 < var4.theory.front_end.predict_names_buttons_vector.size(); ++var5) {
                ((Button)var4.theory.front_end.predict_names_buttons_vector.elementAt(var5)).addActionListener(var4.theory);
            }

            if (var1.length == 1) {
                System.out.println("here");
                var4.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent var1) {
                        System.exit(0);
                    }
                });
                var4.setVisible(true);
            }

            if (var1.length >= 3 && var1[1].equals("macro")) {
                String var10 = var4.theory.input_files_directory + var1[2];

                for(int var6 = 2; var6 < var1.length; ++var6) {
                    var4.theory.command_line_arguments.addElement(var1[var6]);
                }

                var4.theory.use_front_end = false;
                var4.setVisible(false);
                System.out.println("Playing macro: " + var10);
                var4.theory.loadMacro(var10);
                var4.theory.playMacro();
                System.out.println("done macro");
            }

            return var4;
        }
    }
}
