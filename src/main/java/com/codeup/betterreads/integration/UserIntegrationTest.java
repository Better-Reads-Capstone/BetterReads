package com.codeup.betterreads.integration;

import com.codeup.betterreads.BetterreadsApplication;
import com.codeup.betterreads.models.ClubMember;
import com.codeup.betterreads.models.Post;
import com.codeup.betterreads.models.Review;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.PostRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetterreadsApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTest {

    //todo Question: Would it be advantageous to use the @Autowire annotation on these test properties?
    private User testUser;
    private Date testDate = new Date(20200101);
    private List<Post> testPosts;
    private List<ClubMember> testClubMembers;
    private List<Review> testUserReviews;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepo userDao;

    @Autowired
    PostRepo postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("TestUsername");

        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setEmail("TestEmail@Test.com");
            newUser.setUsername("TestUsername");
            newUser.setPassword("TestPassword");
            String hash = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(hash);
            newUser.setFirstName("TestFirstName");
            newUser.setMiddleName("TestMiddleName");
            newUser.setLastName("TestLastName");
            newUser.setPronouns("TestPronoun");
            newUser.setAboutMe("TestAboutMe");
            newUser.setCountry("TestCountry");
            newUser.setWebsiteURL("TestWebsiteURL");
            newUser.setAvatarURL("TestAvatarURL");
            newUser.setDob(testDate);
            newUser.setCreatedDate(testDate);
            newUser.setPosts(testPosts);
            newUser.setClubMembers(testClubMembers);
            newUser.setUserReviews(testUserReviews);
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


}

