package dijkstra;

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

		while (in.hasNext())
			data.add(in.next());

		reader.close();

		// generate a graph with size of the words read
		Graph graph = new Graph(data.size());

		// loop through all the words and add them to an adjacency list if
		// the difference between them is one character
		for (int i = 0; i < data.size(); i++)
			for (int j = 0; j < data.size(); j++)
				if ((i != j)) {
					int dist = diff(data.get(i), data.get(j));
					if (dist != 0)
						graph.getVertex(i).addToAdjList(j, dist);
				}

		int startI = data.indexOf(word1);
		int endI = data.indexOf(word2);

		// dijkstra search using word1 as start and word2 as destination
		graph.dijkstra(graph.getVertex(startI), graph.getVertex(endI));

		// if the destination word was not reached (-1 predecessor),
		// it means there is no ladder
		if (graph.getVertex(endI).getPredecessor() == -1)
			System.out.println("There is no ladder possible between " + word1 + " and " + word2 + ".");
		// ladder exist
		else {
			LinkedList<String> ladder = new LinkedList<>();

			int cur = endI;
			int predecessor = graph.getVertex(cur).getPredecessor();

			// loop through starting from the destination, add each word to the ladder
			// until start vertex is reached
			while (predecessor != -1) {
				ladder.add(data.get(cur));
				cur = predecessor;
				predecessor = graph.getVertex(cur).getPredecessor();
			}

			ladder.add(word1);

			System.out.println("Ladder length: " + (ladder.size() - 1));

			System.out.println("Ladder distance: " + graph.getVertex(endI).getDistance());

			while (!ladder.isEmpty())
				System.out.println(ladder.pollLast());
		}


		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}

	// a method to compare the two given words and see
	// if they differ by one character and returns the difference
	private static int diff(String w1, String w2) {
		int diff = 0;
		int distance = 0;

		for (int i = 0; i < w1.length(); i++)
			if (w1.charAt(i) != w2.charAt(i)) {
				distance = Math.abs(Character.getNumericValue(w1.charAt(i)) - Character.getNumericValue(w2.charAt(i)));
				diff++;
			}
		if (diff == 1)
			return distance;
		return 0;
	}

}
