package exercise14;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import java.io.IOException;
import java.nio.file.Files;

public class FunctionalProgramming {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("Movie-Data.txt");

		// Prints all titles and their revenues over $500 mil
		Files.lines(path)
				.filter((row) -> getRevenue(row) > 500.0)
				.sorted((r1, r2) -> r1.compareTo(r2))
				.forEach((line) -> System.out.println(getTitle(line) + "  " + getRevenue(line)));

		System.out.println("");

		System.out.print("Highest revenue movie: ");
		//Prints the line that made the highest revenue
		Files.lines(path)
				.max((v1, v2) -> Double.compare(getRevenue(v1), getRevenue(v2)))
				.ifPresent((line) -> System.out.println(line));

		System.out.println("");

		//get total revenues for each decade
		Map<Integer, Double> decadeRevenue = Files.lines(path)
				.collect(
						groupingBy(FunctionalProgramming::getDecade, summingDouble(FunctionalProgramming::getRevenue)));
		System.out.println("Most revenue between 70s and 80s is: " + (decadeRevenue.get(1970) > decadeRevenue.get(1980) ? 1970 : 1980));

		//get the max revenue by studio in 1960s
		Entry<String, Double> maxRevenueByStudioIn60s = Files.lines(path)
				.filter((row) -> getDecade(row) == 1960)
				.collect(groupingBy(FunctionalProgramming::getStudio, summingDouble(FunctionalProgramming::getRevenue)))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.get();
		System.out.println("Studio with the greatest revenue in 60s: " + maxRevenueByStudioIn60s);

	}

	//Helpers//	
	private static String getTitle(String s) {
		return s.split("[|]")[0];
	}

	private static String getStudio(String s) {
		return s.split("[|]")[1];
	}

	private static Double getRevenue(String s) {
		return Double.parseDouble(s.split("[|]")[2]);
	}

	private static String getYear(String s) {
		return s.split("[|]")[3];
	}

	private static Integer getDecade(String s) {
		String decade = getYear(s).substring(0, 3);
		return Integer.parseInt(decade) * 10;
	}
}
