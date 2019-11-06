package com.mt2sconnect.recipes.SQLDataStructure;

/**
 * Created by LMATER on 30/01/2019.
 */

public class Recipe {

    private int id_r;
    private String title;
    private String url_img;
    private String ingridient;
    private String howtodo;
    private boolean Fav;

    public Recipe() {
    }

    public Recipe(int id_r, String title, String ingridient, String howtodo, String url_img, boolean fav) {
        this.id_r = id_r;
        this.title = title;
        this.url_img = url_img;
        this.ingridient = ingridient;
        this.howtodo = howtodo;
        Fav = fav;
    }

    public Recipe(int id_r, String title, String ingridient, String howToDo, String url_img) {
        this.id_r = id_r;
        this.title = title;
        this.url_img = url_img;
        this.ingridient = ingridient;
        this.howtodo = howToDo;
    }

    public String getHowtodo() {
        return howtodo;
    }

    public boolean getFav() {
        return Fav;
    }

    public void setHowtodo(String howtodo) {
        this.howtodo = howtodo;
    }

    public void setFav(boolean fav) {
        Fav = fav;
    }

    public void setId_r(int id_r) {
        this.id_r = id_r;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public void setIngridient(String ingridient) {
        this.ingridient = ingridient;
    }

    public void setHowToDo(String howToDo) {
        this.howtodo = howToDo;
    }

    public int getId_r() {
        return id_r;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl_img() {
        return url_img;
    }

    public String getIngridient() {
        return ingridient;
    }

    public String getHowToDo() {
        return howtodo;
    }

}
