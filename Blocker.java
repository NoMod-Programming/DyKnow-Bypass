import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import javax.swing.*;

public class Blocker
{
    // The instance of the tray icon
    private static TrayIcon trayIcon;

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

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setTitle("Console");

        JTextArea ta = new JTextArea();
        PrintStream ps = new PrintStream(new Echo(System.out, ta));
        System.setOut(ps);
        System.setErr( ps );

        frame.add(new JScrollPane(ta));

        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
            }
        });
        frame.setSize(800,600);
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = new ImageIcon(Blocker.class.getResource("trayIcon.png")).getImage();
            PopupMenu popup = new PopupMenu();
            MenuItem editItem = new MenuItem("DyKnow");
            editItem.setEnabled(false);
            MenuItem consoleItem = new MenuItem("Open log");
            consoleItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(true);
                }
            });
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            popup.add(editItem);
            popup.add(consoleItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon = new TrayIcon(image, "DyKnow", popup);
            trayIcon.setImageAutoSize(true);
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("SystemTray not supported! This should not happen!");
        }
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
                        r.exec("wmic /interactive:off process where \"name like '" + procName + "'\" call terminate");
                        if (!procName.equals("DyKnowDiagnosticTool.exe")) {
                            System.out.println("Killed b'" + procName + "'!");
                        }
                    } catch (IOException e) {
                        System.out.println("Error killing process '" + procName + "'");
                    }
                }
            }
            Thread.sleep(40);
        }
    }
}
