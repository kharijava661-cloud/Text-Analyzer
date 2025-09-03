package com.text.detector.controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TextController {


    public Map<String, Integer> readTextFile() {
        Map<String, Integer> wordCount = new HashMap<>();
        Set<String> wordsToExclude = wordsToExclude();
        try {

            InputStream inputStream = TextController.class.getClassLoader()
                    .getResourceAsStream("moby.txt");

            if (inputStream == null) {
                System.err.println("File not found in resources folder.");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {

                line = line.replaceAll("[^a-zA-Z ]", " ").toLowerCase();

                String[] words = line.split("\\s+");

                for (String word : words) {
                    if (!word.isEmpty() && !wordsToExclude.contains(word)) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }
            int totalWords = wordCount.values().stream().mapToInt(Integer::intValue).sum();

            // Top 5 most used words
            List<Map.Entry<String, Integer>> top5Words = wordCount.entrySet().stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .limit(5)
                    .collect(Collectors.toList());

            // uniquely sorted 50 words
            List<String> sortedUniqueWords = wordCount.keySet().stream()
                    .sorted()
                    .limit(50)
                    .collect(Collectors.toList());


            System.out.println("Total Word Count (after exclusions): " + totalWords);
            System.out.println("\nTop 5 Most Used Words:");
            for (Map.Entry<String, Integer> entry : top5Words) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }

            System.out.println("\n50 Alphabetically Sorted Unique Words:");
            for (String word : sortedUniqueWords) {
                System.out.println(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> wordsToExclude(){
        Set<String> EXCLUDE_WORDS = new HashSet<>(Arrays.asList(
                "the", "a", "an", "and", "or", "but", "so", "yet", "for", "nor", "on", "in", "at", "by", "with",
                "about", "against", "between", "into", "through", "during", "before", "after", "above", "below",
                "to", "from", "up", "down", "over", "under", "again", "further", "then", "once", "here", "there",
                "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some",
                "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "can", "will", "just",
                "don", "should", "now", "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them",
                "my", "of","s","this","that","as","your", "his", "its", "our", "their", "is", "was", "are", "were",
                "be", "been", "being", "am", "do","have","had","like","one","what","which","upon","out","if"
        ));
        return EXCLUDE_WORDS;
    }

}
