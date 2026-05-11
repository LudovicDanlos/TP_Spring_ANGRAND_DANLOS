package org.example.service;

import org.example.dto.ItemResponseDto;

import java.util.List;

/**
 * Contrat du service metier.
 * Une seule methode est vraiment implementee dans le squelette.
 * Les autres servent de guide pour le TD / TP.
 */
public interface ItemService {

    List<ItemResponseDto> findAll();

    ItemResponseDto findById(Long id);

    ItemResponseDto create(Object request);

    ItemResponseDto update(Long id, Object request);

    void delete(Long id);
}
