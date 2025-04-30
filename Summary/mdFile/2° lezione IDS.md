# 2° lezione IDS

Due Date: March 5, 2025
Status: Done
Argomenti: Caratteriste del software
Categoria Desing Pattern: Desing Pattern Comportamentali, Desing Pattern Creazionali, Desing Pattern di Astrazione

## Come avviene la compilazione(interpretazione) in Java

- **JDK (Java Development Kit)**: È l'insieme di strumenti necessari per sviluppare applicazioni Java. Include la **JRE** e vari strumenti come il compilatore Java (`javac`), che converte il codice sorgente `.java` in bytecode `.class`. Questo file `.class` può essere eseguito su qualsiasi macchina che abbia la **JVM**, indipendentemente dal sistema operativo o dal tipo di processore.
- **JRE (Java Runtime Environment)**: È l'ambiente di esecuzione che contiene la **JVM** e le librerie necessarie per eseguire i programmi Java. La **JRE** permette di eseguire il bytecode `.class` generato dal compilatore.
- **JVM (Java Virtual Machine)**: È il programma che interpreta e esegue il bytecode Java. La **JVM** si occupa di eseguire il codice in modo indipendente dal sistema operativo, fornendo un livello di astrazione che isola l'applicazione dalle specificità del sistema. Quando esegui il comando **`java`** da terminale, stai avviando la **JVM** per eseguire un programma Java.

## Parole chiavi Java

- **`class`: crea una classe**
- **`final`**: costante
- **`import:`**  trovare una libreria
- **`new`**: istanza
- **`private,public,protected`:** modalità di accesso
- **`void`**: tipo return di un programma
- **`static`**: se  all’ interno della classe è un attributo, può essere anche un metodo ed in questo caso per richiamalo bisogna usare la classe in cui è stato definito

---

## Caratteristiche del software

- **Modificabilità**
    
    Il software non è tangibile, pertanto è facilmente modificabile. Al contrario dell’ hardware. Queste modifiche possono essere dovute dal cliente che ci chiede di implementare nuove ficiur al nostro sistema software, o anche perché le leggi che regolano la società cambiano (cambio di aliquota), o anche perchè lo sviluppato si accorge di poter apportare delle migliorie al software. **Il cambiamento nel software è una buona notizia**, poiché indica che il software è facilmente modificabile e offre opportunità sia ai professionisti del settore, che ne traggono profitto, sia alle aziende, che possono basare i loro scambi commerciali su queste evoluzioni. Chi non riesce a tenere il passo con l’innovazione rischia di perdere quote di mercato, mentre chi si adatta beneficia della dinamicità del settore. Più un software ha successo, più sarà soggetto a cambiamenti. **Lo stesso principio vale per la sua durata**: se un programma non viene aggiornato per un lungo periodo, diventerà obsoleto. Questo fenomeno è noto anche come **Legge della variazione continua**
    
    Bisogna cambiare il software, anche in base all'hardware che si rinnova periodicamente(1,2,4,5 anni)
    
