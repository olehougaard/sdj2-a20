package utility.observer.subject;

import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;

public interface PropertyChangeAction<S, T> 
{
  boolean addListener(GeneralListener<S, T> listener, String... propertyNames);
  boolean removeListener(GeneralListener<S, T> listener,
      String... propertyNames);
  void firePropertyChange(ObserverEvent<S, T> event);
  void firePropertyChange(String propertyName, S value1, T value2);
  void close();
}
