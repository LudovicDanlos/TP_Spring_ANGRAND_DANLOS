# Rapport TP API Spring
**ANGRAND Louis, DANLOS Ludovic M1 TNSID**

## Sujet choisi
Nous sommes restés sur le sujet de base, c'est-à-dire une API qui permet de gérer des tâches.  

## Lancer le projet
1. Lancer la base de données
2. Lancer l'API, soit via un IDE en exécutant la classe TPApplication, soit avec la commande ```mvn spring-boot:run``` (ne marche pas chez nous)
3. Lancer le FrontEnd avec les commandes : ```npm install``` puis ```npm run dev```

## Architecture BackEnd
Concernant l'architecture du BackEnd de notre API, nous avons utilisé une architecture en layers (ou couches). Voici les
différents éléments qui constituent notre API :  

Une Entité (Task). C'est l'objet qu'on stocke dans la base de données et dont notre API se charge de rendre disponible
une partie de ses données. Notre Entité dispose notamment d'un ID technique qui est incrémenté automatiquement et qu'on
évite d'exposer.  

Un DTO de sortie (TaskResponse). Ce DTO de sortie est une représentation réduite de notre entité stockée en base de
données (Task). Cet objet nous permet de définir qu'elles données nous proposons en sortie de nos routes : il structure
les données qui sont renvoyées par nos routes lors d'un appel à notre API.  

Un DTO d'entré (TaskInput). Ce DTO d'entré est une représentation réduite de notre entité stockée en base de données
(Task). Ce DTO nous permet de récupérer un objet similaire à notre entité au travers de certaines routes sans qu'on ait
besoin d'exposer complètement notre entité et donc une partie du fonctionnement interne. Ici, on utilise surtout le DTO
d'entré pour éviter d'exposer l'ID technique des entités qui sont stockées en base de données.  

Un Controller (TaskController). Le Controller est le point d'entré de notre API. Il nous permet de définir les routes
qui sont disponibles, ce qu'elles attendent comme données, et ce qu'elles renvoient. Notre Controller propose 5 routes :
une route qui permet de récupérer toutes les tâches, une route qui permet de récupérer une tâche à l'aide de son ID, une
route qui permet d'ajouter une tâche, une route qui permet de modifier une tâche, et une route qui permet de supprimer
une tâche.  

Un Service (TaskService implémenté par TaskServiceImpl). Le Service est l'élément qui transforme les données et
applique les règles métier. Dans notre Service, nous avons au moins une méthode pour chaque route qui récupère les
données transmises par le Controller, les transforme si besoin, transmet les données transformées au Repository, puis
transmet une réponse au Controller. De plus, notre Service définit les règles les métiers et les applique dans chaque
méthode.  

Un Repository (TaskRepository). C'est l'élément qui intéragit avec notre base de données. Il se contente d'envoyer des
des données qu'il reçoit du Service vers la base de données et de récupérer des données depuis la base de données pour
les transmettre au Service. De plus, on s'en sert pour définir des méthodes qui défnissent des requêtes personnalisées.  

## Fonctionnalités et règles métier
Dans cette partie, nous décrivons les différentes fonctionnalités ainsi que les différentes règles métiers que nous
avons mis en place dans notre API.  

Pour ce qui est des règles métiers voici celles que nous avons mise en place :
- Le nom d'une tâche est obligatoire.
- La priorité d'une tâche est obligatoire et doit avoir une des trois valeurs suivantes : "LOW", "MEDIUM" ou "HIGH".
- Le statut d'une tâche est obligatoire et doit avoir une des trois valeurs suivantes : "TODO", "IN_PROGRESS" ou "DONE".
- La date de fin (attribut deadline) d'une tâche est obligatoire et doit être future à la date d'enregistrement de la tâche.  

Toutes ces règles métiers sont vérifiées par le Service. De plus, lorsqu'une de ces règles n'est pas validée, une
Exception personnalisée est levée et un message d'erreur personnalisé est renvoyé au travers de la route.  

Maintenant, pour ce qui est des fonctionnalités, nous en proposons 4 :
- Pagination lorsqu'on récupère toutes les tâches.
- La possibilité de filtrer les tâches par statut lorsqu'on récupère toutes les tâches.
- La possibilité de filtrer les tâches par priorité lorsqu'on récupère toutes les tâches.
- La possibilité de filtrer les tâches par priorité et statut lorsqu'on récupère toutes les tâches.  

Ces fonctionnalités n'ont pas été trop difficiles à mettre en place, notamment grâce à Spring et JpaRepository qui
intègrent de nombreuses fonctionnalités facilitant les choses. Pour la pagination, la méthode findAll() de JpaRepository
supporte déjà la pagination et il a suffit d'ajouter des paramètres à la route via le Controller puis de donner un objet
Pageable en paramètre de la méthode findAll() lorsqu'on l'appelle dans le Service. Concernant les fonctinnalités de
filtrage, JpaRepository nous permet de facilement créer des méthodes dérivées des méthodes déjà existantes, ce qui
simplifie la partie la plus compliquée de la tâche.  

## Les Tests
Courte partie qui aborde les tests réalisés.
Globalement, nous n'avons pas réalisé beaucoup de tests. Nous nous sommes contenté de quelques tests qui vérifient
généralement que les méthodes du service retourne les valeurs attendues lorsque en fonction de ce que le Repository (simulé)
lui remonte. Par ailleurs, la pagination a été plus difficile à tester qu'à mettre en place, notamment parce qu'on doit
utiliser une implémentation de Pageable et que cette information n'a pas été simple a trouver.