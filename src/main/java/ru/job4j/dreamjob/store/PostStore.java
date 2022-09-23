package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {

    private final AtomicInteger id = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        Post post1 = new Post(1, "Junior Java Job", "Description1", true);
        post1.setCity(new City(1, "Москва"));
        Post post2 = new Post(2, "Middle Java Job", "Description2", true);
        post2.setCity(new City(2, "СПб"));
        Post post3 = new Post(3, "Senior Java Job", "Description3", true);
        post3.setCity(new City(3, "Екб"));
        posts.put(1, post1);
        posts.put(2, post2);
        posts.put(3, post3);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        int ids = id.getAndIncrement();
        post.setId(ids);
        posts.put(ids, post);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

    public Optional<Post> findById(int id) {
        return Optional.ofNullable(posts.get(id));
    }
}
