# ADR-001: Speicher hinter AlbumRepository-Interface, ohne Factory

- **Status:** vorgeschlagen (vom Team zu bestätigen) · **Datum:** 19.10.2026
- **Grundlage:** Muster-ADR aus dem Pattern-Gericht (B3 F35), Beispiel-ADR B3 F46

## Kontext

- Gefordert ist lokale Datenhaltung im Dateisystem; Datenbanken sind nicht verpflichtend (PB §6).
- NFA-03 Testbarkeit: Die Use Cases sollen ohne Dateisystem unit-testbar sein; mindestens 10 Unit-Tests, deren Fehlerfälle den unveränderten Zustand prüfen (PB §10).
- Der Change Request in Block 6 kann Datenmodell und Architektur berühren (PB §11).

## Entscheidung

- Port `AlbumRepository` in `application` mit `save`, `findById` (liefert `Optional<Album>`), `findAll`, `delete` (B3 F9, F33).
- Adapter `FileAlbumRepository` in `infrastructure`; `InMemoryAlbumRepository` in den Tests (B3 F8, F9).
- Das Repository enthält keine Geschäftsregeln, nur Speichern und Finden (B3 F33).
- Verdrahtung an genau einer Stelle (Main, Composition Root); keine Factory (B3 F9, F30).

## Verworfene Alternativen

1. **Direkter Dateizugriff im Use Case:** nicht ohne Dateisystem testbar; ein Formatwechsel trifft alle Use Cases (B3 F46).
2. **Interface plus Factory:** bei einer Konfiguration unnötige Klasse und Indirektion (B3 F30, F34, F46).

## Konsequenzen

- \+ Unit-Tests ohne Dateisystem; Speicher austauschbar, ohne Use Cases zu ändern
- − ein Interface mehr, Mapping-Code (B3 F46)
- Neu bewerten, sobald die Speicherwahl konfigurierbar sein muss (dann Factory).
