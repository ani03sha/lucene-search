package org.anirudh.redquark.lucenesearch.document;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

/**
 * This class is used to write lucene documents to index
 * 
 * @author anirshar
 *
 */
public class LuceneWriteIndex {

	/* Setting the file where indices will be created */
	private static final String INDEX_DIR = "C:\\dev\\tools\\lucene\\index";

	public static void main(String[] args) {

		try {
			/**
			 * An IndexWriter creates and maintains an index. The third argument to the
			 * constructor determines whether a new index is created, or whether an existing
			 * index is opened for the addition of new documents. 
			 */
			IndexWriter writer = createWriter();

			List<Document> documents = new ArrayList<>();

			/**
			 * Documents are the unit of indexing and search. A Document is a set of fields.
			 * Each field has a name and a textual value.
			 */
			Document document1 = createDocument(1, "Anirudh", "Sharma", "org.anirudh.redquark");
			documents.add(document1);

			Document document2 = createDocument(1, "Bruce", "Wayne", "org.anirudh.redquark");
			documents.add(document2);

			/* Cleaning everything here */
			writer.deleteAll();

			/* Adding documents to the IndexWriter */
			writer.addDocuments(documents);

			/* Committing the writer */
			writer.commit();

			/* Closing the writer */
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Provides functionality to create and manage index. It’s constructor takes two
	 * arguments: FSDirectory and IndexWriterConfig. Please note that after the
	 * writer is created, the given configuration instance cannot be passed to
	 * another writer.
	 * 
	 * @return IndexWriter
	 */
	private static IndexWriter createWriter() {
		try {
			FSDirectory directory = FSDirectory.open(Paths.get(INDEX_DIR));
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			IndexWriter writer = new IndexWriter(directory, config);
			return writer;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method defines the Lucene indexed document
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param website
	 * @return Document
	 */
	private static Document createDocument(Integer id, String firstName, String lastName, String website) {

		Document document = new Document();
		document.add(new StringField("id", id.toString(), Field.Store.YES));
		document.add(new TextField("firstName", firstName, Field.Store.YES));
		document.add(new TextField("lastName", lastName, Field.Store.YES));
		document.add(new TextField("website", website, Field.Store.YES));
		return document;
	}
}
