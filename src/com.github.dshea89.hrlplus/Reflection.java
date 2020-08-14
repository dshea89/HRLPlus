package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.lang.reflect.*;
import java.io.*;

/** A class for using Java reflection capabilities, i.e., to find class names, method
 * names, fields, etc.
 *
 * @author Simon Colton, started 22nd May 2002
 * @version 1.0 */

public class Reflection extends PseudoCodeUser implements Serializable
{
    /** A hashtable of shorthands for conditions, e.g., forall is shorthand for
     * prodruleUsed() == forall (for concepts).
     */

    public Hashtable condition_shorthands = new Hashtable();

    /** A hashtable of shorthands for calues e.g., devel steps is shorthand
     * for development_steps_num
     */

    public Hashtable value_shorthands = new Hashtable();


    /** The simple constructor.
     */

    public Reflection()
    {
        condition_shorthands.put("cross domain", "object.is_cross_domain");
        condition_shorthands.put("integer output", "object.types.size() == 2 && object.types.elementAt(1).equals(\"integer\")");
        condition_shorthands.put("specialisation", "object.types.size() == 1");
        condition_shorthands.put("element type", "object.types.size() == 2 && object.types.elementAt(1).equals(\"element\")");
        condition_shorthands.put("learned", "object.has_required_categorisation");
        condition_shorthands.put("arithmeticb", "object.productionRuleUsedName().equals(\"arithmeticb\")");//friday
        condition_shorthands.put("compose", "object.productionRuleUsedName().equals(\"compose\")");
        condition_shorthands.put("disjunct", "object.productionRuleUsedName().equals(\"disjunct\")");
        condition_shorthands.put("embed_algebra", "object.productionRuleUsedName().equals(\"embed_algebra\")");
        condition_shorthands.put("embed_graph", "object.productionRuleUsedName().equals(\"embed_graph\")");
        condition_shorthands.put("equal", "object.productionRuleUsedName().equals(\"equal\")");
        condition_shorthands.put("exists", "object.productionRuleUsedName().equals(\"exists\")");
        condition_shorthands.put("forall", "object.productionRuleUsedName().equals(\"forall\")");
        condition_shorthands.put("concept_forced", "object.rh_concept.construction.forced");
        condition_shorthands.put("forced", "object.construction.forced");
        condition_shorthands.put("match", "object.productionRuleUsedName().equals(\"match\")");
        condition_shorthands.put("negate", "object.productionRuleUsedName().equals(\"negate\")");
        condition_shorthands.put("record", "object.productionRuleUsedName().equals(\"record\")");
        condition_shorthands.put("size", "object.productionRuleUsedName().equals(\"size\")");
        condition_shorthands.put("split", "object.productionRuleUsedName().equals(\"split\")");

        condition_shorthands.put("segregated", "object.involves_segregated_concepts");
        condition_shorthands.put("prime implicate", "object.is_prime_implicate");
        condition_shorthands.put("instantiation", "object.involves_instantiation");
        condition_shorthands.put("not_instantiation", "!object.involves_instantiation");
        condition_shorthands.put("axiom", "object.proof_status.equals(\"axiom\")");
        condition_shorthands.put("equivalence", "object instanceof Equivalence");
        condition_shorthands.put("implicate", "object instanceof Implicate");
        condition_shorthands.put("near_equiv", "object instanceof NearEquivalence");
        condition_shorthands.put("implication", "object instanceof Implication");
        condition_shorthands.put("non-exists", "object instanceof NonExists");
        condition_shorthands.put("not prime implicate", "!object.is_prime_implicate");
        condition_shorthands.put("proved", "object.proof_status.equals(\"proved\")");
        condition_shorthands.put("undetermined", "!object.proof_status.equals(\"proved\") && !object.proof_status.equals(\"disproved\")");
        condition_shorthands.put("disproved", "object.proof_status.equals(\"disproved\")");
        condition_shorthands.put("sos", "object.proof_status.equals(\"sos\")");
        condition_shorthands.put("time", "object.proof_status.equals(\"time\")");
        condition_shorthands.put("syntax error", "object.proof_status.equals(\"syntax error\")");
    }

    /** This gets all the method names of a given object.
     */

    public Vector getMethodNames(Object obj)
    {
        Method[] methods = obj.getClass().getMethods();
        Vector output = new Vector();
        for (int i=0; i<methods.length; i++)
            output.addElement(findFinal(methods[i].getName()));
        return output;
    }

    /** This gets all the method names of a given object.
     */

    public Vector getMethodDetails(Object obj)
    {
        Method[] methods = obj.getClass().getMethods();
        Vector output = new Vector();
        for (int i=0; i<methods.length; i++)
        {
            String method_detail = findFinal(methods[i].getName()) + "(";
            Class[] arg_classes = methods[i].getParameterTypes();
            for (int j=0; j<arg_classes.length; j++)
                method_detail = method_detail + findFinal(arg_classes[j].getName()) + ",";
            if (arg_classes.length > 0)
                method_detail = method_detail.substring(0, method_detail.length() - 1);
            method_detail = method_detail  + ") = ";
            method_detail = method_detail + findFinal(methods[i].getReturnType().getName());
            output.addElement(method_detail);
        }
        return output;
    }

