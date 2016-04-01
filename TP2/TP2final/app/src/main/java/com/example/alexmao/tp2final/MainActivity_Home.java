package com.example.alexmao.tp2final;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;
import com.example.alexmao.tp2final.firebase.GroupTestActivity;
import com.example.alexmao.tp2final.firebase.LocalUser;
import com.example.alexmao.tp2final.firebase.MDPTestActivity;
import com.example.alexmao.tp2final.firebase.MapActivity;
import com.example.alexmao.tp2final.firebase.MyGroup;
import com.example.alexmao.tp2final.firebase.MyLocalGroup;
import com.example.alexmao.tp2final.firebase.RemoteBD;
import com.example.alexmao.tp2final.firebase.UserFirebase;
import com.example.alexmao.tp2final.fragment.testActivity;
import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.ArrayList;

public class MainActivity_Home extends ConnectedMapActivity {

    private static final String DEBUG_TAG = "MainActivity_Home";
    MaterialViewPager materialViewPager;
    View headerLogo;
    ImageView headerLogoContent;
    private int positionCourante;
    GroupeBDD groupeBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity__home);
        setLocalUser((LocalUser) getIntent().getParcelableExtra("localUser"));

        //4 onglets
        final int tabCount = 4;
        UsersBDD userBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);
        userBDD.open();
        groupeBDD.open();
        userBDD.affichageUsers();
        ArrayList<User> lUser = userBDD.getUsers();
        userBDD.affichageUtilisateurConnecte();
        //les vues définies dans @layout/header_logo
        headerLogo = findViewById(R.id.headerLogo);
        headerLogoContent = (ImageView) findViewById(R.id.headerLogoContent);
        headerLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (positionCourante == 2) {
                    Intent intent = new Intent(com.example.alexmao.tp2final.MainActivity_Home.this, MapActivity.class);
                    intent.putExtra("localUser", localUser);
                    startActivity(intent);
                }else if(positionCourante == 0) {
                    Intent intent = new Intent(com.example.alexmao.tp2final.MainActivity_Home.this, testActivity.class);
                    intent.putExtra("localUser", localUser);
                    startActivity(intent);
                }
                else{
                    if (positionCourante == 3) {
                        Intent intent = new Intent(com.example.alexmao.tp2final.MainActivity_Home.this, testActivity.class);
                        intent.putExtra("localUser", localUser);
                        startActivity(intent);
                    }
                    Intent intent = new Intent(com.example.alexmao.tp2final.MainActivity_Home.this, UserProfileActivity.class);
                    startActivity(intent);
                }

            }
        });;
        //le MaterialViewPager
        this.materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        //remplir le ViewPager
        this.materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                //je créé pour chaque onglet un RecyclerViewFragment
                Log.d(DEBUG_TAG, " on recupere la position :  " + position);

                switch (position % 4) {
                    case 0:
                    case 1:
                        //return RecyclerViewFragment.newInstance();
                    case 2:
                        //return WebViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance(positionCourante);
                }
            }

            @Override
            public int getCount() {
                return tabCount;
            }

            //le titre à afficher pour chaque page
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getResources().getString(R.string.accueil);
                    case 1:
                        return getResources().getString(R.string.groupe);
                    case 2:
                        return getResources().getString(R.string.localisation);
                    case 3:
                        return getResources().getString(R.string.profil);
                   /* case 4:
                        return getResources().getString(R.string.parametres);*/
                    default:
                        return "Page " + position;
                }
            }

            int oldItemPosition = -1;

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                //seulement si la page est différente
                if (oldItemPosition != position) {
                    oldItemPosition = position;
                    positionCourante = position;
                    //définir la nouvelle couleur et les nouvelles images
                    String imageUrl = null;
                    int color = Color.BLACK;
                    Drawable newDrawable = null;

                    switch (position) {
                        case 0:
                            //Pour récupérer l'image depuis l'url
                            imageUrl = "http://www.skyscanner.fr/sites/default/files/image_import/fr/micro.jpg";
                            color = getResources().getColor(R.color.purple);
                            newDrawable = getResources().getDrawable(R.drawable.people);

                            break;
                        case 1:
                            imageUrl = "http://www.larousse.fr/encyclopedie/data/images/1311904-Balle_de_tennis_et_filet.jpg";
                            color = getResources().getColor(R.color.orange);
                            newDrawable = getResources().getDrawable(R.drawable.groupe);
                            break;
                        case 2:
                            imageUrl = "http://soocurious.com/fr/wp-content/uploads/2014/03/8-facettes-de-notre-cerveau-qui-ont-evolue-avec-la-technologie8.jpg";
                            color = getResources().getColor(R.color.cyan);
                            newDrawable = getResources().getDrawable(R.drawable.tool);
                            break;
                        case 3:
                            imageUrl = "http://graduate.carleton.ca/wp-content/uploads/prog-banner-masters-international-affairs-juris-doctor.jpg";
                            color = getResources().getColor(R.color.blue);
                            newDrawable = getResources().getDrawable(R.drawable.profil);
                            break;

                    }

                    //puis modifier les images/couleurs
                    int fadeDuration = 400;
                    materialViewPager.setColor(color, fadeDuration);
                    materialViewPager.setImageUrl(imageUrl, fadeDuration);
                    toggleLogo(newDrawable, color, fadeDuration);
                }
            }

            private void toggleLogo(final Drawable newLogo, final int newColor, int duration) {

                //animation de disparition
                final AnimatorSet animatorSetDisappear = new AnimatorSet();
                animatorSetDisappear.setDuration(duration);
                animatorSetDisappear.playTogether(
                        ObjectAnimator.ofFloat(headerLogo, "scaleX", 0),
                        ObjectAnimator.ofFloat(headerLogo, "scaleY", 0)
                );

                //animation d'apparition
                final AnimatorSet animatorSetAppear = new AnimatorSet();
                animatorSetAppear.setDuration(duration);
                animatorSetAppear.playTogether(
                        ObjectAnimator.ofFloat(headerLogo, "scaleX", 1),
                        ObjectAnimator.ofFloat(headerLogo, "scaleY", 1)
                );

                //après la disparition
                animatorSetDisappear.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        //modifie la couleur du cercle
                        ((GradientDrawable) headerLogo.getBackground()).setColor(newColor);

                        //modifie l'image contenue dans le cercle
                        headerLogoContent.setImageDrawable(newLogo);

                        //démarre l'animation d'apparition
                        animatorSetAppear.start();
                    }
                });

                //démarre l'animation de disparition
                animatorSetDisappear.start();
            }

        });


        //permet au viewPager de garder 4 pages en mémoire (à ne pas utiliser sur plus de 4 pages !)
        this.materialViewPager.getViewPager().setOffscreenPageLimit(tabCount);
        //relie les tabs au viewpager
        this.materialViewPager.getPagerTitleStrip().setViewPager(this.materialViewPager.getViewPager());
