package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A superclass for Puzzle objects.
 */
public class Puzzle implements Serializable {
    /**
     * The starting number of the puzzle (when it's a next in sequence using a function,
     * e.g. f(3), f(4), f(5), [solver needs to be told that the sequence starts with the function applied to 3.
     */
    public String starting_number = "";

    /**
     * The tuple of choices for the puzzle.
     */
    public Vector choices = new Vector();

    /**
     * The answer for the puzzle.
     */
    public String answer = "";

    /**
     * The concept embedded in this puzzle.
     */
    public Concept embedded_concept = new Concept();

    /**
     * The overall interestingness of this puzzle.
     */
    public double interestingness = 0.0D;

    /**
     * The disguise measure of this puzzle.
     */
    public double disguise = 0.0D;

    /**
     * The disguising concepts of this puzzle.
     */
    public Vector disguising_concepts = new Vector();

    /**
     * The type of puzzle ("ooo" = odd one out, "nis" = next in sequence, "analogy" = analogy).
     */
    public String type = "";

    /**
     * If this is an analogy puzzle, then the triple of clues is required. e.g., a is to b as c is to.... (a,b,c) is the triple.
     */
    public Vector analogy_triple = new Vector();

    /**
     * If this is a next-in-sequence puzzle, then these are the initial integers.
     */
    public Vector integer_sequence = new Vector();

    /**
     * The simple constructor
     */
    public Puzzle() {
    }

    /**
     * The constructor for puzzles.
     */
    public Puzzle(Vector choices_in, String answer_entity, Concept concept, String puzzle_type) {
        this.choices = (Vector)choices_in.clone();
        this.answer = answer_entity;
        this.embedded_concept = concept;
        this.type = puzzle_type;
    }

    public void randomizeOrder() {
        Vector var1 = (Vector)this.choices.clone();
        this.choices.removeAllElements();

        while(!var1.isEmpty()) {
            long var2 = Math.round(Math.random() * (double)(var1.size() - 1));
            int var4 = new Integer(Long.toString(var2));
            this.choices.addElement(var1.elementAt(var4));
            var1.removeElementAt(var4);
        }

    }

    public Object chooseRandom(Vector choices) {
        long var2 = Math.round(Math.random() * (double)(choices.size() - 1));
        int var4 = new Integer(Long.toString(var2));
        Object var5 = choices.elementAt(var4);
        choices.removeElementAt(var4);
        return var5;
    }

    public String writeAnswer() {
        String var1 = "";
        var1 = this.embedded_concept.id + ". " + this.embedded_concept.writeDefinitionWithStartLetters("ascii");
        if (this.type.equals("nis") && !this.starting_number.equals("")) {
            var1 = var1 + "\nStarting with n=" + this.starting_number;
        }

        return var1;
    }

    public String writePuzzle() {
        String var1 = "";
        if (this.type.equals("ooo")) {
            var1 = "Which is the odd one out?\n";
            var1 = var1 + this.writeChoices();
        }

        if (this.type.equals("analogy")) {
            var1 = (String)this.analogy_triple.elementAt(0) + " is to " + (String)this.analogy_triple.elementAt(1) + " as " + (String)this.analogy_triple.elementAt(2) + " is to:\n";
            var1 = var1 + this.writeChoices();
        }

        if (this.type.equals("nis")) {
            var1 = "What's next in the sequence:\n";

            for(int var2 = 0; var2 < this.integer_sequence.size(); ++var2) {
                var1 = var1 + (String)this.integer_sequence.elementAt(var2) + " ";
            }

            var1 = var1 + "(" + this.answer + ")";
            var1 = var1 + "?\n";
        }

        return var1;
    }

