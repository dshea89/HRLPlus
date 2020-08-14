package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;
import javax.swing.JTable;

/** A class for handling the various explanation generators which try
 * to prove and disprove conjectures provided by the theory.
 *
 * @author Simon Colton, started 18th July 2002
 * @version 1.0 */

public class ExplanationHandler implements Serializable
{
    /** Whether or not to store the conjectures.
     */

    public boolean store_conjectures = false;

    /** The storage handler object (inherited from the theory).
     */

    public StorageHandler storage_handler = new StorageHandler();

    /** The vector of explainer objects (Otter, MACE, HRProver, etc.) that
     * will be used -- in the order they appear in this vector -- to settle
     * conjectures which come in.
     */

    public SortableVector explainers = new SortableVector();

    /** The implicate strings, for checking whether an implicate has
     * already been seen, kept in a hashtable, with the conjecture itself.
     */

    public Hashtable previous_implicates = new Hashtable();

    /** The implication strings, for checking whether an implicate has
     * already been seen, kept in a hashtable, with the conjecture itself.
     */

    public Hashtable previous_implications = new Hashtable();

    /** The equivalence strings, for checking whether an implicate has
     * already been seen, kept in a hashtable, with the conjecture itself.
     */

    public Hashtable previous_equivalences = new Hashtable();

    /** The non_exists strings, for checking whether an implicate has
     * already been seen, kept in a hashtable, with the conjecture itself.
     */

    public Hashtable previous_non_exists = new Hashtable();

    /** The name of the operating system inherited from the theory.
     */

    public String operating_system = "";

    /** The input files directory inherited from the theory.
     */

    public String input_files_directory = "";

    /** Whether or not to use the entity letter in conjecture statements.
     */

    public boolean use_entity_letter = false;

    /** Whether or not to use the ground instances in proving.
     */

    public boolean use_ground_instances = false;

    /** Whether or not to use all the explainers exhaustively, or to stop
     * once one explanation has been found.
     */

    public boolean use_all_explainers = false;

    /** The hold back checker (which checks whether any data held back by
     * the user and specified as available for counterexamples breaks a conjecture).
     */

    public HoldBackChecker hold_back_checker = new HoldBackChecker();

    /** The reflection mechanism for this explanation handler.
     */

    public Reflection reflect = new Reflection();

    /** The explainers are used in order to try to prove/disprove the given
     * conjecture. The proof status of the conjecture is changed accordingly.
     */

