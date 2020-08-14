package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.*;
import java.lang.Runtime.*;
import java.awt.Label;

/** A super class for the mace model generator
 *
 * @author Simon Colton, started 18th December 2000
 * @version 1.0 */

public class Mace extends DataGenerator implements Serializable
{
    /** The standard constructor. Just sets the name of the model generator to "mace".
     */

    public Mace()
    {
    }

    public Hashtable readModelFromMace(String filename)
    {
        Hashtable output = new Hashtable();
        System.out.println("Thurs - 1");
        try
        {
            System.out.println("Thurs - 2");
            BufferedReader file = new BufferedReader(new FileReader(filename));
            System.out.println("Thurs - 3");
            String line = "";
            boolean starting_up = true;
            boolean found_counterexample = false;
            boolean end_now = false;
            boolean read_now = false;
            boolean reading_data = false;
            boolean get_name_now = false;
            boolean wait_one_more_line = false;
            int line_num = 0;
            String pred_name = "";
            Tuples tuples = new Tuples();
            Vector tuple = new Vector();
            Vector elements = new Vector();
            System.out.println("Thurs - 4");
            line = file.readLine().trim();
            System.out.println("Thurs - 5");
            while (line!=null && !end_now)
            {
                if (!starting_up && line.equals(")"))	      break;
                if (read_now)
                {
                    if (starting_up && line.indexOf("((")>=0 && line_num>1)
                    {
                        get_name_now = true;
                        starting_up = false;
                    }
                    if (wait_one_more_line)
                    {
                        reading_data = true;
                        wait_one_more_line = false;
                    }
                    if (get_name_now)
                    {
                        pred_name = line.substring(2, line.indexOf(" "));
                        get_name_now = false;
                        wait_one_more_line = true;
                    }
                    if (line.indexOf("))")>=0)
                    {
                        if (!pred_name.equals("") && !pred_name.equals("equal") &&
                                !tuples.isEmpty() && pred_name.indexOf("$")<0)
                            output.put(pred_name, tuples);
                        if (line.indexOf("((")>=0)
                        {
                            pred_name = line.substring(2, line.indexOf(" "));
                            if (pred_name.indexOf("$")<0)
                            {
                                tuple = new Vector();
                                tuples = new Tuples();
                                String element = line.substring(line.lastIndexOf(" ")+1,line.indexOf("))"));
                                tuple.addElement(element);
                                tuples.addElement(tuple);
                                if (!elements.contains(element))
                                    elements.addElement(element);
                                output.put(pred_name,tuples);
                            }
                        }
                        reading_data = false;
                        get_name_now = true;
                        tuple = new Vector();
                        tuples = new Tuples();
                    }
                    if (reading_data && !get_name_now)
                    {
                        String first_entries = line.substring(2,line.indexOf(")"));
                        String entry = "";
                        while (first_entries.indexOf(" ")>=0)
                        {
                            entry = first_entries.substring(0, first_entries.indexOf(" "));
                            tuple.addElement(entry);
                            first_entries = first_entries.substring(first_entries.indexOf(" ")+1,
                                    first_entries.length());
                            if (!elements.contains(entry) && !entry.equals(pred_name) && !entry.equals(".") &&
                                    !entry.equals(""))
                                elements.addElement(entry);
                        }
                        tuple.addElement(first_entries);
                        String final_entry = line.substring(line.lastIndexOf(" ")+1, line.length()-1);
                        tuple.addElement(final_entry);
                        if (!tuple.contains(pred_name))
                            tuples.addElement((Vector)tuple.clone());
                        tuple = new Vector();
                        if (!elements.contains(final_entry) && !final_entry.equals(pred_name) && !final_entry.equals(".")
                                && !final_entry.equals(""))
                            elements.addElement(final_entry);
                    }
                    line_num++;
                }
                if (line.equals(";; BEGINNING OF IVY MODEL"))
                    read_now = true;
                line = file.readLine();
                if (line!=null)
                    line = line.trim();
            }
            if (!elements.isEmpty())
            {
                Tuples element_tuples = new Tuples();
                for (int i=0; i<elements.size(); i++)
                {
                    String element = (String)elements.elementAt(i);
                    if (!element.equals("t") && !element.equals("nil"))
                    {
                        Vector element_tuple = new Vector();
                        element_tuple.addElement(element);
                        element_tuples.addElement(element_tuple);
                    }
                }
                element_tuples.sort();
                output.put("element",element_tuples);
            }
        }
        catch(Exception e)
        {
            System.out.println("Problem reading Mace model");
            System.out.println(e);
        }
        return output;
    }

    /** Returns a string for the conjecture statement (in Otter's format).
     */

