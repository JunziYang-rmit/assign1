package assign1;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class birthDate {
    static int pageScanned = 0;
    static int numOfArtists = 0;
    static long startTime = 0;
    static long endTime = 0;
    static ArrayList<Page> pages = new ArrayList<>();
    static ArrayList<String> matchedRecords = new ArrayList<>();
    static int start = 0;
    static int end = 0;

    public static void main(String[] args) {
        if (args.length == 2){
            start = Integer.valueOf(args[0]);
            end = Integer.valueOf(args[1]);
        }
    }

    public static void readFile(File file){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            Page page;
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            while (true){
                page = (Page)ois.readObject();
                pages.add(page);
                birthdayQuery();
                pages.remove(page);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void birthdayQuery(){
        for (Page page : pages){
            for (Artist artist : page.artists){
                if (artist.getBirthDate()!=null){
                    if (Integer.valueOf(artist.getBirthDate())>=start && Integer.valueOf(artist.getBirthDate())<=end){
                        matchedRecords.add(artist.getBirthDate());
                        numOfArtists++;
                    }
                }
            }
        }
        pageScanned++;
    }

    public static void stdout(){
        System.out.println("The query result is below:");
        for (String s : matchedRecords){
            System.out.println(s);
        }
        long timeTaken = startTime - endTime;
        System.out.println("Total time taken is "+timeTaken);
    }
}
