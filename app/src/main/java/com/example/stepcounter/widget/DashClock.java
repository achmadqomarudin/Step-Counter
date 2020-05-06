package com.example.stepcounter.widget;

import android.content.Intent;

import com.example.stepcounter.Database;
import com.example.stepcounter.MainActivity;
import com.example.stepcounter.R;
import com.example.stepcounter.ui.Fragment_Overview;
import com.example.stepcounter.util.Util;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

/**
 * Class for providing a DashClock (https://code.google.com/p/dashclock)
 * extension
 */
public class DashClock extends DashClockExtension {

    @Override
    protected void onUpdateData(int reason) {
        ExtensionData data = new ExtensionData();
        Database db = Database.getInstance(this);
        int steps = Math.max(db.getCurrentSteps() + db.getSteps(Util.getToday()), 0);
        data.visible(true).status(Fragment_Overview.formatter.format(steps))
                .icon(R.drawable.ic_dashclock)
                .clickIntent(new Intent(DashClock.this, MainActivity.class));
        db.close();
        publishUpdate(data);
    }

}
