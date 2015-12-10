package com.checkdoc.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "DOCUMENT")
@Table(name = "DOCUMENT")
public class Document {

    @Id
    @GeneratedValue(generator = "identity")
    @GenericGenerator(name = "identity", strategy = "identity")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "URL")
    private String url;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DIRECTORY_ID")
    private Directory directory;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private Set<Mistake> mistakes = new HashSet<>();

    public Document() {
    }

    public Document(String url, Date date) {

        this.url = url;
        this.date = date;
    }

    public Document(String url, Date date, Directory directory) {
        this.url = url;
        this.date = date;
        this.directory = directory;
    }

    public Document(String url, Date date, Directory directory, Set<Mistake> mistakes) {
        this.url = url;
        this.date = date;
        this.directory = directory;
        this.mistakes = mistakes;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return url.substring(url.lastIndexOf(System.getProperty("file.separator")) + 1, url.length());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Mistake> getMistakes() {
        return mistakes;
    }

    public void setMistakes(Set<Mistake> mistakes) {
        this.mistakes = mistakes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (getId() != document.getId()) return false;
        if (!getUrl().equals(document.getUrl())) return false;
        if (!getDate().equals(document.getDate())) return false;
        return getDirectory().equals(document.getDirectory());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getUrl().hashCode();
        result = 31 * result + getDate().hashCode();
//        result = 31 * result + getDirectory().hashCode();
        return result;
    }

    public Directory getDirectory() {

        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "Document{" +
                "url='" + url + '\'' +
                ", date=" + date +
                ", directory=" + directory +
                '}';
    }
}
