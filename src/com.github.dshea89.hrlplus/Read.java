package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Vector;

public class Read implements Serializable {
    public UserFunctions user_functions = new UserFunctions();

    public Read() {
    }

    public Vector defaultDetails(String var1) {
        return new Vector();
    }

    public Vector readFile(String var1, String var2) {
        Vector var3 = new Vector();
        Vector var4 = new Vector();
        Vector var5 = new Vector();
        Vector var6 = new Vector();
        Vector var7 = new Vector();
        Vector var8 = new Vector();
        Vector var9 = new Vector();
        var3.addElement(var4);
        var3.addElement(var5);
        var3.addElement(var6);
        var3.addElement(var7);
        var3.addElement(var8);
        var3.addElement(var9);
        Vector var10 = new Vector();
        Vector var11 = new Vector();
        Vector var12 = new Vector();

        int var16;
        try {
            BufferedReader var13 = new BufferedReader(new FileReader(var1));
            String var14 = var13.readLine();
            if (!var2.equals("")) {
                for(int var15 = 0; var15 < var14.length() - 7; ++var15) {
                    if (var14.substring(var15, var15 + 7).equals("algebra")) {
                        var14 = var14.substring(0, var15) + var2 + var14.substring(var15 + 7, var14.length());
                    }
                }
            }

            Vector var29 = new Vector();

            label359:
            while(true) {
                do {
                    if (var14 == null) {
                        if (!var10.isEmpty()) {
                            var10.insertElementAt(var29.clone(), 2);
                            new Vector();
                            var11.addElement(var10.clone());
                        }

                        var13.close();
                        break label359;
                    }

                    if (var14.trim().equals("")) {
                        if (!var10.isEmpty()) {
                            var10.insertElementAt(var29.clone(), 2);
                            var29 = new Vector();
                            var11.addElement(var10.clone());
                        }

                        var10 = new Vector();
                    } else if (!var14.substring(0, 1).equals("%")) {
                        if (var14.indexOf(":\"\"") < 0 && var14.indexOf("@") < 0) {
                            var10.addElement(var14);
                        } else {
                            var29.addElement(var14);
                        }
                    }

                    var14 = var13.readLine();
                } while(var2.equals(""));

                for(var16 = 0; var16 < var14.length() - 6; ++var16) {
                    if (var14.substring(var16, var16 + 7).equals("algebra")) {
                        var14 = var14.substring(0, var16) + var2 + var14.substring(var16 + 7, var14.length());
                    }
                }
            }
        } catch (Exception var26) {
            ;
        }

        int var17;
        int var18;
        String var19;
        int var20;
        String var21;
        String var22;
        int var27;
        Concept var28;
        String var30;
        String var31;
        Vector var36;
        for(var27 = 0; var27 < var11.size(); ++var27) {
            var10 = (Vector)var11.elementAt(var27);
            var28 = new Concept();
            var4.addElement(var28);
            var30 = (String)var10.elementAt(1);
            var28.name = var30.substring(0, var30.indexOf("("));
            var28.id = (String)var10.elementAt(0);
            var28.ancestor_ids.addElement(var28.id);
            var28.arity = 1;
            var28.is_user_given = true;
            var28.complexity = 1;

            for(var31 = var30.substring(var30.indexOf("(") + 1, var30.indexOf(")")); var31.indexOf(",") >= 0; var31 = var31.substring(0, var17) + var31.substring(var17 + 1, var31.length())) {
                ++var28.arity;
                var17 = var31.indexOf(",");
            }

            var12.addElement(var31);
            var28.is_java_enabled = false;
            var28.is_object_of_interest_concept = true;
            Vector var32 = new Vector();

            for(var18 = 3; var18 < var10.size(); ++var18) {
                var19 = (String)var10.elementAt(var18);
                if (var19.indexOf("function:") == 0) {
                    var32.addElement(var19.substring(var19.indexOf(" ") + 1, var19.length()).trim());
                }

                if (var19.indexOf("->") >= 0) {
                    var28.is_object_of_interest_concept = false;
                }

                if (var19.indexOf("Code") == 0 || var19.indexOf("code") == 0) {
                    var28.is_java_enabled = true;
                }
            }

            Relation var34 = new Relation(var32);
            var36 = (Vector)var10.elementAt(2);

            for(var20 = 0; var20 < var36.size(); ++var20) {
                var21 = (String)var36.elementAt(var20);
                var22 = var21.substring(0, var21.indexOf(":"));
                String var23 = var21.substring(var21.indexOf(":") + 1, var21.length());
                if (var23.trim().equals("\"\"")) {
                    var23 = "";
                }

                var34.addDefinition(var31, var23, var22);
            }

            var34.name = var28.name;
            var9.addElement(var34);
            if (var28.is_object_of_interest_concept) {
                var8.addElement(var28.name);

                for(var20 = 3; var20 < var10.size(); ++var20) {
                    var21 = (String)var10.elementAt(var20);
                    if (var21.indexOf("function:") < 0 && var21.indexOf("->") < 0) {
                        while(var21.indexOf(").") >= 0) {
                            var22 = var21.substring(var21.indexOf("(") + 1, var21.indexOf(")."));
                            var5.addElement(var28.name + ":" + var22);
                            var21 = var21.substring(var21.indexOf(").") + 2, var21.length());
                        }
                    }
                }
            }
        }

        for(var27 = 0; var27 < var4.size(); ++var27) {
            var28 = (Concept)var4.elementAt(var27);
            var10 = (Vector)var11.elementAt(var27);
            var30 = (String)var12.elementAt(var27);

            for(var16 = 0; var16 < var28.arity; ++var16) {
                var28.types.addElement("bad");
            }

            var31 = "";
            String var35;
            int var37;
            String var38;
            if (var28.is_object_of_interest_concept) {
                var31 = var28.name;
                var28.types.setElementAt(var28.name, 0);
            } else {
                for(var17 = 3; var17 < var10.size(); ++var17) {
                    var35 = (String)var10.elementAt(var17);
                    if (var35.indexOf("->") >= 0) {
                        for(var37 = 0; var37 < var30.length(); ++var37) {
                            var38 = "(" + var30.substring(var37, var37 + 1) + ")";
                            if (var35.indexOf(var38) > 0) {
                                var21 = var35.substring(var35.indexOf("->") + 3, var35.lastIndexOf("("));
                                if (var37 == 0) {
                                    var31 = var21;
                                }

                                var28.types.setElementAt(var21, var37);
                            }

                            var21 = "(" + var30.substring(0, 1) + "," + var30.substring(var37, var37 + 1) + ")";
                            if (var35.lastIndexOf(var21) > var35.indexOf("->")) {
                                var22 = var35.substring(var35.indexOf("->") + 3, var35.lastIndexOf("("));
                                if (var28.types.elementAt(var37).equals("bad")) {
                                    var28.types.setElementAt(var22, var37);
                                }
                            }
                        }
                    }
                }
            }

            if (var28.types.size() == 2 && ((String)var28.types.elementAt(1)).equals("bad")) {
                var28.types.setElementAt(var28.name, 1);
            }

            var28.domain = (String)var28.types.elementAt(0);
            var28.object_type = (String)var28.types.elementAt(0);
            var28.user_functions = this.user_functions;
            Datatable var33 = new Datatable();

            for(var18 = 0; var18 < var5.size(); ++var18) {
                var19 = (String)var5.elementAt(var18);
                if (var19.indexOf(var31 + ":") == 0) {
                    var38 = var19.substring(var19.indexOf(":") + 1, var19.length());
                    var33.addEmptyRow(var38);
                }
            }

            if (!var28.is_java_enabled) {
                for(var18 = 3; var18 < var10.size(); ++var18) {
                    var19 = (String)var10.elementAt(var18);
                    if (var19.indexOf("function:") < 0 && var19.indexOf("->") < 0) {
                        while(var19.indexOf(").") >= 0) {
                            var38 = var19.substring(var19.indexOf("(") + 1, var19.indexOf(")."));
                            var19 = var19.substring(var19.indexOf(").") + 2, var19.length());

                            Vector var40;
                            for(var40 = new Vector(); var38.indexOf(",") > 0; var38 = var38.substring(var38.indexOf(",") + 1, var38.length())) {
                                var40.addElement(var38.substring(0, var38.indexOf(",")));
                            }

                            if (!var38.equals("")) {
                                var40.addElement(var38);
                            }

                            if (var40.size() > 1) {
                                var33.addTuple(var40);
                            } else {
                                Row var42 = var33.rowWithEntity((String)var40.elementAt(0));
                                var42.tuples.addElement(new Vector());
                            }
                        }
                    }
                }
            }

            if (var28.is_java_enabled) {
                for(var18 = 0; var18 < var33.size(); ++var18) {
                    Row var39 = (Row)var33.elementAt(var18);
                    var39.tuples = this.user_functions.calculateTuples(var28.id, var39.entity);
                }
            }

            var33.sort();
            var28.datatable = var33;
            var28.datatable.setNumberOfTuples();

            int var43;
            for(var18 = 3; var18 < var10.size(); ++var18) {
                var19 = (String)var10.elementAt(var18);
                if (var19.indexOf("->") >= 0) {
                    var38 = var19.substring(var19.lastIndexOf("(") + 1, var19.lastIndexOf(")"));
                    var21 = "[";

                    int var44;
                    for(var43 = 0; var43 < var38.length(); ++var43) {
                        var44 = var30.indexOf(var38.substring(var43, var43 + 1));
                        if (var44 >= 0) {
                            if (!var21.equals("[")) {
                                var21 = var21 + ",";
                            }

                            var21 = var21 + Integer.toString(var44);
                        }
                    }

                    var21 = var21 + "]";
                    var22 = var19.substring(var19.indexOf("->") + 3, var19.lastIndexOf("("));

                    for(var44 = 0; var44 < var9.size(); ++var44) {
                        Relation var24 = (Relation)var9.elementAt(var44);
                        if (var24.name.equals(var22)) {
                            Specification var25 = new Specification(var21, var24);
                            var28.specifications.addElement(var25);
                            var7.addElement(var25);
                        }
                    }
                }
            }

            var35 = "[";

            for(var37 = 0; var37 < var28.arity; ++var37) {
                var35 = var35 + Integer.toString(var37) + ",";
            }

            var35 = var35.substring(0, var35.length() - 1) + "]";

            Specification var45;
            for(var37 = 0; var37 < var9.size(); ++var37) {
                Relation var41 = (Relation)var9.elementAt(var37);
                if (var41.name.equals(var28.name)) {
                    var45 = new Specification(var35, var41);
                    var28.specifications.addElement(var45);
                    var7.addElement(var45);
                }
            }

            var28.setSkolemisedRepresentation();
            var36 = new Vector();

            for(var20 = 0; var20 < var28.specifications.size(); ++var20) {
                var45 = (Specification)var28.specifications.elementAt(var20);

                for(var43 = 0; var43 < var45.functions.size(); ++var43) {
                    Function var46 = (Function)var45.functions.elementAt(var43);
                    if (!var36.contains(var46.writeFunction())) {
                        var28.functions.addElement(var46);
                        var36.addElement(var46.writeFunction());
                    }
                }
            }
        }

        return var3;
    }
}
