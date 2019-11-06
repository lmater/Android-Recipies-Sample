package com.mt2sconnect.recipes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mt2sconnect.recipes.SQLDataStructure.DataBase;
import com.mt2sconnect.recipes.SQLDataStructure.Recipe;

import java.io.IOException;
import java.io.InputStream;

import static com.mt2sconnect.recipes.SQLDataStructure.DataBase.listRecipesFav;
import static com.mt2sconnect.recipes.SQLDataStructure.DataBase.updateRecipefavList;

public class RecipeActivity extends AppCompatActivity {

    String recipe , group;
    Recipe theRecipe = new Recipe();
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipe= getIntent().getStringExtra("recipe");
        String from = getIntent().getStringExtra("from");
        if(from.equals("fav")) {
            theRecipe = listRecipesFav.get(Integer.parseInt(recipe));
//            System.out.println(theRecipe.getTitle());
        }
        else if(from.equals("list")){
            group = getIntent().getStringExtra("group");
            theRecipe = DataBase.listGroups.get(Integer.parseInt(group)).getListRecipes().get(Integer.parseInt(recipe));
            }
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("  "+ theRecipe.getTitle());
        ImageView recipeImage = (ImageView) findViewById(R.id.headerImg);
        TextView ingredients = (TextView) findViewById(R.id.ingredient);
        TextView howTo = (TextView) findViewById(R.id.howTo);
        InputStream ims = null;
        try {
            ims = getAssets().open("imgs/"+theRecipe.getUrl_img()+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        recipeImage.setImageDrawable(d);
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adsZoneBanner);
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("843A8C72CC7337876D5238B32FCC105E")
                .build();
        mAdView.loadAd(adRequest);


        final FloatingActionButton addToFavourit = (FloatingActionButton) findViewById(R.id.fabAddFavourite);
        if(theRecipe.getFav())
        addToFavourit.setImageResource(R.drawable.ic_star_black_24dp);
        else  addToFavourit.setImageResource(R.drawable.ic_star_border_black_24dp);
        addToFavourit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateRecipefavList(theRecipe.getId_r(), !theRecipe.getFav());
                if(theRecipe.getFav()){
                    addToFavourit.setImageResource(R.drawable.ic_star_black_24dp);
                    Snackbar.make(view, R.string.addtofav, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else  {addToFavourit.setImageResource(R.drawable.ic_star_border_black_24dp);
                Snackbar.make(view, R.string.delfromfav, Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.about_gmail_color))
                        .setAction("Action", null).show();
                }
            }
        });

        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        fabShare.setImageResource(R.drawable.ic_menu_share);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Share Recipe Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        System.out.println(DataBase.listGroups.get(Integer.parseInt(group)).getListRecipes().get(Integer.parseInt(recipe)).getTitle());


        ingredients.setText(Html.fromHtml(theRecipe.getIngridient()));
//        System.out.println(ingredients.getText());
        howTo.setText(Html.fromHtml(theRecipe.getHowToDo()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("group", group);
        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("group", group);
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
