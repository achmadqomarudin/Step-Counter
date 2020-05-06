package com.example.stepcounter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.stepcounter.ui.Fragment_Overview;
import com.example.stepcounter.ui.Fragment_Settings;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(final Bundle b) {
        super.onCreate(b);
        startService(new Intent(this, SensorListener.class));
        if (b == null) {
            // Create new fragment and transaction
            Fragment_Overview newFragment = new Fragment_Overview();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this
            // fragment,
            // and add the transaction to the back stack
            transaction.replace(android.R.id.content, newFragment);

            // Commit the transaction
            transaction.commit();
        }

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 23 && PermissionChecker
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();

            MainActivity.this.getActionBar().setDisplayHomeAsUpEnabled(false);
            MainActivity.this.getActionBar().setTitle("Step Counter");
        } else {
            finish();
        }
    }

    public boolean optionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                MainActivity.this.getActionBar().setDisplayHomeAsUpEnabled(false);
                MainActivity.this.getActionBar().setTitle("Step Counter");
                break;
            case R.id.action_settings:
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new Fragment_Settings()).addToBackStack(null)
                        .commit();
                break;
            case R.id.action_leaderboard:
            case R.id.action_achievements:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("Google services required");
                builder2.setMessage(
                        "This feature is not available on the F-Droid version of the app");
                builder2.setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder2.create().show();
                break;
            case R.id.action_faq:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://j4velin.de/faq/index.php?app=pm"))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.about);
                builder.setMessage(getString(R.string.about_text_links));
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
        }
        return true;
    }
}
