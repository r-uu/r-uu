# Entwicklung modularer Software mit Java

Modularisierung ist ein wichtiges Qualitätsmerkmal für große Softwaresysteme. Sie verbessert die Möglichkeit, diese zu verstehen, erhöht dadurch ihre Beherrschbarkeit und trägt zu deren effektiverer Wart-, Test- und Erweiterbarkeit bei. 

Dieser Beitrag beschreibt unzureichende Modularisierung als eine Ursache für die Schwierigkeit, komplexe Software zu beherrschen und wie es zu Defiziten in der Modularisierung kommt. Es werden Strategien für die Reduzierung dieser Defizite bescgrieben und dabei wird insbesondere auf Möglichkeiten zur Modularisierung in Java eingegangen. Diese werden zum Teil an praktischen Beispielen demonstriert.

## Komplexität

Die Schwierigkeit, Softwaresysteme zu verstehen, zu warten und zu erweitern, liegt vor allem in deren Komplexität begründet. Was also macht ein System komplex?

Nicht selten ist vor allem die interne Struktur von Systemen dafür verantwortlich. Sie ist häufig gekennzeichnet durch eine kaum überschaubare Menge von direkten und indirekten internen Abhängigkeiten. Diese Abhängigkeiten machen es schwer vorherzusagen, welche Auswirkungen lokale Änderungen auf das Verhalten des Gesamtsystems haben.

## Softwarearchitektur

Eine zentrale Aufgabe von Softwarearchitektur ist, tragfähige Strukturen für große Systeme zu entwickeln. Bedeuten die beschriebenen Probleme also ein Scheitern der Softwarearchitektur?

Jein. Soll ein System entwickelt werden, entwerfen Softwarearchitekten meist sinnvolle Strukturen, die den bekannten Anforderungen genügen (v1 in **Abb. 1**).

<p align="center">
  <img src="system-architecture-evolving.drawio.svg" alt="Erosion der Systemarchitektur" width="500"/>
  <br/>
  <em>Abb. 1: Erosion der Systemarchitektur</em>
</p>

Was passiert aber, wenn sich die Anforderungen ändern und neue hinzukommen? Dasselbe wie bei "klassischen" Architekturen für Gebäude, Städte, Infrastruktur, ... Zunächst tragfähige Lösungen kommen an ihre Grenzen.

<p align="center">
  <img src="mont-st-michel.png" alt="Es wird eng ..." width="500"/>
  <br/>
  <em>Abb. 2: Es wird eng ...</em>
</p>

Zu wenig Platz wie in **Abb. 2** ist bei Softwaresystemen heute normalerweise nicht das Problem. Wie schon gesagt, liegt es hier vor allem in der stetig wachsende Zahl von (internen) Abhängigkeiten. Liessen sich diese besser in den Griff kriegen, könnten auch große Systeme besser verstanden, verbessert, erweitert, getestet werden.

## Design for Changeability

Gesucht wird also nach Ansätzen, die Softwaresysteme anpassbar machen, ihre Grenzen erweitern, ohne dass ihre Komplexität Überhand nimmt und die gleichzeitig effektive Qualitätssicherung unterstützen. Solange zu viele Abhängigkeiten aber die Ursache für die Probleme sind, ist die Beherrschung dieser Abhängigkeiten ein Beitrag zur Lösung. Was hier Hoffnung macht ist, dass eine große Menge dieser Abhängigkeiten weder gewollt noch nötig ist.

Warum gibt es sie dann? Zwei Gründe sind meist ausschlaggebend:

- Es kann bequem sein, zusätzliche Abhängigkeiten bewusst in Kauf zu nehmen, und
- es gibt kein wirksames Mittel gegen das unbewusste, ungewollte Entstehen unnötiger Abhängigkeiten.

Wie hat die Softwareindustrie darauf reagiert hat und wie erfolgreich ist bzw. war sie bis jetzt dabei?

## Klassische Ansätze

Das Folgende bezieht sich in erster Linie auf die heute sehr verbreiteten Java-(Alt)-Systeme, ist in ähnlicher Weise aber auch auf andere Technologiestacks übertragbar.

Mehrere Probleme monolithischer Systeme (s. o.) haben vor Jahren das Aufkommen von Microservices stark begünstigt, u. a. die eingeschränkten Möglichkeiten zur Modularisierung von großen Systemen. Monolithen werden typischerweise als eine große Einheit entworfen, in der sich mehrere Teilsysteme befinden. Der Monolith wird dabei in einer einzigen, großen Einheit erstellt und (in einem Applikationsserver) in einem einzigen Betriebssystemprozess ausgeführt.

