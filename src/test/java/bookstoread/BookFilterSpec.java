package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 各テストに対して共通の振る舞いが期待できるテスト
 */
interface FilterBoundaryTests {
    BookFilter get();

    @Test
    @DisplayName("should not fail for null book.")
    default void nullTest() { assertThat(get().apply(null)).isFalse(); }
}

//publicをつけず同じパッケージ外からアクセスできないようにする長所：テストのみに使うクラスなので、パッケージ外からアクセスできないことで、テストのみに使うクラスであることを明示できる
class BookFilterSpec {
    private Book cleanCode;
    private Book codeComplete;

    @BeforeEach
    void init() {
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
    }

    @Nested
    @DisplayName("book published date post specified year")
    class BookPublishedAfterFilterSpec implements FilterBoundaryTests {
        BookFilter filter;

        @BeforeEach
        void init() {
            filter = BookPublishedYearFilter.After(2007);
        }

        @Override
        public BookFilter get() {
            return filter;
        }

        @Test
        @DisplayName("should give matching book")
        void validateBookPublishedDatePostAskedYear() {
            assertTrue(filter.apply(cleanCode));
            assertFalse(filter.apply(codeComplete));
        }
    }

    @Nested
    @DisplayName("book published date pre specified year")
    class BookPublishedBeforeFilterSpec implements FilterBoundaryTests {

        BookFilter filter;

        @BeforeEach
        void init() {
            filter = BookPublishedYearFilter.Before(2007);
        }

        @Override
        public BookFilter get() { return filter; }

        @Test
        @DisplayName("should give matching book")
        void validateBookPublishedDatePreAskedYear() {
            assertFalse(filter.apply(cleanCode));
            assertTrue(filter.apply(codeComplete));
        }
    }

    /**
     * フィルターが正しく機能することを検証するためのテスト<br>
     * フィルターを呼び出し、フィルターが入力をされた値を受け取った際に、期待される出力を返す
     */
    @Test
    @DisplayName("Composite criteria is based on multiple filters(複合条件が正しく機能することを確認するために、複数のフィルターを使用する)")
    void shouldFilterOnMultiplesCriteria(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter(bookFilter -> false); //意味：addFilterメソッドに引数bookFilterを渡す。戻り値はfalse。”->”はラムダ式の記法。bookFilterは引数、falseは戻り値
        assertFalse(compositeFilter.apply(cleanCode)); //
    }

    @Test
    @DisplayName("Composite criteria  invokes all in case of failure(複合条件の適応が失敗し、全てのフィルターが実行されることを確認する)")
    void shouldInvokeAllInFailure(){
        CompositeFilter compositeFilter = new CompositeFilter();

        BookFilter invokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(invokedMockedFilter.apply(cleanCode)).thenReturn(false);
        compositeFilter.addFilter(invokedMockedFilter);

        BookFilter secondInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(secondInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
        compositeFilter.addFilter(secondInvokedMockedFilter);

        assertFalse(compositeFilter.apply(cleanCode));
        Mockito.verify(invokedMockedFilter).apply(cleanCode);
        Mockito.verify(secondInvokedMockedFilter).apply(cleanCode);
    }

    @Test
    @DisplayName("Composite criteria invokes all filters(複合条件が成功し、全てのフィルターが実行されることを確認する)")
    void shouldInvokeAllFilters(){
        CompositeFilter compositeFilter = new CompositeFilter();

        BookFilter firstInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(firstInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
        compositeFilter.addFilter(firstInvokedMockedFilter);

        BookFilter secondInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(secondInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
        compositeFilter.addFilter(secondInvokedMockedFilter);

        assertTrue(compositeFilter.apply(cleanCode));
        Mockito.verify(firstInvokedMockedFilter).apply(cleanCode);
        Mockito.verify(secondInvokedMockedFilter).apply(cleanCode);
    }

    class MockedFilter implements BookFilter {
        boolean returnValue;
        boolean invoked;

        MockedFilter(boolean returnValue) {
            this.returnValue = returnValue;
        }

        @Override
        public boolean apply(Book b) {
            invoked = true;
            return returnValue;
        }
    }

}