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

        /**
         * 処理の説明：filter(2007年1月1日)はcleanCode(2008年8月1日)よりも後(isAfter)ではない→filterはcleanCodeより前である
         */
        @Test
        @DisplayName("is before specified year")
        void validateBookPublishedDatePostPreAskedYear(){
            BookFilter filter = BookPublishedYearFilter.Before(2007);
            assertFalse(filter.apply(cleanCode));
            assertTrue(filter.apply(codeComplete));
        }
    }

    /**
     * 複合条件が正しく機能することを確認するために、複数のフィルターを使用する
     */
    @Test
    @DisplayName("Composite criteria is based on multiple filters") //意味：
    void shouldFilterOnMultiplesCriteria(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter(bookFilter -> false); //意味：addFilterメソッドに引数bookFilterを渡す。戻り値はfalse。”->”はラムダ式の記法。bookFilterは引数、falseは戻り値
        assertFalse(compositeFilter.apply(cleanCode)); //
    }

    /**
     * 複合条件は最初の失敗の後、起動しない。
     */
    @Test
    @DisplayName("Composite criteria does not invoke after first failure")
    void shouldNotInvokeAfterFirstFailure(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( bookFilter -> false);
        compositeFilter.addFilter( bookFilter -> true);
        assertFalse(compositeFilter.apply(cleanCode));
    }

    /**
     * 複合条件はすべてのフィルタを呼び出す
     */
    @Test
    @DisplayName("Composite criteria invokes all filters")
    void shouldInvokeAllFilters(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( bookFilter -> true);
        compositeFilter.addFilter( bookFilter -> true);
        assertTrue(compositeFilter.apply(cleanCode));
    }
}