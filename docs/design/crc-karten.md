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
| Umbenennen; nur über `AlbumLibrary` aufrufbar | |

### `Photo`

| Verantwortlichkeiten | Partner |
|---|---|
| Eigene ID und Inhaltshash kennen und die ID herausgeben; die App-Kopie ist unter der ID abgelegt | – |
| Titel (höchstens 100 Zeichen) und Beschreibung (höchstens 500 Zeichen) prüfen, ändern und leeren (A-04) | |

### `AlbumLibrary`

| Verantwortlichkeiten | Partner |
|---|---|
| Die aktiven Alben kennen; wird aus den geladenen Alben aufgebaut | `Album` |
| Beim Anlegen, Umbenennen und Wiederherstellen sicherstellen, dass Albumnamen unabhängig von Groß- und Kleinschreibung eindeutig sind (A-02) | |

### `Trash`

| Verantwortlichkeiten | Partner |
|---|---|
| Gelöschte Alben und Fotos als Einträge aufnehmen (A-07) | `TrashEntry` |
| Einen nicht fälligen Eintrag zum Wiederherstellen herausgeben; fällige ablehnen (A-09, A-10) | |
| Fällige Einträge nennen, ohne sie zu entfernen (A-10) | |
| Nach dem endgültigen Löschen die gelöschten Fotos aus einem Eintrag entfernen; nicht gelöschte bleiben im Papierkorb (SR-01, A-12) | |

### `TrashEntry`

| Verantwortlichkeiten | Partner |
|---|---|
| Löschzeitpunkt und Ursprungsalbum mit ID und Namen kennen, damit ein fehlendes Album neu angelegt werden kann (A-07, A-09) | `Album`, `Photo` |
| Das gelöschte Album oder Foto halten | |
| Ab 30 × 24 Stunden nach dem Löschzeitpunkt fällig sein und die verbleibenden Tage nennen (A-08, A-10) | |

## Anwendung

### `AddPhotoUseCase`

| Verantwortlichkeiten | Partner |
|---|---|
| Album laden und speichern | `AlbumRepository` |
| Datei prüfen und hashen lassen | `ImageHasher` |
| Neues `Photo` mit dem Hash erzeugen | `Photo` |
| `Album` das Foto hinzufügen lassen | `Album` |
| Nach der Aufnahme die App-Kopie anlegen und bei einem Fehler wieder entfernen (A-11) | `PhotoStorage` |
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
| Alben speichern und löschen | `Album` |
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
| Kopie zum Anzeigen laden; Kopie löschen, eine schon fehlende gilt als gelöscht; die IDs aller Kopien nennen | |

### `ImageHasher`

| Verantwortlichkeiten | Partner |
|---|---|
| Datei lesen und als JPEG oder PNG bis 20 MiB prüfen (A-03) | wird von `Sha256ImageHasher` implementiert |
| Inhaltshash berechnen | |

## Zuordnung der Geschäftsregeln

Jede Regel steht auf genau einer Karte. Zwei Punkte aus den Anforderungen sind Abläufe statt Regeln eines Objekts: Die Bestätigung vor dem Löschen (FA-04, A-10) fragt die UI ab, bevor sie den Use Case aufruft. Die Bereinigung beim Start, vor dem Anzeigen des Papierkorbs und vor einer Wiederherstellung (A-10) stößt der jeweilige Use Case an: Er holt die fälligen Einträge über `Trash.dueEntries`, löscht ihre App-Kopien und nimmt danach nur die gelöschten Fotos über `Trash.remove` heraus (A-12). Beim Start bereinigt die Anwendung außerdem doppelte Fotos und verwaiste Kopien ([ADR-002](../adr/ADR-002-fehlerbehandlung.md)).

| Regel | Karte |
|---|---|
| Albumname 1 bis 50 Zeichen, gezählt nach A-04 | `Album` |
| Albumname eindeutig | `AlbumLibrary` |
| Höchstens 100 Fotos je Album | `Album` |
| Kein bytegleiches Foto zweimal im selben Album | `Album` |
| Titel höchstens 100, Beschreibung höchstens 500 Zeichen, gezählt nach A-04 | `Photo` |
| Frist von 30 × 24 Stunden | `TrashEntry` |
| Wiederherstellen nur vor Ablauf der Frist | `Trash` |
| Endgültiges Löschen mit Teilerfolg je Foto (A-12) | `Trash` |
| Nur JPEG oder PNG bis 20 MiB (technische Eingangsprüfung) | `ImageHasher` |

## Durchspielen von „Foto hinzufügen“

1. Die UI ruft `AddPhotoUseCase` mit Album und gewählter Datei auf.
2. `AddPhotoUseCase` lädt das Album über `AlbumRepository`.
3. `ImageHasher` prüft die Datei und liefert den Hash. Bei Lesefehler, leerer Datei, ungültigem Bild oder zu großer Datei endet der Ablauf hier.
4. `Album` prüft Duplikat und Fotoanzahl und nimmt das neue `Photo` auf. Bei einem Verstoß endet der Ablauf, gespeichert wurde noch nichts.
5. `PhotoStorage` legt die App-Kopie unter der ID des Fotos an.
6. `AddPhotoUseCase` speichert das Album über `AlbumRepository` und meldet der UI das Ergebnis. Scheitert Schritt 5 oder 6, wird das Album nicht gespeichert und eine angelegte Kopie wieder gelöscht.

Die Reihenfolge folgt UC-01: Erst laufen alle Prüfungen, dann wird gespeichert.

Alle Karten außer `CreateAlbumUseCase`, `AlbumLibrary`, `Trash`, `TrashEntry` und `TrashRepository` kommen dabei zu Wort. Diese fünf gehören zu UC-02 und zum Papierkorb.

`AddPhotoUseCase` hat fünf Partner, zwei mehr als die Faustregel der Folien. Die Karte koordiniert nur und trifft keine fachliche Entscheidung. Deshalb bleibt sie ungeteilt.
