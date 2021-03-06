package com.github.glomadrian.wallapopcodetest.ui.main.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.Bind;
import com.github.glomadrian.wallapopcodetest.R;
import com.github.glomadrian.wallapopcodetest.app.AbstractActivity;
import com.github.glomadrian.wallapopcodetest.app.MainApplication;
import com.github.glomadrian.wallapopcodetest.app.di.component.ViewComponent;
import com.github.glomadrian.wallapopcodetest.app.di.component.application.ApplicationComponent;
import com.github.glomadrian.wallapopcodetest.app.di.provider.ComponentProvider;
import com.github.glomadrian.wallapopcodetest.ui.LifeCyclePresenter;
import com.github.glomadrian.wallapopcodetest.ui.comicinfo.view.ComicInfoFragment;
import com.github.glomadrian.wallapopcodetest.ui.comics.view.ComicsFragment;
import com.github.glomadrian.wallapopcodetest.ui.main.presenter.MainPresenter;
import javax.inject.Inject;

public class MainActivity extends AbstractActivity {

  public static final String TAG_PORTRAIT = "V11-portrait";
  public static final String TAG_LANDSCAPE = "V11-landscape";
  @Inject protected MainPresenter mainPresenter;
  @Bind(R.id.comics_frame) protected FrameLayout charactersFrame;
  @Bind(R.id.toolbar) protected Toolbar toolbar;
  @Bind(R.id.drawer_layout) protected DrawerLayout drawerLayout;
  private ActionBarDrawerToggle actionBarDrawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(toolbar);

    actionBarDrawerToggle =
        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,
            R.string.app_name);
    drawerLayout.setDrawerListener(actionBarDrawerToggle);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    actionBarDrawerToggle.syncState();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onViewReady() {
    super.onViewReady();
    mainPresenter.onViewReady();

    if (TAG_PORTRAIT.equals(view.getTag())) {
      mainPresenter.onPortrait();
    } else {
      mainPresenter.onLandScape();
    }
  }

  @Override
  public ViewComponent bindViewComponent() {
    ApplicationComponent applicationComponent =
        ((MainApplication) getApplication()).getApplicationComponent();
    return ComponentProvider.getMainViewComponent(applicationComponent);
  }

  @Override
  public LifeCyclePresenter bindPresenter() {
    return mainPresenter;
  }

  @Override
  public int bindLayout() {
    return R.layout.activity_main;
  }

  public void showComicsView() {
    ComicsFragment comicsFragment = ComicsFragment.newInstance();
    getSupportFragmentManager().beginTransaction().add(R.id.comics_frame, comicsFragment).commit();
  }

  public void showComicInfoView(int comicId) {
    ComicInfoFragment comicInfoFragment = ComicInfoFragment.newInstance(comicId);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.details_frame, comicInfoFragment)
        .commit();
  }
}
