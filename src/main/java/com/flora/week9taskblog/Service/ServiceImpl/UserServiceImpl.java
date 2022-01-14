package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.Repository.PostRepository;
import com.flora.week9taskblog.Repository.UserRepository;
import com.flora.week9taskblog.Service.UserService;
import com.flora.week9taskblog.model.Post;
import com.flora.week9taskblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PageRequest pageable = PageRequest.of(0, 5);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity addConnection(Long userId, String conUsername) {
        Long connectId = userRepository.lookUpUserIdFromUsername(conUsername);
        if (connectId == 0) return new ResponseEntity("User doesn't exists", HttpStatus.NOT_FOUND);
        if (userRepository.connectionExists(userId, connectId)) return new ResponseEntity(new UserResponse(
                                                "Connection already exists", userRepository.getUserById(connectId)),
                                                    HttpStatus.OK);
        return new ResponseEntity(userRepository.addConnect(userId, connectId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllConnections(Long userId) {
        List<Long> allConnectIds = userRepository.getUserConnectionsIds(userId);
        if (allConnectIds.isEmpty()) return new ResponseEntity("You have no connections", HttpStatus.NO_CONTENT);
        Page<User> users = userRepository.getAllConnections(allConnectIds, pageable);
        return new ResponseEntity(new UserResponse("Successful", users), HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllPostsFromOneConnection(Long userId, String conUsername) {
        Long conId = userRepository.lookUpUserIdFromUsername(conUsername);
        if (conId <= 0) return new ResponseEntity( "User doesn't exists", HttpStatus.NOT_FOUND);

        boolean connExists = userRepository.connectionExists(userId, conId);
        if (conId >= 1 && connExists) return new ResponseEntity( new PostResponse("Found",
                                                        postRepository.getUsersPosts(conId, pageable)),HttpStatus.OK);

        return new ResponseEntity( new PostResponse("User isn't your connection",
                                                        postRepository.getUsersPosts(conId, pageable)),
                                                        HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllPostsFromAllConnections(Long userId) {
        List<Long> allConnectIds = userRepository.getUserConnectionsIds(userId);
        if (allConnectIds.isEmpty()) return new ResponseEntity("You have no connections", HttpStatus.NO_CONTENT);

        List<Page<Post>> connPosts = allConnectIds.stream().map(id -> {
            return postRepository.getUsersPosts(id, pageable);
        }).collect(Collectors.toList());

        if (connPosts.isEmpty()) return new ResponseEntity("No Posts Found", HttpStatus.NO_CONTENT);
        return new ResponseEntity(connPosts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity activateDeactivateAccount(String username) {
        Long id = userRepository.lookUpUserIdFromUsername(username);
        User user = userRepository.getUserById(id);
        if (user.getDeactivated().equals(false)){
            // deactivate user
            user.setDeactivated(true);
            user.setDeactivated_at(Timestamp.valueOf(LocalDateTime.now()));
            userRepository.saveUser(user);
            return new ResponseEntity("Your account has been deactivated", HttpStatus.OK);
        }
        // activate user
        user.setDeactivated(false);
        userRepository.saveUser(user);
        return new ResponseEntity("Your account has been activated", HttpStatus.OK);
    }

    @Override
    @Scheduled(cron = "0 0/2 * * * ?")
//    @Scheduled(cron = "0 */2 * * * ?")
    public void deleteAccount() {
        List<User> users = userRepository.getAllUsers();
        users.forEach(user->{
            if (user.getDeactivated() &&  System.currentTimeMillis() - user.getDeactivated_at().getTime() >= 60 ){
                userRepository.deleteUserById(user.getId());
                System.out.println("Deleted Successfully");
            }
        });
    }
}
