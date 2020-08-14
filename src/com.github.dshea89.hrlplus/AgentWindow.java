package com.github.dshea89.hrlplus;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/** In this class a front end for each agent is defined, which
 * tells us each time an agent reads or writes anything to a file. */

public class AgentWindow extends JFrame
{

    /** The output stream. */

    public PrintWriter file_out;

    /** the port of the student or teacher who owns the window*/
    int port;

    /** whether we want verbose or not */
    boolean verbose = false;

    JTextArea output_text = new JTextArea();



    /** Constructor for the Agent Window */

    public AgentWindow ()
    {
        JScrollPane pane = new JScrollPane(output_text);
        output_text.setLineWrap(true);
        output_text.setWrapStyleWord(true);
        Container container = getContentPane();
        container.add(pane);
        setSize(800, 400);
        setVisible(false);//dec 2006 -- changed to false
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void writeQuietlyToFrontEnd(Vector vector)
    {
        if(verbose)
            writeToFrontEnd(vector);
    }

    public void writeToFrontEnd(Vector vector)
    {
        if(vector.isEmpty())
            writeToFrontEnd("[]");

        if(vector.size()==1)
        {
            if(vector.elementAt(0) instanceof Conjecture)
            {
                String conj_to_write = ((Conjecture)vector.elementAt(0)).writeConjecture();
                writeToFrontEnd("[" +  conj_to_write+ "]");
            }
            else
                writeToFrontEnd("[" + (Object)vector.elementAt(0) + "]");
        }
        if(vector.size()>1)
        {
            Object first_object = (Object)vector.elementAt(0);
            writeToFrontEnd("[" + first_object);
            for(int i=1; i<vector.size()-1;i++)
            {
                Object object = (Object)vector.elementAt(i);
                writeToFrontEnd(object);
            }
            Object last_object = (Object)vector.lastElement();
            writeToFrontEnd(last_object + "]");
        }
    }


    public void writeQuietlyToFrontEnd(Conjecture conjecture)
    {
        if(verbose)
            writeToFrontEnd(conjecture);
    }

    //doesn't come here
    public void writeToFrontEnd(Conjecture conjecture)
    {
        String conj_string = conjecture.id + ": " + conjecture.writeConjecture("ascii");
        writeToFrontEnd(conj_string);
    }

    public void writeQuietlyToFrontEnd(Concept concept)
    {
        if(verbose)
            writeToFrontEnd(concept);
    }

    public void writeToFrontEnd(Concept concept)
    {
        String concept_string = concept.id + ": " + concept.writeDefinition("ascii");
        writeToFrontEnd(concept_string);
    }

    //public void writeToFrontEnd(Motivation motivation)
    //{
    //	String motivation_string = motivation.writeMotivation();
    //	writeToFrontEnd(motivation_string);
    //}

    public void writeQuietlyToFrontEnd(Object object)
    {
        if(verbose)
            writeToFrontEnd(object.toString());
    }


    public void writeToFrontEnd(Object object)
    {
        if(object instanceof Conjecture)
            writeToFrontEnd((Conjecture)object);
        else
        if(object instanceof Concept)
            writeToFrontEnd((Concept)object);
        else
        if(object instanceof Entity)
            writeToFrontEnd((Entity)object);
        else
            writeToFrontEnd(object.toString());

    }

    public void writeQuietlyToFrontEnd(String output)
    {
        if(verbose)
            writeToFrontEnd(output);
    }


    public void writeToFrontEnd(String output)
    {
        output_text.append(output+"\n");
        writeToFile(output+"\n");
    }

    public void writeQuietlyToFrontEnd(GroupAgendaElement gae)
    {
        if(verbose)
            writeToFrontEnd(gae);
    }

    public void writeToFrontEnd(GroupAgendaElement gae)
    {
        if(gae instanceof GroupAgendaTheoryConstituentElement)
        {
            GroupAgendaTheoryConstituentElement gatce = (GroupAgendaTheoryConstituentElement)gae;
            writeToFrontEnd(gae.toString());
        }

        if(gae instanceof GroupAgendaVectorElement)
        {
            GroupAgendaVectorElement gave = (GroupAgendaVectorElement)gae;
            Vector gav = gave.vector;
            writeToFrontEnd(gav);
        }

        if(gae instanceof Request)
        {
            Request request = (Request)gae;
            writeToFrontEnd(request + " is a request");
            String request_string = request.toString();
            writeToFrontEnd("done " + request + ".toString()");
            writeToFrontEnd(request_string);
        }
    }

    public void writeQuietlyToFrontEnd(GroupAgendaTheoryConstituentElement gatce)
    {
        if(verbose)
            writeToFrontEnd(gatce);
    }

    public void writeToFrontEnd(GroupAgendaTheoryConstituentElement gatce)
    {
        TheoryConstituent tc = gatce.theory_constituent;
        writeToFrontEnd(tc, "ascii");
        Motivation motivation = gatce.motivation;
        String motivation_string = motivation.toString();
        writeQuietlyToFrontEnd(motivation_string);
    }


    public void writeQuietlyToFrontEnd(TheoryConstituent tc)
    {
        if(verbose)
            writeToFrontEnd(tc);
    }

    public void writeToFrontEnd(TheoryConstituent tc)
    {
        writeToFrontEnd(tc, "ascii");
    }

    public void writeQuietlyToFrontEnd(TheoryConstituent tc, String language)
    {
        if(verbose)
            writeToFrontEnd(tc, language);
    }

    public void writeToFrontEnd(TheoryConstituent tc, String language)
    {
        String output_string = "";
        if (tc instanceof Conjecture)
        {
            Conjecture conj = (Conjecture)tc;
            output_string = conj.id + ": " + conj.writeConjecture(language);
            writeToFrontEnd(conj + "is a conjecture");
            writeToFrontEnd("as a string, it looks like " + output_string);
        }

        if (tc instanceof Concept)
            output_string = ((Concept)tc).writeDefinition();

        output_text.append(output_string + "\n");
        writeToFile(output_string + "\n");
    }

    public void writeToFrontEnd(ProofScheme ps)
    {

        if(ps.isEmpty())
            writeToFrontEnd("The proof scheme is empty");

        else
        {

            writeToFrontEnd("The global conjecture is " + ps.conj.writeConjecture());

            if(ps.conj.counterexamples.isEmpty())
            {
                writeToFrontEnd("There are no global counterexamples");
            }
            else
            {
                writeToFrontEnd("This has global counterexamples:");
                for(int i=0;i<ps.conj.counterexamples.size();i++)
                {
                    Entity entity = (Entity)ps.conj.counterexamples.elementAt(i);
                    writeToFrontEnd(entity.toString());
                }
            }

            writeToFrontEnd("The local lemmas are:");
            for(int i=0;i<ps.proof_vector.size();i++)
            {
                Conjecture current_conj = (Conjecture)ps.proof_vector.elementAt(i);
                writeToFrontEnd("("+i+") " +current_conj.writeConjecture());
            }

            for(int i=0;i<ps.proof_vector.size();i++)
            {
                Conjecture current_conj = (Conjecture)ps.proof_vector.elementAt(i);
                if(!(current_conj.counterexamples.isEmpty()))
                {
                    writeToFrontEnd("lemma "+i+" has counterexamples:");
                    for(int j=0;j<current_conj.counterexamples.size();j++)
                    {
                        Entity entity = (Entity)current_conj.counterexamples.elementAt(j);
                        writeToFrontEnd(entity.toString());
                    }
                }
            }
        }
    }


    public void writeQuietlyToFrontEnd(boolean b)
    {
        if(verbose)
            writeToFrontEnd(b);
    }


    public void writeToFrontEnd(boolean b)
    {
        if(b)
            writeToFrontEnd("true");
        else writeToFrontEnd("false");
    }

    public void drawQuietLine()
    {
        if(verbose)
            drawLine();
    }


    public void drawLine()
    {
        writeToFrontEnd("\n ---------------- \n");
    }

    public void drawQuietStars()
    {
        if(verbose)
            drawStars();
    }

    public void drawStars()
    {
        writeToFrontEnd("      *****       ");
    }

    public void newQuietLine()
    {
        if(verbose)
            newLine();
    }

    public void newLine()
    {
        writeToFrontEnd("\n");
    }

    public void writeToFile(String string)
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter((new Integer(this.port)).toString(), true)));
            out.println(string);
            out.close();
        }
        catch(Exception e)
        {
            System.out.println(port + ": Exception is " + e);
        }
    }
}
