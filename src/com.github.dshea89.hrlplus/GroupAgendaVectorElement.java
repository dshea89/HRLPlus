package com.github.dshea89.hrlplus;

import java.util.Vector;

// File:    GroupAgendaVectorElement.java
// Author:  Alison Pease <alisonp@dai.ed.ac.uk>
// Created: 19/01/2003
// Updated: <>

/**
 * <b>Description:</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;
 * @author <a href="mailto:alisonp@dai.ed.ac.uk">Alison Pease</a>
 * @version 1.0, 19/01/2003
 */
public class GroupAgendaVectorElement extends GroupAgendaElement
{

    Vector vector = new Vector();

    /** The default constructor */

    public GroupAgendaVectorElement()
    {

    }

    public GroupAgendaVectorElement(GroupAgendaVectorElement gave)
    {
        this.vector = gave.vector;
    }

    /** This has a vector (which will contain entities of the theory)
     * and a motivation (for why the vector was wanted) */

    public GroupAgendaVectorElement(Vector vector, Motivation motivation)
    {
        this.vector = vector;
        this.motivation = motivation;
    }

    public boolean equals(GroupAgendaVectorElement gave)
    {
        boolean verdict = true;

        if (!((this.vector).equals(gave.vector)))
            return (verdict = false);


        if (!((this.motivation).equals(motivation)))
            return (verdict = false);

        return verdict;
    }

    public String toString()
    {
        String return_string = "";

        for(int i=0; i<vector.size(); i++)
        {
            if (vector.elementAt(i) instanceof Concept)
                return_string = return_string+((Concept)vector.elementAt(i)).writeDefinition("ascii");

            if (vector.elementAt(i) instanceof Conjecture)
                return_string = return_string+((Conjecture)vector.elementAt(i)).writeConjecture("ascii");

            if (vector.elementAt(i) instanceof Entity)
                return_string = return_string+((Entity)vector.elementAt(i)).toString();

            else
                return_string = (String)((Object)vector.elementAt(i)).toString();

            return_string = return_string+" , ";
        }

        return_string =  return_string + this.motivation.toString();
        return return_string;
    }
}
