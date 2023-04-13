package edu.uncc.inclass12;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Grade")
public class Grade {


        @PrimaryKey(autoGenerate = true)
        public long id;

        @ColumnInfo
        public String courseName;

        @ColumnInfo
        public String courseNumber;

        @ColumnInfo
        public int creditHours;

        @ColumnInfo
        public char letterGrade;

        public Grade() {
        }

        public Grade(long id, String courseName, String courseNumber, int creditHours, char letterGrade) {
            this.id = id;
            this.courseName = courseName;
            this.courseNumber = courseNumber;
            this.creditHours = creditHours;
            this.letterGrade = letterGrade;
        }

        public Grade(String courseName, String courseNumber, int creditHours, char letterGrade) {
            this.courseName = courseName;
            this.courseNumber = courseNumber;
            this.creditHours = creditHours;
            this.letterGrade = letterGrade;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseNumber() {
            return courseNumber;
        }

        public void setCourseNumber(String courseNumber) {
            this.courseNumber = courseNumber;
        }

        public int getCreditHours() {
            return creditHours;
        }

        public void setCreditHours(int creditHours) {
            this.creditHours = creditHours;
        }

        public char getLetterGrade() {
            return letterGrade;
        }

        public void setLetterGrade(char letterGrade) {
            this.letterGrade = letterGrade;
        }


}
