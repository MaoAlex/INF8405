/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexmao.projetfinal.Adapter.AdapterEvenement;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class TabActivity extends AppCompatActivity {

    /**
     * PagerAdapter va fournir les fragments pour chaque sections
     * FragmentPagerAdapter  va permettre de garder les fragments en mémoire
     * Possibilité d'utiliser FragmentStatePagerAdapter pour economiser de la mémoire
     *
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
//    private static RecyclerView mRecyclerView;
//    private static RecyclerView.Adapter mAdapter;
//    private static RecyclerView.LayoutManager mLayoutManager;
    /**
     * Le ViewPager va permettre d'afficher les trois sections principale de l'application, et un seul à la fois
     */
    ViewPager mViewPager;
    TabLayout tabLayout;
    AppSectionsPagerAdapter pagerAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);

//        // Crée un adpater qui va retourner un fragment pour chacune des trois sections principales de l'appli
//        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
//
//        // Mise en place de l'actionBar.
//        final ActionBar actionBar = getActionBar();
////
////        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////        // Désactive le bouton Home/up, car il n'y a pas de parent
////        actionBar.setHomeButtonEnabled(false);
////
////        // Mise en place des tabs dans la barre d'action
////        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        // Mise en place des différentes pages
//        //On attache l'adapter et les listener pour l'ecoute des swipes entre les parties
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mAppSectionsPagerAdapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        //indique au tablayout quel est le viewpager à écouter
//        tabLayout.setupWithViewPager(mViewPager);
//        //TabLayout tabLayout = (TabLayout)findViewyId(R.id.tabs);
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // Lors du Swipe entre différentes sections, choisi la bonne tab
//                // Utilisation possible de ActionBar.Tab#select() si on a la référence de la tab
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//
//        // Pour chaque section de l'app, on ajjoute une action bar
//        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
//            // Crée la tab avec le texte correspondant défini par l'adapter.
//            // Spécification de l'activité objet, qui implémente l'interface du tabListerner
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
        // Récupération de la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Utilisatino de la toolbar comme action bar
        setSupportActionBar(toolbar);

        // Ajout d'un sous titre
        getSupportActionBar().setSubtitle("Page Accueil");

        // Récupération de la tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Definition de la gravité et de son mode
        // Define its gravity and its mode
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Instantiation du PageAdapter
        // Instanciate the PageAdapter
        pagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), this);

        // Define the color to use (depending on the state a different color should be disaplyed)
        // Définition des couleurs à utiliser en cas d'actions sur les tabs
        // Fonctionnel que si l'association est fait avant l'ajout des tabs
        // Works only if done before adding tabs
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab));

        // Récupération du ViewPager
        mViewPager = (ViewPager) super.findViewById(R.id.viewpager);

        // Affectation de l'adapter au ViewPager
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(12);

        // Add animation when the page are swiped
        // this instanciation only works with honeyComb and more
        // if you want it all version use AnimatorProxy of the nineoldAndroid lib
        // @see:http://stackoverflow.com/questions/15767729/backwards-compatible-pagetransformer
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            //mViewPager.setPageTransformer(true, new AppSectionsPagerAdapter(this));
        }
        // AND CLUE TABLAYOUT AND VIEWPAGER
        tabLayout.setupWithViewPager(mViewPager);
        // Populate your TabLayout
        // Ajout des tab à la tabLayout
//        tabLayout.addTab(tabLayout.newTab().setText("Evenement"));
//        tabLayout.addTab(tabLayout.newTab().setText("Découvertes"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        // Iterate over all tabs and set the custom view
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            //tab.setCustomView(pagerAdapter.getTabView(i));
//        }
        // Ajout d'un listener pour le changement de tab
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {/*do your navigation*/}
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {/*do nothing*/}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {/*do nothing*/}
        });
    }

//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        // Lorsque une tab est choisie, va à la page correspondante dans le ViewPager
//        mViewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }

    /**
     * FragmentPagerAdapter retourne le fragment correspondant a une des sections principales de l'appli
     */
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<Fragment> fragments;
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private Context context;
        public AppSectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            fragments = new ArrayList<Fragment>();
            this.context = context;
            fragments.add(new LaunchpadSectionFragment());
            fragments.add(new FragmentTest());
            //fragments.add(new DummySectionFragment());

        }
