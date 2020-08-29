package com.kingstar.bw.lucune;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
    IndexSearcher searcher;


    public Searcher(String indexDir) {
        //获取要查询的路径，也就是索引所在的位置
        Directory dir = null;
        try {
            dir = FSDirectory.open(Paths.get(indexDir));
            IndexReader reader = DirectoryReader.open(dir);

            //构建 IndexSearcher
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        searcher.getIndexReader().close();
    }

    public void search(String q) throws Exception {


        //标准分词器，会自动去掉空格啊，is a the 等单词
        Analyzer analyzer = new StandardAnalyzer();
        //查询解析器
        QueryParser parser = new QueryParser("contents", analyzer);
        //通过解析要查询的 String，获取查询对象，q 为传进来的待查的字符串
        Query query = parser.parse(q);

        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        //开始查询，查询前10条数据，将记录保存在 docs 中
        TopDocs docs = searcher.search(query, 10);

        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("匹配" + q + "共耗时" + (endTime - startTime) + "毫秒");
        System.out.println("查询到" + docs.totalHits + "条记录");

        //取出每条查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            //scoreDoc.doc相当于 docID，根据这个 docID 来获取文档
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println(doc.get("contents"));
            //fullPath 是刚刚建立索引的时候我们定义的一个字段，表示路径。也可以取其他的内容，只要我们在建立索引时有定义即可。
//            System.out.println(ml.get("fullPath"));
        }

    }

    public void multiSearch(String[] q) throws Exception {


        //标准分词器，会自动去掉空格啊，is a the 等单词
        Analyzer analyzer = new StandardAnalyzer();
        //通过解析要查询的 String，获取查询对象，q 为传进来的待查的字符串
        BooleanClause.Occur[] occ = new BooleanClause.Occur[q.length];
        String[] fields = new String[q.length];
        for (int i = 0; i < q.length; i++) {
            occ[i] = BooleanClause.Occur.MUST;
            fields[i] = "contents";
        }

        Query query = MultiFieldQueryParser.parse(q, fields, occ, new StandardAnalyzer());

        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        //开始查询，查询前10条数据，将记录保存在 docs 中
        TopDocs docs = searcher.search(query, 10);

        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("匹配" + q + "共耗时" + (endTime - startTime) + "毫秒");
        System.out.println("查询到" + docs.totalHits + "条记录");

        //取出每条查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            //scoreDoc.doc相当于 docID，根据这个 docID 来获取文档
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println(doc.get("contents"));
            //fullPath 是刚刚建立索引的时候我们定义的一个字段，表示路径。也可以取其他的内容，只要我们在建立索引时有定义即可。
//            System.out.println(ml.get("fullPath"));
        }

    }

    public static void main(String[] args) {
        String indexDir = "/home/meitao/test/lucene";
        //查询这个字符串
        String q = "Purushottam";
        Searcher searcher = new Searcher(indexDir);
        try {

            searcher.search(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}