package com.example.clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ProfileTest {


    @Rule
    public ActivityTestRule<EmployeeProfile> myActivityTestRule = new ActivityTestRule<EmployeeProfile>(EmployeeProfile.class);
    private EmployeeProfile mEmployeeProfile = null;
    private TextView clinicNameText;
    private TextView addressText;
    private TextView phoneText;

    @Before
    public void setUp() throws Exception{
        mEmployeeProfile = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest

    public void checkClinicText() throws Exception{
        clinicNameText = mEmployeeProfile.findViewById(R.id.clinicNameText);
        clinicNameText.setText("Ottawa Clinic");
        String clinicName = clinicNameText.getText().toString();
        assertTrue(!clinicName.isEmpty());
    }

    @Test
    @UiThreadTest
    
    public void checkAddressText() throws Exception{
        addressText = mEmployeeProfile.findViewById(R.id.addressText);
        addressText.setText("123 Hello Street");
        String address = addressText.getText().toString();
        assertTrue(!address.isEmpty());
    }

    @Test
    @UiThreadTest

    public void checkPhoneText() throws Exception{
        phoneText = mEmployeeProfile.findViewById(R.id.phoneNumberText);
        phoneText.setText("613-555-333");
        String phone = phoneText.getText().toString();
        assertTrue(!phone.isEmpty());
    }
}
