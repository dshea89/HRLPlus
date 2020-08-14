package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;

/** A class representing a mathematical proof in the theory. It consists of [...]
 * @author Alison Pease, started 20th October 2003
 * @version 1.0
 */

public class ProofScheme extends TheoryConstituent implements Serializable
{
    Conjecture conj = new Conjecture();
    Vector proof_vector = new Vector();
    String proof_tree = new String();

    /** Whether the proof_scheme has been produced using Lakatos
     * methods, and if so which one.
     */

    public String lakatos_method = "no";


    /** Simple constructor*/
    public ProofScheme()
    {
    }

    /** Constructor given the vector */
    public ProofScheme(Conjecture conj, Vector v)
    {
        this.conj = conj;
        this.proof_vector = v;
    }

    public ProofScheme(Theory theory, String conjs_to_force)
    {
        String global_conj = conjs_to_force.substring(1,conjs_to_force.indexOf("=")-1);
        this.conj = constructConjecture(theory,global_conj);
        this.proof_vector = proof_vector;

        String local_conj_string = conjs_to_force.substring(conjs_to_force.indexOf("=")+1,conjs_to_force.length());
        String string_without_brackets = omitBrackets(local_conj_string);
        int index;
        int next_index =0;

        for(index=-1;index<string_without_brackets.length();)
        {
            String conj = new String();

            next_index = string_without_brackets.indexOf(",",index+1);
            if(next_index>0)
                conj = string_without_brackets.substring(index+1,next_index);
            else
            {
                conj = string_without_brackets.substring(index+1);
                Conjecture conj_to_add = constructConjecture(theory,conj);
                proof_vector.addElement(conj_to_add);
                break;
            }
            Conjecture conj_to_add = constructConjecture(theory,conj);
            index = next_index;
            proof_vector.addElement(conj_to_add);
        }



        /**
         int index;
         for(index=0;index<conjs_to_force.indexOf("]")-1;)
         {
         int next_index = conjs_to_force.indexOf(":",index+1);
         String conj = conjs_to_force.substring(index+1,next_index);
         Conjecture conj_to_add = constructConjecture(theory,conj);
         proof_vector.addElement(conj_to_add);
         index = next_index;
         }
         */
    }



    /** Given a string, returns the same string without {, }, or <=
     */
    public String omitBrackets(String string)
    {
        int index;
        String output = new String();
        for(index=0;index<string.length();index++)
        {
            char c = string.charAt(index);

            if(c=='{' | c=='}')
                output = output;
            else
            if(c=='=')
            {
                output = output.substring(0,output.length()-1);
                output = output + ",";
            }
            else
                output = output + c;
        }
        return output;
    }




    public Conjecture constructConjecture(Theory theory, String conj_string)
    {
        Concept concept1 = new Concept();
        Concept concept2 = new Concept();

        if(conj_string.indexOf("<")>0)
        {
            int index = conj_string.indexOf("<");
            String concept1_id = conj_string.substring(0,index);
            String concept2_id = conj_string.substring(index+3);

            for(int i=0;i<theory.concepts.size();i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);

                if(concept.id.equals(concept1_id))
                    concept1 = concept;
                if(concept.id.equals(concept2_id))
                    concept2 = concept;
            }
            return (new Equivalence(concept1, concept2,"lemma_in_proof"));

        }
        else
        if(conj_string.indexOf("-")>0)
        {
            int index = conj_string.indexOf("-");
            String concept1_id = conj_string.substring(0,index);
            String concept2_id = conj_string.substring(index+2);
            for(int i=0;i<theory.concepts.size();i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);

                if(concept.id.equals(concept1_id))
                    concept1 = concept;
                if(concept.id.equals(concept2_id))
                    concept2 = concept;
            }
            return (new Implication(concept1, concept2,"lemma_in_proof"));
        }
        else
        {
            System.out.println("got a nonexists");
            String concept1_id = conj_string;
            for(int i=0;i<theory.concepts.size();i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);

                if(concept.id.equals(concept1_id))
                    concept1 = concept;

            }
            return (new NonExists(concept1,"lemma_in_proof"));

        }
    }

    /** print out the proofscheme */
    public String writeProofScheme()
    {
        String output = new String();
        output = output + "     **************         ";
        output = output + "\nproofscheme.conj is " + this.conj.writeConjecture();
        output = output + "\nproofscheme.proof_vector is:";
        for(int i=0;i<this.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)this.proof_vector.elementAt(i);
            output = output + "\n("+i+") " +current_conj.writeConjecture();
        }
        output = output + "\n     **************         ";
        return output;
    }

    public boolean isEmpty()
    {
        if(this.conj.writeConjecture().equals("") && this.proof_vector.isEmpty())
            return true;

        else
            return false;
    }
}
