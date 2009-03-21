package sax2hw;

public class MedlineCitationCollector
{
    public void onArticle(MedlineCitation article)
    {
        System.err.println("Article: {");
        System.err.println("\tTitle : "+article.getArticleTitle());
        System.err.println("\t  Year: "+article.getYear());
        System.err.println("\t Month: "+article.getMonth());
        System.err.println("\t   Day: "+article.getDay());
        System.err.println("}");

    }
}
