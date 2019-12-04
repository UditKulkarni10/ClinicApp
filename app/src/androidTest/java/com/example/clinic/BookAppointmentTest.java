package com.example.clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static org.junit.Assert.assertTrue;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



public class BookAppointmentTest {

    @Rule
    public ActivityTestRule<BookAppointment> myActivityTestRule = new ActivityTestRule<BookAppointment>(BookAppointment.class);
    private BookAppointment myBookAppointment = null;
    private TextView searchText;

    @Before
    public void setUp() throws Exception{
        myBookAppointment = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest

    public void checkSearchText() throws Exception{
        searchText = myBookAppointment.findViewById(R.id.searchText);
        searchText.setText("coffee");
        String searchText2 = searchText.getText().toString();
        assertTrue(!searchText2.isEmpty());
    }

    @Test
    public void isSearchMade(){

        onView(withId(R.id.searchText)).perform(typeText("search"), closeSoftKeyboard());
        onView(withId(R.id.serviceChoice)).perform(click());
        onView(withId(R.id.searchClinic)).perform(click());
        onView(withText("Showing results...")).inRoot(new ToastMatcher()).check(matches(withText("Showing results...")));

    }

}
