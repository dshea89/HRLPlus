package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.TextField;
import java.io.Serializable;
import java.util.Vector;

/**
 * A class for extending a sequence of objects of interest
 */
public class SequenceCalculationThread extends Thread implements Serializable {
    public int bottom_limit = 0;
    public int top_limit = 0;
    public boolean must_stop = false;
    public Concept concept = new Concept();
    public TextField calculate_output_text = new TextField();
    public Button calculate_button = new Button();
    public Vector concepts = new Vector();

    public SequenceCalculationThread() {
    }

    public SequenceCalculationThread(int bottom_int, int top_int, Concept sequence_concept, TextField output_text,
                                     Button calc_button, Vector concepts_in) {
        this.bottom_limit = bottom_int;
        this.top_limit = top_int;
        this.concept = sequence_concept;
        this.calculate_output_text = output_text;
        this.calculate_button = calc_button;
        this.concepts = concepts_in;
    }

    public void run() {
        int var1 = this.bottom_limit;
        Row var2 = new Row();

        while(var1 <= this.top_limit && !var2.is_void && !this.must_stop) {
            String var3 = Integer.toString(var1);
            var2 = this.concept.calculateRow(this.concepts, var3);
            if (!this.calculate_output_text.getText().equals("") && this.concept.arity == 1 && var2.tuples.size() > 0) {
                this.calculate_output_text.setText(this.calculate_output_text.getText() + ", ");
            }

            if (this.concept.arity == 1 && var2.tuples.size() > 0) {
                this.calculate_output_text.setText(this.calculate_output_text.getText() + var2.entity);
            }

            if (this.concept.arity == 2) {
                for(int var4 = 0; var4 < var2.tuples.size(); ++var4) {
                    if (!this.calculate_output_text.getText().equals("")) {
                        this.calculate_output_text.setText(this.calculate_output_text.getText() + ", ");
                    }

                    Vector var5 = (Vector)var2.tuples.elementAt(var4);
                    this.calculate_output_text.setText(this.calculate_output_text.getText() + (String)var5.elementAt(0));
                }
            }

            this.calculate_button.setLabel(Integer.toString(var1) + " STOP");
            ++var1;
            this.calculate_output_text.setCaretPosition(this.calculate_output_text.getText().length());
        }

        if (var2.is_void) {
            this.calculate_output_text.setText(this.calculate_output_text.getText() + ",.....?");
        }

        this.calculate_output_text.setCaretPosition(0);
        this.calculate_button.setLabel("Calculate");
    }
}
