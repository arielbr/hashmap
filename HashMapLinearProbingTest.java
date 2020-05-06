package hw7;

public class HashMapLinearProbingTest extends MapTest {
    @Override
    protected Map<String, String> createMap() {
        return new HashMapLinearProbing<>();
    }
}
