package org.example.service.impl;

import org.example.dto.ItemInput;
import org.example.dto.ItemResponse;
import org.example.entity.Item;
import org.example.repository.ItemRepository;
import org.example.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<ItemResponse> findAll() {
        // Exemple complet :
        // on lit les donnees depuis le repository
        // puis on transforme les entites en DTO de sortie.
        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::fromEntity)
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
    public ItemResponse findById(Long id) {
        // 1. lire l'item depuis le repository
        // 2. gerer le cas ou l'item n'existe pas
        // 3. renvoyer un ItemResponse
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (itemOptional.isEmpty()) {
            return null;
        } else  {
            Item item = itemOptional.get();
            return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.isDone());
        }
    }

    @Override
    public ItemResponse create(ItemInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appliquer les regles metier
        // 3. creer l'entite
        // 4. sauvegarder avec le repository
        // 5. renvoyer un ItemResponse
        Item item = new Item(request.getName(), request.getDescription(), request.isDone());

        return ItemResponse.fromEntity(itemRepository.save(item));
    }

    @Override
    public ItemResponse update(Long id, ItemInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. retrouver l'item en base
        // 3. modifier les champs utiles
        // 4. sauvegarder
        // 5. renvoyer un ItemResponse
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (itemOptional.isEmpty()) {
            return null;
        } else  {
            Item item = itemOptional.get();
            item.setName(request.getName());
            item.setDescription(request.getDescription());
            item.setDone(request.isDone());
            itemRepository.save(item);
            return new ItemResponse(id, item.getName(), item.getDescription(), item.isDone());
        }
    }

    @Override
    public void delete(Long id) {
        // 1. retrouver l'item
        // 2. le supprimer
        // 3. reflechir au comportement si l'id n'existe pas
        Optional<Item> itemOptional = itemRepository.findById(id);

        itemOptional.ifPresent(itemRepository::delete);
    }
}
