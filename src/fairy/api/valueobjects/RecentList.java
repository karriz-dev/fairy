package fairy.api.valueobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.gson.Gson;

public class RecentList implements List<Object> {

	private Long blockHeight = 0L;
	private Long blockTimestamp = 0L;
	private List<Recent> recentList = null;
	
	public RecentList() {
		recentList = new ArrayList<Recent>();
	}
	
	public RecentList(long height, long timestamp) {
		this.blockHeight = height;
		this.blockTimestamp = timestamp;
		this.recentList = new ArrayList<Recent>();
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return "{\"blockHeight\":" + blockHeight + ", \"blockTimestamp\":" + blockTimestamp + ", \"recentList\":"
				+ gson.toJson(recentList) + "}";
	}

	@Override
	public int size() {
		return recentList.size();
	}

	@Override
	public boolean isEmpty() {
		return recentList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return recentList.contains(o);
	}

	@Override
	public Iterator iterator() {
		return recentList.iterator();
	}

	@Override
	public Object[] toArray() {
		return recentList.toArray();
	}

	@Override
	public Object[] toArray(Object[] a) {
		return recentList.toArray(a);
	}

	@Override
	public boolean add(Object e) {
		return recentList.add((Recent)e);
	}

	@Override
	public boolean remove(Object o) {
		recentList.remove(o);
		return true;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		recentList.clear();
	}

	@Override
	public Object get(int index) {
		return recentList.get(index);
	}

	@Override
	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, Object element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
