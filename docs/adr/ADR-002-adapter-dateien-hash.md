# ADR-002: Adapter für Bilddateien und Hash (PhotoStorage, ImageHasher)

- **Status:** vorgeschlagen (vom Team zu bestätigen) · **Datum:** 19.10.2026
- **Grundlage:** Pattern-Bewertung (B3 F27–F34), CRC-Karten, Sequenzdiagramme „Foto hinzufügen“ und „Album löschen“

## Kontext

- Das System speichert beim Hinzufügen eine Kopie der Bilddatei und löscht sie beim Entfernen bzw. Album-Löschen (A-06, AE-04, NFA-04).
- Die gewählte Datei muss geprüft (FA-04) und für die Duplikatregel gehasht werden (FA-05, A-05).
- Dateisystem (`java.nio.file`) und Hash-Bibliothek (`java.security.MessageDigest`) passen nicht zu den fachlichen Ports; Use Cases sollen ohne Dateisystem testbar bleiben (NFA-03).

## Entscheidung

- Pattern **Adapter** (B3 F32): Ports `PhotoStorage` (`store`, `read`, `delete`) und `ImageHasher` (`validate`, `hash`) in `application`; Adapter `FileSystemPhotoStorage` und `Sha256ImageHasher` in `infrastructure`; Fakes in den Tests.
- Der Speicherschlüssel ist je Foto eindeutig, nicht der Hash (V-02 in crc-karten.md).
- `validate` prüft gegen die Werte aus REQUIREMENTS.md (A-03, A-04) und meldet Verstöße als `InvalidImageException`. Offen: Grenzwerte in die Domäne ziehen und den Adapter nur Dateityp und Größe liefern lassen (Pattern-Bewertung „keine Geschäftsregeln im Adapter“; KI-Review AI_USAGE Fall 2, Vorschlag 1).

## Verworfene Alternativen

1. **Technik direkt im Use Case:** nicht ohne Dateisystem testbar; ein Wechsel der Hash-Bibliothek ändert den Use Case.
2. **Photo liest selbst vom Dateisystem:** Domäne kennt Technik, Kopplung steigt (B3 F15 Aussage 4).
3. **Hash als Speicherschlüssel:** Löschen eines Albums würde die Datei desselben Fotos in einem anderen Album mitlöschen (V-02).

## Konsequenzen

- \+ Fehler und Ausnahmen der Technik werden an einer Stelle in fachliche Fehler übersetzt (B3 F32)
- \+ Tests mit Fake-Adapter
- − zwei Interfaces und zwei Klassen mehr
