import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface LanguageModel {
    public HashMap<String, Double> getRepresentationOf(String document);

    public List<HashMap<String, Double>> represent(List<String> documents);

    public Set<String> getVocabulary();
}
