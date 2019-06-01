package com.github.dshea89.hrlplus;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

public class Reflection extends PseudoCodeUser implements Serializable {
    public Hashtable condition_shorthands = new Hashtable();
    public Hashtable value_shorthands = new Hashtable();

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

    public Vector getMethodNames(Object var1) {
        Method[] var2 = var1.getClass().getMethods();
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.addElement(this.findFinal(var2[var4].getName()));
        }

        return var3;
    }

    public Vector getMethodDetails(Object var1) {
        Method[] var2 = var1.getClass().getMethods();
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

    public Vector getFieldNames(Object var1, boolean var2) {
        return this.getFieldNames(var1, new Vector(), var2);
    }

    public Vector getFieldNames(Object var1, String[] var2, boolean var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var2.length; ++var5) {
            var4.addElement(var2[var5]);
        }

        return this.getFieldNames(var1, var4, var3);
    }

    public Vector getFieldNames(Object var1, Vector var2, boolean var3) {
        return this.getFieldNames("", var1, var2, var3);
    }

    private Vector getFieldNames(String var1, Object var2, Vector var3, boolean var4) {
        Vector var5 = new Vector();
        Field[] var6 = var2.getClass().getFields();

        for(int var7 = 0; var7 < var6.length; ++var7) {
            Class var8 = var6[var7].getType();
            String var9 = this.findFinal(var8.toString());
            String var10 = var6[var7].getName();
            if (var3.isEmpty() || var3.contains(var9)) {
                if (!var1.equals("") && !var1.equals(".")) {
                    var5.addElement(var1 + "." + var10);
                } else {
                    var5.addElement(var10);
                }
            }

            if (var4) {
                try {
                    Object var11 = var8.newInstance();
                    if (var1.indexOf("." + var10) < 0) {
                        String var12 = var1 + ".";
                        if (var1.equals("")) {
                            var12 = "";
                        }

                        Vector var13 = this.getFieldNames(var12 + var10, var11, var3, true);

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

    public Object getValue(Object var1, String var2) {
        if (var2.indexOf(";") < 0) {
            var2 = var2 + ";";
        }

        this.pseudo_code_interpreter.local_alias_hashtable.put("object", var1);
        Vector var3 = new Vector();
        var3.addElement("output = object." + var2);
        this.pseudo_code_interpreter.runPseudoCode(var3);
        return this.pseudo_code_interpreter.local_alias_hashtable.get("output");
    }

    public String getFieldName(Object var1, Object var2) {
        String var3 = "";

        try {
            Field[] var4 = var1.getClass().getFields();

            for(int var5 = 0; var5 < var4.length; ++var5) {
                if (var4[var5].get(var1) == var2) {
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

    public boolean checkCondition(Object var1, String var2) {
        this.pseudo_code_interpreter.local_alias_hashtable.put("object", var1);
        if (var2.trim().indexOf("instanceof") == 0) {
            var2 = "object instanceof " + var2.substring(var2.indexOf(" ") + 1, var2.length());
            return this.pseudo_code_interpreter.checkCondition(var2);
        } else {
            String var3 = (String)this.condition_shorthands.get(var2);
            return var3 == null ? this.pseudo_code_interpreter.checkCondition("object." + var2.trim()) : this.pseudo_code_interpreter.checkCondition(var3);
        }
    }

    public int objectSize(Object var1) {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();

        try {
            ObjectOutputStream var3 = new ObjectOutputStream(var2);
            var3.writeObject(var1);
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
