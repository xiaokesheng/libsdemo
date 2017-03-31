package com.zxb.libsdemo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.model.FileVideoDirectory;
import com.zxb.libsdemo.testfilenode.FileManegerx;
import com.zxb.libsdemo.testfilenode.FileNodex;
import com.zxb.libsdemo.util.MediaUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mrzhou on 16/5/28.
 */
public class TestFileSystemActivity extends BaseActivity {

    ListView lvVideoList;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_filesystem);
        tvResult = (TextView) findViewById(R.id.tvResult);


//        ArrayList<FileVideoDirectory> fileList =
//                (ArrayList<FileVideoDirectory>) MediaUtil.getFileVideoList(null, Environment.getExternalStorageDirectory());

        StringBuilder sb = new StringBuilder();
//        for (FileVideoDirectory item : fileList) {
//            sb.append(item.dirName + ": " + String.valueOf(item.number) + "\n");
//        }

        ArrayList<FileNodex> fileNodeList = FileManegerx.getInstance().getAllFiles();

        Collections.sort(fileNodeList);


        for (FileNodex item : fileNodeList) {
            sb.append(item.getFile().getName() + ", time:" + item.getFile().lastModified() + "\n");
        }

        sb.append("\n\n");

        ArrayList<FileNodex> folderNodeList = FileManegerx.getInstance().getAllPureFolders();

        for (FileNodex item : folderNodeList) {
            sb.append(item.getFile().getPath() + ", name = " + item.getFile().getName() + "\n");

            ArrayList<FileNodex> videoFileNodeList = FileManegerx.getInstance().getFiles(item);
            if (null != videoFileNodeList && videoFileNodeList.size() > 0) {
                for (FileNodex childNode : videoFileNodeList) {
                    sb.append("\t\t\t\t" + childNode.getFile().getName() + "\n");
                }
                sb.append("\n");
            }
        }

        tvResult.setText(sb.toString());
    }
}