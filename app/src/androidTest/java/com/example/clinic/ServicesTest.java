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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
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


@RunWith(AndroidJUnit4.class)

public class ServicesTest {
    @Rule
    public ActivityTestRule<Services> myActivityTestRule = new ActivityTestRule<>(Services.class);

    @Test //  we test to see if we added service to list
    public void testServiceAdded(){

        onView(withId(R.id.serviceNameSetText)).perform(typeText("service"), closeSoftKeyboard());
        onView(withId(R.id.nurseRadioBtn)).perform(click());
        onView(withId(R.id.addBtn)).perform(click());

        onView(withText("Service Added!")).inRoot(new ToastMatcher()).check(matches(withText("Service Added!")));

    }

    @Test // we test to see if we edited the service
    public void testServiceEdited(){

        onData(anything()).inAdapterView(withId(list)).atPosition(0).onChildView(withId(serviceEditBtn)).perform(click());
//        onView(withId(R.id.serviceEditBtn)).perform(click());
        onView(withId(R.id.staffRadioBtn)).perform(click());
        onView(withId(R.id.updateServiceBtn)).perform(click());

        onView(withText("Service Edited!")).inRoot(new ToastMatcher()).check(matches(withText("Service Edited!")));


    }

    @Test // we test to see if we deleted the service
    public void testServiceDeleted(){

        onData(anything()).inAdapterView(withId(list)).atPosition(0).onChildView(withId(serviceDeleteBtn)).perform(click());

        onView(withText("Service Deleted!")).inRoot(new ToastMatcher()).check(matches(withText("Service Deleted!")));


    }

}
