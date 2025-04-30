# 8° lezione IDS

Due Date: March 26, 2025
Status: Done
Argomenti: Desing Pattern
MicroArgomenti: Adapter, Builder, Esempio Factory Method

# Esempio Factory Method

![Screenshot 2025-03-30 alle 09.54.54.png](images/Screenshot_2025-03-30_alle_09.54.54.png)

```java
// Codice Java che implementa il design pattern Factory Method

// Posto e' un Product
public interface Posto {
	public String getPosizione();
	public int getCosto();
	public String getSettore();
}
// Palco e' un ConcreteProduct
import java.util.Random;

public class Palco implements Posto {
	private final int numero;

	public Palco() {
		numero = new Random().nextInt(20) + 1;
	}

	@Override
	public int getCosto() {
		if (numero > 10) return 50;
		return 40;
	}

	@Override
	public String getPosizione() {
		return Integer.toString(numero);
	}

	@Override
	public String getSettore() {
		if (numero == 20) return "Centrale";
		if (numero > 10) return "Verde";
		return "Blu";
	}
}

// Platea e' un ConcreteProduct
import java.util.Random;

public class Platea implements Posto {
	private final String[] nomi = { "A", "B", "C", "D", "E", "F" };
	private final int numero;
	private final int riga;

	public Platea() {
		numero = new Random().nextInt(10) + 1;
		riga = new Random().nextInt(5) + 1;
	}

	@Override
	public int getCosto() {
		if (numero > 5 && rigaMax()) return 100;
		if (numero > 5 && rigaMin()) return 80;
		return 60;
	}

	@Override
	public String getPosizione() {
		return nomi[riga].concat(Integer.toString(numero));
	}

	@Override
	public String getSettore() {
		if (riga == 0) return "Riservato";
		return "Normale";
	}

	private boolean rigaMax() {
		return (riga >= 1 && riga <= 4);
	}

	private boolean rigaMin() {
		return (riga == 0 || riga == 5);
	}
}

// GnrPosizioni e' un Creator
import java.util.Set;
import java.util.TreeSet;
public abstract class GnrPosizioni { // versione 1.1
	private static final int MAXPOSTI = 100;
	private final Set< String > pst = new TreeSet< >(); // set di posti dai non duplicabili implementa BRT

  	// metodo che rimanda alla sottoclasse l'istanziazione della classe
	public Posto prendiNumero(int x) {
		if (pst.size() == MAXPOSTI) return null;
		// il chiamante dovrebbe controllare se null
		Posto p;
		do {  				  // fino a quando non trova un posto libero
			p = getPosto(x); // chiama metodo della sottoclasse
		} while (pst.contains(p.getPosizione()));
		pst.add(p.getPosizione());
		return p; 
	}

	public void printPostiOccupati() {
		for (String s : pst)
			System.out.print(s + " ");
	}

	// il metodo factory e' dichiarato ma non implementato
	public abstract Posto getPosto(int tipo);
}

// Posizioni e' un ConcreteCreator con un metodo factory
public class Posizioni extends GnrPosizioni {
	// metodo factory che ritorna una istanza
	@Override
	public Posto getPosto(int tipo) {
		// crea l'istanza di un ConcreteProduct 
		if (1 == tipo) return new Palco();
		return new Platea();
	}
}

// Biglietto e' un client del Product Posto
public class Biglietto {
	private String nome;
	private final Posto pos;
	
	public Biglietto(Posto p) {
		pos = p;
	}
	
	public void intesta(String s) {
		nome = s;
	}
	
	public String getDettagli() {
		return nome.concat(" ").concat(pos.getPosizione());
	}
	
	public String getNome() {
		return nome;
	}

	public int getCosto() {
		return pos.getCosto();
	}
}

// Classe con il main che usa il ConcreteCreator
public class MainBiglietti {
	private static Posizioni cp = new Posizioni();

	public static void main(String[] args) {
		Posto pos = cp.prendiNumero(0);
		Biglietto b = new Biglietto(pos);
		b.intesta("Mario");
		System.out.println("Costo " + b.getCosto());

		new Biglietto(cp.prendiNumero(0));
		new Biglietto(cp.prendiNumero(0));
		cp.printPostiOccupati();
	}
}
```

# Design Pattern Builder

## Costruttore Telescopico

Consideriamo una classe caratterizzata da numerosi attributi, alcuni dei quali devono essere immutabili dopo l'inizializzazione (attributi `*final*`), mentre altri possono essere modificabili. Inoltre, alcuni attributi potrebbero non essere strettamente necessari per il funzionamento della classe.

Per gestire questa variabilità nella configurazione dell'oggetto, si potrebbe adottare un approccio basato sul ***costruttore telescopico***, ovvero definendo più costruttori sovraccarichi con un numero crescente di parametri. In questo modo, sarebbe possibile fornire solo i parametri necessari, definendo costruttori con uno, due, tre parametri e così via.

