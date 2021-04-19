package cn.arning.jgit.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @author arning
 */
public class ExcelData extends BaseRowModel {

    @ExcelProperty
    private String projectName;

    private String url;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "ExcelData{" +
                "projectName='" + projectName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

