package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.*;
import java.util.Vector;
import java.net.*;

/**
 This is the top level Agent class which Student and Teacher both extend
 */

public abstract class Agent extends Thread implements Serializable
{
    /** The front end to this agent. */

    public AgentWindow window = new AgentWindow();

    /** The group file for this agent*/
    public GroupFile group_file = new GroupFile();

    /** This is the name of the agent. */

    public String name = "";

    /** Each agent has a copy of HR. */

    public HR hr = new HR();

    /** The discussion vector for each agent (different for all) [modify] */

    public Vector discussion_vector = new Vector();

    /** A personal discussion vector for each agent (different for all) */

    public Vector personal_discussion_vector = new Vector();

    /** The vector of items awaiting discussion (currently only the teacher makes use of this)*/
    public Vector agenda = new Vector();

    /** The current request from the teacher */

    Request current_request = new Request("");//added public

    /** The current responses from the students to a particular request */

    public Vector current_responses = new Vector();

    /** Clone of the current request from the teacher (used to store in discussion vector)*/

    //Request current_request_clone = new Request("");


    /** The current response just passed */

    Response current_response = new Response();

    /** The Agent's ServerSocket*/

    public ServerSocket server_socket = null;

    /** The Agent's Socket*/
    public Socket socket = null;


    /*************************/
    //INITIALISATION METHODS //
    /*************************/

    /** The method to initialise the copy of HR owned by
     * this agent, given the name of the config file. */

    public void initialiseHR(String configfilename, String status_name)
    {
        HR temp_hr = new HR();
        String[] configfilename_array = {configfilename};
        hr = temp_hr.constructHR(configfilename_array);
        hr.my_agent_name = name;
        hr.theory.front_end.my_agent_name = name;
        hr.theory.front_end.main_frame.setTitle(status_name);
        //hr.theory.front_end.screen_buttons.setTitle(name);
    }

    /** The method to initialise the copy of HR owned by this agent,
     * given the name of the config file and the macro. */
    public void initialiseHR(String configfilename, String status_name, String macro)
    {
        System.out.println("status_name: "+ status_name);
        System.out.println("macro: " + macro);
        String[] args = {configfilename, "vmacro", macro};
        HR temp_hr = new HR();
        hr = temp_hr.constructHR(args);
        hr.my_agent_name = name;
        hr.theory.front_end.my_agent_name = name;
        hr.theory.front_end.main_frame.setTitle(status_name);
    }




    /************************/
    //COMMUNICATION METHODS //
    /************************/

    /** listenForCall() constructs a ServerSocket (device for incoming
     calls), then sits and waits for it to ring (by calling the accept
     method). When it has rung, the agent prints a message in its
     window to say that its phone has rung, and prints out the messsage
     it's received. It adds the message its personal discussion
     vector. */

