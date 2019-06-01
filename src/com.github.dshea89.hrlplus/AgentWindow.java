package com.github.dshea89.hrlplus;

import java.awt.Container;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AgentWindow extends JFrame {
    public PrintWriter file_out;
    int port;
    boolean verbose = false;
    JTextArea output_text = new JTextArea();

    public AgentWindow() {
        JScrollPane var1 = new JScrollPane(this.output_text);
        this.output_text.setLineWrap(true);
        this.output_text.setWrapStyleWord(true);
        Container var2 = this.getContentPane();
        var2.add(var1);
        this.setSize(800, 400);
        this.setVisible(false);
        this.setDefaultCloseOperation(3);
    }

    public void writeQuietlyToFrontEnd(Vector var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(Vector var1) {
        if (var1.isEmpty()) {
            this.writeToFrontEnd("[]");
        }

        if (var1.size() == 1) {
            if (var1.elementAt(0) instanceof Conjecture) {
                String var2 = ((Conjecture)var1.elementAt(0)).writeConjecture();
                this.writeToFrontEnd("[" + var2 + "]");
            } else {
                this.writeToFrontEnd("[" + var1.elementAt(0) + "]");
            }
        }

        if (var1.size() > 1) {
            Object var5 = var1.elementAt(0);
            this.writeToFrontEnd("[" + var5);

            for(int var3 = 1; var3 < var1.size() - 1; ++var3) {
                Object var4 = var1.elementAt(var3);
                this.writeToFrontEnd(var4);
            }

            Object var6 = var1.lastElement();
            this.writeToFrontEnd(var6 + "]");
        }

    }

    public void writeQuietlyToFrontEnd(Conjecture var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(Conjecture var1) {
        String var2 = var1.id + ": " + var1.writeConjecture("ascii");
        this.writeToFrontEnd(var2);
    }

    public void writeQuietlyToFrontEnd(Concept var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(Concept var1) {
        String var2 = var1.id + ": " + var1.writeDefinition("ascii");
        this.writeToFrontEnd(var2);
    }

    public void writeQuietlyToFrontEnd(Object var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1.toString());
        }

    }

    public void writeToFrontEnd(Object var1) {
        if (var1 instanceof Conjecture) {
            this.writeToFrontEnd((Conjecture)var1);
        } else if (var1 instanceof Concept) {
            this.writeToFrontEnd((Concept)var1);
        } else if (var1 instanceof Entity) {
            this.writeToFrontEnd((TheoryConstituent)((Entity)var1));
        } else {
            this.writeToFrontEnd(var1.toString());
        }

    }

    public void writeQuietlyToFrontEnd(String var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(String var1) {
        System.out.println(var1);
        this.output_text.append(var1 + "\n");
        this.writeToFile(var1 + "\n");
    }

    public void writeQuietlyToFrontEnd(GroupAgendaElement var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(GroupAgendaElement var1) {
        if (var1 instanceof GroupAgendaTheoryConstituentElement) {
            GroupAgendaTheoryConstituentElement var2 = (GroupAgendaTheoryConstituentElement)var1;
            this.writeToFrontEnd(var1.toString());
        }

        if (var1 instanceof GroupAgendaVectorElement) {
            GroupAgendaVectorElement var4 = (GroupAgendaVectorElement)var1;
            Vector var3 = var4.vector;
            this.writeToFrontEnd(var3);
        }

        if (var1 instanceof Request) {
            Request var5 = (Request)var1;
            this.writeToFrontEnd(var5 + " is a request");
            String var6 = var5.toString();
            this.writeToFrontEnd("done " + var5 + ".toString()");
            this.writeToFrontEnd(var6);
        }

    }

    public void writeQuietlyToFrontEnd(GroupAgendaTheoryConstituentElement var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(GroupAgendaTheoryConstituentElement var1) {
        TheoryConstituent var2 = var1.theory_constituent;
        this.writeToFrontEnd(var2, "ascii");
        Motivation var3 = var1.motivation;
        String var4 = var3.toString();
        this.writeQuietlyToFrontEnd(var4);
    }

    public void writeQuietlyToFrontEnd(TheoryConstituent var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(TheoryConstituent var1) {
        this.writeToFrontEnd(var1, "ascii");
    }

    public void writeQuietlyToFrontEnd(TheoryConstituent var1, String var2) {
        if (this.verbose) {
            this.writeToFrontEnd(var1, var2);
        }

    }

    public void writeToFrontEnd(TheoryConstituent var1, String var2) {
        String var3 = "";
        if (var1 instanceof Conjecture) {
            Conjecture var4 = (Conjecture)var1;
            var3 = var4.id + ": " + var4.writeConjecture(var2);
            this.writeToFrontEnd(var4 + "is a conjecture");
            this.writeToFrontEnd("as a string, it looks like " + var3);
        }

        if (var1 instanceof Concept) {
            var3 = ((Concept)var1).writeDefinition();
        }

        this.output_text.append(var3 + "\n");
        this.writeToFile(var3 + "\n");
    }

    public void writeToFrontEnd(ProofScheme var1) {
        if (var1.isEmpty()) {
            this.writeToFrontEnd("The proof scheme is empty");
        } else {
            this.writeToFrontEnd("The global conjecture is " + var1.conj.writeConjecture());
            int var2;
            if (var1.conj.counterexamples.isEmpty()) {
                this.writeToFrontEnd("There are no global counterexamples");
            } else {
                this.writeToFrontEnd("This has global counterexamples:");

                for(var2 = 0; var2 < var1.conj.counterexamples.size(); ++var2) {
                    Entity var3 = (Entity)var1.conj.counterexamples.elementAt(var2);
                    this.writeToFrontEnd(var3.toString());
                }
            }

            this.writeToFrontEnd("The local lemmas are:");

            Conjecture var6;
            for(var2 = 0; var2 < var1.proof_vector.size(); ++var2) {
                var6 = (Conjecture)var1.proof_vector.elementAt(var2);
                this.writeToFrontEnd("(" + var2 + ") " + var6.writeConjecture());
            }

            for(var2 = 0; var2 < var1.proof_vector.size(); ++var2) {
                var6 = (Conjecture)var1.proof_vector.elementAt(var2);
                if (!var6.counterexamples.isEmpty()) {
                    this.writeToFrontEnd("lemma " + var2 + " has counterexamples:");

                    for(int var4 = 0; var4 < var6.counterexamples.size(); ++var4) {
                        Entity var5 = (Entity)var6.counterexamples.elementAt(var4);
                        this.writeToFrontEnd(var5.toString());
                    }
                }
            }
        }

    }

    public void writeQuietlyToFrontEnd(boolean var1) {
        if (this.verbose) {
            this.writeToFrontEnd(var1);
        }

    }

    public void writeToFrontEnd(boolean var1) {
        if (var1) {
            this.writeToFrontEnd("true");
        } else {
            this.writeToFrontEnd("false");
        }

    }

    public void drawQuietLine() {
        if (this.verbose) {
            this.drawLine();
        }

    }

    public void drawLine() {
        this.writeToFrontEnd("\n ---------------- \n");
    }

    public void drawQuietStars() {
        if (this.verbose) {
            this.drawStars();
        }

    }

    public void drawStars() {
        this.writeToFrontEnd("      *****       ");
    }

    public void newQuietLine() {
        if (this.verbose) {
            this.newLine();
        }

    }

    public void newLine() {
        this.writeToFrontEnd("\n");
    }

    public void writeToFile(String var1) {
        try {
            PrintWriter var2 = new PrintWriter(new BufferedWriter(new FileWriter((new Integer(this.port)).toString(), true)));
            var2.println(var1);
            var2.close();
        } catch (Exception var3) {
            System.out.println(this.port + ": Exception is " + var3);
        }

    }
}
