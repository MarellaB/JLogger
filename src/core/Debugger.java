package core;

import java.util.ArrayList;

public final class Debugger {

    private static ArrayList<String> debugLog = new ArrayList<>();
    private static boolean initialized = false;
    private String name, version;
    private static FileManager fm;
    private static boolean printToConsole = false;

    /**
     * Creates a new debugger object to write log data to.
     * @param name - What each line will be called when mentioned in the log. (Ex. "Server-Core")
     * @param version - Current version of the logged module (Ex. "3.2.4")
     * @param targetLogFile - Name of the log file to write to. (Ex. "Debug-Log")
     */
    public Debugger(String name, String version, String targetLogFile) {
        this.name = name;
        this.version = version;
        if (!Debugger.initialized) {
            Debugger.fm = new FileManager(targetLogFile);
            Debugger.debugLog.add("================ Debug Log Initialized ===============");
            Debugger.initialized = true;
        }
        this.printInit();
    }

    public Debugger setLogSaveLocation(String loc) {
        fm.setFileLocation(loc);
        return this;
    }

    /**
     * Determines if the console should be printing the log information.
     * @param set - Used to enable/disable console printing.
     * @return - The debugger object so as to allow it to be chained in initialization.
     */
    public Debugger setConsolePrinting(boolean set) {
        debugLog.add("DEBUGGER CORE - " + (set ? "Enabling" : "Disabling") + " Console Printing");
        Debugger.printToConsole = set;
        return this;
    }

    /**
     * Prints to the console that a new debug module has been added and begun.
     * This helps clarify where pieces are being used.
     * @return - True once the log has been saved.
     */
    private boolean printInit() {
        Debugger.debugLog.add("[START] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + " INITIALIZED");
        if (Debugger.printToConsole) {
            System.out.println("[START] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + "INITIALIZED");
        }
        writeLog();
        return true;
    }

    /**
     * Write a basic log line to the file. Used to keep track of information as the program runs.
     * @param log - What needs to be said. This does not include identification information.
     * @return - True once the log has been saved.
     */
    public boolean log(String log) {
        Debugger.debugLog.add("[ LOG ] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        if (Debugger.printToConsole) {
            System.out.println("[ LOG ] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        }
        writeLog();
        return true;
    }

    /**
     * Write an alert line to the file. Used to point out strange behavior.
     * @param log - What needs to be said. This does not include identification information.
     * @return - True once the log has been saved.
     */
    public boolean alert(String log) {
        Debugger.debugLog.add("[ALERT] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        if (Debugger.printToConsole) {
            System.out.println("[ALERT] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        }
        writeLog();
        return true;
    }

    /**
     * Write an error line to the file. Used to alert you to program breaking issues.
     * @param log - What needs to be said. This does not include identification information.
     * @return - True once the log has been saved.
     */
    public boolean error(String log) {
        Debugger.debugLog.add("[ERROR] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        if (Debugger.printToConsole) {
            System.out.println("[ERROR] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        }
        writeLog();
        saveSeperateLog();
        return true;
    }

    /**
     * Prints to the console and the log file regardless of log print status. Should be used anytime printing is needed.
     * @param log - What needs to be said. This does not include identification information.
     * @return - True once the log has been saved.
     */
    public boolean print(String log) {
        Debugger.debugLog.add("[PRINT] [" + this.name + " (" + this.version + ")] " + FileManager.getPrintableTime() + " - " + log);
        System.out.println(log);
        writeLog();
        return true;
    }

    /**
     * Saves the log to the log file. Called after every write statement.
     * @return - True once the log has been saved.
     */
    private boolean writeLog() {
        fm.writeFile(Debugger.debugLog);
        return true;
    }

    /**
     * Creates a new log file with all saved information.
     * @return
     */
    public boolean saveSeperateLog() {
        fm.writeUniqueFile(Debugger.debugLog);
        return true;
    }

    public boolean getConsolePrintStatus() {
        return printToConsole;
    }

    // Only used for IntelliJ to compile as a library.
    public static void main(String args[]) {
        System.out.println("Initialized Debugger");
    }

}