    public void listenForCall(ServerSocket server_socket) //maybe an arg to say whose call you're listening for
    //(and what type of thing you're expecting them to say)?
    {
        //ServerSocket server_socket = null;
        ObjectInputStream object_input_stream = null;

        while(true)
        {
            try
            {
                socket = server_socket.accept();
                object_input_stream = new ObjectInputStream(socket.getInputStream());
                Object received_object = object_input_stream.readObject();
                window.writeToFrontEnd("just received " + received_object);
                //window.writeToFrontEnd(received_object);

                // if teacher has sent a vector of responses, add them
                // all to discussion_vector. then respond to these responses.
                if(received_object instanceof Vector)
                {
                    Vector received_vector = (Vector)received_object;

                    if(!(received_vector.isEmpty()) && received_vector.elementAt(0) instanceof Response)
                    {
                        //first add to discussion vector
                        for(int i = 0; i< received_vector.size(); i++)
                        {
                            Response new_response = (Response)received_vector.elementAt(i);
                            personal_discussion_vector.addElement(new_response);
                        }

                        // then respond ???
                        window.writeToFrontEnd("current_responses is " + current_responses);
                        window.writeToFrontEnd("received_vector is " + received_vector);
                        current_responses = received_vector;
                        window.writeToFrontEnd("current_responses is now " + current_responses);
                        window.writeToFrontEnd("received_vector is now " + received_vector);
                    }
                }

                if(received_object instanceof Request)
                {

                    current_request = (Request)received_object;
                    //window.writeToFrontEnd("current_request is now " + current_request);
                    //window.writeToFrontEnd("personal_discussion_vector is " + personal_discussion_vector);
                    //if(!personal_discussion_vector.isEmpty())
                    //  window.writeToFrontEnd("personal_discussion_vector.lastElement() is " + personal_discussion_vector.lastElement());
                    personal_discussion_vector.addElement(current_request);
                    //window.writeToFrontEnd("personal_discussion_vector now, is " + personal_discussion_vector);
                    //if(!personal_discussion_vector.isEmpty())
                    //  window.writeToFrontEnd("personal_discussion_vector.lastElement() is now " + personal_discussion_vector.lastElement());
                    //window.writeToFrontEnd("HMMMMMM");
                }

                if(received_object instanceof Response)
                {
                    window.writeQuietlyToFrontEnd("received_object is  instanceof  Response");
                    current_response = (Response)received_object;
                    window.writeToFrontEnd("current_response is now " + current_response);
                    personal_discussion_vector.addElement(current_response);
                }

                object_input_stream.close();
                socket.close();
                break;
            }

            catch(Exception e)
            {
                window.writeToFrontEnd("Error in listenForCall: " + e);
                //System.exit(1);
            }
        }
        window.writeQuietlyToFrontEnd("at end of listenForCall, current_responses is " + current_responses);
    }

    /** makeCall(person, message) makes a call to person, and sends
     message. It writes what it has sent in its window. Everytime an agent
     has something to say, it makes a new Socket */

    public void makeCall(int port_of_caller, InetAddress hostname, int port, Object object)//?
    {

        while(true)
        {
            try
            {
                socket = new Socket(hostname, port);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(object);
                personal_discussion_vector.addElement(output.toString());//here
                if(object instanceof Request)
                {
                    Request req = (Request)object;
                    if(!(req.isEmpty()))
                    {
                        window.writeToFrontEnd("Just called " + hostname + " -- " + port +  " -- " + " to say ");
                        window.writeToFrontEnd(object);
                        window.writeToFrontEnd("");

                        //CallableStatement cs = new CallableStatement("test");
                        //int current_time = cs.getDate();
                        //System.out.println(port_of_caller + ": Just called " + hostname + " -- " + port +  " -- " + " to say ");
                        //System.out.println(object);
                        //System.out.println("");
                    }
                }
                else
                {
                    window.writeToFrontEnd("Just called " + hostname + " -- " + port +  " -- " + " to say ");
                    window.writeToFrontEnd(object);
                    window.writeToFrontEnd("");
                    //System.out.println(port_of_caller + ": Just called " + hostname + " -- " + port +  " -- " + " to say ");
                    //System.out.println(object);
                    //System.out.println("");
                }
                output.flush();
                output.close();
                socket.close();
                break;
            }

            catch(ConnectException e)
            {
                //window.writeToFrontEnd("IOError in makeCall: " + e);

            }

            catch(Exception e)
            {
                window.writeToFrontEnd("Error in makeCall: " + e);
                window.writeToFrontEnd("should exit here");
                //System.exit(1);
            }

            //window.writeToFrontEnd("done makeCall()");
        }

    }

