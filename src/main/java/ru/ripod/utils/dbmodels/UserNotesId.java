package ru.ripod.utils.dbmodels;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;


public class UserNotesId implements Serializable {
    private long userId;
    private long noteId;

    public UserNotesId() {
    }

    public UserNotesId(long userId, long noteId) {
        this.userId = userId;
        this.noteId = noteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNotesId that = (UserNotesId) o;
        return userId == that.userId && noteId == that.noteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, noteId);
    }
}
