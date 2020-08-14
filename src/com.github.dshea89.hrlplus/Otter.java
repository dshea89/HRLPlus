package com.github.dshea89.hrlplus;

import java.util.Hashtable;
import java.util.Vector;
import java.lang.String;
import java.io.*;
import java.lang.Runtime.*;

/** A super class for the otter theorem prover.
 *
 * @author Simon Colton, started 7th December 2000
 * @version 1.0 */

// Despite Java warnings that this class is unused, it is invoked via Java Reflection from the Other Provers table
@SuppressWarnings("unused")
public class Otter extends Prover implements Serializable
{
    /** The vector of demodulators for the axioms in the theory.
     */

    public Vector demodulators = new Vector();

    /** The standard constructor.
     */

    public Otter()
    {
    }

    /** Returns a string for the conjecture statement (in Otter's format).
     */

    public String conjectureStatement(Conjecture conjecture)
    {
        String time_limit = executionParameter("time_limit");
        String memory_limit = executionParameter("memory_limit");
        String conjecture_text = conjecture.writeConjecture("otter");
        if (conjecture_text.equals(""))
            return "";
        conjecture_text = "-(" + conjecture_text + ").";
        String otter_text = "set(auto).\n";
        otter_text = otter_text + "assign(max_seconds, " + time_limit + ").\n";
        otter_text = otter_text + "assign(max_mem, " + memory_limit + ").\n";
        otter_text = otter_text + writeOperators();
        otter_text = otter_text + "formula_list(usable).\n";
        otter_text = otter_text + writeAxioms();
        for (int i=0; i<axiom_conjectures.size(); i++)
        {
            Conjecture ax_conj = (Conjecture)axiom_conjectures.elementAt(i);
            otter_text = otter_text + ax_conj.writeConjecture("otter") + ".\n";
        }
        if (use_ground_instances)
            otter_text = otter_text + ground_instances_as_axioms + "\n";
        otter_text = otter_text + conjecture_text;
        otter_text = otter_text + "\nend_of_list.\n";
        return otter_text;
    }

    /** The method for attempting to prove the given conjecture.
     */

