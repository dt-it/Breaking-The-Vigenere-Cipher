package pl.dtit.io;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import pl.dtit.logic.VigenereBreaker;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class DataReader {
    Scanner scanner = new Scanner(System.in);

    public String getFileFromUser(){
        System.out.println("Select file to decrypt");
        FileResource resource = new FileResource();
        String message = resource.asString();
        if (message!=null){
            System.out.println("File uploaded successfully!");
            System.out.println();
        }
        return message;
    }

    public HashMap<String, HashSet<String>> getDictionaries () throws InterruptedException {
        System.out.print("Uploading languages.");
        Thread.sleep(2000);
        System.out.print(".");
        Thread.sleep(2000);
        System.out.print(".");
        Thread.sleep(2000);
        System.out.print(".");
        Thread.sleep(2000);
        System.out.print(".");
        System.out.println();
        HashMap<String, HashSet<String>> dictionaries = new HashMap<>();
        String[] languages = {"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese", "Spanish"};

        for (int i = 0; i < languages.length; i++) {
            FileResource dictionaryResource = new FileResource("dictionaries/"+languages[i]);
            if(!dictionaries.containsKey(languages[i])) {
                dictionaries.put(languages[i], readDictionary(dictionaryResource));
                System.out.println("Done reading " + languages[i] + " dictionary");
            }
        }
        return dictionaries;
    }


    public HashSet<String> readDictionary(FileResource resource){
        HashSet<String> words = new HashSet<>();
        for (String line : resource.words()) {
            line = line.toLowerCase();
            words.add(line);
        }
        return words;
    }

    public int getInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public void close() {
        scanner.close();
    }

}
