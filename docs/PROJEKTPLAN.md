# Projektplan Photo Album (v3, Vorschlag an Felix und das Team)

**Grundlage:** Grundlage ist allein die **Projektbeschreibung v3 (PB)**. Das Projekt ist weitgehend losgelöst von der Vorlesung und läuft unabhängig von ihrem Fortschritt. Folien sind nur Hilfsquelle; was nur aus Übungen stammt, steht in §9.

**Ziel:** Jedes Paket ist einem Bewertungspunkt zugeordnet (PB §16.1, §16.2). Was keine Punkte bringt, entfällt (PB §2).

**Zeit:** Es gibt **keine Termine und keinen festen Zeitrahmen**, nur Reihenfolge und Abhängigkeiten. Wir arbeiten so schnell wie möglich, Pausen sind jederzeit möglich. Die Blöcke gruppieren die Abgaben. Bewertet wird der Stand zum jeweiligen Tag, Verbesserungen zählen bis `final` (PB §3).

**Kürzel:**
- PB §n = Projektbeschreibung
- B1 Fn = Folie aus Block 1 (PDF-Seite); B2/B3 Fn = Folie aus Block 2/3 (gedruckte Nummer)
- FA, NFA, AK, AE, A, T, F = IDs aus REQUIREMENTS.md
- PR #n = Draft-PR in `felix-henry/photo-album-projekt`
- [Vorgabe] steht in der PB; [Vorschlag] beschließt das Team

> **Einstieg für jede Person**
> 0. `git config user.name` setzen und `user.email` auf die Adresse, die im eigenen GitHub-Konto verifiziert ist. Committet wird nur über das eigene Konto (PB §3: Die Git-Historie wird bewertet).
> 1. GitHub-Namen an Felix geben und die Einladung annehmen.
> 2. Java 21, IDE, `./mvnw verify` (Windows: `mvnw.cmd verify`) auf dem Branch `build/projektgeruest`. Das Ergebnis als Kommentar in PR #4 posten.
> 3. Im A1-Issue eintragen: deine Rangfolge der fünf Bereiche (§5) und ein Phase-A-Paket per Checkbox; nach der Bereichszuteilung dein Bündel.
>
> Für dich reichen deine Zeile in §5 sowie die DoD und die Spielregeln in §3.

---

## 1 Wofür es Punkte gibt [Vorgabe]

**Note** = Projektartefakte × 0,5 + Präsentation × 0,3 + Mitmachen × 0,2 (PB §16).

| Bereich (PB §16.1) | Punkte | Wo im Plan |
|---|---|---|
| Requirements: FA/NFA testbar (5), AK/Use Cases (4), Konsistenz (3), **Auftraggeber-Feedback eingearbeitet (3)** | 15 | PR #2, A6, **A6c** |
| Architektur: angemessen (6), Kohäsion/Kopplung (5), UML (4), **Entscheidungen/ADR (5)** | 20 | PR #3, A8, DoD 4 |
| Implementierung: **Pflichtfunktionalität (8)**, wartbar (5), Modularisierung (3), Fehlerfälle (2), Konventionen (2) | 20 | Bereiche 1–5 |
| Testing: **≥ 10 Unit (6)**, ≥ 2 Integration (4), **Blackbox systematisch (5)**, Normal/Fehler/Grenze (4), Coverage (3), Regression (3) | 25 | DoD, C-Pakete, Bereiche |
| Prozess: **Historie zeigt Zusammenarbeit (3)**, Branches/PRs/Review (3), CI (3), Struktur (1) | 10 | Einstieg, §3 |
| Weiterentwicklung: Change Request (3), Refactoring (2), Risiken/Release (2), KI kritisch (3) | 10 | D, E, KI in §3 |

**Präsentation, 100 Punkte (PB §16.2):**

| Teil | Punkte | Bewertet wird |
|---|---|---|
| Struktur | 15 | roter Faden „Anforderung → Entscheidung → Test → Änderung“, Zeit eingehalten |
| Reflexion | 25 | Alternativen und Schwächen benannt, Fehleinschätzungen zugegeben |
| Visualisierung/Demo | 15 | |
| Auftreten | 15 | |
| **Diskussion** | **30** | Jede Person erklärt das **Gesamtprojekt** |

## 2 Stand

