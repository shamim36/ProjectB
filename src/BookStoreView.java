
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.util.HashMap;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.stage.Stage;

public class BookStoreView {

    private VBox vbox;
    Button backBtn;
    BookStatus status;
    Genre genre;
    private BookStoreSystemController controller;
    private SimpleIntegerProperty loginAttemptProperty = new SimpleIntegerProperty(0);
    private Stage primaryStage;
    private BookStoreModel bookStoreModel;
    private Book bookModel;
    private User userModel;

    public BookStoreView(BookStoreModel bookStoreModel, Book bookModel, User userModel, Stage primaryStage,
            BookStoreSystemController controller) {

        // Create a SimpleIntegerProperty to track the number of clicks.

        this.primaryStage = primaryStage;
        this.controller = controller;
        this.bookModel = bookModel;
        this.bookStoreModel = bookStoreModel;
        this.userModel = userModel;

        initialHomePage();

    }

    public Parent asParent() {
        return vbox;
    }

    private void initialHomePage() {

        primaryStage.setTitle("Book Store Management System");

        Label label = new Label("DashBoard");
        Button bookListMenuBtn = new Button("Book List Menu");
        Button adminMenuBtn = new Button("Admin Menu");

        adminMenuBtn.setOnAction(event -> {
            primaryStage.close();
            adminLoginWindow();
        });
        bookListMenuBtn.setOnAction(event -> {
            primaryStage.close();
            MenuPage(false); // this is a normal user, thats why passed false value.
        });

        vbox = new VBox(5, label, bookListMenuBtn, adminMenuBtn);
        vbox.setAlignment(Pos.CENTER);
    }

