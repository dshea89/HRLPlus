package com.github.dshea89.hrlplus;

import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class for individual reactions to certain events in the theory.
 *
 * @author Simon Colton, started 24th April 2002
 * @version 1.0 */

public class Reaction extends PseudoCodeUser implements Serializable
{
    String situation = "all_purpose";

    public Reaction(String given_id, String pseudo_code, Hashtable hashtable)
    {
        getCodeLines(pseudo_code+"\n");
        original_alias_hashtable = hashtable;
        original_alias_hashtable.put("this",this);
        id = given_id;
    }

    /** This reacts to the given object.
     */

    public void react(String situation, Object object_to_react_to)
    {
        System.out.println("situation is " + situation);
        System.out.println("object_to_react_to is " + object_to_react_to);
        original_alias_hashtable.put("object_to_react_to", object_to_react_to);
        original_alias_hashtable.put("situation", situation);
        pseudo_code_interpreter.local_alias_hashtable = (Hashtable)original_alias_hashtable.clone();
        System.out.println("pseudo_code_lines are " + pseudo_code_lines);
        pseudo_code_interpreter.runPseudoCode(pseudo_code_lines);
    }

    /** This sets the situation of this reaction.
     */

    public void setSituation(String given_situation)
    {
        situation = given_situation;
    }
}