Dies erleichtert vieles, hat aber auch seinen Preis: Innerhalb des Monolithen war es lange schwer, die enthaltenen Teilsysteme sauber voneinander zu trennen. So schlichen sich, bewusst oder unbewusst, unnötige Abhängigkeiten zwischen Teilsystemen ein. Viele Monolithen wurden so zu einem "big ball of mud", deren Wart-, Test- und Erweiterbarkeit zunehmend komplexer bis hin zu unmöglich wurden. Es gab schlicht keine effektiven und technisch wasserdichten Mechanismen, mit denen sich die Abhängigkeiten von Teilsystemen besser hätten kontrollieren und ggf. verhindern lassen können.

Sicher macht man sich Konzepte wie Kapselung von Klassen und Packages zunutze, um eine interne Struktur des Gesamtsystems herzustellen. Diese Konzepte sind aber letztlich zu durchlässig, um die Einhaltung der Strukturen, also die Sicherstellung einer konsistenten Architektur, systemweit zu erzwingen. Vieles beruhte auf Einhaltung von Konventionen. Verstösse mussten dabei erst einmal mit viel Mühe erkannt werden, bevor sie korrigiert werden konnten. Tools wie z. B. archunit ermöglichen eine automatisierte und regelbasierte Unterstützung dabei, müssen aber für jedes System korrekt und möglichst vollständig konfiguriert (und getestet?) werden. Ist die automatisierte Überprüfung der Systemstruktur in den Buildprozess integriert, erhält man frühzeitig Hinweise auf Verstöße. Natürlich darf in so einem Fall nicht die entsprechende Regel gelockert oder gar deaktiviert werden.

## Microservices

Microservices verfolgen hier einen rigoroseren Ansatz. Für jeden Microservice wird eine oft plattformunabhängige Schnittstelle vereinbart. Die Implementierung dieser Schnittstelle erfolgt vollständig autonom, bis hin zur Wahl der verwendeten Technologie. Auch teilen sich Microservices nicht einen gemeinsamen Betriebssystemprozess, sondern jeder bekommt exklusiv einen eigenen. Offensichtlich werden so unerwünschte Querverbindungen zwischen den Teilsystemen, also solche, die nicht dessen Schnittstelle verwenden, kategorisch unterbunden. Auf diese Weise lässt sich so ein sehr hoher Grad an Modularisierung erreichen, es zeigt sich aber, dass mit wachsender Zahl von Microservices die Komplexität an anderen Stellen, z. B. bei der Infrastruktur und dessen Management, enorm steigt.

Warum ist das so?

Der Grund liegt in einem typischen Merkmal von Microservices: sie sind üblicherweise tatsächlich klein - so klein, dass sie isoliert von der Aussenwelt wenig Sinn erbringen. Typischerweise laufen Microservices daher in einem Netzwerk aus Microservices, oft in sogenannten Containern. Im Netzwerk kommunizieren die Microservices untereinander und stellen so die mächtige Funktionalität heutiger Softwaresysteme zur Verfügung. Deren Mächtigkeit steht dabei im Kontrast zur Kleinheit der Microservices. Es liegt auf der Hand, dass es eine große Anzahl von Microservices braucht, um heutige Systeme zu bauen.

Natürlich steigt mit wachsender Zahl von Microservices / Containern / Prozessen die Komplexität, diese zu beherrschen. In einem vernetzten System von eigenständigen Betriebssystemprozessen muss über viele Aspekte ganz neu nachgedacht werden: Wie wird mit der Latenz bei der Netzwerkkommunikation umgegangen? Wie erkennt das Gesamtsystem den Ausfall einzelner Komponenten und wie reagiert es darauf? Wird die Konsistenz ggf. redundanter Daten hergestellt und falls ja, wie? Wie lassen sich Transaktionen über Prozessgrenzen realisieren? Um all dem zu begegnen, entstand eine Vielzahl von zum Teil konkurrierenden Konzepten, Tools und Technologien (und natürlich entsprechenden Dienstleistungsangeboten).

Dies hinterließ bei vielen den Eindruck, dass die Komplexität von Microservices die der Monolithen deutlich übersteigt, wenn es sich hier auch um eine andere Art von Komplexität handelt. Das darf aber kein Grund sein, Microservices voreilig ad acta zu legen. Es muss eingeräumt werden, dass Modularisierung nicht das einzige Qualitätsmerkmal von guten Softwaresystemen ist. Neben diesem erhält man mit Microservices neue Möglichkeiten z.B. für die Skalierbarkeit und für das Deployment neuer Softwarefeatures.

Dennoch, die Kritik an Microservices führte nach einiger Zeit zu einer gegenläufigen Entwicklung mit Slogans wie "I want my monolith back". Tatsächlich tat sich gleichzeitig und unabhängig vom Trend zu Microservices auch einiges in Bezug auf Modularisierung im Java-Umfeld (s. u.).

## Warum ist Modularisierung wichtig und was sind Module?

