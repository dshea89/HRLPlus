package com.github.dshea89.hrlplus;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SOTHandler extends Prover implements Serializable {
    public PrintWriter pw = null;

    public SOTHandler() {
    }

    public String submit(Conjecture var1, TextArea var2) {
        String var3 = this.writeAxioms();
        String var4 = var1.writeConjecture("tptp");
        if (var4.equals("")) {
            var1.proof_status = "trivial";
            return "Conjecture is trivial";
        } else {
            var3 = var3 + var4;
            String var5 = "";

            try {
                URL var6 = new URL("http://www.tptp.org/cgi-bin/SystemOnTPTPFormReply");
                URLConnection var7 = var6.openConnection();
                var7.setDoOutput(true);
                this.pw = new PrintWriter(var7.getOutputStream());
                var5 = var5 + this.sendLineToSOT("NoHTML", "1");
                var5 = var5 + this.sendLineToSOT("QuietFlag", "-q0");
                var5 = var5 + this.sendLineToSOT("SubmitButton", "RunParallel");
                var5 = var5 + this.sendLineToSOT("ProblemSource", "UPLOAD");
                var5 = var5 + this.sendLineToSOT("AutoModeTimeLimit", "300");
                var5 = var5 + this.sendLineToSOT("AutoMode", "-cE");
                var5 = var5 + this.sendLineToSOT("AutoModeSystemsLimit", "3");
                var5 = var5 + this.sendLastLineToSOT("UPLOADProblem", var3);
                var2.append("Sent: \n" + var5);
                this.pw.close();
                BufferedReader var8 = new BufferedReader(new InputStreamReader(var7.getInputStream()));
                StringBuffer var10 = new StringBuffer();

                String var9;
                while((var9 = var8.readLine()) != null) {
                    var10.append(var9 + "\n");
                    var2.append(var9 + "\n");
                }

                var8.close();
                var5 = var5 + "\nThe reply was:\n\n" + var10.toString();
                return var5;
            } catch (Exception var11) {
                return "Failed because:\n" + var11.toString();
            }
        }
    }

    public String submitTPTP(String var1, TextArea var2) {
        String var3 = "";

        try {
            URL var4 = new URL("http://www.cs.jcu.edu.au/cgi-bin/tptp/SystemOnTPTPFormReply");
            URLConnection var5 = var4.openConnection();
            var5.setDoOutput(true);
            this.pw = new PrintWriter(var5.getOutputStream());
            this.sendLineToSOT("NoHTML", "1");
            this.sendLineToSOT("QuietFlag", "-q2");
            this.sendLineToSOT("SubmitButton", "RunParallel");
            this.sendLineToSOT("ProblemSource", "TPTP");
            this.sendLineToSOT("AutoModeTimeLimit", "300");
            this.sendLineToSOT("AutoMode", "-cE");
            this.sendLineToSOT("AutoModeSystemsLimit", "3");
            this.sendLineToSOT("TPTPProblem", var1);
            this.pw.close();
            BufferedReader var6 = new BufferedReader(new InputStreamReader(var5.getInputStream()));
            StringBuffer var8 = new StringBuffer();

            String var7;
            while((var7 = var6.readLine()) != null) {
                var8.append(var7 + "\n");
                var2.append(var7 + "\n");
            }

            var6.close();
            var3 = var3 + "\nThe reply was:\n\n" + var8.toString();
            return var3;
        } catch (Exception var9) {
            return "Failed because:\n" + var9.toString();
        }
    }

    private String sendLineToSOT(String var1, String var2) {
        return this.sendLineToSOT(var1, var2, false);
    }

    private String sendLastLineToSOT(String var1, String var2) {
        return this.sendLineToSOT(var1, var2, true);
    }

    private String sendLineToSOT(String var1, String var2, boolean var3) {
        char var4 = '&';
        if (var3) {
            var4 = '\n';
        }

        String var5 = var1 + "=" + URLEncoder.encode(var2) + var4;
        this.pw.print(var5);
        return var5;
    }
}
