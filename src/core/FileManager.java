package core;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class FileManager {

    private static String directory = System.getProperty("user.home") + File.separator + "logs";
    private String fileName;
    private static String absolutePath;

    /**
     * This is only used for the Debugger to save the created log file
     * @param filename - Name of the file to create/write to.
     */
    public FileManager(String filename) {
        this.fileName = filename;
        absolutePath = directory + File.separator + fileName + ".log";
    }

    /**
     * Creates the directory if it does not exist, then sets the save path.
     * @return - Returns true once successful.
     */
    public FileManager init() {
        new File(directory).mkdirs();
        FileManager.absolutePath = directory + File.separator + fileName + ".log";
        return this;
    }

    public FileManager setFileLocation(String Directory) {
        FileManager.directory = Directory;
        init();
        return this;
    }

    /**
     * Creates and writes the passed array of strings.
     * @param printed - An array of strings to be printed, each string is a single line.
     */
    public void writeFile(ArrayList<String> printed) {
        // write the content in file
        try( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath)) ) {
            for (String str : printed) {
                bufferedWriter.write(str + "\n");
            }
        } catch ( IOException e ) {
            System.out.println("IOEXCEPTION UNCAUGHT");
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Creates and writes the passed array of strings.
     * @param printed - An array of strings to be printed, each string is a single line.
     */
    public void writeUniqueFile(ArrayList<String> printed) {
        // write the content in file
        try( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory + File.separator + fileName + "_" + getUniqueFullDate() + ".log")) ) {
            for (String str : printed) {
                bufferedWriter.write(str + "\n");
            }
        } catch ( IOException e ) {
            System.out.println("ERROR ON writeUniqueFile INSIDE FileManager OF Debugger!!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a formatted time.
     * @return - A formatted string of the current time (Format: '[hh:mm:ss]')
     */
    public static String getPrintableTime() {
        Date date = new Date();
        String strDateFormat = "[hh:mm:ss]";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    /**
     * Returns a formatted date.
     * @return - A formatted string of the current time (Format: 'dd-MM-yyyy')
     */
    public static String getDate() {
        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    /**
     * Returns a date will full date and time.
     * @return - A formatted string of the current date & time (Format: 'dd-MM-yyyy_(hh-mm-ss)')
     */
    public static String getUniqueFullDate() {
        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy_(hh-mm-ss)";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }
}
