package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.handlers.DocumentHandler;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.handlers.PDFHandler;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.model.RequiredHighlight;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ResultRetriever {
	
	private TopScoreDocCollector collector;
	private static int maxHits = 10;
	
	public ResultRetriever(){
		collector= TopScoreDocCollector.create(10,true);
	}
	
	public static void setMaxHits(int maxHits) {
		ResultRetriever.maxHits = maxHits;
	}

	public static int getMaxHits() {
		return ResultRetriever.maxHits;
	}

	public static List<Ebook> getResults(Query query,
										 List<RequiredHighlight> requiredHighlights, String subscriberGenre) {
		if (query == null) {
			return null;
		}
		try {
			Directory indexDir = new SimpleFSDirectory(new File(ResourceBundle
					.getBundle("app").getString("index")));
			DirectoryReader reader = DirectoryReader.open(indexDir);
			IndexSearcher is = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					maxHits, true);

			List<Ebook> results = new ArrayList<Ebook>();

			is.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			Ebook rd;
			Document doc;
			Highlighter hl;
			SerbianAnalyzer sa = new SerbianAnalyzer();
			
			for (ScoreDoc sd : hits) {
				doc = is.doc(sd.doc);
				String[] allKeywords = doc.getValues("keyword");
				String keywords = "";
				for (String keyword : allKeywords) {
					keywords += keyword.trim() + " ";
				}
				keywords = keywords.trim();
				String title = doc.get("title");
				String author = doc.get("author");
				String publicationYear = doc.get("publicationYear");
				String location = doc.get("filename");
				String categoryId = doc.get("category");
				String highlight = "";
				for (RequiredHighlight rh : requiredHighlights) {
					hl = new Highlighter(new QueryScorer(query, reader, rh.getFieldName()));
					try{
						highlight += hl.getBestFragment(sa, rh.getFieldName(),
								"" + getDocumentText(location));
					}catch (InvalidTokenOffsetsException e) {
						//throw new IllegalArgumentException("Unable to make highlight");
					}
				}
				rd = new Ebook();
				rd.setTitle(title);
				rd.setKeywords(keywords);
				if (publicationYear != null){
					rd.setPublicationYear(Integer.parseInt(publicationYear));
				}

				rd.setAuthor(author);
				rd.setFilename(location);

				if (subscriberGenre != null){
					if (subscriberGenre == categoryId){
						results.add(rd);
					}
				}else{
					results.add(rd);
				}


			}
			reader.close();
			return results;

		} catch (IOException e) {
			throw new IllegalArgumentException(
					"U prosledjenom direktorijumu ne postoje indeksi ili je direktorijum zakljucan");
		} 
	}

	
	private static String getDocumentText(String location){
		File file = new File(location);
		DocumentHandler handler = getHandler(location);
		return handler.getText(file);
	}
	
	protected static DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}else{
			return null;
		}
	}
}
