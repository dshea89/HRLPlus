package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.Hashtable;

/** This is the class for a student. */

public class Student extends Agent implements Serializable
{

    /** The window for this agent*/

    AgentWindow window = new AgentWindow();

    /** Student name */

    String student_name = null;

    /** The port number for each student - a class variable */

    public int port;

    /** The host name of the teacher. */

    InetAddress teacher_hostname;

    /** The port number of the teacher. */

    int teacher_port = 8500;


    //variables for surrender

//   boolean test_modifications = false;

//   boolean test_interestingness = true; //here

//   boolean test_average_interestingness = false;

//   boolean test_plausibility = false;

//   boolean test_domain_applications = false;

//   int modification_threshold = 2;

//   double interestingness_threshold = 0.5;

//   double plausibility_threshold = 0.7;

//   double domain_of_application_threshold = 0.5;


//variables for monster-barring

    /** The max percentage of conjectures which an entity may break
     before the agent votes to perform monster-barring on the entity
     and exclude it from the theory -- should be a flag. Reset to
     10.0 */

    //double monster_barring_min = 10.0; //change to 14% for eg  of adding to theory

    /** The type of monster-barring to be done. This can be
     * vaguetovague, vaguetospecific or specifictospecific
     */

    //String monster_barring_type = "vaguetospecific";
    //String monster_barring_type = "vaguetovague";

    /** The max percentage of conjectures which an entity may
     culprit_break, i.e. force all the other entities in the theory to
     break as well, before the agent votes to perform monster-barring
     on the entity and exclude it from the theory -- should be a
     flag.*/

    //int  monster_barring_culprit_min = 10;

    int num_independent_work_stages_so_far = 1;

    int num_loops_so_far = 1;

    boolean test_react_to = false;

    //double threshold_to_add_conj_to_theory = 0.0;

    // double threshold_to_add_concept_to_theory = 0.0;

    /* the max number of steps to take*/
    //int max_num_steps = 100; //change to 1000

    /* the max number of independent work stages to take*/
    //int max_num_independent_work_stages = 10;

    boolean hyp_mode = false; //change to true when running in hypothesis mode [??]

    /** Whether the students are still on the first request or not*/
    //boolean first_request = true;

    /** The current responses from the students to a particular request */

    //public Vector current_responses = new Vector();


    /** The basic constructor */
    public Student()
    {  }

    /** This constructs a student given the config file name
     * and the name of this student. */

    public Student(String configfilename, String given_name, InetAddress teacher_hostname, int port)
    {
        name = given_name;
        student_name = "student: " + name;
        initialiseHR(configfilename, student_name);
        initialiseStudent(configfilename, given_name, teacher_hostname, port);
    }

    /** This constructs a student given the config file name, name of
     * the student and the macro. */

    public Student(String configfilename, String given_name, String macro, InetAddress teacher_hostname, int port)
    {
        name = given_name;
        student_name = "student: " + name;
        initialiseHR(configfilename, student_name, macro);
        initialiseStudent(configfilename, name, teacher_hostname, port);
    }

    public void initialiseStudent(String configfilename, String given_name, InetAddress teacher_hostname, int port)
    {
        window.setTitle(student_name);
        window.port = port;

        this.teacher_hostname = teacher_hostname;
        this.discussion_vector = discussion_vector;
        //setting the port num (different for each agent - so can run two agents on one machine)
        this.port = port;
        this.server_socket = server_socket;


        //create the student's  ServerSocket(port). If address is already
        //in use, try again with a port number of 20 more

        while(server_socket==null)

        {
            try
            {
                server_socket = new ServerSocket(port);
            }
            //if address is already in use, try again with a port number of 20 more
            catch(BindException e)
            {
                port = port+20;
            }

            catch(IOException e)
            {
                window.writeToFrontEnd("IOException: can't make ServerSocker" + e);//should exit here
            }
        }

        window.writeToFrontEnd("my name is: " + name);
        window.writeToFrontEnd("my port is " + port);
        window.writeToFrontEnd("teacher_hostname is: " + teacher_hostname);
        window.writeToFrontEnd("teacher port is: " + teacher_port);
        window.drawLine();
    }



    /** The method run is a loop which tells the student to run for 100
     * steps and then wait for a message from the Teacher -
     * listenForCall(). The message will be a request in the form
     * supply(<type>, <num>, <condition>, <object under discussion>),
     * eg. supply(Entity, Break, <conjecture>). The student then responds
     * to request - respondToRequest(), and finally sends its reponse to
     * the teacher. It then repeats the loop, running for another 100
     * steps, etc.  */


    public void run()
    {
        //get list of others in class
        //listenForCall(server_socket);
        //Vector student_inetaddresses = discussion_vector.elementAt(0); //here
        //check your flags are working


        //student sends the teacher its port number
        //makeCall(teacher_hostname, teacher_port, port);
        //window.writeToFrontEnd("called " + teacher_hostname + "to say my port is " + port);

        while(true)
        {
            //if(hr.theory.steps_taken>=max_num_steps)

            if(num_independent_work_stages_so_far>=hr.theory.lakatos.max_num_independent_work_stages)
            {
                window.writeToFrontEnd("I've done " + num_independent_work_stages_so_far
                        + " independent work stages now, so I'm stopping.");
                writeVariableInformation();
                String theory_report = hr.theory.theoryReport();
                window.writeToFrontEnd("My theory report follows. \n\n" + theory_report);
                System.out.println("Hi, I'm "+port+", and  my theoryReport is: \n" + theory_report);
                System.exit(0);
                break;
            }
            window.writeQuietlyToFrontEnd("just started loop: " + num_loops_so_far);

            //check method flags working OK


            // //test reactTo

// 	hr.thoery.react.reactTo(, "new_step");
//  	if(test_react_to)
//  	  testReact(hr);

//  	boolean test_react_to = false;

//  	//end test reactTo

// 	//test relatesConcepts(Vector concepts, Theory theory)
// 	//get some concepts
// 	Vector concepts =  new Vector();
// 	if(hr.theory.concepts.size()>2)
// 	  {
// 	    for(int i=0; i<2;i++)
// 	      {
// 		Concept concept_to_add = (Concept)hr.theory.concepts.elementAt(i);
// 		concepts.addElement(concept_to_add);
// 	      }
// 	  }
// 	for(int i=0; i<hr.theory.conjectures.size();i++)
// 	  {
// 	    Conjecture current_conj = (Conjecture)hr.theory.conjectures.elementAt(i);

// 	    boolean b = current_conj.relatesAllConcepts(concepts, hr.theory);
// 	    System.out.println("current_conj.relatesAllConcepts returns " + b + "\n\n");

// 	  }

// 	//end test relatesConcepts(Vector concepts, Theory theory)



            //   EulerProofScheme(Concept given_vertices_concept, Concept given_edges_concept, Concept given_faces_concept,
            //	       Concept given_vertices_on_edges_concept, Concept given_faces_on_edges_concept)

            // end test for lemma incorp:

            listenForCall(server_socket);// makes an incoming phone to this number and waits for it to ring - put in own port number
            //Request current_request = getCurrentRequestfromDiscussionVector();

            //	stopIt()


// 	//run for num_steps then stop
// if (button_title.equals("Continue"))
// 	  {
// 	    hr.theory.front_end.start_button.setEnabled(false);
// 	    hr.theory.front_end.stop_button.setEnabled(true);
// 	    hr.theory.front_end.step_button.setEnabled(false);
// 	    hr.theory.stop_now = false;
// 	    hr.theory.use_front_end = true;
// 	    hr.theory.getFrontEndSettings();
// 	    int number_to_complete =
// 	      (new Integer(hr.theory.front_end.required_text.getText())).intValue();
// 	    hr.theory.formTheoryUntil(number_to_complete,hr.theory.front_end.required_choice.getSelectedItem());
// 	  }


// if (button_title.equals("Stop"))
// 	  {
// 	    macro_to_complete = "";
// 	    hr.theory.front_end.macro_text.select(0,0);
// 	    hr.theory.front_end.start_button.setLabel("Continue");
// 	    hr.theory.front_end.stop_button.setLabel("Kill");
// 	    hr.theory.stop_now = true;
// 	  }



            // Theory Formation Stage - HR runs for num_steps (default = 100 - change in Teacher)
            if(current_request.string_object.equals("work independently"))
            {
                //first evaluate the discussion_vector and take what is
                //considered to be interesting into individual theories

                //addToTheoryFromDiscussionVector();

                int num_steps = current_request.num_steps;

                window.writeToFrontEnd("IWP " + num_independent_work_stages_so_far + " (" + num_steps + " steps)");

                //if (button_title.equals("Continue"))
                //if(hr.theory.step_counter <= num_steps)
                // {
                //	hr.theory.front_end.start_button.setEnabled(false);
                //	hr.theory.front_end.stop_button.setEnabled(true);
                //	hr.theory.front_end.step_button.setEnabled(false);
                //	hr.theory.stop_now = false;
                //	hr.theory.use_front_end = true;
                //	hr.theory.getFrontEndSettings();
                //	int number_to_complete =
                //	  (new Integer(hr.theory.front_end.required_text.getText())).intValue();
                //	hr.theory.formTheoryUntil(number_to_complete,hr.theory.front_end.required_choice.getSelectedItem());
                // }

                hr.theory.front_end.start_button.setEnabled(false);
                hr.theory.front_end.stop_button.setEnabled(true);
                hr.theory.front_end.step_button.setEnabled(false);
                hr.theory.stop_now = false;
                hr.theory.use_front_end = true;
                hr.theory.getFrontEndSettings();
                int number_to_complete =
                        (new Integer(hr.theory.front_end.required_text.getText())).intValue(); //??
                hr.theory.formTheoryUntil(number_to_complete,hr.theory.front_end.required_choice.getSelectedItem());//??

                boolean enter_discussion_phase = false;
                while(enter_discussion_phase==false)
                {
                    //window.writeToFrontEnd("hr.theory.step_counter is: " + hr.theory.step_counter);


                    //window.writeToFrontEnd("total number of theory formation steps is:  " + i);
                    if (hr.theory.step_counter > num_steps)

                    {
                        //window.writeToFrontEnd("in here****");
                        enter_discussion_phase = true;
                        hr.theory.stop_now = true;
                    }

                }

                hr.theory.macro_to_complete = "";
                hr.theory.front_end.macro_text.select(0,0);
                hr.theory.front_end.start_button.setLabel("Continue");
                hr.theory.front_end.stop_button.setLabel("Kill");
                hr.theory.step_counter = 0;
                num_independent_work_stages_so_far++;




                //TheoryConstituent tc_test = hr.theory.lakatos.testForce(hr.theory.concepts,hr.theory);
                //window.writeToFrontEnd("tc_test is " + tc_test);

                if(hr.theory.teacher_requests_nonexists)
                    current_request.type = "NonExists";
                if(hr.theory.teacher_requests_implication)
                    current_request.type = "Implication";
                if(hr.theory.teacher_requests_nearimplication)
                    current_request.type = "NearImplication";
                if(hr.theory.teacher_requests_equivalence)
                    current_request.type = "Equivalence";
                if(hr.theory.teacher_requests_nearequivalence)
                    current_request.type = "NearEquivalence";

                window.writeQuietlyToFrontEnd("current_request.type is " + current_request.type);
                window.writeToFrontEnd("I have entities " + hr.theory.entities);

                // test for lemma incorp:
                //window.writeToFrontEnd("tuesday - starting test now");

                //hr.theory.lakatos.lemmaIncorporation(hr.theory, window);

                //window.writeToFrontEnd("tuesday - finishing test now");
                //end lemma incorp test

                listenForCall(server_socket);
                //window.writeToFrontEnd("tuesday - starting test now"); //here
                //Concept test_concept = (Concept)hr.theory.lakatos.test1(hr.theory.entities, hr.theory);
                //hr.theory.lakatos.test1(hr.theory.entities, hr.theory);
                //hr.theory.lakatos.test_equals(hr.theory, window);
                //window.writeToFrontEnd("tuesday - finishing test now");
            }

            //Discussion Stage
            // listenForCall(server_socket);// makes an incoming phone to this number and waits for it to ring - put in own port number
            //current_request = getCurrentRequestfromDiscussionVector();

            if(personal_discussion_vector.isEmpty())
            {
                window.writeQuietlyToFrontEnd("    **    personal_discussion_vector.isEmpty()    **");
                listenForCall(server_socket);

            }
            else

                //if just got a Request, work out response, send it, and listen for the next call
                if(personal_discussion_vector.lastElement() instanceof Request && !(current_request==null))
                {
                    current_request = (Request)personal_discussion_vector.lastElement();

                    //if(hr.theory.teacher_requests_nonexists && first_request)
                    // current_request.type = "NonExists";

                    window.writeToFrontEnd("The current request is " + current_request);
                    //first_request = false;
                    Response response = respondToRequest(current_request);
                    makeCall(port, teacher_hostname, teacher_port, response);//put in port number you want to talk to
                    listenForCall(server_socket);// makes an incoming phone to this number and waits for it to ring - put in own port number
                    respondToResponses(current_responses);
                    current_responses.clear();
                }

                else

                    //if just got a Vector (of responses - then just await further instruction)
                    if(personal_discussion_vector.lastElement() instanceof Vector)
                    {
                        window.writeQuietlyToFrontEnd("    **    LAST ELEMENT OF PDV IS Vector    **");
                        listenForCall(server_socket);
                    }
            /**

             while(!(current_request==null)) // may need testing
             {
             window.writeToFrontEnd("current_request is: " + current_request);
             window.writeToFrontEnd(".. am just about to respond to it");
             Response response = respondToRequest(current_request);
             window.writeToFrontEnd("my response is " + response);
             //makeCallToAllStudents(student_inetaddresses, response);  //all members in class?
             //can't use at the moment - it waits for response and only takes request
             makeCall(teacher_hostname, teacher_port, response);//put in port number you want to talk to
             window.writeToFrontEnd("done makeCall(), to teacher_hostname = " + teacher_hostname + ", response = " + response);
             listenForCall(server_socket);// makes an incoming phone to this number and waits for it to ring - put in own port number
             //window.writeToFrontEnd("done listenForCall(server_socket)");
             //current_request = getCurrentRequestfromDiscussionVector();
             window.writeToFrontEnd("current_request is: " + current_request);
             }
             */

            num_loops_so_far++;
            window.drawLine();

        }
    }

