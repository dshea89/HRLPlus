package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.*;
import java.awt.List;

/** A class for handling the storage of objects and retrieval of objects in files.
 *
 * @author Simon Colton, started 3rd January 2000
 * @version 1.0 */

public class StorageHandler implements Serializable
{
    /** The vector of pairs (condition_string, object_stream). Each object created
     * in the theory is tested against the condition string, and if it passes, is
     * written to the object stream.
     */

    public Vector object_streams = new Vector();

    /** The pseudo-code interpreter for this storage handler.
     */

    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();

    /** The guide object (inherited from the theory).
     */

    public Guide guider = new Guide();

    /** The directory for where the objects are going to be stored.
     */

    public String storage_directory = "";

    /** The reflection object (inherited from the theory).
     */

    public Reflection reflect = new Reflection();

    /** This stores the given object in the given file.
     */

    public void storeObject(Object obj, String filename)
    {
        try
        {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(storage_directory+filename));
            o.writeObject(obj);
        }
        catch(Exception e)
        {
        }
    }

    /** This retrieves an object from a file.
     */

    public Object retrieveObject(String filename)
    {
        try
        {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(storage_directory+filename));
            Object obj = o.readObject();
            return obj;
        }
        catch(Exception e)
        {
        }
        return null;
    }

    /** This retrieves an object from a file and adds it to the given Vector.
     */

    public void retrieveObject(String filename, Vector v)
    {
        v.addElement(retrieveObject(filename));
    }

    /** This retrieves an object from a file and adds it to the given Vector, and
     * adds the id of the object (if it is a theoryconstituent) it to the given List.
     */

    public void retrieveObject(String filename, Vector v, List l)
    {
        Object obj = retrieveObject(filename);
        v.addElement(obj);
        if (obj instanceof TheoryConstituent)
            l.addItem(((TheoryConstituent)obj).id);
    }

    /** This opens a file with construction details in it, and carries out those
     * theory formation steps.
     */

    public void buildConstructionFromFile(String filename, Theory theory)
    {
        Vector step_strings = new Vector();
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(storage_directory+filename));
            String step_string = in.readLine();
            step_strings.addElement(step_string);
            while (step_string!=null)
            {
                step_string = in.readLine();
                if (step_string!=null)
                    step_strings.addElement(step_string);
            }
            in.close();
        }
        catch(Exception e)
        {
            System.out.println("problem with file: " + storage_directory+filename);
        }
        pseudo_code_interpreter.local_alias_hashtable = new Hashtable();
        pseudo_code_interpreter.local_alias_hashtable.put("theory", theory);
        pseudo_code_interpreter.local_alias_hashtable.put("this", theory.guider);
        pseudo_code_interpreter.input_text_box = theory.front_end.pseudo_code_input_text;
        pseudo_code_interpreter.output_text_box = theory.front_end.pseudo_code_output_text;
        pseudo_code_interpreter.runPseudoCode(step_strings);
    }

    /** This writes the construction history of an object (concept, conjecture, etc.)
     * to the given file.
     */

    public void writeConstructionToFile(String filename, Object object)
    {
        if (object instanceof Concept)
            writeConstructionToFile(filename, (Concept)object);
        if (object instanceof Equivalence)
            writeConstructionToFile(filename, (Equivalence)object);
        if(object instanceof NearEquivalence) //alisonp
            writeConstructionToFile(filename, (NearEquivalence)object);
    }

    /** This writes a string to a file, which is <i>not</i> opened for appending.
     */

    public void writeStringToFile(String filename, Object object)
    {
        writeStringToFile(filename, object, false);
    }

    /** This writes a string to a file.
     */

    public void writeStringToFile(String filename, Object object, boolean append)
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(storage_directory+filename, append)));
            if (object instanceof Vector)
            {
                Vector v = (Vector)object;
                for (int i=0; i<v.size(); i++)
                {
                    out.write(v.elementAt(i).toString() + "\n");
                }
            }
            else
                out.write(object.toString());
            out.close();
        }
        catch(Exception e)
        {
        }
    }

    /** This writes the construction to a file, strictly <i>not</i> appending.
     */

    public void writeConstructionToFile(String filename, Concept concept)
    {
        writeConstructionToFile(filename, concept, false);
    }

    /** This writes a construction to a file.
     */

    public void writeConstructionToFile(String filename, Concept concept, boolean append)
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(storage_directory+filename, append)));
            for (int i=0; i<concept.ancestors.size(); i++)
            {
                Concept ancestor = (Concept)concept.ancestors.elementAt(i);
                if (!ancestor.is_user_given)
                {
                    String construction_string = getConstructionString(ancestor);
                    out.write("forceStep(\"" + construction_string + "\", theory);\n");
                }
            }
            out.write("forceStep(\"" + getConstructionString(concept) + "\", theory);\n");
            out.close();
        }
        catch(Exception e){System.out.println(e);}
    }

    /** This works out the construction steps required to reconstruct a concept.
     */

    public String getConstructionString(Concept concept)
    {
        String output = concept.id;
        if (!concept.is_user_given)
            output = output + "*";
        output = output + " = ";
        for (int i=0; i<concept.parents.size(); i++)
        {
            Concept parent = (Concept)concept.parents.elementAt(i);
            String parent_id = parent.id;
            if (!parent.is_user_given)
                parent_id = parent_id + "*";
            output = output + parent_id + " ";
        }
        output = output + concept.construction.productionRule().getName() + " ";
        output = output + removeSpaces(concept.construction.parameters().toString());
        return output;
    }

    /** This write the construction history of an equivalence conjecture to
     * a file.
     */

    public void writeConstructionToFile(String filename, Equivalence equiv)
    {
        writeConstructionToFile(filename, equiv.lh_concept, false);
        writeConstructionToFile(filename, equiv.rh_concept, true);
        String lh_conc_id = equiv.lh_concept.id;
        if (!equiv.lh_concept.is_user_given)
            lh_conc_id = lh_conc_id + "*";
        String rh_conc_id = equiv.rh_concept.id;
        if (!equiv.rh_concept.is_user_given)
            rh_conc_id = rh_conc_id + "*";
        writeCommandToFile(filename, "action(construct_equivalence," + lh_conc_id + "," + rh_conc_id  + "," + equiv.id + "*)", true);
    }

    /** This write the construction history of an near_equivalence conjecture to
     * a file. alisonp
     */

    public void writeConstructionToFile(String filename, NearEquivalence nequiv)
    {
        writeConstructionToFile(filename, nequiv.lh_concept, false);
        writeConstructionToFile(filename, nequiv.rh_concept, true);
        String lh_conc_id = nequiv.lh_concept.id;
        if (!nequiv.lh_concept.is_user_given)
            lh_conc_id = lh_conc_id + "*";
        String rh_conc_id = nequiv.rh_concept.id;
        if (!nequiv.rh_concept.is_user_given)
            rh_conc_id = rh_conc_id + "*";
        writeCommandToFile(filename, "action(construct_near_equivalence," + lh_conc_id + "," + rh_conc_id  + "," + nequiv.id + "*)", true);
    }


    /** This writes a command (string) to a file.
     */

    public void writeCommandToFile(String filename, String command, boolean append)
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(storage_directory+filename, append)));
            out.write(command + "\n");
            out.close();
        }
        catch(Exception e)
        {
        }
    }

    private String removeSpaces(String in)
    {
        String output = "";
        for (int i=0; i<in.length(); i++)
        {
            String s = in.substring(i,i+1);
            if (!s.equals(" "))
                output = output + s;
        }
        return output;
    }

    /** This returns an object of the same type as the object which was
     * input, but with fewer details, so that the representation is slimmer,
     * in order to store it more easily.
     */

    public Object slimRepresentation(Object obj)
    {
        if (obj instanceof Implicate)
        {
            Implicate imp = (Implicate)obj;
            Concept premise = new Concept();
            premise.specifications = imp.premise_concept.specifications;
            premise.types = imp.premise_concept.types;
            Implicate slim_implicate = new Implicate();
            slim_implicate.premise_concept = premise;
            slim_implicate.goal = imp.goal;
            slim_implicate.proof_status = imp.proof_status;
            slim_implicate.counterexamples = imp.counterexamples;
            slim_implicate.proof = imp.proof;
            return slim_implicate;
        }
        return obj;
    }

    /** This expands a given object in object-specific ways.
     */

    public Object expandedObject(Object obj)
    {
        return obj;
    }

    /** This opens a file and reads in all the objects in that file.
     */

    public Vector retrieveAllSlim(String filename)
    {
        Vector output = new Vector();
        try
        {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(storage_directory+filename));
            boolean end_of_file = false;
            while (!end_of_file)
            {
                try
                {
                    Object obj = o.readObject();
                    output.addElement(expandedObject(obj));
                }
                catch(Exception e)
                {
                    end_of_file = true;
                    o.close();
                }
            }
            return output;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    /** This retrieves an object in its slim representation from a file,
     * then performs various object-specific routines to build the object
     * back up.
     */

    public Object retrieveSlim(String filename)
    {
        Object obj = retrieveObject(filename);
        return expandedObject(obj);
    }

    /** This opens an object stream and puts it in the hashtable so that
     * it can be retrieved from the filename.
     */

    public boolean openStorageStream(String condition, String filename)
    {
        if (filename.equals(""))
            return true;

        for (int i=0; i<object_streams.size(); i++)
        {
            Vector triple = (Vector)object_streams.elementAt(i);
            String old_filename = (String)triple.elementAt(2);
            if (old_filename.equals(filename))
            {
                ObjectOutputStream o = (ObjectOutputStream)triple.elementAt(1);
                Vector new_triple = new Vector();
                new_triple.addElement(condition);
                new_triple.addElement(o);
                new_triple.addElement(filename);
                object_streams.addElement(new_triple);
                return true;
            }
        }
        try
        {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(storage_directory+filename));
            Vector new_triple = new Vector();
            new_triple.addElement(condition);
            new_triple.addElement(o);
            new_triple.addElement(filename);
            object_streams.addElement(new_triple);
        }
        catch(Exception e){return false;}
        return true;
    }

    /** This closes all the open streams.
     */

    public void closeObjectStreams()
    {
        for (int i=0; i<object_streams.size(); i++)
        {
            Vector pair = (Vector)object_streams.elementAt(i);
            ObjectOutputStream o = (ObjectOutputStream)pair.elementAt(1);
            try
            {
                o.close();
            }
            catch(Exception e){}
        }
        Enumeration en = object_streams.elements();
        while (en.hasMoreElements())
        {
            ObjectOutputStream s = (ObjectOutputStream)en.nextElement();
            try
            {
                s.close();
            }
            catch(Exception e){}
        }
    }

    /** This handles the storage of a given object. i.e., it writes it to
     * every open stream for which it passes the conditions associated with
     * that file.
     */

    public void handleStorageOf(Object obj)
    {
        for (int i=0; i<object_streams.size(); i++)
        {
            Vector pair = (Vector)object_streams.elementAt(i);
            String condition = (String)pair.elementAt(0);
            if (reflect.checkCondition(obj, condition))
            {
                try
                {
                    ObjectOutputStream o = (ObjectOutputStream)pair.elementAt(1);
                    Object slim_obj = slimRepresentation(obj);
                    o.writeObject(slim_obj);
                }
                catch(Exception e){}
            }
        }
    }
}
