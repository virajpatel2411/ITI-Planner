package royal.com.itiplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


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

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, homeFragment);
        fragmentTransaction.commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.my_profile:
                        //Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
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
                    //fragmentTransaction.addToBackStack("homeFragment");
                    fragmentTransaction.commit();
                }


                return true;

            }
        });


    }

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, fragmentManager.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
        if (fragmentManager.getBackStackEntryCount()>0) {

            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, homeFragment);
            fragmentTransaction.commit();
            fragmentManager.popBackStack();
        }

    }*/
}
