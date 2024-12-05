
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookStoreController {
    ObservableList<Book> books = FXCollections.observableArrayList();

    private final Model model;

    public BookStoreController(Model model) {
        this.model = model;

    }

    public List<Book> getBookList() {
        return model.bookStore.getBookList();
    }

    public String bookToString() {
        return model.book.toString();
    }

    public boolean addBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {
        return model.bookStore.addBook(title, genre, status, author, price,
                quantityAvailable, discountPercentage);
    }

    public boolean removeBook(String title) {
        return model.bookStore.removeBook(title);
    }

    public HashMap<Integer, Book> searchBooks(String title) {
        return model.bookStore.searchBooks(title);
    }

    public HashMap<Integer, Book> matchedBooksFromSearch(String title) {
        HashMap<Integer, Book> matchedBooks = this.searchBooks(title);
        return matchedBooks;
    }

    public void setBookDetails(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {
        if (genre == Genre.ART) {
            model.book = new ArtBook(title, genre, status, author, price, quantityAvailable,
                    discountPercentage);
            

        } else if (genre == Genre.TECHNOLOGY) {
            model.book = new TechnologyBook(title, genre, status, author, price, quantityAvailable,
                    discountPercentage);

        } else if (genre == Genre.SCIENCE) {
            model.book = new ScienceBook(title, genre, status, author, price, quantityAvailable,
                    discountPercentage);

        } else if (genre == Genre.FICTION) {
            model.book = new FictionBook(title, genre, status, author, price, quantityAvailable,
                    discountPercentage);

        }
       
    }

    public boolean createBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {

        model.bookStore.addBook(title, genre, status, author, price, quantityAvailable,
                discountPercentage);
        model.book.setBookDetails(title, genre, status, author, price, quantityAvailable,
                discountPercentage, model.book.getTargetAgeGroup());

        return true;
    }

    public String getBookDetails() {
        return model.book.toString();
    }

    public void checkUser(String userID, String userName) {
        model.user.checkUser(userID, userName);
    }

    public String checkUserID() {
        return model.user.checkUserID();
    }

    public String checkUserPassword() {
        return model.user.checkUserPassword();
    }

}
