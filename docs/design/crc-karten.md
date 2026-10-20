# CRC-Karten – Photo Album

Block 3 · Format nach B3 F18, Übung B3 F19 · IDs wie in [REQUIREMENTS.md](../../REQUIREMENTS.md) v1.2 · Klassen- und Methodennamen wie im [Klassendiagramm](klassendiagramm.puml).

Karten für Domäne, Anwendung (die zwei ausgearbeiteten Use Cases UC-01 und UC-02) und Ports (B3 F19). Keine Karte haben Werttypen, die keine Regel entscheiden (`AlbumId`, `PhotoId`, `ContentHash`, `ImageType`), Exceptions, Adapter, die übrigen Use Cases und die KI-Teile («Entwurf, Won't», ADR-007). `ErrorMessages` und `Result` nutzt jeder Use Case zum Übersetzen der Fehler; sie stehen nicht auf den Karten. Geschäftsregeln (NFA-05) sind **fett**.

## Domäne

**Album**

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| kennt `id`, `name` und die Fotos in Einfügereihenfolge, gibt sie nur als Kopie heraus (`photos()`, `photoCount()`, A-11) | Photo |
| **prüft in `addPhoto` zuerst Duplikat (gleicher `ContentHash`), dann höchstens `MAX_PHOTOS` = 100** (FA-07, AE-01, AE-03, A-01, AK-07.6); ebenso der Konstruktor zum Wiederherstellen (NFA-02) | Photo |
| entfernt ein Foto, benennt sich um, beschreibt ein Foto (`removePhoto`, `rename`, `describePhoto`) | Photo, AlbumName |

**AlbumName**

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| **Name getrimmt (`strip`), NFC, 1 bis `MAX_LENGTH` = 50 Codepoints**, sonst `InvalidAlbumNameException` mit `EMPTY` bzw. `TOO_LONG` (FA-02, A-02, A-13) | – |
| vergleicht Namen (`isSameAs`: NFC und `toLowerCase(Locale.ROOT)`, A-14) | – |
| **Name eindeutig:** `requireUnique(candidate, others)`, sonst `DUPLICATE` (FA-02, A-07, UC-02 4c); einzige Stelle dieser Regel | – |

**Photo**

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| kennt `id`, `hash`, `fileName`, `type`, `sizeBytes`, `title`, `description` | – |
| **nur JPEG oder PNG (`type` ≠ `OTHER`) bis `MAX_FILE_SIZE_BYTES` = 20 MB**, sonst `UnsupportedImageException` (FA-06, A-03, A-04); den Typ erkennt `ImageType.fromHeader` | ImageType |
| **Titel höchstens `MAX_TITLE_LENGTH` = 100, Beschreibung höchstens `MAX_DESCRIPTION_LENGTH` = 500 Codepoints nach NFC, beide optional**, sonst `TextTooLongException` (FA-10, AE-02, A-05, A-06, A-13) | – |
| liefert das Foto mit neuem Titel und neuer Beschreibung (`describe`) | – |

## Anwendung

**AddPhotoUseCase** (UC-01)

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| lädt das Album (`findById`) | AlbumRepository |
| lässt die Datei lesen und Typ, Größe und Hash ermitteln (`inspect`) | ImageInspector |
| erzeugt `Photo` mit `PhotoId.newId()` und übergibt es dem Album (`addPhoto`) | Photo, Album |
| lässt erst danach die Kopie anlegen (`store`) und das Album speichern (`save`); scheitert `save`, nimmt er die Kopie zurück (`delete`, UC-01 5a, NFA-01) | PhotoStorage, AlbumRepository |
| übersetzt jede Exception in `Result.failure`, sonst `Result.success(PhotoView)` | – |

Fünf Mitarbeiter, mehr als die Faustregel erlaubt (B3 F18). Bewusst nicht geteilt: Die Karte ist der Controller für UC-01 (B3 F21), koordiniert nur und prüft keine Regel (ARCHITECTURE.md, Schwächen; AI_USAGE Fall 3 #3).

**CreateAlbumUseCase** (UC-02)

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| erzeugt `AlbumName` aus der Eingabe | AlbumName |
| lässt die Eindeutigkeit prüfen: `AlbumName.requireUnique` mit allen Namen aus `findAll()` | AlbumName, AlbumRepository |
| legt ein leeres `Album` an und speichert es (`save`) | Album, AlbumRepository |
| übersetzt Fehler in `Result<AlbumSummary>` | – |

## Ports

**AlbumRepository** «interface», implementiert von `FileAlbumRepository`

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| `findAll()`, `findById(AlbumId): Optional<Album>`, `save(Album)`, `delete(AlbumId)` | Album |
| liefert bei jedem Aufruf neue Objekte, ein verworfenes Album ändert nichts (NFA-01); keine Regeln (B3 F33) | – |
| meldet Fehler als `StorageException` mit Dateipfad | – |

**ImageInspector** «interface», implementiert von `FileImageInspector`

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| **Datei lesbar**, sonst `ImageNotReadableException` (FA-06, UC-01 3a) | – |
| liefert nur Fakten: `inspect(Path)` → `ImageInfo(type, sizeBytes, hash)`, Typ am Inhalt (A-03), SHA-256 (A-01); über die Annahme entscheidet `Photo` | – |

**PhotoStorage** «interface», implementiert von `FilePhotoStorage`

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| `store(Photo, Path)`: eigene Kopie im Datenordner, Schlüssel `PhotoId` (A-08); bei Fehler keine Teilkopie (FA-06) | Photo |
| `delete(Photo)`: entfernt die Kopie, sonst `StorageException` mit Pfad (FA-05, FA-09) | Photo |

## Prüfung

- **Regeln:** Jede Zeile von „Geschäftsregel → Klasse“ in [ARCHITECTURE.md](../../ARCHITECTURE.md) steht fett auf genau einer Karte: Name und Eindeutigkeit auf AlbumName; Typ, Größe, Titel und Beschreibung auf Photo; Duplikat vor Anzahl auf Album; „Datei lesbar“ auf ImageInspector. Die Bestätigung vor dem Löschen liegt in der UI und hat keine Karte (FA-05, FA-09, A-09).
- **Faustregel** (B3 F18: mehr als 5 Verantwortlichkeiten oder mehr als 3 Mitarbeiter → teilen): eingehalten außer bei AddPhotoUseCase (siehe oben).
- **Walkthrough „Foto hinzufügen“** (B3 F19) in der Reihenfolge des [Sequenzdiagramms](sequenz-foto-hinzufuegen.puml): Jede Nachricht trifft eine Verantwortlichkeit. Stumm bleiben nur AlbumName und CreateAlbumUseCase (UC-02). Keine Karte übernimmt alles: AddPhotoUseCase spricht in jedem Schritt, entscheidet aber keine Regel.