    public String writeChoices() {
        String var1 = "";
        String[] var2 = new String[]{"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};

        for(int var3 = 0; var3 < this.choices.size(); ++var3) {
            var1 = var1 + var2[var3] + ". ";
            String var4 = (String)this.choices.elementAt(var3);
            var1 = var1 + var4;
            if (var4.equals(this.answer)) {
                var1 = var1 + "*";
            }

            var1 = var1 + " ";
        }

        return var1 + "\n";
    }

    public void calculateDisguiseMeasure(Vector concepts) {
        for(int var2 = 0; var2 < concepts.size(); ++var2) {
            Concept var3 = (Concept)concepts.elementAt(var2);
            if (var3.arity == 1) {
                int var4 = 0;

                boolean var5;
                for(var5 = true; var5 && var4 < this.choices.size(); ++var4) {
                    String var6 = (String)this.choices.elementAt(var4);
                    if (var3.datatable.rowWithEntity(var6).tuples.isEmpty()) {
                        var5 = false;
                    }
                }

                if (var5) {
                    ++this.disguise;
                    this.disguising_concepts.addElement(var3);
                }
            }
        }

    }

    public boolean hasAlternativeSolution(Vector concepts) {
        boolean var2 = false;
        int var3;
        int var6;
        String var7;
        if (this.type.equals("ooo")) {
            for(var3 = 0; var3 < concepts.size() && !var2; ++var3) {
                int var4 = 0;
                Concept var5 = (Concept)concepts.elementAt(var3);
                if (var5 != this.embedded_concept && var5.arity == 1) {
                    for(var6 = 0; var6 < this.choices.size(); ++var6) {
                        var7 = (String)this.choices.elementAt(var6);
                        if (var5.datatable.rowWithEntity(var7).tuples.isEmpty()) {
                            ++var4;
                        }
                    }

                    if (var4 == 1) {
                        var2 = true;
                    }
                }
            }
        }

        int var9;
        Concept var12;
        if (this.type.equals("analogy")) {
            for(var3 = 0; var3 < concepts.size() && !var2; ++var3) {
                var12 = (Concept)concepts.elementAt(var3);
                if (var12 != this.embedded_concept && var12.isGeneralisationOf(this.embedded_concept) < 0) {
                    for(int var13 = 0; var13 < var12.categorisation.size(); ++var13) {
                        Vector var15 = (Vector)var12.categorisation.elementAt(var13);
                        var7 = (String)var15.elementAt(0);
                        if (!var12.datatable.rowWithEntity(var7).tuples.isEmpty()) {
                            boolean var8 = true;

                            for(var9 = 0; var9 < 3 && var8; ++var9) {
                                if (!var15.contains((String)this.analogy_triple.elementAt(var9))) {
                                    var8 = false;
                                }
                            }

                            if (var8) {
                                var9 = 0;

                                for(int var10 = 0; var10 < this.choices.size(); ++var10) {
                                    String var11 = (String)this.choices.elementAt(var10);
                                    if (!var11.equals(this.answer) && var15.contains(var11)) {
                                        ++var9;
                                    }
                                }

                                if (var9 == 1) {
                                    var2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (this.type.equals("nis")) {
            for(var3 = 0; var3 < concepts.size(); ++var3) {
                var12 = (Concept)concepts.elementAt(var3);
                if (var12.arity < 2 && var12 != this.embedded_concept && this.embedded_concept.isGeneralisationOf(var12) < 0) {
                    Vector var14 = new Vector();
                    if (var12.arity == 1) {
                        for(var6 = 0; var6 < var12.datatable.size(); ++var6) {
                            Row var17 = (Row)var12.datatable.elementAt(var6);
                            String var19 = var17.entity;
                            if (!var17.tuples.isEmpty()) {
                                var14.addElement(var19);
                            }
                        }
                    }

                    if (var12.arity == 2) {
                        boolean var16 = false;

                        int var18;
                        for(var18 = 0; var18 < var12.functions.size() && !var16; ++var18) {
                            Function var20 = (Function)var12.functions.elementAt(var18);
                            if (var20.input_columns.toString().equals("[0]") && var20.output_columns.toString().equals("[1]")) {
                                var16 = true;
                            }
                        }

                        if (var16) {
                            for(var18 = 0; var18 < var12.datatable.size(); ++var18) {
                                Row var22 = (Row)var12.datatable.elementAt(var18);

                                for(var9 = 0; var9 < var22.tuples.size(); ++var9) {
                                    Vector var24 = (Vector)var22.tuples.elementAt(var9);

                                    for(int var27 = 0; var27 < var24.size(); ++var27) {
                                        var14.addElement(var24.elementAt(var27));
                                    }
                                }
                            }
                        }
                    }

                    if (var14.size() >= this.integer_sequence.size()) {
                        for(var6 = 0; var6 < var14.size() - this.integer_sequence.size() && !var2; ++var6) {
                            boolean var21 = true;

                            for(int var23 = 0; var23 < this.integer_sequence.size() && var21; ++var23) {
                                String var25 = (String)this.integer_sequence.elementAt(var23);
                                String var26 = (String)var14.elementAt(var23 + var6);
                                if (!var25.equals(var26)) {
                                    var21 = false;
                                }
                            }

                            if (var21) {
                                var2 = true;
                            }
                        }
                    }
                }
            }
        }

        return var2;
    }
}
