package com.example.csci3130courseproject;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.csci3130courseproject.UI.HomePage.ListingSearchFragment;

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
        assertTrue(ListingSearchFragment.filterTitle("", ""));
        assertTrue(ListingSearchFragment.filterTitle("abc", "B"));
    }

    @Test
    public void checkFilterFalse() {
        assertFalse(ListingSearchFragment.filterTitle("B", "abc"));
    }

    @Test
    public void FilterSalaryTrue() {
        assertTrue(ListingSearchFragment.filterSalary(11,10));
    }

    @Test
    public void FilterSalaryFalse() {
        assertFalse(ListingSearchFragment.filterSalary(9,10));
    }

    @Test
    public void FilterSalaryEdgeTrue() {
        assertTrue(ListingSearchFragment.filterSalary(10,10));
    }
}