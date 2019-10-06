package com.example.git.plurasightlearn1;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by git on 03/07/2019.
 */
@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

    @Rule
   public ActivityTestRule<NoteListActivity> mNoteListActivityActivity
            =new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote(){
        ViewInteraction fabNewNote = onView(withId(R.id.fab));
    fabNewNote.perform(click());
//    onView(withId(R.id.fab)).perform(click());
//    onView(withId(R.id.note_title)).perform(typeText("Test note title"));
//    onView(withId(R.id.note_text)).perform(typeText("This is the body of our note"));
//    closeSoftKeyboard();
    }
}