package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A superclass for Puzzle objects.
 *
 * @author Simon Colton, started 24th December 2001
 * @version 1.0 */

public class Puzzle implements Serializable
{
    /** The starting number of the puzzle (when it's a next in sequence
     * using a function, e.g. f(3), f(4), f(5), [solver needs to be told that the
     * sequence starts with the function applied to 3.
     */

    public String starting_number = "";

    /** The tuple of choices for the puzzle.
     */

    public Vector choices = new Vector();

    /** The answer for the puzzle.
     */

    public String answer = "";

    /** The concept embedded in this puzzle.
     */

    public Concept embedded_concept = new Concept();

    /** The overall interestingness of this puzzle.
     */

    public double interestingness = 0;

    /** The disguise measure of this puzzle.
     */

    public double disguise = 0;

    /** The disguising concepts of this puzzle.
     */

    public Vector disguising_concepts = new Vector();

    /** The type of puzzle ("ooo" = odd one out, "nis" = next in sequence, "analogy" = analogy).
     */

    public String type = "";


    /** If this is an analogy puzzle, then the triple of clues is required.
     * e.g., a is to b as c is to.... (a,b,c) is the triple.
     */

    public Vector analogy_triple = new Vector();

    /** If this is a next-in-sequence puzzle, then these are the initial integers.
     */

    public Vector integer_sequence = new Vector();

    /** The simple constructor
     */

    public Puzzle()
    {
    }

    /** The constructor for puzzles.
     */

    public Puzzle(Vector choices_in, String answer_entity, Concept concept, String puzzle_type)
    {
        choices = (Vector)choices_in.clone();
        answer = answer_entity;
        embedded_concept = concept;
        type = puzzle_type;
    }

    public void randomizeOrder()
    {
        Vector choices_copy = (Vector)choices.clone();
        choices.removeAllElements();
        while (!choices_copy.isEmpty())
        {
            long pos = Math.round((Math.random()*(choices_copy.size()-1)));
            int posint = (new Integer(Long.toString(pos))).intValue();
            choices.addElement(choices_copy.elementAt(posint));
            choices_copy.removeElementAt(posint);
        }
    }

    public Object chooseRandom(Vector choices)
    {
        long pos = Math.round((Math.random()*(choices.size()-1)));
        int posint = (new Integer(Long.toString(pos))).intValue();
        Object output = choices.elementAt(posint);
        choices.removeElementAt(posint);
        return output;
    }

    public String writeAnswer()
    {
        String output = "";
        output = embedded_concept.id + ". " +
                embedded_concept.writeDefinitionWithStartLetters("ascii");
        if (type.equals("nis") && !starting_number.equals(""))
            output = output + "\nStarting with n="+starting_number;
        return output;
    }

    public String writePuzzle()
    {
        String output = "";
        if (type.equals("ooo"))
        {
            output = "Which is the odd one out?\n";
            output = output + writeChoices();
        }
        if (type.equals("analogy"))
        {
            output = (String)analogy_triple.elementAt(0) + " is to " +
                    (String)analogy_triple.elementAt(1) + " as " +
                    (String)analogy_triple.elementAt(2) + " is to:\n";
            output = output + writeChoices();
        }
        if (type.equals("nis"))
        {
            output = "What's next in the sequence:\n";
            for (int i=0; i<integer_sequence.size(); i++)
                output = output + (String)integer_sequence.elementAt(i) + " ";
            output = output + "(" + answer + ")";
            output = output + "?\n";
        }
        return output;
    }

    public String writeChoices()
    {
        String output = "";
        String[] markers = {"i","ii","iii","iv","v","vi","vii","viii","ix","x"};
        for (int i=0; i<choices.size(); i++)
        {
            output = output + markers[i] + ". ";
            String entity = (String)choices.elementAt(i);
            output = output + entity;
            if(entity.equals(answer))
                output = output + "*";
            output = output + " ";
        }
        return output + "\n";
    }

