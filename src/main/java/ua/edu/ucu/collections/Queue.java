package ua.edu.ucu.collections;

public class Queue{
    private ImmutableLinkedList data;


    public Queue()
    {
        data = new ImmutableLinkedList();
    }

    public Object peek()
    {
        return data.getFirst();
    }

    public Object dequeue()
    {
        Object object = peek();
        data = data.removeFirst();
        return object;
    }

    public boolean isEmpty() {
        return data.size() == 0;
    }

    public void enqueue(Object e)
    {
        data = data.addLast(e);
    }

    public Object[] toArray() {
        return data.toArray();
    }


}
