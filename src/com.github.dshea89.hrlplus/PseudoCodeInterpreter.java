package com.github.dshea89.hrlplus;

import java.awt.TextArea;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

public class PseudoCodeInterpreter implements Serializable {
    public int highlight_pos = 0;
    public String input_reporting_style = "highlight";
    public boolean output_to_system_out = false;
    public boolean break_now = false;
    public String line_being_looked_at = "";
    public Vector packages = new Vector();
    public boolean out_to_screen = true;
    public TextArea input_text_box = new TextArea();
    public TextArea output_text_box = new TextArea();
    public Hashtable local_alias_hashtable = new Hashtable();

    public PseudoCodeInterpreter() {
    }

    public void runPseudoCode(String var1, Hashtable var2) {
        this.local_alias_hashtable = var2;
        Vector var3 = new Vector();
        this.packages = new Vector();
        var1 = var1 + "\n";

        for(boolean var4 = false; var1.indexOf("\n") > 0; var1 = var1.substring(var1.indexOf("\n") + 1, var1.length())) {
            String var5 = var1.substring(0, var1.indexOf("\n"));
            var3.addElement(var5);
        }

        this.runPseudoCode(var3);
    }

    public void runPseudoCode(Vector var1) {
        if (this.out_to_screen) {
            this.output_text_box.setText("");
            if (this.input_reporting_style.equals("print")) {
                this.input_text_box.setText("");
            }
        }

        boolean var2 = false;

        String var4;
        int var10;
        for(var10 = 0; var10 < var1.size() && !this.break_now; ++var10) {
            this.line_being_looked_at = (String)var1.elementAt(var10);
            if (this.output_to_system_out) {
                System.out.println("executing: " + this.line_being_looked_at);
            }

            this.inputString(this.line_being_looked_at);
            String var3 = this.line_being_looked_at.trim();
            if (var3.equals("break")) {
                break;
            }

            if (!var3.equals("") && var3.indexOf("//") != 0 && !var3.equals("}") && !var3.equals("{")) {
                if (var3.indexOf("for(") == 0 || var3.indexOf("for (") == 0) {
                    var4 = var3.substring(var3.indexOf("(") + 1, var3.indexOf(";")).trim() + ";";
                    if (var4.indexOf(" ") >= 0) {
                        var4 = var4.substring(var4.indexOf(" ") + 1, var4.length());
                    }

                    String var5 = var3.substring(var3.indexOf(";") + 1, var3.lastIndexOf(";")).trim();
                    String var6 = var3.substring(var3.lastIndexOf(";") + 1, var3.length() - 1).trim() + ";";
                    Vector var7 = new Vector();
                    int var8 = var10 + 2;
                    this.inputString("{");

                    for(var8 = var10 + 2; var8 < var1.size(); ++var8) {
                        this.line_being_looked_at = (String)var1.elementAt(var8);
                        String var9 = this.line_being_looked_at.trim();
                        if (var9.equals("}")) {
                            break;
                        }

                        var7.addElement(var9);
                        this.inputString(var9);
                    }

                    if (!this.runPseudoCodeLine(var4)) {
                        this.outputString("couldn't initialise loop");
                        return;
                    }

                    while(true) {
                        if (!this.checkCondition(var5)) {
                            this.inputString("}");
                            var10 = var10 + var7.size() + 2;
                            break;
                        }

                        for(var8 = 0; var8 < var7.size(); ++var8) {
                            this.runPseudoCodeLine((String)var7.elementAt(var8));
                        }

                        this.runPseudoCodeLine(var6);
                    }
                }

                if (var3.indexOf("if") == 0) {
                    var4 = var3.substring(var3.indexOf("(") + 1, var3.lastIndexOf(")"));
                    boolean var12 = this.checkCondition(var4);
                    if (!var12) {
                        int var14 = 1;
                        this.inputString((String)var1.elementAt(var10 + 1));
                        int var15 = var10 + 2;

                        for(var15 = var10 + 2; var15 < var1.size() && var14 > 0; ++var15) {
                            String var16 = (String)var1.elementAt(var15);
                            if (var16.trim().equals("{")) {
                                ++var14;
                            }

                            if (var16.trim().equals("}")) {
                                --var14;
                            }

                            this.inputString(var16);
                            this.outputString("");
                        }

                        var10 = var15 - 1;
                        this.outputString("");
                    }
                }

                if (var3.indexOf("if") != 0 && var3.indexOf("for(") != 0 && var3.indexOf("for (") != 0) {
                    boolean var13 = this.runPseudoCodeLine(var3);
                    if (!var13) {
                        break;
                    }
                }
            } else {
                this.outputString("");
            }
        }

        for(int var11 = var10 + 1; var11 < var1.size() && !this.input_reporting_style.equals("highlight"); ++var11) {
            var4 = (String)var1.elementAt(var11);
            this.inputString(var4);
        }

        this.line_being_looked_at = "";
        if (this.break_now) {
            this.break_now = false;
        }

    }

