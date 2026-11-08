# Leitfaden für PR-Reviews

Jeder Reviewer prüft die Änderungen selbst und hält sein Ergebnis im PR fest. Bei Block 2–6 reviewen die vier anderen Teammitglieder den Blockbearbeiter. Bei Block 7 und dem Abschlussreview reviewen alle. Zum Blockabschluss prüft auch der Bearbeiter den gesamten Stand; seine Selbstprüfung zählt nicht als PR-Freigabe.

## 1. Review vorbereiten

1. Ziel und Umfang des PR sowie das verknüpfte Issue lesen. Die Aufgaben des Blocks in der [ROADMAP](../../ROADMAP.md) zum Abgleich verwenden.
2. Alle geänderten Dateien im Diff lesen. Bei Bedarf die vollständige Datei und zugehörige Anforderungen, Diagramme oder Entscheidungen öffnen.
3. Die betroffenen Dokumente in der Vorschau ansehen; Links und Diagramme prüfen. Bei ausführbaren Änderungen die passenden Abläufe und Tests selbst prüfen. Sobald der Build eingerichtet ist, den Maven Wrapper verwenden (`./mvnw verify`, unter PowerShell `.\mvnw.cmd verify`).

## 2. Inhalt prüfen

Die Punkte gelten, soweit sie vom PR betroffen sind. Fachliche Fragen zuerst klären, Formatierung zuletzt.

| Bereich | Prüffragen |
| --- | --- |
| Vollständigkeit und fachliche Korrektheit | Sind die vereinbarten Aufgaben erfüllt? Passen die Änderungen zu Anforderungen und Akzeptanzkriterien, einschließlich Normal-, Fehler- und Grenzfällen? |
| Konsistenz | Stimmen Texte, IDs, Diagramme, Prototyp, Code und Tests miteinander überein? Sind offene Fragen als offen erkennbar und Annahmen von bestätigten Entscheidungen getrennt? |
| Verantwortlichkeiten und Entwurf | Passt die Änderung zur Architektur und den ADRs? Liegt die Logik in der richtigen Schicht? Sind neue Abhängigkeiten oder Entwurfsmuster begründet? |
| Tests und Nachweise | Prüfen die Tests das geforderte Verhalten und relevante Risiken? Prüfen Fehlerfälle auch den Zustand? Sind angegebene Ergebnisse tatsächlich belegt? |
| Fehler und Sicherheit | Werden ungültige Eingaben und Datei-/Speicherfehler sinnvoll behandelt? Sind Datenverlust, unbeabsichtigte Änderungen und veröffentlichte Zugangsdaten ausgeschlossen? |
| Verständlichkeit und Stil | Können die anderen Teammitglieder Inhalt und Entscheidungen erklären? Gibt es Dopplungen oder unklare Formulierungen? Stimmen Darstellung und Konventionen? |

Die Projektbeschreibung und die vereinbarten projektbezogenen Folienaufträge bilden die Grundlage. Das Team kann den Umfang um Funktionen ergänzen, die zum Projektziel passen. Im Review wägen wir ihren Nutzen und den Aufwand für Umsetzung und Tests ab. Ein Papierkorb ist zum Beispiel sinnvoll, weil Nutzer versehentlich gelöschte Fotos und Alben wiederherstellen können.

## 3. Rückmeldungen schreiben

- Befunde möglichst direkt an der betroffenen Zeile kommentieren. Übergreifende Fragen gehören ins PR-Review; eigenständige Folgeaufgaben ins passende Issue.
- Fundstelle, Anforderung oder Prinzip und die Auswirkung nennen. Als konkrete Frage formulieren und möglichst einen Verbesserungsvorschlag ergänzen.
- Rückmeldungen als notwendige Korrektur, offene Frage oder Verbesserungsvorschlag kennzeichnen. Bei einer notwendigen Korrektur erklären, warum sie vor dem Merge nötig ist.
- Keine künstlichen Befunde suchen. Wenn etwas passt, darf das Review ohne Änderungswunsch enden. KI-Unterstützung ersetzt die eigene Prüfung nicht.

**Beispiel:** „Wird das Hinzufügen auch bei bereits 10.000 Fotos abgelehnt? Das verlangt FA-07. Könnten wir diesen Grenzfall mit einem Test absichern?“

### Mehrere Reviews

Jeder Reviewer prüft den PR selbst und schreibt ein eigenes Gesamtreview. Die Bewertungen dürfen sich dabei überschneiden.

Zuerst das Gesamtreview formulieren und neue Korrektur- oder Verbesserungspunkte als zugehörige Threads darunter bündeln. Auf GitHub dafür die Zeilenkommentare mit „Start a review“ bzw. „Add review comment“ sammeln und gemeinsam mit dem Gesamtreview über „Review changes“ und „Submit review“ veröffentlichen.

- Wer das erste Review veröffentlicht, legt für jeden eigenständigen Korrektur- oder Verbesserungspunkt einen eigenen Thread an.
- Wer das zweite Review veröffentlicht, berücksichtigt die offenen und gelösten Threads der ersten Person. Beim dritten Review zählen die Threads der ersten beiden, beim vierten die Threads der ersten drei.
- Neue Befunde bekommen einen eigenen Thread. Wer zu einem offenen Punkt etwas ergänzen kann, antwortet im vorhandenen Thread. Bei gelösten Punkten die Korrektur prüfen und den vorhandenen Thread wieder öffnen und dort kommentieren, falls das Problem weiter besteht.

Der Blockbearbeiter beantwortet die Kommentare, übernimmt nötige Korrekturen oder begründet eine andere Lösung. Die Reviewer prüfen die betroffenen Änderungen erneut; ein geschlossener Thread allein belegt keine Korrektur.

## 4. Review abschließen

Das abschließende Review nennt kurz den geprüften Umfang, die tatsächlich ausgeführten Prüfungen und das Ergebnis. Nicht ausgeführte Prüfungen und verbleibende Einschränkungen offen nennen.

```text
Geprüft: [Dateien oder Aufgaben]
Prüfung: [Abgleich, manuelle Abläufe oder Tests mit Ergebnis]
Ergebnis: [keine offenen Befunde oder konkrete offene Punkte]
```

- **Approve:** Der geprüfte Stand erfüllt die vereinbarten Aufgaben; es gibt keine offenen Befunde, die den Merge verhindern.
- **Request changes:** Vor dem Merge sind konkrete Korrekturen erforderlich.
- **Comment:** Rückfragen oder Zwischenfeedback, noch keine abschließende Freigabe.

Vor dem Merge sind die vier Freigaben der anderen Teammitglieder erforderlich. Nach weiteren Änderungen die betroffenen Punkte erneut prüfen und das Review bei Bedarf aktualisieren. Ab Block 4 müssen zusätzlich die CI-Pflichtprüfungen grün sein. Eine grüne CI ersetzt das inhaltliche Review nicht.

„Reviewed“ im Issue erst nach der eigenen abschließenden Prüfung abhaken. Den gesamten Block erst gemäß Blockabschluss in der ROADMAP als erledigt markieren.

Grundlagen: [Projektbeschreibung §9](../task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4), [Block 4: Code Review](../lecture_slides/Block_4_Implementierung_und_Versionsmanagement.md#4-code-review-und-continuous-integration) und [Arbeitsverteilung und Blockabschlüsse](../../ROADMAP.md).
