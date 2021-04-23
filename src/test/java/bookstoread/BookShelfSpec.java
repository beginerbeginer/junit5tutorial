package bookstoread;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BookShelfSpec {

  //本棚を作成したときに本が入っていない（つまり空である）ことをテストする
  @Test
  void shelf_empty_when_no_book_added() throws Exception {
    BookShelf shelf = new BookShelf();
    List<String> books = shelf.books();
    assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
  }
}
