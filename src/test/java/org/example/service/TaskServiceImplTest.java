package org.example.service;

import org.example.dto.TaskResponse;
import org.example.entity.Task;
import org.example.repository.TaskRepository;
import org.example.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Exemple de test unitaire simple sur la couche service.
 *
 * TODO eleves :
 * - ajoutez des tests sur findById
 * - ajoutez des tests sur update
 * - ajoutez des tests sur delete
 * - ajoutez des tests sur create
 * - ajoutez des tests sur les cas d'erreur
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl itemService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void findAll_shouldReturnMappedResponses() {
        LocalDateTime creationDateTest = LocalDateTime.now();
        LocalDateTime deadlineTest = LocalDateTime.of(2026, 10, 21, 15, 0);
        Task firstTask = new Task("Task Test 1", "Description de test", "HIGH", "TODO", creationDateTest, deadlineTest);
        firstTask.setId(1L);

        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        List<Task> tasks = List.of(firstTask);

        Page<Task> taskPage = new PageImpl<>(tasks);

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);

        List<TaskResponse> response = itemService.findAll(page, size);

        assertEquals(1, response.size());
        assertEquals(1L, response.getFirst().getId());
        assertEquals("Task Test 1", response.getFirst().getName());
        assertEquals("Description de test", response.getFirst().getDescription());
        assertEquals("HIGH", response.getFirst().getPriority());
        assertEquals("TODO", response.getFirst().getStatus());
        assertEquals(creationDateTest.format(formatter), response.getFirst().getCreationDate().format(formatter));
        assertEquals(deadlineTest.format(formatter), response.getFirst().getDeadline().format(formatter));
    }
//
//    @Test
//    void findById_shouldReturnMappedResponses() {
//        Task firstTask = new Task("Premier item", "Description de test", false);
//        firstTask.setId(1L);
//
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(firstTask));
//
//        TaskResponse response = itemService.findById(1L);
//
//        assertEquals(1L, response.getId());
//        assertEquals("Premier item", response.getName());
//        assertEquals("Description de test", response.getDescription());
//        assertFalse(response.isDone());
//    }
//
//    @Test
//    void update_shouldReturnMappedResponses() {
//        Task firstTask = new Task("Premier item", "Description de test", false);
//        firstTask.setId(1L);
//
//        TaskInput itemChange = new TaskInput(1L, "Change item", "Description de test change", true);
//        Task savedTask = new Task(itemChange.getName(), itemChange.getDescription(), itemChange.isDone());
//        savedTask.setId(1L);
//
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(firstTask));
//        when(taskRepository.save(firstTask)).thenReturn(savedTask);
//
//        TaskResponse response = itemService.update(1L, itemChange);
//
//        assertEquals(1L, response.getId());
//        assertEquals("Change item", response.getName());
//        assertEquals("Description de test change", response.getDescription());
//        assertTrue(response.isDone());
//    }
//
//    @Test
//    void delete_shouldReturnMappedResponses() {
//        Task firstTask = new Task("Premier item", "Description de test", false);
//        firstTask.setId(1L);
//
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(firstTask));
//
//        itemService.delete(1L);
//
//        verify(taskRepository).delete(firstTask);
//    }
//
//    @Test
//    void create_shouldReturnMappedResponses() {
//        TaskInput taskInput = new TaskInput(1L, "Nouveau item", "Description de test", false);
//        Task savedTask = new Task(taskInput.getName(), taskInput.getDescription(), taskInput.isDone());
//        savedTask.setId(1L);
//
//        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
//
//        TaskResponse response = itemService.create(taskInput);
//
//        assertEquals(1L, response.getId());
//        assertEquals("Nouveau item", response.getName());
//        assertEquals("Description de test", response.getDescription());
//        assertFalse(response.isDone());
//    }
}

