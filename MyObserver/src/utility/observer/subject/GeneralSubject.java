package utility.observer.subject;

import utility.observer.listener.GeneralListener;

public interface GeneralSubject<S, T>
{
  boolean addListener(GeneralListener<S, T> listener, String... propertyNames) throws Exception;
  boolean removeListener(GeneralListener<S, T> listener,
      String... propertyNames) throws Exception;
}
