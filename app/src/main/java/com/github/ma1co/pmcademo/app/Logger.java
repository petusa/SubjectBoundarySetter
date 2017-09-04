package com.github.ma1co.pmcademo.app;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    public static String LOG_NAME_SUFFIX;
    static {
        Date today = new Date();
        LOG_NAME_SUFFIX = today.getYear() + "_" + today.getMonth() + "_" + today.getDay();
    }
    public static File getFile() {
        return new File(Environment.getExternalStorageDirectory(), "PMCADEMO/LOG.TXT");
    }

    public static void log(String msg) {
        try {
            getFile().getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), true));
            writer.append(msg);
            writer.newLine();
            writer.close();
        } catch (IOException e) {}
    }
    protected static void log(String type, String msg) { log("[" + type + "] " + msg); }

    public static void info(String msg) { log("INFO", msg); }
    public static void error(String msg) { log("ERROR", msg); }
}
