package org.example.repository;

import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Le repository ne contient que l'acces aux donnees.
 * Pas de logique metier ici.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
