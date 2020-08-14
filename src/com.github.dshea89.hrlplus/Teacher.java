package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Teacher extends Agent
{
    /** The window for this agent*/

    AgentWindow window = new AgentWindow();

    /** The name of the teacher + :teacher */

    public String name_teacher = null; //change this

    /** The names of the students in the class. */

    public Vector student_names = new Vector();

    /** The number of students in the class*/

    public int num_students;

    /** The inet addresses of the students in the class. */

    Vector student_inetaddresses = new Vector();

    //Request current_request_clone = new Request();
    //Vector discussion_vector_clone = new Vector();

    /** The current request from the teacher */

    public Request current_request = new Request("");//added


    /** The set of objects received from the students. */

    public Vector received_objects = new Vector();

    /** The number of independent theory formation steps the teacher
     asks the students to make []set as flag */

    //public int num_independent_steps = 10; //[change back to 20 or 100 - experiment if nec]

    /** The default request which the teacher is making of the students
     * (used at the beginning of the run, and when discussion comes to a
     * halt) - to ask for a conjecture */


    //the number of times that the teacher has requested the students to
    //work
    public int num_requests_to_work = 0;
    public String type = "";
    public int num = 1;
    public String condition = "null";
    public Vector theory_constituent_vector = new Vector();

    public Conjecture conj = new Conjecture();
    public String attempted_method = "to get discussion started";
    public Motivation motivation = new Motivation(conj,attempted_method);

    //set the default request to a conjecture, to get the discussion started (the usual default request)
    public Request default_request = new Request(type, num, condition, theory_constituent_vector, motivation);



    //set the request to l-inc (for testing purposes)
    //Request default_request = new Request();




    /** The current request which the teacher is making of the students */

    // public Request current_request = new Request();

    /** The request to work independently which the teacher makes of the students */
    //public Request request_to_work = new Request("work independently", hr.theory.lakatos.num_independent_steps);

    /**the number of times the teacher has sent the request to work
     independently */
    int number_requests_to_work = 0;

    /** The current group agenda element which the teacher uses to determine the current request */

    GroupAgendaElement current_group_agenda_element = new GroupAgendaElement();

    /** The current responses from the students to a particular request */

    public Vector current_responses = new Vector();

    /** The group agenda for the Teacher (students do not have one) */
    public GroupAgenda group_agenda = new GroupAgenda();

    /** The Teacher's port number */
    public int port = 8500;


    /** The basic constructor */
    public Teacher()
    {
    }

    /** This constructor for the teacher takes in a set of student names,
     * a config file name and the name of the teacher. Note that at the
     * moment the teacher is NOT considered a student when sending
     * conjectures etc to file. [???]*/

    public Teacher (Vector given_student_names, String configfilename, String given_name)
    {
        name = given_name;
        name_teacher = "teacher: " + name;
        initialiseHR(configfilename, name_teacher);
        initialiseTeacher(given_student_names, configfilename, given_name);
    }

    /** This constructor for the teacher takes in a set of student names,
     * a config file name, the name of the teacher and the macro.
     */

    public Teacher (Vector given_student_names, String configfilename, String given_name, String macro)
    {
        name = given_name;
        name_teacher = "teacher: " + name;
        initialiseHR(configfilename, name_teacher, macro);
        initialiseTeacher(given_student_names, configfilename, given_name);
    }



    public void initialiseTeacher(Vector given_student_names, String configfilename, String given_name)
    {
        window.setTitle(name_teacher); //set the title of its window to its name
        window.port = port;
        this.port = port;
        student_names = (Vector)given_student_names;

        determineInitialRequest();

        //take teacher's name out of vector of student_names - [MESSY]
        student_names.removeElementAt(student_names.size()-1);

        //determine how many students there are
        num_students = student_names.size();


        //create the teacher's ServerSocket(port). If address is already
        //in use, try again with a port number of 20 more

        port = port;

        while(server_socket==null)
        {

            try
            {
                server_socket = new ServerSocket(port);
            }

            catch(BindException e)
            {
                port = port+20;
            }

            catch(IOException e)
            {
                window.writeToFrontEnd("IOException: can't make ServerSocker" + e);
            }
        }

        InetAddress my_hostname = null;

        try
        {
            my_hostname = InetAddress.getByName(name);
        }

        catch(Exception e)
        {
            System.out.println("Problem getting my_hostname: " + e);
        }

        window.writeToFrontEnd("my name is :" + name);
        window.writeToFrontEnd("my hostname is :" + my_hostname);
        window.writeToFrontEnd("my port is " + port);
        window.writeToFrontEnd("student_names are " + student_names);
        window.drawLine();
    }


    public static void main(String args[])
    {
        String configfilename  = args[0];
        String name = args[1];
        Vector agent_names = new Vector();
        int agents_start_at = 2;
        String macro = new String();

        if(args.length>=2)
        {
            String poss_macro = args[2];
            if(poss_macro.endsWith(".hrm"))
            {
                macro = poss_macro;
                agents_start_at++;
            }
        }

        for (int i=agents_start_at; i<args.length; i++)
        {
            agent_names.addElement(args[i]);
        }


        Teacher teacher = new Teacher();
        if(macro==null)
            teacher = new Teacher(agent_names, configfilename, name);

        else
            teacher = new Teacher(agent_names, configfilename, name, macro);

        teacher.start(); //starts run method

    }


    public void run()
    {


        getStudentInetAddresses(student_names);
        //window.writeToFrontEnd("getStudentInetAddresses(" + student_names + ")");

        //the teacher does an initital loop of 20 steps to start with
        boolean finished_initial_loop = false;
        while(finished_initial_loop==false)
        {
            if (hr.theory.step_counter >= 20)
            {
                hr.theory.step_counter = 0;
                finished_initial_loop = true;
            }
        }


        //fix code for communal_piecemeal_exclusion for Teacher
        //hr.theory.use_communal_piecemeal_exclusion = false;//true
        window.writeToFrontEnd("done initial loop");
        window.writeToFrontEnd("hr.theory.use_monster_barring_check is " + hr.theory.use_monster_barring);

        window.writeToFrontEnd("hr.theory.make_near_equivalences is " + hr.theory.make_near_equivalences);
        window.writeToFrontEnd("hr.theory.use_communal_piecemeal_exclusion is " + hr.theory.use_communal_piecemeal_exclusion);
        window.writeToFrontEnd("hr.theory.teacher_requests_nonexists is " + hr.theory.teacher_requests_nonexists);
        window.writeToFrontEnd("hr.theory.use_lemma_incorporation_check is " + hr.theory.use_lemma_incorporation);

        window.writeToFrontEnd("hr.theory.lakatos.num_independent_steps is " +  hr.theory.lakatos.num_independent_steps);




        window.drawLine();

        //testing starts here

        if(hr.theory.use_lemma_incorporation)
        {
            String conjs_to_force = hr.theory.front_end.force_conjecture_text.getText();

            ProofScheme proofscheme = new ProofScheme(hr.theory,conjs_to_force);

            String macro_chosen = hr.theory.front_end.macro_list.getSelectedItem();
            System.out.println("Teacher: macro_chosen is " + macro_chosen);

            System.out.println("proofscheme.conj is " + proofscheme.conj.writeConjecture());
            System.out.println("proofscheme.proof_vector is: ");
            for(int i=0;i<proofscheme.proof_vector.size();i++)
            {
                System.out.println(((Conjecture)proofscheme.proof_vector.elementAt(i)).writeConjecture());
            }

            System.out.println("I'm the teacher, and I'm starting reconstruct proofscheme here");

            Conjecture global_conj = (proofscheme.conj).reconstructConjecture(hr.theory, window);
            System.out.println("Teacher: global conj is " + global_conj.writeConjecture());
            System.out.println("Teacher: proofscheme.proof_vector.size() is " + proofscheme.proof_vector.size());

            for(int i=0;i<proofscheme.proof_vector.size();i++)
            {
                Conjecture conjecture = (Conjecture)proofscheme.proof_vector.elementAt(i);
                System.out.println("\n\n\nTeacher: ******at the moment we've got conjecture " + i + " is: " +  conjecture.writeConjecture());
                Conjecture current_local_conj = conjecture.reconstructConjecture(hr.theory, window);

                System.out.println("Teacher: ******local_conj " + i + " is: " + current_local_conj.writeConjecture());
                System.out.println("\nTeacher: _____________________________________________\n");
            }

            System.out.println("Teacher: - DONE reconstructConjecture");

            window.writeToFrontEnd(proofscheme);

            default_request.type = "proof scheme";
            default_request.condition = "modifies";
            Vector vector_for_proofscheme = new Vector();
            vector_for_proofscheme.addElement(proofscheme);
            default_request.theory_constituent_vector = vector_for_proofscheme;
            default_request.motivation.attempted_method = "lemma-incorporation";

        }

        //testing ends here


        //omit as the students don't use each others' addresses
        //makeCallToAllStudents(student_inetaddresses, student_inetaddresses); //send everyone the student inet addresses

        while (true)
        {
            window.writeToFrontEnd("At the beginnining of this loop, we have group agenda:");
            window.writeToFrontEnd(group_agenda.agenda);
            window.writeToFrontEnd("The discussion vector is:");
            window.writeToFrontEnd(discussion_vector);


            //System.out.println("At the beginnining of this loop, we have group agenda:");
            //System.out.println(group_agenda.agenda);
            //System.out.println("The discussion vector is:");
            //System.out.println(discussion_vector);


            if(hr.theory.teacher_requests_nonexists)
                default_request.type = "NonExists";
            if(hr.theory.teacher_requests_implication)
                default_request.type = "Implication";
            if(hr.theory.teacher_requests_nearimplication)
                default_request.type = "NearImplication";
            if(hr.theory.teacher_requests_equivalence)
                default_request.type = "Equivalence";
            if(hr.theory.teacher_requests_nearequivalence)
                default_request.type = "NearEquivalence";

            if (!(group_agenda.agenda.isEmpty()))
            {
                Object object = (Object)group_agenda.agenda.elementAt(0);
                window.writeQuietlyToFrontEnd("first element in agenda is " + group_agenda.agenda.elementAt(0) + " .... ");
                if (object instanceof GroupAgendaStringElement)
                    window.writeQuietlyToFrontEnd("... this is a GroupAgendaStringElement");

                if (object instanceof GroupAgendaTheoryConstituentElement)
                    window.writeQuietlyToFrontEnd("... this is a GroupAgendaTheoryConstituentElement");

                if (object instanceof GroupAgendaVectorElement)
                    window.writeToFrontEnd("... this is a GroupAgendaVectorElement");

                if (object instanceof Request)
                    window.writeQuietlyToFrontEnd("... this is a Request");
            }

            window.writeQuietlyToFrontEnd("The current request is " + current_request);
            window.writeQuietlyToFrontEnd("current_request.isEmpty() is: " + current_request.isEmpty());
            window.writeQuietlyToFrontEnd("discussion vector: " + discussion_vector);
            window.writeToFrontEnd("current_responses: " + current_responses);

            //if group_agenda is empty, send request to run for n steps, and add the default request to the group_agenda
            if ((group_agenda.agenda).isEmpty())
            {
                window.writeQuietlyToFrontEnd("nothing in group agenda at the moment: " + group_agenda.agenda);
                Request request_to_work = new Request("work independently", hr.theory.lakatos.num_independent_steps);
                makeCallToAllStudents(port, student_inetaddresses, request_to_work);
                num_requests_to_work++;
                current_request = (Request)default_request.clone();
                group_file.writeToGroupFile("Teacher: " + current_request.toStringForFile());
                (group_agenda.agenda).addElement(current_request);
            }

            //if the first element in the group_agenda is a request, send
            //it to all the students, wait for their responses, sort their
            //responses, send all students a list of their responses, and
            //insert them into the group_agenda

            else
            if ((group_agenda.agenda).elementAt(0) instanceof Request)
            {
                current_request = (Request)(group_agenda.agenda).elementAt(0);
                //remove element from open (group_agenda) and add to closed (disc_vector) when do something with it
                (group_agenda.agenda).removeElementAt(0);
                Request current_request_clone = (Request)current_request.clone();
                discussion_vector.addElement(current_request_clone);
                makeCallToAllStudents(port, student_inetaddresses, current_request);//needs work
                listenForResponsesToCurrentRequest(server_socket, current_request, num_students, current_responses);
                makeCallToAllStudents(port, student_inetaddresses, current_responses);
                group_agenda.addResponsesToGroupAgenda(current_responses, window);//maybe switch order with below?

                Vector req_to_resp = listenForRequestsToResponses1(server_socket, current_request, num_students);
                window.writeQuietlyToFrontEnd("req_to_resp is " + req_to_resp);


                for (int i=0; i<req_to_resp.size(); i++)
                {
                    if(req_to_resp.elementAt(i) instanceof Request)
                    {
                        Request req = (Request)req_to_resp.elementAt(i);
                        window.writeQuietlyToFrontEnd(i+"th item is a request");
                        window.writeQuietlyToFrontEnd("group_agenda.agenda just now is " + group_agenda.agenda);
                        if(!current_request.isEmpty())
                        {
                            //check that the req isn't already there
                            //(to avoid duplicate requests in
                            //monster-barring)
                            if((group_agenda.agenda).elementAt(0) instanceof Request)
                            {
                                Request req_in_agenda = (Request)(group_agenda.agenda).elementAt(0);
                                if (!(req.equals(req_in_agenda)))
                                    (group_agenda.agenda).insertElementAt(req,0);
                            }
                            else
                                (group_agenda.agenda).insertElementAt(req,0);
                        }
                        window.writeQuietlyToFrontEnd("group_agenda.agenda now is ");
                        window.writeQuietlyToFrontEnd(group_agenda.agenda);
                    }
                }
            }

            //if the first element in the group_agenda is a group agenda
            //string element, take it out of the agenda and put it into
            //the discussion vector [need adding to - send to the other
            //students at least] - put into if loop below? (but need to
            //work out how to deal with String elements which don't require
            //a request - ie those which should just be taken out and put
            //into dv and nothing else done...)

            else
            if ((group_agenda.agenda).elementAt(0) instanceof GroupAgendaStringElement)
            {

                window.writeQuietlyToFrontEnd("first element is a GroupAgendaStringElement - taking it out of the agenda and putting it into the dv");
                GroupAgendaStringElement gase = (GroupAgendaStringElement)(group_agenda.agenda).elementAt(0);
                window.writeQuietlyToFrontEnd("first element is " + gase);
                GroupAgendaStringElement clone = new GroupAgendaStringElement(gase);
                discussion_vector.addElement(clone);
                //remove element from open (group_agenda) and add to closed (disc_vector) when do something with it
                if(gase.motivation.attempted_method.equals("monster-barring"))
                {

                    window.writeQuietlyToFrontEnd("gase.motivation.attempted_method is m-b");
                    current_group_agenda_element = (GroupAgendaElement)(group_agenda.agenda).elementAt(0);
                    (group_agenda.agenda).removeElementAt(0);
                    window.writeQuietlyToFrontEnd("going to determineCurrentRequest on " + current_group_agenda_element);
                    determineCurrentRequest(); //work out what the teacher wants to request here
                    window.writeQuietlyToFrontEnd("current_request is NOW " + current_request);
                    if(!current_request.isEmpty())
                    {
                        window.writeQuietlyToFrontEnd("!current_request.isEmpty() so just inserting into agenda");
                        (group_agenda.agenda).insertElementAt(current_request, 0);
                    }
                    else
                    {
                        boolean b = current_request.isEmpty();
                        window.writeQuietlyToFrontEnd("current_request.isEmpty() is " + b + ", so not going to insert anything into agenda");

                    }
                }
                else
                    (group_agenda.agenda).removeElementAt(0);
            }

            //if the first element in the group_agenda is a group agenda
            //element (i.e. a tc - or vector of entities along with
            //motivation), then take it out of agenda and put it into
            //discussion vector, determine the current request and put
            //that into the front of the group_agenda.
            else
            if ((group_agenda.agenda).elementAt(0) instanceof GroupAgendaElement)
            {
                current_group_agenda_element = (GroupAgendaElement)(group_agenda.agenda).elementAt(0);



                if(current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement)
                {
                    GroupAgendaTheoryConstituentElement gatce = (GroupAgendaTheoryConstituentElement)current_group_agenda_element;
                    GroupAgendaTheoryConstituentElement clone = new GroupAgendaTheoryConstituentElement(gatce);
                    discussion_vector.addElement(clone);
                }

                if(current_group_agenda_element instanceof GroupAgendaVectorElement)
                {
                    GroupAgendaVectorElement gave = (GroupAgendaVectorElement)current_group_agenda_element;
                    GroupAgendaVectorElement clone = new GroupAgendaVectorElement(gave);
                    discussion_vector.addElement(clone);
                }
                (group_agenda.agenda).removeElementAt(0);
                determineCurrentRequest(); //work out what the teacher wants to request here
                if(!current_request.isEmpty())
                {
                    (group_agenda.agenda).insertElementAt(current_request, 0);
                }
            }
            try
            {
                sleep(2000);
            }
            catch (Exception e)
            {
            }
            window.drawLine();

            //stop the teacher thread from running
            //hr.theory.stop_now = true;
        }
    }


    /** Given the current group agenda element, determine the next request to make of the students.
     If use_communal_piecemeal_exclusion is true, follow procedure
     - otherwise just ask for personal modifications */

    public void determineCurrentRequest()
    {
        window.writeQuietlyToFrontEnd("\n\n into determineCurrentRequest()");
        window.writeQuietlyToFrontEnd("current_request is " + current_request);
        window.writeQuietlyToFrontEnd("current_group_agenda_element is " + current_group_agenda_element + "\n\n");
        //hr.theory.use_communal_piecemeal_exclusion=false;//change to false for exception-barring egs.

        Request current_request2 = new Request();
        boolean written_to_group_file = false;

        if(hr.theory.use_communal_piecemeal_exclusion==true)
        {
            if(current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement)
            {
                window.writeToFrontEnd("got a GroupAgendaTheoryConstituentElement");
                GroupAgendaTheoryConstituentElement current_group_agenda_tc_element = (GroupAgendaTheoryConstituentElement)current_group_agenda_element;
                //if just got a conjecture then ask for counters - may need more work

                if (current_group_agenda_tc_element.theory_constituent instanceof Conjecture)
                {
                    Conjecture conjecture_to_discuss = (Conjecture)current_group_agenda_tc_element.theory_constituent;

                    if(current_group_agenda_tc_element.motivation.attempted_method.equals("to get discussion started"))
                    {
                        current_request.type = "Entity";
                        current_request.num = 100;//another way of saying 'all'
                        current_request.condition = "breaks";
                        Vector conj_vector = new Vector();
                        conj_vector.addElement(conjecture_to_discuss);
                        current_request.theory_constituent_vector = conj_vector;
                        current_request.motivation.conjecture_under_discussion = conjecture_to_discuss;
                        current_request.motivation.attempted_method = "communal piecemeal exclusion";
                    }
                    boolean b = motivation.conjecture_under_discussion.equals(conjecture_to_discuss);

                    if(!b)
                    {
                        //System.out.println("4) - this is where we want to be");
                        current_request.type = "Entity";
                        current_request.num = 100;//another way of saying 'all'
                        current_request.condition = "breaks";
                        Vector conj_vector = new Vector();
                        conj_vector.addElement(conjecture_to_discuss);
                        current_request.theory_constituent_vector = conj_vector;
                        current_request.motivation.conjecture_under_discussion = conjecture_to_discuss;
                        current_request.motivation.attempted_method = "communal piecemeal exclusion";
                    }
                }



                //if got a concept back then need to perform communal
                //piecemeal exclusion [needs work] currently just
                //assumes all counters on lhs rather than looking where
                //they are - ie only works for near implications
                if (current_group_agenda_tc_element.theory_constituent instanceof Concept)
                {
                    //window.writeToFrontEnd("got a concept");
                    Concept concept_to_discuss = (Concept)current_group_agenda_tc_element.theory_constituent;

                    //TheoryConstituent tc = concept_to_discuss.reconstructConcept(hr.theory);//test
                    //Concept reconstructed_concept_to_discuss = (Concept)tc;

                    Conjecture conj = current_request.motivation.conjecture_under_discussion;
                    Concept left_concept = new Concept();
                    Concept right_concept = new Concept();

                    if (conj instanceof Implication)
                    {
                        Implication imp = (Implication)conj;
                        left_concept = imp.lh_concept;
                        right_concept = imp.rh_concept;
                    }

                    if (conj instanceof NearImplication)
                    {
                        NearImplication nimp = (NearImplication)conj;
                        left_concept = nimp.lh_concept;
                        right_concept = nimp.rh_concept;
                    }
                    if (conj instanceof Equivalence)
                    {
                        Equivalence equiv = (Equivalence)conj;
                        left_concept = equiv.lh_concept;
                        right_concept = equiv.rh_concept;
                    }

                    if (conj instanceof NearEquivalence)
                    {
                        NearEquivalence nequiv = (NearEquivalence)conj;
                        left_concept = nequiv.lh_concept;
                        right_concept = nequiv.rh_concept;
                    }



                    /** get this working - T needs to be able to reconstruct concepts
                     String agenda_line = "id_of_dummy = " + reconstructed_concept_to_discuss.id + " " + left_concept.id + " negate [] ";
                     window.writeToFrontEnd("1 - agenda_line is " + agenda_line);
                     hr.theory.agenda.addForcedStep(agenda_line, false);
                     window.writeToFrontEnd("2 - ");
                     Step forcedstep = hr.theory.guider.forceStep(agenda_line, hr.theory); //sometimes gives problems - see Guide L107
                     window.writeToFrontEnd("3 - ");
                     TheoryConstituent tc_output = forcedstep.result_of_step;
                     window.writeToFrontEnd("4 - tc_output is " + tc_output.writeTheoryConstituent());

                     Vector tc_vector = new Vector();
                     if(tc_output instanceof Concept)
                     {
                     Concept new_concept = (Concept)tc_output;
                     Equivalence output = new Equivalence(new_concept, right_concept, "dummy_id");
                     tc_vector.addElement(output);
                     window.writeToFrontEnd("5 - output is " + output.writeTheoryConstituent());
                     }

                     if(tc_output instanceof Conjecture)
                     {
                     Conjecture new_conj = (Conjecture)tc_output;
                     tc_vector.addElement(new_conj);
                     window.writeToFrontEnd("5 - new_conj is " + new_conj.writeTheoryConstituent());
                     //group_agenda.agenda.addElement(new_conj);
                     }

                     **/

                    //Teacher makes and sends back response herself

                    /**  Response teachers_response = new Response(tc_vector, current_request);
                     Vector teachers_response_vector = new Vector();
                     teachers_response_vector.addElement(teachers_response);
                     window.writeToFrontEnd("teachers_response_vector is " + teachers_response_vector);
                     window.writeToFrontEnd("agenda BEFORE addResponses.. is " + group_agenda.agenda);
                     group_agenda.addResponsesToGroupAgenda(teachers_response_vector);
                     window.writeToFrontEnd("agenda AFTER addResponses.. is " + group_agenda.agenda); */
                    //current_group_agenda_element = (GroupAgendaElement)(group_agenda.agenda).elementAt(0);
                    //window.writeToFrontEnd("current_group_agenda_element is now " + current_group_agenda_element);
                    //boolean t1 = current_group_agenda_element  instanceof GroupAgendaVectorElement;
                    //boolean t2 = current_group_agenda_element  instanceof GroupAgendaTheoryConstituentElement;
                    //window.writeToFrontEnd("current_group_agenda_element instanceof GroupAgendaVectorElement is " + t1);
                    //window.writeToFrontEnd("current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement is " + t2);
                    //Motivation new_mot = current_request.motivation; - put back in - 12/04
                    //current_request = new Request("Entity", 1, "performs communal piecemeal exclusion", tc_vector, new_mot); - 12/04
		/*{
		  current_request.type = "Conjecture";
		  current_request.condition = "performs communal piecemeal exclusion";
		  Vector concept_vector = new Vector();
		  concept_vector.addElement(concept_to_discuss);
		  current_request.theory_constituent_vector = concept_vector;
		  */

                    current_request.type = "Conjecture";
                    current_request.condition = "modifies";

                    Vector conj_vector = new Vector();
                    conj_vector.addElement(conj);
                    current_request.theory_constituent_vector = conj_vector;
                    current_request.motivation.conjecture_under_discussion = conj;
                    current_request.motivation.attempted_method = "individual methods";
                    (group_agenda).removeElementsWithGivenMotivation(conj);
                }


                //thurs might here - debug here
                if (current_group_agenda_tc_element.theory_constituent instanceof Entity)
                {
                    Vector vector_of_entities = new Vector();
                    vector_of_entities.addElement((Entity)current_group_agenda_tc_element.theory_constituent);
                    current_request.type = "Concept";
                    current_request.condition = "covers";

                    current_request.theory_constituent_vector = vector_of_entities;
                }
            }

            //if just got a vector of counters then ask for a concept to cover them
            if(current_group_agenda_element instanceof GroupAgendaVectorElement)
            {
                window.writeQuietlyToFrontEnd("****************************** got a GroupAgendaVectorElement");
                GroupAgendaVectorElement current_group_agenda_vector_element = (GroupAgendaVectorElement)current_group_agenda_element;
                Vector vector_of_entities = (Vector)current_group_agenda_vector_element.vector;
                window.writeQuietlyToFrontEnd("vector_of_entities is " + vector_of_entities);
                if(current_group_agenda_vector_element.vector.isEmpty())
                {
                    window.writeQuietlyToFrontEnd("the vector_of_entities is empty");
                    current_request = new Request();
                }
                else
                {
                    current_request.type = "Concept";
                    current_request.condition = "covers";
                    current_request.theory_constituent_vector = vector_of_entities;
                }
            }
        }

        else
        if(current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement)
        {
            window.writeQuietlyToFrontEnd("2) got a GroupAgendaTheoryConstituentElement");
            GroupAgendaTheoryConstituentElement current_group_agenda_tc_element = (GroupAgendaTheoryConstituentElement)current_group_agenda_element;

            if (current_group_agenda_tc_element.theory_constituent instanceof Conjecture)
            {
                Conjecture conjecture_to_discuss = (Conjecture)current_group_agenda_tc_element.theory_constituent;
                current_request.type = "Conjecture";
                current_request.condition = "modifies";

                Vector conj_vector = new Vector();
                conj_vector.addElement(conjecture_to_discuss);
                current_request.theory_constituent_vector = conj_vector;
                current_request.motivation.conjecture_under_discussion = conjecture_to_discuss;
                current_request.motivation.attempted_method = "individual methods";
            }
        }


        if(current_group_agenda_element instanceof Request)
        {
            window.writeQuietlyToFrontEnd("got a Request");
        }

        if(current_group_agenda_element instanceof GroupAgendaStringElement)
        {
            window.writeToFrontEnd("got a GroupAgendaStringElement");
            GroupAgendaStringElement cgase = (GroupAgendaStringElement)current_group_agenda_element;

            //if current ga element is a vote about whether to
            //perform monster-barring on a particular entity, then
            //(1) look through group agenda for other m-b votes on
            //the same entity, (2) take them all out of the group
            //agenda, clone them and put into discussion vector,
            //(3) count them and construct a request to either
            //addEntityToTheory (if more vote to accept the
            //entity) or removeEntityFromTheory (if more vote to
            //bar the entity)
            if(cgase.motivation.attempted_method.equals("monster-barring"))
            {
                int votes_to_accept_entity = 0;
                int votes_to_bar_entity = 0;

                if(cgase.string.equals("reject proposal to bar entity"))
                    votes_to_accept_entity++;

                if(cgase.string.equals("accept proposal to bar entity"))
                    votes_to_bar_entity++;

                window.writeToFrontEnd("going through agenda to look for other votes");
                window.writeToFrontEnd("before looking, got votes_to_accept_entity is " + votes_to_accept_entity
                        + ", and votes_to_bar_entity is " + votes_to_bar_entity);

                window.writeToFrontEnd("before looking, agenda is " + agenda);

                for (int i=0; i<group_agenda.agenda.size(); i++)
                {
                    GroupAgendaElement gae = (GroupAgendaElement)(group_agenda.agenda).elementAt(i);
                    if(gae instanceof GroupAgendaStringElement)
                    {
                        GroupAgendaStringElement gase = (GroupAgendaStringElement)(group_agenda.agenda).elementAt(0);
                        if(gase.motivation.attempted_method.equals("monster-barring")
                                && gase.motivation.entity_under_discussion.name.equals(cgase.motivation.entity_under_discussion.name))
                        {
                            window.writeToFrontEnd("gase is " + gase);

                            if(gase.string.equals("reject proposal to bar entity"))
                                votes_to_accept_entity++;
                            if(gase.string.equals("accept proposal to bar entity"))
                                votes_to_bar_entity++;


                            GroupAgendaStringElement clone = new GroupAgendaStringElement(gase);
                            discussion_vector.addElement(clone);
                            (group_agenda.agenda).removeElementAt(i);
                            i--;
                        }
                    }
                }

                window.writeToFrontEnd("been through agenda to look for other votes");
                window.writeToFrontEnd("votes_to_accept_entity is " + votes_to_accept_entity
                        + ", and  votes_to_bar_entity is " + votes_to_bar_entity);

                window.writeQuietlyToFrontEnd("before looking, agenda is " + agenda);
                boolean votes = votes_to_accept_entity >= votes_to_bar_entity;
                window.writeToFrontEnd("votes_to_accept_entity >= votes_to_bar_entity is " + votes);

                Vector entity_vector = new Vector();
                entity_vector.addElement(cgase.motivation.entity_under_discussion);

                if (votes_to_accept_entity > votes_to_bar_entity)
                //to do  - decide what the default choice is, if votes_to_accept_entity = votes_to_bar_entity
                {
                    window.writeToFrontEnd("votes_to_accept_entity >= votes_to_bar_entity");
                    current_request.type = "";
                    current_request.num = 0;
                    //current_request.condition = "perform addEntityToTheory";
                    current_request.condition = "";
                    current_request.theory_constituent_vector = entity_vector;
                    current_request.motivation = cgase.motivation;
                    current_request.string_object = "perform addEntityToTheory";
                    //String string_object = "";
                    //int num_steps;


                    //current_request = new Request("", 0, "perform addEntityToTheory", entity_vector, cgase.motivation);
                    //may want to take out other requests in group_agenda.agenda to monster-bar the entity
                }
                else
                {
                    current_request.type = "";
                    current_request.num = 0;
                    //current_request.condition = "perform removeEntityFromTheory";
                    current_request.condition = "";
                    current_request.theory_constituent_vector = entity_vector;
                    current_request.motivation = cgase.motivation;
                    current_request.string_object = "perform downgradeEntityToPseudoEntity";

                    for(int i=0;i<current_request.theory_constituent_vector.size();i++)
                    {
                        Object o = (Object)current_request.theory_constituent_vector.elementAt(i);
                    }
                    //maybe here - need to go through agenda and remove
                    //the entity which the teacher has just told the
                    //students to downgrade -- thurs night.

                    /** testing starts here */

                    Request additional_request = new Request();
                    additional_request.type = "Entity";
                    additional_request.num = 100;//another way of saying 'all'
                    additional_request.condition = "breaks";
                    Vector conj_vector = new Vector();

                    Conjecture conj_to_add = new Conjecture();
                    window.writeToFrontEnd("the last element of the dv is "
                            + discussion_vector.elementAt(discussion_vector.size()-1));

                    boolean b1 = discussion_vector.elementAt(discussion_vector.size()-1) instanceof Request;
                    boolean b2 = discussion_vector.elementAt(discussion_vector.size()-1) instanceof GroupAgendaElement;

                    window.writeToFrontEnd("got a Request is " + b1);
                    window.writeToFrontEnd("got a GroupAgendaElement is " + b2);

                    if(discussion_vector.elementAt(discussion_vector.size()-1) instanceof Request)
                    {

                        Request req = (Request)discussion_vector.elementAt(discussion_vector.size()-1);
                        conj_to_add = req.motivation.conjecture_under_discussion;

                    }

                    if(discussion_vector.elementAt(discussion_vector.size()-1) instanceof GroupAgendaElement)
                    {
                        GroupAgendaElement gae = (GroupAgendaElement)discussion_vector.elementAt(discussion_vector.size()-1);
                        conj_to_add =  gae.motivation.conjecture_under_discussion;

                    }


                    window.writeToFrontEnd("conj_to_add is " + conj_to_add.writeConjecture());

                    conj_vector.addElement(conj_to_add);//current_request.motivation.conjecture_under_discussion
                    additional_request.theory_constituent_vector = conj_vector;
                    additional_request.motivation.attempted_method = "communal piecemeal exclusion";


                    /** testing ends here */


                    //need to take out all counters to the conj
                    // current_request = new Request("", 0, "perform removeEntityFromTheory", entity_vector, cgase.motivation);
                    for(int i=0; i<entity_vector.size();i++)
                    {
                        Entity entity_to_remove = (Entity)entity_vector.elementAt(i);
                        //group_agenda.removeEntity(entity_to_remove, window);
                        group_agenda.removeElementsWithConjecture(conj_to_add, window);

                    }

                    (group_agenda.agenda).insertElementAt(additional_request,0);
                    window.writeToFrontEnd("FRIDAY -- just inserted " + additional_request + "into the agenda");

                }
                window.writeQuietlyToFrontEnd("2) current_request after collecting votes is " + current_request.toString());

                group_file.writeToGroupFile("Teacher: We'll decide democratically with the default being that " +
                        cgase.motivation.entity_under_discussion.name + " is a monster. Can you all "+ current_request.toStringForFile());
                written_to_group_file = true;
            }
        }
        if(!written_to_group_file)
            if(!(current_request.type.equals("")))
                group_file.writeToGroupFile("Teacher: " + current_request.toStringForFile());

    }


    /** Given a vector of responses to a request, order it best first */

    public void orderResponses(Vector responses)
    {
        Vector ordered_responses = new Vector();

        for(int i=0; i<responses.size(); i++)
        {
            responses.elementAt(i);
        }
    }

    /** Given a vector of responses to a request, combine the
     * responses and return the resulting vector. This is used for
     * combining everyone's the counters to a conjecture  */

    public Vector combineResponses(Vector responses)
    {
        Vector combined_responses = new Vector();

        for(int i=0; i<responses.size();i++)
        {
            Response current_response = (Response)responses.elementAt(i);

            Vector curr_resp_vector = current_response.response_vector;
            if (!(curr_resp_vector.isEmpty()))

                if (curr_resp_vector.elementAt(0) instanceof Entity)
                {
                    for(int j=0; j<curr_resp_vector.size(); j++)
                    {
                        Entity entity_to_add = (Entity)curr_resp_vector.elementAt(j);
                        combined_responses.addElement(entity_to_add);
                    }
                }
        }

        return combined_responses;
    }






    // teacher sorts its received conjectures - should return a
    // SortableVector of conjectures - best first//

    public Conjecture sortReceivedConjectures(Vector received_conjectures)
    {
        SortableVector sortable_vector = new SortableVector();
        for (int i=0; i<received_conjectures.size(); i++)
        {
            Conjecture conj = (Conjecture)received_conjectures.elementAt(i);
            sortable_vector.addElement(conj, "interestingness");
        }
        received_objects.removeElementAt(sortable_vector.size()-1);
        return (Conjecture)sortable_vector.lastElement();
    }


    /** Given a vector of student names, adds each student's InetAddress
     to the vector student_inetaddresses, where each is followed by
     their host number (stored as a String).  eg. student_inetaddresses
     = [lenny_inet_address, 8000, lisa_inet_address, 8001] */

    public void getStudentInetAddresses(Vector student_names)
    {

        for(int i=0; i<student_names.size(); i++)
        {
            String current_student = (String)student_names.elementAt(i);
            int index = current_student.indexOf(':');
            String current_student_name = current_student.substring(0,index);
            String current_student_port_string = current_student.substring(index+1);

            InetAddress student_hostname = null;

            try
            {
                student_hostname = InetAddress.getByName(current_student_name);
                student_inetaddresses.addElement(student_hostname);
                student_inetaddresses.addElement(current_student_port_string);
            }

            catch(Exception e)
            {
                System.out.println("Problem getting LocalHost: " + e);
            }
        }
    }


    //****************************************************************************************************


    // teacher determines focus of discussion - same as determineCurrentRequest//
    public Vector determineFocus(Vector received_objects)
    {
        Vector focus =  new Vector();
        Object object_to_discuss = "";
        String required_type = "";
        String request = "";

        //if got conjectures, pick the most interesting and request counters

        if (received_objects.isEmpty())
        {
            return new Vector();
        }

        Object received_object = received_objects.elementAt(0);

        if (received_object instanceof Conjecture)
        {
            Conjecture most_interesting_conj =
                    sortReceivedConjectures(received_objects);

            //sort into best first and take first

            object_to_discuss = most_interesting_conj;
            required_type = "Entity";
            request = "breaks conjecture";
        }

        if (received_object instanceof Entity)
        {
            object_to_discuss = "counters";
            required_type = "Concept";
            request = "covers counters";
        }

        if (received_object instanceof Concept)
        {
            // need to find the concept which covers the most
            // counters (or is the most interesting) and perform concept-barring
        }

        focus.addElement(object_to_discuss);
        focus.addElement(required_type);
        focus.addElement(request);
        return focus;
    }


  /*
  public void printing(Vector vector)
  {

    AgentWindow ag = this.window;
    ag.writeToFrontEnd(vector);
  }
  */

    /** test -- monday. Usally this would read from the macro and
     * construct a proof scheme from that. It should possibly be in
     * UserFunctions */

    public ProofScheme constructProofScheme()
    {
        Conjecture main_conj = new Conjecture();
        Conjecture conj_step1 = new Conjecture();
        Vector v = new Vector();

        for(int i=0; i<hr.theory.conjectures.size();i++)
        {
            Conjecture c = (Conjecture)hr.theory.conjectures.elementAt(i);
            if (c.id.equals("2"))
                main_conj = c;
            if (c.id.equals("6"))
                conj_step1 = c;

        }

        String agenda_line = new String();


        ProofSchemeElement step1 = new ProofSchemeElement(agenda_line, conj_step1);
        v.addElement(step1);
        ProofScheme proof_scheme = new ProofScheme(main_conj, v);

        return proof_scheme;
    }

    public void determineInitialRequest()
    {
        //determine the default_request

        if(hr.theory.teacher_requests_conjectures)
            default_request.type = "Conjecture";
        if(hr.theory.teacher_requests_nonexists)
            default_request.type = "NonExists";
        if(hr.theory.teacher_requests_implication)
            default_request.type = "Implication";
        if(hr.theory.teacher_requests_nearimplication)
            default_request.type = "NearImplication";
        if(hr.theory.teacher_requests_equivalence)
            default_request.type = "Equivalence";
        if(hr.theory.teacher_requests_nearequivalence)
            default_request.type = "NearEquivalence";
        //end
    }
}
