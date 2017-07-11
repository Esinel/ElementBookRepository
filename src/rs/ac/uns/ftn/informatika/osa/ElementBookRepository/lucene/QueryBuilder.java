package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.model.SearchType;

/**
 * Created by alligator on 10.2.17..
 */
public class QueryBuilder {


    private static Version version = Version.LUCENE_40;
    private static SerbianAnalyzer analyzer = new SerbianAnalyzer();
    private static int maxEdits = 1;

    public static int getMaxEdits(){
        return maxEdits;
    }

    public static void setMaxEdits(int maxEdits){
        QueryBuilder.maxEdits = maxEdits;
    }

    public static Query buildQuery(SearchType.Type queryType, String field, String value) throws IllegalArgumentException, ParseException {
        QueryParser parser = new QueryParser(version, field, analyzer);
        String errorMessage = "";
        if (field == null || field.equals("")) {
            errorMessage += "Field not specified";
        }
        if (value == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value not specified";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        Query query = null;
        if (queryType.equals(SearchType.Type.regular)) {
            Term term = new Term(field, value);
            query = new TermQuery(term);
        } else if (queryType.equals(SearchType.Type.fuzzy)) {
            Term term = new Term(field, value);
            query = new FuzzyQuery(term, maxEdits);
        } else {
            String[] values = value.split(" ");
            PhraseQuery pq = new PhraseQuery();
            for (String word : values) {
                Term term = new Term(field, word);
                pq.add(term);
            }
            query = pq;
        }

        return parser.parse(query.toString(field));
    }
}
