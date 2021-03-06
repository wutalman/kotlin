package kotlin

//
// NOTE THIS FILE IS AUTO-GENERATED by the GenerateStandardLib.kt
// See: https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib
//

import java.util.*

/**
 * Returns an ArrayList of all elements
 */
public fun <T> Array<out T>.toArrayList(): ArrayList<T> {
    val list = ArrayList<T>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun BooleanArray.toArrayList(): ArrayList<Boolean> {
    val list = ArrayList<Boolean>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun ByteArray.toArrayList(): ArrayList<Byte> {
    val list = ArrayList<Byte>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun CharArray.toArrayList(): ArrayList<Char> {
    val list = ArrayList<Char>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun DoubleArray.toArrayList(): ArrayList<Double> {
    val list = ArrayList<Double>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun FloatArray.toArrayList(): ArrayList<Float> {
    val list = ArrayList<Float>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun IntArray.toArrayList(): ArrayList<Int> {
    val list = ArrayList<Int>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun LongArray.toArrayList(): ArrayList<Long> {
    val list = ArrayList<Long>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun ShortArray.toArrayList(): ArrayList<Short> {
    val list = ArrayList<Short>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns an ArrayList of all elements
 */
public fun <T> Iterable<T>.toArrayList(): ArrayList<T> {
    return toCollection(ArrayList<T>())
}

/**
 * Returns an ArrayList of all elements
 */
public fun <T> Stream<T>.toArrayList(): ArrayList<T> {
    return toCollection(ArrayList<T>())
}

/**
 * Returns an ArrayList of all elements
 */
public fun String.toArrayList(): ArrayList<Char> {
    return toCollection(ArrayList<Char>())
}

/**
 * Appends all elements to the given *collection*
 */
public fun <T, C : MutableCollection<in T>> Array<out T>.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Boolean>> BooleanArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Byte>> ByteArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Char>> CharArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Double>> DoubleArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Float>> FloatArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Int>> IntArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Long>> LongArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Short>> ShortArray.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <T, C : MutableCollection<in T>> Iterable<T>.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <T, C : MutableCollection<in T>> Stream<T>.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Appends all elements to the given *collection*
 */
public fun <C : MutableCollection<in Char>> String.toCollection(collection: C): C {
    for (item in this) {
        collection.add(item)
    }
    return collection
}

/**
 * Returns a HashSet of all elements
 */
public fun <T> Array<out T>.toHashSet(): HashSet<T> {
    return toCollection(HashSet<T>())
}

/**
 * Returns a HashSet of all elements
 */
public fun BooleanArray.toHashSet(): HashSet<Boolean> {
    return toCollection(HashSet<Boolean>())
}

/**
 * Returns a HashSet of all elements
 */
public fun ByteArray.toHashSet(): HashSet<Byte> {
    return toCollection(HashSet<Byte>())
}

/**
 * Returns a HashSet of all elements
 */
public fun CharArray.toHashSet(): HashSet<Char> {
    return toCollection(HashSet<Char>())
}

/**
 * Returns a HashSet of all elements
 */
public fun DoubleArray.toHashSet(): HashSet<Double> {
    return toCollection(HashSet<Double>())
}

/**
 * Returns a HashSet of all elements
 */
public fun FloatArray.toHashSet(): HashSet<Float> {
    return toCollection(HashSet<Float>())
}

/**
 * Returns a HashSet of all elements
 */
public fun IntArray.toHashSet(): HashSet<Int> {
    return toCollection(HashSet<Int>())
}

/**
 * Returns a HashSet of all elements
 */
public fun LongArray.toHashSet(): HashSet<Long> {
    return toCollection(HashSet<Long>())
}

/**
 * Returns a HashSet of all elements
 */
public fun ShortArray.toHashSet(): HashSet<Short> {
    return toCollection(HashSet<Short>())
}

/**
 * Returns a HashSet of all elements
 */
public fun <T> Iterable<T>.toHashSet(): HashSet<T> {
    return toCollection(HashSet<T>())
}

/**
 * Returns a HashSet of all elements
 */
public fun <T> Stream<T>.toHashSet(): HashSet<T> {
    return toCollection(HashSet<T>())
}

/**
 * Returns a HashSet of all elements
 */
public fun String.toHashSet(): HashSet<Char> {
    return toCollection(HashSet<Char>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun <T> Array<out T>.toLinkedList(): LinkedList<T> {
    return toCollection(LinkedList<T>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun BooleanArray.toLinkedList(): LinkedList<Boolean> {
    return toCollection(LinkedList<Boolean>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun ByteArray.toLinkedList(): LinkedList<Byte> {
    return toCollection(LinkedList<Byte>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun CharArray.toLinkedList(): LinkedList<Char> {
    return toCollection(LinkedList<Char>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun DoubleArray.toLinkedList(): LinkedList<Double> {
    return toCollection(LinkedList<Double>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun FloatArray.toLinkedList(): LinkedList<Float> {
    return toCollection(LinkedList<Float>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun IntArray.toLinkedList(): LinkedList<Int> {
    return toCollection(LinkedList<Int>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun LongArray.toLinkedList(): LinkedList<Long> {
    return toCollection(LinkedList<Long>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun ShortArray.toLinkedList(): LinkedList<Short> {
    return toCollection(LinkedList<Short>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun <T> Iterable<T>.toLinkedList(): LinkedList<T> {
    return toCollection(LinkedList<T>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun <T> Stream<T>.toLinkedList(): LinkedList<T> {
    return toCollection(LinkedList<T>())
}

/**
 * Returns a LinkedList containing all elements
 */
public fun String.toLinkedList(): LinkedList<Char> {
    return toCollection(LinkedList<Char>())
}

/**
 * Returns a List containing all key-value pairs
 */
public fun <K, V> Map<K, V>.toList(): List<Map.Entry<K, V>> {
    val result = ArrayList<Map.Entry<K, V>>(size)
    for (item in this)
        result.add(item)
    return result
}

/**
 * Returns a List containing all elements
 */
public fun <T> Array<out T>.toList(): List<T> {
    return toCollection(ArrayList<T>())
}

/**
 * Returns a List containing all elements
 */
public fun BooleanArray.toList(): List<Boolean> {
    val list = ArrayList<Boolean>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun ByteArray.toList(): List<Byte> {
    val list = ArrayList<Byte>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun CharArray.toList(): List<Char> {
    val list = ArrayList<Char>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun DoubleArray.toList(): List<Double> {
    val list = ArrayList<Double>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun FloatArray.toList(): List<Float> {
    val list = ArrayList<Float>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun IntArray.toList(): List<Int> {
    val list = ArrayList<Int>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun LongArray.toList(): List<Long> {
    val list = ArrayList<Long>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun ShortArray.toList(): List<Short> {
    val list = ArrayList<Short>(size)
    for (item in this) list.add(item)
    return list
}

/**
 * Returns a List containing all elements
 */
public fun <T> Iterable<T>.toList(): List<T> {
    return toCollection(ArrayList<T>())
}

/**
 * Returns a List containing all elements
 */
public fun <T> Stream<T>.toList(): List<T> {
    return toCollection(ArrayList<T>())
}

/**
 * Returns a List containing all elements
 */
public fun String.toList(): List<Char> {
    return toCollection(ArrayList<Char>())
}

/**
 * Returns a Set of all elements
 */
public fun <T> Array<out T>.toSet(): Set<T> {
    return toCollection(LinkedHashSet<T>())
}

/**
 * Returns a Set of all elements
 */
public fun BooleanArray.toSet(): Set<Boolean> {
    return toCollection(LinkedHashSet<Boolean>())
}

/**
 * Returns a Set of all elements
 */
public fun ByteArray.toSet(): Set<Byte> {
    return toCollection(LinkedHashSet<Byte>())
}

/**
 * Returns a Set of all elements
 */
public fun CharArray.toSet(): Set<Char> {
    return toCollection(LinkedHashSet<Char>())
}

/**
 * Returns a Set of all elements
 */
public fun DoubleArray.toSet(): Set<Double> {
    return toCollection(LinkedHashSet<Double>())
}

/**
 * Returns a Set of all elements
 */
public fun FloatArray.toSet(): Set<Float> {
    return toCollection(LinkedHashSet<Float>())
}

/**
 * Returns a Set of all elements
 */
public fun IntArray.toSet(): Set<Int> {
    return toCollection(LinkedHashSet<Int>())
}

/**
 * Returns a Set of all elements
 */
public fun LongArray.toSet(): Set<Long> {
    return toCollection(LinkedHashSet<Long>())
}

/**
 * Returns a Set of all elements
 */
public fun ShortArray.toSet(): Set<Short> {
    return toCollection(LinkedHashSet<Short>())
}

/**
 * Returns a Set of all elements
 */
public fun <T> Iterable<T>.toSet(): Set<T> {
    return toCollection(LinkedHashSet<T>())
}

/**
 * Returns a Set of all elements
 */
public fun <T> Stream<T>.toSet(): Set<T> {
    return toCollection(LinkedHashSet<T>())
}

/**
 * Returns a Set of all elements
 */
public fun String.toSet(): Set<Char> {
    return toCollection(LinkedHashSet<Char>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun <T> Array<out T>.toSortedSet(): SortedSet<T> {
    return toCollection(TreeSet<T>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun BooleanArray.toSortedSet(): SortedSet<Boolean> {
    return toCollection(TreeSet<Boolean>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun ByteArray.toSortedSet(): SortedSet<Byte> {
    return toCollection(TreeSet<Byte>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun CharArray.toSortedSet(): SortedSet<Char> {
    return toCollection(TreeSet<Char>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun DoubleArray.toSortedSet(): SortedSet<Double> {
    return toCollection(TreeSet<Double>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun FloatArray.toSortedSet(): SortedSet<Float> {
    return toCollection(TreeSet<Float>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun IntArray.toSortedSet(): SortedSet<Int> {
    return toCollection(TreeSet<Int>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun LongArray.toSortedSet(): SortedSet<Long> {
    return toCollection(TreeSet<Long>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun ShortArray.toSortedSet(): SortedSet<Short> {
    return toCollection(TreeSet<Short>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun <T> Iterable<T>.toSortedSet(): SortedSet<T> {
    return toCollection(TreeSet<T>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun <T> Stream<T>.toSortedSet(): SortedSet<T> {
    return toCollection(TreeSet<T>())
}

/**
 * Returns a SortedSet of all elements
 */
public fun String.toSortedSet(): SortedSet<Char> {
    return toCollection(TreeSet<Char>())
}

