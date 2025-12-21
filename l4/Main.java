/*
 * Laboratory Work #4: Class Relationships in Java
 * Student ID: 7, C3=1 (String), C17=7 (sort by vowels)
 */

import java.util.Arrays;
import java.util.Comparator;

class Letter {
    private char value;
    
    public Letter(char value) {
        this.value = value;
    }
    
    public char getValue() {
        return value;
    }
    
    public boolean isVowel() {
        return "aeiouAEIOU".indexOf(value) != -1;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

class Word {
    private Letter[] letters;
    
    public Word(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
        
        letters = new Letter[word.length()];
        for (int i = 0; i < word.length(); i++) {
            letters[i] = new Letter(word.charAt(i));
        }
    }
    
    public int countVowels() {
        int count = 0;
        for (Letter letter : letters) {
            if (letter.isVowel()) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : letters) {
            sb.append(letter.getValue());
        }
        return sb.toString();
    }
}

class Punctuation {
    private char value;
    
    public Punctuation(char value) {
        this.value = value;
    }
    
    public char getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

class Sentence {
    private Object[] elements;
    
    public Sentence(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }
        
        sentence = sentence.replaceAll("[\\s\\t]+", " ").trim();
        
        String[] tokens = sentence.split("(?=[\\p{Punct}\\s])|(?<=[\\p{Punct}\\s])");
        int elementCount = 0;
        
        for (String token : tokens) {
            if (!token.isEmpty() && !token.matches("\\s+")) {
                elementCount++;
            }
        }
        
        elements = new Object[elementCount];
        int index = 0;
        
        for (String token : tokens) {
            if (token.isEmpty() || token.matches("\\s+")) {
                continue;
            }
            
            if (token.matches("\\p{Punct}")) {
                elements[index++] = new Punctuation(token.charAt(0));
            } else {
                elements[index++] = new Word(token);
            }
        }
    }
    
    public Word[] getWords() {
        int wordCount = 0;
        for (Object element : elements) {
            if (element instanceof Word) {
                wordCount++;
            }
        }
        
        Word[] words = new Word[wordCount];
        int index = 0;
        
        for (Object element : elements) {
            if (element instanceof Word) {
                words[index++] = (Word) element;
            }
        }
        
        return words;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i].toString());
            if (i < elements.length - 1 && elements[i + 1] instanceof Word) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}

class Text {
    private Sentence[] sentences;
    
    public Text(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        text = text.replaceAll("[\\s\\t]+", " ").trim();
        
        String[] sentenceStrings = text.split("(?<=[.!?])\\s*");
        sentences = new Sentence[sentenceStrings.length];
        
        for (int i = 0; i < sentenceStrings.length; i++) {
            sentences[i] = new Sentence(sentenceStrings[i]);
        }
    }
    
    public Word[] getAllWords() {
        int totalWords = 0;
        for (Sentence sentence : sentences) {
            totalWords += sentence.getWords().length;
        }
        
        Word[] allWords = new Word[totalWords];
        int index = 0;
        
        for (Sentence sentence : sentences) {
            Word[] sentenceWords = sentence.getWords();
            for (Word word : sentenceWords) {
                allWords[index++] = word;
            }
        }
        
        return allWords;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentences.length; i++) {
            sb.append(sentences[i].toString());
            if (i < sentences.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}

public class Main {
    
    public static void main(String[] args) {
        try {
            int studentId = 7;
            int c3 = studentId % 3;
            int c17 = studentId % 17;
            
            System.out.printf("Student ID: %d\nC3=%d (String), C17=%d\n\n", 
                            studentId, c3, c17);
            
            String inputText = "Test java kpi " +
                              "\t aoiiio";
            
            System.out.println("Original text:");
            System.out.println(inputText);
            System.out.println();
            
            Text text = new Text(inputText);
            
            System.out.println("Processed text:");
            System.out.println(text);
            System.out.println();
            
            Word[] words = text.getAllWords();
            Word[] sortedWords = sortWordsByVowelCount(words);
            
            System.out.println("Words sorted by vowel count:");
            for (Word word : sortedWords) {
                System.out.printf("%s (%d vowels)\n", word, word.countVowels());
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    
    static Word[] sortWordsByVowelCount(Word[] words) {
        if (words == null) {
            throw new NullPointerException("Words array cannot be null");
        }
        if (words.length == 0) {
            throw new IllegalArgumentException("Words array cannot be empty");
        }
        
        Word[] sortedWords = Arrays.copyOf(words, words.length);
        
        Arrays.sort(sortedWords, new Comparator<Word>() {
            public int compare(Word w1, Word w2) {
                return Integer.compare(w1.countVowels(), w2.countVowels());
            }
        });
        
        return sortedWords;
    }
}