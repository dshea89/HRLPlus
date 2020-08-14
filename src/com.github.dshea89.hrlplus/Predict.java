package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.*;
import java.io.*;

/** A class for predicting values for objects supplied by the user.
 *
 * @author Simon Colton, started 6th September 2001
 * @version 1.0 */

public class Predict implements Serializable
{
    /** The objects of interest which have not been correctly classified (from
     * those supplied to the theory formation process).
     */

    public Vector given_outliers = new Vector();

    /** The objects of interest which have not been correctly classified (from
     * those not supplied to the theory formation process).
     */

    public Vector not_given_outliers = new Vector();

    /** The hashtable of prediction scores, with the concept to be predicted
     * as the key.
     */

    public Hashtable prediction_scores = new Hashtable();

    /** Whether or not to use equivalent definitions for concepts.
     */

    public boolean use_equivalences = true;

    /** Whether or not to also calculate P(X|-(f(a_1,...,a_n))).
     */

    public boolean use_negation = false;

    /** The HR applet (so that the title can be set to keep the user updated about
     * multiple predictions).
     */

    public Frame main_frame = new Frame();

    /** The vector of results from predict all for different step numbers.
     */

    public Vector step_results = new Vector();

    /** The average of the ranges between most likely predicted and least
     * likely predicted values
     */

    public double average_of_ranges = 0;

    /** Whether or not multiple predictions are being made
     */

    public boolean making_multiple_predictions = false;

    /** The relations read in from the file.
     */

    Vector relations = new Vector();

    /** The number of concepts which were around when the last set of near conjectures
     * were calculated.
     */

    int previous_number_of_concepts = -1;

    /** Which concepts have had their near conjectures calculated.
     */

    Vector already_have_near_conjectures = new Vector();


    /** How many predictions have been made (used as a counter).
     */

    public int trial_number = 0;

    /** The set of concepts read in from a file which will supply some of the information
     * about the objects (with the rest to be predicted)
     */

    public Vector read_in_concepts = new Vector();

    public void Predict()
    {
    }

    /** This is the top level for performing the predictions.
     */

    public Vector makePredictions(String method_name, String entity_name, Vector names, Vector values,
                                  double min_percent, String entity_type, Vector concepts,
                                  Vector names_buttons, Vector values_texts,
                                  List explanation_list, int max_steps)
    {
        Vector output = new Vector();
        average_of_ranges = 0;

        // Remove additional rows //

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            concept.additional_datatable.removeRow("px");
            for (int j=0; j<concept.conjectured_equivalent_concepts.size(); j++)
            {
                Concept conjectured_equivalent_concept =
                        (Concept)concept.conjectured_equivalent_concepts.elementAt(j);
                conjectured_equivalent_concept.additional_datatable.removeRow("px");
            }
        }

        if (!making_multiple_predictions)
            explanation_list.removeAll();

        Vector concepts_to_predict = new Vector();
        Vector textfields_to_fill = new Vector();

