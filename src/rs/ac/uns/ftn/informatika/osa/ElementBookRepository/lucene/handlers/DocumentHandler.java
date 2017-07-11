package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.handlers;

import org.apache.lucene.document.Document;

import java.io.File;

public abstract class DocumentHandler {
	/**
	 * Od prosledjene datoteke se konstruise Lucene Document
	 * 
	 * @param file
	 *            datoteka u kojoj se nalaze informacije
	 * @return Lucene Document
	 */
	public abstract Document getDocument(File file);
	public abstract String getText(File file);

}
