public class Document {
    String text;
    Integer documentIndex;

    public Document(String text, Integer documentIndex) {
        this.text = text;
        this.documentIndex = documentIndex;
    }

    public String getText() {
        return text;
    }

    public Integer getDocumentIndex() {
        return documentIndex;
    }
}
