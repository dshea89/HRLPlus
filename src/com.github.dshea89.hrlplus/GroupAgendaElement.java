package com.github.dshea89.hrlplus;

import java.io.Serializable;

// File:    GroupAgendaElement.java
// Author:  Alison Pease <alisonp@dai.ed.ac.uk>
// Created: 18/01/2003
// Updated: <>

/**
 * <b>Description: This class is the blueprint for items in the group agenda. Each item has a theory constituent part
 * (eg.a conjecture, concept, etc) - or a list of entities - and a motivation for why it was wanted.
 * </b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;
 * @author <a href="mailto:alisonp@dai.ed.ac.uk">Alison Pease</a>
 * @version 1.0, 18/01/2003
 */

public class GroupAgendaElement implements Serializable {
    Motivation motivation = new Motivation();

    public GroupAgendaElement()
    {
    }

    public GroupAgendaElement(GroupAgendaElement gae)
    {
        this.motivation = gae.motivation;
    }


    public String toString()
    {

        if(this instanceof GroupAgendaTheoryConstituentElement)
            return (String)((GroupAgendaTheoryConstituentElement)this).toString();


        if(this instanceof GroupAgendaVectorElement)
            return (String)((GroupAgendaVectorElement)this).toString();

        if(this instanceof GroupAgendaStringElement)
            return (String)((GroupAgendaStringElement)this).toString();

        else
            return "";
    }

    //doesn't work - needs repairing - 15/03/04
    public boolean equals(GroupAgendaElement gae)
    {
        if(this instanceof GroupAgendaTheoryConstituentElement && gae instanceof GroupAgendaTheoryConstituentElement)
            return ((GroupAgendaTheoryConstituentElement)this).equals((GroupAgendaTheoryConstituentElement)gae);

        if(this instanceof GroupAgendaVectorElement && gae instanceof GroupAgendaVectorElement)
            return ((GroupAgendaVectorElement)this).equals((GroupAgendaVectorElement)gae);

        if(this instanceof Request && gae instanceof Request)
            return ((Request)this).equals((Request)gae);

        if(this instanceof GroupAgendaVectorElement && gae instanceof GroupAgendaTheoryConstituentElement)

        {
            GroupAgendaVectorElement gave = (GroupAgendaVectorElement)this;
            GroupAgendaTheoryConstituentElement gatce = (GroupAgendaTheoryConstituentElement)gae;
            if(gave.vector.size()==1)
            {
                TheoryConstituent tc = (TheoryConstituent)gave.vector.elementAt(0);
                if(tc.equals(gatce.theory_constituent))
                    return true;
            }
        }

        if(gae instanceof GroupAgendaVectorElement && this instanceof GroupAgendaTheoryConstituentElement)

        {
            GroupAgendaVectorElement gave = (GroupAgendaVectorElement)gae;
            GroupAgendaTheoryConstituentElement gatce = (GroupAgendaTheoryConstituentElement)this;
            if(gave.vector.size()==1)
            {
                TheoryConstituent tc = (TheoryConstituent)gave.vector.elementAt(0);
                if(tc.equals(gatce.theory_constituent))
                    return true;
            }
        }
        return false;
    }
}
