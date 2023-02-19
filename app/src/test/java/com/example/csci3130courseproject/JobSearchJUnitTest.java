package com.example.csci3130courseproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JobSearchJUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkFilterTrue() {
        assertTrue(ListingSearchFragment.filterTitles("", ""));
        assertTrue(ListingSearchFragment.filterTitles("abc", "B"));
    }

    @Test
    public void checkFilterFalse() {
        assertFalse(ListingSearchFragment.filterTitles("B", "abc"));
    }

}