package com.zxb.libsdemo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrzhou on 16/5/29.
 */
public class FileNode {
    int type;
    File file;
    FileNode parent;
    List<FileNode> children;

    public void addChild(FileNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public FileNode() {

    }

    public FileNode(File file) {
        this.file = file;
    }

    public List<FileNode> getChildren() {
        return children;
    }

    public void setChildren(List<FileNode> children) {
        this.children = children;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileNode getParent() {
        return parent;
    }

    public void setParent(FileNode parent) {
        this.parent = parent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
