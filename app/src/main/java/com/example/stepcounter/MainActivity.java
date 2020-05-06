package com.example.stepcounter;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStackImmediate();
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
//        } else {
//            finish();
//            Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
//        }
//    }

    public boolean optionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.action_settings:
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new Fragment_Settings()).addToBackStack(null)
                        .commit();
                break;
            case R.id.action_leaderboard:
            case R.id.action_achievements:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
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
                TextView tv = new TextView(this);
                tv.setPadding(10, 10, 10, 10);
                tv.setText(R.string.about_text_links);
                try {
                    tv.append(getString(R.string.about_app_version,
                            getPackageManager().getPackageInfo(getPackageName(), 0).versionName));
                } catch (PackageManager.NameNotFoundException e1) {
                    // should not happen as the app is definitely installed when
                    // seeing the dialog
                    e1.printStackTrace();
                }
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                builder.setView(tv);
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
