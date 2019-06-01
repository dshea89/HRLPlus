package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class MathWebProver extends Prover implements Serializable {
    public Vector prover_names = new Vector();

    public boolean prove(Conjecture var1) {
        String var2 = this.writeAxioms();
        String var3 = var1.writeConjecture("tptp");
        if (var3.equals("")) {
            var1.proof_status = "trivial";
            return true;
        } else {
            var2 = var2 + var3;
            int var4 = new Integer(var1.id);
            String var5 = "[";

            for(int var6 = 0; var6 < this.prover_names.size() - 1; ++var6) {
                var5 = var5 + (String)this.prover_names.elementAt(var6) + " ";
            }

            if (!this.prover_names.isEmpty()) {
                var5 = var5 + (String)this.prover_names.lastElement();
            }

            var5 = var5 + "]";
            byte var7 = 120;
            String var8 = "tptp";
            String var9 = "all-on-one";
            String var21 = "proveProblem(prob(id: " + var4 + " problem: \"" + var2 + "\"#" + var8 + " provers: " + var5 + " mode: '" + var9 + "' " + " timeout: " + var7 + "))";
            Object var10 = this.mathweb_handler.applyMethod("ATP", var21, (String)null, new Integer(3 * var7 + 20));
            var1.proof_status = var10.toString();
            boolean var11 = false;
            if (var10 instanceof Vector) {
                Vector var12 = (Vector)var10;
                Enumeration var14 = var12.elements();

                while(var14.hasMoreElements()) {
                    Hashtable var13 = (Hashtable)var14.nextElement();
                    String var15 = (String)var13.get("state");
                    if (var15 != null && var15.equals("proof")) {
                        var11 = true;
                        var1.proof_status = "proved";
                    }

                    String var16 = (String)var13.get("time");
                    Double var17 = new Double(var16);
                    double var18 = var17;
                    if (var18 > var1.mw_max_proof_time) {
                        var1.mw_max_proof_time = var18;
                    }

                    if (var18 < var1.mw_min_proof_time || var1.mw_min_proof_time == -1.0D) {
                        var1.mw_min_proof_time = var18;
                    }

                    var1.mw_av_proof_time += var18;
                    String var20 = (String)var13.get("name");
                    if (var16 != null && var20 != null && var15 != null) {
                        var1.proof_attempts_information.addElement(var20 + ":" + var15 + ":" + var16);
                    }
                }

                if (var12.size() > 0) {
                    var1.mw_av_proof_time /= (double)var12.size();
                    var1.mw_diff_proof_time = var1.mw_max_proof_time - var1.mw_min_proof_time;
                }
            }

            return var11;
        }
    }

    public MathWebProver() {
        this.name = "mathweb";
    }

    public String conjectureStatement(Conjecture var1) {
        String var2 = "";
        String var3 = var1.writeConjecture("tptp");
        return var3.equals("") ? "" : var2;
    }
}