    public boolean prove(Conjecture conjecture, Theory theory)
    {
        conjecture.use_entity_letter = use_entity_letter;
        boolean is_proved = false;
        String otter_text = conjectureStatement(conjecture);
        boolean checking_for_existence = false;
        if (conjecture instanceof NonExists)
        {
            NonExists ne = (NonExists)conjecture;
            if (ne.concept.is_object_of_interest_concept)
                checking_for_existence = true;
        }

        // If the otter statement is blank, then the conjecture is returned true.

        if (otter_text.trim().equals("") && !checking_for_existence)
        {
            conjecture.is_trivially_true = true;
            return true;
        }

        if (use_mathweb==true)
        {
            try
            {
                String mathweb_otter_conjecture = "prove(\""+otter_text+
                        "\" replyWith: atp_result(state: unit time: time(sys: unit)))";
                Object result = mathweb_handler.applyMethod(mathweb_service_name, mathweb_otter_conjecture, null, null);
                if (result==null)
                    return false;

                try
                {
                    Hashtable results_hashtable = (Hashtable)result;
                    conjecture.proof_status=(String)results_hashtable.get("state");
                    if (conjecture.proof_status.equals("proof"))
                        conjecture.proof_status = "proved";
                    if (conjecture.proof_status.equals("timeout"))
                        conjecture.proof_status = "time";
                    if (conjecture.proof_status.equals("search-exhausted"))
                        conjecture.proof_status = "sos";
                    conjecture.proof_time = (new Double((String)results_hashtable.get("time"))).doubleValue();
                }
                catch(Exception e)
                {
                    conjecture.proof_status=result.toString();
                    System.out.println(e);
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return false;
        }

        if (use_mathweb==false)
        {
            try
            {
                otter_text = otter_text + "\n";
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delconj.in")));
                out.write(otter_text);
                out.close();
            }
            catch(Exception e)
            {
                System.out.println("Having trouble opening file delconj.in");
            }
            try
            {
                if (operating_system.equals("york_unix_old"))
                {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("otter1.bat")));
                    out.write("#! /bin/csh\n");
                    out.write("otter < delconj.in > delconj.out &\n");
                    out.write("sleep 1\n");
                    out.write("set C=1\n");
                    out.write("set MP=`ps | grep otter | grep -v \"1.b\" | nawk '{print $1}'`\n");
                    out.write("while ($C != \""+Integer.toString(time_limit)+"\" & $MP != \"\")\n");
                    out.write("set MP=`ps | grep otter | grep -v \"1.b\" | nawk '{print $1}'`\n");
                    out.write("@ C=$C + 1\n");
                    out.write("echo $C\n");
                    out.write("sleep 1\n");
                    out.write("end\n");
                    out.write("set MP=`ps | grep otter | grep -v \"1.b\" | nawk '{print $1}'`\n");
                    out.write("kill $MP\n");
                    out.write("set MP=`ps | grep otter | nawk '{print $1}'`\n");
                    out.write("kill $MP\n");
                    out.close();
                }
                if (operating_system.equals("jcu_unix"))
                {
                    String[] inputs = new String[3];
                    inputs[0] = "/bin/sh";
                    inputs[1] = "-c";
                    inputs[2] = "/home/guest/hr/devel/otter1.bat";
                    Process otter_process = Runtime.getRuntime().exec(inputs);
                    int exit_value = otter_process.waitFor();
                    otter_process.destroy();
                }
                if (operating_system.equals("windows"))
                {
                    Process otter_process = Runtime.getRuntime().exec("otter1.bat");
                    int exit_value = otter_process.waitFor();
                    otter_process.destroy();
                }
                if (operating_system.equals("york_unix"))
                {
                    Process otter_process = Runtime.getRuntime().exec("otter1.bat");
                    int exit_value = otter_process.waitFor();
                    otter_process.destroy();
                }

                BufferedReader file = new BufferedReader(new FileReader("delconj.out"));
                String line = "";
                String proof_length = "";
                String proof_level = "";
                boolean get_proof = false;
                while (!line.equals(null))
                {
                    try
                    {
                        if (line.indexOf("end of proof")>-1)
                            get_proof = false;
                        if (get_proof)
                            conjecture.proof = conjecture.proof+"\n"+line;
                        if (line.indexOf("PROOF")>-1)
                        {
                            get_proof = true;
                            line = file.readLine();
                        }
                        if (line.indexOf("Length of proof is ")>-1)
                        {
                            is_proved = true;
                            conjecture.proof_status = "proved";
                            conjecture.proof_length =
                                    (new Double(line.substring(19,line.indexOf(".")))).doubleValue();
                            conjecture.proof_level =
                                    (new Double(line.substring(line.lastIndexOf(" "), line.length()-1))).doubleValue();
                        }
                        if (line.indexOf("user CPU time")>-1)
                            conjecture.proof_time = (new Double(line.substring(23,line.indexOf(" ",23)))).doubleValue();
                        if (line.indexOf("wall-clock time")>-1)
                        {
                            conjecture.wc_proof_time = (new Double(line.substring(23,line.indexOf(" ",23)))).doubleValue();
                            break;
                        }
                        if (line.indexOf("Search stopped because sos empty")>-1)
                        {
                            is_proved = false;
                            conjecture.proof_status = "sos";
                            break;
                        }
                        if (line.indexOf("ERROR")>-1)
                        {
                            is_proved = false;
                            conjecture.proof_status = "syntax error";
                            break;
                        }
                        line = file.readLine();
                    }
                    catch(Exception e)
                    {}
                }
            }
            catch(Exception e)
            {
            }
        }
        conjecture.proof_attempts_information.addElement
                (name + "(" + setup_name + "): " + Double.toString(conjecture.proof_time*1000)+":"+conjecture.proof_status);
        if (conjecture.proof_status.equals("open"))
            conjecture.proof_status="time";
        return is_proved;
    }

