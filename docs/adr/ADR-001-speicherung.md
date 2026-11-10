# ADR-001: Speicherung als JSON-Dateien hinter Ports

**Status:** akzeptiert · **Datum:** 10.11.2026

## Kontext

Die Projektbeschreibung verlangt eine lokale Datenhaltung im Dateisystem mit dokumentiertem Format. Alben und Papierkorb müssen einen Neustart überstehen (NFA-02). Die Geschäftsregeln sollen sich ohne echte Dateizugriffe testen lassen (NFA-05). Doppelte Bilder werden am bytegleichen Inhalt erkannt (A-01).

## Entscheidung

- Die Anwendung legt die Ports `AlbumRepository`, `TrashRepository`, `PhotoStorage` und `ImageHasher` als Interfaces fest. Use Cases kennen nur diese Ports.
- Adapter in der Infrastruktur setzen sie um. JSON ist lesbar und bildet ein Album mit seinen Fotos ohne Umwege ab. Im Datenordner der App liegen:
  - `albums/<AlbumId>.json`: ein Album mit Name und Fotos samt Hash, Titel und Beschreibung,
  - `trash.json`: die Papierkorbeinträge mit Löschzeitpunkt und Ursprungsalbum,
  - `photos/<PhotoId>`: die App-Kopie jedes Bildes.

  Die genauen Felder legt das Projektgerüst fest.
- `Sha256ImageHasher` berechnet beim Import einen SHA-256-Hash des Dateiinhalts. `Album` vergleicht nur noch Hashes und muss vorhandene Bilder nicht erneut lesen. Dass zwei verschiedene Dateien denselben SHA-256-Hash haben, ist praktisch ausgeschlossen.
- Nur `Main` erzeugt die Adapter. Tests setzen Varianten im Arbeitsspeicher ein.

Damit setzen wir zwei Entwurfsmuster ein:

- **Repository:** Use Cases sollen Alben laden und speichern, ohne Speicherort und Format zu kennen. `AlbumRepository` und `TrashRepository` bieten dafür Methoden in Fachsprache wie `save(album)`.
- **Adapter:** Dateisystem und Hashfunktion haben eine technische Schnittstelle, die nicht zu unseren Ports passt. Die Adapter übersetzen zwischen beiden.

## Verworfene Alternativen

- **Use Cases greifen direkt auf Dateien zu.** Dann ließen sich die Regeln nur mit echten Dateien testen, und ein anderes Format würde jeden Use Case ändern.
- **Datenbank wie SQLite.** Die Projektbeschreibung verlangt keine Datenbank. Sie wäre eine zusätzliche Abhängigkeit ohne fachlichen Nutzen für wenige lokale Alben.
- **Factory zum Erzeugen der Adapter.** Es gibt nur eine Konfiguration im Betrieb. `Main` reicht dafür aus.

## Konsequenzen

- Die Regeln lassen sich ohne Dateien testen, und Speicherung oder Format lassen sich austauschen, ohne Use Cases zu ändern.
- Jeder Port braucht ein Interface und einen Adapter. Zwischen Domänenobjekten und JSON ist Umwandlungscode nötig.
- Java bringt keinen JSON-Parser mit. Die Bibliothek dafür wird beim Projektgerüst ausgewählt und in einem eigenen ADR begründet.
