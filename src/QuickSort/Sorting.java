package QuickSort;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Rafał on 06.06.2017.
 */
public class Sorting {

    List<Integer> array;

    public Sorting(List<Integer> array_) {
        array = array_;
        run();
    }

    public void run()
    {
        sort(0, array.size() - 1);
        save();
    }

    private void save()
    {
        try{
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            for (int i = 0; i<array.size();i++)
            writer.println(array.get(i));
            writer.close();
        } catch (IOException e) {
        }
    }
    private int length;

    private void sort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        int pivot = array.get(lowerIndex+(higherIndex-lowerIndex)/2);
        // Divide into two arrays
        while (i <= j) {

            while (array.get(i) < pivot) {
                i++;
            }
            while (array.get(j) > pivot) {
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

    private void exchangeNumbers(int i, int j) {
        int temp = array.get(i);
        array.set(i,array.get(j));
        array.set(j,temp);
    }

}
