import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class InterleaveIterator<E> implements Iterator<E> {

	private List<Iterator<E>> list;
	private int i;

	public InterleaveIterator() {
		initFields();
	}

	public InterleaveIterator(List<Iterator<E>> list) {
		initFields();
		for (Iterator<E> iterator : list) {
			addIterator(iterator);
		}
	}

	private void initFields() {
		list = new ArrayList<>();
		i = 0;
	}

	public void addIterator(Iterator<E> it) {
		list.add(it);
	}

	public void removeIterator(int index) {
		list.remove(index);

		if (i == index) {
			i++;
		}
		if (i == list.size()) {
			i = 0;
		}
	}

	@Override
	public boolean hasNext() {
		if (list == null || list.isEmpty()) {
			return false;
		}
		int j = i == list.size() ? 0 : i;
		boolean hasNext = hasNextInLoop(j);
		if (!hasNext) {
			j = 0;
			hasNext = hasNextInLoop(j);
		}
		return hasNext;
	}

	private boolean hasNextInLoop(int j) {
		while (j < list.size()) {
			Iterator<E> it = list.get(j);
			if (it.hasNext()) {
				return true;
			}
			j++;
		}
		return false;
	}

	@Override
	public E next() {
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (i == list.size()) {
			i = 0;
		}
		E e = getNextInLoop();
		if (e == null) {
			i = 0;
			e = getNextInLoop();
		}
		return e;
	}

	private E getNextInLoop() {
		while (i < list.size()) {
			Iterator<E> it = list.get(i);
			i++;
			if (it.hasNext()) {
				return it.next();
			}
		}
		return null;
	}

	/*
	 * Sample Tests
	 */
public static void main(String[] args) {
		InterleaveIterator iter = new InterleaveIterator<>();		
		
		ArrayList<Integer> i1 = new ArrayList<>();
		i1.add(1);
		i1.add(2);
		i1.add(3);
		i1.add(4);
		i1.add(5);
		
		List<Node> i2 = new ArrayList<>();
		i2.add(new Node(1));
		i2.add(new Node(2));
		
		Collection<Element> i3 = new ArrayList<>();
		i3.add(new Element(1));
		i3.add(new Element(2));
		i3.add(new Element(3));
		
		iter.addIterator(i1.iterator());
		iter.addIterator(i2.iterator());
		iter.addIterator(i3.iterator());
		
		while (iter.hasNext()) {
			System.out.print(iter.next() + " , ");
		}
}
}