package utility.observer.listener;

import utility.observer.event.ObserverEvent;

public interface LocalListener<S, T> extends GeneralListener<S, T>
{
   void propertyChange(ObserverEvent<S, T> event);
}
