package com.example.git.plurasightlearn1;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

/**
 * Created by Opeyemi on 28/07/2019.
 */
public class NextThroughNoteTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void NextThroughNotes() {
//        onView(withId(R.id.drawer_layout)).perform(DrawerAction);

    }
}