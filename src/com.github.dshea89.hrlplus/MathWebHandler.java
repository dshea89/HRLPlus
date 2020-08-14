package com.github.dshea89.hrlplus;

import java.io.*;
import java.net.*;
import java.util.*;

/** A Java class for the access of MathWeb services.
 * @author J"urgen Zimmer and Simon Colton, started 1st October 2001
 * @version 1.0
 */

public class MathWebHandler implements Serializable
{
    /** Whether or not the results from all the Mathweb
     * provers is required (if not, just the result from
     * the first is supplied).
     */

    public boolean require_all = false;

    private InputStream in;
    private OutputStream out;
    private Socket mathweb_socket;

    /** The simple constructor
     */

    public MathWebHandler()
    {
    }

    /** Constructor requiring an InetAddress and a port number
     */

    public MathWebHandler(InetAddress host, int mathweb_port) {
        super();
        try {
            mathweb_socket = new Socket(host, mathweb_port);

            in = mathweb_socket.getInputStream();
            out = mathweb_socket.getOutputStream();
            String token = null;
            for (int i=0; i<10; i++)
            {
                token = readToken();
            }
        }
        catch (IOException e) {System.out.println(e);}
    }

    // If some application wants to install its own listener on the
    // inputstream it can get it the input stream here. The application
    // should then use only asynchronous method calls

    public InputStream getInputStream()
    {
        return this.in;
    }

    public String getService(String ServiceName, String mode) {
        String message =
                "enter('"+ServiceName+"'"+
                        getOptionalArgString("mode", mode)+
                        ")";

        Object result = sendMessage(message, true);

        if (result instanceof String)
            return (String) result;
        else
            return null;
    }

    private String getOptionalArgString(String feature, Object value)
    {
        if (value == null)
            return "";
        else
            return " "+feature+": "+value;
    }

    public Object applyMethod(String service, String message, String id, Integer timeout)
    {
        String real_message = "applyMethod('"+service+"' "+message+
                getOptionalArgString("id", id) +
                getOptionalArgString("timeout", timeout) + ")";
        boolean synchronous = true;
        if (id != null)
            synchronous = false;
        return sendMessage(real_message, synchronous);
    }

    private Object sendMessage(String message, boolean synchronous)
    {
        byte[] bytes = message.getBytes();

        try {
            this.out.write(bytes);
            this.out.write(128);
            this.out.flush();
        }
        catch (IOException e) {
            //      System.out.println("exc: " + e);
        }
        if (synchronous == true)
            return readResult();
        else
            return new Boolean(synchronous);
    }

    private Object readResult()
    {
        String token = readToken();

        if (token.equals("end-of-record"))
        {
            return null;
        }
        if (token.equals("end-of-list"))
        {
            return null;
        }
        else if (token.equals("string"))
        {
            token = readToken();
            return token;
        }
        else if (token.equals("record") || token.equals("record"))
        {
            Hashtable table = new Hashtable();
            String key = null;
            Object value = null;

            token = readToken();
            while (! token.equals("end-of-record"))
            {
                key = token;
                value = readToken();
                table.put(key, value);
                token = readToken();
            }
            return table;
        }
        else if (token.equals("list") || token.equals("ls"))
        {
            Object res = null;
            Vector list = new Vector();
            boolean end = false;
            do {
                res = readResult();
                if (res != null)
                {
                    if (res instanceof String)
                    {
                        if (((String) res).equals("end-of-list"))
                            end = true;
                        else
                        {
                            list.addElement(res);
                        }
                    }
                    else
                    {
                        list.addElement(res);
                    }
                }
                else end = true;
            }
            while (!end);
            return list;
        }

        else if (token.equals("error"))
        {
            token = readToken();
            System.out.println("error: "+token);
            return token;
        }
        else
        {
            System.out.println("No method to read result " + token);
            return null;
        }
    }

    private String readToken()
    {
        int c=0;
        byte b[] = new byte[1024];

        try
        {
            c = this.in.read();
        }

        catch (IOException e)
        {
            return "";
        }

        int i=0;
        String output = "";
        String add_on;

        while (c != 128)
        {
            if (i<1024)
            {
                b[i]=(byte)c;
                i++;
            }
            else
            {
                add_on = new String(b);
                output = output + add_on;
                i=0;
            }
            try
            {
                c = this.in.read();
            }
            catch (IOException e)
            {
                c = 128;
            }
        }
        if (i<1025)
        {
            byte rest[] = new byte[i];
            for (int e=0; e<i; e++) rest[e]=b[e];
            add_on = new String(rest);
            output = output + add_on;
        }

        return output;
    }

    public String leaveService(String service)
    {
        String message = "leave('"+service+"')";
        Object result = sendMessage(message, true);
        if (result instanceof String)
            return (String) result;
        else
            return null;
    }

    public static void main (String[] args)
    {
        try
        {
            String os_type;
            String hostname;
            int inout_port;
            int mathweb_port;
            MathWebHandler mw;

            if ((args != null) && (args.length == 4) && ("--mathweb_service".equals(args[0])))
            {
                hostname = args[1];
                inout_port = Integer.parseInt(args[2]);
                mathweb_port  = Integer.parseInt(args[3]);

                InetAddress host = InetAddress.getByName(hostname);

                Socket inout= new Socket(host, inout_port);

                mw = new MathWebHandler(host, mathweb_port);

                String prove = "prove(\""+
                        "set(auto).\nformula_list(usable).\nall a (a * id = a).\n-(all a (a * id = a)).\nend_of_list."+
                        "\")";

                String otter = mw.getService("OTTER", null);
                Object result = mw.applyMethod(otter, prove, null, null);
                mw.leaveService(otter);

            }
            else
            {
                System.out.println("usage: java MathWeb --mathweb_service HostName InoutPort ServicePort");
            }

        }
        catch (Exception e)
        {
            System.out.println("exc: " + e);
        }

    }
}
