package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;
import java.util.Optional;

public class CandidateService {

    private static final CandidateService INST = new CandidateService();

    private final CandidateStore candidateStore = CandidateStore.instOf();

    private CandidateService() {
    }

    public static CandidateService instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }

    public Optional<Candidate> findById(int id) {
        return candidateStore.findById(id);
    }
}
