# Architektur

## 1. Schichten und Abhängigkeitsrichtung

Das Fotoalbum ist in vier Schichten aufgeteilt. Abhängigkeiten zeigen nur nach innen zur Domäne.

```mermaid
flowchart TB
    subgraph UI["UI"]
        ui["Swing-Fenster"]
    end
    subgraph INFRA["Infrastruktur"]
        infra["FileAlbumRepository<br/>FilePhotoStorage<br/>Sha256ImageHasher"]
    end
    subgraph APP["Anwendung"]
        uc["AddPhotoUseCase<br/>CreateAlbumUseCase"]
        ports["Ports (Interfaces)<br/>AlbumRepository<br/>PhotoStorage<br/>ImageHasher"]
    end
    subgraph DOM["Domäne"]
        dom["Album, Photo<br/>alle Geschäftsregeln"]
    end
    UI --> APP
    INFRA -- implementiert Ports --> APP
    APP --> DOM
    INFRA --> DOM
```

Ein Pfeil bedeutet „hängt ab von“. Die Infrastruktur implementiert die Ports der Anwendung und kennt `Album` und `Photo`, um sie zu speichern. Die Domäne hängt von keiner anderen Schicht ab. Die Klasse `Main` erzeugt die Infrastrukturklassen und übergibt sie den Use Cases. Nur sie kennt alle Schichten.

| Schicht | Aufgabe | Klassen (vorläufig) |
|---|---|---|
| UI | Zeigt Alben und Fotos an, nimmt Eingaben entgegen und zeigt Meldungen. Enthält keine Geschäftsregeln und greift nicht auf Dateien zu. | Swing-Fenster |
| Anwendung | Ein Use Case je Nutzerziel. Lädt das Album über einen Port, lässt die Domäne entscheiden und speichert das Ergebnis. Legt die Ports als Interfaces fest. | `AddPhotoUseCase`, `CreateAlbumUseCase`; Ports `AlbumRepository`, `PhotoStorage`, `ImageHasher` |
| Domäne | Enthält alle Geschäftsregeln aus [REQUIREMENTS.md](REQUIREMENTS.md): Albumname, höchstens 100 Fotos, kein Duplikat, Textlängen. | `Album`, `Photo` |
| Infrastruktur | Setzt die Ports technisch um: JSON-Dateien, App-Kopien der Bilder, Inhaltshash. | `FileAlbumRepository`, `FilePhotoStorage`, `Sha256ImageHasher` |

Die Klassenliste ist ein erster Stand. Sie wird mit den CRC-Karten und dem Klassendiagramm abgeglichen. Die Ports beschreibt Abschnitt 2.

**Begründung:**

- **Testbarkeit:** Die Geschäftsregeln liegen in der Domäne und hängen von keiner anderen Schicht ab. Unit-Tests prüfen sie ohne Oberfläche und ohne Dateizugriffe, wie NFA-05 es verlangt.
- **Austauschbarkeit:** Use Cases kennen nur die Ports. Speicherung und Oberfläche lassen sich ändern, ohne Use Cases oder Domäne anzupassen. Im Test ersetzt eine Variante im Arbeitsspeicher die Dateispeicherung.
- **Klare Zuständigkeit:** Jede Geschäftsregel steht an genau einer Stelle. Die UI zeigt nur an, was die Anwendung zurückmeldet.
