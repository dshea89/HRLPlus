package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.*;

/** A class for individual reports.
 *
 * @author Simon Colton, started 28th June 2002.
 * @version 1.0 */

public class Report extends PseudoCodeUser implements Serializable
{
    /** This constructs the report from the given file.
     */

    public Report(String filename)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String s = in.readLine();
            while (!(s==null))
            {
                pseudo_code_lines.addElement(s);
                s = in.readLine();
            }
            in.close();
        }
        catch(Exception e){}
    }

    /** This runs the report.
     */

    public void runReport()
    {
        pseudo_code_interpreter.runPseudoCode(pseudo_code_lines);
    }
}
