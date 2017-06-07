package QuickSort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafał on 06.06.2017.
 */
public class Sorting {

    List<Integer> array = new ArrayList<>();

    FileController fileController;

    //Default constructor
    public Sorting(List<Integer> array_)
    {
        array.addAll(array_);
    }

    //Start sorting and save result to file
    public void run()
    {
        fileController = new FileController();
        sort(0, array.size() - 1);
        fileController.saveOutput(array);
    }



    //Sorting by QuickSort
    private void sort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;

        //Pivot - taking middle value of array
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

    //Swap values in array
    private void exchangeNumbers(int i, int j) {
        int temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j,temp);
    }

}
