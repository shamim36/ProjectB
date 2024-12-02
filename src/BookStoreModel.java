import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

class BookStoreModel implements AddRemoveBook {
    List<Book> books = new ArrayList<>();

    // Comparator to sort books by title using method references
    private final Comparator<Book> bookTitleComparator = Comparator.comparing(Book::getTitle);

    public boolean addBook(Book book) {

        books.add(book);
        Collections.sort(books, bookTitleComparator); // Sort books by title after adding
        return true;
    }

    public List<Book> getBookList() {
        return books;
    }

    public boolean removeBook(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equals(title)) {
                books.remove(i);
                break;
            }
        }
        return true;
    }

    public HashMap<Integer, Book> searchBooks(String title) {
        HashMap<Integer, Book> machedResult = new LinkedHashMap<>();
        for (Book book : books) {
            int i = 1;
            if (book instanceof Book && book.getTitle().equals(title)) {
                machedResult.put(i, book);
                i++;
            }
        }
        return machedResult;
    }

}

interface AddRemoveBook {
    public boolean removeBook(String title);

    public boolean addBook(Book book);
}

class Book {
    private String title;
    private String author;
    private Genre genre;
    private BookStatus status;
    private double price;
    private int quantityAvailable;
    private double discountPercentage;

    public void setBookDetails(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, double discountPercentage) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.discountPercentage = discountPercentage;

    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setquantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public BookStatus getStatus() {
        return status;
    }

    public double getPriceAfterMaximumDiscount() {

        return price - (price * (discountPercentage / 100));
    }

    @Override
    public String toString() {
        return "-> Book Details: " +
                "Title: " + title +
                ", Author: " + author +
                ", Genre: " + genre +
                ", Status: " + status +
                ", Price: $" + price +
                ", Discount: " + discountPercentage +
                ", Price After Maximum Discount: $" + getPriceAfterMaximumDiscount() +
                ", Quantity Available: " + quantityAvailable;
    }

}

enum BookStatus {
    AVAILABLE, RESERVED
}

enum Genre {
    FICTION, TECHNOLOGY, SCIENCE, ART
}

class User {
    private String userID;
    private String userPassword;

    public void checkUser(String userID, String userPassword) {
        this.userID = userID;
        this.userPassword = userPassword;
        isAdmin();
    }

    public String checkUserID() {
        return userID = "admin";
    }

    public String checkUserPassword() {
        return userPassword = "admin";
    }

    public boolean isAdmin() {
        try {
            if (this.userID.equals("admin") && this.userPassword.equals("admin")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }
}
