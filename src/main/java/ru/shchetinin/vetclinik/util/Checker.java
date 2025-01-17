package ru.shchetinin.vetclinik.util;

import ru.shchetinin.vetclinik.entities.Request;
import ru.shchetinin.vetclinik.exceptions.NotFoundGroupException;
import ru.shchetinin.vetclinik.exceptions.NotRealCreatorException;
import ru.shchetinin.vetclinik.exceptions.UserNotFoundException;
import ru.shchetinin.vetclinik.entities.User;

import java.security.Principal;
import java.util.Optional;

public class Checker {
    public static void isUserExist(User user){
        if (user == null){
            throw new UserNotFoundException();
        }
    }
    public static void isGroupExist(Optional<Request> group){
        if (group.isEmpty()){
            throw new NotFoundGroupException();
        }
    }

    public static void isRealCreator(Request group, Principal principal){
        String nameRealCreator = group.getOwner().getUsername();
        String nameCheckCreator = principal.getName();
        boolean isRealCreator = nameRealCreator.equals(nameCheckCreator);
        if (!isRealCreator){
            throw new NotRealCreatorException();
        }
    }

}
