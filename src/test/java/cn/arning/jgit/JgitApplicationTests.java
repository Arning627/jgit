package cn.arning.jgit;

import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.utils.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCache;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    void test2() throws Exception{
        File file = new File(path);
        Git open = Git.open(file);
        List<DiffEntry> call = open.diff().call();
        for (DiffEntry diffEntry : call) {
            String newPath = diffEntry.getNewPath();
            if (newPath.startsWith(".")){
                continue;
            }
            DirCache add = open.add().addFilepattern(newPath).call();
        }
        open.commit().setMessage("测试提交").call();
        open.push().setRemote("origin").setCredentialsProvider(GitAuthentication.authentication()).call();
    }


    @Test
    void test3(){
        System.out.println("\033[31;2mauth -u [用户名] -p [密码] \033[0m");
        System.out.println("\033[31;2mtag  -v [版本] -m [描述信息] -f [c 创建,d 删除]\033[0m");
        System.out.println("\033[31;2mpull -b [分支]\033[0m");
    }

    @Test
    void testStash() throws IOException, GitAPIException {
        File file = new File(path);
        Git git = Git.open(file);
        Status call = git.status().call();
        Set<String> modified = call.getModified();
        for (String s : modified) {
            System.out.println("modified=>>>>"+s);
        }
        Set<String> changed = call.getChanged();
        System.out.println(changed.size());
        for (String s : changed) {
            System.out.println("changed=>>>>"+s);
        }
        Set<String> uncommittedChanges = call.getUncommittedChanges();
        System.out.println(uncommittedChanges.size());
        Set<String> added = call.getAdded();
        for (String s : added) {
            System.out.println("added=>>>>"+s);
        }
    }

    @Test
    void deleteTag() throws IOException, GitAPIException {
        File file = new File(path);
        Git git = Git.open(file);
        List<String> call = git.tagDelete().setTags("1.1.1").call();

        git.push().setPushTags().setCredentialsProvider(GitAuthentication.authentication()).call();


    }


}
