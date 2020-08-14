package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.awt.*;
import java.io.Serializable;

public class PuzzleGenerator implements Serializable
{
    int number_of_puzzles = 0;

    private Vector sortPuzzles(Vector puzzles)
    {
        Vector ordered_puzzles = new Vector();
        for (int i=0; i<puzzles.size();i++)
        {
            Puzzle puzzle = (Puzzle)puzzles.elementAt(i);
            int j=0;
            boolean placed = false;

            while (j<ordered_puzzles.size() && !placed)
            {
                Puzzle other_puzzle = (Puzzle)ordered_puzzles.elementAt(j);
                if (puzzle.interestingness > other_puzzle.interestingness)
                {
                    placed = true;
                    ordered_puzzles.insertElementAt(puzzle, j);
                }
                j++;
            }
            if (!placed)
                ordered_puzzles.addElement(puzzle);
        }
        return ordered_puzzles;
    }

    public void generatePuzzles(TextArea output_text, int ooo_choices_size,
                                int nis_choices_size, int analogy_choices_size,
                                Vector concepts)
    {
        output_text.setText("");
        Timer timer = new Timer();
        timer.startStopWatch("puzzle_timer");
        number_of_puzzles = 0;
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            concept.ooo_puzzles = new Vector();
            concept.nis_puzzles = new Vector();
            concept.analogy_puzzles = new Vector();
            if (ooo_choices_size>0)
                makeOddOneOutPuzzles(ooo_choices_size, concept, concepts);
            if (nis_choices_size>0)
                makeNextInSequencePuzzles(nis_choices_size, concept, concepts);
            if (analogy_choices_size>0)
                makeAnalogyPuzzles(analogy_choices_size, concept, concepts);
        }
        String for_box = "";
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            concept.ooo_puzzles = sortPuzzles(concept.ooo_puzzles);
            concept.nis_puzzles = sortPuzzles(concept.nis_puzzles);
            concept.analogy_puzzles = sortPuzzles(concept.analogy_puzzles);
            for_box = for_box + writePuzzles(concept.ooo_puzzles);
            for_box = for_box + writePuzzles(concept.nis_puzzles);
            for_box = for_box + writePuzzles(concept.analogy_puzzles);
        }
        String top_text =
                "time="+Long.toString(timer.millisecondsPassed("puzzle_timer"))+" milliseconds\n" +
                        "number="+number_of_puzzles+"\n"+
                        "\n------------------------\n";
        output_text.setText(top_text + for_box);
        timer.stopStopWatch("puzzle_timer");
    }

    public String writePuzzles(Vector puzzles)
    {
        String output = "";
        if (!puzzles.isEmpty())
        {
            Puzzle top_puzzle = (Puzzle)puzzles.elementAt(0);
            number_of_puzzles++;
            output = output +  "interestingness="+Double.toString(top_puzzle.interestingness)+
                    ", disguise="+Double.toString(top_puzzle.disguise)+
                    ", complexity="+Integer.toString(top_puzzle.embedded_concept.complexity)+"\n";
            output = output + top_puzzle.writePuzzle();
            output = output + "answer: "+top_puzzle.writeAnswer();
            if (!top_puzzle.disguising_concepts.isEmpty())
            {
                output = output + "\nDisguising Concepts:";
                for (int i=0; i<top_puzzle.disguising_concepts.size(); i++)
                {
                    Concept disguiser = (Concept)top_puzzle.disguising_concepts.elementAt(i);
                    output = output + "\n"+disguiser.id+". "+disguiser.writeDefinition("ascii");
                }
            }
            output = output + "\n------------------------\n";
        }
        return output;
    }

    public Vector makeAnalogyPuzzles(int choices_size, Concept concept, Vector concepts)
    {
        Vector output = new Vector();
        Vector entities = new Vector();
        for (int i=0; i<concept.datatable.size(); i++)
            entities.addElement(((Row)concept.datatable.elementAt(i)).entity);
        if (concept.arity!=1)
            return output;
        Vector categorisation = (Vector)concept.categorisation.clone();
        for (int i=0; i<categorisation.size(); i++)
        {
            Vector analogy_category = (Vector)categorisation.elementAt(i);
            if (analogy_category.size()>=4 &&
                    analogy_category.size()<=entities.size()-choices_size+1)
            {
                Vector other_entities = new Vector();
                for (int j=0; j<entities.size(); j++)
                {
                    String entity = (String)entities.elementAt(j);
                    if (!analogy_category.contains(entity))
                        other_entities.addElement(entity);
                }
                Vector other_choices = allTuples(choices_size-1, other_entities);
                String first_entity = (String)analogy_category.elementAt(i);
                Row first_entity_row = concept.datatable.rowWithEntity(first_entity);
                if (!first_entity_row.tuples.isEmpty())
                {
                    Vector all_fours = allTuples(4, analogy_category);
                    boolean found_one = false;
                    while (!all_fours.isEmpty() && !other_choices.isEmpty() && !found_one)
                    {
                        Vector four = (Vector)chooseRandom(all_fours);
                        Vector choices = (Vector)chooseRandom(other_choices);
                        String answer = (String)four.elementAt(3);
                        choices.addElement(answer);
                        four.removeElementAt(3);
                        Puzzle puzzle = new Puzzle(choices, answer, concept, "analogy");
                        puzzle.randomizeOrder();
                        puzzle.analogy_triple = four;
                        if (!puzzle.hasAlternativeSolution(concepts))
                        {
                            output.addElement(puzzle);
                            concept.analogy_puzzles.addElement(puzzle);
                            found_one = true;
                        }
                    }
                }
            }
        }
        return output;
    }

    public Vector makeNextInSequencePuzzles(int choices_size, Concept concept, Vector concepts)
    {
        Vector output = new Vector();
        String types_string = concept.types.toString();
        if (concept.complexity < 3)
            choices_size = choices_size/2 + 1;
        Vector positives = new Vector();
        Vector starting_numbers = new Vector();
        if (types_string.equals("[integer]"))
        {
            for (int i=0; i<concept.datatable.size(); i++)
            {
                {
                    Row row = (Row)concept.datatable.elementAt(i);
                    String e = row.entity;
                    if (!row.tuples.isEmpty())
                        positives.addElement(e);
                }
            }
        }
        if (types_string.equals("[integer, integer]"))
        {
            boolean concept_is_function = false;
            for (int i=0; i<concept.functions.size() && !concept_is_function; i++)
            {
                Function function = (Function)concept.functions.elementAt(i);
                if (function.input_columns.toString().equals("[0]") &&
                        function.output_columns.toString().equals("[1]"))
                    concept_is_function = true;
            }
            if (concept_is_function)
            {
                for (int i=0; i<concept.datatable.size(); i++)
                {
                    {
                        Row row = (Row)concept.datatable.elementAt(i);
                        for (int j=0; j<row.tuples.size(); j++)
                        {
                            Vector tuple = (Vector)row.tuples.elementAt(j);
                            for (int k=0; k<tuple.size(); k++)
                                positives.addElement(tuple.elementAt(k));
                        }
                        starting_numbers.addElement(row.entity);
                    }
                }
            }
        }
        if ((types_string.equals("[integer]") || types_string.equals("[integer, integer]")) &&
                positives.size()>=choices_size)
        {
            int start_pos = positives.size()-choices_size;
            boolean found_one = false;
            for (int i=start_pos; i>0 && !found_one; i--)
            {
                Vector integer_sequence = new Vector();
                Vector different_numbers = new Vector();
                for (int j=0; j<choices_size; j++)
                {
                    String number = (String)positives.elementAt(i+j);
                    integer_sequence.addElement(number);
                    if (!different_numbers.contains(number))
                        different_numbers.addElement(number);
                }
                String answer = (String)integer_sequence.lastElement();
                integer_sequence.removeElementAt(integer_sequence.size()-1);
                Puzzle puzzle = new Puzzle(new Vector(), answer, concept, "nis");
                puzzle.integer_sequence = integer_sequence;
                if (starting_numbers.size()>start_pos)
                    puzzle.starting_number = (String)starting_numbers.elementAt(start_pos);
                if (different_numbers.size()>2 && !puzzle.hasAlternativeSolution(concepts))
                {
                    found_one = true;
                    concept.nis_puzzles.addElement(puzzle);
                    if (concept.complexity<3)
                        addDisguisingSequence(puzzle, concepts);
                    output.addElement(puzzle);
                }
            }
        }
        return output;
    }

    public void addDisguisingSequence(Puzzle nis_puzzle, Vector concepts)
    {
        Vector possibilities = new Vector();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.arity==1 && concept.complexity<3)
            {
                Vector positives = new Vector();
                for (int j=0; j<concept.datatable.size(); j++)
                {
                    {
                        Row row = (Row)concept.datatable.elementAt(j);
                        String e = row.entity;
                        if (!row.tuples.isEmpty())
                            positives.addElement(e);
                    }
                }
                if (positives.size()>=nis_puzzle.integer_sequence.size())
                {
                    Vector possibility = new Vector();
                    possibility.addElement(concept);
                    possibility.addElement(positives);
                    possibilities.addElement(possibility);
                }
            }
        }
        boolean found_one = false;
        while (!found_one && !possibilities.isEmpty())
        {
            Vector possibility = (Vector)chooseRandom(possibilities);
            Concept concept = (Concept)possibility.elementAt(0);
            Vector positives = (Vector)possibility.elementAt(1);
            Vector new_integer_sequence = new Vector();
            for (int i=0; i<nis_puzzle.integer_sequence.size(); i++)
            {
                new_integer_sequence.addElement(nis_puzzle.integer_sequence.elementAt(i));
                new_integer_sequence.addElement(positives.elementAt(i));
            }
            Puzzle try_puzzle = new Puzzle(new Vector(), "", new Concept(), "nis");
            try_puzzle.integer_sequence = new_integer_sequence;
            if (!try_puzzle.hasAlternativeSolution(concepts))
            {
                nis_puzzle.integer_sequence = new_integer_sequence;
                found_one = true;
                nis_puzzle.disguising_concepts.addElement(concept);
                nis_puzzle.disguise=1;
            }
        }
    }

    public Vector makeOddOneOutPuzzles(int choices_size, Concept concept, Vector concepts)
    {
        Vector output = new Vector();
        if (concept.arity!=1)
            return output;
        Vector all_positives = new Vector();
        Vector all_negatives = new Vector();
        for (int i=0; i<concept.datatable.size(); i++)
        {
            Row row = (Row)concept.datatable.elementAt(i);
            String e = row.entity;
            if (row.tuples.isEmpty())
                all_negatives.addElement(e);
            else
                all_positives.addElement(e);
        }

        Vector all_triples = allTuples(choices_size-1,all_positives);
        for (int i=0; i<all_triples.size(); i++)
        {
            Vector positives = (Vector)chooseRandom(all_triples);
            for (int j=0; j<all_negatives.size(); j++)
            {
                String odd_entity = (String)all_negatives.elementAt(j);
                Vector choices = (Vector)positives.clone();
                choices.addElement(odd_entity);
                Puzzle ooo = new Puzzle(choices, odd_entity, concept, "ooo");
                if (!ooo.hasAlternativeSolution(concepts))
                {
                    ooo.randomizeOrder();
                    ooo.calculateDisguiseMeasure(concepts);
                    ooo.interestingness = (new Double(ooo.disguise + ooo.embedded_concept.complexity)).doubleValue()/2;
                    output.addElement(ooo);
                    concept.ooo_puzzles.addElement(ooo);
                }
            }
        }
        return output;
    }

    public Vector allTuples(int arity, Vector elements)
    {
        Vector output = new Vector();
        output.addElement(new Vector());

        for (int i=0; i<arity; i++)
        {
            int os = output.size();
            for (int j=0; j<os; j++)
            {
                Vector v = (Vector)output.elementAt(0);
                int pos = -1;
                if (!v.isEmpty())
                    pos = elements.indexOf(v.lastElement());
                for (int k=pos+1; k<elements.size(); k++)
                {
                    Vector new_v = (Vector)v.clone();
                    new_v.addElement(elements.elementAt(k));
                    output.addElement(new_v);
                }
                output.removeElementAt(0);
            }
        }
        return output;
    }

    public Object chooseRandom(Vector choices)
    {
        long pos = Math.round((Math.random()*(choices.size()-1)));
        int posint = (new Integer(Long.toString(pos))).intValue();
        Object output = choices.elementAt(posint);
        choices.removeElementAt(posint);
        return output;
    }
}
