package com.danclowry.noteapp.database;

import com.danclowry.noteapp.models.Note;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface Repository {
    Note getById(int id) throws SQLException, NoSuchElementException;
    void createNote(Note note) throws SQLException;
    void updateNote(Note note) throws SQLException;
    void deleteNote(Note note) throws SQLException;
    List<Note> getListOfNotes() throws SQLException;
    boolean testConnection() throws SQLException;
    void setupDatabase() throws SQLException, IOException, URISyntaxException;
}
