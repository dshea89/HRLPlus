package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;

public class ScreenButtons extends Panel {
    public FrontEnd front_end = new FrontEnd();
    public Button concept_screen_button = new Button("Concepts");
    public Button conjecture_screen_button = new Button("Conjectures");
    public Button entities_screen_button = new Button("Entities");
    public Button macro_screen_button = new Button("Macro");
    public Button domain_screen_button = new Button("Domain");
    public Button search_screen_button = new Button("Search");
    public Button report_screen_button = new Button("Report");
    public Button force_screen_button = new Button("Force");
    public Button agenda_screen_button = new Button("Agenda");
    public Button proof_screen_button = new Button("Proof");
    public Button store_screen_button = new Button("Store");
    public Button interface_screen_button = new Button("Interface");
    public Button react_screen_button = new Button("React");
    public Button lakatos_screen_button = new Button("Lakatos");
    public Button lakatos_methods_screen_button = new Button("Methods");
    public Button learn_screen_button = new Button("Learn");
    public Button predict_screen_button = new Button("Predict");
    public Button systemout_screen_button = new Button("Systemout");
    public Button pseudocode_screen_button = new Button("Pseudocode");
    public Button topics_screen_button = new Button("Topics");
    public Button about_screen_button = new Button("About");
    public Button statistics_screen_button = new Button("Statistics");

    public ScreenButtons() {
        this.setLayout(new GridLayout(10, 2));
        this.add(this.concept_screen_button);
        this.add(this.conjecture_screen_button);
        this.add(this.entities_screen_button);
        this.add(this.macro_screen_button);
        this.add(this.domain_screen_button);
        this.add(this.search_screen_button);
        this.add(this.report_screen_button);
        this.add(this.force_screen_button);
        this.add(this.agenda_screen_button);
        this.add(this.statistics_screen_button);
        this.add(this.proof_screen_button);
        this.add(this.store_screen_button);
        this.add(this.interface_screen_button);
        this.add(this.react_screen_button);
        this.add(this.lakatos_screen_button);
        this.add(this.lakatos_methods_screen_button);
        this.add(this.learn_screen_button);
        this.add(this.predict_screen_button);
        this.add(this.systemout_screen_button);
        this.add(this.topics_screen_button);
        this.add(this.about_screen_button);
        this.setSize(150, 300);
        this.setVisible(true);
    }
}
