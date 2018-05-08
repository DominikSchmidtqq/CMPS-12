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
			string += o;
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

		//Convert the string to lowercase, make a new stack and a new queue
		MyStack stack = new MyStack();
		MyQueue queue = new MyQueue();
		text = text.toLowerCase();

		//loop through text, add an element of text to stack and queue if the element is alphabetic
		for (int i = 0; i < text.length(); i++) {
			char currentChar = (Character)text.charAt(i);
			if (Character.isAlphabetic(currentChar)) {
				stack.push(currentChar);
				queue.enqueue(currentChar);
			}
		}

		//create one new string for both the stack and the queue
		String queueString = new String();
		String stackString = new String();

		//add each element in stack and queue to the corresponding string
		while (!queue.isEmpty() || !stack.isEmpty()) {
			queueString += queue.dequeue();
			stackString += stack.pop();
		}
		System.out.println(queueString +" "+stackString);

		//return whether or not the strings are equal and therefore checks if palindrome
		return queueString.equals(stackString);
		/* 
		* TODO 5: Implement "explorePalindrome"
		*/
	}



	//lists all possible endings that would make a string a palindrome
	public static void explorePalindrome(String text) {

	/* 
	* TODO 6: Implement "explorePalindrome" & helper function
	*/

	//convert text to lowercase and call the decomposition function
	text = text.toLowerCase();
	String reversedText = new String(reverseStringAndRemoveNonAlpha(text));
	decomposeText(text, reversedText, 0, new MyStack());

	/* 
	* TODO 6
	*/

	}

	//recursive helper function that that decomposes the string from explorePalindrome into words
	public static void decomposeText(String originalText, String textToDecompose, int index, MyStack decomposition) {
		if (index == originalText.length()) {
			System.out.println(originalText + ": " + stackToReverseString(decomposition));
		} else {
			String[] possiblePalindromes = getWords(textToDecompose, index);
			for (int i = 0; i < possiblePalindromes.length; i++) {
				decomposition.push(possiblePalindromes[i]);
				int lengthOfPalindrome = possiblePalindromes[i].length();
				decomposeText(originalText, textToDecompose, index + lengthOfPalindrome, decomposition);
				decomposition.pop();
			}
		}
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