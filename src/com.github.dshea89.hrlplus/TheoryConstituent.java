package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class TheoryConstituent implements Serializable {
    public long when_constructed = 0L;
    public String id = "";

    public TheoryConstituent() {
    }

    public String decimalPlaces(int var1, int var2) {
        return this.decimalPlaces(new Double((double)var1), var2);
    }

    public String decimalPlaces(double var1, int var3) {
        String var4 = Double.toString(var1);
        if (!var4.equals("Inf") && !var4.equals("Infinity")) {
            try {
                int var5 = var4.length() - var4.indexOf(".") - 1;
                if (var5 == var3) {
                    return var4;
                }

                if (var5 < var3) {
                    for(int var6 = 0; var6 < var3 - var5; ++var6) {
                        var4 = var4 + "0";
                    }
                }

                if (var5 > var3) {
                    var4 = var4.substring(0, var4.indexOf(".") + var3 + 2);
                    String var15 = var4.substring(var4.length() - 2, var4.length() - 1);
                    int var7 = new Integer(var15);
                    if (var7 < 5) {
                        var4 = var4.substring(0, var4.length() - 1);
                    } else {
                        String var8 = var4.substring(var4.indexOf(".") + 1, var4.length());
                        String var9 = Integer.toString(10 - var7);
                        String var10 = this.APlusB(var8, var9);
                        if (var10.length() == var8.length()) {
                            var4 = var4.substring(0, var4.indexOf(".") + 1) + var10.substring(0, var10.length() - 1);
                        } else {
                            String var11 = var4.substring(0, var4.indexOf("."));
                            int var12 = new Integer(var11) + 1;
                            var4 = Integer.toString(var12) + ".";

                            for(int var13 = 0; var13 < var3; ++var13) {
                                var4 = var4 + "0";
                            }
                        }
                    }
                }
            } catch (Exception var14) {
                ;
            }

            return var4;
        } else {
            return "Infinity";
        }
    }

    public String APlusB(String var1, String var2) {
        if (this.compareIntegers(var1, var2) < 0) {
            return this.APlusB(var2, var1);
        } else {
            String var3 = "";
            byte var4 = 0;

            int var5;
            for(var5 = var1.length() - var2.length() - 1; var5 >= 0; --var5) {
                var2 = "0" + var2;
            }

            for(var5 = var2.length(); var5 > 0; --var5) {
                String var6 = var2.substring(var5 - 1, var5);
                String var7 = var1.substring(var5 - 1, var5);
                int var8 = new Integer(var6);
                int var9 = new Integer(var7);
                String var10 = Integer.toString(new Integer(var8 + var9 + var4));
                var4 = 0;
                if (var10.length() == 2) {
                    var4 = 1;
                }

                var10 = var10.substring(var10.length() - 1, var10.length());
                var3 = var10 + var3;
            }

            if (var4 == 1) {
                var3 = "1" + var3;
            }

            return var3;
        }
    }

    public int compareIntegers(String var1, String var2) {
        if (var1.equals(var2)) {
            return 0;
        } else {
            boolean var3 = false;
            boolean var4 = false;
            if (var1.substring(0, 1).equals("-")) {
                var3 = true;
            }

            if (var2.substring(0, 1).equals("-")) {
                var4 = true;
            }

            if (var3 && !var4) {
                return -1;
            } else if (!var3 && var4) {
                return 1;
            } else if (var1.length() < var2.length() && !var3) {
                return -1;
            } else if (var1.length() < var2.length() && var3) {
                return 1;
            } else if (var1.length() > var2.length() && !var3) {
                return 1;
            } else if (var1.length() > var2.length() && var3) {
                return -1;
            } else {
                if (var3) {
                    var1 = var1.substring(1, var1.length());
                }

                if (var4) {
                    var2 = var2.substring(1, var2.length());
                }

                int var5 = 0;

                int var6;
                for(var6 = 0; var6 == 0 && var5 < var1.length(); ++var5) {
                    var6 = var1.substring(var5, var5 + 1).compareTo(var2.substring(var5, var5 + 1));
                }

                if (var3 && var4) {
                    if (var6 > 1) {
                        var6 = -1;
                    } else {
                        var6 = 1;
                    }
                }

                return var6;
            }
        }
    }

    public String replaceLTForHTML(String var1) {
        String var2 = "";

        for(int var3 = 0; var3 < var1.length(); ++var3) {
            if (var1.substring(var3, var3 + 1).equals("<")) {
                var2 = var2 + "&lt;";
            } else {
                var2 = var2 + var1.substring(var3, var3 + 1);
            }
        }

        return var2;
    }

    public String writeTheoryConstituent() {
        if (this instanceof Conjecture) {
            return ((Conjecture)this).writeConjecture();
        } else if (this instanceof Concept) {
            return ((Concept)this).writeDefinition();
        } else if (this instanceof Entity) {
            return ((Entity)this).toString();
        } else {
            return this instanceof ProofScheme ? ((ProofScheme)this).writeProofScheme() : "";
        }
    }

    public boolean equals(String var1) {
        return this.writeTheoryConstituent().equals(var1);
    }

    public boolean equals(Object var1) {
        if (var1 instanceof String) {
            return this.equals((String)var1);
        } else {
            System.out.println("Warning: TheoryConstituent equals method - not a String");
            return false;
        }
    }
}
