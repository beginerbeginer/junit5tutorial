package src.main.java.bookstoread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookShelf {
    private final List<String> books = new ArrayList<>();

    public List<String> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(String... bookToAdd) {
        Arrays.stream(bookToAdd).forEach(books::add);
    }

    public List<String> arrange() {
        books.sort(Comparator.naturalOrder());
        return books;
    }
}