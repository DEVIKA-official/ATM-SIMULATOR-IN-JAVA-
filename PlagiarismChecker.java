import java.io.*;
import java.util.*;

public class PlagiarismChecker {

    // Normalize text (lowercase + trimmed)
    public static String normalize(String line) {
        return line.toLowerCase().trim().replaceAll("[^a-zA-Z0-9\\s]", "");
    }

    // Read lines from file
    public static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                lines.add(normalize(line));
            }
        }
        br.close();
        return lines;
    }

    // Compare lines and return matching lines
    public static List<String> getMatchingLines(List<String> file1Lines, List<String> file2Lines) {
        Set<String> file2Set = new HashSet<>(file2Lines);
        List<String> matched = new ArrayList<>();

        for (String line : file1Lines) {
            if (file2Set.contains(line)) {
                matched.add(line);
            }
        }

        return matched;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter path for File 1: ");
            String file1 = sc.nextLine();

            System.out.print("Enter path for File 2: ");
            String file2 = sc.nextLine();

            List<String> file1Lines = readFile(file1);
            List<String> file2Lines = readFile(file2);

            List<String> matchingLines = getMatchingLines(file1Lines, file2Lines);

            double similarityPercent = ((double) matchingLines.size() / file1Lines.size()) * 100;

            System.out.println("\n📊 Similarity Report:");
            System.out.println("Total Lines in File 1: " + file1Lines.size());
            System.out.println("Matching Lines: " + matchingLines.size());
            System.out.printf("Similarity: %.2f%%\n", similarityPercent);

            System.out.println("\n🔍 Matching Lines:");
            for (String line : matchingLines) {
                System.out.println("• " + line);
            }

            // Optional: write to report.txt
            FileWriter writer = new FileWriter("similarity_report.txt");
            writer.write("Similarity Report\n");
            writer.write("Similarity: " + String.format("%.2f", similarityPercent) + "%\n");
            writer.write("Matching Lines:\n");
            for (String line : matchingLines) {
                writer.write("• " + line + "\n");
            }
            writer.close();

            System.out.println("\n✅ Report saved as 'similarity_report.txt'");

        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }

        sc.close();
    }
}
