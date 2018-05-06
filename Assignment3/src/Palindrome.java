import jdk.nashorn.internal.ir.WhileNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException; 
import java.util.Arrays;
import java.util.ArrayList;

public class Palindrome {

	static WordDictionary dictionary = new WordDictionary(); 

	
	// Get all words that can be formed starting at this index
	public static String[] getWords(String text, int index) {
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i <= text.length() - index; i++) {
			String maybeWord = text.substring(index, index + i);
			if (dictionary.isWord(text.substring(index, index + i))) {
				words.add(maybeWord);
			}
		}

		return words.toArray(new String[0]);
	}

	//Reverses a String in a stack
	public static String stackToReverseString(MyStack stack) {
		/* 
		* TODO 3
		*/

		//create new String and temporary stack
		MyStack temp = new MyStack();
		String string = new String();

		//pop everything from stack into temp
		while (!stack.isEmpty()) {
			Object o = stack.pop();
			temp.push(o);
		}

		//pop everything from temp into stack
		while (!temp.isEmpty()) {
			Object o = temp.pop();
			stack.push(o);
			string += (String)o;
		}

		return string;
		/* 
		* TODO 3
		*/
	}

	//reverses a String in a stack and removes all non character elements
	public static String reverseStringAndRemoveNonAlpha(String text) {
		/* 
		* TODO 4
		*/
		MyStack stack = new MyStack();

		//check if each character in text is alphabetic, if yes adds it to the stack
		for (int i = 0; i < text.length(); i++) {
			char currentChar = (Character)text.charAt(i);
			if (Character.isAlphabetic(currentChar)) {
				stack.push(currentChar);
			}
		}

		//create new string and stack
		MyStack temp = new MyStack();
		String string = new String();

		//pop everything from stack to temp
		while (!stack.isEmpty()) {
			Object o = stack.pop();
			temp.push(o);
		}

		//pop everything from temp to stack and add it to the string
		while (!temp.isEmpty()) {
			Object o = temp.pop();
			stack.push(o);
			string += (String)o;
		}

		return string;
		/* 
		* TODO 4
		*/
	}



	// Returns true if the text is a palindrome, false if not.
	public static boolean isPalindrome(String text) {
		/* 
		* TODO 5: Implement "explorePalindrome"
		*/

		return false;
		/* 
		* TODO 5: Implement "explorePalindrome"
		*/
	}




	public static void explorePalindrome(String text) {

	/* 
	* TODO 6: Implement "explorePalindrome" & helper function
	*/
		

	/* 
	* TODO 6
	*/

	}


	// This function looks at the arguments that are passed 
	// and decides whether to test palindromes or expand them
	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("ERROR: Remember to set the mode with an argument: 'test' or 'expand'");
		} else {
			String mode = args[0];

			// Default palindromes to use if none are provided
			String[] testPalindromes = {"A", "ABBA", "oh no an oboe", "salami?", "I'm alas, a salami"};
			if (args.length > 1)
				testPalindromes = Arrays.copyOfRange(args, 1, args.length);

			// Test whether the provided strings are palindromes
			if (mode.equals("test")) {

				for (int i = 0; i < testPalindromes.length; i++) {
					String text = testPalindromes[i];
					boolean result = isPalindrome(text);
					System.out.println("'" + text + "': " + result);
				}

			} else if (mode.equals("expand")) {
				for (int i = 0; i < testPalindromes.length; i++) {

					explorePalindrome(testPalindromes[i]);
				}	
			}
			else {
				System.out.println("unknown mode: " + mode);
			}
		}
	}
}