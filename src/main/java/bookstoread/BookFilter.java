package bookstoread;

import java.time.LocalDate;

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