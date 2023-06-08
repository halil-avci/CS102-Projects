package Decoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Decoder {
    public static void main(String[] args) {
        try {
            decoder( "encoded.txt", "map.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decoder( String encodedText, String mapText) throws IOException {
        FileReader mapReader = new FileReader(mapText);
        FileReader mapReader2 = new FileReader(mapText);
        FileReader encodedReader = new FileReader(encodedText);
        FileWriter writer = new FileWriter( "decoded.txt");

        BufferedReader bfReader = new BufferedReader(mapReader);
        BufferedReader bfReader2 = new BufferedReader(mapReader2);
        BufferedReader bfReader3 = new BufferedReader(encodedReader);
        BufferedWriter bfWriter = new BufferedWriter(writer);

        //Learnes number of lines, creates map Array
        String line;
        int counter = 0;
        while ((line = bfReader.readLine()) != null) {
            counter++;
        }
        bfReader.close();

        String[] map = new String[counter];

        //Arranges the map Array according to the given map text
        while ((line = bfReader2.readLine()) != null) {
            int SCOPE_OF_SEARCH = 4;
            for (int i = 0; i < SCOPE_OF_SEARCH; i++) {
                if (line.substring( i, i+1).equals( ":")) {
                    map[ Integer.valueOf(line.substring( 0, i))] = line.substring( i + 2);
                }
            }
            
        }

        //Writes the decoded text
        while ((line = bfReader3.readLine()) != null) {
            String number = "";
            for (int i = 0; i < line.length(); i++) {         
                String ch = line.substring( i, i+1);

                if (ch.equals(" ")) {
                    bfWriter.write( map[ Integer.valueOf(number)] + " ");
                    number = "";
                }
                else {
                    number = number + ch;
                }
            }
            bfWriter.newLine();
        }

        bfReader2.close();
        bfReader3.close();
        bfWriter.close();
    }
}
