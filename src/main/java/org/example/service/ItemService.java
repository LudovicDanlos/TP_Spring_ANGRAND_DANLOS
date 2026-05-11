package org.example.service;

import org.example.dto.ItemInput;
import org.example.dto.ItemResponse;

import java.util.List;

/**
 * Contrat du service metier.
 * Une seule methode est vraiment implementee dans le squelette.
 * Les autres servent de guide pour le TD / TP.
 */
public interface ItemService {

    List<ItemResponse> findAll();

    ItemResponse findById(Long id);

    ItemResponse create(ItemInput request);

    ItemResponse update(Long id, ItemInput request);

    void delete(Long id);
}
