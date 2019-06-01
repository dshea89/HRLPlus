package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Explainer implements Serializable {
    public String setup_name = "";
    public String condition_string = "";
    public Vector axiom_strings = new Vector();
    public String operating_system = "";
    public String name = "";
    public Hashtable execution_parameters = new Hashtable();
    public int try_pos = 0;
    String input_files_directory = "";
    public Vector axiom_conjectures = new Vector();
    public boolean use_ground_instances = false;
    public String ground_instances_as_axioms = "";
    public boolean use_entity_letter = false;
    public int time_limit = 10;
    public MathWebHandler mathweb_handler = null;
    public boolean use_mathweb = false;
    public String mathweb_service_name = "";

    public Explainer() {
    }

    public String writeAxioms() {
        String var1 = "";

        for(int var2 = 0; var2 < this.axiom_strings.size(); ++var2) {
            String var3 = (String)this.axiom_strings.elementAt(var2);
            if (var3.indexOf("op") != 0) {
                var1 = var1 + var3 + "\n";
            }
        }

        return var1;
    }

    public String writeOperators() {
        String var1 = "";

        for(int var2 = 0; var2 < this.axiom_strings.size(); ++var2) {
            String var3 = (String)this.axiom_strings.elementAt(var2);
            if (var3.indexOf("op") == 0) {
                var1 = var1 + var3 + "\n";
            }
        }

        return var1;
    }

    public String executionParameter(String var1) {
        return (String)this.execution_parameters.get(var1);
    }
}
