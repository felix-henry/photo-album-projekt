# ADR-001: Vier Schichten mit Ports und Adaptern

**Status:** vorgeschlagen · Block 3

**Kontext (Problem):** NFA-05 verlangt jeden Grenzwert an genau einer Stelle, keine Geschäftsregel in der Oberfläche und Regeltests ohne Oberfläche und ohne Dateisystem (AK-N05.2). Dateisystem, JSON und SHA-256 sind Technik; der Change Request in Block 6 berührt Datenmodell und Architektur (PB §11).

**Entscheidung:**
- Pakete `ui`, `application` (mit `application.port`), `domain` und `infrastructure` unter `de.dhbw.photoalbum` (PB §8; B3 F7, F11).
- Abhängigkeiten zeigen nach innen: `ui → application → domain` (aus der Domäne kennt die UI nur `AlbumId` und `PhotoId`) und `infrastructure → application.port`. Die Domäne importiert nur `java.base` (B3 F7, F20).
- Pattern **Adapter** für jede Technik: Ports als Interfaces in Fachsprache in `application.port`, Adapter in `infrastructure` (B3 F8, F32). `new …Adapter` steht nur in `Main` ([ADR-002](ADR-002-repository.md)).

**Verworfen:**
- „UI macht alles“ (Entwurf A, B3 F24): Die Regeln sind ohne Oberfläche nicht testbar, und eine zweite Oberfläche müsste sie kopieren.
- Use Cases lesen und schreiben Dateien selbst über `Files`: Use-Case-Tests brauchen ein Dateisystem, und ein Formatwechsel trifft jeden Use Case.

**Konsequenzen:**
- **+** Die Domäne ist ohne Oberfläche und Dateisystem testbar (NFA-05); Technik lässt sich tauschen, ohne einen Use Case anzufassen (B3 F8).
- **−** Mehr Klassen und eine Indirektion je Port. Jeder Port hat nur einen Produktiv-Adapter; ohne den Test-Ersatz in Block 5 wäre das zu frühe Abstraktion (B3 F34).
