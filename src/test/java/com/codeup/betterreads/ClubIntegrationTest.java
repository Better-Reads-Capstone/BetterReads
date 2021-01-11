package com.codeup.betterreads;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.ClubRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetterreadsApplication.class)
@AutoConfigureMockMvc
public class ClubIntegrationTest {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ClubRepo clubDao;

    //CLUB TESTS
    //Create Club
    @Test
    public void testCreateClub() throws Exception {
        this.mvc.perform(
                post("/create-club/**").with(csrf())
                        .session((MockHttpSession) httpSession)
                        // Add all the required parameters to your request like this
                        .param("name", "test")
                        .param("about", "book club about")
                        .param("genre", "1"))
                .andExpect(status().is3xxRedirection());
    }

    //Read club
    @Test
    public void testShowClub() throws Exception {

        Club existingClub = clubDao.findAll().get(0);

        this.mvc.perform(get("/bookclub/" + existingClub.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingClub.getName())));
    }

    //Read all clubs
    @Test
    public void testAdsIndex() throws Exception {
        Club existingClub = clubDao.findAll().get(0);

        this.mvc.perform(get("/bookclubs"))
                .andExpect(status().isOk())
                // Test the static content of the page
                .andExpect(content().string(containsString("<label for=\"genreSelect\">Filter By Genre:</label>")))
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingClub.getName())));
    }

    //Edit Club
    @Test
    public void testEditClub() throws Exception {
        Club existingClub = clubDao.findAll().get(0);

        this.mvc.perform(
                post("/edit-bookclub/" + existingClub.getId()).with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("name", "edited name")
                        .param("about", "edited about")
                        .param("genre", "1"))
                .andExpect(status().is3xxRedirection());

        this.mvc.perform(get("/bookclub/" + existingClub.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString("edited name")))
                .andExpect(content().string(containsString("edited about")));
    }

    //Delete Club
    @Test
    public void testDeleteAd() throws Exception {
        // Creates a test Ad to be deleted
        this.mvc.perform(
                post("/create-club/**").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("name", "club to be deleted")
                        .param("about", "won't last long")
                        .param("genre", "1"))
                .andExpect(status().is3xxRedirection());

        // Get the recent Ad that matches the title
        Club existingClub = clubDao.findByName("club to be deleted");

        // Makes a Post request to /ads/{id}/delete and expect a redirection to the Ads index
        this.mvc.perform(
                post("/bookclub/" + existingClub.getId() + "/delete").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("id", String.valueOf(existingClub.getId())))
                .andExpect(status().is3xxRedirection());
    }
}
