import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;

public class SimpleEmbedded { 
    private static ArrayList<String> getBlacklist() {
        ArrayList<String> fileNames = new ArrayList();
        try {
            try (Stream<Path> paths = Files.walk(Paths.get("C:\\Program Files\\DyKnow\\"))) {
        paths
            .filter(f -> f.toFile().getName().toLowerCase().endsWith(".exe"))
            .forEach(f -> fileNames.add(f.toFile().getName()));
            } 
        } catch (IOException e) {
        }
        return fileNames;
    }
    
    public static void main(String []args) throws InterruptedException { 
        String procName = "";
        Runtime r = Runtime.getRuntime();
        Process proc = null;
            while (true) {
                try {
                    proc = r.exec("tasklist");
                } catch (IOException e) {
                    System.out.println("Error getting process list");
                }
                Scanner is = new Scanner(new InputStreamReader(proc.getInputStream()));
                ArrayList<String> blacklist = getBlacklist();
                while (is.hasNextLine()) {
                    procName = is.next();
                    is.nextLine();
                    if (blacklist.contains(procName)) { 
                        try {
                            r.exec("taskkill /F /IM " + procName);
                            System.out.println("Killed b'" + procName + "'!");
                        } catch (IOException e) {
                            System.out.println("Error killing process '" + procName + "'");
                        }
                    }
                }
                Thread.sleep(30);
        }
    }
}