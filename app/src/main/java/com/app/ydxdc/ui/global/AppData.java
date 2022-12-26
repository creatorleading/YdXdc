package com.app.ydxdc.ui.global;


import com.google.common.collect.Lists;

import java.util.List;


public final class AppData {


    private boolean isEditTask;


    public boolean isEditTask() {
        return isEditTask;
    }

    public void setEditTask(boolean editTask) {
        isEditTask = editTask;
    }

    public List<String> getLayerManagerList() {
        return layerManagerList;
    }

    public void setLayerManagerList(List<String> layerManagerList) {
        this.layerManagerList = layerManagerList;
    }

    private List<String> layerManagerList = Lists.newArrayList();

}