    /** This gets all the field names of a given object.
     */

    public Vector getFieldNames(Object obj, boolean recurse)
    {
        return getFieldNames(obj, new Vector(), recurse);
    }

    /** This gets the field names of a given object, returning only those which
     * have a type found in the given types (or all if types are null).
     */

    public Vector getFieldNames(Object obj, String field_types[], boolean recurse)
    {
        Vector vtypes = new Vector();
        for (int i=0; i<field_types.length; i++)
            vtypes.addElement(field_types[i]);
        return getFieldNames(obj, vtypes, recurse);
    }

    /** This gets the field names of a given object, returning only those which
     * have a type found in the given types (or all if types is empty).
     */

    public Vector getFieldNames(Object obj, Vector types, boolean recurse)
    {
        return getFieldNames("", obj, types, recurse);
    }

    private Vector getFieldNames(String prefix, Object obj, Vector types, boolean recurse)
    {
        Vector output = new Vector();
        Field[] fields = obj.getClass().getFields();
        for (int i=0; i<fields.length; i++)
        {
            Class field_class = fields[i].getType();
            String ft = findFinal(field_class.toString());
            String fs = fields[i].getName();
            if (types.isEmpty() || types.contains(ft))
            {
                if (prefix.equals("") || prefix.equals("."))
                    output.addElement(fs);
                else
                    output.addElement(prefix + "." + fs);
            }
            if (recurse)
            {
                try
                {
                    Object new_obj = field_class.newInstance();
                    if (prefix.indexOf("." + fs)<0)
                    {
                        String pass_on_prefix = prefix + ".";
                        if (prefix.equals(""))
                            pass_on_prefix = "";
                        Vector sub_fields = getFieldNames(pass_on_prefix + fs, new_obj, types, true);
                        for (int j=0; j<sub_fields.size(); j++)
                            output.addElement((String)sub_fields.elementAt(j));
                    }
                }
                catch(Exception e){}
            }
        }
        return output;
    }

    /** This gets the value of the given field of the given object, and returns it
     * as a string. Note that we are allowed to put "." between properties. E.g.,
     * given that the object is a concept, we can say the field name is construction.forced
     * which will return a true or false, dependent on whether the construction is forced.
     * Also, the subfield can actually be a method name.
     */

    public Object getValue(Object obj, String subfield)
    {
        if (subfield.indexOf(";")<0)
            subfield = subfield + ";";
        pseudo_code_interpreter.local_alias_hashtable.put("object", obj);
        Vector pseudo_code_lines = new Vector();
        pseudo_code_lines.addElement("output = object." + subfield);
        pseudo_code_interpreter.runPseudoCode(pseudo_code_lines);
        return pseudo_code_interpreter.local_alias_hashtable.get("output");
    }

    /** This returns the field name of the given subobject of the given object.
     */

    public String getFieldName(Object super_obj, Object obj)
    {
        String output = "";
        try
        {
            Field[] fields = super_obj.getClass().getFields();
            for (int i=0; i<fields.length; i++)
            {
                if (fields[i].get(super_obj)==obj)
                    output = fields[i].toString();
            }
        }
        catch(Exception e)
        {
        }
        if (output.lastIndexOf(".")>=0)
            output = output.substring(output.lastIndexOf(".")+1, output.length());
        return output;
    }

    public boolean checkCondition(Object obj, String condition_string)
    {
        pseudo_code_interpreter.local_alias_hashtable.put("object", obj);
        if (condition_string.trim().indexOf("instanceof")==0)
        {
            condition_string =
                    "object instanceof " + condition_string.substring(condition_string.indexOf(" ")+1, condition_string.length());
            return pseudo_code_interpreter.checkCondition(condition_string);
        }
        String expanded_condition_string = (String)condition_shorthands.get(condition_string);
        if (expanded_condition_string==null)
            return pseudo_code_interpreter.checkCondition("object." + condition_string.trim());
        return pseudo_code_interpreter.checkCondition(expanded_condition_string);
    }

    /** This returns how much space the serialized object takes up (giving an
     * indication of the memory required)
     */

    public int objectSize(Object obj)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream o = new ObjectOutputStream(baos);
            o.writeObject(obj);
            o.close();
        }
        catch(Exception ex){}
        return baos.toString().length();
    }

    private String findFinal(String s)
    {
        if (s.indexOf(".")>=0)
            s = s.substring(s.lastIndexOf(".") + 1, s.length());
        if (s.indexOf(" ")>=0)
            s = s.substring(s.lastIndexOf(" ") + 1, s.length());
        return s;
    }
}
