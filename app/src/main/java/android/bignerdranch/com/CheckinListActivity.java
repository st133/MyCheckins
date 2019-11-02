package android.bignerdranch.com;

import androidx.fragment.app.Fragment;

public class CheckinListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new CheckinListFragment();
    }
}
