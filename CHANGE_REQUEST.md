# Change Request CR-1 – Impact Analysis

Stand: 22.11.2026, `block-6`, vor jeder CR-Codeänderung. Grundlage sind [PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6) und [Block 6, S. 11–12](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=11). Sammel-Issue: [#28](https://github.com/felix-henry/photo-album-projekt/issues/28).

Vorschläge des Bearbeiters sind als **Vorschlag** gekennzeichnet. Sie gelten erst, wenn das Team sie in #28 bestätigt hat. Offene Fragen stehen in [Abschnitt 9](#9-offene-fragen).

## 1. Der Change Request

**CR-1 „Fotos in mehreren Alben und Tags“** · Auftraggeberin · **Priorität hoch** · Ziel: Release `v1.1.0` bis Block 7.

| ID | Änderungsanforderung |
| --- | --- |
| CR-1.1 | Ein Foto muss gleichzeitig in mehreren Alben enthalten sein können. Die Datei wird nur einmal gespeichert. Titel und Beschreibung gehören zum Foto, nicht zum Album. |
| CR-1.2 | Entfernen aus einem Album darf das Foto in anderen Alben nicht verändern. Ist es in keinem Album mehr enthalten, muss es nach Bestätigung endgültig gelöscht werden können. |
| CR-1.3 | Ein Nutzer muss einem Foto bis zu fünf Tags zuweisen und diese entfernen können. Jeder Tag hat 1–20 Zeichen; Tags sind je Foto ohne Beachtung der Groß-/Kleinschreibung eindeutig. |
| CR-1.4 | Der Nutzer muss die Fotos eines Albums nach einem Tag filtern können. Die Albumansicht zeigt die Trefferzahl. |

**Unverändert laut CR:** höchstens 100 Fotos je Album; ein Foto zählt in jedem Album, in dem es enthalten ist. Die Duplikatregel gilt je Album (FA-06, A-01). Die übrigen AK bleiben gültig.

## 2. Mini-Impact-Analysis

| Frage | Antwort |
| --- | --- |
| Welche Anforderung widerspricht CR-1? | **A-05:** „Jeder aktive Fotoeintrag gehört genau einem Album.“ CR-1.1 verlangt das Gegenteil. Daraus folgt auch FA-09 („ein Foto aus *seinem* Album“). |
| Welche Klasse ändert sich am stärksten? | **`domain.Album`.** Es hält heute die Fotoliste mit vollständigen `Photo`-Objekten samt Titel und Beschreibung. Nach CR-1.1 verweist ein Album nur noch auf Fotos aus einem gemeinsamen Fotobestand. Davon hängen `FileAlbumRepository` (Albumdatei) und alle Use Cases ab, die Fotos über das Album finden. |
| Welcher vorhandene Test wird rot? | **`StartupCleanupUseCaseTest.removesOnlyDuplicateFromTile`.** Er sichert die Regel aus A-11, dass eine Foto-ID nicht zugleich in einem Album und im Papierkorb liegen darf, und nimmt das Foto aus der Kachel „Alt“, weil es auch in „Urlaub“ liegt. Nach CR-1.2 ist genau das erlaubt: Die Kachel muss das geteilte Foto behalten, damit sie als Einheit wiederhergestellt werden kann. |

## 3. Betroffene und neue Anforderungen

### 3.1 Geänderte Anforderungen und Annahmen

| ID | Heute | Änderung durch CR-1 |
| --- | --- | --- |
| A-05 | Jeder aktive Fotoeintrag gehört genau einem Album. | Ein Foto kann in mehreren Alben liegen; seine App-Kopie existiert einmal (CR-1.1). |
| FA-09 | Foto aus *seinem* Album in den Papierkorb verschieben. | Foto aus *einem* Album entfernen; die anderen Alben bleiben unverändert (CR-1.2). In den Papierkorb kommt es nur, wenn es danach in keinem Album mehr liegt (**Vorschlag**, Frage F-1). |
| FA-10, A-04 | Titel und Beschreibung je Foto; faktisch je Album, weil jedes Album eigene Fotoeinträge hat. | Texte gehören zum Foto und sind in allen Alben gleich (CR-1.1). |
| FA-04, A-07, A-09 | Ein gelöschtes Album wird mit allen Fotos eine Kachel und kommt als Einheit zurück. | Die Kachel enthält auch Fotos, die noch in anderen Alben liegen. Ihre App-Kopie darf nicht gelöscht werden, solange ein Album sie nutzt. Beim Wiederherstellen kommt das Foto als Verweis zurück. |
| A-08 | Bytegleiche Fotos aus verschiedenen Alben dürfen getrennt im Papierkorb liegen. | Bytegleiche Dateien sind nach CR-1.1 dasselbe Foto (**Vorschlag**, Frage F-2). Dasselbe Foto kann nur noch als Teil verschiedener Kacheln mehrfach im Papierkorb liegen. |
| A-10, A-12, SR-01 | Endgültiges Löschen entfernt Eintrag und App-Kopie. | Die App-Kopie wird nur gelöscht, wenn kein Album das Foto mehr enthält (CR-1.2). `DueEntryCleanup.deleteCopies` prüft das heute schon über `photosInAlbums`. |
| A-11 | Liegt dieselbe Foto-ID in einem Album und im Papierkorb, bleibt das Foto im Album und verlässt den Papierkorb. | Gilt nur noch für Einzelfoto-Einträge. Für Kacheln braucht die Bereinigung nach einem doppelten Schreibfehler eine neue Regel (Risiko R-2). |
| NFA-02 | Alle Daten außer fälligen Einträgen bleiben nach dem Neustart erhalten. | Gilt zusätzlich für Tags und Mehrfachzuordnungen. Daten aus `v1.0.0` müssen nach dem Update lesbar bleiben (Migration, Abschnitt 4.2). |
| NFA-05 | Unit-Tests für fünf Regelgruppen. | Zwei neue Regelgruppen: Tags und Mehrfachzuordnung. |
| KI-01 | Nur der Bildinhalt geht an die KI. | Bleibt; Tags gehören nicht zu den übergebenen Daten. Nur ein Satz in der Dokumentation. |

### 3.2 Geänderte Akzeptanzkriterien

| AK | Änderung |
| --- | --- |
| AK-09.1 | Die Ausgangslage „bytegleiche Kopie aus einem anderen Album im Papierkorb“ gibt es nach F-2 nicht mehr als eigenes Foto. Neu: Das Foto liegt noch in einem zweiten Album; nach dem Entfernen bleibt es dort unverändert, und es entsteht kein Papierkorbeintrag (AK-11.3). |
| AK-SR01.1, AK-SR01.4 | Ergänzung: Liegt das Foto noch in einem Album, bleibt seine App-Kopie erhalten. |
| AK-04.3, AK-09.3, AK-09.4 | Ergänzung: Ein wiederhergestelltes Foto behält Titel, Beschreibung und Tags des gemeinsamen Fotos. |
| AK-10.1 | Ergänzung: Die geänderten Texte sind in jedem Album sichtbar, das das Foto enthält. |
| AK-N02.1 | Ergänzung: Tags und Mehrfachzuordnungen sind nach dem Neustart vorhanden. |

Unverändert bleiben insbesondere AK-06.1 bis AK-06.3 und AK-07.1/AK-07.2, weil Duplikatregel und Höchstzahl je Album gelten.

### 3.3 Neue Anforderungen (**Vorschlag**)

| ID | Anforderung | CR |
| --- | --- | --- |
| FA-11 | Das System muss dem Nutzer ermöglichen, ein vorhandenes Foto in ein weiteres Album aufzunehmen, ohne die Bilddatei erneut zu speichern. Titel, Beschreibung und Tags bleiben dieselben. | CR-1.1 |
| FA-12 | Das System muss dem Nutzer ermöglichen, einem Foto bis zu fünf Tags mit je 1 bis 20 Zeichen zuzuweisen und sie wieder zu entfernen. Tags sind je Foto ohne Beachtung der Groß- und Kleinschreibung eindeutig. | CR-1.3 |
| FA-13 | Das System muss dem Nutzer ermöglichen, die Fotos des geöffneten Albums nach einem Tag zu filtern, und die Zahl der Treffer anzeigen. | CR-1.4 |
| A-13 | Ein importiertes Bild, das bytegleich zu einem Foto in einem anderen Album ist, wird mit diesem Foto verknüpft statt neu gespeichert (F-2). | CR-1.1 |
| A-14 | Tags werden wie Albumnamen ohne äußere Leerzeichen gespeichert und nach A-04 gezählt. Der Filter vergleicht ohne Beachtung der Groß- und Kleinschreibung (F-3). | CR-1.3, CR-1.4 |

Neue AK (Entwurf, IDs folgen den neuen FA):

| ID | Given | When | Then |
| --- | --- | --- | --- |
| AK-11.1 | Foto „Strand“ liegt in „Urlaub“. „Familie“ hat Platz und enthält kein bytegleiches Foto. | Der Nutzer nimmt „Strand“ in „Familie“ auf. | „Strand“ liegt in beiden Alben mit denselben Texten. Es gibt eine App-Kopie. |
| AK-11.2 | „Strand“ liegt in „Urlaub“ und „Familie“. | Der Nutzer ändert den Titel in „Familie“. | Der neue Titel erscheint auch in „Urlaub“. |
| AK-11.3 | „Strand“ liegt in „Urlaub“ und „Familie“. | Der Nutzer entfernt „Strand“ aus „Urlaub“. | „Familie“ ist unverändert, die App-Kopie bleibt, der Papierkorb bleibt unverändert (F-1). |
| AK-11.4 | „Strand“ liegt nur noch in „Familie“. | Der Nutzer entfernt es dort und bestätigt im Papierkorb das endgültige Löschen. | Eintrag und App-Kopie sind entfernt (SR-01). |
| AK-11.5 | „Familie“ enthält 100 Fotos. | Der Nutzer versucht, „Strand“ aufzunehmen. | Ablehnung mit Höchstzahl-Meldung (FA-07), beide Alben unverändert. |
| AK-12.1 | Ein Foto hat vier Tags. | Der Nutzer fügt „Meer“ hinzu. | Das Foto hat fünf Tags. |
| AK-12.2 | Ein Foto hat fünf Tags. | Der Nutzer versucht einen sechsten. | Ablehnung mit Meldung, Tags unverändert. |
| AK-12.3 | Ein Foto hat den Tag „Meer“. | Der Nutzer fügt „meer“ hinzu. | Ablehnung als doppelter Tag, Tags unverändert. |
| AK-12.4 | Ein Foto ohne Tags ist ausgewählt. | Der Nutzer speichert einen Tag mit 0, 1, 20 oder 21 Zeichen nach dem Entfernen äußerer Leerzeichen. | 1 und 20 Zeichen werden angenommen, 0 und 21 abgelehnt. |
| AK-12.5 | Ein Foto hat die Tags „Meer“ und „Sommer“. | Der Nutzer entfernt „Meer“. | Das Foto hat nur noch „Sommer“. |
| AK-13.1 | „Urlaub“ enthält fünf Fotos, zwei davon mit Tag „Meer“. | Der Nutzer filtert nach „meer“. | Die Ansicht zeigt die zwei Fotos und die Trefferzahl 2. |
| AK-13.2 | Kein Foto in „Urlaub“ hat den Tag „Berg“. | Der Nutzer filtert nach „Berg“. | Die Ansicht zeigt keine Fotos und die Trefferzahl 0. |

## 4. Architektur, Datenmodell und ADRs

### 4.1 Betroffene Teile je Schicht

| Schicht | Klasse | Änderung |
| --- | --- | --- |
| Domäne | `Album` | Hält statt `Photo`-Objekten die Foto-IDs in Reihenfolge. Duplikatprüfung je Album über die Hashes der verknüpften Fotos, Höchstzahl unverändert. |
| Domäne | `Photo` | Bekommt Tags mit den Regeln aus FA-12 (`addTag`, `removeTag`). |
| Domäne | neu, z. B. `PhotoCatalog` (**Vorschlag**) | Alle Fotos mit ID, Hash, Texten und Tags; findet ein Foto über den Hash (A-13) und sagt, ob es noch in einem Album liegt. |
| Domäne | `Trash`, `TrashEntry` | Einträge verweisen auf Foto-IDs; Kacheln dürfen geteilte Fotos enthalten. |
| Domäne | `Violation` | Neue Verstöße für Tags: zu viele, Länge, doppelt. |
| Anwendung | neuer Port `PhotoRepository` (**Vorschlag**) | Lädt und speichert den Fotobestand. |
| Anwendung | `AddPhotoUseCase` | Verknüpft ein bytegleiches vorhandenes Foto statt eine zweite Kopie anzulegen (A-13). Die Schreibreihenfolge bekommt einen dritten Schritt (Kopie → Fotobestand → Album). |
| Anwendung | `RemovePhotoUseCase`, `DeleteAlbumUseCase`, `RestoreTrashEntryUseCase`, `DeletePermanentlyUseCase`, `StartupCleanupUseCase`, `DueEntryCleanup` | Prüfen, ob ein Foto noch in einem anderen Album liegt; Regeln aus Abschnitt 3.1. |
| Anwendung | `EditPhotoTextsUseCase` | Speichert die Texte im Fotobestand statt in der Albumdatei. |
| Anwendung | neu: `AddPhotoToAlbumUseCase`, `EditPhotoTagsUseCase`, Filter in `OpenAlbumUseCase` | FA-11 bis FA-13. `PhotoSummary` bekommt die Tags, `AlbumDetails` die Trefferzahl. |
| Infrastruktur | `FileAlbumRepository`, `AlbumFile`, `PhotoFile`, `FileTrashRepository`, `TrashEntryFile` | Neues Dateiformat, Lesen der Dateien von `v1.0.0`. |
| Infrastruktur | neu: `FilePhotoRepository` | Schreibt `photos.json` atomar wie die übrigen Dateien (ADR-002). |
| UI | `AlbumWindow`, `PhotoTextsDialog`, `TrashWindow`, `UseCases`, `Main` | Aufnehmen in weiteres Album, Tags bearbeiten, Tag-Filter mit Trefferzahl; neue Use Cases verdrahten. |

Die Schichtung aus [ARCHITECTURE.md](ARCHITECTURE.md) bleibt erhalten: Regeln in der Domäne, Dateien hinter Ports.

### 4.2 Datenmodell (**Vorschlag**)

| Pfad | Heute | Nach CR-1 |
| --- | --- | --- |
| `photos.json` | – | neu: alle Fotos mit `id`, `contentHash`, `title`, `description`, `tags` |
| `albums/<AlbumId>.json` | `photos`: Liste vollständiger Fotos | `photoIds`: Liste von Foto-IDs in Reihenfolge |
| `trash.json` | Einträge mit vollständigen Fotos | Einträge mit Foto-IDs; Texte und Tags stehen im Fotobestand |
| `photos/<PhotoId>` | eine Kopie je Fotoeintrag | eine Kopie je Foto (CR-1.1) |

**Migration:** Liest die App eine Albumdatei mit `photos` statt `photoIds`, übernimmt sie die Fotos in den Fotobestand und schreibt beim nächsten Speichern das neue Format. Fotos mit gleichem Hash in verschiedenen Alben bleiben dabei getrennte Fotos mit ihren bisherigen Texten, weil die App nicht entscheiden kann, welche Texte gelten sollen. Der Datenordner von `v1.0.0` bleibt so ohne Datenverlust nutzbar (NFA-02).

### 4.3 ADRs

| ADR | Auswirkung |
| --- | --- |
| [ADR-001](docs/adr/ADR-001-speicherung.md) | **Teilweise ungültig:** „`albums/<AlbumId>.json`: ein Album mit Name und Fotos samt Hash, Titel und Beschreibung“ und die Begründung „JSON bildet ein Album mit seinen Fotos ohne Umwege ab“ gelten nicht mehr. Der Port `PhotoRepository` kommt hinzu. Status wird „in Teilen ersetzt durch ADR-004“. |
| [ADR-002](docs/adr/ADR-002-fehlerbehandlung.md) | **Anzupassen:** Schreibreihenfolge beim Hinzufügen (drei statt zwei Schritte), Startbereinigung „doppelte Foto-IDs“ (A-11) nur für Einzelfoto-Einträge, endgültiges Löschen der Kopie nur ohne Album-Verweis. |
| [ADR-003](docs/adr/ADR-003-json-bibliothek.md) | Unverändert (Gson). |
| **ADR-004 neu** | „Fotobestand getrennt von Alben“: Fotos als eigene Einheit in `photos.json`, Alben mit Foto-IDs, Migration von `v1.0.0`. Verworfene Alternative: Fotos weiter in jeder Albumdatei mit geteilter ID. Dann müsste jede Text- oder Tagänderung alle betroffenen Albumdateien zugleich ändern; das widerspricht CR-1.1 und lässt sich nach ADR-002 nicht als Einheit zurücknehmen. |

## 5. Rot werdende und neue Tests

### 5.1 Voraussichtlich rot

| Test | Grund |
| --- | --- |
| `StartupCleanupUseCaseTest.removesOnlyDuplicateFromTile` | A-11 nimmt ein geteiltes Foto fälschlich aus der Kachel (Abschnitt 2). |
| `RemovePhotoUseCaseTest.keepsExistingEntrySeparate`, `TrashTest.keepsByteIdenticalEntriesSeparate` | Setzen bytegleiche, getrennte Fotos aus zwei Alben voraus; nach A-13 ist das dasselbe Foto (F-2). |
| `FileAlbumRepositoryTest.keepsPhotosAfterRestart`, `keepsPhotoTextsAfterRestart`, `readsPhotoWithoutTexts`, `reportsPhotoWithoutHash`, `renamedAlbumKeepsPhotos` | Texte und Hash stehen nicht mehr in der Albumdatei. |
| `FileTrashRepositoryTest.keepsEntriesAfterRestart` | Einträge speichern Foto-IDs statt vollständiger Fotos. |
| `EditPhotoTextsUseCaseTest` (alle acht Tests) | Texte werden im Fotobestand statt im Album gespeichert; Konstruktor und Fakes ändern sich. |
| `AddPhotoUseCaseTest.rollsBackCopyWhenAlbumCannotBeSaved`, `reportsCleanupWhenRollbackFails` | Zusätzlicher Schreibschritt für `photos.json` und damit neue Rücknahme. |

Weitere Use-Case-Tests kompilieren nur wegen geänderter Konstruktoren und Fakes nicht mehr. Fachlich bleiben sie gültig; sie werden angepasst, nicht inhaltlich umgeschrieben. `PersistenceIntegrationTest.duplicateIsDetectedAfterRestart` bleibt fachlich unverändert (Duplikat je Album).

### 5.2 Neue Tests

| Ebene | Tests |
| --- | --- |
| Domäne | `PhotoTagTest`: AK-12.1 bis AK-12.5 mit Grenzwerten 0/1/20/21 Zeichen und 5/6 Tags, Groß- und Kleinschreibung, Umlaut und Emoji als ein Zeichen. `PhotoCatalogTest`: Suche über den Hash, „liegt noch in einem Album“. `AlbumPhotoTest` ergänzt: Duplikat je Album bei geteilten Fotos. |
| Anwendung | `AddPhotoToAlbumUseCaseTest` (AK-11.1, AK-11.5, Duplikat, Rücknahme), `RemovePhotoUseCaseTest` (AK-11.3, AK-11.4), `EditPhotoTextsUseCaseTest` (AK-11.2), `EditPhotoTagsUseCaseTest`, Filter (AK-13.1, AK-13.2), `DeletePermanentlyUseCaseTest` und `StartupCleanupUseCaseTest` mit geteilten Fotos in Kacheln. |
| Infrastruktur | `FilePhotoRepositoryTest` (Neustart, unlesbare Datei, keine Zwischendatei), Migration: ein Datenordner im Format von `v1.0.0` wird ohne Verlust gelesen. |
| Integration | `PersistenceIntegrationTest`: Foto in zwei Alben, Neustart, Entfernen aus einem Album, beide Zustände nach dem Neustart. |
| Regression | Alle bisherigen AK-Tests zu Duplikat, Höchstzahl, Papierkorbfristen und Teilerfolg bleiben grün. |

## 6. Risiken

| ID | Risiko | Auswirkung | Gegenmaßnahme |
| --- | --- | --- | --- |
| R-1 | Die Migration verliert oder verdoppelt Fotos aus `v1.0.0`. | hoch: Datenverlust (NFA-02) | Migrationstest mit einem Datenordner im Format von `v1.0.0`; alte Dateien erst nach erfolgreichem Schreiben ersetzen; README-Hinweis zur Sicherung. |
| R-2 | Ein doppelter Schreibfehler hinterlässt geteilte Fotos inkonsistent zwischen Album, Kachel und `photos.json` (A-11). | hoch: eine Kopie wird gelöscht, obwohl ein Album sie nutzt | Kopien nur löschen, wenn weder ein Album noch ein Eintrag sie nennt (wie heute in `deleteOrphans`); die Regel für Kacheln vor dem Code in ADR-002 festlegen. |
| R-3 | Viele Use Cases und Tests ändern sich gleichzeitig. | mittel: lange rote Phase, schwer prüfbare Diffs | Kleine Commits je CR-Teil, nach jedem Schritt `verify` grün; Refactoring vorher getrennt committen. |
| R-4 | Die Fragen F-1 bis F-4 bleiben offen. | mittel: späterer Umbau | Vor Schritt 3 im Team klären; die Vorschläge sind so gewählt, dass sie wenig Code kosten. |
| R-5 | Die UI-Erweiterung ist nur im Systemtest prüfbar. | mittel | Logik in den Use Cases halten; Systemtests S1/S2 um CR-Szenarien ergänzen. |
| R-6 | Der Termin Block 7 wird nicht gehalten. | mittel | Reihenfolge nach Abschnitt 8; eine unvollständige Umsetzung wird ehrlich dokumentiert (PB §11). |

## 7. Aufwandsschätzung

Schätzung in Stunden für eine Person mit KI-Unterstützung, vor der Umsetzung. Der tatsächliche Aufwand folgt im Rückblick (Abschnitt 10).

| Teil | Inhalt | Schätzung |
| --- | --- | ---: |
| 0 | Fragen F-1 bis F-5 klären, REQUIREMENTS, ADR-002 und ADR-004 anpassen | 2 h |
| 1 | CR-1.1 Domäne und Anwendung: Fotobestand, `Album` mit Foto-IDs, `AddPhotoUseCase`, `AddPhotoToAlbumUseCase`, Texte | 5 h |
| 2 | CR-1.1 Infrastruktur: `photos.json`, neues Album- und Papierkorbformat, Migration | 4 h |
| 3 | CR-1.2 Entfernen, Album löschen, Wiederherstellen, endgültiges Löschen, Startbereinigung | 4 h |
| 4 | CR-1.3 Tags in Domäne, Use Case und Speicherung | 2 h |
| 5 | CR-1.4 Filter mit Trefferzahl | 1 h |
| 6 | UI für alle vier Teile, Systemtest | 3 h |
| 7 | TESTING.md, ARCHITECTURE.md, CHANGELOG, Version `1.1.0` | 1,5 h |
| | **Summe** | **22,5 h** |

## 8. Umsetzungsreihenfolge

1. Fragen F-1 bis F-5 im Team klären und die Ergebnisse in REQUIREMENTS, ADR-002 und ADR-004 übernehmen.
2. Refactoring nach [docs/REFACTORING.md](docs/REFACTORING.md) als eigener Commit, möglichst an einer Stelle, die den Umbau erleichtert.
3. CR-1.1 in Domäne und Anwendung (Teil 1), danach Infrastruktur mit Migration (Teil 2). Ab hier ist das Datenmodell stabil.
4. CR-1.2 (Teil 3), weil es auf dem Fotobestand aufsetzt und die riskanten Papierkorbregeln betrifft.
5. CR-1.3 und CR-1.4 (Teile 4 und 5). Sie hängen nur an `Photo` und `OpenAlbumUseCase` und sind am wenigsten riskant.
6. UI (Teil 6), Systemtests, Dokumentation und Release (Teil 7).

Jeder Schritt endet mit grünem `.\mvnw.cmd verify` und einem eigenen Commit. Reicht die Zeit nicht, wird zuerst die UI für CR-1.3/CR-1.4 gekürzt, nicht die Migration.

## 9. Offene Fragen

| ID | Frage | Vorschlag |
| --- | --- | --- |
| F-1 | Entsteht beim Entfernen eines Fotos, das noch in einem anderen Album liegt, ein Papierkorbeintrag? | Nein. Das Foto verlässt nur dieses Album; ein Papierkorbeintrag entsteht erst, wenn es in keinem Album mehr liegt. Nichts geht verloren, weil das Foto im anderen Album bleibt. |
| F-2 | Wie kommt ein Foto in ein zweites Album? | Über eine neue Aktion „In weiteres Album aufnehmen“ (FA-11). Zusätzlich wird ein bytegleicher Import mit dem vorhandenen Foto verknüpft (A-13), damit die Datei nur einmal gespeichert wird. |
| F-3 | Werden Tags getrimmt, und filtert der Filter ohne Beachtung der Groß- und Kleinschreibung? | Ja zu beidem (A-14), analog zu den Albumnamen (A-02). |
| F-4 | Was passiert mit einem Import, der bytegleich zu einem Foto ist, das nur noch im Papierkorb liegt? | Neues Foto mit neuer ID; der Papierkorbeintrag bleibt unverändert. |
| F-5 | Die Folienübertragung nennt 10.000 Fotos je Album, REQUIREMENTS und Code 100. | 100 beibehalten, wie der CR es nennt; Hinweis in der Folienübertragung korrigieren. |

## 10. Rückblick

Folgt nach der Umsetzung: geschätzter und tatsächlicher Aufwand je Teil, Überraschungen.
