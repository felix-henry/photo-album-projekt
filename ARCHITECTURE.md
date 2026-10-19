# ARCHITECTURE – Photo Album (v1)

Stand 19.10.2026 · Block 3 (PB §8; Architecture Studio B3 F47) · Quellenkürzel wie in [REQUIREMENTS.md](REQUIREMENTS.md): **PB** = Projektbeschreibung v3, **B3 F** = Folie Block 3 (gedruckte Foliennummer).
Alle Entscheidungen sind Vorschläge, bis das Team sie bestätigt. Regelwerte sind vorläufig (AE-01 bis AE-03, A-01 bis A-05).

## 1 Schichten und Abhängigkeitsrichtung (B3 F7, F8)

| Schicht | Paket | Verantwortung | Klassen (v1) |
|---|---|---|---|
| Oberfläche | `ui` | zeigt an, nimmt Eingaben entgegen, fragt vor dem Löschen nach; kennt nur Use Cases | noch offen (UI-Technik: Teamentscheidung) |
| Anwendung | `application` | ein Use Case je Nutzerziel, koordiniert, definiert Ports, übersetzt Fehler in `Result` | `AddPhotoUseCase`, `DeleteAlbumUseCase`, `Result`; Ports `AlbumRepository`, `PhotoStorage`, `ImageHasher` |
| Domäne | `domain` | Fachmodell und alle Geschäftsregeln; kennt weder UI noch Datei noch KI | `Album`, `Photo`, `AlbumId`, `PhotoId`, fachliche Exceptions |
| Infrastruktur | `infrastructure` | implementiert die Ports; austauschbar, in Tests ersetzbar | `FileAlbumRepository`, `FileSystemPhotoStorage`, `Sha256ImageHasher` |

```text
ui ──► application ──► domain
            ▲
infrastructure (implementiert Ports)
```

**Regel:** Abhängigkeiten zeigen nach innen; die Domäne zeigt nirgendwohin (B3 F7, F20). Verdrahtet wird nur in Main (Composition Root, B3 F9).

**Begründung:** NFA-03 verlangt alle Geschäftsregeln in genau einem Modul und Tests ohne UI und Dateisystem; das leistet nur eine Domäne ohne Abhängigkeiten (B3 F5, F10). Eine zweite Oberfläche übernimmt die Regeln ohne Kopie (B3 F5, F24).

**Weitere Use Cases** der Pflichtfunktionalität (PB §5.1) folgen demselben Muster und werden in Block 4 ergänzt: Album erstellen, anzeigen, umbenennen; Foto anzeigen, entfernen, beschreiben.

## 2 Entwurfsziele aus den NFA (B3 F10)

| NFA | Entwurfskonsequenz |
|---|---|
| NFA-01 Performance | Anzeige lädt Bilddaten über `PhotoStorage.read`; Vorschaubilder als mögliche Optimierung bewusst offen |
| NFA-02 Zuverlässigkeit | alle Prüfungen (Datei, Regeln in `addPhoto`) vor jedem Speichern; das gespeicherte Album ändert sich erst mit `save`, bei einem Fehler wird das geladene Album verworfen; scheitert `save`, entfernt der Use Case die Kopie wieder (B3 F10; ADR-003) |
| NFA-03 Testbarkeit | Regeln nur in `Album`/`Photo`; Use Cases bekommen Ports im Konstruktor (ADR-001, ADR-002) |
| NFA-04 Datenschutz | Dateien nur über `PhotoStorage.delete`; Zuständigkeit für Nachlöschen in 24 h offen (§8) |
| NFA-05 Benutzbarkeit | UI fragt nur bei irreversiblen Aktionen nach (AE-04, T-04) |

## 3 Komponenten und Schnittstellen (Ports)

| Port (application) | Operationen | Adapter (infrastructure) | Test-Ersatz | ADR |
|---|---|---|---|---|
| `AlbumRepository` | `save`, `findById: Optional<Album>`, `findAll`, `delete` | `FileAlbumRepository` | `InMemoryAlbumRepository` | ADR-001 |
| `PhotoStorage` | `store(file, key)`, `read(key)`, `delete(key)` | `FileSystemPhotoStorage` | In-Memory-Fake | ADR-002 |
| `ImageHasher` | `validate(file)`, `hash(file)` | `Sha256ImageHasher` | Fake mit festen Hashes | ADR-002 |
| `DescriptionSuggester` (nur Entwurf) | `suggest(image): Optional<String>` | z. B. `ClaudeDescriptionSuggester` | `FakeSuggester` | ADR-005 |

Speicherformat der Albumdaten: Folienbeispiel JSON-Dateien (B3 F8); verbindlich festgelegt und dokumentiert wird es mit `FileAlbumRepository` in Block 4 (PB §6).

## 4 Klassendiagramm (Domäne, Anwendung, Ports)

Quelle: [docs/design/klassendiagramm.puml](docs/design/klassendiagramm.puml) (PlantUML, B3 F47 Punkt 5). Abgeleitet aus den [CRC-Karten](docs/design/crc-karten.md); Änderungen K-1 bis K-9 gegenüber dem vorläufigen Team-Klassendiagramm sind eingearbeitet.