    /** MakeCall to every student in the class, to say current_request */
    public void makeCallToAllStudents(int port_of_caller, Vector student_inetaddresses, Request current_request)
    {
        //window.writeToFrontEnd("student_inetaddresses are " + student_inetaddresses);
        for (int i = 0; i<student_inetaddresses.size(); )
        {
            //window.writeToFrontEnd("i is " + i);
            InetAddress student_hostname = (InetAddress)student_inetaddresses.elementAt(i);
            //	window.writeToFrontEnd("student_hostname at " +i);
            String current_student_port_string = (String)student_inetaddresses.elementAt(i+1);
            //window.writeToFrontEnd("current_student_port_string is " + current_student_port_string + " at i+1 = " + (i+1));
            int current_student_port = Integer.parseInt(current_student_port_string);
            //window.writeToFrontEnd("starting makeCall(" + student_hostname + "," + current_student_port + "," +current_request);
            makeCall(port_of_caller, student_hostname, current_student_port, current_request);
            //window.writeToFrontEnd("finished makeCall() to: " + student_hostname);
            //listenForCall(server_socket);
            //window.writeToFrontEnd("done listenForCall(server_socket) to: " + student_hostname);
            i = i+2;
            //	window.writeToFrontEnd("done a loop");
        }
    }


    /** MakeCall to every student in the class, to say current_responses */
    public void makeCallToAllStudents(int port_of_caller, Vector student_inetaddresses, Vector current_responses)
    {
        for (int i = 0; i<student_inetaddresses.size(); )
        {

            InetAddress student_hostname = (InetAddress)student_inetaddresses.elementAt(i);
            String current_student_port_string = (String)student_inetaddresses.elementAt(i+1);
            int current_student_port = Integer.parseInt(current_student_port_string);
            //window.writeToFrontEnd("calling makeCall(" + student_hostname + "," + current_student_port + "," +current_responses);
            makeCall(port_of_caller, student_hostname, current_student_port, current_responses);
            //window.writeToFrontEnd("done makeCall() to: " + student_hostname);
            //listenForCall(server_socket);
            //window.writeToFrontEnd("done listenForCall(server_socket) to: " + student_hostname);
            i = i+2;
        }
    }


    /** listens for the number of calls as students in class. Retruns a vector of what they have said */
    public Vector listenForCallFromAllStudents(ServerSocket server_socket, Vector student_inetaddresses)
    {
        Vector output = new Vector();

        for (int i = 0; i<student_inetaddresses.size(); )
        {
            listenForCall(server_socket);
            window.writeToFrontEnd("done listenForCall(server_socket)");

            //may not work
            if(!(current_request==null))
                output.addElement(current_request);

            //may not work
            if(!(current_response==null))
                output.addElement(current_response);

            i = i+2;
        }
        return output;
    }

    /** listens for n responses to the current request and stores them
     in a current_responses vector */

    public void listenForResponsesToCurrentRequest(ServerSocket server_socket, Request current_request, int num_students, Vector current_responses)
    {
        // window.writeToFrontEnd("started listenForResponsesToCurrentRequest");
        //window.writeToFrontEnd("current_responses is " + current_responses);
        for(int i=0; i<num_students; i++)
        {
            current_response = null;
            listenForCall(server_socket);

            if(!(current_response==null) && ((Request)current_response.request).equals(current_request))
                current_responses.addElement(current_response);
        }
        //window.writeToFrontEnd("current_responses is now " + current_responses);
        //window.writeToFrontEnd("finished listenForResponsesToCurrentRequest");
    }

