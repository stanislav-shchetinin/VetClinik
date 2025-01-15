package ru.shchetinin.groupmanager.util;

import lombok.RequiredArgsConstructor;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.exceptions.NotFoundGroupException;
import ru.shchetinin.groupmanager.exceptions.NotRealCreatorException;
import ru.shchetinin.groupmanager.exceptions.UserNotFoundException;
import ru.shchetinin.groupmanager.entities.User;

import java.security.Principal;
import java.util.Optional;

public class Checker {
    public static void isUserExist(User user){
        if (user == null){
            throw new UserNotFoundException();
        }
    }
    public static void isGroupExist(Optional<Group> group){
        if (group.isEmpty()){
            throw new NotFoundGroupException();
        }
    }

    public static void isRealCreator(Group group, Principal principal){
        String nameRealCreator = group.getOwner().getUsername();
        String nameCheckCreator = principal.getName();
        boolean isRealCreator = nameRealCreator.equals(nameCheckCreator);
        if (!isRealCreator){
            throw new NotRealCreatorException();
        }
    }

}
