package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.Serializable;

/** A class for the theory formation thread.
 *
 * @author Simon Colton, started 13th December 1999
 * @version 1.0 */

public class TheoryFormationThread extends Thread implements Serializable
{
    public int number_required = 0;
    public String thing_required = "";
    public Theory theory = null;
    public boolean thread_has_stopped = false;

    public TheoryFormationThread()
    {
    }

    public TheoryFormationThread(int number_reqd, String thing_reqd, Theory theory_in)
    {
        number_required = number_reqd;
        thing_required = thing_reqd;
        theory = theory_in;
    }

    public void run()
    {
        thread_has_stopped = false;
        boolean has_achieved = false;
        while (!theory.stop_now && !has_achieved)
        {
            theory.addToTimer("0.0 Checking for completion of run");

            Step theory_formation_step = theory.theoryFormationStep();
            //System.out.println("step.result_of_step.writeTheoryConstituent() is " + theory_formation_step.result_of_step.writeTheoryConstituent());

            if (theory_formation_step.result_of_step_explanation.equals("exhausted search"))
                has_achieved = true;

            int trial_number = 0;

            if (thing_required.equals("categorisations"))
                trial_number = (new Integer(theory.learned_concepts.size())).intValue();

            if (thing_required.equals("steps"))
                trial_number = (new Double(theory.steps_taken)).intValue();

            if (thing_required.equals("learned concepts"))
                trial_number = (new Double(theory.learned_concepts.size())).intValue();

            if (thing_required.equals("concepts"))
                trial_number = (new Integer(theory.concepts.size())).intValue();

            if (thing_required.equals("conjectures"))
                trial_number = (new Integer(theory.equivalences.size())).intValue();

            if (thing_required.equals("seconds"))
                trial_number = (new Long(theory.seconds_elapsed)).intValue();

            if (thing_required.equals("pis"))
                trial_number = (new Long(theory.prime_implicates.size())).intValue();

            if (trial_number >= number_required)
                has_achieved = true;
        }

        theory.addToTimer("Dummy");

        if (theory.stop_now)
            theory.status("Waiting");

        if (has_achieved)
        {
            System.out.println("Done the theory formation");
            System.out.println("Here are my concepts");
            for(int i=0;i<theory.concepts.size();i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                System.out.println(concept.id + ": " + concept.writeDefinition());
            }
            theory.status("Finished");
        }

        thread_has_stopped = true;

        if (theory.macro_to_complete.equals(""))
        {
            theory.front_end.stop_button.setLabel("Stop");
            theory.front_end.stop_button.setEnabled(false);
            theory.front_end.start_button.setLabel("Continue");
            theory.front_end.start_button.setEnabled(true);
            theory.front_end.step_button.setEnabled(true);
        }
        else
            theory.playMacro();
    }
}