//        prepareUser();
        userBDD.close();
        groupeBDD.close();
    }


    private void testCreateUser() {
        localUser = new LocalUser("fifi", "test", "exemple@polymtl.ca");
//        RemoteBD remoteBD = getMyRemoteBD();
//        String userBDID = remoteBD.addUser((UserFirebase) localUser);
//        remoteBD.addMdpToUser(localUser.getMailAdr().trim(), "fifi");
//        localUser.setDataBaseId(userBDID);
        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                getMyRemoteBD().updateLocationOnServer((UserFirebase) localUser, localUser.getDataBaseId());
            }
        });
        setLocalUser(localUser);
    }

    private String prepareGroupTest() {
        RemoteBD remoteBD = getMyRemoteBD();
        localUser = new LocalUser("fifi", "test1", "exemple1@polymtl.ca");
        String userBDID = remoteBD.addUser((UserFirebase) localUser);
        localUser.setDataBaseId(userBDID);
        MyLocalGroup myLocalGroup = new MyLocalGroup("test group", localUser.getDataBaseId());


        localUser = new LocalUser("fifi", "test2", "exemple2@polymtl.ca");
        userBDID = remoteBD.addUser((UserFirebase) localUser);
        localUser.setDataBaseId(userBDID);
        myLocalGroup.addMember(userBDID);


        localUser = new LocalUser("fifi", "test3", "exemple3@polymtl.ca");
        userBDID = remoteBD.addUser((UserFirebase) localUser);
        localUser.setDataBaseId(userBDID);
        myLocalGroup.addMember(userBDID);

        return remoteBD.addGroup((MyGroup) myLocalGroup);
    }

    private void gotoMap() {
        Intent intent = new Intent(MainActivity_Home.this, MapActivity.class);
        intent.putExtra("localUser", localUser);
        startActivity(intent);
    }

    private void gotoGroupTest(String myLocalGroupID) {
        Intent intent = new Intent(MainActivity_Home.this, GroupTestActivity.class);
        intent.putExtra("localUser", localUser);
        intent.putExtra("groupID", myLocalGroupID);
        startActivity(intent);
    }

    private void gotoMDPTest() {
        Intent intent = new Intent(MainActivity_Home.this, MDPTestActivity.class);
        startActivity(intent);
    }

    private void prepareUser() {
        Log.d(DEBUG_TAG, "prepareUser: " + "creation of id");
        testCreateUser();
    }
}