    /** Returns a vector of all the conjectures in the
     * personal_discussion_vector - think works - may need testing*/

    public Vector getConjecturesFromDiscussionVector() //test 3
    {
        Vector output = new Vector();
        for(int i=0; i<personal_discussion_vector.size(); i++)
        {
            if(!personal_discussion_vector.isEmpty())
            {
                if (personal_discussion_vector.elementAt(i) instanceof Response)
                {
                    Response response = (Response)personal_discussion_vector.elementAt(i);
                    for(int j=0; j<response.response_vector.size(); j++)
                    {
                        if(response.response_vector.elementAt(j) instanceof Conjecture)
                            output.addElement((Conjecture)response.response_vector.elementAt(j));
                    }
                }
            }
        }
        return output;
    }


    /** Looks in discussion_vector for the latest request (i.e. the last
     request in the discussion_vector). If there isn't a request then
     it returns null. */

    public Request getCurrentRequestfromDiscussionVector()
    {
        for(int i = discussion_vector.size()-1; i>=0; i--)
        {

            if (discussion_vector.elementAt(i) instanceof Request)
            {
                current_request = (Request)discussion_vector.elementAt(i);
                //System.out.println("The current request is: " + current_request);
                break;
            }
        }
        return current_request;
    }


    //needs work - now

    /** Given a current request, the students send back a response which
     is a Vector containing TheoryConstituent(s). response depends on
     what\'s been requested, eg if response = supply(TC, null, null), the
     student returns a vector containing the best of the TC asked required
     (either Concept or Conjecture); if response = supply(Entity, breaks,
     <conj>), the student returns a vector of counters to conj, if response
     = supply(Concept, covers, <counters>), the student returns a vector
     containing a concept to cover <counters>. NOTE - have taken out all
     the else's as they weren't working - but needs testing now*/


