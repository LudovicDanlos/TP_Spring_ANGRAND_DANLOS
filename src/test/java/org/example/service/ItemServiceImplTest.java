package org.example.service;

import org.example.dto.ItemInput;
import org.example.dto.ItemResponse;
import org.example.entity.Item;
import org.example.repository.ItemRepository;
import org.example.service.impl.ItemServiceImpl;
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
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void findAll_shouldReturnMappedResponses() {
        Item firstItem = new Item("Premier item", "Description de test", false);
        firstItem.setId(1L);

        when(itemRepository.findAll()).thenReturn(List.of(firstItem));

        List<ItemResponse> response = itemService.findAll();

        assertEquals(1, response.size());
        assertEquals(1L, response.getFirst().getId());
        assertEquals("Premier item", response.getFirst().getName());
        assertEquals("Description de test", response.getFirst().getDescription());
        assertFalse(response.getFirst().isDone());
    }

    @Test
    void findById_shouldReturnMappedResponses() {
        Item firstItem = new Item("Premier item", "Description de test", false);
        firstItem.setId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(firstItem));

        ItemResponse response = itemService.findById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Premier item", response.getName());
        assertEquals("Description de test", response.getDescription());
        assertFalse(response.isDone());
    }

    @Test
    void update_shouldReturnMappedResponses() {
        Item firstItem = new Item("Premier item", "Description de test", false);
        firstItem.setId(1L);

        ItemInput itemChange = new ItemInput(1L, "Change item", "Description de test change", true);
        Item savedItem = new Item(itemChange.getName(), itemChange.getDescription(), itemChange.isDone());
        savedItem.setId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(firstItem));
        when(itemRepository.save(firstItem)).thenReturn(savedItem);

        ItemResponse response = itemService.update(1L, itemChange);

        assertEquals(1L, response.getId());
        assertEquals("Change item", response.getName());
        assertEquals("Description de test change", response.getDescription());
        assertTrue(response.isDone());
    }

    @Test
    void delete_shouldReturnMappedResponses() {
        Item firstItem = new Item("Premier item", "Description de test", false);
        firstItem.setId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(firstItem));

        itemService.delete(1L);

        verify(itemRepository).delete(firstItem);
    }

    @Test
    void create_shouldReturnMappedResponses() {
        ItemInput itemInput = new ItemInput(1L, "Nouveau item", "Description de test", false);
        Item savedItem = new Item(itemInput.getName(), itemInput.getDescription(), itemInput.isDone());
        savedItem.setId(1L);

        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        ItemResponse response = itemService.create(itemInput);

        assertEquals(1L, response.getId());
        assertEquals("Nouveau item", response.getName());
        assertEquals("Description de test", response.getDescription());
        assertFalse(response.isDone());
    }
}

