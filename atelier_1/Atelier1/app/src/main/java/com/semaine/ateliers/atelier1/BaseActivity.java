package com.semaine.ateliers.atelier1;

import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /** Le layout qui sert de conteneur */
    protected DrawerLayout activityContainer;
    /** Le layout qui sert de contenu */
    protected FrameLayout activityContent;
    /** La barre d'outils de l'application */
    protected Toolbar mainToolbar;
    /** Le menu de navigation de l'application */
    protected NavigationView navigationView;

    /**
     * 1. On instancie d'abord le conteneur, en fait, on crée une hiérarchie de layout.
     *    On passe null comme parent puisque le conteneur est lui-même le parent.
     * 2. On instancie ensuite le layout qui sert de contenu.
     * 3. Ensuite, on insère le layout passé en paramètre dans le layout qui sert de
     *    contenu et on l'attache au layout parent, le conteneur.
     * 4. On dit à l'activité qui sert de gabarit que son contenu est le conteneur que l'on a crée.
     * 5. On crée la barre d'outils et le menu de navigation.
     *
     * @param layoutResID l'id du layout qui deviendra le contenu de l'activité
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        activityContainer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        activityContent = (FrameLayout) activityContainer.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContent, true);
        super.setContentView(activityContainer);
        setUpToolbar();
        setUpNavigationView();
        setTitle("Android Example");
    }

    /**
     * 1. On instancie d'abord la barre d'outils
     * 2. Si l'activité en cours utilise la barre d'outils, on l'ajoute à l'activité
     *    pour pouvoir gérer les options qu'elle offre (les 3 petits points, voir plus loin...)
     * 3. Sinon, on l'a cache pour ne pas qu'elle apparaisse dans l'activité
     */
    protected void setUpToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (useToolbar()) {
            setSupportActionBar(mainToolbar);
        } else {
            mainToolbar.setVisibility(View.GONE);
        }
    }

    /**
     * Par défaut, on veut que toutes les activités aient la barre d'outils.
     * Si une activité n'en a pas de besoin, vous avez seulement à réimplémenter
     * la fonction et retourner false car le tout est gérer dans la fonction setUpToolbar.
     * @return true par défaut
     */
    protected boolean useToolbar() {
        return true;
    }

    /**
     * 1. On instancie d'abord la vue pour le menu de navigation
     * 2. Si l'activité en cours n'utilise pas le menu de navigation, on empêche le mouvement
     *    dans l'écran (causé par le fait que le conteneur est un DrawerLayout)
     * 3. Si l'activité en cours utilise le menu de navigation,
     *    on ajoute l'activité en tant que listener pour gérer les clics (voir plus loin...),
     *    on crée le bouton "hamburger" (ActionBarDrawerToggle) qui permettra de l'ouvrir,
     *    on ajoute le bouton "hamburger" en tant que listener du conteneur de l'activité,
     *    et le dernier appel, syncState, sert tout simplement à gérer l'ouverture et la
     *    fermeture du menu de navigation et donc changer les images (hamburger vs arrow)
     * 3. Sinon si on utilise tout de même la barre d'outils, on affiche un bouton "home".
     */
    protected void setUpNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if(!useNavigationMenu()) {
            activityContainer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        if (useNavigationMenu() && navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityContainer, mainToolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            activityContainer.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        } else if (useToolbar()) {
            addHomeButtonToToolbar();
        }
    }

    protected void addHomeButtonToToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //TODO Ajouter l'image créée à l'atelier 1
            //getSupportActionBar().setHomeAsUpIndicator(ResourcesCompat.getDrawable(getResources(), R.drawable.monImageSVG, null));
        }
    }

    /**
     * Par défaut, on veut que toutes les activités aient le menu de navigation.
     * Si une activité n'en a pas de besoin, vous avez seulement à réimplémenter
     * la fonction et retourner false car le tout est gérer dans la fonction setUpNavigationView.
     * @return true par défaut
     */
    protected boolean useNavigationMenu() {
        return true;
    }

    /**
     * C'est ici que l'on gère les clics réalisés dans le menu de navigation.
     * Donc, tout dépendamment de ce que vous voulez faire, vous écrirez le code
     * en conséquence. Pour ma part, j'ai simplement un petit message qui s'affichera
     * en bas de l'écran pour afficher le nom de l'item cliqué. Il ne faut pas oublier
     * de fermer le menu de navigation lorsqu'un clic est réalisé.
     * @param item l'item sélectionné
     * @return true pour surligner l'item selectionné à l'écran
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Snackbar.make(activityContent, item.getTitle(), Snackbar.LENGTH_LONG).show();
        activityContainer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Permet de créer le menu de la barre d'outils (les 3 petits points).
     * @param menu le template de menu qui sera utilisé pour le menu choisi
     * @return true pour afficher le menu dans la barre d'outils
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    /**
     * C'est ici que l'on gère les clics réalisés dans les options de la barre d'outils.
     * Donc, tout dépendamment de ce que vous voulez faire, vous écrirez le code
     * en conséquence. Pour ma part, j'ai simplement un petit message qui s'affichera
     * en bas de l'écran pour afficher le nom de l'item cliqué.
     * @param item l'item sélectionné
     * @return true pour ne pas faire d'appels de fonctions inutiles
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar.make(activityContent, item.getTitle(), Snackbar.LENGTH_LONG).show();
        return true;
    }

}
