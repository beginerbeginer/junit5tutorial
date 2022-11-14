package bookstoread;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface BookFilter {
  boolean apply(Book book);
}

/**
 * @param startDate 開始日
 */
class BookPublishedYearFilter implements BookFilter {
  private LocalDate startDate;
  /**
   * 引数yearよりも後に出版された本か判定する
   * @param year 年
   * @param month 月
   * @param day 日
   */
  static BookPublishedYearFilter After(int year) {
    BookPublishedYearFilter filter = new BookPublishedYearFilter();
    filter.startDate = LocalDate.of(year, 12, 31);
    return filter;
  }

  /**
   * @return 本の出版日がstartDateよりも後の場合true
   */
  @Override
  public boolean apply(final Book book) { //finalの理由：本の情報を変更することはないため
    return book.getPublishedOn().isAfter(startDate);
  }
}

class CompositeFilter implements BookFilter {
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