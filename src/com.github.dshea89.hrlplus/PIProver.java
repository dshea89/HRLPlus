package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class for HR to prove implicates itself using previously proved prime
 * implicates, in a forward chaining fashion.
 *
 * @author Simon Colton, started 18th July 2002
 * @version 1.0 */

// Despite Java warnings that this class is unused, it is invoked via Java Reflection from the Other Provers table
@SuppressWarnings("unused")
public class PIProver extends Prover implements Serializable
{
    /** The maximum branching number for when HR puts together its own proofs.
     */

    public int branches = 0;

    /** This uses forward chaining to attempt to prove the given implicate conjecture
     * using the set of prime implicates also supplied.
     */

    public boolean prove(Conjecture conjecture, Theory theory)
    {
        if (!(conjecture instanceof Implicate))
            return false;
        Implicate implicate = (Implicate)conjecture;
        Vector pis = theory.prime_implicates;
        branches = 0;
        Hashtable pis_for_specs = new Hashtable();
        Vector paths = new Vector();
        Vector lh_specs = (Vector)implicate.premise_concept.specifications.clone();
        int old_size = lh_specs.size();
        int new_size = 0;
        boolean proved = false;
        Vector pis_used = new Vector();
        int cycle_num = 0;
        while (old_size!=new_size && !proved)
        {
            cycle_num ++;
            old_size = lh_specs.size();
            Vector additional_lh_specs = new Vector();
            for (int i=0; i<pis.size(); i++)
            {
                Implicate pi = (Implicate)pis.elementAt(i);
                if (pi!=implicate &&
                        containsSpecs(lh_specs, pi.premise_concept.specifications))
                {
                    Vector pis_for_spec = (Vector)pis_for_specs.get(pi.goal);
                    if (pis_for_spec==null)
                    {
                        Vector dummy_vector = new Vector();
                        dummy_vector.addElement(pi);
                        pis_for_specs.put(pi.goal, dummy_vector);
                    }
                    else
                    {
                        if (!pis_for_spec.contains(pi))
                            pis_for_spec.addElement(pi);
                    }
                    if (pi.goal!=implicate.goal)
                    {
                        additional_lh_specs.addElement(pi.goal);
                    }
                    if (pi.goal==implicate.goal)
                        proved = true;
                    if (!pis_used.contains(pi))
                        pis_used.addElement(pi);
                }
            }
            for (int i=0; i<additional_lh_specs.size(); i++)
            {
                if (!lh_specs.contains(additional_lh_specs.elementAt(i)))
                    lh_specs.addElement(additional_lh_specs.elementAt(i));
            }
            new_size = lh_specs.size();
        }
        if (!proved)
            return false;

        Vector proofs = new Vector();
        Vector pis_for_goal = getPisForGoal(implicate.goal, pis_for_specs);
        for (int i=0; i<pis_for_goal.size(); i++)
        {
            Vector proof = new Vector();
            proof.addElement("after here");
            proof.addElement(pis_for_goal.elementAt(i));
            proofs.addElement(proof);
        }

        Vector final_proofs = new Vector();
        for (int i=0; i<proofs.size(); i++)
        {
            Vector proof = (Vector)proofs.elementAt(i);
            Vector new_proofs = expandProof(implicate, proof, pis_for_specs, cycle_num);
            for (int j=0; j<new_proofs.size(); j++)
                final_proofs.addElement(new_proofs.elementAt(j));
        }

        implicate.proof = "";
        for (int i=0; i<final_proofs.size(); i++)
        {
            implicate.proof = implicate.proof + "\n\n" + implicate.writeConjecture("otter") + "\n---------------\n";
            Vector proof = (Vector)final_proofs.elementAt(i);
            for (int j=0; j<proof.size(); j++)
            {
                Implicate proof_imp = (Implicate)proof.elementAt(j);
                implicate.proof = implicate.proof + "(" + proof_imp.id + ") " + proof_imp.writeConjecture("otter")+"\n";
            }
        }
        return true;
    }

