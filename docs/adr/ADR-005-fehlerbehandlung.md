# ADR-005: Fachliche Exceptions in der Domäne, `Result` an der UI-Grenze

**Status:** vorgeschlagen · Block 3

**Kontext:** Ungültige Eingaben und definierte Fehlerfälle brauchen eine fachliche Meldung bei unverändertem Zustand (PB §5.1, NFA-01). Die Meldungen stehen wörtlich in REQUIREMENTS (UC-01, UC-02, AK); in UC-01 bestimmt die erste fehlgeschlagene Prüfung die Meldung. Beim Löschen gibt es einen Teilerfolg mit Pfadhinweis (AK-05.3, AK-09.3).

**Entscheidung:**
- Die Domäne wirft Unterklassen von `DomainException` (B3 F13, F40), die Ports `StorageException` oder `ImageNotReadableException`, alle unchecked.
- Jeder Use Case fängt sie und gibt `Result<T>` zurück: `Success<T>(T value)` oder `Failure<T>(String message)` (B3 F23, F24), nicht `Error` wegen `java.lang.Error`. Texte stehen nur in `ErrorMessages`, Zahlen kommen aus den Domänenkonstanten (NFA-05).
- Erst prüfen, dann speichern (B3 F10): Bei `Failure` wird das geladene Album verworfen. Scheitert in UC-01 das Speichern nach dem Kopieren, löscht `AddPhotoUseCase` die Kopie wieder; scheitert auch das, wird der Fehler abgefangen, und die Meldung bleibt `photoNotSaved()` (5a).
- `DeleteAlbumUseCase` und `RemovePhotoUseCase` liefern `Result<Optional<String>>`: `Success` mit optionalem Hinweis samt Pfad, `Failure` heißt „nichts gelöscht“. Bildkopien werden daher erst nach dem erfolgreichen Schreiben der Albumdaten entfernt.

**Verworfen:**
- Boolesche Rückgabewerte: Der Grund geht verloren (B3 F13).
- Exceptions bis in die UI: Die UI müsste Domänen-Exceptions kennen (Kopplung UI → Domäne) und Texte selbst bauen.

**Konsequenzen:**
- **+** Jeder Fehlerfall ist Teil des Vertrags und einzeln testbar; die UI zeigt nur `message` an.
- **−** Übersetzungscode in jedem Use Case. Scheitert auch die Kompensation, bleibt eine verwaiste Bildkopie (Restrisiko, siehe [ARCHITECTURE.md](../../ARCHITECTURE.md)).
