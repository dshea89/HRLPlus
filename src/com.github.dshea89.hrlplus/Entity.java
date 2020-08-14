package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.List;
import java.io.Serializable;

/** A class for storing information about the objects of interest (entities) in the
 * theory.
 *
 * @author Simon Colton, started 7th December 2000
 * @version 1.0 */

public class Entity extends TheoryConstituent implements Serializable
{
    /** Whether or not this should be displayed in table format.
     */

    public boolean required_in_table_format = false;

    /** The domain of this entity.
     */

    public String domain = "";

    /** The conjecture (if any) which introduced this entity as a counterexample.
     */

    public Conjecture conjecture = new Conjecture();

    /** The name of this entity (e.g. c2xc4 in group theory ).
     */

    public String name = "";

    /** A hashtable for the concept values (as tuples) for each
     * user-given concept.
     */

    public Hashtable concept_data = new Hashtable();

    /** Whether the entity has been picked out using Lakatos methods, and
     * if so which one.
     */

    public String lakatos_method = "no";



    /** Default constructor for entity class.
     */

    public Entity()
    {
    }

    /** Default constructor for entity class.
     */

    public Entity(String entity_name)
    {
        name = entity_name;
    }

    /** Writes full details of this entity.
     */

    public String fullDetails(Vector concepts, String[] attributes_in)
    {
        Vector attributes = new Vector();
        for (int i=0; i<attributes_in.length; i++)
            attributes.addElement(attributes_in[i]);

        String output = "";
        output = name + "\n";
        output = output + domain + "\n\n";

        if (attributes.contains("representation"))
            output = output + userConcepts(concepts) + "\n\n";
        if (attributes.contains("conjecture") &&
                !conjecture.writeConjecture("ascii").equals(""))
        {
            output = output + "Conjecture:\n";
            output = output + conjecture.writeConjecture("ascii") + "\n";
            output = output + conjecture.writeConjecture("otter");
        }
        if (attributes.contains("related"))
            output = output + "\n" + mostRelated(concepts);

        if (attributes.contains("distinctive"))
            output = output + "\n" + distinctiveFor(concepts, "ascii");

        return output;
    }

    /** Writes full details of this entity.
     */

    public String fullHTMLDetails(Vector concepts, String[] attributes_in)
    {
        Vector attributes = new Vector();
        for (int i=0; i<attributes_in.length; i++)
            attributes.addElement(attributes_in[i]);

        String output = "<table border=1 bgcolor=yellow><tr><td>" + name + "</td></tr></table><br>\n";
        output = output + domain + "<hr>\n";

        if (attributes.contains("representation"))
        {
            output = output + "<u><b>Values for user-given concepts</b></u><p>\n";
            output = output + userConcepts(concepts) + "<p><hr>";
        }

        String main_language = "ascii";
        if (!conjecture.writeConjecture("otter").equals(""))
        {
            int num_defs = 0;

            if (attributes.contains("ascii conjecture"))
            {
                num_defs++;
                main_language = "ascii";
            }
            if (attributes.contains("otter conjecture"))
            {
                num_defs++;
                main_language = "otter";
            }
            if (attributes.contains("prolog conjecture"))
            {
                num_defs++;
                main_language = "prolog";
            }
            if (attributes.contains("tptp conjecture"))
            {
                num_defs++;
                main_language = "tptp";
            }

            if (num_defs == 1)
            {
                output = output + "<u><b>Conjecture for which this entity was a counterexample</b></u><p>";
                output = output + conjecture.writeConjectureHTML(main_language) + "<p>\n";
            }

            if (num_defs > 1)
            {
                main_language = "ascii";
                output = output + "<u><b>Conjecture for which this entity was a counterexample</b></u><p><ul>\n";
                if (attributes.contains("ascii conjecture"))
                    output = output + "<li>" + conjecture.writeConjectureHTML("ascii") + "</li>\n";
                if (attributes.contains("otter conjecture"))
                    output = output + "<li>" + conjecture.writeConjectureHTML("otter") + "</li>\n";
                if (attributes.contains("prolog conjecture"))
                    output = output + "<li>" + conjecture.writeConjectureHTML("prolog") + "</li>\n";
                if (attributes.contains("tptp conjecture"))
                    output = output + "<li>" + conjecture.writeConjectureHTML("tptp") + "</li>\n";
                output = output + "</ul>\n";
            }
        }

        if (attributes.contains("related"))
            output = output + "<hr><b><u>Most related entities</u></b><br>" + mostRelated(concepts);

        if (attributes.contains("distinctive"))
            output = output + "<hr><b><u>Concepts for which this entity is distinct</u></b><p>"
                    + distinctiveFor(concepts, main_language);

        return output;
    }

