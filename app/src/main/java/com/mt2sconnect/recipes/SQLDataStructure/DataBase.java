package com.mt2sconnect.recipes.SQLDataStructure;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.mt2sconnect.recipes.SQLDataStructure.DataBaseHandler.TABLES_DATA_EXISTS;

/**
 * Created by LMATER on 30/01/2019.
 */

public class DataBase {

    public static List<Recipe> listRecipesFav = new ArrayList<Recipe>();
    public static List<Group> listGroups = new ArrayList<Group>();
    public static List<Group> getListGroups() {
        return listGroups;
    }

    public static DataBaseHandler dbHandler ;
    public DataBase(Context context) {
        dbHandler = new DataBaseHandler(context, null);
    }

    public static void setListGroups(List<Group> listGroups) {
        DataBase.listGroups = listGroups;
    }
   /* public static  void setDataToDataBase(Context context){
        DataBaseHandler dbHandler = new DataBaseHandler(context, null);
        for (int i = 1; i < 10; i++){
            for (int j = i; j < 10; j++) {
                dbHandler.addHandlerRecipe(new Recipe("title " + j, "url_img " + j, "ingridient " + j, "howToDo " + j),i);
            }
            dbHandler.addHandlerGroup(new Group("Group "+i));
        }
    }*/
    public static  void setFileAssetsToDataBase(Context context){
        AssetManager assetManager = context.getAssets();

        if(dbHandler.executeSQLScript_Return_result(TABLES_DATA_EXISTS)==0){
        InputStream isg = null;
        InputStream isr = null;
        BufferedReader reader = null;
        try {
            isg = assetManager.open("db/groups.txt");
            isr = assetManager.open("db/recipes.txt");
            reader = new BufferedReader(new InputStreamReader(isg));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] thisLine = line.split("@");
//                System.out.println("handle abdo "+ thisLine[0]);
//                System.out.println("handle abdo "+ Integer.valueOf(thisLine[0]));
                dbHandler.addHandlerGroup(new Group(Integer.valueOf(thisLine[0]),thisLine[1]));
            }
            reader = new BufferedReader(new InputStreamReader(isr));
            while ((line = reader.readLine()) != null) {
                String[] thisLine = line.split("@");
//                System.out.println(thisLine.length);
//                System.out.println(thisLine[0]);
                    Recipe r = new Recipe(Integer.valueOf(thisLine[0]), thisLine[1], thisLine[2], thisLine[3], thisLine[4],false);
                    dbHandler.addHandlerRecipe(r, Integer.valueOf(thisLine[5]));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (isg != null) {
                try {
                    isg.close();
                } catch (IOException ignored) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        }
    }


    public static  void loadDataFromDataBase(){
        setListGroups(dbHandler.loadHandlerGroupsRecipes());
        getRecipefavList();
    }

    public static  void updateRecipefavList(int id_r,boolean fav){

        for (int i = 0; i < listGroups.size(); i++){
            for (int j = 0; j < listGroups.get(i).getListRecipes().size(); j++) {
                if(listGroups.get(i).getListRecipes().get(j).getId_r()==id_r)
                {
                    listGroups.get(i).getListRecipes().get(j).setFav(fav);
                    if(fav)
                        listRecipesFav.add(listGroups.get(i).getListRecipes().get(j));
                    else
                        listRecipesFav.remove(listGroups.get(i).getListRecipes().get(j));

                    dbHandler.updateHandlerRecipeSetFav(id_r,fav);
                    //loadDataFromDataBase();
//                    System.out.println(listGroups.get(i).getListRecipes().get(j).getId_r()+" "+listGroups.get(i).getListRecipes().get(j).getFav() );
//                    updateRecipefavList(DataBase.listGroups.get(Integer.parseInt(group)).getListRecipes().get(Integer.parseInt(recipe)).getId_r(), !DataBase.listGroups.get(Integer.parseInt(group)).getListRecipes().get(Integer.parseInt(recipe)).getFav());

                }
            }
        }
    }

    public static  void getRecipefavList(){
        listRecipesFav.clear();
        for (int i = 0; i < listGroups.size(); i++){
            for (int j = 0; j < listGroups.get(i).getListRecipes().size(); j++) {
                if(listGroups.get(i).getListRecipes().get(j).getFav())
                {
                    listRecipesFav.add(listGroups.get(i).getListRecipes().get(j));
//                    updateRecipefavList(DataBase.listGroups.get(Integer.parseInt(group)).getListRecipes().get(Integer.parseInt(recipe)).getId_r(), !DataBase.listGroups.get(Integer.parseInt(group)).getListRecipes().get(Integer.parseInt(recipe)).getFav());

                }
            }
        }
    }

}
