# TP - Application full-stack avec Spring Boot et Vue

## Objectif du TP

Le but de ce TP est de transformer le socle du TD en une petite application full-stack complète.

Vous partez de l'API Spring Boot travaillée pendant le TD et vous devez construire une application plus ambitieuse, avec :

- un backend Spring Boot propre ;
- une API REST ;
- une base de données PostgreSQL ;
- un frontend Vue ;
- une architecture lisible et maintenable ;
- un rapport expliquant vos choix d'architecture et votre démarche de clean code.

Le sujet proposé est une application de gestion de tâches.

L'objectif n'est pas seulement de produire une application qui fonctionne.
L'objectif est de produire une application propre, bien structurée, compréhensible et évolutive.

## Contexte

Pendant le TD, vous avez travaillé sur une entité générique `Item` et sur une architecture en couches :

- `controller`
- `service`
- `repository`
- `entity`
- `dto`

Et optionellement :

- `transformer`
- `exception`

Dans ce TP, vous devez continuer sur cette base.

Vous pouvez :

- soit garder le nom `Item` ;
- soit renommer proprement le domaine vers `Task`.

Le renommage n'est pas obligatoire, mais il est recommandé si vous voulez rendre votre application plus lisible.

## Sujet fonctionnel

Vous devez réaliser une application de gestion de tâches.

Le sujet des tâches est le sujet recommandé, car il prolonge naturellement le TD et correspond bien au socle `Item` déjà fourni.

Si ce sujet ne vous parle pas, vous pouvez choisir un autre domaine applicatif qui vous intéresse davantage.

Exemples :

- gestion de livres ;
- gestion de films ;
- gestion de recettes ;
- gestion de dépenses ;
- gestion de contacts ;
- suivi d'habitudes ;
- gestion d'événements.

Choisir un sujet libre peut aussi être à double tranchant : un sujet plus original peut vite demander plus de modélisation, plus de règles métier et plus de temps.

Si vous choisissez un autre sujet, vous devez donc rester raisonnables.
Le domaine choisi doit permettre de montrer proprement l'architecture vue en cours, sans transformer le TP en projet trop large.

Dans le cas de la todo list, une tâche doit au minimum contenir :

- un identifiant technique ;
- un titre ;
- une description ;
- un statut ;
- une priorité ;
- une date de création ;
- éventuellement une date limite.

Exemples de statuts possibles :

- `TODO`
- `IN_PROGRESS`
- `DONE`

Exemples de priorités possibles :

- `LOW`
- `MEDIUM`
- `HIGH`

## Partie 1 - Backend Spring Boot

Vous devez construire ou adapter l'API REST permettant de gérer les tâches.

### Endpoints attendus

Votre API doit proposer au minimum :

- `GET /tasks` : récupérer toutes les tâches avec pagination ;
- `GET /tasks/{id}` : récupérer une tâche par son id ;
- `POST /tasks` : créer une tâche ;
- `PUT /tasks/{id}` : modifier complètement une tâche ;
- `DELETE /tasks/{id}` : supprimer une tâche.

Vous pouvez garder `/items` si vous ne renommez pas l'entité, mais les routes doivent rester cohérentes avec votre domaine.

Les endpoints qui renvoient une liste doivent utiliser une pagination quand cela est pertinent.

Exemple :

```http
GET /tasks?page=0&size=10
```

La pagination doit être gérée proprement côté backend, par exemple avec `Pageable` et `Page` de Spring Data.

### Fonctionnalités supplémentaires attendues

Votre backend doit proposer au moins deux fonctionnalités parmi les suivantes :

- filtrer les tâches par statut ;
- filtrer les tâches par priorité ;
- rechercher une tâche par mot-clé dans le titre ou la description ;
- paginer les résultats des endpoints de recherche et de filtrage ;
- trier les tâches par date de création ;
- trier les tâches par priorité ;
- ajouter un endpoint `PATCH` pour changer uniquement le statut d'une tâche ;
- ajouter un endpoint permettant de marquer une tâche comme terminée.

