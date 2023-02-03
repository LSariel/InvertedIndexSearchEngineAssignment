import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Create a corpus to build the search engine on
        ArrayList<String> corpus = new ArrayList<>();
        corpus.add("The cats really like cheese, I mean, cats love cheese.");
        corpus.add("Why would a cat like cheese instead of chocolate?");
        corpus.add("Dogs love all sorts of foods when they get the chance.");

        //corpus.add("the brown fox jumped over the brown dog");
        //corpus.add("the lazy brown dog sat in the corner");
        //corpus.add("the red fox bit the lazy dog");

        // Create the models
        LanguageModel tfidfLanguageModel = new TFIDF();
        SearchEngine searchEngine = new InvertedIndexSearchEngine(corpus, tfidfLanguageModel);
        SimpleTextUserInterface ui = new SimpleTextUserInterface();


        // The actual search loop
        do {
            String searchTerm = ui.getUserQuery();

            try {
                List<String> result = searchEngine.searchFor(searchTerm);
                ui.presentSearchResults(result);
            }

            catch (IllegalArgumentException e) {
                System.out.println("Your search term isn't present in any document. Try another word.");
            }

            ui.askUserIfTheyWantToQuit();

        }while((!ui.userWantsToQuitProgram()));

    }

}