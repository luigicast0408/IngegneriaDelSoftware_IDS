# Builder Pattern – Note di Implementazione

## Cose a cui fare attenzione

### Coerenza tra `Exam` e `ExamBuilder`
La classe `Exam` e il suo `Builder` devono **conciliare bene tra loro**, ovvero:
- Ogni campo definito in `Exam` deve essere **inizializzato nel costruttore** usando i valori provenienti dal `Builder`.
- La classe `ExamBuilder`deve essere dentro la classe `Exam` perche è una classe **STATICA**

### 🧩 Setter ben formati
Assicurati che **tutti i metodi `setX()`** nel `ExamBuilder`:
- Abbiano il **parametro corretto** (es. `String`, `int`, `Date`, etc.).
- Impostino correttamente il campo: `this.x = x;`.
- **Restituiscano `this`**, per supportare la **concatenazione fluente**:
```java
Exam exam = new Exam.ExamBuilder()
    .setCourseName("Reti")
    .setProfessor("Prof")
    .setCFU(9)
    .setDate(new Date())
    .setVote(30)
    .setLaude(true)
    .build();
