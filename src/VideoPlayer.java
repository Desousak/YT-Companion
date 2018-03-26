import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VideoPlayer extends Application {
    public static void main(String[] args) { launch(args); }

    @Override public void start(Stage stage) throws Exception {
        // Code to play YouTube Videos, only works with embed, might use, might not.
        WebView webview = new WebView();
        webview.getEngine().load(
                "https://www.youtube.com/embed/fzQ6gRAEoy0"
        );
        webview.setPrefSize(640, 390);

        stage.setScene(new Scene(webview));
        stage.show();
    }
}