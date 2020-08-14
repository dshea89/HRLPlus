package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.*;

/** A class for timing how long certain processes take within HR,
 * and for logging the steps undertaken.
 *
 * @author Simon Colton, started 13th January 2001
 * @version 1.0 */

public class Timer implements Serializable
{
    /** The hashtable of stopwatches.
     */

    public Hashtable stopwatches = new Hashtable();

    /** The process which is currently being timed.
     */

    public String currently_timing = "";

    /** The vector of times so far.
     */

    public Vector times = new Vector();

    /** The vector of names of processes.
     */

    public Vector names = new Vector();

    /** The vector of last times recorded.
     */

    public Vector last_times = new Vector();

    /** This stops timing the current process and starts timing the new
     * process.
     */

    public void addTo(String process_name)
    {
        long time = (new Long(System.currentTimeMillis())).longValue();
        int ind = names.indexOf(process_name);
        if (ind==-1)
        {
            names.addElement(process_name);
            times.addElement(new Long(0));
            last_times.addElement(new Long(time));
        }
        if(ind>-1)
            last_times.setElementAt(new Long(time),ind);
        if (!currently_timing.equals(""))
        {
            int last_index = names.indexOf(currently_timing);
            long last_time = ((Long)last_times.elementAt(names.indexOf(currently_timing))).longValue();
            long this_duration = time - last_time;
            long current_total = ((Long)times.elementAt(last_index)).longValue();
            Long new_duration = new Long(this_duration + current_total);
            times.setElementAt(new_duration, last_index);
        }
        currently_timing = process_name;
    }

    /** Starts a new stopwatch.
     */

    public void startStopWatch(String stopwatch_name)
    {
        stopwatches.put(stopwatch_name, new Long(System.currentTimeMillis()));
    }

    /** Stops a stopwatch (removes it from the list being timed).
     */

    public void stopStopWatch(String stopwatch_name)
    {
        stopwatches.remove(stopwatch_name);
    }

    /** Finds how many milliseconds have elapsed since the given stopwatch
     * was started.
     */

    public long millisecondsPassed(String stopwatch_name)
    {
        Long timestarted = (Long)stopwatches.get(stopwatch_name);
        return (System.currentTimeMillis() - timestarted.longValue());
    }
}
