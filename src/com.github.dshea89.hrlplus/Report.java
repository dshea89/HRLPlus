package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;

public class Report extends PseudoCodeUser implements Serializable {
    public Report(String var1) {
        try {
            BufferedReader var2 = new BufferedReader(new FileReader(var1));

            for(String var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
                this.pseudo_code_lines.addElement(var3);
            }

            var2.close();
        } catch (Exception var4) {
            ;
        }

    }

    public void runReport() {
        this.pseudo_code_interpreter.runPseudoCode(this.pseudo_code_lines);
    }
}
