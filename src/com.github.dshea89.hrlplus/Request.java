package com.github.dshea89.hrlplus;

import java.util.Vector;

//public class Request extends CommunicationLanguage

public class Request extends GroupAgendaElement implements Cloneable {
    //test purposes only:
    int interestingness;

    //"supply(type=conjecture,num=2,condition=null,theory_constituent_vector=null,motivation = null)";

    String type = "";
    int num;
    String condition = "";
    Vector theory_constituent_vector = new Vector();
    Motivation motivation = new Motivation();

    String string_object = "";
    int num_steps;

    /** [ask]
     public Request(String type, int num, String condition, Vector theory_constituent_vector)
     {
     //this.motivation = motivation;
     Request(String type, int num, String condition, Vector theory_constituent_vector, Motivation motivation);
     }
     */

    public Request()
    {
        this.type = type;
        this.num = num;
        this.condition = condition;
        this.theory_constituent_vector = theory_constituent_vector;
        this.motivation = motivation;
    }

    public Request(String type, int num, String condition, Vector theory_constituent_vector, Motivation motivation)
    {
        this.type = type;
        this.num = num;
        this.condition = condition;
        this.theory_constituent_vector = theory_constituent_vector;
        this.motivation = motivation;
    }


    public Object clone()
    {
        Request request = new Request();

        request.type = this.type;
        request.num = this.num;
        request.condition = this.condition;
        request.theory_constituent_vector = (Vector)(this.theory_constituent_vector).clone();
        request.motivation = new Motivation(this.motivation.conjecture_under_discussion,this.motivation.attempted_method);

        return request;
    }


    public Request(String string_object)
    {
        this.string_object = string_object;
    }


    public Request(String string_object, int n)
    {
        this.string_object = string_object;
        this.num_steps = n;
    }


    public String toString()
    {
        if(this.string_object.equals("work independently"))
            return "work independently for " + num_steps + " steps";

        if(this.motivation.attempted_method.equals("monster-barring"))
            //return string_object + motivation.toString();
            return string_object;

        else
        {
            Conjecture conjecture = motivation.conjecture_under_discussion;
            String method = motivation.attempted_method;
            String num_string = new String();
            if(num==1)
                num_string = "a";
            else
                num_string = (new Integer(num)).toString();


            if(method.equals("to get discussion started"))
            {
                if(condition.equals("null"))
                    return "supply " + num_string + " " + type + ", in order to " + method;
                else
                    return "supply " + num_string + " " + type + " which " + condition + " "
                            + toString(theory_constituent_vector) + ", in order to " + method;
            }

            else

                // return "supply(type=" + type + ", num=" + num + ", condition=" + condition + ", theory_constituent_vector=" + toString(theory_constituent_vector) + ")";
                return "supply " + num_string + " " + type + " which " + condition + " "
                        + toString(theory_constituent_vector) + ", in order to " + method;

            //add to above to see the motivation
            //, in order to perform " + method + " on \"" + conjecture.id + ": " + conjecture.writeConjecture("ascii") + "\"";
        }
    }


    public String toStringForFile()
    {
        String type_to_write = new String();
        String condition_to_write = new String();


        if(this.string_object.equals("work independently"))
            return "work independently for " + num_steps + " steps";

        if(this.motivation.attempted_method.equals("monster-barring"))
        {
            if(string_object.equals("perform downgradeEntityToPseudoEntity"))
                return " please downgrade the entity to a pseudo-entity.";
            if(string_object.equals("perform addEntityToTheory"))
                return " please add the entity to your theories.";
            //return string_object + motivation.toString();
            return string_object;
        }
        else
        {
            Conjecture conjecture = motivation.conjecture_under_discussion;
            String method = motivation.attempted_method;
            String theory_constituents = toStringForFile(theory_constituent_vector);

            if(type.equals("Entity"))
                type_to_write = "entities";
            if(type.equals("Conjecture"))
                type_to_write = "conjectures";
            if(type.equals("Equivalence"))
                type_to_write = "equivalence conjectures";
            if(type.equals("NearEquivalence"))
                type_to_write = "near-equivalence conjectures";
            if(type.equals("NonExists"))
                type_to_write = "non-existence conjectures";
            if(type.equals("Implication"))
                type_to_write = "implication conjectures";
            if(type.equals("NearImplication"))
                type_to_write = "near-implication conjectures";
            if(type.equals("Concept"))
                type_to_write = "concepts";


            if(condition.equals("breaks"))
                condition_to_write = "break";
            if(condition.equals("modifies"))
                condition_to_write = "modify";
            if(condition.equals("covers"))
                condition_to_write = "cover";

            if(method.equals("to get discussion started") && theory_constituents.equals(""))
                return "Has anyone got any interesting " + type_to_write + " to get the discussion started?";

            //if(method.equals("to get discussion started"))

            //  return "Has anyone got any " + type_to_write + ", which " + condition_to_write  + " " + theory_constituents
            //   + "? We want to get the discussion started";

            if(!(type_to_write.equals("")) && !(condition_to_write.equals("")))
                return "Has anyone got any " + type_to_write + " which " + condition_to_write
                        + " " + theory_constituents + "? We want to perform " + method;


            //add to above to see the motivation
            //, in order to perform " + method + " on \"" + conjecture.id + ": " + conjecture.writeConjecture("ascii") + "\"";
        }
        return "";
    }