    public void explainConjecture(Conjecture conjecture, Theory theory, String timer_num)
    {
        logThis(conjecture.writeConjecture("tptp"), theory);
        for (int i=0; i<explainers.size(); i++)
        {
            Explainer explainer = (Explainer)explainers.elementAt(i);
            explainer.use_entity_letter = use_entity_letter;
            theory.addToTimer(timer_num + "." + Integer.toString(i) + " Trying " + explainer.name + " to solve conjecture");

            if (explainer.condition_string.trim().equals("") ||
                    reflect.checkCondition(conjecture, explainer.condition_string))
            {
                if (explainer instanceof FileProver)
                {
                    FileProver file_prover = (FileProver)explainer;
                    boolean is_proved = file_prover.prove(conjecture, theory);
                    logThis(explainer.name + "(" + explainer.setup_name + ") " + conjecture.proof_status, theory);
                    if (is_proved)
                    {
                        conjecture.proof_status = "proved";
                        if (conjecture.is_trivially_true)
                            conjecture.explained_by = "being trivial";
                        else
                            conjecture.explained_by = explainer.name + "(" + explainer.setup_name + ")";
                        if (!use_all_explainers)
                        {
                            logThis("-----------------", theory);
                            if (store_conjectures)
                                storage_handler.handleStorageOf(conjecture);
                            return;
                        }
                    }
                    if (!conjecture.counterexamples.isEmpty() || file_prover.disprove(conjecture, theory))
                    {
                        conjecture.proof_status = "disproved";
                        logThis(explainer.name + "(" + explainer.setup_name + ") " + conjecture.proof_status, theory);
                        conjecture.explained_by = explainer.name + "(" + explainer.setup_name + ")";
                        if (!use_all_explainers)
                        {
                            logThis("-----------------", theory);
                            if (store_conjectures)
                                storage_handler.handleStorageOf(conjecture);
                            return;
                        }
                    }
                    if (conjecture.proof_status.equals("sos") && !use_all_explainers)
                    {
                        logThis("-----------------", theory);
                        if (store_conjectures)
                            storage_handler.handleStorageOf(conjecture);
                        return;
                    }
                }
                if (explainer instanceof DataGenerator)
                {
                    DataGenerator data_generator = (DataGenerator)explainer;
                    Vector counterexamples = data_generator.counterexamplesFor(conjecture, theory, 1);
                    if (!counterexamples.isEmpty())
                    {
                        conjecture.proof_status = "disproved";
                        logThis(explainer.name + "(" + explainer.setup_name + ") " + conjecture.proof_status, theory);
                        conjecture.counterexamples = counterexamples;
                        conjecture.explained_by = explainer.name + "(" + explainer.setup_name + ")";
                        if (!use_all_explainers)
                        {
                            logThis("-----------------", theory);
                            if (store_conjectures)
                                storage_handler.handleStorageOf(conjecture);
                            return;
                        }
                    }
                }
                if (explainer instanceof Prover && !(explainer instanceof FileProver))
                {
                    Prover prover = (Prover)explainers.elementAt(i);
                    boolean is_proved = prover.prove(conjecture, theory);
                    logThis(explainer.name + "(" + explainer.setup_name + ") " + conjecture.proof_status, theory);
                    if (is_proved)
                    {
                        conjecture.proof_status = "proved";
                        if (conjecture.is_trivially_true)
                            conjecture.explained_by = "being trivial";
                        else
                            conjecture.explained_by = explainer.name + "(" + explainer.setup_name + ")";
                        if (!use_all_explainers)
                        {
                            logThis("-----------------", theory);
                            if (store_conjectures)
                                storage_handler.handleStorageOf(conjecture);
                            return;
                        }
                    }
                    else if (prover.disprove(conjecture, theory)) {
                        conjecture.proof_status = "disproved";
                        this.logThis(explainer.name + "(" + explainer.setup_name + ") " + conjecture.proof_status, theory);
                        conjecture.explained_by = explainer.name + "(" + explainer.setup_name + ")";
                        if (!this.use_all_explainers) {
                            this.logThis("-----------------", theory);
                            if (this.store_conjectures) {
                                this.storage_handler.handleStorageOf(conjecture);
                            }
                            return;
                        }
                    }
                }
            }
            else
                logThis("Failed conditions for " + explainer.name + "(" + explainer.setup_name + ")", theory);
        }
        if (store_conjectures)
            storage_handler.handleStorageOf(conjecture);
        logThis("-----------------", theory);
    }

    /** This initialises all the explainers using the theory.
     */

