package QuickSort;

import javafx.application.Application;
import javafx.stage.Stage;


    public class Main extends Application
    {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception
        {
            //Set window title
            stage.setTitle("QuickSort");

            ViewController controller = new ViewController(stage);
            controller.start();

        }

    }