    public String toStringForFile(Vector theory_constituent_vector)
    {
        String string_object = new String();

        for(int i=0; i<theory_constituent_vector.size(); i++)
        {
            TheoryConstituent tc_element = (TheoryConstituent)theory_constituent_vector.elementAt(i);
            if (tc_element instanceof Conjecture)
            {
                Conjecture conj = (Conjecture)tc_element;
                string_object = "the conjecture '" + conj.writeConjecture("ascii") + "'";
            }

            if (tc_element instanceof Entity)
            {
                Entity entity = (Entity)tc_element;
                String entity_string = entity.name;
                string_object = string_object + entity_string + "; ";
            }
            if (tc_element instanceof Concept)
            {
                Concept concept = (Concept)tc_element;
                String concept_string = concept.writeDefinition("ascii");
                string_object = string_object + " the concept " + concept_string + "; ";
            }

        }
        return string_object;
    }



    public String toString(Vector theory_constituent_vector)
    {
        String string_object = "[";

        for(int i=0; i<theory_constituent_vector.size(); i++)
        {
            TheoryConstituent tc_element = (TheoryConstituent)theory_constituent_vector.elementAt(i);
            if (tc_element instanceof Conjecture)
            {
                Conjecture conj = (Conjecture)tc_element;
                String string_conj = conj.id + ": " + conj.writeConjecture("ascii");
                string_object = string_object +  string_conj + "; ";
            }

            if (tc_element instanceof Entity)
            {
                Entity entity = (Entity)tc_element;
                String entity_string = entity.name;
                string_object = string_object + entity_string + "; ";
            }
            if (tc_element instanceof Concept)
            {
                Concept concept = (Concept)tc_element;
                String concept_string = concept.writeDefinition("ascii");
                string_object = string_object + concept_string + "; ";
            }
            if (tc_element instanceof ProofScheme)
            {
                ProofScheme proof_scheme = (ProofScheme)tc_element;
                String global_conj_string = "Global conjecture: " + proof_scheme.conj.writeConjecture("ascii");
                string_object = string_object + global_conj_string;
            }

        }
        return string_object + "]";
    }

    public boolean equals(Request request)
    {
        boolean verdict = true;

        if (!((this.type).equals(request.type)))
            return (verdict = false);


        if (!((this.num)==num))

            return (verdict = false);

        if (!((this.condition).equals(condition)))
            return (verdict = false);

        if (!((this.theory_constituent_vector).equals(theory_constituent_vector)))
            return (verdict = false);

        if (!((this.motivation).equals(motivation)))
            return (verdict = false);

        if (!((this.string_object).equals(string_object)))
            return (verdict = false);

        return verdict;
    }


    /** Returns true if the request is empty (i.e. the default request),
     false otherwise **/

    public boolean isEmpty()
    {
        boolean verdict = true;

        if (!((this.type).equals("")))
            return (verdict = false);

        if (!((this.num)==0))
            return (verdict = false);

        if (!((this.condition).equals("")))
            return (verdict = false);

        if (!((this.theory_constituent_vector).isEmpty()))
            return (verdict = false);

        if (!((this.string_object).equals("")))
            return (verdict = false);

        return verdict;
    }
}
