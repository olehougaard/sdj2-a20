package utility.observer.subject;

import utility.observer.listener.GeneralListener;

public interface LocalSubject<S, T> extends GeneralSubject<S, T>
{  
  boolean addListener(GeneralListener<S, T> listener, String... propertyNames);
  boolean removeListener(GeneralListener<S, T> listener,
      String... propertyNames);
}
