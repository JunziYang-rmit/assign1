package assign1;
import java.io.*;
import java.util.ArrayList;

public class dbLoad {
    public static int pageSize;
    public static int artistsNum;
    public static int pageNum;
    public static long startTime;
    public static long endTime;

    public static ArrayList<Page> heapFile = new ArrayList<>();

    public static void main(String[] args){
        startTime = System.currentTimeMillis();
        if (args.length == 3){
            if (args[0].equals("-p")){
                File file = new File(args[2]);
                pageSize = Integer.valueOf(args[1]);
                System.out.println("read file");
                System.out.println(pageSize);
                try {
                    System.out.println("Start reading file......");
                    System.out.println("-------------------");
                    readLine(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                System.out.println("Reading has finished!");
                System.out.println();
                System.out.println("Start writing heap file......");
                System.out.println("------------------");
                writeLine();
                endTime = System.currentTimeMillis();
                System.out.println("Writing has finished!");
                stdout();
            }
        }
    }

    public static void readLine(File file) throws IOException {
        Artist artist = null;
        Page page = new Page(pageSize);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        //The first four lines are unrelated to the data, so skip them...
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        while ((line = br.readLine())!=null){
            String[] columns = line.split(",", -1);
            artist = new Artist();
            if (!columns[1].equals("\"NULL\"")){
                String str = columns[1];
                str = str.substring(1,str.length()-1);
                artist.setPersonName(str);
            }
            if (!columns[23].equals("\"NULL\"")){
                String str = columns[23];
                str = str.substring(1,str.length()-1);
                artist.setBirthDate(str);
            }
            if (!columns[25].equals("\"NULL\"")){
                String str = columns[25];
                str = str.substring(1,str.length()-1);
                artist.setBirthPlace_label(str);
            }
            if (!columns[40].equals("\"NULL\"")){
                String str = columns[40];
                str = str.substring(1,str.length()-1);
                artist.setDeathDate(str);
            }
            if (!columns[50].equals("\"NULL\"")){
                String str = columns[50];
                str = str.substring(1,str.length()-1);
                artist.setField_label(str);
            }
            if (!columns[52].equals("\"NULL\"")){
                String str = columns[52];
                str = str.substring(1,str.length()-1);
                artist.setGenre_label(str);
            }
            if (!columns[124].equals("\"NULL\"")){
                String str = columns[124];
                str = str.substring(1,str.length()-1);
                artist.setThumbnail(str);
            }

            if (isNumber(columns[133])&&(!columns[133].isEmpty())){
                artist.setWikiPageID(Integer.valueOf(columns[133].substring(1,columns[133].length()-1)));
                //System.out.println(artist.getWikiPageID());
            }
            if (!columns[62].equals("\"NULL\"")){
                String str = columns[62];
                str = str.substring(1,str.length()-1);
                artist.setInstrument_label(str);
            }
            if (!columns[73].equals("\"NULL\"")){
                String str = columns[73];
                str = str.substring(1,str.length()-1);
                artist.setNationality_label(str);
            }
            if (!columns[137].equals("\"NULL\"")){
                String str = columns[137];
                str = str.substring(1,str.length()-1);
                artist.setDescription(str);
            }

            if (page.getPageSize()<(pageSize-artist.getOneArtistSize())){
                page.addArtist(artist);
                artistsNum++;
            } else {
                heapFile.add(page);
                page = new Page(pageSize);
                pageNum++;
                page.addArtist(artist);
                artistsNum++;
            }

        }
        heapFile.add(page);
        br.close();
        fr.close();
    }

    public static void writeLine(){
        FileOutputStream fos = null;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream("heap."+pageSize);
            oos = new ObjectOutputStream(fos);
            for (Page page : heapFile){
                if (page!=null){
                    oos.writeObject(page);
                }
            }
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stdout(){
        System.out.println("The number of Artists is "+artistsNum);
        System.out.println("The number of pages is "+pageNum);
        System.out.println("It takes time "+(endTime-startTime)+"ms");
    }

    //to filter the non-Int values
    public static boolean isNumber(String str) {
        char[] array = str.toCharArray();
        for (int i = 1; i < array.length-1; i++) {
            if (array[i] < '0' || array[i] > '9') {
                return false;
            }
        }
        return true;
    }
}

class Artist implements Serializable {
    //private static final long serialVersionUID = 1L;
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer wikiPageID;
    String birthDate;
    private String deathDate;
    private String personName,birthPlace_label,field_label,genre_label,instrument_label,nationality_label,thumbnail,description;

    public Integer getWikiPageID() {
        return wikiPageID;
    }

    public void setWikiPageID(Integer wikiPageID) {
        this.wikiPageID = wikiPageID;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getBirthPlace_label() {
        return birthPlace_label;
    }

    public void setBirthPlace_label(String birthPlace_label) {
        this.birthPlace_label = birthPlace_label;
    }

    public String getField_label() {
        return field_label;
    }

    public void setField_label(String field_label) {
        this.field_label = field_label;
    }

    public String getGenre_label() {
        return genre_label;
    }

    public void setGenre_label(String genre_label) {
        this.genre_label = genre_label;
    }

    public String getInstrument_label() {
        return instrument_label;
    }

    public void setInstrument_label(String instrument_label) {
        this.instrument_label = instrument_label;
    }

    public String getNationality_label() {
        return nationality_label;
    }

    public void setNationality_label(String nationality_label) {
        this.nationality_label = nationality_label;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOneArtistSize(){
        int artistSize = 0;
        //There is one attribute which is "Int" occupy 4 bytes
        artistSize += 4;

        if (birthDate != null){
            artistSize += 20;
        }
        if (deathDate != null){
            artistSize += 20;
        }
        if (personName != null){
            artistSize += 50;
        }
        if (birthPlace_label != null){
            artistSize += 100;
        }
        if (field_label != null){
            artistSize += 100;
        }
        if (genre_label != null){
            artistSize += 100;
        }
        if (instrument_label != null){
            artistSize += 100;
        }
        if (nationality_label != null){
            artistSize += 100;
        }
        if (thumbnail != null){
            artistSize += 100;
        }
        if (description != null){
            artistSize += 200;
        }
        return artistSize;
    }

    /*@Override
    public String toString() {
        return "Artist{" +
                "wikiPageID=" + wikiPageID +
                ", birthDate='" + birthDate + '\'' +
                ", deathDate='" + deathDate + '\'' +
                ", personName='" + personName + '\'' +
                ", birthPlace_label='" + birthPlace_label + '\'' +
                ", field_label='" + field_label + '\'' +
                ", genre_label='" + genre_label + '\'' +
                ", instrument_label='" + instrument_label + '\'' +
                ", nationality_label='" + nationality_label + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                '}';
    }*/
}
class Page implements Serializable {
    //private static final long serialVersionUID = 1L;
    private static final long serialVersionUID = 6529685098267757690L;
    int pageSize;
    ArrayList<Artist> artists = new ArrayList<Artist>();

    public Page(int pageSize){
        this.pageSize = pageSize;
    }

    public void addArtist(Artist artistsLine){
        artists.add(artistsLine);
    }

    public int getPageSize(){
        int pageSize = 0;
        for (Artist a : artists){
            pageSize += a.getOneArtistSize();
        }
        return pageSize;
    }

}
