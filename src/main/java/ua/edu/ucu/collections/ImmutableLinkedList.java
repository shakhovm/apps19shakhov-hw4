package ua.edu.ucu.collections;

import java.util.Arrays;


public final class ImmutableLinkedList implements ImmutableList {

    private Node head;

    private Node tail;

    private int size = 0;

    public ImmutableLinkedList(Object[] data) {
        size = data.length;
        Node[] nodes = createArrayNodes(data, size);
        if (nodes != null) {
            head = nodes[0];
            tail = nodes[1];
        }
        else {
            head = new Node(null, null, null);
            tail = new Node(null, null, head);
            head.setNext(tail);
        }
    }

    public ImmutableLinkedList(Node head, Node tail, int size)
    {
        this.head = head;
        this.tail = tail;
        this.size = size;
    }


    public ImmutableLinkedList() {
        head = new Node(null, null, null);
        tail = new Node(null, null, head);
        head.setNext(tail);
    }

    public static void indexErrorChecker(int index, int size)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException();
        }
    }

    public static Node findNode(int index, Node node)
    {
        Node newNode = node;
        for (int i = 0; i < index; i++) {
            newNode = newNode.getNext();
        }
        return newNode;
    }

    private static Node[] createArrayNodes(Object[] c, int newSize)
    {
        if (newSize == 0) {
            return null;
        }
        Node newHead = new Node(c[0], null, null);
        Node newTail = new Node(null, null, newHead);
        newHead.setNext(newTail);
        for (int i = 1; i < newSize; i++) {
            Node newNode = new Node(c[i], newTail, newTail.getPrev());
            newTail.setPrev(newNode);
            newTail.getPrev().getPrev().setNext(newNode);
        }
        return new Node[]{newHead, newTail};
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }


    @Override
    public ImmutableLinkedList add(int index, Object e) {

        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {

        indexErrorChecker(index, size);
        int newSize = c.length;
        Node[] arrayNodes = createArrayNodes(c, newSize);
        if (isEmpty())
        {
            return arrayNodes != null
                    ? new ImmutableLinkedList(
                            arrayNodes[0], arrayNodes[1],
                    newSize)
                    : new ImmutableLinkedList();
        }
        Node newHeadNode = new Node();
        Node newNode = newHeadNode;

        Node newHead = head;
        int sizeIter = index == size ? size + 1 : size;
        for (int i = 0; i < sizeIter; i++) {
            newNode.setData(newHead.getData());
            newNode.setNext(new Node(null, null, newNode));
            if (i == index && arrayNodes != null) {
                if (newNode.getPrev() == null)
                {
                    newHeadNode = arrayNodes[0];
                }
                else {
                    newNode.getPrev().setNext(arrayNodes[0]);
                }

                arrayNodes[0].setPrev(newNode.getPrev());
                newNode.setPrev(arrayNodes[1].getPrev());
                arrayNodes[1].getPrev().setNext(newNode);
            }
            newHead = newHead.getNext();
            newNode = newNode.getNext();
        }

        return new ImmutableLinkedList(newHeadNode, newNode, size + newSize);
    }

    @Override
    public Object get(int index) {

        indexErrorChecker(index, size - 1);

        return findNode(index, head).getData();
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        indexErrorChecker(index, size - 1);

        Node newHeadNode = new Node();
        Node newNode = newHeadNode;

        Node newHead = head;
        for (int i = 0; i < size; i++) {
            newNode.setData(newHead.getData());
            newNode.setNext(new Node(null, null, newNode));

            if (i == 0 && index == 0) {
                newHeadNode = newHeadNode.getNext();
                newHeadNode.setPrev(null);
            }
            else if (i == index) {
                newNode.getPrev().setNext(newNode.getNext());
                newNode.getNext().setPrev(newNode.getPrev());
            }
            newHead = newHead.getNext();
            newNode = newNode.getNext();
        }

        return new ImmutableLinkedList(newHeadNode, newNode, size - 1);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        indexErrorChecker(index, size - 1);

        Node newHeadNode = new Node();
        Node newNode = newHeadNode;

        Node newHead = head;
        for (int i = 0; i < size; i++) {
            newNode.setData(newHead.getData());
            newNode.setNext(new Node(null, null, newNode));
            if (i == index) {
                newNode.setData(e);
            }
            newHead = newHead.getNext();
            newNode = newNode.getNext();
        }

        return new ImmutableLinkedList(newHeadNode, newNode, size);
    }

    @Override
    public int indexOf(Object e) {
        Node newHead = head;
        for (int i = 0; i < size; i++) {
            System.out.println(newHead.getData() == null);
            if (newHead.getData() != null && newHead.getData().equals(e))
            {

                return i;
            }
            newHead = newHead.getNext();
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public ImmutableLinkedList addFirst(Object e)
    {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e)
    {
        return add(e);
    }

    public Object getFirst()
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException();
        }
        return head.getData();
    }

    public Object getLast()
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException();
        }
        return tail.getPrev().getData();
    }

    public ImmutableLinkedList removeFirst()
    {
        return remove(0);
    }

    public ImmutableLinkedList removeLast()
    {
        return remove(size - 1);
    }

    @Override
    public Object[] toArray() {
        Node newHead = head;
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = newHead.getData();
            newHead = newHead.getNext();
        }
        return array;
    }


    @Override
    public String toString()
    {
        return Arrays.toString(toArray());
    }
}
