package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A super class for the otter theorem prover.
 */
public class Otter extends Prover implements Serializable {
    /**
     * The vector of demodulators for the axioms in the theory.
     */
    public Vector demodulators = new Vector();

    /**
     * The standard constructor.
     */
    public Otter() {
    }

    /**
     * Returns a string for the conjecture statement (in Otter's format).
     */
    public String conjectureStatement(Conjecture conjecture) {
        String var2 = this.executionParameter("time_limit");
        String var3 = this.executionParameter("memory_limit");
        String var4 = conjecture.writeConjecture("otter");
        if (var4.equals("")) {
            return "";
        } else {
            var4 = "-(" + var4 + ").";
            String var5 = "set(auto).\n";
            var5 = var5 + "assign(max_seconds, " + var2 + ").\n";
            var5 = var5 + "assign(max_mem, " + var3 + ").\n";
            var5 = var5 + this.writeOperators();
            var5 = var5 + "formula_list(usable).\n";
            var5 = var5 + this.writeAxioms();

            for(int var6 = 0; var6 < this.axiom_conjectures.size(); ++var6) {
                Conjecture var7 = (Conjecture)this.axiom_conjectures.elementAt(var6);
                var5 = var5 + var7.writeConjecture("otter") + ".\n";
            }

            if (this.use_ground_instances) {
                var5 = var5 + this.ground_instances_as_axioms + "\n";
            }

            var5 = var5 + var4;
            var5 = var5 + "\nend_of_list.\n";
            return var5;
        }
    }

