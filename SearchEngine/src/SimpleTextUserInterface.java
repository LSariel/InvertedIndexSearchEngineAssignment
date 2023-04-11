import java.util.List;
import java.util.Scanner;

public class SimpleTextUserInterface implements UserInterface{
    Scanner scn;
    boolean quit;
    public SimpleTextUserInterface(){
        scn = new Scanner(System.in);
        quit = false;
    }
    @Override
    public String getUserQuery() {

        System.out.println("Please enter what you want to search for.");

        // Use nextLine() instead of next() in case we later want to do full sentence searches
        return scn.nextLine();
    }

    @Override
    public boolean userWantsToQuitProgram() {
        return this.quit;
    }

    @Override
    public void presentSearchResults(List<Document> searchResults) {
        System.out.println("These are the documents that match your search term. Presented" +
                " in decending order of importance.");
        for (Document doc : searchResults){
            System.out.println(doc.text);
        }
    }

    public void askUserIfTheyWantToQuit(){
        System.out.println("If you are done using the program, please press 'Q'. Press any other key to search again.");
        if(scn.hasNextLine()) {
            String result = this.scn.nextLine();
            if (result.equals("Q")){
                this.quit = true;
            }
        }
    }
}