    public Response respondToRequest(Request current_request)
    {
        boolean test1 = current_request.theory_constituent_vector.isEmpty();
        window.writeQuietlyToFrontEnd("current_request.theory_constituent_vector.isEmpty() is " + test1);
        window.writeQuietlyToFrontEnd("current_request.type is " + current_request.type);

        if(!test1)
        {
            boolean test2 = current_request.theory_constituent_vector.elementAt(0) instanceof Entity;
            window.writeQuietlyToFrontEnd("current_request.theory_constituent_vector.elementAt(0) instanceof Entity is " + test2);
        }
        Vector response_vector = new Vector();//shouldn't need this
        Response response = new Response(response_vector, current_request);
        //response.request = current_request;

        boolean crc_null = current_request.condition.equals("null");
        boolean crtc_empty = current_request.theory_constituent_vector.isEmpty();

        window.writeQuietlyToFrontEnd("crc_null is " + crc_null);
        window.writeQuietlyToFrontEnd("crtc_empty is " + crtc_empty);

        if(current_request.condition.equals("null") && current_request.theory_constituent_vector.isEmpty())
        {
            if (current_request.type.equals("Concept"))
                response.response_vector = getNBestConcepts(current_request.num);

            else
            {
                Vector conjectures_to_avoid = getConjecturesFromDiscussionVector(); //test 1
                //window.writeToFrontEnd("---- conjectures_to_avoid is: ");
                //for(int i=0; i<conjectures_to_avoid.size();i++)
                // {
                // 	Conjecture conj = (Conjecture)conjectures_to_avoid.elementAt(i);
                //	window.writeToFrontEnd(conj.writeConjecture("ascii"));
                // }
                if (current_request.type.equals("Conjecture"))
                    response.response_vector = getNBestConjectures("Conjecture", current_request.num, conjectures_to_avoid);
                if (current_request.type.equals("NearEquivalence"))
                    response.response_vector = getNBestConjectures("NearEquivalence", current_request.num, conjectures_to_avoid);
                if (current_request.type.equals("NearImplication"))
                    response.response_vector = getNBestConjectures("NearImplication", current_request.num, conjectures_to_avoid);
                if (current_request.type.equals("Equivalence"))
                    response.response_vector = getNBestConjectures("Equivalence", current_request.num, conjectures_to_avoid);
                if (current_request.type.equals("Implication"))
                    response.response_vector = getNBestConjectures("Implication", current_request.num, conjectures_to_avoid);
                if (current_request.type.equals("NonExists"))
                    response.response_vector = getNBestConjectures("NonExists", current_request.num, conjectures_to_avoid);
                if (current_request.type.equals("Implicate"))
                    response.response_vector = getNBestConjectures("Implicate", current_request.num, conjectures_to_avoid);

                window.writeToFrontEnd("my response vector is ");
                window.writeToFrontEnd(response.response_vector);


            }
        }


        //excluding/adding an entity to the theory
        if(current_request.string_object.equals("perform addEntityToTheory"))
        {
            window.writeQuietlyToFrontEnd("***************************************************");
            window.writeQuietlyToFrontEnd("HEY HEY HEY");
            window.writeToFrontEnd("told to perform addEntityToTheory"); //need to change here too
            //hr.theory.addNewEntityToTheory(entity, concept.domain, "adding new entity to theory");
            hr.theory.addEntityToTheory(current_request.theory_constituent_vector, hr.theory, window);
            for(int i=0;i<(current_request.theory_constituent_vector).size();i++)
            {
                Entity entity = (Entity)(current_request.theory_constituent_vector).elementAt(i);//may be a class cast exception
                entity.lakatos_method = "highlighted by dialogue";
            }
            response.response_vector = new Vector();
            response.request = new Request();
            group_file.writeToGroupFile("Student " + port + ": OK - my entities are now " + hr.theory.entities);
            return response;

        }

        if(current_request.string_object.equals("perform downgradeEntityToPseudoEntity"))
        {
            window.writeToFrontEnd("told to perform downgradeEntityToPseudoEntity");


            //Tuesday - why does theory_constituent_vector contain a conjecture instead of an entity???

            //this works now
            boolean b =current_request.motivation.entity_under_discussion.entitiesContains(hr.theory.entities);
            window.writeToFrontEnd("current_request.motivation.entity_under_discussion.entitiesContains(hr.theory.entities) is "
                    + b);

            //need to change this

            if(b)
            {
                window.writeToFrontEnd("in herererer ");
                window.writeToFrontEnd("right now, the entities in my theory are " + hr.theory.entities);
                hr.theory.downgradeEntityToPseudoEntity(current_request.motivation.entity_under_discussion, window);
                current_request.motivation.entity_under_discussion.lakatos_method = "monster-barring";
                window.writeToFrontEnd("NOW however, the entities in my theory are " + hr.theory.entities);
            }

            else
            {
                hr.theory.upgradeEntityToPseudoEntity(current_request.motivation.entity_under_discussion);
                //current_request.motivation.entity_under_discussion.lakatos_method = "monster-barring";
            }


            //current_request.theory_constituent_vector
            response.response_vector = new Vector();
            response.request = new Request();
            group_file.writeToGroupFile("Student " + port + ": OK - my pseudo entities are now " + hr.theory.pseudo_entities);
            return response;
        }


        if(current_request.type.equals("Entity") && current_request.condition.equals("breaks")
                && current_request.theory_constituent_vector.elementAt(0) instanceof Conjecture)
        {

            Conjecture conjecture_under_discussion = (Conjecture)current_request.theory_constituent_vector.elementAt(0);
            window.writeQuietlyToFrontEnd("looking for entities to break " + conjecture_under_discussion.writeConjecture("ascii"));
            Conjecture reconstructed_conj = conjecture_under_discussion.reconstructConjecture(hr.theory, window);
            window.writeQuietlyToFrontEnd("reconstructed_conj is " + reconstructed_conj.writeConjecture());
            if(reconstructed_conj instanceof Implication)
            {
                Implication imp = (Implication)reconstructed_conj;
                double d = imp.lh_concept.datatable.percentageMatchWith(imp.rh_concept.datatable);
                window.writeQuietlyToFrontEnd("percentageMatchWith returns " + d);
            }


            Vector v = hr.theory.addConjectureToTheory(reconstructed_conj);
            window.writeQuietlyToFrontEnd("looking for counters");
            response.response_vector =reconstructed_conj.getCountersToConjecture();
            window.writeToFrontEnd("counters are " + response.response_vector);



            //response.response_vector = hr.theory.lakatos.getCountersToConjecture(conjecture_under_discussion); //define
        }
        //doesn't use current_request.num (assumes that the request is for *all* the counters)


        if(current_request.type.equals("Concept") && current_request.condition.equals("covers")
                && current_request.theory_constituent_vector.elementAt(0) instanceof Entity)
        {
            Vector entities_under_discussion = (Vector)current_request.theory_constituent_vector;

            Vector entity_strings = hr.theory.lakatos.fromEntitiesToStrings(entities_under_discussion);
            //response.response_vector = hr.theory.lakatos.getNConceptsWhichCoverGivenEntities(entity_strings, current_request.num); //define
            Vector output = new Vector();
            output.addElement(hr.theory.lakatos.getConceptWhichExactlyCoversGivenEntities(entity_strings));
            for(int i=0;i<output.size();i++)
            {
                Concept concept = (Concept)output.elementAt(i);
                concept.lakatos_method = "communal_piecemeal_exclusion";
            }
            response.response_vector = output;
        }


        //not sure about null
        if((current_request.motivation.attempted_method).equals("monster-barring")
                && !(current_request.motivation.entity_under_discussion==null))
        {

            Entity entity = current_request.motivation.entity_under_discussion;
            //into monster-barring. evaluating the proposal

            //extra bit -- lakatos variable for mb -- need to put this elsewhere too
            boolean use_breaks_conj_under_discussion = hr.theory.lakatos.use_breaks_conj_under_discussion;
            if(use_breaks_conj_under_discussion)
            {
                Conjecture conj = current_request.motivation.conjecture_under_discussion;
                if(conj.counterexamples.isEmpty())
                {
                    String mb_vote = "accept proposal to bar entity";
                    response.response_vector.addElement(mb_vote);
                }
                if(!(conj.counterexamples.isEmpty()))
                {
                    String mb_vote = "reject proposal to bar entity";
                    response.response_vector.addElement(mb_vote);
                }
            }

            //flag for accepting the most limiting definition
            boolean accept_strictest = hr.theory.lakatos.accept_strictest;

            if(accept_strictest)
            {
                String mb_vote = "accept proposal to bar entity";
                response.response_vector.addElement(mb_vote);
                // remove entity from theory
            }
            else
            {
                double d = hr.theory.lakatos.percentageConjecturesBrokenByEntity(hr.theory, entity, window);
                window.writeToFrontEnd(entity.name + " is breaking " + d + " % of my conjs");
                if(d > hr.theory.lakatos.monster_barring_min) //= max_num_conj_broken_by_entity
                {
                    window.writeToFrontEnd("going to accept proposal to bar entity");
                    String mb_vote = "accept proposal to bar entity";
                    response.response_vector.addElement(mb_vote);

                }
                else
                {
                    window.writeToFrontEnd("going to reject proposal to bar entity");
                    if(current_request.theory_constituent_vector.isEmpty())
                    {
                        String mb_vote = "reject proposal to bar entity";
                        response.response_vector.addElement(mb_vote);
                    }
                    else
                    {
                        Conjecture conj = current_request.motivation.conjecture_under_discussion;
                        Concept concept = new Concept();

                        for(int i = 0; i<hr.theory.concepts.size(); i++)
                        {
                            concept = (Concept)hr.theory.concepts.elementAt(i);
                            if(concept.is_object_of_interest_concept)
                                break;
                        }
                        Concept rival_def = (Concept)hr.theory.lakatos.getSpecificFromVagueDefinition(entity, true, concept.domain,
                                hr.theory, conj, window);
                        rival_def.lakatos_method = "monster-barring";
                        conj.num_modifications++;
                        conj.lakatos_method = "monster-barring";
                        window.writeToFrontEnd("-- because I think that a " + concept.domain + " is something which satisfies "
                                + rival_def.writeDefinition());
                        String mb_vote = "reject proposal to bar entity";
                        response.response_vector.addElement(mb_vote);
                        response.response_vector.addElement(rival_def);
                    }

                }
            }
        }



        //here for lakatos methods - needs work


        if(current_request.type.equals("Conjecture") && current_request.condition.equals("modifies")
                && current_request.theory_constituent_vector.elementAt(0) instanceof Conjecture)
        {
            window.writeQuietlyToFrontEnd("hr.theory.use_surrender is " + hr.theory.use_surrender + ", hr.theory.use_strategic_withdrawal is " + hr.theory.use_strategic_withdrawal + ", hr.theory.use_piecemeal_exclusion is " + hr.theory.use_piecemeal_exclusion);
            Conjecture faulty_conj = (Conjecture)current_request.theory_constituent_vector.elementAt(0);


            //test which version of reconstructConjecture is better
            //window.writeToFrontEnd("the possibly faulty conjecture is     " + faulty_conj.writeConjecture());





            Conjecture reconstructed_conj = faulty_conj.reconstructConjecture(hr.theory, window);


            if(!(reconstructed_conj instanceof NonExists))
            {
                System.out.println("adding to theory");

                Vector v = hr.theory.addConjectureToTheory(reconstructed_conj);
            }
            if(!reconstructed_conj.writeConjecture().equals(faulty_conj.writeConjecture()))
                window.writeToFrontEnd("I've reconstructed the conjecture as " + reconstructed_conj.writeConjecture()
                        + ", which is slightly different. Now I'm going to try to modify this.");

            //if the conjecture holds for all examples, then say so and return
            if((reconstructed_conj.getCountersToConjecture()).isEmpty())
            {
                //Vector output = new Vector();
                String response_to_conj = new String();
                response_to_conj = reconstructed_conj.writeConjecture() + " holds for all my examples";
                //output.addElement(response_to_conj);
                //response.response_vector = output;
                response.response_vector.addElement(response_to_conj);
                group_file.writeToGroupFile("Student " + port + ": " + response.toStringForFile());
                return response;
            }



            //else
            //window.writeToFrontEnd("The possibly faulty conjecture is " + reconstructed_conj.writeConjecture());
            //window.writeToFrontEnd("reconstructConjecture returns this " + reconstructed_conj.writeConjecture());
            //end test

            if(hr.theory.use_surrender)
            {
                window.writeToFrontEnd(" ** might surrender on " + reconstructed_conj.writeConjecture());

                boolean test_num_modifications = hr.theory.lakatos.test_number_modifications_surrender;
                int max_num_mods = hr.theory.lakatos.number_modifications_surrender;

                boolean test_interestingness = hr.theory.lakatos.test_interestingness_surrender;
                double interestingness_threshold = hr.theory.lakatos.interestingness_th_surrender;

                boolean compare_av_interestingness = hr.theory.lakatos.compare_average_interestingness_surrender;

                boolean test_plausibility = hr.theory.lakatos.test_plausibility_surrender;
                double plausibility_threshold = hr.theory.lakatos.plausibility_th_surrender;

                boolean test_domain_application = hr.theory.lakatos.test_domain_application_surrender;
                double domain_application_threshold = hr.theory.lakatos.domain_application_th_surrender;

                //	test_average_interestingness

                for(int i=0;i<hr.theory.conjectures.size();i++)
                {
                    Conjecture conjecture = (Conjecture)hr.theory.conjectures.elementAt(i);
                    hr.theory.measure_conjecture.measureConjecture(conjecture, hr.theory);
                }

                Vector counters = reconstructed_conj.getCountersToConjecture();
                double dom_of_app = reconstructed_conj.applicability / hr.theory.entities.size();
                double av_interestingness_conjs = hr.theory.averageInterestingnessOfConjectures();

                if(!(counters.isEmpty()))
                {
                    window.writeToFrontEnd("got counters, so starting to calculate whether to perform surrender");
                    window.writeToFrontEnd("counters are " + counters);
                    window.writeToFrontEnd("dom_of_app is " + dom_of_app);

                    window.writeToFrontEnd("av_interestingness_conjs is " + av_interestingness_conjs);

                    window.writeToFrontEnd("test_interestingness: " + test_interestingness);
                    window.writeToFrontEnd("reconstructed_conj.interestingness: " + reconstructed_conj.interestingness);
                    window.writeToFrontEnd("reconstructed_conj.plausibility: " + reconstructed_conj.plausibility);

                    boolean test_1a =  reconstructed_conj.num_modifications > hr.theory.lakatos.number_modifications_surrender;
                    boolean test_1 = reconstructed_conj.interestingness < hr.theory.lakatos.interestingness_th_surrender;
                    boolean test_2 = reconstructed_conj.interestingness < av_interestingness_conjs;
                    boolean test_3 = reconstructed_conj.plausibility < hr.theory.lakatos.plausibility_th_surrender;
                    boolean test_4 = dom_of_app < hr.theory.lakatos.domain_application_th_surrender;

                    window.writeToFrontEnd("test1a is " + test_1a);
                    window.writeToFrontEnd("test1 is " + test_1);
                    window.writeToFrontEnd("test2 is " + test_2);
                    window.writeToFrontEnd("test3 is " + test_3);
                    window.writeToFrontEnd("test4 is " + test_4);

                    if((test_num_modifications && reconstructed_conj.num_modifications >= max_num_mods) ||
                            (test_interestingness && reconstructed_conj.interestingness < interestingness_threshold) ||
                            (compare_av_interestingness && reconstructed_conj.interestingness < av_interestingness_conjs) ||
                            (test_plausibility && reconstructed_conj.plausibility < plausibility_threshold) ||
                            (test_domain_application && dom_of_app < domain_application_threshold ))
                    {

                        window.writeToFrontEnd("gonna perform surrender");
                        response.response_vector = hr.theory.lakatos.surrender(reconstructed_conj);

                        reconstructed_conj.lakatos_method = "surrender";
                        hr.theory.downgradeConjectureToPseudoConjecture(reconstructed_conj);

                        window.writeToFrontEnd("response.toString() is now " + response.toString());
                        window.writeToFrontEnd(" ** finished surrender");
                        group_file.writeToGroupFile("Student " + port + ": " + response.toStringForFile());
                        return response;
                    }
                }
            }


            if(hr.theory.use_piecemeal_exclusion)
            {
                window.writeToFrontEnd(" ** starting piecemeal_exclusion on " + reconstructed_conj.writeConjecture("ascii") + "      ** ");

                //test - jan
                boolean write_int_measures = false;
                if(write_int_measures)
                {
                    hr.theory.measure_conjecture.measureConjecture(reconstructed_conj,hr.theory);
                    double d = hr.theory.averageInterestingnessOfConjectures();
                    window.writeToFrontEnd("Before modification, the interestingness of the conjecture is "
                            + reconstructed_conj.interestingness);
                    window.writeToFrontEnd("reconstructed_conj.applicability is " + reconstructed_conj.applicability);
                    window.writeToFrontEnd("reconstructed_conj.normalised_applicability is " + reconstructed_conj.normalised_applicability);
                    window.writeToFrontEnd("reconstructed_conj.complexity is " + reconstructed_conj.complexity);
                    window.writeToFrontEnd("reconstructed_conj.comprehensibility is " + reconstructed_conj.comprehensibility);
                    window.writeToFrontEnd("reconstructed_conj.normalised_comprehensibility is "+reconstructed_conj.normalised_comprehensibility);
                    window.writeToFrontEnd("reconstructed_conj.surprisingness is " + reconstructed_conj.surprisingness);
                    window.writeToFrontEnd("reconstructed_conj.normalised_surprisingness is " + reconstructed_conj.normalised_surprisingness);
                    window.writeToFrontEnd("reconstructed_conj.plausibility is " + reconstructed_conj.plausibility);

                    window.writeToFrontEnd("The averageinterestingness of all of my conjectures is " + d);
                }
                //end test - jan


                Vector piecemeal_modifications = hr.theory.lakatos.piecemealExclusion(reconstructed_conj, hr.theory, window);
                for(int i=0; i<piecemeal_modifications.size(); i++)
                {
                    if(piecemeal_modifications.elementAt(i) instanceof Conjecture)
                    {
                        Conjecture conj = (Conjecture)piecemeal_modifications.elementAt(i);
                        //test - jan
                        if(write_int_measures)
                        {
                            hr.theory.measure_conjecture.measureConjecture(reconstructed_conj,hr.theory);
                            double d = hr.theory.averageInterestingnessOfConjectures();
                            window.writeToFrontEnd("After modification, the interestingness of the conjecture is "
                                    + reconstructed_conj.interestingness);
                            window.writeToFrontEnd("reconstructed_conj.applicability is " + reconstructed_conj.applicability);
                            window.writeToFrontEnd("reconstructed_conj.normalised_applicability is " + reconstructed_conj.normalised_applicability);
                            window.writeToFrontEnd("reconstructed_conj.complexity is " + reconstructed_conj.complexity);
                            window.writeToFrontEnd("reconstructed_conj.comprehensibility is " + reconstructed_conj.comprehensibility);
                            window.writeToFrontEnd("reconstructed_conj.normalised_comprehensibility is "+reconstructed_conj.normalised_comprehensibility);
                            window.writeToFrontEnd("reconstructed_conj.surprisingness is " + reconstructed_conj.surprisingness);
                            window.writeToFrontEnd("reconstructed_conj.normalised_surprisingness is " + reconstructed_conj.normalised_surprisingness);
                            window.writeToFrontEnd("reconstructed_conj.plausibility is " + reconstructed_conj.plausibility);

                            window.writeToFrontEnd("The averageinterestingness of all of my conjectures is " + d);
                        }
                        //hr.theory.measure_conjecture.measureConjecture(conj,hr.theory);
                        //window.writeToFrontEnd("After modifying, the interestingness of the modification is " + conj.interestingness);

                        //end test - jan

                        if(!(conj.lakatos_method.equals("counterexample_barring")))
                            conj.lakatos_method = "piecemeal_exclusion";
                        conj.num_modifications++;
                        (response.response_vector).addElement(conj);
                    }
                    if(piecemeal_modifications.elementAt(i) instanceof String)
                    {
                        String string = (String)piecemeal_modifications.elementAt(i);
                        (response.response_vector).addElement(string);
                    }
                }
                window.writeToFrontEnd("response is " + response.toString());
                window.writeToFrontEnd(" ** finished piecemeal_exclusion **");
            }

            if(hr.theory.use_strategic_withdrawal)
            {
                window.writeToFrontEnd(" ** starting use_strategic_withdrawal on " + reconstructed_conj + "        **");
                Vector withdrawal_modifications = hr.theory.lakatos.strategicWithdrawal(reconstructed_conj, hr.theory, window);
                for(int i=0; i<withdrawal_modifications.size(); i++)
                {
                    if(withdrawal_modifications.elementAt(i) instanceof Conjecture)
                    {
                        Conjecture conj = (Conjecture)withdrawal_modifications.elementAt(i);
                        conj.lakatos_method = "strategic_withdrawal";
                        conj.num_modifications++;
                        (response.response_vector).addElement(conj);
                    }
                    if(withdrawal_modifications.elementAt(i) instanceof String)
                    {
                        String string = (String)withdrawal_modifications.elementAt(i);
                        (response.response_vector).addElement(string);
                    }
                }
                window.writeToFrontEnd(" ** finished use_strategic_withdrawal  **");
            }
        }

        if((current_request.motivation.attempted_method).equals("lemma-incorporation"))
        {

            if(!current_request.theory_constituent_vector.isEmpty())
            {
                ProofScheme proofscheme = (ProofScheme)current_request.theory_constituent_vector.elementAt(0);
                ProofScheme proofscheme_toimprove = new ProofScheme();


                String macro_chosen = hr.theory.front_end.macro_list.getSelectedItem();
                System.out.println("Student: macro_chosen is " + macro_chosen);

                System.out.println("I'm a student, and I'm going to reconstruct the proofscheme now");

                Conjecture global_conj = (proofscheme.conj).reconstructConjecture_old(hr.theory, window);//sat - fuck
                proofscheme_toimprove.conj = global_conj;
                Vector vector_for_proofscheme = new Vector();

                System.out.println("Student: global conj is " + global_conj.writeConjecture());
                System.out.println("Student: proofscheme.proof_vector.size() is " + proofscheme.proof_vector.size());

                for(int i=0;i<proofscheme.proof_vector.size();i++)
                {
                    Conjecture conjecture = (Conjecture)proofscheme.proof_vector.elementAt(i);
                    System.out.println("\n\n\nStudent: ******at the moment we've got conjecture " + i + " is: " +  conjecture.writeConjecture());
                    Conjecture current_local_conj = conjecture.reconstructConjecture(hr.theory, window);
                    vector_for_proofscheme.addElement(current_local_conj);
                    System.out.println("Student: ******local_conj " + i + " is: " + current_local_conj.writeConjecture());
                    System.out.println("\nStudent: _____________________________________________\n");
                }

                System.out.println("Student: done reconstructConjecture");
                System.out.println("Student: macro_chosen is " + macro_chosen);

                proofscheme_toimprove.proof_vector = vector_for_proofscheme;

                ProofScheme improved_proofscheme = hr.theory.lakatos.lemmaIncorporation(hr.theory, proofscheme_toimprove,window);
                improved_proofscheme.lakatos_method = "lemma_incorporation";

                System.out.println("old proofscheme was " + proofscheme);
                System.out.println("improved_proofscheme is " + improved_proofscheme);


            }
        }


        //    else
        //   window.writeToFrontEnd("not sure what the teacher wants from me, she's requested " + current_request);

        if(!((response.toStringForFile()).equals("")))
            group_file.writeToGroupFile("Student " + port + ": " + response.toStringForFile());
        return response;
    }

