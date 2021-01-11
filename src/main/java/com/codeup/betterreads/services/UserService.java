package com.codeup.betterreads.services;

import com.codeup.betterreads.models.ClubMember;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.UserRepo;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("usersSvc")
@Transactional
public class UserService {
    UserRepo userDao;

    public UserService(UserRepo userDao) {
        this.userDao = userDao;
    }

    public boolean isLoggedIn() {
        boolean isAnonymousUser =
                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return ! isAnonymousUser;
    }

    // Returns a user obj directly from the DB
    public User loggedInUser() {

        if (! isLoggedIn()) {
            return null;
        }

        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDao.getOne(sessionUser.getId());
    }

    // Checks if the user is the owner of the post
    public boolean isOwner(User postUser){
        if(isLoggedIn()){
            return (postUser.getUsername().equals(loggedInUser().getUsername()));
        }

        return false;

    }

    // Edit controls are being showed up if the user is logged in and it's the same user viewing the file
    public boolean canEditProfile(User profileUser){
        return isLoggedIn() && (profileUser.getId() == loggedInUser().getId());
    }

    public boolean isAdmin(ClubMember clubAdmin) {
        if(isLoggedIn()){
            return (clubAdmin.getIsAdmin());
        }
        return false;
    }

    //testing password reset...
    //TODO: CHECK if UsernameNotFoundException has issues...
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User dbUser = userDao.findUserByEmail(email);

        if (dbUser == null) {
            throw new UsernameNotFoundException("");
        } else {
            dbUser.setResetPasswordToken(token);
            userDao.save(dbUser);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userDao.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userDao.save(user);
    }
}
