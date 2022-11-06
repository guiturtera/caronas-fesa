import sqlite3 as sql
import pandas as pd

conn = None

try:
    conn = sql.connect("database.db")
    cursor = conn.cursor()

    cursor.executescript(
        """
        DROP TABLE IF EXISTS [User];
        CREATE TABLE IF NOT EXISTS [User] (
            [id] INTEGER PRIMARY KEY,
            [name] TEXT,
            [description] TEXT,
            [student_id] TEXT,
            [course] INTEGER,
            [phone_number] TEXT,
            [password] TEXT
        );
        
        DROP TABLE IF EXISTS [Location];
        CREATE TABLE IF NOT EXISTS [Location] (
            [id] INTEGER PRIMARY KEY,
            [latitude] REAL,
            [longitude] REAL
        );
        
        DROP TABLE IF EXISTS [Post];
        CREATE TABLE IF NOT EXISTS [Post] (
            [id] INTEGER PRIMARY KEY,
            [title] TEXT,
            [description] TEXT,
            [place_of_departure] INTEGER,
            [destination] INTEGER,
            [available_weekdays] TEXT,
            available_seats INTEGER,
            departure_time TEXT,
            FOREIGN KEY([place_of_departure]) REFERENCES [Location](id),
            FOREIGN KEY([destination]) REFERENCES [Location](id)
        );
        """
    )

finally:
    if conn:
        conn.close()
