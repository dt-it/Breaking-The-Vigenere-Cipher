package pl.dtit.logic;

import java.util.*;

import edu.duke.*;
import pl.dtit.io.DataReader;

public class VigenereBreaker {
    DataReader reader = new DataReader();

    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker caesarCracker = new CaesarCracker();
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            key[i] = caesarCracker.getKey(slice);
        }
        return key;
    }

    public void breakVigenere() throws InterruptedException {
        String message = reader.getFileFromUser();
        HashMap<String, HashSet<String>> dictionaries = reader.getDictionaries();

        breakForAllLangs(message, dictionaries);
    }

    public HashSet<String> readDictionary(FileResource resource){
        HashSet<String> words = new HashSet<>();
        for (String line : resource.words()) {
            line = line.toLowerCase();
            words.add(line);
        }
        return words;
    }

    public int countWords (String message, HashSet<String> dictionary){
        String[] words = message.split("\\W+");
        int counter = 0;
        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())){
                counter ++;
            }
        }
        return counter;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int[] keys = {};
        VigenereCipher vigenereCipher;
        String decryptedMessage = "";
        int count = 0;
        int max = 0;
        int keyLength = 0;
        for(int i = 1; i <= 100; i++) {
            keys = tryKeyLength(encrypted, i, mostCommonCharIn(dictionary));
            vigenereCipher = new VigenereCipher(keys);
            String decrypt = vigenereCipher.decrypt(encrypted);
            count = countWords(decrypt, dictionary);
            if(count > max) {
                max = count;
                decryptedMessage = decrypt;
                keyLength = i;
            }
        }
//        System.out.println("Key length with most real words: " + keyLength);
        return decryptedMessage;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> letters = new HashMap<>();
        char mostCommonChar = ' ';
        int count = 0;
        for(String word : dictionary) {
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(!letters.containsKey(ch)) {
                    letters.put(ch, 1);
                } else {
                    letters.put(ch, letters.get(ch) + 1);
                }
            }
        }
        for(Character c : letters.keySet()) {
            if(letters.get(c) > count) {
                count = letters.get(c);
                mostCommonChar = c;
            }
        }
        return mostCommonChar;
    }

    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages){
        int maxWords = 0;
        String decryptedMessage = "";
        HashMap<String, Integer> langsMostWords = new HashMap<String, Integer>();
        for(String language : languages.keySet()) {
            HashSet<String> dictionary = languages.get(language);
            decryptedMessage = breakForLanguage(encrypted, dictionary);
            int count = countWords(decryptedMessage, dictionary);
            langsMostWords.put(language, count);
        }
        System.out.println("Decrypted message:");
        for(String lang : langsMostWords.keySet()) {
            if(langsMostWords.get(lang) > maxWords) {
                maxWords = langsMostWords.get(lang);
                System.out.println(decryptedMessage);
                System.out.println("Language: " + lang + " with " + maxWords + " of words\n");
            }
        }
    }

}
