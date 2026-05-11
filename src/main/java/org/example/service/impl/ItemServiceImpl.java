package org.example.service.impl;

import org.example.dto.ItemResponseDto;
import org.example.repository.ItemRepository;
import org.example.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ici se trouve la logique metier.
 *
 * Le squelette ne laisse qu'un seul vrai exemple fonctionnel : findAll().
 * Le reste doit etre complete par les eleves.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemResponseDto> findAll() {
        // Exemple complet :
        // on lit les donnees depuis le repository
        // puis on transforme les entites en DTO de sortie.
        return itemRepository.findAll()
                .stream()
                .map(ItemResponseDto::fromEntity)
                .toList(); // Ici le stream peut être remplacé par plein d'autre moyen. Il sagit juste d'une manière efficace et efficiente d'écrire cette partie.

        /* Autre manière de faire si le stream n'est pas familié
        List<ItemResponse> itemResponses = new ArrayList<>();
        List<Item> all = itemRepository.findAll();

        for (Item item : all) {
            ItemResponse itemResponse = new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.isDone());
            itemResponses.add(itemResponse);
        }
        return itemResponses;
         */
    }

    @Override
    public ItemResponseDto findById(Long id) {
        // TODO eleves :
        // 1. lire l'item depuis le repository
        // 2. gerer le cas ou l'item n'existe pas
        // 3. renvoyer un ItemResponse
        return null;
    }

    @Override
    public ItemResponseDto create(Object request) {
        // TODO eleves :
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appliquer les regles metier
        // 3. creer l'entite
        // 4. sauvegarder avec le repository
        // 5. renvoyer un ItemResponse
        return null;
    }

    @Override
    public ItemResponseDto update(Long id, Object request) {
        // TODO eleves :
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. retrouver l'item en base
        // 3. modifier les champs utiles
        // 4. sauvegarder
        // 5. renvoyer un ItemResponse
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO eleves :
        // 1. retrouver l'item
        // 2. le supprimer
        // 3. reflechir au comportement si l'id n'existe pas
    }
}
