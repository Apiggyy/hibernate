package com.self.learning.manyToMany;

import java.util.HashSet;
import java.util.Set;

public class Categorys {
    private int id;
    private String name;
    private Set<Items> items = new HashSet<Items>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        Categorys categorys = (Categorys) o;

        if (id != categorys.id) return false;
        if (name != null ? !name.equals(categorys.name) : categorys.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Set<Items> getItems() {
        return items;
    }

    public void setItems(Set<Items> items) {
        this.items = items;
    }
}
