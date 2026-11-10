# CRC-Karten

CRC steht für Class, Responsibilities, Collaborators. Die Karten leiten sich aus [UC-01 Foto hinzufügen](../../REQUIREMENTS.md#14-uc-01-foto-hinzufügen), [UC-02 Album erstellen](../../REQUIREMENTS.md#15-uc-02-album-erstellen) und den Geschäftsregeln in [REQUIREMENTS.md](../../REQUIREMENTS.md) ab. Sie sind das Rohmaterial für das Klassendiagramm.

## Domäne

### `Album`

| Verantwortlichkeiten | Partner |
|---|---|
| Namen kennen und prüfen: nach dem Entfernen äußerer Leerzeichen 1 bis 50 Zeichen (A-02) | `Photo` |
| Seine Fotos kennen und zählen | |
| Foto hinzufügen, wenn noch kein Foto mit demselben Hash enthalten ist (A-01) und höchstens 100 Fotos erreicht werden | |
| Foto entfernen | |
| Umbenennen | |

### `Photo`

| Verantwortlichkeiten | Partner |
|---|---|
| Eigene ID und Inhaltshash kennen; die App-Kopie ist unter der ID abgelegt | – |
| Titel (höchstens 100 Zeichen) und Beschreibung (höchstens 500 Zeichen) prüfen, ändern und leeren (A-04) | |

### `AlbumLibrary`

| Verantwortlichkeiten | Partner |
|---|---|
| Die aktiven Alben kennen | `Album` |
| Sicherstellen, dass Albumnamen unabhängig von Groß- und Kleinschreibung eindeutig sind (A-02) | |

### `Trash`

| Verantwortlichkeiten | Partner |
|---|---|
| Gelöschte Alben und Fotos mit Löschzeitpunkt kennen (A-07) | `Album`, `Photo` |
| Einen Eintrag ab 30 × 24 Stunden nach dem Löschzeitpunkt als fällig erkennen (A-10) | |
| Nicht fällige Einträge wiederherstellen; fällige ablehnen (A-09, A-10) | |

## Anwendung

### `AddPhotoUseCase`

| Verantwortlichkeiten | Partner |
|---|---|
| Album über `AlbumRepository` laden | `AlbumRepository` |
| Datei von `ImageHasher` prüfen und hashen lassen | `ImageHasher` |
| `Album` das Foto hinzufügen lassen und das Album speichern | `PhotoStorage` |
| Nach der Aufnahme die App-Kopie anlegen und bei einem Fehler wieder entfernen (A-11) | `Album` |
| Fehler in eine verständliche Meldung für die UI übersetzen | |

### `CreateAlbumUseCase`

| Verantwortlichkeiten | Partner |
|---|---|
| Aktive Alben über `AlbumRepository` laden | `AlbumRepository` |
| Neues `Album` von `AlbumLibrary` aufnehmen lassen und speichern | `AlbumLibrary` |
| Fehler in eine verständliche Meldung für die UI übersetzen | `Album` |

## Ports

### `AlbumRepository`

| Verantwortlichkeiten | Partner |
|---|---|
| Alben speichern | `Album` |
| Ein Album über seine ID laden; alle Alben laden | wird von `FileAlbumRepository` implementiert |

### `TrashRepository`

| Verantwortlichkeiten | Partner |
|---|---|
| Papierkorb speichern und laden | `Trash` |
| | wird von `FileTrashRepository` implementiert |

### `PhotoStorage`

| Verantwortlichkeiten | Partner |
|---|---|
| Eigene Kopie einer Bilddatei anlegen (A-05) | wird von `FilePhotoStorage` implementiert |
| Kopie zum Anzeigen laden; Kopie löschen | |

### `ImageHasher`

| Verantwortlichkeiten | Partner |
|---|---|
| Datei lesen und als JPEG oder PNG bis 20 MiB prüfen (A-03) | wird von `Sha256ImageHasher` implementiert |
| Inhaltshash berechnen | |

## Zuordnung der Geschäftsregeln

Jede Regel steht auf genau einer Karte.

| Regel | Karte |
|---|---|
| Albumname 1 bis 50 Zeichen | `Album` |
| Albumname eindeutig | `AlbumLibrary` |
| Höchstens 100 Fotos je Album | `Album` |
| Kein bytegleiches Foto zweimal im selben Album | `Album` |
| Titel höchstens 100, Beschreibung höchstens 500 Zeichen | `Photo` |
| Frist von 30 × 24 Stunden und Wiederherstellen nur vor Ablauf | `Trash` |
| Nur JPEG oder PNG bis 20 MiB (technische Eingangsprüfung) | `ImageHasher` |

## Durchspielen von „Foto hinzufügen“

1. Die UI ruft `AddPhotoUseCase` mit Album und gewählter Datei auf.
2. `AddPhotoUseCase` lädt das Album über `AlbumRepository`.
3. `ImageHasher` prüft die Datei und liefert den Hash. Bei Lesefehler, leerer Datei, ungültigem Bild oder zu großer Datei endet der Ablauf hier.
4. `Album` prüft Duplikat und Fotoanzahl und nimmt das neue `Photo` auf. Bei einem Verstoß endet der Ablauf, gespeichert wurde noch nichts.
5. `PhotoStorage` legt die App-Kopie unter der ID des Fotos an.
6. `AddPhotoUseCase` speichert das Album über `AlbumRepository` und meldet der UI das Ergebnis. Scheitert Schritt 5 oder 6, wird das Album nicht gespeichert und eine angelegte Kopie wieder gelöscht.

Die Reihenfolge folgt UC-01: Erst laufen alle Prüfungen, dann wird gespeichert.

Alle Karten außer `CreateAlbumUseCase`, `AlbumLibrary`, `Trash` und `TrashRepository` kommen dabei zu Wort. Diese vier gehören zu UC-02 und zum Papierkorb.

`AddPhotoUseCase` hat vier Partner, einen mehr als die Faustregel der Folien. Die Karte koordiniert nur und trifft keine fachliche Entscheidung. Deshalb bleibt sie ungeteilt.
