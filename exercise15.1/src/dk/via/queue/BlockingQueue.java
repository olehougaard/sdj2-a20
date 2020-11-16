package dk.via.queue;

import utility.collection.BoundedArrayQueue;
import utility.collection.QueueADT;

public class BlockingQueue<T> implements Buffer<T> {
	private QueueADT<T> queue;
	
	public BlockingQueue(int capacity) {
		queue = new BoundedArrayQueue<>(capacity);
	}

	@Override
	public synchronized void put(T element) {
		while(queue.isFull()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		queue.enqueue(element);
		notifyAll();
	}

	@Override
	public synchronized T take() {
		while(queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T element = queue.dequeue();
		notifyAll();
		return element;
	}

	@Override
	public synchronized T look() {
		return queue.first();
	}

	@Override
	public synchronized boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public boolean isFull() {
		return queue.isFull();
	}

	@Override
	public int size() {
		return queue.size();
	}

}
