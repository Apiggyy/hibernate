package com.self.learning.manyToMany;

import java.util.HashSet;
import java.util.Set;

public class Items {
    private int id;
    private String name;
    private Set<Categorys> categorys = new HashSet<Categorys>();

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

        Items items = (Items) o;

        if (id != items.id) return false;
        if (name != null ? !name.equals(items.name) : items.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Set<Categorys> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<Categorys> categorys) {
        this.categorys = categorys;
    }
}
