package com.example.clinic;

import android.util.Log;
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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)

public class createLoginTest {
    @Rule
    public ActivityTestRule<createLogin> myActivityTestRule = new ActivityTestRule<>(createLogin.class);

    @Test
//    @UiThreadTest

    // Test to see if account is created
    public void isAccountCreated(){

        onView(withId(R.id.firstName)).perform(typeText("firstname"), closeSoftKeyboard());
        onView(withId(R.id.lastName)).perform(typeText("lastname"), closeSoftKeyboard());
        onView(withId(R.id.patientBtn)).perform(click());
        onView(withId(R.id.username)).perform(typeText("email@email.com"), closeSoftKeyboard());
        onView(withId(R.id.pwd)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.confpwd)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.create)).perform(click());
        onView(withText("Account Created!")).inRoot(new ToastMatcher()).check(matches(withText("Account Created!")));
    }

}
