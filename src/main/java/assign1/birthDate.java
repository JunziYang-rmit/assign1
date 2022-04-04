package assign1;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            File file = new File("heap.4096");
            startTime = System.currentTimeMillis();
            readFile(file);
            endTime = System.currentTimeMillis();
            stdout();
        }
    }

    public static void readFile(File file){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            Page page = null;
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            while (true){
                try {
                    page = (Page)ois.readObject();
                    pages.add(page);
                    birthdayQuery();
                    pages.remove(page);
                } catch (EOFException e){
                    break;
                }

            }

        } /*catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }*/ catch (IOException e) {
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

                int day = dateFormatProcess(artist.getBirthDate());
                //int day = Integer.valueOf(artist.getBirthDate());
                if (day>=start && day<=end){
                    String result = artist.getPersonName()+": "+day;
                    matchedRecords.add(result);
                    numOfArtists++;
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
        long timeTaken = endTime - startTime;
        System.out.println("Total time taken is "+timeTaken);
    }

    //process the invalid values
    public static Integer dateFormatProcess(String birthdayDate){
        Integer intBirthDay = 0;
        if (birthdayDate != null){
            if (!(birthdayDate.contains("{"))){
                if (birthdayDate.contains("T")){
                    int indexOfT = birthdayDate.indexOf("T");
                    birthdayDate = birthdayDate.substring(0,indexOfT);
                }
            }else {
                int index = birthdayDate.indexOf("|");
                birthdayDate = birthdayDate.substring(1,index);
                if (birthdayDate.contains("T")){
                    int indexOfT = birthdayDate.indexOf("T");
                    birthdayDate = birthdayDate.substring(0,indexOfT);
                }
            }
            if (birthdayDate.contains("/")){
                birthdayDate.replace('/','-');
            }
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = df1.parse(birthdayDate);
            } catch (ParseException e) {
                intBirthDay = 0;
                return intBirthDay;
            }
            DateFormat df2 = new SimpleDateFormat("yyyyMMdd");
            String date2 = df2.format(date);
            intBirthDay = Integer.valueOf(date2);
        }


        return intBirthDay;

    }
}
