package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "Description1", true,
                        LocalDateTime.now(), new City(1, "City1")),
                new Post(2, "New post", "Description2", true,
                        LocalDateTime.now(), new City(2, "City2"))
        );
        User user = new User(1, "email", "password");

        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);

        when(postService.findAll()).thenReturn(posts);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any(String.class))).thenReturn(user);
        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.posts(model, request);
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenFormAddPost() {
        Post post = new Post(0, "Заполните поле", "Заполните поле", true);
        User user = new User(1, "email", "password");
        List<City> cities = Arrays.asList(
                new City(1, "City1"),
                new City(2, "City2")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any(String.class))).thenReturn(user);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.addPost(model, request);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "Description1", true);
        City city =  new City(1, "City");
        input.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "New post", "Description1", true);
        City city =  new City(1, "City");
        input.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "Заполните поле", "Заполните поле", true);
        List<City> cities = Arrays.asList(
                new City(1, "City1"),
                new City(2, "City2")
        );
        int id = 1;
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);

        when(cityService.getAllCities()).thenReturn(cities);
        when(postService.findById(id)).thenReturn(post);
        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.formUpdatePost(model, id);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("updatePost");
    }
}