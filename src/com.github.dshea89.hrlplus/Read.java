package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.*;

/** A class for reading input files in HR's format
 *
 * @author Simon Colton, started 29th August 2001
 * @version 1.0 */

public class Read implements Serializable
{
    public UserFunctions user_functions = new UserFunctions();

    public Vector defaultDetails(String domain_name)
    {
        return new Vector();
    }

    public Vector readFile(String domain_file_name, String replace_algebra_with)
    {
        Vector output = new Vector();
        Vector concepts = new Vector();
        Vector objects_of_interest = new Vector();
        Vector axioms = new Vector();
        Vector specifications = new Vector();
        Vector object_types = new Vector();
        Vector relations = new Vector();
        //Vector proof_schemes = new Vector();//alisonp
        output.addElement(concepts);
        output.addElement(objects_of_interest);
        output.addElement(axioms);
        output.addElement(specifications);
        output.addElement(object_types);
        output.addElement(relations);
        // output.addElement(proof_schemes);//alisonp

        Vector concept_vector = new Vector();
        Vector concept_vectors = new Vector();
        Vector definition_letters_vector = new Vector();

        /* Read the blocks of text in from the file into seperate vectors,
         * one for each concept */

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(domain_file_name));
            String s = in.readLine();
            if (!replace_algebra_with.equals(""))
            {
                for (int i=0; i<s.length()-7; i++)
                {
                    if (s.substring(i, i+7).equals("algebra"))
                        s = s.substring(0,i) + replace_algebra_with + s.substring(i+7, s.length());
                }
            }
            Vector definitions_vector = new Vector();
            while (!(s==null))
            {
                if (s.trim().equals(""))
                {
                    if (!concept_vector.isEmpty())
                    {
                        concept_vector.insertElementAt(definitions_vector.clone(),2);
                        definitions_vector = new Vector();
                        concept_vectors.addElement(concept_vector.clone());
                    }
                    concept_vector = new Vector();
                }
                else
                {
                    if (!s.substring(0,1).equals("%"))
                    {
                        if (s.indexOf(":\"\"")>=0 || s.indexOf("@")>=0)
                            definitions_vector.addElement(s);
                        else
                            concept_vector.addElement(s);
                    }
                }
                s = in.readLine();
                if (!replace_algebra_with.equals(""))
                {
                    for (int i=0; i<s.length()-6; i++)
                    {
                        if (s.substring(i, i+7).equals("algebra"))
                            s = s.substring(0,i) + replace_algebra_with + s.substring(i+7, s.length());
                    }
                }
            }
            if (!concept_vector.isEmpty())
            {
                concept_vector.insertElementAt(definitions_vector.clone(),2);
                definitions_vector = new Vector();
                concept_vectors.addElement(concept_vector.clone());
            }
            in.close();
        }
        catch (Exception ex){}

        /* Put the skeleton concepts together and find the specifications
         * and objects of interest */

        for (int i=0; i<concept_vectors.size(); i++)
        {
            concept_vector = (Vector)concept_vectors.elementAt(i);
            Concept new_concept = new Concept();
            concepts.addElement(new_concept);

            String concept_name = (String)concept_vector.elementAt(1);
            new_concept.name = concept_name.substring(0,concept_name.indexOf("("));
            new_concept.id = (String)concept_vector.elementAt(0);
            new_concept.ancestor_ids.addElement(new_concept.id);
            new_concept.arity = 1;
            new_concept.is_user_given = true;
            new_concept.complexity = 1;

            String definition_letters =
                    concept_name.substring(concept_name.indexOf("(")+1,concept_name.indexOf(")"));

            /* Get the letters in the definition and update the arity */

            while (definition_letters.indexOf(",")>=0)
            {
                new_concept.arity++;
                int pos = definition_letters.indexOf(",");
                definition_letters =
                        definition_letters.substring(0,pos)+definition_letters.substring(pos+1,definition_letters.length());
            }
            definition_letters_vector.addElement(definition_letters);

            /* Get the function statements (and while there, see if this is an object of interest concept) */
            /* Also, check to see whether this concept is live (has Java code attached) */

            new_concept.is_java_enabled = false;
            new_concept.is_object_of_interest_concept = true;
            Vector function_vector = new Vector();
            for (int j=3; j<concept_vector.size(); j++)
            {
                String line = (String)concept_vector.elementAt(j);
                if (line.indexOf("function:")==0)
                    function_vector.addElement(line.substring(line.indexOf(" ")+1,line.length()).trim());
                if (line.indexOf("->")>=0)
                    new_concept.is_object_of_interest_concept = false;
                if (line.indexOf("Code")==0 ||
                        line.indexOf("code")==0)
                    new_concept.is_java_enabled = true;
            }

            /* Put together the new relation */

            Relation new_relation = new Relation(function_vector);
            Vector definitions_vector = (Vector)concept_vector.elementAt(2);

            for (int j=0; j<definitions_vector.size(); j++)
            {
                String def_line = (String)definitions_vector.elementAt(j);
                String language = def_line.substring(0,def_line.indexOf(":"));
                String def = def_line.substring(def_line.indexOf(":")+1,def_line.length());
                if (def.trim().equals("\"\""))
                    def = "";
                new_relation.addDefinition(definition_letters, def, language);
            }
            new_relation.name = new_concept.name;
            relations.addElement(new_relation);

            /* Extract the objects of interest */

            if (new_concept.is_object_of_interest_concept)
            {
                object_types.addElement(new_concept.name);
                for (int j=3; j<concept_vector.size(); j++)
                {
                    String line = (String)concept_vector.elementAt(j);
                    if (line.indexOf("function:")<0 && line.indexOf("->")<0)
                    {
                        while (line.indexOf(").")>=0)
                        {
                            String object_of_interest = line.substring(line.indexOf("(")+1,line.indexOf(")."));
                            objects_of_interest.addElement(new_concept.name+":"+object_of_interest);
                            line = line.substring(line.indexOf(").")+2,line.length());
                        }
                    }
                }
            }
        }

        /* Now go through and fill in the details of the concepts */

        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            concept_vector = (Vector)concept_vectors.elementAt(i);

            String definition_letters = (String)definition_letters_vector.elementAt(i);

            /* First find out the name of the object of interest concept in this concept */
            /* And find the types while we're there */

            for (int j=0; j<concept.arity; j++)
                concept.types.addElement("bad");
            String object_of_interest_name = "";
            if (concept.is_object_of_interest_concept)
            {
                object_of_interest_name = concept.name;
                concept.types.setElementAt(concept.name,0);
            }
            else
            {
                for (int j=3; j<concept_vector.size(); j++)
                {
                    String line = (String)concept_vector.elementAt(j);
                    if (line.indexOf("->")>=0)
                    {
                        for (int k=0; k<definition_letters.length(); k++)
                        {
                            String single_check = "("+definition_letters.substring(k,k+1)+")";
                            if (line.indexOf(single_check) > 0)
                            {
                                String type = line.substring(line.indexOf("->")+3,line.lastIndexOf("("));
                                if (k==0)
                                    object_of_interest_name = type;
                                concept.types.setElementAt(type,k);
                            }
                            String pair_check = "(" + definition_letters.substring(0,1) + "," +
                                    definition_letters.substring(k,k+1)+")";
                            if (line.lastIndexOf(pair_check) > line.indexOf("->"))
                            {
                                String type = line.substring(line.indexOf("->")+3,line.lastIndexOf("("));
                                if (concept.types.elementAt(k).equals("bad"))
                                    concept.types.setElementAt(type,k);
                            }
                        }
                    }
                }
            }

            if (concept.types.size()==2 && ((String)concept.types.elementAt(1)).equals("bad"))
                concept.types.setElementAt(concept.name,1);

            concept.domain = (String)concept.types.elementAt(0);
            concept.object_type = (String)concept.types.elementAt(0);
            concept.user_functions = user_functions;

            /* Construct an empty datatable */

            Datatable new_datatable = new Datatable();

            for (int j=0; j<objects_of_interest.size(); j++)
            {
                String object_of_interest = (String)objects_of_interest.elementAt(j);
                if (object_of_interest.indexOf(object_of_interest_name+":")==0)
                {
                    String entity =
                            object_of_interest.substring(object_of_interest.indexOf(":")+1,object_of_interest.length());
                    new_datatable.addEmptyRow(entity);
                }
            }

            /* Fill in the datatable */

            if (!concept.is_java_enabled)
            {
                for (int j=3; j<concept_vector.size(); j++)
                {
                    String line = (String)concept_vector.elementAt(j);
                    if (line.indexOf("function:")<0 && line.indexOf("->")<0)
                    {
                        while (line.indexOf(").")>=0)
                        {
                            String values = line.substring(line.indexOf("(")+1,line.indexOf(")."));
                            line = line.substring(line.indexOf(").")+2,line.length());
                            Vector tuple = new Vector();
                            while (values.indexOf(",")>0)
                            {
                                tuple.addElement(values.substring(0,values.indexOf(",")));
                                values = values.substring(values.indexOf(",")+1,values.length());
                            }
                            if (!values.equals(""))
                                tuple.addElement(values);
                            if (tuple.size()>1)
                                new_datatable.addTuple(tuple);
                            else
                            {
                                Row row = new_datatable.rowWithEntity((String)tuple.elementAt(0));
                                row.tuples.addElement(new Vector());
                            }
                        }
                    }
                }
            }

            if (concept.is_java_enabled)
            {
                for (int j=0; j<new_datatable.size(); j++)
                {
                    Row row = (Row)new_datatable.elementAt(j);
                    row.tuples = user_functions.calculateTuples(concept.id, row.entity);
                }
            }

            new_datatable.sort();
            concept.datatable = new_datatable;
            concept.datatable.setNumberOfTuples();

            /* Now make the specifications of the concept */

            for (int j=3; j<concept_vector.size(); j++)
            {
                String line = (String)concept_vector.elementAt(j);
                if (line.indexOf("->")>=0)
                {
                    String letters_in_use =
                            line.substring(line.lastIndexOf("(")+1,line.lastIndexOf(")"));
                    String permutation = "[";
                    for (int k=0; k<letters_in_use.length(); k++)
                    {
                        int pos = definition_letters.indexOf(letters_in_use.substring(k,k+1));
                        if (pos >= 0)
                        {
                            if (!permutation.equals("["))
                                permutation = permutation + ",";
                            permutation = permutation + Integer.toString(pos);
                        }
                    }
                    permutation = permutation + "]";
                    String relation_name =
                            line.substring(line.indexOf("->")+3,line.lastIndexOf("("));
                    for (int k=0; k<relations.size(); k++)
                    {
                        Relation relation = (Relation)relations.elementAt(k);
                        if (relation.name.equals(relation_name))
                        {
                            Specification new_specification = new Specification(permutation,relation);
                            concept.specifications.addElement(new_specification);
                            specifications.addElement(new_specification);
                        }
                    }
                }
            }

            /* Add in the concept's own specification */

            String permutation = "[";
            for (int j=0; j<concept.arity; j++)
                permutation = permutation + Integer.toString(j)+",";
            permutation = permutation.substring(0,permutation.length()-1)+"]";
            for (int j=0; j<relations.size(); j++)
            {
                Relation relation = (Relation)relations.elementAt(j);
                if (relation.name.equals(concept.name))
                {
                    Specification new_specification = new Specification(permutation,relation);
                    concept.specifications.addElement(new_specification);
                    specifications.addElement(new_specification);
                }
            }

            /* Set the skolemised representation of the concept */

            concept.setSkolemisedRepresentation();

            /* Determine the functions in the concept */

            Vector temp_strings = new Vector();
            for (int j=0; j<concept.specifications.size(); j++)
            {
                Specification specification = (Specification)concept.specifications.elementAt(j);
                for (int k=0; k<specification.functions.size(); k++)
                {
                    Function function = (Function)specification.functions.elementAt(k);
                    if (!temp_strings.contains(function.writeFunction()))
                    {
                        concept.functions.addElement(function);
                        temp_strings.addElement(function.writeFunction());
                    }
                }
            }
        }
        return output;
    }
}
