package bookstoread;

import java.time.LocalDate;

public class Book implements Comparable<Book> {
    private final String title;
    private final String author;
    private final LocalDate publishedOn;

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
}