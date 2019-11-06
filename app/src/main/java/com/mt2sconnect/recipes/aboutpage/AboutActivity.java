package com.mt2sconnect.recipes.aboutpage;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mt2sconnect.recipes.R;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
//ca-app-pub-4178605509245433~7103897343 AdMob ID of the App Recipes

    /*Ensuite, insérez le bloc d'annonces dans votre application.
    Procédez comme suit :

    Suivez les instructions du guide SDK Google Mobile Ads en utilisant cet ID d'application :
    https://developers.google.com/admob/android/quick-start?hl=fr#import_the_mobile_ads_sdk
    test Ads: https://developers.google.com/admob/android/test-ads
    Recipes ca-app-pub-4178605509245433~7103897343
    Pour intégrer le SDK, suivez le guide d'insertion des annonces interstitielles. Vous définirez le type et l'emplacement de l'annonce au moment de l'intégration du code à l'aide de cet ID de bloc d'annonces :
    https://developers.google.com/admob/android/interstitial?hl=fr
    adsZone ca-app-pub-4178605509245433/3809264565
    Consultez les Règles AdMob pour vérifier que votre intégration les respecte.
    https://support.google.com/admob/answer/6128543?hl=fr

    Envoyer les instructions par e-mail
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
//        Element adsElement = new Element();
//        adsElement.setTitle("Nous Contacter");
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.recipe_book_2)
//                .addItem(adsElement)
                .addGroup("Nous Contacter")
                .addEmail("tariqelmoussaoui@gmail.com")
                .addPhone("0660749553")
                .addWebsite("http://drh.sante.gov.ma/Pages/MA_D.aspx")
                .addFacebook("tariq.elmoussaoui")
                .addYoutube("UCp8jgoVFF1IlDFa58Kwc8Tw")
//                .addItem(new Element().setTitle("Version 1.0.1"))
                .addItem(getCopyRightsElement())
                .create();

        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarabout);
        setSupportActionBar(toolbar);

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_about);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_about);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getMenu().getItem(5).setChecked(true);
        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Version 1.0.1", Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
    // Drawer Menu
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_about);
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/
            super.onBackPressed();
        /*}*/
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
            launchPayActivity();
            finish();

        } else if (id == R.id.nav_gallery) {
            launchRechargeActivity();
            finish();
        } else if (id == R.id.nav_slideshow) {
            launchStudentActivity();
            finish();
        } else if (id == R.id.nav_manage) {
            launchAddUpdateActivity();
            finish();
        } else if (id == R.id.nav_share) {
            finish();
        } else*/ if (id == R.id.nav_send) {
            launchAboutActivity();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_about);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void launchAboutActivity() {

        // Intent intent = new Intent(this, ProfileActivity.class);
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }
}
