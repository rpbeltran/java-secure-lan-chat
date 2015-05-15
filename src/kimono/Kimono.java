package kimono;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Kimono {
	
	public static final String VERSION = Double.toString(Math.PI);
	
	private static Options options = new Options();
	
	public static void main(String args[]) {
		
		options.addOption("n", "no-gui", false, "Runs without GUI");
		options.addOption("h", "help", false, "Shows help");
		options.addOption("s", "server", false, "Starts server");
		options.addOption("u", "username", true, "Sets username");
		options.addOption("p", "password", true, "Sets password");
		options.addOption("H", "host", true, "Sets hostname to connect to. Default: localhost");
		options.addOption("P", "port", true, "Sets port to connect to. Default: 32800");
		CommandLineParser parser = new BasicParser();
		
		boolean headless = false;
		boolean server = false;
		String u = "";
		String p = "";
		String host = "127.0.0.1";
		int port = 32800;
		
		try {
			
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("h")) {
				help();
			}
			
			if (cmd.hasOption("n")) {
				headless = true;
			}
			
			if (cmd.hasOption("s")) {
				server = true;
			}
			
			if (cmd.hasOption("u")) {
				u = cmd.getOptionValue("u");
			}
			
			if (cmd.hasOption("p")) {
				p = cmd.getOptionValue("p");
			}
			
			if (cmd.hasOption("H")) {
				host = cmd.getOptionValue("H");
			}
			
			if (cmd.hasOption("P")) {
				try {
					port = Integer.parseInt(cmd.getOptionValue("P"));
				} catch(NumberFormatException e) {
					System.out.println("Invalid port!");
					System.exit(1);
				}
				
			}
			
		} catch (ParseException e) {
			help();
		}
		
		final boolean fserver = server;
		final String fu = u;
		final String fp = p;
		final String fhost = host;
		final int fport = port;
		
		if(headless) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new CommandLineInterface(fserver, fu, fp, fhost, fport);
				}
			});
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new GraphicalInterface(fu,fp,fhost,fport);
				}
			});
		}
	}
	
	private static void help() {
		
		// This prints out some help
		
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		
		System.exit(0);

	}

}