Tuttavia, questa soluzione presenta uno svantaggio significativo: la complessità nell'uso della classe da parte dei client. Infatti, il chiamante deve conoscere esattamente l'ordine dei parametri richiesti da ciascun costruttore, il che può portare a errori e ridurre la leggibilità del codice.

```java
public class Biglietto {
private final String titolare, agente;
private final LocalDateTime data;
private final double prezzo;
private double acconto;
public Biglietto(String t, String a, LocalDateTime d, double p) {
	data = d;
	titolare = t;
	prezzo = p;
	agente = a;
}
public Biglietto(String t, String a, LocalDateTime d) {
	this(t, a, d, 100);
}
public Biglietto(String t, String a) {
	this(t, a, LocalDateTime.now());
}
public Biglietto(String t) {
	this(t, “BBBSelling"); // uso valori prestabiliti se non sono passati
}
// altri metodi utili (e altre combinazioni di parametri)
}
```

## Metodi Set Separati

Un'alternativa al costruttore telescopico è l'uso di metodi *set*, che permettono di assegnare i valori agli attributi in modo incrementale. In questo approccio, gli attributi non possono essere dichiarati *final*, poiché devono poter essere modificati dopo l'istanziazione dell'oggetto.

### Svantaggi

- **Impossibilità di avere attributi immutabili**: poiché gli attributi possono essere modificati successivamente, non è possibile garantire la loro immutabilità.
- **Incoerenza dello stato dell'oggetto**: assegnando i valori singolarmente, non si ha alcuna garanzia che l'oggetto sia in uno stato consistente in un determinato momento. Inoltre, il controllo sulla consistenza dei dati non può essere effettuato nel costruttore, rendendo più difficile verificare che tutti i parametri necessari siano stati impostati correttamente.
- un ulteriore svantaggio dell’approccio basato sui metodi *set* è che, se alcuni di essi non vengono chiamati, gli attributi corrispondenti potrebbero rimanere **non inizializzati** o contenere valori predefiniti non adeguati.il sistema software potrebbe funzionare in modo errato o generare errori a causa di valori mancanti.

```java
public class Biglietto {
private String titolare, agente;
private LocalDateTime data;
private double prezzo;
private double acconto;

public Biglietto() {
}
public void setPrezzo(double p) {
	prezzo = p;
}
public void setData(LocalDateTime d) {
	data = d;
}
public void setAgente(String a) {
	agente = a;
}
public void setTitolare(String t) {
	titolare = t;
}
// altri metodi utili
}
```

## Soluzione approssimativa

Richiedere l’oggetto desiderato indirettamente, attraverso il design pattern Builder

1. Il client istanzia la classe Builder, passando al costruttore i parametri richiesti, quindi al client viene restituito un oggetto di tipo Builder
2. Il client chiama i metodi set sul Builder per ciascun attributo nell’oggetto desiderato
3. Infine, il client chiama un metodo (build) che genera l’oggetto desiderato che è immutabile. Il metodo build può controllare se i parametri necessari sono presenti (verifica se lo stato è consistente prima di creare l’istanza)

## Design Pattern Builder

![image.png](images/image.png)

Si vuole separare la costruzione di un oggetto complesso dalla sua rappresentazione così che lo stesso processo costruttivo può creare diverse rappresentazioni

## Quando si utilizza il *Pattern Builder*?

Il *Pattern Builder* si utilizza quando l’algoritmo per la creazione di un oggetto complesso deve essere indipendente dalle sue componenti e dal modo in cui vengono assemblate. Inoltre, è utile quando il processo di costruzione deve consentire diverse rappresentazioni dello stesso oggetto.

## Classi principali del *Pattern Builder*

- Il *Builder* definisce un’interfaccia per la creazione delle diverse parti di un oggetto.
- Il *ConcreteBuilder* (ad esempio, *BigliettoBuilder*) implementa questa interfaccia, costruendo e assemblando le parti del *Product*. Inoltre, mantiene lo stato della costruzione e fornisce un metodo per ottenere l’oggetto finito.
- Il *Director* (ad esempio, *InitBiglietto*) coordina il processo di costruzione utilizzando l’interfaccia *Builder*, garantendo che l’oggetto venga creato seguendo una sequenza predefinita di passaggi.
- *Product* (ad esempio, *Biglietto*) rappresenta l’oggetto complesso da costruire, definendone le parti costituenti e fornendo le istruzioni per il loro assemblaggio.

![image.png](images/image%201.png)

