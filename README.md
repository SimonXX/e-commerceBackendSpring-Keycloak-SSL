# E-commerce App

Applicazione di e-commerce sviluppata utilizzando il framework Spring. L'applicazione sfrutta Keycloak per la gestione dell'autenticazione e della registrazione degli utenti e utilizza un certificato SSL PKCS12 per garantire la sicurezza delle comunicazioni.

## Descrizione

L'applicazione offre una piattaforma di e-commerce completa con le seguenti funzionalità:

- Registrazione e autenticazione degli utenti utilizzando Keycloak come server di autenticazione
- Gestione dei prodotti, delle categorie e degli ordini
- Carrello della spesa per gli utenti per selezionare i prodotti da acquistare
- Pagamenti sicuri tramite integrazione con gateway di pagamento
- Interfaccia amministrativa per la gestione dei prodotti e degli ordini
- Supporto multilingua per l'interfaccia utente utilizzando il modulo AppLocalizations
- Integrazione con servizi di spedizione per il monitoraggio delle spedizioni

## Struttura dei Package e delle Classi

L'applicazione è organizzata nei seguenti package e classi principali:

- **Package `config`**: Contiene le configurazioni aggiuntive dell'applicazione.
    - `EncodingConfiguration`: Configurazione dell'encoding utilizzato nell'applicazione.
    - `SecurityConfig`: Configurazione della sicurezza e integrazione con Keycloak.

- **Package `controller`**: Contiene i controller dell'applicazione.
    - `FavoriteController`: Gestisce le operazioni relative ai prodotti preferiti degli utenti.
    - `PaymentController`: Gestisce le operazioni relative ai pagamenti.
    - `ProductsController`: Gestisce le operazioni relative ai prodotti.
    - `ReviewController`: Gestisce le operazioni relative alle recensioni dei prodotti.
    - `UsersController`: Gestisce le operazioni relative agli utenti.

- **Package `entities`**: Contiene le entità principali utilizzate nell'applicazione.
    - `Favorite`: Rappresenta un prodotto preferito da un utente.
    - `Product`: Rappresenta un prodotto disponibile per l'acquisto.
    - `ProductInPurchase`: Rappresenta un prodotto all'interno di un ordine.
    - `Purchase`: Rappresenta un ordine effettuato da un utente.
    - `Review`: Rappresenta una recensione di un prodotto.
    - `User`: Rappresenta un utente dell'applicazione.

- **Package `repository`**: Contiene i repository per l'accesso ai dati.
    - `FavoriteRepository`: Repository per l'accesso ai dati dei prodotti preferiti.
    - `ProductInPurchaseRepository`: Repository per l'accesso ai dati dei prodotti all'interno degli ordini.
    - `ProductRepository`: Repository per l'accesso ai dati dei prodotti.
    - `ReviewRepository`: Repository per l'accesso ai dati delle recensioni.
    - `UserRepository`: Repository per l'accesso ai dati degli utenti.

- **Package `services`**: Contiene i servizi per la gestione delle operazioni di business logic.
    - `FavoriteService`: Servizio per la gestione dei prodotti preferiti.
    - `PaymentService`: Servizio per la gestione dei pagamenti.
    - `ProductService`: Servizio per la gestione dei prodotti.
    - `ReviewService`: Servizio per la gestione delle recensioni.
    - `UserService`: Servizio per la gestione degli utenti.

- **Package `support.authentication`**: Contiene le classi di supporto per l'autenticazione utilizzando JWT.
    - `JwtAuthConverter`: Converte i token JWT per l'autenticazione degli utenti.

- **Package `exception`**: Contiene le eccezioni personalizzate per gestire situazioni come utente con stessa email esistente o prodotto con stesso barcode esistente.

## Requisiti di Sistema

- Java JDK 8 o versione successiva
- Spring Boot 2.x
- Keycloak come server di autenticazione

## Configurazione

Per configurare correttamente l'applicazione, è necessario:

1. Configurare i file di configurazione, ad esempio `application.yaml`, con le corrette impostazioni del database, di Keycloak e del certificato SSL PKCS12.

2. Eseguire l'applicazione utilizzando Maven o un IDE compatibile con Spring Boot.



