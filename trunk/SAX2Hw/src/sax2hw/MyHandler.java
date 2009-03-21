package sax2hw;

import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class MyHandler  extends DefaultHandler
{
    private Stack<String>    tags;
    private MedlineCitation          article;
    private MedlineCitationCollector collector;

    public MyHandler(MedlineCitationCollector col)
    {
        this.tags       = new Stack<String>();
        this.collector  = col;
        this.article    = new MedlineCitation();
    }

    @Override
    public void startElement (String uri, String name, String qName, Attributes atts)
    {
        String tagName;
        if (uri.length()==0) {
            tagName = qName;
        } else {
            tagName = uri+":"+qName; // Namespaces support, just in case :)
        }

        tags.push(tagName);

        // Cleaning up from previous MedlineCitation
        if ("MedlineCitation".equals(this.getCurrentTag())) {
            this.article = new MedlineCitation();
        }

    }

    @Override
    public void endElement (String uri, String name, String qName)
    {
        if ("MedlineCitation".equals(this.getCurrentTag())) {
            this.collector.onArticle(this.article);
        }
        
        tags.pop();
    }

    @Override
    public void characters (char ch[], int start, int length)
    {
        if ("Day".equals(this.getCurrentTag()) && "DateCreated".equals(this.getParentTag())) {
            this.article.setDay(new String(ch, start, length));
            return;
        }

        if ("Month".equals(this.getCurrentTag()) && "DateCreated".equals(this.getParentTag())) {
            this.article.setMonth(new String(ch, start, length));
            return;
        }

        if ("Year".equals(this.getCurrentTag()) && "DateCreated".equals(this.getParentTag())) {
            this.article.setYear(new String(ch, start, length));
            return;
        }

        if ("ArticleTitle".equals(this.getCurrentTag())) {
            this.article.setArticleTitle(new String(ch, start, length));
            return;
        }
    }

    private String getCurrentTag()
    {
        if (!this.tags.isEmpty()) {
            return this.tags.peek();
        } else {
            return null;
        }
    }

    private String getParentTag()
    {
        if (this.tags.size()<2) {
            return null;
        } else {
            return this.tags.get(this.tags.size()-2);
        }
    }
}
