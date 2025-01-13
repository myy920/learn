package com.myy.luceneLearn;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class WikiDao {
    public static final String ROOT_DIR = "F:\\code\\JavaGuide\\JavaGuide-main\\docs";


    public List<Wiki> findMDWiki() {
        List<Wiki> wikiList = new ArrayList<>();
        findWiki(new File(ROOT_DIR), wikiList);
        return wikiList.stream().filter(one -> StringUtils.endsWith(one.getTitle(), ".md")).collect(Collectors.toList());
    }

    private void findWiki(File file, List<Wiki> wikiList) {
        if (Objects.isNull(file) || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            try (FileInputStream in = new FileInputStream(file); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                IOUtils.copy(in, out);
                Wiki wiki = new Wiki();
                wiki.setId(UUID.randomUUID().toString());
                wiki.setTitle(file.getName());
                wiki.setContent(out.toString());
                wikiList.add(wiki);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File[] files = file.listFiles();
            if (Objects.nonNull(files)) {
                for (File one : files) {
                    findWiki(one, wikiList);
                }
            }
        }
    }

}