    public String conjectureStatement(Conjecture conjecture)
    {
        String conjecture_text = conjecture.writeConjecture("otter");
        if (!conjecture_text.equals(""))
        {
            if (conjecture instanceof NonExists)
                conjecture_text = conjecture_text.substring(1,conjecture_text.length());
            else
                conjecture_text = "-(" + conjecture_text + ").";
        }
        if (conjecture instanceof NonExists)
        {
            NonExists ne = (NonExists)conjecture;
            if (ne.concept.is_object_of_interest_concept)
                conjecture_text = "";
        }
        String mace_text = "set(auto).\n";
        mace_text = mace_text + "formula_list(usable).\n";
        mace_text = mace_text + writeAxioms();
        for (int i=0; i<axiom_conjectures.size(); i++)
        {
            Conjecture ax_conj = (Conjecture)axiom_conjectures.elementAt(i);
            mace_text = mace_text + ax_conj.writeConjecture("otter") + ".\n";
        }
        if (use_ground_instances)
            mace_text = mace_text + ground_instances_as_axioms + "\n";
        mace_text = mace_text + conjecture_text;
        mace_text = mace_text + "\nend_of_list.\n";
        return mace_text;
    }

    /** The method for attempting to prove the given conjecture.
     */

    public Vector counterexamplesFor(Conjecture conjecture, Theory theory, int num_required)
    {
        String size_limit = executionParameter("size_limit");
        String time_limit = executionParameter("time_limit");
        Vector output = new Vector();
        Entity counterexample = new Entity();
        String entity_string = "";
        boolean is_proved = false;
        boolean look_for_single = false;
        operating_system = "york_unix";//added alisonp
        if (conjecture instanceof NonExists)
        {
            NonExists ne = (NonExists)conjecture;
            if (ne.concept.is_object_of_interest_concept)
                look_for_single = true;
        }
        String conjecture_text = conjectureStatement(conjecture);
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            out.write(conjecture_text);
            out.close();
        }
        catch(Exception e)
        {
            System.out.println("Having trouble opening file delmodel.in");
        }
        try
        {
            if (operating_system.equals("jcu_unix"))
            {
                String[] inputs = new String[3];
                inputs[0] = "/bin/sh";
                inputs[1] = "-c";
                inputs[2] = "/home/guest/hr/devel/mace1.bat";
                Process mace_process = Runtime.getRuntime().exec(inputs);
                mace_process.waitFor();
            }
            if (operating_system.equals("york_unix"))
            {
                Process otter_process = Runtime.getRuntime().exec("mace1.bat");
                int exit_value = otter_process.waitFor();
            }
            if (operating_system.equals("windows"))
            {
                boolean no_single = false;
                if (look_for_single)
                {
                    String mace_process_string =
                            input_files_directory + "mace2.bat 1 " + time_limit;
                    Process otter_process = Runtime.getRuntime().exec(mace_process_string);
                    int exit_value = otter_process.waitFor();
                    Hashtable concept_data_temp = readModelFromMace("delmodel.out");
                    if (concept_data_temp.isEmpty())
                        no_single = true;
                }
                if (!look_for_single || no_single)
                {
                    String mace_process_string =
                            input_files_directory + "mace1.bat " + size_limit + " " + time_limit;
                    Process otter_process = Runtime.getRuntime().exec(mace_process_string);
                    int exit_value = otter_process.waitFor();
                }
            }
            Hashtable concept_data = readModelFromMace("delmodel.out");
            if (!concept_data.isEmpty())
            {
                counterexample.concept_data = concept_data;
                counterexample.conjecture = conjecture;
                output.addElement(counterexample);
                conjecture.counterexamples.addElement(counterexample);
                conjecture.proof_status = "disproved";
            }
            Enumeration keys = concept_data.keys();
            String counterexample_name = "";
            boolean not_simple_algebra = false;
            while (keys.hasMoreElements())
            {
                String key = (String)keys.nextElement();
                if (key.equals("*"))
                {
                    Tuples tuples = (Tuples)concept_data.get(key);
                    for (int i=0; i<tuples.size(); i++)
                    {
                        Vector tuple = (Vector)tuples.elementAt(i);
                        counterexample_name = counterexample_name + (String)tuple.lastElement();
                    }
                }
                if (key.equals("+"))
                    not_simple_algebra = true;
            }
            if (!not_simple_algebra)
                counterexample.name = counterexample_name;
            else
                counterexample.name = "new_" + Integer.toString(theory.entities.size());
        }
        catch(Exception e)
        {
            System.out.println("Having trouble running MACE");
        }
        return output;
    }
}