    /** returns a vector of the n best conjectures in the theory,
     excluding those in conjectures_to_avoid (which is usually the
     discussion vector). It then writes it to the noticeboard and
     informs us in the dialogue box. Note that these conjectures all
     have arity 0 or 1, and the concepts in them all have arity 1 -
     changed on 2/11/03
     */

    public Vector getNBestConjectures(String conj_type, int n, Vector conjectures_to_avoid)
    {

        Vector n_best_conj = new Vector();
        double threshold_for_interestingness = 1.0; //threshold for a conj to be added to the list

        //System.out.println("port " + port + ": started getNBestConjectures");
        //System.out.println("port " + port + ": we want " + n + " conjectures of type "
        //+ conj_type + " and we want to avoid " + conjectures_to_avoid);

        boolean b1 = conjectures_to_avoid.isEmpty();
        //System.out.println("conjectures_to_avoid.isEmpty() is " + b1);

        //System.out.println("In our theory, we have: ");
        for(int i=0; i<hr.theory.conjectures.size(); i++)
        {
            Conjecture conj = (Conjecture)hr.theory.conjectures.elementAt(i);
            //System.out.println("conj " +i +" is: " + conj.writeConjecture("ascii"));
        }

        //if(!conjectures_to_avoid.isEmpty())
        //	{
        //	  for (int i=0;i<conjectures_to_avoid.size();i++)
        //	    {
        //	      Conjecture conj = (Conjecture)conjectures_to_avoid.elementAt(i);
        //	      window.writeToFrontEnd(conj.writeConjecture("ascii"));
        //	    }
        //	}

        if (n >= hr.theory.conjectures.size())
        {
            for(int i=0; i<hr.theory.conjectures.size(); i++)
            {
                Conjecture conj = (Conjecture)hr.theory.conjectures.elementAt(i);
                //System.out.println("conjectures_to_avoid is " + conjectures_to_avoid);
                //System.out.println("conj is " + conj.writeConjecture("ascii"));
                boolean b = contains(conjectures_to_avoid, conj);
                //System.out.println("contains(conjectures_to_avoid, conj) is " + b);

                if((conj.arity==1 || conj.arity==0) && !(contains(conjectures_to_avoid, conj)))//test 2
                {
                    //if conj_type is conjecture, just add it. For all
                    //other types check if the concepts in them are of
                    //arity 1


                    if(conj_type.equals("Conjecture"))
                        n_best_conj.addElement(conj);

                    if(conj_type.equals("NearEquivalence") && conj instanceof NearEquivalence)
                    {
                        NearEquivalence nequiv = (NearEquivalence)conj;
                        if (nequiv.lh_concept.arity==1 && nequiv.rh_concept.arity==1)
                            n_best_conj.addElement(nequiv);
                    }

                    if(conj_type.equals("NearImplication") && conj instanceof NearImplication)
                    {
                        NearImplication nimp = (NearImplication)conj;
                        if (nimp.lh_concept.arity==1 && nimp.rh_concept.arity==1)
                            n_best_conj.addElement(nimp);
                    }

                    if(conj_type.equals("Equivalence") && conj instanceof Equivalence)
                    {
                        Equivalence equiv = (Equivalence)conj;
                        if (equiv.lh_concept.arity==1 && equiv.rh_concept.arity==1)
                            n_best_conj.addElement(equiv);
                    }

                    if(conj_type.equals("Implication") && conj instanceof Implication)
                    {
                        Implication imp = (Implication)conj;

                        if (imp.lh_concept.arity==1 && imp.rh_concept.arity==1)//why is this a condition????
                            n_best_conj.addElement(imp);
                        if (imp.lh_concept.arity==0.00 && imp.rh_concept.arity==1)//added 2/11/03 - may need to delete
                            n_best_conj.addElement(imp);
                        if (imp.lh_concept.arity==1.00 && imp.rh_concept.arity==0.00)//added 2/11/03 - may need to delete
                        {
                            n_best_conj.addElement(imp);
                            //System.out.println("in here: n_best_conj is " + n_best_conj);
                        }
                    }

                    if(conj_type.equals("NonExists") && conj instanceof NonExists)
                    {

                        NonExists nonexist = (NonExists)conj;
                        if (nonexist.concept.arity==1)
                        {

                            n_best_conj.addElement(nonexist);
                        }
                    }

                    if(conj_type.equals("Implicate") && conj instanceof Implicate)
                    {
                        Implicate imp = (Implicate)conj;
                        if (imp.premise_concept.arity==1)
                            n_best_conj.addElement(imp);
                    }
                }
            }
        }
        else
        {
            int num_so_far = 0;
            for(int i =0; i< hr.theory.conjectures.size(); i++)
            {
                if(num_so_far<n)
                {
                    Conjecture conj = (Conjecture)hr.theory.conjectures.elementAt(i);//or lastElement?

                    boolean conj_interesting = true;
                    if(hyp_mode)
                        conj_interesting = conj.interestingness >= threshold_for_interestingness;

                    // //test
// 		 if(conj instanceof NearEquivalence)
// 		   {
// 		     NearEquivalence nequiv = (NearEquivalence)conj;
// 		     System.out.println("thinking about sending this nequiv: " +  nequiv.writeConjecture());
// 		     System.out.println("nequiv.lh_concept.arity is " + nequiv.lh_concept.arity);
// 		     System.out.println("nequiv.rh_concept.arity is " + nequiv.lh_concept.arity);
// 		     System.out.println("conj_interesting " + conj_interesting);
// 		   }
// 		 //end test

                    //System.out.println("conj.arity is" + conj.arity);
                    //System.out.println("contains(conjectures_to_avoid, conj) is " + contains(conjectures_to_avoid, conj));
                    if((conj.arity==1 || conj.arity==0) && !(contains(conjectures_to_avoid, conj)) && conj_interesting)
                    {
                        //System.out.println("wed -- 1");

                        //if conj_type is conjecture, just add it. For all
                        //other types check if the concepts in them are of
                        //arity 1
                        if(conj_type.equals("Conjecture"))
                        {
                            n_best_conj.addElement(conj);
                            num_so_far++;
                        }
                        if(conj_type.equals("NearEquivalence") && conj instanceof NearEquivalence)
                        {
                            NearEquivalence nequiv = (NearEquivalence)conj;
                            if (nequiv.lh_concept.arity==1 && nequiv.rh_concept.arity==1)
                            {
                                n_best_conj.addElement(nequiv);
                                num_so_far++;
                            }
                        }

                        if(conj_type.equals("NearImplication") && conj instanceof NearImplication)
                        {
                            NearImplication nimp = (NearImplication)conj;
                            if (nimp.lh_concept.arity==1 && nimp.rh_concept.arity==1)
                            {
                                n_best_conj.addElement(nimp);
                                num_so_far++;
                            }
                        }

                        if(conj_type.equals("Equivalence") && conj instanceof Equivalence)
                        {

                            //System.out.println("wed -- 2");

                            Equivalence equiv = (Equivalence)conj;
                            //System.out.println("equiv.lh_concept.arity is " + equiv.lh_concept.arity);
                            //System.out.println("and equiv.rh_concept.arity is " + equiv.rh_concept.arity);

                            if (equiv.lh_concept.arity==1 && equiv.rh_concept.arity==1)
                            {

                                //System.out.println("wed -- 3");
                                n_best_conj.addElement(equiv);
                                num_so_far++;
                            }

                            if (equiv.lh_concept.arity==1 && equiv.rh_concept.arity==0 ||
                                    equiv.lh_concept.arity==0 && equiv.rh_concept.arity==1)//added 15/6/05 - may need to delete
                            {

                                //System.out.println("wed -- 4");
                                n_best_conj.addElement(equiv);
                                num_so_far++;
                            }



                        }

                        if(conj_type.equals("Implication") && conj instanceof Implication)
                        {
                            Implication imp = (Implication)conj;

                            if (imp.lh_concept.arity==1 && imp.rh_concept.arity==1)//why is this a condition????
                            {
                                n_best_conj.addElement(imp);
                                num_so_far++;
                            }
                            if (imp.lh_concept.arity==0 && imp.rh_concept.arity==1)//added 2/11/03 - may need to delete
                            {
                                n_best_conj.addElement(imp);
                                num_so_far++;
                            }
                            if (imp.lh_concept.arity==1 && imp.rh_concept.arity==0)//added 2/11/03 - may need to delete
                            {
                                n_best_conj.addElement(imp);
                                num_so_far++;
                                //System.out.println("in here: n_best_conj is " + n_best_conj);
                            }

                        }

                        if(conj_type.equals("NonExists") && conj instanceof NonExists)
                        {
                            NonExists nonexist = (NonExists)conj;

                            if (nonexist.concept.arity==0 || nonexist.concept.arity==1)//added arity =0
                            {
                                n_best_conj.addElement(nonexist);

                                num_so_far++;
                            }
                        }

                        if(conj_type.equals("Implicate") && conj instanceof Implicate)
                        {
                            Implicate imp = (Implicate)conj;
                            if (imp.premise_concept.arity==1)
                            {
                                n_best_conj.addElement(imp);
                                num_so_far++;
                            }
                        }
                    }
                }
                else
                    break;
            }
        }
        return n_best_conj;
    }

