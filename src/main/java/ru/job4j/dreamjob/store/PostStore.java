package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {

    private AtomicInteger id = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Description1"));
        posts.put(2, new Post(2, "Middle Java Job", "Description2"));
        posts.put(3, new Post(3, "Senior Java Job", "Description3"));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        int ids = id.getAndIncrement();
        posts.put(ids, new Post(ids, post.getName(), post.getDescription()));
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }

    public Optional<Post> findById(int id) {
        return Optional.ofNullable(posts.get(id));
    }
}
