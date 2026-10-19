# CRC-Karten – Photo Album

Stand 19.10.2026 · Übung B3 F19 · Grundlage: UC-01, UC-02, FA, Auftraggeber-Entscheidungen (AE) und Annahmen (A) aus [REQUIREMENTS.md](../../REQUIREMENTS.md), vorläufiges Klassendiagramm des Teams (**Team-KD**), Sequenzdiagramm „Album löschen“ (B3 F25).
Quellenkürzel wie in REQUIREMENTS.md. Digitale Fassung der Papierkarten; Foto der Karten aus der Übung hier ergänzen (B3 F19). Das Ergebnis ist eine Hypothese, die das Sequenzdiagramm prüft (B3 F17).

---

## 1 Nomen-Analyse (B3 F17)

### 1.1 Kandidaten sammeln

Aus UC-01, UC-02, FA-01 bis FA-10, AE-01 bis AE-04, A-05, A-06:

Nutzerin, System, Album, Foto, Bild, Bilddatei, Datei, Originaldatei, Kopie, Dateiauswahl, Dateityp (JPEG, PNG), Dateigröße, Albumansicht, Albumübersicht, Bestätigungsabfrage, Fehlermeldung, Grund, Name, Albumname, Titel, Beschreibung, Anzahl, Höchstzahl, Duplikat, identisches Foto, Inhalt, Hash, Speicher, Albumdaten, Dateisystem, KI-Dienst, Vorschlag, Eingabe, Bedingung, Aktion, „Urlaub 2026“, „Album X“, „Strand“, „notizen.txt“.

### 1.2 Aussortieren

| Grund | Kandidaten | Verbleib |
|---|---|---|
| irrelevant: Oberfläche | Dateiauswahl, Albumansicht, Albumübersicht, Bestätigungsabfrage, Fehlermeldung, Grund | UI; Fehler kommen als fachliche Exception bzw. `Result` (B3 F13, F24) |
| irrelevant: zu allgemein | System, Eingabe, Bedingung, Aktion | – |
| irrelevant: außerhalb | Originaldatei (bleibt unverändert, A-06); KI-Dienst, Vorschlag (optional, „wird“, T-02) | – |
| irrelevant: Akteur | Nutzerin: eine Nutzerin je Installation, keine Anmeldung (A-07) | keine Klasse; „Nutzerin besitzt Alben“ (B3 F17) = alle Alben, `findAll()` |
| Instanzen | „Urlaub 2026“, „Album X“, „Strand“, „notizen.txt“; JPEG, PNG; 100, 50, 20 MB | Werte von Attributen bzw. Regeln |
| Synonyme | Bild → Foto (B3 F17); Bilddatei → Datei; Albumname → Name; Duplikat → identisches Foto; Höchstzahl → Anzahl; Albumdaten → Album; Speicher → Dateisystem | – |

### 1.3 Attribut oder Klasse?

| Kandidat | Ergebnis | Quelle |
|---|---|---|
| Album | **Klasse** `Album` (Domäne): id, Name, Fotos | B3 F17, F20 |
| Foto | **Klasse** `Photo` (Domäne): id, Hash, Speicherschlüssel, Titel, Beschreibung | B3 F17, F20; Team-KD |
| Name | Attribut von Album | B3 F20 |
| Titel, Beschreibung | Attribute von Photo | B3 F17 |
| Inhalt, Hash | Attribut `hash` von Photo; „identisch“ = gleicher Hash | B3 F13, F17; A-05 |
| Kopie | Attribut `storageKey` von Photo; die Kopie selbst liegt im Speicher | Team-KD; A-06 |
| Anzahl | abgeleitet (Größe der Fotoliste), kein Attribut | B3 F13 |
| Dateityp, Dateigröße | Eigenschaften der gewählten Datei; geprüft, nicht gespeichert (Photo kennt seinen Hash, nicht die Pixel) | B3 F11, F13 |
| Datei, Dateisystem | Technik, keine Domänenklasse; austauschbar → Ports `AlbumRepository` (Albumdaten), `PhotoStorage` (Bilddateien), `ImageHasher` (Datei prüfen, Hash) | B3 F18, F21, F25 |
| Use Cases UC-01, UC-02 | je eine Anwendungsklasse: `AddPhotoUseCase`, `DeleteAlbumUseCase` | B3 F21 (Controller) |

