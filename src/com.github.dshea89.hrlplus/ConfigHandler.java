package com.github.dshea89.hrlplus;

import java.util.Hashtable;
import java.lang.String;
import java.io.*;

/** A class for obtaining the user configuration from a specified file.
 *
 * @author Simon Colton, started 30th April 2002
 * @version 1.0 */

public class ConfigHandler extends PseudoCodeUser implements Serializable
{
    /** The hashtable of values read from the config file.
     */

    public Hashtable values_table = new Hashtable();

    /** The simple constructor.
     */

    public ConfigHandler()
    {
    }

    /** This reads the file and puts the various keys and values into the
     * hashtable.
     */

    public boolean readUserConfiguration(String filename)
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
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }

    /** This sets a value in the config hashtable, which will be used
     * when HR is started.
     */

    public void setValue(String value_name, String value)
    {
        values_table.put(value_name, value);
    }

    /** This initialises the HR object with respect to the user configuration file
     */

    public void initialiseHR(HR hr)
    {
        pseudo_code_interpreter.local_alias_hashtable.put("this", this);
        pseudo_code_interpreter.local_alias_hashtable.put("hr", hr);
        pseudo_code_interpreter.runPseudoCode(pseudo_code_lines);
        int width = 1100;
        int height = 800;
        if (values_table.containsKey("width"))
            width = (new Integer((String)values_table.get("width"))).intValue();
        if (values_table.containsKey("height"))
            height = (new Integer((String)values_table.get("height"))).intValue();
        hr.setSize(width, height);
        hr.setLocation(30,30);
        hr.setTitle(hr.my_agent_name + " HR" + hr.version_number);
        hr.theory.front_end.version_number = hr.version_number;
        if (values_table.containsKey("operating system"))
            hr.theory.operating_system = (String)values_table.get("operating system");
        else
            System.out.println("HR> warning: you have not specified the operating system");

        if (values_table.containsKey("input files directory"))
        {
            String files_dir = (String)values_table.get("input files directory");
            if (!files_dir.substring(files_dir.length()-1, files_dir.length()).equals("/"))
                files_dir = files_dir + "/";
            hr.theory.input_files_directory = files_dir;
            hr.theory.front_end.input_files_directory = files_dir;
        }
        else
            System.out.println("HR> warning: you have not specified a directory for your input files");

        if (values_table.containsKey("storage directory"))
        {
            String store_dir = (String)values_table.get("storage directory");
            if (!store_dir.substring(store_dir.length()-1, store_dir.length()).equals("/"))
                store_dir = store_dir + "/";
            hr.theory.storage_handler.storage_directory = store_dir;
        }
    }
}