    /** not called anywhere - can probably delete */
    public Vector getNBestConjectures1(String conj_type, int n, Vector conjectures_to_avoid)
    {
        Vector n_best_conj = new Vector();
        Vector conjs_to_consider = new Vector();

        if (conj_type.equals("NearEquivalence"))
            conjs_to_consider =  hr.theory.near_equivalences;
        if (conj_type.equals("NearImplication"))
            conjs_to_consider =  hr.theory.near_implications;
        if (conj_type.equals("Conjecture"))
            conjs_to_consider =  hr.theory.conjectures;
        if (conj_type.equals("Equivalence"))
            conjs_to_consider =  hr.theory.equivalences;
        if (conj_type.equals("NonExistence"))
            conjs_to_consider =  hr.theory.non_existences;
        if (conj_type.equals("Implication"))
            conjs_to_consider =  hr.theory.implications;
        if (conj_type.equals("Implicate"))
            conjs_to_consider =  hr.theory.implicates;

        if (n >= conjs_to_consider.size())
        {
            for(int i=0; i<conjs_to_consider.size(); i++)
            {
                Conjecture conj = (Conjecture)conjs_to_consider.elementAt(i);
                if((conj.arity==1 || conj.arity==0) && !(contains(conjectures_to_avoid, conj)))
                    n_best_conj.addElement(conj);
            }
        }
        else
        {
            int num_so_far = 0;
            for(int i =0; i< conjs_to_consider.size(); i++)
            {
                if(num_so_far<n)
                {
                    Conjecture conj = (Conjecture)conjs_to_consider.elementAt(i);//or lastElement?
                    if(conj.arity==1 || conj.arity==0 && !(contains(conjectures_to_avoid, conj)))
                    {
                        n_best_conj.addElement(conj);
                        num_so_far++;
                    }
                }
                else
                    break;
            }
        }
        return n_best_conj;
    }


