package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Timer implements Serializable {
    public Hashtable stopwatches = new Hashtable();
    public String currently_timing = "";
    public Vector times = new Vector();
    public Vector names = new Vector();
    public Vector last_times = new Vector();

    public Timer() {
    }

    public void addTo(String var1) {
        long var2 = new Long(System.currentTimeMillis());
        int var4 = this.names.indexOf(var1);
        if (var4 == -1) {
            this.names.addElement(var1);
            this.times.addElement(new Long(0L));
            this.last_times.addElement(new Long(var2));
        }

        if (var4 > -1) {
            this.last_times.setElementAt(new Long(var2), var4);
        }

        if (!this.currently_timing.equals("")) {
            int var5 = this.names.indexOf(this.currently_timing);
            long var6 = (Long)this.last_times.elementAt(this.names.indexOf(this.currently_timing));
            long var8 = var2 - var6;
            long var10 = (Long)this.times.elementAt(var5);
            Long var12 = new Long(var8 + var10);
            this.times.setElementAt(var12, var5);
        }

        this.currently_timing = var1;
    }

    public void startStopWatch(String var1) {
        this.stopwatches.put(var1, new Long(System.currentTimeMillis()));
    }

    public void stopStopWatch(String var1) {
        this.stopwatches.remove(var1);
    }

    public long millisecondsPassed(String var1) {
        Long var2 = (Long)this.stopwatches.get(var1);
        return System.currentTimeMillis() - var2;
    }
}