### 1.4 Beziehungen und Verantwortung

- Album enthält Fotos: Komposition 1 ◆ 0..100 (AE-01). Untergrenze 0, weil ein neues Album leer ist (AK-03.1); B3 F17 nennt 1..100.
- Ein Foto gehört zu genau einem Album (A-06).
- Use Cases benutzen Ports und Domäne; die Domäne zeigt nirgendwohin (B3 F7, F20).
- Wer prüft die 100er-Regel? → Album (Information Expert, B3 F21). Wer berechnet den Hash? → ImageHasher (B3 F18).

**Ergebnis:** Domäne `Album`, `Photo` · Anwendung `AddPhotoUseCase`, `DeleteAlbumUseCase` · Ports `AlbumRepository`, `PhotoStorage`, `ImageHasher`.
Keine Karte: UI und Adapter (`FileAlbumRepository`, `FileSystemPhotoStorage`, `Sha256ImageHasher`); B3 F19 verlangt Karten für Domäne, Anwendung und Ports.

---

## 2 CRC-Karten (B3 F18, F19)

Eine Zeile = eine Verantwortlichkeit, rechts die dafür nötigen Mitarbeiter. Geschäftsregeln **fett**.

### Album – Domäne

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. kennt id, Name und Fotos; gibt die Fotos nur als Kopie heraus (B3 F13) | Photo |
| 2. prüft **Name 1–50 Zeichen, nicht nur Leerzeichen** (FA-01, A-01), **höchstens 100 Fotos** (FA-06, AE-01), **kein Foto mit gleichem Hash im selben Album** (FA-05, AE-03, A-05); meldet Verstoß als fachliche Exception (B3 F13) | Photo (liefert Hash) |
| 3. fügt Foto hinzu / entfernt Foto | Photo |
| 4. benennt sich um (Namensregel aus 2.) | – |
| 5. liefert die Speicherschlüssel seiner Fotos (neu, UC-02) | Photo (liefert Speicherschlüssel) |

### Photo – Domäne

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. kennt id, Hash, Speicherschlüssel, Titel, Beschreibung; Titel und Beschreibung optional (A-02) | – |
| 2. prüft **Titel höchstens 100 Zeichen** (FA-07, AE-02), **Beschreibung höchstens 100 Zeichen** (FA-07, A-02) | – |
| 3. ändert Titel und Beschreibung (`describe`) | – |

### AddPhotoUseCase – Anwendung (UC-01)

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. lässt die gewählte Datei prüfen und hashen | ImageHasher |
| 2. lädt Album; nicht gefunden → Fehler | AlbumRepository |
| 3. erzeugt Photo (neue id, Hash, eindeutiger Speicherschlüssel, V-02) und ruft `album.addPhoto` | Photo, Album |
| 4. lässt die Kopie der Datei speichern, erst nach erfolgreichem `addPhoto`; scheitert danach das Speichern des Albums, lässt er die Kopie wieder löschen (B3 F10) | PhotoStorage |
| 5. speichert Album; übersetzt Fehler in `Result` (B3 F24) | AlbumRepository |

### DeleteAlbumUseCase – Anwendung (UC-02)

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. lädt Album | AlbumRepository |
| 2. holt die Speicherschlüssel der Fotos | Album |
| 3. **löscht die enthaltenen Fotos mit** (FA-09, AE-04): zuerst Albumdaten, dann jede Bilddatei (keine Teilzustände, B3 F10) | AlbumRepository, PhotoStorage |
| 4. übersetzt Fehler in `Result` | – |

Die Nachfrage vor dem Löschen (AE-04) stellt die UI, nicht der Use Case (Sequenzdiagramm „Album löschen“, Plenumsfrage B3 F25).

