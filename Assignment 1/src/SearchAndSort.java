import java.io.*;
		import java.util.*;
		import java.nio.file.Files;
		import java.nio.file.Paths;


public class SearchAndSort {


	// Utility function: split a string into words,
	// making them all lowercase and ignoring all non-text characters
	public static String[] splitIntoWords(String str) {
		// Handle apostrophes: "lula's", "'top o' the mornin''"
		// Ignore any non-alphabetical characters ("1942", "1920s")
		str = str.toLowerCase();
		str = str.replaceAll("'","").replaceAll("\\s+", " ").replaceAll("[^a-zA-Z ]", " ");

		// Split on any amount of spaces
		String[] words = str.split("\\s+");
		return words;
	}

	// Load all of the words in this filename
	public static String[] createWordList(String filename) {
		try {
			String text = new String(Files.readAllBytes(Paths.get(filename)));
			return splitIntoWords(text);

		} catch (IOException ex){
			ex.printStackTrace();
		}
		return new String[0];
	}

	/*
	* ========================================================================
	* START TODO #1: "countWordsInUnsorted"
	*/
	public static int countWordsInUnsorted(String[] words, String query) {
		int counter = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(query)) {
				counter++;
			}
		}
		return counter;
	}
	/*
	* END TODO #1: "countWordsInUnsorted"
	* ========================================================================
	*/



	/*
	* ========================================================================
	* START TODO #2: "mergeSort"
	*/
	public static void mergeSort(String[] array, String[] temp, int first, int last) {
		if (first < last) {
			int mid = first + (last - first)/2;
			mergeSort(array, temp, first, mid);
			mergeSort(array, temp, mid + 1, last);
			merge(array, temp, first, last, mid);
		}
	}

	public static void merge(String[] array, String[] temp, int first, int last, int mid) {

		for (int i = 0; i < array.length; i++) {
			temp[i] = array[i];
		}
		int i = first;
		int j = mid + 1;
		int k = first;

		while((i <= mid) && (j <= last)) {
			if (temp[i].compareTo(temp[j]) <=0) {
				array[k] = temp[i];
				i++;
			} else {
				array[k] = temp[j];
				j++;
			}
			k++;
		}
		while (i <= mid) {
			array[k] = temp[i];
			k++;
			i++;
		}
	}
	/*
	* END TODO #2: "mergeSort"
	* ========================================================================
	*/



	/*
	* ========================================================================
	* START TODO #3: binary search
	*/
	public static int binarySearch(String[] sortedWords, String query, int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			return -1;
		}

		int middle = startIndex + (endIndex - startIndex)/2;

		if (sortedWords[middle].compareTo(query) == 0) {
			return middle;
		} else if ((sortedWords[middle].compareTo(query)) < 0) {
			return binarySearch(sortedWords, query, middle + 1, endIndex);
		} else {
			return binarySearch(sortedWords, query, startIndex, middle - 1);
		}
	}

	public static int getSmallestIndex(String[] words, String query, int startIndex, int endIndex) {
		if (startIndex > endIndex)
			return -1;

		int index = binarySearch(words,query,startIndex,endIndex);

		if (index == -1)
			return -1;

		int smallestIndex = binarySearch(words, query, startIndex, index - 1);

		if (smallestIndex == -1)
			return index;

		return smallestIndex;


		/*if (index == -1) {
			return -1;
		} else if (!words[index].equals(query)) {
			return index + 1;
		} else {
			return getSmallestIndex(words,query, startIndex, index - 1);
		}*/
	}

	public static int getLargestIndex(String[] words, String query, int startIndex, int endIndex) {
		if (endIndex < startIndex)
			return -1;

		int index = binarySearch(words, query, startIndex, endIndex);

		if (index == -1)
			return -1;

		int largestIndex = binarySearch(words, query, index + 1, endIndex);

		if (largestIndex == -1)
			return index;

		return largestIndex;

		/*if (index == -1) {
			return -1;
		} else if (!words[index].equals(query)) {
			return index - 1;
		} else {
			return getLargestIndex(words, query, index + 1, endIndex);
		}*/
	}
	/*
	* END TODO #3: binary search
	* ========================================================================
	*/


	public static void main(String []args) {

		// Create a word list from Frankenstein
		String[] allWords = createWordList("frankenstein.txt");

		// Save the arguments

		String[] queryWords = {"doctor", "frankenstein", "the", "monster", "igor", "student", "college", "lightning", "electricity", "blood", "soul"};
		int timingCount = 100;

		if (args.length > 0) {
			// There is an argument, so some different words to search for and count were passed in.
			queryWords = args[0].split(",");
		}


		System.out.println("\nSEARCH AND SORT");
		System.out.println("\nSearching and counting the words " + String.join(",", queryWords));

		System.out.println("\nNAIVE SEARCH:");


		// Record the current time
		long t0 = (new Date()).getTime();

		// Time how long it takes to run timingCount loops
		//   for countWordsInUnsorted
		for (int j = 0; j < timingCount; j++) {
			// Search for and count the words timingCount times in order to get an average time

			for (int i = 0; i < queryWords.length; i++) {
				//

				/*
				* ========================================================================
				* 	START: TODO #1
				*/
				int count = countWordsInUnsorted(allWords, queryWords[i]);
				/*
				* 	END: TODO #1
				* ========================================================================
				*/

				// For the first time the words are counted, print out the value
				if (j == 0)
					System.out.println(queryWords[i] + ":" + count);

			}
		}

		// Record the current time
		long t1 = (new Date()).getTime();

		long timeToSeachNaive = t1 - t0;
		int searchCount = timingCount*queryWords.length;

		// Output how long the searches took, for how many searches
		// (remember: searches = timingcount * the number of words searched)
		System.out.printf("%d ms for %d searches, %f ms per search\n", timeToSeachNaive, searchCount, timeToSeachNaive*1.0f/searchCount);

		// Sort the list of words
		System.out.println("\nSORTING: ");

		/*
		* ========================================================================
		* 	START: TODO #2
		*/
		mergeSort(allWords, new String[allWords.length], 0, allWords.length-1);
		/*
		* 	END: TODO #2
		* ========================================================================
		*/

		// Record the current time
		long t2 = (new Date()).getTime();

		// Output how long the sorting took
		long timeToSort = t2 - t1;
		System.out.printf("%d ms to sort %d words\n", timeToSort, allWords.length);

		// Output every 1000th word of your sorted wordlist
		int step = (int)(allWords.length*.00663 + 1);
		System.out.print("\nSORTED (every " + step + " word): ");
		for (int i = 0; i < allWords.length; i++) {
			if (i%step == 0)
				System.out.print(allWords[i] + " ");
		}
		System.out.println("\n");


		System.out.println("BINARY SEARCH:");

		// Run timingCount loops for countWordsInSorted
		// for the first loop, output the count for each word

		for (int j = 0; j < timingCount; j++) {
			for (int i = 0; i < queryWords.length; i++) {

				/*
				* ========================================================================
				* 	START: TODO #3
				*/
				int count = 0;

				int smallest = getSmallestIndex(allWords, queryWords[i], 0, allWords.length - 1);
				int largest = getLargestIndex(allWords, queryWords[i], 0, allWords.length - 1);

				if (smallest == -1)
					count = 0;
				else
					count = largest - smallest + 1;

				/*
				* 	END: TODO #3
				* ========================================================================
				*/

				// For the first one, print out the value
				if (j == 0)
					System.out.println(queryWords[i] + ":" + count);
			}
		}

		// Output how long the searches took, for how many searches
		// (remember: searchCount = timingcount * the number of words searched. This is computed above.)

		// Record the current time
		long t3 = (new Date()).getTime();

		long timeToSeachBinary = t3 - t2;
		System.out.printf("%d ms for %d searches, %f ms per search\n", timeToSeachBinary, searchCount, timeToSeachBinary*1.0f/searchCount);
	}
}