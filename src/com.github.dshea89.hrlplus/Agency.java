package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.*;
import java.util.Vector;

/** The top level class in which the HR students are created and set
 *  running on the computers which are input as arguments.
 */

public class Agency
{

    /** the vector of processes
     */
    public Vector processes = new Vector();

    /** the homedirectory
     */
    public String homedir = "";

    /** the path to the agent files
     */
    public String remote_HR_path = "";

    /** A string of the agent names
     */
    public String agent_names = "";

    /** a simple constructor for Agency
     */
    public Agency ()
    {
    }

    /** In main we create a new agency, check that arguments have been
     * input, make files for each agent in ~/hr/agency, and start the
     * processes running
     */

    public static void main(String[] args)
    {

        Agency agency = new Agency();//create a new Agency object

        agency.parseArgs(args);
        agency.getHomeDir();
        agency.remote_HR_path = agency.homedir + "/hr/agency"; //set path for agency file to ~/hr/agency
        File f = new File(agency.remote_HR_path);
        if (!f.exists())
            f.mkdir();
        agency.createAndStartProcesses(args);
    }

    /** If no arguments are input in then print error message and exit the program
     */
    public void parseArgs(String[] args)
    {
        if (args.length ==0)
        {
            System.out.println("You need to input the names of the computers ");
            System.out.println("on which you wish to run HR,");
            System.out.println("i.e. agency <hostname1> [<hostname2>...]");
            System.exit(-1);
        }
    }


    /** Gets the home directory of the user
     */
    public void getHomeDir()
    {
        try
        {
            homedir = System.getProperty("user.home");
        }
        catch (SecurityException e)
        {
            System.out.println("Problem reading user home directory");
            System.exit(-1);
        }
    }

    //note - if on edinburgh sun machine then change edinburgh-sun to edinburgh-sun (or just omit it)
    // if on edinburgh dice machine then change edinburgh-sun to edinburgh-sun

    public void createAndStartProcesses(String[] args)
    {
        String teacher_name = "";

        if(!(args.length == 0))
        {
            int last_arg = args.length - 1;
            teacher_name = args[last_arg];
        }

        for(int i=0; i<args.length; i++)
        {
            try
            {
                String localhostname = java.net.InetAddress.getLocalHost().getHostName();
                String agent_command;

                int port = 8000;

                if (i<args.length - 1)
                {
                    port = port+i;
                    agent_names = agent_names + " " + args[i]+":"+port; //added so that teacher knows each student's port number
                    String command =  homedir + "/hr/runs/makestudent " + args[i] + " "
                            + localhostname + " " + teacher_name + " " + port;
                    System.out.println(command);
                    Runtime.getRuntime().exec(command);
                    System.out.println("student");
                }

                else
                {
                    agent_names = agent_names + " " + args[i];
                    String command =  homedir + "/hr/runs/maketeacher " + args[i] + " " + localhostname + agent_names;
                    System.out.println(command);
                    Process p = Runtime.getRuntime().exec(command);
                    System.out.println("agent_names are " + agent_names);
                    p.waitFor();
                }


                //processes.addElement(new (Process)(Runtime.getRuntime().exec(command)));
                //AgentProcess ap = Runtime.getRuntime().exec(command);
                //processes.addElement(ap);
                //System.out.println("processes: " + processes);

            }
            catch (Exception e)
            {
                System.out.println("Problem starting agency");
                System.out.println(e);
                System.exit(-1);
            }
        }
    }
}
