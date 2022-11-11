package bookstoread;

import java.time.LocalDate;

public interface BookFilter {
  boolean apply(Book book);
}

class BookPublishedYearFilter implements BookFilter {
  /**
   * startDate 開始日
   */
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

  @Override
  public boolean apply(final Book book) { //finalの理由：本の情報を変更することはないため
    /**
     * @return 本の出版日がstartDateよりも後の場合true
     */
    return book.getPublishedOn().isAfter(startDate);
  }
}