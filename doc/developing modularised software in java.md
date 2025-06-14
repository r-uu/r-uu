# Modularisierung in Java

Modularisierung ist ein wichtiges Qualitätsmerkmal von großen Softwaresystemen. Sie verbessert das Verständnis, die
Beherrschbarkeit und somit vor allem die Wart- und Erweiterbarkeit großer Systeme. Im Folgenden wird vorgestellt, wie
Modularisierung in Java realisiert werden kann.

## Microservices vs. Monoliths

Mehrere Probleme monolithischer Systeme haben vor Jahren das Aufkommen von Microservices stark begünstigt, u. a. die
eingeschränkten Möglichkeiten zur Modularisierung von großen Systemen. Mit Microservices lässt sich zwar ein hoher Grad
an Modularisierung erreichen, es zeigt sich aber, dass mit wachsender Zahl von Microservices z. B. die Komplexität der
benötigten Infrastruktur enorm steigt.

Warum ist das so? Der Grund liegt in einem typischen Merkmal von Microservices: sie sind üblicherweise tatsächlich
klein - so klein, dass sie isoliert von der Aussenwelt wenig Sinn erbringen. Typischerweise laufen Microservices daher
in einem Netzwerk aus Microservices, und zwar meist als jeweils eigenständiger Betriebssystemprozesse in sogenannten
Containern. Im Netzwerk kommunizieren die Microservices untereinander und stellen so die mächtige Funktionalität
heutiger Softwaresysteme zur Verfügung. Die Mächtigkeit dieser Systeme steht dabei im Kontrast zu der Kleinheit der
Microservices. Es liegt auf der Hand, dass es eine große Anzahl von Microservices braucht, um heutige Systeme zu
bauen.

Natürlich steigt mit wachsender Zahl von Microservices / Containern / Prozessen die Komplexität, diese zu beherrschen.
Um dem zu begegnen entstand eine Vielzahl von zum Teil konkurrierenden Tools und Technologien (und natürlich
entsprechenden Dienstleistungsangeboten). Dies hinterließ bei vielen den Eindruck, dass die Komplexität von Microservices
die der Monolithen sogar deutlich übersteigt. Bevor aber Microservices hier nun voreilig ad acta gelegt werden, muss
natürlich eingeräumt werden, dass Modularisierung nicht das einzige Qualitätsmerkmal von Softwaresystemen ist. Neben
diesem erhält man mit Microservices neue Möglichkeiten z.B. für die Skalierbarkeit und für das Deployment neuer
Softwarefeatures.

Trotzdem führte die Komplexität der Microservices zu einer gegenläufigen Entwicklung mit Slogans wie "I want my monolith
back". Tatsächlich tat sich gleichzeitig und unabhängig vom Trend zu Microservices auch einiges in Bezug auf
Modularisierung z. B. im Java-Umfeld.

## Warum ist Modularisierung wichtig?

Das Schlüsselargument ist hier Beherrschung von Komplexität auch in großen Systemen. Große monolithische Systeme wiesen
in der Vergangenheit häufig problematische Merkmale wie starke Kopplung auf. Bei zu starker Kopplung sind Systemteile
unnötiger und oft ungewollter Weise voneinander abhängig. Viele dieser Abhängigkeiten entstehen unkontrolliert und so
kommt es, dass schnell so viele existieren, dass große Teile des Systems nahezu unwartbar werden: Jede Änderung birgt
die Gefahr von schwer kontrollierbaren Seiteneffekten in kaum vorhersehbaren Teilen des Systems. Diese Komplexität führt
dazu, dass die Kosten für Wartung und Erweiterung der Software explodieren.

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