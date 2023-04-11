import java.util.List;

public interface SearchEngine {

    List<String> searchFor(String term);

    void store(List<String> documents);

}
