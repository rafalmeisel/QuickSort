package QuickSort;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application
{
    List<Integer> arrayInteger = new ArrayList<>();
    TextField textFieldEnterNumber;
    TextField textFieldRangesFrom;
    TextField textFieldRangerTo;
    Button buttonSubmit;
    int min;
    int max;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {

        //Load layout from file
        Pane root = FXMLLoader.load(getClass().getResource("gui.fxml"));

        //Initialize new scene on window
        Scene scene = new Scene(root,600,600);

        //Set window title
        stage.setTitle("QuickSort");

        //Create new group for buttons radio
        final ToggleGroup group = new ToggleGroup();

        //Create new radio button for options

        RadioButton radioButtonFile = (RadioButton) scene.lookup("#radioButtonFile");
        radioButtonFile.setToggleGroup(group);
        radioButtonFile.setUserData("File");

        RadioButton radioButtonRandom = (RadioButton) scene.lookup("#radioButtonRandom");
        radioButtonRandom.setToggleGroup(group);
        radioButtonRandom.setUserData("Random");

        //Initialize TextFields and connect them with items on the layout
        textFieldEnterNumber = (TextField) scene.lookup("#textFieldEnterNumber");
        textFieldRangesFrom = (TextField) scene.lookup("#textFieldRangerFrom");
        textFieldRangerTo = (TextField) scene.lookup("#textFieldRangerTo");

        buttonSubmit = (Button) scene.lookup("#buttonSubmit");

        //Create listener for radio buttons
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle)
            {

                if (group.getSelectedToggle() != null)
                {
                    System.out.println(group.getSelectedToggle().getUserData().toString());
                }

                //Check what radio button was choose
                if(group.getSelectedToggle() == radioButtonFile)
                {
                    //Clear array with data - to avoid adding the same data after choose this option next time
                    arrayInteger.clear();

                    readFile("Data.txt");
                    disableTextField(true);

                }
                else if(group.getSelectedToggle() == radioButtonRandom)
                {
                    disableTextField(false);
                }

            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private void disableTextField(boolean disable)
    {
        if (!disable)
        {
            textFieldEnterNumber.setDisable(false);
            textFieldEnterNumber.setText("");
            textFieldRangesFrom.setDisable(false);
            textFieldRangesFrom.setText("");
            textFieldRangerTo.setDisable(false);
            textFieldRangerTo.setText("");

            buttonSubmit.setDisable(false);
        }

        else
            {
                textFieldEnterNumber.setDisable(true);
                textFieldEnterNumber.setText(Integer.toString(arrayInteger.size()));
                textFieldRangesFrom.setDisable(true);
                textFieldRangesFrom.setText(Integer.toString(min));
                textFieldRangerTo.setDisable(true);
                textFieldRangerTo.setText(Integer.toString(max));

                buttonSubmit.setDisable(true);
            }
    }
    private void readFile(String path)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {

            String line;

            //Reading integers by line and add them to array list
            while ((line = reader.readLine()) != null)
                arrayInteger.add(Integer.parseInt(line));

            min = arrayInteger.get(0);
            max = arrayInteger.get(0);

            for(Integer i: arrayInteger) {
                if(i < min) min = i;
                if(i > max) max = i;
            }
                //TODO:
                // To deleted - display arrayInteger contents
                for(int i = 0; i<arrayInteger.size();i++)
                    System.out.println(arrayInteger.get(i));

                //Close file
                reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}