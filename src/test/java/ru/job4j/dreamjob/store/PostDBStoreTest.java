package ru.job4j.dreamjob.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Optional;

public class PostDBStoreTest {

    @Test
    public void whenAddPost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Name1", "Description1",
                true, LocalDateTime.now(), new City());
        store.add(post);

        Post postInDb = store.findById(post.getId()).get();
        Assertions.assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Name1", "Description1",
                true, LocalDateTime.now(), new City());
        store.add(post);
        post.setName("Name2");
        store.update(post);

        Post postInDb = store.findById(post.getId()).get();
        Assertions.assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenFindPostById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post1 = new Post(0, "Name1", "Description1",
                true, LocalDateTime.now(), new City());
        Post post2 = new Post(0, "Name2", "Description2",
                true, LocalDateTime.now(), new City());
        store.add(post1);
        store.add(post2);

        Post postInDb = store.findById(post1.getId()).get();
        Assertions.assertThat(postInDb.getName()).isEqualTo(post1.getName());
    }

    @Test
    public void whenDoNotFindPostById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Name1", "Description1",
                true, LocalDateTime.now(), new City());
        store.add(post);

        Assertions.assertThat(store.findById(0)).isEqualTo(Optional.empty());
    }
}