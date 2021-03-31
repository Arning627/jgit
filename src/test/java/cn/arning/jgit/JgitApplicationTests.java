package cn.arning.jgit;

import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.utils.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.RemoteConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JgitApplicationTests {




    @Test
    void contextLoads() {
        File file = new File("/Users/arning/develop/code/my");
        List<File> localGitRepository = FileUtil.findLocalGitRepository(file,new ArrayList<File>());
        for (File file1 : localGitRepository) {
            String absolutePath = file1.getAbsolutePath();
            System.out.println(absolutePath);
        }

        String property = System.getProperty("java.home");
        System.out.println(property);
    }

    @Test
    void test1() throws IOException, GitAPIException {
        File file = new File("/Users/arning/Desktop/tmp/gitTest/.git");
        Git git  = Git.open(file);
        String branch = git.getRepository().getBranch();
        System.out.println(branch);

    }



}
