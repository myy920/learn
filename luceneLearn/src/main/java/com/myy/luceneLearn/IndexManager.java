package com.myy.luceneLearn;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IndexManager {
    public static final String INDEX_DIR = "F:\\code\\JavaGuide\\luceneIndex";

    public static void main(String[] args) throws Exception {
        // new IndexManager().load();
        new IndexManager().query();
    }

    public void load() throws IOException {
        WikiDao wikiDao = new WikiDao();
        List<Wiki> wikiList = wikiDao.findMDWiki();
        List<Document> documentList = wikiList.stream().map(wiki -> {
            Document document = new Document();
            document.add(new TextField("id", wiki.getId(), Field.Store.YES));
            document.add(new TextField("title", wiki.getTitle(), Field.Store.YES));
            document.add(new TextField("content", wiki.getContent(), Field.Store.YES));
            return document;
        }).collect(Collectors.toList());

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        try (IndexWriter indexWriter = new IndexWriter(directory, config)) {
            for (Document document : documentList) {
                indexWriter.addDocument(document);
            }
        }
    }

    public void query() throws IOException, ParseException {
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Analyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("content", analyzer);

        Query query = queryParser.parse("Java");

        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("Total hits: " + topDocs.totalHits);
        if (Objects.isNull(topDocs.scoreDocs)) {
            return;
        }
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            Wiki wiki = new Wiki();
            wiki.setId(doc.get("id"));
            wiki.setTitle(doc.get("title"));
            wiki.setContent(doc.get("content"));
            System.out.println(wiki);
        }
        System.out.println(1);
    }

}
