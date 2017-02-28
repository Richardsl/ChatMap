package sl.richard.app.chatmap;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MapFragment.MapFragmentListener{

    ImageButton photoButton;

    // viewpager
    TabLayout tabLayout;
    ViewPager viewPager;

    Fragment chatFragment;

    // viewpager END

    static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;

    // Camera END


    @Override
    public void setTextView(String string) {
        List<Fragment> list = getSupportFragmentManager().getFragments();
        Toast.makeText(this, list.get(0).getTargetFragment().getId(), Toast.LENGTH_SHORT).show();

       // chatFragment = list.get(0).getTargetFragment().getId();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set fullscreen and add transparency to top "battery" status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.argb(200,150,150,150));





        photoButton = (ImageButton) findViewById(R.id.PhotoButton);



        /*
        * This is where all the Viewpager code is
        * Also this is where the animations for the icons are set as we swipe
        *
        */

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));




        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Log.i("richardlog","onTabSelected "+ tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Log.i("richardlog","onTabUnselected " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Log.i("richardlog","onTabReselected "+ tab.getText());
            }
        });

        // remove text from bottom
        tabLayout.getTabAt(1).setText("");



        //make the photobutton bouncy
        photoButton.animate().setInterpolator(new BounceInterpolator());

        photoButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    photoButton.animate().scaleX(1.5f).scaleY(1.5f).setDuration(400).start();
                    tabLayout.getTabAt(1).select();
                    Log.i("richardlog", "Photo button Action_Down");
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    photoButton.animate().scaleX(1.0f).scaleY(1.0f).setDuration(400).start();
                    Log.i("richardlog", "Action_Up");
                }
                return true;
            }
        });


        // alot of animation stuff here
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int onPageStateScrollChanged;
            int lastPosition;
            int onPageSelected;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //invert the parameter
                float InvPositionOffset = (1 - positionOffset);

                float buttonHeightAfterSelected= -130.0f;

                //map range from 0-1 to 0.5-1 to use in alpha animation because we don't want the icon to disappear completely
                float R = (1-0.5f)/(1-0);
                float positionOffsetAlpha = positionOffset *R+0.5f;
                float InvPositionOffsetAlpha = -positionOffset *R+1f;
//
//                Log.i("richardlog ","positionOffset "+positionOffset);
//                Log.i("richardlog ","positionOffsetAlpha "+positionOffsetAlpha);
//                Log.i("richardlog ","-----------");
//                Log.i("richardlog ","InvPositionOffset "+InvPositionOffset);
//                Log.i("richardlog ","InvPositionOffsetAlpha "+InvPositionOffsetAlpha);
//                Log.i("richardlog ","-----------");
//
//
//                Log.i("richardlog ","positionOffsetAlpha "+positionOffsetAlpha);
//                Log.i("richardlog ","INVpositionOffsetAlpha "+InvPositionOffsetAlpha);
//
//                Log.i("richardlog ","onPageScrolled   position:" + position + " positionoffset:"+positionOffset+" positionoffsetpixels:"+positionOffsetPixels);
//                Log.i("richardlog ","lastPosition:"+lastPosition);

                // onPageSelected because we don't want the animation to run if we go from 0 to 2 or vice
                if (lastPosition == 0 && onPageSelected != 2) {
                    photoButton.setTranslationY(buttonHeightAfterSelected * positionOffset);
                    photoButton.setAlpha(positionOffsetAlpha);
                    Log.i("richardlog ","1");
                }
                else if (lastPosition == 2 && onPageSelected != 0) {
                    photoButton.setTranslationY(buttonHeightAfterSelected * InvPositionOffset);
                    photoButton.setAlpha(InvPositionOffsetAlpha);
                    Log.i("richardlog ","2");
                }

                else if (lastPosition == 1) {

                    if (position == 1) {
                        photoButton.setTranslationY(buttonHeightAfterSelected * InvPositionOffset);
                        photoButton.setAlpha(InvPositionOffsetAlpha);
                        Log.i("richardlog ","one");
                    }
                    else if (position == 0) {
                        photoButton.setTranslationY(buttonHeightAfterSelected * positionOffset);
                        photoButton.setAlpha(positionOffsetAlpha);
                        Log.i("richardlog ","two");
                    }
                }

                // set the final animation values
                if (positionOffset == 0.0) {

                    lastPosition = position;

                    if (position == 0) {
                        photoButton.setTranslationY(0);
                        photoButton.setAlpha(0.5f);
                    }
                    else if (position == 1) {
                        photoButton.setTranslationY(buttonHeightAfterSelected);
                        photoButton.setAlpha(1f);
                    }
                    else if (position == 2) {
                        photoButton.setTranslationY(0);
                        photoButton.setAlpha(0.5f);
                    }
                }

            }

            @Override
            public void onPageSelected(int position) {

                onPageSelected = position;
                Log.i("richardlog ","!!!!!!!!!!!onPageSelected!!!!!!!!!!!" + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("richardlog ","onPageScrollStateChanged" + state);
                onPageStateScrollChanged = state;

            }
        });









    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(),"onResume",Toast.LENGTH_SHORT).show();




    }



    @Override
    protected void onPause() {

        super.onPause();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CAMERA_PERMISSION_RESULT){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"application will not run without camera permission",Toast.LENGTH_SHORT).show();
            }
        }
    }






















    private class CustomAdapter extends FragmentPagerAdapter {

        private String fragments[] = {"Chat","Photo", "Map"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext ){
            super(supportFragmentManager);

        }

        @Override
        public Fragment getItem(int position) {
           switch (position){
               case 0:
                   return new ChatFragment();
               case 1:
                   return new PhotoFragment();
               case 2:
                   return new MapFragment();
               default:
                   return null;
           }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
