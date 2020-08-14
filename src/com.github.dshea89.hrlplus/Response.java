package com.github.dshea89.hrlplus;

import java.util.Vector;

public class Response extends CommunicationLanguage {
    Vector response_vector = new Vector();
    Request request = new Request();

    public Response()
    {

    }

    public Response(Request request)
    {
        this.request = request;
        //Response(response_vector, request);
    }

    public Response(Vector response_vector, Request request)
    {
        this.response_vector = response_vector;
        this.request = request;
    }

    public String toString()
    {
        Vector string_response_vector = new Vector();

        for(int i=0; i<response_vector.size(); i++)
        {
            if (response_vector.elementAt(i) instanceof Conjecture)
            {
                Conjecture conjecture = (Conjecture)response_vector.elementAt(i);
                String string_conj = conjecture.id + ": " + conjecture.writeConjecture("ascii");
                string_response_vector.addElement(string_conj);
            }

            if (response_vector.elementAt(i) instanceof Concept)
            {
                Concept concept = (Concept)response_vector.elementAt(i);
                String string_concept = concept.id + ": " + concept.writeDefinition("ascii");
                string_response_vector.addElement(string_concept);
            }

            if (response_vector.elementAt(i) instanceof Entity)
            {
                Entity entity = (Entity)response_vector.elementAt(i);
                String string_entity = entity.toString();
                string_response_vector.addElement(string_entity);
            }

            if (response_vector.elementAt(i) instanceof String)
            {
                String string_element  = (String)response_vector.elementAt(i);
                string_response_vector.addElement(string_element);
            }
            if( i<response_vector.size()-1)
                string_response_vector.addElement("\n");
        }

        if(!(string_response_vector.isEmpty()))
        {
            return ""+string_response_vector; // + ", request=" + request +")";
        }

        else
        {
            return ""+response_vector;// + ", request=" + request +")";

        }
    }



    public String toStringForFile()
    {
        String string_response = new String();

        for(int i=0; i<response_vector.size(); i++)
        {
            if (response_vector.elementAt(i) instanceof Conjecture)
            {
                Conjecture conjecture = (Conjecture)response_vector.elementAt(i);
                string_response = string_response + conjecture.writeConjecture("ascii");
            }

            if (response_vector.elementAt(i) instanceof Concept)
            {
                Concept concept = (Concept)response_vector.elementAt(i);
                string_response = string_response + concept.writeDefinition("ascii");
            }

            if (response_vector.elementAt(i) instanceof Entity)
            {
                Entity entity = (Entity)response_vector.elementAt(i);
                string_response = string_response + entity.toString();
            }

            if (response_vector.elementAt(i) instanceof String)
            {
                String string_element  = (String)response_vector.elementAt(i);

                if(string_element.equals("accept proposal to bar entity"))
                {
                    string_response = string_response + "I think we should bar the entity";
                    break;
                }
                if(string_element.equals("reject proposal to bar entity"))
                {
                    string_response = string_response + "I think the entity is fine";
                    break;
                }
                string_response = string_response +string_element;
            }

            if( i<response_vector.size()-1)
                string_response = string_response +", ";
        }

        if((request.type).equals("Entity") && response_vector.isEmpty())
            string_response = "No, sorry.";

        return string_response;
    }
}
