package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class UserFunctions implements Serializable {
    public Maple maple = new Maple();

    public UserFunctions() {
    }

    public Tuples calculateTuples(String var1, String var2) {
        Maths var3 = new Maths();
        Tuples var4 = new Tuples();
        if (var1.equals("int001")) {
            var4.addElement(new Vector());
        }

        int var5;
        Vector var6;
        if (var1.equals("int002") && !var2.equals("0")) {
            for(var5 = 1; var5 <= new Integer(var2); ++var5) {
                var6 = new Vector();
                var6.addElement(Integer.toString(var5));
                var4.addElement(var6);
            }
        }

        int var8;
        double var9;
        Vector var11;
        Integer var30;
        double var31;
        Vector var35;
        if (var1.equals("int003") && !var2.equals("0")) {
            var30 = new Integer(var2);
            var31 = (double)var30;

            for(var8 = 1; (double)var8 <= var31 / 2.0D; ++var8) {
                var9 = var31 / (double)var8;
                if (var9 == Math.floor(var9)) {
                    var11 = new Vector();
                    var11.addElement(Integer.toString(var8));
                    var4.addElement(var11);
                }
            }

            var35 = new Vector();
            var35.addElement(var30.toString());
            var4.addElement(var35);
        }

        if (var1.equals("int019") && !var2.equals("0")) {
            var30 = new Integer(var2);
            var31 = (double)var30;

            for(var8 = 2; (double)var8 <= var31 / 2.0D; ++var8) {
                var9 = var31 / (double)var8;
                if (var9 == Math.floor(var9)) {
                    var11 = new Vector();
                    var11.addElement(Integer.toString(var8));
                    var4.addElement(var11);
                }
            }

            var35 = new Vector();
            var35.addElement(var30.toString());
            var4.addElement(var35);
        }

        Vector var7;
        if (var1.equals("int004")) {
            var5 = 0;

            for(var6 = new Vector(); var5 < var2.length(); ++var5) {
                if (!var6.contains(var2.substring(var5, var5 + 1))) {
                    var7 = new Vector();
                    var7.addElement(var2.substring(var5, var5 + 1));
                    var6.addElement(var2.substring(var5, var5 + 1));
                    var4.addElement(var7);
                }
            }
        }

        Vector var12;
        int var43;
        if (var1.equals("int005")) {
            var30 = new Integer(var2);
            var31 = (double)var30;

            for(var8 = 1; (double)var8 <= var31 / 2.0D; ++var8) {
                var9 = var31 / (double)var8;
                var43 = (int)var31 / var8;
                if (var9 == Math.floor(var9)) {
                    var12 = new Vector();
                    var12.addElement(Integer.toString(var8));
                    var12.addElement(Integer.toString(var43));
                    var4.addElement(var12);
                }
            }

            var35 = new Vector();
            var35.addElement(var30.toString());
            var35.addElement("1");
            var4.addElement(var35);
        }

        if (var1.equals("int020")) {
            var30 = new Integer(var2);
            var31 = (double)var30;

            for(var8 = 2; (double)var8 <= var31 / 2.0D; ++var8) {
                var9 = var31 / (double)var8;
                var43 = (int)var31 / var8;
                if (var9 == Math.floor(var9)) {
                    var12 = new Vector();
                    var12.addElement(Integer.toString(var8));
                    var12.addElement(Integer.toString(var43));
                    var4.addElement(var12);
                }
            }

            var35 = new Vector();
            var35.addElement(var30.toString());
            var35.addElement("1");
            var4.addElement(var35);
        }

        String var32;
        int var33;
        Tuples var39;
        String var44;
        if (var1.equals("int006")) {
            var5 = new Integer(var2);

            for(var33 = 1; var33 < var5; ++var33) {
                var32 = Integer.toString(var33);
                var44 = Integer.toString(var5 - var33);
                var39 = new Tuples();
                var39.addElement(var32);
                var39.addElement(var44);
                var4.addElement(var39);
            }
        }

        if (var1.equals("int018")) {
            var5 = new Integer(var2);

            for(var33 = 0; var33 <= var5; ++var33) {
                var32 = Integer.toString(var33);
                var44 = Integer.toString(var5 - var33);
                var39 = new Tuples();
                var39.addElement(var32);
                var39.addElement(var44);
                var4.addElement(var39);
            }
        }

        if (var1.equals("int007")) {
            var4 = var3.baseRepresentation(2, var2);
        }

        String[] var34;
        if (var1.equals("int008")) {
            var34 = new String[]{"numtheory"};
            var4 = this.maple.getOutput(2, var2, "tau", var34);
        }

        if (var1.equals("int009")) {
            var34 = new String[]{"numtheory"};
            var4 = this.maple.getOutput(2, var2, "sigma", var34);
        }

        if (var1.equals("int010")) {
            var34 = new String[]{"numtheory"};
            var4 = this.maple.getOutput(2, var2, "phi", var34);
        }

        if (var1.equals("int011") && !var2.equals("0") && !var2.equals("1") && !var2.equals("2")) {
            var34 = new String[]{"numtheory"};
            var4 = this.maple.getOutput(2, var2, "prevprime", var34);
        }

        if (var1.equals("int012")) {
            var34 = new String[]{"numtheory"};
            var4 = this.maple.getOutput(1, var2, "isprime", var34);
        }

        Tuples var37;
        if (var1.equals("int013")) {
            var34 = new String[]{"numtheory"};
            var37 = this.maple.getOutput(2, var2, "divisors", var34);
            if (var37.toString().indexOf("[" + Integer.toString(var37.size()) + "]") >= 0) {
                var7 = new Vector();
                var4.addElement(var7);
            }
        }

        if (var1.equals("int014")) {
            var34 = new String[]{"numtheory"};
            var4 = this.maple.getOutput(1, var2, "issqrfree", var34);
        }

        Vector var40;
        if (var1.equals("int015") && var3.isSquare(var2)) {
            var40 = new Vector();
            var4.addElement(var40);
        }

        if (var1.equals("int016") && var3.isOdd(var2)) {
            var40 = new Vector();
            var4.addElement(var40);
        }

        if (var1.equals("int017") && var3.isEven(var2)) {
            var40 = new Vector();
            var4.addElement(var40);
        }

        if (var1.equals("poly001")) {
            var4.addElement(new Vector());
        }

        String var38;
        if (var1.equals("poly002")) {
            var5 = 0;

            for(var38 = var2.substring(var5, var5 + 1); !var38.equals("["); var38 = var2.substring(var5, var5 + 1)) {
                var7 = new Vector();
                var7.addElement(var38);
                var4.addElement(var7);
                ++var5;
            }
        }

        int var36;
        Vector var42;
        boolean var45;
        if (var1.equals("poly003")) {
            if (var2.indexOf("[]") > -1) {
                return new Tuples();
            }

            var45 = var2.indexOf("[]") > -1;
            try {
                var33 = var2.substring(var2.indexOf("["), var2.indexOf("]")).length();
            } catch (StringIndexOutOfBoundsException e) {
                var33 = 0;
            }
            var36 = 1;

            for(var8 = 0; var8 < var33; var8 += 3) {
                var42 = new Vector();
                var42.addElement("e" + Integer.toString(var36++));
                var4.addElement(var42);
            }
        }

        if (var1.equals("poly004")) {
            if (var2.indexOf("[]") > -1) {
                return new Tuples();
            }

            var45 = var2.indexOf("[]") > -1;
            try {
                var33 = var2.substring(var2.indexOf("["), var2.indexOf("]")).length();
            } catch (StringIndexOutOfBoundsException e) {
                var33 = 0;
            }
            var36 = 1;

            for(var8 = 0; var8 < var33; var8 += 6) {
                var42 = new Vector();
                var42.addElement("f" + Integer.toString(var36++));
                var4.addElement(var42);
            }
        }

        if (var1.equals("poly009")) {
            var4.addElement(new Vector());
        }

        Vector var10;
        String var46;
        if (var1.equals("poly005")) {
            if (var2.indexOf("[]") > -1) {
                return new Tuples();
            }

            var5 = var2.substring(var2.indexOf("["), var2.indexOf("]")).length();
            var33 = 1;
            var36 = var2.indexOf("[") + 1;

            for(var8 = 0; var8 < var5; var8 += 3) {
                var42 = new Vector();
                var10 = new Vector();
                var46 = var2.substring(var36 + var8, var36 + var8 + 2);
                var42.addElement(var46.substring(0, 1));
                var42.addElement("e" + Integer.toString(var33));
                var10.addElement(var46.substring(1, 2));
                var10.addElement("e" + Integer.toString(var33));
                var4.addElement(var42);
                var4.addElement(var10);
                ++var33;
            }
        }

        if (var1.equals("poly006")) {
            var4.addElement(new Vector());
        }

        if (var1.equals("graph001")) {
            var4.addElement(new Vector());
        }

        if (var1.equals("graph001m")) {
            var34 = new String[]{"networks"};
            var4 = this.maple.getOutput(1, var2, "new", var34);
        }

        if (var1.equals("graph002")) {
            var5 = 0;

            for(var38 = var2.substring(var5, var5 + 1); !var38.equals("["); var38 = var2.substring(var5, var5 + 1)) {
                var7 = new Vector();
                var7.addElement(var38);
                var4.addElement(var7);
                ++var5;
            }
        }

        if (var1.equals("graph002m")) {
            var34 = new String[]{"networks"};
            var4 = this.maple.getOutput(2, var2, "addvertex", var34);
        }

        if (var1.equals("graph003")) {
            if (var2.indexOf("[]") > -1) {
                return new Tuples();
            }

            var5 = var2.substring(var2.indexOf("["), var2.indexOf("]")).length();
            var33 = 1;

            for(var36 = 0; var36 < var5; var36 += 3) {
                var35 = new Vector();
                var35.addElement("e" + Integer.toString(var33++));
                var4.addElement(var35);
            }
        }

        if (var1.equals("graph003m")) {
            var34 = new String[]{"networks"};
            var4 = this.maple.getOutput(2, var2, "connect", var34);
        }

        if (var1.equals("graph004")) {
            if (var2.indexOf("[]") > -1) {
                return new Tuples();
            }

            var5 = var2.substring(var2.indexOf("["), var2.indexOf("]")).length();
            var33 = 1;
            var36 = var2.indexOf("[") + 1;

            for(var8 = 0; var8 < var5; var8 += 3) {
                var42 = new Vector();
                var10 = new Vector();
                var46 = var2.substring(var36 + var8, var36 + var8 + 2);
                var42.addElement(var46.substring(0, 1));
                var42.addElement("e" + Integer.toString(var33));
                var10.addElement(var46.substring(1, 2));
                var10.addElement("e" + Integer.toString(var33));
                var4.addElement(var42);
                var4.addElement(var10);
                ++var33;
            }
        }

        String var58;
        if (var1.equals("graph005")) {
            var58 = this.maple.writeGraphForMaple(var2);
            String[] var53 = new String[]{"networks"};
            var4 = this.maple.getOutput(1, var58, "isplanar", var53);
        }

        if (var1.equals("algebra001") || var1.equals("group001") || var1.equals("abq001") || var1.equals("aq5001") || var1.equals("as6001") || var1.equals("naq5001") || var1.equals("nas6001") || var1.equals("am5001") || var1.equals("nam5001") || var1.equals("naq6001") || var1.equals("nas5001")) {
            var4.addElement(new Vector());
        }

        if (var1.equals("algebra002") || var1.equals("group002") || var1.equals("abq002") || var1.equals("aq5002") || var1.equals("as6002") || var1.equals("naq5002") || var1.equals("nas6002") || var1.equals("am5002") || var1.equals("nam5002") || var1.equals("naq6002") || var1.equals("nas5002")) {
            var5 = 1;

            for(var38 = "0123456789"; var5 * var5 <= var2.length(); ++var5) {
                var7 = new Vector();
                var7.addElement(var38.substring(var5 - 1, var5));
                var4.addElement(var7);
            }
        }

        String var13;
        String var14;
        Vector var15;
        int var41;
        int var47;
        int var48;
        if (var1.equals("algebra003") || var1.equals("algebra004") || var1.equals("group003") || var1.equals("abq003") || var1.equals("aq5003") || var1.equals("as6003") || var1.equals("naq5003") || var1.equals("nas6003") || var1.equals("am5003") || var1.equals("nam5003") || var1.equals("naq6003") || var1.equals("nas5003")) {
            var58 = "0123456789";
            var31 = new Double((double)var2.length());
            var8 = (new Double(Math.sqrt(var31))).intValue();
            var47 = 0;

            for(var41 = 1; var41 <= var8; ++var41) {
                var46 = var58.substring(var41 - 1, var41);

                for(var48 = 1; var48 <= var8; ++var48) {
                    var13 = var58.substring(var48 - 1, var48);
                    var14 = var2.substring(var47, var47 + 1);
                    ++var47;
                    var15 = new Vector();
                    var15.addElement(var46);
                    var15.addElement(var13);
                    var15.addElement(var14);
                    var4.addElement(var15);
                }
            }
        }

        String var50;
        Vector var51;
        if (var1.equals("algebra005") || var1.equals("group004")) {
            var58 = "0123456789";
            var31 = new Double((double)var2.length());
            var8 = (new Double(Math.sqrt(var31))).intValue();
            boolean var49 = false;

            for(var41 = 0; !var49 && var41 < var8; ++var41) {
                var46 = var58.substring(var41, var41 + 1);
                var50 = var2.substring(var41 * var8 + var41, var41 * var8 + var41 + 1);
                if (var46.equals(var50)) {
                    var49 = true;
                    var51 = new Vector();
                    var51.addElement(var46);
                    var4.addElement(var51);
                }
            }
        }

        if (var1.equals("group004a")) {
            var58 = this.getIdentity(var2);
            if (!var58.equals("no identity")) {
                var6 = new Vector();
                var6.addElement(var58);
                var4.addElement(var6);
            }
        }

        String var54;
        Vector var55;
        if (var1.equals("algebra006") || var1.equals("group005")) {
            var58 = "0123456789";
            var31 = new Double((double)var2.length());
            var8 = (new Double(Math.sqrt(var31))).intValue();
            var54 = "";

            for(var41 = 0; var54.equals("") && var41 < var8; ++var41) {
                var46 = var58.substring(var41, var41 + 1);
                var50 = var2.substring(var41 * var8 + var41, var41 * var8 + var41 + 1);
                if (var46.equals(var50)) {
                    var54 = var46;
                }
            }

            for(var41 = 0; var41 < var8; ++var41) {
                var46 = var58.substring(var41, var41 + 1);

                for(var48 = 0; var48 < var8; ++var48) {
                    var13 = var2.substring(var41 * var8 + var48, var41 * var8 + var48 + 1);
                    if (var13.equals(var54)) {
                        var55 = new Vector();
                        var55.addElement(var46);
                        var55.addElement(var58.substring(var48, var48 + 1));
                        var4.addElement(var55);
                    }
                }
            }
        }

        if (var1.equals("group005a")) {
            var58 = "0123456789";
            var31 = new Double((double)var2.length());
            var8 = (new Double(Math.sqrt(var31))).intValue();
            var54 = this.getIdentity(var2);
            boolean var52 = false;

            for(var41 = 0; var41 < var8; ++var41) {
                var46 = var58.substring(var41, var41 + 1);

                for(var48 = 0; var48 < var8; ++var48) {
                    var13 = var2.substring(var41 * var8 + var48, var41 * var8 + var48 + 1);
                    if (var13.equals(var54)) {
                        var55 = new Vector();
                        var55.addElement(var46);
                        var55.addElement(var58.substring(var48, var48 + 1));
                        var4.addElement(var55);
                    }
                }
            }
        }

        String var16;
        int var17;
        String var18;
        Vector var19;
        int var20;
        String var21;
        String var22;
        Vector var23;
        int var24;
        String var25;
        Vector var26;
        int var27;
        String var28;
        Vector var29;
        int var57;
        double var61;
        Tuples var62;
        if (var1.equals("group006")) {
            var62 = this.calculateTuples("group003", var2);
            var37 = this.calculateTuples("group005", var2);
            var32 = "0123456789";
            var61 = new Double((double)var2.length());
            var41 = (new Double(Math.sqrt(var61))).intValue();
            var43 = 0;

            for(var48 = 1; var48 <= var41; ++var48) {
                var13 = var32.substring(var48 - 1, var48);
                var55 = (Vector)var37.elementAt(0);

                for(var57 = 0; !((String)var55.elementAt(0)).equals(var13) && var57 < var37.size(); var55 = (Vector)var37.elementAt(var57)) {
                    ++var57;
                }

                var16 = (String)var55.elementAt(1);

                for(var17 = 1; var17 <= var41; ++var17) {
                    var18 = var32.substring(var17 - 1, var17);
                    var19 = (Vector)var37.elementAt(0);

                    for(var20 = 0; !((String)var19.elementAt(0)).equals(var18) && var20 < var37.size(); var19 = (Vector)var37.elementAt(var20)) {
                        ++var20;
                    }

                    var21 = (String)var19.elementAt(1);
                    var22 = var2.substring(var43, var43 + 1);
                    var23 = (Vector)var62.elementAt(0);

                    for(var24 = 0; (!((String)var23.elementAt(0)).equals(var22) || !((String)var23.elementAt(1)).equals(var16)) && var24 < var62.size(); var23 = (Vector)var62.elementAt(var24)) {
                        ++var24;
                    }

                    var25 = (String)var23.elementAt(2);
                    var26 = (Vector)var62.elementAt(0);

                    for(var27 = 0; (!((String)var26.elementAt(0)).equals(var25) || !((String)var26.elementAt(1)).equals(var21)) && var27 < var62.size(); var26 = (Vector)var62.elementAt(var27)) {
                        ++var27;
                    }

                    var28 = (String)var26.elementAt(2);
                    ++var43;
                    var29 = new Vector();
                    var29.addElement(var13);
                    var29.addElement(var18);
                    var29.addElement(var28);
                    var4.addElement(var29);
                }
            }
        }

        if (var1.equals("group006a")) {
            var62 = this.calculateTuples("group003", var2);
            var37 = this.calculateTuples("group005a", var2);
            var32 = "0123456789";
            var61 = new Double((double)var2.length());
            var41 = (new Double(Math.sqrt(var61))).intValue();
            var43 = 0;

            for(var48 = 1; var48 <= var41; ++var48) {
                var13 = var32.substring(var48 - 1, var48);
                var55 = (Vector)var37.elementAt(0);

                for(var57 = 0; !((String)var55.elementAt(0)).equals(var13) && var57 < var37.size(); var55 = (Vector)var37.elementAt(var57)) {
                    ++var57;
                }

                var16 = (String)var55.elementAt(1);

                for(var17 = 1; var17 <= var41; ++var17) {
                    var18 = var32.substring(var17 - 1, var17);
                    var19 = (Vector)var37.elementAt(0);

                    for(var20 = 0; !((String)var19.elementAt(0)).equals(var18) && var20 < var37.size(); var19 = (Vector)var37.elementAt(var20)) {
                        ++var20;
                    }

                    var21 = (String)var19.elementAt(1);
                    var22 = var2.substring(var43, var43 + 1);
                    var23 = (Vector)var62.elementAt(0);

                    for(var24 = 0; (!((String)var23.elementAt(0)).equals(var22) || !((String)var23.elementAt(1)).equals(var16)) && var24 < var62.size(); var23 = (Vector)var62.elementAt(var24)) {
                        ++var24;
                    }

                    var25 = (String)var23.elementAt(2);
                    var26 = (Vector)var62.elementAt(0);

                    for(var27 = 0; (!((String)var26.elementAt(0)).equals(var25) || !((String)var26.elementAt(1)).equals(var21)) && var27 < var62.size(); var26 = (Vector)var62.elementAt(var27)) {
                        ++var27;
                    }

                    var28 = (String)var26.elementAt(2);
                    ++var43;
                    var29 = new Vector();
                    var29.addElement(var13);
                    var29.addElement(var18);
                    var29.addElement(var28);
                    var4.addElement(var29);
                }
            }
        }

        String var56;
        boolean var59;
        String var60;
        if (var1.equals("group007")) {
            var62 = this.calculateTuples("group003", var2);
            var59 = true;

            for(var36 = 0; var36 < var62.size(); ++var36) {
                var35 = (Vector)var62.elementAt(var36);
                var54 = (String)var35.elementAt(0);
                var56 = (String)var35.elementAt(1);
                var46 = (String)var35.elementAt(2);

                for(var48 = 0; var48 < var62.size(); ++var48) {
                    var51 = (Vector)var62.elementAt(var48);
                    var14 = (String)var51.elementAt(0);
                    if (var14.equals(var46)) {
                        var60 = (String)var51.elementAt(1);
                        var16 = (String)var51.elementAt(2);

                        for(var17 = 0; var17 < var62.size(); ++var17) {
                            Vector var63 = (Vector)var62.elementAt(var17);
                            String var64 = (String)var63.elementAt(0);
                            if (var64.equals(var54)) {
                                for(var20 = 0; var20 < var62.size(); ++var20) {
                                    Vector var65 = (Vector)var62.elementAt(var20);
                                    var22 = (String)var65.elementAt(0);
                                    if (var22.equals(var56)) {
                                        String var66 = (String)var65.elementAt(1);
                                        if (var66.equals(var60)) {
                                            String var67 = (String)var65.elementAt(2);

                                            for(int var68 = 0; var68 < var62.size(); ++var68) {
                                                var26 = (Vector)var62.elementAt(var68);
                                                if (var26.elementAt(0).equals(var64) && var26.elementAt(1).equals(var67)) {
                                                    String var69 = (String)var26.elementAt(2);
                                                    if (!var69.equals(var16)) {
                                                        var59 = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (var59) {
                var4.addElement(new Vector());
            }
        }

        if (var1.equals("group008")) {
            var62 = this.calculateTuples("group003", var2);
            var59 = true;

            for(var36 = 0; var36 < var62.size(); ++var36) {
                var35 = (Vector)var62.elementAt(var36);
                var54 = (String)var35.elementAt(0);
                var56 = (String)var35.elementAt(1);
                var46 = (String)var35.elementAt(2);

                for(var48 = 0; var48 < var62.size(); ++var48) {
                    var51 = (Vector)var62.elementAt(var48);
                    var14 = (String)var51.elementAt(0);
                    var60 = (String)var51.elementAt(1);
                    var16 = (String)var51.elementAt(2);
                    if (var14.equals(var54) && var16.equals(var46) && !var60.equals(var56)) {
                        var59 = false;
                        break;
                    }
                }
            }

            if (var59) {
                var4.addElement(new Vector());
            }
        }

        if (var1.equals("algebra007")) {
            var5 = 1;

            for(var38 = "0123456789"; var5 * var5 <= var2.length(); ++var5) {
                var7 = new Vector();
                var7.addElement(var38.substring(var5 - 1, var5));
                var7.addElement(var38.substring(var5 - 1, var5));
                var4.addElement(var7);
            }
        }

        if (var1.equals("qg3001") || var1.equals("qg4001") || var1.equals("qg5001") || var1.equals("qg6001") || var1.equals("qg7001") || var1.equals("qg8001") || var1.equals("qg9001")) {
            var4.addElement(new Vector());
        }

        if (var1.equals("qg3002") || var1.equals("qg4002") || var1.equals("qg5002") || var1.equals("qg6002") || var1.equals("qg7002") || var1.equals("qg8002") || var1.equals("qg9002")) {
            var5 = 1;

            for(var38 = "abcdefghijklmnopqrstuvwxyz"; var5 * var5 <= var2.length(); ++var5) {
                var7 = new Vector();
                var7.addElement(var38.substring(var5 - 1, var5));
                var4.addElement(var7);
            }
        }

        if (var1.equals("qg3003") || var1.equals("qg4003") || var1.equals("qg5003") || var1.equals("qg6003") || var1.equals("qg7003") || var1.equals("qg8003") || var1.equals("qg9003")) {
            var58 = "abcdefghijklmnopqrstuvwxyz";
            var31 = new Double((double)var2.length());
            var8 = (new Double(Math.sqrt(var31))).intValue();
            var47 = 0;

            for(var41 = 1; var41 <= var8; ++var41) {
                var46 = var58.substring(var41 - 1, var41);

                for(var48 = 1; var48 <= var8; ++var48) {
                    var13 = var58.substring(var48 - 1, var48);
                    var14 = var2.substring(var47, var47 + 1);
                    ++var47;
                    var15 = new Vector();
                    var15.addElement(var46);
                    var15.addElement(var13);
                    var15.addElement(var14);
                    var4.addElement(var15);
                }
            }
        }

        var4.sort();
        return var4;
    }

    public String getIdentity(String var1) {
        String var2 = "0123456789";
        double var3 = new Double((double)var1.length());
        int var5 = (new Double(Math.sqrt(var3))).intValue();
        int var6 = 0;
        boolean var7 = false;
        int var8 = 200;
        int var9 = 0;

        String var10;
        Integer var11;
        while(var9 < var5) {
            var10 = var1.substring(var6 * var5 + var9, var6 * var5 + var9 + 1);
            var11 = new Integer(var9);
            if (var10.equals(var11.toString())) {
                var7 = true;
                ++var9;
            } else {
                if (var6 >= var5 - 1) {
                    var7 = false;
                    break;
                }

                ++var6;
                var9 = 0;
                var7 = false;
            }
        }

        if (var7) {
            var8 = var6;
        }

        if (var7) {
            var6 = 0;

            for(var9 = 0; var9 < var5; ++var9) {
                var10 = var1.substring(var8 + var6 * var5, var8 + var6 * var5 + 1);
                var11 = new Integer(var9);
                if (!var10.equals(var11.toString())) {
                    var7 = false;
                    break;
                }

                var7 = true;
                ++var6;
            }
        }

        if (!var7) {
            var8 = 200;
        }

        new String();
        String var13;
        if (var7 && var8 != 200) {
            Integer var12 = new Integer(var8);
            var13 = var12.toString();
        } else {
            var13 = "no identity";
        }

        return var13;
    }
}
