package bookstoread;

import java.util.Collections;
import java.util.List;

/*テストケースを通過させるために、BookShelf books メソッドの実装を変更
 *空のリストを返すようにした
 * */

public class BookShelf {
  public List<String> books() {
    return Collections.emptyList();
  }
}