```java
// Product
class Biglietto {
    private final String evento;
    private final String data;
    private final double prezzo;
    
    private Biglietto(BigliettoBuilder builder) {
        this.evento = builder.evento;
        this.data = builder.data;
        this.prezzo = builder.prezzo;
    }
    
    public String getEvento() { return evento; }
    public String getData() { return data; }
    public double getPrezzo() { return prezzo; }
    
    @Override
    public String toString() {
        return "Biglietto [evento=" + evento + ", data=" + data + ", prezzo=" + prezzo + "]";
    }
    
    // Builder
    public static class BigliettoBuilder {
        private String evento;
        private String data;
        private double prezzo;
        
        public BigliettoBuilder setEvento(String evento) {
            this.evento = evento;
            return this;
        }
        
        public BigliettoBuilder setData(String data) {
            this.data = data;
            return this;
        }
        
        public BigliettoBuilder setPrezzo(double prezzo) {
            this.prezzo = prezzo;
            return this;
        }
        
        public Biglietto build() {
            return new Biglietto(this);
        }
    }
}

// Director
class InitBiglietto {
    public Biglietto costruisciBigliettoConcerto() {
        return new Biglietto.BigliettoBuilder()
                .setEvento("Concerto Rock")
                .setData("15-07-2025")
                .setPrezzo(99.99)
                .build();
    }
}

// Esempio di utilizzo
public class Main {
    public static void main(String[] args) {
        InitBiglietto director = new InitBiglietto();
        Biglietto biglietto = director.costruisciBigliettoConcerto();
        System.out.println(biglietto);
    }
}
```

## Builder: **Conseguenze**:

- La rappresentazione interna del Product può cambiare. Il Builder nasconde la rappresentazione e la struttura interna del Product. Per cambiare la rappresentazione interna del Product si può definire un nuovo tipo di Builder
- Isola il codice di costruzione da quello di rappresentazione. Migliora la modularità incapsulando il modo in cui un oggetto complesso è costruito e rappresentato. I client non devono conoscere alcunché sulle classi che definiscono la struttura interna del Product. Ogni ConcreteBuilder contiene tutto il
codice per creare e assemblare un particolare tipo di Product
- Fornisce un controllo fine sul processo di costruzione, in quanto il Product è costruito passo dopo passo dal Director. Solo quando il prodotto è completo viene preso da dentro il Builder

Il **method chaining** (concatenazione dei metodi) è una tecnica di programmazione che consente di chiamare più metodi in sequenza sulla stessa istanza di un oggetto. Questo approccio viene implementato facendo in modo che ciascun metodo *setter* del *Builder* restituisca l'istanza corrente dell'oggetto (*this*), permettendo così di concatenare più chiamate in un'unica istruzione.

Affinché la concatenazione funzioni correttamente, ogni metodo deve restituire l'istanza del *Builder*, mentre l'ultima chiamata nella catena deve essere effettuata sul valore restituito dall'ultima operazione eseguita.

Ad esempio, nel *Builder pattern*, il metodo `setEvento()` restituisce `this`, consentendo la scrittura di chiamate concatenate come la seguente

```java
Biglietto biglietto = new Biglietto.BigliettoBuilder()
        .setEvento("Concerto Rock")
        .setData("15-07-2025")
        .setPrezzo(99.99)
        .build();
```

Grazie a questo *design pattern*, si ottiene una netta separazione tra il **processo di costruzione** dell’oggetto finale e la sua **struttura interna**. In questo modo, il codice che utilizza l’oggetto non ha bisogno di conoscere i dettagli della sua composizione, ma può semplicemente richiedere un’istanza già configurata.

Solo il **ConcreteBuilder** è a conoscenza di come l’oggetto è strutturato internamente e di quali passaggi siano necessari per assemblarlo correttamente. Questo approccio migliora l'**incapsulamento**, favorisce la **manutenibilità** del codice e semplifica la gestione di oggetti complessi con molteplici configurazioni possibili.

Anche il **Director** svolge un ruolo fondamentale nel nascondere i dettagli di implementazione. Esso si occupa di orchestrare il processo di costruzione senza esporre la logica interna della creazione dell'oggetto.

Grazie a questa suddivisione, il codice client può richiedere un’istanza dell’oggetto senza preoccuparsi della sequenza esatta di operazioni necessarie per la sua costruzione. Questo ulteriore livello di astrazione contribuisce a migliorare l’**incapsulamento**, ridurre le **dipendenze** e rendere il sistema più **flessibile** e **modulare**. Questo desing Pttarn si deve usare solamente quando la composizione dll’ogetto è un pò piu complessa.

<aside>
💡

***Come si fa a capire quale Desing Pattern utilizzare?***

La scelta del *design pattern* dipende dalla natura del problema che si deve risolvere. Un *design pattern* non deve essere applicato arbitrariamente, ma solo quando il problema presenta caratteristiche che possono essere efficacemente gestite da quel particolare schema progettuale. Bisogna 

</aside>

## Design pattern Adapter versione Object Adapter

