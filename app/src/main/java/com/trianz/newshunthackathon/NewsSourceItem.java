package com.trianz.newshunthackathon;

/**
 * Created by niveditha.kabbur on 30-12-2016.
 */
public class NewsSourceItem {

    private String source_id;
    private String source_name;
    private String source_logo;
    private String source_url;

    public void setSourceId(String source_id)
    {
        this.source_id = source_id;
    }

    public String getSourceId()
    {
        return source_id;
    }

    public void setSourceName(String source_name)
    {
        this.source_name = source_name;
    }

    public String getSourceName()
    {
        return source_name;
    }

    public void setSourceLogo(String source_logo)
    {
        this.source_logo = source_logo;
    }

    public String getSourceLogo()
    {
        return source_logo;
    }

    public void setSourceUrl(String source_url)
    {
        this.source_url = source_url;
    }

    public String getSourceUrl()
    {
        return source_url;
    }
}
