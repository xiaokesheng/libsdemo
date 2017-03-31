package com.zxb.libsdemo.testfilenode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrzhou on 16/5/29.
 */
public class FileNodex implements Comparable<FileNodex> {

    private int type;
    private File file;
    FileNodex parent;
    List<FileNodex> children;

    public void addChild(FileNodex fileNode) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(fileNode);
    }

    public FileNodex(File file) {
        this.file = file;
    }


    public List<FileNodex> getChildren() {
        return children;
    }

    public void setChildren(List<FileNodex> children) {
        this.children = children;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileNodex getParent() {
        return parent;
    }

    public void setParent(FileNodex parent) {
        this.parent = parent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(FileNodex another) {
        long thisModify = this.file.lastModified();
        long nextModify = another.getFile().lastModified();

        if (thisModify > nextModify) {
            return 1;
        } else if (thisModify < nextModify) {
            return -1;
        }
        return 0;
    }
}
