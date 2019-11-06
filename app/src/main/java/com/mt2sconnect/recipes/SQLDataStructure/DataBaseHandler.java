package com.mt2sconnect.recipes.SQLDataStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LMATER on 30/01/2019.
 */

public class DataBaseHandler   extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Recipes.db";
    //table Group
    public static final String TABLE_GROUP = "t_group";
    public static final String COLUMN_ID_G = "id_g";
    public static final String COLUMN_LABEL_G = "label_g";
    public static String CREATE_TABLE_GROUP = "CREATE TABLE if not exists " + TABLE_GROUP + "(" + COLUMN_ID_G +
            " Integer  primary key ," + COLUMN_LABEL_G + " TEXT);";
    public static String DROP_TABLE_GROUP = "DROP TABLE if exists  " + TABLE_GROUP;
    //table Fav
    public static final String TABLE_FAV = "t_fav";
    public static final String COLUMN_ID_FR = "id_fr";
    public static String CREATE_TABLE_FAV = "CREATE TABLE if not exists " + TABLE_FAV + "(" + COLUMN_ID_FR + " Integer);";
    public static String DROP_TABLE_FAV = "DROP TABLE if exists  " + TABLE_FAV;
    //table Recipie
    public static final String TABLE_RECIPE = "t_recipe";
    public static final String COLUMN_ID_R = "id_r";
    public static final String COLUMN_ID_GR = "id_rg";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URL_IMG = "url_img";
    public static final String COLUMN_HOWTODO = "howtodo";
    public static final String COLUMN_INGRIDIENT = "ingridient";
    public static final String COLUMN_FAV = "fav";
    public static String CREATE_TABLE_RECIPE = "CREATE TABLE if not exists " + TABLE_RECIPE + " ( " + COLUMN_ID_R +
            " Integer primary key ," + COLUMN_TITLE+ " TEXT," + COLUMN_INGRIDIENT+ " TEXT," + COLUMN_HOWTODO+ " TEXT," +  COLUMN_URL_IMG+ " TEXT," + COLUMN_ID_GR+ " INTEGER ," + COLUMN_FAV+ " INTEGER )";
    public static String DROP_TABLE_RECIPE = "DROP TABLE if exists " + TABLE_RECIPE;
    public static String TABLES_DATA_EXISTS = "select id_r from " + TABLE_RECIPE;
    //initialize the database
    public DataBaseHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_FAV);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,  int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_GROUP);
        db.execSQL(DROP_TABLE_RECIPE);
        onCreate(db);
    }

    public List<Group> loadHandlerGroupsRecipes() {
        List<Group> listGroups=new ArrayList<Group>();
        String query = "Select * FROM " + TABLE_GROUP+" order by "+COLUMN_LABEL_G;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_g = db.rawQuery(query, null);
        while (cursor_g.moveToNext()) {
            int id_g = cursor_g.getInt(0);
            query = "Select "+COLUMN_ID_R+","+COLUMN_TITLE+","+COLUMN_INGRIDIENT+","+COLUMN_HOWTODO+","+COLUMN_URL_IMG+","+COLUMN_ID_GR+", CASE  WHEN "+COLUMN_ID_FR+">0 THEN "+COLUMN_ID_FR+"  ELSE 0  END FROM " + TABLE_RECIPE + " LEFT outer JOIN " + TABLE_FAV + " ON "+COLUMN_ID_R+" = "+COLUMN_ID_FR+ " WHERE " + COLUMN_ID_GR + " = " + id_g+" order by "+COLUMN_TITLE;
         //   System.out.println(query);
            Cursor cursor_r = db.rawQuery(query, null);
            List<Recipe> listRecipes = new ArrayList<Recipe>();
            while (cursor_r.moveToNext()) {
                listRecipes.add(new Recipe(cursor_r.getInt(0), cursor_r.getString(1), cursor_r.getString(2), cursor_r.getString(3), cursor_r.getString(4), (cursor_r.getInt(6)!= 0)? true : false));
            }
            cursor_r.close();
            listGroups.add(new Group(id_g, cursor_g.getString(1),listRecipes));
        }
        cursor_g.close();
        db.close();
        return listGroups;
    }

    public void addHandlerGroup(Group group) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_G, group.getId_g());
        values.put(COLUMN_LABEL_G, group.getLabel_g());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GROUP, null, values);
        db.close();
    }
    public void addHandlerRecipe(Recipe recipe,int id_g) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_R, recipe.getId_r());
        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_URL_IMG, recipe.getUrl_img());
        values.put(COLUMN_INGRIDIENT, recipe.getIngridient());
        values.put(COLUMN_HOWTODO, recipe.getHowToDo());
        values.put(COLUMN_ID_GR, id_g);
        values.put(COLUMN_FAV, (recipe.getFav() == true)? 1 : 0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPE, null, values);
        db.close();
    }

    public void updateHandlerRecipeSetFav(int id_r,boolean fav) {
//        System.out.println(id_r);
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAV, (fav == true)? 1 : 0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_RECIPE, values, COLUMN_ID_R+" = "+id_r, null);
        values = new ContentValues();
        values.put(COLUMN_ID_FR, id_r);
        if(fav)
            db.insert(TABLE_FAV, null, values);
        else
            db.delete(TABLE_FAV,  COLUMN_ID_FR+" = "+id_r, null);
        db.close();
    }

    public  int executeSQLScript_Return_result(String query) {
        int id_g=0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_RECIPE);
        Cursor cursor_g = db.rawQuery(query, null);
        while (cursor_g.moveToNext()) {
            id_g = cursor_g.getInt(0);
        }
        cursor_g.close();
        return id_g;
    }














/*
    public List<Group> findHandlerGroupRecipe(int str_key) {
        List<Group> listGroups=new ArrayList<Group>();
        List<Recipe> listRecipes = new ArrayList<Recipe>();
        String query = "Select "+ TABLE_GROUP+".*,  " + TABLE_RECIPE+".*  FROM " + TABLE_GROUP+", "+TABLE_RECIPE+ " WHERE ("+ TABLE_GROUP+"."+COLUMN_ID_G+"="+ TABLE_RECIPE+"."+COLUMN_ID_GR +") and (" + TABLE_GROUP+"."+COLUMN_LABEL_G + " = '%" + str_key + "%' or " + TABLE_RECIPE+"."+COLUMN_TITLE + " = '%" + str_key + "%' or " + TABLE_RECIPE+"."+COLUMN_INGRIDIENT + " = '%" + str_key + "%'" + TABLE_RECIPE+"."+COLUMN_HOWTODO + " = '%" + str_key + "%'  )";;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            listGroups.add(new Group(cursor.getInt(0), cursor.getString(1),listRecipes));
            listRecipes.add(new Recipe(cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));

        }
        cursor.close();
        db.close();
        return listGroups;
    }
    public boolean deleteHandler(int id_g) {

        boolean result = false;
        String query = "Select * FROM " + TABLE_GROUP + " WHERE " + COLUMN_ID_G + " = '" + String.valueOf(id_g) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Group group = new Group();
        if (cursor.moveToFirst()) {
            group.setId_g(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_GROUP, COLUMN_ID_G + "=?",
                    new String[] {
                String.valueOf(group.getId_g())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(int id_g, String label_g) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID_G, id_g);
        args.put(COLUMN_LABEL_G, label_g);
        return db.update(TABLE_GROUP, args, COLUMN_ID_G + "=" + String.valueOf(id_g) , null) > 0;
    }

*/
}