### AlbumRepository «interface» – Port

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. `save(Album)` | Album |
| 2. `findById(AlbumId) : Optional<Album>` (B3 F9) | Album |
| 3. `findAll()` | Album |
| 4. `delete(AlbumId)` | – |

Implementiert von FileAlbumRepository (Infrastruktur).

### PhotoStorage «interface» – Port für Dateien (B3 F25)

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. `store(file, key)`: speichert die Kopie der Bilddatei (A-06) | – |
| 2. `read(key)`: liefert die gespeicherte Kopie für die Anzeige (FA-02; K-7) | – |
| 3. `delete(key)`: löscht die gespeicherte Kopie (UC-02, NFA-04) | – |

Implementiert von FileSystemPhotoStorage. Lesender Zugriff `read(key)` nach K-7 ergänzt (19.10.2026).

### ImageHasher «interface» – Port (B3 F18)

| Verantwortlichkeiten | Zusammenarbeit |
|---|---|
| 1. `validate(file)`: prüft **Datei lesbar, JPEG oder PNG, höchstens 20 MB** (FA-04, A-03, A-04) | – (Adapter liest die gewählte Datei; B3 F18: „Dateisystem“) |
| 2. `hash(file) : String`: berechnet den Inhalts-Hash (A-05) | – |

Implementiert von Sha256ImageHasher.

### 2.1 Geschäftsregel → Karte

| Regel | Quelle | Karte |
|---|---|---|
| Albumname nicht leer, 1–50 Zeichen | FA-01; PB §5.2; A-01 | Album |
| höchstens 100 Fotos je Album | FA-06; AE-01 (B2 F31) | Album |
| kein identisches Foto im selben Album | FA-05; AE-03 (B2 F31); A-05 | Album |
| Titel höchstens 100 Zeichen | FA-07; AE-02 (B2 F31) | Photo |
| Beschreibung höchstens 100 Zeichen | FA-07; A-02 | Photo |
| Datei lesbar, JPEG oder PNG, höchstens 20 MB | FA-04; A-03, A-04 | ImageHasher |
| Album löschen löscht die enthaltenen Fotos mit | FA-09; AE-04 (B3 F25) | DeleteAlbumUseCase |
| Album löschen fragt nach | FA-09; AE-04 (B3 F25) | keine Karte: UI (Interaktion) |

Jede Regel steht auf genau einer Karte. Die Regeln aus NFA-03 (FA-01, FA-05 bis FA-07) liegen auf Album und Photo, also in einem Modul (Domäne). Ändert der Auftraggeber einen Wert (F-01 bis F-03), ändert sich genau eine Karte.

### 2.2 Faustregel und Kohäsion (B3 F11, F12, F18)

| Karte | Verantw. | Mitarbeiter | max. 5 / max. 3 | Bemerkung |
|---|---|---|---|---|
| Album | 5 | 1 | erfüllt, an der Grenze | hält Fotos und prüft die Regeln – gehört zusammen (B3 F12) |
| Photo | 3 | 0 | erfüllt | – |
| AddPhotoUseCase | 5 | 5 | **Mitarbeiter überschritten** | koordiniert nur, prüft keine Regel → V-01 |
| DeleteAlbumUseCase | 4 | 3 | erfüllt | – |
| AlbumRepository | 4 | 1 | erfüllt | – |
| PhotoStorage | 3 | 0 | erfüllt | `read` nach K-7 ergänzt |
| ImageHasher | 2 | 0 | erfüllt | beide Aufgaben betreffen die gewählte Datei; keine Dateiverwaltung (B3 F11) |

Für das Plenum (B3 F19, Karte mit den meisten Einträgen): AddPhotoUseCase.

### 2.3 Vorschläge (vom Team zu bestätigen)

