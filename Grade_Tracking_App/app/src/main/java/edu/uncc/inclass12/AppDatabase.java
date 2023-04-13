package edu.uncc.inclass12;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(version=1,entities = {Grade.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GradeDAO gradeDAO();
}
