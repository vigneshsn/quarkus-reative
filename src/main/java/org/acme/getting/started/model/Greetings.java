package org.acme.getting.started.model;

import java.util.List;

public class Greetings {
    public Greetings() {
    }

    public List<Greeting> getGreetings() {
        return greetings;
    }

    public void setGreetings(List<Greeting> greetings) {
        this.greetings = greetings;
    }

    List<Greeting> greetings;

    @Override
    public String toString() {
        return "Greetings{" +
                "greetings=" + greetings +
                '}';
    }
}
