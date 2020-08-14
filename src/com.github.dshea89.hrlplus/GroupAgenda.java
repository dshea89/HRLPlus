package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;

/* A class representing the agenda for the group to discuss. The
   teacher uses it to determine the next item to be discussed. Note -
   used to extend Agenda*/

public class GroupAgenda extends Vector
{
    /* The Vector containing all items in the group agenda */

    public Vector agenda = new Vector();

    /** Whether to order the responses best first then insert into the
     * front of group agenda (so that the responses are grouped together, best first).
     */

    public boolean best_first_responses = false;

    /** Whether to insert the responses into the appropriate position
     in the group agenda (so that the whole agenda is ordered best
     first) */

    public boolean best_first_agenda = false;

    /** Whether to combine the responses before inserting them as a
     vector into the front of the group agenda (used for entities,
     eg after a request for counterexamples). No evaluation is
     performed. */

    public boolean combine_responses = false;

    /** Whether to perform a depth first search (always respond to the
     last - undeveloped - thing which was said). This is the
     default setting (for the moment) - but need to add as an
     option to flags */

    public boolean depth_first = true;

    /** Whether to perform a breadth first search */

    public boolean breadth_first = false;


    /** Default constructor */

    public GroupAgenda()
    {
    }

    /** Constructor which takes in a Vector agenda  */

    public GroupAgenda(Vector agenda)
    {
        this.agenda = agenda;
    }

    /** Given a response, returns the type of theory constituent the
     response contains, i.e., concept, conjecture, etc.  */

    public String getType(Response response)
    {
        String response_type = (response.request).type;
        return response_type;
    }

    /** Given a vector of responses to the same request, return the
     type of theory constituent the responses contain, i.e.,
     concept, conjecture, etc.  */
    /*

    public String getType(Vector responses)
    {
	String response_type = "";

	if(!responses.isEmpty())
	    response_type = (Request((Response)responses.elementAt(0)).request).type;

	return response_type;
    }
    */

    /** Given a theory constituent, returns the type of thing it is,
     i.e., concept, conjecture, etc. */

    public String getType(TheoryConstituent theory_constituent)
    {
        String tc_type = "";

        if(theory_constituent instanceof Concept)
            tc_type = "Concept";

        if(theory_constituent instanceof Conjecture)
            tc_type = "Conjecture";

        if(theory_constituent instanceof Entity)
            tc_type = "Entity";

        return tc_type;
    }

    /** Given a vector of responses, return true if at least one of
     the response vectors contains a theory consituent, or false
     otherwise [needs testing]*/

    public boolean responsesContainsTheoryConstituents(Vector responses)
    {
        boolean responses_contains_tc = false;

        if (responses.isEmpty())
            return responses_contains_tc;

        for(int i=0; i<responses.size();i++)
        {
            Response current_response = (Response)responses.elementAt(i);
            Vector curr_resp_vector = current_response.response_vector;
            if (!curr_resp_vector.isEmpty())
                return  responses_contains_tc = true;
        }
        return responses_contains_tc;
    }

    /** Given an unpacked vector of responses, orders all the
     conjectures etc by interestingness - best first - into a
     single vector and returns the ordered vector. */

    public Vector orderResponses(Vector unpacked_responses)
    {
        Vector ordered_responses = new Vector();

        for(int i=0; i<unpacked_responses.size(); i++)
        {
            GroupAgendaElement group_agenda_element = (GroupAgendaElement)unpacked_responses.elementAt(i);
            insertElementIntoGroupAgenda(group_agenda_element, ordered_responses);
        }

        return ordered_responses;
    }


    /** Takes in a vector of responses and unpacks it, returning a
     vector of group agenda elements, i.e. the various conjectures,
     concepts etc suggested in the responses, along with the
     motivation for why they are being considered. */