    /** listens for n responses to the current responses - if any of the
     students requests to perform monster-barring then inserts this
     into the group agenda. currently any other response is ignored*/
    //group_agenda.agenda
    public Vector listenForRequestsToResponses1(ServerSocket server_socket, Request current_request, int num_students)
    {
        //window.writeToFrontEnd("started listenForRequestsToResponses");
        Vector output = new Vector();

        for(int i=0; i<num_students; i++)
        {
            listenForCall(server_socket);
            Object mb_object = personal_discussion_vector.lastElement();
            if(mb_object instanceof Request)
            {
                Request mb_request = (Request)personal_discussion_vector.lastElement();

                //boolean method = (mb_request.motivation.attempted_method).equals("monster-barring");
                //String motivation = mb_request.motivation.attempted_method;

                //window.writeToFrontEnd("mb_request.motivation.attempted_method is " + method);
                //window.writeToFrontEnd("(mb_request.motivation.attempted_method).equals(\"monster-barring\") is " + motivation);

                if(!(mb_request==null) && (mb_request.motivation.attempted_method).equals("monster-barring"))
                {
                    //	window.writeToFrontEnd("in here - just adding " + mb_request + " to output");
                    output.addElement(mb_request);
                }
                //  group_agenda.agenda.insertElementAt(current_request,0);
            }
        }
        /**

         Vector requests_from_students = new Vector();
         for (int i=0; i<responses_to_responses.size(); i++)
         {
         if(responses_to_responses.elementAt(i) instanceof Response)
         {
         Response resp = (Response)responses_to_responses.elementAt(i);
         current_responses.addElement(resp);
         }

         if(responses_to_responses.elementAt(i) instanceof Request)
         {
         Request req = (Request)responses_to_responses.elementAt(i);
         requests_from_students.addElement(req);
         }
         }


         for(int i=0; i<requests_from_students.size(); i++)
         {
         Request req = (Request)responses_to_responses.elementAt(i);
         group_agenda.insertElementAt(req,0);
         }
         */
        //window.writeToFrontEnd("at end of listenForRequestsToResponses, output is " + output);
        return output;
    }


    /** listens for n responses to the current responses - if any of the
     students requests to perform monster-barring then inserts tghis
     into the group agenda. currently any other response is ignored*/
    //group_agenda.agenda
    public Vector listenForRequestsToResponses(ServerSocket server_socket, Request current_request, int num_students)
    {
        window.writeToFrontEnd("started listenForRequestsToResponses");
        Vector output = new Vector();

        for(int i=0; i<num_students; i++)
        {
            //current_request = null;
            //current_request = new Request();
            window.writeToFrontEnd("before listenForCall, current_request is "+ current_request);
            listenForCall(server_socket);
            window.writeToFrontEnd("after listenForCall, current_request is "+ current_request);
            window.writeToFrontEnd("current_request==null is " + current_request==null);

            boolean test = (current_request.motivation.attempted_method).equals("monster-barring");
            String test1 = current_request.motivation.attempted_method;

            window.writeQuietlyToFrontEnd("current_request.motivation.attempted_method is " + test1);
            window.writeQuietlyToFrontEnd("(current_request.motivation.attempted_method).equals(\"monster-barring\") is " + test);
            if(!(current_request==null) && (current_request.motivation.attempted_method).equals("monster-barring"))
            {
                window.writeQuietlyToFrontEnd("in here - just adding " + current_request + " to output");
                output.addElement(current_request);
            }
            //  group_agenda.agenda.insertElementAt(current_request,0);
        }
        /**

         Vector requests_from_students = new Vector();
         for (int i=0; i<responses_to_responses.size(); i++)
         {
         if(responses_to_responses.elementAt(i) instanceof Response)
         {
         Response resp = (Response)responses_to_responses.elementAt(i);
         current_responses.addElement(resp);
         }

         if(responses_to_responses.elementAt(i) instanceof Request)
         {
         Request req = (Request)responses_to_responses.elementAt(i);
         requests_from_students.addElement(req);
         }
         }


         for(int i=0; i<requests_from_students.size(); i++)
         {
         Request req = (Request)responses_to_responses.elementAt(i);
         group_agenda.insertElementAt(req,0);
         }
         */
        window.writeToFrontEnd("at end of listenForRequestsToResponses, output is " + output);
        return output;
    }

    protected void finalize()
    {
        try
        {
            server_socket.close();
            socket.close();
        }
        catch (IOException e)
        {
            window.writeToFrontEnd("couldn't close socket");
            //System.out.println(name + ": couldn't close socket");
            System.exit(-1);
        }

    }

}
