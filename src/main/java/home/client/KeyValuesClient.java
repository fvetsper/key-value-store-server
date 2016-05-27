package home.client;

import java.util.Arrays;

import home.commons.Action;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class KeyValuesClient {
	
	private static Options options;
	
	static {
		options = new Options();
		options.addOption(
				Option.builder("gak")
				.longOpt("get-all-keys")
                .desc( "returns all keys matching pattern." )
                .hasArg()
                .argName("pattern")
                .build());
		options.addOption(
				Option.builder("ra")
                .longOpt("right-add")
				.desc( "adds a value V to key K, from the right." )
                .numberOfArgs(2)
                .argName("K> <V")
                .build());
		options.addOption(
				Option.builder("la")
                .longOpt("left-add")
				.desc( "adds a value V to key K, from the left." )
                .numberOfArgs(2)
                .argName("K> <V")
                .build());
		options.addOption(
				Option.builder("s")
                .longOpt("set")
				.desc( "set a list V1, V2,... by its key K." )
                .hasArgs()
                .argName("K> <V1> <V2> <...")
                .build());
		options.addOption(
				Option.builder("g")
                 .longOpt("get")
				.desc( "gets a list by its key K." )
                .hasArg()
                .argName("K")
                .build());
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			usage();
			return;
		}
		
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("gak")) {
				String optionValue = cmd.getOptionValue("gak");
				Request request = new Request(Action.GET_ALL_KEYS);
				request.setPattern(optionValue);
				String host = cmd.getArgs()[0];
				String port = cmd.getArgs()[1];
				
				KeyValuesClientConnection keyValuesClientConnection = 
						new KeyValuesClientConnection(host, Integer.valueOf(port));
				Response response = keyValuesClientConnection.send(request);
				if (response != null) {
					response.print();
				}
			}
			if (cmd.hasOption("ra")) {
				String[] optionValues = cmd.getOptionValues("ra");
				
				if (optionValues.length != 2) {
					usage();
					return;
				}
				
				String host = cmd.getArgs()[0];
				String port = cmd.getArgs()[1];
				
				Request request = new Request(Action.RIGHT_ADD);
				request.setKey(optionValues[0]);
				request.setValue(optionValues[1]);
				
				KeyValuesClientConnection keyValuesClientConnection = 
						new KeyValuesClientConnection(host, Integer.valueOf(port));
				keyValuesClientConnection.send(request);
				
			}
			if (cmd.hasOption("la")) {
				String[] optionValues = cmd.getOptionValues("la");
				
				if (optionValues.length != 2) {
					usage();
					return;
				}
				
				String host = cmd.getArgs()[0];
				String port = cmd.getArgs()[1];
				
				Request request = new Request(Action.LEFT_ADD);
				request.setKey(optionValues[0]);
				request.setValue(optionValues[1]);
				
				KeyValuesClientConnection keyValuesClientConnection = 
						new KeyValuesClientConnection(host, Integer.valueOf(port));
				keyValuesClientConnection.send(request);
			}
			if (cmd.hasOption('s')) {
				String[] optionValues = cmd.getOptionValues('s');
				
				if (optionValues.length < 2) {
					usage();
					return;
				}
				
				String host = cmd.getArgs()[0];
				String port = cmd.getArgs()[1];
				
				Request request = new Request(Action.SET);
				request.setKey(optionValues[0]);
				request.setValues(Arrays.asList(Arrays.copyOfRange(optionValues, 1, optionValues.length)));
				
				KeyValuesClientConnection keyValuesClientConnection = 
						new KeyValuesClientConnection(host, Integer.valueOf(port));
				keyValuesClientConnection.send(request);
			}
			if (cmd.hasOption('g')) {
				String optionValue = cmd.getOptionValue('g');
				
				String host = cmd.getArgs()[0];
				String port = cmd.getArgs()[1];
				
				Request request = new Request(Action.GET);
				request.setKey(optionValue);
				
				KeyValuesClientConnection keyValuesClientConnection = 
						new KeyValuesClientConnection(host, Integer.valueOf(port));
				Response response = keyValuesClientConnection.send(request);
				if (response != null) {
					response.print();
				}
			}
			
		} catch (ParseException e) {
			usage();
		}
	}
	

	private static void usage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("keyValuesClient [host] [port] [action] [params]", options );
	}
	
	
	
}