    /**
     * The method for attempting to prove the given conjecture.
     */
    public boolean prove(Conjecture conjecture, Theory theory) {
        conjecture.use_entity_letter = this.use_entity_letter;
        boolean var3 = false;
        String var4 = this.conjectureStatement(conjecture);
        boolean var5 = false;
        if (conjecture instanceof NonExists) {
            NonExists var6 = (NonExists)conjecture;
            if (var6.concept.is_object_of_interest_concept) {
                var5 = true;
            }
        }

        if (var4.trim().equals("") && !var5) {
            conjecture.is_trivially_true = true;
            return true;
        } else if (this.use_mathweb) {
            try {
                String var23 = "prove(\"" + var4 + "\" replyWith: atp_result(state: unit time: time(sys: unit)))";
                Object var26 = this.mathweb_handler.applyMethod(this.mathweb_service_name, var23, (String)null, (Integer)null);
                if (var26 == null) {
                    return false;
                }

                try {
                    Hashtable var25 = (Hashtable)var26;
                    conjecture.proof_status = (String)var25.get("state");
                    if (conjecture.proof_status.equals("proof")) {
                        conjecture.proof_status = "proved";
                    }

                    if (conjecture.proof_status.equals("timeout")) {
                        conjecture.proof_status = "time";
                    }

                    if (conjecture.proof_status.equals("search-exhausted")) {
                        conjecture.proof_status = "sos";
                    }

                    conjecture.proof_time = new Double((String)var25.get("time"));
                } catch (Exception var12) {
                    conjecture.proof_status = var26.toString();
                    System.out.println(var12);
                }
            } catch (Exception var13) {
                System.out.println(var13);
            }

            return false;
        } else {
            if (!this.use_mathweb) {
                PrintWriter var17;
                try {
                    var4 = var4 + "\n";
                    var17 = new PrintWriter(new BufferedWriter(new FileWriter("delconj.in")));
                    var17.write(var4);
                    var17.close();
                } catch (Exception var14) {
                    System.out.println("Having trouble opening file delconj.in");
                }

                try {
                    if (this.operating_system.equals("york_unix_old")) {
                        var17 = new PrintWriter(new BufferedWriter(new FileWriter("otter1.bat")));
                        var17.write("#! /bin/csh\n");
                        var17.write("otter < delconj.in > delconj.out &\n");
                        var17.write("sleep 1\n");
                        var17.write("set C=1\n");
                        var17.write("set MP=`ps | grep otter | grep -v \"1.b\" | nawk '{print $1}'`\n");
                        var17.write("while ($C != \"" + Integer.toString(this.time_limit) + "\" & $MP != \"\")\n");
                        var17.write("set MP=`ps | grep otter | grep -v \"1.b\" | nawk '{print $1}'`\n");
                        var17.write("@ C=$C + 1\n");
                        var17.write("echo $C\n");
                        var17.write("sleep 1\n");
                        var17.write("end\n");
                        var17.write("set MP=`ps | grep otter | grep -v \"1.b\" | nawk '{print $1}'`\n");
                        var17.write("kill $MP\n");
                        var17.write("set MP=`ps | grep otter | nawk '{print $1}'`\n");
                        var17.write("kill $MP\n");
                        var17.close();
                    }

                    if (this.operating_system.equals("jcu_unix")) {
                        String[] var18 = new String[]{"/bin/sh", "-c", "/home/guest/hr/devel/otter1.bat"};
                        Process var7 = Runtime.getRuntime().exec(var18);
                        int var8 = var7.waitFor();
                        var7.destroy();
                    }

                    Process var19;
                    int var20;
                    if (this.operating_system.equals("windows")) {
                        var19 = Runtime.getRuntime().exec("otter1.bat");
                        var20 = var19.waitFor();
                        var19.destroy();
                    }

                    if (this.operating_system.equals("york_unix")) {
                        var19 = Runtime.getRuntime().exec("otter1.bat");
                        var20 = var19.waitFor();
                        var19.destroy();
                    }

                    BufferedReader var21 = new BufferedReader(new FileReader("delconj.out"));
                    String var22 = "";
                    String var24 = "";
                    String var9 = "";
                    boolean var10 = false;

                    while(!var22.equals((Object)null)) {
                        try {
                            if (var22.indexOf("end of proof") > -1) {
                                var10 = false;
                            }

                            if (var10) {
                                conjecture.proof = conjecture.proof + "\n" + var22;
                            }

                            if (var22.indexOf("PROOF") > -1) {
                                var10 = true;
                                var22 = var21.readLine();
                            }

                            if (var22.indexOf("Length of proof is ") > -1) {
                                var3 = true;
                                conjecture.proof_status = "proved";
                                conjecture.proof_length = new Double(var22.substring(19, var22.indexOf(".")));
                                conjecture.proof_level = new Double(var22.substring(var22.lastIndexOf(" "), var22.length() - 1));
                            }

                            if (var22.indexOf("user CPU time") > -1) {
                                conjecture.proof_time = new Double(var22.substring(23, var22.indexOf(" ", 23)));
                            }

                            if (var22.indexOf("wall-clock time") > -1) {
                                conjecture.wc_proof_time = new Double(var22.substring(23, var22.indexOf(" ", 23)));
                                break;
                            }

                            if (var22.indexOf("Search stopped because sos empty") > -1) {
                                var3 = false;
                                conjecture.proof_status = "sos";
                                break;
                            }

                            if (var22.indexOf("ERROR") > -1) {
                                var3 = false;
                                conjecture.proof_status = "syntax error";
                                break;
                            }

                            var22 = var21.readLine();
                        } catch (Exception var15) {
                            ;
                        }
                    }
                } catch (Exception var16) {
                    ;
                }
            }

            conjecture.proof_attempts_information.addElement(this.name + "(" + this.setup_name + "): " + Double.toString(conjecture.proof_time * 1000.0D) + ":" + conjecture.proof_status);
            if (conjecture.proof_status.equals("open")) {
                conjecture.proof_status = "time";
            }

            return var3;
        }
    }

