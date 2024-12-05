import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Model {
    public Book book = new Book();
    public User user = new User();
    public BookStore bookStore = new BookStore();
}

class BookStore implements AddRemoveBook {
    ObservableList<Book> books = FXCollections.observableArrayList();

    // comparator - sort books by title using method references
    private final Comparator<Book> bookTitleComparator = Comparator.comparing(Book::getTitle);

    public boolean addBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {

        if (genre == Genre.ART) {
            Book artBook = new ArtBook(title, genre, status, author, price,
                    quantityAvailable, discountPercentage);
            books.add(artBook);
        } else if (genre == Genre.TECHNOLOGY) {
            Book technologyBook = new TechnologyBook(title, genre, status, author, price,
                    quantityAvailable, discountPercentage);
            books.add(technologyBook);

        } else if (genre == Genre.SCIENCE) {
            Book scienceBook = new ScienceBook(title, genre, status, author, price,
                    quantityAvailable, discountPercentage);
            books.add(scienceBook);

        } else if (genre == Genre.FICTION) {
            Book fictionBook = new FictionBook(title, genre, status, author, price,
                    quantityAvailable, discountPercentage);
            books.add(fictionBook);

        }

        Collections.sort(books, bookTitleComparator); // sort books by title after adding
        return true;
    }

    public ObservableList<Book> getBookList() {
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
        HashMap<Integer, Book> machedResult = new HashMap();
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

    public boolean addBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage);
}

class Book {
    private String title;
    private String author;
    private Genre genre;
    private BookStatus status;
    private double price;
    private int quantityAvailable;
    private int discountPercentage;
    private String targetAgeGroup;

    public void setBookDetails(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage, String targetAgeGroup) {
        this.title = title;
        this.author = author;
        this.status = status;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.discountPercentage = discountPercentage;
        this.genre = genre;
        this.targetAgeGroup = targetAgeGroup;

    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setquantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Genre getGenre() {
        return genre;
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

    public BookStatus getStatus() {
        return status;
    }

    public String getTargetAgeGroup() {
        return targetAgeGroup;
    }

    public double getPriceAfterMaximumDiscount() {

        return price - (price * (discountPercentage / 100));
    }

    @Override
    public String toString() {
        return "-> Book Details: " +
                "Title: " + title +

                ", Author: " + author +
                ", Status: " + status +
                ", Price: $" + price +
                ", Discount: " + discountPercentage +
                ", Price After Maximum Discount: $" + getPriceAfterMaximumDiscount() +
                ", Quantity Available: " + quantityAvailable;
    }

}

class TechnologyBook extends Book {

    public TechnologyBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {

        super.setBookDetails(title, Genre.TECHNOLOGY, status, author, price, quantityAvailable, discountPercentage,
                "12 to 60 years");

    }

    @Override
    public String toString() {
        return super.toString() + ", " + getTargetAgeGroup();
    }

}

class FictionBook extends Book {

    public FictionBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {

        super.setBookDetails(title, Genre.FICTION, status, author, price, quantityAvailable, discountPercentage,
                "10 to 35 years");

    }

    @Override
    public String toString() {
        return super.toString() + ", " + getTargetAgeGroup();
    }

}

class ScienceBook extends Book {

    public ScienceBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {

        super.setBookDetails(title, Genre.SCIENCE, status, author, price, quantityAvailable, discountPercentage,
                "15 to 50 years");

    }

    @Override
    public String toString() {
        return super.toString() + ", " + getTargetAgeGroup();
    }

}

class ArtBook extends Book {

    public ArtBook(String title, Genre genre, BookStatus status, String author, double price,
            int quantityAvailable, int discountPercentage) {

        super.setBookDetails(title, Genre.ART, status, author, price, quantityAvailable, discountPercentage,
                "2 to 15 years");

    }

    @Override
    public String toString() {
        return super.toString() + ", " + getTargetAgeGroup();
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

        if (this.userID.equals("admin") && this.userPassword.equals("admin")) {
            return true;
        } else {
            return false;
        }

    }
}
