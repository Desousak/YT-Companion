import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    private Scene mainScene;
    private BorderPane mainPane;
    private BorderPane centerPane;

    private TextField searchField;
    private MenuBar menuBar;
    private Menu loginItem;
    private Menu searchItem;
    private Menu quitItem;
    private ListView<String> videoList;


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


        videoList = new ListView<>();
        videoList.setItems(FXCollections.observableArrayList());
       /* videoList.setCellFactory(param -> new ListCell<String>() { //This will be needed to put videos onto the list
            private ImageView imageView = new ImageView();
            public void updateItem(String name, boolean empty) {
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(name.equals("RUBY"))
                        imageView.setImage(IMAGE_RUBY);
                    else if(name.equals("APPLE"))
                        imageView.setImage(IMAGE_APPLE);
                    else if(name.equals("VISTA"))
                        imageView.setImage(IMAGE_VISTA);
                    else if(name.equals("TWITTER"))
                        imageView.setImage(IMAGE_TWITTER);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });*/
        centerPane.setLeft(videoList);

        mainPane.setTop(menuBar);
        mainPane.setCenter(centerPane);
        mainScene = new Scene(mainPane,700,500);

        //Creates and starts the logo display
        Stage logoStage = new Stage();
        new LogoScreen().start(logoStage);

        //Waits then displays the main screen
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e ->{
            logoStage.close();
            primaryStage.setScene(mainScene);
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.show();
        });
        pause.play();


    }
}