| ID | Vorschlag | Begründung | Alternative |
|---|---|---|---|
| V-01 | Die Karte AddPhotoUseCase trotz 5 Mitarbeitern nicht teilen. | Genau eine Anwendungsklasse je Use Case, sie koordiniert nur (B3 F21). Drei Mitarbeiter sind Ports, die ohnehin austauschbar bleiben sollen (B3 F21). Keine Regel liegt auf der Karte, die Kohäsion bleibt hoch. Teilen ergäbe eine Klasse ohne eigenen Use Case. | Datei-Schritte (prüfen, hashen, Kopie speichern) in eine eigene Anwendungsklasse auslagern; AddPhotoUseCase hätte dann 3 Mitarbeiter (B3 F18). |
| V-02 | Der Speicherschlüssel ist je Foto eindeutig (nicht der Hash); AddPhotoUseCase erzeugt ihn. | Dieselbe Datei in einem zweiten Album ist ein eigenes Foto (A-06). Mit dem Hash als Schlüssel würde UC-02 Schritt 4 auch die Datei des Fotos im anderen Album löschen. | Hash als Schlüssel: verworfen (siehe Begründung). |

---

## 3 Walkthrough „Foto hinzufügen“ (B3 F19, Punkt 3)

Ausgangslage: Album geöffnet, Nutzerin hat eine Datei gewählt (UC-01 Schritte 1–3). Reihenfolge nach B3 F10 und F24: erst prüfen, dann speichern.

| # | UC-01 | Nachricht | Karte hebt sich | Verantwortlichkeit |
|---|---|---|---|---|
| 1 | 1–3 | UI → `execute(albumId, file)` | AddPhotoUseCase | koordiniert; die UI hat keine Karte und ruft nur den Use Case auf (B3 F21) |
| 2 | 4 | `validate(file)` | ImageHasher | lesbar, JPEG oder PNG, höchstens 20 MB |
| 3 | 5 | `hash(file)` | ImageHasher | Inhalts-Hash |
| 4 | 5 | `findById(albumId)` | AlbumRepository | liefert `Optional<Album>` |
| 5 | 5 | Photo erzeugen | Photo | kennt id, Hash, Speicherschlüssel |
| 6 | 5 | `album.addPhoto(photo)` | Album, Photo | Album prüft 100er-Regel und Duplikat (fragt `photo.hash()`), fügt hinzu |
| 7 | 5 | `store(file, key)` | PhotoStorage | speichert die Kopie |
| 8 | 5 | `save(album)` | AlbumRepository | speichert das Album |
| 9 | 6 | `Result.ok` → UI zeigt das Foto | AddPhotoUseCase | übersetzt das Ergebnis; **die Bilddaten für die Anzeige liefert keine Karte** |

