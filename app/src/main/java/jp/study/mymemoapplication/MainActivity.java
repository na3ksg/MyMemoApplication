package jp.study.mymemoapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    String fileName = "";
    static String encoding = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fileName = getResources().getString(R.string.app_name) + ".txt";
        File f = this.getFileStreamPath(fileName);
        if (f.exists()) {
            try {
                InputStream is = openFileInput(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
                StringBuffer sb = new StringBuffer();
                int c;
                while ((c = br.read()) != -1)
                    sb.append((char) c);
                EditText et = (EditText) findViewById(R.id.editText1);
                et.setText(sb.toString());
                br.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                //noinspection SimplifiableIfStatement
                showMessage("設定機能は未実装です");
                return true;
            case R.id.action_save:
                outputToFile();
                return true;
            case R.id.action_clear:
                EditText et = (EditText) findViewById(R.id.editText1);
                et.setText("");
                return true;
            case R.id.action_exit:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String s) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(s);
        b.setPositiveButton(android.R.string.ok, null);
        b.show();
    }

    private void outputToFile() {
        EditText et = (EditText) findViewById(R.id.editText1);
        String s = et.getText().toString();
        if (s.equals("")) {
            deleteFile(fileName);
        } else {
            try {
                OutputStream os = openFileOutput(fileName, MODE_PRIVATE);
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, encoding));
                pw.write(s);
                pw.close();
                os.close();
                showMessage(this.getFileStreamPath(fileName).toString() + "に保存しました");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
