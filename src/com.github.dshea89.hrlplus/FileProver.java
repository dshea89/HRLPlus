package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class FileProver extends Prover implements Serializable {
    public Vector operation_substitutions = new Vector();
    public Vector stored_results = new Vector();

    public FileProver(String var1, StorageHandler var2) {
        this.stored_results = var2.retrieveAllSlim(var1);
        if (this.stored_results != null) {
            for(int var3 = 0; var3 < this.stored_results.size(); ++var3) {
                Conjecture var4 = (Conjecture)this.stored_results.elementAt(var3);
                System.out.println(var4.writeConjecture("otter") + " " + var4.proof_status);
            }
        } else {
            this.stored_results = new Vector();
        }

    }

    public boolean prove(Conjecture var1, Theory var2) {
        for(int var3 = 0; var3 < this.stored_results.size(); ++var3) {
            Conjecture var4 = (Conjecture)this.stored_results.elementAt(var3);
            String var5 = var4.writeConjecture("otter").trim();
            String var6 = var1.writeConjecture("otter").trim();
            if (var5.equals(var6)) {
                if (var4.proof_status.equals("proved")) {
                    var2.front_end.systemout_text.append(var4.proof_status + "\n");
                    var1.proof_status = "proved";
                    var1.proof = var4.proof;
                    return true;
                }

                return false;
            }
        }

        return false;
    }
}
