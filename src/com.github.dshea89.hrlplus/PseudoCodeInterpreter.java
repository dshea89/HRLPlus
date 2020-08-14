package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;
import java.awt.TextArea;
import java.lang.reflect.*;

/** A class for handling pieces of (psuedo)-java code.
 * @author Simon Colton, started 27th June 2002
 * @version 1.0
 */

public class PseudoCodeInterpreter implements Serializable
{
    /** The highlight position for the input code.
     */

    public int highlight_pos = 0;

    /** The input reporting style (either "highlight" or "print").
     */

    public String input_reporting_style = "highlight";

    /** Whether or not to copy the line being executed to System.out.
     */

    public boolean output_to_system_out = false;//change to true to see what's going on

    /** Whether or not to stop executing the code. If this is set during the code
     * execution, then the interpreter will stop.
     */

    public boolean break_now = false;

    /** The current line being executed (useful for finding where this
     * code stopped).
     */

    public String line_being_looked_at = "";

    /** The packages which are available to this piece of pseudo-code
     */

    public Vector packages = new Vector();

    /** Whether or not to output to screen.
     */

    public boolean out_to_screen = true;

    /** The input text area (inherited from the theory (frontend)).
     */

    public TextArea input_text_box = new TextArea();

    /** The output text area (inherited from the theory (frontend)).
     */

    public TextArea output_text_box = new TextArea();

    /** The hashtable of (alias,objects) pairs, where the objects have been
     * constructed as part of the pseudo-code being run.
     */

    public Hashtable local_alias_hashtable = new Hashtable();

    /** This runs the given piece of psuedo-code on the given theory.
     * The hashtable contains the starting aliases. For instance, if you wish
     * to run some pseudo code which uses the Theory, then you can
     * call this "theory" in the starting hashtable, and refer to
     * "theory" in the pseudo code.
     */

    public void runPseudoCode(String pseudo_code, Hashtable starting_alias_hashtable)
    {
        local_alias_hashtable = starting_alias_hashtable;
        Vector pseudo_code_lines = new Vector();
        packages = new Vector();
        pseudo_code = pseudo_code+"\n";
        int i=0;
        while (pseudo_code.indexOf("\n")>0)
        {
            String pseudo_code_line = pseudo_code.substring(0, pseudo_code.indexOf("\n"));
            pseudo_code_lines.addElement(pseudo_code_line);
            pseudo_code = pseudo_code.substring(pseudo_code.indexOf("\n")+1, pseudo_code.length());
        }
        runPseudoCode(pseudo_code_lines);
    }

    /** This runs the given lines of pseudo-code.
     */