| Block | Pflicht (PB §3) | Stand |
|---|---|---|
| 1 | Team, Repository, README, Vorgehensentscheidung | PR #1 |
| 2 | REQUIREMENTS.md v1, UI-Prototyp, AI_USAGE.md Fall 1 | PR #2 (inkl. AI_USAGE.md, Fall 1 noch leer) |
| 3 | ARCHITECTURE.md, Klassen- und Sequenzdiagramm, ≥ 1 ADR, Projektgerüst | PR #3, PR #4 (`./mvnw verify` grün) |

- Die Entwürfe zu Block 1–3 hat Pit mit Claude Code erstellt (AI_USAGE Fall 3). Damit die Historie alle zeigt, übernehmen **die anderen** Einarbeitungen, Bestätigungen und Reviews. Pit committet nach A0 nicht mehr auf PR #1–#4.
- „Grill me“ ist in PB §13 nur ein Beispiel. Pflicht ist AI_USAGE Fall 1 (PB §3) und **mindestens ein verworfener Vorschlag über alle Fälle** (PB §13, §18). „In Fall 1 verwerfen“ stammt aus einer Übung (B2 F44).

## 3 Arbeitsweise

**Vorgehen [Vorschlag zur Vorgehensentscheidung, PB §3]:**
- Die Anforderungen ändern sich während des Projekts (PB §1, §4). Deshalb arbeiten wir **iterativ-inkrementell im Kanban-Fluss**, ohne feste Intervalle. Tags sind Lieferzustände (Hilfsquelle B1 F20, F31, F34–F36).
- Rollen als bewusst gewählte Zuständigkeiten: **Felix** ist Product Owner (Backlog, Kontakt zur Dozentin, Admin), **Pit** ist für den Prozess verantwortlich, alle entwickeln.
- Scrum mit festen Sprints ist verworfen: Die Anforderungen ändern sich laufend (PB §1, §4), und die Block-Tags sind Lieferzustände, keine Iterationsgrenzen.

**Board:**
- Spalten: To do | In Arbeit | Review | Fertig. Je Person höchstens **ein** Paket „In Arbeit“; Reviews zählen nicht mit.
- Wer ein Paket sucht, nimmt zuerst ein offenes Review, dann ein Paket, auf das jemand wartet, dann eins aus dem eigenen Bereich.
- Wer ein Issue schließt, schiebt die davon abhängigen Pakete nach „To do“.
- Die Issues der Phasen B–E legt die Bereichs- bzw. Bündelperson nach A1 (c) an. Danach sind Board und Issues maßgeblich, nicht dieser Plan.

**Ablauf [Vorgabe PB §9]:**
- Issue → Branch → Pull Request → Review → CI → Merge.
- Commits konventionell mit Anforderungs-ID (FA/NFA/KI) oder Issue-ID; kein „fix“ oder „WIP“ auf `main`.
- Die CI ist Teil des Ablaufs ab B1 (PB §9, Block 4); PR #1–#4 werden noch ohne CI gemergt.
- Mindestens ein dokumentiertes **Code Review je Person**.

**Definition of Done für Code-PRs [Vorschlag, gestützt auf PB §10]:**
1. Die AK der FA sind im selben PR automatisiert getestet: Normal- und Fehlerfall je muss-FA, Grenzfall je Geschäftsregel. Der Grenzwert steht als Literal im Test. Der Fehlerfall prüft den unveränderten Zustand. Der Testname nennt die AK-ID.
   - NFA-AK laufen über C3–C5 bzw. das CI-Log.
   - Reine UI-AK werden manuell geprüft und mit einer Zeile in TESTING.md belegt.
   - Tests gegen das echte Dateisystem tragen das Integrations-Tag.
2. Ändert der PR Ports, Use Cases oder Domänenklassen, zieht er `docs/design` und ARCHITECTURE.md im selben PR nach (UML 4 P., Konsistenz 3 P.).
3. Die Maske des Use Case darf als eigener PR folgen, sobald der UI-Rahmen (B4) steht.
4. Eine Zusatztechnologie braucht ein ADR und eine CI ohne manuelle Installation (PB §6). Ein neues Pattern braucht ein ADR (PB §8).
5. CI grün, eine unabhängige Freigabe, Issue geschlossen.

**Review-Checkliste:**
- Ist der PB-Paragraf genannt? Ist jeder Wert belegt oder als Annahme markiert?
- Doku-PR: zusätzlich die Prüfhinweise 1–5 aus AI_USAGE Fall 3.
- Code-PR: DoD erfüllt?

