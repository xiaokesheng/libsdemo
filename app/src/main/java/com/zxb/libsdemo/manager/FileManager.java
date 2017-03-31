package com.zxb.libsdemo.manager;

import android.os.Environment;
import android.util.Log;

import com.zxb.libsdemo.model.FileNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrzhou on 16/5/29.
 */
public class FileManager {

    private static FileManager instance;

    private FileNode rootNode;

    private FileManager() {
        build();
    }

    public static FileManager getInstance() {
//        if (instance == null) {
//            instance = new FileManager();
//        }
        return instance;
    }

    private void build() {
        rootNode = new FileNode(Environment.getExternalStorageDirectory());

        buildNode(rootNode, null);
    }

    private void buildNode(FileNode fileNode, FileNode parent) {
        File file = fileNode.getFile();

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File item : files) {
                FileNode node = new FileNode(item);
                node.setParent(parent);
                node.setType(0);
                buildNode(node, fileNode);
            }

            if (null != fileNode.getChildren() && fileNode.getChildren().size() > 0 && null != parent) {
                parent.addChild(fileNode);
            }

        } else {
            if (file.getName().endsWith(".mp4")) {
                FileNode newNode = new FileNode(file);
                newNode.setParent(parent);
                newNode.setType(1);
                fileNode.getParent().addChild(newNode);
            }
        }
    }

    public List<String> getVideo() {
        if (null == rootNode) {
            return null;
        }
        ArrayList<String> fileStringList = new ArrayList<>();
        fileStringList = getVideoFileNames(rootNode, fileStringList);

        return fileStringList;
    }

    private ArrayList<String> getVideoFileNames(FileNode node, ArrayList<String> fileStringList) {
        if (node.getType() == 1) {
            fileStringList.add(node.getFile().getName());
            Log.e("video", "parent: " + node.getParent() != null ? node.getParent().getFile().getName() : "/" +
                    "fileName: " + node.getFile().getName());
        } else {
            for (FileNode item : node.getChildren()) {
                getVideoFileNames(item, fileStringList);
            }
        }
        return fileStringList;
    }

//    public ArrayList<FileNode> getAllFolders() {
//        if (null == rootNode) {
//            return null;
//        }
//
//        ArrayList<FileNode> nodeList = new ArrayList<>();
//        return getAllFolders(rootNode, nodeList);
//    }

//    private ArrayList<FileNode> getAllFolders(FileNode node, ArrayList<FileNode> folders) {
//        if (node.getType() == 1) {
//            return folders;
//        } else {
//            return folders;
//        }
//    }

    public void listAllNodes() {
        if (null == rootNode) {
            return;
        }

        listAllNodes(rootNode);
    }

    private void listAllNodes(FileNode node) {

    }
}
