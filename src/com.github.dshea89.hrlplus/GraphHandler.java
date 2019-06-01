package com.github.dshea89.hrlplus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.JTable;

public class GraphHandler implements Serializable {
    public HR main_frame = null;
    JTable statistics_output_table = new JTable();

    public GraphHandler() {
    }

    public void plotGraphs(boolean var1) {
        String var2 = "";

        for(int var3 = 0; var3 < this.statistics_output_table.getColumnCount() && var2.equals(""); ++var3) {
            String var4 = this.statistics_output_table.getColumnName(var3);
            if (var4.equals("concept")) {
                var2 = var4;
            }

            if (var4.equals("step")) {
                var2 = var4;
            }

            if (var4.equals("equivalence")) {
                var2 = var4;
            }

            if (var4.equals("nonexists")) {
                var2 = var4;
            }

            if (var4.equals("implicate")) {
                var2 = var4;
            }

            if (var4.equals("near_equiv")) {
                var2 = var4;
            }
        }

        String var12 = "";
        int var13 = 0;

        for(int var5 = 0; var5 < this.statistics_output_table.getColumnCount(); ++var5) {
            String var6 = this.statistics_output_table.getColumnName(var5);

            try {
                PrintWriter var7 = new PrintWriter(new BufferedWriter(new FileWriter(var6)));

                for(int var8 = 0; var8 < this.statistics_output_table.getRowCount(); ++var8) {
                    String var9 = (String)this.statistics_output_table.getValueAt(var8, var5);
                    if (var1) {
                        var9 = (new Double(new Double(var9) + (double)(2 * var13))).toString();
                    }

                    var7.write(Integer.toString(var8) + " " + var9 + "\n");
                }

                var12 = var12 + "\"" + var6 + "\"" + ",";
                var7.close();
            } catch (Exception var11) {
                System.out.println(var11);
            }

            ++var13;
        }

        var12 = var12.substring(0, var12.length() - 1);

        try {
            PrintWriter var14 = new PrintWriter(new BufferedWriter(new FileWriter("delplot.gnu")));
            this.writeGnuFile(var14, var2, "value", var12);
            this.runGnuPlot();
        } catch (Exception var10) {
            ;
        }

    }

    private void runGnuPlot() {
        try {
            Process var1 = Runtime.getRuntime().exec("gnuplot1.bat");
            int var2 = var1.waitFor();
            var1.destroy();
        } catch (Exception var3) {
            ;
        }

    }