    void adminLoginWindow() {
        Stage stage = new Stage();
        stage.setTitle("Admin Login");

        // User ID input
        TextField inputUserIdField = new TextField();
        inputUserIdField.setPromptText("Enter your user ID");
        HBox userIdRow = new HBox(5, new Label("User ID: "), inputUserIdField);
        userIdRow.setAlignment(Pos.CENTER);

        // Password input
        TextField inputPasswordField = new TextField();
        inputPasswordField.setPromptText("Enter your password");
        HBox userPasswordRow = new HBox(5, new Label("Password: "), inputPasswordField);
        userPasswordRow.setAlignment(Pos.CENTER);

        // Login status message
        Label loginAttemptMessage = new Label("");
        Label loginMessage = new Label("Only For Admin");

        // Login button
        Button adminLoginBtn = new Button("Admin Login");
        Button backBtn = new Button("DashBoard");

        // Binding the login CountAttepmpt property to the login attempt meessage
        // property
        // users login attempt count will automatically reflected in the loggin massage
        loginAttemptMessage.textProperty().bind(loginAttemptProperty.asString("Login Attempt : %d.  "));

        loginAttemptProperty.addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue.intValue() > 5) {
                        loginMessage.setText("Too Many Login Attempt!!");
                    }
                });

        adminLoginBtn.setOnAction(event -> {

            // Incrementing login attempt how many times it clicked
            loginAttemptProperty.set(loginAttemptProperty.get() + 1);

            // Read the input values inside the button click handler
            String inputId = inputUserIdField.getText();
            String inputPassword = inputPasswordField.getText();

            String systemUserId = controller.checkUserID();
            String systemPassword = controller.checkUserID();

            // Check credentials using the controller
            if (systemUserId.equals(inputId) && systemPassword.equals(inputPassword)) {
                loginMessage.setText("Login Successful!");
                stage.close();
                MenuPage(true);

            } 
        });
        backBtn.setOnAction(event -> {
            stage.close();
            MainApp mainApp = new MainApp();
            mainApp.start(primaryStage);

        });

        // Layout for the login window
        VBox loginBox = new VBox(10, userIdRow, userPasswordRow, adminLoginBtn, backBtn, loginMessage,
                loginAttemptMessage);
        loginBox.setAlignment(Pos.CENTER);

        // Set up the scene and show the stage
        Scene scene = new Scene(loginBox, 600, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void MenuPage(boolean isAdmin) {

        Stage stage = new Stage();

        if (isAdmin) {
            stage.setTitle("Admin Access Window");
        } else {
            stage.setTitle("Book Menu List Window");
        }

        Button addBookBtn = new Button("Add Book");
        Button removeBookBtn = new Button("Remove Book");
        backBtn = new Button("Back");

        // Search by title
        TextField searchField = new TextField();
        searchField.setPromptText("Enter search title");
        HBox searchFieldRow = new HBox(5, new Label("Search: "), searchField);
        searchFieldRow.setAlignment(Pos.CENTER);

        Button searchBtn = new Button("Search");

        searchBtn.setOnAction(event -> {
            displaySearchResults(searchField.getText());
        });

        HBox searchBox = new HBox(10, searchFieldRow, searchBtn);

        HBox addRemoveBox;
        if (isAdmin) {
            addRemoveBox = new HBox(10, addBookBtn, removeBookBtn, backBtn);
            addBookBtn.setAlignment(Pos.BOTTOM_RIGHT);
        } else {
            addRemoveBox = new HBox(10);
            addBookBtn.setAlignment(Pos.BOTTOM_RIGHT);
        }

        // Initialize the book list with demo data
        controller.createBook("Java", Genre.TECHNOLOGY, BookStatus.AVAILABLE, "Robert", 35.99, 2, 10);

        // Create the TableView
        TableView<Book> tableView = new TableView<>(FXCollections.observableArrayList(controller.getBookList()));

        // Create columns for the TableView
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre().toString()));

        TableColumn<Book, String> statusColumn = new TableColumn<>("Status");
        statusColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Book, Integer> quantityColumn = new TableColumn<>("Quantity Available");
        quantityColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantityAvailable()).asObject());

        TableColumn<Book, Double> discountPercentageColumn = new TableColumn<>("Discount (%)");
        discountPercentageColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getDiscountPercentage()).asObject());

        TableColumn<Book, Double> discountPriceColumn = new TableColumn<>("Discount Price");
        discountPriceColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getPriceAfterMaximumDiscount()).asObject());

        // Add all columns to the TableView
        tableView.getColumns().addAll(
                titleColumn,
                authorColumn,
                genreColumn,
                statusColumn,
                priceColumn,
                quantityColumn,
                discountPercentageColumn,
                discountPriceColumn);

        // Add Book button action
        addBookBtn.setOnAction(event -> {
            showAddBookDialog(stage, tableView);
        });

        // Remove Book button action
        removeBookBtn.setOnAction(event -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                // Remove from model
                boolean removed = controller.removeBook(selectedBook.getTitle());
                if (removed) {
                    // Remove from table
                    tableView.getItems().remove(selectedBook);
                }
            }
        });

        // going Home Page and it will automatically logout from admin page
        backBtn.setOnAction(event -> {

            MainApp mainApp = new MainApp();
            mainApp.start(stage);

        });
        VBox layout;
        if (isAdmin) {
            // Layout for the window
            layout = new VBox(10, searchBox, addRemoveBox, backBtn, tableView);
            layout.setAlignment(Pos.CENTER);
        } else {
            // Layout for the window
            layout = new VBox(10, searchBox, backBtn, tableView);
            layout.setAlignment(Pos.CENTER);
        }

        // Create the scene and show it
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showAddBookDialog(Stage parentStage, TableView<Book> tableView) {
        // Dialog for adding a new book
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.setTitle("Add New Book");

        // Input fields for book details
        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        // Toggle group to set status
        ToggleGroup genreGroup = new ToggleGroup();
        ToggleGroup statusGroup = new ToggleGroup();
        RadioButton fictionBtn = new RadioButton("FICTION");
        fictionBtn.setToggleGroup(genreGroup);

        RadioButton technologyBtn = new RadioButton("TECHNOLOGY");
        technologyBtn.setToggleGroup(genreGroup);

        RadioButton scienceBtn = new RadioButton("SCIENCE");
        scienceBtn.setToggleGroup(genreGroup);

        RadioButton artBtn = new RadioButton("ART");
        artBtn.setToggleGroup(genreGroup);

        HBox genreRadioBtnRow = new HBox(5, fictionBtn, technologyBtn, scienceBtn, artBtn);
        genreRadioBtnRow.setAlignment(Pos.CENTER);

        RadioButton availableBtn = new RadioButton("AVAILABLE");
        availableBtn.setToggleGroup(statusGroup);

        RadioButton reservedBtn = new RadioButton("RESERVED");
        reservedBtn.setToggleGroup(statusGroup);

        HBox statusRadioBtnRow = new HBox(5, availableBtn, reservedBtn);
        genreRadioBtnRow.setAlignment(Pos.CENTER);

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity Available");

        TextField discountField = new TextField();
        discountField.setPromptText("Discount Percentage");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Layout for the dialog
        VBox dialogLayout = new VBox(10,
                new Label("Enter Book Details"),
                new HBox(10, new Label("Genre:"), genreRadioBtnRow),
                new HBox(10, new Label("Title:"), titleField),
                new HBox(10, new Label("Author:"), authorField),
                new HBox(10, new Label("Price:"), priceField),
                new HBox(10, new Label("Quantity:"), quantityField),
                new HBox(10, new Label("Discount:"), discountField),
                new HBox(10, new Label("Status:"), statusRadioBtnRow),
                buttonBox);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setPadding(new Insets(10));

        Scene dialogScene = new Scene(dialogLayout, 400, 300);
        dialog.setScene(dialogScene);
        dialog.show();

        // Save button action
        saveButton.setOnAction(event -> {
            String title = titleField.getText();
            String author = authorField.getText();

            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            double discount = Double.parseDouble(discountField.getText());

            // setting Book Status
            if (availableBtn.isSelected()) {
                status = BookStatus.AVAILABLE;
            } else if (reservedBtn.isSelected()) {
                status = BookStatus.RESERVED;
            }

            // Setting book genre
            if (fictionBtn.isSelected()) {
                genre = Genre.FICTION;
            } else if (technologyBtn.isSelected()) {
                genre = Genre.TECHNOLOGY;
            } else if (artBtn.isSelected()) {
                genre = Genre.ART;
            } else if (scienceBtn.isSelected()) {
                genre = Genre.SCIENCE;
            }

            // Create new book object
            controller.setBookDetails(title, genre, status, author, price, quantity, discount);

            // Add book to the controller
            boolean added = controller.addBook(bookModel);
            if (added) {
                tableView.getItems().add(bookModel);
                dialog.close();
            }

        });

        // Cancel button action
        cancelButton.setOnAction(event -> dialog.close());
    }

    private void displaySearchResults(String searchField) {
        Stage searchStage = new Stage();
        searchStage.setTitle("Search Results");

        TableView<Book> tableView = new TableView<>();

        // Define columns
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getGenre().toString()));

        TableColumn<Book, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Book, Integer> quantityColumn = new TableColumn<>("Quantity Available");
        quantityColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantityAvailable()).asObject());

        TableColumn<Book, Double> discountPercentageColumn = new TableColumn<>("Discount (%)");
        discountPercentageColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getDiscountPercentage()).asObject());

        TableColumn<Book, Double> discountPriceColumn = new TableColumn<>("Discount Price");
        discountPriceColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getPriceAfterMaximumDiscount())
                        .asObject());

        // Add all columns to the TableView
        tableView.getColumns().addAll(
                titleColumn,
                authorColumn,
                genreColumn,
                statusColumn,
                priceColumn,
                quantityColumn,
                discountPercentageColumn,
                discountPriceColumn);

        // Fetch and add data
        HashMap<Integer, Book> matchedBooks = controller.searchBooks(searchField);
        if (matchedBooks != null && !matchedBooks.isEmpty()) {
            // Convert the values of the HashMap to an ObservableList
            tableView.setItems(FXCollections.observableArrayList(matchedBooks.values()));
        }

        // Add TableView to the layout
        VBox layout = new VBox(10, tableView);
        layout.setAlignment(Pos.CENTER);

        // Set the scene and show the stage
        Scene scene = new Scene(layout, 800, 400);
        searchStage.setScene(scene);
        searchStage.show();
    }

}