    public Vector unpackResponses(Vector responses)
    {

        Vector unpacked_responses = new Vector();

        for(int i=0; i<responses.size(); i++)
        {
            Response response = (Response)responses.elementAt(i);
            Vector response_vector = response.response_vector;
            Motivation motivation = (response.request).motivation;

            //if got back a String response, add to the group agenda as it is
            if(!(response_vector.isEmpty()) && response_vector.elementAt(0) instanceof String)
            {
                String string = (String)response_vector.elementAt(0);
                GroupAgendaStringElement group_agenda_string_element = new GroupAgendaStringElement(string, motivation);
                unpacked_responses.addElement(group_agenda_string_element);
            }


            // if got back a vector of entities then add to group agenda as it is
            if(!(response_vector.isEmpty()) && response_vector.elementAt(0) instanceof Entity)
            {
                GroupAgendaVectorElement group_agenda_vector_element = new GroupAgendaVectorElement(response_vector, motivation);
                unpacked_responses.addElement(group_agenda_vector_element);
            }

            // otherwise go through the response vector and add each theory constituent to the group agenda separately
            // if response_vector is empty then don't add it to the unpacked_responses
            if(!(response_vector.isEmpty()) && !(response_vector.elementAt(0) instanceof String))
            {
                for(int j=0; j<response_vector.size(); j++)
                {
                    if(response_vector.elementAt(j) instanceof TheoryConstituent)
                    {
                        TheoryConstituent response_tc = (TheoryConstituent)response_vector.elementAt(j);
                        GroupAgendaTheoryConstituentElement group_agenda_tc_element =
                                new GroupAgendaTheoryConstituentElement(response_tc, motivation);
                        unpacked_responses.addElement(group_agenda_tc_element);
                    }
                    //if not, then what???
                }
            }
        }
        return unpacked_responses;
    }

    /** The main Search method which takes in a vector of responses,
     unpacks them into a vector containing theory constituents, and
     adds these theory constituents to the group agenda. The order
     by which they are added - best/depth/breadth first etc - is
     determined by flags which are user-set. After adding the
     individual theory constituents to the group agenda in the
     correct position, the vector responses is emptied, so as to be
     ready for the next lot of responses. */

    public void addResponsesToGroupAgenda(Vector responses, AgentWindow window)
    {
        // first unpack all the responses to get a list of group
        // agenda theory constituent elements, i.e conjectures,
        // concepts, etc.along with the motivation as to why they're
        // wanted. This vector may also contain group agenda vector
        // elements, which contain a vector of entities, with the
        // motivation.

        //[can this handle entity vectors?]

        for (int i=0; i<responses.size(); i++)
        {
            Response response = (Response)responses.elementAt(i);
        }

        Vector unpacked_responses = unpackResponses(responses);
        for (int i=0; i<unpacked_responses.size(); i++)
        {
            GroupAgendaElement gae = (GroupAgendaElement)unpacked_responses.elementAt(i);
        }
        //put everyone's counters together, then continue with the other flags
        //needs work

        //(hr.theory.lakatos.use_communal_piecemeal_exclusion==true)
        if (combine_responses)
        {
            Vector combined_responses = new Vector();
            //int index = unpacked_responses.indexOf(GroupAgendaVectorElement);
            int index = 0; //need to redo
            GroupAgendaVectorElement first_vector_element = (GroupAgendaVectorElement)unpacked_responses.elementAt(index);
            Motivation vector_motivation = first_vector_element.motivation;

            for(int i=0; i<unpacked_responses.size(); i++)
            {
                GroupAgendaElement current_element = (GroupAgendaElement)unpacked_responses.elementAt(i);
                Motivation current_motivation = current_element.motivation;

                if(current_element instanceof GroupAgendaVectorElement)
                {
                    if(current_motivation.equals(vector_motivation))
                    {
                        GroupAgendaVectorElement current_vector_element = (GroupAgendaVectorElement)current_element;
                        Vector current_vector = current_vector_element.vector;

                        for(int j=0; j<current_vector.size(); j++)
                        {
                            Entity current_vector_entity = (Entity)current_vector.elementAt(j);
                            combined_responses.addElement(current_vector_entity);
                        }
                    }
                }
            }
            unpacked_responses = combined_responses;//?
        }

        if (best_first_responses)
        {
            Vector ordered_responses = orderResponses(unpacked_responses);

            for(int i = ordered_responses.size()-1; i>=0; i--)
            {
                agenda.insertElementAt(ordered_responses.elementAt(i), 0);
            }
        }

        if (best_first_agenda)
        {
            for(int i = 0; i<unpacked_responses.size(); i++)
            {
                GroupAgendaElement current_ga_element = (GroupAgendaElement)unpacked_responses.elementAt(i);
                insertElementIntoGroupAgenda(current_ga_element, agenda);
            }
        }

        if (depth_first)
        {
            for(int i = unpacked_responses.size()-1; i>=0; i--)
            {
                agenda.insertElementAt(unpacked_responses.elementAt(i), 0);
            }

        }

        if (breadth_first)
        {
            for(int i = 0; i<unpacked_responses.size(); i++)
            {
                agenda.addElement(unpacked_responses.elementAt(i));
            }
        }


        //when finished, clear the responses vector
        responses.removeAllElements();

        // lastly check the agenda for duplicates

        agenda = removeDuplicatesFromVector(agenda, window); //check this is ok

    }

