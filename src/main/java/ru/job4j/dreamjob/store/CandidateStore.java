package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private AtomicInteger id = new AtomicInteger(4);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Alex", "Junior Java"));
        candidates.put(2, new Candidate(2, "Petr", "Middle Java"));
        candidates.put(3, new Candidate(3, "Ivan", "Senior Java"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        int ids = id.getAndIncrement();
        candidates.put(ids, new Candidate(ids, candidate.getName(), candidate.getDescription()));
    }

    public void update(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }

    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }
}
