package hw7;

public class HashMapTest extends MapTest{
    @Override
    protected Map<String, String> createMap() {
        return new HashMap<>();
    }
}
