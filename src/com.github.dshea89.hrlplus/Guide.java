package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;


/** A class for enabling the user to guide the theory formation process.
 *
 * @author Simon Colton, started 7th December 2001
 * @version 1.0 */

public class Guide implements Serializable
{
    /** Given a vector of theory formation step strings, this will carry them out.
     * It returns the result of the last step.
     */

    public TheoryConstituent forceSteps(Vector step_strings, Theory theory)
    {
        System.out.println("doing forceSteps(" + step_strings + ")");
        TheoryConstituent obj = new TheoryConstituent();
        for (int i=0; i<step_strings.size(); i++)
        {
            String step_string = (String)step_strings.elementAt(i);
            Step step_to_try = theory.agenda.getStepFromString(step_string, theory);
            System.out.println("current step_string is " + step_string);


            //dec 2006

            // System.out.println("DEC 2006: step_to_try.result_of_step_explanation is " + step_to_try.result_of_step_explanation);
// 	System.out.println("step_to_try.writeTheoryConstituent() is "
// 			   + ((TheoryConstituent)step_to_try.result_of_step).writeTheoryConstituent());

// 	if(step_to_try.result_of_step instanceof Conjecture)

// 	  System.out.println("DEC 2006 step_to_try.result_of_step was this conjecture: "
// 			     + ((Conjecture)step_to_try.result_of_step).writeConjecture());


            //needs testing
            try
            {
                Concept concept_produced =
                        (Concept)theory.agenda.repeated_forced_steps.get(step_to_try.asString());
                if (concept_produced==null)
                {
                    System.out.println("concept_produced was null so doing more things...");
                    theory.agenda.steps_to_force.addElement(step_string);
                    theory.agenda.addForcedStep(step_string, true);
                    Step step = theory.theoryFormationStep();
                    obj = step.result_of_step;
                    System.out.println("a - obj is " +obj.writeTheoryConstituent());
                }
                else
                {
                    System.out.println("concept_produced was NOT null");
                    obj = concept_produced;
                    System.out.println("we produced: obj " + ((Concept)obj).writeDefinition());
                }
                System.out.println("down here: in guide........\n\n");
            }
            catch(Exception e)
            {}
        }
        return obj;
    }

    /** This adds a single step to the agenda.
     */

    public void addStep(String resulting_concept_name, String primary_concept,
                        String secondary_concept, String production_rule, String params,
                        Theory theory)
    {
        String step_string = resulting_concept_name + " = " + primary_concept;
        if (!secondary_concept.equals(""))
            step_string = step_string + " " + secondary_concept;
        step_string = step_string + " " + production_rule + " " + params;
        theory.agenda.addForcedStep(step_string, false);
    }

    /** This forces a single step.
     */

    public Step forceStep(String resulting_concept_name, String primary_concept,
                          String secondary_concept, String production_rule, String params,
                          Theory theory)
    {
        //System.out.println("forcing:" + primary_concept + " " + secondary_concept + " " +
        //		 production_rule + " " + params);
        String step_string = resulting_concept_name + " = " + primary_concept;
        if (!secondary_concept.equals(""))
            step_string = step_string + " " + secondary_concept;
        step_string = step_string + " " + production_rule + " " + params;
        Step output = forceStep(step_string, theory);
        return output;
    }

    /** This forces a single step (written as a string).
     */

    public Step forceStep(String step_string, Theory theory)
    {
        //System.out.println("\ndoing forceStep: step_string is " + step_string);
        Step step_to_try = theory.agenda.getStepFromString(step_string, theory);
        // if(!(step_to_try==null))
//       System.out.println("step_to_try is " + step_to_try.asString());
//     else
//       System.out.println("step_to_try is null");
        Step step = new Step();
        //System.out.println("forceStep -- a");
        try
        {
            //System.out.println("forceStep -- b");
            Concept concept_produced =
                    (Concept)(theory.agenda.repeated_forced_steps.get(step_to_try.asString()));
            //System.out.println("forceStep -- c");
            //System.out.println("concept_produced is " + concept_produced);
            if (concept_produced==null)
            {
                //System.out.println("forceStep - 1");
                theory.agenda.steps_to_force.addElement(step_string);
                //System.out.println("forceStep - 2");
                theory.agenda.addForcedStep(step_string, true);
                //System.out.println("forceStep - 3");
                step = theory.theoryFormationStep(); //the main method
                //System.out.println("forceStep - 4\n");
                //System.out.println("step.result_of_step is " + step.result_of_step);

                if(step.result_of_step instanceof Concept)
                {
                    Concept result_concept = (Concept)step.result_of_step;
                    //System.out.println("result_concept is " + result_concept.writeDefinition());
                }

            }
            else
                step.result_of_step_explanation = "Step already carried out";
        }

        catch(Exception e)
        {
            System.out.println("HERE -- Exception is " + e);
            e.printStackTrace(System.out);
        }

        return step;
    }


    /** This finds all parameterisations for a choice of concept and
     * production rule.
     */

