package model.component.base;

import api.IComponent;


public class Value<V> implements IComponent {
    private V value;

    public Value(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

}
