package com.fuzzy.fuzzy_backend.util;

public class FuzzySearch {
    /*
    * This method calculates the edit distance between two strings.
    * The edit distance is the number of operations required to transform one string into the other.
    * The operations are insertion, deletion, substitution, and transposition.
    * The method returns true if the edit distance is less than or equal to the threshold value.
    * */
    public static boolean isSimilar(String source, String target) {
        int threshold = 2; // Maximum allowable edit distance
        return calculateDistance(source.toLowerCase(), target.toLowerCase()) <= threshold;
    }

    public static int calculateDistance(String a, String b) {
        int[][] distance = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) distance[i][0] = i;
        for (int j = 0; j <= b.length(); j++) distance[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                distance[i][j] = Math.min(Math.min(
                                distance[i - 1][j] + 1,      // Deletion
                                distance[i][j - 1] + 1),     // Insertion
                        distance[i - 1][j - 1] + cost); // Substitution

                if (i > 1 && j > 1 && a.charAt(i - 1) == b.charAt(j - 2) && a.charAt(i - 2) == b.charAt(j - 1)) {
                    distance[i][j] = Math.min(distance[i][j], distance[i - 2][j - 2] + 1); // Transposition
                }
            }
        }
        return distance[a.length()][b.length()];
    }
}