    public void calculateDisguiseMeasure(Vector concepts)
    {
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.arity==1)
            {
                int j=0;
                boolean count_this = true;
                while (count_this && j<choices.size())
                {
                    String entity = (String)choices.elementAt(j);
                    if (concept.datatable.rowWithEntity(entity).tuples.isEmpty())
                        count_this = false;
                    j++;
                }
                if (count_this)
                {
                    disguise++;
                    disguising_concepts.addElement(concept);
                }
            }
        }
    }

    public boolean hasAlternativeSolution(Vector concepts)
    {
        boolean output = false;
        if (type.equals("ooo"))
        {
            for (int i=0; i<concepts.size() && !output; i++)
            {
                int num_neg = 0;
                Concept concept = (Concept)concepts.elementAt(i);
                if (!(concept==embedded_concept) && concept.arity==1)
                {
                    for (int j=0; j<choices.size(); j++)
                    {
                        String positive = (String)choices.elementAt(j);
                        if (concept.datatable.rowWithEntity(positive).tuples.isEmpty())
                            num_neg++;
                    }
                    if (num_neg==1)
                        output = true;
                }
            }
        }
        if (type.equals("analogy"))
        {
            for (int i=0; i<concepts.size() && !output; i++)
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if (!(concept==embedded_concept) &&
                        concept.isGeneralisationOf(embedded_concept)<0)
                {
                    for (int j=0; j<concept.categorisation.size(); j++)
                    {
                        Vector category = (Vector)concept.categorisation.elementAt(j);
                        String first_entity = (String)category.elementAt(0);
                        if (!concept.datatable.rowWithEntity(first_entity).tuples.isEmpty())
                        {
                            boolean contains_analogy_triple = true;
                            for (int k=0; k<3 && contains_analogy_triple; k++)
                            {
                                if (!category.contains((String)analogy_triple.elementAt(k)))
                                    contains_analogy_triple = false;
                            }
                            if (contains_analogy_triple)
                            {
                                int num_choices_in_category = 0;
                                for (int k=0; k<choices.size(); k++)
                                {
                                    String choice = (String)choices.elementAt(k);
                                    if (!choice.equals(answer) && category.contains(choice))
                                        num_choices_in_category++;
                                }
                                if (num_choices_in_category==1)
                                    output = true;
                            }
                        }
                    }
                }
            }
        }
        if (type.equals("nis"))
        {
            for (int i=0; i<concepts.size(); i++)
            {
                Concept concept = (Concept)concepts.elementAt(i);
                if (concept.arity<2 && !(concept==embedded_concept) &&
                        embedded_concept.isGeneralisationOf(concept)<0)
                {
                    Vector positives = new Vector();
                    if (concept.arity==1)
                    {
                        for (int j=0; j<concept.datatable.size(); j++)
                        {
                            Row row = (Row)concept.datatable.elementAt(j);
                            String e = row.entity;
                            if (!row.tuples.isEmpty())
                                positives.addElement(e);
                        }
                    }
                    if (concept.arity==2)
                    {
                        boolean concept_is_function = false;
                        for (int j=0; j<concept.functions.size() && !concept_is_function; j++)
                        {
                            Function function = (Function)concept.functions.elementAt(j);
                            if (function.input_columns.toString().equals("[0]") &&
                                    function.output_columns.toString().equals("[1]"))
                                concept_is_function = true;
                        }
                        if (concept_is_function)
                        {
                            for (int j=0; j<concept.datatable.size(); j++)
                            {
                                {
                                    Row row = (Row)concept.datatable.elementAt(j);
                                    for (int k=0; k<row.tuples.size(); k++)
                                    {
                                        Vector tuple = (Vector)row.tuples.elementAt(k);
                                        for (int l=0; l<tuple.size(); l++)
                                            positives.addElement(tuple.elementAt(l));
                                    }
                                }
                            }
                        }
                    }

                    if (positives.size()>=integer_sequence.size())
                    {
                        for (int j=0; j<positives.size()-integer_sequence.size() && !output; j++)
                        {
                            boolean matches = true;
                            for (int k=0; k<integer_sequence.size() && matches==true; k++)
                            {
                                String ise = (String)integer_sequence.elementAt(k);
                                String positive = (String)positives.elementAt(k+j);
                                if (!ise.equals(positive))
                                    matches = false;
                            }
                            if (matches)
                                output = true;
                        }
                    }
                }
            }
        }
        return output;
    }
}
