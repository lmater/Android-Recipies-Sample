package com.mt2sconnect.recipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mt2sconnect.recipes.SQLDataStructure.DataBase;
import com.mt2sconnect.recipes.SQLDataStructure.Group;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GroupActivity extends AppCompatActivity {


    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabContactUs = (FloatingActionButton) findViewById(R.id.fabContactUS);
        fabContactUs.setImageResource(R.drawable.ic_menu_send);
        fabContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact US Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        fabShare.setImageResource(R.drawable.ic_menu_share);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Share app Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int index = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            index = ThreadLocalRandom.current().nextInt(DataBase.listGroups.size());
        }
        System.out.println("index: "+index);

        setTitle("  "+"  حلويات متنوعة  ");
        ImageView recipeImage = (ImageView) findViewById(R.id.headerImg);
        InputStream ims = null;
        try {
            ims = getAssets().open("imgs/"+DataBase.listGroups.get(index).getListRecipes().get(0).getUrl_img()+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        recipeImage.setImageDrawable(d);

        listview = (ListView) this.findViewById(R.id.listViewGroups);
//        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarDelete);
        listview.setAdapter(new ListviewStatAdapter(listview.getContext(), DataBase.listGroups));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public class ListviewStatAdapter extends BaseAdapter {
        private List<Group> listGroup;

        private LayoutInflater mInflater;

        public ListviewStatAdapter(Context fragment, List<Group> groups){
            listGroup = groups;
            mInflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listGroup.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return listGroup.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final listviewHolder holder;
            final Group Gr = listGroup.get(position);
//            if(convertView == null){
                convertView = mInflater.inflate(R.layout.layout_group_list, null);
                holder = new listviewHolder();
                holder.groupLabel = (TextView) convertView.findViewById(R.id.groupName);
                holder.groupImage = (ImageView) convertView.findViewById(R.id.groupImage);
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
                holder.groupLabel.setText("  "+Gr.getLabel_g()+" ");
            InputStream ims = null;
            try {
                if(Gr.getListRecipes().size()>0)
                ims = getAssets().open("imgs/"+Gr.getListRecipes().get(0).getUrl_img()+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
                holder.groupImage.setImageDrawable(d);

            holder.groupLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(GroupActivity.this, RecipeListActivity.class) ;
                    intent.putExtra("group",position+"");
                    startActivity(intent);
                }
            });
            holder.groupImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Intent intent =new Intent(GroupActivity.this, RecipeListActivity.class) ;
                    intent.putExtra("group",position+"");
                    startActivity(intent);
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

        class listviewHolder {  ImageView groupImage; TextView groupLabel; }
    }
}
