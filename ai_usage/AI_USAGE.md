# AI_USAGE – Einsatz von KI-Werkzeugen

Substanzielle KI-Einsätze nach PB §13. Prompts liegen unter `ai_usage/prompts/`.

## Fall 1 – Kritisches Review der Anforderungen („Grill me“), Block 2

| Feld | Inhalt |
|---|---|
| **Aufgabe/Ziel** | REQUIREMENTS.md v1 vor der Abgabe auf Lücken, Widersprüche, fehlende Grenzfälle und unklare Begriffe prüfen lassen. |
| **Werkzeug/Modell** | _<Produkt und Modell eintragen>_; zweiter KI-Chat zur Formulierung der Antworten: _<Produkt und Modell eintragen>_ · Prompt: [`prompts/grill-me.md`](prompts/grill-me.md) (B2 F36) · Eingaben: REQUIREMENTS.md v1 und Projektbeschreibung |
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
| 14 | Grenzwerte (50, 500 Zeichen, 20 MB, JPEG/PNG, SHA-256) beim Auftraggeber erfragen. | **verworfen** | Die Auftraggeber-Sprechstunde (B2 F42) hat für unser Team nicht stattgefunden, in Moodle liegen keine Antworten vor; PB §4 sieht dafür dokumentierte Annahmen vor. Bestätigte Werte wandern nach 7.1. |
| 15 | NFA-04 und Stakeholder-Anliegen messen Verschiedenes; Herkunft von 60 s und 4/5 fehlt. | angepasst | Anliegen angeglichen; Herkunft A-16. |

**Erkenntnis:** Stark bei Messbarkeit und Begriffsklärung (SOPHIST-Regeln), aber mit Hang zu immer mehr Absicherung. Wo der Projektumfang endet, mussten wir selbst entscheiden.

## Fall 2 – Überarbeitung zur Abgabefassung, Block 2

| Feld | Inhalt |
|---|---|
| **Aufgabe/Ziel** | REQUIREMENTS.md v1.2 aus v1.1 erstellen (Konsistenz, Prüfbarkeit, Kürzung). |
| **Werkzeug/Modell** | Claude Code (Anthropic), claude-opus-5-5; mehrere KI-Reviewrollen und ein abschließender Prüfer. Prompt: Arbeitsaufträge im Chat, nicht versioniert (Abweichung von PB §13). |
| **Ergebnis** | REQUIREMENTS.md v1.2 (siehe Änderungshistorie dort). |
| **Kritische Bewertung** | Zutreffend: fehlende AK für NFA und SR-01, FA-06/FA-10 ohne Ablehnung, falsche Quelle in 6.1, Zählfehler in Fall 1. Mehrere Vorschläge hätten Teamentscheidungen still geändert oder den Umfang erweitert. |
| **Entscheidung** | Übernommen bzw. angepasst wie in v1.2. Verworfen: Liste offener Auftraggeberfragen (widerspricht Fall 1 #14), Dateiauswahl statt Pfadeingabe (Teamentscheidung UC-01), Speicherfehler-Fall für alle Aktionen (Umfang, Meldungen ohne Quelle). |
