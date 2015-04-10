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
		
		options.addOption("n", "no-gui", false, "Runs server without GUI");
		options.addOption("h", "help", false, "Shows help");
		
		CommandLineParser parser = new BasicParser();
		
		boolean headless = false;
		
		try {
			
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("h")) {
				help();
			}
			
			if (cmd.hasOption("n")) {
				headless = true;
			}
			
		} catch (ParseException e) {
			help();
		}
		
		if(headless) {
			//Run headless server
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new FrontEnd();
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
