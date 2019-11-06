package com.mt2sconnect.recipes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mt2sconnect.recipes.SQLDataStructure.DataBase;
import com.mt2sconnect.recipes.SQLDataStructure.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RecipeListActivity extends AppCompatActivity {

    ListView listview;
    String groupPos;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK){
             groupPos = data.getStringExtra("group");
            System.out.println(RESULT_OK);
            System.out.println(groupPos);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        groupPos = getIntent().getStringExtra("group");
        int index = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            index = ThreadLocalRandom.current().nextInt(DataBase.listGroups.get(Integer.parseInt(groupPos)).getListRecipes().size());
        }

        setTitle("  "+ DataBase.listGroups.get(Integer.parseInt(groupPos)).getLabel_g());
        ImageView recipeImage = (ImageView) findViewById(R.id.headerImg);
        InputStream ims = null;
        try {
            ims = getAssets().open("imgs/"+DataBase.listGroups.get(Integer.parseInt(groupPos)).getListRecipes().get(index).getUrl_img()+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        recipeImage.setImageDrawable(d);
/*        FloatingActionButton fabContactUs = (FloatingActionButton) findViewById(R.id.fabAddFavourite);
        fabContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact US Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Share app Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        listview = (ListView) this.findViewById(R.id.listViewRecipes);
//        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarDelete);
        listview.setAdapter(new RecipeListActivity.ListviewStatAdapter(listview.getContext(), DataBase.listGroups.get(Integer.parseInt(groupPos)).getListRecipes()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public class ListviewStatAdapter extends BaseAdapter {
        private List<Recipe> listRecipes;

        private LayoutInflater mInflater;

        public ListviewStatAdapter(Context fragment, List<Recipe> groups){
            listRecipes = groups;
            mInflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listRecipes.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return listRecipes.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final RecipeListActivity.ListviewStatAdapter.listviewHolder holder;
            final Recipe Gr = listRecipes.get(position);
//            if(convertView == null){
            convertView = mInflater.inflate(R.layout.layout_recipe_list, null);
            holder = new RecipeListActivity.ListviewStatAdapter.listviewHolder();
            holder.recipeLabel = (TextView) convertView.findViewById(R.id.recipeName);
            holder.recipeImage = (ImageView) convertView.findViewById(R.id.recipeImage);
            convertView.setTag(holder);
//            } else {
//                holder = (listviewHolder) convertView.getTag();
//            }

/*            if(Gr.getLabel_g().toLowerCase().contains("copyright")){
                holder.nomPrenom.setText("");
                holder.detailchoisissants.setVisibility(View.GONE);
                holder.ordreMeritechoisissants.setVisibility(View.GONE);
                convertView.setEnabled(false);
            }
            else {*/
            holder.recipeLabel.setText("  "+Gr.getTitle()+" ");// get input stream
            InputStream ims = null;
            try {
                ims = getAssets().open("imgs/"+Gr.getUrl_img()+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            holder.recipeImage.setImageDrawable(d);
//          holder.recipeImage.setImageResource(getAssets().open(Gr.getUrl_img()+".jpg"));

            holder.recipeLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent toRecipe = new Intent(RecipeListActivity.this, RecipeActivity.class);
                    toRecipe.putExtra("recipe",""+position);
                    toRecipe.putExtra("group", groupPos);
                    toRecipe.putExtra("from","list");
                    startActivity(toRecipe);
                }
            });
            holder.recipeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Intent toRecipe = new Intent(RecipeListActivity.this, RecipeActivity.class);
                    toRecipe.putExtra("recipe",""+position);
                    toRecipe.putExtra("group", groupPos);
                    toRecipe.putExtra("from","list");
                    startActivity(toRecipe);
                    /*if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                        //ex. if ics is met then do this
                        String score="";
                        if(Ps.getListScore()!=null)
                            for (int i = 0; i < Ps.getListScore().size(); i++) {
                                score = score + "\nScore "+getLibtypemvt(Ps.getListScore().get(i).getTypemvt()) +" : "+Ps.getListScore().get(i).getValScore();
                            }

                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle(Ps.getNom()+" "+Ps.getPrenom())
                                .setMessage("PPR: "+Ps.getPpr()+score)
                                .setIcon(R.mipmap.information)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();

                    }else{
                        //if api is not ics then do this
                        openOptionMenu(view, Ps);
                    }*/
                }
            });

            return convertView;
        }

        class listviewHolder {  ImageView recipeImage; TextView recipeLabel; }
    }
}