    public void initialiseExplainers(Theory theory)
    {
        Hashtable axiom_string_hashtable = new Hashtable();
        Hashtable algebra_name_hashtable = new Hashtable();

        JTable axiom_table = theory.front_end.axiom_table;
        for (int i=0; i<axiom_table.getRowCount(); i++)
        {
            String axiom_name = (String)axiom_table.getValueAt(i,0);
            if (axiom_name instanceof String && axiom_name.trim().equals(""))
                break;
            if (axiom_name instanceof String)
            {
                String axiom_string = (String)axiom_table.getValueAt(i,1);
                axiom_string_hashtable.put(axiom_name.trim(), axiom_string.trim());
            }
        }

        JTable algebra_table = theory.front_end.algebra_table;
        for (int i=0; i<algebra_table.getRowCount(); i++)
        {
            String algebra_name = (String)algebra_table.getValueAt(i,0);
            if (algebra_name instanceof String && !algebra_name.trim().equals(""))
            {
                String axiom_vector_string = (String)algebra_table.getValueAt(i,1);
                if (axiom_vector_string instanceof String)
                {
                    Categorisation cat = new Categorisation("["+axiom_vector_string+"]");
                    Vector axiom_vector = (Vector)cat.elementAt(0);
                    algebra_name_hashtable.put(algebra_name, axiom_vector);
                }
                String add_to_embed_algebra = (String)algebra_table.getValueAt(i,2);
                if (add_to_embed_algebra instanceof String)
                {
                    if (add_to_embed_algebra.equals("true") ||
                            add_to_embed_algebra.equals("yes"))
                    {
                        Vector axiom_names = (Vector)algebra_name_hashtable.get(algebra_name);
                        Vector axiom_strings_for_embed = new Vector();
                        for (int j=0; j<axiom_names.size(); j++)
                        {
                            String axiom_name = (String)axiom_names.elementAt(j);
                            String axiom_string = (String)axiom_string_hashtable.get(axiom_name);
                            axiom_strings_for_embed.addElement(axiom_string);
                        }
                        theory.embed_algebra.algebra_hashtable.put(algebra_name, axiom_strings_for_embed);
                    }
                }
            }
        }

        JTable file_prover_table = theory.front_end.file_prover_table;
        for (int i=0; i<file_prover_table.getRowCount(); i++)
        {
            String pos_string = (String)file_prover_table.getValueAt(i,0);
            if (pos_string==null)
                break;
            if (pos_string instanceof String && pos_string.trim().equals(""))
                break;
            if (pos_string instanceof String)
            {
                String file_string = (String)file_prover_table.getValueAt(i,1);
                FileProver file_prover = new FileProver(file_string, theory.storage_handler);
                file_prover.try_pos = (new Integer(pos_string)).intValue();
                file_prover.name = "file:"+file_string;
                String operation_substitution_string = (String)file_prover_table.getValueAt(i,3);
                if (!operation_substitution_string.equals(""))
                {
                    Categorisation cat = new Categorisation("[" + operation_substitution_string + "]");
                    Vector v = (Vector)cat.elementAt(0);
                    for (int j=0; j<v.size(); j++)
                    {
                        String op_sub = (String)v.elementAt(j);
                        String lh = op_sub.substring(0,op_sub.indexOf(":"));
                        String rh = op_sub.substring(op_sub.indexOf(":")+1, op_sub.length());
                        Vector changer = new Vector();
                        changer.addElement(lh);
                        changer.addElement(rh);
                        file_prover.operation_substitutions.addElement(changer);
                    }
                }
                explainers.addElement(file_prover, "try_pos");
            }
        }

        JTable other_table = theory.front_end.other_prover_table;
        for (int i=0; i<other_table.getRowCount(); i++)
        {
            String pos_string = (String)other_table.getValueAt(i,0);
            if (pos_string==null)
                break;
            pos_string = pos_string.trim();
            if (pos_string instanceof String && pos_string.trim().equals(""))
                break;
            if (pos_string instanceof String)
            {
                Explainer explainer = new Explainer();
                String name_string = (String)other_table.getValueAt(i,1);
                if (name_string instanceof String)
                {
                    if (name_string.equals("HoldBackChecker"))
                        explainer = hold_back_checker;
                    else
                    {
                        try
                        {
                            Object obj = (Class.forName(name_string)).newInstance();
                            explainer = (Explainer)obj;
                        }
                        catch(Exception e){}
                    }
                    explainer.try_pos = (new Integer(pos_string)).intValue();
                    explainer.name = name_string;
                    explainers.addElement(explainer, "try_pos");
                }

                String condition_string = (String)other_table.getValueAt(i,2);
                if (condition_string instanceof String)
                    explainer.condition_string = condition_string;

                String algebra_string = (String)other_table.getValueAt(i,3);
                if (algebra_string instanceof String && !algebra_string.trim().equals(""))
                {
                    Vector axiom_names = (Vector)algebra_name_hashtable.get(algebra_string);
                    for (int j=0; j<axiom_names.size(); j++)
                    {
                        String axiom_name = (String)axiom_names.elementAt(j);
                        String axiom_string = (String)axiom_string_hashtable.get(axiom_name);
                        // Add the axiom string pointed to by the axiom name in the algebra's list of axioms
                        if (axiom_string.startsWith("file:")) {
                            // The axiom string begins with "file:", so open the file and add all axioms in it
                            // Currently, only TPTP style axioms are supported
                            parseAxiomFile(explainer, axiom_string.substring(5), "tptp");
                        }
                        else {
                            explainer.axiom_strings.addElement(axiom_string);
                        }
                    }
                }

                String execution_parameters_string = (String)other_table.getValueAt(i,4);
                if (execution_parameters_string instanceof String && !execution_parameters_string.trim().equals(""))
                {
                    Categorisation cat = new Categorisation("["+execution_parameters_string+"]");
                    Vector execution_params = (Vector)cat.elementAt(0);
                    for (int j=0; j<execution_params.size(); j++)
                    {
                        String eqstring = (String)execution_params.elementAt(j);
                        String lhs = eqstring.substring(0, eqstring.indexOf("="));
                        String rhs = eqstring.substring(eqstring.indexOf("=") + 1, eqstring.length());
                        explainer.execution_parameters.put(lhs, rhs);
                    }
                }
                String setup_name = (String)other_table.getValueAt(i,5);
                if (setup_name instanceof String && !((String)setup_name).equals(""))
                    explainer.setup_name = setup_name;
            }
        }
        explainers.reverse();
        for (int i=0; i<explainers.size(); i++)
        {
            Explainer explainer = (Explainer)explainers.elementAt(i);
            explainer.operating_system = operating_system;
            explainer.input_files_directory = input_files_directory;
            if (use_ground_instances)
                setGroundInstancesAsAxioms(explainer, theory.concepts);
        }

    }

