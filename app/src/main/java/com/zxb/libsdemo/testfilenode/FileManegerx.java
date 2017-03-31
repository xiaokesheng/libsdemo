package com.zxb.libsdemo.testfilenode;

import android.os.Environment;

import com.zxb.libsdemo.model.FileNode;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mrzhou on 16/5/29.
 */
public class FileManegerx {

    static FileManegerx instance;

    static FileNodex rootNode;

    private FileManegerx() {
        build();
    }

    public static FileManegerx getInstance() {
//        if (null == instance) {
//            instance = new FileManegerx();
//        }
        return instance;
    }

    private void build() {
        rootNode = new FileNodex(Environment.getExternalStorageDirectory());

        buildNode(rootNode);
    }

    private void buildNode(FileNodex fileNode) {
        File file = fileNode.getFile();
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File childFile : files) {
                if (childFile.isDirectory()) {
                    FileNodex childNode = new FileNodex(childFile);
                    childNode.setType(0);
                    childNode.setParent(fileNode);

                    buildNode(childNode);

                    if (null != childNode.getChildren() && childNode.getChildren().size() > 0 && null != childNode.getParent()) {
                        childNode.getParent().addChild(childNode);
                    }
                } else {
                    if (childFile.getName().endsWith(".mp4")) {
                        FileNodex childNode = new FileNodex(childFile);
                        childNode.setType(1);
                        childNode.setParent(fileNode);
                        childNode.getParent().addChild(childNode);
                    }
                }
            }
        }
    }

    /**
     * 返回所有的视频文件
     * @return
     */
    public ArrayList<FileNodex> getAllFiles() {

        if (null == rootNode) {
            return null;
        }

        ArrayList<FileNodex> fileNodeList = new ArrayList<>();

        return getAllFiles(rootNode, fileNodeList);

    }

    private ArrayList<FileNodex> getAllFiles(FileNodex rootNode, ArrayList<FileNodex> list) {

        ArrayList<FileNodex> children = (ArrayList<FileNodex>) rootNode.getChildren();

        if (null != children && children.size() > 0) {
            for (FileNodex fileNode : children) {
                if (fileNode.getType() == 1) {
                    list.add(fileNode);
                } else {
                    getAllFiles(fileNode, list);
                }
            }
        }
        return list;
    }

    /**
     * 得到所有树中的节点
     * @return
     */
    public ArrayList<FileNodex> getAllFolders() {
        if (null == rootNode) {
            return null;
        }

        ArrayList<FileNodex> folderNodeList = new ArrayList<>();

        return getAllFolders(rootNode, folderNodeList);
    }

    private ArrayList<FileNodex> getAllFolders(FileNodex rootNode, ArrayList<FileNodex> folderNodeList) {

        ArrayList<FileNodex> children = (ArrayList<FileNodex>) rootNode.getChildren();

        if (null != children && children.size() > 0) {
            for (FileNodex fileNode : children) {
                if (fileNode.getType() == 0) {
                    folderNodeList.add(fileNode);
                    getAllFolders(fileNode, folderNodeList);
                }
            }
        }
        return folderNodeList;
    }

    /**
     * 得到纯视频目录，剔除空目录
     * @return
     */
    public ArrayList<FileNodex> getAllPureFolders() {
        if (null == rootNode) {
            return null;
        }

        ArrayList<FileNodex> folderNodeList = new ArrayList<>();

        return getAllPureFolders(rootNode, folderNodeList);
    }

    private ArrayList<FileNodex> getAllPureFolders(FileNodex rootNode, ArrayList<FileNodex> folderNodeList) {

        ArrayList<FileNodex> children = (ArrayList<FileNodex>) rootNode.getChildren();

        if (null != children && children.size() > 0) {
            for (FileNodex fileNode : children) {
                if (fileNode.getType() == 0) {
                    if (hasVideo(fileNode)) {
                        folderNodeList.add(fileNode);
                    }
                    getAllPureFolders(fileNode, folderNodeList);
                }
            }
        }
        return folderNodeList;
    }

    private boolean hasVideo(FileNodex fileNode) {
        ArrayList<FileNodex> children = (ArrayList<FileNodex>) fileNode.getChildren();
        for (FileNodex item : children) {
            if (item.getType() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到每个目录下面的视频文件信息
     * @param fileNodex
     * @return
     */
    public ArrayList<FileNodex> getFiles(FileNodex fileNodex) {
        ArrayList<FileNodex> children = (ArrayList<FileNodex>) fileNodex.getChildren();
        if (null != children && children.size() > 0) {
            ArrayList<FileNodex> result = new ArrayList<>();
            for (FileNodex item : children) {
                if (item.getType() == 1) {
                    result.add(item);
                }
            }
            return result;
        }
        return null;
    }
}
