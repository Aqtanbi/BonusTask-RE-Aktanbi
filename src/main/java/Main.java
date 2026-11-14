import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static String generateRandomString(int length, Random rand) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + rand.nextInt(26)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] patternCounts = {10, 100, 1000};
        int[] textLengths = {1000, 10000, 100000};
        Random rand = new Random(42);

        System.out.printf("%-10s | %-12s | %-18s | %-18s%n", "Patterns", "Text Length", "Aho-Corasick (ms)", "KMP (ms)");
        System.out.println("------------------------------------------------------------------");

        for (int pCount : patternCounts) {
            List<String> patterns = new ArrayList<>();
            for (int i = 0; i < pCount; i++) {
                patterns.add(generateRandomString(5 + rand.nextInt(6), rand));
            }

            for (int tLen : textLengths) {
                String text = generateRandomString(tLen, rand);
                long acTotalTime = 0;
                long kmpTotalTime = 0;

                for (int run = 0; run < 5; run++) {
                    long start = System.nanoTime();
                    AhoCorasick ac = new AhoCorasick();
                    for (int i = 0; i < patterns.size(); i++) {
                        ac.insert(patterns.get(i), i);
                    }
                    ac.build();
                    ac.search(text);
                    acTotalTime += (System.nanoTime() - start);

                    start = System.nanoTime();
                    for (String p : patterns) {
                        KMP.search(text, p);
                    }
                    kmpTotalTime += (System.nanoTime() - start);
                }

                double acAvgMs = (acTotalTime / 5.0) / 1_000_000.0;
                double kmpAvgMs = (kmpTotalTime / 5.0) / 1_000_000.0;

                System.out.printf("%-10d | %-12d | %-18.4f | %-18.4f%n", pCount, tLen, acAvgMs, kmpAvgMs);
            }
        }
    }
}