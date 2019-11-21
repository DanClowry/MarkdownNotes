package com.danclowry.noteapp.database.MySql;

import com.danclowry.noteapp.database.DatabaseConfig;
import com.danclowry.noteapp.database.Repository;
import com.danclowry.noteapp.models.Note;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
    public Note getById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createNote(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNote(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteNote(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Note> getListOfNotes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean testConnection() throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            return connection.isValid(10);
        }
    }
}
