/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.swsoft.trial.util.ResourceUtil;

/**
 * Server application entry point
 * @author localstorm
 *
 */
public class Main {

	public static void main(String[] args){
		
		final PrintStream out = System.out;
		final PrintStream err = System.err;

		try {

			Options options = new Options();
		
			options.addOption(ShortOpt.VERSION, LongOpt.VERSION,	false, 	null);
			options.addOption(ShortOpt.HELP, 	LongOpt.HELP, 		false, 	null);

			CommandLineParser parser = new BasicParser();
			CommandLine cmd = parser.parse(options, args);

			boolean version = cmd.hasOption(ShortOpt.VERSION);
			boolean help = cmd.hasOption(ShortOpt.HELP);
			
			if (version) {
				printVersion(out);
			}
		
			if (help) {
				printHelp(out);
			}
			
			if (version||help) {
				// It was information call only
				return;
			}

			final Core core = new Core(CONFIG_FILE);
			
			/*
			 * This will handle some termination signals (SIGTERM)
			 */
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					Logger logger = LogHelper.getLogger();
					logger.info("Termination signal catched. Trying to stop server...");
					core.stop();
					core.join();
					logger.info("Server stopped!");
				}
			});
				
			core.start();
			core.join();
			System.exit(0);
			
		} catch(ParseException e) {
			err.println(INVALID_ARGS);
			System.exit(1);
		} catch(IOException e) {
			Logger logger = LogHelper.getLogger();
			if (e.getCause()!=null){
				logger.error(e.getCause());
			}else{
				logger.error(e);
			}
			System.exit(2);
		} catch(Throwable e){
			e.printStackTrace();
			System.exit(3);
		}
	}

	private static void printHelp(OutputStream os) throws IOException {
		ResourceUtil.printResource(os, HELP_RESOURCE);
	}
	
	private static void printVersion(OutputStream os) throws IOException {
		ResourceUtil.printResource(os, VERSION_RESOURCE);
	}
	
	private final static String 	CONFIG_FILE			= "config.xml";
	private final static String 	HELP_RESOURCE		= "help.txt";
	private final static String 	VERSION_RESOURCE	= "version.txt";
	
	private final static String 	INVALID_ARGS		= "Invalid command line arguments, try --help for help, please";

	private final static class ShortOpt{
		public final static String VERSION 	= "v";
		public final static String HELP	 	= "h";
	}
	
	private final static class LongOpt{
		public final static String VERSION 	= "version";
		public final static String HELP	 	= "help";
	}
}
