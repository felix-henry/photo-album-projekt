# ADR-001: Adapter für Dateien und Hash

**Status:** vorgeschlagen · 20.10.2026

**Kontext:** Die Geschäftsregeln sollen ohne Dateisystem testbar sein.

**Entscheidung:** Pattern Adapter. Die Ports liegen in `application.port`, die Adapter in `infrastructure`. Nur `Main` erzeugt Adapter.

**Verworfen:** Use Cases lesen und schreiben Dateien selbst, denn dann bräuchte jeder Test ein Dateisystem.

**Konsequenzen:**
- **Plus:** Die Regeln sind ohne Dateien testbar, die Technik ist austauschbar.
- **Minus:** Jeder Port bringt eine Schnittstelle mehr.
