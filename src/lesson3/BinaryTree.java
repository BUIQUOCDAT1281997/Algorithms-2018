package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    private Set<T> listNodes = new TreeSet<>();

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        listNodes.add(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }


    /**
     * Удаление элемента в дереве
     * Средняя
     */

    private boolean isRemoveIterator = false;

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        @SuppressWarnings("unchecked")
        T data = (T) o;
        Node<T> parent = root;
        Node<T> current = root;
        boolean isLeftChild = false;
        while (current.value != data) {
            parent = current;
            if (data.compareTo(current.value) > 0) {
                current = current.right;
                isLeftChild = false;
            } else {
                current = current.left;
                isLeftChild = true;
            }
        }
        if (!isRemoveIterator) listNodes.remove(data);
        if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else {
            Node<T> successor = current.right;
            Node<T> successorParent = current;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            if (successor != current.right) {
                successorParent.left = successor.right;
                successor.right = current.right;
            }
            successor.left = current.left;
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
        }
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;


        private BinaryTreeIterator() {
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */

        private Iterator<T> iterator = listNodes.iterator();

        private Node<T> findNext() {
            if (iterator.hasNext()) {
                return find(iterator.next());
            }
            return null;

        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            if (current == null) return;
            isRemoveIterator = true;
            BinaryTree.this.remove(current.value);
            isRemoveIterator = false;
            iterator.remove();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        SortedSet<T> result = new TreeSet<>();
        for (T element : listNodes) {
            if (element.compareTo(fromElement) > 0 && element.compareTo(toElement) < 0)
                result.add(element);
        }
        return result;
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> result = new TreeSet<>();
        for (T element : listNodes) {
            if (element.compareTo(toElement) < 0)
                result.add(element);
        }
        return result;
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> result = new TreeSet<>();
        for (T element : listNodes) {
            if (element.compareTo(fromElement) >= 0)
                result.add(element);
        }
        return result;
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
