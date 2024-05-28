# Svømmehal Delfinen Projekt

## Oversigt
 Swimapp er en Java-applikation, der tilbyder funktioner relateret til svømmehal's administration. Dette projekt bruger JavaFX til det grafiske brugergrænseflade og SQLite til databasehåndtering.
 Projektet er en løsning til et skole projekt.

## Projektstruktur
- **Hovedklasse:** `src/swimapp/frontend/Main.java`
- **Biblioteker:** JavaFX og SQLite bibliotekerne er placeret i `/lib` mappen.

## Forudsætninger
- **Java Development Kit (JDK):** Sørg for, at du har JDK 17 eller senere installeret.
- **IntelliJ IDEA:** Denne vejledning antager, at du bruger IntelliJ IDEA som din IDE.

## Opsætningsinstruktioner

### 1. Klon Repositoriet
Klon repositoriet til din lokale maskine ved hjælp af følgende kommando:
```bash
git clone https://github.com/KvasirSG/swimhall-project.git
```

### 2. Åbn Projektet i IntelliJ IDEA
1. Åbn IntelliJ IDEA.
2. Vælg `File > Open` og naviger til den klonede repositorie-mappe.
3. Vælg projektets rodmappe og klik på `OK`.

### 3. Konfigurer Projekt SDK
1. Gå til `File > Project Structure`.
2. I `Project` sektionen, sørg for, at Project SDK er sat til JDK 11 eller senere.
3. Hvis JDK ikke er listet, klik på `New > JDK`, og naviger til installationsmappen for din JDK.

### 4. Tilføj JavaFX Bibliotek
1. Download JavaFX SDK fra [den officielle hjemmeside](https://gluonhq.com/products/javafx/).
2. Udpak den downloadede JavaFX SDK til en passende placering på din maskine.
3. Gå til `File > Project Structure > Libraries`.
4. Klik på `+` ikonet, vælg `Java`, og naviger til `lib` mappen inde i din udpakkede JavaFX SDK.
5. Vælg alle `.jar` filer i `lib` mappen og klik på `OK`.

### 5. Tilføj SQLite Bibliotek
1. Sørg for, at SQLite biblioteket (f.eks. `sqlite-jdbc-<version>.jar`) er placeret i `/lib` mappen af dit projekt.
2. Gå til `File > Project Structure > Libraries`.
3. Klik på `+` ikonet, vælg `Java`, og naviger til `/lib` mappen af dit projekt.
4. Vælg SQLite `.jar` filen og klik på `OK`.

### 6. Kør Projektet
1. Find `src/swimapp/frontend/Main.java` i Projekt-værktøjsvinduet.
2. Højreklik på `Main.java` og vælg `Run 'Main.main()'`.