    public void runPseudoCode(Vector pseudo_code_lines)
    {
        if (out_to_screen)
        {
            output_text_box.setText("");
            if (input_reporting_style.equals("print"))
                input_text_box.setText("");
        }

        int i=0;
        for (i=0; i<pseudo_code_lines.size() && break_now==false; i++)
        {
            line_being_looked_at = (String)pseudo_code_lines.elementAt(i);
            if (output_to_system_out)
                System.out.println("executing: " + line_being_looked_at);
            inputString(line_being_looked_at);
            String pcl = line_being_looked_at.trim();
            if (pcl.equals("break"))
                break;
            if (pcl.equals("") || pcl.indexOf("//")==0 || pcl.equals("}") || pcl.equals("{"))
            {
                outputString("");
            }
            else
            {
                // Deal with for loop //

                if (pcl.indexOf("for(")==0 || pcl.indexOf("for (")==0)
                {
                    String for_initialiser = pcl.substring(pcl.indexOf("(")+1, pcl.indexOf(";")).trim()+";";
                    if (for_initialiser.indexOf(" ")>=0)
                        for_initialiser = for_initialiser.substring(for_initialiser.indexOf(" ")+1, for_initialiser.length());
                    String for_condition = pcl.substring(pcl.indexOf(";")+1, pcl.lastIndexOf(";")).trim();
                    String for_incrementor = pcl.substring(pcl.lastIndexOf(";")+1, pcl.length()-1).trim()+";";
                    Vector for_loop_commands = new Vector();
                    int j=i+2;
                    inputString("{");
                    for (j=i+2; j<pseudo_code_lines.size(); j++)
                    {
                        line_being_looked_at = (String)pseudo_code_lines.elementAt(j);
                        String for_pcl = line_being_looked_at.trim();
                        if (!for_pcl.equals("}"))
                        {
                            for_loop_commands.addElement(for_pcl);
                            inputString(for_pcl);
                        }
                        else
                            break;
                    }

                    if (!runPseudoCodeLine(for_initialiser))
                    {
                        outputString("couldn't initialise loop");
                        return;
                    }

                    while(checkCondition(for_condition))
                    {
                        for (j=0; j<for_loop_commands.size(); j++)
                            runPseudoCodeLine((String)for_loop_commands.elementAt(j));
                        runPseudoCodeLine(for_incrementor);
                    }
                    inputString("}");
                    i=i+for_loop_commands.size()+2;
                }

                // Deal with if statements //

                if (pcl.indexOf("if")==0)
                {
                    //System.out.println(" --------got an if");
                    String condition =  pcl.substring(pcl.indexOf("(")+1, pcl.lastIndexOf(")"));
                    boolean if_statement_is_true = checkCondition(condition);
                    if (!if_statement_is_true)
                    {
                        int balance_num = 1;
                        inputString((String)pseudo_code_lines.elementAt(i+1));
                        int j=i+2;
                        for (j=i+2; j<pseudo_code_lines.size() && balance_num > 0; j++)
                        {
                            String skip_pcl = (String)pseudo_code_lines.elementAt(j);
                            if (skip_pcl.trim().equals("{"))
                                balance_num++;
                            if (skip_pcl.trim().equals("}"))
                                balance_num--;
                            inputString(skip_pcl);
                            outputString("");
                        }
                        i=j-1;
                        outputString("");
                    }
                }

                if (pcl.indexOf("if")!=0 && pcl.indexOf("for(")!=0 && pcl.indexOf("for (")!=0)
                {
                    boolean line_has_run = runPseudoCodeLine(pcl);
                    if (!line_has_run)
                        break;
                }
            }
        }

        for (int j=i+1; j<pseudo_code_lines.size() && !input_reporting_style.equals("highlight"); j++)
        {
            String orig_pcl = (String)pseudo_code_lines.elementAt(j);
            inputString(orig_pcl);
        }
        line_being_looked_at = "";
        if (break_now)
            break_now = false;
    }

    /** This runs a single line of pseudo code.
     */

