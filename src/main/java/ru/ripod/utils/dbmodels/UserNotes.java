package ru.ripod.utils.dbmodels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(UserNotesId.class)
@Table(name = "userNotes")
public class UserNotes {
    public static final int DEFAULT_RIGHT = -1;
    public static final int READ_ONLY = 0;
    public static final int READ_WRITE = 1;
    public static final int ADMIN = 2;


    @Id
    private long userId;
    @Id
    private long noteId;
    private int right = DEFAULT_RIGHT;

    public UserNotes() {
    }

    public UserNotes(long userId, long noteId, int right) {
        this.userId = userId;
        this.noteId = noteId;
        this.right = right;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
