# TD / TP Spring Boot

Socle de depart pour les eleves.

## Objectif

Comprendre une architecture backend propre avec :

- `controller`
- `service`
- `repository`
- `entity`
- `dto`
- `tests`

## Base de donnees

Le projet est configure pour se connecter au PostgreSQL Docker fourni a cote.

Configuration actuelle dans `src/main/resources/application.properties` :

- base : `coursBasile`
- utilisateur : `user`
- mot de passe : `Pass2026!`
- port : `5432`

## Idee du socle

Le projet utilise un objet generique `Item`.

Les eleves peuvent :

- garder `Item` tel quel
- ou le renommer selon leur sujet :
  - `Book`
  - `Movie`
  - `Expense`
  - `Task`
  - etc.

## Ce qui fonctionne deja

- le projet demarre
- la connexion PostgreSQL est configuree
- l'entite `Item` existe
- le repository existe
- l'endpoint `GET /items` est complet
- un test unitaire d'exemple existe sur `findAll()`

## Ce que les eleves doivent construire

- `GET /items/{id}`
- `POST /items`
- `PUT /items/{id}`
- `DELETE /items/{id}`
- les DTO d'entree
- les regles metier
- les tests manquants

## Consignes de travail

- ne pas mettre de logique metier dans le controller
- mettre les regles metier dans le service
- laisser le repository gerer l'acces aux donnees
- reflechir aux DTO a creer pour les entrees API
- garder un DTO de sortie propre
- ecrire des tests unitaires sur le service

## Lancer le projet

```powershell
mvn spring-boot:run
```

## Endpoints du squelette

- `GET /items` -> complet
- `GET /items/{id}`
- `POST /items`
- `PUT /items/{id}`
- `DELETE /items/{id}`

## Conseil pedagogique

Le but n'est pas de tout deviner d'un coup.
Servez-vous de `GET /items` comme modele :

- controller appelle service
- service appelle repository
- service transforme l'entite en DTO de sortie
