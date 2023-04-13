package edu.uncc.inclass12;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GradeDAO {

    @Query("select * from Grade")
    List<Grade> getAll();


    @Query("select * from grade where id = :id limit 1")
    Grade getById(long id);

    @Insert
    void insertAll(Grade... grades);

    @Update
    void update(Grade grade);

    @Delete
    void delete(Grade grade);


}
