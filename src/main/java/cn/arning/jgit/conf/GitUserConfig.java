package cn.arning.jgit.conf;

/**
 * @author arning
 */
public class GitUserConfig {

    /**
     * 是否添加认证 如果不是 使用本地默认配置
     */
    public static boolean IS_AUTHENTICATION = false;

    public static String GIT_USERNAME = System.getenv("GIT_USERNAME");

    public static String GIT_PASSWORD = System.getenv("GIT_PASSWORD");


}
