package src.test.java.bookstoread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.main.java.bookstoread.BookShelf;

@DisplayName("A bookshelf")
public class BookShelfSpec {

  //本棚を作成したときに本が入っていない（つまり空である）ことをテストする
  @Test
  @DisplayName("is empty when no book is added to it")
  void shelfEmptyWhenNoBookAdded() throws Exception {
    BookShelf shelf = new BookShelf();
    List<String> books = shelf.books();
    assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
  }

  @Test
  public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
    BookShelf shelf = new BookShelf();
    shelf.add("Effective Java", "Code Complete");
    List<String> books = shelf.books();
    assertEquals(2, books.size(), () -> "BookShelf should have two books.");
  }

  @Test
  public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
    BookShelf shelf = new BookShelf();
    shelf.add();
    List<String> books = shelf.books();
    assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
  }

  @Test
  void booksReturnedFromBookShelfIsImmutableForClient() {
  	BookShelf shelf = new BookShelf();
  	shelf.add("Effective Java", "Code Complete");
  	List<String> books = shelf.books();
  	try {
  		books.add("The Mythical Man-Month");
  		fail(() -> "Should not be able to add book to books");
		} catch (Exception e) {
  		assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
  	}
  }
}