    /** returns a vector of the n best concepts in the theory and writes
     *   it to the window - assumes ordered by interestingness [?]
     */

    public Vector getNBestConcepts(int n)
    {
        Vector n_best_concepts = new Vector();

        if (n >= hr.theory.concepts.size())
            n_best_concepts = hr.theory.concepts;
        else
        {
            for(int i =0; i< n; i++)
            {

                Concept concept = (Concept)hr.theory.concepts.elementAt(i);
                n_best_concepts.addElement(concept);
            }
        }
        window.writeQuietlyToFrontEnd("getNBestConcepts():");
        window.writeQuietlyToFrontEnd("my " + n + " best concepts are " + n_best_concepts);
        return n_best_concepts;
    }



    public static void main(String args[])
    {

        String configfilename = args[0];
        String name = args[1];
        String teacher_name = args[2];
        String port_string = args[3];
        int port = Integer.parseInt(port_string);
        InetAddress teacher_hostname = null;

        try
        {
            teacher_hostname = InetAddress.getByName(teacher_name);
        }

        catch(Exception e)
        {
            System.out.println("Problem getting teacher_hostname: " + e);
        }

        Student student = new Student();

        if(args.length==4)
        {
            student = new Student(configfilename, name, teacher_hostname, port);
        }
        if(args.length==5)
        {
            String macro = args[4];
            student = new Student(configfilename, name, macro, teacher_hostname, port);
        }
        student.start();
    }

    public void sleeping()
    {
        System.out.println("Sleeping");
    }

    /** looks in teacher's file and if there is an object in it writes it
     to screen. - redundant - delete (?)*/

    public void receiveTeachersRequest()
    {
        boolean waiting_for_teachers_request = true;
        while(waiting_for_teachers_request==true)
        {

            try
            {
                //TheoryConstituent request = (TheoryConstituent)notice_board.getRequestFromTeacher();
                //String request = (String)notice_board.getRequestFromTeacher(); - here
                window.writeQuietlyToFrontEnd("Just read this request in the teacher's file:");
                window.writeQuietlyToFrontEnd(current_request); //problem
                window.drawQuietLine();
                waiting_for_teachers_request=false;

            }
            catch (Exception e)
            {
                System.out.println(name);
                System.out.println(" just looked in teacher's file but couldn't get object");
            }
        }
    }

    /** returns true if the vector contains the conj, false
     otherwise. Needs testing. */
    public boolean contains(Vector vector, Conjecture conj)
    {

        for(int i=0; i<vector.size(); i++)
        {

            //if (vector.elementAt(i) instanceof GroupAgendaElement)

            //if (vector.elementAt(i) instanceof Request)

            if (vector.elementAt(i) instanceof Vector)
            {

                Vector responses = (Vector)vector.elementAt(i);
                for (int j=0; j<responses.size(); j++)
                {
                    Response response = (Response)vector.elementAt(j);
                    if (!(response.response_vector.isEmpty()) && response.response_vector.elementAt(0) instanceof Conjecture)
                    {

                        for(int k=0; j<response.response_vector.size(); k++)
                        {
                            Conjecture other_conj = (Conjecture)response.response_vector.elementAt(k);
                            if (other_conj.writeConjecture("ascii").equals(conj.writeConjecture("ascii")))
                                return true;
                        }
                    }
                }
            }
            if (vector.elementAt(i) instanceof Response)
            {
                Response response = (Response)vector.elementAt(i);
                if (!(response.response_vector.isEmpty()) && response.response_vector.elementAt(0) instanceof Conjecture)
                {
                    for(int j=0; j<response.response_vector.size(); j++)
                    {
                        Conjecture other_conj = (Conjecture)response.response_vector.elementAt(j);
                        if (other_conj.writeConjecture("ascii").equals(conj.writeConjecture("ascii")))
                            return true;
                    }
                }
            }

            if (vector.elementAt(i) instanceof Conjecture)
            {
                Conjecture other_conj = (Conjecture)vector.elementAt(i);
                if (other_conj.equals(conj))
                    return true;
            }
        }
        return false;
    }





    /** Looks through a vector of responses. Check whether any new
     entities have been sent by other students, and if so, then by
     default add them to the theory. If monster-barring is set then
     test to see whether to perform this. If it should be performed
     then send a request to the teacher to do so. Otherwise send the
     teacher a null request */