    /** Goes through group_agenda and removes all elements where the motivation involves this conjecture under discussion */
    public void removeElementsWithGivenMotivation(Conjecture given_conjecture_under_discussion)
    {
        for(int i=0;i<agenda.size();i++)
        {
            GroupAgendaElement gae = (GroupAgendaElement)(agenda).elementAt(i);
            if ((gae.motivation.conjecture_under_discussion).equals(given_conjecture_under_discussion))
            {
                (agenda).removeElementAt(i);
                i--;
            }
        }
    }


    /** Evaluates the interestingness of the given conjecture and
     inserts it into the appropriate position in the mixed vector. Note
     that it only compares like with like, eg. doesn't compare the
     interestingness of a concept with the interestingness of a
     conjecture [may need adding] */

    public void insertElementIntoGroupAgenda(Conjecture conjecture_to_insert, GroupAgendaElement group_agenda_element, Vector agenda)
    {
        boolean inserted_conjecture = false;
        int i =0;

        while(i<agenda.size() && !inserted_conjecture)
        {
            Object vector_item = agenda.elementAt(i);

            if(vector_item instanceof GroupAgendaTheoryConstituentElement)
            {
                GroupAgendaTheoryConstituentElement vector_tc_item =  (GroupAgendaTheoryConstituentElement)vector_item;

                if(vector_tc_item.theory_constituent instanceof Conjecture)
                {
                    Conjecture vector_item_conjecture = (Conjecture)vector_tc_item.theory_constituent;

                    if(conjecture_to_insert.interestingness >= vector_item_conjecture.interestingness)

                    {
                        agenda.insertElementAt(group_agenda_element, i);
                        inserted_conjecture =true;
                    }
                }
            }
            i++;
        }

        if(!inserted_conjecture)
            agenda.addElement(group_agenda_element);

    }


    /** Evaluates the interestingness of the given concept and inserts
     it into the appropriate position in the mixed vector */

    public void insertElementIntoGroupAgenda(Concept concept_to_insert, GroupAgendaElement group_agenda_element, Vector agenda)
    {
        boolean inserted_concept = false;
        int i =0;

        while(i<agenda.size() && !inserted_concept)
        {
            GroupAgendaElement vector_item = (GroupAgendaElement)agenda.elementAt(i);

            if(vector_item instanceof GroupAgendaTheoryConstituentElement)
            {
                GroupAgendaTheoryConstituentElement vector_tc_item =  (GroupAgendaTheoryConstituentElement)vector_item;

                if(vector_tc_item.theory_constituent instanceof Concept)
                {
                    Concept vector_item_concept = (Concept)vector_tc_item.theory_constituent;

                    if(concept_to_insert.interestingness >= vector_item_concept.interestingness)

                    {
                        agenda.insertElementAt(group_agenda_element, i);
                        inserted_concept =true;
                    }
                }
            }
            i++;
        }

        if(!inserted_concept)
            agenda.addElement(group_agenda_element);
    }


    /** [Should evaluate the interestingness of the given vector of
     entities - but doesn't yet - need to write interestingness values
     for this]. inserts vector into the agenda - at the moment into the
     front of the agenda.*/

    public void insertElementIntoGroupAgenda(Vector vector_of_entities, GroupAgendaElement group_agenda_element, Vector agenda)
    {
        agenda.addElement(group_agenda_element);
    }


    /** insertElementIntoGroupAgenda takes in a response and a vector
     of mixed items (containing conjectures, concepts etc) - usually
     the agenda (but sometimes the ordered_responses vector). It looks
     at the response vector, evaluates the contents, and inserts them
     into the mixed vector in the appropriate position - sorted best
     first. Note that at the moment we only compare like with like -
     i.e. we don't compare a concept with a conjecture. [check]*/

