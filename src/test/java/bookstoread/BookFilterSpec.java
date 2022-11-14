package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("book published date")
    class BookPublishedFilterSpec{
        @Test
        @DisplayName("is after specified year")
        void validateBookPublishedDatePostAskedYear(){
            BookFilter filter = BookPublishedYearFilter.After(2007);
            assertTrue(filter.apply(cleanCode));
            assertFalse(filter.apply(codeComplete));
        }
    }

    /**
     * 複合条件が正しく機能することを確認するために、複数のフィルターを使用する
     */
    @Test
    @DisplayName("Composite criteria is based on multiple filters") //意味：
    void shouldFilterOnMultiplesCriteria(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter(book -> false); //意味：book -> falseは、bookを受け取ってfalseを返すラムダ式
        assertFalse(compositeFilter.apply(cleanCode)); //
    }

    /**
     * 複合条件は最初の失敗の後、起動しない。
     */
    @Test
    @DisplayName("Composite criteria does not invoke after first failure")
    void shouldNotInvokeAfterFirstFailure(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( b -> false);
        compositeFilter.addFilter( b -> true);
        assertFalse(compositeFilter.apply(cleanCode));
    }

    /**
     * 複合条件はすべてのフィルタを呼び出す
     */
    /
    @Test
    @DisplayName("Composite criteria invokes all filters")
    void shouldInvokeAllFilters(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( b -> true);
        compositeFilter.addFilter( b -> true);
        assertTrue(compositeFilter.apply(cleanCode));
    }
}