    public boolean runPseudoCodeLine(String pcl)
    {
        // Check whether this is the importation of classes //

        if (pcl.indexOf("import")==0)
        {
            String pack = pcl.substring(pcl.indexOf(" "), lastNonStringIndexOf(pcl, ".")).trim();
            packages.addElement(pack);
            outputString("package added: " + pack);
            return true;
        }

        int equals_index = nonStringIndexOf(pcl, "=");

        if (equals_index<0)
        {
            if (pcl.indexOf("++")>0)
            {
                String var_alias = pcl.substring(0, pcl.indexOf("++"));
                try
                {
                    Integer i = (Integer)local_alias_hashtable.get(var_alias);
                    Integer new_i = new Integer(i.intValue()+1);
                    local_alias_hashtable.put(var_alias, new_i);
                    outputString(var_alias + " = " + new_i.toString());
                    return true;
                }
                catch(Exception e){}
                try
                {
                    Double d = (Double)local_alias_hashtable.get(var_alias);
                    Double new_d = new Double(d.doubleValue()+1);
                    local_alias_hashtable.put(var_alias, new_d);
                    outputString(var_alias + " = " + new_d.toString());
                    return true;
                }
                catch(Exception e){}
            }
            if (nonStringIndexOf(pcl, ".")<0 || nonStringIndexOf(pcl, "(") < nonStringIndexOf(pcl, "."))
                pcl = "this." + pcl;
            Object return_object = runCode(pcl);
            if (return_object==null)
            {
                outputString("method failed: " + pcl);
                return false;
            }
            else
            {
                outputString("done");
                return true;
            }
        }

        String lhs = pcl.substring(0, equals_index).trim();
        String rhs = pcl.substring(equals_index + 1,pcl.indexOf(";")).trim();
        Object rh_object = getObject(rhs);
        if (lhs.indexOf(" ")>=0 && rh_object!=null)
        {
            lhs = lhs.substring(lhs.indexOf(" ") + 1, lhs.length()).trim();
            local_alias_hashtable.put(lhs, rh_object);
            outputString(lhs + " = " + rh_object.toString());
            return true;
        }
        Object lh_object = getObject(lhs);

        // First see whether the left hand side needs "this." added to it.

        if (rh_object!=null)
        {
            String field_name = "";
            lh_object = getObject(lhs);
            if (nonStringIndexOf(lhs, ".")>=0)
            {
                String short_lhs = lhs.substring(0,lastNonStringIndexOf(lhs, "."));
                field_name = lhs.substring(lastNonStringIndexOf(lhs, ".")+1, lhs.length());
                lh_object = getObject(short_lhs);
                if (lh_object==null)
                    lh_object = getObject("this." + short_lhs);
            }
            else
            {
                lh_object = getObject("this");
                field_name = lhs;
            }
            if (lh_object!=null)
            {
                try
                {
                    Field field = lh_object.getClass().getField(field_name);
                    field.set(lh_object, rh_object);
                    outputString(lhs + " = " + rh_object.toString());
                    return true;
                }
                catch(Exception e){}
            }
        }

        if (nonStringIndexOf(lhs, ".")<0 && rh_object!=null)
        {
            local_alias_hashtable.put(lhs, rh_object);
            outputString(lhs + " = " + rh_object.toString());
            return true;
        }

        outputString("assignment failed");
        return false;
    }

    public Object getObject(String code)
    {
        //System.out.println("code is " + code);

        // Check whether this is an Integer or Double function //

        if (code.indexOf("Integer.toString(")==0 || code.indexOf("Double.toString(")==0)
        {
            String input_to_string = code.substring(nonStringIndexOf(code, "(") + 1, nonStringIndexOf(code, ")"));
            Object obj = getObject(input_to_string);
            return (String)obj.toString();
        }

        // Check whether this is the construction of a new object //

        if (code.indexOf("new ")>=0)
        {
            String class_name = code.substring(code.lastIndexOf(" ")+1, code.indexOf("("));
            try
            {
                Object obj = (Class.forName(class_name)).newInstance();
                return obj;
            }
            catch(Exception e){}
            for (int i=0; i<packages.size(); i++)
            {
                String pack = (String)packages.elementAt(i);
                try
                {
                    Object obj = (Class.forName(pack+"."+class_name)).newInstance();
                    return obj;
                }
                catch(Exception e){}
            }
            outputString("can't create this object");
            return null;
        }

        // Check to see if this is just a string //

        if (code.indexOf("\"")>=0)
        {
            String output = getString(code);
            if (output!=null)
                return output;
        }

        // Check to see if this is just an integer //

        try
        {
            Integer output = new Integer(code.substring(0,code.length()));
            if (output!=null)
                return output;
        }
        catch(Exception e){};

        // Check to see if this is just a double //

        try
        {
            Double output = new Double(code.substring(0,code.length()));
            if (output!=null)
                return output;
        }
        catch(Exception e){};

        // Check to see if this is a local variable //

        if (local_alias_hashtable.containsKey(code))
            return local_alias_hashtable.get(code);

        // Check to see if this is a boolean

        if (code.equals("true"))
            return new Boolean(true);

        if (code.equals("false"))
            return new Boolean(false);

        // Seperate into the sections (delimited by a dot) and run the individual methods //

        Object output = runCode(code);
        return output;
    }