    /**
     * This uses otter to generate the demodulators for the axioms of the theory.
     */
    public void getDemodulators() {
        String var1 = "set(knuth_bendix).\n";
        var1 = var1 + "set(print_lists_at_end).\n";
        var1 = var1 + "list(sos).\n";

        String var3;
        for(int var2 = 0; var2 < this.axiom_strings.size(); ++var2) {
            var3 = (String)this.axiom_strings.elementAt(var2);
            var1 = var1 + var3.substring(var3.indexOf("(") + 1, var3.length() - 2) + ".\n";
        }

        var1 = var1 + "end_of_list.\n";

        try {
            PrintWriter var8 = new PrintWriter(new BufferedWriter(new FileWriter("delconj.in")));
            var8.write(var1);
            var8.close();
        } catch (Exception var6) {
            ;
        }

        if (this.operating_system.equals("windows")) {
            try {
                Process var9 = Runtime.getRuntime().exec("otter1.bat");
                int var10 = var9.waitFor();
                var9.destroy();
            } catch (Exception var5) {
                ;
            }
        }

        try {
            BufferedReader var11 = new BufferedReader(new FileReader("delconj.out"));
            var3 = "";
            boolean var4 = false;

            while(!var3.equals((Object)null)) {
                var3 = var11.readLine();
                if (var4 && var3.equals("end_of_list.")) {
                    var4 = false;
                }

                if (var4) {
                    this.demodulators.addElement(var3.substring(var3.indexOf("]") + 2, var3.length()));
                }

                if (var3.equals("list(demodulators).")) {
                    var4 = true;
                }
            }

            var11.close();
        } catch (Exception var7) {
            ;
        }

    }

    /**
     * This attempts to prove the conjecture using only Knuth-Bendix completion.
     */
    public boolean proveUsingKnuthBendix(Implicate implicate) {
        if (this.demodulators.isEmpty()) {
            this.getDemodulators();
        }

        if (implicate.premise_concept.specification_strings_from_knuth_bendix.isEmpty()) {
            this.getSpecificationStringsFromKnuthBendix(implicate.premise_concept);
        }

        String var2 = implicate.writeConjecture("otter");
        String var3 = var2.substring(var2.indexOf("->") + 4, var2.length() - 3);
        return implicate.premise_concept.specification_strings_from_knuth_bendix.contains(var3);
    }

    /**
     * This uses otter to get the additional specifications (as strings) using otter's paramodulation rules.
     */
    public void getSpecificationStringsFromKnuthBendix(Concept concept) {
        Vector var2 = new Vector();

        try {
            PrintWriter var3 = new PrintWriter(new BufferedWriter(new FileWriter("delconj.in")));
            var3.write("assign(max_seconds, 1).\n");
            var3.write("set(demod_inf).\n");
            var3.write("set(order_eq).\n");
            var3.write("set(para_from).\n");
            var3.write("set(para_into).\n");
            var3.write("list(demodulators).\n");

            for(int var4 = 0; var4 < this.demodulators.size(); ++var4) {
                var3.write((String)this.demodulators.elementAt(var4));
            }

            var3.write("end_of_list.\n");
            var3.write("list(sos).\n");
            var3.write(concept.writeDefinition("otter_demod"));
            var3.write("\nend_of_list.\n");
            var3.close();
            if (this.operating_system.equals("windows")) {
                Process var8 = Runtime.getRuntime().exec("otter1.bat");
                int var5 = var8.waitFor();
                var8.destroy();
            }

            BufferedReader var9 = new BufferedReader(new FileReader("delconj.out"));
            String var10 = "";

            while(!var10.equals((Object)null)) {
                var10 = var9.readLine();
                if (var10.indexOf("** KEPT") == 0) {
                    String var6 = var10.substring(var10.indexOf("]") + 2, var10.length() - 1);
                    var6 = this.removeSpaces(var6);
                    if (!var2.contains(var6)) {
                        var2.addElement(var6);
                    }
                }
            }

            var9.close();
        } catch (Exception var7) {
            ;
        }

        concept.specification_strings_from_knuth_bendix = var2;
    }

    private String removeSpaces(String var1) {
        String var2 = "";

        for(int var3 = 0; var3 < var1.length(); ++var3) {
            String var4 = var1.substring(var3, var3 + 1);
            if (!var4.equals(" ")) {
                var2 = var2 + var4;
            }
        }

        return var2;
    }
}