L’**Adapter Pattern** appartiene alla categoria dei *Design Pattern Strutturali* e consente di convertire l’interfaccia di una classe in un’altra interfaccia attesa dai client. Grazie a questa soluzione, classi che altrimenti risulterebbero incompatibili possono interagire tra loro, eliminando il problema derivante da interfacce non corrispondenti.

Talvolta, l’utilizzo di una classe appartenente a una libreria esterna risulta impossibile poiché la sua interfaccia non coincide con quella prevista dall’applicazione. In particolare, i nomi dei metodi, i parametri e i relativi tipi potrebbero non corrispondere a quelli richiesti dal sistema.

Modificare direttamente l’interfaccia della libreria non è un’opzione percorribile, sia perché il codice sorgente potrebbe non essere disponibile, sia perché un’eventuale modifica implicherebbe la necessità di mantenere tali cambiamenti nel tempo e di eseguire nuovamente tutti i test per verificare il corretto funzionamento del sistema.

Allo stesso modo, non è possibile intervenire direttamente sull’applicazione, poiché questa potrebbe dipendere rigidamente da una determinata interfaccia. Inoltre, potrebbe rendersi necessario modificare il metodo invocato senza che tale cambiamento sia reso esplicito al codice chiamante. L’adozione dell’Adapter Pattern consente di risolvere tali problematiche, fungendo da intermediario tra l’applicazione e la libreria esterna, senza che nessuna delle due debba essere modificata.

## Design pattern Adapter: Soluzione

![image.png](images/image%202.png)

![image.png](images

/image%203.png)

- **Target** è l’interfaccia che il chiamante si aspetta
- **Adaptee** è l’oggetto di libreria
- **Adapter** converte, ovvero adatta, la chiamata che fa una classe client all’interfaccia della classe di libreria.
    
    Il chiamante usa l’Adapter come se fosse l’oggetto di libreria. Adapter tiene il riferimento all’oggetto di libreria (Adaptee) e sa come invocarlo, ovvero implementa le chiamate verso i metodi di Adaptee
    

### Esempio

![image.png](images/image%204.png)

```java
public interface ILabel { // Target
public String getNextLabel();
}

// Adapter
public class Label implements ILabel {
private LabelServer ls;
private String p;
public Label(String prefix) {
 p = prefix;
 }
public String getNextLabel() {
 if (ls == null)
 ls = new LabelServer(p);
 return ls.serveNextLabel();
 }
} 

public class Client {
public static void main(String args[]) {
 ILabel s = new Label("LAB");
 String l = s.getNextLabel();
 if (l.equals("LAB1"))
 System.out.println("Test 1:Passed");
 else
 System.out.println("Test1:Failed");
 }
}

public class LabelServer { // Adaptee
private int labelNum = 1;
private String labelPrefix;
public LabelServer(String prefix) {
 labelPrefix = prefix;
 }
public String serveNextLabel() {
 return labelPrefix + labelNum++;
 }
}

public class Label extends LabelServer implements ILabel { // Adapter

public Label(String prefix) {
 super(prefix);
 }
public String getNextLabel() {
 return serveNextLabel();
 }
}

```

## Design pattern Adapter versione class Adapter

![image.png](images/image%205.png)

![image.png](images/image%206.png)

La classe **Adapter** fornisce l’interfaccia di **Target** e quella di **Adaptee**, consentendo l’interazione tra componenti con interfacce incompatibili. La sua realizzazione può avvenire secondo due strategie principali: il *Class Adapter*, che sfrutta l’ereditarietà per estendere il comportamento dell’**Adaptee**, e l’*Object Adapter*, che utilizza la composizione per delegare le chiamate all’**Adaptee**. Nel caso del *Class Adapter*, si parla di una soluzione a due vie, in cui l’**Adapter** agisce come un ponte bidirezionale tra le due interfacce.

L’adozione del *design pattern Adapter* comporta diverse conseguenze. In primo luogo, garantisce l’indipendenza tra il **client** e la classe di libreria **Adaptee**, consentendo la modifica del comportamento di quest’ultima senza impattare direttamente sul client. Inoltre, offre la possibilità di introdurre verifiche di **precondizioni** e **postcondizioni**, stabilendo criteri da soddisfare prima dell’esecuzione di un metodo e validando il corretto esito dell’operazione.

L’*Object Adapter* può implementare la tecnica della **Lazy Initialization**, ritardando la creazione dell’oggetto **Adaptee**fino al momento in cui è effettivamente necessario, ottimizzando così l’uso delle risorse. Tuttavia, l’introduzione di un **livello di indirezione** comporta un leggero overhead, poiché ogni invocazione effettuata dal client viene instradata attraverso l’Adapter prima di raggiungere l’Adaptee. Sebbene questo impatto sulle prestazioni sia generalmente trascurabile, la maggiore complessità strutturale potrebbe rendere il codice più difficile da comprendere e manutenere.