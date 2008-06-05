/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.commons.cli.*;
import org.apache.commons.io.*;
import org.apache.commons.lang.math.*;

import com.swsoft.trial.common.Address;
import com.swsoft.trial.common.Device;
import com.swsoft.trial.util.*;

/**
 * Client application entry point.
 * 
 * @author Kuznetsov A.
 * @version 1.0
 */
public class Main {
	
	private static void printHelp(OutputStream os) throws IOException {
		ResourceUtil.printResource(os, HELP_RESOURCE);
	}
	
	private static void printVersion(OutputStream os) throws IOException {
		ResourceUtil.printResource(os, VERSION_RESOURCE);
	}
	
	public static void main(String[] args) {
		
		PrintStream out = System.out;
		PrintStream err = System.err;

		try {

			if (args.length==0) {
				printHelp(out);
				return;
			}
		
			Options options = new Options();
		
			options.addOption(ShortOpt.HOST,	LongOpt.HOST, 		true,	null);
			options.addOption(ShortOpt.PORT, 	LongOpt.PORT, 		true, 	null);
			options.addOption(ShortOpt.LOOP, 	LongOpt.LOOP, 		false, 	null);
			options.addOption(ShortOpt.PSEUDO, 	LongOpt.PSEUDO, 	false, 	null);
			options.addOption(ShortOpt.VERSION, LongOpt.VERSION,	false, 	null);
			options.addOption(ShortOpt.HELP, 	LongOpt.HELP, 		false, 	null);

			CommandLineParser parser = new BasicParser();
			CommandLine cmd = parser.parse(options, args);

			boolean pseudo 	= cmd.hasOption(ShortOpt.PSEUDO);
			boolean version = cmd.hasOption(ShortOpt.VERSION);
			boolean help	= cmd.hasOption(ShortOpt.HELP);
			boolean host	= cmd.hasOption(ShortOpt.HOST);
			boolean port	= cmd.hasOption(ShortOpt.PORT);
			boolean loop	= cmd.hasOption(ShortOpt.LOOP);

			if (version) {
				printVersion(out);
			}
		
			if (help) {
				printHelp(out);
			}

			String serverAddress 	= null;
			int serverPort  		= DEFAULT_PORT;

			if (port) {
				serverPort = NumberUtils.toInt(cmd.getOptionValue(ShortOpt.PORT), -1);
				if (!PORT_RANGE.containsInteger(serverPort)) {
					err.println(ILLEGAL_PORT_ERROR);
					return;
				}
			}
		
			if (host) {
				serverAddress = cmd.getOptionValue(ShortOpt.HOST);
				verifyFileSystems(serverAddress, serverPort, pseudo, loop, out, err);
				return;
			} else {
				if (pseudo || port || loop) {
					err.println(NOTHING_TO_DO);	
				}
			}
			
		} catch(ParseException e) {
			err.println(INVALID_ARGS);
		} catch(UnknownHostException e){
			err.println("Specified server host not found!");
		} catch(IOException e) {
			err.println(e.getMessage());
		}
	}

	private static void verifyFileSystems(String host, int port, boolean pseudo, boolean loop, PrintStream out, PrintStream err) throws IOException {
		
		Socket sock = null;
		InputStream response = null;
		
		try{
			
			Collection<Address> addr 		= NetworkInfoCollector.getActiveNetworkAddresses(loop);
			Collection<String> desiredFS 	= FSInfoCollector.getSupportedFileSystems(pseudo);
			Collection<Device> devs 		= FSInfoCollector.getMountedDevices(desiredFS);
			
			sock = new Socket(host, port);
			
			OutputStream os = sock.getOutputStream();
			Request.perform(devs, addr, os);
			
			os.flush();
			sock.shutdownOutput();
			
			response = sock.getInputStream();

			showParsed(response, out);
			
			response.close();
			sock.close();
			
		} finally {
			IOUtils.closeQuietly(response);
			SocketUtils.closeQuietly(sock);
		}
	}
	
	private static void showParsed(InputStream is, PrintStream out) throws IOException {

		Collection<String> devices = Response.getIllegalDeviceNames(is);
		
		if (devices==null) {
			out.println("Server doesn't have any information about this host.");
			return;
		}
		
		if (devices.size()>0) {
			out.println(ODD_DEVICES);
			for (String device: devices) {
				out.println(device);
			}
		} else {
			out.println(NO_ODD_DEVICES);
		}
		
	}
	
	private final static int 		MIN_PORT		= 1;
	private final static int 		MAX_PORT 		= 65535;
	private final static int 		DEFAULT_PORT 	= 1888;
	private final static IntRange 	PORT_RANGE 		= new IntRange(MIN_PORT, MAX_PORT);
	
	private final static String 	HELP_RESOURCE		= "help.txt";
	private final static String 	VERSION_RESOURCE	= "version.txt";
	private final static String 	INVALID_ARGS		= "Invalid command line arguments, try --help for help, please";
	private final static String 	NOTHING_TO_DO		= "Server host not specified, try --help for help, please.";
	
	private final static String 	ILLEGAL_PORT_ERROR	= "Port must be integer between " + 
														  MIN_PORT + " and " + MAX_PORT;
	private final static String 	ODD_DEVICES			= "These devices have odd file systems:";
	private final static String 	NO_ODD_DEVICES		= "No odd devices been detected";
	
	private final static class ShortOpt{
		public final static String HOST		= "H";
		public final static String PORT   	= "P";
		public final static String LOOP   	= "l";
		public final static String SECURE 	= "s";
		public final static String PSEUDO 	= "d";
		public final static String VERSION 	= "v";
		public final static String HELP	 	= "h";
	}
	
	private final static class LongOpt{
		public final static String HOST		= "host";
		public final static String PORT   	= "port";
		public final static String LOOP   	= "loop";
		public final static String SECURE 	= "secure";
		public final static String PSEUDO 	= "dummy";
		public final static String VERSION 	= "version";
		public final static String HELP	 	= "help";
	}
	
}
