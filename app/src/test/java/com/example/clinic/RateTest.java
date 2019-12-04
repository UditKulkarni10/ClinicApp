package com.example.clinic;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RateTest {

/*
    @Rule
    public ActivityTestRule<Rate> myActivityTestRule = new ActivityTestRule<Rate>(Rate.class);
    private Rate myRate = null;
    private TextView ClinicNameText;
    private TextView RatingText;
    private TextView CommentsText;

    @Before
    public void setUp() throws Exception{
        myRate = myActivityTestRule.getActivity();
    }
*/

    @Test
    public void checkClinicName() throws Exception {
        Rate rate = new Rate();
        rate.setClinicName("TestClinicName");
        assertEquals("TestClinicName", rate.getClinicName());
    }

    @Test
    public void checkComments() throws Exception {
        Rate rate = new Rate();
        rate.setComments("TestComments");
        assertEquals("TestComments", rate.getComments());
    }

    @Test
    public void checkRating() throws Exception {
        Rate rate = new Rate();
        rate.setRating("TestRating");
        assertEquals("TestRating", rate.getRating());
    }

}
