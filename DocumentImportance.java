class DocumentImportance implements Comparable<DocumentImportance> {
    public Integer documentIndex;
    public Double importance;

    public DocumentImportance(Integer documentIndex, Double importance){
        this.documentIndex = documentIndex;
        this.importance = importance;
    }

    @Override
    public int compareTo(DocumentImportance other) {
        return this.importance.compareTo(other.importance);
    }
}