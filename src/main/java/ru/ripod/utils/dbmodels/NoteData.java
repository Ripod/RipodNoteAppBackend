package ru.ripod.utils.dbmodels;

import javax.persistence.*;

@Entity
@Table(name = "notedata")
public class NoteData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noteId;
    private String address;
    private String title;
    private boolean passwordProtected;
    private String password;
    private boolean shared;

    public NoteData() {
    }

    public NoteData(long noteId, String address, String title, boolean passwordProtected, String password, boolean shared) {
        this.noteId = noteId;
        this.address = address;
        this.title = title;
        this.passwordProtected = passwordProtected;
        this.password = password;
        this.shared = shared;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
