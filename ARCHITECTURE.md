# ARCHITECTURE – Photo Album

Version 1 · Block 3 · IDs und Kürzel wie in [REQUIREMENTS.md](REQUIREMENTS.md) v1.2 (PB §n, B3 Fn). „Block 4“ heißt: entworfen, Code folgt in Block 4.

## Schichten und Abhängigkeitsrichtung

Vier Schichten (B3 F7), Pakete unter `de.dhbw.photoalbum`:

| Paket | Verantwortung | darf importieren |
|---|---|---|
| `ui` | Swing: zeigt an, nimmt Eingaben an, fragt vor dem Löschen nach; keine Geschäftsregel | `application`, `AlbumId`, `PhotoId` |
| `application` | ein Use Case je Nutzerziel; koordiniert, übersetzt Fehler in `Result` | `domain`, `application.port`, `java.util`, `Path` nur als Werttyp |
| `application.port` | Ports in Fachsprache mit Datentypen und Exceptions | `domain`, `java.util`, `Path` |
| `domain` | `Album`, `Photo` und alle Annahme- und Ablehnungsregeln (FA-02, FA-06, FA-07, FA-10) außer „Datei lesbar“ | nur `java.base` |
| `infrastructure` | Adapter für Dateien, JSON, SHA-256 | `application.port`, `domain`, `java.*`, ab Block 4 Jackson |
| `Main` (Block 4) | Composition Root: einzige Stelle mit `new …Adapter` (B3 F9) | alle |

Richtung: `ui → application → domain`, `infrastructure → application.port`. Die Domäne zeigt nirgendwohin (B3 F20).

**Begründung:** NFA-05 verlangt jeden Grenzwert an genau einer Stelle und Regeltests ohne Oberfläche und Dateisystem (AK-N05.2). Das geht nur mit einer technikfreien Domäne (B3 F5, F10). Eine zweite Oberfläche übernähme die Regeln, ohne sie zu kopieren (B3 F24).

## Entwurfsziele aus den NFA (B3 F10)

- **NFA-01:** erst prüfen, dann speichern; geladene Objekte werden bei `Failure` verworfen, bei UC-01 5a löscht `AddPhotoUseCase` die Kopie wieder.
- **NFA-02:** `albums.json` wird über Temp-Datei und `ATOMIC_MOVE` geschrieben; beim Laden prüfen die Domänenkonstruktoren, unlesbare oder regelwidrige Daten werden gemeldet und nicht überschrieben ([ADR-004](docs/adr/ADR-004-speicherformat-json.md)).
- **NFA-03:** Die Albumansicht zeigt nur Texte (FA-08) und liest keine Bilddateien; kein Vorschaubild-Cache nötig.
- **NFA-04:** Swing statt Konsole; Pfadeingabe als Textfeld wie in UC-01 und AK-N04.1.
- **NFA-05:** Grenzwerte als Konstanten in der Domäne; Ports kommen per Konstruktor, Tests setzen Ersatz ein (B3 F9).

## Komponenten und Ports

**Domäne:** `Album` (Fotos in Einfügereihenfolge, A-11), `AlbumName`, `Photo`, `AlbumId`, `PhotoId`, `ContentHash`, `ImageType`, Exceptions unter `DomainException` (B3 F13). `ContentHash` entscheidet über Inhaltsgleichheit (Duplikatregel FA-07, A-01); die Identität eines Fotos ist `PhotoId`.

**Anwendung:** `AddPhotoUseCase` (UC-01), `CreateAlbumUseCase` (UC-02), `ListAlbumsUseCase`, `RenameAlbumUseCase`, `DeleteAlbumUseCase`, `ListPhotosUseCase`, `RemovePhotoUseCase`, `DescribePhotoUseCase` (UC-03 bis UC-08), ohne eigene Interfaces (B3 F34). Rückgabe ist `Result<T>`; Meldungstexte stehen nur in `ErrorMessages`, wörtlich aus REQUIREMENTS, Zahlen aus den Domänenkonstanten. Die UI erhält `AlbumSummary` und `PhotoView`, nie veränderbare Domänenobjekte.

