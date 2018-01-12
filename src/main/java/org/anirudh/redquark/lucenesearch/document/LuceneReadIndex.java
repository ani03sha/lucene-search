package org.anirudh.redquark.lucenesearch.document;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneReadIndex {

	/* Setting the file where indices are created */
	private static final String INDEX_DIR = "C:\\dev\\tools\\lucene\\index";

	public static void main(String[] args) {

		try {
			IndexSearcher searcher = createSearcher();

			/* Search by ID */
			TopDocs foundDocs1 = searchById(1, searcher);
			System.out.println("Total results: " + foundDocs1.totalHits);
			
			for(ScoreDoc doc : foundDocs1.scoreDocs) {
				Document d = searcher.doc(doc.doc);
				System.out.println(String.format(d.get("firstName")));
			}
			
			/* Search by first name */
			TopDocs foundDocs2 = searchByFirstName("Anirudh", searcher);
			
			System.out.println("Total results: " + foundDocs2.totalHits);
			
			for(ScoreDoc doc : foundDocs2.scoreDocs) {
				Document d = searcher.doc(doc.doc);
				System.out.println(String.format(d.get("id")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Searching the lucene documents
	 * 
	 * @return IndexSearcher
	 */
	private static IndexSearcher createSearcher() {
		try {
			Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
			IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			return searcher;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Searching by the first name
	 * 
	 * @param firstName
	 * @param searcher
	 * @return TopDocs
	 * @throws Exception
	 */
	private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception {

		QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
		Query firstNameQuery = qp.parse(firstName);
		TopDocs hits = searcher.search(firstNameQuery, 10);
		return hits;
	}

	/**
	 * Searching by id
	 * 
	 * @param id
	 * @param searcher
	 * @return TopDocs
	 * @throws Exception
	 */
	private static TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception {

		QueryParser qp = new QueryParser("id", new StandardAnalyzer());
		Query idQuery = qp.parse(id.toString());
		TopDocs hits = searcher.search(idQuery, 10);
		return hits;
	}

}
