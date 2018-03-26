import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Scene mainScene;
    private BorderPane mainPane;
    private BorderPane centerPane;

    private TextField searchField;
    private MenuBar menuBar;
    private Menu loginItem;
    private Menu searchItem;
    private Menu quitItem;

    public void start(Stage primaryStage) {
        centerPane = new BorderPane();
        mainPane = new BorderPane();
        searchField = new TextField("Enter your search query here");
        menuBar = new MenuBar();
        loginItem = new Menu("Login");
        searchItem = new Menu("Search");
        quitItem = new Menu("Quit");

        // Allows the menu to have just one item and also fire
        searchItem.getItems().add(new MenuItem());
        searchItem.addEventHandler(Menu.ON_SHOWN, event -> searchItem.hide());
        searchItem.addEventHandler(Menu.ON_SHOWING, event -> searchItem.fire());
        searchItem.setOnAction(e -> centerPane.setTop(searchField));

        quitItem.getItems().add(new MenuItem());
        quitItem.addEventHandler(Menu.ON_SHOWN, event -> quitItem.hide());
        quitItem.addEventHandler(Menu.ON_SHOWING, event -> quitItem.fire());
        quitItem.setOnAction(e -> System.exit(0));


        //Code for the search box to display or hide properly
        searchField.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ESCAPE){
                centerPane.getChildren().remove(searchField);
            }
        });

        menuBar.getMenus().addAll(loginItem, searchItem, quitItem);
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#F1F1F1"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        mainPane.setTop(menuBar);
        mainPane.setCenter(centerPane);
        mainScene = new Scene(mainPane,500,200);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
}
