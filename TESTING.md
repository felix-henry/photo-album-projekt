# TESTING – Teststrategie und Qualitätsnachweise

## 1. Teststrategie

Die Tests folgen der Testpyramide: viele schnelle Unit-Tests, wenige Integrationstests, einzelne manuelle Systemtests. Welche Testarten eingesetzt werden, richtet sich nach dem Risiko ([Block 5, S. 7–9](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=7)).

### 1.1 Teststufen

| Stufe | Was wird geprüft | Umsetzung im Projekt |
| --- | --- | --- |
| Unit-Test | Fachliche Regeln der Domäne und Abläufe der Use Cases, ohne Oberfläche, ohne echte Dateien und ohne Wartezeit (NFA-05) | JUnit 5 in `src/test/java/.../domain` und `.../application`. Use Cases laufen gegen Fakes (`InMemoryAlbumRepository`, `InMemoryPhotoStorage`, `InMemoryTrashRepository`, `FakeImageHasher`) und eine feste Uhr (`Clock.fixed`). |
| Integrationstest | Use Case mit echter Infrastruktur und einer fachlichen Frage zu Persistenz, Serialisierung oder Pfaden | JUnit 5 mit `@TempDir`, siehe Abschnitt 4.2. Die Adaptertests in `.../infrastructure` prüfen die Dateiadapter zusätzlich einzeln. |
| Systemtest | Die gesamte Anwendung über die Swing-Oberfläche | Manuell mit der gebauten JAR, siehe Abschnitt 5. |
| Abnahmetest | Prüfung gegen die Akzeptanzkriterien durch die Auftraggeberin | Abschlusspräsentation (Block 7). |

### 1.2 Testarten

| Testart | Frage | Umsetzung im Projekt |
| --- | --- | --- |
| Funktional | Tut das System, was FA und AK verlangen? | Unit- und Integrationstests mit AK-Bezug, Zuordnung in Abschnitt 2. |
| Regression | Funktioniert Bestehendes nach einer Änderung noch? | Die gesamte Suite läuft in der CI bei jedem Push auf `main` und bei jedem PR (`./mvnw verify`). In Block 6 sichern Regressionstests Change Request und Refactoring ab. |
| Sicherheit | Was passiert bei bösartigen oder fehlerhaften Dateien? | Tests für Dateieingaben, siehe Abschnitt 6. |
| Performance | Hält das System NFA-03 ein? | Messung mit 100 Fotos auf den Team-Laptops, siehe Abschnitt 7. |
| Usability | Können Nutzer ohne Erklärung ein Album erstellen und ein Foto hinzufügen (NFA-04)? | Selbsttest durch die Teammitglieder mit Protokoll, siehe Abschnitt 5. |
| Kompatibilität | Läuft das System dort, wo es laufen soll? | CI auf Linux (Ubuntu, JDK 21), Build und manuelle Tests auf den Laptops des Teams. |

Die Testabdeckung wird mit JaCoCo erhoben und je Schicht interpretiert, siehe Abschnitt 8.

## 2. Zuordnung Akzeptanzkriterium → Verfahren → Testmethode

Jeder automatisierte Test nennt seine AK-ID am Anfang von `@DisplayName`. Die Tabelle ist daraus abgeleitet. „Szenario aus dem AK“ heißt, dass der Test Ausgangslage, Aktion und Ergebnis des Akzeptanzkriteriums direkt umsetzt. Die Tests aus dem Test Design Lab (Abschnitt 3) sind gesondert aufgeführt.

