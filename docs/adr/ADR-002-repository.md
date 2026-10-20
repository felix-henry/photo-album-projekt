# ADR-002: Speicher hinter `AlbumRepository`, Verdrahtung in `Main`

**Status:** vorgeschlagen · Block 3

**Kontext (Problem):** Die Use Cases müssen Alben finden und speichern, ohne Dateien zu kennen; gefordert ist nur lokale Datenhaltung im Dateisystem (PB §6). Der Fehlerfall „Album kann nicht gespeichert werden“ (AK-06.7, UC-01 5a) ist mit echten Dateien schwer gezielt auszulösen.

**Entscheidung:**
- Pattern **Repository** (B3 F33): Port `AlbumRepository` in `application.port` mit `List<Album> findAll()`, `Optional<Album> findById(AlbumId)`, `void save(Album)` und `void delete(AlbumId)`. Adapter `FileAlbumRepository` ([ADR-004](ADR-004-speicherformat-json.md)), Test-Ersatz `InMemoryAlbumRepository` (Block 5).
- Das Repository enthält keine Geschäftsregeln (B3 F33). Jeder Aufruf liefert neue Objekte, ein geladenes und dann verworfenes Album ändert also nichts (NFA-01). Fehler meldet es als `StorageException` mit Dateipfad.
- Keine Factory: Alle `new …Adapter` stehen in `Main` im Wurzelpaket als Composition Root (B3 F9, F30).

**Verworfen:**
- Direkter Dateizugriff im Use Case: ohne Dateisystem nicht testbar (B3 F46).
- `RepositoryFactory`: eine zusätzliche Klasse bei nur einer Konfiguration (B3 F30, F46).

**Konsequenzen:**
- **+** Use Cases sind ohne Dateisystem testbar, auch der Speicherfehler (AK-06.7); der Speicher ist austauschbar.
- **−** Ein Interface mehr und Mapping-Code zwischen JSON und Domäne (B3 F46). Muss die Speicherwahl konfigurierbar werden, ist die Factory neu zu bewerten (B3 F30).
