package com.wayne.jmx.demo.mbean.mx;

//extends PlatformManagedObject
public interface PaxiQMXBean  {
    PaxiQueue getPaxiQueue();
    void clearQueue();
}
