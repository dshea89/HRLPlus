package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.*;

/** A class for handling interaction with Maple.
 *
 * @author Simon Colton, started 14th November 2001
 * @version 1.0 */

public class Maple implements Serializable
{
    public String callFunction(String[] entities, String function_name,
                               String[] packages_required, String[] files_required){

        String output = "";
        String maple_text = "";

        for (int i=0; i<packages_required.length; i++)
            maple_text = maple_text + "with(" + packages_required[i] + "):\n";
        for (int i=0; i<files_required.length; i++)
            maple_text = maple_text + "read `" + files_required[i] + "`:\n";
        maple_text = maple_text + "writeto(\"delcalc.out\"):\n";
        maple_text += "lprint(" + function_name + "(";
        for (int i=0; i<entities.length; i++){
            maple_text += entities[i];
            if (i<entities.length-1)
                maple_text += ", ";
        }
        maple_text += ")):\n";
        maple_text = maple_text + "quit:";

        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delcalc.in")));
            out.write(maple_text);
            out.close();

            Process maple_process = Runtime.getRuntime().exec("maple2.bat");
            int exit_value = maple_process.waitFor();
            maple_process.destroy();

            BufferedReader in = new BufferedReader(new FileReader("delcalc.out"));
            String s = in.readLine();
            while (s!=null){
                if (!(s==null) && s.indexOf(">") <0 && s.indexOf("bytes")<0)
                    output=s;
                s = in.readLine();
            }
            in.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("here in Maple");
        }
        return output;

    }

    public String callFunction(String entity, String function_name,
                               String[] packages_required, String[] files_required)
    {
        String[] entities = {entity};
        return callFunction(entities, function_name, packages_required, files_required);
    }

    public Tuples getOutput(int arity, String entity, String function_name, String[] packages, String[] files)
    {
        Tuples output = new Tuples();
        String function_output = callFunction(entity, function_name, packages, files);
        if (arity==1)
        {
            if (function_output.equals("true"))
            {
                Vector tuple = new Vector();
                output.addElement(tuple);
            }
        }

        if (arity==2)
        {
            if (function_output.indexOf("{")<0)
            {
                Vector tuple = new Vector();
                tuple.addElement(function_output);
                output.addElement(tuple);
            }
            else
            {
                function_output = function_output.substring(1,function_output.length()-1);
                while (function_output.indexOf(",")>=0)
                {
                    Vector tuple = new Vector();
                    String val = function_output.substring(0,function_output.indexOf(","));
                    tuple.addElement(val);
                    output.addElement(tuple);
                    function_output = function_output.substring(function_output.indexOf(",")+2, function_output.length());
                }
                Vector tuple = new Vector();
                tuple.addElement(function_output);
                output.addElement(tuple);
            }
        }
        output.sort();
        return output;
    }

    public Tuples getOutput(int arity, String entity, String function_name, String[] packages)
    {
        String[] files = {};
        return getOutput(arity, entity, function_name, packages, files);
    }


    /** Given an HR string representation of a graph, outputs a string
     * in Maple's representation. */
    public String writeGraphForMaple(String hr_string)
    {
        //System.out.println("hr_string is " + hr_string);

        String letters = "abcdefghijklmnopqrstuvwxzy";

        String output = "graph({";;
        int i;

        for(i=0;i<hr_string.indexOf("[");i++)
        {
            output = output + Integer.toString(letters.indexOf(hr_string.charAt(i)));;
            if(i<hr_string.indexOf("[")-1)
                output = output + ",";
        }
        output = output + "},{";
        i++;
        for(;i<hr_string.indexOf("]");)
        {

            output = output + "{" + Integer.toString(letters.indexOf(hr_string.charAt(i))) + "," +
                    Integer.toString(letters.indexOf(hr_string.charAt(i+1))) + "}";
            i = i+3;
            if(i<hr_string.indexOf("]")-1)
                output = output + ",";
        }

        //System.out.println("returning output " + output + "})\").");
        return output + "})";
    }
}
