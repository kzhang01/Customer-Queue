import java.util.NoSuchElementException;

public class Queue<E>{
	
	public Node first, last;
	public Node position, previous;
	private int total = -1;
	
	public class Node{
		E data;
		Node prev, next;
	}
	
	public Queue() {
		this.first = null;
		this.last = null;
	}
	
	public void enqueue(E data){
		Node current = new Node();
		current.data = data;
		/*
		 * Checks empty case:
		 * if the queue is empty, the node is first and last
		 */
		if(++total == 0) {
			first = current;
			last = current;
			position = current;
			return;
		}
		/*
		 * Non-empty case:
		 * change reference of last to current
		 * and current becomes last
		 */
		current.prev = last;
		last.next = current;
		last = current;
	
	}
	
	public E dequeue() {
		if(total == 0)
			throw new NoSuchElementException();
		E data = first.data;
		first = first.next;
		if(--total == 0)
			last = null;
		return data;
	}
	
	public E removeFirst()
	{
		if(first == null)
			throw new NoSuchElementException();
		E ele = first.data;
		first = first.next;
		return ele;
	}
	
	public void addFirst(E element)
	{
		Node newNode = new Node();
		newNode.data = element;
		newNode.next = first;
		first = newNode;
	}
	
	/*
	 * Methods for iterator
	 */
	
	public E next(){
		if(hasNext() == false)
			throw new NoSuchElementException();
		previous = position;
		
		if(position == null)
			position = first;
		else
			position = position.next;
		
		return position.data;
	}
	
	public E prev() {
		if(hasPrev() == false)
			throw new NoSuchElementException();
		previous = position.prev.prev;
		
		if(position == null)
			position = first;
		else
			position = position.prev;
		
		return position.data;
	}
	
	public boolean hasNext(){
		if(position == null)
			return first != null;
		return position.next != null;
		
	}
	
	public boolean hasPrev() {
		if(position == null)
			return false;
		return position.prev != null;
	}
}
