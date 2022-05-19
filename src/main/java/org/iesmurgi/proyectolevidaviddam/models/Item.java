package org.iesmurgi.proyectolevidaviddam.models;

public class Item {

    public String username;
    public int id;
    public String name;
    public String item;
    public String description;
    public String image;
    public String genere;
    public int copyright;       //1 si   0 no
    public String uploadDate;

    public Item(String username, int id, String name, String item, String description, String image, String genere, int copyright, String uploadDate) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.item = item;
        this.description = description;
        this.image = image;
        this.genere = genere;
        this.copyright = copyright;
        this.uploadDate=uploadDate;
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

    public int isCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
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

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
