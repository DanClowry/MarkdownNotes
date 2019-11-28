package com.danclowry.noteapp.database;

import com.danclowry.noteapp.models.Note;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface Repository {
    Note getById(int id) throws SQLException, NoSuchElementException;

    /**
     * Creates a new note in the database using an INSERT INTO statement
     * @param note The note to be inserted into the database
     * @return The ID of the created note
     * @throws SQLException If there is an error executing the statement
     */
    int createNote(Note note) throws SQLException;
    void updateNote(Note note) throws SQLException;
    void deleteNote(Note note) throws SQLException;
    List<Note> getListOfNotes() throws SQLException;
    List<Note> getNotesByTitle(String searchTerm) throws SQLException;
    boolean testConnection() throws SQLException;
    void setupDatabase() throws SQLException, IOException, URISyntaxException;
}