    /** This updates the ground instances of the explainers.
     */

    public void updateGroundInstances(Theory theory)
    {
        for (int i=0; i<explainers.size(); i++)
        {
            Explainer explainer = (Explainer)explainers.elementAt(i);
            if (use_ground_instances)
                setGroundInstancesAsAxioms(explainer, theory.concepts);
        }
    }

    /** This takes the given set of concepts (user given) and turns
     * the ground instances into axioms, passing them to all the
     * explainer objects.
     */

    public void setGroundInstancesAsAxioms(Explainer explainer, Vector concepts)
    {
        if (!use_ground_instances)
            return;
        explainer.use_ground_instances = true;
        explainer.ground_instances_as_axioms = "";
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.is_user_given)
            {
                for (int j=0; j<concept.datatable.size(); j++)
                {
                    Row row = (Row)concept.datatable.elementAt(j);
                    for (int k=0; k<row.tuples.size(); k++)
                    {
                        Vector tuple = (Vector)row.tuples.elementAt(k);
                        Vector ground_letters = (Vector)tuple.clone();
                        ground_letters.insertElementAt(row.entity, 0);
                        String add_to = concept.writeDefinition("tptp",ground_letters);
                        if (!add_to.trim().equals(""))
                            explainer.ground_instances_as_axioms = explainer.ground_instances_as_axioms + add_to.trim() + ".\n";
                    }
                    if (concept.arity==1 && row.tuples.size()==0)
                    {
                        Vector ground_letters = new Vector();
                        ground_letters.addElement(row.entity);
                        String add_to = concept.writeDefinition("tptp",ground_letters);
                        if (!add_to.trim().equals(""))
                            explainer.ground_instances_as_axioms = explainer.ground_instances_as_axioms + "~(" + add_to.trim() + ").\n";
                    }
                }
            }
        }
    }

    /** This adds the given conjecture as an axiom.
     */

    public void addConjectureAsAxiom(Conjecture conjecture)
    {
        conjecture.proof_status="axiom";
        for (int i=0; i<explainers.size(); i++)
        {
            Explainer explainer = (Explainer)explainers.elementAt(i);
            explainer.axiom_strings.addElement(conjecture.writeConjecture("tptp"));
        }
    }

    /** This checks whether the given conjecture has been seen yet.
     */

    public boolean conjectureSeenAlready(Conjecture conj)
    {
        String conj_string = conj.writeConjecture("tptp");
        if (conj instanceof Implication)
        {
            if (previous_implications.containsKey(conj_string))
                return true;
            return false;
        }
        if (conj instanceof Equivalence)
        {
            if (previous_equivalences.containsKey(conj_string))
                return true;
            return false;
        }
        if (conj instanceof NonExists)
        {
            if (previous_non_exists.containsKey(conj_string))
                return true;
            return false;
        }

        if (conj instanceof Implicate)
        {
            if (previous_implicates.containsKey(conj_string))
                return true;
            return false;
        }
        return false;
    }

    /** This returns the proof status of a conjecture if it is found
     * in the hashtables.
     */

    public String previousResult(Conjecture conj)
    {
        String conj_string = conj.writeConjecture("tptp");
        Conjecture previous_conjecture = new Conjecture();
        if (conj instanceof Implication)
            previous_conjecture = (Conjecture)previous_implications.get(conj_string);
        if (conj instanceof Equivalence)
            previous_conjecture = (Conjecture)previous_equivalences.get(conj_string);
        if (conj instanceof NonExists)
            previous_conjecture = (Conjecture)previous_non_exists.get(conj_string);
        if (conj instanceof Implicate)
            previous_conjecture = (Conjecture)previous_implicates.get(conj_string);
        if (previous_conjecture==null)
            return null;
        return previous_conjecture.proof_status;
    }

    /** This checks whether the set of implicates given subsumes the
     * given conjectures.
     */

    public Implicate getSubsumingImplicate(Implicate implicate, Theory theory, boolean require_proof)
    {
        for (int i=0; i<theory.implicates.size(); i++)
        {
            Implicate old_implicate = (Implicate)theory.implicates.elementAt(i);
            if ((old_implicate.proof_status.equals("proved") || !require_proof) &&
                    old_implicate.subsumes(implicate, theory.specification_handler))
                return old_implicate;
        }
        return null;
    }

    /** This adds a conjecture to the list of those already seen, so
     * that we can look for repetitions later.
     */

    public void addConjecture(Conjecture conj)
    {
        String conj_string = conj.writeConjecture("tptp");
        if (conj instanceof Implicate)
            previous_implicates.put(conj_string, conj);
        if (conj instanceof Implication)
            previous_implications.put(conj_string, conj);
        if (conj instanceof Equivalence)
            previous_equivalences.put(conj_string, conj);
        if (conj instanceof NonExists)
            previous_non_exists.put(conj_string, conj);
    }

    /** This makes a log of what's gone on.
     */

    public void logThis(String log, Theory theory)
    {
    }

    public void parseAxiomFile(Explainer explainer, String filename, String format) {
        if (format.equalsIgnoreCase("tptp")) {
            // Axioms in this file are being provided in TPTP format
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                String axiom = "";
                while ((line = br.readLine()) != null) {
                    if (line.isBlank() || line.startsWith("%")) {
                        if (!axiom.isBlank()) {
                            explainer.axiom_strings.addElement(axiom);
                        }
                        axiom = "";
                    }
                    else {
                        axiom += ((axiom.isBlank()) ? "" : " ") + line.strip();
                    }
                }
                if (!axiom.isBlank()) {
                    explainer.axiom_strings.addElement(axiom);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
