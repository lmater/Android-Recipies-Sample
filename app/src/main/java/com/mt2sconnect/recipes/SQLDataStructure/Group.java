package com.mt2sconnect.recipes.SQLDataStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LMATER on 30/01/2019.
 */

public class Group {
    private int id_g;
    private String label_g;

    private List<Recipe> listRecipes = new ArrayList<Recipe>();

    public Group() {
    }

    public Group(String label_g) {
        this.label_g = label_g;
    }

    public Group(String label_g, List<Recipe> listRecipes) {
        this.label_g = label_g;
        this.listRecipes = listRecipes;
    }

    public Group(int id_g, String label_g) {
        this.id_g = id_g;
        this.label_g = label_g;
    }

    public Group(int id_g, String label_g, List<Recipe> listRecipes) {
        this.id_g = id_g;
        this.label_g = label_g;
        this.listRecipes = listRecipes;
    }

    public void setListRecipes(List<Recipe> listRecipes) {
        this.listRecipes = listRecipes;
    }

    public List<Recipe> getListRecipes() {
        return listRecipes;
    }

    public void setId_g(int id_g) {
        this.id_g = id_g;
    }

    public void setLabel_g(String label_g) {
        this.label_g = label_g;
    }

    public int getId_g() {
        return id_g;
    }

    public String getLabel_g() {
        return label_g;
    }
}
