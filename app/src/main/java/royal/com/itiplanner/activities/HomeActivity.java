package royal.com.itiplanner.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import royal.com.itiplanner.R;
import royal.com.itiplanner.fragments.CustomizeFragment;
import royal.com.itiplanner.fragments.HomeFragment;
import royal.com.itiplanner.fragments.ProfileFragment;
import royal.com.itiplanner.fragments.SearchFragment;

public class HomeActivity extends AppCompatActivity {

  BottomNavigationView navigationView;
  private FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    navigationView = findViewById(R.id.bottom_nav_view);

    fragmentManager = getSupportFragmentManager();

    HomeFragment homeFragment = new HomeFragment();

    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.frame, homeFragment);
    fragmentTransaction.commit();
    navigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;
            switch (menuItem.getItemId()) {
              case R.id.home:
                fragment = new HomeFragment();
                break;
              case R.id.my_profile:
                fragment = new ProfileFragment();
                break;
              case R.id.search:
                fragment = new SearchFragment();
                break;
              case R.id.customize:
                fragment = new CustomizeFragment();
                break;
            }
            if (fragment != null) {
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.frame, fragment);
              fragmentTransaction.addToBackStack("homeFragment");
              fragmentTransaction.commit();
            }

            return true;
          }
        });
  }

  @Override
  public void onBackPressed() {
    //super.onBackPressed();
    if (fragmentManager.getBackStackEntryCount() > 0) {
      fragmentManager.popBackStack();
    } else {
      super.onBackPressed();
    }
  }
}
