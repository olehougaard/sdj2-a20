package utility.observer.event;

import utility.observer.subject.GeneralSubject;

import java.io.Serializable;

public class ObserverEvent<S, T> implements Serializable
{
  private static final long serialVersionUID = 2910;
  
  private final GeneralSubject<S, T> source;
  private final String propertyName;
  private final S value1;
  private final T value2;
  
  /**
     * Create a new ObserverEvent. 
     *
     * @param source containing the property
     * @param propertyName the property's name
     * @param value1 the 1st value, e.g. old value of the property
     * @param value2 the 2nd value, e.g. new value of the property
     */
    public ObserverEvent(GeneralSubject<S, T> source, String propertyName,
                               S value1, T value2)
    {
      this.source = source;
      this.propertyName = propertyName;
      this.value1 = value1;
      this.value2 = value2;
    }

  public GeneralSubject<S, T> getSource()
  {
    return source;
  }

  public String getPropertyName()
  {
    return propertyName;
  }

  public S getValue1()
  {
    return value1;
  }

  public T getValue2()
  {
    return value2;
  }

  @Override
  public String toString()
  {
    return "ObserverEvent [source=" + source + ", propertyName=" + propertyName
        + ", value1=" + value1 + ", value2=" + value2 + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((propertyName == null) ? 0 : propertyName.hashCode());
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
    result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObserverEvent<S, T> other = (ObserverEvent<S,T>) obj;
    if (propertyName == null)
    {
      if (other.propertyName != null)
        return false;
    }
    else if (!propertyName.equals(other.propertyName))
      return false;
    if (source == null)
    {
      if (other.source != null)
        return false;
    }
    else if (!source.equals(other.source))
      return false;
    if (value1 == null)
    {
      if (other.value1 != null)
        return false;
    }
    else if (!value1.equals(other.value1))
      return false;
    if (value2 == null)
    {
      if (other.value2 != null)
        return false;
    }
    else if (!value2.equals(other.value2))
      return false;
    return true;
  }
  

}
