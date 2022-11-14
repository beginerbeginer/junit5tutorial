package bookstoread;

/**
 * @param completed すでに読了しているページの割合
 * @param toRead 残りページの割合
 * @param inProgress 読了したページの割合
 */
public class Progress {

    private final int completed;
    private final int toRead;
    private final int inProgress;

    public Progress(int completed, int toRead, int inProgress) {
        this.completed = completed;
        this.toRead = toRead;
        this.inProgress = inProgress;
    }

    public static Progress notStarted() {
        return new Progress(0, 0, 0);
    }


    public int completed() {
        return this.completed;
    }

    public int toRead() {
        return this.toRead;
    }

    public int inProgress() {
        return this.inProgress;
    }
}