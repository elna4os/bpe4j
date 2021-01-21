import java.util.Objects;

public class Pair<T, K> {
    private final T key;
    private final K value;

    public Pair(T key, K value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return key.equals(pair.key) && value.equals(pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    public T getKey() {
        return key;
    }

    public K getValue() {
        return value;
    }
}
