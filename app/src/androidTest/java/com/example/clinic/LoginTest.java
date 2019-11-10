package com.example.clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)

public class LoginTest {
    @Rule
    public ActivityTestRule<Login> myActivityTestRule = new ActivityTestRule<>(Login.class);
    @Test

    public void testActivity() throws Exception{
        onView(withId(R.id.userEmail)).perform(typeText("email@email.com"));
        onView(withId(R.id.userPassword)).perform(typeText("hello12"));
        onView(withId(R.id.loginBtn)).perform(click());
    }



}
