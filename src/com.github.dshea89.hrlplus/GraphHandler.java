package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.util.Enumeration;
import java.io.*;
import java.lang.Runtime.*;
import java.awt.*;
import javax.swing.*;

/** A class for drawing various graphs.
 *
 * @author Simon Colton, started 10th January 2002.
 * @version 1.0 */

public class GraphHandler implements Serializable
{
    public HR main_frame = null;
    JTable statistics_output_table = new JTable();

    public void plotGraphs(boolean seperate_graphs)
    {
        String x_axis = "";
        for (int i=0; i<statistics_output_table.getColumnCount() && x_axis.equals(""); i++)
        {
            String column_name = statistics_output_table.getColumnName(i);
            if (column_name.equals("concept")) x_axis = column_name;
            if (column_name.equals("step")) x_axis = column_name;
            if (column_name.equals("equivalence")) x_axis = column_name;
            if (column_name.equals("nonexists")) x_axis = column_name;
            if (column_name.equals("implicate")) x_axis = column_name;
            if (column_name.equals("near_equiv")) x_axis = column_name;
        }
        String file_names = "";
        int counter=0;
        for (int i=0; i<statistics_output_table.getColumnCount(); i++)
        {
            String y_axis = statistics_output_table.getColumnName(i);
            try
            {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(y_axis)));
                for (int j=0; j<statistics_output_table.getRowCount(); j++)
                {
                    String value = (String)statistics_output_table.getValueAt(j, i);
                    if (seperate_graphs)
                        value = (new Double((new Double(value)).doubleValue() + (2*counter))).toString();
                    out.write(Integer.toString(j) + " " + value + "\n");
                }
                file_names = file_names + "\"" + y_axis + "\"" + ",";
                out.close();
            }
            catch(Exception ex){System.out.println(ex);}
            counter++;
        }
        file_names = file_names.substring(0, file_names.length()-1);
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delplot.gnu")));
            writeGnuFile(out, x_axis, "value", file_names);
            runGnuPlot();
        }
        catch(Exception e){}
    }

    private void runGnuPlot()
    {
        try
        {
            Process plot_process = Runtime.getRuntime().exec("gnuplot1.bat");
            int exit_value = plot_process.waitFor();
            plot_process.destroy();
        }
        catch(Exception e){}
    }

    private void writeGnuFile(PrintWriter out, String x_axis, String y_axis, String files_string)
    {
        out.println("set terminal postscript landscape noenhanced monochrome dashed defaultplex \"Helvetica\" 14");
        out.println("set output 'delplot.ps'");
        out.println("set noclip points");
        out.println("set clip one");
        out.println("set noclip two");
        out.println("set bar 1.000000");
        out.println("set border 31 lt -1 lw 1.000");
        out.println("set xdata");
        out.println("set ydata");
        out.println("set zdata");
        out.println("set x2data");
        out.println("set y2data");
        out.println("set boxwidth");
        out.println("set dummy x,y");
        out.println("set format x \"%g\"");
        out.println("set format y \"%g\"");
        out.println("set format x2 \"%g\"");
        out.println("set format y2 \"%g\"");
        out.println("set format z \"%g\"");
        out.println("set angles radians");
        out.println("set nogrid");
        out.println("set key title \"\"");
        out.println("set key right top Right noreverse box linetype -2 linewidth 1.000 samplen 4 spacing 1 width 0");
        out.println("set nolabel");
        out.println("set noarrow");
        out.println("set nolinestyle");
        out.println("set nologscale");
        out.println("set offsets 0, 0, 0, 0");
        out.println("set pointsize 1");
        out.println("set encoding default");
        out.println("set nopolar");
        out.println("set noparametric");
        out.println("set view 60, 30, 1, 1");
        out.println("set samples 100, 100");
        out.println("set isosamples 10, 10");
        out.println("set surface");
        out.println("set nocontour");
        out.println("set clabel '%8.3g'");
        out.println("set mapping cartesian");
        out.println("set nohidden3d");
        out.println("set cntrparam order 4");
        out.println("set cntrparam linear");
        out.println("set cntrparam levels auto 5");
        out.println("set cntrparam points 5");
        out.println("set size ratio 0 1,1");
        out.println("set origin 0,0");
        out.println("set data style lines");
        out.println("set function style lines");
        out.println("set xzeroaxis lt -2 lw 1.000");
        out.println("set x2zeroaxis lt -2 lw 1.000");
        out.println("set yzeroaxis lt -2 lw 1.000");
        out.println("set y2zeroaxis lt -2 lw 1.000");
        out.println("set tics in");
        out.println("set ticslevel 0.5");
        out.println("set ticscale 1 0.5");
        out.println("set mxtics default");
        out.println("set mytics default");
        out.println("set mx2tics default");
        out.println("set my2tics default");
        out.println("set xtics border mirror norotate autofreq ");
        out.println("set ytics border mirror norotate autofreq ");
        out.println("set ztics border nomirror norotate autofreq ");
        out.println("set nox2tics");
        out.println("set noy2tics");
        out.println("set title \"" + x_axis + " versus " + y_axis + "\" 0.000000,0.000000  \"\"");
        out.println("set timestamp \"\" bottom norotate 0.000000,0.000000  \"\"");
        out.println("set rrange [ * : * ] noreverse nowriteback  # (currently [0:10] )");
        out.println("set trange [ * : * ] noreverse nowriteback  # (currently [-5:5] )");
        out.println("set urange [ * : * ] noreverse nowriteback  # (currently [-5:5] )");
        out.println("set vrange [ * : * ] noreverse nowriteback  # (currently [-5:5] )");
        out.println("set xlabel \"" + x_axis + "\" 0.000000,0.000000  \"\"");
        out.println("set x2label \"\" 0.000000,0.000000  \"\"");
        out.println("set xrange [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        out.println("set x2range [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        out.println("set ylabel \"" + y_axis + "\" 0.000000,0.000000  \"\"");
        out.println("set y2label \"\" 0.000000,0.000000  \"\"");
        out.println("set yrange [ 0 : * ] # currently [-10:10] ) ");
        out.println("set y2range [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        out.println("set zlabel \"\" 0.000000,0.000000  \"\"");
        out.println("set zrange [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        out.println("set zero 1e-008");
        out.println("set lmargin -1");
        out.println("set bmargin -1");
        out.println("set rmargin -1");
        out.println("set tmargin -1");
        out.println("set locale \"C\"");
        out.println("plot " + files_string);
        out.close();
    }

    /** This draws the construction history of a concept using the dot program.
     */

    public void drawConstructionHistory(Conjecture conjecture, Theory theory, String language)
    {
        PrintWriter out = openFile("delgraph.dot");
        if (out!=null)
        {
            if (conjecture instanceof Equivalence)
            {
                Equivalence equiv = (Equivalence)conjecture;
                Vector lh_ancestors = (Vector)equiv.lh_concept.ancestors.clone();
                Vector rh_ancestors = (Vector)equiv.rh_concept.ancestors.clone();
                lh_ancestors.addElement(equiv.lh_concept);
                rh_ancestors.addElement(equiv.rh_concept);
                addAncestors(lh_ancestors, out, language);
                addAncestors(rh_ancestors, out, language);
                out.write("\"" + equiv.lh_concept.id + "\" -> \"" + equiv.rh_concept.id + "\" [ label = \"equivalence\" style = \"dotted\" dir = \"both\"];");
            }
            if (conjecture instanceof Implication)
            {
                Implication imp = (Implication)conjecture;
                Vector lh_ancestors = (Vector)imp.lh_concept.ancestors.clone();
                Vector rh_ancestors = (Vector)imp.rh_concept.ancestors.clone();
                lh_ancestors.addElement(imp.lh_concept);
                rh_ancestors.addElement(imp.rh_concept);
                addAncestors(lh_ancestors, out, language);
                addAncestors(rh_ancestors, out, language);
                out.write("\"" + imp.lh_concept.id + "\" -> \"" + imp.rh_concept.id + "\" [ label = \"implication\" style = \"dotted\" ];");
            }
            if (conjecture instanceof NonExists)
            {
                NonExists ne = (NonExists)conjecture;
                Vector ancestors = (Vector)ne.concept.ancestors.clone();
                ancestors.addElement(ne.concept);
                addAncestors(ancestors, out, language);
            }
            out.write("\n}");
            out.close();
            showGraph();
        }
    }

    public void drawConstructionHistory(Concept concept, Theory theory, String language)
    {
        PrintWriter out = openFile("delgraph.dot");
        if (out!=null)
        {
            Vector ancestors = concept.getAncestors(theory);
            addAncestors(ancestors, out, language);
            out.write("\n}");
            out.close();
            showGraph();
        }
    }

    private void showGraph()
    {
        try
        {
            Process draw_process = Runtime.getRuntime().exec("drawgraph.bat");
            int exit_value = draw_process.waitFor();
            draw_process.destroy();
            Process show_process = Runtime.getRuntime().exec("showgraph.bat");
            int next_exit_value = show_process.exitValue();
            show_process.destroy();
        }
        catch(Exception e)
        {
        }
    }

    private PrintWriter openFile(String filename)
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delgraph.dot")));
            out.write("digraph mygraph {\nsize=\"7.5,10.5\";");
            out.write("\norientation=portrait;\nrankdir = TB;\nnode [shape=box];\nconcentrate=true;\nratio=fill;\n");
            return out;
        }
        catch(Exception e)
        {
        }
        return null;
    }

    private void addAncestors(Vector ancestors, PrintWriter out, String language)
    {
        for (int i=0; i<ancestors.size(); i++)
        {
            Concept ancestor = (Concept)ancestors.elementAt(i);
            if (!ancestor.writeDefinition(language).trim().equals(""))
                out.write("\"" + ancestor.id + "\" [ label = \"[" + ancestor.id + "] "
                        + ancestor.writeDefinitionWithStartLetters(language)+"\"];\n");
            else
                out.write("\"" + ancestor.id + "\" [ label = \"[" + ancestor.id + "] "
                        + ancestor.writeDefinitionWithStartLetters(language)+"\"];\n");

        }
        for (int i=0; i<ancestors.size(); i++)
        {
            Concept ancestor = (Concept)ancestors.elementAt(i);
            for (int j=0; j<ancestor.parents.size(); j++)
            {
                Concept parent = (Concept)ancestor.parents.elementAt(j);
                out.write("\"" + parent.id + "\" -> \"" + ancestor.id + "\"");
                out.write(" [ label = \"" + ancestor.construction.asString() + "\"];\n");
            }
        }
    }

}
