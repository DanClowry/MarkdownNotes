package com.danclowry.noteapp.database.mysql;

import com.danclowry.noteapp.database.DatabaseConfig;
import com.danclowry.noteapp.database.Repository;
import com.danclowry.noteapp.models.Note;
import com.danclowry.noteapp.util.LoadResource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MySqlRepository implements Repository {
    MysqlDataSource dataSource = new MysqlDataSource();

    public MySqlRepository() throws SQLException {
        dataSource.setURL("jdbc:mysql://" + DatabaseConfig.getHostname() + "/notes");
        dataSource.setUser(DatabaseConfig.getUsername());
        dataSource.setPassword(DatabaseConfig.getPassword());
        dataSource.setServerTimezone("UTC");
    }

    public MySqlRepository(String hostname, String username, String password) throws SQLException {
        dataSource.setURL("jdbc:mysql://" + hostname + "/notes");
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setServerTimezone("UTC");
    }

    @Override
    public Note getById(int id) throws SQLException, NoSuchElementException {
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL Note_SelectByID(?)}");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new NoSuchElementException("A note with the specified ID was not found");
            }
            resultSet.first();
            return new Note(resultSet.getInt("NoteID"), resultSet.getString("Title"),
                    resultSet.getString("Content"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createNote(Note note) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL Note_Create(?, ?)}");
            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            ResultSet resultSet = stmt.executeQuery();
            resultSet.first();
            return resultSet.getInt("LAST_INSERT_ID()");
        }
    }

    @Override
    public void updateNote(Note note) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL Note_Update(?, ?, ?)}");
            stmt.setInt(1, note.getId());
            stmt.setString(2, note.getTitle());
            stmt.setString(3, note.getContent());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteNote(Note note) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL Note_Delete(?)}");
            stmt.setInt(1, note.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Note> getListOfNotes() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL Note_SelectAll}");
            ResultSet resultSet = stmt.executeQuery();

            List<Note> notes = new ArrayList<>();
            while (resultSet.next()) {
                Note note = new Note(resultSet.getInt("NoteID"), resultSet.getString("Title"),
                        resultSet.getString("Content"));
                notes.add(note);
            }
            return notes;
        }
    }

    @Override
    public List<Note> getNotesByTitle(String searchTerm) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL Note_SelectByTitle(?)}");
            stmt.setString(1, searchTerm);
            ResultSet resultSet = stmt.executeQuery();

            List<Note> notes = new ArrayList<>();
            while (resultSet.next()) {
                Note note = new Note(resultSet.getInt("NoteID"), resultSet.getString("Title"),
                        resultSet.getString("Content"));
                notes.add(note);
            }
            return notes;
        }
    }

    @Override
    public void setupDatabase() throws SQLException, IOException {
        MysqlDataSource setupDataSource = new MysqlDataSource();
        setupDataSource.setURL("jdbc:mysql://" + DatabaseConfig.getHostname());
        setupDataSource.setUser(DatabaseConfig.getUsername());
        setupDataSource.setPassword(DatabaseConfig.getPassword());
        setupDataSource.setServerTimezone("UTC");
        setupDataSource.setAllowMultiQueries(true);

        try (Connection connection = setupDataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            String script = LoadResource.LoadResource("database/mysql/setup.sql");
            stmt.executeUpdate(script);
        }

        try (Connection connection = dataSource.getConnection()) {
            String autoInc = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = Database() AND TABLE_NAME = 'Note'";
            String sql = "INSERT INTO Note (Title, Content) SELECT 'Welcome to Markdown', ? WHERE (" +
                    "SELECT Count(*) FROM Note) = 0";
            String cheatsheet = LoadResource.LoadResource("database/cheatsheet.md");
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cheatsheet);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean testConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(10);
        }
    }
}
