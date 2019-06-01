package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class TheoryFormationThread extends Thread implements Serializable {
    public int number_required = 0;
    public String thing_required = "";
    public Theory theory = null;
    public boolean thread_has_stopped = false;

    public TheoryFormationThread() {
    }

    public TheoryFormationThread(int var1, String var2, Theory var3) {
        this.number_required = var1;
        this.thing_required = var2;
        this.theory = var3;
    }

    public void run() {
        this.thread_has_stopped = false;
        boolean var1 = false;

        while(!this.theory.stop_now && !var1) {
            this.theory.addToTimer("0.0 Checking for completion of run");
            Step var2 = this.theory.theoryFormationStep();
            if (var2.result_of_step_explanation.equals("exhausted search")) {
                var1 = true;
            }

            int var3 = 0;
            if (this.thing_required.equals("categorisations")) {
                var3 = new Integer(this.theory.learned_concepts.size());
            }

            if (this.thing_required.equals("steps")) {
                var3 = (new Double((double)this.theory.steps_taken)).intValue();
            }

            if (this.thing_required.equals("learned concepts")) {
                var3 = (new Double((double)this.theory.learned_concepts.size())).intValue();
            }

            if (this.thing_required.equals("concepts")) {
                var3 = new Integer(this.theory.concepts.size());
            }

            if (this.thing_required.equals("conjectures")) {
                var3 = new Integer(this.theory.equivalences.size());
            }

            if (this.thing_required.equals("seconds")) {
                var3 = (new Long((long)this.theory.seconds_elapsed)).intValue();
            }

            if (this.thing_required.equals("pis")) {
                var3 = (new Long((long)this.theory.prime_implicates.size())).intValue();
            }

            if (this.number_required > 0 && var3 >= this.number_required) {
                var1 = true;
            }
        }

        this.theory.addToTimer("Dummy");
        if (this.theory.stop_now) {
            this.theory.status("Waiting");
        }

        if (var1) {
            System.out.println("Done the theory formation");
            System.out.println("Here are my concepts");

            for(int var4 = 0; var4 < this.theory.concepts.size(); ++var4) {
                Concept var5 = (Concept)this.theory.concepts.elementAt(var4);
                System.out.println(var5.id + ": " + var5.writeDefinition());
            }

            this.theory.status("Finished");
        }

        this.thread_has_stopped = true;
        if (this.theory.macro_to_complete.equals("")) {
            this.theory.front_end.stop_button.setLabel("Stop");
            this.theory.front_end.stop_button.setEnabled(false);
            this.theory.front_end.start_button.setLabel("Continue");
            this.theory.front_end.start_button.setEnabled(true);
            this.theory.front_end.step_button.setEnabled(true);
        } else {
            this.theory.playMacro();
        }

    }
}
