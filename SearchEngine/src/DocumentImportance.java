class DocumentImportance implements Comparable<DocumentImportance> {
    public Document document;
    public Double importance;

    public DocumentImportance(Document document, Double importance){
        this.document = document;
        this.importance = importance;
    }

    @Override
    public int compareTo(DocumentImportance other) {
        return this.importance.compareTo(other.importance);
    }
}