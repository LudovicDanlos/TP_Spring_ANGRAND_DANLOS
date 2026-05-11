# TD - Conception et completion d'une API Spring Boot propre

## Objectif du TD

Le but de ce TD est de comprendre comment construire une API Spring Boot avec une architecture propre.

Vous partez d'un squelette deja prepare.
Votre travail consiste a :

- comprendre comment le projet est structure ;
- completer les parties manquantes ;
- garder une separation claire entre `controller`, `service` et `repository` ;
- preparer une base solide pour le TP.

## Ce qui est deja fourni

Le projet contient deja :

- une configuration PostgreSQL dans `application.properties` ;
- une entite generique `Item` ;
- un `ItemRepository` ;
- une interface `ItemService` ;
- une implementation `ItemServiceImpl` ;
- un `ItemController` ;
- un endpoint complet : `GET /items` ;
- un test unitaire d'exemple sur `findAll()`.

## Ce que vous devez comprendre

### 1. Le controller

Le controller :

- recoit la requete HTTP ;
- lit les parametres et le body ;
- appelle le service ;
- renvoie une reponse HTTP.

Le controller ne doit pas contenir la logique metier.

### 2. Le service

Le service :

- contient la logique metier ;
- applique les regles ;
- appelle le repository ;
- transforme les donnees si besoin.

### 3. Le repository

Le repository :

- parle a la base de donnees ;
- lit, sauvegarde, modifie ou supprime les donnees ;
- ne doit pas contenir la logique metier.

## Travail demande pendant le TD

### Etape 1 - Lire le projet

Vous devez d'abord lire :

- `ItemController`
- `ItemService`
- `ItemServiceImpl`
- `ItemRepository`
- `Item`
- `ItemResponse`

Objectif :
comprendre le trajet d'une requete dans le projet.

### Etape 2 - Expliquer l'exemple deja complet

Vous devez etre capables d'expliquer comment fonctionne `GET /items` :

1. la requete arrive dans le controller ;
2. le controller appelle le service ;
3. le service appelle le repository ;
4. le service transforme les entites en DTO ;
5. le controller renvoie la reponse HTTP.

### Etape 3 - Completer les parties manquantes

Vous devez ensuite completer :

- `GET /items/{id}`
- `POST /items`
- `PUT /items/{id}`
- `DELETE /items/{id}`

## Contraintes d'architecture

Vous devez respecter les regles suivantes :

- pas de logique metier dans le controller ;
- pas d'acces direct au repository depuis le controller ;
- le controller doit appeler le service ;
- le service doit appeler le repository ;
- le service doit passer par son interface ;
- la structure du projet doit rester lisible.

## DTO

Un seul DTO de sortie est deja fourni : `ItemResponse`.

A vous de reflechir :

- faut-il un DTO pour la creation ?
- faut-il un DTO pour la modification ?
- faut-il reutiliser le meme DTO ou en creer plusieurs ?

Le squelette utilise volontairement `Object request` dans certaines methodes pour vous obliger a vous poser cette question.

## Tests

Un test unitaire simple existe deja pour `findAll()`.

Vous devrez ensuite reflechir a des tests pour :

- `findById`
- `create`
- `update`
- `delete`

## Base de donnees

Le projet est configure pour se connecter a PostgreSQL avec :

- URL : `jdbc:postgresql://localhost:5432/coursBasile`
- utilisateur : `user`
- mot de passe : `Pass2026!`

Assurez-vous que le conteneur Docker PostgreSQL est lance avant de demarrer le projet.

## Lancer la base de donnees avec Docker

### 1. Installer Docker Desktop

Si Docker n'est pas encore installe :

- telecharger Docker Desktop ;
- l'installer ;
- redemarrer la machine si necessaire ;
- lancer Docker Desktop.

Tant que Docker Desktop n'est pas ouvert et actif, la base PostgreSQL ne pourra pas demarrer.

### 2. Aller dans le dossier fourni pour PostgreSQL

Le dossier prepare pour vous se trouve ici :

`postgres-eleves`

Il contient deja :

- un `docker-compose.yml`
- un `.env`

Vous n'avez normalement rien a modifier.

### 3. Lancer la base

Depuis un terminal, placez-vous dans le dossier `postgres-eleves`, puis lancez :

```powershell
docker compose up -d
```

### 4. Verifier que la base tourne

Vous pouvez verifier avec :

```powershell
docker compose ps
```

Si tout va bien, le conteneur PostgreSQL doit apparaitre comme demarre.