Exemples :

```http
GET /tasks?status=TODO
GET /tasks?priority=HIGH
GET /tasks?search=rapport
GET /tasks?status=TODO&page=0&size=10
PATCH /tasks/3/status
```

### Règles métier

Votre service doit contenir des règles métier simples mais explicites.

Exemples :

- le titre est obligatoire ;
- le titre ne doit pas dépasser une certaine longueur ;
- la description ne doit pas dépasser une certaine longueur ;
- la priorité est obligatoire ;
- le statut est obligatoire ;
- une tâche supprimée ou inexistante doit produire une erreur claire ;
- une modification doit vérifier que la tâche existe avant d'écrire en base.

Les règles métier ne doivent pas être placées dans le controller.

## Partie 2 - Architecture attendue

Vous devez respecter l'architecture vue en cours.

### Controller

Le controller doit :

- recevoir les requêtes HTTP ;
- lire les paramètres de route, les query params et le body ;
- appeler le service ;
- renvoyer une réponse HTTP adaptée.

Le controller ne doit pas contenir de logique métier.

### Service

Le service doit :

- contenir la logique métier ;
- vérifier les règles ;
- appeler le repository ;
- transformer les entités si nécessaire ;
- être testable.

Le service doit passer par une interface.

### Repository

Le repository doit :

- gérer l'accès aux données ;
- utiliser Spring Data JPA ;
- contenir uniquement les requêtes nécessaires.

Le repository ne doit pas contenir de logique métier.

### DTO

Vous devez utiliser des DTO pour les entrées et les sorties de l'API.

Exemples :

- `TaskRequest`
- `TaskResponse`
- `TaskStatusRequest`

L'entité JPA ne doit pas être exposée directement par les controllers.

## Partie 3 - Frontend Vue

Vous devez créer un frontend avec Vue.

Cette partie est exigée dans le TP, mais ce n'est pas la partie principale de l'évaluation.

Elle sert surtout à comprendre comment un frontend communique avec un backend :

- appel HTTP vers l'API ;
- affichage des données reçues ;
- envoi d'un formulaire vers le backend ;
- gestion simple des erreurs ;
- compréhension des problèmes classiques comme les routes, les ports et le CORS.

Il n'est donc pas nécessaire de passer trop de temps sur le design ou sur une interface très avancée.
Un frontend simple, lisible et correctement branché au backend suffit.

Le frontend peut être placé :

- soit dans un dossier séparé du projet backend, par exemple :

```text
frontend/
```

- soit dans un projet séparé, si vous préférez séparer complètement le backend et le frontend.

Vous pouvez initialiser le projet avec Vite.

### Fonctionnalités frontend attendues

Le frontend doit permettre au minimum :

- d'afficher la liste des tâches ;
- d'afficher une liste paginée quand l'API renvoie plusieurs pages ;
- de créer une nouvelle tâche ;
- de modifier une tâche existante ;
- de supprimer une tâche ;
- de changer le statut d'une tâche ;
- d'afficher une erreur si l'appel API échoue.

### Fonctionnalités frontend bonus

Vous pouvez ajouter :

- un filtre par statut ;
- un filtre par priorité ;
- une barre de recherche ;
- des boutons de pagination ;
- un tri ;
- un affichage des tâches en colonnes par statut ;
- un compteur de tâches terminées et restantes ;
- des états de chargement.

### Organisation conseillée

Exemple d'organisation possible :

```text
frontend/
  src/
    components/
      TaskList.vue
      TaskForm.vue
      TaskFilters.vue
    services/
      taskApi.js
    App.vue
```

Le code d'appel HTTP doit être isolé dans un fichier dédié, par exemple `taskApi.js`.

Les composants Vue doivent rester simples et lisibles.

## Partie 4 - Tests

Vous devez ajouter des tests unitaires sur la couche service.

Tests attendus au minimum :

