package utility.observer.subject;

import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;

import java.util.ArrayDeque;
import java.util.HashMap;

public class PropertyChangeHandler<S, T> implements PropertyChangeAction<S, T>
{
  private GeneralSubject<S, T> source;
  private HashMap<String, ArrayDeque<GeneralListener<S, T>>> map;
  private boolean oldNewCheck;

  public PropertyChangeHandler(GeneralSubject<S, T> source, boolean oldNewCheck)
  {
    this.oldNewCheck = oldNewCheck;
    this.source = source;
    this.map = new HashMap<>();
  }

  public PropertyChangeHandler(GeneralSubject<S, T> source)
  {
    this(source, true);
  }

  public void firePropertyChange(ObserverEvent<S, T> event)
  {
    if (oldNewCheck && ((event.getValue1() != null && event.getValue1()
        .equals(event.getValue2())) || (event.getValue1() == null
        && event.getValue2() == null)))
    {
      return;
    }
    ArrayDeque<GeneralListener<S, T>> queue = map.get(event.getPropertyName());
    if (queue != null)
    {
      for (GeneralListener<S, T> listener : queue)
      {
        try
        {
          listener.propertyChange(event);
        }
        catch (Exception e)
        {
          //
        }
      }
    }
    if (event.getPropertyName() != null) // all unnamed property listeners
    {
      queue = map.get(null);
      if (queue != null)
      {
        for (GeneralListener<S, T> listener : queue)
        {
          try
          {
            listener.propertyChange(event);
          }
          catch (Exception e)
          {
            //
          }
        }
      }
    }
  }

  public void firePropertyChange(String propertyName, S value1, T value2)
  {
    ObserverEvent<S, T> event = new ObserverEvent<>(source, propertyName,
        value1, value2);
    firePropertyChange(event);
  }

  @Override public void close()
  {
    // nothing
  }

  @Override public boolean addListener(GeneralListener<S, T> listener,
      String... propertyNames)
  {
    if (propertyNames == null || propertyNames.length == 0)
    {
      ArrayDeque<GeneralListener<S, T>> queue = map.get(null);
      if (queue == null)
      {
        queue = new ArrayDeque<>();
        map.put(null, queue);
      }
      if (!queue.contains(listener))
      {
        queue.addLast(listener);
      }
      return queue.contains(listener);
    }
    boolean added = true;
    for (String propertyName : propertyNames)
    {
      ArrayDeque<GeneralListener<S, T>> queue = map.get(propertyName);
      if (queue == null)
      {
        queue = new ArrayDeque<>();
        map.put(propertyName, queue);
      }
      if (!queue.contains(listener))
      {
        queue.addLast(listener);
      }
      else
      {
        added = false;
      }
    }
    return added;
  }

  @Override public boolean removeListener(GeneralListener<S, T> listener,
      String... propertyNames)
  {
    if (propertyNames == null || propertyNames.length == 0)
    {
      ArrayDeque<GeneralListener<S, T>> queue = map.get(null);
      if (queue == null)
      {
        return false;
      }
      return queue.remove(listener);
    }
    boolean removed = true;
    for (String propertyName : propertyNames)
    {
      ArrayDeque<GeneralListener<S, T>> queue = map.get(propertyName);
      boolean removedOne = false;
      if (queue != null)
      {
        removedOne = queue.remove(listener);
      }
      if (!removedOne)
      {
        removed = false;
      }
    }
    return removed;
  }
}

