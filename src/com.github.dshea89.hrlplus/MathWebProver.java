package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.*;
import java.util.*;
import java.lang.Runtime.*;

/** A class for the Mathweb prover (the ATP service)
 *
 * @author J"urgen Zimmer, Simon Colton, started 9th October 2001
 * @version 1.0 */

// Despite Java warnings that this class is unused, it is invoked via Java Reflection from the Other Provers table
@SuppressWarnings("unused")
public class MathWebProver extends Prover implements Serializable
{
    /** The vector of theorem prover names to use within MathWeb.
     */

    public Vector prover_names = new Vector();

    /** The method for attempting to prove the given conjecture.
     */

    public boolean prove(Conjecture conjecture)
    {
        String tptp_input = writeAxioms();
        String conjecture_string = conjecture.writeConjecture("tptp");

        if (conjecture_string.equals(""))
        {
            conjecture.proof_status = "trivial";
            return true;
        }

        tptp_input = tptp_input + conjecture_string;

        int problemID = (new Integer(conjecture.id)).intValue();
        String provers = "[";
        for (int i=0; i<prover_names.size()-1; i++)
            provers = provers + (String)prover_names.elementAt(i)+" ";
        if (!prover_names.isEmpty())
            provers = provers + (String)prover_names.lastElement();
        provers = provers + "]";

        String mathweb_atp_conjecture;
        int time_limit = 120;
        String syntax = "tptp";

        String mode =  "all-on-one"; // mode can also be: "first-of-all"

        mathweb_atp_conjecture = "proveProblem(prob(id: "+problemID+" problem: \""+tptp_input +
                "\"#"+syntax +
                " provers: "+provers +
                " mode: '" + mode + "' "+
                " timeout: "+time_limit+"))";

        Object result =
                mathweb_handler.applyMethod("ATP", mathweb_atp_conjecture, null, new Integer((3*time_limit)+20));

        conjecture.proof_status=result.toString();

        boolean output = false;
        if (result instanceof Vector)
        {
            Vector res = (Vector) result;
            Hashtable table;
            for (Enumeration e = res.elements(); e.hasMoreElements();)
            {
                table = (Hashtable) e.nextElement();
                String state = (String)table.get("state");
                if (state!=null)
                {
                    if (state.equals("proof"))
                    {
                        output = true;
                        conjecture.proof_status = "proved";
                    }
                }
                String time = (String)table.get("time");
                Double time_d = new Double(time);
                double d_time = time_d.doubleValue();
                if (d_time > conjecture.mw_max_proof_time)
                    conjecture.mw_max_proof_time = d_time;
                if (d_time < conjecture.mw_min_proof_time || conjecture.mw_min_proof_time==-1)
                    conjecture.mw_min_proof_time = d_time;
                conjecture.mw_av_proof_time = conjecture.mw_av_proof_time + d_time;
                String prover_name = (String)table.get("name");
                if (time!=null && prover_name!=null && state!=null)
                    conjecture.proof_attempts_information.addElement(prover_name+":"+state+":"+time);
            }
            if (res.size()>0)
            {
                conjecture.mw_av_proof_time = conjecture.mw_av_proof_time/res.size();
                conjecture.mw_diff_proof_time = conjecture.mw_max_proof_time - conjecture.mw_min_proof_time;
            }
        }
        return output;
    }

    /** The standard constructor. Just sets the name of the prover to "mathweb".
     */

    public MathWebProver()
    {
        name = "mathweb";
    }

    /** Returns a string for the conjecture statement (in Otter's format).
     */

    public String conjectureStatement(Conjecture conjecture)
    {
        String output = "";
        String conjecture_text = conjecture.writeConjecture("tptp");
        if (conjecture_text.equals(""))
            return "";

        return output;
    }
}
