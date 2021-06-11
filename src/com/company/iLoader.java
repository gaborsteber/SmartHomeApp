package com.company;
import java.util.List;

interface iLoader{
    default void loadSubscribers(String fajlnev) throws Exception {

    }

    default List<Subscriber> loadSubscribersToList(String fajlNev) throws Exception {
        return null;
    }

    default List<Subscriber> getSubscribers() {
        return null;
    }


}