    private void writeGnuFile(PrintWriter var1, String var2, String var3, String var4) {
        var1.println("set terminal postscript landscape noenhanced monochrome dashed defaultplex \"Helvetica\" 14");
        var1.println("set output 'delplot.ps'");
        var1.println("set noclip points");
        var1.println("set clip one");
        var1.println("set noclip two");
        var1.println("set bar 1.000000");
        var1.println("set border 31 lt -1 lw 1.000");
        var1.println("set xdata");
        var1.println("set ydata");
        var1.println("set zdata");
        var1.println("set x2data");
        var1.println("set y2data");
        var1.println("set boxwidth");
        var1.println("set dummy x,y");
        var1.println("set format x \"%g\"");
        var1.println("set format y \"%g\"");
        var1.println("set format x2 \"%g\"");
        var1.println("set format y2 \"%g\"");
        var1.println("set format z \"%g\"");
        var1.println("set angles radians");
        var1.println("set nogrid");
        var1.println("set key title \"\"");
        var1.println("set key right top Right noreverse box linetype -2 linewidth 1.000 samplen 4 spacing 1 width 0");
        var1.println("set nolabel");
        var1.println("set noarrow");
        var1.println("set nolinestyle");
        var1.println("set nologscale");
        var1.println("set offsets 0, 0, 0, 0");
        var1.println("set pointsize 1");
        var1.println("set encoding default");
        var1.println("set nopolar");
        var1.println("set noparametric");
        var1.println("set view 60, 30, 1, 1");
        var1.println("set samples 100, 100");
        var1.println("set isosamples 10, 10");
        var1.println("set surface");
        var1.println("set nocontour");
        var1.println("set clabel '%8.3g'");
        var1.println("set mapping cartesian");
        var1.println("set nohidden3d");
        var1.println("set cntrparam order 4");
        var1.println("set cntrparam linear");
        var1.println("set cntrparam levels auto 5");
        var1.println("set cntrparam points 5");
        var1.println("set size ratio 0 1,1");
        var1.println("set origin 0,0");
        var1.println("set data style lines");
        var1.println("set function style lines");
        var1.println("set xzeroaxis lt -2 lw 1.000");
        var1.println("set x2zeroaxis lt -2 lw 1.000");
        var1.println("set yzeroaxis lt -2 lw 1.000");
        var1.println("set y2zeroaxis lt -2 lw 1.000");
        var1.println("set tics in");
        var1.println("set ticslevel 0.5");
        var1.println("set ticscale 1 0.5");
        var1.println("set mxtics default");
        var1.println("set mytics default");
        var1.println("set mx2tics default");
        var1.println("set my2tics default");
        var1.println("set xtics border mirror norotate autofreq ");
        var1.println("set ytics border mirror norotate autofreq ");
        var1.println("set ztics border nomirror norotate autofreq ");
        var1.println("set nox2tics");
        var1.println("set noy2tics");
        var1.println("set title \"" + var2 + " versus " + var3 + "\" 0.000000,0.000000  \"\"");
        var1.println("set timestamp \"\" bottom norotate 0.000000,0.000000  \"\"");
        var1.println("set rrange [ * : * ] noreverse nowriteback  # (currently [0:10] )");
        var1.println("set trange [ * : * ] noreverse nowriteback  # (currently [-5:5] )");
        var1.println("set urange [ * : * ] noreverse nowriteback  # (currently [-5:5] )");
        var1.println("set vrange [ * : * ] noreverse nowriteback  # (currently [-5:5] )");
        var1.println("set xlabel \"" + var2 + "\" 0.000000,0.000000  \"\"");
        var1.println("set x2label \"\" 0.000000,0.000000  \"\"");
        var1.println("set xrange [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        var1.println("set x2range [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        var1.println("set ylabel \"" + var3 + "\" 0.000000,0.000000  \"\"");
        var1.println("set y2label \"\" 0.000000,0.000000  \"\"");
        var1.println("set yrange [ 0 : * ] # currently [-10:10] ) ");
        var1.println("set y2range [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        var1.println("set zlabel \"\" 0.000000,0.000000  \"\"");
        var1.println("set zrange [ * : * ] noreverse nowriteback  # (currently [-10:10] )");
        var1.println("set zero 1e-008");
        var1.println("set lmargin -1");
        var1.println("set bmargin -1");
        var1.println("set rmargin -1");
        var1.println("set tmargin -1");
        var1.println("set locale \"C\"");
        var1.println("plot " + var4);
        var1.close();
    }

    public void drawConstructionHistory(Conjecture var1, Theory var2, String var3) {
        PrintWriter var4 = this.openFile("delgraph.dot");
        if (var4 != null) {
            Vector var6;
            Vector var7;
            if (var1 instanceof Equivalence) {
                Equivalence var5 = (Equivalence)var1;
                var6 = (Vector)var5.lh_concept.ancestors.clone();
                var7 = (Vector)var5.rh_concept.ancestors.clone();
                var6.addElement(var5.lh_concept);
                var7.addElement(var5.rh_concept);
                this.addAncestors(var6, var4, var3);
                this.addAncestors(var7, var4, var3);
                var4.write("\"" + var5.lh_concept.id + "\" -> \"" + var5.rh_concept.id + "\" [ label = \"equivalence\" style = \"dotted\" dir = \"both\"];");
            }

            if (var1 instanceof Implication) {
                Implication var8 = (Implication)var1;
                var6 = (Vector)var8.lh_concept.ancestors.clone();
                var7 = (Vector)var8.rh_concept.ancestors.clone();
                var6.addElement(var8.lh_concept);
                var7.addElement(var8.rh_concept);
                this.addAncestors(var6, var4, var3);
                this.addAncestors(var7, var4, var3);
                var4.write("\"" + var8.lh_concept.id + "\" -> \"" + var8.rh_concept.id + "\" [ label = \"implication\" style = \"dotted\" ];");
            }

            if (var1 instanceof NonExists) {
                NonExists var9 = (NonExists)var1;
                var6 = (Vector)var9.concept.ancestors.clone();
                var6.addElement(var9.concept);
                this.addAncestors(var6, var4, var3);
            }

            var4.write("\n}");
            var4.close();
            this.showGraph();
        }

    }

    public void drawConstructionHistory(Concept var1, Theory var2, String var3) {
        PrintWriter var4 = this.openFile("delgraph.dot");
        if (var4 != null) {
            Vector var5 = var1.getAncestors(var2);
            this.addAncestors(var5, var4, var3);
            var4.write("\n}");
            var4.close();
            this.showGraph();
        }

    }

    private void showGraph() {
        try {
            Process var1 = Runtime.getRuntime().exec("drawgraph.bat");
            int var2 = var1.waitFor();
            var1.destroy();
            Process var3 = Runtime.getRuntime().exec("showgraph.bat");
            int var4 = var3.exitValue();
            var3.destroy();
        } catch (Exception var5) {
            ;
        }

    }

    private PrintWriter openFile(String var1) {
        try {
            PrintWriter var2 = new PrintWriter(new BufferedWriter(new FileWriter("delgraph.dot")));
            var2.write("digraph mygraph {\nsize=\"7.5,10.5\";");
            var2.write("\norientation=portrait;\nrankdir = TB;\nnode [shape=box];\nconcentrate=true;\nratio=fill;\n");
            return var2;
        } catch (Exception var3) {
            return null;
        }
    }

    private void addAncestors(Vector var1, PrintWriter var2, String var3) {
        int var4;
        Concept var5;
        for(var4 = 0; var4 < var1.size(); ++var4) {
            var5 = (Concept)var1.elementAt(var4);
            if (!var5.writeDefinition(var3).trim().equals("")) {
                var2.write("\"" + var5.id + "\" [ label = \"[" + var5.id + "] " + var5.writeDefinitionWithStartLetters(var3) + "\"];\n");
            } else {
                var2.write("\"" + var5.id + "\" [ label = \"[" + var5.id + "] " + var5.writeDefinitionWithStartLetters(var3) + "\"];\n");
            }
        }

        for(var4 = 0; var4 < var1.size(); ++var4) {
            var5 = (Concept)var1.elementAt(var4);

            for(int var6 = 0; var6 < var5.parents.size(); ++var6) {
                Concept var7 = (Concept)var5.parents.elementAt(var6);
                var2.write("\"" + var7.id + "\" -> \"" + var5.id + "\"");
                var2.write(" [ label = \"" + var5.construction.asString() + "\"];\n");
            }
        }

    }
}
