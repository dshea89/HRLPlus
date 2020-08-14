package com.github.dshea89.hrlplus;

import java.awt.*;

/** A class for changing screens, because Linux is a poxy operating system.
 *
 * @author Simon Colton, started 7th July 2003
 * @version 1.0 */

public class ScreenButtons extends Panel
{
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
    public Button lakatos_methods_screen_button = new Button("Methods");//added
    public Button learn_screen_button = new Button("Learn");
    public Button predict_screen_button = new Button("Predict");
    public Button systemout_screen_button = new Button("Systemout");
    public Button pseudocode_screen_button = new Button("Pseudocode");
    public Button topics_screen_button = new Button("Topics");
    public Button about_screen_button = new Button("About");
    public Button statistics_screen_button = new Button("Statistics");

    public ScreenButtons()
    {
        setLayout(new GridLayout(10,2));
        add(concept_screen_button);
        add(conjecture_screen_button);
        add(entities_screen_button);
        add(macro_screen_button);
        add(domain_screen_button);
        add(search_screen_button);
        add(report_screen_button);
        add(force_screen_button);
        add(agenda_screen_button);
        add(statistics_screen_button);
        add(proof_screen_button);
        add(store_screen_button);
        add(interface_screen_button);
        add(react_screen_button);
        add(lakatos_screen_button); //added
        add(lakatos_methods_screen_button);
        add(learn_screen_button);
        add(predict_screen_button);
        add(systemout_screen_button);
        add(topics_screen_button);
        add(about_screen_button);
        setSize(150,300);
        setVisible(true);
    }
}
