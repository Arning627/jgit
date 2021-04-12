package cn.arning.jgit.command;

import java.io.File;

/**
 * @author arning
 */
public interface Clone {


    void clone(String url, File rootPath);
}
