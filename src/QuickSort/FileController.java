package QuickSort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Rafał on 06.06.2017.
 */
public class FileController {

    List<Integer> array = new ArrayList<>();

    //Saving results to file
    public void saveOutput(List<Integer> arrayParam) {

        try {
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            for (int i = 0; i < arrayParam.size(); i++)
                writer.println(arrayParam.get(i));
            writer.close();
        } catch (IOException e) {
        }
    }

    //Generate array with random values
    public void generateInput(int count, int min, int max) {
        Random generator = new Random();
        array.clear();
        for (int i = 0; i < count; i++)
            array.add(generator.nextInt((max - min) + 1) + min);
        //Creating input to save unsorted data
        try {
            PrintWriter writer = new PrintWriter("input.txt", "UTF-8");
            writer.flush();
            for (int i = 0; i < array.size(); i++) {
                writer.append(array.get(i) + "");
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
        }
    }

    //Loading data from file to array
    public List<Integer> readInput() {

        String line;
        int min;
        int max;

        array.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("input.txt")))) {

            //Reading integers by line and add them to array list
            while ((line = reader.readLine()) != null)
                array.add(Integer.parseInt(line));

            min = array.get(0);
            max = array.get(0);

            for (Integer i : array) {
                if (i < min) min = i;
                if (i > max) max = i;
            }

            array.add(min);
            array.add(max);

            //Close file
            reader.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
            generateInput(100,0,100);
            readInput();
        }
        return array;

    }

}