package org.localstorm.mcc.web.gtd.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.notes.Note;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.web.gtd.actions.wrap.RoSearchResult;

/**
 *
 * @author localstorm
 */
public class RefObjectCollector extends HitCollector {

    private RoSearchResult sr;
    private IndexSearcher  searcher;
    private String         idField;
    private String         typeField;
    private Map<Integer, Note> noteMap;
    private Map<Integer, FileAttachment> fileMap;
    
    public RefObjectCollector(IndexSearcher is,
                              String idField,
                              String typeField,
                              Collection<ReferencedObject> ros,
                              Collection<Note> notes,
                              Collection<FileAttachment> files) {
        
        this.sr = new RoSearchResult();
        this.searcher = is;
        this.idField = idField;
        this.typeField = typeField;

        this.noteMap = new HashMap<Integer, Note>();
        this.fileMap = new HashMap<Integer, FileAttachment>();

        for (Note note: notes) {
            this.noteMap.put(note.getId(), note);
        }

        for (FileAttachment file: files) {
            this.fileMap.put(file.getId(), file);
        }

    }

    @Override
    public void collect(int docId, float relevance) {
        try {

            Document doc = searcher.doc(docId);
            Field idf    = doc.getField(this.idField);
            Field tf     = doc.getField(this.typeField);
            
            int id       = Integer.parseInt(idf.stringValue());
            DocType type = DocType.valueOf(tf.stringValue());

            Note note    = null;
            switch (type) {
                case TEXT:
                    note = this.noteMap.get(id);
                    this.sr.addTextualNote(note, relevance);
                    break;
                case URL:
                    note = this.noteMap.get(id);
                    this.sr.addUrlNote(note, relevance);
                    break;
                case FILE:
                    FileAttachment file = this.fileMap.get(id);
                    this.sr.addFileResult(file, relevance);
                    break;
                default:
                    throw new RuntimeException("Unexpected case!");
            }

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RoSearchResult getSearchResult() {
        this.sr.close();
        return this.sr;
    }

    private static enum DocType
    {
        FILE,
        TEXT,
        URL
    }

}
