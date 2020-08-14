package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing a theory formation step. This is just an extension of a vector.
 * The first entry of the vector is the list of concepts which were used in the step.
 * The second entry is the production rule used. The third entry is the parameters used.
 * @author Simon Colton, started 13th December 1999
 * @version 1.0
 */

public class Step extends Vector implements Serializable
{
    /** The id of this step.
     */

    public String id = "";

    /** If a concept arises from this step, then don't develop it further
     * i.e., dont add it to the agenda.
     */

    public boolean dont_develop = false;

    /** The name of the concept (which should) arise from this step.
     */

    public String concept_arising_name = "";

    /** A score for the step. This will help place the step in an Agenda.
     */

    public double score = 0;

    /** Flag to say whether the concept was forced or not
     */

    public boolean forced = false;

    /** What happened when this step was carried out.
     */

    public String result_of_step_explanation = "";

    /** The theory object which resulted from this step.
     */

    public TheoryConstituent result_of_step = new TheoryConstituent();

    /** Simple constructor
     */

    public Step()
    {
    }

    /** Pretty printing the step.
     */

    public String asString()
    {
        // System.out.println("\n\n\nstarted asString()");
        //System.out.println("size() is " + size());
        if (size()==0) return("");
        String output = "[";
        if (size()==1)
        {
            //System.out.println("size()==1");
            Vector concept_list = (Vector)elementAt(0);
            for (int i=0;i<concept_list.size();i++)
                output = output + ((Concept)concept_list.elementAt(i)).id + " ";
            //System.out.println("output is " + output);
            output = output.trim();
        }
        if (size()==2)
        {
            //System.out.println("size()==2");
            Vector concept_list = (Vector)elementAt(0);
            ProductionRule pr = (ProductionRule)elementAt(1);
            for (int i=0;i<concept_list.size();i++)
                output = output + ((Concept)concept_list.elementAt(i)).id + " ";
            output = output.trim() + "," + pr.getName();
        }
        if (size()==3)
        {
            //System.out.println("size()==3");
            Vector concept_list = (Vector)elementAt(0);
            ProductionRule pr = (ProductionRule)elementAt(1);
            Vector parameters = (Vector)elementAt(2);
            for (int i=0;i<concept_list.size();i++)
                output = output + ((Concept)concept_list.elementAt(i)).id + " ";
            output = output.trim() + "," + pr.getName() + "," + writeParameters(parameters);
        }
        output = output + "]";
        //System.out.println("finished asString() - got output is "+output);
        return output;
    }

    public String writeParameters(Vector parameters)
    {
        String output = "[";
        for (int i=0; i<parameters.size(); i++)
        {
            Object param = parameters.elementAt(i);
            if (param instanceof Concept)
                output = output + ((Concept)param).id;
            else
                output = output + param.toString();
            if (i<parameters.size()-1)
                output = output + ", ";
        }
        return output + "]";
    }

    /** This returns the concept list used in this step.
     */

    public Vector conceptList()
    {
        return (Vector)elementAt(0);
    }

    /** This returns the production rule used in this step.
     */

    public ProductionRule productionRule()
    {
        if (size()<1)
            return new ProductionRule();
        return (ProductionRule)elementAt(1);
    }

    /** This returns the parameters used in this step.
     */

    public Vector parameters()
    {
        if (size()<2)
            return new Vector();
        return (Vector)elementAt(2);
    }
}