    public Vector expandProof(Implicate original_implicate, Vector proof, Hashtable h_table, int cycle_num)
    {
        branches++;
        Vector output = new Vector();
        cycle_num--;

        boolean start_now = false;
        int start_pos=0;

        for (start_pos=0; start_pos<proof.size() && !start_now; start_pos++)
        {
            Object obj = proof.elementAt(start_pos);
            if (obj instanceof String)
            {
                start_now = true;
                proof.removeElementAt(start_pos);
            }
        }
        if (cycle_num==-1)
        {
            return output;
        }
        Vector new_proofs = new Vector();
        Vector first_proof = (Vector)proof.clone();
        new_proofs.addElement(first_proof);
        for (int i=start_pos - 1; i<proof.size(); i++)
        {
            Implicate pi = (Implicate)proof.elementAt(i);
            for (int j=0; j<pi.premise_concept.specifications.size(); j++)
            {
                Vector temp_new_proofs = new Vector();
                Specification goal_spec = (Specification)pi.premise_concept.specifications.elementAt(j);
                Vector pis_for_goal = new Vector();
                if (!original_implicate.premise_concept.specifications.contains(goal_spec))
                {
                    if (!first_proof.contains("after here"))
                        first_proof.addElement("after here");
                    pis_for_goal = getPisForGoal(goal_spec, h_table);
                    for (int k=0; k < new_proofs.size(); k++)
                    {
                        Vector old_proof = (Vector)new_proofs.elementAt(k);
                        for (int l=0; l < pis_for_goal.size(); l++)
                        {
                            Implicate pi_for_goal = (Implicate)pis_for_goal.elementAt(l);
                            Vector new_proof = (Vector)old_proof.clone();
                            Vector goals_added = new Vector();
                            for (int m=0; m<new_proof.size(); m++)
                            {
                                Object proof_line = new_proof.elementAt(m);
                                if (proof_line instanceof Implicate)
                                    goals_added.addElement(((Implicate)proof_line).goal);
                            }
                            if (!new_proof.contains(pi_for_goal) && !goals_added.contains(goal_spec))
                            {
                                new_proof.addElement(pi_for_goal);
                                temp_new_proofs.addElement(new_proof);
                            }
                        }
                    }
                }
                else
                {
                    for (int k=0; k < new_proofs.size(); k++)
                    {
                        Vector old_proof = (Vector)new_proofs.elementAt(k);
                        temp_new_proofs.addElement(old_proof);
                    }
                }
                new_proofs = temp_new_proofs;
            }
        }

        for (int i=0; i<new_proofs.size(); i++)
        {
            Vector add_proof = (Vector)new_proofs.elementAt(i);
            if (add_proof.contains("after here"))
            {
                Vector expanded_proofs = expandProof(original_implicate, add_proof, h_table, cycle_num);
                for (int j=0; j<expanded_proofs.size(); j++)
                    output.addElement(expanded_proofs.elementAt(j));
            }
            else
            {
                output.addElement(add_proof);
            }
        }
        return output;
    }

    private Vector getPisForGoal(Specification goal_spec, Hashtable h_table)
    {
        Vector output = new Vector();
        Vector pis_for_goal = (Vector)h_table.get(goal_spec);
        if (pis_for_goal!=null)
        {
            for (int i=0; i<pis_for_goal.size(); i++)
            {
                Implicate pi_for_goal = (Implicate)pis_for_goal.elementAt(i);
                output.addElement(pi_for_goal);
            }
        }
        return output;
    }

    private boolean containsSpecs(Vector containing_specs, Vector contained_specs)
    {
        boolean output = true;
        for (int i=0; i<contained_specs.size() && output; i++)
        {
            Specification spec = (Specification)contained_specs.elementAt(i);
            if (!containing_specs.contains(spec))
                output = false;
        }
        return output;
    }
}
