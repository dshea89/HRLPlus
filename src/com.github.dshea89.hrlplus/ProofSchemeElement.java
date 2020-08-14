package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.Serializable;

/** A class representing a mathematical proof in the theory. It consists of [...]
 * @author Alison Pease, started 20th October 2003
 * @version 1.0
 */

public class ProofSchemeElement implements Serializable
{
    String agenda_line = new String();
    Conjecture associated_conj = new Conjecture();
    Exception e = new Exception();

    public ProofSchemeElement(String agenda_line, Conjecture associated_conj)
    {
        this.agenda_line = agenda_line;
        this.associated_conj =  associated_conj;
    }

    // public void constructConjectures()
//   {


//   }

//   public Conjecture constructEquivalence(Concept concept1, Concept concept2, String step_number)
//   {
//     return (new Equivalence(concept1, concept2, step_number));
//   }

//   public Conjecture constructImplication(Concept concept1, Concept concept2)
//   {
//     return (new Implication(concept1, concept2, step_number));
//   }

//   public Conjecture constructNonExists(Concept concept)
//   {
//     return (new NonExists(conceptstep_number));
//   }

//    hr.theory.front_end.use_surrender_check.getState());
//   hr.theory.front_end.force_button.getState()
//   clickButton("force_button");

//      for(int i=0;i<theory.concepts.size();i++)
//        {
// 	 Concept concept = (Concept)theory.concepts.elementAt(i);
// 	 if(concept.id.equals("poly004"))//was shape004 in conj1_working.hrm
// 	   concept1 = concept;

// 	 if(concept.id.equals("p15_0"))//was s10_0 in conj1_working.hrm
// 	   concept2 = concept;

// 	 if(concept.id.equals("g16_0"))//was p15_0 in conj2_working.hrm
//            concept3 = concept;

// 	 if(concept.id.equals("g19_0"))//was p19_0 in conj2_working.hrm
// 	   concept4 = concept;

// 	 if(concept.id.equals("g25_0"))
// 	   concept5 = concept;

// 	 if(concept.id.equals("g22_0"))
// 	   concept6 = concept;

// 	 if(concept.id.equals("g28_0"))
// 	   concept7 = concept;

// 	 if(concept.id.equals("g29_0"))
// 	   concept8 = concept;
//        }

//      Equivalence conj1 = new Equivalence(concept1, concept2, "step1");
//      System.out.println("\n\nconj for step 1 is:\n" + conj1.writeConjecture());

//      Equivalence conj2 = new Equivalence(concept3, concept4, "step2");
//      System.out.println("\n\nconj for step 2 is:\n" + conj2.writeConjecture());

//      Equivalence conj3 = new Equivalence(concept5, concept6, "step3_i");
//      System.out.println("\n\nconj for step 3 - part 1 is:\n" + conj3.writeConjecture());

//      Equivalence conj4 = new Equivalence(concept7, concept8, "step3_ii");
//      System.out.println("\n\nconj for step 3 - part 2 is:\n" + conj4.writeConjecture());
}
