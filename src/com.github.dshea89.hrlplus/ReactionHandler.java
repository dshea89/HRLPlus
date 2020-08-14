package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.awt.List;
import java.io.Serializable;

/** A class for reacting to certain events in the theory.
 *
 * @author Simon Colton, started 23rd April 2002
 * @version 1.0 */

public class ReactionHandler implements Serializable
{
    /** The hashtable of pairs (reaction_type, vector of reactions).
     */

    public Hashtable reaction_vector_hashtable = new Hashtable();

    public void reactTo(Object obj, String situation)
    {
        //System.out.println("in reactTo");
        //System.out.println("obj is " + obj + " and situation is " + situation);
        Vector reactions = (Vector)reaction_vector_hashtable.get(situation);
        if (reactions!=null)
        {
            for (int i=0; i<reactions.size(); i++)
            {
                Reaction reaction = (Reaction)reactions.elementAt(i);
                System.out.println("reaction is " + reaction);
                reaction.react(situation, obj);
                System.out.println("here - 1");
            }
        }
        //System.out.println("reactions is " + reactions);
        //System.out.println("finished reactTo");
        //System.out.println("\n  **************************************  \n");
    }

    /** This adds a reaction (from the front end) to the list of those to
     * react to at the appropriate time.
     */

    public void addReaction(String id, String pseudo_code, Theory theory)
    {
        System.out.println("in addReaction");
        System.out.println("pseudo_code is " + pseudo_code);

        Hashtable original_alias_hashtable = new Hashtable();
        original_alias_hashtable.put("theory", theory);
        Reaction reaction = new Reaction(id, pseudo_code, original_alias_hashtable);
        reaction.pseudo_code_interpreter.output_text_box = theory.front_end.pseudo_code_output_text;
        reaction.pseudo_code_interpreter.input_text_box = theory.front_end.pseudo_code_input_text;
        reaction.pseudo_code_interpreter.input_reporting_style = "print";
        String situation = "all_purpose";
        int ind = pseudo_code.indexOf("setSituation(");
        System.out.println("ind is " + ind);
        if (ind > 0) //alisonp changed
        //if (ind >= 0)
        {
            situation = pseudo_code.substring(ind+14, pseudo_code.indexOf(")", ind+13) - 1);
            reaction.setSituation(situation);
        }
        Vector reactions = (Vector)reaction_vector_hashtable.get(situation);
        System.out.println("in addReaction - reactions is " + reactions);
        if (reactions==null)
            reactions = new Vector();
        reactions.addElement(reaction);
        reaction_vector_hashtable.put(situation, reactions);
        System.out.println("reaction_vector_hashtable is " + reaction_vector_hashtable.toString());
        System.out.println("reactions is now " + reactions);
    }

    /** This removes a reaction (from the front end) to the list of those to
     * react to at the appropriate time.
     */

    public void removeReaction(String id)
    {
        Enumeration reaction_vectors = reaction_vector_hashtable.elements();
        while(reaction_vectors.hasMoreElements())
        {
            Vector reaction_vector = (Vector)reaction_vectors.nextElement();
            for (int i=0; i<reaction_vector.size(); i++)
            {
                Reaction reaction = (Reaction)reaction_vector.elementAt(i);
                if (reaction.id.equals(id))
                {
                    reaction_vector.removeElementAt(i);
                    break;
                }
            }
        }
    }

    public void testForConjecture(TheoryConstituent tc)
    {
        System.out.println("-------- hurray!!!!!!");
    }
}
