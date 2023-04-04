import java.util.*;
import java.nio.file.*;
import java.io.IOException;

public class Song<T1, T2, T3, T4> {
    private T1 songData1;
    private T2 songData2;
    private T3 songData3;
    private T4 songData4;

    public Song(T1 songData1, T2 songData2, T3 songData3, T4 songData4) {
        this.songData1 = songData1;
        this.songData2 = songData2;
        this.songData3 = songData3;
        this.songData4 = songData4;
    }

    public T1 getSongData1() {
        return songData1;
    }

    public void setSongData1(T1 songData1) {
        this.songData1 = songData1;
    }

    public T2 getSongData2() {
        return songData2;
    }

    public void setSongData2(T2 songData2) {
        this.songData2 = songData2;
    }

    public T3 getSongData3() {
        return songData3;
    }

    public void setSongData3(T3 songData3) {
        this.songData3 = songData3;
    }

    public T4 getSongData4() {
        return songData4;
    }

    public void setSongData4(T4 songData4) {
        this.songData4 = songData4;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songData1=" + songData1 +
                ", songData2=" + songData2 +
                ", songData3=" + songData3 +
                ", songData4=" + songData4 +
                '}';
    }
    
    public static <T1, T2, T3, T4> void displayAllSongs(LinkedList<Song<T1, T2, T3, T4>> songs) {
        for (Song<T1, T2, T3, T4> song : songs) {
            System.out.println(song.getSongData1() + ", " + song.getSongData2() + ", " + song.getSongData3());
        }
    }
    
    public static Song[] readSongsArray(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            Song[] songs = new Song[lines.size()];

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(", ");
                String songData1 = parts[0];
                String songData2 = parts[1];
                String songData3 = parts[2];
                int songData4 = Integer.parseInt(parts[3]);

                Song song = new Song(songData1, songData2, songData3, songData4);
                songs[i] = song;
            }

