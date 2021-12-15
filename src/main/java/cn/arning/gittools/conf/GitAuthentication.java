package cn.arning.gittools.conf;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * @author arning
 */
public class GitAuthentication extends UsernamePasswordCredentialsProvider {


    private GitAuthentication(String username, String password) {
        super(username, password);
    }

    private GitAuthentication(String username, char[] password) {
        super(username, password);
    }

    private GitAuthentication() {
        super(GitUserConfig.GIT_USERNAME, GitUserConfig.GIT_PASSWORD);
    }

    public static GitAuthentication instance;

    static {
        instance = new GitAuthentication();
    }

    public static GitAuthentication authentication() {
        return instance;
    }
}