- **Qualità del software**:
    
    Un software di grandi dimensioni deve essere aggiornato molto frequentemente per farlo permanere nel tempo. Se è molto utilizzato verranno chieste sempre delle migliorie.
    
    <aside>
    💡
    
    **Esame (Domande Multiple)**: Programmi di grande dimensione e che vengono sviluppati su più versioni 
    
    </aside>
    
    I costi ed i tempi per la realizzazione di un sistema software devono rispettare quelli che sono stati preventivati con qualità accettabile. Se questo avviene significa che il software in questione è stato scritto bene. Ma purtroppo non è sempre così perché  in certi software pochi cambiamenti nei requisiti spingono a tantissimi cambiamenti nel sistema software ne consegue che ci saranno dei costi molto alti per la modifica di questo sistema software.
    
    Per la risoluzione di questo problema ci serviamo dell’ ingegnaria del software che ci suggerisce come costruire il software in modo da limitare i costi ed i tempo quando si va a modifica il sistema software 
    
    ---
    
    ## Come si valuta la qualità del software?
    
    Si valuta in base a delle caratteristiche:
    
    - **Correttezza**
        
        é la prima qualità che si deve valutare. Il software si deve comportare in maniera corretta rispettando i requisiti stabiliti nel documento dei requisiti.Ovviamente il documento dei requisiti deve essere scritto bene (e quindi è possibile consegnare il software al nostro cliente). 
        
        Il cliente è soddisfatto? 
        
        Si solamente se si è espresso bene e i requisiti sono stati effettuati. **Quanto prima
        mostriamo il prodotto e meglio è**. L’interazione del cliente è fondamentale per essere più conforme all’idea del cliente.
        
    - **Efficenza**
        
        Si intende per memoria/CPU usata. 
        
        Si poteva realizzare il sistema software in questione applicando qualche algoritmo o struttura dati che  faccia risparmiare memoria e clicli di CPU?
        
        Se ci si accorge che vi sono degli sprechi sia di memoria che di CPU bisogna risolverli, ma non è facile accorgersene, i programmatori più esperti vanno a guardare sia il codice che il consumo di memoria e di CPU. Altre volte invece possono essere facilmente individuabile. 
        
        L’obietto è evitare lo spreco di risorse
        
    - **Manutenibilità**
        
        come detto prima, se facendo il lavoro impiego poche risorse è mantenibile, sennò
        no.
        
    - **Dependability**
        
        Si compone di due parti:
        
        1. **Security:**  proteggere i dati da persone non autorizzate, alcuni dati sono riservati, non tutti gli utenti devono vedere i dati presenti ma devono essere distinti per i vari ruoli.
        2. **Safety:** protezione del sistema stesso e della vite umana. 
        3. **affidabilità (reliability):** ovvero l’ esecuzione è priva di guasti oppure incontra malfunzionamenti? Di solito si sistema con un warning a schermo per avvisare il
        cliente e poi sistemato in un secondo momento (se poco grave)
    - **Usabilità**
        
        Se l’utente riesce a interagirci in maniera ottimale (ovvero non ci sono troppi problemi
        con l’interazione del software stesso). 
        
        Quindi bisogna interrogarsi sull’interfaccia stessa in base ai feedback degli utenti, anche perché non tutti i clienti sono programmatori e quindi ci sono modidiversi di vedere l’applicazione.
        
    
    Se tutte queste proprietà sono di **qualità alta** allora il software è di **qualità** se, invece, alcune di queste proprietà hanno una **qualità più bassa** allora il software  **scende di qualità**.
    
    <aside>
    💡
    
    **Ricorda**: Se il software non è corretto, le altre opzioni sono secondarie
    
    </aside>
    

## Che cos’è un interfaccia in Java?

Le interfacce non contengono l’implementazione di nessun metodo ma hanno attributi. I metodi definiti in queste interfacce sono tutti pubblici anche perché renderli privati non avrebbe senso in quanto non  sarebbero visibili alle altre classi ma sono alla classe stessa in cui  sono stati definiti. 

Vi è una relazione tra l’interfaccia contenente tutti i metodi pubblici privi di implementazione e la classe che implementa l’interfaccia.

Un esempio è costituito dall’ interfaccia `List` e una sua classe `ArrayList` che imprenta un array  e quindi alloca in memoria lo spazio continuo. Si ha anche la classe `LisnkedList`  che implementa una lista doppiamente concatenata.

## Che cos’è l’interfaccia `Collection`?

L'interfaccia **Collection** e le sue sotto-interfacce rappresentano un insieme di classi e interfacce che consentono di gestire e organizzare gruppi di oggetti in modo efficiente. Questo sistema riduce la necessità di implementazioni personalizzate, offrendo strutture dati predefinite e ottimizzate, note anche come **collezioni di dati**.

Le collezioni sono **parametrizzate** tramite **generics**, permettendo di specificare il tipo di dati che possono contenere, come ad esempio `String`, `Integer`, `Double`, ecc.