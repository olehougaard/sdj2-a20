package utility.observer.subject;

import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;

public class PropertyChangeProxy<S, T> implements PropertyChangeAction<S, T>
{
  private PropertyChangeAction<S, T> realSubject;

  public PropertyChangeProxy(GeneralSubject<S, T> source, boolean usingThreads,
      boolean oldNewCheck)
  {
    if (usingThreads)
    {
      realSubject = new PropertyChangeThread<>(source, oldNewCheck);
    }
    else
    {
      realSubject = new PropertyChangeHandler<>(source, oldNewCheck);
    }
  }

  public PropertyChangeProxy(GeneralSubject<S, T> source)
  {
    this(source, false, true);
  }

  public PropertyChangeProxy(GeneralSubject<S, T> source, boolean usingThreads)
  {
    this(source, usingThreads, true);
  }

  @Override public boolean addListener(GeneralListener<S, T> listener,
      String... propertyNames)
  {
    return realSubject.addListener(listener, propertyNames);
  }

  @Override public boolean removeListener(GeneralListener<S, T> listener,
      String... propertyNames)
  {
    return realSubject.removeListener(listener, propertyNames);
  }

  @Override public void firePropertyChange(ObserverEvent<S, T> event)
  {
    realSubject.firePropertyChange(event);
  }

  @Override public void firePropertyChange(String propertyName, S value1,
      T value2)
  {
    realSubject.firePropertyChange(propertyName, value1, value2);
  }

  @Override public void close()
  {
    realSubject.close();
  }

}
