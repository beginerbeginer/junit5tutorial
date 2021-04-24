package src.test.java.bookstoread;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
