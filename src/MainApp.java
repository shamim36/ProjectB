import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Book Store Managemnet System");
        Book bookModel = new Book();
        User userModel = new User();
        BookStoreModel bookStoreModel = new BookStoreModel();

        BookStoreSystemController controller = new BookStoreSystemController(bookStoreModel, bookModel, userModel);
        BookStoreView bookStoreView = new BookStoreView(bookStoreModel, bookModel, userModel, stage, controller);

        Scene scene = new Scene(bookStoreView.asParent(), 600, 300);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