    public void insertElementIntoGroupAgenda(GroupAgendaElement group_agenda_element, Vector agenda)
    {
        if(group_agenda_element instanceof GroupAgendaTheoryConstituentElement)
        {
            GroupAgendaTheoryConstituentElement group_agenda_tc_element = (GroupAgendaTheoryConstituentElement)group_agenda_element;
            insertElementIntoGroupAgenda(group_agenda_tc_element, agenda);
        }

        if(group_agenda_element instanceof GroupAgendaVectorElement)
        {
            GroupAgendaVectorElement group_agenda_vector_element = (GroupAgendaVectorElement)group_agenda_element;
            insertElementIntoGroupAgenda(group_agenda_vector_element, agenda);
        }

    }

    // if got a theory constituent (as opposed to a vector of entities)

    public void insertElementIntoGroupAgenda(GroupAgendaTheoryConstituentElement group_agenda_tc_element, Vector agenda)
    {

        if(group_agenda_tc_element.theory_constituent instanceof Concept)
        {
            Concept group_agenda_concept = (Concept)group_agenda_tc_element.theory_constituent;
            insertElementIntoGroupAgenda(group_agenda_concept, group_agenda_tc_element, agenda);
        }


        if(group_agenda_tc_element.theory_constituent instanceof Conjecture)
        {
            Conjecture group_agenda_conjecture = (Conjecture)group_agenda_tc_element.theory_constituent;
            insertElementIntoGroupAgenda(group_agenda_conjecture, group_agenda_tc_element, agenda);
        }
    }

    // if got a vector of entities (as opposed to a theory constituent)

    public void insertElementIntoGroupAgenda(GroupAgendaVectorElement group_agenda_vector_element, Vector agenda)
    {
        Vector vector_of_entities = (Vector)group_agenda_vector_element.vector;
        insertElementIntoGroupAgenda(vector_of_entities, group_agenda_vector_element, agenda);
    }





    /** This takes in a vector of responses, and returns true if they
     are all responses to the same request, false otherwise */

    public boolean checkResponsesToSameRequest(Vector responses)
    {

        boolean request_common_to_all_responses = true;

        if(responses.isEmpty())
            return request_common_to_all_responses;

        else
        {
            Response first_response = (Response)responses.elementAt(0);
            Request request_of_first_response = first_response.request;

            for(int i=1; i<responses.size(); i++)
            {
                Response response = (Response)responses.elementAt(i);
                if (!((response.request).equals(request_of_first_response)))
                {
                    request_common_to_all_responses = false;
                    break;
                }
            }
        }

        return request_common_to_all_responses;
    }

    /** Takes in a vector containing theory constituents,
     * communication language, and Strings, and removes any duplicates
     * [still need testing with non-empty concepts] [rewrite this
     * method with cases] - 15/03/04
     */

