package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Sequence extends Vector implements Serializable {
    public int cycle_length = 0;
    public int hill_offset = 0;
    public int common_difference = 0;
    public double common_ratio = 0.0D;
    public int top_integer = 0;
    public int bottom_integer = 0;
    public String name = "";
    public String definition = "";

    public Sequence() {
    }

    public Sequence(String var1) {
        try {
            int var2 = var1.indexOf(",");
            String var3 = ",";
            if (var2 < 0) {
                var3 = " ";
            }

            String var4 = "";

            for(int var5 = 0; var5 < var1.length(); ++var5) {
                String var6 = var1.substring(var5, var5 + 1);
                if (!var6.equals(var3)) {
                    var4 = var4 + var6;
                }

                if (var6.equals(var3)) {
                    this.addElement(var4);
                    int var7 = new Integer(var4);
                    if (var7 > this.top_integer) {
                        this.top_integer = var7;
                    }

                    var4 = "";
                }
            }

            if (!var4.equals("")) {
                int var9 = new Integer(var4);
                if (var9 > this.top_integer) {
                    this.top_integer = var9;
                }

                this.addElement(var4);
            }

            this.bottom_integer = new Integer((String)this.elementAt(0));
        } catch (Exception var8) {
            System.out.println("problem with: " + var1);
        }

    }

    public Vector asCategorisation(boolean var1) {
        Vector var2 = new Vector();
        Vector var3 = new Vector();
        Vector var4 = new Vector();
        int var5 = this.bottom_integer;
        if (var1) {
            var5 = 1;
        }

        int var6;
        for(var6 = var5; var6 <= this.top_integer; ++var6) {
            String var7 = Integer.toString(var6);
            int var8 = this.indexOf(var7);
            if (var8 > -1) {
                var3.addElement(var7);
            } else {
                var4.addElement(var7);
            }
        }

        var2.addElement(var3);
        var6 = new Integer((String)var3.elementAt(0));
        if (!var4.isEmpty()) {
            int var9 = new Integer((String)var4.elementAt(0));
            if (var6 < var9) {
                var2.addElement(var4);
            } else {
                var2.insertElementAt(var4, 0);
            }
        }

        return var2;
    }

    public Concept asNumberTypeConcept() {
        Concept var1 = new Concept();

        for(int var2 = this.bottom_integer; var2 <= this.top_integer; ++var2) {
            String var3 = Integer.toString(var2);
            Row var4 = new Row();
            var4.entity = var3;
            int var5 = this.indexOf(var3);
            if (var5 > -1) {
                var4.tuples.addElement(new Vector());
            }

            var1.datatable.addElement(var4);
            var1.types.addElement("integer");
            var1.arity = 1;
        }

        return var1;
    }

    public Sequence reversed() {
        Sequence var1 = new Sequence();

        for(int var2 = this.size() - 1; var2 >= 0; --var2) {
            String var3 = (String)this.elementAt(var2);
            int var4 = new Integer(var3);
            if (var4 > var1.top_integer) {
                var1.top_integer = var4;
            }

            var1.addElement(var3);
        }

        return var1;
    }

    public String asString(boolean var1) {
        String var2 = "";
        String var3 = "";
        if (var1) {
            var3 = ",";
        } else {
            var3 = " ";
        }

        for(int var4 = 0; var4 < this.size(); ++var4) {
            var2 = var2 + (String)this.elementAt(var4);
            if (var4 < this.size() - 1) {
                var2 = var2 + var3;
            }
        }

        return var2;
    }

    public int range() {
        Vector var1 = (Vector)this.clone();
        int var2 = 0;

        while(var1.size() > 0) {
            String var3 = (String)var1.elementAt(0);
            var1.removeElementAt(0);
            if (!var1.contains(var3)) {
                ++var2;
            }
        }

        return var2;
    }

    public boolean isSubsequenceOf(Sequence var1) {
        for(int var2 = 0; var2 < this.size(); ++var2) {
            if (!var1.contains((String)this.elementAt(var2))) {
                return false;
            }
        }

        return true;
    }

    public int termOverlap(Sequence var1) {
        int var2 = 0;
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < this.size(); ++var4) {
            String var5 = (String)this.elementAt(var4);
            if (var1.contains(var5) && !var3.contains(var5)) {
                ++var2;
                var3.addElement(var5);
            }
        }

        return var2;
    }

    public boolean isIncreasing() {
        if (this.size() < 2) {
            return false;
        } else {
            int var1 = new Integer((String)this.elementAt(0));

            for(int var2 = 1; var2 < this.size(); ++var2) {
                int var3 = new Integer((String)this.elementAt(var2));
                if (var3 <= var1) {
                    return false;
                }

                if (var3 > var1) {
                    var1 = var3;
                }
            }

            return true;
        }
    }

    public boolean isDecreasing() {
        if (this.size() < 2) {
            return false;
        } else {
            int var1 = new Integer((String)this.elementAt(0));

            for(int var2 = 1; var2 < this.size(); ++var2) {
                int var3 = new Integer((String)this.elementAt(var2));
                if (var3 >= var1) {
                    return false;
                }

                if (var3 < var1) {
                    var1 = var3;
                }
            }

            return true;
        }
    }

    public boolean isNonDecreasing() {
        int var1 = new Integer((String)this.elementAt(0));

        for(int var2 = 1; var2 < this.size(); ++var2) {
            int var3 = new Integer((String)this.elementAt(var2));
            if (var3 < var1) {
                return false;
            }

            if (var3 > var1) {
                var1 = var3;
            }
        }

        return true;
    }

    public boolean isGolomb() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.size() - 1; ++var2) {
            int var3 = new Integer((String)this.elementAt(var2));

            for(int var4 = var2 + 1; var4 < this.size(); ++var4) {
                int var5 = new Integer((String)this.elementAt(var4));
                if (var1.contains(Integer.toString(var5 - var3))) {
                    return false;
                }

                var1.addElement(Integer.toString(var5 - var3));
            }
        }

        return true;
    }

    public boolean isAP() {
        if (this.size() < 2) {
            return false;
        } else {
            int var1 = new Integer((String)this.elementAt(0));
            int var2 = new Integer((String)this.elementAt(1));
            int var3 = var2 - var1;
            int var4 = 1;

            boolean var5;
            for(var5 = true; var5 && var4 < this.size() - 1; ++var4) {
                var1 = new Integer((String)this.elementAt(var4));
                var2 = new Integer((String)this.elementAt(var4 + 1));
                if (var2 - var1 != var3) {
                    var5 = false;
                }
            }

            if (var5) {
                this.common_difference = var3;
            }

            return var5;
        }
    }

    public boolean isGP() {
        if (this.size() < 2) {
            return false;
        } else {
            double var1 = new Double((String)this.elementAt(0));
            double var3 = new Double((String)this.elementAt(1));
            double var5 = var3 / var1;
            int var7 = 1;

            boolean var8;
            for(var8 = true; var8 && var7 < this.size() - 1; ++var7) {
                var1 = new Double((String)this.elementAt(var7));
                var3 = new Double((String)this.elementAt(var7 + 1));
                if (var3 / var1 != var5) {
                    var8 = false;
                }
            }

            if (var8) {
                this.common_ratio = var5;
            }

            return var8;
        }
    }

    public boolean isCyclic() {
        int var1 = 0;
        boolean var2 = false;

        while(var1 < this.size() && !var2) {
            ++var1;
            var2 = true;

            for(int var3 = 0; var2 && var3 < this.size() - var1; ++var3) {
                String var4 = (String)this.elementAt(var3);
                String var5 = (String)this.elementAt(var3 + var1);
                if (!var4.equals(var5)) {
                    var2 = false;
                }
            }
        }

        if (var2 && var1 < this.size()) {
            this.cycle_length = var1;
            return true;
        } else {
            var2 = false;
            double var9 = (double)(this.size() / 2);
            int var10 = (new Double(var9)).intValue() - 1;

            while(var10 < this.size() && !var2) {
                ++var10;
                int var6 = 1;

                for(var2 = true; var2 && var10 - var6 >= 0 && var10 + var6 < this.size(); ++var6) {
                    String var7 = (String)this.elementAt(var10 - var6);
                    String var8 = (String)this.elementAt(var10 + var6);
                    if (!var7.equals(var8)) {
                        var2 = false;
                    }
                }
            }

            if (var2 && var10 < this.size() - 1) {
                this.hill_offset = var10;
                this.cycle_length = (var10 + 1) * 2;
                return true;
            } else {
                return false;
            }
        }
    }

    public Sequence differenceSequence() {
        Sequence var1 = new Sequence();

        for(int var2 = 1; var2 < this.size(); ++var2) {
            int var3 = new Integer((String)this.elementAt(var2));
            int var4 = new Integer((String)this.elementAt(var2 - 1));
            int var5 = var3 - var4;
            if (var5 > var1.top_integer) {
                var1.top_integer = var5;
            }

            if (var5 < 0) {
                var5 = 0 - var5;
            }

            var1.addElement(Integer.toString(var5));
        }

        return var1;
    }

    public Sequence recordSequence(Concept var1) {
        Sequence var2 = new Sequence();
        int var3 = -1000000;

        for(int var4 = 0; var4 < var1.datatable.size(); ++var4) {
            Row var5 = (Row)var1.datatable.elementAt(var4);
            if (!var5.tuples.isEmpty()) {
                Vector var6 = (Vector)var5.tuples.elementAt(0);
                int var7 = new Integer((String)var6.elementAt(0));
                if (var7 > var3) {
                    var3 = var7;
                    var2.addElement(var5.entity);
                    if (var2.size() == 1) {
                        var2.bottom_integer = new Integer(var5.entity);
                    }

                    var2.top_integer = new Integer(var5.entity);
                }
            }
        }

        return var2;
    }
}