    public void respondToResponses(Vector received_vector)
    {
        window.writeQuietlyToFrontEnd("started respondToResponses(" + received_vector);
        Request request = new Request();
        window.writeQuietlyToFrontEnd("received_vector.size() is " + received_vector.size());
        //Conjecture conjecture_under_discussion = (Conjecture)current_request.theory_constituent_vector.elementAt(0); //here
        Conjecture conj = current_request.motivation.conjecture_under_discussion;

        for(int i = 0; i< received_vector.size(); i++)
        {
            Response new_response = (Response)received_vector.elementAt(i);
            Vector rv = new_response.response_vector;
            if(!(rv.isEmpty()) && rv.elementAt(0) instanceof Entity)
            {
                //check whether entity is new or not
                for(int j = 0; j< rv.size(); j++)
                {
                    Entity entity = (Entity)rv.elementAt(j);

                    if(!(entity.entitiesContains(hr.theory.entities))) //thurs - here - if it already has entity, then break
                    {
                        //if monster-barring is not set, then add
                        //new entities to the theory

                        if(!hr.theory.use_monster_barring)
                        {
                            window.writeToFrontEnd("use_monster_barring is false");
                            for(int k = 0; j<hr.theory.concepts.size(); k++)
                            {
                                Concept concept = (Concept)hr.theory.concepts.elementAt(k);
                                if(concept.is_object_of_interest_concept)
                                {
                                    System.out.println("\n\n\n 1) got a new entity and going to add it to theory");
                                    hr.theory.addNewEntityToTheory(entity, concept.domain, "adding new entity to theory");
                                    System.out.println("\n\n\n 2) just performed addNewEntityToTheory() for entity="
                                            + entity+ ", concept.domain = " + concept.domain);
                                    group_file.writeToGroupFile("Student " + port + ": OK - I hadn't seen the " + concept.domain
                                            + " " + entity.toString() + " before. I've just added it to my theory.");
                                    break;
                                }
                            }
                        }

                        if(hr.theory.use_monster_barring)
                        {
                            window.writeToFrontEnd("use_monster_barring is true");

                            //extra bit -- lakatos variable for mb -- need to put this elsewhere too
                            boolean use_breaks_conj_under_discussion = hr.theory.lakatos.use_breaks_conj_under_discussion;

                            boolean performed_mb = false;

                            if(use_breaks_conj_under_discussion)
                            {
                                if(conj.counterexamples.isEmpty())
                                {
                                    conj.num_modifications++;
                                    for(int k = 0; k<hr.theory.concepts.size(); k++)//should this be j??
                                    {
                                        Concept concept = (Concept)hr.theory.concepts.elementAt(k);
                                        if(concept.is_object_of_interest_concept)
                                        {
                                            performed_mb = true;
                                            request = hr.theory.lakatos.monsterBarring(entity, concept.domain, hr.theory, conj,
                                                    hr.theory.lakatos.monster_barring_type, window);

                                            group_file.writeToGroupFile("Student " + port + ": " + request);
                                            break;
                                        }
                                    }
                                }
                            }


                            boolean use_percentage_conj_broken = hr.theory.lakatos.use_percentage_conj_broken;
                            if(use_percentage_conj_broken)
                            {
                                //first decide whether to perform monster-barring
                                double d = hr.theory.lakatos.percentageConjecturesBrokenByEntity(hr.theory, entity, window);
                                window.writeToFrontEnd(d + "% of my conjectures are broken by " + entity.name);

                                //if so, perform it

                                if(d>hr.theory.lakatos.monster_barring_min)
                                {
                                    window.writeToFrontEnd(d + " > " + hr.theory.lakatos.monster_barring_min + " so am going to perform m-b");
                                    entity.lakatos_method = "monster_barring";
                                    conj.num_modifications++;
                                    for(int k = 0; k<hr.theory.concepts.size(); k++)//should this be j??
                                    {
                                        Concept concept = (Concept)hr.theory.concepts.elementAt(k);
                                        if(concept.is_object_of_interest_concept)
                                        {
                                            performed_mb = true;
                                            request = hr.theory.lakatos.monsterBarring(entity, concept.domain, hr.theory, conj,
                                                    hr.theory.lakatos.monster_barring_type, window);


                                            group_file.writeToGroupFile("Student " + port + ": " + request);
                                            break;
                                        }
                                    }
                                }
                            }
                            //If there are alot of counterexamples,
                            //i.e. more than half the number of entities
                            //you have, and use_culprit_breaker is true,
                            //then check to see whether there is a single
                            //culprit entity. If so, monster-bar it.
                            boolean use_culprit_breaker = hr.theory.lakatos.use_culprit_breaker;
                            boolean use_culprit_breaker_on_conj = hr.theory.lakatos.use_culprit_breaker_on_conj;
                            boolean use_culprit_breaker_on_all = hr.theory.lakatos.use_culprit_breaker_on_all;

                            window.writeToFrontEnd("use_culprit_breaker is " + use_culprit_breaker);

                            if(use_culprit_breaker)
                            {
                                window.drawQuietStars();
                                window.writeToFrontEnd("into use_culprit_breaker");
                                window.writeToFrontEnd("rv is " + rv);
                                window.writeToFrontEnd("rv.size() is " + rv.size());
                                window.writeToFrontEnd(" hr.theory.entities.size()/2 is " + hr.theory.entities.size()/2);
                                if(rv.size()>hr.theory.entities.size()/2)
                                {
                                    window.writeQuietlyToFrontEnd("rv.size() is " + rv.size());
                                    window.writeQuietlyToFrontEnd("hr.theory.entities.size() is " + hr.theory.entities.size());
                                    window.writeToFrontEnd("It seems that rv.size()>hr.theory.entities.size()/2");


                                    if(use_culprit_breaker_on_conj)
                                    {
                                        //Conjecture conj = current_request.motivation.conjecture_under_discussion;
                                        window.writeQuietlyToFrontEnd("conj is " + conj.writeConjecture("ascii"));
                                        String culprit_entity_string =
                                                hr.theory.lakatos.conjectureBrokenByCulpritEntity(hr.theory, conj, window);
                                        window.writeQuietlyToFrontEnd("just had a look to see whether we have a culprit breaker to "
                                                + conj.writeConjecture("ascii"));
                                        window.writeQuietlyToFrontEnd("... found culprit_entity_string = " + culprit_entity_string);

                                        Entity culprit_entity = new Entity(culprit_entity_string);
                                        culprit_entity.lakatos_method = "monster_barring";
                                        conj.num_modifications++;
                                        window.writeQuietlyToFrontEnd("just converted this culprit_entity_string from String to entity - now got "+ culprit_entity);

                                        if(!culprit_entity_string.equals("no"))
                                        {
                                            window.writeQuietlyToFrontEnd("it isn't no");
                                            for(int k = 0; j<hr.theory.concepts.size(); k++)
                                            {
                                                Concept concept = (Concept)hr.theory.concepts.elementAt(k);
                                                if(concept.is_object_of_interest_concept)
                                                {
                                                    performed_mb = true;
                                                    request = hr.theory.lakatos.monsterBarring(culprit_entity, concept.domain, hr.theory,
                                                            conj, hr.theory.lakatos.monster_barring_type,                                                                                            window);

                                                    group_file.writeToGroupFile("Student " + port + ": " + request);
                                                    break;
                                                }
                                            }
                                        }

                                    }

                                    if(use_culprit_breaker_on_all) //MONDAY - check this works
                                    {
                                        String bar_or_not =
                                                hr.theory.lakatos.conjectureBrokenByCulpritEntity(hr.theory, window,
                                                        hr.theory.lakatos.monster_barring_culprit_min);
                                        if(!bar_or_not.equals("no"))//this should be boolean (and so should the other method)
                                        {
                                            window.writeQuietlyToFrontEnd("it isn't no");
                                            for(int k = 0; j<hr.theory.concepts.size(); k++)
                                            {
                                                Concept concept = (Concept)hr.theory.concepts.elementAt(k);
                                                if(concept.is_object_of_interest_concept)
                                                {
                                                    performed_mb = true;
                                                    request = hr.theory.lakatos.monsterBarring(entity, concept.domain, hr.theory,
                                                            conj, hr.theory.lakatos.monster_barring_type,                                                                                            window);

                                                    group_file.writeToGroupFile("Student " + port + ": " +request);
                                                    break;
                                                }
                                            }
                                        }
                                    }


                                    window.writeQuietlyToFrontEnd("finished use_culprit_breaker");
                                    window.drawStars();
                                }



                            }



                            for(int k=0;k<request.theory_constituent_vector.size();k++)
                            {
                                TheoryConstituent tc = (TheoryConstituent)request.theory_constituent_vector.elementAt(k);
                                if(tc instanceof Concept)
                                {
                                    Concept concept = (Concept)tc;
                                    concept.lakatos_method = "monster_barring";
                                    conj.num_modifications++;
                                }
                            }

                            if(!performed_mb)
                            //otherwise just add new entity to theory (as above)
                            {
                                for(int k = 0; j<hr.theory.concepts.size(); k++)
                                {
                                    Concept concept = (Concept)hr.theory.concepts.elementAt(k);
                                    if(concept.is_object_of_interest_concept)
                                    {
                                        window.writeQuietlyToFrontEnd("6");
                                        hr.theory.addNewEntityToTheory(entity, concept.domain, "adding new entity to theory");
                                        entity.lakatos_method = "highlighted by dialogue";
                                        group_file.writeToGroupFile("Student " + port + ": OK - I hadn't seen the " + concept.domain +
                                                " " + entity.toString()  + " before. I've decided to add it to my theory.");
                                        break;
                                    }
                                }

                            }
                        }
                        //window.writeToFrontEnd("7");
                    }
                }
            }

        }


        //NOTE: to do - the students
        //should store this request so that students can remember what they
        //proposed and don't then go against their proposals!
        makeCall(port, teacher_hostname, teacher_port, request);

    }


    public void testReact(HR hr)
    {

        //addReaction(String id, String pseudo_code, Theory theory)
        //System.out.println("***test reactTo");
        //hr.theory.react.addReaction("dummy_id", "System.out.println(this is a reaction)", hr.theory);


        //get the concept integer

        Concept concept_to_react_to = new Concept();
        for(int i=0;i<hr.theory.concepts.size();i++)
        {
            Concept concept = (Concept)hr.theory.concepts.elementAt(i);
            if(concept.id.equals("int001"))
                concept_to_react_to = concept;
        }

        //hr.theory.react.reactTo(concept_to_react_to, "System.out.println(this is a reaction)");
        Hashtable hashtable = new Hashtable();
        //Reaction reaction = new Reaction("dummy_id", "System.out.println(this is a reaction)", hashtable);

        //hr.theory.react = .reactTo(concept_to_react_to, "System.out.println(this is a reaction)");
        //hr.theory.react.addReaction("dummy_id", "System.out.println(this is a reaction)", hr.theory);
        //reaction.react("", concept_to_react_to);


        Vector reactions = new Vector();
        String situation = concept_to_react_to.id;//concept_to_react_to;
        Reaction reaction = new Reaction("dummy_id", "System.out.println(this is a reaction)", hashtable);

        reactions.addElement(reaction);

        hr.theory.react.reaction_vector_hashtable.put(situation, reactions);

        //System.out.println("***finish test reactTo");
    }



    /** looks through the discussion vector and if it considers the
     * interestingness of the conjectures and concepts to be over a
     * certain threshold then it adds them to its theory and tells it to
     * develop. Otherwise they're added but with the flag don't
     * develop. note -- can it perform all of the interestingness
     * measures? eg is there a construction history?

     removes responses once it's dealt with them
     */

    public void addToTheoryFromDiscussionVector()
    {
        System.out.println("started addToTheoryFromDiscussionVector");
        System.out.println("personal_discussion_vector is " + personal_discussion_vector);
        System.out.println("my conjectures so far are:");
        window.writeToFrontEnd("my conjectures so far are:");
        for(int i=0;i<hr.theory.conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)hr.theory.conjectures.elementAt(i);
            System.out.println(conjecture.id + ": " +conjecture.writeConjecture());
            window.writeToFrontEnd(conjecture.id + ": " +conjecture.writeConjecture());
        }
        System.out.println("end of my conjectures");
        System.out.println("\n               ---------------");
        window.writeToFrontEnd("end of my conjectures");
        window.writeToFrontEnd("\n               ---------------");



        window.writeToFrontEnd("my concepts are:");
        for(int i=0;i<hr.theory.concepts.size();i++)
        {
            Concept concept = (Concept)hr.theory.concepts.elementAt(i);

            window.writeToFrontEnd(concept.id + ": " + concept.writeDefinition());
        }


        window.writeToFrontEnd("end of my new concepts");
        window.writeToFrontEnd("\n              --------------------");



