import java.util.*;

public class TFIDF implements LanguageModel {

    Set<String> vocabulary;
    HashMap<String, Double> idfCounts;

    public TFIDF(){
        idfCounts = new HashMap<String, Double>();
        vocabulary = idfCounts.keySet();
    }

    @Override
    public HashMap<String, Double> getRepresentationOf(String Document) {
        return null;
    }


    @Override
    public List<HashMap<String, Double>> represent(List<String> documents){

        ArrayList<HashMap<String, Double>> documentRepresentations = new ArrayList<>();

        for (String doc : documents) {

            //The representation of this document
            HashMap<String, Double> docRepresentation = new HashMap<String, Double>();

            String[] splitDoc = this.tokenize(doc);

            double inverseNumberOfTokensInDocument = 1/ (double) splitDoc.length;
            for (String word : splitDoc) {

                if (docRepresentation.containsKey(word)) {
                    Double oldValue = docRepresentation.get(word);
                    Double newValue = Double.sum(inverseNumberOfTokensInDocument, oldValue);
                    docRepresentation.put(word, newValue);
                }
                else {
                    docRepresentation.put(word, inverseNumberOfTokensInDocument);

                    // We only need to update the idf-counts when the first time a word appears in doc
                    if (idfCounts.containsKey(word)){
                        Double oldValue = idfCounts.get(word);
                        Double newIdfValue = Double.sum(1, oldValue);
                        idfCounts.put(word, newIdfValue);
                    }
                    else {
                        idfCounts.put(word, 1.0);
                    }
                }
            }
            documentRepresentations.add(docRepresentation);
        }

        return calculateTFIDFfromCounts(documentRepresentations, idfCounts, (double)documents.size());
    }

    @Override
    public Set<String> getVocabulary() {

        // Return a copy of the set to not mess with the idf-map
        return new HashSet<>(this.vocabulary);
    }

    private ArrayList<HashMap<String, Double>> calculateTFIDFfromCounts(ArrayList<HashMap<String, Double>> documents,
                                                                        HashMap<String, Double> idfCounts, Double numberOfDocuments){
        for (HashMap<String, Double> doc : documents) {
            doc.forEach((word, tf)-> doc.put(word, tf*(Math.log(numberOfDocuments/idfCounts.get(word)))));
        }
        return documents;
    }

    private String[] tokenize(String document){

        document = document.toLowerCase();
        document = document.replaceAll("[\\.\\?,]", " ");

        // Remove all whitespace in beg and end to not get empty tokens in the first and last tokens
       // document = document.strip();

        return document.split("\\s+");
    }

}