    public Vector removeDuplicatesFromVector(Vector vector_to_check, AgentWindow window)
    {

        //System.out.println(" into removeDuplicatesFromVector");

        Vector no_duplicates_vector = new Vector();

        for(int i=0; i<vector_to_check.size(); )
        {
            boolean add_element_to_no_duplicates = true;

            Object object = vector_to_check.elementAt(i);
            window.writeToFrontEnd("got object " + object);
            //System.out.println("got object " + object);




            /**if(object instanceof GroupAgendaTheoryConstituentElement)
             {
             System.out.println("got a GroupAgendaTheoryConstituentElement");
             GroupAgendaTheoryConstituentElement gatce = (GroupAgendaTheoryConstituentElement)vector_to_check.elementAt(i);
             vector_to_check.removeElementAt(i);

             for(int j=0; j<vector_to_check.size(); j++)
             {
             Object other_object = vector_to_check.elementAt(j);
             if(other_object instanceof GroupAgendaTheoryConstituentElement)
             {
             GroupAgendaTheoryConstituentElement other_gatce = (GroupAgendaTheoryConstituentElement)vector_to_check.elementAt(j);

             if(other_gatce.equals(gatce))
             add_element_to_no_duplicates = false;

             }
             }


             }
             */

            if(object instanceof GroupAgendaElement)// this works - not sure about the other cases
            {
                //System.out.println("got a GroupAgendaElement");
                GroupAgendaElement gae = (GroupAgendaElement)vector_to_check.elementAt(i);
                //System.out.println("vector_to_check.size() is " + vector_to_check.size());
                boolean b = vector_to_check.isEmpty();
                //  if(b)
// 		System.out.println("LOOK -- vector_to_check.isEmpty() is " + b);

                vector_to_check.removeElementAt(i);

                for(int j=0; j<vector_to_check.size(); j++)
                {
                    Object other_object = vector_to_check.elementAt(j);

                    if(other_object instanceof GroupAgendaElement)
                    {
                        GroupAgendaElement other_gae = (GroupAgendaElement)vector_to_check.elementAt(j);
                        if(other_gae.equals(gae))
                            add_element_to_no_duplicates = false;
                    }
                }
            }

            if(object instanceof Concept)
            {
                //System.out.println("got a Concept");
                Concept vector_concept = (Concept)vector_to_check.elementAt(i);
                vector_to_check.removeElementAt(i);

                for(int j=0; j<vector_to_check.size(); j++)
                {
                    Object other_object = vector_to_check.elementAt(j);
                    if(other_object instanceof Concept)
                    {
                        Concept other_vector_concept = (Concept)vector_to_check.elementAt(j);

                        if(other_vector_concept.equals(vector_concept))
                            add_element_to_no_duplicates = false;

                    }
                }
            }

            if(object instanceof Conjecture) // needs working on - fails today 15/03/04
            {//System.out.println("got a Conjecture");

                Conjecture vector_conjecture = (Conjecture)vector_to_check.elementAt(i);
                vector_to_check.removeElementAt(i);

                for(int j=0; j<vector_to_check.size(); j++)
                {
                    Object other_object = vector_to_check.elementAt(j);
                    if(other_object instanceof Conjecture)
                    {
                        Conjecture other_vector_conjecture = (Conjecture)vector_to_check.elementAt(j);
                        if(other_vector_conjecture.equals(vector_conjecture))
                            add_element_to_no_duplicates = false;
                    }
                }
            }

            if(object instanceof Entity)
            {
                //System.out.println("got an Entity");
                Entity vector_entity = (Entity)vector_to_check.elementAt(i);
                vector_to_check.removeElementAt(i);

                for(int j=0; j<vector_to_check.size(); j++)
                {
                    Object other_object = vector_to_check.elementAt(j);
                    if(other_object instanceof Entity)
                    {
                        Entity other_vector_entity = (Entity)vector_to_check.elementAt(j);

                        if(other_vector_entity.equals(vector_entity))
                            add_element_to_no_duplicates = false;
                    }
                }
            }

            /** we might need to puit this back in, but i think it's
             * okay because a Request is a type of GroupAgendaElement so
             * it should have already been taken care of.

             if(object instanceof Request)
             {
             Request vector_request = (Request)object;
             //Request vector_request = (Request)vector_to_check.elementAt(i);
             vector_to_check.removeElementAt(i);

             for(int j=0; j<vector_to_check.size(); j++)
             {
             Object other_object = vector_to_check.elementAt(j);
             if(other_object instanceof Request)
             {
             Request other_vector_request = (Request)vector_to_check.elementAt(j);
             if(other_vector_request.equals(vector_request))
             add_element_to_no_duplicates = false;
             }
             }
             }
             */

            if(object instanceof Response)
            {
                //System.out.println("got a Response");
                Response vector_response = (Response)vector_to_check.elementAt(i);
                vector_to_check.removeElementAt(i);

                for(int j=0; j<vector_to_check.size(); j++)
                {
                    Object other_object = vector_to_check.elementAt(j);

                    if(other_object instanceof Response)
                    {
                        Response other_vector_response = (Response)vector_to_check.elementAt(j);
                        if(other_vector_response.equals(vector_response))
                            add_element_to_no_duplicates = false;
                    }
                }
            }

            if(object instanceof String)
            {//System.out.println("got a String");

                String vector_string = (String)vector_to_check.elementAt(i);
                vector_to_check.removeElementAt(i);

                for(int j=0; j<vector_to_check.size(); j++)
                {
                    Object other_object = vector_to_check.elementAt(j);
                    if(other_object instanceof String)
                    {
                        String other_vector_string = (String)vector_to_check.elementAt(j);
                        if(other_vector_string.equals(vector_string))
                            add_element_to_no_duplicates = false;
                    }
                }
            }

            if(add_element_to_no_duplicates)
                no_duplicates_vector.addElement(object);
        }
        //if(!(vector_to_check.contains(vector_item)))
        // no_duplicates_vector.addElement(vector_item);
        return no_duplicates_vector;

    }