        for(int i=0;i<personal_discussion_vector.size();i++)
        {
            if(personal_discussion_vector.elementAt(i) instanceof Response)
            {
                Response response = (Response)personal_discussion_vector.elementAt(i);

                for(int j=0;j<response.response_vector.size();j++)
                {
                    if(response.response_vector.elementAt(j) instanceof Conjecture)
                    {
                        Conjecture conj = (Conjecture)response.response_vector.elementAt(j);
                        System.out.println(conj.id + ": " + conj.writeConjecture() + " is a conj");

                        //check to see if it's in theory already
                        boolean theory_contains_conj = conj.theoryContainsConjecture(hr.theory.conjectures);
                        System.out.println("theory_contains_conj is " + theory_contains_conj);

                        //if not then calculate its interestingness nad
                        //add into the approporiate place in the agenda.

                        if(!theory_contains_conj)
                        {
                            //Integer int = new Integer(hr.theory.conjectures.size());
                            //conj.id = int.toString();

                            conj.id = (new Integer(hr.theory.conjectures.size())).toString();



                            //calculating the interestingness of the conj
                            //-- does this affect anything???
                            hr.theory.measure_conjecture.measureConjecture(conj, hr.theory);

                            if(conj.interestingness >= hr.theory.lakatos.threshold_to_add_conj_to_theory)
                            {
                                //here -- doesn't seem to work. get addEntityToTheory to work and then
                                //try again

                                Conjecture reconstructed_conj = conj.reconstructConjecture(hr.theory, window);

                                Vector v = hr.theory.addConjectureToTheory(reconstructed_conj);
                                //hr.theory.addConjectureToTheory(conj);
                            }
                        }
                        response.response_vector.removeElementAt(j);
                        j--;
                        break; //check
                    }

                    //arla dis need testing man.
                    if(response.response_vector.elementAt(j) instanceof Concept)
                    {
                        Concept concept = (Concept)response.response_vector.elementAt(j);
                        System.out.println(concept.writeDefinition() + " is a concept");


                        //check to see if it's in theory already
                        boolean theory_contains_concept = concept.theoryContainsConcept(hr.theory.concepts);
                        System.out.println("theory_contains_concept is " + theory_contains_concept);

                        //if not then calculate its interestingness nad
                        //add into the approporiate place in the agenda.

                        if(!theory_contains_concept)
                        {
                            //Integer int = new Integer(hr.theory.conjectures.size());
                            //conj.id = int.toString();

                            concept.id = (new Integer(hr.theory.concepts.size())).toString();



                            //calculating the interestingness of the conj
                            //-- does this affect anything???


                            //calculating the interestingness of the concept --
                            //if concept is new:
                            System.out.println("(concept.interestingness is " + concept.interestingness);


                            hr.theory.measure_concept.measureConcept(concept, hr.theory.concepts, true);

                            System.out.println("(concept.interestingness is NOW " + concept.interestingness);

                            //  //else:
                            // 		    else
                            // 		      {
                            // 			hr.theory.measure_concept.measureConcept(concept, hr.theory.concepts, false);

                            // 		      }



                            if(concept.interestingness >= hr.theory.lakatos.threshold_to_add_concept_to_theory)
                            {
                                //Concept reconstructed_concept = concept.reconstructConcept(hr.theory, window);
                                concept.dont_develop = false;//we want to develop if it's interesting
                                hr.theory.addConceptToTheory(concept);
                                System.out.println("concept over threshold");

                            }
                        }
                        response.response_vector.removeElementAt(j);
                        j--;
                        break;
                    }


                    personal_discussion_vector.removeElementAt(i);
                    i--;
                }
            }
        }


        System.out.println("my conjectures NOW are:");
        window.writeToFrontEnd("my conjectures NOW are:");
        for(int i=0;i<hr.theory.conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)hr.theory.conjectures.elementAt(i);
            System.out.println(conjecture.id + ": " +conjecture.writeConjecture());
            window.writeToFrontEnd(conjecture.id + ": " +conjecture.writeConjecture());
        }
        System.out.println("end of my new conjectures");
        System.out.println("\n              ---------------");
        window.writeToFrontEnd("end of my new conjectures");
        window.writeToFrontEnd("\n              ---------------");

        //print out concepts

        window.writeToFrontEnd("my concepts NOW are:");
        for(int i=0;i<hr.theory.concepts.size();i++)
        {
            Concept concept = (Concept)hr.theory.concepts.elementAt(i);

            window.writeToFrontEnd(concept.id + ": " + concept.writeDefinition());
        }


        window.writeToFrontEnd("end of my new concepts");
        window.writeToFrontEnd("\n              --------------------");



        System.out.println("personal_discussion_vector is now " + personal_discussion_vector);
        System.out.println("finished addToTheoryFromDiscussionVector");
        System.out.println("\n\n\n");
    }

    public void writeVariableInformation()
    {

        window.writeQuietlyToFrontEnd("By the way,");
        if(hr.theory.use_surrender)
            window.writeQuietlyToFrontEnd("hr.theory.front_end.use_surrender.getState() is " + hr.theory.front_end.use_surrender_check.getState());

        if(hr.theory.use_strategic_withdrawal)
            window.writeQuietlyToFrontEnd("hr.theory.use_strategic_withdrawal_check is " + hr.theory.use_strategic_withdrawal);

        if(hr.theory.use_piecemeal_exclusion)
            window.writeQuietlyToFrontEnd("hr.theory.use_piecemeal_exclusion_check is " + hr.theory.use_piecemeal_exclusion);

        if(hr.theory.use_monster_barring)
            window.writeQuietlyToFrontEnd("hr.theory.use_monster_barring_check is " + hr.theory.use_monster_barring);

        if(hr.theory.use_monster_adjusting)
            window.writeQuietlyToFrontEnd("hr.theory.use_monster_adjusting_check is " + hr.theory.use_monster_adjusting);

        if(hr.theory.use_lemma_incorporation)
            window.writeQuietlyToFrontEnd("hr.theory.use_lemma_incorporation_check is " + hr.theory.use_lemma_incorporation);

        if(hr.theory.use_proofs_and_refutations)
            window.writeQuietlyToFrontEnd("hr.theory.use_proofs_and_refutations_check is " + hr.theory.use_proofs_and_refutations);

        if (hr.theory.use_communal_piecemeal_exclusion)
            window.writeQuietlyToFrontEnd("hr.theory.use_communal_piecemeal_exclusion_check is " + hr.theory.use_communal_piecemeal_exclusion);


        window.writeQuietlyToFrontEnd("the entities in my theory are " + hr.theory.entities);
        window.writeQuietlyToFrontEnd("the pseudo_entities in my theory are " + hr.theory.pseudo_entities);
        window.writeQuietlyToFrontEnd("my personal discussion_vector is " + personal_discussion_vector);
        window.writeQuietlyToFrontEnd("the discussion vector is " + discussion_vector);

        String macro_chosen = hr.theory.front_end.macro_list.getSelectedItem();
        window.writeToFrontEnd("The macro chosen is " + macro_chosen);


        boolean write_discussion_variables = true;
        boolean write_surrender_variables = true;
        boolean write_mb_variables = true;
        boolean write_eb_variables = true;

        if(write_discussion_variables)
        {
            window.writeToFrontEnd("max_num_independent_work_stages is " + hr.theory.lakatos.max_num_independent_work_stages);

            window.writeToFrontEnd("test_num_independent_steps is " +
                    hr.theory.lakatos.test_num_independent_steps);

            window.writeToFrontEnd("num_independent_steps is " + hr.theory.lakatos.num_independent_steps);

            window.writeToFrontEnd("hr.theory.lakatos.threshold_to_add_conj_to_theory is "
                    + hr.theory.lakatos.threshold_to_add_conj_to_theory);

            window.writeToFrontEnd("hr.theory.lakatos.threshold_to_add_concept_to_theory is "
                    + hr.theory.lakatos.threshold_to_add_concept_to_theory);

        }
        if(write_surrender_variables)
        {
            window.writeToFrontEnd("hr.theory.lakatos.test_number_modifications_surrender is "
                    + hr.theory.lakatos.test_number_modifications_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.test_interestingness_surrender is "
                    + hr.theory.lakatos.test_interestingness_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.compare_average_interestingness_surrender is "
                    + hr.theory.lakatos.compare_average_interestingness_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.test_plausibility_surrender is  "
                    + hr.theory.lakatos.test_plausibility_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.test_domain_application_surrender is "
                    + hr.theory.lakatos.test_domain_application_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.number_modifications_surrender is "
                    + hr.theory.lakatos.number_modifications_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.interestingness_th_surrender is "
                    + hr.theory.lakatos.interestingness_th_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.plausibility_th_surrender is "
                    + hr.theory.lakatos.plausibility_th_surrender);
            window.writeToFrontEnd("hr.theory.lakatos.domain_application_th_surrender is "
                    + hr.theory.lakatos.domain_application_th_surrender);
        }

        if(write_mb_variables)
        {
            window.writeToFrontEnd("hr.theory.lakatos.monster_barring_min is "
                    + hr.theory.lakatos.monster_barring_min);

            window.writeToFrontEnd("hr.theory.lakatos.monster_barring_type is "
                    + hr.theory.lakatos.monster_barring_type);

            window.writeToFrontEnd("hr.theory.lakatos.monster_barring_culprit_min is "
                    + hr.theory.lakatos.monster_barring_culprit_min);

            window.writeToFrontEnd("hr.theory.lakatos.use_breaks_conj_under_discussion is "
                    + hr.theory.lakatos.use_breaks_conj_under_discussion);

            window.writeToFrontEnd("hr.theory.lakatos.accept_strictest is "
                    + hr.theory.lakatos.accept_strictest);

            window.writeToFrontEnd("hr.theory.lakatos.use_percentage_conj_broken is "
                    + hr.theory.lakatos.use_percentage_conj_broken);
            window.writeToFrontEnd("hr.theory.lakatos.use_culprit_breaker is " + hr.theory.lakatos.use_culprit_breaker);
            window.writeToFrontEnd("hr.theory.lakatos.use_culprit_breaker_on_conj is "
                    + hr.theory.lakatos.use_culprit_breaker_on_conj);
            window.writeToFrontEnd("hr.theory.lakatos.use_culprit_breaker_on_all is "
                    + hr.theory.lakatos.use_culprit_breaker_on_all);
        }
    }
}
