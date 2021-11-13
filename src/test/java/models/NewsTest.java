package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {
    @Test
    public void News_instantiatesCorrectly() {
        News testNews = setUpNews();
        assertTrue(testNews instanceof News);
    }

    @Test
    public void News_instantiatesCorrectlyWithTitle() {
        News testNews = setUpNews();
        assertEquals("Renaming",testNews.getTitle());
    }

    @Test
    public void News_instantiatesCorrectlyWithContent() {
        News testNews = setUpNews();
        assertEquals("Our department will be renamed",testNews.getContent());
    }

    @Test
    public void setTitle_correctlySetsTitle() {
        News testNews = setUpNews();
        testNews.setTitle("Holidays");
        assertNotEquals("Renaming",testNews.getTitle());
    }

    @Test
    public void setContent_correctlySetsContent() {
        News testNews = setUpNews();
        testNews.setContent("Our holidays starts next week");
        assertNotEquals("Our department will be renamed",testNews.getContent());
    }

    //helpers
    public News setUpNews() {
        return new News("Renaming","Our department will be renamed");
    }
}