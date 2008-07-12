package main;

import gui.mainframe.*;
import gui.GUIManager;

import descriptors.SingletonBootDescriptor;
import service.install.InstallationController;
import service.install.ZipJarPackageInstaller;
import service.install.InstallerDBManager;
import exceptions.DBWrapperException;
import config.Config;
import storage.dbclean.DBCleaner;
import logs.ILogger;
import logs.SingletonLogger;
import logs.MessageTypes;
import logs.WriterTypes;

import java.io.File;

/**
 * This is a server launcher.
 * @author LocalStorm
 * @version 0.9
 * @since 0.9
 */
@Annotation
public class Main {

    private static void w(String s) {
        System.out.println(s);
    }

    private static class Goto extends Exception {
        List<String> list;
	public Goto(String message) {
            super(message);
        }
    }

	/**
	 * This is an entry point to server. It creates an instance of <b><i>gui.mainframe.MainFrame</i></b> and shows it.
	 * @see MainFrame
	 * @param args No parameters required
	 */
	public static void main(String[] args)
	{
		try{
            String lanch = null;
            boolean nogui = false;
            boolean scheme = false;
            boolean imp = false;

            w("To read about command line options launch with --help");
            for (int i = 0; i < args.length; i++) {
                if ("--cleandb".equalsIgnoreCase(args[i]) || "-c".equalsIgnoreCase(args[i])) {
                    if (imp) {
                        throw new Goto("--cleandb (or -c) key must be before --import (or -i) key");
                    }
                    DBCleaner cleaner = new DBCleaner(Config.Folders.DB_PATH);
                    cleaner.cleanAll();
                    cleaner.close();
                    continue;
                }
                if ("--scheme".equalsIgnoreCase(args[i]) || "-s".equalsIgnoreCase(args[i])) {
                    if (i + 1 >= args.length) {
                        throw new Goto("Argument " + args[i] + " must have a following parameter (launch scheme name).");
                    }
                    scheme = true;
                    lanch = args[i + 1];
                    i++;
                    continue;
                }
                if ("--nogui".equalsIgnoreCase(args[i]) || "-n".equalsIgnoreCase(args[i])) {
                    nogui = true;
                    continue;
                }
                if ("--import".equalsIgnoreCase(args[i]) || "-i".equalsIgnoreCase(args[i])) {
                    if (i + 1 >= args.length) {
                        throw new Goto("Argument " + args[i] + " must have a following parameter (directory name).");
                    }
                    InstallerDBManager.getInstance().importPCDFFromDirectory(new File(args[i+1]));
                    i++;
                    imp = true;
                    continue;
                }
                if ("--help".equalsIgnoreCase(args[i]) || "-h".equalsIgnoreCase(args[i])) {
                    w(" ---========== Eskimo Server, v1.0 ==========---");
                    w("");
                    w("Launch syntax: java -classpath ./classes;./je.jar main.Main [keys]");
                    w("");
                    w("Keys:");
                    w("\n\t--cleandb or -c \n\t\tif you want to clean database before starting. " +
                            "\n\t\tCleaning DB means removing unused records from DB in order to " +
                            "\n\t\tminimize its size. Cleaning DB is a sufficiently powerful " +
                            "\n\t\toperation, which requests time, especially if the database " +
                            "\n\t\tis big." +
                            "\n\t\tATTENTION: --cleandb key doesn't work, if --import key " +
                            "\n\t\tis defined before it. So if you want to use both " +
                            "\n\t\t--import and --cleandb keys, you must put --cleandb " +
                            "\n\t\tbefore --import");
                    w("\n\t--scheme <launchscheme name> or -s <launchscheme name>\n\t\t" +
                            "with the help of this option you can define the launchscheme " +
                            "\n\t\tto load. Boot manager window won't appear in that case.");
                    w("\n\t--import <directory name> or -i <directory name>\n\t\t" +
                            "with the help of this key you can forcibly import " +
                            "\n\t\tsome pcdf-files into database. It may be useful, " +
                            "\n\t\tif the database crashed, and you want to restore it. " +
                            "\n\t\tIn this case you have to import pcdf directory from " +
                            "\n\t\tserver root folder." +
                            "\n\t\tThis option imports all valid pcdf-files from the given " +
                            "\n\t\tdirectory into database." +
                            "\n\t\tATTENTION: --cleandb key doesn't work, if --import key " +
                            "\n\t\tis defined before it. So if you want to use both " +
                            "\n\t\t--import and --cleandb keys, you must put --cleandb " +
                            "\n\t\tbefore --import");
                    w("\n\t--nogui or -n " +
                            "\n\t\tif you want to start the server without graphical " +
                            "\n\t\tuser interface. In that case you have to use " +
                            "\n\t\tEskimo Remoting plugin " +
                            "\n\t\tif you want to control the server. Also you " +
                            "\n\t\thave to use --scheme (or -s) " +
                            "\n\t\tkey with --nogui (or -n) key.");
                    return;
                }
                throw new Goto("Incorrect key: " + args[i]);
            }

            InstallationController ic = ZipJarPackageInstaller.getInstance();
            ic.ensureLoggerInstalled();
            ic.ensureGroupInstalled();

            if (!nogui) {
                if (!scheme) {
                    GUIManager.getInstance().showBootFrame();
                }
                else {
                    SingletonBootDescriptor.getInstance().launch( lanch, false );
                    GUIManager.getInstance().showMainFrame( Config.GUI.Titles.MAIN_FRAME);
                }
            }
            else {
                if (!scheme) {
                    throw new Goto("You must define lauchscheme, if you want to load server without GUI");
                }
                SingletonBootDescriptor.getInstance().launch( lanch, false );
            }

        }
        catch (Goto e) {
            ILogger log = SingletonLogger.getInstance().getLogger(WriterTypes.SERVER_TYPE,
                    "main");
            log.unsafeLog(MessageTypes.ERROR, e.getMessage());
        }
        catch(DBWrapperException e){
            ILogger log = SingletonLogger.getInstance().getLogger(WriterTypes.SERVER_TYPE,
                    "main");
            log.unsafeLog(MessageTypes.ERROR, Config.ErrorMessages.CommonExceptionsMessages.DATABASE_ACCESS_FAILED+" Normal work impossible now.\n Probably multiple instances of server launched.");
        }
        catch(Exception e){
            ILogger log = SingletonLogger.getInstance().getLogger(WriterTypes.SERVER_TYPE,
                    "main");
			log.unsafeLog(MessageTypes.ERROR, "Kernel-level exception: "+e);
            e.printStackTrace();
 		}
	}

}
