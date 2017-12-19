package risabh_mishra.olaandroidplay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    home hfrag;
    BottomNavigationView navigation;
    MenuItem prevMenuItem;
    private ViewPager viewPager;
    private Favourites fFrag;
    private Developer developer;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_fav:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_dev:
                    viewPager.setCurrentItem(2);
                    return true;

            }
            return false;
        }
    };
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
ImageView imageView = (ImageView)findViewById(R.id.imageView);
Glide.with(this).load(R.drawable.olaplay).into(imageView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        hfrag = new home();
        fFrag = new Favourites();
        developer = new Developer();

        adapter.addFragment(hfrag);
        adapter.addFragment(fFrag);
        adapter.addFragment(developer);

        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}
