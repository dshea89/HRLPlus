package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.TextField;
import java.io.Serializable;
import java.util.Vector;

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

    public SequenceCalculationThread(int var1, int var2, Concept var3, TextField var4, Button var5, Vector var6) {
        this.bottom_limit = var1;
        this.top_limit = var2;
        this.concept = var3;
        this.calculate_output_text = var4;
        this.calculate_button = var5;
        this.concepts = var6;
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
