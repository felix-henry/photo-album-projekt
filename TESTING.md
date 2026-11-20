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

Jeder automatisierte Test mit AK-Bezug nennt seine AK-ID am Anfang von `@DisplayName`. Die Tabelle ist daraus abgeleitet; Tests ohne eigenes AK beschreibt Abschnitt 4.1. „Szenario aus dem AK“ heißt, dass der Test Ausgangslage, Aktion und Ergebnis des Akzeptanzkriteriums direkt umsetzt. Die Tests aus dem Test Design Lab (Abschnitt 3) sind gesondert aufgeführt.

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
| AK-04.3 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.restoresTileAsAlbum`<br>`PersistenceIntegrationTest.deletedAlbumIsRestoredAfterRestart` |
| AK-04.4 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.restoresFullTileIntoNewAlbum` |
| AK-04.5 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.removesCreatedAlbumWhenTrashSaveFails` |
| AK-04.6 | Szenario aus dem AK | `RestoreTrashEntryUseCaseTest.reportsNameConflictForTile` |
| AK-04.8 | Szenario aus dem AK | `ListTrashUseCaseTest.showsTileAndSinglePhoto` |
| AK-05.1 | Szenario aus dem AK | `AddPhotoUseCaseTest.addsPhotoAndStoresCopy`<br>`AlbumPhotoTest.addsPhotoToAlbumWithSpace`<br>`FilePhotoStorageTest.storeKeepsOriginal`<br>`Sha256ImageHasherTest.acceptsJpeg`<br>`Sha256ImageHasherTest.acceptsPngWithJpgExtension`<br>`Sha256ImageHasherTest.acceptsImageOfExactlyMaxSize` |
| AK-05.2 | Äquivalenzklassen | `AddPhotoUseCaseTest.rejectsInvalidFile`<br>`Sha256ImageHasherTest.rejectsImageOneByteTooLarge`<br>`Sha256ImageHasherTest.rejectsEmptyFile`<br>`Sha256ImageHasherTest.rejectsTextFileWithJpgExtension`<br>`Sha256ImageHasherTest.rejectsDamagedPng` |
| AK-05.4 | Szenario aus dem AK | `AddPhotoUseCaseTest.rejectsInvalidFile`<br>`Sha256ImageHasherTest.rejectsMissingFile`<br>`FileInputSecurityTest.rejectsDirectoryWithImageExtension` |
| AK-05.5 | Szenario aus dem AK | `AddPhotoUseCaseTest.rollsBackCopyWhenAlbumCannotBeSaved` |
| AK-06.1 | Äquivalenzklassen | `AddPhotoUseCaseTest.rejectsDuplicateWithoutStoringCopy`<br>`AlbumPhotoTest.rejectsDuplicateContent`<br>`Sha256ImageHasherTest.sameContentGivesSameHash`<br>`PersistenceIntegrationTest.duplicateIsDetectedAfterRestart` |
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
| AK-N03.1 | Messung, nur auf Abruf (Abschnitt 7) | `OpenAlbumPerformanceTest.opensAlbumWithHundredPhotosWithinOneSecond` |
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

Offene fachliche Fragen (an die Auftraggeberin, festgehalten in [#45](https://github.com/felix-henry/photo-album-projekt/issues/45)):

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

**Aufbau der Tests (Arrange–Act–Assert).** Methodennamen beschreiben Verhalten und Bedingung, zum Beispiel `rejectsPhotoForFullAlbum`. `@DisplayName` beginnt mit der AK-ID oder, wo kein AK passt, mit der Regel (A-xx, ADR-xxx, NFA-xx). Jeder Test bereitet nur die nötigen Daten vor, führt genau eine Aktion aus und prüft danach Ergebnis und Zustand; die drei Teile sind durch Leerzeilen getrennt ([Block 5, S. 10](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=10)). 35 Tests ohne eigenes AK prüfen technische Abläufe, vor allem Fehlerwege wie unbekannte IDs, nicht lesbare Dateien oder Albumdateien früherer Versionen. Ihr `@DisplayName` beschreibt das Verhalten ohne ID.

### 4.2 Integrationstests

`PersistenceIntegrationTest` verbindet die Use Cases mit den echten Dateiadaptern (`FileAlbumRepository`, `FileTrashRepository`, `FilePhotoStorage`, `Sha256ImageHasher`). Jeder Test nutzt einen eigenen Datenordner über `@TempDir`. Ein Neustart heißt: Neue Adapter-Objekte lesen die Daten von der Platte ([Block 5, S. 21](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=21)).

| Testmethode | Fachliche Frage | AK |
| --- | --- | --- |
| `duplicateIsDetectedAfterRestart` | Erkennt das System ein bytegleiches Bild mit anderem Namen und anderer Endung auch nach einem Neustart als Duplikat? Geprüft werden der gespeicherte Hash (Serialisierung), dass das vorhandene Foto mit Titel und Beschreibung unverändert bleibt und dass keine zweite App-Kopie entsteht. | AK-06.1, NFA-02 |
| `deletedAlbumIsRestoredAfterRestart` | Lässt sich ein gelöschtes Album nach einem Neustart mit Name, Foto und lesbarer App-Kopie wiederherstellen? Geprüft werden das Speichern des Papierkorbs mit Ursprungsangaben und die Pfade der App-Kopien. | AK-04.3, NFA-02 |

### 4.3 UI-Adapter als zweite Integration

Geprüft, aber nicht als Integrationstest genutzt. Die Fenster (`AlbumOverviewWindow`, `AlbumWindow`, `TrashWindow`) rufen die Use Cases direkt auf und enthalten außer Anzeige und Rückfragen keine eigene Logik. Ohne echtes Fenster prüfbar ist die Logik im UI-Adapter nur in `ImageBuffer`, `ImageDecoder` und in `PhotoFileChooser` (Startordner und Zurücksetzen des Filters); alle drei haben Unit-Tests (AK-N03.2, AK-08.3, #42). Für die Fenster bräuchte ein Test einen Bildschirm, den die CI nicht hat. Sie werden deshalb im Systemtest (Abschnitt 5) geprüft. Die beiden Integrationstests in 4.2 prüfen Persistenz, Serialisierung und Pfade, wie die Folie es verlangt.

## 5. Systemtests und Usability

### 5.1 Manuelle Systemtests

Beide Systemtests prüfen die gebaute JAR (`photo-album-0.1.0.jar`, Stand `block-5`) über die Oberfläche. Durchgeführt am 18.11.2026 auf Windows 11 mit Java 21 (Temurin 21.0.13+9), Start mit leerem Datenordner (`java -Duser.home=<Testordner> -jar target/photo-album-0.1.0.jar`). Testbilder: `strand.jpg` (JPEG) und `berg.png` (PNG) mit je 800 × 600 Pixeln, dazu die Textdatei `notiz.jpg`.

**S1: Album anlegen → Foto hinzufügen → Neustart → Löschen und Wiederherstellen**

| Schritt | Aktion | Erwartet | Ergebnis |
| --- | --- | --- | --- |
| 1 | App starten | Übersicht leer, „Noch keine Alben vorhanden.“; in der Übersicht gibt es keine Funktion „Foto hinzufügen“ (AK-02.2, AK-05.3) | bestanden |
| 2 | „Album erstellen“, Namen eingeben, „Abbrechen“ | kein Album angelegt (AK-01.2) | bestanden |
| 3 | „Album erstellen“, „Sommer“, Enter | „Sommer“ steht in der Übersicht (AK-01.1) | bestanden |
| 4 | Album öffnen, „Foto hinzufügen“, `strand.jpg` | „Album „Sommer“ – 1 Foto“, Bild sichtbar (AK-05.1, AK-08.1) | bestanden |
| 5 | „Texte bearbeiten“: Titel „Strandtag“, Beschreibung „Ostsee 2026“, „Speichern“ | Texte unter dem Bild sichtbar (AK-10.1) | bestanden |
| 6 | `berg.png` hinzufügen, mit „Vorheriges Foto“ und „Nächstes Foto“ blättern | „2 Fotos“, „Foto 2 von 2“ (AK-05.1) | bestanden |
| 7 | Beide Fenster schließen, App neu starten, Album öffnen | 2 Fotos, Titel und Beschreibung unverändert (NFA-02, AK-N02.1) | bestanden |
| 8 | „Album löschen“, Rückfrage mit „Nein“ beantworten | Album bleibt in der Übersicht (AK-04.7) | bestanden |
| 9 | „Album löschen“, „Ja“, „Papierkorb“ | Eintrag „Album „Sommer“ · 2 Fotos · … · noch 30 Tage“ (AK-04.1, AK-04.8) | bestanden |
| 10 | Eintrag wählen, „Endgültig löschen“, „Nein“ | Eintrag bleibt (AK-SR01.5) | bestanden |
| 11 | „Wiederherstellen“, zurück zur Übersicht, Album öffnen | „Wiederhergestellt in „Sommer“.“, Album mit 2 Fotos und Texten (AK-04.3) | bestanden |

**S2: Ablehnungen beim Hinzufügen**

| Schritt | Aktion | Erwartet | Ergebnis |
| --- | --- | --- | --- |
| 1 | Im Album „Sommer“ `notiz.jpg` (Text mit Bildendung) hinzufügen | „Die Datei enthält kein gültiges JPEG- oder PNG-Bild.“, weiterhin 2 Fotos (AK-05.2) | bestanden |
| 2 | `strand.jpg` erneut hinzufügen | „Dieses Bild befindet sich schon in diesem Album“, weiterhin 2 Fotos (AK-06.1) | bestanden |

Beobachtung: Einmal kam ein Foto nicht im Album an, nachdem in der Dateiauswahl sofort nach dem Öffnen ein ganzer Pfad eingetippt und mit Enter bestätigt wurde. Beim erneuten Öffnen stand die Datei vorausgewählt im richtigen Ordner, und „Öffnen“ fügte sie hinzu. Bei langsamer Eingabe ließ sich der Fall nicht wiederholen ([#44](https://github.com/felix-henry/photo-album-projekt/issues/44)).

**Nachstellung von #44** (19.11.2026, JAR `1.0.0`, Windows 11): Viermal wurde ein ganzer Pfad per Tastatur eingetippt und mit Enter bestätigt, jeweils mit einem anderen Bild. Getestet wurden der Ablauf aus S1 (Dateiauswahl öffnen, 1 s warten, ins Feld klicken, tippen), das Tippen sofort nach dem Klick auf „Foto hinzufügen“ und das Tippen direkt nach einem Neustart, wenn die Dateiauswahl erst entsteht. Jedes Foto kam an. Laut Sitzungsprotokoll kam der Pfad in S1 nicht über die Tastatur, sondern über das Einfügen aus der Zwischenablage durch das Testwerkzeug. Bei der Nachstellung fügte dasselbe Werkzeug einmal einen veralteten Inhalt der Zwischenablage statt des Pfads ein. Die Ursache lag deshalb wahrscheinlich im Testwerkzeug, nicht in der App; ein Fehler der App ließ sich nicht nachweisen.

### 5.2 Usability-Test (NFA-04)

Nach Teamabsprache testen die fünf Teammitglieder selbst (NFA-04, AK-N04.1). Jede Person startet die App mit leerem Datenordner und löst ohne Hilfe zwei Aufgaben ([Block 5, S. 23](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=23)):

1. „Lege ein Album ‚Sommer‘ an.“ Gemessen ab dem Klick auf „Album erstellen“ bis „Sommer“ in der Übersicht steht.
2. „Füge dem Album ‚Sommer‘ das Foto vom Desktop hinzu.“ Gemessen ab dem Klick auf „Foto hinzufügen“ bis das Foto im Album angezeigt wird. Zusätzlich notiert: die Zeit vom Beginn der Aufgabe bis zu diesem Klick.

Bestanden ist NFA-04, wenn alle Personen beide Schritte in jeweils höchstens 60 Sekunden schaffen. Notiert werden Beobachtungen statt Meinungen, zum Beispiel „suchte 20 s den Knopf“ statt „unübersichtlich“. Einschränkung: Die Teammitglieder kennen die App aus den Reviews. Wie neue Nutzer bedienen sie sie also nicht; Probleme beim ersten Zurechtfinden zeigen sich deshalb eher schwächer.

| Teammitglied | Album erstellen | Bis „Foto hinzufügen“ | Foto hinzufügen | NFA-04 | Beobachtungen |
| --- | ---: | ---: | ---: | --- | --- |
| Pit | 7 s | 8 s | 15 s | bestanden | Keine Beobachtungen notiert. |
| Moritz | 3 s | 2 s | 6 s | bestanden | Kennt die App gut aus den Block-4-Tests. Öffnete „Sommer“ per Doppelklick statt über „Öffnen“. Kein Zögern, keine Fehlklicks, keine Meldungen. |
| Alex | 4 s | 4 s | 9 s | bestanden | Keine Auffälligkeiten notiert. |
| Alanur | 7 s | 3,5 s | 37 s | bestanden | Die Dateiauswahl öffnete auf „Macintosh HD“. Der Weg über „Benutzer“, den eigenen Ordner und „Schreibtisch“ kostete den Großteil der 37 s. Vermutlich durch den Testaufbau verstärkt: Mit geändertem `user.home` startet die Dateiauswahl nicht im eigenen Benutzerordner. Keine Fehlklicks, keine Meldungen. |
| Felix | 1,62 s | 3,14 s | 5,16 s | bestanden | Nach dem Klick auf „Öffnen“ erschien die Albumansicht mit kurzer Verzögerung. Keine Fehlklicks oder Meldungen aufgefallen. |

Alle fünf schaffen beide Schritte deutlich unter 60 Sekunden; NFA-04 ist erfüllt. Aus den Beobachtungen entstehen drei Usability-Issues, nach Auswirkung geordnet: Aufgabe nicht lösbar vor verzögert vor kosmetisch.

| Issue | Beobachtung | Auswirkung | Stand |
| --- | --- | --- | --- |
| [#44](https://github.com/felix-henry/photo-album-projekt/issues/44) | Foto kommt nach schneller Pfadeingabe ohne Meldung nicht an (Systemtest S1, Abschnitt 5.1) | Aufgabe im ersten Versuch nicht gelöst | untersucht: mit Tastatureingabe nicht reproduzierbar, wahrscheinlich Ursache im Testwerkzeug (Abschnitt 5.1) |
| [#42](https://github.com/felix-henry/photo-album-projekt/issues/42) | Dateiauswahl startet weit entfernt von den Fotos (Alanur) und beginnt in jedem Album neu | verzögert (37 s) | behoben in `block-5`, auf macOS mit Einschränkung (unten) |
| [#43](https://github.com/felix-henry/photo-album-projekt/issues/43) | Albumansicht erscheint nach „Öffnen“ verzögert (Felix) | kurz verzögert | behoben in `block-5` |

**Behebung von #42 und #43.** Alle Albumansichten teilen sich jetzt eine Dateiauswahl (`PhotoFileChooser`). Sie startet beim ersten Mal im Ordner „Pictures“ des Benutzerordners, falls es ihn gibt, und merkt sich danach den zuletzt benutzten Ordner über alle Alben hinweg. Erzeugt wird sie erst beim ersten „Foto hinzufügen“. Vorher erzeugte jede Albumansicht beim Öffnen eine eigene. Gemessen auf Pits Laptop mit einem Album mit einem Foto, je drei Programmstarts mit sechsmal Öffnen:

| | Erstes Öffnen | Weiteres Öffnen |
| --- | ---: | ---: |
| vorher | 550 bis 583 ms | 214 bis 258 ms |
| nachher | 139 bis 161 ms | 56 bis 63 ms |

In der gebauten JAR unter Windows und macOS geprüft: Die Dateiauswahl öffnete in „Pictures“; nach dem Hinzufügen aus „Pictures/Urlaub“ startete sie im zweiten Album wieder in „Urlaub“. Ohne „Pictures“ startete sie im Benutzerordner (macOS, Alanur).

Einschränkung auf macOS: Die Swing-Dateiauswahl hat dort keine Seitenleiste. Von „Pictures“ zum Schreibtisch geht es nur über die Pfadliste oben; vorher startete sie bei unverändertem `user.home` im Benutzerordner selbst. Für die Testaufgabe „Foto vom Desktop“ ist der Weg auf dem Mac also nicht kürzer geworden. Alanurs 37 s kamen nach ihrer Einschätzung vor allem durch den Testaufbau mit geändertem `user.home` zustande. Mit diesem Startbefehl gibt es im Testordner kein „Pictures“, und die Dateiauswahl startet im Testordner. Ein erneuter Usability-Test mit diesem Aufbau würde die Behebung also nicht zeigen.

Bei der Nachstellung von #44 fiel auf, dass ein eingetipptes Suchmuster wie `*.jpg` als Filter stehen blieb, durch die gemeinsame Dateiauswahl nun auch im nächsten Album; ein Ordner mit PNG-Bildern wirkte dann leer. Die Dateiauswahl setzt den Filter jetzt vor jedem Öffnen auf „Alle Dateien“ zurück, in der JAR unter Windows und macOS nachgeprüft. `PhotoFileChooserTest` prüft die Wahl des Startordners und das Zurücksetzen des Filters.

## 6. Security-Tests für Dateieingaben

Jede Datei, die der Nutzer auswählt, gilt als nicht vertrauenswürdig. Die App prüft sie nach ihrem Inhalt, nicht nach der Endung (A-03), und legt die App-Kopie immer unter der Foto-ID ab (A-05). Fälle nach [Block 5, S. 23](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=23):

| Fall | Eingabe | Erwartetes Verhalten | Testmethode |
| --- | --- | --- | --- |
| Falsche Endung, kein Bild | Textdatei `notiz.jpg` | „kein gültiges JPEG- oder PNG-Bild“, nichts gespeichert | `Sha256ImageHasherTest.rejectsTextFileWithJpgExtension`, Systemtest S2 |
| Falsche Endung, gültiges Bild | PNG-Inhalt mit Endung `.jpg` | angenommen, weil der Inhalt zählt | `Sha256ImageHasherTest.acceptsPngWithJpgExtension` |
| Nicht zugelassenes Format | GIF | „kein gültiges JPEG- oder PNG-Bild“ | `Sha256ImageHasherTest.rejectsGif` |
| Leere Datei | 0 Byte | „Die Bilddatei ist leer.“ | `Sha256ImageHasherTest.rejectsEmptyFile` |
| Zu große Datei | gültiges Bild mit 20 MiB + 1 Byte | „höchstens 20 MiB“; die Datei wird nicht vollständig gelesen | `Sha256ImageHasherTest.rejectsImageOneByteTooLarge` |
| Große Datei ohne Bildinhalt | über 20 MiB, kein JPEG- oder PNG-Kopf | „kein gültiges JPEG- oder PNG-Bild“, nur der Dateikopf wird gelesen | `Sha256ImageHasherTest.rejectsLargeNonImageAsNotAnImage` |
| Dekompressionsbombe | kleine Datei, die über 100 Megapixel angibt | abgelehnt, bevor das Bild entschlüsselt wird | `Sha256ImageHasherTest.rejectsImageWithTooManyPixels` |
| Beschädigtes Bild | PNG-Signatur mit unbrauchbaren Bilddaten | „kein gültiges JPEG- oder PNG-Bild“ | `Sha256ImageHasherTest.rejectsDamagedPng` |
| Fehlende Datei | Pfad ohne Datei | „Die Datei konnte nicht gelesen werden.“ | `Sha256ImageHasherTest.rejectsMissingFile` |
| Ordner statt Datei | Ordner `ordner.jpg` | „Die Datei konnte nicht gelesen werden.“, keine App-Kopie | `FileInputSecurityTest.rejectsDirectoryWithImageExtension` |
| Pfad mit `../` | `unterordner/../strand.png` | App-Kopie liegt nur unter `photos/<Foto-ID>`, außerhalb entsteht keine Datei | `FileInputSecurityTest.storesCopyOfTraversalPathOnlyUnderPhotoId` |
| Sonderzeichen im Dateinamen | `Foto; 😀 ä & $(rm).png` | angenommen, der Dateiname geht nicht in den Namen der App-Kopie ein | `FileInputSecurityTest.storesCopyOfFileWithSpecialNameUnderPhotoId` |

Eine fehlende Leseberechtigung (AK-05.4) hat keinen eigenen Test, weil sie sich nicht auf allen Systemen gleich herstellen lässt; unter Windows ginge das nur über Zugriffslisten. `Sha256ImageHasher` erkennt sie in derselben Prüfung wie eine fehlende Datei oder einen Ordner (`Files.isReadable`), und auch ein Fehler beim Lesen führt zu „Die Datei konnte nicht gelesen werden.“

Steuerzeichen in Dateinamen erlaubt Windows nicht. Da der Name der App-Kopie nie vom Dateinamen abhängt, deckt der Sonderzeichen-Test diesen Fall mit ab. Bildmetadaten wertet die App nicht aus; sie kopiert die Datei unverändert. Eine Angriffsfläche über Metadaten entstünde erst mit der nur entworfenen KI-Funktion (KI-01 bis KI-03 in REQUIREMENTS.md).

## 7. Performance-Messung (NFA-03)

NFA-03 verlangt: Ab dem Öffnen eines Albums mit 100 Fotos sind Fotoanzahl und erstes Bild in höchstens 1 Sekunde sichtbar, und der Bildpuffer bleibt innerhalb von 5 Bildern (AK-N03.1, A-06). Gemessen wird auf allen fünf Team-Laptops mit demselben Anwendungsstand und demselben Datensatz.

### 7.1 Messaufbau

`OpenAlbumPerformanceTest` (Tag `performance`) läuft nicht im normalen Build und nicht in der CI, sondern nur auf Abruf:

```powershell
.\mvnw.cmd test "-Dtest=OpenAlbumPerformanceTest" "-DexcludedGroups="
```

Der Test erzeugt beim ersten Lauf den Datensatz unter `target/performance-dataset` und legt über die echten Use Cases und Dateiadapter ein Album mit allen 100 Fotos in einem temporären Datenordner an. Dann misst er elfmal, was `AlbumWindow` beim Öffnen ausführt, bevor Fotoanzahl und erstes Bild sichtbar sind: Album von der Platte laden, erstes Bild über den `ImageBuffer` laden und auf Anzeigegröße (640 × 420) entschlüsseln. Jede Messung beginnt mit neuen Adaptern und leerem Puffer. Die erste Messung entspricht dem ersten Öffnen nach dem Start, die zehn weiteren dem erneuten Öffnen. Nach jeder Messung prüft der Test, dass der Puffer mit den nachgeladenen Nachbarn höchstens 5 Bilder hält. Das Zeichnen durch Swing ist nicht enthalten.

**Datensatz.** Aus festen Startwerten erzeugt; ob er auf einem Laptop übereinstimmt, zeigt die Prüfsumme in der Ausgabe:

| Dateien | Format | Auflösung | Dateigröße |
| --- | --- | --- | --- |
| `foto-001.jpg` bis `foto-090.jpg` | JPEG | 4000 × 3000 (12 MP) | 0,8 bis 3,2 MiB |
| `foto-091.png` bis `foto-100.png` | PNG | 1920 × 1080 | 0,8 bis 3,2 MiB |
| zusammen 100 Dateien | | | 113,1 MiB, Prüfsumme `e5456c0b45364a47` |

### 7.2 Ergebnisse

| Teammitglied | Betriebssystem | Prozessor | RAM | Java | Erstes Öffnen | Wiederholt (Median / Max.) | Ergebnis |
| --- | --- | --- | --- | --- | --- | --- | --- |
| Pit | Windows 11 Home | Intel Core 7 150U (12 Threads) | 15,7 GiB | 21.0.13 | 91 ms | 60 / 66 ms | bestanden |
| Moritz | Windows 11 Pro | Intel Core i5-8365U (8 Threads) | 15,8 GiB | 21.0.13 | 148 ms | 103 / 157 ms | bestanden |
| Alex | Windows 11 Pro | Intel Core i5-1145G7 (8 Threads) | 15,7 GiB | 21.0.13 | 316 ms | 203 / 224 ms | bestanden |
| Alanur | macOS 27.2.1 | Apple M1 Pro (10 Threads) | 32,0 GiB | 21.0.12.1 | 327 ms | 52 / 52 ms | bestanden |
| Felix | Windows 11 Pro | AMD Ryzen 5 PRO 230 (12 Threads) | 15,3 GiB | 21.0.12.1 | 164 ms | 87 / 90 ms | bestanden |

NFA-03 ist auf allen fünf Laptops erfüllt. Der langsamste Wert liegt bei 327 ms (erstes Öffnen bei Alanur), also bei rund einem Drittel der Grenze. Einschließlich des Zeichnens durch Swing waren bei Felix in einem zusätzlichen Durchlauf mit 100 Fotos Fotoanzahl, erstes Bild und der aktive Knopf „Nächstes Foto“ nach 563 ms sichtbar.

## 8. Coverage mit JaCoCo

JaCoCo läuft bei jedem `./mvnw verify` mit. Der Report liegt danach unter `target/site/jacoco/index.html`; die CI lädt ihn als Artefakt `jacoco-report` hoch. Stand `block-5` mit 231 Tests (ohne die Performance-Messung):

| Schicht | Anweisungen | Zweige | Zeilen |
| --- | ---: | ---: | ---: |
| `domain` | 96 % | 92 % | 98 % |
| `application` | 96 % | 91 % | 96 % |
| `infrastructure` | 91 % | 76 % | 90 % |
| `ui` | 9 % | 11 % | 9 % |
| `Main` | 0 % | – | 0 % |
| gesamt | 57 % | 59 % | 62 % |

**Interpretation je Schicht.** Domäne und Use Cases tragen alle Geschäftsregeln und sind fast vollständig abgedeckt, wie NFA-05 es verlangt. Die fehlenden Zweige dort sind vor allem Abwehrprüfungen für Programmierfehler. In der Infrastruktur fehlen Zweige beim Lesen beschädigter Dateien: Die Prüfung auf fehlende Felder in `TrashEntryFile` ist nur für einen Teil der Felder getestet. Die Oberfläche ist kaum abgedeckt, weil ihre Fenster einen Bildschirm brauchen, den die CI nicht hat. Abgedeckt sind dort nur `ImageBuffer`, `ImageDecoder` und Startordner und Filter in `PhotoFileChooser`; die Fenster prüfen die Systemtests in Abschnitt 5.1. Die Coverage zeigt nur, welcher Code bei den Tests lief, nicht, ob die Assertions das richtige Verhalten prüfen. Deshalb sind die Tests den AK zugeordnet (Abschnitt 2), und die Fehlerfalltests prüfen den Zustand.

**Drei Klassen mit der niedrigsten Branch-Coverage:**

| Klasse | Branch-Coverage | Grund |
| --- | ---: | --- |
| `ui.AlbumWindow` | 0 % (0 von 32) | Swing-Fenster, nur im Systemtest geprüft |
| `ui.TrashWindow` | 0 % (0 von 28) | Swing-Fenster, nur im Systemtest geprüft |
| `ui.AlbumOverviewWindow` | 0 % (0 von 26) | Swing-Fenster, nur im Systemtest geprüft |

Außerhalb der Oberfläche sind es `infrastructure.TrashEntryFile` (60 %, 6 von 10), `infrastructure.FileTrashRepository` (70 %, 7 von 10) und `application.CreateAlbumUseCase` (75 %, 3 von 4). Diese Klassen sind Kandidaten für Regressionstests in Block 6.

## 9. Evaluation Set für die KI-Funktion

Die KI-Funktion ist nur entworfen (Won't). Für ihren Titelvorschlag liegt das Evaluation Set unter [eval/describe-photo/cases.md](eval/describe-photo/cases.md): fünf Fälle (normal, mehrdeutig, sensibel, adversarial, fehlerhaft) mit je drei prüfbaren Kriterien, dazu die Sicherheitsanforderungen KI-01 bis KI-03 und die begründete Freigabe-Pass-Rate.