    public Object runCode(String code)
    {
        //System.out.println("Into runCode: code is " + code);

        // Check whether this is a System.out call

        if (code.indexOf("System.out.println")==0)
        {
            String input_to_string =
                    code.substring(nonStringIndexOf(code, "(") + 1, nonStringIndexOf(code, ")"));
            Object obj_to_print = getObject(input_to_string);
            //System.out.println(obj_to_print);
            return "void";
        }

        code = code + ".";
        int pos = nonStringNonBracketIndexOf(code, ".");
        //System.out.println("pos is " + pos);
        Object final_obj = new Object();
        Object current_obj = null;
        while (pos > 0)
        {
            //System.out.println("runCode - in here");
            String code_string = code.substring(0, pos);
            //System.out.println("code_string is " + code_string);
            final_obj = runMethod(current_obj, code_string);
            if (final_obj==null)
                return null;
            current_obj = final_obj;
            code = code.substring(pos+1, code.length());
            pos = nonStringNonBracketIndexOf(code, ".");
            //System.out.println("pos is now " + pos);
        }
        return final_obj;
    }

    /** This runs a method on the given object.
     */

    public Object runMethod(Object obj, String method_string)
    {
        //System.out.println("into runMethod:  method_string is " +  method_string);
        //System.out.println("method_string.indexOf is " + method_string.indexOf("("));
        //System.out.println("obj is " + obj);

        if (method_string.indexOf("(")<0)
        {
            //System.out.println("index of ( is less than 0");

            // Check to see if the method is just an object (in the alias hashtable).

            if (local_alias_hashtable.containsKey(method_string))
                return local_alias_hashtable.get(method_string);

            // Check to see if the method is just a string.

            if (method_string.indexOf("\"")>=0)
            {
                String output = getString(method_string);
                if (output!=null)
                    return output;
            }
            try
            {
                //System.out.println(" mon night ");
                Field f = obj.getClass().getField(method_string);
                //System.out.println(" mon night - f is " + f);
                Object output = f.get(obj);
                //System.out.println(" mon night - output is " + output);
                if (output!=null)
                    return output;
            }
            catch(Exception e){}
            return null;
        }

        String args =
                method_string.substring(nonStringIndexOf(method_string, "(")+1, lastNonStringIndexOf(method_string, ")")) + ",";

        //System.out.println(" args is " + args);
        int ind = nonStringIndexOf(args, ",");
        Vector variables = new Vector();
        while (ind > 0)
        {
            //System.out.println("hello ");

            // Find the correct place for the comma //

            Object variable = null;
            String var_string = args.substring(0, ind).trim();

            // First see if the variable is a string //

            variable = getString(var_string);

            // Next see if the variable is 'true' or 'false' //

            if (variable==null && (var_string.equals("true") || var_string.equals("false")))
                variable = new Boolean(var_string);

            // Next see if the variable is an integer //

            if (variable==null)
            {
                try
                {
                    variable = new Integer(var_string);
                }
                catch(Exception e){}
            }

            // Next see if the variable is a double //

            if (variable==null)
            {
                try
                {
                    variable = new Double(var_string);
                }
                catch(Exception e){}
            }

            // Next, see if the variable is a local variable (i.e., it has a hashtable) //

            if (variable==null)
            {
                //System.out.println("trying the hashtable - var_string is " + var_string);
                variable = local_alias_hashtable.get(var_string);
                //System.out.println("still trying the hashtable -- the variable is " + variable);
            }
            //  if the variable has not been identified //

            if (variable==null)
            {
                //System.out.println("returning null - here");
                return null;
            }
            variables.addElement(variable);
            args = args.substring(ind + 1, args.length());
            ind = nonStringIndexOf(args, ",");
        }

        Object[] variable_array = new Object[variables.size()];
        for (int i=0; i<variables.size(); i++)
        {
            variable_array[i] = variables.elementAt(i);
        }

        String method_name = method_string.substring(0, method_string.indexOf("("));
        Method[] methods = obj.getClass().getMethods();
        for (int i=0; i<methods.length; i++)
        {
            String try_method_name = methods[i].getName();
            if (try_method_name.equals(method_name))
            {
                try
                {
                    if (methods[i].getReturnType().getName().equals("void"))
                    {
                        methods[i].invoke(obj, variable_array);
                        return "void";
                    }
                    else
                    {
                        Object output = methods[i].invoke(obj, variable_array);
                        if (output!=null)
                            return output;
                    }
                }
                catch(Exception e){}
            }
        }
        return null;
    }

