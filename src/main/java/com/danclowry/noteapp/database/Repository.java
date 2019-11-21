package com.danclowry.noteapp.database;

import com.danclowry.noteapp.models.Note;

import java.sql.SQLException;
import java.util.List;

public interface Repository {
    Note getById(int id);
    void createNote(Note note);
    void updateNote(Note note);
    void deleteNote(Note note);
    List<Note> getListOfNotes();
    public boolean testConnection() throws SQLException;
}
