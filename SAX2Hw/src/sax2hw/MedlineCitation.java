package sax2hw;


public class MedlineCitation
{
    private String day;
    private String month;
    private String year;
    private String articleTitle;

    public MedlineCitation()
    {
    }

    public String getArticleTitle()
    {
        return articleTitle;
    }

    public String getDay()
    {
        return day;
    }

    public String getMonth()
    {
        return month;
    }

    public String getYear()
    {
        return year;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public void setArticleTitle(String articleTitle)
    {
        this.articleTitle = articleTitle;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

}
