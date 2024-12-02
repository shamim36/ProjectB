
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookStoreSystemController {
    List<Book> books = new ArrayList<>();

    private final BookStoreModel bookStoreModel;
    private final Book bookModel;
    private final User userModel;

    public BookStoreSystemController(BookStoreModel bookStoreModel, Book bookModel, User userModel) {
        this.bookStoreModel = bookStoreModel;
        this.bookModel = bookModel;
        this.userModel = userModel;

    }

    // Controlling Model Object

    public List<Book> getBookList() {
        return bookStoreModel.getBookList();
    }

    public String bookToString() {
        return bookModel.toString();
    }

    public boolean addBook(Book book) {
        return bookStoreModel.addBook(book);
    }

    public boolean removeBook(String title) {
        return bookStoreModel.removeBook(title);
    }

    public HashMap<Integer, Book> searchBooks(String title) {
        return bookStoreModel.searchBooks(title);
    }

    public void setBookDetails(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, double discountPercentage) {
        bookModel.setBookDetails(title, genre, status, author, price, quantityAvailable,
                discountPercentage);
    }

    public Book createBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, double discountPercentage) {

        bookModel.setBookDetails(title, genre, status, author, price, quantityAvailable,
                discountPercentage);
        addBook(bookModel);

        return bookModel;
    }

    public String getBookDetails() {
        return bookModel.toString();
    }

    public void checkUser(String userID, String userName) {
        userModel.checkUser(userID, userName);
    }

    public String checkUserID() {
        return userModel.checkUserID();
    }

    public String checkUserPassword() {
        return userModel.checkUserPassword();
    }

}
