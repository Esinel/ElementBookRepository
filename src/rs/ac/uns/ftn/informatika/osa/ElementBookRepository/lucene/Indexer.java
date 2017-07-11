package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene;

import org.apache.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.util.SystemOutLogger;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.handlers.DocumentHandler;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.handlers.*;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;
import sun.rmi.runtime.Log;


import java.io.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;


public class Indexer {

	static Logger log = Logger.getLogger(Indexer.class.getName());

	private final Version v = Version.LUCENE_40;
	private File indexDirPath;
	private IndexWriter indexWriter;
	private Directory indexDir;
	Ebook ebook;
	
	private static Indexer indexer = new Indexer(true);
	
	public static Indexer getInstance(){
		return indexer;
	}
	
	/**
	 * @param path - kanonicka adresa direktorijuma u koji ce biti smesteni indeksi
	 * @param restart - <b>true</b> za kreiranje novog indeksa, <b>false</b> za nastavak koriscenja vec postojecih indeksa
	 */
	private Indexer(String path, boolean restart) {
		IndexWriterConfig iwc = new IndexWriterConfig(v, new SerbianAnalyzer());
		if(restart){
			iwc.setOpenMode(OpenMode.CREATE);
		}else{
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		try{
			this.indexDir = new SimpleFSDirectory(new File(path));
			this.indexWriter = new IndexWriter(this.indexDir, iwc);
		}catch(IOException ioe){
			throw new IllegalArgumentException("Path not correct");
		}catch(ExceptionInInitializerError e){
			log.error("FUCK ME", e);
			try {
				System.setOut(new PrintStream(new FileOutputStream("/home/alligator/Desktop/debugStack/debugStack.log")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * @param restart - <b>true</b> za kreiranje novog indeksa, <b>false</b> za nastavak koriscenja vec postojecih indeksa
	 * <p>
	 * Direktorijum u kojem ce se indeks nalaziti se ucitava iz <i>app.properties</i> datoteke
	 */
	private Indexer(boolean restart){
		this(ResourceBundle.getBundle("app").getString("index"), restart);
	}
	
	private Indexer(){
		this(false);
	}
	
	public IndexWriter getIndexWriter(){
		return this.indexWriter;
	}
	
	public Directory getIndexDir(){
		return indexDir;
	}
	
	public File getIndexDirPath(){
		return indexDirPath;
	}
	
	/**
	 * Od dobijenih vrednosti se konstruise Term po kojem se vrsi pretraga dokumenata
	 * Dokumenti koji zadovoljavaju uslove pretrage ce biti obrisani
	 * 
	 * @param fieldName naziv polja
	 * @param value vrednost polja
	 * @return
	 */
	public boolean delete(String filename){
		Term delTerm = new Term("filename", filename);
		try {
			synchronized (this) {
				this.indexWriter.deleteDocuments(delTerm);
				this.indexWriter.commit();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean add(Document doc){
		try {
			synchronized (this) {
				this.indexWriter.addDocument(doc);
				this.indexWriter.commit();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}
	
	public boolean updateDocument(String filename, List<IndexableField> fields){
		try {
			DirectoryReader reader = DirectoryReader.open(this.indexDir);
			IndexSearcher is = new IndexSearcher(reader);
			Query query = new TermQuery(new Term("filename", filename));
			TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
			is.search(query, collector);
			
			ScoreDoc[] scoreDocs = collector.topDocs().scoreDocs;
			if(scoreDocs.length > 0){
				int docID = scoreDocs[0].doc;
				Document doc = is.doc(docID);
				if(doc != null){
					for(IndexableField field : fields){
						doc.removeFields(field.name());
					}
					for(IndexableField field : fields){
						doc.add(field);
					}
					try{
						synchronized (this) {
							this.indexWriter.updateDocument(new Term("filename", filename), doc);
							this.indexWriter.commit();
							return true;
						}
					}catch(IOException e){
					}
				}
			}
			
			return false;
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Indeksni direktorijum nije u redu");
		} 
	}
	
	/**
	 * 
	 * @param file Direktorijum u kojem se nalaze dokumenti koje treba indeksirati
	 */
	public void index(File file, Ebook ebook){
		DocumentHandler handler = null;
		String fileName = null;
		try {
			File[] files;
			if(file.isDirectory()){
				files = file.listFiles();
			}else{
				files = new File[1];
				files[0] = file;
			}
			for(File newFile : files){
				if(newFile.isFile()){
					fileName = newFile.getName();
					handler = getHandler(fileName);
					if(handler == null){
						System.out.println("Nije moguce indeksirati dokument sa nazivom: " + fileName);
						continue;
					}
					Document indexedDoc = handler.getDocument(newFile);

					//title
					indexedDoc.add(new TextField("title", ebook.getTitle(), Field.Store.YES));
					//author
					indexedDoc.add(new TextField("author", ebook.getAuthor(), Field.Store.YES));
					// keywords
					indexedDoc.add(new TextField("keywords", ebook.getKeywords(), Field.Store.YES));
					// year
					indexedDoc.add(new TextField("publicationYear", Integer.toString(ebook.getPublicationYear()), Field.Store.YES));
					//language
					indexedDoc.add(new TextField("language", ebook.getLanguage().getName(), Field.Store.YES));

					//category
					indexedDoc.add(new TextField("category", String.valueOf(ebook.getCategory().getId()), Field.Store.YES));

					this.indexWriter.addDocument(indexedDoc);
				}
			}
			this.indexWriter.commit();
			System.out.println("indexing done");
		} catch (IOException e) {
			System.out.println("indexing NOT done");
		}
	}
	
	protected void finalize() throws Throwable {
		this.indexWriter.close();
	}
	
	protected DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}else{
			return null;
		}
	}

}