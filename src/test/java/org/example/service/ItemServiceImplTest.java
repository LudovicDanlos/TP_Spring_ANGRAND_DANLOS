package org.example.service;

import org.example.dto.ItemResponseDto;
import org.example.entity.Item;
import org.example.repository.ItemRepository;
import org.example.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

        List<ItemResponseDto> response = itemService.findAll();

        assertEquals(1, response.size());
        assertEquals(1L, response.getFirst().getId());
        assertEquals("Premier item", response.getFirst().getName());
        assertEquals("Description de test", response.getFirst().getDescription());
        assertFalse(response.getFirst().isDone());
    }
}