//        public  View getTabView(int position) {
//            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
////            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
////            TextView tv = (TextView) v.findViewById(R.id.textView);
////            tv.setText(tabTitles[position]);
////            ImageView img = (ImageView) v.findViewById(R.id.imgView);
////            img.setImageResource(imageResId[position]);
////            return v;
//            View rootView = LayoutInflater.from(context).inflate(R.layout.content_main,null);
////            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
////                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
//            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//            mRecyclerView.setHasFixedSize(true);
////            mLayoutManager = new LinearLayoutManager(getContext());
////            mRecyclerView.setLayoutManager(mLayoutManager);
//
//            return mRecyclerView;
////
//        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.

                    return new LaunchpadSectionFragment();
                case 1 :
                    return new FragmentTest();
                default:
                    // Les sections sont actuellements des sections par défaut
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }

            //return fragments.get(i);
        }

        @Override
        public int getCount() {
            return 3;//fragments.size();
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            // Inflate the layout for the page
//            RecyclerView mRecyclerView = (RecyclerView)LayoutInflater.from(context).inflate(R.layout.fragment_recycler_decouvertes, container, false);
//            // Find and populate data into the page (i.e set the image)
////            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            // ...
//            // Add the page to the container
//            //mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_decouvertes, container, false);
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            mRecyclerView.setHasFixedSize(true);
//
//            // use a linear layout manager
//            mLayoutManager = new LinearLayoutManager(context);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//
//            // specify an adapter (see also next example)
//            String[] myDataset = {"test"};
//            mAdapter = new AdapterEvenement(myDataset);
//            mRecyclerView.setAdapter(mAdapter);
//            container.addView(mRecyclerView);
//            // Return the page
//            return mRecyclerView;
//        }

        // Removes the page from the container for the given position.
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Evenement";
            else if(position == 1)
                return "Decouvertes";
            else
                return "Participation";
        }


    }

    /**
     * Fragment qui lance d'autres partie de l'appli
     */
    public static class LaunchpadSectionFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_launchpad, container, false);

            // Demonstration of a collection-browsing activity.
            rootView.findViewById(R.id.demo_collection_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), CollectionDemoActivity.class);
                            startActivity(intent);
                        }
                    });

            // Demonstration of navigating to external activities.
            rootView.findViewById(R.id.demo_external_activity)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Create an intent that asks the user to pick a photo, but using
                            // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
                            // the application from the device home screen does not return
                            // to the external activity.
                            Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                            externalActivityIntent.setType("image/*");
                            externalActivityIntent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            startActivity(externalActivityIntent);
                        }
                    });

            return rootView;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
//            Bundle args = getArguments();
////            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
////                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//            View rootView = inflater.inflate(R.layout.content_main, container, false);
            Bundle args = getArguments();
//            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));

            mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_participations, container, false);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            String[] myDataset = {"test"};
            ArrayList<Evenement> evenements = new ArrayList<>();
            Date date = new Date();
            Utilisateur u1 = new Utilisateur();
            u1.setNom("FR");
            evenements = new ArrayList<>();
            Evenement evenement = new Evenement();
            evenement.setNbreMaxParticipants(10);
            GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
            date = test.getTime();
            evenement.setDate(date);
            evenement.setLieu("cepsum");
            evenement.setNomEvenement("One day");
            evenement.setOrganisateur(u1);
            evenement.setSport("basket");
            Groupe g = new Groupe();

            g.setListeMembre(new ArrayList<Utilisateur>());
            g.getListeMembre().add(u1);

            Utilisateur u2 = new Utilisateur();
            u2.setNom("Poly Technique");
            u2.setDateNaissance(test.getTimeInMillis());
            Utilisateur u3 = new Utilisateur();
            u3.setNom("Mont Real");
            u3.setDateNaissance(test.getTimeInMillis());
            g.getListeMembre().add(u2);
            g.getListeMembre().add(u3);
            evenement.setGroupeAssocie(g);
            evenements.add(evenement);
            evenements.add(evenement);
            Evenement evenement1 = new Evenement();
            evenement1 = evenement;
            evenements.add(evenement1);
            mAdapter = new AdapterEvenement(evenements);
            mRecyclerView.setAdapter(mAdapter);



            return mRecyclerView;
        }


    }

    public static class FragmentTest extends Fragment {
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_decouvertes, container, false);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            String[] myDataset = {"test"};
            mAdapter = new AdapterEvenement(myDataset);
            mRecyclerView.setAdapter(mAdapter);
            return mRecyclerView;
        }
    }
}
