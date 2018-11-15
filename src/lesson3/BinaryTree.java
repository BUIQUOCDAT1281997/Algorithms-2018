package lesson3;

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

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
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

    private int sizeSubSet(T from, T toEnd) {
        count = 0;
        sizeSubSet(from, toEnd, root);
        return count;
    }

    private int count;

    private void sizeSubSet(T from, T toEnd, Node<T> node) {
        if (node != null) {
            sizeSubSet(from, toEnd, node.left);
            if (from == null) {
                if (node.value.compareTo(toEnd) < 0) count++;
            } else if (toEnd == null) {
                if (node.value.compareTo(from) >= 0) count++;
            } else {
                if (node.value.compareTo(from) > 0 && node.value.compareTo(toEnd) < 0) count++;
            }
            sizeSubSet(from, toEnd, node.right);
        }
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
    // трудоёмкост : O(log(n)) n- количество узлов дерева
    // ресурсоёмкост : O(1)

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

        private int location = 0;

        private List<Node<T>> list;

        private BinaryTreeIterator() {
            list = new ArrayList<>();
            addToList(root);
        }

        private void addToList(Node<T> node) {
            if (node != null) {
                addToList(node.left);
                list.add(node);
                addToList(node.right);
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */

        private Node<T> findNext() {
            return list.get(location++);
        }
        // трудоёмкост : O(1)
        // ресурсоёмкост : O(1)

        @Override
        public boolean hasNext() {
            return location < list.size();
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
            BinaryTree.this.remove(list.get(location - 1).value);
            list.remove(list.get(location - 1));
            location--;
        }
        // трудоёмкост : O(log(n)) n- количество узлов дерева
        // ресурсоёмкост : O(1)
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
        return new ImpSortedSet<>(this,
                false, fromElement,
                false, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new ImpSortedSet<>(this,
                true, null, false,
                toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new ImpSortedSet<>(this, false, fromElement, true, null);
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

    static final class ImpSortedSet<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

        final BinaryTree<T> m;
        final T lo, hi;
        final boolean fromStart, toEnd;

        ImpSortedSet(BinaryTree<T> m,
                    boolean fromStart, T lo,
                    boolean toEnd, T hi) {
            this.m = m;
            this.fromStart = fromStart;
            this.lo = lo;
            this.toEnd = toEnd;
            this.hi = hi;
        }

        final boolean inRange(Object o) {
            @SuppressWarnings("unchecked")
            T t = (T) o;
            if (lo != null && hi != null) {
                return t.compareTo(lo) > 0 && t.compareTo(hi) < 0;
            } else if (lo == null) {
                return t.compareTo(hi) < 0;
            } else return t.compareTo(lo) >= 0;
        }

        @Override
        public boolean add(T t) {
            return inRange(t) && m.add(t);
        }

        @Override
        public boolean remove(Object o) {
            return inRange(o) && m.remove(o);
        }

        @Override
        public boolean contains(Object o) {
            return inRange(o) && m.contains(o);
        }

        @Override
        public int size() {
            return m.sizeSubSet(lo, hi);
        }

        @NotNull
        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            if (fromElement.compareTo(toElement) > 0) throw new NoSuchElementException();
            return new ImpSortedSet<>(m, false, fromElement,
                    false, toElement);
        }

        @NotNull
        @Override
        public SortedSet<T> headSet(T toElement) {
            return new ImpSortedSet<>(m, fromStart, lo, false, toElement);
        }

        @NotNull
        @Override
        public SortedSet<T> tailSet(T fromElement) {
            return new ImpSortedSet<>(m, false, fromElement, toEnd, hi);
        }

        @Override
        public T first() {
            if (size() == 0) throw new NoSuchElementException();
            if (lo == null) {
                return m.first();
            } else if (toEnd) {
                return lo;
            } else {
                Iterator<T> bIterator = m.iterator();
                T current = null;
                while (bIterator.hasNext()) {
                    current = bIterator.next();
                    if (current.compareTo(lo) == 0) {
                        current = bIterator.next();
                        break;
                    }
                }
                return current;
            }
        }

        @Override
        public T last() {
            if (size() == 0) throw new NoSuchElementException();
            if (hi == null) {
                return m.last();
            } else {
                Iterator<T> bIterator = m.iterator();
                T current;
                T sCurrent = null;
                while (bIterator.hasNext()) {
                    current = bIterator.next();
                    if (current.compareTo(hi) == 0) {
                        break;
                    }
                    sCurrent = current;
                }
                return sCurrent;
            }
        }

        // Я не успел написать эти методы
        @Override
        public Iterator<T> iterator() {
            return null;
        }

        @Override
        public boolean checkInvariant() {
            return false;
        }

        @Nullable
        @Override
        public Comparator<? super T> comparator() {
            return null;
        }
    }
}
