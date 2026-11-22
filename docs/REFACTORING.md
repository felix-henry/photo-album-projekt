# Refactoring – Block 6

Grundlage: [PB §12](task_description/Projektbeschreibung_Photo_Album.md#12-wartung-und-refactoring-block-6) und die fünf Schritte aus [Block 6, S. 10](lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=10). Sammel-Issue: [#28](https://github.com/felix-henry/photo-album-projekt/issues/28).

## 1. Ausgangssituation

**Klassen:** `AddPhotoUseCase`, `DeleteAlbumUseCase`, `EditPhotoTextsUseCase`, `OpenAlbumUseCase` und `RemovePhotoUseCase` im Paket `application`, dazu `domain.Album`.

**Smell:** *Duplicated Code* (Fowler). Fünf Use Cases beginnen mit demselben Block aus neun Zeilen: Album über die ID laden, bei einer `StorageException` „Das Album konnte nicht geladen werden.“ melden, bei einem fehlenden Album „Das Album wurde nicht gefunden.“ melden. Zwei Use Cases und `Album.removePhoto` suchen außerdem jeweils mit einem eigenen Stream ein Foto über seine ID.

**Nachweis (Stand `block-6` nach Commit `34e376c`):**

| Messgröße | Wert |
| --- | --- |
| Kopien des Ladeblocks | 5 × 9 Zeilen = 45 Zeilen, Meldungstexte 10-mal als Literal |
| Kopien der Foto-Suche | 3 (`Album.removePhoto`, `EditPhotoTextsUseCase`, `RemovePhotoUseCase`) |
| Länge von `execute` | `AddPhotoUseCase` 23, `DeleteAlbumUseCase` 31, `EditPhotoTextsUseCase` 29, `OpenAlbumUseCase` 12, `RemovePhotoUseCase` 38 Zeilen |

Gezählt mit einem kleinen Skript über die Quelldateien (Zeilen von der Signatur bis zur schließenden Klammer).

## 2. Problem

- Eine Änderung an Meldung oder Ladeweg muss an fünf Stellen gleich ausfallen. Vergisst man eine, meldet die App denselben Fehler unterschiedlich.
- Der Change Request CR-1 ändert genau diese Stellen: Nach der Impact Analysis ([CHANGE_REQUEST.md, Abschnitt 4.1](../CHANGE_REQUEST.md#41-betroffene-teile-je-schicht)) kommt neben dem Album ein Fotobestand hinzu, und Fotos werden nicht mehr nur im Album gesucht. Mit gebündeltem Laden und gebündelter Suche ändert der CR je eine Stelle statt fünf beziehungsweise drei.

## 3. Änderung

Jeder Schritt war eine einzelne Umstrukturierung, danach lief `.\mvnw.cmd verify` mit Java 21, und der Diff wurde auf Verhaltensneutralität geprüft.

| Schritt | Umstrukturierung | Commit | Tests |
| --- | --- | --- | --- |
| 0 | Verhalten fixieren: vorhandene Tests grün, keine Charakterisierungstests nötig (Abschnitt 5) | – | 234 grün |
| 1 | *Extract Method* in die neue Klasse `AlbumLookup.load(AlbumRepository, UUID)` mit `Result<Album>`. Die fünf Use Cases rufen sie auf und geben bei einem Fehler deren Meldung weiter. Ungenutzte Importe von `AlbumId` entfernt. | `2b0a107` | 234 grün |
| 2 | *Extract Method* `Album.findPhoto(PhotoId)` mit `Optional<Photo>`. `Album.removePhoto`, `EditPhotoTextsUseCase` und `RemovePhotoUseCase` nutzen sie. | `8afd7d1` | 234 grün |

Beide Commits ändern nur `src/main`, keine Tests. Fachliche Änderungen (CR-1) folgen in getrennten Commits.

**Diff-Prüfung:**

- Schritt 1: Meldungstexte, ihre Reihenfolge und der `try`-Bereich um `findById` sind unverändert. Die Use Cases geben die Meldung als `Result.failure` mit ihrem eigenen Ergebnistyp weiter, wie vorher.
- Schritt 2: Vorher verglichen die Use Cases `getId().value()` mit der `UUID`, jetzt vergleicht `findPhoto` zwei `PhotoId`. Da `PhotoId` ein `record` über die `UUID` ist, ist das gleichwertig. Einzige Abweichung: Eine `null`-Foto-ID führt jetzt wie schon bei der Album-ID (`new AlbumId(albumId)`) zu einer `NullPointerException` statt zu „Das Foto wurde nicht gefunden.“. Kein Aufrufer übergibt `null`: `AlbumWindow` nimmt die ID immer aus einer `PhotoSummary`.

## 4. Ergebnis

| Messgröße | Vorher | Nachher |
| --- | ---: | ---: |
| Kopien des Ladeblocks | 5 | 1 (`AlbumLookup`) |
| Kopien der Foto-Suche | 3 | 1 (`Album.findPhoto`) |
| `AddPhotoUseCase.execute` | 23 | 19 Zeilen |
| `DeleteAlbumUseCase.execute` | 31 | 27 Zeilen |
| `EditPhotoTextsUseCase.execute` | 29 | 22 Zeilen |
| `OpenAlbumUseCase.execute` | 12 | 8 Zeilen |
| `RemovePhotoUseCase.execute` | 38 | 31 Zeilen |

In den fünf Use Cases sind 29 Zeilen weggefallen, die neue Klasse `AlbumLookup` hat 29 Zeilen; die Gesamtlänge bleibt gleich, aber Ladeweg und Meldungen stehen an einer Stelle. `RenameAlbumUseCase` bleibt bewusst unverändert: Er lädt mit `findAll` alle Alben, weil `AlbumLibrary` die Eindeutigkeit des neuen Namens prüft, und hat deshalb eigene Meldungen.

## 5. Absicherung durch Tests

Die Fehlerwege beider Schritte sind schon vor dem Refactoring durch Tests abgedeckt, die Meldung und Zustand prüfen:

| Verhalten | Tests |
| --- | --- |
| Album nicht lesbar | `AddPhotoUseCaseTest.reportsAlbumLoadFailure`, `OpenAlbumUseCaseTest.reportsLoadFailure`, `EditPhotoTextsUseCaseTest.reportsLoadFailure` |
| Album unbekannt | `reportsUnknownAlbum` in `AddPhotoUseCaseTest`, `DeleteAlbumUseCaseTest`, `EditPhotoTextsUseCaseTest`, `OpenAlbumUseCaseTest`, `RemovePhotoUseCaseTest` |
| Foto unbekannt | `EditPhotoTextsUseCaseTest.reportsUnknownPhoto`, `RemovePhotoUseCaseTest.reportsUnknownPhoto`, `AlbumPhotoTest.rejectsRemovingUnknownPhoto` |
| Normalfall | alle übrigen Tests der fünf Use Cases und von `AlbumPhotoTest` |

Nach jedem Schritt liefen alle 234 Tests grün. Die Coverage der Domäne bleibt bei 100 % der Zweige.