| AK | Verfahren | Testmethode |
| --- | --- | --- |
| AK-01.1 | Äquivalenzklassen | `CreateAlbumUseCaseTest.savesValidAlbum`<br>`AlbumTest.stripsOuterSpaces` |
| AK-01.3 | Äquivalenzklassen | `CreateAlbumUseCaseTest.rejectsBlankName`<br>`AlbumTest.rejectsBlankName` |
| AK-01.4 | Grenzwertanalyse | `AlbumTest.acceptsFiftyCharactersWithEmoji` |
| AK-01.5 | Grenzwertanalyse | `CreateAlbumUseCaseTest.rejectsTooLongName`<br>`AlbumTest.rejectsFiftyOneCharacters` |
| AK-01.6 | Szenario aus dem AK | `CreateAlbumUseCaseTest.reportsSaveFailure`<br>`FileAlbumRepositoryTest.reportsSaveFailure` |
| AK-01.7 | Szenario aus dem AK | `CreateAlbumUseCaseTest.rejectsTakenName`<br>`AlbumLibraryTest.rejectsTakenNameIgnoringCase`<br>`AlbumLibraryTest.keepsAlbumsUnchangedAfterRejection` |
| AK-02.1 | Szenario aus dem AK | `ListAlbumsUseCaseTest.listsAllAlbumsSortedByName` |
| AK-02.2 | Szenario aus dem AK | `ListAlbumsUseCaseTest.listsNothingWithoutAlbums`<br>`FileAlbumRepositoryTest.findsNothingWithoutAlbumFolder` |
| AK-02.3 | Szenario aus dem AK | `ListAlbumsUseCaseTest.reportsLoadFailure`<br>`FileAlbumRepositoryTest.reportsUnreadableAlbumFile`<br>`FileAlbumRepositoryTest.reportsPhotoWithoutHash`<br>`FileAlbumRepositoryTest.reportsAlbumIdNotMatchingFileName` |
| AK-03.1 | Szenario aus dem AK | `RenameAlbumUseCaseTest.changesCaseOfOwnName`<br>`AlbumLibraryTest.renamesToOwnNameInOtherCase`<br>`FileAlbumRepositoryTest.renamedAlbumKeepsPhotos` |
| AK-03.2 | Äquivalenzklassen | `RenameAlbumUseCaseTest.rejectsBlankName`<br>`AlbumLibraryTest.rejectsBlankNameAndKeepsOldName` |
| AK-03.3 | Szenario aus dem AK | `RenameAlbumUseCaseTest.rejectsNameOfAnotherAlbum`<br>`AlbumLibraryTest.rejectsNameOfAnotherAlbum` |
| AK-03.4 | Grenzwertanalyse | `RenameAlbumUseCaseTest.rejectsTooLongName`<br>`AlbumLibraryTest.rejectsTooLongNameAndKeepsOldName` |
| AK-03.5 | Szenario aus dem AK | `RenameAlbumUseCaseTest.reportsSaveFailureAndKeepsOldName` |
| AK-04.1 | Szenario aus dem AK | `DeleteAlbumUseCaseTest.movesAlbumAsTile`<br>`TrashTest.movesAlbumAsTile` |
| AK-04.2 | Szenario aus dem AK | `DeleteAlbumUseCaseTest.rollsBackTrashWhenDeletingFails` |
| AK-04.3 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.restoresTileAsAlbum` |
| AK-04.4 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.restoresFullTileIntoNewAlbum` |
| AK-04.5 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.removesCreatedAlbumWhenTrashSaveFails` |
| AK-04.6 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.reportsNameConflictForTile` |
| AK-04.8 | Szenario aus dem AK | `ListTrashUseCaseTest.showsTileAndSinglePhoto` |
| AK-05.1 | Szenario aus dem AK | `AddPhotoUseCaseTest.addsPhotoAndStoresCopy`<br>`AlbumPhotoTest.addsPhotoToAlbumWithSpace`<br>`FilePhotoStorageTest.storeKeepsOriginal`<br>`Sha256ImageHasherTest.acceptsJpeg`<br>`Sha256ImageHasherTest.acceptsPngWithJpgExtension`<br>`Sha256ImageHasherTest.acceptsImageOfExactlyMaxSize` |
| AK-05.2 | Äquivalenzklassen | `AddPhotoUseCaseTest.rejectsInvalidFile`<br>`Sha256ImageHasherTest.rejectsImageOneByteTooLarge`<br>`Sha256ImageHasherTest.rejectsEmptyFile`<br>`Sha256ImageHasherTest.rejectsTextFileWithJpgExtension`<br>`Sha256ImageHasherTest.rejectsDamagedPng` |
| AK-05.4 | Szenario aus dem AK | `AddPhotoUseCaseTest.rejectsInvalidFile`<br>`Sha256ImageHasherTest.rejectsMissingFile` |
| AK-05.5 | Szenario aus dem AK | `AddPhotoUseCaseTest.rollsBackCopyWhenAlbumCannotBeSaved` |
| AK-06.1 | Äquivalenzklassen | `AddPhotoUseCaseTest.rejectsDuplicateWithoutStoringCopy`<br>`AlbumPhotoTest.rejectsDuplicateContent`<br>`Sha256ImageHasherTest.sameContentGivesSameHash` |
| AK-06.2 | Äquivalenzklassen | `AlbumPhotoTest.acceptsDifferentContent`<br>`Sha256ImageHasherTest.differentContentGivesDifferentHash` |
| AK-06.3 | Szenario aus dem AK | `AlbumPhotoTest.reportsDuplicateBeforeFullAlbum` |
| AK-07.1 | Grenzwertanalyse | `AlbumPhotoTest.acceptsHundredthPhoto` |
| AK-07.2 | Grenzwertanalyse | `AddPhotoUseCaseTest.rejectsPhotoForFullAlbum`<br>`AlbumPhotoTest.rejectsHundredFirstPhoto` |
| AK-08.1 | Szenario aus dem AK | `LoadPhotoUseCaseTest.loadsStoredImage`<br>`OpenAlbumUseCaseTest.opensAlbumWithPhotos`<br>`FilePhotoStorageTest.loadReturnsStoredCopy` |
| AK-08.2 | Szenario aus dem AK | `OpenAlbumUseCaseTest.opensEmptyAlbum` |
| AK-08.3 | Szenario aus dem AK | `LoadPhotoUseCaseTest.reportsUnreadableImage`<br>`FilePhotoStorageTest.loadOfMissingCopyFails`<br>`ImageBufferTest.reportsUnreadableImageAndRetries`<br>`ImageDecoderTest.reportsUndecodableData` |
| AK-09.1 | Szenario aus dem AK | `RemovePhotoUseCaseTest.movesPhotoToTrash`<br>`RemovePhotoUseCaseTest.keepsExistingEntrySeparate`<br>`TrashTest.movesPhotoAsOwnEntry`<br>`TrashTest.keepsByteIdenticalEntriesSeparate` |
| AK-09.2 | Szenario aus dem AK | `RemovePhotoUseCaseTest.rollsBackTrashWhenAlbumSaveFails` |
| AK-09.3 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.restoresPhotoIntoOrigin` |
| AK-09.4 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.recreatesMissingOriginForPhoto` |
| AK-09.5 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.reportsDuplicateInOrigin`<br>`RestoreTrashEntryUseCaseTest.reportsFullOrigin` |
| AK-09.6 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.restoresConflictingPhotoIntoNewAlbum` |
| AK-10.1 | Szenario aus dem AK | `EditPhotoTextsUseCaseTest.savesBothTexts`<br>`PhotoTest.changesBothTexts`<br>`FileAlbumRepositoryTest.keepsPhotoTextsAfterRestart` |
| AK-10.2 | Grenzwertanalyse | `PhotoTest.acceptsTitleOfMaxLength` |
| AK-10.3 | Grenzwertanalyse | `EditPhotoTextsUseCaseTest.rejectsTooLongTitle`<br>`PhotoTest.rejectsTooLongTitle` |
| AK-10.4 | Grenzwertanalyse | `PhotoTest.acceptsDescriptionOfMaxLength` |
| AK-10.5 | Grenzwertanalyse | `EditPhotoTextsUseCaseTest.rejectsTooLongDescription`<br>`PhotoTest.rejectsTooLongDescription` |
| AK-10.6 | Szenario aus dem AK | `PhotoTest.clearsTitleOnly`<br>`PhotoTest.clearsDescriptionOnly` |
| AK-N02.1 | Szenario aus dem AK | `FileTrashRepositoryTest.keepsEntriesAfterRestart` |
| AK-N02.2 | Szenario aus dem AK | `StartupCleanupUseCaseTest.changesNothingWhenAlbumsAreUnreadable`<br>`StartupCleanupUseCaseTest.changesNothingWhenTrashIsUnreadable`<br>`FileTrashRepositoryTest.reportsUnreadableFile` |
| AK-N03.2 | Szenario aus dem AK | `ImageBufferTest.neverHoldsMoreThanCapacity`<br>`ImageBufferTest.doesNotReloadBufferedImage`<br>`ImageBufferTest.releasesDistantImages` |
| AK-SR01.1 | Szenario aus dem AK | `DeletePermanentlyUseCaseTest.deletesPhotoEntryAndCopy` |
| AK-SR01.2 | Szenario aus dem AK | `DeletePermanentlyUseCaseTest.keepsPhotoWhenDeletionFails` |
| AK-SR01.3 | Grenzwertanalyse | `ListTrashUseCaseTest.removesDueEntriesBeforeShowing`<br>`StartupCleanupUseCaseTest.removesDueEntries`<br>`TrashEntryTest.isDueAfterExactlyThirtyTimesTwentyFourHours`<br>`TrashTest.listsOnlyDueEntries` |
| AK-SR01.4 | Szenario aus dem AK | `DeletePermanentlyUseCaseTest.reportsPartialSuccessForTile`<br>`TrashTest.keepsPhotosThatWereNotDeleted` |
| AK-SR01.6 | Grenzwertanalyse | `ListTrashUseCaseTest.keepsDueEntryWhenCopyCannotBeDeleted`<br>`RestoreTrashEntryUseCaseTest.refusesDueEntryAndCleansItUp`<br>`RestoreTrashEntryUseCaseTest.keepsDueEntryBlockedWhenCleanupFails`<br>`StartupCleanupUseCaseTest.keepsDueEntryWhoseCopyCannotBeDeleted`<br>`TrashEntryTest.isNotDueOneSecondBeforeDeadline`<br>`TrashTest.refusesDueEntry` |