    /** This uses otter to generate the demodulators for the axioms of the theory.
     */

    public void getDemodulators()
    {
        String otter_text = "set(knuth_bendix).\n";
        otter_text = otter_text + "set(print_lists_at_end).\n";
        otter_text = otter_text + "list(sos).\n";
        for (int i=0; i<axiom_strings.size(); i++)
        {
            String axiom_string = (String)axiom_strings.elementAt(i);
            otter_text = otter_text +
                    axiom_string.substring(axiom_string.indexOf("(")+1, axiom_string.length()-2) + ".\n";
        }
        otter_text = otter_text + "end_of_list.\n";
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delconj.in")));
            out.write(otter_text);
            out.close();
        }
        catch(Exception e){}
        if (operating_system.equals("windows"))
        {
            try
            {
                Process otter_process = Runtime.getRuntime().exec("otter1.bat");
                int exit_value = otter_process.waitFor();
                otter_process.destroy();
            }
            catch(Exception e){}
        }
        try
        {
            BufferedReader file = new BufferedReader(new FileReader("delconj.out"));
            String line = "";
            boolean log_demods = false;
            while (!line.equals(null))
            {
                line = file.readLine();
                if (log_demods && line.equals("end_of_list."))
                    log_demods = false;
                if (log_demods)
                    demodulators.addElement(line.substring(line.indexOf("]")+2, line.length()));
                if (line.equals("list(demodulators)."))
                    log_demods = true;
            }
            file.close();
        }
        catch(Exception e){}
    }

    /** This attempts to prove the conjecture using only Knuth-Bendix completion.
     */

    public boolean proveUsingKnuthBendix(Implicate implicate)
    {
        if (demodulators.isEmpty())
            getDemodulators();
        if (implicate.premise_concept.specification_strings_from_knuth_bendix.isEmpty())
            getSpecificationStringsFromKnuthBendix(implicate.premise_concept);
        String implicate_string2 =
                implicate.writeConjecture("otter");
        String goal_spec_string =
                implicate_string2.substring(implicate_string2.indexOf("->")+4,
                        implicate_string2.length()-3);
        if (implicate.premise_concept.specification_strings_from_knuth_bendix.contains(goal_spec_string))
            return true;
        return false;
    }

    /** This uses otter to get the additional specifications (as strings) using
     * otter's paramodulation rules.
     */

    public void getSpecificationStringsFromKnuthBendix(Concept concept)
    {
        Vector output = new Vector();
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delconj.in")));
            out.write("assign(max_seconds, 1).\n");
            out.write("set(demod_inf).\n");
            out.write("set(order_eq).\n");
            out.write("set(para_from).\n");
            out.write("set(para_into).\n");
            out.write("list(demodulators).\n");
            for (int i=0; i<demodulators.size(); i++)
                out.write((String)demodulators.elementAt(i));
            out.write("end_of_list.\n");
            out.write("list(sos).\n");
            out.write(concept.writeDefinition("otter_demod"));
            out.write("\nend_of_list.\n");
            out.close();

            if (operating_system.equals("windows"))
            {
                Process otter_process = Runtime.getRuntime().exec("otter1.bat");
                int exit_value = otter_process.waitFor();
                otter_process.destroy();
            }
            BufferedReader file = new BufferedReader(new FileReader("delconj.out"));
            String line = "";
            while (!line.equals(null))
            {
                line = file.readLine();
                if (line.indexOf("** KEPT")==0)
                {
                    String new_spec_string = line.substring(line.indexOf("]")+2, line.length()-1);
                    new_spec_string = removeSpaces(new_spec_string);
                    if (!output.contains(new_spec_string))
                        output.addElement(new_spec_string);
                }
            }
            file.close();
        }
        catch(Exception e){}
        concept.specification_strings_from_knuth_bendix = output;
    }

    private String removeSpaces(String in)
    {
        String output = "";
        for (int i=0; i<in.length(); i++)
        {
            String s = in.substring(i,i+1);
            if (!s.equals(" "))
                output = output + s;
        }
        return output;
    }
}
