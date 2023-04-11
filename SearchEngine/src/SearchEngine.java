import java.util.List;

public interface SearchEngine {

    List<Document> searchFor(String term);

    void store(List<String> documents);

}
