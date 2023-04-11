import java.util.*;

public class InvertedIndexSearchEngine implements SearchEngine{
    LanguageModel languageModel;

    // Here we store the original documents and their id is the indices they get assigned
    ArrayList<String> rawDocuments;

    // Contains the words and the documents they occur in
    HashMap<String,SortedSet<DocumentImportance>> invertedIndexDB;

    public InvertedIndexSearchEngine(List<String> documents, LanguageModel languageModel){

        // Create the models and storage
        this.languageModel = languageModel;
        rawDocuments = new ArrayList<String>();
        invertedIndexDB = new HashMap<String,SortedSet<DocumentImportance>>();

        store(documents);
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
                    DocumentImportance docimp = new DocumentImportance(doc_index, importance);

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
    public List<String> searchFor(String term) {
        if(invertedIndexDB.containsKey(term)){
            ArrayList<String> rawDocumentsToReturn = new ArrayList<>();
            SortedSet<DocumentImportance> docsContainingTerm = invertedIndexDB.get(term);

            Iterator<DocumentImportance> it = docsContainingTerm.iterator();
            it.forEachRemaining(document -> rawDocumentsToReturn.add(rawDocuments.get(document.documentIndex)));

            return rawDocumentsToReturn;
        }
        else{
            throw new IllegalArgumentException("Sorry, the term you searched for is not present in any document");
        }
    }

    @Override
    public void store(List<String> documents) {
        this.rawDocuments.addAll(documents);
        generateDataBaseFrom(documents);
    }


}
