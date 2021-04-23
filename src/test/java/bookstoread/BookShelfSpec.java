package bookstoread;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookShelfSpec {

  //本棚を作成したときに本が入っていない（つまり空である）ことをテストする
  @Test
  public void shelfEmptyWhenNoBookAdded() throws Exception {
    BookShelf shelf = new BookShelf();
    List<String> books = shelf.books();
    assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
  }
}
