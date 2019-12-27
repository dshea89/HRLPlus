package com.github.dshea89.hrlplus;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A class for using Java reflection capabilities, i.e., to find class names, method names, fields, etc.
 */
public class Reflection extends PseudoCodeUser implements Serializable {
    /**
     * A hashtable of shorthands for conditions, e.g., forall is shorthand for prodruleUsed() == forall (for concepts).
     */
    public Hashtable condition_shorthands = new Hashtable();

    /**
     * A hashtable of shorthands for values e.g., devel steps is shorthand for development_steps_num
     */
    public Hashtable value_shorthands = new Hashtable();

    /**
     * The simple constructor.
     */
    public Reflection() {
        this.condition_shorthands.put("cross domain", "object.is_cross_domain");
        this.condition_shorthands.put("integer output", "object.types.size() == 2 && object.types.elementAt(1).equals(\"integer\")");
        this.condition_shorthands.put("specialisation", "object.types.size() == 1");
        this.condition_shorthands.put("element type", "object.types.size() == 2 && object.types.elementAt(1).equals(\"element\")");
        this.condition_shorthands.put("learned", "object.has_required_categorisation");
        this.condition_shorthands.put("arithmeticb", "object.productionRuleUsedName().equals(\"arithmeticb\")");
        this.condition_shorthands.put("compose", "object.productionRuleUsedName().equals(\"compose\")");
        this.condition_shorthands.put("disjunct", "object.productionRuleUsedName().equals(\"disjunct\")");
        this.condition_shorthands.put("embed_algebra", "object.productionRuleUsedName().equals(\"embed_algebra\")");
        this.condition_shorthands.put("embed_graph", "object.productionRuleUsedName().equals(\"embed_graph\")");
        this.condition_shorthands.put("equal", "object.productionRuleUsedName().equals(\"equal\")");
        this.condition_shorthands.put("exists", "object.productionRuleUsedName().equals(\"exists\")");
        this.condition_shorthands.put("forall", "object.productionRuleUsedName().equals(\"forall\")");
        this.condition_shorthands.put("concept_forced", "object.rh_concept.construction.forced");
        this.condition_shorthands.put("forced", "object.construction.forced");
        this.condition_shorthands.put("match", "object.productionRuleUsedName().equals(\"match\")");
        this.condition_shorthands.put("negate", "object.productionRuleUsedName().equals(\"negate\")");
        this.condition_shorthands.put("record", "object.productionRuleUsedName().equals(\"record\")");
        this.condition_shorthands.put("size", "object.productionRuleUsedName().equals(\"size\")");
        this.condition_shorthands.put("split", "object.productionRuleUsedName().equals(\"split\")");
        this.condition_shorthands.put("segregated", "object.involves_segregated_concepts");
        this.condition_shorthands.put("prime implicate", "object.is_prime_implicate");
        this.condition_shorthands.put("instantiation", "object.involves_instantiation");
        this.condition_shorthands.put("not_instantiation", "!object.involves_instantiation");
        this.condition_shorthands.put("axiom", "object.proof_status.equals(\"axiom\")");
        this.condition_shorthands.put("equivalence", "object instanceof Equivalence");
        this.condition_shorthands.put("implicate", "object instanceof Implicate");
        this.condition_shorthands.put("near_equiv", "object instanceof NearEquivalence");
        this.condition_shorthands.put("implication", "object instanceof Implication");
        this.condition_shorthands.put("non-exists", "object instanceof NonExists");
        this.condition_shorthands.put("not prime implicate", "!object.is_prime_implicate");
        this.condition_shorthands.put("proved", "object.proof_status.equals(\"proved\")");
        this.condition_shorthands.put("undetermined", "!object.proof_status.equals(\"proved\") && !object.proof_status.equals(\"disproved\")");
        this.condition_shorthands.put("disproved", "object.proof_status.equals(\"disproved\")");
        this.condition_shorthands.put("sos", "object.proof_status.equals(\"sos\")");
        this.condition_shorthands.put("time", "object.proof_status.equals(\"time\")");
        this.condition_shorthands.put("syntax error", "object.proof_status.equals(\"syntax error\")");
    }

    /**
     * This gets all the method names of a given object.
     */
    public Vector getMethodNames(Object obj) {
        Method[] var2 = obj.getClass().getMethods();
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.addElement(this.findFinal(var2[var4].getName()));
        }