    public Vector getAllClassNames(Object obj)
    {
        Vector output = new Vector();
        Class obj_class = obj.getClass();
        String class_name = obj_class.getName();
        if (nonStringIndexOf(class_name, ".")>0)
            class_name = class_name.substring(lastNonStringIndexOf(class_name, ".")+1, class_name.length());
        output.addElement(class_name);
        Class super_class = obj_class.getSuperclass();
        if (!super_class.getName().equals("Object"))
        {
            try
            {
                Object super_object = super_class.newInstance();
                Vector super_classes = getAllClassNames(super_object);
                for (int i=0; i<super_classes.size(); i++)
                    output.addElement(super_classes.elementAt(i));
            }
            catch(Exception e)
            {
            }
        }
        output.addElement("Object");
        return output;
    }

    public String getString(String s)
    {
        if (!s.substring(0,1).equals("\"") && s.indexOf("+")<0)
            return null;
        String output = "";
        s = s + "+";
        int plus_pos = nonStringIndexOf(s, "+");
        while (plus_pos > 0)
        {
            String next_string = s.substring(0,plus_pos).trim();
            if (next_string.substring(0,1).equals("\""))
                next_string = next_string.substring(1,next_string.length()-1);
            else
            {
                if (local_alias_hashtable.containsKey(next_string))
                {
                    Object ns = local_alias_hashtable.get(next_string);
                    if (!(ns instanceof String))
                        return null;
                    else
                        next_string = (String)ns;
                }
            }
            output = output + next_string;
            s = s.substring(plus_pos+1, s.length());
            plus_pos = nonStringIndexOf(s, "+");
        }
        while (output.indexOf("\\n")>=0)
            output = output.substring(0, output.indexOf("\\n")) + "\n" + output.substring(output.indexOf("\\n")+2, output.length());
        while (output.indexOf("\\\"")>=0)
            output = output.substring(0, output.indexOf("\\\"")) + "\"" + output.substring(output.indexOf("\\\"")+2, output.length());
        return output;
    }

    public boolean checkCondition(String condition_string)
    {
        //System.out.println("condition_string is " + condition_string);

        int and_pos = condition_string.indexOf("&&");
        while (and_pos>=0)
        {
            String first_condition = condition_string.substring(0, and_pos).trim();
            if (checkCondition(first_condition) == false)
                return false;
            condition_string = condition_string.substring(and_pos + 2, condition_string.length()).trim();
            and_pos = condition_string.indexOf("&&");
        }

        boolean output = false;
        String relationship = "";
        String lh_to_eval = "";
        String rh_to_eval = "";
        String[] relationships = {"==","!=","<=",">=","<",">","instanceof"};
        double lhd = 0;
        double rhd = 0;
        Object lh_obj = new Object();
        Object rh_obj = new Object();

        //System.out.println("relationships.length is " + relationships.length);
        for (int i=0; i<relationships.length; i++)
        {
            if (condition_string.indexOf(relationships[i])>0)
            {
                relationship = relationships[i];
                //System.out.println("relationship is " + relationship);
                lh_to_eval = condition_string.substring(0,condition_string.indexOf(relationships[i])).trim();
                rh_to_eval = condition_string.substring(condition_string.indexOf(relationships[i])+relationship.length(),
                        condition_string.length()).trim();
                //System.out.println("lh_to_eval is " + lh_to_eval);

                lh_obj = getObject(lh_to_eval);
                if (!relationship.equals("instanceof"))
                {
                    rh_obj = getObject(rh_to_eval);
                    try
                    {
                        lhd = (new Double(lh_obj.toString())).doubleValue();
                        rhd = (new Double(rh_obj.toString())).doubleValue();
                    }
                    catch(Exception e)
                    {
                        outputString("couldn't evaluate this if statement: " + condition_string);
                        return false;
                    }
                }
                break;
            }
        }

        if (relationship.equals("instanceof"))
        {
            //System.out.println("lh_obj is " + lh_obj);
            Vector class_names = getAllClassNames(lh_obj);
            if (class_names.contains(rh_to_eval))
                output = true;
        }

        if (relationship.equals("==") && lhd == rhd) output = true;
        if (relationship.equals("!=") && lhd != rhd) output = true;
        if (relationship.equals("<") && lhd < rhd) output = true;
        if (relationship.equals(">") && lhd > rhd) output = true;
        if (relationship.equals("<=") && lhd <= rhd) output = true;
        if (relationship.equals(">=") && lhd >= rhd) output = true;
        if (!relationship.equals(""))
        {
            outputString((new Boolean(output)).toString());
            return output;
        }

        boolean require_negative = false;
        if (condition_string.substring(0,1).equals("!"))
        {
            condition_string = condition_string.substring(1,condition_string.length());
            require_negative = true;
        }

        Object tf = getObject(condition_string);
        if (tf==null || !(tf instanceof Boolean))
        {
            outputString("couldn't evaluate this if statement");
            return false;
        }
        if (tf instanceof Boolean)
        {
            outputString(tf.toString());
            output = ((Boolean)tf).booleanValue();
        }
        if (require_negative && output==false)
            return true;
        if (require_negative && output==true)
            return false;

        return output;
    }

