import java.io.*;
import java.util.*;

public class SongIDAppend {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/soldi/Desktop/School/Spring 2023/Data Structures/songs.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/soldi/Desktop/School/Spring 2023/Data Structures/songs_with_ids.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0].trim();
                String artist = parts[1].trim();
                String duration = parts[2].trim();
                int year = Integer.parseInt(parts[3].trim());

                int songID = new Random().nextInt(8999999) + 1000000;

                String newLine = String.format("%s, %s, %s, %d, %d\n", title, artist, duration, year, songID);
                writer.write(newLine);
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
