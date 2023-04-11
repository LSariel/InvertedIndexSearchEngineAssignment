import java.util.*;

public class InvertedIndexSearchEngine implements SearchEngine{
    LanguageModel languageModel;

    // Here we store the original documents using their ids as keys
    HashMap<Integer, Document> documents;

    // Contains the words and the documents they occur in
    HashMap<String,SortedSet<DocumentImportance>> invertedIndexDB;

    public InvertedIndexSearchEngine(List<String> rawDocuments, LanguageModel languageModel){

        // Create the models and storage
        this.languageModel = languageModel;

        // The documents are stored in a set ordered by document-id
        this.documents = new HashMap<Integer, Document>();
        this.invertedIndexDB = new HashMap<String,SortedSet<DocumentImportance>>();

        store(rawDocuments);
    }

    /* Generates the inverted index database from the raw string documents, using the provided language model.*/
    private void generateDataBaseFrom(List<String> documents){
        List<HashMap<String, Double>> representations = languageModel.represent(documents);
        Set<String> vocab = languageModel.getVocabulary();

        for(String word : vocab){
            for(int doc_index = 0; doc_index < representations.size(); doc_index++){
                HashMap<String, Double> document = representations.get(doc_index);

                if(document.containsKey(word)) {
                    Double importance = document.get(word);
                    Document doc = this.documents.get(doc_index);
                    DocumentImportance docimp = new DocumentImportance(doc, importance);

                    if(this.invertedIndexDB.containsKey(word)){
                       SortedSet<DocumentImportance> docsAndImportances = this.invertedIndexDB.get(word);
                        docsAndImportances.add(docimp);
                    }
                    else {
                        SortedSet<DocumentImportance> docsAndImportances = new TreeSet<DocumentImportance>().descendingSet();
                        docsAndImportances.add(docimp);
                        this.invertedIndexDB.put(word, docsAndImportances);
                    }

                }
            }
        }
    }

    @Override
    public List<Document> searchFor(String term) {
        if(invertedIndexDB.containsKey(term)){
            ArrayList<Document> documentsToReturn = new ArrayList<>();
            SortedSet<DocumentImportance> docsContainingTerm = invertedIndexDB.get(term);

            Iterator<DocumentImportance> it = docsContainingTerm.iterator();
            it.forEachRemaining(documentImportance -> documentsToReturn.add(documentImportance.document));

            return documentsToReturn;
        }
        else{
            throw new IllegalArgumentException("Sorry, the term you searched for is not present in any document");
        }
    }

    @Override
    public void store(List<String> rawDocuments) {
        for(int doc_index = 0; doc_index < rawDocuments.size(); doc_index++){

            // Create and store a new document using the document index and raw text
            Document document = new Document(rawDocuments.get(doc_index), doc_index);
            this.documents.put(document.getDocumentIndex(), document);
        }

        generateDataBaseFrom(rawDocuments);
    }


}
