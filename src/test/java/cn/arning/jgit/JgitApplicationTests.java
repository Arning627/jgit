package cn.arning.jgit;

import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.utils.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

class JgitApplicationTests {


    String path = "/Users/arning/Desktop/tmp/gitTest/.git";

    @Test
    void contextLoads() {
        File file = new File("/Users/arning/develop/code/my");
        List<File> localGitRepository = FileUtil.findLocalGitRepository(file, new ArrayList<File>());
        for (File file1 : localGitRepository) {
            String absolutePath = file1.getAbsolutePath();
            System.out.println(absolutePath);
        }

        String property = System.getProperty("java.home");
        System.out.println(property);
    }

    @Test
    void test1() throws IOException, GitAPIException {
        File file = new File(path);
        Git git = Git.open(file);
        String branch = git.getRepository().getBranch();
        System.out.println(branch);
    }

    @Test
    void test2() throws Exception {
        File file = new File(path);
        Git open = Git.open(file);
        List<DiffEntry> call = open.diff().call();
        for (DiffEntry diffEntry : call) {
            String newPath = diffEntry.getNewPath();
            if (newPath.startsWith(".")) {
                continue;
            }
            DirCache add = open.add().addFilepattern(newPath).call();
        }
        open.commit().setMessage("测试提交").call();
        open.push().setRemote("origin").setCredentialsProvider(GitAuthentication.authentication()).call();
    }

    @Test
    void testStash() throws IOException, GitAPIException {
        File file = new File(path);
        Git git = Git.open(file);
        git.stashCreate().setWorkingDirectoryMessage("").call();

        Collection<RevCommit> call = git.stashList().call();
        for (RevCommit revCommit : call) {
            String s = revCommit.toString();
            System.out.println(s);
        }
    }

    @Test
    void deleteTag() throws IOException, GitAPIException {
        File file = new File(path);
        Git git = Git.open(file);
        List<String> call = git.tagDelete().setTags("1.1.1").call();

        git.push().setPushTags().setCredentialsProvider(GitAuthentication.authentication()).call();
    }

    @Test
    void cloneTest() throws GitAPIException {
        List<String> cloneUrl = FileUtil.findCloneUrl(new File("/Users/arning/cloneUrl.txt"));
        for (int i = 0; i < 5; i++) {
            String s = cloneUrl.get(i);
            File file = new File("/Users/arning/Desktop/tmp/cloneTes" + s.substring(s.lastIndexOf("/")));
            if (!file.exists()) {
                file.mkdir();
                System.out.println("创建完成" + file.getName());
                Git git = Git.cloneRepository().setURI(s).setDirectory(file).setCredentialsProvider(null).call();
            }
        }

    }




}
