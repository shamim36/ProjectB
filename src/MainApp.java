import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Book Store Managemnet System");
        Model model = new Model();
    
        BookStoreController controller = new BookStoreController(model);
        BookStoreView bookStoreView = new BookStoreView(model, stage, controller);

        Scene scene = new Scene(bookStoreView.asParent(), 600, 300);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