Das Schlüsselargument ist hier Beherrschung von Komplexität auch in großen Systemen. Große monolithische Systeme weisen häufig problematische Merkmale wie starke Kopplung auf. Bei zu starker Kopplung sind Systemteile unnötiger und oft ungewollter Weise voneinander abhängig. Viele dieser Abhängigkeiten entstehen unkontrolliert und so kommt es, dass schnell so viele existieren, dass große Teile des Systems nahezu unwartbar werden: Jede Änderung birgt die Gefahr von schwer kontrollierbaren Seiteneffekten in kaum vorhersehbaren Teilen des Systems. Dies wiederum führt dazu, dass die Kosten für Wartung und Erweiterung der Software explodieren.

Der Zugriff auf ein Modul ist wie bei den Microservices technisch nur über vom Modul selbst zur Verfügung gestellte Schnittstellen möglich. Das bedeutet, dass das Entstehen von unerwünschten Abhängigkeiten technisch unterbunden wird. Dadurch wird ein Modul zu einem Baustein, mit dem große Systeme hergestellt werden können. Die Gefahr, dass diese durch unkontrolliert entstehende Abhängigkeiten fragil bzw. unwartbar und nicht erweiterbar werden, ist deutlich reduziert.

Beim Java Platform Module System ist es sogar möglich, dass ein Modul bis ins Detail steuert, welche anderen Module auf welche Teile der bereitgestellten Schnittstelle zugreifen können. Anders als Microservices werden Module nicht notwendigerweise in eigenen Betriebssystemprozessen ausgeführt.

## Modulithen

Sollte es also nicht möglich sein, Softwaresysteme zu konstruieren, die strukturell deutlich weniger komplex sind als die klassischen Monolithen, trotzdem aber deren einfachere Handhabbarkeit im Produktivbetrieb nutzen? Gibt es gleichzeitig eine Möglichkeit, bei Bedarf die Vorteile von Microservices z. B. in Sachen Skalierbarkeit mit einzubringen? Dieser Beitrag versucht anhand eines konkreten Beispiels zu zeigen, dass ein modular aufgebauter Monolith, ggf. an performancekritischen Stellen gezielt kombiniert mit hochskalierbaren Mikroservices, genau dies realisiert. Dieses Konzept taucht seit einiger Zeit in der Literatur unter dem Kunstnamen "Modulith" auf.

## Ein Anwendungsbeispiel


---------- Hintergrund ----------

## Wie Code in Java strukturiert wird

Um dem Wildwuchs an Abhängigkeiten ("big ball of mud") besser Herr zu werden, strukturiert man Java Code schon lange in Konstrukte wie interfaces, classes und packages und verwendet access level (public, protected, ...), um Zugriff auf interne Teile dieser Einheiten gezielt zu steuern. Diese Einheiten werden als (Lego-) Bausteine aufgefasst, aus denen sich größere Konstruktionen zusammensetzen lassen. Benutzer der größeren Einheiten sollen dabei keinen direkten Zugriff auf die internen Bausteine der Einheit haben, es sei denn, der Zugriff wird über eine öffentliche Schnittstelle explizit erlaubt. Code wird also so organisiert, dass große Bausteine aus kleineren zusammengesetzt werden können. Dieses Muster lässt sich natürlich beliebig oft wiederholen.

Leider zeigt sich schnell, dass die beschriebenen Mechanismen nicht ausreichend sind, um die Entstehung von big balls of mud zu verhindern. Dies liegt vor allem an einem Mangel der packages: Will man eine Klasse in einem package von ausserhalb des packages nutzbar machen, muss man die Klasse öffentlich (public) machen. Und genau das entspricht ja dem beschriebenen Bausteinprinzip. Es gab aber lange (zumindest in Standard-Java) keinen Mechanismus, um die Sichtbarkeit von packages zu kontrollieren: Gibt es in einem package eine Klasse, die in einem anderen package verwendet werden soll, muss die Klasse public gemacht werden. Damit ist sie aber öffentlich für ALLE Systemteile, in denen das zugehörige package direkt oder indirekt verwendet wird.

Dies ist ein Ausgangspunkt für die Entstehung von big balls of mud: Früher oder später wird es dazu kommen, dass die Klasse von (weit entfernten) Stellen aus verwendet wird, die eigentlich keinen Zugriff haben sollten, da die Klasse für sie ein zu verbergendes Implementierungsdetail eines größeren Bausteins ist.

Module schaffen hier Abhilfe, indem sie für Code Schnittstellen festlegen, die präzise definieren, wie auf den Code zugegriffen werden kann.

Schnittstellen definieren somit auch die Dieses Konzept existiert schon sehr lange in der Softwareentwicklung. Bis zur Einführung von
Modulen war allerdings das Problem, dass gewissermaßen zwangsläufig zu viele Schnittstellen entstanden.
Module erfordern zwar im ersten Moment einen gewissen Zusatzaufwand, garantieren aber danach eine sauberere interne
Struktur des Gesamtsystems. Dies wird erreicht, indem die Inflation von Abhängigkeiten effektiv verhindert wird.

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

## Modularisierung mit Java



## Modulith
