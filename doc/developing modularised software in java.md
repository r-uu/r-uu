# Modularisierung und Java

Modularisierung ist ein wichtiges Qualitätsmerkmal für große Softwaresysteme. Sie verbessert die Möglichkeit, diese zu verstehen, erhöht dadurch ihre Beherrschbarkeit und trägt zu deren effektiverer Wart-, Test- und Erweiterbarkeit bei. 

Im folgenden wird zunächst beschrieben, warum die Beherrschung heutiger Software so schwierig ist und wie es dazu kommt. Anschließend wird auf einige typische Strategien für den Umgang mit den genannten Problemen eingegangen. Schließlich wird ein Blick auf Möglichkeiten zur Modularisierung in Java geworfen. Für einige wird auch beschrieben, wie sie sich in der Praxis umsetzen lassen.

[open diagram](test.drawio)

[open diagram](test.svg)

[open diagram](test_mit_include.svg)

## Komplexität

Die Schwierigkeit, Softwaresysteme zu verstehen, zu warten und zu erweitern, liegt vor allem in deren Komplexität begründet. Es stellt sich somit die Frage, was macht ein System komplex?

Nicht selten ist vor allem die interne Struktur von Systemen dafür verantwortlich. Sie ist häufig gekennzeichnet durch eine kaum überschaubare Menge von direkten und indirekten internen Abhängigkeiten. Diese Abhängigkeiten machen es schwer vorherzusagen, welche Auswirkungen lokale Änderungen auf das Verhalten des Gesamtsystems haben.

## Softwarearchitektur

Eine zentrale Aufgabe von Softwarearchitektur ist, tragfähige Strukturen für große Systeme zu entwickeln. Bedeuten die beschriebenen Probleme also ein Scheitern der Softwarearchitektur?

Jein. Soll ein System entwickelt werden, entwerfen Softwarearchitekten meist sinnvolle Strukturen, die den bekannten Anforderungen genügen.

[Bild für einfache Struktur mit wenigen Abhängigkeiten]()

Was passiert aber, wenn sich die Anforderungen ändern und neue hinzukommen? Dasselbe wie bei "klassischen" Architekturen für Gebäude, Städte, Infrastruktur, ... Zunächst tragfähige Lösungen kommen an ihre Grenzen.

[Bild mit Mont St. Michel]()

Zu wenig Platz wie im Bild ist bei Softwaresystemen heute normalerweise nicht das Problem. Wie schon gesagt, liegt das Problem hier vor allem in der stetig wachsende Zahl von internen Abhängigkeiten.

[Bild für komplexere Struktur mit mehr Abhängigkeiten]()

## Design for Changeability

Gesucht wird also nach Ansätzen, die Softwaresysteme anpassbar machen, ihre Grenzen erweitern, ohne dass ihre Komplexität überhand nimmt. Solange zu viele Abhängigkeiten aber die Ursache für die Probleme sind, ist die Beherrschung dieser Abhängigkeiten ein Beitrag zur Lösung. Was hier Hoffnung macht ist, dass eine große Menge dieser Abhängigkeiten weder gewollt, noch nötig ist.

Warum gibt es sie dann? Zwei Gründe sind meist ausschlaggebend:

- Es kann bequem sein, zusätzliche Abhängigkeiten bewusst in Kauf zu nehmen, und
- es gibt kein wirksames Mittel gegen das unbewusste, ungewollte Entstehen unnötiger Abhängigkeiten.

Im folgenden wird gezeigt, wie die Softwareindustrie darauf reagiert hat und wie erfolgreich sie dabei war bzw. ist.

## Microservices vs. Monolithen

Das Folgende bezieht sich in erster Linie auf die heute sehr verbreiteten Java-(Alt)-Systeme, dürfte in ähnlicher Weise aber auch auf andere Technologiestacks übertragbar sein.

Mehrere Probleme monolithischer Systeme (s. o.) haben vor Jahren das Aufkommen von Microservices stark begünstigt, u. a. die eingeschränkten Möglichkeiten zur Modularisierung von großen Systemen. Monolithen werden typischerweise als eine große Deploymenteinheit betrieben, in der sich mehrere Teilsysteme befinden. Die Deploymenteinheit wird dabei (in einem Applikationsserver) in einem Betriebssystemprozess ausgeführt.

Dies erleichtert den Betrieb, hat aber auch seinen Preis: Innerhalb des Monolithen war es lange schwer, die enthaltenen Teilsysteme sauber voneinander zu trennen. So schlichen sich, bewusst oder unbewusst, unnötige Abhängigkeiten zwischen den Teilsystemen ein. Viele Monolithen wurden zu einem "big ball of mud", deren Wart-, Test- und Erweiterbarkeit zunehmend komplexer bis hin zu unmöglich wurden. Es gab keine Module, mit denen sich die Abhängigkeiten von Teilsystemen besser hätten kontrollieren lassen.

Da jeder Microservice in eigenen, autonomen Prozess läuft, löst sich das Problem der Modularisierung quasi von selbst. Es lässt sich so ein sehr hoher Grad an Modularisierung erreichen, es zeigt sich aber, dass mit wachsender Zahl von Microservices z. B. die Komplexität der benötigten Infrastruktur enorm steigt.

Warum ist das so?

