package src.test.java.bookstoread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.main.java.bookstoread.Book;
import src.main.java.bookstoread.BookShelf;

@DisplayName("A bookshelf")
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;

    @BeforeEach
    void init() throws Exception {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bioch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Pjillipes Brooks",
                LocalDate.of(1975, Month.JANUARY, 1));
    }

    // 本棚を作成したときに本が入っていない（つまり空である）ことをテストする
    @Test
    @DisplayName("is empty when no book is added to it")
    void shelfEmptyWhenNoBookAdded() throws Exception {
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        shelf.add(effectiveJava, codeComplete);
        List<Book> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        shelf.add();
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient() {
        shelf.add(effectiveJava, codeComplete);
        List<Book> books = shelf.books();
        try {
            books.add(mythicalManMonth);
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
        }
    }

    @Test
    void bookshelfArrangedByBookTitle() {
        BookShelf shelf = new BookShelf();
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange();
        assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth), books,
                () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @Test
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        shelf.arrange();
        List<Book> books = shelf.books();
        assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth), books,
                () -> "Books in bookshelf are in insertion order");

    }

}
