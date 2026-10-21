# AI_USAGE – Einsatz von KI-Werkzeugen

Substanzielle KI-Einsätze nach PB §13. Prompts liegen unter `ai_usage/prompts/`. Die Entwürfe von REQUIREMENTS.md sind mit Claude entstanden; die Entscheidungen hat das Team getroffen.

## Fall 1 – Kritisches Review der Anforderungen („Grill me“), Block 2

| Feld | Inhalt |
|---|---|
| **Aufgabe/Ziel** | REQUIREMENTS.md v1 vor der Abgabe auf Lücken, Widersprüche, fehlende Grenzfälle und unklare Begriffe prüfen lassen. |
| **Werkzeug/Modell** | Claude (claude.ai), Claude Opus 5.5; zweiter KI-Chat zur Formulierung der Antworten: Claude (claude.ai), Claude Opus 5.5 · Prompt: [`prompts/grill-me.md`](prompts/grill-me.md) (B2 F36) · Eingaben: REQUIREMENTS.md v1 und Projektbeschreibung |
| **Ergebnis** | 8 Fragen nacheinander, danach eine Tabelle der 5 wichtigsten offenen Punkte. Antworten formuliert mit Hilfe eines zweiten KI-Chats; entschieden hat das Team. |
| **Kritische Bewertung** | Die meisten Hinweise trafen echte Schwächen (Messaufbau, unklare Begriffe, Widerspruch 1.1/6.4, nicht widerlegbare Bedingung bei NFA-03) und deckten einen beim Überarbeiten entstandenen Fehler auf (Verweis in A-08). Zwei Vorschläge (#6, #14) gingen über den Projektrahmen hinaus. |
| **Entscheidung** | 8 übernommen, 5 angepasst, 2 verworfen; umgesetzt in v1.1. |

| # | Hinweis der KI | Entscheidung | Begründung / Umsetzung |
|---|---|---|---|
| 1 | NFA-04: Messaufbau fehlt; Testperson kennt den Dateipfad nicht. | übernommen | Messaufbau ergänzt; Pfad steht auf der Aufgabenkarte. |
| 2 | Startsignal „Karte gelesen“ ist nicht beobachtbar. | übernommen | Start: Testperson legt die Karte hin. |
| 3 | NFA-05: „Geschäftsregel“ undefiniert; darf die Oberfläche Eingaben vorab begrenzen? | angepasst | Definition ergänzt, nach Hinweis der KI auf Regeln über Annahme/Ablehnung eingeschränkt; Sortierung ist Darstellungsregel. |
| 4 | Speichern scheitert nach dem Kopieren der Bilddatei. | übernommen | UC-01 5a und AK-06.7: Kopie wird zurückgenommen. |
| 5 | Widerspruch: nicht entfernbare Bildkopien beim Löschen vs. 1.1 und 6.4. | angepasst | Pfadhinweis in FA-05/FA-09, AK-05.3, AK-09.3. |
| 6 | Verwaiste Dateien beim nächsten Start automatisch bereinigen. | **verworfen** | Zusätzliche Funktion außerhalb des Auftrags. Unser zweites Argument (Löschen ohne Bestätigung) nannte die KI zu Recht schwach. |
| 7 | „Sehr selten“ als Begründung ohne Beleg. | übernommen | Gestrichen; konkrete Ursachen genannt. |
| 8 | „Zeichen“ undefiniert (Emojis, Akzente). | angepasst | A-13: Codepoints nach NFC; Zählung nach sichtbaren Zeichen wegen Aufwand nicht gewählt. |
| 9 | Vergleich von Albumnamen undefiniert („Café“, „Straße“/„STRASSE“). | angepasst | A-14 fachlich mit Beispielen; Java-Aufruf ist Umsetzungsdetail. |
| 10 | NFA-03: Referenzgerät erst nach der Messung; Testdaten unklar; Herkunft von 2 s. | übernommen | Gerät und Testdatensatz vorab festgelegt; Dateigröße ohne Einfluss; Quelle A-15. |
| 11 | NFA-02: Garantie nur bei „Beenden“; Startverhalten bei unlesbaren Daten offen; Absturz nicht testbar. | übernommen | Garantie für jede bestätigte Aktion, geprüft durch hartes Beenden; Stromausfall nicht getestet; AK-N02.1 bis N02.3. |
| 12 | Startfehler ist eine Sackgasse für die Nutzerin. | übernommen | Meldung mit Handlungsanweisung. |
| 13 | Reihenfolge der Prüfungen bei mehreren Fehlern offen. | übernommen | Reihenfolge in UC-01 und FA-07; AK-07.6. |
| 14 | Grenzwerte (50, 500 Zeichen, 20 MB, JPEG/PNG, SHA-256) beim Auftraggeber erfragen. | **verworfen** | Eigene Fragen konnten wir nicht stellen: Die Auftraggeber-Sprechstunde (B2 F42) hat für unser Team nicht stattgefunden, in Moodle liegen keine Antworten vor. Übernommen sind nur die Werte von B2 F31 (AE-01 bis AE-03); PB §4 sieht dafür dokumentierte Annahmen vor. Bestätigte Werte wandern nach 7.1. |
| 15 | NFA-04 und Stakeholder-Anliegen messen Verschiedenes; Herkunft von 60 s und 4/5 fehlt. | angepasst | Anliegen angeglichen; Herkunft A-16. |

**Erkenntnis:** Stark bei Messbarkeit und Begriffsklärung (SOPHIST-Regeln), aber mit Hang zu immer mehr Absicherung. Wo der Projektumfang endet, mussten wir selbst entscheiden.

## Fall 2 – Überarbeitung zur Abgabefassung, Block 2

| Feld | Inhalt |
|---|---|
| **Aufgabe/Ziel** | REQUIREMENTS.md v1.2 aus v1.1 erstellen (Konsistenz, Prüfbarkeit, Kürzung) und den Klickprototyp für UC-01/UC-02 erstellen. |
| **Werkzeug/Modell** | Claude Code (Anthropic), claude-opus-5-5; mehrere KI-Reviewrollen und ein abschließender Prüfer. Prompt: Arbeitsaufträge im Chat, nicht versioniert (Abweichung von PB §13). |
| **Ergebnis** | REQUIREMENTS.md v1.2 (siehe Änderungshistorie dort); [docs/prototype/index.html](../docs/prototype/index.html), Meldungstexte und Grenzwerte aus den AK. |
| **Kritische Bewertung** | Zutreffend: fehlende AK für NFA und SR-01, FA-06/FA-10 ohne Ablehnung, falsche Quelle in 6.1, Zählfehler in Fall 1. Mehrere Vorschläge hätten Teamentscheidungen still geändert oder den Umfang erweitert. |
| **Entscheidung** | Übernommen bzw. angepasst wie in v1.2. Verworfen: Liste offener Auftraggeberfragen (widerspricht Fall 1 #14), Dateiauswahl statt Pfadeingabe (die Eingabeart hängt von der Oberflächentechnik ab und wird nicht in den Anforderungen festgelegt; v1.2 behält die Pfadeingabe aus UC-01), Speicherfehler-Fall für alle Aktionen (Umfang, Meldungen ohne Quelle). |

## Fall 3 – KI-Review des Klassendiagramms, Block 3

Die Block-3-Entwürfe (ARCHITECTURE.md, ADRs, Diagramme, CRC-Karten, Projektgerüst) sind mit einem Team aus Claude-Code-Subagents erstellt und gegengeprüft worden; dieser Fall dokumentiert davon das Diagramm-Review.

| Feld | Inhalt |
|---|---|
| **Aufgabe/Ziel** | Klassendiagramm und Sequenzdiagramm „Foto hinzufügen“ von einer KI prüfen lassen; einen Vorschlag übernehmen, einen verwerfen (Hausaufgabe B3 F50). |
| **Werkzeug/Modell** | Claude Code (Anthropic), claude-opus-5-5, als Subagent · Prompt: [`prompts/klassendiagramm-review.md`](prompts/klassendiagramm-review.md) (Prüfschritte B3 F48) · Eingaben: `docs/design/klassendiagramm.puml`, `docs/design/sequenz-foto-hinzufuegen.puml`, REQUIREMENTS.md v1.2 |
| **Ergebnis** | Regel-Check: Jede Geschäftsregel hat eine prüfende Klasse, keine liegt in der UI. Konsistenz-Check: Jede Nachricht hat eine Operation, eine Antwort ist unvollständig (#2). Kopplungs-Check: `AddPhotoUseCase` hat die meisten Abhängigkeiten (#3). Vier Vorschläge, siehe Tabelle. |
| **Kritische Bewertung** | Alle Fundstellen stehen so in den `.puml`-Dateien (dort nachgeprüft), erfunden war nichts. #2 und #4 sind echte Lücken: ein fehlender Fehlerpfad und eine nicht gezeichnete Abhängigkeit. #1 und #3 klingen plausibel, verlagern aber eine Regel in den Adapter bzw. fügen eine Klasse hinzu, ohne dass eine Anforderung es verlangt. Einschränkung: Reviewer und Entwürfe stammen vom selben Modell; das ersetzt nicht das Review über Kreuz durch das Partnerteam (B3 F48). |
| **Entscheidung** | #2 und #4 übernommen (umgesetzt in `sequenz-foto-hinzufuegen.puml` bzw. `klassendiagramm.puml`), #1 und #3 verworfen. Entschieden von Pit; Bestätigung durch das Team im Review. |

| # | Hinweis der KI | Entscheidung | Begründung / Umsetzung |
|---|---|---|---|
| 1 | Regel-Check: `ImageInspector.inspect` liest und hasht auch Dateien über 20 MB vollständig, erst `Photo` lehnt sie ab. Größe schon im Adapter prüfen. | **verworfen** | Größenregel (FA-06, A-04) stünde in zwei Klassen; den Zeitgewinn verlangt keine NFA. ADR-003 nennt den Preis. |
| 2 | Konsistenz-Check: Die Antwort auf `findById` zeigt nur `Optional<Album>`. Laut Klassendiagramm wirft `AlbumRepository` eine `StorageException`; auch ein leeres Optional hat keinen Pfad. | übernommen | Fehlerpfad fehlt (B3 F23). Umsetzung: alt-Zweig `StorageException` → `photoNotSaved()` (FA-06, keine neue Meldung); leeres Optional als verletzte Vorbedingung von UC-01 vermerken. |
| 3 | Kopplungs-Check: `AddPhotoUseCase` hat sechs Abhängigkeiten (drei Ports, `Album`, `Photo`, `ErrorMessages`), mehr als die Faustregel erlaubt (B3 F18). Einen `PhotoImporter` (prüfen, `Photo` erzeugen, kopieren) abspalten. | **verworfen** | `store` muss zwischen `addPhoto` und `save` laufen und bei 5a zurückgenommen werden; ein Importer brächte eine Indirektion mehr bei kaum weniger Kopplung (B3 F34). |
| 4 | Kopplungs-Check: `ErrorMessages` nimmt die Zahlen aus den Domänenkonstanten (Notiz), das Diagramm zeigt diese Abhängigkeiten aber nicht. | übernommen | Macht NFA-05 im Diagramm sichtbar (AK-N05.1). Umsetzung: `ErrorMessages ..> Album`, `AlbumName`, `Photo` («use»). |

## Fall 4 – Tests aus Akzeptanzkriterien erzeugen, Block 5

| Feld | Inhalt |
|---|---|
| **Aufgabe/Ziel** | Für FA-02 und UC-01 den Testentwurf (Äquivalenzklassen, Grenzwerte, Entscheidungstabelle) und JUnit-Tests aus den AK erzeugen lassen; prüfen, ob die Assertions das Kriterium wirklich prüfen (B5 F19, F32). |
| **Werkzeug/Modell** | Claude Code (Anthropic), claude-opus-5-5, mit drei Subagenten als Prüfern · Prompt: Arbeitsauftrag im Chat, nicht versioniert (Abweichung von PB §13) |
| **Ergebnis** | PR #12: TESTING.md, `AlbumNameTest`, `AddPhotoDecisionTableTest`, `Album.addPhoto`, CI. |
| **Kritische Bewertung** | Mehrere Tests prüften weniger, als Name oder AK versprachen. R1 prüfte „Album unverändert“, obwohl das Album gar nicht beteiligt war. Die Zählweise (A-13) und „erst trimmen, dann zählen“ (A-02) prüfte kein Test. AK-07.2 hing an einem Test mit 100 statt 1 Foto. Ohne Duplikatprüfung wurde r2 nur zufällig rot. Erkannt haben wir das durch den Abgleich der Subagenten mit REQUIREMENTS und durch die Mutationen aus B5 F20. |
| **Entscheidung** | angepasst: Assertion in R1 gestrichen, Test für A-13 ergänzt, Grenzwerte mit Leerzeichen am Rand, r2 mit 1 und 100 Fotos. Die Bestätigung durch das Team steht im Review von PR #12 aus. |
