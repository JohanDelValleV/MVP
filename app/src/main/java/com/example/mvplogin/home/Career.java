package com.example.mvplogin.home;

public class Career {
    private int id;
    private String name;
    private String slug;

    public Career(int id, String name, String slug) {
        this.setId(id);
        this.setName(name);
        this.setSlug(slug);
    }

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
