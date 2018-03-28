import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private Scene mainScene;
    private BorderPane mainPane;
    private BorderPane centerPane;
    private TextField searchField;
    private MenuBar menuBar;
    private Menu searchItem;
    private Menu quitItem;
    private ListView<Video> videoList;
    private WebView webview;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        centerPane = new BorderPane();
        mainPane = new BorderPane();
        searchField = new TextField();
        webview = new WebView();
        searchField.setPromptText("Enter your search query here");
        menuBar = new MenuBar();
        searchItem = new Menu("Search");
        quitItem = new Menu("Quit");

        // Allows the menu to have just one item and also fire
        searchItem.getItems().add(new MenuItem());
        searchItem.addEventHandler(Menu.ON_SHOWN, event -> searchItem.hide());
        searchItem.addEventHandler(Menu.ON_SHOWING, event -> searchItem.fire());
        searchItem.setOnAction(e -> {
            if (centerPane.getTop() == searchField) {
                centerPane.getChildren().remove(searchField);
            } else {
                centerPane.setTop(searchField);
            }
        });

        quitItem.getItems().add(new MenuItem());
        quitItem.addEventHandler(Menu.ON_SHOWN, event -> quitItem.hide());
        quitItem.addEventHandler(Menu.ON_SHOWING, event -> quitItem.fire());
        quitItem.setOnAction(e -> System.exit(0));


        // Code for the search box to display or hide properly
        searchField.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                if (!searchField.getText().equals("") && event.getCode() == KeyCode.ENTER) {
                    videoList.setItems(FXCollections.observableList(VideoRetriever.getVideos(searchField.getText())));
                }
                centerPane.getChildren().remove(searchField);
            }
        });

        menuBar.getMenus().addAll(searchItem, quitItem);
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#F1F1F1"), CornerRadii.EMPTY, Insets.EMPTY)));

        videoList = new ListView<>();
        videoList.setItems(FXCollections.observableArrayList());
        // Placing videos onto ListView
        videoList.setCellFactory(param -> new ListCell<Video>() {
            private ImageView imageView = new ImageView();
            public void updateItem(Video video, boolean empty) {
                super.updateItem(video, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(video.getThumbnailURL()));
                    setText(video.getTitle());
                    setGraphic(imageView);
                }
            }

        });
        videoList.setOnMouseClicked(e -> {
            System.out.println(videoList.getSelectionModel().getSelectedItem());
            if (videoList.getSelectionModel().getSelectedItem() != null) {
                System.out.println(videoList.getSelectionModel().getSelectedItem().getVideoURL());
                webview.getEngine().load(
                        videoList.getSelectionModel().getSelectedItem().getVideoURL()
                );
            }
        });
        
        centerPane.setLeft(videoList);
        centerPane.setRight(webview);

        mainPane.setTop(menuBar);
        mainPane.setCenter(centerPane);
        mainScene = new Scene(mainPane, 1050, 500);

        // Creates and starts the logo display
        Stage logoStage = new Stage();
        new LogoScreen().start(logoStage);

        // Waits then displays the main screen
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            primaryStage.setResizable(false);
            logoStage.close();
            primaryStage.setScene(mainScene);
            primaryStage.getIcons().add(new Image("file:images/icon.png"));
            primaryStage.setTitle("YT Companion");
            primaryStage.show();
        });
        pause.play();
    }
}