**Ports:**

| Port | Vertrag | Adapter (Block 4) |
|---|---|---|
| `AlbumRepository` | `findAll`, `findById`, `save`, `delete`; liefert neue Objekte, keine Regeln (B3 F33) | `FileAlbumRepository` |
| `PhotoStorage` | `store(Photo, Path)` ohne Teilkopie, `delete(Photo)`; Schlüssel `PhotoId` (A-08) | `FilePhotoStorage` |
| `ImageInspector` | `inspect(Path)` → `ImageInfo` (Typ, Größe, Hash) | `FileImageInspector` |

Abweichung von B3 F20: `ImageInspector` liefert nur Fakten, `Photo` entscheidet, weil Grenzwerte im Adapter NFA-05 verletzen würden.

## Geschäftsregel → Klasse (Regel-Check B3 F48)

| Regel | IDs | prüft | Konstante |
|---|---|---|---|
| Name getrimmt, NFC, 1–50 Codepoints | FA-02, A-02, A-13 | `AlbumName` | `AlbumName.MAX_LENGTH` |
| Name eindeutig | FA-02, A-07, A-14 | `AlbumName.requireUnique` | – |
| Datei lesbar | FA-06 | `FileImageInspector` → `ImageNotReadableException` | – |
| JPEG/PNG am Inhalt | FA-06, A-03 | erkennt `ImageType.fromHeader`, entscheidet `Photo` | – |
| höchstens 20 MB | FA-06, A-04 | `Photo` | `Photo.MAX_FILE_SIZE_BYTES` |
| kein Duplikat (SHA-256), vor Anzahl | FA-07, AE-03, A-01 | `Album.addPhoto` | – |
| höchstens 100 Fotos | FA-07, AE-01 | `Album.addPhoto` | `Album.MAX_PHOTOS` |
| Titel ≤ 100, Beschreibung ≤ 500, optional | FA-10, AE-02, A-05, A-06 | `Photo` | `MAX_TITLE_LENGTH`, `MAX_DESCRIPTION_LENGTH` |

- `Album.addPhoto` prüft das Duplikat vor der Anzahl, umgekehrt zu B3 F13, weil FA-07 bei beidem das Duplikat als Grund verlangt (AK-07.6).
- Die Bestätigung vor dem Löschen liegt in der UI und ist keine Geschäftsregel (FA-05, FA-09, AE-04, A-09).
- Jedes Foto hat eine eigene Kopie, das Original bleibt unberührt (A-08). `DeleteAlbumUseCase` und `RemovePhotoUseCase` löschen die Kopien mit; scheitert das, liefern sie `Success` mit Hinweis und Pfad (AK-05.3, AK-09.3).
- SR-01: Es ist kein Netzwerkadapter verdrahtet.

## Diagramme

- [Klassendiagramm](docs/design/klassendiagramm.puml) ([Bild](docs/design/klassendiagramm.png)): Domäne, Anwendung, Ports, Adapter; KI-Teile als «Entwurf, Won't».
- [Sequenzdiagramm „Foto hinzufügen“](docs/design/sequenz-foto-hinzufuegen.puml) ([Bild](docs/design/sequenz-foto-hinzufuegen.png)): Fehlerpfade als `alt`, darunter Duplikat (UC-01 4a, B3 F47) und Kompensation (5a).
- [CRC-Karten](docs/design/crc-karten.md): Klassen, Verantwortlichkeiten, Zusammenarbeit; jede Geschäftsregel auf genau einer Karte (B3 F18, F19).

**Konsistenz-Check (B3 F48):** Jede Nachricht des Sequenzdiagramms hat eine Operation im Klassendiagramm.

## Entscheidungen und Entwurfsmuster

Ein Pattern nur für ein benanntes Problem, jedes mit eigenem ADR (PB §8, B3 F28):