    public void loadParameterisations(Theory theory)
    {
        theory.front_end.force_parameter_list.removeAll();
        if (theory.front_end.force_primary_concept_list.getSelectedIndex()<0)
            return;
        Concept primary_concept =
                theory.getConcept(theory.front_end.force_primary_concept_list.getSelectedItem());
        Concept secondary_concept = new Concept();
        if (theory.front_end.force_secondary_concept_list.getSelectedIndex()>=0)
            secondary_concept =
                    theory.getConcept(theory.front_end.force_secondary_concept_list.getSelectedItem());
        Vector concept_vector = new Vector();
        concept_vector.addElement(primary_concept);
        concept_vector.addElement(secondary_concept);
        ProductionRule pr_chosen = null;
        for (int i=0; i<theory.all_production_rules.size() && pr_chosen == null; i++)
        {
            ProductionRule pr = (ProductionRule)theory.all_production_rules.elementAt(i);
            if (pr.getName().equals(theory.front_end.force_prodrule_list.getSelectedItem()))
                pr_chosen = pr;
        }
        if (pr_chosen!=null)
        {
            Vector parameterisations = pr_chosen.allParameters(concept_vector, theory);

            for (int j=0; j<parameterisations.size(); j++)
            {
                Vector parameters = (Vector)parameterisations.elementAt(j);
                String add_line = (new Step()).writeParameters(parameters);
                Concept dummy_concept = new Concept();
                dummy_concept.types = pr_chosen.transformTypes(concept_vector, parameters);
                dummy_concept.specifications = pr_chosen.newSpecifications(concept_vector, parameters, theory, new Vector());
                add_line = add_line + ": " + dummy_concept.writeDefinitionWithStartLetters("ascii");
                theory.front_end.force_parameter_list.addItem(add_line);
            }
            if (parameterisations.isEmpty())
                theory.front_end.force_parameter_list.addItem(pr_chosen.parameter_failure_reason);
        }
    }

    public void writeForceString(Theory theory)
    {
        String primary = theory.front_end.force_primary_concept_list.getSelectedItem();
        String secondary = theory.front_end.force_secondary_concept_list.getSelectedItem();
        String prodrule = theory.front_end.force_prodrule_list.getSelectedItem();
        String params = theory.front_end.force_parameter_list.getSelectedItem();

        if (primary==null)
        {
            theory.front_end.force_string_text.setText("step = ");
            return;
        }
        if (secondary==null)
        {
            if (prodrule!=null)
            {
                if (params==null)
                    theory.front_end.force_string_text.setText("step = " + primary + " " + prodrule);
                else
                {
                    params = params.substring(0, params.indexOf(":"));
                    theory.front_end.force_string_text.setText("step = " + primary + " " + prodrule + " " + params);
                }
            }
            else
                theory.front_end.force_string_text.setText("step = " + primary);
            return;
        }
        if (prodrule==null)
        {
            theory.front_end.force_string_text.setText("step = " + primary + " " + secondary);
            return;
        }
        if (params==null)
        {
            if (secondary==null)
                theory.front_end.force_string_text.setText("step = " + primary + " " + secondary + " " + prodrule);
            return;
        }
        params = params.substring(0, params.indexOf(":"));
        theory.front_end.force_string_text.setText("step = " + primary + " " + secondary + " " + prodrule + " " + params);
        return;
    }

    public void addCounterexampleBarringSteps(Theory theory, Concept lh_concept,
                                              Vector counterexs, String identifier)
    {
        String final_concept_string = "";
        Vector splits = new Vector();
        for (int j=0; j<counterexs.size(); j++)
        {
            theory.agenda.addForcedStep("split" + identifier + "_" + Integer.toString(j) + " = " +
                    lh_concept.entity_concept.id + " split [[0], [" +
                    (String)counterexs.elementAt(j)+"]] : dont_develop", false);

            splits.addElement("split" + identifier + "_" + Integer.toString(j));
        }
        int counter = 0;
        if (splits.size() >= 2)
        {
            theory.agenda.addForcedStep("disj" + identifier + "_1 = " + "split" + identifier +
                    "_0 split" + identifier + "_1 disjunct [] : dont_develop", false);
            final_concept_string = "disj" + identifier + "_1";
            for (int j=2; j<splits.size(); j++)
            {
                theory.agenda.addForcedStep("disj" + identifier + "_" + Integer.toString(j) +
                        " = disj" + identifier + "_"+Integer.toString(j-1)
                        + " " + (String)splits.elementAt(j) +
                        " disjunct [] : dont_develop", false);
                final_concept_string = "disj" + identifier + "_" + Integer.toString(j);
            }
        }
        else
            final_concept_string="split" + identifier + "_0";
        theory.agenda.addForcedStep("negate_" + identifier + " = " +
                final_concept_string +
                " " + lh_concept.entity_concept.id +
                " negate [] : dont_develop", false);

        String permutation = "[";
        for (int j=0; j<lh_concept.arity-1; j++)
            permutation = permutation + Integer.toString(j+1) + ", ";
        permutation = permutation + Integer.toString(lh_concept.arity) + "]";
        theory.agenda.addForcedStep("compose_" + identifier + " = " + "negate_"
                + identifier + " " + lh_concept.id + " compose " +
                permutation + " : dont_develop", false);
    }
}
