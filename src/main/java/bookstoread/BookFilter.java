package bookstoread;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface BookFilter {
  boolean apply(Book book);
}

class BookPublishedYearFilter implements BookFilter {

  private Function<LocalDate, Boolean> comparison;
  /**
   * 引数yearよりも後に出版された本か判定する
   * @param year 年
   * @param month 月
   * @param day 日
   */
  static BookPublishedYearFilter After(int year) {
    final LocalDate date = LocalDate.of(year, 12, 31);
    BookPublishedYearFilter filter = new BookPublishedYearFilter();
    filter.comparison = date::isBefore;
    return filter;
  }

  /**
   * 引数yearよりも前に出版された本か判定する（isBeforeではなくisAfterメソッドにする理由：引数yearよりも大きい値が入った場合にfalseを返したいから）
   * @param year 年
   * @param month 月
   * @param day 日
   */
  static BookPublishedYearFilter Before(int year) {
    final LocalDate date = LocalDate.of(year, 1, 1);
    BookPublishedYearFilter filter = new BookPublishedYearFilter();
    filter.comparison = date::isAfter;
    return filter;
  }

  /**
   * @param book 本のタイトル+著者+出版日
   * @return 引数bookがnullではない＆comparisonに出版日の値を渡し、真偽値を返す
   */
  @Override
  public boolean apply(final Book book) { //final：本の情報を変更することはないため
    return book!=null && comparison.apply(book.getPublishedOn());
  }
}

class CompositeFilter implements BookFilter {
  //finalをつけない理由：addFilterメソッドでfiltersに値を追加するケースがあるため
  private List<BookFilter> filters;

  CompositeFilter() {
    filters = new ArrayList<>();
  }

  @Override
  public boolean apply(final Book b) {
    return filters.stream()
            .map(bookFilter -> bookFilter.apply(b))
            .reduce(true, (b1, b2) -> b1 && b2);
  }

  void addFilter(final BookFilter bookFilter) {
    filters.add(bookFilter);
  }
}