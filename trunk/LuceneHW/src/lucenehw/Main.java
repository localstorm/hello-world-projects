package lucenehw;

import java.util.Iterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.RAMDirectory;

/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        RAMDirectory ramd = new RAMDirectory();
        
        IndexWriter indexWriter = new IndexWriter(ramd, new StandardAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        Document doc = new Document();
        doc.add(new Field("description", "Ебать тебя в очко", Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(doc);
        indexWriter.close();

        Analyzer analyzer = new StandardAnalyzer();
        final IndexSearcher is = new IndexSearcher(ramd);
        QueryParser parser = new QueryParser("description", analyzer);
        Query query = parser.parse("ОчКо");
        
        HitCollector hc = new HitCollector() {

            @Override
            public void collect(int arg0, float arg1) {
                try{
                    System.out.println(is.doc(arg0));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        is.search(query, hc);

    }

}