**KI [Vorgabe PB §9, §13]:**
- AI_USAGE.md mit ≥ 3 substanziellen Fällen (empfohlen 5, je einer in Block 2–6, Tätigkeit frei), mindestens einer begründet verworfen.
- Prompts, Skills und Modellkonfigurationen liegen versioniert im Repo.
- PRs von Coding Agents durchlaufen denselben Review-Prozess und tragen das Label „ai-assisted“, das gilt auch für PR #1–#4.
- [Vorschlag] **Die Umsetzung des Labels klärt Pit mit Felix; das Ergebnis steht im A1-Issue.** PR-Beschreibungen enthalten keinen KI-Hinweis. Eine Abweichung wird offen in AI_USAGE dokumentiert.

**Spielregeln [Vorschlag]:**
1. Pausen sind jederzeit möglich. Wer pausiert, schreibt eine Übergabenotiz ins Issue und legt das Paket nach „To do“ zurück; ziehen darf es dann jede Person.
   - Wer wartet und keine Notiz findet, fragt im Issue nach.
   - Bleibt die Antwort aus, übernimmt die Vertretung (Review-Ring, Regel 2), sobald ihr eigenes Paket fertig ist. Sonst vergibt Felix das Paket neu.
2. **Freigeben darf nur, wer im PR keinen eigenen Commit hat.**
   - Ring: Den ersten Code-PR von Bereich n reviewt Bereich n+1 (5 → 1); diese Person ist auch die Vertretung.
   - Wer noch kein Code-Review hat, hat Vorrang.
   - Für PR #2 und PR #3 sind zwei Reviews erwünscht, aber keine Pflicht.
3. Entscheidungen fallen im Issue. Beschlossen ist, was mindestens drei Personen unterstützen und wogegen kein Widerspruch mit Gegenvorschlag offen ist. Im Patt entscheidet Felix, die Gegenposition bleibt im Issue; ist Felix in Pause, übernimmt Pit die PO-Fragen.
4. Gemergt wird per **Merge-Commit**. Auf gemeinsam genutzten Branches kein Rebase und kein Force-Push. Bei Paararbeit `Co-authored-by:` setzen.
5. Gemeinsame Dateien:
   - `Main` gehört Bereich 5, die anderen ergänzen nur ihre Verdrahtung.
   - `pom.xml` gehört Bereich 1; eine neue Abhängigkeit nur mit ADR; Release-PRs ändern dort nur die Version.
   - TESTING.md hat einen Abschnitt je Bereich.
   - CHANGELOG ändern nur Release-PRs.
   - Änderungen an Ports, `Result`, `Album` oder `Photo` nach B3a als eigener kleiner PR, Review durch Bereich 1.
6. Releases:
   - Der Release-PR enthält den CHANGELOG-Eintrag und setzt die pom-Version auf die Tag-Version.
   - Das Release-Issue nennt Commit, CI-Lauf und erfüllte Kriterien (das ist der Beleg für die Quality Gates in E1).
   - Getaggt wird der Merge-Commit auf `main`, in aufsteigender Reihenfolge, und nie verschoben. Fehler korrigiert ein Revert-PR.
7. A2a prüft, ob eine zweite Admin-Person möglich ist. In einem persönlichen Repo geht das voraussichtlich nur über eine Organisation; sonst entfällt diese Regel.
8. Alles, was von außen kommt (Auftraggeber-Eingang, Change Request), blockiert kein internes Paket. Fehlt zu einem Tag ein externer Teil, wird trotzdem getaggt. Die Lücke ist ein Issue und kommt per Folge-PR bis `final`.

## 4 Arbeitspakete

### Phase A – Block 1–3 abschließen