    public int nonStringIndexOf(String line, String match)
    {
        int output = -1;
        int balance = 0;
        int pos = 0;
        while (pos < line.length() && output < 0)
        {
            String sub = line.substring(pos, pos+1);
            if (sub.equals("\"") && (pos==0 || !line.substring(pos-1,pos).equals("\\")))
            {
                if (balance==0)
                    balance=1;
                else
                    balance=0;
            }
            if (sub.equals(match) && balance==0)
                return pos;
            pos++;
        }
        return output;
    }

    public int nonStringNonBracketIndexOf(String line, String match)
    {
        int output = -1;
        int quote_balance = 0;
        int bracket_balance = 0;
        int pos = 0;
        while (pos < line.length() && output < 0)
        {
            String sub = line.substring(pos, pos+1);
            if (sub.equals("\"") && (pos==0 || !line.substring(pos-1,pos).equals("\\")))
            {
                if (quote_balance==0)
                    quote_balance=1;
                else
                    quote_balance=0;
            }
            if (sub.equals("("))
                bracket_balance++;
            if (sub.equals(")"))
                bracket_balance--;
            if (sub.equals(match) && quote_balance==0 && bracket_balance==0)
                return pos;
            pos++;
        }
        return output;
    }

    public int lastNonStringIndexOf(String line, String match)
    {
        int output = -1;
        int balance = 0;
        int pos = 0;
        while (pos < line.length())
        {
            String sub = line.substring(pos, pos+1);
            if (sub.equals("\"") && (pos==0 || !line.substring(pos-1,pos).equals("\\")))
            {
                if (balance==0)
                    balance=1;
                else
                    balance=0;
            }
            if (sub.equals(match) && balance==0)
                output = pos;
            pos++;
        }
        return output;
    }

    public void outputString(String s)
    {
        if (out_to_screen)
            output_text_box.append(s+"\n");
    }

    public void inputString(String s)
    {
        if (out_to_screen)
        {
            if (input_reporting_style.equals("print"))
                input_text_box.append(s+"\n");
            if (input_reporting_style.equals("highlight"))
            {
                int endpos = input_text_box.getText().indexOf("\n", highlight_pos);
                if (endpos >= 0)
                {
                    input_text_box.replaceRange("", highlight_pos, endpos);
                    input_text_box.insert(s, highlight_pos);
                }
                int old_hpos = highlight_pos;
                highlight_pos = highlight_pos + s.length()+1;
                input_text_box.setSelectionStart(old_hpos);
                input_text_box.setSelectionEnd(highlight_pos);
            }
        }
    }
}
