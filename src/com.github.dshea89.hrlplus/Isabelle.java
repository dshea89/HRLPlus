package com.github.dshea89.hrlplus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A super class for the Isabelle theorem prover.
 */
public class Isabelle extends Prover implements Serializable {
    /**
     * The standard constructor.
     */
    public Isabelle() {
    }

    /**
     * The method for attempting to prove the given conjecture.
     */
    public boolean prove(Conjecture conjecture, Theory theory) {
        String tptp = conjecture.writeConjecture("tptp");
        String filename = conjecture.id + "_" + System.nanoTime() + "_sledgehammer" + ".thy";
        int timeoutInSeconds = 30;
        boolean proved = false;

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            writer.write(tptp);
            writer.close();

            ProcessBuilder builder;
            Process process;
            BufferedReader stdInput;
            String s;
            String isabelleCommand = "isabelle tptp_sledgehammer " + timeoutInSeconds + " " + filename;
            if (isWindows()) {
                // Isabelle cannot be invoked via the command line in Windows without using the Cygwin bash terminal.
                // This can be accessed by getting the parent directory of the Isabelle command line utility and then
                // appending /contrib/cygwin/bin/bash.exe
                builder = new ProcessBuilder("where isabelle");
                builder.redirectErrorStream(true);
                process = builder.start();
                stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                s = stdInput.readLine();
                s = s.substring(0, s.length() - 12) + "\\contrib\\cygwin\\bin\\bash.exe -l -c ";
                builder = new ProcessBuilder(s + isabelleCommand);
            }
            else {
                builder = new ProcessBuilder(isabelleCommand);
            }
            builder.redirectErrorStream(true);
            process = builder.start();

            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                if (s.startsWith("SUCCESS: ")) {
                    proved = true;
                    break;
                }
            }
            try {
                Files.deleteIfExists(Paths.get(filename));
            } catch (Exception ex) {
                System.err.println("Isabelle prover: Unable to delete file " + filename + ": " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Isabelle prover: " + e.getMessage());
            proved = false;
        }

        return proved;
    }

    /**
     * The method for attempting to disprove the given conjecture by finding a counterexample.
     */
    public boolean disprove(Conjecture conjecture, Theory theory) {
        String tptp = conjecture.writeConjecture("tptp");
        String filename = conjecture.id + "_" + System.nanoTime() + "_nitpick" + ".thy";
        int timeoutInSeconds = 30;
        boolean disproved = false;

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            writer.write(tptp);
            writer.close();

            ProcessBuilder builder;
            Process process;
            BufferedReader stdInput;
            String isabelleCommand = "isabelle tptp_nitpick " + timeoutInSeconds + " " + filename;
            String s;
            if (isWindows()) {
                // Isabelle cannot be invoked via the command line in Windows without using the Cygwin bash terminal.
                // This can be accessed by getting the parent directory of the Isabelle command line utility and then
                // appending /contrib/cygwin/bin/bash.exe
                builder = new ProcessBuilder("where isabelle");
                builder.redirectErrorStream(true);
                process = builder.start();
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                stdInput = new BufferedReader(inputStreamReader);
                s = stdInput.readLine();
                s = s.substring(0, s.length() - 12) + "\\contrib\\cygwin\\bin\\bash.exe -l -c ";
                isabelleCommand = s + isabelleCommand;
            }
            builder = new ProcessBuilder(isabelleCommand);
            builder.redirectErrorStream(true);
            process = builder.start();

            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                if (s.startsWith("Nitpick found a counterexample ")) {
                    disproved = true;
                    break;
                }
            }
            try {
                Files.deleteIfExists(Paths.get(filename));
            } catch (Exception ex) {
                System.err.println("Isabelle disprover: Unable to delete file " + filename + ": " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Isabelle disprover: " + e.getMessage());
            disproved = false;
        }

        return disproved;
    }

    private boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.startsWith("windows") || osName.startsWith("microsoft");
    }
}
