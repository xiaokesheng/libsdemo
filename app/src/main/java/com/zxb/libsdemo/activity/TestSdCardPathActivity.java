package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.zxb.libsdemo.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by mrzhou on 16/6/17.
 */
public class TestSdCardPathActivity extends BaseActivity {

    TextView tvSdcardPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_sdcard_path);

        tvSdcardPath = (TextView) findViewById(R.id.tvSdcardPath);

        getPath2();
    }

    public String getPath2() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        Log.d("text", sd_default);
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
        try {
            StringBuilder sb = new StringBuilder();

            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");

                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }
            }

            sb.append("\n\n\n" + sdcard_path);

            tvSdcardPath.setText(sb.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("text", sdcard_path);
        return sdcard_path;
    }
}
