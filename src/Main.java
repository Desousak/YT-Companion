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

import java.io.File;
import java.io.FileReader;

public class Main extends Application {

    private Scene mainScene;
    private BorderPane mainPane;
    private BorderPane centerPane;
    private TextField searchField;
    private MenuBar menuBar;
    private Menu searchItem;
    private Menu quitItem;
    private Menu searchHistItem;
    private Menu videoHistoryItem;
    private Menu nightModeItem;
    private ListView<String> videoHistList;
    private ListView<String> historyList;
    private ListView<Video> videoList;
    private WebView webview;
    private Image blackScreen;
    private ImageView blackScreenView;
    private Boolean nightModeOn = false;
    private Boolean videoOn = false;

    // Instance for keeping track of the search history
    private SearchHistory searchH;
    private SearchHistory videoH;

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
        nightModeItem = new Menu("Night Mode");
        searchHistItem = new Menu("Search History");
        videoHistoryItem = new Menu("Video History");

        //File to store searches
        File SearchHFile = new File("Search History.txt");
        File VideoHFile = new File("File History.txt");

        searchH = new SearchHistory(SearchHFile);
        videoH = new SearchHistory(VideoHFile);


        searchH.setSearchs();
        videoH.setSearchs();

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

        searchHistItem.getItems().add(new MenuItem());
        searchHistItem.addEventHandler(Menu.ON_SHOWN, event -> searchHistItem.hide());
        searchHistItem.addEventHandler(Menu.ON_SHOWING, event -> searchHistItem.fire());
        searchHistItem.setOnAction(e -> {
            if(centerPane.getTop() == historyList){
                centerPane.getChildren().remove(historyList);
            } else {
                historyList.setItems(searchH.getSearchs());
                centerPane.setTop(historyList);
            }
        });
        //Allowing menu item to autohide and act as a button
        videoHistoryItem.getItems().add(new MenuItem());
        videoHistoryItem.addEventHandler(Menu.ON_SHOWN, event -> videoHistoryItem.hide());
        videoHistoryItem.addEventHandler(Menu.ON_SHOWING, event -> videoHistoryItem.fire());
        videoHistoryItem.setOnAction(e -> {
            if(centerPane.getTop() == videoHistList){
                centerPane.getChildren().remove(videoHistList);
            } else {
                videoHistList.setItems(videoH.getSearchs());
                centerPane.setTop(videoHistList);
            }
        });

        nightModeItem.getItems().add(new MenuItem());
        nightModeItem.addEventHandler(Menu.ON_SHOWN, event -> nightModeItem.hide());
        nightModeItem.addEventHandler(Menu.ON_SHOWING, event -> nightModeItem.fire());
        nightModeItem.setOnAction(e -> {
            if (nightModeOn == false) {
                nightModeOn = true;
                if (videoOn == false) {
                    blackScreen = new Image("file:images/Black.png");
                    blackScreenView = new ImageView();
                    blackScreenView.setImage(blackScreen);
                    centerPane.setRight(blackScreenView);
                }
                mainScene.getStylesheets().add("NightMode.css");
            } else if (nightModeOn == true) {
                nightModeOn = false;
                centerPane.setRight(webview);
                mainScene.getStylesheets().clear();
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
                    String query = searchField.getText();
                    while (query.contains(" ")){
                        query = query.substring(0, query.indexOf(' ')) + "%20" +  query.substring(query.indexOf(' ') + 1, query.length());
                    }

                    searchH.trackSearchHistory(query);

                    videoList.setItems(FXCollections.observableList(VideoRetriever.getVideos(query)));
                }
                centerPane.getChildren().remove(searchField);
            }
        });

        menuBar.getMenus().addAll(searchItem, searchHistItem, videoHistoryItem, nightModeItem, quitItem);
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#F1F1F1"), CornerRadii.EMPTY, Insets.EMPTY)));

        videoHistList = new ListView<>();
        videoHistList.setItems(FXCollections.observableArrayList());

        historyList = new ListView<>();
        historyList.setItems(FXCollections.observableArrayList());

        videoList = new ListView<>();
        videoList.setItems(FXCollections.observableArrayList());

        videoHistList.setOnMouseClicked(e -> {
            String selection = videoHistList.getSelectionModel().getSelectedItem();
            String splitter[] = selection.split("\t");
            webview.getEngine().load(splitter[1]);
            centerPane.getChildren().remove(videoHistList);
        });

        historyList.setOnMouseClicked(e -> {
            String selection = historyList.getSelectionModel().getSelectedItem();
            centerPane.getChildren().remove(historyList);
            searchField.setText(selection);
            centerPane.setTop(searchField);
        });

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
                    imageView.setImage(new Image(video.getThumbnailURL()));
                    // Getting the title of the video to "wrap" in the ListView
                    String title = video.getTitle();
                    int listViewBuffer = 10;
                    int lastSpace = 0;
                    for (int i = 0; i < title.length(); i++) {
                        if (title.charAt(i) == ' ') {
                            lastSpace = i;
                        }
                        if (i != 0 && i % listViewBuffer == 0) {
                            title = title.substring(0, lastSpace) + '\n' + title.substring(lastSpace + 1, title.length());
                        }
                    }

                    setText(title);
                    setGraphic(imageView);
                }
            }

        });

        videoList.setOnMouseClicked(e -> {
            videoOn = true;
            System.out.println(videoList.getSelectionModel().getSelectedItem());
            // If nightMode's on remove the black screen "curtain"
            if (nightModeOn == true) {
                centerPane.getChildren().remove(blackScreenView);
                centerPane.setRight(webview);
            }

            centerPane.setRight(webview);

            if (videoList.getSelectionModel().getSelectedItem() != null) {
                System.out.println(videoList.getSelectionModel().getSelectedItem().getVideoURL());
                videoH.trackSearchHistory(videoList.getSelectionModel().getSelectedItem().getTitle()+'\t'+
                                             videoList.getSelectionModel().getSelectedItem().getVideoURL());
                webview.getEngine().load(
                        videoList.getSelectionModel().getSelectedItem().getVideoURL()
                );
            }
        });

        centerPane.setLeft(videoList);

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
