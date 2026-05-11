package org.example.service;

import org.example.dto.TaskInput;
import org.example.dto.TaskResponse;
import org.example.entity.Task;
import org.example.repository.TaskRepository;
import org.example.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

//    @Mock
//    private TaskRepository taskRepository;
//
//    @InjectMocks
//    private TaskServiceImpl itemService;
//
//    @Test
//    void findAll_shouldReturnMappedResponses() {
//        Task firstTask = new Task("Premier item", "Description de test", false);
//        firstTask.setId(1L);
//
//        when(taskRepository.findAll()).thenReturn(List.of(firstTask));
//
//        List<TaskResponse> response = itemService.findAll();
//
//        assertEquals(1, response.size());
//        assertEquals(1L, response.getFirst().getId());
//        assertEquals("Premier item", response.getFirst().getName());
//        assertEquals("Description de test", response.getFirst().getDescription());
//        assertFalse(response.getFirst().isDone());
//    }
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

