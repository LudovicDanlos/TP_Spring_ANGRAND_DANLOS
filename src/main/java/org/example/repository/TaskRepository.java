package org.example.repository;

import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Le repository ne contient que l'acces aux donnees.
 * Pas de logique metier ici.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Pageable pageable, String status);
    List<Task> findByPriority(Pageable pageable, String priority);
    List<Task> findByStatusAndPriority(Pageable pageable, String status, String priority);
}
