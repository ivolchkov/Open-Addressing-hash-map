package ua.com.test.task;

import ua.com.test.task.exception.ElementNotFoundException;
import ua.com.test.task.exception.OutOfSpaceException;

public class CustomHashMap {
    private static final int MAXIMUM_CAPACITY = (1 << 31) - 1;
    private static final float LOAD_FACTOR = 0.4f;

    private int size;
    private int threshold;
    private Entry[] table;

    public CustomHashMap(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Size value can`t be less than zero!");
        }
        this.size = 0;
        this.table = new Entry[calculateTableSize(initialCapacity)];
        this.threshold = (int) (table.length * LOAD_FACTOR);
    }

    public void put(int x, long y) {
        validateSize();

        for (int i = index(hash(x)), j = 1; ; i += quadraticProbing(j), j++) {
            if (i >= table.length) {
                i = 0;
            }
            if (table[i] == null) {
                table[i] = new Entry(x, y);
                size += 1;
                return;
            }
            if (table[i].getKey() == x) {
                table[i].setValue(y);
                return;
            }
        }
    }

    public long get(int key) {
        int hash = hash(key);

        for (int i = index(hash), j = 1; ; i += quadraticProbing(j), j++) {
            if (i == table.length) {
                i = 0;
            }
            if (table[i] == null) {
                throw new ElementNotFoundException("Invalid key value!");
            }
            if (table[i].getKey() == key) {
                return table[i].getValue();
            }
        }
    }

    public int size() {
        return size;
    }

    private int calculateTableSize(int size) {
        int tableSize = size - 1;
        tableSize |= tableSize >>> 1;
        tableSize |= tableSize >>> 2;
        tableSize |= tableSize >>> 4;
        tableSize |= tableSize >>> 8;
        tableSize |= tableSize >>> 16;
        return tableSize < 0 ? 1 : (tableSize >= 1073741824 ? 1073741824 : tableSize + 1);
    }

    private void validateSize() {
        if (size > threshold) {
            int newCapacity;

            if (table.length == MAXIMUM_CAPACITY) {
                throw new OutOfSpaceException("There is no empty space in hash table!");
            }

            if (table.length >= (1 << 30)) {
                newCapacity = MAXIMUM_CAPACITY;
            } else {
                newCapacity = table.length * 2;
            }

            resize(newCapacity);
        }
    }

    private void resize(int capacity) {
        Entry[] oldTable = table;
        table = new Entry[capacity];
        threshold = (int) (LOAD_FACTOR * table.length);

        transfer(oldTable);
    }

    private void transfer(Entry[] oldTable) {
        for (Entry e : oldTable) {
            for (int i = index(hash(e.getKey())), j = 1; ; i += quadraticProbing(j), j++) {
                if (i >= table.length) {
                    i = 0;
                }
                if (table[i] == null) {
                    table[i] = new Entry(e.getKey(), e.getValue());
                    return;
                }
            }
        }
    }

    private int quadraticProbing(int index) {
        return (index * index + index) / 2;
    }

    private int hash(int x) {
        x = ((x >>> 16) ^ x) * 0x45d9f3b;
        x = ((x >>> 16) ^ x) * 0x45d9f3b;
        x = (x >>> 16) ^ x;

        return x;
    }

    private int index(int hash) {
        return hash & (table.length - 1);
    }


    private static class Entry {
        private final int key;
        private long value;

        public Entry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
    
}
