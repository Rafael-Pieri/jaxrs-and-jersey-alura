package br.com.alura.store.repository;

import br.com.alura.store.model.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ProjectRepository extends Repository<Project, Long> {

    Optional<Project> findOne(Long id);

    Optional<List<Project>> findAll();

    Optional<Project> save(Project project);

    void delete(Long id);

    boolean exists(Long id);
}