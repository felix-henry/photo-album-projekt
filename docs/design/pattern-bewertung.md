# Pattern-Bewertung für das Photo Album (Block 3, Folien 27–34)

Vorgehen nach Folie 28: Veränderungsdruck → konkretes Problem → Lösung ohne Pattern → Pattern mit Konsequenzen → ADR.

| Pattern | Problem bei uns? | Lösung ohne Pattern | Urteil |
|---|---|---|---|
| **Strategy** (F29) | Nein. Sortieren gehört nicht zur Pflichtfunktionalität (PB §5.1) und wäre nur „sollte“ (B2 F24). | `Comparator` oder `switch` bei 2 festen Varianten | ❌ nicht einsetzen |
| **Factory Method** (F30) | Kaum. Es gibt eine Konfiguration im Betrieb und eine im Test. | alle `new` in Main (Composition Root) | ❌ nicht einsetzen |
| **Observer** (F31) | Nein. Die UI zeigt nach jeder Aktion eine Ansicht; der Use Case gibt ein `Result` zurück, danach aktualisiert die UI sich selbst. | Rückgabewert `Result` | ❌ nicht einsetzen |
| **Adapter** (F32) | **Ja.** Dateisystem, JSON-Format und Hash-Bibliothek passen nicht zu unseren Ports: `FileAlbumRepository`, `FileSystemPhotoStorage`, `Sha256ImageHasher`. | Technik direkt im Use Case (nicht ohne Dateisystem testbar, Formatwechsel betrifft alle Use Cases) | ✅ einsetzen |
| **Template Method** (F33) | Nein. Ein formatabhängiger Import-Schritt ist nicht gefordert. | – | ❌ nicht einsetzen |
| **Repository** (F33) | **Ja.** Alben werden gespeichert und gefunden, ohne dass Use Cases die Dateien kennen. Tests laufen mit `InMemoryAlbumRepository`. | direkter Dateizugriff im Use Case | ✅ einsetzen |
| **Singleton** (F33) | Nein. Es wäre eine versteckte globale Abhängigkeit und schlecht testbar. | Composition Root | ❌ nicht einsetzen |

**Neu bewerten, wenn:**
- **Strategy:** Die Nutzerin soll zwischen mehr als zwei Sortierungen wählen, z. B. durch den Change Request.
- **Factory:** Die Speicherwahl muss konfigurierbar sein.
- **Observer:** Mehrere Ansichten sind gleichzeitig sichtbar und müssen sich aktualisieren.

## Adapter im Detail (F32)

| Port (Target, application) | Adapter (infrastructure) | Adaptee (Fremdsystem) | Was der Adapter übersetzt | Test-Adapter |
|---|---|---|---|---|
| `AlbumRepository`: `save`, `findById`, `findAll`, `delete` | `FileAlbumRepository` | Dateisystem (`java.nio.file`) + Speicherformat (Folienbeispiel JSON, B3 F8; festgelegt in Block 4) | Album ↔ Datei im dokumentierten Format (PB §6); `IOException` → fachlicher Speicherfehler | `InMemoryAlbumRepository` (F8, F9) |
| `PhotoStorage`: `store(file, key)`, `read(key)`, `delete(key)` | `FileSystemPhotoStorage` | Dateisystem | Bildkopie unter einem Schlüssel ablegen und löschen; der Zielpfad kommt aus dem Schlüssel, nie aus dem Dateinamen der Nutzerin | In-Memory-Fake |
| `ImageHasher`: `validate(file)`, `hash(file)` | `Sha256ImageHasher` | `java.security.MessageDigest` | Dateiinhalt → Hash-String; Dateityp und Größe ermitteln (CRC F18) | Fake mit festen Hashes |
| `DescriptionSuggester` (nur Entwurf, ADR-005) | z. B. `ClaudeDescriptionSuggester` | HTTP-Client/SDK | Guardrails: Metadaten entfernen, Timeout, Ausgabe prüfen (F10, F32) | `FakeSuggester` (F32) |

**Regeln:**
- Der Port hat Fachnamen (`save`, `store`) statt Technik.
- Der Use Case kennt nur den Port.
- Nur der Adapter kennt die Technik.
- Verdrahtet wird nur in Main (F8, F9).
- Fehler, Timeouts und Guardrails liegen an genau einer Stelle, im Adapter (F32).

**Nicht sinnvoll:**
- Adapter für Domänenklassen
- Adapter ohne eigenen Port
- Adapter für Bibliotheken, die nur intern in einem Adapter genutzt werden
- Geschäftsregeln im Repository (F33: „Das Repository enthält keine Geschäftsregeln“). Ob die Grenzwerte für Dateityp und -größe in der Domäne statt in `ImageHasher` liegen sollen, ist ein offener Teamvorschlag ohne Folienbeleg; die CRC-Folie gibt die Prüfung `ImageHasher` (F18).

**Abgleich mit den Pattern-Risiken (F34):**
- **Zu frühe Abstraktion?** Nein. Jeder Port hat zwei Implementierungen: den echten Adapter und einen Test-Adapter.
- **Zu viele Indirektionen?** Nein, solange es keine Factory gibt: Use Case → Port → Adapter.
- **Folge:** Für Repository und Adapter je einen ADR schreiben und darin die verworfene Alternative nennen. Das adressiert das Bewertungskriterium „Entscheidungen begründet“ (5 Punkte, F34).