Der Grund liegt in einem typischen Merkmal von Microservices: sie sind üblicherweise tatsächlich klein - so klein, dass sie isoliert von der Aussenwelt wenig Sinn erbringen. Typischerweise laufen Microservices daher in einem Netzwerk aus Microservices in sogenannten Containern. Im Netzwerk kommunizieren die Microservices untereinander und stellen so die mächtige Funktionalität heutiger Softwaresysteme zur Verfügung. Deren Mächtigkeit steht dabei im Kontrast zur Kleinheit der Microservices. Es liegt auf der Hand, dass es eine große Anzahl von Microservices braucht, um heutige Systeme zu bauen.

Natürlich steigt mit wachsender Zahl von Microservices / Containern / Prozessen die Komplexität, diese zu beherrschen. In einem vernetzten System von eigenständigen Betriebssystemprozessen muss über viele Aspekte ganz neu nachgedacht werden: Wie wird mit der Latenz bei der Netzwerkkommunikation umgegangen? Wie erkennt das Gesamtsystem den Ausfall einzelner Komponenten und wie reagiert es darauf? Wie wird die Konsistenz ggf. redundanter Daten umgegangen? Wie lassen sich Transaktionen realisieren? Um all dem zu begegnen entstand eine Vielzahl von zum Teil konkurrierenden Konzepten, Tools und Technologien (und natürlich entsprechenden Dienstleistungsangeboten).

Dies hinterließ bei vielen den Eindruck, dass die Komplexität von Microservices die der Monolithen sogar deutlich übersteigt. Bevor aber Microservices hier nun voreilig ad acta gelegt werden, muss natürlich eingeräumt werden, dass Modularisierung nicht das einzige Qualitätsmerkmal von guten Softwaresystemen ist. Neben diesem erhält man mit Microservices neue Möglichkeiten z.B. für die Skalierbarkeit und für das Deployment neuer Softwarefeatures.

Dies führte nach einiger Zeit zu einer gegenläufigen Entwicklung mit Slogans wie "I want my monolith back". Tatsächlich tat sich gleichzeitig und unabhängig vom Trend zu Microservices auch einiges in Bezug auf Modularisierung, z. B. im Java-Umfeld (s. u.).

## Warum ist Modularisierung wichtig?

Das Schlüsselargument ist hier Beherrschung von Komplexität auch in großen Systemen. Große monolithische Systeme wiesen in der Vergangenheit häufig problematische Merkmale wie starke Kopplung auf. Bei zu starker Kopplung sind Systemteile unnötiger und oft ungewollter Weise voneinander abhängig. Viele dieser Abhängigkeiten entstehen unkontrolliert und so kommt es, dass schnell so viele existieren, dass große Teile des Systems nahezu unwartbar werden: Jede Änderung birgt die Gefahr von schwer kontrollierbaren Seiteneffekten in kaum vorhersehbaren Teilen des Systems. Dies wiederum führt dazu, dass die Kosten für Wartung und Erweiterung der Software explodieren.

Module sind in sich gekapselt, d. h., ein Zugriff ist nur über vom Modul selbst zur Verfügung gestellte Schnittstellen möglich.

## Modulithen

Sollte es also nicht möglich sein,

## Wie Code in Java strukturiert wird

Um dem Wildwuchs an Abhängigkeiten ("big ball of mud") besser Herr zu werden, strukturiert man Java Code schon lange in
Konstrukte wie interfaces, classes und packages und verwendet access level (public, protected, ...), um Zugriff auf
interne Teile dieser Einheiten gezielt zu steuern. Diese Einheiten werden als (Lego-) Bausteine aufgefasst, aus denen
sich größere Konstruktionen zusammensetzen lassen. Benutzer der größeren Einheiten sollen dabei keinen direkten Zugriff
auf die internen Bausteine haben, es sei denn, der Zugriff wird über eine öffentliche Schnittstelle explizit erlaubt.
Code wird also so organisiert, dass große Bausteine aus kleineren zusammengesetzt werden können. Dieses Muster läßt sich
natürlich beliebig oft wiederholen.

Leider zeigte sich, dass diese Mechanismen nicht ausreichend sind, um die Entstehung von big balls of mud zu
verhindern. Dies liegt vor allem an einer Eigenschaft der größten vorgenannten Einheiten, den packages: Will man etwa
ein Klasse in einem package von ausserhalb des packages nutzbar machen, muss man die Klasse öffentlich (public) machen.
Und genau das entspricht ja dem beschriebenen Bausteinprinzip. Es gab aber (zumindest in Standard-Java) keinen
Mechanismus, um die Sichtbarkeit von packages zu kontrollieren: Gibt es in einem package z. B. eine Klasse, die in einem
anderen package verwendet werden soll, so muss die Klasse public gemacht werden. Damit ist sie aber öffentlich für ALLE
Systemteile, in denen der umgebende Baustein direkt oder indirekt (als interner Bestandteil eines umgebenden Bausteins)
verwendet wird.

Dies wiederum führt dazu, dass auf Internas eines Bausteins genauso zugegriffen werden kann, wie auf die eigentlichen
Schnittstellen des Bausteins.

Module schaffen hier Abhilfe, indem sie für Code Schnittstellen festlegen die präzise definieren, wie auf den Code
zugegriffen werden kann, Schnittstellen definieren somit auch die Dieses Konzept existiert schon sehr lange in der Softwareentwicklung. Bis zur Einführung von
Modulen war allerdings das Problem, dass gewissermaßen zwangsläufig zu viele Schnittstellen entstanden.
Module erfordern zwar im ersten Moment einen gewissen Zusatzaufwand, garantieren aber danach eine sauberere interne
Struktur des Gesamtsystems. Dies wird erreicht, indem die Inflation von Abhängigkeiten effektiv verhindert wird.

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

## Modularisierung mit Java



## Modulith
