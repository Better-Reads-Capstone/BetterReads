package com.codeup.betterreads.services;

import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("usersSvc")
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
}
