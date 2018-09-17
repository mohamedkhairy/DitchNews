package com.example.khairy.ditchnews.utils;

import com.google.firebase.crash.FirebaseCrash;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Matches;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Created by khairy on 1/9/2018.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({FirebaseCrash.class})
public class DateUtilsTest {
    String correctInput = "2016-07-25T09:56:27Z";
    String correctOutput = "Mon, 25 Jul 2016";
    String incorrectDate = "2016-07-25T09";

// TODO(15) testing


    @Test
    public void FormatNewsapiDate_inputDate_outputCorrectDate(){
        String outputDate = DateUtils.FormatNewsapiDate(correctInput);
        assertEquals(outputDate ,correctOutput);
    }


    @Test
    public void FormatNewsapiDate_nullInput_nullOutput(){
        String outputDate = DateUtils.FormatNewsapiDate(null);
        assertEquals(outputDate , null);
    }

    @Test
    public void FormatNewsapiDate_incorrectDate_returnsSame(){
        PowerMockito.mockStatic(FirebaseCrash.class);

        String outputDate = DateUtils.FormatNewsapiDate(incorrectDate);
        assertEquals(outputDate , incorrectDate);
        PowerMockito.verifyStatic();
        FirebaseCrash.report(Matchers.isA(ParseException.class));
    }
}