    public boolean runPseudoCodeLine(String var1) {
        if (var1.indexOf("import") == 0) {
            String var12 = var1.substring(var1.indexOf(" "), this.lastNonStringIndexOf(var1, ".")).trim();
            this.packages.addElement(var12);
            this.outputString("package added: " + var12);
            return true;
        } else {
            int var2 = this.nonStringIndexOf(var1, "=");
            String var3;
            if (var2 < 0) {
                if (var1.indexOf("++") > 0) {
                    var3 = var1.substring(0, var1.indexOf("++"));

                    try {
                        Integer var15 = (Integer)this.local_alias_hashtable.get(var3);
                        Integer var17 = new Integer(var15 + 1);
                        this.local_alias_hashtable.put(var3, var17);
                        this.outputString(var3 + " = " + var17.toString());
                        return true;
                    } catch (Exception var10) {
                        try {
                            Double var14 = (Double)this.local_alias_hashtable.get(var3);
                            Double var16 = new Double(var14 + 1.0D);
                            this.local_alias_hashtable.put(var3, var16);
                            this.outputString(var3 + " = " + var16.toString());
                            return true;
                        } catch (Exception var9) {
                            ;
                        }
                    }
                }

                if (this.nonStringIndexOf(var1, ".") < 0 || this.nonStringIndexOf(var1, "(") < this.nonStringIndexOf(var1, ".")) {
                    var1 = "this." + var1;
                }

                Object var13 = this.runCode(var1);
                if (var13 == null) {
                    this.outputString("method failed: " + var1);
                    return false;
                } else {
                    this.outputString("done");
                    return true;
                }
            } else {
                var3 = var1.substring(0, var2).trim();
                String var4 = var1.substring(var2 + 1, var1.indexOf(";")).trim();
                Object var5 = this.getObject(var4);
                if (var3.indexOf(" ") >= 0 && var5 != null) {
                    var3 = var3.substring(var3.indexOf(" ") + 1, var3.length()).trim();
                    this.local_alias_hashtable.put(var3, var5);
                    this.outputString(var3 + " = " + var5.toString());
                    return true;
                } else {
                    this.getObject(var3);
                    if (var5 != null) {
                        String var7 = "";
                        this.getObject(var3);
                        Object var6;
                        if (this.nonStringIndexOf(var3, ".") >= 0) {
                            String var8 = var3.substring(0, this.lastNonStringIndexOf(var3, "."));
                            var7 = var3.substring(this.lastNonStringIndexOf(var3, ".") + 1, var3.length());
                            var6 = this.getObject(var8);
                            if (var6 == null) {
                                var6 = this.getObject("this." + var8);
                            }
                        } else {
                            var6 = this.getObject("this");
                            var7 = var3;
                        }

                        if (var6 != null) {
                            try {
                                Field var18 = var6.getClass().getField(var7);
                                var18.set(var6, var5);
                                this.outputString(var3 + " = " + var5.toString());
                                return true;
                            } catch (Exception var11) {
                                ;
                            }
                        }
                    }

                    if (this.nonStringIndexOf(var3, ".") < 0 && var5 != null) {
                        this.local_alias_hashtable.put(var3, var5);
                        this.outputString(var3 + " = " + var5.toString());
                        return true;
                    } else {
                        this.outputString("assignment failed");
                        return false;
                    }
                }
            }
        }
    }

