package ir.shariaty.onlinetranslatorapp.model;

public class Word {
    private String id;
    private String title;
    private String title_en;
    private String source;
    private String db;
    private String text;

    public Word(String id, String title, String title_en, String source, String db, String text) {
        this.id = id;
        this.title = title;
        this.title_en = title_en;
        this.source = source;
        this.db = db;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public String getSource() {
        return source;
    }

    public String getDb() {
        return db;
    }

    public String getText() {
        return text;
    }
}
