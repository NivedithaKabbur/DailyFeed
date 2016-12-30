package com.trianz.newshunthackathon;

/**
 * Created by niveditha.kabbur on 22-12-2016.
 */
public class NewsDetails {

    private String title;
    private String author;  // author
    private String publishedAt; // publishedAt
    private String urlToImage;  // urlToImage
    private String description;  // description
    private String url;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt()
    {
        return publishedAt;
    }

    public void setUrlToImage(String urlToImage)
    {
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage()
    {
        return urlToImage;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

}
