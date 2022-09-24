package ru.job4j.dreamjob.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Optional;

public class CandidateDBStoreTest {

    @Test
    public void whenAddCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Name1", "Description1");
        store.add(candidate);

        Candidate candidateInDb = store.findById(candidate.getId()).get();
        Assertions.assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Name1", "Description1");
        store.add(candidate);
        candidate.setName("Name2");
        store.update(candidate);

        Candidate candidateInDb = store.findById(candidate.getId()).get();
        Assertions.assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenFindCandidateById() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Name1", "Description1");
        store.add(candidate1);
        Candidate candidate2 = new Candidate(0, "Name2", "Description2");
        store.add(candidate2);

        Candidate candidateInDb = store.findById(candidate1.getId()).get();
        Assertions.assertThat(candidateInDb.getName()).isEqualTo(candidate1.getName());
    }

    @Test
    public void whenDoNotFindCandidateById() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Name1", "Description1");
        store.add(candidate);

        Assertions.assertThat(store.findById(0)).isEqualTo(Optional.empty());
    }
}