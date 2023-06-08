package Encoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Encoder
 */
public class Encoder {

    public static void main(String[] args) {
        try {
            //encoderV2( "input.txt");
            encoder( "input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Encodes by first occurence
    public static void encoder( String text) throws IOException {
        FileReader reader = new FileReader(text);
        BufferedReader bfReader = new BufferedReader(reader);
        FileWriter writer = new FileWriter( "encoded.txt");
        BufferedWriter bfWriter = new BufferedWriter(writer);
        FileWriter writer2 = new FileWriter( "map.txt");
        BufferedWriter bfWriter2 = new BufferedWriter( writer2);

        String line;
        ArrayList<String> myList = new ArrayList<>();

        while ((line = bfReader.readLine()) != null) {
            if (!line.substring(line.length() - 1, line.length()).equals(" ")) {
                line += " ";
            }

            String word = "";
            for (int i = 0; i < line.length(); i++) {
                if (!line.substring( i , i+1 ).equals(" ")) {
                    word += line.substring( i, i+1);
                }
                else {
                    boolean wordExists = false;
                    if (myList.size() == 0) {
                        myList.add(word);
                        wordExists = true;
                        bfWriter.write(String.valueOf(0));
                    }
                    else {
                        for (int c = 0; c < myList.size(); c++) {
                            if (myList.get(c).equals(word)) {
                                wordExists = true;
                                bfWriter.write( "" + c);
                            }
                        }
                    }

                    if (!wordExists) { 
                        myList.add(word);
                        bfWriter.write( String.valueOf( myList.size() - 1));
                    }
                    word = "";
                    bfWriter.write( " ");
                }
            }
            bfWriter.newLine();
        }

        //Writes map
        for (int i = 0; i < myList.size(); i++) {
            bfWriter2.write( "" + i + ": " + myList.get(i));
            bfWriter2.newLine();
        }

        bfReader.close();
        bfWriter.close();
        bfWriter2.close();
    }

    //Second strategy for encoding
    //Encodes by frequency of occurence
    public static void encoderV2( String text) throws IOException {
        FileReader reader = new FileReader(text);
        BufferedReader bfReader = new BufferedReader(reader);

        String line;
        ArrayList<String> myList = new ArrayList<>();
        ArrayList<Integer> occurences = new ArrayList<>();

        //Creates an ArrayList of the words in the text
        while ((line = bfReader.readLine()) != null) {
            if (!line.substring(line.length() - 1, line.length()).equals(" ")) {
                line += " ";
            }

            String word = "";
            for (int i = 0; i < line.length(); i++) {
                if (!line.substring( i , i+1 ).equals(" ")) {
                    word += line.substring( i, i+1);
                }
                else {
                    boolean wordExists = false;
                    if (myList.size() == 0) {
                        myList.add(word);
                        occurences.add(1);
                        wordExists = true;
                    }
                    else {
                        for (int c = 0; c < myList.size(); c++) {
                            if (myList.get(c).equals(word)) {
                                wordExists = true;
                                occurences.set(c, (occurences.get(c) + 1));
                            }
                        }
                    }

                    if (!wordExists) { 
                        myList.add(word);
                        occurences.add(1);
                    }
                    word = "";
                }
            }
        }
        
        //Creates the map of the words by frequences of their occurence
        ArrayList<String> map = new ArrayList<>();
        int size = occurences.size();
        for (int i = 0; i < size; i++) {
            int biggestIndex = 0;
            for (int c = 1; c < occurences.size(); c++) {
                if (occurences.get(biggestIndex) < occurences.get(c)) {
                    biggestIndex = c;
                }
            }

            map.add( myList.get( biggestIndex));
            occurences.remove( biggestIndex);
            myList.remove( biggestIndex);
        }
        
        FileReader reader2 = new FileReader(text);
        BufferedReader bfReader2 = new BufferedReader(reader2);
        FileWriter writer = new FileWriter( "encodedV2.txt");
        BufferedWriter bfWriter = new BufferedWriter(writer);

        //Writes the encoded text
        line = "";
        while ((line = bfReader2.readLine()) != null) {
            if (!line.substring(line.length() - 1, line.length()).equals(" ")) {
                line += " ";
            }

            String word = "";
            for (int i = 0; i < line.length(); i++) {
                if (!line.substring( i , i+1 ).equals(" ")) {
                    word += line.substring( i, i+1);
                }
                else {
                    for (int index = 0; index < map.size(); index++) {
                        if (word.equals( map.get( index))) {
                            bfWriter.write( "" + index + " ");
                            
                        }
                    }
                    word = "";
                }
            }
            bfWriter.newLine();
        }

        //Writes map
        FileWriter writer2 = new FileWriter("map.txt");
        BufferedWriter bfWriter2 = new BufferedWriter(writer2);

        for (int i = 0; i < map.size(); i++) {
            bfWriter2.write( "" + i + ": " + map.get(i));
            bfWriter2.newLine();
        }


        bfReader.close();
        bfReader2.close();
        bfWriter.close();
        bfWriter2.close();
    }
    
}