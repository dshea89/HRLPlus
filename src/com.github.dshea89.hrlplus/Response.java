package com.github.dshea89.hrlplus;

import java.util.Vector;

public class Response extends CommunicationLanguage {
    Vector response_vector = new Vector();
    Request request = new Request();

    public Response() {
    }

    public Response(Request var1) {
        this.request = var1;
    }

    public Response(Vector var1, Request var2) {
        this.response_vector = var1;
        this.request = var2;
    }

    public String toString() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.response_vector.size(); ++var2) {
            String var4;
            if (this.response_vector.elementAt(var2) instanceof Conjecture) {
                Conjecture var3 = (Conjecture)this.response_vector.elementAt(var2);
                var4 = var3.id + ": " + var3.writeConjecture("ascii");
                var1.addElement(var4);
            }

            if (this.response_vector.elementAt(var2) instanceof Concept) {
                Concept var5 = (Concept)this.response_vector.elementAt(var2);
                var4 = var5.id + ": " + var5.writeDefinition("ascii");
                var1.addElement(var4);
            }

            if (this.response_vector.elementAt(var2) instanceof Entity) {
                Entity var6 = (Entity)this.response_vector.elementAt(var2);
                var4 = var6.toString();
                var1.addElement(var4);
            }

            if (this.response_vector.elementAt(var2) instanceof String) {
                String var7 = (String)this.response_vector.elementAt(var2);
                var1.addElement(var7);
            }

            if (var2 < this.response_vector.size() - 1) {
                var1.addElement("\n");
            }
        }

        if (!var1.isEmpty()) {
            return "" + var1;
        } else {
            return "" + this.response_vector;
        }
    }

    public String toStringForFile() {
        String var1 = new String();

        for(int var2 = 0; var2 < this.response_vector.size(); ++var2) {
            if (this.response_vector.elementAt(var2) instanceof Conjecture) {
                Conjecture var3 = (Conjecture)this.response_vector.elementAt(var2);
                var1 = var1 + var3.writeConjecture("ascii");
            }

            if (this.response_vector.elementAt(var2) instanceof Concept) {
                Concept var4 = (Concept)this.response_vector.elementAt(var2);
                var1 = var1 + var4.writeDefinition("ascii");
            }

            if (this.response_vector.elementAt(var2) instanceof Entity) {
                Entity var5 = (Entity)this.response_vector.elementAt(var2);
                var1 = var1 + var5.toString();
            }

            if (this.response_vector.elementAt(var2) instanceof String) {
                String var6 = (String)this.response_vector.elementAt(var2);
                if (var6.equals("accept proposal to bar entity")) {
                    var1 = var1 + "I think we should bar the entity";
                    break;
                }

                if (var6.equals("reject proposal to bar entity")) {
                    var1 = var1 + "I think the entity is fine";
                    break;
                }

                var1 = var1 + var6;
            }

            if (var2 < this.response_vector.size() - 1) {
                var1 = var1 + ", ";
            }
        }

        if (this.request.type.equals("Entity") && this.response_vector.isEmpty()) {
            var1 = "No, sorry.";
        }

        return var1;
    }
}
