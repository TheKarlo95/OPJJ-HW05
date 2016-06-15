package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@code SimpleHashtable} class implements a hash table, which maps keys to
 * values. Any non-null object can be used as a key or as a value but you can
 * also store null values. To successfully store and retrieve objects from a
 * hashtable, the objects used as keys must implement the {@code hashCode}
 * method and the {@code equals} method.
 * 
 * 
 * @author Karlo Vrbić
 * @version 1.0
 *
 * @param <K>
 *            describes type of key
 * @param <V>
 *            describes type of value
 */
public class SimpleHashtable<K, V>
        implements
        Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Default capacity of this hashtable. Used if capacity isn't specified by
     * user.
     */
    private static final int DEAFULT_CAPACITY = 16;

    /**
     * Default load factor of this hashtable. Used if capacity isn't load factor
     * by user.
     */
    private static final float DEAFULT_LOAD_FACTOR = 0.75f;

    /**
     * Number of stored pairs of keys and values
     */
    private int size;

    /**
     * The load factor of the hashtable.
     */
    private final float loadFactor;

    /**
     * Array containing all pairs of keys and values
     */
    private TableEntry<K, V>[] table;

    /**
     * Counts number of modification done on this hashtable
     */
    private int modificationCount;

    /**
     * Constructs a new, empty {@code SimpleHashtable} object with the specified
     * initial capacity.
     * 
     * @param capacity
     *            initial capacity of this hashtable
     * @param loadFactor
     *            the load factor of the hashtable
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity, float loadFactor) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }

        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal Load: " + loadFactor);
        }

        if (!isPowerOfTwo(capacity)) {
            capacity = (int) Math.pow(2, Math.ceil(Math.sqrt(capacity)));
        }

        this.table = (TableEntry<K, V>[]) new TableEntry[capacity];

        this.loadFactor = loadFactor;
    }

    /**
     * Constructs a new, empty {@code SimpleHashtable} object with the specified
     * initial capacity and default load factor of {@value #DEAFULT_LOAD_FACTOR}
     * .
     * 
     * @param capacity
     *            initial capacity of this hashtable
     */
    public SimpleHashtable(int capacity) {
        this(capacity, DEAFULT_LOAD_FACTOR);
    }

    /**
     * Constructs a new, empty {@code SimpleHashtable} object with a default
     * initial capacity of {@value #DEAFULT_CAPACITY} and default load factor of
     * {@value #DEAFULT_LOAD_FACTOR}.
     */
    public SimpleHashtable() {
        this(DEAFULT_CAPACITY);
    }

    /**
     * Maps the specified {@code key} to the specified {@code value} in this
     * hashtable. The key cannot be {@code null} but value can.
     * 
     * @param key
     *            the hashtable key
     * @param value
     *            the value
     */

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null reference!");
        }

        int hashcode = hashcode(key);
        TableEntry<K, V> current = table[hashcode];

        if (current == null) {
            current = new TableEntry<K, V>(key, value);
            table[hashcode] = current;
        } else {
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }

                current = current.next;
            }

            if (current.key.equals(key)) {
                current.value = value;
                return;
            }

            current.next = new TableEntry<K, V>(key, value);
        }

        modificationCount++;
        size++;

        if (loadFactor < ((float) size / table.length)) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null}
     * if this map contains no mapping for the key or the value mapped by key is
     * equals to {@code null}.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key or the value mapped
     *         by key is equals to {@code null}.
     */
    public V get(Object key) {
        if (key == null) {
            return null;
        }

        TableEntry<K, V> current;

        current = this.table[hashcode(key)];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Returns the number of keys in this hashtable.
     * 
     * @return the number of keys in this hashtable.
     */
    public int size() {
        return this.size;
    }

    /**
     * Tests if the specified object is a key in this hashtable.
     * 
     * @param key
     *            possible key
     * @return {@code true} if and only if the specified object is a key in this
     *         hashtable, as determined by the equals method; {@code false}
     *         otherwise.
     */
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }

        TableEntry<K, V> current = this.table[hashcode(key)];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Tests if the specified value is a value stored in this hashtable.
     * 
     * @param value
     *            possible value
     * @return {@code true} if and only if the specified object is a value in
     *         this hashtable, as determined by the equals method; {@code false}
     *         otherwise.
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            if (entry != null
                    && (entry.value != null && entry.value.equals(value)
                            || (entry.value == null && value == null))) {
                return true;
            }
        }

        return false;

    }

    /**
     * Removes the key (and its corresponding value) from this hashtable. This
     * method does nothing if the key is not in the hashtable.
     * 
     * @param key
     *            the key that needs to be removed
     */
    public void remove(Object key) {
        if (key == null) {
            return;
        }

        int hashcode = hashcode(key);

        TableEntry<K, V> before = null;
        TableEntry<K, V> current = this.table[hashcode];

        if (current == null) {
            return;
        }

        do {
            if (current.key.equals(key)) {
                if (before == null) {
                    table[hashcode] = current.next;

                    modificationCount++;
                    size--;
                    break;
                } else {
                    before.next = current.next;
                    current.next = null;

                    modificationCount++;
                    size--;
                    break;
                }
            }

            before = current;
            current = current.next;
        } while (current != null);
    }

    /**
     * Tests if this hashtable maps no keys to values.
     * 
     * @return {@code true} if this hashtable maps no keys to values;
     *         {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears this {@code SimpleHashtable} so that it contains no entries.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = (TableEntry<K, V>[]) new TableEntry[table.length];

        modificationCount++;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                builder.append(entry);
                builder.append(", ");

                entry = entry.next;
            }
        }
        if (builder.length() >= 2) {
            builder.delete(builder.length() - 2, builder.length());
        }

        builder.append("]");

        return builder.toString();
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new Iterator<TableEntry<K, V>>() {

            /** Index of the current entry. */
            private int index = 0;

            /** Array of all entries. */
            private TableEntry<K, V>[] entries = SimpleHashtable.this.toArray();

            private int modificationCount = SimpleHashtable.this.modificationCount;

            @Override
            public boolean hasNext() {
                return SimpleHashtable.this.size > index;
            }

            @Override
            public TableEntry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException(
                            "Iteration has no more elements!");
                }

                if (modificationCount != SimpleHashtable.this.modificationCount) {
                    throw new ConcurrentModificationException(
                            "You're not allowed to change hashtable without"
                                    + " using this iterator");
                }

                return entries[index++];
            }

            @Override
            public void remove() {
                if (entries[index - 1] == null) {
                    throw new IllegalStateException(
                            "The next method has not"
                                    + " yet been called, or the remove method"
                                    + " has already been called after the last"
                                    + " call to the next method");
                }

                if (modificationCount != SimpleHashtable.this.modificationCount) {
                    throw new ConcurrentModificationException(
                            "You're not allowed to change hashtable without"
                                    + " using this iterator");
                }

                SimpleHashtable.this.remove(entries[index - 1].key);
                entries[index - 1] = null;
                modificationCount++;
            }
        };
    }

    /**
     * Tests if specified number is power of two.
     * 
     * @param num
     *            number to be tested
     * @return {@code true} if this number is power of two; {@code false}
     *         otherwise.
     */
    private static boolean isPowerOfTwo(int num) {
        return (num & (num - 1)) == 0;
    }

    /**
     * Returns hashcode of specified key.
     * 
     * @param key
     *            key to be hashed
     * @return hashcode of specified key
     */
    private int hashcode(Object key) {
        return Math.abs(key.hashCode()) % this.table.length;
    }

    /**
     * Doubles the hashtable size and copies previous elements to new array.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        TableEntry<K, V>[] tmpTable = this.table.clone();

        table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];

        for (TableEntry<K, V> entry : tmpTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                size--; // put increase size so we have decrement to right size

                entry = entry.next;
            }
        }
    }

    /**
     * Converts this {@code SimpleHashtable} to a {@code TableEntry} array.
     * 
     * @return {@code TableEntry} array
     */
    private TableEntry<K, V>[] toArray() {
        @SuppressWarnings("unchecked")
        TableEntry<K, V>[] output = (TableEntry<K, V>[]) new TableEntry[table.length];
        int i = 0;
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                output[i++] = entry;

                entry = entry.next;
            }
        }

        return Arrays.copyOf(output, i);
    }

    /**
     * {@code TableEntry} class represents a pair of key and value. This class
     * is used in {@code SimpleHashtable} to store keys and their values.
     * 
     * @author Karlo Vrbić
     * @version 1.0
     * 
     * @param <K>
     *            describes type of key
     * @param <V>
     *            describes type of value
     */
    public static class TableEntry<K, V> {

        /**
         * The hashtable key
         */
        private K key;

        /**
         * The value
         */
        private V value;

        /**
         * The next hashtable entry
         */
        private TableEntry<K, V> next;

        /**
         * Constructs a new {@code TableEntry} object with specified key, value
         * and next entry.
         * 
         * @param key
         *            the hashtable key
         * @param value
         *            the value
         * @param next
         *            the next hashtable entry
         */
        public TableEntry(K key, V value, TableEntry<K, V> next) {
            if (key == null) {
                throw new IllegalArgumentException(
                        "Key cannot be null reference!");
            }

            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Constructs a new {@code TableEntry} object with specified key and
         * value.
         * 
         * @param key
         *            the hashtable key
         * @param value
         *            the value
         */
        public TableEntry(K key, V value) {
            this(key, value, null);
        }

        /**
         * Returns value.
         * 
         * @return value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets value.
         * 
         * @param value
         *            new value
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Returns key.
         * 
         * @return key
         */
        public K getKey() {
            return key;
        }

        /**
         * {@inheritDoc}
         * 
         * @return {@inheritDoc}
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
