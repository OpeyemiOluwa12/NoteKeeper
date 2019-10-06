package com.example.git.plurasightlearn1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by git on 27/06/2019.
 */
public class DataManagerTest {
    @Test
    public void createNewNote() throws Exception {
        final DataManager dm = DataManager.getInstance();
        final CourseInfo course = dm.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body text of my test note";

        int noteIndex = dm.createNewNote();
        NoteInfo newNote = dm.getNotes().get(noteIndex -1);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        NoteInfo compareNote = dm.getNotes().get(noteIndex);

        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle() );
        assertEquals(noteText, compareNote.getText());
    }

}