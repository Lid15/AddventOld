package nord.is.addvent;

import android.support.v4.app.Fragment;

public class AddventActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AddventFragment.newInstance();
    }
}
