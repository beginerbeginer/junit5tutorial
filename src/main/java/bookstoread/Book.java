package bookstoread;

import java.time.LocalDate;

public class Book implements Comparable<Book> {
    private final String title;
    private final String author;
    private final LocalDate publishedOn;
    private LocalDate startedReadingOn;
    private LocalDate finishedReadingOn;

    public Book(String title, String author, LocalDate publishedOn) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
    }

    /**
     * @return title 本のタイトル
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @return author 本の著者
     */
    public final String getAuthor() {
        return author;
    }

    /**
     * @return publishedOn 本の出版日
     */
    public final LocalDate getPublishedOn() {
        return publishedOn;
    }

    /**
     * TODO自動生成されたメソッド・スタブ
     * @return タイトル、著者、出版日
     */
    @Override
    public String toString() {

        return "Book{" + "title='" + title + '\'' + ", author='" + author + '\'' + ", publishedOn=" + publishedOn + '}';
    }

    @Override
    public int compareTo(Book book) {
        return this.title.compareTo(book.title);
    }

    public void startedReadingOn(LocalDate startedOn) {
        this.startedReadingOn = startedOn;
    }

    public void finishedReadingOn(LocalDate finishedOn) {
        this.finishedReadingOn = finishedOn;
    }

    /**
     * @return 開始日と終了日がnullではなく、本を読み終わっている場合はtrue
     */
    public boolean isRead() {
        return startedReadingOn != null && finishedReadingOn != null;
    }

    /**
     * @return 終了日がnullで、本を読み終わっていない場合はtrue
     */
    public boolean isProgress() {
        return startedReadingOn != null && finishedReadingOn == null;
    }
}