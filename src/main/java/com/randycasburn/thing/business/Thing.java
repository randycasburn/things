package com.randycasburn.thing.business;

import java.util.Objects;

public class Thing {
    private long id;
    private String name;

    public Thing(){}
    public Thing(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return getId() == thing.getId() && Objects.equals(getName(), thing.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return id + "| " + name;
    }
}
