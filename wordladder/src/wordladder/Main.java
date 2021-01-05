package wordladder;

import java.io.*;
import java.util.*;

/**
 * program to find word ladder with shortest path (i.e. minimum number edges
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();

		String inputFileName = args[0]; // dictionary
		String word1 = args[1]; // first word
		String word2 = args[2]; // second word

		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);

		ArrayList<String> data = new ArrayList<>();

		//add each word to the array list 
		while (in.hasNext())
			data.add(in.next());

		reader.close();

		// generate a graph with size of the words read
		Graph graph = new Graph(data.size());

		// loop through all the words and add them to an adjacency list if
		// the difference between them is one character
		for (int i = 0; i < data.size(); i++)
			for (int j = 0; j < data.size(); j++)
				if ((i != j) && (diff(data.get(i), data.get(j))))
					graph.getVertex(i).addToAdjList(j);

		int startI = data.indexOf(word1);
		int endI = data.indexOf(word2);

		// breadth-first search using word1 as the beginning
		graph.bfs(graph.getVertex(startI));

		LinkedList<String> ladder = new LinkedList<>();

		int cur = endI;
		int predecessor = graph.getVertex(cur).getPredecessor();

		// if the predecessor of the word its not the same as itself
		// add it to the ladder and move to it's predecessor
		while (predecessor != cur) {
			ladder.add(data.get(cur));
			cur = predecessor;
			predecessor = graph.getVertex(cur).getPredecessor();
		}

		ladder.add(word1);

		// if the final word's predecessor is not itself
		// print the size of the ladder and the ladder
		if (graph.getVertex(endI).getPredecessor() != endI) {
			System.out.println("Ladder length: " + (ladder.size() - 1));
			while (!ladder.isEmpty())
				System.out.println(ladder.pollLast());
		}
		// if there was no possible ladder
		else
			System.out.println("There is no ladder possible between " + word1 + " and " + word2);


		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}

	// a method to compare the two given words and see
	// if they differ by one character
	private static boolean diff(String w1, String w2) {
		int diff = 0;

		for (int i = 0; i < w1.length(); i++)
			if (w1.charAt(i) != w2.charAt(i))
				diff++;
		if (diff == 1)
			return true;
		return false;
	}

}
