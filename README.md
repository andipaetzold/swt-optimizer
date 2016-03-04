# Übungsaufgabe 1 (JavaBeans & OSGi)

## Funktionale Anforderungen

Entwickeln Sie das Gerüst für ein flexibel erweiterbares Optimierungssystem. Gehen Sie davon aus, dass dieses System aus verschiedenen Optimierungs-Modulen (Optimierer) besteht, welche dynamisch hinzugefügt werden können. Von einem Optimierer kann es mehrere Implementierungen geben, welche eine Problemstellung mit unterschiedlichen Algorithmen lösen. Ein Optimierer nimmt Eingangsdaten entgegen und liefert nach einiger Zeit ein Ergebnis. Ihre Optimierer müssen nicht tatsächlich Berechnungen durchführen, sondern nur den Zeitverbrauch für die Berechnungen simulieren.

Koordiniert werden die Optimierer von einem Optimierungs-Manager, der Optimierungsaufgaben an Optimierer weitergibt und die Optimierer überwacht. Der Optimierungs-Manager gibt eine Optimierung an alle Optimierer weiter, die ein bestimmtes Interface implementieren. Alle Berechnungsläufe werden parallel durchgeführt. Der Optimierer wartet die Ergebnisse sämtlicher Berechnungsläufe ab.

Über das Optimierungs-Frontend können die Eingabedaten erfasst und Optimierungsläufe gestartet werden. Hier wird auch der Fortschritt eines Optimierungslaufes grafisch dargestellt und das Ergebnis ausgegeben.

## Technische Anforderungen

* _Optimierer:_ Überlegen Sie sich ein geeignetes Interface. Dieses Interface sollte zumindest die Übergabe der Optimierungsparameter, die Abfrage des Ergebnisses, die Abfrage des Optimierungsstatus (running/finished) sowie die Abfrage des Fortschritts ermöglichen.
* _Optimierungs-Manager:_ Er ist für die Koordination der Optimierer verantwortlich. Die Optimierung muss auch ohne Frontend weiterlaufen. Das Frontend muss während einer Optimierung gestartet und gestoppt werden können. In einer ersten Ausbaustufe soll der Optimierungs-Manager nur jene Optimierer berücksichtigen, die beim Start eines Optimierungslaufs zur Verfügung stehen. Verwenden Sie für den Zugriff auf die Optimierer einen ServiceTracker ohne ServiceTrackerCustomizer. In einer weiteren Ausbaustufe sollte der Optimierungs-Manager während eines Optimierungslaufs neu hinzukommende Optimierer erkennen und diese auch mit der Abarbeitung der aktuellen Optimierung beauftragen. Dafür werden Sie einen ServiceTrackerCustomizer benötigen.
* _Optimierungs-Frontend:_ Hier kann der Benutzer die Eingangsdaten für ein Optimierungsproblem erfassen (es genügt ein Double-Wert) und den Fortschritt der Berechnungen verfolgen. Der Fortschritt ist für alle gestarteten Optimierer darzustellen. Liefert ein Optimierer ein Ergebnis, ist dieses ebenfalls anzuzeigen. Ein Optimierungslauf darf erst dann wieder gestartet werden, wenn der vorausgehende vollständig abschlossen ist.
* _Event-getriebenes Design (optional, für Beurteilung mit „Sehr gut“ erforderlich):_ Versuchen Sie so weit wie möglich auf Polling zu verzichten. Legen Sie Ihre Anwendung eventgesteuert aus und verwenden Sie dafür das OSGi-Service „Event Admin“.

# Dokumentation
## Architektur
### Optimizer
![Architektur Optimizer](https://github.com/andipaetzold/swt-optimizer/blob/master/optimizer/documentation/optimizer.png)
### OptimizerFactory
![Architektur OptimizerFactory](https://github.com/andipaetzold/swt-optimizer/blob/master/optimizer/documentation/factory.png)

## Module
Die Module sind sehr kompakt gehalten und beinhalten jeweil nur ein Package. Dementsprechend wird dieses importiert. Zudem wird ausschließlich das Modul _optimizer-base_ importiert, da die restliche Kommunikation über Services oder _EventAdmin_ geschieht. Die restlichen importierten Module sind das osgi-Framework, der Tracker für die _Services_ oder _EventAdmin_. Deren Import ergibt sich aus der nachfolgenden Erläuterung der einzelnen Module.

### optimizer-base
Dieses Modul enthält ausschließlich abstrakte Klassen und Interfaces und dient als Basis für die implementierten Optimierer.

_AbstractOptimizer_ implementiert das Interface _Optimizer_ und stellt bereits die grundlegenden Funktionen der Optimierer fest. Hierzu zählen die Verarbeitung der Stati sowie Ergebnisse und deren Verbreitung über den _EventAdmin_.

### optimizer-one / optimizer-two / optimizer-three
Diese Module implementieren die zuvor erläuterten Basisklassen bzw. -interfaces. Die Optimierer enthalten lediglich eine Methode, die einen zufälligen Wert zurück gibt und den Thread davor einige Sekunden schlafen legt. Eine konkrete Implementierung war hier nicht gefordert.

Abgesehen von der Registrierung des _Services_ und dem Erzeugen des _EventAdmins_, wird hier keinerlei Funktionalität implementiert. Dies geschieht in der zuvor erläuterten optimizer-base.

### optimizer-manager
Der Manager verwaltet alle Optimierer (_OptimizerFactory_) die sich als Service registriert haben. Beim Starten eines Optimierungslaufs werden diese genutzt, um die entsprechenden Optimierer zu erzeugen. Jeder Optimierer wird in einem separaten Thread gestartet, um ein paralleles abarbeiten zu gewährleisten. Anhand dieser Threads kann auch festgestellt werden, ob ein neuer Optimierungslauf gestartet werden darf oder nicht.

Da die Kommunikation mit der Benutzeroberfläche über den _EventAdmin_ abläuft, dient die Klasse _ManagerEventHandler_ zum Empfangen der gesendeten Nachrichten.

### optimizer-frontend
Die Benutzeroberfläche wurde mit JavaFX realisiert. Die Oberfläche wird auf Basis einer fxml-Datei erzeugt und mit einem Controller (_FrontendController_) verbunden. Dieser Controller enthält öffentliche Methoden zur Bearbeitung der Benutzeroberfläche. Hierüber können Optimierer hinzugefügt bzw. entfernt werden oder deren Stati und Ergebnisse aktualisiert werden.

Auf diese Funktionen wird über einen _EventHandler_ zugegriffen, der die über den EventAdmin gesendeten Nachrichten verarbeitet. Dieser EventHandler erhält die Stati und Ergebnisse der Optimierer direkt aus den entsprechenden Klassen. Informationen über die verfügbaren Optimierer werden durch den Manager versendet.
