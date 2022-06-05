package ru.ripod.utils.restmodels.responses;

public class NoteDataResponse extends BaseResponse {
    private long noteId;

    public NoteDataResponse(int code, String message, long noteId) {
        super(code, message);
        this.noteId = noteId;
    }

    public NoteDataResponse(String message, long noteId) {
        super(0, message);
        this.noteId = noteId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