    /** Pretty prints the user-given concepts for this entity.
     */

    public String userConcepts(Vector concepts)
    {
        String output = "<table border=1>\n";
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.is_user_given)
                output = output + "<tr><td>" + concept.name + "</td><td>" + conceptAsTable(concept) + "</td></tr>\n";
        }
        output = output + "</table>\n";
        return output;
    }

    private String conceptAsTable(Concept concept)
    {
        Row row = concept.datatable.rowWithEntity(name);
        if (concept.arity==1)
        {
            return (new Boolean(!row.tuples.isEmpty())).toString();
        }
        if (concept.arity==2)
        {
            if (row.tuples.size()==1)
                return (String)((Vector)(row.tuples.elementAt(0))).elementAt(0);
        }
        if (concept.arity==3)
        {
            boolean is_function = false;
            for (int i=0; i<concept.functions.size(); i++)
            {
                Function function = (Function)concept.functions.elementAt(i);
                if (function.output_columns.toString().equals("[2]") &&
                        function.input_columns.toString().equals("[0, 1]"))
                    is_function = true;
            }
            if (is_function)
            {
                String top_row = "<pre>";
                String divider = "";
                String bottom_row = "";
                for (int i=0; i<row.tuples.size(); i++)
                {
                    Vector tuple = (Vector)row.tuples.elementAt(i);
                    String top_element = (String)tuple.elementAt(0);
                    String bottom_element = (String)tuple.elementAt(1);
                    top_row = top_row + "|" + top_element;
                    divider = divider + "+";
                    divider = divider + repeat("-",top_element.length());
                    bottom_row = bottom_row + "|" + bottom_element;
                    divider = divider + repeat("-", bottom_element.length() - top_element.length());
                }
                top_row = top_row + "|\n";
                bottom_row = bottom_row + "|";
                divider = divider + "+\n";
                return top_row + divider + bottom_row + "</pre>";
            }
        }
        if (concept.arity==4)
        {
            boolean is_function = false;
            for (int i=0; i<concept.functions.size(); i++)
            {
                Function function = (Function)concept.functions.elementAt(i);
                if (function.output_columns.toString().equals("[3]") &&
                        function.input_columns.toString().equals("[0, 1, 2]"))
                    is_function = true;
            }
            if (is_function)
            {
                String top_row = "<pre> |";
                String body_row = "";
                String body = "";
                String lh_element = "";
                boolean getting_top_row = true;
                int lh_max_len = 0;
                for (int i=0; i<row.tuples.size(); i++)
                {
                    Vector tuple = (Vector)row.tuples.elementAt(i);
                    String new_lh_element = (String)tuple.elementAt(0);
                    String new_top_element = (String)tuple.elementAt(1);
                    String body_element = (String)tuple.elementAt(2);
                    if (!new_lh_element.equals(lh_element))
                    {
                        if (!body_row.equals(""))
                        {
                            body = body + body_row + "\n";
                            getting_top_row = false;
                        }
                        if (lh_element.length()>lh_max_len)
                            lh_max_len = lh_element.length();
                        lh_element = new_lh_element;
                        body_row = lh_element + "|";
                    }
                    body_row = body_row + body_element;
                    if (getting_top_row)
                        top_row = top_row + new_top_element;
                }
                body = body + body_row;
                String divider = repeat("-",top_row.trim().length()-5);
                top_row = top_row + "\n" + divider;
                return top_row + "\n" + body + "</pre>";
            }
        }
        return row.tuples.toString();
    }

    private String repeat(String str, int num)
    {
        if (num <= 0)
            return "";
        String output = "";
        for (int i=0; i<num; i++)
            output = output + str;
        return output;
    }

    /** This returns a string representation of the entities in descending order
     * of how related (in terms of the number of times they appear together in
     * a categorisation).
     */

    public String mostRelated(Vector concepts)
    {
        String entity = name;
        Vector other_entities = new Vector();
        Vector scores = new Vector();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            Vector categorisation = concept.categorisation;
            for (int j=0; j<categorisation.size(); j++)
            {
                Vector category = (Vector)categorisation.elementAt(j);
                if (category.contains(entity))
                {
                    for (int k=0; k<category.size(); k++)
                    {
                        String other_entity = (String)category.elementAt(k);
                        if (!other_entity.equals(entity))
                        {
                            int index = other_entities.indexOf(other_entity);
                            if (index>=0)
                            {
                                int score = ((Integer)scores.elementAt(index)).intValue();
                                scores.setElementAt(new Integer(score+1), index);
                            }
                            else
                            {
                                other_entities.addElement(other_entity);
                                scores.addElement(new Integer(1));
                            }
                        }
                    }
                }
            }
        }

        SortableVector sv = new SortableVector();
        for (int i=0; i<other_entities.size(); i++)
        {
            String other_entity = (String)other_entities.elementAt(i);
            Integer score = (Integer)scores.elementAt(i);
            int sc = score.intValue();
            sv.addElement(other_entity, sc);

        }

        String output = "<table border=0><tr><td><u>Entity</u></td><td><u>Number of relating concepts</u></td></tr>\n";
        for (int i=0; i<sv.size(); i++)
        {
            String other_entity = (String)sv.elementAt(i);
            Double val = (Double)sv.values.elementAt(i);
            output = output + "<tr><td>" + other_entity + "</td><td align=center>" + val.toString() + "</td></tr>\n";
        }
        output = output + "</table>\n";
        return output;
    }

    /** This returns a string representation of the list of concepts for which this
     * entity is in a category of its own.
     */

    public String distinctiveFor(Vector concepts, String main_language)
    {
        String output = "";
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            Vector cat = concept.categorisation;
            boolean found = false;
            int j=0;
            while (j<cat.size() && !found)
            {
                Vector category = (Vector)cat.elementAt(j);
                if (category.contains(name))
                {
                    found = true;
                    if (category.size()==1)
                        output = output + concept.id + ". " +
                                replaceLTForHTML(concept.writeDefinitionWithStartLetters(main_language))+"<br>\n";
                }
                j++;
            }
        }
        return output;
    }

    public String toString()
    {
        return name;
    }

    //returns true if the name of the given entity is the same as the
    //name of one of the entities in the vector entities, false
    //otherwise
    public boolean entitiesContains(Vector entities)
    {

        for(int i = 0; i<entities.size(); i++)
        {
            Entity current_entity = (Entity)entities.elementAt(i);
            if (this.name.equals(current_entity.name))
                return true;
        }
        return false;
    }

    /** Removes the entity_string from the vector of entity_strings
     (if it is in it), and returns the depleted vector */
    public Vector removeEntityStringFromVector(Vector entities, AgentWindow window)
    {
        for(int i=0; i<entities.size(); i++)
        {
            Object current_entity_object = entities.elementAt(i);
            if(current_entity_object instanceof String)
            {
                String current_entity_string = (String)entities.elementAt(i);
                if((this.toString()).equals(current_entity_string))
                    entities.removeElementAt(i);
            }
            if(current_entity_object instanceof Entity)
            {
                Entity current_entity = (Entity)entities.elementAt(i);
                if((this.toString()).equals(current_entity.toString()))
                    entities.removeElementAt(i);
            }
        }
        return entities;
    }


    /** tested and working. but still having probs
     */

    //  public boolean equals(Entity entity)
//   {
//     if((this.toString()).equals(entity.toString()))
//       return true;

//     else
//       return false;
//   }

    /** dec 2006 -- need to test
     */

    public boolean equals(Object entity)
    {
        if(!(entity instanceof Entity))
        {
            System.out.println("Warning: Entity equals method - not an entity");
            if(entity instanceof Conjecture)
                System.out.println("it's a conj: " + ((Conjecture)entity).writeConjecture());
            if(entity instanceof Concept)
                System.out.println("it's a concept: " + ((Concept)entity).writeDefinition());
        }
        else
        {
            if((this.toString()).equals(((Entity)entity).toString()))
                return true;
        }
        return false;
    }
}
