package QuickSort;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafał on 06.06.2017.
 */
public class Sorting {

    List<Integer> arrayToSort = new ArrayList<>();

    //Default constructor
    public Sorting(List<Integer> array_) {
        arrayToSort.addAll(array_);
        run();
    }

    //Start sorting and save result to file
    public void run()
    {
        sort(0, arrayToSort.size() - 1);
        saveToFile();
    }

    //Saving results to file
    private void saveToFile()
    {
        try{
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            for (int i = 0; i< arrayToSort.size(); i++)
            writer.println(arrayToSort.get(i));
            writer.close();
        } catch (IOException e) {}
    }

    //Sorting by QuickSort
    private void sort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;

        //Pivot - taking middle value of array
        int pivot = arrayToSort.get(lowerIndex+(higherIndex-lowerIndex)/2);

        // Divide into two arrays
        while (i <= j) {

            while (arrayToSort.get(i) < pivot) {
                i++;
            }
            while (arrayToSort.get(j) > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            sort(lowerIndex, j);
        if (i < higherIndex)
            sort(i, higherIndex);
    }

    //Swap values in array
    private void exchangeNumbers(int i, int j) {
        int temp = arrayToSort.get(i);
        arrayToSort.set(i, arrayToSort.get(j));
        arrayToSort.set(j,temp);
    }

}
