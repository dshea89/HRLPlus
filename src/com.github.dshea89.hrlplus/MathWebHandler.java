package com.github.dshea89.hrlplus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

public class MathWebHandler implements Serializable {
    public boolean require_all = false;
    private InputStream in;
    private OutputStream out;
    private Socket mathweb_socket;

    public MathWebHandler() {
    }

    public MathWebHandler(InetAddress var1, int var2) {
        try {
            this.mathweb_socket = new Socket(var1, var2);
            this.in = this.mathweb_socket.getInputStream();
            this.out = this.mathweb_socket.getOutputStream();
            String var3 = null;

            for(int var4 = 0; var4 < 10; ++var4) {
                var3 = this.readToken();
            }
        } catch (IOException var5) {
            System.out.println(var5);
        }

    }

    public InputStream getInputStream() {
        return this.in;
    }

    public String getService(String var1, String var2) {
        String var3 = "enter('" + var1 + "'" + this.getOptionalArgString("mode", var2) + ")";
        Object var4 = this.sendMessage(var3, true);
        return var4 instanceof String ? (String)var4 : null;
    }

    private String getOptionalArgString(String var1, Object var2) {
        return var2 == null ? "" : " " + var1 + ": " + var2;
    }

    public Object applyMethod(String var1, String var2, String var3, Integer var4) {
        String var5 = "applyMethod('" + var1 + "' " + var2 + this.getOptionalArgString("id", var3) + this.getOptionalArgString("timeout", var4) + ")";
        boolean var6 = true;
        if (var3 != null) {
            var6 = false;
        }

        return this.sendMessage(var5, var6);
    }

    private Object sendMessage(String var1, boolean var2) {
        byte[] var3 = var1.getBytes();

        try {
            this.out.write(var3);
            this.out.write(128);
            this.out.flush();
        } catch (IOException var5) {
            ;
        }

        return var2 ? this.readResult() : new Boolean(var2);
    }

    private Object readResult() {
        String var1 = this.readToken();
        if (var1.equals("end-of-record")) {
            return null;
        } else if (var1.equals("end-of-list")) {
            return null;
        } else if (var1.equals("string")) {
            var1 = this.readToken();
            return var1;
        } else {
            Hashtable var2;
            Vector var3;
            if (!var1.equals("record") && !var1.equals("record")) {
                if (!var1.equals("list") && !var1.equals("ls")) {
                    if (var1.equals("error")) {
                        var1 = this.readToken();
                        System.out.println("error: " + var1);
                        return var1;
                    } else {
                        System.out.println("No method to read result " + var1);
                        return null;
                    }
                } else {
                    var2 = null;
                    var3 = new Vector();
                    boolean var6 = false;

                    do {
                        Object var5 = this.readResult();
                        if (var5 != null) {
                            if (var5 instanceof String) {
                                if (((String)var5).equals("end-of-list")) {
                                    var6 = true;
                                } else {
                                    var3.addElement(var5);
                                }
                            } else {
                                var3.addElement(var5);
                            }
                        } else {
                            var6 = true;
                        }
                    } while(!var6);

                    return var3;
                }
            } else {
                var2 = new Hashtable();
                var3 = null;
                String var4 = null;

                for(var1 = this.readToken(); !var1.equals("end-of-record"); var1 = this.readToken()) {
                    var4 = this.readToken();
                    var2.put(var1, var4);
                }

                return var2;
            }
        }
    }

    private String readToken() {
        boolean var1 = false;
        byte[] var2 = new byte[1024];

        int var10;
        try {
            var10 = this.in.read();
        } catch (IOException var9) {
            return "";
        }

        int var3 = 0;
        String var4 = "";

        String var5;
        while(var10 != 128) {
            if (var3 < 1024) {
                var2[var3] = (byte)var10;
                ++var3;
            } else {
                var5 = new String(var2);
                var4 = var4 + var5;
                var3 = 0;
            }

            try {
                var10 = this.in.read();
            } catch (IOException var8) {
                var10 = 128;
            }
        }

        if (var3 < 1025) {
            byte[] var6 = new byte[var3];

            for(int var7 = 0; var7 < var3; ++var7) {
                var6[var7] = var2[var7];
            }

            var5 = new String(var6);
            var4 = var4 + var5;
        }

        return var4;
    }

    public String leaveService(String var1) {
        String var2 = "leave('" + var1 + "')";
        Object var3 = this.sendMessage(var2, true);
        return var3 instanceof String ? (String)var3 : null;
    }

    public static void main(String[] var0) {
        try {
            if (var0 != null && var0.length == 4 && "--mathweb_service".equals(var0[0])) {
                String var2 = var0[1];
                int var3 = Integer.parseInt(var0[2]);
                int var4 = Integer.parseInt(var0[3]);
                InetAddress var6 = InetAddress.getByName(var2);
                new Socket(var6, var3);
                MathWebHandler var5 = new MathWebHandler(var6, var4);
                String var8 = "prove(\"set(auto).\nformula_list(usable).\nall a (a * id = a).\n-(all a (a * id = a)).\nend_of_list.\")";
                String var9 = var5.getService("OTTER", (String)null);
                var5.applyMethod(var9, var8, (String)null, (Integer)null);
                var5.leaveService(var9);
            } else {
                System.out.println("usage: java MathWeb --mathweb_service HostName InoutPort ServicePort");
            }
        } catch (Exception var11) {
            System.out.println("exc: " + var11);
        }

    }
}
