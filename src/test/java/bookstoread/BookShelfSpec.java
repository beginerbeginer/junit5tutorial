package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A bookshelf")
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init() throws Exception {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));

    }

    @Nested
    @DisplayName("is empty")
    class IsEmpty {

        // 本棚を作成したときに本が空の状態であることをテストする
        @Test
        @DisplayName("when no book is added to it")
        void shelfEmptyWhenNoBookAdded() throws Exception {
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

        @Test
        @DisplayName("when add is called without books")
        public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            shelf.add();
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

    }

    @Nested
    @DisplayName("after adding books")
    class BooksAreAdded {

        @Test
        @DisplayName("contains two books")
        public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            assertEquals(2, books.size(), () -> "BookShelf should have two books.");
        }

        @Test
        @DisplayName("returned an immutable books collection to client")
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
    }


    @Nested
    @DisplayName("is arranged")
    class BooksAreArranged {

        @Test
        @DisplayName("lexicographically by book title")
        void bookshelfArrangedByBookTitle() {
            BookShelf shelf = new BookShelf();
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> books = shelf.arrange();
            assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth), books,
                    () -> "Books in a bookshelf should be arranged lexicographically by book title");
        }

        // ユーザーが与えた基準で本棚を並べる（逆順で並んでいること）
        @Test
        @DisplayName("by user provided criteria(by book title lexicographically descending)")
        void bookshelfArrangedByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
        }

        @Test
        @DisplayName("by book publication date in ascending order")
        void bookshelfArrangedByAnotherUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> books = shelf.arrange((b1, b2) -> b1.getPublishedOn().compareTo(b2.getPublishedOn()));
            assertEquals(Arrays.asList(mythicalManMonth, codeComplete, effectiveJava), books,
                    () -> "Books in a bookshelf are arranged by book publication date in ascending order");
        }

    }

    // arrange を呼び出した後、本棚の本は挿入順で並んでいること
    @Test
    @DisplayName("books in bookshelf are in insertion order after calling arrange")
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        shelf.arrange();
        List<Book> books = shelf.books();
        assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth), books,
                () -> "Books in bookshelf are in insertion order");
    }

    /**
     * writing a Failing Test
     */
    @Nested
    @DisplayName("books are grouped by")
    class GroupBy {

        @Test
        @DisplayName("publication year")
        void groupedBooksInsideBookShelfByPublicationYear() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2004))
                    .containsValues(singletonList(codeComplete));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(1975))
                    .containsValues(singletonList(mythicalManMonth));
        }

        // 本棚の本は、ユーザーが指定した基準でグループ化されます（著者名でグループ化）
        @Test
        @DisplayName("user provided criteria(group by author name)")
        void groupBooksByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

            assertThat(booksByAuthor)
                    .containsKey("Joshua Bloch")
                    .containsValues(singletonList(effectiveJava));

            assertThat(booksByAuthor)
                    .containsKey("Steve McConnel")
                    .containsValues(singletonList(codeComplete));

            assertThat(booksByAuthor)
                    .containsKey("Frederick Phillips Brooks")
                    .containsValues(singletonList(mythicalManMonth));

            assertThat(booksByAuthor)
                    .containsKey("Robert C. Martin")
                    .containsValues(singletonList(cleanCode));
        }
    }
}
