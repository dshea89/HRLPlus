package com.github.dshea89.hrlplus;

// File:    GroupAgendaTheoryConstituentElement.java
// Author:  Alison Pease <alisonp@dai.ed.ac.uk>
// Created: 19/01/2003
// Updated: <>

/**
 * <b>Description:</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;
 * @author <a href="mailto:alisonp@dai.ed.ac.uk">Alison Pease</a>
 * @version 1.0, 19/01/2003
 */
public class GroupAgendaTheoryConstituentElement extends GroupAgendaElement
{

    TheoryConstituent theory_constituent = new TheoryConstituent();

    /** The default constructor */

    public GroupAgendaTheoryConstituentElement()
    {

    }

    /** This has a theory constituent and a motivation (for why the theory constituent was wanted) */

    public GroupAgendaTheoryConstituentElement(TheoryConstituent theory_constituent, Motivation motivation)
    {
        this.theory_constituent = theory_constituent;
        this.motivation = motivation;
    }

    public GroupAgendaTheoryConstituentElement(GroupAgendaTheoryConstituentElement gatce)
    {
        this.theory_constituent = gatce.theory_constituent;
        this.motivation = gatce.motivation;
    }

    // public boolean equals(GroupAgendaTheoryConstituentElement gatce)
    //{
    // if (!(theory_constituent==gatce.theory_constituent))
    //   return false;
    // if (!(motivation==gatce.motivation))
    //   return false;
    // return true;
    //}

    //needs testing. not sure whether we need motivation to be same or not (currently we do)...
    public boolean equals(GroupAgendaTheoryConstituentElement gatce)
    {
        if(this.theory_constituent instanceof Conjecture && gatce.theory_constituent instanceof Conjecture)
        {
            Conjecture conj1 = (Conjecture)this.theory_constituent;
            Conjecture conj2 = (Conjecture)gatce.theory_constituent;

            if(conj1.equals(conj2))
            {
                if(motivation.equals(gatce.motivation))
                    return true;
            }
        }
        if(this.theory_constituent instanceof Concept && gatce.theory_constituent instanceof Concept)//needs checking
        {
            if((Concept)this.theory_constituent==(Concept)gatce.theory_constituent)
            {
                {
                    if(motivation.equals(gatce.motivation))
                        return true;
                }
            }
        }

        if(this.theory_constituent instanceof Entity && gatce.theory_constituent instanceof Entity)
        {
            Entity entity1 = (Entity)this.theory_constituent;
            Entity entity2 = (Entity)gatce.theory_constituent;

            if(entity1.equals(entity2))
            {
                if(motivation.equals(gatce.motivation))
                    return true;
            }
        }
        return false;
    }


    /**
     public boolean equals(GroupAgendaTheoryConstituentElement gatce)
     {
     System.out.println("starting equals(gae)");
     boolean verdict = true;
     if(this.theory_constituent instanceof Conjecture && gatce.theory_constituent instanceof Conjecture)
     {
     System.out.println("got two conjs");
     Conjecture this_conj = (Conjecture)this.theory_constituent;
     Conjecture other_conj = (Conjecture)gatce.theory_constituent;
     System.out.println("this_conj is " + this_conj.writeConjecture("ascii"));
     System.out.println("other_conj is " + other_conj.writeConjecture("ascii"));
     System.out.println("this_conj.equals(other_conj) is " + this_conj.equals(other_conj));
     if(!(this_conj.equals(other_conj)))
     return (verdict = false);
     }

     if(this.theory_constituent instanceof Concept && gatce.theory_constituent instanceof Concept)
     {
     Concept this_concept = (Concept)this.theory_constituent;
     Concept other_concept = (Concept)gatce.theory_constituent;
     if(!(this_concept.equals(other_concept)))
     return (verdict = false);
     }

     //if (!((this.theory_constituent).equals(gatce.theory_constituent)))
     // return (verdict = false);


     if (!((this.motivation).equals(motivation)))
     return (verdict = false);

     return verdict;
     }
     */

    public String toString()
    {
        String return_string = "";

        if(this.theory_constituent instanceof Conjecture ||
                this.theory_constituent instanceof Entity ||
                this.theory_constituent instanceof Concept)
        {
            if (this.theory_constituent instanceof Conjecture)
            {
                Conjecture conj = (Conjecture)this.theory_constituent;
                return_string =  conj.id + ": " + conj.writeConjecture("ascii");
            }

            if (this.theory_constituent instanceof Entity)
            {
                Entity entity = (Entity)this.theory_constituent;
                return_string =  entity.toString();
            }

            if (this.theory_constituent instanceof Concept)
            {
                Concept concept = (Concept)this.theory_constituent;
                return_string =  concept.id + ": " + concept.writeDefinition("ascii");
            }
        }
        else
            return_string  =  "can't write this object as a string";

        return_string =  return_string + this.motivation.toString();
        return return_string;
    }
}
