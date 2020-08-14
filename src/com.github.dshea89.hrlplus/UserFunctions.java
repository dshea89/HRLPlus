package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.Serializable;

/** A class where all the user defined concept calculations are stored.
 * Note that these can also appeal to external processes. At present, the
 * only external program available is Maplev3.
 *
 * @author Simon Colton, started 31st August 2001
 * @version 1.0 */

public class UserFunctions implements Serializable
{
    /** The maple handler for the users to employ.
     */

    public Maple maple = new Maple();

    public Tuples calculateTuples(String concept_id, String entity)
    {
        Maths maths = new Maths();
        Tuples output = new Tuples();

        if (concept_id.equals("int001"))
        {
            //System.out.println("got int001");
            output.addElement(new Vector());
        }

        if (concept_id.equals("int002"))
        {
            if (!entity.equals("0"))
            {
                for(int i=1;i<=(new Integer(entity)).intValue();i++)
                {
                    Vector tuple = new Vector();
                    tuple.addElement(Integer.toString(i));
                    output.addElement(tuple);
                }
            }
        }

        if (concept_id.equals("int003"))
        {
            if (!entity.equals("0"))
            {
                Integer i = new Integer(entity);
                double i2=i.intValue();
                for(int j=1;j<=i2/2;j++)
                {
                    double k=i2/j;
                    if(k==Math.floor(k))
                    {
                        Vector tuple = new Vector();
                        tuple.addElement(Integer.toString(j));
                        output.addElement(tuple);
                    }
                }
                Vector tuple = new Vector();
                tuple.addElement(i.toString());
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("int019"))
        {
            if (!entity.equals("0"))
            {
                Integer i = new Integer(entity);
                double i2=i.intValue();
                for(int j=2;j<=i2/2;j++)
                {
                    double k=i2/j;
                    if(k==Math.floor(k))
                    {
                        Vector tuple = new Vector();
                        tuple.addElement(Integer.toString(j));
                        output.addElement(tuple);
                    }
                }
                Vector tuple = new Vector();
                tuple.addElement(i.toString());
                output.addElement(tuple);
            }
        }



        if (concept_id.equals("int004"))
        {
            int i=0;
            Vector digits = new Vector();
            while (i<entity.length())
            {
                if (!digits.contains(entity.substring(i,i+1)))
                {
                    Vector tuple = new Vector();
                    tuple.addElement(entity.substring(i,i+1));
                    digits.addElement(entity.substring(i,i+1));
                    output.addElement(tuple);
                }
                i++;
            }
        }

        if (concept_id.equals("int005"))
        {
            Integer i = new Integer(entity);
            double i2=i.intValue();
            for(int j=1;j<=i2/2;j++)
            {
                double k = i2/j;
                int k2 = (int)i2/j;
                if(k==Math.floor(k))
                {
                    Vector tuple = new Vector();
                    tuple.addElement(Integer.toString(j));
                    tuple.addElement(Integer.toString(k2));
                    output.addElement(tuple);
                }
            }
            Vector tuple = new Vector();
            tuple.addElement(i.toString());
            tuple.addElement("1");
            output.addElement(tuple);
        }


        if (concept_id.equals("int020"))
        {
            Integer i = new Integer(entity);
            double i2=i.intValue();
            for(int j=2;j<=i2/2;j++)
            {
                double k = i2/j;
                int k2 = (int)i2/j;
                if(k==Math.floor(k))
                {
                    Vector tuple = new Vector();
                    tuple.addElement(Integer.toString(j));
                    tuple.addElement(Integer.toString(k2));
                    output.addElement(tuple);
                }
            }
            Vector tuple = new Vector();
            tuple.addElement(i.toString());
            tuple.addElement("1");
            output.addElement(tuple);
        }


        if (concept_id.equals("int006"))
        {
            int i = (new Integer(entity)).intValue();
            for(int j=1;j<i;j++)
            {
                String js = Integer.toString(j);
                String js2 = Integer.toString(i-j);
                Tuples tuple = new Tuples();
                tuple.addElement(js);
                tuple.addElement(js2);
                output.addElement(tuple);
            }
        }


        if (concept_id.equals("int018"))
        {
            int i = (new Integer(entity)).intValue();
            for(int j=0;j<=i;j++)
            {
                String js = Integer.toString(j);
                String js2 = Integer.toString(i-j);
                Tuples tuple = new Tuples();
                tuple.addElement(js);
                tuple.addElement(js2);
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("int007"))
        {
            output = maths.baseRepresentation(2,entity);
        }

        if (concept_id.equals("int008"))
        {
            String[] packages = {"numtheory"};
            output = maple.getOutput(2, entity,"tau",packages);
        }

        if (concept_id.equals("int009"))
        {
            String[] packages = {"numtheory"};
            output = maple.getOutput(2, entity,"sigma",packages);
        }

        if (concept_id.equals("int010"))
        {
            String[] packages = {"numtheory"};
            output = maple.getOutput(2, entity,"phi",packages);
        }

        if (concept_id.equals("int011"))
        {
            if (!entity.equals("0") && !entity.equals("1") && !entity.equals("2"))
            {
                String[] packages = {"numtheory"};
                output = maple.getOutput(2, entity,"prevprime",packages);
            }
        }

        if (concept_id.equals("int012"))
        {
            String[] packages = {"numtheory"};
            output = maple.getOutput(1, entity,"isprime",packages);
        }

        if (concept_id.equals("int013"))
        {
            String[] packages = {"numtheory"};
            Tuples t = maple.getOutput(2,entity,"divisors",packages);
            if (t.toString().indexOf("[" + Integer.toString(t.size()) + "]") >= 0)
            {
                Vector tuple = new Vector();
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("int014"))
        {
            String[] packages = {"numtheory"};
            output = maple.getOutput(1, entity,"issqrfree",packages);
        }

        if (concept_id.equals("int015"))
        {
            if (maths.isSquare(entity))
            {
                Vector tuple = new Vector();
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("int016"))
        {
            if (maths.isOdd(entity))
            {
                Vector tuple = new Vector();
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("int017"))
        {
            if (maths.isEven(entity))
            {
                Vector tuple = new Vector();
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("poly001"))
        {
            output.addElement(new Vector());
        }

        if (concept_id.equals("poly002"))
        {
            int i = 0;
            String node = entity.substring(i,i+1);
            while (!node.equals("["))
            {
                Vector tuple = new Vector();
                tuple.addElement(node);
                output.addElement(tuple);
                i++;
                node = entity.substring(i,i+1);
            }
        }

        if (concept_id.equals("poly003"))
        {
            //System.out.println("entity is " + entity);
            if (entity.indexOf("[]")>-1)
                return new Tuples();
            boolean b = entity.indexOf("[]")>-1;
            //System.out.println("entity.indexOf(\"[]\")>-1 is " + b);
            //System.out.println("entity.substring(entity.indexOf(\"[\"), entity.indexOf(\"]\")) is " +
            //entity.substring(entity.indexOf("["), entity.indexOf("]")));
            int thrice_num_edges = 0;
            try {
                thrice_num_edges = entity.substring(entity.indexOf("["), entity.indexOf("]")).length();
            } catch (StringIndexOutOfBoundsException ignored) {
            }
            //System.out.println("thrice_num_edges is " + thrice_num_edges);
            int j = 1;
            for (int i=0; i<thrice_num_edges; i=i+3)
            {
                Vector tuple = new Vector();
                //System.out.println("1) tuple is " + tuple);
                tuple.addElement("e"+Integer.toString(j++));
                //System.out.println("2) tuple is now " + tuple);
                output.addElement(tuple);
                //System.out.println("3) output is " + output);
            }
        }

        if (concept_id.equals("poly004"))
        {
            // System.out.println("just calculating the faces");//here
            if (entity.indexOf("[]")>-1)
                return new Tuples();
            boolean b = entity.indexOf("[]")>-1;
            //System.out.println("entity.indexOf(\"[]\")>-1 is " + b);
            // System.out.println("entity.substring(entity.indexOf(\"[\"), entity.indexOf(\"]\")) is " +
            //	       entity.substring(entity.indexOf("["), entity.indexOf("]")));
            int six_times_num_faces = 0;
            try {
                six_times_num_faces = entity.substring(entity.indexOf("["), entity.indexOf("]")).length();
            } catch (StringIndexOutOfBoundsException ignored) {
            }
            //System.out.println("six_times_num_faces is " + six_times_num_faces);
            int j = 1;
            for (int i=0; i<six_times_num_faces; i=i+6)
            {
                Vector tuple = new Vector();
                //System.out.println("1) tuple is " + tuple);
                tuple.addElement("f"+Integer.toString(j++));
                // System.out.println("2) tuple is now " + tuple);
                output.addElement(tuple);
                // System.out.println("3) output is " + output);
            }
        }

        if (concept_id.equals("poly009"))
        {
            output.addElement(new Vector());
        }


        if (concept_id.equals("poly005"))
        {
            if (entity.indexOf("[]")>-1) return new Tuples();
            int thrice_num_edges = entity.substring(entity.indexOf("["), entity.indexOf("]")).length();
            int j = 1;
            int offset = entity.indexOf("[") + 1;
            for (int i=0; i<thrice_num_edges; i=i+3)
            {
                Vector tuple1 = new Vector();
                Vector tuple2 = new Vector();
                String pair = entity.substring(offset+(i),offset+(i)+2);
                tuple1.addElement(pair.substring(0,1));
                tuple1.addElement("e"+Integer.toString(j));
                tuple2.addElement(pair.substring(1,2));
                tuple2.addElement("e"+Integer.toString(j));
                output.addElement(tuple1);
                output.addElement(tuple2);
                j++;
            }
        }

        if (concept_id.equals("poly006"))
        {
            output.addElement(new Vector());
        }


        if (concept_id.equals("graph001"))
        {
            //System.out.println("got graph001");
            output.addElement(new Vector());
        }

        if (concept_id.equals("graph001m")) //?
        {
            String[] packages = {"networks"};
            output = maple.getOutput(1, entity,"new",packages);
        }

        if (concept_id.equals("graph002"))
        {
            //System.out.println("got graph002");
            int i = 0;
            String node = entity.substring(i,i+1);
            while (!node.equals("["))
            {
                Vector tuple = new Vector();
                tuple.addElement(node);
                output.addElement(tuple);
                i++;
                node = entity.substring(i,i+1);
            }
        }

        if (concept_id.equals("graph002m"))
        {
            String[] packages = {"networks"};
            output = maple.getOutput(2, entity,"addvertex",packages);
        }

        if (concept_id.equals("graph003"))
        {
            //System.out.println("got graph003");
            if (entity.indexOf("[]")>-1)
                return new Tuples();
            int thrice_num_edges = entity.substring(entity.indexOf("["), entity.indexOf("]")).length();
            int j = 1;
            for (int i=0; i<thrice_num_edges; i=i+3)
            {
                Vector tuple = new Vector();
                tuple.addElement("e"+Integer.toString(j++));
                output.addElement(tuple);
            }
        }

        if (concept_id.equals("graph003m"))
        {
            String[] packages = {"networks"};
            output = maple.getOutput(2, entity,"connect",packages);
        }


        if (concept_id.equals("graph004"))
        {
            //System.out.println("got graph004");
            if (entity.indexOf("[]")>-1) return new Tuples();
            int thrice_num_edges = entity.substring(entity.indexOf("["), entity.indexOf("]")).length();
            int j = 1;
            int offset = entity.indexOf("[") + 1;
            for (int i=0; i<thrice_num_edges; i=i+3)
            {
                Vector tuple1 = new Vector();
                Vector tuple2 = new Vector();
                String pair = entity.substring(offset+(i),offset+(i)+2);
                tuple1.addElement(pair.substring(0,1));
                tuple1.addElement("e"+Integer.toString(j));
                tuple2.addElement(pair.substring(1,2));
                tuple2.addElement("e"+Integer.toString(j));
                output.addElement(tuple1);
                output.addElement(tuple2);
                j++;
            }
        }

        if (concept_id.equals("graph005"))
        {
            //System.out.println("got graph005");
            String entity_for_maple = maple.writeGraphForMaple(entity);
            //System.out.println("entity_for_maple is " + entity_for_maple);
            String[] packages = {"networks"};
            output = maple.getOutput(1, entity_for_maple,"isplanar",packages);
        }

        if (concept_id.equals("algebra001") ||
                concept_id.equals("group001") ||
                concept_id.equals("abq001") ||
                concept_id.equals("aq5001") ||
                concept_id.equals("as6001") ||
                concept_id.equals("naq5001") ||
                concept_id.equals("nas6001") ||
                concept_id.equals("am5001") ||
                concept_id.equals("nam5001") ||
                concept_id.equals("naq6001") ||
                concept_id.equals("nas5001"))

        {
            output.addElement(new Vector());
        }

        if (concept_id.equals("algebra002") ||
                concept_id.equals("group002") ||
                concept_id.equals("abq002") ||
                concept_id.equals("aq5002") ||
                concept_id.equals("as6002") ||
                concept_id.equals("naq5002") ||
                concept_id.equals("nas6002") ||
                concept_id.equals("am5002") ||
                concept_id.equals("nam5002") ||
                concept_id.equals("naq6002") ||
                concept_id.equals("nas5002"))
        {
            int i = 1;
            String all_elements = "0123456789";
            while (i*i<=entity.length())
            {
                Vector tuple = new Vector();
                tuple.addElement(all_elements.substring(i-1,i));
                output.addElement(tuple);
                i++;
            }
        }

        if (concept_id.equals("algebra003") ||
                concept_id.equals("algebra004") ||
                concept_id.equals("group003") ||
                concept_id.equals("abq003") ||
                concept_id.equals("aq5003") ||
                concept_id.equals("as6003") ||
                concept_id.equals("naq5003") ||
                concept_id.equals("nas6003") ||
                concept_id.equals("am5003") ||
                concept_id.equals("nam5003") ||
                concept_id.equals("naq6003") ||
                concept_id.equals("nas5003"))
        {
            String all_elements = "0123456789";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            int pos = 0;
            for (int i=1; i<=size; i++)
            {
                String a = all_elements.substring(i-1,i);
                for (int j=1; j<=size; j++)
                {
                    String b = all_elements.substring(j-1,j);
                    String ab = entity.substring(pos,pos+1);
                    pos++;
                    Vector tuple = new Vector();
                    tuple.addElement(a);
                    tuple.addElement(b);
                    tuple.addElement(ab);
                    output.addElement(tuple);
                }
            }
        }

        if (concept_id.equals("algebra005") ||
                concept_id.equals("group004"))
        {
            String all_elements = "0123456789";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            boolean found = false;
            int i=0;
            while (!found && i<size)
            {
                String a = all_elements.substring(i,i+1);
                String aa = entity.substring(i*size+i,(i*size+i)+1);
                if (a.equals(aa))
                {
                    found = true;
                    Vector tuple = new Vector();
                    tuple.addElement(a);
                    output.addElement(tuple);
                }
                i++;
            }
        }


        if (concept_id.equals("group004a"))
        {
            String id_string = getIdentity(entity);
            if(!(id_string.equals("no identity")))
            {
                Vector tuple = new Vector();
                tuple.addElement(id_string);
                output.addElement(tuple);
            }
        }


        if (concept_id.equals("algebra006") ||
                concept_id.equals("group005"))
        {
            String all_elements = "0123456789";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            String identity = "";
            int i=0;
            while (identity.equals("") && i<size)
            {
                String a = all_elements.substring(i,i+1);
                String aa = entity.substring(i*size+i,((i*size)+i)+1);
                if (a.equals(aa))
                    identity=a;
                i++;
            }
            for (i=0; i<size; i++)
            {
                String a = all_elements.substring(i,i+1);
                for (int j=0; j<size; j++)
                {
                    String possid = entity.substring(i*size+j,((i*size)+j)+1);
                    if (possid.equals(identity))
                    {
                        Vector tuple = new Vector();
                        tuple.addElement(a);
                        tuple.addElement(all_elements.substring(j,j+1));
                        output.addElement(tuple);
                    }
                }
            }
        }

        if (concept_id.equals("group005a"))
        {
            String all_elements = "0123456789";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            String identity = getIdentity(entity);
            int i=0;

            for (i=0; i<size; i++)
            {
                String a = all_elements.substring(i,i+1);
                for (int j=0; j<size; j++)
                {
                    String possid = entity.substring(i*size+j,((i*size)+j)+1);
                    if (possid.equals(identity))
                    {
                        Vector tuple = new Vector();
                        tuple.addElement(a);
                        tuple.addElement(all_elements.substring(j,j+1));
                        output.addElement(tuple);
                    }
                }
            }
        }


        if (concept_id.equals("group006"))
        {
            Tuples mults = calculateTuples("group003", entity);
            Tuples invs = calculateTuples("group005", entity);
            String all_elements = "0123456789";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            int pos = 0;
            for (int i=1; i<=size; i++)
            {
                String a = all_elements.substring(i-1,i);
                Vector inv_vector_a = (Vector)invs.elementAt(0);
                int inv_pos_a=0;
                while (!((String)inv_vector_a.elementAt(0)).equals(a) && inv_pos_a<invs.size())
                {
                    inv_pos_a++;
                    inv_vector_a = (Vector)invs.elementAt(inv_pos_a);
                }
                String inv_a = (String)inv_vector_a.elementAt(1);
                for (int j=1; j<=size; j++)
                {
                    String b = all_elements.substring(j-1,j);
                    Vector inv_vector_b = (Vector)invs.elementAt(0);
                    int inv_pos_b=0;
                    while (!((String)inv_vector_b.elementAt(0)).equals(b) && inv_pos_b<invs.size())
                    {
                        inv_pos_b++;
                        inv_vector_b = (Vector)invs.elementAt(inv_pos_b);
                    }
                    String inv_b = (String)inv_vector_b.elementAt(1);
                    String ab = entity.substring(pos,pos+1);

                    Vector mult_vector1 = (Vector)mults.elementAt(0);
                    int mult_pos1 = 0;
                    while (!(((String)mult_vector1.elementAt(0)).equals(ab)
                            && ((String)mult_vector1.elementAt(1)).equals(inv_a)) && mult_pos1 < mults.size())
                    {
                        mult_pos1++;
                        mult_vector1 = (Vector)mults.elementAt(mult_pos1);
                    }
                    String abinv_a = (String)mult_vector1.elementAt(2);

                    Vector mult_vector2 = (Vector)mults.elementAt(0);
                    int mult_pos2 = 0;
                    while (!(((String)mult_vector2.elementAt(0)).equals(abinv_a)
                            && ((String)mult_vector2.elementAt(1)).equals(inv_b)) && mult_pos2 < mults.size())
                    {
                        mult_pos2++;
                        mult_vector2 = (Vector)mults.elementAt(mult_pos2);
                    }
                    String abinv_ainv_b = (String)mult_vector2.elementAt(2);

                    pos++;
                    Vector tuple = new Vector();
                    tuple.addElement(a);
                    tuple.addElement(b);
                    tuple.addElement(abinv_ainv_b);
                    output.addElement(tuple);
                }
            }
        }

        //this doesn't work
        if (concept_id.equals("group006a"))
        {
            Tuples mults = calculateTuples("group003", entity);
            Tuples invs = calculateTuples("group005a", entity);
            String all_elements = "0123456789";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            int pos = 0;
            for (int i=1; i<=size; i++)
            {
                String a = all_elements.substring(i-1,i);
                Vector inv_vector_a = (Vector)invs.elementAt(0);
                int inv_pos_a=0;
                while (!((String)inv_vector_a.elementAt(0)).equals(a) && inv_pos_a<invs.size())
                {
                    inv_pos_a++;
                    inv_vector_a = (Vector)invs.elementAt(inv_pos_a);
                }
                String inv_a = (String)inv_vector_a.elementAt(1);
                for (int j=1; j<=size; j++)
                {
                    String b = all_elements.substring(j-1,j);
                    Vector inv_vector_b = (Vector)invs.elementAt(0);
                    int inv_pos_b=0;
                    while (!((String)inv_vector_b.elementAt(0)).equals(b) && inv_pos_b<invs.size())
                    {
                        inv_pos_b++;
                        inv_vector_b = (Vector)invs.elementAt(inv_pos_b);
                    }
                    String inv_b = (String)inv_vector_b.elementAt(1);
                    String ab = entity.substring(pos,pos+1);

                    Vector mult_vector1 = (Vector)mults.elementAt(0);
                    int mult_pos1 = 0;
                    while (!(((String)mult_vector1.elementAt(0)).equals(ab)
                            && ((String)mult_vector1.elementAt(1)).equals(inv_a)) && mult_pos1 < mults.size())
                    {
                        mult_pos1++;
                        mult_vector1 = (Vector)mults.elementAt(mult_pos1);
                    }
                    String abinv_a = (String)mult_vector1.elementAt(2);

                    Vector mult_vector2 = (Vector)mults.elementAt(0);
                    int mult_pos2 = 0;
                    while (!(((String)mult_vector2.elementAt(0)).equals(abinv_a)
                            && ((String)mult_vector2.elementAt(1)).equals(inv_b)) && mult_pos2 < mults.size())
                    {
                        mult_pos2++;
                        mult_vector2 = (Vector)mults.elementAt(mult_pos2);
                    }
                    String abinv_ainv_b = (String)mult_vector2.elementAt(2);

                    pos++;
                    Vector tuple = new Vector();
                    tuple.addElement(a);
                    tuple.addElement(b);
                    tuple.addElement(abinv_ainv_b);
                    output.addElement(tuple);
                }
            }
        }

        //may need more testing
        if (concept_id.equals("group007"))
        {
            Tuples mults = calculateTuples("group003", entity);
            boolean associative = true;

            for(int i=0; i<mults.size(); i++)
            {
                Vector tuple1 = (Vector)mults.elementAt(i);
                String a = (String)tuple1.elementAt(0);
                String b = (String)tuple1.elementAt(1);
                String a_times_b = (String)tuple1.elementAt(2);

                for(int j=0; j<mults.size();j++)
                {
                    Vector other_tuple1 = (Vector)mults.elementAt(j);
                    String first_in_tuple = (String)other_tuple1.elementAt(0);
                    if(first_in_tuple.equals(a_times_b))
                    {
                        String c = (String)other_tuple1.elementAt(1);
                        String a_times_b___times_c = (String)other_tuple1.elementAt(2);

                        for(int k=0; k<mults.size(); k++)
                        {
                            Vector tuple2 = (Vector)mults.elementAt(k);
                            String a2 = (String)tuple2.elementAt(0);

                            if(a2.equals(a))
                            {
                                for(int l=0; l<mults.size(); l++)
                                {
                                    Vector other_tuple2 = (Vector)mults.elementAt(l);
                                    String b2 = (String)other_tuple2.elementAt(0);
                                    if(b2.equals(b))
                                    {
                                        String c2 = (String)other_tuple2.elementAt(1);
                                        if(c2.equals(c))
                                        {
                                            String b_times_c = (String)other_tuple2.elementAt(2);

                                            for(int m=0; m<mults.size(); m++)
                                            {
                                                Vector tuple = (Vector)mults.elementAt(m);
                                                if(tuple.elementAt(0).equals(a2) && tuple.elementAt(1).equals(b_times_c))
                                                {
                                                    String a_times___b_times_c = (String)tuple.elementAt(2);

                                                    if(!(a_times___b_times_c.equals(a_times_b___times_c)))
                                                    {
                                                        associative = false;
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
            if(associative)
                output.addElement(new Vector());
        }


        //test here
        if (concept_id.equals("group008"))
        {
            //System.out.println("Thursday -- testing group008");
            Tuples mults = calculateTuples("group003", entity);
            boolean left_hand_cancellation = true;

            for(int i=0; i<mults.size(); i++)
            {
                Vector tuple1 = (Vector)mults.elementAt(i);
                //System.out.println("\n\ngot tuple1 " + tuple1);
                String a = (String)tuple1.elementAt(0);
                String b = (String)tuple1.elementAt(1);
                String c = (String)tuple1.elementAt(2);
                for(int j=0; j<mults.size(); j++)
                {
                    Vector tuple2 = (Vector)mults.elementAt(j);
                    //System.out.println("got tuple2 " + tuple2);
                    String a1 = (String)tuple2.elementAt(0);
                    String b1= (String)tuple2.elementAt(1);
                    String c1 = (String)tuple2.elementAt(2);
                    if(a1.equals(a) && c1.equals(c))
                    {
                        //System.out.println("a1 is " + a1 + " and a is " + a + " and a1.equals(a) is "
                        //				       + a1.equals(a));

                        //System.out.println("b1 is " + b1 + " and b is " + b + " and b1.equals(b) is "
                        //	       + b1.equals(b));
                        //System.out.println("c1 is " + c1 + " and c is " + c + " and c1.equals(c) is "
                        //	       + c1.equals(c));

                        //  System.out.println("a1 = a and c1 = c");

                        if(!(b1.equals(b)))
                        {
                            //	System.out.println("in here -- but b1 is not =b");
                            left_hand_cancellation = false;
                            break;
                        }
                    }
                }

            }

            if(left_hand_cancellation)
                output.addElement(new Vector());

//	System.out.println("Thursday -- finished testing group008 -- left_hand_cancellation is "
            //   + left_hand_cancellation);
        }

        if (concept_id.equals("algebra007"))
        {
            int i = 1;
            String all_elements = "0123456789";
            while (i*i<=entity.length())
            {
                Vector tuple = new Vector();
                tuple.addElement(all_elements.substring(i-1,i));
                tuple.addElement(all_elements.substring(i-1,i));
                output.addElement(tuple);
                i++;
            }
        }

        if (concept_id.equals("qg3001") ||
                concept_id.equals("qg4001") ||
                concept_id.equals("qg5001") ||
                concept_id.equals("qg6001") ||
                concept_id.equals("qg7001") ||
                concept_id.equals("qg8001") ||
                concept_id.equals("qg9001"))
        {
            output.addElement(new Vector());
        }

        if (concept_id.equals("qg3002") ||
                concept_id.equals("qg4002") ||
                concept_id.equals("qg5002") ||
                concept_id.equals("qg6002") ||
                concept_id.equals("qg7002") ||
                concept_id.equals("qg8002") ||
                concept_id.equals("qg9002"))
        {
            int i = 1;
            String all_elements = "abcdefghijklmnopqrstuvwxyz";
            while (i*i<=entity.length())
            {
                Vector tuple = new Vector();
                tuple.addElement(all_elements.substring(i-1,i));
                output.addElement(tuple);
                i++;
            }
        }

        if (concept_id.equals("qg3003") ||
                concept_id.equals("qg4003") ||
                concept_id.equals("qg5003") ||
                concept_id.equals("qg6003") ||
                concept_id.equals("qg7003") ||
                concept_id.equals("qg8003") ||
                concept_id.equals("qg9003"))
        {
            String all_elements = "abcdefghijklmnopqrstuvwxyz";
            double l = (new Double(entity.length())).doubleValue();
            int size = (new Double(Math.sqrt(l))).intValue();
            int pos = 0;
            for (int i=1; i<=size; i++)
            {
                String a = all_elements.substring(i-1,i);
                for (int j=1; j<=size; j++)
                {
                    String b = all_elements.substring(j-1,j);
                    String ab = entity.substring(pos,pos+1);
                    pos++;
                    Vector tuple = new Vector();
                    tuple.addElement(a);
                    tuple.addElement(b);
                    tuple.addElement(ab);
                    output.addElement(tuple);
                }
            }
        }
        output.sort();
        return output;
    }

    /** finds the identity of a group entity. If there is no identity,
     * returns "no identity" */
    public String getIdentity(String entity)
    {
        String all_elements = "0123456789";
        double l = (new Double(entity.length())).doubleValue();

        int size = (new Double(Math.sqrt(l))).intValue();
        int n=0;
        boolean oksofar = false;
        int identity = 200;

        for(int i=0;i<size;)
        {
            String a = entity.substring(n*size+i,(n*size+i)+1);
            Integer integer_i = new Integer(i);
            if(a.equals(integer_i.toString()))
            {
                oksofar = true;
                i++;
            }

            else
            {
                if(n<(size-1))
                {
                    n++;
                    i=0;
                    oksofar = false;
                }
                else
                {
                    oksofar = false;
                    break;
                }
            }
        }
        if (oksofar)
            identity = n;

        if(oksofar)
        {
            n=0;
            for(int i=0;i<size;i++)
            {
                String a = entity.substring(identity+(n*size),(identity+(n*size))+1);
                Integer integer_i = new Integer(i);
                if(a.equals(integer_i.toString()))
                {
                    oksofar = true;
                    n++;
                }
                else
                {
                    oksofar = false;

                    break;
                }
            }
        }

        if(!oksofar)
            identity = 200;


        String id_string = new String();
        if(oksofar && !(identity==200))
        {
            Integer integer_id = new Integer(identity);
            id_string = integer_id.toString();
        }
        else
            id_string = "no identity";

        return id_string;
    }
}