Nicht automatisiert geprüft:

| AK | Nachweis |
| --- | --- |
| AK-01.2 | Systemtest S1 (Abbrechen beim Erstellen) |
| AK-04.7 | Systemtest S1 (Abbrechen beim Album löschen) |
| AK-05.3 | Systemtest S1 (Hinzufügen nur in einem geöffneten Album) |
| AK-SR01.5 | Systemtest S1 (Abbrechen beim endgültigen Löschen) |
| AK-N01.1 | Zustandsprüfung in den Fehlerfalltests, Abschnitt 4.1 |
| AK-N03.1 | Performance-Messung, Abschnitt 7 |
| AK-N04.1 | Usability-Selbsttest, Abschnitt 5 |
| AK-N05.1 | Zuordnung der Regelgruppen, Abschnitt 4.1 |

## 3. Test Design Lab

Gewählt sind die Namensregel für Alben (A-02 mit der Zeichenzählung nach A-04) und der Use Case UC-01 „Foto hinzufügen“ ([Block 5, S. 12 und 19](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=12)). Die neuen Tests stehen in `AlbumNameLabTest` und `AddPhotoDecisionTableTest` und sind mit `@Tag("normalfall")`, `@Tag("fehlerfall")` oder `@Tag("grenzfall")` gekennzeichnet.

