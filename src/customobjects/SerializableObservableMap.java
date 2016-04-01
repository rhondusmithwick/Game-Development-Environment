package customobjects;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SerializableObservableMap<K, V> implements Serializable {

    private final Map<K, V> map = new HashMap<>();

    private transient final ObservableMap<K, V> observableMap = FXCollections.observableMap(map);

    public V get(K key) {
        return observableMap.get(key);
    }

    public V put(K key, V value) {
        return observableMap.put(key, value);
    }

    public V remove(K key) {
        return observableMap.remove(key);
    }

    public void clear() {
        observableMap.clear();
    }

    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        observableMap.addListener(listener);
    }

    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        observableMap.removeListener(listener);
    }

    public Set<Entry<K, V>> entrySet() {
        return observableMap.entrySet();
    }

    public Set<K> keySet() {
        return observableMap.keySet();
    }

    public Collection<V> values() {
        return observableMap.values();
    }

    public boolean containsKey(K key) {
        return observableMap.containsKey(key);
    }

    public boolean containsValue(V value) {
        return observableMap.containsValue(value);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