        return var3;
    }

    /**
     * This gets all the method names of a given object.
     */
    public Vector getMethodDetails(Object obj) {
        Method[] var2 = obj.getClass().getMethods();
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            String var5 = this.findFinal(var2[var4].getName()) + "(";
            Class[] var6 = var2[var4].getParameterTypes();

            for(int var7 = 0; var7 < var6.length; ++var7) {
                var5 = var5 + this.findFinal(var6[var7].getName()) + ",";
            }

            if (var6.length > 0) {
                var5 = var5.substring(0, var5.length() - 1);
            }

            var5 = var5 + ") = ";
            var5 = var5 + this.findFinal(var2[var4].getReturnType().getName());
            var3.addElement(var5);
        }

        return var3;
    }

    /**
     * This gets all the field names of a given object.
     */
    public Vector getFieldNames(Object obj, boolean recurse) {
        return this.getFieldNames(obj, new Vector(), recurse);
    }

    /**
     * This gets the field names of a given object, returning only those which have a type found in the given types (or all if types are null).
     */
    public Vector getFieldNames(Object obj, String[] field_types, boolean recurse) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < field_types.length; ++var5) {
            var4.addElement(field_types[var5]);
        }

        return this.getFieldNames(obj, var4, recurse);
    }

    /**
     * This gets the field names of a given object, returning only those which have a type found in the given types (or all if types is empty).
     */
    public Vector getFieldNames(Object obj, Vector types, boolean recurse) {
        return this.getFieldNames("", obj, types, recurse);
    }

    private Vector getFieldNames(String var1, Object obj, Vector types, boolean recurse) {
        Vector var5 = new Vector();
        Field[] var6 = obj.getClass().getFields();

        for(int var7 = 0; var7 < var6.length; ++var7) {
            Class var8 = var6[var7].getType();
            String var9 = this.findFinal(var8.toString());
            String var10 = var6[var7].getName();
            if (types.isEmpty() || types.contains(var9)) {
                if (!var1.equals("") && !var1.equals(".")) {
                    var5.addElement(var1 + "." + var10);
                } else {
                    var5.addElement(var10);
                }
            }

            if (recurse) {
                try {
                    Object var11 = var8.newInstance();
                    if (var1.indexOf("." + var10) < 0) {
                        String var12 = var1 + ".";
                        if (var1.equals("")) {
                            var12 = "";
                        }

                        Vector var13 = this.getFieldNames(var12 + var10, var11, types, true);

                        for(int var14 = 0; var14 < var13.size(); ++var14) {
                            var5.addElement((String)var13.elementAt(var14));
                        }
                    }
                } catch (Exception var15) {
                    ;
                }
            }
        }

        return var5;
    }

    /**
     * This gets the value of the given field of the given object, and returns it as a string. Note that we are allowed
     * to put "." between properties. E.g., given that the object is a concept, we can say the field name is
     * construction.forced which will return a true or false, dependent on whether the construction is forced.
     * Also, the subfield can actually be a method name.
     */
    public Object getValue(Object obj, String subfield) {
        if (subfield.indexOf(";") < 0) {
            subfield = subfield + ";";
        }

        this.pseudo_code_interpreter.local_alias_hashtable.put("object", obj);
        Vector var3 = new Vector();
        var3.addElement("output = object." + subfield);
        this.pseudo_code_interpreter.runPseudoCode(var3);
        return this.pseudo_code_interpreter.local_alias_hashtable.get("output");
    }

    /**
     * This returns the field name of the given subobject of the given object.
     */
    public String getFieldName(Object super_obj, Object obj) {
        String var3 = "";

        try {
            Field[] var4 = super_obj.getClass().getFields();

            for(int var5 = 0; var5 < var4.length; ++var5) {
                if (var4[var5].get(super_obj) == obj) {
                    var3 = var4[var5].toString();
                }
            }
        } catch (Exception var6) {
            ;
        }

        if (var3.lastIndexOf(".") >= 0) {
            var3 = var3.substring(var3.lastIndexOf(".") + 1, var3.length());
        }

        return var3;
    }

    public boolean checkCondition(Object obj, String condition_string) {
        this.pseudo_code_interpreter.local_alias_hashtable.put("object", obj);
        if (condition_string.trim().indexOf("instanceof") == 0) {
            condition_string = "object instanceof " + condition_string.substring(condition_string.indexOf(" ") + 1, condition_string.length());
            return this.pseudo_code_interpreter.checkCondition(condition_string);
        } else {
            String var3 = (String)this.condition_shorthands.get(condition_string);
            return var3 == null ? this.pseudo_code_interpreter.checkCondition("object." + condition_string.trim()) : this.pseudo_code_interpreter.checkCondition(var3);
        }
    }

    /**
     * This returns how much space the serialized object takes up (giving an indication of the memory required)
     */
    public int objectSize(Object obj) {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();

        try {
            ObjectOutputStream var3 = new ObjectOutputStream(var2);
            var3.writeObject(obj);
            var3.close();
        } catch (Exception var4) {
            ;
        }

        return var2.toString().length();
    }

    private String findFinal(String var1) {
        if (var1.indexOf(".") >= 0) {
            var1 = var1.substring(var1.lastIndexOf(".") + 1, var1.length());
        }

        if (var1.indexOf(" ") >= 0) {
            var1 = var1.substring(var1.lastIndexOf(" ") + 1, var1.length());
        }

        return var1;
    }
}
