package sda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.script.ScriptException;
import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException, ScriptException {
     //  AppleScriptConvertor appleScriptConvertor= new AppleScriptConvertor();
      //  appleScriptConvertor.convertPagesOnMac();
       launch(args);
     //   Convert convert = new Convert();
       // convert.convertFiles("ява лаба 5.pdf")
   // AppleScriptConvertor appleScriptConvertor= new AppleScriptConvertor();
   // appleScriptConvertor.convertPagesOnMac();
    }
}
