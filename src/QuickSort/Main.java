package QuickSort;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application
{
    List<Integer> arrayInteger = new ArrayList<>();
    TextField textFieldEnterNumber;
    TextField textFieldRangeFrom;
    TextField textFieldRangerTo;

    Label labelNumberOfDataWarning;
    Label labelRangeMinOfDataWarning;
    Label labelRangeMaxOfDataWarning;

    Button buttonSubmit;
    Button buttonAbout;

    boolean radioButtonCheck = true;

    int min;
    int max;
    int count;
    
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

        //Initialize elements and connect them with items on the layout
        textFieldEnterNumber = (TextField) scene.lookup("#textFieldEnterNumber");
        textFieldRangeFrom = (TextField) scene.lookup("#textFieldRangerFrom");
        textFieldRangerTo = (TextField) scene.lookup("#textFieldRangerTo");

        labelNumberOfDataWarning = (Label) scene.lookup("labelNumberOfDataWarning");
        labelRangeMinOfDataWarning = (Label) scene.lookup("labelRangeMinOfDataWarning");
        labelRangeMaxOfDataWarning = (Label) scene.lookup("labelRangeMaxOfDataWarning");

        buttonSubmit = (Button) scene.lookup("#buttonSubmit");
        buttonAbout = (Button) scene.lookup("#buttonAbout");


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
                    //Clear arrayToSort with data - to avoid adding the same data after choose this option next time
                    arrayInteger.clear();

                    radioButtonCheck = false;
                    readFile("input.txt");
                    disableTextField(true);

                }
                else if(group.getSelectedToggle() == radioButtonRandom)
                {
                    disableTextField(false);
                    radioButtonCheck = true;
                }

            }
        });

        stage.setScene(scene);
        stage.show();


        //Listener buttonSubmit
        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //If radio button with data to write was choose - catch data to integer
                if(radioButtonCheck)
                {
                    count = Integer.parseInt(textFieldEnterNumber.getText());
                    min = Integer.parseInt(textFieldRangeFrom.getText());
                    max = Integer.parseInt(textFieldRangerTo.getText());
                    random(count, min, max);
                }

                //Start sorting
                Sorting sorting = new Sorting(arrayInteger);
                sorting.run();

                //Show Dialog with information about success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Huge Success!");
                alert.setHeaderText(null);
                alert.setContentText("" +
                        "This was a triumph!\n" +
                        "I'm making a note here:\n" +
                        "Huge success!\n" +
                        "\n" +
                        "It's hard to overstate\n" +
                        "my satisfaction.\n" +
                        "\n"+
                        "Check file: \"output.txt\"");
                alert.showAndWait();

            }
        });

        stage.setScene(scene);
        stage.show();
    }


    //Generate array with random values
    private void random(int count, int min, int max)
    {
        Random generator = new Random();
        arrayInteger.clear();
        for (int i = 0; i<count; i++)
        arrayInteger.add(generator.nextInt(max)+min);

        //Creating input to save unsorted data
        try{
            PrintWriter writer = new PrintWriter("input.txt", "UTF-8");
            writer.flush();
            for(int i =0;i<arrayInteger.size();i++)
            {
                writer.append(arrayInteger.get(i) + "");
                writer.println();
            }
            writer.close();
        } catch (IOException e) {}
    }

    //GUI function to disable TextFields
    private void disableTextField(boolean disable)
    {
        if (!disable)
        {
            textFieldEnterNumber.setDisable(false);
            textFieldEnterNumber.setText("");
            textFieldRangeFrom.setDisable(false);
            textFieldRangeFrom.setText("");
            textFieldRangerTo.setDisable(false);
            textFieldRangerTo.setText("");
        }

        else
        {
            textFieldEnterNumber.setDisable(true);
            textFieldEnterNumber.setText(Integer.toString(arrayInteger.size()));
            textFieldRangeFrom.setDisable(true);
            textFieldRangeFrom.setText(Integer.toString(min));
            textFieldRangerTo.setDisable(true);
            textFieldRangerTo.setText(Integer.toString(max));
        }
    }

    //Loading data from file to array
    private void readFile(String path)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path))))
        {
            String line;
            arrayInteger.clear();

            //Reading integers by line and add them to arrayToSort list
            while ((line = reader.readLine()) != null)
                arrayInteger.add(Integer.parseInt(line));

            min = arrayInteger.get(0);
            max = arrayInteger.get(0);

            for(Integer i: arrayInteger)
            {
                if(i < min) min = i;
                if(i > max) max = i;
            }
            count = arrayInteger.size();
                //Close file
                reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
