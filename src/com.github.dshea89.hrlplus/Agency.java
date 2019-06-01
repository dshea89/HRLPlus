package com.github.dshea89.hrlplus;

import java.io.File;
import java.net.InetAddress;
import java.util.Vector;

public class Agency {
    public Vector processes = new Vector();
    public String homedir = "";
    public String remote_HR_path = "";
    public String agent_names = "";

    public Agency() {
    }

    public static void main(String[] var0) {
        Agency var1 = new Agency();
        var1.parseArgs(var0);
        var1.getHomeDir();
        var1.remote_HR_path = var1.homedir + "/hr/agency";
        File var2 = new File(var1.remote_HR_path);
        if (!var2.exists()) {
            var2.mkdir();
        }

        var1.createAndStartProcesses(var0);
    }

    public void parseArgs(String[] var1) {
        if (var1.length == 0) {
            System.out.println("You need to input the names of the computers ");
            System.out.println("on which you wish to run HR,");
            System.out.println("i.e. agency <hostname1> [<hostname2>...]");
            System.exit(-1);
        }

    }

    public void getHomeDir() {
        try {
            this.homedir = System.getProperty("user.home");
        } catch (SecurityException var2) {
            System.out.println("Problem reading user home directory");
            System.exit(-1);
        }

    }

    public void createAndStartProcesses(String[] var1) {
        String var2 = "";
        int var3;
        if (var1.length != 0) {
            var3 = var1.length - 1;
            var2 = var1[var3];
        }

        for(var3 = 0; var3 < var1.length; ++var3) {
            try {
                String var4 = InetAddress.getLocalHost().getHostName();
                short var6 = 8000;
                String var7;
                if (var3 < var1.length - 1) {
                    int var10 = var6 + var3;
                    this.agent_names = this.agent_names + " " + var1[var3] + ":" + var10;
                    var7 = this.homedir + "/hr/runs/makestudent " + var1[var3] + " " + var4 + " " + var2 + " " + var10;
                    System.out.println(var7);
                    Runtime.getRuntime().exec(var7);
                    System.out.println("student");
                } else {
                    this.agent_names = this.agent_names + " " + var1[var3];
                    var7 = this.homedir + "/hr/runs/maketeacher " + var1[var3] + " " + var4 + this.agent_names;
                    System.out.println(var7);
                    Process var8 = Runtime.getRuntime().exec(var7);
                    System.out.println("agent_names are " + this.agent_names);
                    var8.waitFor();
                }
            } catch (Exception var9) {
                System.out.println("Problem starting agency");
                System.out.println(var9);
                System.exit(-1);
            }
        }

    }
}
