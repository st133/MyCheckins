package android.bignerdranch.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;


public class CheckinActivity extends AppCompatActivity {

    private static final String EXTRA_CHECKIN_ID =
            "com.bignerdranch.android.mycheckins.checkin_id";

    private ViewPager mViewPager;
    private List<Checkin> mCheckins;

    public static Intent newIntent(Context packageContext, UUID checkinId) {
        Intent intent = new Intent(packageContext, CheckinActivity.class);
        intent.putExtra(EXTRA_CHECKIN_ID, checkinId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        UUID checkinId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CHECKIN_ID);

        mViewPager = (ViewPager) findViewById(R.id.checkin_view_pager);

        mCheckins = CheckinStore.get(this).getCheckins();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @Override
            public Fragment getItem(int position) {
                Checkin checkin = mCheckins.get(position);
                return CheckinFragment.newInstance(checkin.getId());
            }

            @Override
            public int getCount() {
                return mCheckins.size();
            }
        });

        for (int i = 0; i < mCheckins.size(); i++) {
            if (mCheckins.get(i).getId().equals(checkinId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
    /*@Override
    protected void onResume() {
        super.onResume();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            errorDialog.show();
        }
    }*/
}
