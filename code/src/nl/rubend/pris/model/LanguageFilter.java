package nl.rubend.pris.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class LanguageFilter {
//  Een main functie | Staat om de klasse te kunnen testen
//	public static void main(String[] arg) throws IOException {
//		System.out.println(isGeschikt(""));
//	}

	private static String bronbestand = "scheldwoorden.txt";

	private static ArrayList setScheldwoorden() throws IOException {
		ArrayList<String> scheldwoorden = new ArrayList<>();
		Path path = Path.of(bronbestand);
		BufferedReader br = Files.newBufferedReader(path);
		String regel = br.readLine();
		while (regel != null) {
			scheldwoorden.add(regel);
			regel = br.readLine();
		}
		br.close();

		return scheldwoorden;
	}

	public static boolean isGeschikt(String tekst) throws IOException {
		ArrayList<String> scheldwoorden = setScheldwoorden();
		String s = tekst.toLowerCase();
		if (scheldwoorden != null) {
			for (String woord : scheldwoorden) {
				if (s.contains(woord)) {
					return false;
				}
			}
		}
		return true;
	}

}
