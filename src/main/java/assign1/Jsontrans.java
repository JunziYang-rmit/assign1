package assign1;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.io.*;
import java.util.ArrayList;

public class Jsontrans {

    static ArrayList<Page> pages = new ArrayList<>();
    static ArrayList<Artist> records = new ArrayList<>();
    public static void main(String[] args) {
        File file = new File("/Volumes/STUDY/2022S1/databasesystems/assign1/src/main/java/heap.4096");
        readObj(file);
        jsonTest(records);
    }

    public static void readObj(File file){
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
                    read();
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

    public static void read(){
        for (Page page : pages){
            for (Artist artist : page.artists){
                records.add(artist);
            }
        }
    }

    public static void jsonTest(ArrayList<Artist> artists){
        ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if(v==null)
                return "";
            return v;
        }
    };
        PrintStream ps = null;
        try {
            ps = new PrintStream("data2.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(ps);
        for (Artist artist : artists){
            String json = JSON.toJSONString(artist,filter);
            System.out.println("["+json+",");
        }
    }
}
