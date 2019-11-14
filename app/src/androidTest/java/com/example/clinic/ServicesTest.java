package com.example.clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.clinic.R.id.list;
import static com.example.clinic.R.id.serviceDeleteBtn;
import static com.example.clinic.R.id.serviceEditBtn;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;


@RunWith(AndroidJUnit4.class)

public class ServicesTest {
    @Rule
    public ActivityTestRule<Services> myActivityTestRule = new ActivityTestRule<>(Services.class);

    @Test //  we test to see if we add service to list
    public void testServiceAdded(){

        onView(withId(R.id.serviceNameSetText)).perform(typeText("service"), closeSoftKeyboard());
        onView(withId(R.id.nurseRadioBtn)).perform(click());
        onView(withId(R.id.addBtn)).perform(click());

        onView(withText("Service Added!")).inRoot(new ToastMatcher()).check(matches(withText("Service Added!")));

    }

    @Test
    public void testServiceEdited(){

        // in case there is no service to edit, we will add a service first
        onView(withId(R.id.serviceNameSetText)).perform(typeText("newservice"), closeSoftKeyboard());
        onView(withId(R.id.nurseRadioBtn)).perform(click());
        onView(withId(R.id.addBtn)).perform(click());

        //test to edit
        onData(anything()).inAdapterView(withId(list)).atPosition(0).onChildView(withId(serviceEditBtn)).perform(click());
//        onView(withId(R.id.serviceEditBtn)).perform(click());
        onView(withId(R.id.staffRadioBtn)).perform(click());
        onView(withId(R.id.serviceNameSetText)).perform(clearText());
        onView(withId(R.id.serviceNameSetText)).perform(typeText("service2.0"), closeSoftKeyboard());
        onView(withId(R.id.updateServiceBtn)).perform(click());

        onView(withText("Service Edited!")).inRoot(new ToastMatcher()).check(matches(withText("Service Edited!")));


    }

    @Test
    public void testServiceDeleted(){

        // in case there is no service to delete, we will add a service first
        onView(withId(R.id.serviceNameSetText)).perform(typeText("newservice"), closeSoftKeyboard());
        onView(withId(R.id.nurseRadioBtn)).perform(click());
        onView(withId(R.id.addBtn)).perform(click());

        // we test to delete
        onData(anything()).inAdapterView(withId(list)).atPosition(0).onChildView(withId(serviceDeleteBtn)).perform(click());

        onView(withText("Service Deleted!")).inRoot(new ToastMatcher()).check(matches(withText("Service Deleted!")));


    }

}

