import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private BorderPane layout = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        // Neat code to test clearing all the objects (nodes) on the javafx window
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(100, 100, 100, 100));
        editArea.setVgap(10);
        editArea.setHgap(10);

        Label lbl1 = new Label("Testing......... ");
        editArea.add(lbl1, 0, 1);

        layout.setCenter(editArea);

        Button addButton = new Button("KILL ALL OBJECTS");
        editArea.add(addButton, 10, 10);
        addButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                editArea.getChildren().clear();

                Label lbl2 = new Label("HEY A NEW LABEL!!!");
                editArea.add(lbl2, 0, 1);

            }
        });

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
