package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class Row implements Serializable {
    public String entity = "";
    public Tuples tuples = new Tuples();
    public boolean is_void = false;

    public Row() {
    }

    public Row(String var1, Tuples var2) {
        this.entity = var1;
        this.tuples = var2;
        this.tuples.sort();
    }

    public Row(String var1, String var2) {
        this.entity = var1;
        if (var2.equals("void")) {
            this.is_void = true;
        }

    }
}