| AP | Inhalt | Wer | Hängt ab von |
|---|---|---|---|
| A0 | AI_USAGE.md in PR #2 (erledigt); Board und Phase-A-Issues anlegen, je PR verlinken | Pit | – |
| A1 | Issue mit drei Teilen: **(a)** Repo öffentlich oder GitHub Pro, ADR-004 Maven; **(b)** Vorgehen und Spielregeln; **(c)** Bereichswahl und Bündel | alle, Moderation Felix | A0 |
| A2a | Alanur und Alex einladen; zweite Admin-Person prüfen | Felix | – |
| A2b | Branch-Schutz `main` (PR erforderlich) | Felix | A1 (a) |
| A4 | `./mvnw verify` bei jeder Person (Einstieg 2) | alle | A2a |
| A9a | PR #4 (Gerüst) reviewen und mergen | nicht Pit | A1 (a), A2b |
| A5 | PR #1: README auf Kanban-Fluss umstellen; streichen: „Sprint = Blockzyklus“, „Reines Kanban verworfen“, Scrum-Rollen, Checkpoint-Backlog; Merge, Tag `block-1` | nicht Pit | A0, A1 (b), A2b |
| A6a | AI_USAGE Fall 1 „Grill me“: Antworten von mindestens zwei Personen außer Pit, ehrlich bewerten | ≥ 2 Personen | A0, A2a |
| A6b | T-01–T-05, A-01–A-08 und NFA-Werte bestätigen; Referenzgerät eintragen. Maßstab: **Keine NFA erzwingt eine Funktion außerhalb PB §5.1** (PB §2): NFA-04 straffen oder streichen (nur hier entschieden), NFA-01 ohne Vorschaubilder erfüllbar machen; nach der Messung wird an einer NFA nichts mehr verschoben | 1 Person | A2a |
| A7 | PR #2 einarbeiten: A6a, A6b, AK für die untere Grenze von FA-01; in §8 den Angriff auf den `DescriptionSuggester` umschreiben, KI-02 ohne `shareAlbum`. Merge, Tag `block-2` | nicht Pit | A0, A5, A6a, A6b |
| A8a | Status ADR-001–ADR-005 festlegen. Entscheiden: `validate` (ADR-002), S-2 passend zu A6b, T-02/ADR-005 (Vorschlag: Won't, Entwurf bleibt nach PB §8). Fall 2 Nr. 1–5 vom Team bestätigen (damit ist der verworfene Vorschlag Nr. 2 eine Teamentscheidung). | 2 Personen | A1 (a), A2a, A6b |
| A8b | UI-Technik (JavaFX, Swing oder Konsole, PB §6) als ADR mit verworfener Alternative [Vorschlag]; vorbereitet von Bereich 5 | Bereich 5 | A1 (c) |
| A9b | PR #3 einarbeiten: vorher `main` in den Branch mergen, AI_USAGE.md zusammenführen; ARCHITECTURE §7 Nr. 2–4 (Übungs-Agent „Fotos aufräumen“) streichen. Merge, Tag `block-3` | nicht Pit | A0, A7, A8a, **A9a** |
| A9c | Folge-PR „AI_USAGE Fall 3 Teambewertung“: jede reviewende Person ein Commit; die Entscheidung schreibt eine Person außer Pit | Reviewende | A9b |
| A6c | **Auftraggeber-Eingang** (Moodle-Antwort, Feedback zu den Abgaben, Sprechstunde, Antwort auf A11): Felix legt je Eingang ein Issue an. Der betroffene Bereich arbeitet ihn als eigenen PR ein (AE/A, AK, Konstanten, Tests, TESTING), alter Wert mit Verweis in REQUIREMENTS §6. Bei „Das entscheiden Sie“ bleibt die Annahme stehen (PB §4). | Felix + Bereich | Eingang |
| A11 | An die Dozentin: Ist die Teamgröße 5 in Ordnung (PB §3: 3–4; §16.2: „alle vier sprechen“)? Antworten zu F-01–F-03? Abgrenzung `v0.1.0` zu `v1.0.0`? Felix prüft Moodle nach jedem Block, Vertretung ist jede andere Person. | Felix | – |

### Phase B – Pflichtfunktionalität (PB §3 Block 4, §5, §9)

| AP | Inhalt | Hängt ab von |
|---|---|---|
| B1 | CI (GitHub Actions): `./mvnw verify` mit Java 21 bei jedem PR, Unit- und Integrationstests, JaCoCo. Felix setzt den Check als Pflicht. | A9a, A1 (c) |
| B2 | AGENTS.md: Regeln für Coding Agents, Ergebnis der Label-Klärung, Prompts und Modellkonfigurationen unter `prompts/`, Pattern nur mit ADR, AK-IDs in Testnamen | A1 |
| B3a | **Tor 1 (für die Use Cases):** Ports (inklusive Folgen aus `validate` und S-2), `Result` mit Wert, Fehlertyp der Ports (ADR-003), InMemory-Fakes mit Fehlerinjektion und Kopien, Domänen-API (`removePhoto` meldet unbekannte ID nach FA-10; Foto über `Album` beschreiben), Klassendiagramm | B1, A8a (ADR-001–003, `validate`, S-2) |
| B3b | Getrennte Meldungen für AK-01.1/01.4 und AK-07.3/07.5, Grenzwerttests der Domäne | B3a |
| B4 | **Tor 2 (für die Masken):** UI-Rahmen mit Main/Composition Root, Albumübersicht und Albumansicht, zunächst mit Fakes verdrahtet | A8b, B3a |
| B5 | **Tor 3 (für Integration und Lauffähigkeit):** Datei-Adapter mit dokumentiertem Format | B3a |
| Bereiche | Use-Case-PRs je Bereich laut §5; Code-PRs mit Coding Agent erst nach B2 | A1 (c), B3a; Masken zusätzlich B4 |
| B9 | Release `v0.1.0`: Pflichtfunktionalität lauffähig, Build, PRs mit Review, CI grün, AGENTS.md. Tag `block-4` (PB §3) | Bereiche, B2, B5, A9b |

### Phase C – Testen (PB §3 Block 5, §10)

| AP | Inhalt | Hängt ab von |
|---|---|---|
| C1 | TESTING.md mit **2 Blackbox-Verfahren** (PB §3): Grenzwertanalyse (Bereich 2) und Entscheidungstabelle UC-02/FA-08 (Bereich 3). Klassen und Grenzen werden aus FA, PB §5.2 und A-01–A-05 abgeleitet und dann den AK zugeordnet. Traceability AK → Verfahren → Testmethode samt Testebene. Fehlende Fälle gehen als Issue an den Bereich der FA. | A7 |
| C2 | Lücken bis ≥ 10 sinnvolle Unit-Tests schließen (die meisten entstehen über die DoD) | C1, Bereiche |
| C3 | ≥ 2 deterministische Integrationstests: **IT1 AK-10.1** (Speicherfehler, auch nach Neustart unverändert; Bereich 4), **IT2 AK-09.1** (Löschen entfernt gespeicherte Dateien, Originale bleiben; Bereich 3); Ersatz AK-05.3 mit echtem Hasher. Zusätzlich NFA-Nachweise AK-N02.1 (Bereich 4) und AK-N04.1 (Bereich 3, nur falls NFA-04 bleibt). Testdateien werden im Test erzeugt. | B5, Bereiche 2, 3 |
| C4 | Usability-Test NFA-05 mit Protokoll; Vorlage und Testpersonen aus der Zielgruppe gleich nach A6b vorbereiten | A6b, alle Masken |
| C5 | Security-Tests für Dateieingaben (Bereich 2); Performance NFA-01 (Bereich 5). Laut PB §10 Block 5–6, spätestens vor E1 | Bereich 2; A6b, B4, B5 |
| C6 | Evaluation Set für den entworfenen `DescriptionSuggester` (PB §8, §10). Keine Zahl der KI ohne Quelle. | A8a |
| C7a | Release `v1.0.0` (Pflichtfunktionalität komplett und getestet) | B9, Bereiche, C3, CI grün |
| C7b | Tag `block-5` | C1–C4, C6, C7a |

### Phase D – Weiterentwicklung (PB §3 Block 6, §10–§12)

| AP | Inhalt | Hängt ab von |
|---|---|---|
| D1 | Coverage (JaCoCo) je Schicht interpretieren; hohe Coverage ist kein Qualitätsnachweis | C2, C3 |
| D2 | `docs/REFACTORING.md`: Klasse, Metrik und Smell mit Nachweis. Den Gegenstand möglichst so wählen, dass er den CR erleichtert. Erst Absicherungstests, jeder Schritt mit grünen Tests; Refactoring-Commits ändern kein Verhalten und bleiben von fachlichen Commits getrennt (PB §12) | D1; Auswahl nach D3 |
| D3 | CHANGE_REQUEST.md, **Impact Analysis vor der Umsetzung** (PB §11): Anforderungen/AK (betroffen und neu), Architektur/Datenmodell, ungültige und neue ADRs, Tests rot und neu (mit IDs), Risiken, Aufwandsschätzung je Teil, Reihenfolge. Klären: Bleiben vorhandene Daten lesbar? Sonst Migration und Rückweg auf `v1.0.0` mit Test „v1.0.0-Daten unter v1.1.0“ (Bereich 4) | Bekanntgabe CR |
| D4 | Umsetzung in der Reihenfolge aus D3, jeder Schritt als PR mit grüner Suite. REQUIREMENTS, ARCHITECTURE, `docs/design`, ADRs und TESTING werden mitgezogen; tatsächlichen Aufwand je Teil festhalten. **Teilumsetzung ehrlich dokumentieren ist zulässig** (PB §11). `v1.1.0` mit dem umgesetzten Stand | D3, D2, C7a, C7b |
| D5 | Rückblick: geschätzt vs. tatsächlich, Überraschungen; Abschnitt „Regression“ in TESTING.md (D2-Tests, D3-Liste rot/neu gegen Ist, deaktivierte Tests mit Issue). Tag `block-6` | D4, C7b |

### Phase E – Abschluss (PB §3 Block 7, §14, §16.2)

| AP | Inhalt | Hängt ab von |
|---|---|---|
| E1 | RISK_ANALYSIS.md: ≥ 3 Risiken (≥ 1 Produkt-, ≥ 1 Projektrisiko) mit Matrix und Begründung, Maßnahmen mit Verantwortlichen, 4–6 Quality Gates mit Beleg (Release-Issues), Rollout (Strategie, Migration, Rollback), KI-Governance | Beginn nach C7a; fertig nach C5, C7b und D4 |
| E2 | Release-Entscheidung ja / nein / unter Bedingungen, mit Bezug auf Matrix und Gates; „Nein, weil …“ wird nicht schlechter bewertet | E1 |
| E3a | Präsentation als **eine Geschichte** entlang der sechs Pflichtfragen (PB §16.2). Jede Person übernimmt mindestens eine Frage. Dazu ein Diagramm, das eine Entscheidung erklärt, Demo ≤ 90 s auf `v1.0.0`, Fragen 2, 3 und 5. Im Issue wächst laufend die Liste „Schwächen und Fehleinschätzungen“ (verworfene Alternativen, §7, D5, offene Gates, verworfene KI-Vorschläge). | C7a |
| E3b | Fragen 1, 4 und 6, höchstens 8 Folien, PDF in `docs/presentation/`, Probedurchlauf mit hartem Stopp bei 10 min | D5, E2 |
| E4 | Diskussion (30 P.): Sobald ein Bereich gemergt ist, erklärt **eine andere Person** ihn nach dem Raster Anforderungen, Architektur, QS | je Bereich |
| E5 | Checkliste PB §18, README final (Zweck, Build, Run, Team, Versionen), Tag `final` **nach der gehaltenen Präsentation** | D5, E1–E4 |

**Sechs Pflichtfragen (PB §16.2):**
1. Wie haben sich die Anforderungen verändert?
2. Welche Architekturentscheidung war wichtig?
3. Welcher Testfall ist interessant und wie wurde er hergeleitet?
4. Welche Auswirkungen hatte der Change Request?
5. Wie wurde KI eingesetzt und was wurde verworfen?
6. Würde das Team heute releasen?

## 5 Entwicklungsbereiche zum Aussuchen [Vorschlag]

**Wahl:**
- Jede Person gibt im A1-Issue (c) eine **Rangfolge** der fünf Bereiche ab. Eindeutige Erstwünsche gelten.
- Wollen mehrere denselben Bereich, einigen sie sich im Issue, sonst entscheidet das Los.
- Pits Wunsch ist Bereich 1, weil die Entwürfe zu Block 1–3 von ihm sind. Das gilt nur als seine eigene Rangfolge.
- **Grundregel: Eine Klasse gehört genau einem Bereich.**
- **Testebene:** Bereich 1 testet Regeln und Grenzfälle direkt an `Album`/`Photo`. Die Use-Case-Bereiche testen Normal- und Fehlerfall mit unverändertem Zustand gegen das Repository-Fake.

| Bereich | Entwicklung | Tests, QS, Doku | Vor B3a schon möglich | Umfang | Punkte vor allem aus |
|---|---|---|---|---|---|
| **1 Kern & CI** | B1 CI, B3a Ports/Fakes/Domänen-API, B3b; besitzt `pom.xml` | Domänentests AK-01/05/06/07 (Regelebene), **AK-N03.1**, D1 Coverage, Klassendiagramm | B1 | 0 Use Cases, 0 Adapter, 0 Masken; CI und Kern | CI (3), Modularisierung (3), Unit (6), Coverage (3) |
| **2 Foto hinzufügen (UC-01)** | `AddPhotoUseCase` mit Kompensation (NFA-02), `Sha256ImageHasher`, Maske | AK-03–06, **AK-10.1/10.2**, **AK-KI01.1**; Security-Tests (C5); **Grenzwertanalyse** (C1) | Tabelle zur Grenzwertanalyse | 1 UC, 1 Adapter, 1 Maske | Pflichtfunktion (8), Grenzfälle (4), Blackbox (5) |
| **3 Löschen & Entfernen (UC-02)** | `DeleteAlbumUseCase`, Foto entfernen (FA-08, FA-09), Masken mit Bestätigungsabfrage, Löschreihenfolge nach NFA-02 | AK-08, AK-09; **Entscheidungstabelle** (C1); **IT2 AK-09.1** (C3); Nachlöschen und Protokoll nur, falls NFA-04 so bleibt | Entscheidungstabelle | 2 UC, 0 Adapter, 2 Masken | Pflichtfunktion (8), Integration (4), Fehlerfälle (2) |
| **4 Speicherung & Beschreiben** | B5: `FileAlbumRepository`, `FileSystemPhotoStorage` (`store`/`read`/`delete`), Format dokumentiert (ADR nur bei Zusatzbibliothek), atomares Schreiben; Foto beschreiben (FA-07) mit Maske | **IT1 AK-10.1** (C3), AK-N02.1, AK-07 (UC-Ebene); Migration und Rückweg beim CR | Formatdokumentation | 1 UC, 2 Adapter, 1 Maske | ADR/Entscheidungen (5), Integration (4), Regression (3) |
| **5 Oberfläche & Alben** | A8b UI-ADR, B4 UI-Rahmen und Main, Album erstellen, anzeigen, umbenennen und Fotos ansehen (FA-01, FA-02), README „Run“, Demo | AK-01 (UC-Ebene), AK-02; Performance NFA-01 (C5) | A8b | 3 UC, 0 Adapter, Rahmen + 3 Masken | Pflichtfunktion (8), Use Cases bedienbar (PB §4) |

**Bündel:** Gewählt wird in umgekehrter Reihenfolge der Wunscherfüllung, sodass jedes Release eine andere Person setzt.

| Bündel | Inhalt |
|---|---|
| Q1 | AGENTS.md (B2), TESTING.md und Traceability (C1/C2), Release `v0.1.0` (B9) |
| Q2 | Usability-Test (C4), Evaluation Set (C6), Release `v1.0.0` (C7a) |
| Q3 | Impact Analysis (D3/D5), Refactoring (D2), Release `v1.1.0` |
| Q4 | RISK_ANALYSIS und Release-Entscheidung (E1/E2), Präsentations-PDF (E3b) |
| PO (Felix) | A2a, A2b, A11, Auftraggeber-Eingang (A6c), Abgabe-Checkliste und README final (E5) |

**Phase A:** Die Pakete stehen als Checkboxen im A1-Issue. Jede Person außer Pit trägt sich für mindestens eines ein. Steht der Bereich schon fest, bereitet die Bereichsperson die passende Entscheidung vor (`validate` → 2, S-2 → 3, Format → 4, A8b/A6b → 5). Entschieden wird im Team.

## 6 Meilensteine (Zustände) und Tag-Folge

| Meilenstein | Erreicht, wenn |
|---|---|
| M1 arbeitsfähig | Zugriff für alle, `main` geschützt, Gerüst gemergt, CI Pflicht (A1, A2a, A2b, A4, A9a, B1) |
| M2 Block 1–3 | `block-1`, `block-2`, `block-3` |
| M3 `v0.1.0` | B9 = `block-4` |
| M4 `v1.0.0` | C7a; danach `block-5` (C7b) |
| M5 `v1.1.0` | CR analysiert, umgesetzt oder Teilumsetzung dokumentiert, Rückblick, Coverage, REFACTORING; `block-6` |
| M6 `final` | Risikoanalyse, Release-Entscheidung, Präsentation gehalten, PB §18 erfüllt |

**Tag-Folge:** `block-1` → `block-2` → `block-3` → `block-4` = `v0.1.0` → `v1.0.0` → `block-5` → `v1.1.0` → `block-6` → `final`

## 7 Risiken

| Risiko | Maßnahme |
|---|---|
| Historie zeigt vor allem Pit (PB §16.1, 3 P.) | Einstieg 0, Phase A durch andere, Bereiche 2–5 bei den anderen, Merge-Commits |
| Privates Repo ohne Branch-Schutz (PB §6) | A1 (a), A2b |
| Label „ai-assisted“ fehlt (PB §13) | Klärung Pit/Felix, sonst Abweichung offen dokumentieren |
| Teamgröße 5 (PB §3) | A11 |
| Antworten des Auftraggebers fehlen | A6c, Spielregel 8; Werte stehen als Konstanten an einer Stelle |
| Change Request unbekannt | Ports/Adapter, D3 zuerst, D2 vorher, Rückweg `v1.0.0`, Teil-CR zulässig |
| Ausfall oder Pause | Spielregeln 1–3, Review-Ring als Vertretung |
| NFA erzwingt Zusatzaufwand (NFA-04, NFA-01) | A6b-Maßstab; ein ehrliches „nicht erfüllt“ geht in E1/E2 |
| Usability braucht externe Testpersonen | C4 früh vorbereiten; blockiert nur `block-5` |
| Gerüst weicht von den AK ab (Meldungen, `removePhoto`) | B3a/B3b |

## 8 Entscheidungsvorlage

**A1 im Issue, ohne Treffen:**
- **(a)** Repo öffentlich oder GitHub Pro? Maven bestätigen?
- **(b)** Kanban-Fluss, Rollen und Spielregeln?
- **(c)** Rangfolge der Bereiche, Bündel, Phase-A-Checkboxen.

**Zur Information:** Das Label „ai-assisted“ klärt Pit mit Felix; das Ergebnis kommt ins A1-Issue.

**A8b:** Welche UI-Technik?

**A11 an die Dozentin:**
- Ist die Teamgröße 5 in Ordnung?
- Antworten zu F-01–F-03.
- Abgrenzung `v0.1.0` zu `v1.0.0`.

## 9 Nur aus der Vorlesung (keine PB-Vorgabe; blockiert nichts)

- Git-Hausaufgabe Teil B/C: Felix legt `se2-git-group-N` aus dem Template mit allen Branches an (Public), alle nehmen die Einladung an, der Link geht in Moodle. Teil A ist mit Einstieg 0 erledigt.
- Review-Issues über Kreuz mit dem Partnerteam (B2 F33, B3 F48): Kommt eins, wird es eingearbeitet.
- Checkpoint-Selbstbewertung (B2 F43, B3 F49): Gelbe und rote Punkte werden vorhandenen Issues zugeordnet.
- Fotos der Papier-CRC-Karten (B3 F19), falls es die Karten noch gibt.
- [Annahme] Diese Punkte wirken höchstens auf „Mitmachen“. Die PB sagt dazu nichts.

## 10 Abgleich mit PB §18

| Checklistenpunkt | Pakete |
|---|---|
| Pflichtfunktionalität (`v1.0.0`), CR umgesetzt (`v1.1.0`) | Bereiche, C7a, D4 |
| 6–10 FA, 3–5 NFA, 2 Use Cases, AK, Auftraggeber-Entscheidungen | PR #2, A6a–c, A7 |
| Architektur, Klassen- und Sequenzdiagramm, ≥ 1 ADR | PR #3, A8a/b, A9b, DoD 2/4 |
| ≥ 10 Unit-, ≥ 2 Integrationstests; Blackbox und Coverage | DoD, C1–C3, D1 |
| CHANGE_REQUEST.md mit Impact Analysis und Rückblick | D3, D5 |
| REFACTORING.md | D2 |
| Git-Historie, PRs mit Reviews, CI grün, AGENTS.md | Einstieg, §3, B1, B2 |
| AI_USAGE.md mit ≥ 3 Fällen, ≥ 1 verworfen | A6a, A8a (Fall 2), A9c, Fälle 4–6 |
| RISK_ANALYSIS.md mit Release-Entscheidung | E1, E2 |
| Präsentation gehalten, PDF im Repository | E3a/b, E4 |
| Aktive Beteiligung an der Vorlesung | individuell (§9) |
| Tags `block-1` … `block-6`, `v0.1.0`, `v1.0.0`, `v1.1.0`, `final` (PB §15) | §6 |