            return songs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LinkedList<Song<String,String,String,Integer>> readSongsLinkedList(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            LinkedList<Song<String,String,String,Integer>> songs = new LinkedList<>();

            for (String line : lines) {
                String[] parts = line.split(", ");
                String songData1 = parts[0];
                String songData2 = parts[1];
                String songData3 = parts[2];
                int songData4 = Integer.parseInt(parts[3]);

                Song<String,String,String,Integer> song = new Song<>(songData1, songData2, songData3, songData4);
                songs.add(song);
            }

            return songs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public static Song<String, String, String, Integer>[] searchSongsByName(Song<String, String, String, Integer>[] songs, String name) {
        List<Song<String, String, String, Integer>> result = new ArrayList<>();

        for (Song<String, String, String, Integer> song : songs) {
            if (song.getSongData1().equals(name)) {
                result.add(song);
            }
        }

        return result.toArray(new Song[0]);
    }

    public static List<Song<String, String, String, Integer>> searchSongsByYearAndName(List<Song<String, String, String, Integer>> songs, int year) {
        List<Song<String, String, String, Integer>> result = new ArrayList<>();

        for (Song<String, String, String, Integer> song : songs) {
            if (song.getSongData4() == year) {
                result.add(song);
            }
        }

        // sort the result list by name
        result.sort(Comparator.comparing(Song::getSongData1));

        return result;
    }
    
    public static Comparator<Song<String, String, String, Integer>> compareByName() {
        return new Comparator<Song<String, String, String, Integer>>() {
            @Override
            public int compare(Song<String, String, String, Integer> song1, Song<String, String, String, Integer> song2) {
                return song1.getSongData1().compareTo(song2.getSongData1());
            }
        };
    }

    //Compare songs by year then name from unsorted List
    public static Comparator<Song<String, String, String, Integer>> compareByYearAndName() {
        return Comparator.comparing(Song<String, String, String, Integer>::getSongData4)
                .thenComparing(Song<String, String, String, Integer>::getSongData1);
    }

    
    public static void main(String[] args) {
    	//Create array of Song objects
    	Song<String, String, String, Integer>[] songs = Song.readSongsArray("C:/Users/soldi/Desktop/School/Spring 2023/Data Structures/songs_with_ids.txt");

        // Search for songs by name
    	Song<String, String, String, Integer>[] songsByName = Song.searchSongsByName(songs, "Song 1");

        //Print out results
    	for (Song<String, String, String, Integer> song : songsByName) {
            System.out.println(song.toString());
        }

        //Create linked list of Song objects
    	LinkedList<Song<String,String,String,Integer>> songList = Song.<String,String,String,Integer>readSongsLinkedList("C:/Users/soldi/Desktop/School/Spring 2023/Data Structures/songs_with_ids.txt");

        //Search songs by year and name
    	List<Song<String, String, String, Integer>> songsByYearAndName = Song.searchSongsByYearAndName(songList, 2001);

        //Print out results
    	for (Song<String, String, String, Integer> song : songsByYearAndName) {
            System.out.println(song.toString());
        }

        //Test comparators
    	Comparator<Song<String, String, String, Integer>> nameComparator = Song.compareByName();
        Comparator<Song<String, String, String, Integer>> yearAndNameComparator = Song.compareByYearAndName();

        //Sort songs by name using name comparator
        Arrays.sort(songs, (Comparator<? super Song<String, String, String, Integer>>) nameComparator);

        //Print sorted songs
        for (Song<String, String, String, Integer> song : songs) {
            System.out.println(song.toString());
        }

        //Sort song list year/name using that comparator
        songList.sort(yearAndNameComparator);

        //Print out sorted song list
        for (Song song : songList) {
            System.out.println(song.toString());
        }
    	
//    	try {
//            List<String> lines = Files.readAllLines(Paths.get("C:/Users/soldi/Desktop/School/Spring 2023/Data Structures/songs_with_ids.txt"));
//
//            // Create a linked list to hold the songs
//            LinkedList<Song<String, String, String, Integer>> songs = new LinkedList<>();
//
//            // Parse each line into a Song instance and add it to the linked list
//            for (String line : lines) {
//                String[] parts = line.split(", ");
//                String songData1 = parts[0];
//                String songData2 = parts[1];
//                String songData3 = parts[2];
//                Integer songData4 = Integer.parseInt(parts[4]);
//                Song<String, String, String, Integer> song = new Song<>(songData1, songData2, songData3, songData4);
//                songs.add(song);
//            }
//
//            // Display the first three songData of all songs in the linked list
//            displayAllSongs(songs);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    	
//    	// Create an instance of Song with data types <String, String, String, Integer>
//        Song<String, String, String, Integer> song1 = new Song<>("The Spine (feat. Ashley Barret)", "Darren Korb", "3:14", 1983749);
//
//        // Use getters and setters to modify and access the songData1 field
//        song1.setSongData1("The Spine (feat. Ashley Barret) - updated");
//        String songData1 = song1.getSongData1();
//
//        // Print the values of the songData fields using the toString method
//        System.out.println(song1.toString());
//
//        // Create an instance of Song with data types <Integer, String, Integer, Integer>
//        Song<Integer, String, Integer, Integer> song2 = new Song<>(1001, "Song 2", 120, 2000);
//
//        // Use getters and setters to modify and access the songData3 field
//        song2.setSongData3(180);
//        Integer songData3 = song2.getSongData3();
//
//        // Print the values of the songData fields using the toString method
//        System.out.println(song2.toString());
//
//        // Create an instance of Song with data types <String, Double, Integer, String>
//        Song<String, Double, Integer, String> song3 = new Song<>("Song 3", 3.5, 150, "Artist 3");
//
//        // Use getters and setters to modify and access the songData2 field
//        song3.setSongData2(4.2);
//        Double songData2 = song3.getSongData2();
//
//        // Print the values of the songData fields using the toString method
//        System.out.println(song3.toString());
    }

}