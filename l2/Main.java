/*
 * Laboratory Work #2: Strings in Java
 * Student ID: 7, C3=1 (String), C17=7 (sort by vowels)
*/

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    
    public static void main(String[] args) {
        try {
            int studentId = 7;
            int c3 = studentId % 3;
            int c17 = studentId % 17;
            
            System.out.printf("Student ID: %d\nC3=%d (String), C17=%d\n\n", 
                            studentId, c3, c17);
            
            String text = "Java " +
                         "KPI";
            
            System.out.println("Original text:");
            System.out.println(text);
            System.out.println();
            
            String[] sortedWords = sortWordsByVowelCount(text);
            
            System.out.println("Words sorted by vowel count:");
            for (String word : sortedWords) {
                int vowelCount = countVowels(word);
                System.out.printf("%s (%d vowels)\n", word, vowelCount);
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    
    static String[] sortWordsByVowelCount(String text) {
        if (text == null) {
            throw new NullPointerException("Text cannot be null");
        }
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        
        String[] words = text.split("[\\s\\p{Punct}]+");
        
        String[] filteredWords = Arrays.stream(words)
            .filter(word -> !word.isEmpty())
            .toArray(String[]::new);
        
        if (filteredWords.length == 0) {
            throw new IllegalArgumentException("No valid words found in text");
        }
        
        Arrays.sort(filteredWords, new Comparator<String>() {
            public int compare(String word1, String word2) {
                int count1 = countVowels(word1);
                int count2 = countVowels(word2);
                return Integer.compare(count1, count2);
            }
        });
        
        return filteredWords;
    }
    
    static int countVowels(String word) {
        if (word == null) {
            return 0;
        }
        
        int count = 0;
        String vowels = "aeiouAEIOU";
        
        for (int i = 0; i < word.length(); i++) {
            if (vowels.indexOf(word.charAt(i)) != -1) {
                count++;
            }
        }
        
        return count;
    }
}