### 3.1 Verfahren 1: Äquivalenzklassen und Grenzwerte für den Albumnamen

Regel A-02: Nach dem Entfernen äußerer Leerzeichen sind 1 bis 50 Zeichen erlaubt. Die Namen aktiver Alben sind unabhängig von Groß- und Kleinschreibung eindeutig. Ein Emoji zählt als ein Zeichen (A-04).

| Klasse | Typ | Beschreibung | Repräsentant | Erwartetes Verhalten | Testmethode |
| --- | --- | --- | --- | --- | --- |
| ÄK1 | gültig | 1–50 Zeichen nach dem Entfernen äußerer Leerzeichen, Name frei | „  Urlaub  2026 “ | Album „Urlaub  2026“ gespeichert, innere Leerzeichen bleiben | `AlbumNameLabTest.keepsInnerSpaces` |
| ÄK2 | ungültig | kein Zeichen | „“ | „Der Albumname darf nicht leer sein.“, kein Album gespeichert | `AlbumNameLabTest.rejectsNameWithoutCharacters` |
| ÄK3 | ungültig | nur Leerraum: Leerzeichen, Tabulator, Zeilenumbruch | „\t\n \t“ | wie ÄK2 | `AlbumNameLabTest.rejectsNameOfTabsAndLineBreaks`, `AlbumTest.rejectsBlankName` |
| ÄK4 | ungültig | mehr als 50 Zeichen | 51 × „👨‍👩‍👧“ | „Der Albumname darf höchstens 50 Zeichen lang sein.“, kein Album gespeichert | `AlbumNameLabTest.rejectsFiftyOneComposedEmoji`, `AlbumTest.rejectsFiftyOneCharacters` |
| ÄK5 | ungültig | Name eines aktiven Albums in anderer Groß- und Kleinschreibung, auch bei Umlauten | „äPFEL UND öL“ neben „Äpfel und Öl“ | „Dieser Albumname ist bereits vergeben.“, Alben unverändert | `AlbumNameLabTest.rejectsNameDifferingOnlyInUmlautCase`, `CreateAlbumUseCaseTest.rejectsTakenName` |
| ÄK6 | offen | nur unsichtbare Zeichen, die nicht als Leerraum gelten: geschütztes Leerzeichen (U+00A0), Nullbreite-Leerzeichen (U+200B) | „ “ | Derzeit als Name mit 1 Zeichen angenommen; das Album hat dann einen unsichtbaren Namen. | offene Frage 1 |
| ÄK7 | offen | gleich aussehender Name in anderer Unicode-Schreibweise: „é“ als ein Zeichen oder als „e“ mit Akzent, „ß“ und „SS“ | „Café“ neben „Café“ (zerlegt) | Derzeit als verschiedene Namen angenommen; zwei Alben sehen gleich aus. | offene Frage 2 |

