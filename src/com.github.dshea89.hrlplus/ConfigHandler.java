package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class ConfigHandler extends PseudoCodeUser implements Serializable {
    public Hashtable values_table = new Hashtable();

    public ConfigHandler() {
    }

    public boolean readUserConfiguration(String var1) {
        try {
            BufferedReader var2 = new BufferedReader(new FileReader(var1));

            for(String var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
                this.pseudo_code_lines.addElement(var3);
            }

            var2.close();
            return true;
        } catch (Exception var4) {
            return false;
        }
    }

    public void setValue(String var1, String var2) {
        this.values_table.put(var1, var2);
    }

    public void initialiseHR(HR var1) {
        this.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
        this.pseudo_code_interpreter.local_alias_hashtable.put("hr", var1);
        this.pseudo_code_interpreter.runPseudoCode(this.pseudo_code_lines);
        int var2 = 1100;
        int var3 = 800;
        if (this.values_table.containsKey("width")) {
            var2 = new Integer((String)this.values_table.get("width"));
        }

        if (this.values_table.containsKey("height")) {
            var3 = new Integer((String)this.values_table.get("height"));
        }

        var1.setSize(var2, var3);
        var1.setLocation(30, 30);
        var1.setTitle(var1.my_agent_name + " HR" + var1.version_number);
        var1.theory.front_end.version_number = var1.version_number;
        if (this.values_table.containsKey("operating system")) {
            var1.theory.operating_system = (String)this.values_table.get("operating system");
        } else {
            System.out.println("HR> warning: you have not specified the operating system");
        }

        String var4;
        if (this.values_table.containsKey("input files directory")) {
            var4 = (String)this.values_table.get("input files directory");
            if (!var4.substring(var4.length() - 1, var4.length()).equals("/")) {
                var4 = var4 + "/";
            }

            Path p = Paths.get(var4);
            if (!p.isAbsolute()) {
                var4 = attachBasePath(var4);
            }

            var1.theory.input_files_directory = var4;
            var1.theory.front_end.input_files_directory = var4;
        } else {
            System.out.println("HR> warning: you have not specified a directory for your input files");
        }

        if (this.values_table.containsKey("storage directory")) {
            var4 = (String)this.values_table.get("storage directory");
            if (!var4.substring(var4.length() - 1, var4.length()).equals("/")) {
                var4 = var4 + "/";
            }

            Path p = Paths.get(var4);
            if (!p.isAbsolute()) {
                var4 = attachBasePath(var4);
            }

            var1.theory.storage_handler.storage_directory = var4;
        }

        if (this.values_table.containsKey("default max steps")) {
            try {
                var1.theory.front_end.required_text.setText(new Integer((String) this.values_table.get("default max steps")).toString());
            } catch (Exception ignored) {
            }
        }
    }

    private String attachBasePath(String path) {
        String base_path = System.getProperty("user.dir");
        if (!base_path.endsWith(File.separator)) {
            base_path += File.separator;
        }
        if (path.startsWith(File.separator) || path.startsWith("/")) {
            path = path.substring(1);
        }
        return base_path + path;
    }
}
