package org.iesmurgi.proyectolevidaviddam.models;

public class Item {

    public String username;
    public int id;
    public String name;
    public String item;
    public String description;
    public String image;
    public String genere;
    public boolean copyright;

    public Item(String username, int id, String name, String item, String description, String image, String genere, boolean copyright) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.item = item;
        this.description = description;
        this.image = image;
        this.genere = genere;
        this.copyright = copyright;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public Item(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
