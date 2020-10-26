package utility.observer.subject;

import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;

import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayDeque;

public class ListenerThread<S, T> extends Thread implements LocalListener<S, T>
{
   private static final String THREAD_NAME = "Listener-Thread-";
   private static ThreadGroup group = new ThreadGroup("ListenerThreads");
   private GeneralSubject<S, T> source;
   private GeneralListener<S, T> listener;
   private ArrayDeque<ObserverEvent<S, T>> events;
   private boolean isAlive;
   private int failedUpdates;

   public ListenerThread(GeneralSubject<S, T> source, GeneralListener<S, T> listener)
   {
      super(group, THREAD_NAME + listener.hashCode());
      this.source = source;
      this.listener = listener;
      this.events = new ArrayDeque<>();
      this.isAlive = true;
      this.failedUpdates = 0;
      this.start();
   }

   @Override public synchronized void propertyChange(ObserverEvent<S, T> event)
   {
      events.addLast(event);
      notifyAll();
   }

   @Override public synchronized boolean equals(Object obj)
   {
      if (!(obj instanceof ListenerThread))
      {
         return false;
      }
      ListenerThread<S, T> other = (ListenerThread<S, T>) obj;
      return listener.equals(other.listener);
   }

   public synchronized void close()
   {
      isAlive = false;
      notifyAll();
   }

   public synchronized GeneralListener<S, T> getListener()
   {
      return listener;
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
            return null;
         }
      }
      return events.removeFirst();
   }

   /**
    * Get the next event from the queue and execute the listeners property
    * change event. If this courses a ConnectionException or a
    * NoSuchElementException (for a RemoteListener) three times in a row, then
    * the listener is assumed dead and is removed. If any other kind of
    * exception is caught, then the event is lost. If events are lost three
    * times in a row the listener is also assumed dead and removed. The method
    * ends (and thereby the thread terminates) if the listener has been
    * removed or the isAlive flag has been set to false.
    */
   @Override public void run()
   {
      while (isAlive)
      {
        ObserverEvent<S, T> event = getNextEvent();
         for (int attempt = 1; attempt <= 3 && isAlive; attempt++)
         {
            try
            {
               listener.propertyChange(event);
               failedUpdates = 0;
               break;
            }
            catch (ConnectException | NoSuchObjectException e)
            {
               if (attempt >= 3)
               {
                  removeDeadListener();
                  return;
               }
            }
            catch (Exception e)
            {
               // could not access listener, maybe recover?
               failedUpdates++;
               if (failedUpdates >= 3) // the third event in a row is lost
               {
                  removeDeadListener();
                  return;
               }
               break; // event is lost
            }
         }
      }
   }

   private synchronized void removeDeadListener()
   {
      try
      {
         isAlive = false;
         source.removeListener(listener);
      }
      catch (Exception e)
      {
         close();
      }
   }

}
