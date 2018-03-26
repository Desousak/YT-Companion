import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignIn extends Application {

    public static void main(String[] args) { launch(args); }

    private BorderPane layout;
    private Image ytlogo;
    private ImageView imageLogoView;
    private GridPane loginGrid;
    private Label username;
    private Label password;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button signInButton;

    public void start(Stage primaryStage) {
        layout = new BorderPane();
        // Logo (Image) code
        ytlogo = new Image("https://ih0.redbubble.net/image.25011287.7046/flat,800x800,070,f.u2.jpg",
                100, 100, false, false);
        imageLogoView = new ImageView();
        imageLogoView.setImage(ytlogo);

        loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(100, 100, 100, 100));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);
        // Labels and buttons for sign in page
        username = new Label("Username: ");
        password = new Label("Password: ");
        usernameField = new TextField();
        passwordField = new PasswordField();
        signInButton = new Button("Sign In");
        loginGrid.add(username, 0, 1);
        loginGrid.add(password, 0, 2);
        loginGrid.add(usernameField, 1, 1);
        loginGrid.add(passwordField, 1, 2);
        loginGrid.add(signInButton, 0, 3);

        signInButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                loginGrid.getChildren().clear();
                imageLogoView.setImage(null); // Clear logo

                Label successfulSignIn = new Label("Signed in!");
                loginGrid.add(successfulSignIn, 0, 1);

            }
        });

        layout.setCenter(loginGrid);
        layout.setLeft(imageLogoView);
        primaryStage.setScene(new Scene(layout, 600, 600));
        primaryStage.show();
    }
}
