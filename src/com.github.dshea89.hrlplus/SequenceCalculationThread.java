package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.*;
import java.io.Serializable;

/** A class for extending a sequence of objects of interest
 *
 * @author Simon Colton, started 8th August 1999
 * @version 1.0 */

public class SequenceCalculationThread extends Thread implements Serializable
{
    public int bottom_limit = 0;
    public int top_limit = 0;
    public boolean must_stop = false;
    public Concept concept = new Concept();
    public TextField calculate_output_text = new TextField();
    public Button calculate_button = new Button();
    public Vector concepts = new Vector();

    public SequenceCalculationThread(){}

    public SequenceCalculationThread(int bottom_int, int top_int,
                                     Concept sequence_concept,
                                     TextField output_text,
                                     Button calc_button,
                                     Vector concepts_in)
    {
        bottom_limit = bottom_int;
        top_limit = top_int;
        concept = sequence_concept;
        calculate_output_text = output_text;
        calculate_button = calc_button;
        concepts = concepts_in;
    }

    public void run()
    {
        int i=bottom_limit;
        Row row = new Row();

        while (i<=top_limit && !row.is_void && !must_stop)
        {
            String entity = Integer.toString(i);
            row = concept.calculateRow(concepts, entity);
            if (!calculate_output_text.getText().equals("") &&
                    concept.arity==1 && row.tuples.size()>0)
                calculate_output_text.setText(calculate_output_text.getText() + ", ");
            if (concept.arity==1 && row.tuples.size() > 0)
                calculate_output_text.setText(calculate_output_text.getText() + row.entity);
            if (concept.arity==2)
            {
                for (int j=0;j<row.tuples.size();j++)
                {
                    if (!calculate_output_text.getText().equals(""))
                        calculate_output_text.setText(calculate_output_text.getText() + ", ");
                    Vector tuple = (Vector)row.tuples.elementAt(j);
                    calculate_output_text.setText(calculate_output_text.getText() +
                            (String)tuple.elementAt(0));
                }
            }
            calculate_button.setLabel(Integer.toString(i) + " STOP");
            i++;
            calculate_output_text.setCaretPosition(calculate_output_text.getText().length());
        }
        if (row.is_void) calculate_output_text.setText(calculate_output_text.getText()+",.....?");
        calculate_output_text.setCaretPosition(0);
        calculate_button.setLabel("Calculate");
    }
}