**Geschäftsregel → Klasse (Regel-Check B3 F48):**

| Regel | Klasse |
|---|---|
| Albumname nicht leer, höchstens 50 Zeichen (FA-01) | `Album` (Konstruktor, `rename`) |
| höchstens 100 Fotos (FA-06) | `Album.addPhoto` |
| kein identisches Foto im selben Album (FA-05) | `Album.addPhoto` (Vergleich `Photo.hash()`) |
| Titel, Beschreibung höchstens 100 Zeichen (FA-07) | `Photo` (`describe`) |
| Datei lesbar, Typ, Größe (FA-04) | `ImageHasher.validate` (Port; Werte aus REQUIREMENTS) |
| Löschen löscht Fotos mit (FA-09) | `DeleteAlbumUseCase` |
| Löschen fragt nach (FA-09) | UI |

## 5 Sequenzdiagramme

- **Foto hinzufügen** inkl. Fehlerpfad Duplikat als `alt`-Fragment: [docs/design/sequenz-foto-hinzufuegen.puml](docs/design/sequenz-foto-hinzufuegen.puml) (PB §8; B3 F47 Punkt 3).
- **Album löschen** inkl. Abbruch der Bestätigung: [docs/design/Sequenzdiagramm_Album_loeschen.md](docs/design/Sequenzdiagramm_Album_loeschen.md), gerendert als [Bild](docs/design/Sequenzdiagramm_Album_loeschen.png) (Übung B3 F25).

**Konsistenz-Check (B3 F48):** Jede Nachricht beider Diagramme entspricht einer Operation im Klassendiagramm (`execute`, `validate`, `hash`, `findById`, `addPhoto`, `store`, `save`, `storageKeys`, `delete`).
**Kopplungs-Check (B3 F48):** Die meisten Abhängigkeiten hat `AddPhotoUseCase` (3 Ports, `Album`, `Photo`, `Result`). Begründet: Er koordiniert nur und prüft keine Regel (V-01 in crc-karten.md).

## 6 Entwurfsmuster und Entscheidungen

Pattern nur für ein benanntes Problem (PB §8; B3 F34):

| Pattern | Problem | ADR |
|---|---|---|
| Repository | Alben speichern und finden, ohne dass Use Cases Dateien kennen; Tests ohne Dateisystem | [ADR-001](docs/adr/ADR-001-repository.md) |
| Adapter | Dateisystem und Hash-Bibliothek passen nicht zu den Ports | [ADR-002](docs/adr/ADR-002-adapter-dateien-hash.md) |
| bewusst nicht: Strategy, Factory Method, Observer, Template Method, Singleton | kein benanntes Problem in v1 (Begründung: [Pattern-Bewertung](docs/design/pattern-bewertung.md)) | – |

Weitere Entscheidungen: [ADR-003 Fehlerbehandlung](docs/adr/ADR-003-fehlerbehandlung.md) · [ADR-004 Build mit Maven](docs/adr/ADR-004-build-maven.md) · [ADR-005 KI-Komponente](docs/adr/ADR-005-ki-komponente.md).

## 7 KI-Komponente (Übung B3 F45)

1. **Port und Adapter:** `DescriptionSuggester` in `application`, Adapter in `infrastructure`; Prüfung vor der Anfrage: Metadaten entfernen, Timeout; nach der Antwort: bereinigen, ≤ 100 Zeichen, keine URLs (B3 F32, F43).
2. **Agent „Fotos aufräumen“ – ToolSpec-Liste (höchstens vier Tools):** `listPhotos(albumId)` – IDs und Titel eines Albums lesen; `renameAlbum(albumId, name)` – Album umbenennen; `removePhoto(albumId, photoId)` – Foto aus Album entfernen; `shareAlbum(albumId)` – Album teilen.
3. **Zuordnung:** direkt ausführen: `listPhotos`, `renameAlbum` · nur mit Freigabe (ApprovalQueue + UI, B3 F42): `removePhoto` (irreversibel, T-04), `shareAlbum` (KI-01) · gar nicht anbieten: `deleteAlbum` (kein `case` im Dispatcher, B3 F43).
4. **Grenzen im Code, nicht im Prompt:** Allow-List im `switch` des Dispatchers, Argumentprüfung über `AlbumId`/`PhotoId`, Domänenregeln gelten für jeden Aufrufer, höchstens 20 Runden (B3 F37, F43).
5. **Entscheidung:** Won't, dokumentiert in ADR-005 (Vorschlag; Alternative Should mit `FakeSuggester`).

## 8 Offene Punkte

- Teamentscheidungen: UI-Technik (JavaFX, Swing oder Konsole, PB §6), Speicherformat, Bestätigung ADR-001 bis ADR-005.
- S-2 (crc-karten.md §4.2) / NFA-04: Wer entfernt eine nicht löschbare Bilddatei innerhalb von 24 h und protokolliert die Löschung?
- Methodenname `PhotoStorage.read` (K-7) bestätigen.
- Fotos der Papier-CRC-Karten aus der Übung ergänzen (B3 F19).
- Review-Issue „review: architecture v1“ des Partnerteams einarbeiten (B3 F48); Partnerteam unbekannt.
