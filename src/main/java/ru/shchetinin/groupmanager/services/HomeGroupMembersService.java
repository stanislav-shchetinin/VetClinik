package ru.shchetinin.groupmanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shchetinin.groupmanager.dto.UserDto;
import ru.shchetinin.groupmanager.dto.UserMinusDto;
import ru.shchetinin.groupmanager.dto.UserPlusDto;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.entities.JoinedUserGroup;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.repositories.GroupRepository;
import ru.shchetinin.groupmanager.repositories.JoinedUserGroupRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.shchetinin.groupmanager.util.Checker.*;

@Service
@RequiredArgsConstructor
public class HomeGroupMembersService {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;
    private final JoinedUserGroupRepository jugRepository;

    public ResponseEntity<List<UserDto>> getUsersFromGroup(UUID groupId,
                                                           Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);

        isGroupExist(group);
        isRealCreator(group.get(), principal);

        List<User> members = new ArrayList<>();
        group.get().getJug().forEach(jug -> members.add(jug.getUser()));

        return ResponseEntity.ok(members
                .stream()
                .map(user -> new UserDto(
                        user.getUsername(),
                        jugRepository.findByUserAndGroup(user, group.get()).getNumberClasses()))
                .collect(Collectors.toList()));

    }

    public void addUserInGroup(UUID groupId,
                               String userId,
                               Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userId);

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        JoinedUserGroup jug = new JoinedUserGroup();
        jug.setUser(user);
        jug.setGroup(group.get());
        jug.setNumberClasses(0);
        jugRepository.save(jug);
    }

    public void deleteUserFromGroup(UUID groupId,
                                    String userId,
                                    Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userId);

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        jugRepository.delete(
                jugRepository.findByUserAndGroup(user, group.get())
        );

    }

    public void plusNumberOfCLasses(UUID groupId,
                                    UserPlusDto userPlusDto,
                                    Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userPlusDto.getUsername());

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        JoinedUserGroup jug = jugRepository.findByUserAndGroup(user, group.get());
        Integer curNumberClasses = jug.getNumberClasses();
        jug.setNumberClasses(curNumberClasses + userPlusDto.getPlus());
        jugRepository.save(jug);
    }

    public void minusNumberOfCLasses(UUID groupId,
                                     UserMinusDto userMinusDto,
                                     Principal principal){

        Optional<Group> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userMinusDto.getUsername());

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        JoinedUserGroup jug = jugRepository.findByUserAndGroup(user, group.get());
        Integer curNumberClasses = jug.getNumberClasses();
        jug.setNumberClasses(curNumberClasses - userMinusDto.getMinus());
        jugRepository.save(jug);
    }

}
