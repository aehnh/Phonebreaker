package com.example.anders.breakyourphone;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppListViewActivity extends ListActivity {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    private int action;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applistview);

        action = getIntent().getIntExtra("action", 0);
        if(action == 0) {
            Toast.makeText(getApplication().getBaseContext(), "invalid action", Toast.LENGTH_LONG).show();
        }

        packageManager = getPackageManager();
        new LoadApplications().execute();
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Integer.toString(action), applist.get(position).packageName);
        editor.commit();

        Intent output = new Intent();
        output.putExtra("action", action);
        output.putExtra("appName", (applist.get(position)).loadLabel(packageManager));
        setResult(RESULT_OK, output);
        finish();
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = new ArrayList<ApplicationInfo>();
            List<ApplicationInfo> temp = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            for(ApplicationInfo applicationInfo : temp) {
                if(packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    applist.add(applicationInfo);
                }
            }
            listadaptor = new ApplicationAdapter(AppListViewActivity.this, R.layout.simple_list_item_3, applist);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AppListViewActivity.this, null, "Loading installed apps...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
