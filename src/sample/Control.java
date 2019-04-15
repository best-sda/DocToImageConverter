package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class Control {
    public AnchorPane pane;
    public Label panel;
    public VBox application;
    private Window primaryStage;

    @FXML
    private void dropFile(DragEvent event) throws IOException, ParseException, InterruptedException {

        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            panel.setText("Converting...");
            Convert convert = new Convert();
            String path = db.getFiles().get(0).toString();
            System.out.println(db.getFiles().get(0).getPath());
            convert.convertFile(path);
            panel.setText("Success");
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();

    }

    @FXML
    public void dragFile(DragEvent event) {
        if (event.getGestureSource() != panel
                && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }
    @FXML
    public void openFile(ActionEvent actionEvent) throws IOException, ParseException, InterruptedException {
        final FileChooser fileChooser = new FileChooser();

        File file = (File) fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            List<File> files = Arrays.asList(file);
            Convert convert = new Convert();
            convert.convertFile(files.get(0).toString());
        }
    }
}
