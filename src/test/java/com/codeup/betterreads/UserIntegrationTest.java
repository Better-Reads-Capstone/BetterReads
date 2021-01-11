package com.codeup.betterreads;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.PostRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpSession;

import java.util.Date;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetterreadsApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTest {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepo userDao;

    @Autowired
    PostRepo postDao;

    @Autowired
    ClubRepo clubDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("TestUsername");

        //CREATE

        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setEmail("TestEmail@Test.com");
            newUser.setUsername("TestUsername");
            newUser.setPassword("TestPassword");
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setFirstName("TestFirstName");
            newUser.setMiddleName("TestMiddleName");
            newUser.setLastName("TestLastName");
            newUser.setPronouns("TestPronoun");
            newUser.setAboutMe("TestAboutMe");
            newUser.setCountry("TestCountry");
            newUser.setWebsiteURL("TestWebsiteURL");
            newUser.setAvatarURL("TestAvatarURL");
            newUser.setDob(new Date());
            newUser.setCreatedDate(new Date());
            testUser = userDao.save(newUser);
        }

        // Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "TestUsername")
                .param("password", "TestPassword"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    //Sanity Check
    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }
    @Test
    public void testIfUserSessionIsActive() throws Exception {
        // It makes sure the returned session is not null
        assertNotNull(httpSession);
    }

    @Test
    public void testShowRegistration() throws Exception {
        this.mvc.perform(get("/sign-up"))
                .andExpect(status().isOk());
    }

    //CLUB TESTS
    //Create Club
    @Test
    public void testCreateClub() throws Exception {
        this.mvc.perform(
                post("/create-club").with(csrf())
                        .session((MockHttpSession) httpSession)
                        // Add all the required parameters to your request like this
                        .param("name", "test")
                        .param("about", "book club about")
                        .param("genre", "1")
                        .param("headerImageUrl", "/img/logo.png"))
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

//    Delete Club
    @Test
    public void testDeleteClub() throws Exception {
        this.mvc.perform(
                post("/create-club").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("name", "club yay")
                        .param("about", "won't last long"))
                .andExpect(status().is3xxRedirection());

        Club existingClub = clubDao.findByName("club yay");
        System.out.println(existingClub.getId());

        this.mvc.perform(
                post("/bookclub/" + existingClub.getId() + "/delete").with(csrf())
                        .session((MockHttpSession) httpSession))
                .andExpect(status().is3xxRedirection());

    }

}

