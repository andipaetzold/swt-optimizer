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