| Alternativablauf | Auslöser | Verlauf |
|---|---|---|
| 4a Datei ungültig | ImageHasher (#2) | AddPhotoUseCase → `Result.error`; nichts gespeichert |
| Album nicht gefunden | AlbumRepository (#4, leer) | `Result.error`; nichts gespeichert |
| 5a Album voll, 5b Duplikat | Album (#6) | fachliche Exception (B3 F13) → `Result.error`; #7–#8 entfallen, keine Kopie, Album unverändert |
| 5c Speichern scheitert | PhotoStorage (#7) bzw. AlbumRepository (#8) | bei #7: Album wird nicht gespeichert; bei #8: AddPhotoUseCase lässt die Kopie mit `delete(key)` entfernen. Album unverändert (NFA-02) |

**Befund**
- **Bleibt eine Karte stumm?** In UC-01 keine. DeleteAlbumUseCase bleibt stumm; sie gehört zu UC-02 und ist im Sequenzdiagramm „Album löschen“ geprüft. `findAll` und `delete(AlbumId)` werden in UC-01 nicht gebraucht.
- **Übernimmt eine Karte alles?** Nein. AddPhotoUseCase spricht in jedem Schritt, entscheidet aber keine Regel; die Regeln prüfen Album und ImageHasher. Entwurf A „UI macht alles“ (B3 F24) ist vermieden. Die Mitarbeiterzahl liegt über der Faustregel (V-01).
- **Lücke:** UC-01 Schritt 6 (FA-02) braucht die gespeicherte Bilddatei für die Anzeige. PhotoStorage kann nur speichern und löschen. Photo liest nicht selbst vom Dateisystem (die Domäne kennt keine Datei, B3 F7), die UI kennt keinen Port (B3 F11). → PhotoStorage braucht eine lesende Verantwortlichkeit, aufgerufen über einen Use Case (K-7).

---

## 4 Abgleich

### 4.1 Vorläufiges Klassendiagramm (Team-KD)

| # | Element | Team-KD | Änderung | Grund |
|---|---|---|---|---|
| K-1 | `AlbumRepository.findById` | `findById(AlbumId) : Album` | `findById(AlbumId) : Optional<Album>` | B3 F9; Walkthrough #4 |
| K-2 | `Album` | keine lesende Methode | `storageKeys()` ergänzen | Karte Album 5.; Sequenzdiagramm „Album löschen“ |
| K-3 | `Album` | – | `photos()` liefert eine Kopie der Liste | Karte Album 1.; B3 F13 |
| K-4 | `Album` | kein Konstruktor, keine Konstante | `Album(id, name)` mit Namensprüfung, `MAX_PHOTOS = 100` | Karte Album 2.; B3 F13 |
| K-5 | `Photo` | nur `describe(title, description)` | lesend `hash()` und `storageKey()` | Album vergleicht `p.hash()` (B3 F13); K-2 |
| K-6 | `Photo.storageKey` | Bedeutung offen | eindeutig je Foto, nicht der Hash | V-02 |
| K-7 | `PhotoStorage` | `store(file, key)`, `delete(key)` | lesende Methode für die Anzeige: Vorschlag `read(key)` (Name vom Team zu bestätigen) | Walkthrough #9; FA-02 |
| K-8 | Abhängigkeiten | DeleteAlbumUseCase ⇢ Album und AddPhotoUseCase ⇢ Photo fehlen | ergänzen | DeleteAlbumUseCase ruft `storageKeys()`; AddPhotoUseCase erzeugt Photo |
| K-9 | Linienführung | AddPhotoUseCase ⇢ PhotoStorage und ⇢ ImageHasher laufen hinter AlbumRepository bzw. PhotoStorage durch und wirken wie Abhängigkeiten zwischen Ports | ohne Überdeckung zeichnen | Ports haben keine Mitarbeiter (Karten) |

Unverändert und konsistent mit den Karten: Komposition Album 1 ◆ 0..100 Photo; `ImageHasher.validate(file)`, `hash(file)` (B3 F18, F20); `execute(…) : Result` beider Use Cases; Adapter realisieren die Ports.

### 4.2 Sequenzdiagramm „Album löschen“

| # | Stelle | Änderung | Grund |
|---|---|---|---|
| S-1 | Antwort auf `findById(albumId)` | `Optional<Album>` statt `album`; leer → `Result.error` | K-1 |
| S-2 | UC-02 4b (Bilddatei nicht löschbar) | offen: weder im Diagramm noch auf einer Karte steht, wer die Datei innerhalb von 24 h entfernt und die Löschung protokolliert | NFA-04 |

Alle übrigen Nachrichten entsprechen je einer Verantwortlichkeit: `execute` → DeleteAlbumUseCase; `findById`, `delete(albumId)` → AlbumRepository 2., 4.; `storageKeys()` → Album 5.; `delete(key)` → PhotoStorage 3.; `Result.ok` → DeleteAlbumUseCase 4. (B3 F25, Auftrag 3).

---

## 5 Offene Punkte

- V-01 und V-02 vom Team bestätigen.
- K-7: Name `read(key)` bestätigen; aufrufender Use Case (Album/Foto anzeigen) folgt in Block 4.
- S-2: Zuständigkeit für UC-02 4b und NFA-04.
- Fotos der Papierkarten aus der Übung ergänzen (B3 F19).
- Regelwerte sind vorläufig (AE-01 bis AE-03; A-01 bis A-05; Fragen F-01 bis F-03).
