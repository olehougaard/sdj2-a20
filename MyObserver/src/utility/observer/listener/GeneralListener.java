package utility.observer.listener;

import utility.observer.event.ObserverEvent;

public interface GeneralListener<S, T>
{
   void propertyChange(ObserverEvent<S, T> event) throws Exception;
}