- récupérer toutes les tâches ;
- récupérer une tâche par id ;
- créer une tâche valide ;
- refuser une tâche invalide ;
- modifier une tâche existante ;
- supprimer une tâche existante ;
- gérer le cas d'une tâche introuvable.

Les tests doivent vérifier la logique du service, pas seulement l'existence des méthodes.

## Partie 5 - Rapport

Vous devez fournir un rapport sous la forme d'un fichier `README.md` ou `RAPPORT.md`.

Ce rapport doit être clair et utile pour comprendre votre projet.

Il doit contenir :

- le sujet choisi ;
- les fonctionnalités réalisées ;
- les endpoints disponibles ;
- les choix d'architecture ;
- les DTO créés ;
- les principales règles métier ;
- ce que vous avez mis en place pour produire du clean code ;
- l'explication du rôle des principales couches de votre application ;
- l'explication de l'intérêt des choix faits ;
- les tests réalisés ;
- les difficultés rencontrées ;
- les commandes pour lancer le backend et le frontend.

Vous devez notamment expliquer à quoi servent les éléments que vous avez mis en place.

Exemples de questions auxquelles votre rapport doit répondre :

- pourquoi ne met-on pas de logique métier dans le controller ?
- à quoi sert la couche service ?
- pourquoi passer par une interface de service ?
- à quoi sert le repository ?
- pourquoi utiliser des DTO plutôt que d'exposer directement les entités JPA ?
- comment les erreurs sont-elles gérées ?
- comment la pagination est-elle construite ?
- comment le frontend communique-t-il avec le backend ?
- quelles pratiques de clean code avez-vous appliquées ?

Le rapport n'a pas besoin d'être très long, mais il doit montrer que vous comprenez ce que vous avez codé.
Il ne doit pas seulement lister les fichiers du projet.

## Lancement attendu

Votre rendu doit permettre de lancer le backend et le frontend.

Exemple :

```powershell
mvn spring-boot:run
```

Puis, dans le dossier frontend :

```powershell
npm install
npm run dev
```

La base PostgreSQL doit être lancée avant le backend.

## Livrables attendus

Vous devez rendre :

- le code backend Spring Boot ;
- le code frontend Vue ;
- les tests ;
- le rapport ;
- les fichiers nécessaires pour lancer le projet.

Le livrable est attendu sur Git.

Vous devez envoyer un lien accessible vers votre repository Git à l'adresse suivante :

```text
Basile.Thiry@uphf.fr
```

Le lien doit permettre de consulter le code sans demande d'accès supplémentaire au moment de la correction.

## Critères de notation

La notation portera principalement sur la propreté du code et le respect de l'architecture vue en cours.

### Points majeurs

- séparation claire entre controller, service et repository ;
- absence de logique métier dans les controllers ;
- service clair, testable et cohérent ;
- utilisation correcte des DTO ;
- pagination propre sur les endpoints qui renvoient des listes ;
- gestion propre des erreurs ;
- code lisible, nommage clair, responsabilités bien séparées ;
- frontend Vue simple mais connecté correctement à l'API ;
- tests pertinents sur la couche service ;
- rapport clair, argumenté et utile pour comprendre les choix techniques.

### Points secondaires

- qualité et complexité du frontend ;
- qualité visuelle du frontend ;
- nombre de fonctionnalités bonus ;
- richesse de l'interface ;
- niveau de personnalisation du sujet.

Une application simple mais propre, bien structurée et bien expliquée sera mieux notée qu'une application plus ambitieuse mais confuse, fragile ou mal organisée.

## Rappel important

Le TP doit montrer que vous avez compris l'architecture logicielle travaillée en cours.

Vous devez donc être capables d'expliquer :

- pourquoi le controller ne contient pas la logique métier ;
- pourquoi le service est la couche centrale du métier ;
- pourquoi le repository ne fait que l'accès aux données ;
- pourquoi les DTO sont utiles ;
- comment le frontend communique avec le backend.

Le clean code et l'architecture sont au cœur de l'évaluation.
