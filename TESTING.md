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