    public Object getObject(String var1) {
        String var2;
        Object var3;
        if (var1.indexOf("Integer.toString(") != 0 && var1.indexOf("Double.toString(") != 0) {
            if (var1.indexOf("new ") >= 0) {
                var2 = var1.substring(var1.lastIndexOf(" ") + 1, var1.indexOf("("));

                try {
                    var3 = Class.forName(var2).newInstance();
                    return var3;
                } catch (Exception var9) {
                    int var12 = 0;

                    while(var12 < this.packages.size()) {
                        String var4 = (String)this.packages.elementAt(var12);

                        try {
                            Object var5 = Class.forName(var4 + "." + var2).newInstance();
                            return var5;
                        } catch (Exception var8) {
                            ++var12;
                        }
                    }

                    this.outputString("can't create this object");
                    return null;
                }
            } else {
                if (var1.indexOf("\"") >= 0) {
                    var2 = this.getString(var1);
                    if (var2 != null) {
                        return var2;
                    }
                }

                try {
                    Integer var10 = new Integer(var1.substring(0, var1.length()));
                    if (var10 != null) {
                        return var10;
                    }
                } catch (Exception var7) {
                    ;
                }

                try {
                    Double var11 = new Double(var1.substring(0, var1.length()));
                    if (var11 != null) {
                        return var11;
                    }
                } catch (Exception var6) {
                    ;
                }

                if (this.local_alias_hashtable.containsKey(var1)) {
                    return this.local_alias_hashtable.get(var1);
                } else if (var1.equals("true")) {
                    return new Boolean(true);
                } else if (var1.equals("false")) {
                    return new Boolean(false);
                } else {
                    Object var13 = this.runCode(var1);
                    return var13;
                }
            }
        } else {
            var2 = var1.substring(this.nonStringIndexOf(var1, "(") + 1, this.nonStringIndexOf(var1, ")"));
            var3 = this.getObject(var2);
            return var3.toString();
        }
    }

    public Object runCode(String var1) {
        if (var1.indexOf("System.out.println") == 0) {
            String var6 = var1.substring(this.nonStringIndexOf(var1, "(") + 1, this.nonStringIndexOf(var1, ")"));
            this.getObject(var6);
            return "void";
        } else {
            var1 = var1 + ".";
            int var2 = this.nonStringNonBracketIndexOf(var1, ".");
            Object var3 = new Object();

            for(Object var4 = null; var2 > 0; var2 = this.nonStringNonBracketIndexOf(var1, ".")) {
                String var5 = var1.substring(0, var2);
                var3 = this.runMethod(var4, var5);
                if (var3 == null) {
                    return null;
                }

                var4 = var3;
                var1 = var1.substring(var2 + 1, var1.length());
            }

            return var3;
        }
    }

    public Object runMethod(Object var1, String var2) {
        String var3;
        if (var2.indexOf("(") < 0) {
            if (this.local_alias_hashtable.containsKey(var2)) {
                return this.local_alias_hashtable.get(var2);
            } else {
                if (var2.indexOf("\"") >= 0) {
                    var3 = this.getString(var2);
                    if (var3 != null) {
                        return var3;
                    }
                }

                try {
                    Field var16 = var1.getClass().getField(var2);
                    Object var17 = var16.get(var1);
                    if (var17 != null) {
                        return var17;
                    }
                } catch (Exception var12) {
                    ;
                }

                return null;
            }
        } else {
            var3 = var2.substring(this.nonStringIndexOf(var2, "(") + 1, this.lastNonStringIndexOf(var2, ")")) + ",";
            int var4 = this.nonStringIndexOf(var3, ",");

            Vector var5;
            String var7;
            for(var5 = new Vector(); var4 > 0; var4 = this.nonStringIndexOf(var3, ",")) {
                Object var6 = null;
                var7 = var3.substring(0, var4).trim();
                var6 = this.getString(var7);
                if (var6 == null && (var7.equals("true") || var7.equals("false"))) {
                    var6 = new Boolean(var7);
                }

                if (var6 == null) {
                    try {
                        var6 = new Integer(var7);
                    } catch (Exception var15) {
                        ;
                    }
                }

                if (var6 == null) {
                    try {
                        var6 = new Double(var7);
                    } catch (Exception var14) {
                        ;
                    }
                }

                if (var6 == null) {
                    var6 = this.local_alias_hashtable.get(var7);
                }

                if (var6 == null) {
                    return null;
                }

                var5.addElement(var6);
                var3 = var3.substring(var4 + 1, var3.length());
            }

            Object[] var19 = new Object[var5.size()];

            for(int var18 = 0; var18 < var5.size(); ++var18) {
                var19[var18] = var5.elementAt(var18);
            }

            var7 = var2.substring(0, var2.indexOf("("));
            Method[] var8 = var1.getClass().getMethods();

            for(int var9 = 0; var9 < var8.length; ++var9) {
                String var10 = var8[var9].getName();
                if (var10.equals(var7)) {
                    try {
                        if (var8[var9].getReturnType().getName().equals("void")) {
                            var8[var9].invoke(var1, var19);
                            return "void";
                        }

                        Object var11 = var8[var9].invoke(var1, var19);
                        if (var11 != null) {
                            return var11;
                        }
                    } catch (Exception var13) {
                        ;
                    }
                }
            }

            return null;
        }
    }

