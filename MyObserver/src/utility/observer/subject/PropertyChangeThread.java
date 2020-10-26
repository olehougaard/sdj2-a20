package utility.observer.subject;

import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public final class PropertyChangeThread<S, T> extends Thread
    implements PropertyChangeAction<S, T>, GeneralSubject<S, T>
{
  private static final String THREAD_NAME = "Subject-Thread";
  private static ThreadGroup group = new ThreadGroup("SubjectThreads");
  private PropertyChangeHandler<S, T> property;
  private ArrayDeque<ObserverEvent<S, T>> events;
  private HashMap<String, ArrayDeque<ListenerThread<S, T>>> listenerMap;
  private GeneralSubject<S, T> source;
  private boolean isAlive;
  private boolean oldNewCheck;

  public PropertyChangeThread(GeneralSubject<S, T> source, boolean oldNewCheck)
  {
    super(group,
        THREAD_NAME + (source == null ? "" : "-"+source.getClass().getSimpleName()));
    this.source = source;
    if (this.source == null)
    {
      throw new IllegalArgumentException("source is null");
    }
    this.oldNewCheck = oldNewCheck;
    this.property = new PropertyChangeHandler<>(source);
    this.events = new ArrayDeque<>();
    this.listenerMap = new HashMap<>();
    this.isAlive = true;
    super.setName(THREAD_NAME + this.hashCode());
    this.start();
  }

  public PropertyChangeThread(GeneralSubject<S, T> source)
  {
    this(source, true);
  }

  public synchronized void firePropertyChange(ObserverEvent<S, T> event)
  {
    if (oldNewCheck && ((event.getValue1() != null && event.getValue1()
        .equals(event.getValue2())) || (event.getValue1() == null
        && event.getValue2() == null)))
    {
      return;
    }
    events.addLast(event);
    notifyAll();
    try
    {
      wait(10);
    }
    catch (InterruptedException e)
    {
      // 
    }
  }

  public synchronized void firePropertyChange(String name, S value1, T value2)
  {
    ObserverEvent<S, T> event = new ObserverEvent<>(source, name, value1,
        value2);
    firePropertyChange(event);
  }

  public synchronized void close()
  {
    isAlive = false;
    Iterator<Entry<String, ArrayDeque<ListenerThread<S, T>>>> iterator = listenerMap
        .entrySet().iterator();
    while (iterator.hasNext())
    {
      Entry<String, ArrayDeque<ListenerThread<S, T>>> next = iterator.next();
      for (ListenerThread<S, T> localListener : next.getValue())
      {
        localListener.close();
      }
    }
    notifyAll();
    try
    {
      wait(10);
    }
    catch (InterruptedException e)
    {
      // 
    }
    stopThreads(hashCode()+"");
  }

  private synchronized ObserverEvent<S, T> getNextEvent()
  {
    while (events.isEmpty())
    {
      try
      {
        wait();
        if (!isAlive)
        {
          return null;
        }
      }
      catch (InterruptedException e)
      {
        close();
        return null;
      }
    }
    return events.removeFirst();
  }

  @Override
  public void run()
  {
    while (isAlive)
    {
      ObserverEvent<S, T> event = getNextEvent();
      if (isAlive && event != null)
      {
        property.firePropertyChange(event);
      }
    }
  }

  @Override
  public boolean addListener(GeneralListener<S, T> listener,
      String... propertyNames)
  {
    if (propertyNames == null || propertyNames.length == 0)
    {
      ArrayDeque<ListenerThread<S, T>> queue = listenerMap.get(null);
      if (queue == null)
      {
        ListenerThread<S, T> localListener = new ListenerThread<>(source,
            listener);
        queue = new ArrayDeque<>();
        queue.add(localListener);
        listenerMap.put(null, queue);
        property.addListener(localListener);
        return true;
      }
      for (ListenerThread<S, T> localListener : queue)
      {
        if (localListener.getListener().equals(listener))
        {
          return false;
        }
      }
      ListenerThread<S, T> localListener = new ListenerThread<>(source,
          listener);
      queue.add(new ListenerThread<>(source, listener));
      property.addListener(localListener);
      return true;
    }
    boolean added = true;
    for (String propertyName : propertyNames)
    {
      boolean alreadyThere = false;
      ArrayDeque<ListenerThread<S, T>> queue = listenerMap.get(propertyName);
      if (queue == null)
      {
        ListenerThread<S, T> localListener = new ListenerThread<>(source,
            listener);
        queue = new ArrayDeque<>();
        queue.add(localListener);
        listenerMap.put(propertyName, queue);
        property.addListener(localListener, propertyName);
      }
      else
      {
        for (ListenerThread<S, T> localListener : queue)
        {
          if (localListener.getListener().equals(listener))
          {
            alreadyThere = true;
            added = false;
            break;
          }
        }
        if (!alreadyThere)
        {
          ListenerThread<S, T> localListener = new ListenerThread<>(source,
              listener);
          queue.add(localListener);
          property.addListener(localListener, propertyName);
        }
      }
    }
    return added;
  }

  @Override
  public boolean removeListener(GeneralListener<S, T> listener,
      String... propertyNames)
  {
    if (propertyNames == null || propertyNames.length == 0)
    {
      ArrayDeque<ListenerThread<S, T>> queue = listenerMap.get(null);
      for (ListenerThread<S, T> localListener : queue)
      {
        if (localListener.getListener().equals(listener))
        {
          queue.remove(localListener);
          localListener.close();
          property.removeListener(localListener);
          if (queue.isEmpty())
          {
            listenerMap.remove(null);
          }
          return true;
        }
      }
      return false;
    }
    boolean removed = true;
    for (String propertyName : propertyNames)
    {
      boolean alreadyThere = false;
      ArrayDeque<ListenerThread<S, T>> queue = listenerMap.get(propertyName);
      for (ListenerThread<S, T> localListener : queue)
      {
        if (localListener.getListener().equals(listener))
        {
          queue.remove(localListener);
          localListener.close();
          property.removeListener(localListener, propertyName);
          alreadyThere = true;
          if (queue.isEmpty())
          {
            listenerMap.remove(propertyName);
          }
        }
      }
      if (!alreadyThere)
      {
        removed = false;
      }
    }
    return removed;
  }

  public static boolean closeAll()
  {
    boolean closed = true;
    PropertyChangeThread[] subjectThreads = new PropertyChangeThread[group
        .activeCount()];
    group.enumerate(subjectThreads);
    for (PropertyChangeThread t : subjectThreads)
    {
      try
      {
        t.close();
      }
      catch (Exception e)
      {
        closed = false;
      }
    }
    return closed;
  }

  private boolean stopThreads(String id)
  {
    if (id == null)
    {
      return false;
    }
    boolean closed = true;
    PropertyChangeThread[] subjectThreads = new PropertyChangeThread[group
        .activeCount()];
    group.enumerate(subjectThreads);
    for (PropertyChangeThread t : subjectThreads)
    {
      if (t.getName().endsWith(id))
      {
        try
        {
          t.close();
        }
        catch (Exception e)
        {
          closed = false;
        }
      }
    }
    return closed;
  }

}
