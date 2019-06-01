package com.github.dshea89.hrlplus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public abstract class Agent extends Thread implements Serializable {
    public AgentWindow window = new AgentWindow();
    public GroupFile group_file = new GroupFile();
    public String name = "";
    public HR hr = new HR();
    public Vector discussion_vector = new Vector();
    public Vector personal_discussion_vector = new Vector();
    public Vector agenda = new Vector();
    Request current_request = new Request("");
    public Vector current_responses = new Vector();
    Response current_response = new Response();
    public ServerSocket server_socket = null;
    public Socket socket = null;

    public Agent() {
    }

    public void initialiseHR(String var1, String var2) {
        HR var3 = new HR();
        String[] var4 = new String[]{var1};
        this.hr = var3.constructHR(var4);
        this.hr.my_agent_name = this.name;
        this.hr.theory.front_end.my_agent_name = this.name;
        this.hr.theory.front_end.main_frame.setTitle(var2);
    }

    public void initialiseHR(String var1, String var2, String var3) {
        System.out.println("status_name: " + var2);
        System.out.println("macro: " + var3);
        String[] var4 = new String[]{var1, "vmacro", var3};
        HR var5 = new HR();
        this.hr = var5.constructHR(var4);
        this.hr.my_agent_name = this.name;
        this.hr.theory.front_end.my_agent_name = this.name;
        this.hr.theory.front_end.main_frame.setTitle(var2);
    }

    public void listenForCall(ServerSocket var1) {
        ObjectInputStream var2 = null;

        while(true) {
            try {
                this.socket = var1.accept();
                var2 = new ObjectInputStream(this.socket.getInputStream());
                Object var3 = var2.readObject();
                this.window.writeToFrontEnd("just received " + var3);
                if (var3 instanceof Vector) {
                    Vector var4 = (Vector)var3;
                    if (!var4.isEmpty() && var4.elementAt(0) instanceof Response) {
                        for(int var5 = 0; var5 < var4.size(); ++var5) {
                            Response var6 = (Response)var4.elementAt(var5);
                            this.personal_discussion_vector.addElement(var6);
                        }

                        this.window.writeToFrontEnd("current_responses is " + this.current_responses);
                        this.window.writeToFrontEnd("received_vector is " + var4);
                        this.current_responses = var4;
                        this.window.writeToFrontEnd("current_responses is now " + this.current_responses);
                        this.window.writeToFrontEnd("received_vector is now " + var4);
                    }
                }

                if (var3 instanceof Request) {
                    this.current_request = (Request)var3;
                    this.personal_discussion_vector.addElement(this.current_request);
                }

                if (var3 instanceof Response) {
                    this.window.writeQuietlyToFrontEnd("received_object is  instanceof  Response");
                    this.current_response = (Response)var3;
                    this.window.writeToFrontEnd("current_response is now " + this.current_response);
                    this.personal_discussion_vector.addElement(this.current_response);
                }

                var2.close();
                this.socket.close();
                break;
            } catch (Exception var7) {
                this.window.writeToFrontEnd("Error in listenForCall: " + var7);
            }
        }

        this.window.writeQuietlyToFrontEnd("at end of listenForCall, current_responses is " + this.current_responses);
    }

    public void makeCall(int var1, InetAddress var2, int var3, Object var4) {
        while(true) {
            try {
                this.socket = new Socket(var2, var3);
                ObjectOutputStream var5 = new ObjectOutputStream(this.socket.getOutputStream());
                var5.writeObject(var4);
                this.personal_discussion_vector.addElement(var5.toString());
                if (var4 instanceof Request) {
                    Request var6 = (Request)var4;
                    if (!var6.isEmpty()) {
                        this.window.writeToFrontEnd("Just called " + var2 + " -- " + var3 + " -- " + " to say ");
                        this.window.writeToFrontEnd(var4);
                        this.window.writeToFrontEnd("");
                    }
                } else {
                    this.window.writeToFrontEnd("Just called " + var2 + " -- " + var3 + " -- " + " to say ");
                    this.window.writeToFrontEnd(var4);
                    this.window.writeToFrontEnd("");
                }

                var5.flush();
                var5.close();
                this.socket.close();
                return;
            } catch (ConnectException var7) {
                ;
            } catch (Exception var8) {
                this.window.writeToFrontEnd("Error in makeCall: " + var8);
                this.window.writeToFrontEnd("should exit here");
            }
        }
    }

    public void makeCallToAllStudents(int var1, Vector var2, Request var3) {
        for(int var4 = 0; var4 < var2.size(); var4 += 2) {
            InetAddress var5 = (InetAddress)var2.elementAt(var4);
            String var6 = (String)var2.elementAt(var4 + 1);
            int var7 = Integer.parseInt(var6);
            this.makeCall(var1, var5, var7, var3);
        }

    }

    public void makeCallToAllStudents(int var1, Vector var2, Vector var3) {
        for(int var4 = 0; var4 < var2.size(); var4 += 2) {
            InetAddress var5 = (InetAddress)var2.elementAt(var4);
            String var6 = (String)var2.elementAt(var4 + 1);
            int var7 = Integer.parseInt(var6);
            this.makeCall(var1, var5, var7, var3);
        }

    }

    public Vector listenForCallFromAllStudents(ServerSocket var1, Vector var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.size(); var4 += 2) {
            this.listenForCall(var1);
            this.window.writeToFrontEnd("done listenForCall(server_socket)");
            if (this.current_request != null) {
                var3.addElement(this.current_request);
            }

            if (this.current_response != null) {
                var3.addElement(this.current_response);
            }
        }

        return var3;
    }

    public void listenForResponsesToCurrentRequest(ServerSocket var1, Request var2, int var3, Vector var4) {
        for(int var5 = 0; var5 < var3; ++var5) {
            this.current_response = null;
            this.listenForCall(var1);
            if (this.current_response != null && this.current_response.request.equals(var2)) {
                var4.addElement(this.current_response);
            }
        }

    }

    public Vector listenForRequestsToResponses1(ServerSocket var1, Request var2, int var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var3; ++var5) {
            this.listenForCall(var1);
            Object var6 = this.personal_discussion_vector.lastElement();
            if (var6 instanceof Request) {
                Request var7 = (Request)this.personal_discussion_vector.lastElement();
                if (var7 != null && var7.motivation.attempted_method.equals("monster-barring")) {
                    var4.addElement(var7);
                }
            }
        }

        return var4;
    }

    public Vector listenForRequestsToResponses(ServerSocket var1, Request var2, int var3) {
        this.window.writeToFrontEnd("started listenForRequestsToResponses");
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var3; ++var5) {
            this.window.writeToFrontEnd("before listenForCall, current_request is " + var2);
            this.listenForCall(var1);
            this.window.writeToFrontEnd("after listenForCall, current_request is " + var2);
            this.window.writeToFrontEnd("current_request==null is " + var2 == null);
            boolean var6 = var2.motivation.attempted_method.equals("monster-barring");
            String var7 = var2.motivation.attempted_method;
            this.window.writeQuietlyToFrontEnd("current_request.motivation.attempted_method is " + var7);
            this.window.writeQuietlyToFrontEnd("(current_request.motivation.attempted_method).equals(\"monster-barring\") is " + var6);
            if (var2 != null && var2.motivation.attempted_method.equals("monster-barring")) {
                this.window.writeQuietlyToFrontEnd("in here - just adding " + var2 + " to output");
                var4.addElement(var2);
            }
        }

        this.window.writeToFrontEnd("at end of listenForRequestsToResponses, output is " + var4);
        return var4;
    }

    protected void finalize() {
        try {
            this.server_socket.close();
            this.socket.close();
        } catch (IOException var2) {
            this.window.writeToFrontEnd("couldn't close socket");
            System.exit(-1);
        }

    }
}
