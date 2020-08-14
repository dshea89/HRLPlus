package com.github.dshea89.hrlplus;

import java.io.Serializable;

// File:    Motivation.java
// Author:  Alison Pease <alisonp@dai.ed.ac.uk>
// Created: 18/01/2003
// Updated: <>

/**
 * <b>Description:</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;
 * @author <a href="mailto:alisonp@dai.ed.ac.uk">Alison Pease</a>
 * @version 1.0, 18/01/2003
 */

/** A class representing the motivation for a particular discussion
 * move. It contains the conjecture under discussion and the attempted
 * method, and exists so that the thread of the discussion is not
 * lost, eg. if the teacher asks for a concept to cover certain
 * entities, it needs to associate with this move its reasons for
 * making the request (eg. to cover some counterexamples to a certain
 * conjecture so that it cam perform piecemeal exclusion), so that it
 * knows how to use any responses.  */

public class Motivation implements Serializable
{
    Conjecture conjecture_under_discussion = new Conjecture();
    Entity entity_under_discussion = new Entity();
    ProofScheme proofscheme_under_discussion = new ProofScheme();
    String attempted_method = "";


    /** the default constructor */

    public Motivation()
    {
    }

    public Motivation(Conjecture conjecture_under_discussion, String attempted_method)
    {
        this.conjecture_under_discussion = conjecture_under_discussion;
        this.attempted_method = attempted_method;
    }

    public Motivation(Entity entity_under_discussion, String attempted_method)
    {
        this.entity_under_discussion = entity_under_discussion;
        this.attempted_method = attempted_method;
    }

    public Motivation(ProofScheme proofscheme_under_discussion, String attempted_method)
    {
        this.proofscheme_under_discussion = proofscheme_under_discussion;
        this.attempted_method = attempted_method;
    }



    //needs checking
    public String toString()
    {
        String output = new String();

        if(this.attempted_method.equals("to get discussion started"))
        {
            return " (to get discussion started)";
        }
        //else

        if(!(this.conjecture_under_discussion.writeConjecture("ascii").equals("")))
        {
            return " (in order to perform " + this.attempted_method + " on " +
                    this.conjecture_under_discussion.id + ": " +
                    this.conjecture_under_discussion.writeConjecture("ascii") + ")";
        }

        if(!(this.entity_under_discussion.name.equals("")))
        {
            return " (in order to perform " + this.attempted_method + " on " + this.entity_under_discussion.name + ")";
        }

        return output;
    }

    public boolean equals(Motivation motivation)
    {
        if(this.conjecture_under_discussion.equals(motivation.conjecture_under_discussion)
                && this.entity_under_discussion.equals(motivation.entity_under_discussion)
                && this.attempted_method.equals(motivation.attempted_method))
            return true;

        else
            return false;
    }
}