# ADR-002: Repository für Alben

**Status:** vorgeschlagen · 20.10.2026

**Kontext:** Die Use Cases müssen Alben laden und speichern, ohne Dateien zu kennen.

**Entscheidung:** Pattern Repository. `AlbumRepository` ist ein Port, `FileAlbumRepository` der Adapter, beide ohne Geschäftsregeln. In Tests ersetzt ein Repository im Speicher die Dateien.

**Verworfen:** `RepositoryFactory`, denn sie wäre eine Klasse mehr bei nur einer Konfiguration.

**Konsequenzen:**
- **Plus:** Speicherfehler lassen sich im Test gezielt auslösen.
- **Minus:** Zwischen Datei und Domäne braucht es Mapping-Code.