- [ADR-001](docs/adr/ADR-001-schichten-ports-adapter.md) Schichten, Ports und Pattern **Adapter** für alle Technik. Problem: Regeltests sollen ohne Dateisystem laufen, Technik soll austauschbar sein (NFA-05; B3 F8, F32).
- [ADR-002](docs/adr/ADR-002-repository.md) Pattern **Repository**. Problem: Use Cases sollen Alben speichern und finden, ohne Dateien zu kennen. `AlbumRepository` ohne Factory, verdrahtet in `Main` (B3 F33, F46).
- [ADR-003](docs/adr/ADR-003-bilddatei-ports.md) Bilddatei-Ports
- [ADR-004](docs/adr/ADR-004-speicherformat-json.md) Speicherformat JSON
- [ADR-005](docs/adr/ADR-005-fehlerbehandlung.md) Fehlerbehandlung
- [ADR-006](docs/adr/ADR-006-ui-swing.md) Oberfläche Swing
- [ADR-007](docs/adr/ADR-007-ki-komponente.md) KI-Komponente

Bewusst nicht eingesetzt:

- Strategy: Sortierung ist durch A-11 fest, ein Comparator reicht (B3 F29).
- Factory: nur eine Konfiguration (B3 F30).
- Observer: Änderungen löst nur die UI selbst aus; sie aktualisiert ihre Ansichten aus dem Result und lädt die Albumübersicht beim Zurückwechseln über `ListAlbumsUseCase` neu. Es gibt keinen Empfänger, den die Anwendung nicht kennt (B3 F31).
- Template Method: kein variabler Schritt (B3 F33).
- Singleton: Composition Root statt Singleton (B3 F33).

## KI-Komponente (Entwurf, Won't)

1. Port `TitleSuggester` in `application.port`: `Optional<String> suggestTitle(byte[] imageContent)`.
2. `SuggestTitleUseCase` liefert nur einen „Vorschlag“; gespeichert wird erst nach Bestätigung über `DescribePhotoUseCase` (KI-03).
3. Adapter `HttpTitleSuggester` in `infrastructure`, nicht verdrahtet: SR-01 hat Vorrang.
4. Guardrails im Adapter (B3 F43): vorher Metadaten entfernen, keinen Dateinamen senden (KI-01), Timeout 5 s; nachher nur Text annehmen, keine Änderungsfunktion (KI-02), Steuerzeichen entfernen, keine URLs, höchstens `Photo.MAX_TITLE_LENGTH` Codepoints nach NFC, sonst verwerfen (KI-03, A-13).
5. Umsetzung Won't (REQUIREMENTS 6.1); Begründung und verworfene Alternativen (Should mit Fake, Agent mit Werkzeugen) in [ADR-007](docs/adr/ADR-007-ki-komponente.md). Die Domäne kennt keine KI.

## Build und Projektgerüst

Maven mit Wrapper (Teamentscheidung; PB §6 lässt Maven oder Gradle zu), `./mvnw verify`. Gerüst (B3 F50): Java 21; `domain` mit Konstruktoren und Invarianten, `application`, `infrastructure` und `ui` nur mit `package-info.java`. Weitere Methoden, Ports, Use Cases, Adapter und `Main` folgen in Block 4; `Album.addPhoto` und erste Tests siehe [TESTING.md](TESTING.md).

## Schwächen und offene Punkte

- `AddPhotoUseCase` hat mehr als drei Mitarbeiter (drei Ports, `Album`, `Photo`; Faustregel B3 F18). Bewusst: Er ist der Controller für UC-01 (B3 F21) und koordiniert nur.
- Eine verwaiste Bildkopie bleibt, wenn bei UC-01 5a auch die Kompensation scheitert (dann ist NFA-01 verletzt) oder der Prozess zwischen Kopie und `save` hart beendet wird (mit AK-N02.5 vereinbar). Automatisches Aufräumen haben wir verworfen (AI_USAGE Fall 1 #6).
