package cn.arning.gittools.command;


import cn.arning.gittools.utils.Result;

import java.util.concurrent.Future;

/**
 * @author arning
 */
public interface Clone {


    void clone(String url, String rootPath);
}
