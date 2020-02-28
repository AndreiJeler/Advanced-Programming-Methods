package GUI;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root=FXMLLoader.load(getClass().getResource("graphic.fxml"));
        //Button b=new Button();
        //b.setText("click click me pls");
        //StackPane layout=new StackPane();
        //layout.getChildren().add(b);
       // primaryStage.setTitle("hello");
        //primaryStage.setScene(new Scene(root,300,275));
        //primaryStage.show();

        FXMLLoader runLoader=new FXMLLoader();
        runLoader.setLocation(getClass().getResource("runForm.fxml"));
        Parent runP=runLoader.load();
        RunForm runCtr=runLoader.getController();
        Stage runS=new Stage();
        runS.setTitle("Select");
        runS.setScene(new Scene(runP,667,437));
        runS.show();
    }
}
