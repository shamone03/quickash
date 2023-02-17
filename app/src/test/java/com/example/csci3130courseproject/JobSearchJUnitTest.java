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
@RunWith(JUnit4.class)
public class JobSearchJUnitTest {
    ListingSearchFragment listingSearchFragment;
    @Before
    public void setup() {
        listingSearchFragment = new ListingSearchFragment();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkFilterTrue() {
        assertFalse(listingSearchFragment.filterTitles("", ""));
        assertFalse(listingSearchFragment.filterTitles("abc", "B"));
    }

    @Test
    public void checkFilterFalse() {
        assertFalse(listingSearchFragment.filterTitles("B", "abc"));
    }

}