### 5. Arreter la base

Si vous voulez arreter PostgreSQL :

```powershell
docker compose down
```

### 6. Important pour le projet Java

Si tout se passe bien :

- la base PostgreSQL tourne en local ;
- le projet Java est deja configure pour s'y connecter ;
- vous n'avez pas a modifier la configuration de connexion.

Autrement dit :

- vous lancez Docker ;
- vous lancez PostgreSQL ;
- puis vous pouvez lancer le projet Spring Boot.

Le lien entre Java et la base est deja pret dans `application.properties`.

## Ce que vous pouvez personnaliser

Le projet utilise un objet generique `Item`.

Vous pouvez :

- garder `Item`
- ou le renommer selon votre sujet plus tard

Exemples :

- `Book`
- `Movie`
- `Expense`
- `Recipe`
- `Habit`

Mais pour le TD, vous pouvez tres bien rester sur `Item`.

## Questions a vous poser pendant le TD

- Quelle est la responsabilite exacte du controller ?
- Quelle est la responsabilite exacte du service ?
- A quel endroit faut-il mettre les regles metier ?
- Pourquoi ne faut-il pas mettre toute la logique dans le controller ?
- Pourquoi est-ce utile d'avoir une interface de service ?
- Pourquoi un DTO peut-il etre utile ?

## Livrable attendu a la fin du TD

A la fin du TD, vous devez avoir :

- compris l'architecture du projet ;
- explique l'exemple `GET /items` ;
- identifie ce qu'il manque pour finir le CRUD ;
- propose les DTO necessaires ;
- commence ou complete les methodes manquantes selon les consignes du professeur.

## Bonus - Initialiser un frontend

Dans un second temps, vous pouvez aussi preparer un frontend relie a votre backend.

L'idee n'est pas de faire un frontend complexe tout de suite, mais :

- de reflechir a un sujet que vous aimeriez produire dans un systeme complet ;
- d'initialiser un projet front ;
- de choisir une technologie ;
- de brancher ce front sur les endpoints du backend ;
- de preparer une vraie avance pour le TP qui viendra ensuite.

### Avant de commencer le front

Avant meme d'initialiser le frontend, prenez quelques minutes pour reflechir a un sujet que vous aimeriez produire dans un systeme avec :

- un frontend ;
- un backend ;
- une API ;
- une base de donnees.

Exemples possibles :

- gestion de livres ;
- gestion de films ;
- gestion de recettes ;
- gestion de depenses ;
- gestion de contacts ;
- gestion d'habitudes.

L'idee est de commencer a projeter votre sujet de TP tout en gardant le socle technique du TD.

### Choix de la techno

Vous pouvez choisir la technologie de votre choix :

- HTML / CSS / JavaScript simple
- Angular
- React
- Vue
- autre techno si elle est validee par le professeur

### Pourquoi Angular est une bonne piste

Angular est une proposition interessante car :

- il est tres utilise dans de nombreuses entreprises ;
- il reste particulierement present dans certaines entreprises du nord de la France ;
- c'est donc une competence interessante a developper pour votre employabilite ;
- se former dessus peut avoir un vrai interet professionnel, meme si ce n'est pas la seule option possible.

### Ce que vous pouvez faire en bonus

En bonus, vous pouvez :

- choisir votre sujet applicatif ;
- initialiser un projet frontend ;
- preparer une page liste ;
- preparer un formulaire d'ajout ;
- faire un premier appel HTTP vers votre backend ;
- brancher le frontend sur un ou plusieurs endpoints du back ;
- afficher les donnees renvoyees par `GET /items`.

### Pourquoi ce bonus est utile

Ce bonus vous fera gagner du temps pour le TP :

- vous aurez deja une idee de sujet ;
- vous aurez deja commence le frontend ;
- vous aurez deja compris comment relier le front a l'API ;
- vous arriverez au TP avec une base de travail plus concrete.

### Important

Le bonus frontend ne remplace pas le travail backend.

La priorite reste :

- comprendre l'architecture Spring Boot ;
- completer proprement le backend ;
- garder une separation claire des responsabilites.

## Rappel important

Le but n'est pas seulement de "faire marcher" le projet.

Le but est de produire un backend :

- lisible ;
- bien decoupe ;
- testable ;
- propre.

## Branch sur Git :
Pour le TD, avant la pause faites dans le terminal : 
git checkout -b "VOTRE PRENOM" 
git add .
git commit -m "Feat : add travail"
git push --set-upstream origin "VOTRE PRENOM"