# ADR-003: Fachliche Exceptions in der Domäne, Result an der UI-Grenze

- **Status:** vorgeschlagen (vom Team zu bestätigen) · **Datum:** 19.10.2026

## Kontext

- Ungültige Eingaben und definierte Fehlerfälle brauchen eine fachliche Fehlermeldung und einen unveränderten Zustand (PB §5.1; FA-10).
- NFA-02: keine Teilzustände nach Abbruch.
- Die UI soll keine Regeln kennen und nur den Use Case aufrufen (B3 F21, F24).

## Entscheidung

- Die Domäne meldet Regelverstöße als fachliche Exceptions (`InvalidAlbumNameException`, `AlbumFullException`, `DuplicatePhotoException`, `InvalidTextException`), nicht als boolesche Rückgabewerte (B3 F13).
- Jeder Use Case fängt fachliche und technische Fehler und gibt `Result.ok` bzw. `Result.error(grund)` an die UI zurück (B3 F23, F24).
- Reihenfolge im Use Case nach B3 F10 („erst prüfen, dann speichern, dann Album ändern“): Datei und Regeln werden geprüft, bevor etwas gespeichert wird; das gespeicherte Album ändert sich erst mit `save`, bei einem Fehler wird das geladene Album verworfen.

## Verworfene Alternativen

1. **Boolesche Rückgabewerte in Album:** Grund des Fehlers geht verloren; Aufrufer können die Prüfung ignorieren (B3 F13).
2. **Exceptions bis in die UI durchreichen:** UI müsste Domänen-Exceptions kennen, Kopplung UI → Domäne.

## Konsequenzen

- \+ Fehlerfälle als Teil des Vertrags; Unit-Tests prüfen Exception und unveränderten Zustand (PB §10)
- − Übersetzungscode in jedem Use Case
