package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class for proving new conjectures by matching them with previous conjectures
 * (possibly from different domains).
 *
 * @author Simon Colton, started 20th July 2002.
 * @version 1.0
 */

public class FileProver extends Prover implements Serializable
{
    /** The vector of vector pairs where the operation string in the
     * first is to be substituted by the operation string in the second.
     */

    public Vector operation_substitutions = new Vector();

    /** The vector of results loaded from a file which this
     * prover will look through to find a match.
     */

    public Vector stored_results = new Vector();

    /** This initialises the file prover to retrieve the conjectures
     * found in the given file.
     */

    public FileProver(String filename, StorageHandler storage_handler)
    {
        stored_results = storage_handler.retrieveAllSlim(filename);
        if (stored_results!=null)
        {
            for (int i=0; i<stored_results.size(); i++)
            {
                Conjecture stored_conj = (Conjecture)stored_results.elementAt(i);
                System.out.println(stored_conj.writeConjecture("otter") + " " + stored_conj.proof_status);
            }
        }
        else
            stored_results = new Vector();
    }

    /** This runs through the set of stored results and sees whether
     * any of the results subsumes the given conjecture.
     */

    public boolean prove(Conjecture conj, Theory theory)
    {
        for (int i=0; i<stored_results.size(); i++)
        {
            Conjecture stored_conj = (Conjecture)stored_results.elementAt(i);
            String stored_string = stored_conj.writeConjecture("otter").trim();
            String conj_string = conj.writeConjecture("otter").trim();
            if (stored_string.equals(conj_string))
            {
                if (stored_conj.proof_status.equals("proved"))
                {
                    theory.front_end.systemout_text.append(stored_conj.proof_status+"\n");
                    conj.proof_status = "proved";
                    conj.proof = stored_conj.proof;
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }
}