    public Vector getAllClassNames(Object var1) {
        Vector var2 = new Vector();
        Class var3 = var1.getClass();
        String var4 = var3.getName();
        if (this.nonStringIndexOf(var4, ".") > 0) {
            var4 = var4.substring(this.lastNonStringIndexOf(var4, ".") + 1, var4.length());
        }

        var2.addElement(var4);
        Class var5 = var3.getSuperclass();
        if (!var5.getName().equals("Object")) {
            try {
                Object var6 = var5.newInstance();
                Vector var7 = this.getAllClassNames(var6);

                for(int var8 = 0; var8 < var7.size(); ++var8) {
                    var2.addElement(var7.elementAt(var8));
                }
            } catch (Exception var9) {
                ;
            }
        }

        var2.addElement("Object");
        return var2;
    }

    public String getString(String var1) {
        if (!var1.substring(0, 1).equals("\"") && var1.indexOf("+") < 0) {
            return null;
        } else {
            String var2 = "";
            var1 = var1 + "+";

            for(int var3 = this.nonStringIndexOf(var1, "+"); var3 > 0; var3 = this.nonStringIndexOf(var1, "+")) {
                String var4 = var1.substring(0, var3).trim();
                if (var4.substring(0, 1).equals("\"")) {
                    var4 = var4.substring(1, var4.length() - 1);
                } else if (this.local_alias_hashtable.containsKey(var4)) {
                    Object var5 = this.local_alias_hashtable.get(var4);
                    if (!(var5 instanceof String)) {
                        return null;
                    }

                    var4 = (String)var5;
                }

                var2 = var2 + var4;
                var1 = var1.substring(var3 + 1, var1.length());
            }

            while(var2.indexOf("\\n") >= 0) {
                var2 = var2.substring(0, var2.indexOf("\\n")) + "\n" + var2.substring(var2.indexOf("\\n") + 2, var2.length());
            }

            while(var2.indexOf("\\\"") >= 0) {
                var2 = var2.substring(0, var2.indexOf("\\\"")) + "\"" + var2.substring(var2.indexOf("\\\"") + 2, var2.length());
            }

            return var2;
        }
    }

