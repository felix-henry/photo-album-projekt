# ADR-003: Gson für JSON-Dateien

**Status:** akzeptiert · **Datum:** 13.11.2026

## Kontext

ADR-001 legt fest, dass die Adapter Alben und Papierkorb als JSON-Dateien speichern. Java bringt keinen JSON-Parser mit. Laut ADR-001 wird die Bibliothek mit den Adaptern ausgewählt und in einem eigenen ADR begründet. Unsere Daten sind einfach: Alben mit Namen, später Fotos mit Hash, Titel und Beschreibung sowie Papierkorbeinträge mit Löschzeitpunkt.

## Entscheidung

- Die Adapter in `infrastructure` lesen und schreiben JSON mit **Gson 2.15.0** (Lizenz Apache 2.0). Gson bringt nur eine weitere Bibliothek mit, `error_prone_annotations`, die ausschließlich Annotationen enthält.
- Gson arbeitet nicht direkt mit den Domänenklassen. Die Adapter wandeln ein `Album` in eine einfache Datenklasse nur für die Datei um (DTO) und beim Laden wieder zurück. Das `Album` entsteht dabei über die Domäne, die Namensregel bleibt also auch beim Laden wirksam.
- Nur das Paket `infrastructure` nutzt Gson. Domäne und Anwendung hängen nicht davon ab.

## Verworfene Alternativen

- **Jackson 2:** mächtiger, besteht aber aus mehreren Bibliotheken und braucht für Datum und Uhrzeit ein Zusatzmodul. Diese Möglichkeiten brauchen wir für unsere einfachen Daten nicht.
- **Jackson 3:** Datum und Uhrzeit sind eingebaut, es besteht aber ebenfalls aus mehreren Bibliotheken. Wegen der neuen Paketnamen passen viele vorhandene Beispiele nicht mehr.
- **JSON selbst schreiben und lesen:** keine Abhängigkeit, aber Sonderzeichen, Escaping und fehlerhafte Dateien selbst zu behandeln ist fehleranfällig.

## Konsequenzen

- Die fertige App enthält zwei fremde Bibliotheken. Updates schlägt Dependabot vor, sie werden per PR geprüft.
- Für jede gespeicherte Klasse braucht der Adapter ein DTO und eine Umwandlung in beide Richtungen.
- Ein einfaches JAR enthält Gson nicht. Wie Gson beim Start mitgeliefert wird, legen wir mit der Startklasse `Main` fest.
