import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogoScreen extends Application {

    private BorderPane loadingPane;
    private Scene mainScene;
    private Image youTubeLogo;
    private ImageView imageLogoView;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        loadingPane = new BorderPane();

        // Loading screen code
        youTubeLogo = new Image("file:images/LogoLoadingButton.png",
                250, 250, true, true);
        imageLogoView = new ImageView();
        imageLogoView.setImage(youTubeLogo);

        loadingPane.setCenter(imageLogoView);
        mainScene = new Scene(loadingPane, 1050, 500);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
