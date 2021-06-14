package com.company;

import java.util.List;

interface iLoader {

    void loadSubscribers(String fajlnev) throws Exception;

    public List<Subscriber> loadSubscribersToList(String fajlNev) throws Exception;

    public List<Subscriber> getSubscribers();
}
