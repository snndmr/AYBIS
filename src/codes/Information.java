package codes;

public class Information {

    private final String text;
    private final String link;

    public Information(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

}
