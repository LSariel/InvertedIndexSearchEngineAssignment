import java.util.List;
public interface UserInterface {
    String getUserQuery();
    boolean userWantsToQuitProgram();

    void presentSearchResults(List<Document> searchResults);

}
