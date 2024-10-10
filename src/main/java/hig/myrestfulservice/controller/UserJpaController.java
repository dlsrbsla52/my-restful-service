package hig.myrestfulservice.controller;


import hig.myrestfulservice.bean.Post;
import hig.myrestfulservice.bean.User;
import hig.myrestfulservice.exception.UserNotFoundException;
import hig.myrestfulservice.repository.PostRepository;
import hig.myrestfulservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUserById(@PathVariable(value = "id") int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id-"+id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).getAllUsers());
        entityModel.add(linTo.withRel("all-users")); // all-users -> http://localhost:8080/users

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable(value = "id") int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUsers(@Valid @RequestBody User user) {

        User saveUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByuser(@PathVariable(name = "id") int id) {

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id-"+id);
        }

        return user.get().getPosts();
    }

    @GetMapping("/users/{id}/all")
    public Optional<User> retrieveAllUser(@PathVariable(name = "id") int id) {

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id-"+id);
        }

        return user;
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable(name = "id") int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("id-"+id);
        }

        User user = userOptional.get();

        post.setUser(user);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
