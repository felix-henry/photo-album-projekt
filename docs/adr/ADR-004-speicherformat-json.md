# ADR-004: Speicherformat JSON, atomar geschrieben

**Status:** vorgeschlagen · Block 3

**Kontext:** Gefordert ist lokale Datenhaltung mit dokumentiertem Format (PB §6). Nach NFA-02 überstehen bestätigte Aktionen ein hartes Beenden, nach einem Abbruch beim Speichern gilt der letzte vollständige Stand (AK-N02.5), und unlesbare oder regelwidrige Daten werden weder verändert noch überschrieben (AK-N02.2, AK-N02.4). Der Change Request kann das Datenmodell ändern (PB §11).

**Entscheidung:**
- Datenordner `<user.home>/.photo-album/` mit `albums.json` und `images/<photoId>.jpg` bzw. `.png` ([ADR-003](ADR-003-bilddatei-ports.md)). Format: `{"version":1,"albums":[{id,name,photos:[{id,hash,fileName,type,sizeBytes,title,description}]}]}`.
- Schreiben über eine Temp-Datei im selben Ordner und `Files.move` mit `ATOMIC_MOVE`; bei Fehler wird die Temp-Datei gelöscht (NFA-01, NFA-02). Die Bildkopie entsteht vor dem Speichern (UC-01 Schritt 5), daher hat jedes gespeicherte Foto eine Kopie (AK-N02.5).
- Laden: Eine fehlende Datei heißt „keine Alben“ (AK-N02.3). Alle Objekte entstehen über die Domänenkonstruktoren und `AlbumName.requireUnique`, die Regeln bleiben also in der Domäne. Jeder Fehler beim Lesen oder beim Aufbau der Objekte (Parse-Fehler, `DomainException`, `IllegalArgumentException` oder `NullPointerException` etwa aus `AlbumId.of`, `PhotoId.of`, `ContentHash`, `Photo`) wird zur `StorageException` mit Dateipfad; `Main` zeigt dann `ErrorMessages.dataNotReadable(path)` und beendet sich, ohne zu schreiben (AK-N02.2, AK-N02.4).
- Jackson Databind ab Block 4, von Maven geladen, damit die CI ohne manuelle Installation läuft (PB §6).

**Verworfen:**
- Java-Serialisierung: binär, nicht als Format dokumentierbar und bricht bei Änderungen am Datenmodell durch den Change Request (PB §11).
- Eigenes Textformat: Parser und Escaping (etwa Anführungszeichen in Namen) werden zur eigenen Fehlerquelle.

**Konsequenzen:**
- **+** Lesbares, dokumentiertes Format; `version` erlaubt eine Migration beim Change Request; nach einem Abbruch gilt der alte oder der neue Stand vollständig.
- **−** DTO-Records und Mapping-Code in `infrastructure`, damit die Domäne keine Jackson-Annotationen braucht; eine Abhängigkeit mehr; jede Änderung schreibt die ganze Datei neu.
