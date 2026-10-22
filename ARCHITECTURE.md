# ARCHITECTURE – Photo Album

## Schichten

Pakete unter `de.dhbw.photoalbum`:

| Paket | Verantwortung |
|---|---|
| `ui` | zeigt an, nimmt Eingaben an, fragt vor dem Löschen nach; keine Geschäftsregel |
| `application` | ein Use Case je Nutzerziel; übersetzt Fehler in Meldungen |
| `application.port` | Schnittstellen zu Speicher und Bilddateien |
| `domain` | `Album`, `Photo` und alle Geschäftsregeln |
| `infrastructure` | Adapter für Dateien, JSON und Hash |

Abhängigkeiten: `ui → application → domain` und `infrastructure → application.port → domain`. Aus der Domäne kennt die UI nur `AlbumId` und `PhotoId`. `Main` verdrahtet alle Adapter.

**Begründung:** Die Geschäftsregeln sind so ohne Oberfläche und Dateisystem testbar. Technik lässt sich tauschen, ohne einen Use Case zu ändern.

## Ports

| Port | Aufgabe | Adapter |
|---|---|---|
| `AlbumRepository` | Alben laden, speichern, löschen | `FileAlbumRepository` |
| `ImageInspector` | Typ, Größe und Hash einer Bilddatei ermitteln | `FileImageInspector` |
| `PhotoStorage` | Bildkopie im Datenordner ablegen und löschen | `FilePhotoStorage` |

## Geschäftsregeln

| Regel | Klasse |
|---|---|
| Albumname nicht leer, begrenzte Länge, eindeutig | `AlbumName` |
| nur erlaubte Bildtypen bis zur Höchstgröße | `Photo` |
| Titel und Beschreibung optional, begrenzte Länge | `Photo` |
| kein Duplikat, danach höchstens 10.000 Fotos | `Album` |

## Diagramme

- [Klassendiagramm](docs/design/klassendiagramm.puml) ([Bild](docs/design/klassendiagramm.png))
- [Sequenzdiagramm „Foto hinzufügen“](docs/design/sequenz-foto-hinzufuegen.puml) ([Bild](docs/design/sequenz-foto-hinzufuegen.png))

## Entscheidungen

- [ADR-001](docs/adr/ADR-001-adapter.md): Pattern Adapter für Dateien und Hash
- [ADR-002](docs/adr/ADR-002-repository.md): Pattern Repository
- [ADR-003](docs/adr/ADR-003-speicherformat-json.md): Speicherformat JSON

## KI-Komponente (Entwurf, nicht umgesetzt)

Der Port `TitleSuggester` schlägt einen Titel vor. Der Adapter `HttpTitleSuggester` ist nicht verdrahtet, weil keine Daten das Gerät verlassen dürfen.

Guardrails im Adapter:
- vor dem Senden Metadaten und Dateinamen entfernen
- nur Text annehmen, zu lange Vorschläge verwerfen

Gespeichert wird erst nach Bestätigung durch die Nutzerin.

## Schwächen

- `AddPhotoUseCase` hat viele Abhängigkeiten, koordiniert aber nur.
- Scheitert beim Hinzufügen nach dem Kopieren auch das Zurücknehmen, bleibt eine verwaiste Bildkopie.