        // Get the concepts to predict //

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.is_user_given)
            {
                for (int j=0; j<names.size(); j++)
                {
                    String value = (String)values.elementAt(j);
                    String concept_name = (String)names.elementAt(j);
                    if (concept_name.equals(concept.name) && value.equals(""))
                    {
                        concepts_to_predict.addElement(concept);
                        textfields_to_fill.addElement(values_texts.elementAt(j));
                    }
                }
            }
        }

        // Update the user given concepts //

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.is_user_given)
            {
                for (int j=0; j<names.size(); j++)
                {
                    String concept_name = (String)names.elementAt(j);
                    String value = (String)values.elementAt(j);
                    if (concept_name.equals(concept.name) && !value.equals(""))
                        concept.addAdditionalRow("px", value);
                }
            }
        }

        for (int i=0; i<concepts_to_predict.size(); i++)
        {
            Tuples scores = new Tuples();
            Concept predict_concept = (Concept)concepts_to_predict.elementAt(i);
            if (!making_multiple_predictions)
                explanation_list.addItem("Predicting for "+predict_concept.name);

            // At present, only works for the arity 1 and 2 cases //

            Vector possible_values = new Vector();

            Concept type_concept=null;
            if (predict_concept.arity==2)
            {
                for (int j=0; j<concepts.size() && type_concept==null; j++)
                {
                    Concept tc = (Concept)concepts.elementAt(j);
                    if (tc.name.equals(predict_concept.types.elementAt(predict_concept.arity-1)))
                        type_concept = tc;
                }
                for (int j=0; j<type_concept.datatable.size(); j++)
                    possible_values.addElement(((Row)type_concept.datatable.elementAt(j)).entity);
            }

            if (predict_concept.arity==1)
            {
                possible_values.removeAllElements();
                possible_values.addElement("yes");
                possible_values.addElement("no");
            }

            // Find the prediction concepts //

            Vector positive_concepts_for_predicting = new Vector();
            Vector negative_concepts_for_predicting = new Vector();
            for (int j=0; j<concepts.size(); j++)
            {
                Concept concept = (Concept)concepts.elementAt(j);
                if (!(concept==predict_concept) && concept.arity==1 &&
                        ((String)concept.types.elementAt(0)).equals(predict_concept.types.elementAt(0)))
                {
                    boolean contains_bad_ancestor = false;
                    for (int k=0; k<concepts_to_predict.size() && !contains_bad_ancestor; k++)
                        if (concept.ancestor_ids.contains(((Concept)concepts_to_predict.elementAt(k)).id))
                            contains_bad_ancestor = true;
                    if (!contains_bad_ancestor && concept.calculateRow(concepts, "px").tuples.size()==1 &&
                            (concept.step_number <= max_steps || max_steps < 0))
                        positive_concepts_for_predicting.addElement(concept);
                    if (!contains_bad_ancestor && concept.calculateRow(concepts, "px").tuples.size()==0 &&
                            (concept.step_number <= max_steps || max_steps < 0))
                        negative_concepts_for_predicting.addElement(concept);
                    if (use_equivalences)
                    {
                        for (int k=0; k<concept.conjectured_equivalent_concepts.size(); k++)
                        {
                            Concept conjectured_equivalent_concept =
                                    (Concept)concept.conjectured_equivalent_concepts.elementAt(k);
                            contains_bad_ancestor = false;
                            for (int l=0; l<concepts_to_predict.size() && !contains_bad_ancestor; l++)
                                if (conjectured_equivalent_concept.ancestor_ids.contains(
                                        ((Concept)concepts_to_predict.elementAt(l)).id))
                                    contains_bad_ancestor = true;
                            if (conjectured_equivalent_concept.calculateRow(concepts, "px").tuples.size()==1 &&
                                    !contains_bad_ancestor &&
                                    (conjectured_equivalent_concept.step_number <= max_steps || max_steps < 0))
                            {
                                positive_concepts_for_predicting.addElement(conjectured_equivalent_concept);
                                if (concept.id.equals("a14"))
                                    conjectured_equivalent_concept.id="lookie";
                            }
                            if (conjectured_equivalent_concept.calculateRow(concepts, "px").tuples.size()==0 &&
                                    !contains_bad_ancestor &&
                                    (conjectured_equivalent_concept.step_number <= max_steps || max_steps < 0))
                                negative_concepts_for_predicting.addElement(conjectured_equivalent_concept);
                        }
                    }
                }
            }

            Vector final_scores = new Vector();
            for (int j=0; j<possible_values.size(); j++)
            {
                Vector positive_objects = new Vector();
                String possible_value = (String)possible_values.elementAt(j);
                for (int k=0; k<predict_concept.datatable.size(); k++)
                {
                    Row row = (Row)predict_concept.datatable.elementAt(k);
                    if (!row.entity.equals(entity_name))
                    {
                        if (predict_concept.arity==1 && possible_value.equals("yes") && !row.tuples.isEmpty())
                            positive_objects.addElement(row.entity);
                        if (predict_concept.arity==1 && possible_value.equals("no") && row.tuples.isEmpty())
                            positive_objects.addElement(row.entity);
                        if (predict_concept.arity==2)
                        {
                            if (row.tuples.toString().indexOf("["+possible_value+"]")>=0)
                                positive_objects.addElement(row.entity);
                        }
                    }
                }

                double final_score = 0;
                double concepts_not_used = 0;

                for (int k=0; k<positive_concepts_for_predicting.size(); k++)
                {
                    double num_predict_pos = 0;
                    Concept positive_prediction_concept = (Concept)positive_concepts_for_predicting.elementAt(k);
                    for (int l=0; l<positive_prediction_concept.datatable.size(); l++)
                    {
                        Row row = (Row)positive_prediction_concept.datatable.elementAt(l);
                        if (!row.tuples.isEmpty() && !row.entity.equals(entity_name))
                            num_predict_pos++;
                    }

                    // It could be that the only entity with this property is the one we're predicting for //

                    if (num_predict_pos > 0)
                    {
                        double score = 0;
                        for (int l=0; l<positive_objects.size(); l++)
                        {
                            String entity = (String)positive_objects.elementAt(l);
                            if (!(positive_prediction_concept.datatable.rowWithEntity(entity)).tuples.isEmpty())
                                score++;
                        }
                        double pcent = score/num_predict_pos;
                        if (!making_multiple_predictions && (pcent>=min_percent || pcent<=(1-min_percent)))
                        {
                            Vector letters =
                                    predict_concept.definition_writer.lettersForTypes(predict_concept.types,
                                            "prolog", new Vector());
                            String letter = (String)letters.elementAt(0)+",";
                            explanation_list.addItem(positive_prediction_concept.id+". P("+
                                    predict_concept.name+"("+letter+possible_value+") | "+
                                    positive_prediction_concept.writeDefinition("prolog")+") = "+
                                    Double.toString(pcent));
                        }

                        // Add the final score on only if it is above the minimum percent //

                        if (pcent>=min_percent || pcent <= (1-min_percent))
                            final_score = final_score + (score/num_predict_pos);
                        else
                            concepts_not_used++;
                    }
                    else
                        concepts_not_used++;
                }

                if (use_negation)
                {
                    for (int k=0; k<negative_concepts_for_predicting.size(); k++)
                    {
                        double num_predict_pos = 0;
                        Concept negative_prediction_concept = (Concept)negative_concepts_for_predicting.elementAt(k);
                        for (int l=0; l<negative_prediction_concept.datatable.size(); l++)
                        {
                            Row row = (Row)negative_prediction_concept.datatable.elementAt(l);
                            if (row.tuples.isEmpty() &&!row.entity.equals(entity_name))
                                num_predict_pos++;
                        }
                        if (num_predict_pos > 0)
                        {
                            double score = 0;
                            for (int l=0; l<positive_objects.size(); l++)
                            {
                                String entity = (String)positive_objects.elementAt(l);
                                if (negative_prediction_concept.datatable.rowWithEntity(entity).tuples.isEmpty())
                                    score++;
                            }
                            double pcent = score/num_predict_pos;
                            if (!making_multiple_predictions && (pcent>=min_percent || pcent<=(1-min_percent)))
                            {
                                Vector letters =
                                        predict_concept.definition_writer.lettersForTypes(predict_concept.types,
                                                "prolog", new Vector());
                                String letter = (String)letters.elementAt(0)+",";
                                explanation_list.addItem(negative_prediction_concept.id+".P("+
                                        predict_concept.name+"("+letter+possible_value+") | -("+
                                        negative_prediction_concept.writeDefinition("prolog")+")) = "+
                                        Double.toString(pcent));
                            }

                            // Add the final score on only if it is above the minimum percent //

                            if (pcent>=min_percent || pcent <= (1-min_percent))
                                final_score = final_score + (score/num_predict_pos);
                            else
                                concepts_not_used++;
                        }
                        else
                            concepts_not_used++;
                    }
                }

                if (use_negation)
                    final_score = final_score/(positive_concepts_for_predicting.size()+
                            negative_concepts_for_predicting.size()-concepts_not_used);
                else
                    final_score = final_score/(positive_concepts_for_predicting.size()-concepts_not_used);

                final_scores.addElement(new Double(final_score));
            }
            double worst_score = 100;
            double best_score = 0;
            String best_value = "";
            if (!making_multiple_predictions)
                explanation_list.addItem(predict_concept.name,0);
            int j=0;
            for (j=0; j<final_scores.size(); j++)
            {
                Double final_score = (Double)final_scores.elementAt(j);
                if (final_score.doubleValue() > best_score)
                {
                    best_score = final_score.doubleValue();
                    best_value = (String)possible_values.elementAt(j);
                }
                if (final_score.doubleValue() < worst_score)
                    worst_score = final_score.doubleValue();
            }
            if (making_multiple_predictions)
                explanation_list.addItem(best_value);

            for (j=0; j<final_scores.size(); j++)
            {
                Double final_score = (Double)final_scores.elementAt(j);
                if (making_multiple_predictions)
                    explanation_list.addItem((String)possible_values.elementAt(j)+"  ["+final_score.toString()+"]");
                else
                    explanation_list.addItem((String)possible_values.elementAt(j)+"  ["+final_score.toString()+"]",1+j);
            }
            if (making_multiple_predictions)
                explanation_list.addItem("------------------------------------");
            else
            {
                explanation_list.addItem("------------------------------------",1+j);
                ((TextField)textfields_to_fill.elementAt(i)).setText(best_value);
            }
            output.addElement(best_value);
            average_of_ranges = average_of_ranges + (best_score - worst_score);
        }
        double num_conc = (new Double(concepts_to_predict.size())).doubleValue();
        average_of_ranges = average_of_ranges/num_conc;
        return output;
    }

    public void makeAllPredictions(String method_name, double min_percent, String entity_type, Vector concepts,
                                   Vector names_buttons, Vector values_texts,
                                   List explanation_list, int max_steps, boolean display_in_box)
    {
        Vector names = getNames(names_buttons);
        Vector values = getValues(values_texts, names_buttons);

        given_outliers = new Vector();
        not_given_outliers = new Vector();
        Vector concepts_to_predict = new Vector();
        Vector concepts_to_clear_values_for = new Vector();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.is_user_given)
            {
                for (int j=0; j<names.size(); j++)
                {
                    String value = (String)values.elementAt(j);
                    String concept_name = (String)names.elementAt(j);
                    if (concept_name.equals(concept.name) && value.equals(""))
                    {
                        concepts_to_predict.addElement(concept);
                        concepts_to_clear_values_for.addElement(Integer.toString(j));
                    }
                }
            }
        }

        Concept given_entity_concept = new Concept();
        if (!concepts_to_predict.isEmpty())
            given_entity_concept = (Concept)concepts_to_predict.elementAt(0);

        double total_average_of_ranges = 0;
        explanation_list.removeAll();
        making_multiple_predictions = true;

        double[] not_given_scores = new double[concepts_to_predict.size()];
        double[] given_scores = new double[concepts_to_predict.size()];
        double given_average_of_ranges = 0;
        double not_given_average_of_ranges = 0;

        for (int i=0; i<concepts_to_predict.size(); i++)
        {
            not_given_scores[i] = 0;
            given_scores[i] = 0;
        }

        Vector entities_to_predict_for = new Vector();
        for (int i=0; i<read_in_concepts.size(); i++)
        {
            Concept concept = (Concept)read_in_concepts.elementAt(i);
            if (concept.object_type.equals(entity_type) &&
                    concept.is_object_of_interest_concept)
            {
                for (int j=0; j<concept.datatable.size(); j++)
                {
                    Row row = (Row)concept.datatable.elementAt(j);
                    entities_to_predict_for.addElement(row.entity);
                }
            }
        }

        explanation_list.removeAll();
        double num_given_entities = 0;
        int total_given_correct = 0;
        int total_not_given_correct = 0;

        for (int i=0; i<entities_to_predict_for.size(); i++)
        {
            values = new Vector();
            String entity_to_predict_for = (String)entities_to_predict_for.elementAt(i);
            explanation_list.addItem(entity_to_predict_for);
            Vector should_be_values = new Vector();
            for (int j=0; j<read_in_concepts.size(); j++)
            {
                Concept concept = (Concept)read_in_concepts.elementAt(j);
                if (concept.object_type.equals(entity_type))
                {
                    Vector tuples = concept.datatable.rowWithEntity(entity_to_predict_for).tuples;
                    for (int k=0; k<names.size(); k++)
                    {
                        String concept_name = (String)names.elementAt(k);
                        if (concept_name.equals(concept.name))
                        {
                            if (!concepts_to_clear_values_for.contains(Integer.toString(k)))
                                values.addElement(tuples.toString());
                            else
                            {
                                values.addElement("");
                                String should_be_value = tuples.toString();
                                if (should_be_value.equals("[]"))
                                    should_be_value="no";
                                if (should_be_value.equals("[[]]"))
                                    should_be_value="yes";
                                if (!should_be_value.equals("yes") && !should_be_value.equals("no"))
                                    should_be_value = should_be_value.substring(2,should_be_value.length()-2);
                                should_be_values.addElement(should_be_value);
                            }
                        }
                    }
                }
            }

            Vector predictions =
                    makePredictions(method_name, entity_to_predict_for, names, values, min_percent, entity_type,
                            concepts, names_buttons, values_texts, explanation_list, max_steps);

            boolean is_given_entity = false;
            if (given_entity_concept.datatable.hasEntity(entity_to_predict_for))
            {
                is_given_entity = true;
                num_given_entities++;
            }

            if (is_given_entity)
                given_average_of_ranges = given_average_of_ranges + average_of_ranges;
            else
                not_given_average_of_ranges = not_given_average_of_ranges + average_of_ranges;

            for (int j=0; j<predictions.size(); j++)
            {
                String predicted_value = (String)predictions.elementAt(j);
                if (predicted_value.equals((String)should_be_values.elementAt(j)))
                {
                    if (is_given_entity)
                    {
                        given_scores[j]++;
                        total_given_correct++;
                    }
                    else
                    {
                        not_given_scores[j]++;
                        total_not_given_correct++;
                    }
                }
                else
                {
                    if (is_given_entity)
                        given_outliers.addElement(entity_to_predict_for);
                    else
                        not_given_outliers.addElement(entity_to_predict_for);
                }
            }
            main_frame.setTitle(Integer.toString(i+1)+"/"+Integer.toString(entities_to_predict_for.size())+"["+
                    Integer.toString(total_not_given_correct)+","+Integer.toString(not_given_outliers.size())+"]["+
                    Integer.toString(total_given_correct)+","+Integer.toString(given_outliers.size())+"]");
        }

        for (int i=0; i<concepts_to_predict.size(); i++)
        {
            String p = (String)concepts_to_clear_values_for.elementAt(i);
            int pos = (new Integer(p)).intValue();
            TextField tf = (TextField)values_texts.elementAt(pos);
            Double s = new Double(entities_to_predict_for.size()-num_given_entities);
            double not_given_percent = not_given_scores[i]/s.doubleValue();
            double given_percent = given_scores[i]/num_given_entities;
            String given_percent_string = Double.toString(given_percent*100);
            String not_given_percent_string = Double.toString(not_given_percent*100);
            if (given_percent_string.length()>=5)
                given_percent_string = given_percent_string.substring(0,5);
            if (not_given_percent_string.length()>=5)
                not_given_percent_string = not_given_percent_string.substring(0,5);
            String given_range_average_string = Double.toString(given_average_of_ranges);
            String not_given_range_average_string = Double.toString(not_given_average_of_ranges);
            if (given_range_average_string.length()>=5)
                given_range_average_string = given_range_average_string.substring(0,5);
            if (not_given_range_average_string.length()>=5)
                not_given_range_average_string = not_given_range_average_string.substring(0,5);
            if (display_in_box)
                tf.setText(not_given_percent_string + "%, " + given_percent_string+"%, "+
                        not_given_range_average_string+", "+given_range_average_string);
            else
                tf.setText("");
            Concept concept_to_predict = ((Concept)concepts_to_predict.elementAt(i));
            explanation_list.addItem("Prediction for " + concept_to_predict.id + "(" + concept_to_predict.name +")");
            explanation_list.addItem("Prediction accuracy for those not supplied: " + not_given_percent_string + "%", 1);
            explanation_list.addItem("Prediction accuracy for those supplied: " + given_percent_string+"%", 2);
            Hashtable results = new Hashtable();
            results.put("concept.id", concept_to_predict.id);
            results.put("concept.name", concept_to_predict.name);
            results.put("steps for concepts", new Integer(max_steps));
            results.put("percent correct for given", given_percent_string);
            results.put("percent correct for not given", not_given_percent_string);
            results.put("given outliers", given_outliers);
            results.put("not given outliers", not_given_outliers);
            step_results.addElement(results);
            System.out.println("steps: " + Integer.toString(max_steps) + " = " + given_percent_string + " " + not_given_percent_string);
        }
        making_multiple_predictions = false;
        explanation_list.addItem("given outliers:"+given_outliers.toString(),0);
        explanation_list.addItem("not given outliers:"+not_given_outliers.toString(),0);
    }


    public void makeIncrementalPredictions(String method_name, double min_percent, String entity_type, Vector concepts,
                                           Vector names_buttons, Vector values_texts,
                                           List explanation_list, int increment_steps,
                                           int total_steps)
    {
        step_results.clear();
        for (int i=0; i<=total_steps; i=i+increment_steps)
        {
            makeAllPredictions(method_name, min_percent, entity_type, concepts,
                    names_buttons, values_texts, explanation_list, i, false);
        }
        makeAllPredictions(method_name, min_percent, entity_type, concepts,
                names_buttons, values_texts, explanation_list, total_steps, false);

    }

    /** Extracts the labels on a vector of buttons.
     */

    public Vector getNames(Vector buttons)
    {
        Vector output = new Vector();
        for (int i=0; i<buttons.size(); i++)
        {
            Button button = (Button)buttons.elementAt(i);
            if (!(button.getLabel().equals("")))
                output.addElement(button.getLabel());
        }
        return output;
    }

    /** Extracts the texts in a vector of textfields.
     */

    public Vector getValues(Vector textfields, Vector buttons)
    {
        Vector output = new Vector();
        for (int i=0; i<textfields.size(); i++)
        {
            Button button = (Button)buttons.elementAt(i);
            TextField textfield = (TextField)textfields.elementAt(i);
            if (!(button.getLabel().equals("")))
                output.addElement(textfield.getText());
        }
        return output;
    }
}
