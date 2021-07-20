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
     * @return title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @return author
     */
    public final String getAuthor() {
        return author;
    }

    /**
     * @return publishedOn
     */
    public final LocalDate getPublishedOn() {
        return publishedOn;
    }

    @Override
    public String toString() {
        // TODO 自動生成されたメソッド・スタブ
        return "Book{" + "title='" + title + '\'' + ", author='" + author + '\'' + ", publishedOn=" + publishedOn + '}';
    }

    @Override
    public int compareTo(Book that) {
        return this.title.compareTo(that.title);
    }

    public void startedReadingOn(LocalDate startedOn) {
        this.startedReadingOn = startedOn;
    }

    public void finishedReadingOn(LocalDate finishedOn) {
        this.finishedReadingOn = finishedOn;
    }

    // 読み終わっている場合はtrueを返す（両方の日付に値がある場合）
    public boolean isRead() {
        return startedReadingOn != null && finishedReadingOn != null;
    }

    public boolean isProgress() {
        return startedReadingOn != null && finishedReadingOn == null;
    }
}