Der Wert `null` ist keine Klasse für Nutzereingaben: Bricht der Nutzer den Dialog ab, ruft die Oberfläche den Use Case nicht auf (AK-01.2, Systemtest).

| Grenzwert | Eingabe | Erwartet | Testmethode |
| --- | --- | --- | --- |
| 0 Zeichen | „“ | abgelehnt (ÄK2) | `AlbumNameLabTest.rejectsNameWithoutCharacters` |
| 1 Zeichen | „A“ | angenommen | `AlbumTest.acceptsNameWithOneCharacter` |
| 50 Zeichen | 50 × „👨‍👩‍👧“; 49 × „a“ und „📷“ mit äußeren Leerzeichen | angenommen | `AlbumNameLabTest.acceptsFiftyComposedEmoji`, `AlbumTest.acceptsFiftyCharactersWithEmoji` |
| 51 Zeichen | 51 × „👨‍👩‍👧“; 51 × „a“ mit äußeren Leerzeichen | abgelehnt (ÄK4) | `AlbumNameLabTest.rejectsFiftyOneComposedEmoji`, `AlbumTest.rejectsFiftyOneCharacters` |

Offene fachliche Fragen (an die Auftraggeberin, festgehalten in [#27](https://github.com/felix-henry/photo-album-projekt/issues/27)):

1. Sollen Namen aus unsichtbaren Zeichen wie leere Namen abgelehnt werden (ÄK6)?
2. Sollen gleich aussehende Namen in anderer Unicode-Schreibweise als derselbe Name gelten (ÄK7)?

### 3.2 Verfahren 2: Entscheidungstabelle für UC-01 „Foto hinzufügen“

Schlägt eine Prüfung fehl, meldet das System nur den ersten zutreffenden Grund: Lesefehler → leere Datei → kein JPEG oder PNG → zu groß → Duplikat → Album voll (UC-01, Schritt 4). „–“ heißt, die Bedingung ist für die Regel ohne Bedeutung.

| Bedingung | R1 | R2 | R3 | R4 | R5 | R6 | R7 |
| --- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| B1 Datei lesbar | J | N | J | J | J | J | J |
| B2 Datei nicht leer | J | – | N | J | J | J | J |
| B3 JPEG oder PNG | J | – | – | N | J | J | J |
| B4 höchstens 20 MiB | J | – | – | – | N | J | J |
| B5 kein Duplikat im Album | J | – | – | – | – | N | J |
| B6 Album nicht voll | J | – | – | – | – | – | N |
| **Aktion** | Foto hinzugefügt | Lesefehler | leere Datei | kein gültiges Bild | zu groß | Duplikat | Album voll |

Bei jeder Ablehnung bleiben Album und App-Kopien unverändert (NFA-01). Über 20 MiB liest das System nur den Dateikopf. Eine beschädigte Datei mit gültigem JPEG- oder PNG-Kopf wird deshalb als zu groß gemeldet (ADR-002).

| Regel | AK | Testmethode |
| --- | --- | --- |
| R1 | AK-05.1 | `AddPhotoUseCaseTest.addsPhotoAndStoresCopy` |
| R2 | AK-05.4 | `Sha256ImageHasherTest.rejectsMissingFile` |
| R3 | AK-05.2 | `Sha256ImageHasherTest.rejectsEmptyFile`, `AddPhotoDecisionTableTest.reportsInvalidFileBeforeFullAlbum` (B6 = N) |
| R4 | AK-05.2 | `Sha256ImageHasherTest.rejectsTextFileWithJpgExtension`, `Sha256ImageHasherTest.rejectsLargeNonImageAsNotAnImage` (B4 = N) |
| R5 | AK-05.2 | `Sha256ImageHasherTest.rejectsImageOneByteTooLarge` |
| R6 | AK-06.1, AK-06.3 | `AddPhotoUseCaseTest.rejectsDuplicateWithoutStoringCopy`, `AddPhotoDecisionTableTest.reportsDuplicateBeforeFullAlbum` (B6 = N) |
| R7 | AK-07.2 | `AddPhotoUseCaseTest.rejectsPhotoForFullAlbum` |

## 4. Unit- und Integrationstests

### 4.1 Unit-Tests für Domäne und Use Cases

Domäne und Use Cases haben 164 Unit-Tests (55 in `domain`, 109 in `application`). Sie laufen gegen Fakes, ohne Oberfläche, ohne echte Dateien und mit fester Uhr statt Wartezeit (NFA-05). Jeder Fehlerfalltest prüft neben der Meldung auch, dass Album, Papierkorb und App-Kopien unverändert bleiben (NFA-01, AK-N01.1).

Die folgenden Tests decken die fünf Regelgruppen aus NFA-05 ab und sind mit `@Tag` als Normal-, Fehler- oder Grenzfall gekennzeichnet. Dazu kommen die acht Lab-Tests aus Abschnitt 3.

| Regelgruppe | Testmethode | Fall | Geprüfter Zustand |
| --- | --- | --- | --- |
| Albumnamen | `CreateAlbumUseCaseTest.savesValidAlbum` | Normalfall | Album ohne äußere Leerzeichen gespeichert |
| Textlängen | `PhotoTest.acceptsTitleOfMaxLength` | Grenzfall (100 Zeichen) | Titel gesetzt, Beschreibung unverändert |
| Textlängen | `EditPhotoTextsUseCaseTest.rejectsTooLongTitle` | Fehlerfall (101 Zeichen) | gespeicherter Titel unverändert |
| Fotoanzahl | `AlbumPhotoTest.acceptsHundredthPhoto` | Grenzfall (100. Foto) | Album enthält 100 Fotos |
| Fotoanzahl | `AddPhotoUseCaseTest.rejectsPhotoForFullAlbum` | Grenzfall (101. Foto) | Album behält 100 Fotos, keine App-Kopie |
| Duplikate | `AddPhotoUseCaseTest.rejectsDuplicateWithoutStoringCopy` | Fehlerfall | Album behält 1 Foto, keine App-Kopie |
| Papierkorb | `RemovePhotoUseCaseTest.movesPhotoToTrash` | Normalfall | Foto im Papierkorb mit Ursprungsalbum und Zeitpunkt |
| Papierkorb | `TrashEntryTest.isNotDueOneSecondBeforeDeadline` | Grenzfall (1 s vor Fristablauf, AK-SR01.6) | Eintrag nicht fällig |
| Papierkorb | `TrashEntryTest.isDueAfterExactlyThirtyTimesTwentyFourHours` | Grenzfall (genau bei Fristablauf, AK-SR01.3) | Eintrag fällig |
| Papierkorb | `RestoreTrashEntryUseCaseTest.reportsNameConflictForTile` | Fehlerfall (Namenskonflikt, AK-04.6) | Kachel bleibt im Papierkorb, aktives Album unverändert |
| Papierkorb | `DeletePermanentlyUseCaseTest.reportsPartialSuccessForTile` | Fehlerfall (Teilerfolg, AK-SR01.4) | Kachel behält das nicht gelöschte Foto und seine App-Kopie |

Die Konflikte beim Wiederherstellen eines Einzelfotos (AK-09.5) prüfen `RestoreTrashEntryUseCaseTest.reportsDuplicateInOrigin` und `RestoreTrashEntryUseCaseTest.reportsFullOrigin`.

**Aufbau der Tests (Arrange–Act–Assert).** Methodennamen beschreiben Verhalten und Bedingung, zum Beispiel `rejectsPhotoForFullAlbum`. `@DisplayName` beginnt mit der AK-ID oder, wo kein AK passt, mit der Regel (A-xx, ADR-xxx, NFA-xx). Jeder Test bereitet nur die nötigen Daten vor, führt genau eine Aktion aus und prüft danach Ergebnis und Zustand; die drei Teile sind durch Leerzeilen getrennt ([Block 5, S. 10](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=10)). 35 Tests prüfen technische Fehlerwege ohne eigenes AK, etwa unbekannte IDs, nicht lesbare Dateien oder Albumdateien früherer Versionen. Ihr `@DisplayName` beschreibt das Verhalten ohne ID.
