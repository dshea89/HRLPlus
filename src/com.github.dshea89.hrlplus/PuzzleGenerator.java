package com.github.dshea89.hrlplus;

import java.awt.TextArea;
import java.io.Serializable;
import java.util.Vector;

public class PuzzleGenerator implements Serializable {
    int number_of_puzzles = 0;

    public PuzzleGenerator() {
    }

    private Vector sortPuzzles(Vector puzzles) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < puzzles.size(); ++var3) {
            Puzzle var4 = (Puzzle)puzzles.elementAt(var3);
            int var5 = 0;

            boolean var6;
            for(var6 = false; var5 < var2.size() && !var6; ++var5) {
                Puzzle var7 = (Puzzle)var2.elementAt(var5);
                if (var4.interestingness > var7.interestingness) {
                    var6 = true;
                    var2.insertElementAt(var4, var5);
                }
            }

            if (!var6) {
                var2.addElement(var4);
            }
        }

        return var2;
    }

    public void generatePuzzles(TextArea output_text, int ooo_choices_size, int nis_choices_size, int analogy_choices_size, Vector concepts) {
        output_text.setText("");
        Timer var6 = new Timer();
        var6.startStopWatch("puzzle_timer");
        this.number_of_puzzles = 0;

        for(int var7 = 0; var7 < concepts.size(); ++var7) {
            Concept var8 = (Concept)concepts.elementAt(var7);
            var8.ooo_puzzles = new Vector();
            var8.nis_puzzles = new Vector();
            var8.analogy_puzzles = new Vector();
            if (ooo_choices_size > 0) {
                this.makeOddOneOutPuzzles(ooo_choices_size, var8, concepts);
            }

            if (nis_choices_size > 0) {
                this.makeNextInSequencePuzzles(nis_choices_size, var8, concepts);
            }

            if (analogy_choices_size > 0) {
                this.makeAnalogyPuzzles(analogy_choices_size, var8, concepts);
            }
        }

        String var10 = "";

        for(int var11 = 0; var11 < concepts.size(); ++var11) {
            Concept var9 = (Concept)concepts.elementAt(var11);
            var9.ooo_puzzles = this.sortPuzzles(var9.ooo_puzzles);
            var9.nis_puzzles = this.sortPuzzles(var9.nis_puzzles);
            var9.analogy_puzzles = this.sortPuzzles(var9.analogy_puzzles);
            var10 = var10 + this.writePuzzles(var9.ooo_puzzles);
            var10 = var10 + this.writePuzzles(var9.nis_puzzles);
            var10 = var10 + this.writePuzzles(var9.analogy_puzzles);
        }

        String var12 = "time=" + Long.toString(var6.millisecondsPassed("puzzle_timer")) + " milliseconds\n" + "number=" + this.number_of_puzzles + "\n" + "\n------------------------\n";
        output_text.setText(var12 + var10);
        var6.stopStopWatch("puzzle_timer");
    }

    public String writePuzzles(Vector puzzles) {
        String var2 = "";
        if (!puzzles.isEmpty()) {
            Puzzle var3 = (Puzzle)puzzles.elementAt(0);
            ++this.number_of_puzzles;
            var2 = var2 + "interestingness=" + Double.toString(var3.interestingness) + ", disguise=" + Double.toString(var3.disguise) + ", complexity=" + Integer.toString(var3.embedded_concept.complexity) + "\n";
            var2 = var2 + var3.writePuzzle();
            var2 = var2 + "answer: " + var3.writeAnswer();
            if (!var3.disguising_concepts.isEmpty()) {
                var2 = var2 + "\nDisguising Concepts:";

                for(int var4 = 0; var4 < var3.disguising_concepts.size(); ++var4) {
                    Concept var5 = (Concept)var3.disguising_concepts.elementAt(var4);
                    var2 = var2 + "\n" + var5.id + ". " + var5.writeDefinition("ascii");
                }
            }

            var2 = var2 + "\n------------------------\n";
        }

        return var2;
    }

    public Vector makeAnalogyPuzzles(int choices_size, Concept concept, Vector concepts) {
        Vector var4 = new Vector();
        Vector var5 = new Vector();

        for(int var6 = 0; var6 < concept.datatable.size(); ++var6) {
            var5.addElement(((Row)concept.datatable.elementAt(var6)).entity);
        }

        if (concept.arity != 1) {
            return var4;
        } else {
            Vector var19 = (Vector)concept.categorisation.clone();

            for(int var7 = 0; var7 < var19.size(); ++var7) {
                Vector var8 = (Vector)var19.elementAt(var7);
                if (var8.size() >= 4 && var8.size() <= var5.size() - choices_size + 1) {
                    Vector var9 = new Vector();

                    String var11;
                    for(int var10 = 0; var10 < var5.size(); ++var10) {
                        var11 = (String)var5.elementAt(var10);
                        if (!var8.contains(var11)) {
                            var9.addElement(var11);
                        }
                    }

                    Vector var20 = this.allTuples(choices_size - 1, var9);
                    var11 = (String)var8.elementAt(var7);
                    Row var12 = concept.datatable.rowWithEntity(var11);
                    if (!var12.tuples.isEmpty()) {
                        Vector var13 = this.allTuples(4, var8);
                        boolean var14 = false;

                        while(!var13.isEmpty() && !var20.isEmpty() && !var14) {
                            Vector var15 = (Vector)this.chooseRandom(var13);
                            Vector var16 = (Vector)this.chooseRandom(var20);
                            String var17 = (String)var15.elementAt(3);
                            var16.addElement(var17);
                            var15.removeElementAt(3);
                            Puzzle var18 = new Puzzle(var16, var17, concept, "analogy");
                            var18.randomizeOrder();
                            var18.analogy_triple = var15;
                            if (!var18.hasAlternativeSolution(concepts)) {
                                var4.addElement(var18);
                                concept.analogy_puzzles.addElement(var18);
                                var14 = true;
                            }
                        }
                    }
                }
            }

            return var4;
        }
    }

    public Vector makeNextInSequencePuzzles(int choices_size, Concept concept, Vector concepts) {
        Vector var4 = new Vector();
        String var5 = concept.types.toString();
        if (concept.complexity < 3) {
            choices_size = choices_size / 2 + 1;
        }

        Vector var6 = new Vector();
        Vector var7 = new Vector();
        int var8;
        if (var5.equals("[integer]")) {
            for(var8 = 0; var8 < concept.datatable.size(); ++var8) {
                Row var9 = (Row)concept.datatable.elementAt(var8);
                String var10 = var9.entity;
                if (!var9.tuples.isEmpty()) {
                    var6.addElement(var10);
                }
            }
        }

        Vector var12;
        int var13;
        if (var5.equals("[integer, integer]")) {
            boolean var15 = false;

            int var16;
            for(var16 = 0; var16 < concept.functions.size() && !var15; ++var16) {
                Function var17 = (Function)concept.functions.elementAt(var16);
                if (var17.input_columns.toString().equals("[0]") && var17.output_columns.toString().equals("[1]")) {
                    var15 = true;
                }
            }

            if (var15) {
                for(var16 = 0; var16 < concept.datatable.size(); ++var16) {
                    Row var19 = (Row)concept.datatable.elementAt(var16);

                    for(int var11 = 0; var11 < var19.tuples.size(); ++var11) {
                        var12 = (Vector)var19.tuples.elementAt(var11);

                        for(var13 = 0; var13 < var12.size(); ++var13) {
                            var6.addElement(var12.elementAt(var13));
                        }
                    }

                    var7.addElement(var19.entity);
                }
            }
        }

        if ((var5.equals("[integer]") || var5.equals("[integer, integer]")) && var6.size() >= choices_size) {
            var8 = var6.size() - choices_size;
            boolean var18 = false;

            for(int var20 = var8; var20 > 0 && !var18; --var20) {
                Vector var21 = new Vector();
                var12 = new Vector();

                for(var13 = 0; var13 < choices_size; ++var13) {
                    String var14 = (String)var6.elementAt(var20 + var13);
                    var21.addElement(var14);
                    if (!var12.contains(var14)) {
                        var12.addElement(var14);
                    }
                }

                String var22 = (String)var21.lastElement();
                var21.removeElementAt(var21.size() - 1);
                Puzzle var23 = new Puzzle(new Vector(), var22, concept, "nis");
                var23.integer_sequence = var21;
                if (var7.size() > var8) {
                    var23.starting_number = (String)var7.elementAt(var8);
                }

                if (var12.size() > 2 && !var23.hasAlternativeSolution(concepts)) {
                    var18 = true;
                    concept.nis_puzzles.addElement(var23);
                    if (concept.complexity < 3) {
                        this.addDisguisingSequence(var23, concepts);
                    }

                    var4.addElement(var23);
                }
            }
        }

        return var4;
    }

    public void addDisguisingSequence(Puzzle nis_puzzle, Vector concepts) {
        Vector var3 = new Vector();

        Vector var13;
        for(int var4 = 0; var4 < concepts.size(); ++var4) {
            Concept var5 = (Concept)concepts.elementAt(var4);
            if (var5.arity == 1 && var5.complexity < 3) {
                Vector var6 = new Vector();

                for(int var7 = 0; var7 < var5.datatable.size(); ++var7) {
                    Row var8 = (Row)var5.datatable.elementAt(var7);
                    String var9 = var8.entity;
                    if (!var8.tuples.isEmpty()) {
                        var6.addElement(var9);
                    }
                }

                if (var6.size() >= nis_puzzle.integer_sequence.size()) {
                    var13 = new Vector();
                    var13.addElement(var5);
                    var13.addElement(var6);
                    var3.addElement(var13);
                }
            }
        }

        boolean var10 = false;

        while(!var10 && !var3.isEmpty()) {
            Vector var11 = (Vector)this.chooseRandom(var3);
            Concept var12 = (Concept)var11.elementAt(0);
            var13 = (Vector)var11.elementAt(1);
            Vector var14 = new Vector();

            for(int var15 = 0; var15 < nis_puzzle.integer_sequence.size(); ++var15) {
                var14.addElement(nis_puzzle.integer_sequence.elementAt(var15));
                var14.addElement(var13.elementAt(var15));
            }

            Puzzle var16 = new Puzzle(new Vector(), "", new Concept(), "nis");
            var16.integer_sequence = var14;
            if (!var16.hasAlternativeSolution(concepts)) {
                nis_puzzle.integer_sequence = var14;
                var10 = true;
                nis_puzzle.disguising_concepts.addElement(var12);
                nis_puzzle.disguise = 1.0D;
            }
        }

    }

    public Vector makeOddOneOutPuzzles(int choices_size, Concept concept, Vector concepts) {
        Vector var4 = new Vector();
        if (concept.arity != 1) {
            return var4;
        } else {
            Vector var5 = new Vector();
            Vector var6 = new Vector();

            for(int var7 = 0; var7 < concept.datatable.size(); ++var7) {
                Row var8 = (Row)concept.datatable.elementAt(var7);
                String var9 = var8.entity;
                if (var8.tuples.isEmpty()) {
                    var6.addElement(var9);
                } else {
                    var5.addElement(var9);
                }
            }

            Vector var14 = this.allTuples(choices_size - 1, var5);

            for(int var15 = 0; var15 < var14.size(); ++var15) {
                Vector var16 = (Vector)this.chooseRandom(var14);

                for(int var10 = 0; var10 < var6.size(); ++var10) {
                    String var11 = (String)var6.elementAt(var10);
                    Vector var12 = (Vector)var16.clone();
                    var12.addElement(var11);
                    Puzzle var13 = new Puzzle(var12, var11, concept, "ooo");
                    if (!var13.hasAlternativeSolution(concepts)) {
                        var13.randomizeOrder();
                        var13.calculateDisguiseMeasure(concepts);
                        var13.interestingness = new Double(var13.disguise + (double)var13.embedded_concept.complexity) / 2.0D;
                        var4.addElement(var13);
                        concept.ooo_puzzles.addElement(var13);
                    }
                }
            }

            return var4;
        }
    }

    public Vector allTuples(int arity, Vector elements) {
        Vector var3 = new Vector();
        var3.addElement(new Vector());

        for(int var4 = 0; var4 < arity; ++var4) {
            int var5 = var3.size();

            for(int var6 = 0; var6 < var5; ++var6) {
                Vector var7 = (Vector)var3.elementAt(0);
                int var8 = -1;
                if (!var7.isEmpty()) {
                    var8 = elements.indexOf(var7.lastElement());
                }

                for(int var9 = var8 + 1; var9 < elements.size(); ++var9) {
                    Vector var10 = (Vector)var7.clone();
                    var10.addElement(elements.elementAt(var9));
                    var3.addElement(var10);
                }

                var3.removeElementAt(0);
            }
        }

        return var3;
    }

    public Object chooseRandom(Vector choices) {
        long var2 = Math.round(Math.random() * (double)(choices.size() - 1));
        int var4 = new Integer(Long.toString(var2));
        Object var5 = choices.elementAt(var4);
        choices.removeElementAt(var4);
        return var5;
    }
}
