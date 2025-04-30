# 1° lezione IDS

Due Date: March 3, 2025
Status: Done
Argomenti: Introduzione
Categoria Desing Pattern: Desing Pattern Architetturali, Desing Pattern Comportamentali, Desing Pattern Creazionali

## Processo di sviluppo

> Il **processo di sviluppo**  spiega quali sono le attività che bisogna eseguire per creare bene un sistema software che è di grandi dimensioni e che prevede una possibile collaborazione tra varie persone che lavorano nello stesso stesso sistema software.
> 

Se una singola persona sta realizzando un sitema software non è necessario che utilizzi io processi di sviluppo in quanto questi sono usati quando si lavora in team.

Per determinare se un sistema software è di grandi dimensioni si contano le **linee di codice(Line Of Code)**

Un processo di sviluppo si basa su delle fasi(attività che si deve compiere) fondamentali che sono:

- **Analisi e organizzazione dei requisiti** è la fase in cui si capisce cosa il sistema  software dovrà realizzare. Quando accogliere i requisiti, quando modificarli. Questi requisiti vanno anche convalidati e si deve capire se sono scritti in modo corretto.
- **La progettazione(Desing)**: bisogna capire come suddividere il sistema software in componenti in modo da contenere il codice, rispettare i requisiti che si era posti. Quali sono le relazioni tra queste componenti e quali sono le responsabilità dei singoli componenti. Verrà utilizzata la **OOP.** Verranno trattati i desing pattern e il refactoring.
- **L’implementazione**
- **Il testing:** esercitare il sistema software e mandarlo in esecuzione per rilevare se vi sono problemi o di effetti nell'esecuzione in determinati contesti.

## Come ci si accorge che l’output restituito dal programma sia coretto?

La non generazione di errori a runtime e il non bloccaggio del programma non vuol dire che questo sia coretto. Devono anche essere corretti i risultati anche durante l’esecuzione. 

I risultati corretti devono essere inseriti nel **documento dei requisiti**, infatti, questo durante la fase di testing permette di capire se il software realizzato di comporta in maniera corretta oppure no.

## `Domanda Esame`

Come faccio a capire se il software si sta comportando  in maniera corretta?

Si capisce perchè si esegue il sistema software sotto certe particolari situazioni (con determinati dati che si danno in ingesso e in un determinato contesto) dall’esecuzione viene fuori un risultato che deve essere consentito cioè scritto nel documento dei requisiti. Se dovesse venire fuiri uj risultato che  non è presente in questo documento significa che questo è incompleto e quindi si completa 

## Che cos’è il Refactoring?

> Il **refactoring** è una tecnica che si utilizza per cambiare la scrittura del sistema software senza alterarne gli obiettivi di questo.
> 

## Processo di sviluppo XP

è uno tipo di processo di sviluppo

## Desing Pattern

> I **desing pattern**  sono una soluzione gia collaudata per la progettazione di sistemi di software ad oggetti per situazioni che si incontrano tante altre volte (ricorrenti).
>