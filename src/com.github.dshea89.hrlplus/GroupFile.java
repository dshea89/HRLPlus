package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.*;

/** The group file which everyone writes to */

public class GroupFile extends Thread implements Serializable
{
    /** The group file name*/
    String group_file_name = "group-file";

    /** The constructor */
    public GroupFile ()
    {
    }

    public GroupFile (String group_file)
    {
        this.group_file_name = group_file_name;
    }

    public void writeToGroupFile(String string)
    {
        System.out.println("writeToGroupFile: " + string);

        if(!((AgentOutputPanel.group_file_text.getText()).equals("")))
            group_file_name = AgentOutputPanel.group_file_text.getText();

        boolean written = false;

        //  //wrap the string
//     for(int i=70; i<string.length();i+70)
//       {
// 	for(int j=)
//       }


        while(!(written))
        {
            try
            {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(group_file_name, true)));
                out.println(string + "\n");
                out.close();
                written = true;
                break;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            try
            {
                sleep(50);
            }
            catch (Exception e)
            {
            }
        }
        System.out.println("done writeToGroupFile");
    }
}
