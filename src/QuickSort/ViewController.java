package QuickSort;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafał on 06.06.2017.
 */
public class ViewController {

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
    Stage stage;

    ViewController(Stage stageParam)
    {
        stage = stageParam;
    }

    public void start() throws Exception
    {

        //Load layout from file
        Pane root = FXMLLoader.load(getClass().getResource("gui.fxml"));

        //Initialize new scene on window
        Scene scene = new Scene(root,600,600);

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

        FileController fileController = new FileController();

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

                    radioButtonCheck = false;

                    arrayInteger = fileController.readInput();

                    max = arrayInteger.get(arrayInteger.size() - 1);
                    arrayInteger.remove(arrayInteger.size()-1);

                    min = arrayInteger.get(arrayInteger.size() - 1);
                    arrayInteger.remove(arrayInteger.size()-1);

                    count = arrayInteger.size();

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
                    fileController.generateInput(count, min, max);
                    arrayInteger.addAll(fileController.readInput());

                    arrayInteger.remove(arrayInteger.size()-1);
                    arrayInteger.remove(arrayInteger.size()-1);
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



}