    public boolean checkCondition(String var1) {
        for(int var2 = var1.indexOf("&&"); var2 >= 0; var2 = var1.indexOf("&&")) {
            String var3 = var1.substring(0, var2).trim();
            if (!this.checkCondition(var3)) {
                return false;
            }

            var1 = var1.substring(var2 + 2, var1.length()).trim();
        }

        boolean var17 = false;
        String var4 = "";
        String var5 = "";
        String var6 = "";
        String[] var7 = new String[]{"==", "!=", "<=", ">=", "<", ">", "instanceof"};
        double var8 = 0.0D;
        double var10 = 0.0D;
        Object var12 = new Object();
        new Object();

        for(int var14 = 0; var14 < var7.length; ++var14) {
            if (var1.indexOf(var7[var14]) > 0) {
                var4 = var7[var14];
                var5 = var1.substring(0, var1.indexOf(var7[var14])).trim();
                var6 = var1.substring(var1.indexOf(var7[var14]) + var4.length(), var1.length()).trim();
                var12 = this.getObject(var5);
                if (!var4.equals("instanceof")) {
                    Object var13 = this.getObject(var6);

                    try {
                        var8 = new Double(var12.toString());
                        var10 = new Double(var13.toString());
                    } catch (Exception var16) {
                        this.outputString("couldn't evaluate this if statement: " + var1);
                        return false;
                    }
                }
                break;
            }
        }

        if (var4.equals("instanceof")) {
            Vector var18 = this.getAllClassNames(var12);
            if (var18.contains(var6)) {
                var17 = true;
            }
        }

        if (var4.equals("==") && var8 == var10) {
            var17 = true;
        }

        if (var4.equals("!=") && var8 != var10) {
            var17 = true;
        }

        if (var4.equals("<") && var8 < var10) {
            var17 = true;
        }

        if (var4.equals(">") && var8 > var10) {
            var17 = true;
        }

        if (var4.equals("<=") && var8 <= var10) {
            var17 = true;
        }

        if (var4.equals(">=") && var8 >= var10) {
            var17 = true;
        }

        if (!var4.equals("")) {
            this.outputString((new Boolean(var17)).toString());
            return var17;
        } else {
            boolean var19 = false;
            if (var1.substring(0, 1).equals("!")) {
                var1 = var1.substring(1, var1.length());
                var19 = true;
            }

            Object var15 = this.getObject(var1);
            if (var15 != null && var15 instanceof Boolean) {
                if (var15 instanceof Boolean) {
                    this.outputString(var15.toString());
                    var17 = (Boolean)var15;
                }

                if (var19 && !var17) {
                    return true;
                } else {
                    return var19 && var17 ? false : var17;
                }
            } else {
                this.outputString("couldn't evaluate this if statement");
                return false;
            }
        }
    }

    public int nonStringIndexOf(String var1, String var2) {
        byte var3 = -1;
        boolean var4 = false;

        for(int var5 = 0; var5 < var1.length() && var3 < 0; ++var5) {
            String var6 = var1.substring(var5, var5 + 1);
            if (var6.equals("\"") && (var5 == 0 || !var1.substring(var5 - 1, var5).equals("\\"))) {
                if (!var4) {
                    var4 = true;
                } else {
                    var4 = false;
                }
            }

            if (var6.equals(var2) && !var4) {
                return var5;
            }
        }

        return var3;
    }

    public int nonStringNonBracketIndexOf(String var1, String var2) {
        byte var3 = -1;
        boolean var4 = false;
        int var5 = 0;

        for(int var6 = 0; var6 < var1.length() && var3 < 0; ++var6) {
            String var7 = var1.substring(var6, var6 + 1);
            if (var7.equals("\"") && (var6 == 0 || !var1.substring(var6 - 1, var6).equals("\\"))) {
                if (!var4) {
                    var4 = true;
                } else {
                    var4 = false;
                }
            }

            if (var7.equals("(")) {
                ++var5;
            }

            if (var7.equals(")")) {
                --var5;
            }

            if (var7.equals(var2) && !var4 && var5 == 0) {
                return var6;
            }
        }

        return var3;
    }

    public int lastNonStringIndexOf(String var1, String var2) {
        int var3 = -1;
        boolean var4 = false;

        for(int var5 = 0; var5 < var1.length(); ++var5) {
            String var6 = var1.substring(var5, var5 + 1);
            if (var6.equals("\"") && (var5 == 0 || !var1.substring(var5 - 1, var5).equals("\\"))) {
                if (!var4) {
                    var4 = true;
                } else {
                    var4 = false;
                }
            }

            if (var6.equals(var2) && !var4) {
                var3 = var5;
            }
        }

        return var3;
    }

    public void outputString(String var1) {
        if (this.out_to_screen) {
            this.output_text_box.append(var1 + "\n");
        }

    }

    public void inputString(String var1) {
        if (this.out_to_screen) {
            if (this.input_reporting_style.equals("print")) {
                this.input_text_box.append(var1 + "\n");
            }

            if (this.input_reporting_style.equals("highlight")) {
                int var2 = this.input_text_box.getText().indexOf("\n", this.highlight_pos);
                if (var2 >= 0) {
                    this.input_text_box.replaceRange("", this.highlight_pos, var2);
                    this.input_text_box.insert(var1, this.highlight_pos);
                }

                int var3 = this.highlight_pos;
                this.highlight_pos = this.highlight_pos + var1.length() + 1;
                this.input_text_box.setSelectionStart(var3);
                this.input_text_box.setSelectionEnd(this.highlight_pos);
            }
        }

    }
}