    /** Removes any element in the agenda which contains the
     * conjecture_under_discussion in its motivation */


    public void removeElementsWithConjecture(Conjecture conjecture, AgentWindow window)
    {
        window.writeToFrontEnd("we want to eliminate all elements in the agenda with this conj:"
                + conjecture.writeConjecture());

        for(int i=0; i<agenda.size(); i++)
        {
            GroupAgendaElement gae = (GroupAgendaElement)agenda.elementAt(i);
            window.writeToFrontEnd("the conj of the current element is: "
                    + gae.motivation.conjecture_under_discussion.writeConjecture());
            boolean b = gae.motivation.conjecture_under_discussion.writeConjecture().equals(conjecture.writeConjecture());
            window.writeToFrontEnd("gae.motivation.conjecture_under_discussion.writeConjecture().equals(conjecture.writeConjecture()) is " + b);

            if(gae.motivation.conjecture_under_discussion.writeConjecture().equals(conjecture.writeConjecture()))
            {
                (agenda).removeElementAt(i);
                i--;
            }
        }
    }


    /** removes the given entity from the group_agenda.agenda (extend to
     containing conjectures, concepts etc?) I think that this doesn't
     work properly - thurs night. */

    public void removeEntity(Entity entity, AgentWindow window)
    {
        window.writeQuietlyToFrontEnd("started removeEntity on " + entity);
        window.writeQuietlyToFrontEnd("at the moment, agenda is " + agenda);
        for(int i=0; i<agenda.size(); i++)
        {
            GroupAgendaElement gae = (GroupAgendaElement)agenda.elementAt(i);

            if(gae instanceof GroupAgendaStringElement)
            {
                GroupAgendaStringElement gase = (GroupAgendaStringElement)gae;

                if(gase.motivation.entity_under_discussion.name.equals(entity.name))
                    (agenda).removeElementAt(i);



            }

            if(gae instanceof GroupAgendaTheoryConstituentElement)
            {
                GroupAgendaTheoryConstituentElement gatce = (GroupAgendaTheoryConstituentElement)gae;
                if(gatce.theory_constituent instanceof Entity)
                {
                    Entity e = (Entity)gatce.theory_constituent;
                    if(e.name.equals(entity.name))
                        (agenda).removeElementAt(i);
                }
            }

            if(gae instanceof GroupAgendaVectorElement)
            {
                GroupAgendaVectorElement gave = (GroupAgendaVectorElement)gae;
                //needs testing
                //if(gave.vector.contains(entity))
                for (int j=0; j<gave.vector.size(); j++)
                {
                    if (gave.vector.elementAt(j) instanceof Entity)
                    {
                        Entity ent = (Entity)gave.vector.elementAt(j);
                        if(ent.name.equals(entity.name))
                            gave.vector.removeElementAt(j);
                    }
                }
            }

            if(gae instanceof Request)
            {
                Request request = (Request)gae;
                if(request.motivation.entity_under_discussion.name.equals(entity.name))
                    agenda.removeElementAt(i);

                //if(request.theory_constituent_vector.contains(entity))
                for (int j=0; j<request.theory_constituent_vector.size(); j++)
                {
                    if (request.theory_constituent_vector.elementAt(j) instanceof Entity)
                    {
                        Entity ent = (Entity)request.theory_constituent_vector.elementAt(j);
                        if(ent.name.equals(entity.name))
                            request.theory_constituent_vector.removeElementAt(j);
                    }
                }
            }
        }

        //removeDuplicatesFromVector(agenda);//here
        window.writeQuietlyToFrontEnd("finished removeEntity on " + entity);
        window.writeQuietlyToFrontEnd("agenda is now " + agenda);

    }
}
