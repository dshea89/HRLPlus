package com.github.dshea89.hrlplus;

import java.lang.String;
import java.net.*;
import java.io.*;
import java.awt.TextArea;

/** A class for calling Geoff Sutcliffe's SystemOnTPTP program remotely.
 *
 * @author Simon Colton, started 10th January 2002.
 * @version 1.0 */

public class SOTHandler extends Prover implements Serializable
{
    /** The printwriter which writes to SystemOnTPTP.
     */

    public PrintWriter pw = null;

    /** This sends the conjecture to SystemOnTPTP, waits for the reply and
     * returns the output from SOT.
     */

    public String submit(Conjecture conjecture, TextArea text_box)
    {
        String tptp_input = writeAxioms();
        String conjecture_string = conjecture.writeConjecture("tptp");

        if (conjecture_string.equals(""))
        {
            conjecture.proof_status = "trivial";
            return "Conjecture is trivial";
        }

        tptp_input = tptp_input + conjecture_string;

        String output = "";
        try
        {
            URL url = new URL("http://www.tptp.org/cgi-bin/SystemOnTPTPFormReply");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            pw = new PrintWriter(connection.getOutputStream());

            output = output + sendLineToSOT("NoHTML","1");
            output = output + sendLineToSOT("QuietFlag","-q0");
            output = output + sendLineToSOT("SubmitButton","RunParallel");
            output = output + sendLineToSOT("ProblemSource","UPLOAD");
            output = output + sendLineToSOT("AutoModeTimeLimit","300");
            output = output + sendLineToSOT("AutoMode","-cE");
            output = output + sendLineToSOT("AutoModeSystemsLimit","3");
            output = output + sendLastLineToSOT("UPLOADProblem",tptp_input);

            text_box.append("Sent: \n"+output);

            pw.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while((line=in.readLine()) != null)
            {
                response.append(line + "\n");
                text_box.append(line + "\n");
            }
            in.close();
            output = output + "\nThe reply was:\n\n" + response.toString();
        }
        catch(Exception e)
        {
            return "Failed because:\n"+e.toString();
        }
        return output;
    }

    /** This sends a TPTP problem to SystemOnTPTP
     */

    public String submitTPTP(String tptp_number, TextArea text_box)
    {
        String output = "";
        try
        {
            URL url = new URL("http://www.tptp.org/cgi-bin/SystemOnTPTPFormReply");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            pw = new PrintWriter(connection.getOutputStream());

            sendLineToSOT("NoHTML","1");
            sendLineToSOT("QuietFlag","-q2");
            sendLineToSOT("SubmitButton","RunParallel");
            sendLineToSOT("ProblemSource","TPTP");
            sendLineToSOT("AutoModeTimeLimit","300");
            sendLineToSOT("AutoMode","-cE");
            sendLineToSOT("AutoModeSystemsLimit","3");
            sendLineToSOT("TPTPProblem",tptp_number);

            pw.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while((line=in.readLine()) != null)
            {
                response.append(line + "\n");
                text_box.append(line + "\n");
            }
            in.close();
            output = output + "\nThe reply was:\n\n" + response.toString();
        }
        catch(Exception e)
        {
            return "Failed because:\n"+e.toString();
        }
        return output;
    }

    private String sendLineToSOT(String name, String value)
    {
        return sendLineToSOT(name, value, false);
    }

    private String sendLastLineToSOT(String name, String value)
    {
        return sendLineToSOT(name, value, true);
    }

    private String sendLineToSOT(String name, String value, boolean is_final)
    {
        char final_char = '&';
        if (is_final) final_char = '\n';
        String to_pw = name + "=" + URLEncoder.encode(value) + final_char;
        pw.print(to_pw);
        return to_pw;
    }
}
