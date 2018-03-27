import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LogoScreen extends Application {

    private BorderPane loadingPane;
    private Scene mainScene;
    private Image ytlogo;
    private ImageView imageLogoView;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        loadingPane = new BorderPane();

        // Loading screen code...................
        ytlogo = new Image("https://www.youtube.com/yt/about/media/images/brand-resources/icons/YouTube-icon-our_icon.png");
        imageLogoView = new ImageView();
        imageLogoView.setImage(ytlogo);

        loadingPane.setCenter(imageLogoView);
        mainScene = new Scene(loadingPane, ytlogo.getWidth(), ytlogo.getHeight());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
}
