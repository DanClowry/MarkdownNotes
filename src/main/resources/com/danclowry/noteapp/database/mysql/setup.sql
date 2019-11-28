CREATE DATABASE IF NOT EXISTS Notes DEFAULT CHARACTER SET utf8mb4;
USE Notes;

CREATE TABLE IF NOT EXISTS Notes.Note (
    NoteID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(100) NOT NULL,
    Content MEDIUMTEXT CHARACTER SET utf8mb4
);

DROP PROCEDURE IF EXISTS Note_Create;
CREATE PROCEDURE Note_Create
(
    IN title VARCHAR(100),
    IN content MEDIUMTEXT
)
BEGIN
    INSERT INTO Note
    (
        Title,
        Content
    )
    VALUES
    (
        title,
        content
    );
    SELECT LAST_INSERT_ID();
END;

DROP PROCEDURE IF EXISTS Note_Update;
CREATE PROCEDURE Note_Update
(
    IN id INT,
    IN title VARCHAR(100),
    IN content MEDIUMTEXT
)
BEGIN
    UPDATE Note
    SET Title = title, Content = content
    WHERE NoteID = id;
END;

DROP PROCEDURE IF EXISTS Note_Delete;
CREATE PROCEDURE Note_Delete
(
    IN id INT
)
BEGIN
    DELETE FROM Note
    WHERE NoteID = id;
END;

DROP PROCEDURE IF EXISTS Note_SelectAll;
CREATE PROCEDURE Note_SelectAll()
BEGIN
    SELECT * FROM Note;
END;

DROP PROCEDURE IF EXISTS Note_SelectByID;
CREATE PROCEDURE Note_SelectByID
(
    IN id INT
)
BEGIN
    SELECT * FROM Note WHERE NoteID = id;
END;

DROP PROCEDURE IF EXISTS Note_SelectByTitle;
CREATE PROCEDURE Note_SelectByTitle
(
    IN _title VARCHAR(100)
)
BEGIN
    SELECT * FROM Note WHERE Title LIKE CONCAT('%', _title, '%');
END
