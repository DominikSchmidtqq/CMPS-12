import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TraceryRecursion {



	public static void saveFile(String fileName, String text) {
		try {
			Files.write(Paths.get(fileName), text.getBytes());
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}

	public static void outputGrammar(Hashtable<String, Rule[]> grammar) {
		System.out.println("\nGRAMMAR:");
		for ( String key : grammar.keySet() ) {
			String line = "";
			line += key + ": " + String.format("%1$" + (20 - key.length()) + "s", " ");
			for (Rule rule : grammar.get(key)) {
				line += "\"" + rule + "\"," ;
			}

			System.out.println(line);
		}
	}

	// Given a path, load the grammar at that path
	public static Hashtable<String, Rule[]> loadGrammar(String path) {
		Hashtable<String, Rule[]> grammar = new Hashtable<String, Rule[]>();
		try {
			// Creating an array of strings, one for each line in the file
			String[] lines = new String(Files.readAllBytes(Paths.get(path))).split("\\r?\\n");
			
			// Print out the lines to verify that the file has been read
			for(String line : lines) 
				System.out.println(line);

			/*
			* START: TO DO #2
			*/
			for (String line : lines) {
				String[] rules = new String(line).split(":");
				String key = new String(rules[0]);

				String expansion = new String(rules[1]);
				String[] rule = new String(expansion).split(",");
				Rule[] foo = new Rule[rule.length];

				for (int i = 0; i < foo.length; i++) {
					foo[i] = new Rule(rule[i]);
				}

				grammar.put(key, foo);
			}
			/*
			* END: TO DO #2
			*/

		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return grammar;
	}



	public static void main(String []args) throws IOException  {
		System.out.println("Running TraceryRecursion...");

		String grammarFile = "grammar-story.txt"; // The default grammar file to load if none is specified in the args

		// The default symbol to begin expanding if none is specified in the args. This argument has to be in quotes. 
		// So if you were specifying a grammar and a startSymbol named #start# the command to run your program would 
		// look like: java TraceryRecursion my-grammar.txt "#start#"
		String startSymbol = "#origin#"; 

		int count = 1; // The default number of times to expand the starting symbol if this is not specified in the args 
		long seed = System.currentTimeMillis() % 1000; // The default random seed if not specified in the args

		/*
		* START: TO DO #1
		*/
		switch (args.length) {

			case 0:
				break;
			case 1:
			if (args[0].length() > 0) {
				grammarFile = args[0];
			}

			if (args[1].length() > 0) {
				startSymbol = args[1];
			}

			if (args[2].length() > 0) {
				count = Integer.parseInt(args[2]);
			}

			if (args[3].length() > 0) {
				seed = Long.parseLong(args[3]);
			}
		}
		/*
		* END: TO DO #1
		*/

		System.out.printf("\twith grammar:'%s'\tstartsymbol:'%s' \tcount:%d\t seed:%d\n", grammarFile, startSymbol, count, seed);
		Rule.setSeed(seed); // Set the seed using a static method defined on Rule
		Hashtable<String, Rule[]>grammar = loadGrammar(grammarFile); // Load the grammar from the grammar file
		outputGrammar(grammar); // Print out the loaded grammar 

		Rule rule = new Rule(startSymbol); // Create a new Rule object using the startSymbol
		for (int i = 0; i < count; i++) { 
			// Expand the start symbol until there are no more symbols to expand. Do this 'count' number of times. 
			System.out.println(rule.expand(grammar));
		}
	}
}
