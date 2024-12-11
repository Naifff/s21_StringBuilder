import java.util.Stack;

public class CustomStringBuilder {
	private char[] value;
	private int size;
	private final Stack<String> history;
	private static final int DEFAULT_CAPACITY = 16;

	public CustomStringBuilder() {
		value = new char[DEFAULT_CAPACITY];
		size = 0;
		history = new Stack<>();
		saveState();
	}

	public CustomStringBuilder(String str) {
		value = new char[Math.max(DEFAULT_CAPACITY, str.length())];
		size = str.length();
		System.arraycopy(str.toCharArray(), 0, value, 0, str.length());
		history = new Stack<>();
		saveState();
	}

	private void ensureCapacity(int minimumCapacity) {
		if (minimumCapacity > value.length) {
			int newCapacity = Math.max(value.length * 2, minimumCapacity);
			char[] newValue = new char[newCapacity];
			System.arraycopy(value, 0, newValue, 0, size);
			value = newValue;
		}
	}

	private void saveState() {
		history.push(toString());
	}

	public CustomStringBuilder append(String str) {
		if (str == null) str = "null";
		int len = str.length();
		ensureCapacity(size + len);
		System.arraycopy(str.toCharArray(), 0, value, size, len);
		size += len;
		saveState();
		return this;
	}

	public CustomStringBuilder append(char c) {
		ensureCapacity(size + 1);
		value[size++] = c;
		saveState();
		return this;
	}

	public CustomStringBuilder delete(int start, int end) {
		if (start < 0 || start > size || start > end)
			throw new StringIndexOutOfBoundsException();

		end = Math.min(end, size);
		int len = end - start;
		System.arraycopy(value, end, value, start, size - end);
		size -= len;
		saveState();
		return this;
	}

	public CustomStringBuilder reverse() {
		int n = size - 1;
		for (int j = (n - 1) >> 1; j >= 0; j--) {
			int k = n - j;
			char cj = value[j];
			char ck = value[k];
			value[j] = ck;
			value[k] = cj;
		}
		saveState();
		return this;
	}

	public CustomStringBuilder insert(int offset, String str) {
		if (offset < 0 || offset > size)
			throw new StringIndexOutOfBoundsException();
		if (str == null) str = "null";

		int len = str.length();
		ensureCapacity(size + len);

		System.arraycopy(value, offset, value, offset + len, size - offset);
		System.arraycopy(str.toCharArray(), 0, value, offset, len);
		size += len;
		saveState();
		return this;
	}

	public CustomStringBuilder replace(int start, int end, String str) {
		if (start < 0 || start > size || start > end)
			throw new StringIndexOutOfBoundsException();
		if (str == null) str = "null";

		end = Math.min(end, size);
		int len = str.length();
		int newSize = size - (end - start) + len;
		ensureCapacity(newSize);

		System.arraycopy(value, end, value, start + len, size - end);
		System.arraycopy(str.toCharArray(), 0, value, start, len);
		size = newSize;
		saveState();
		return this;
	}

	public boolean undo() {
		if (history.size() <= 1) {
			return false;
		}

		history.pop(); // Remove current state
		String previousState = history.peek();
		value = new char[Math.max(DEFAULT_CAPACITY, previousState.length())];
		size = previousState.length();
		System.arraycopy(previousState.toCharArray(), 0, value, 0, size);
		return true;
	}

	public int length() {
		return size;
	}

	@Override
	public String toString() {
		return new String(value, 0, size);
	}

	public char charAt(int index) {
		if (index < 0 || index >= size)
			throw new StringIndexOutOfBoundsException();
		return value[index];
	}

	public int indexOf(String str) {
		if (str == null) str = "null";
		char[] target = str.toCharArray();

		for (int i = 0; i <= size - target.length; i++) {
			boolean found = true;
			for (int j = 0; j < target.length; j++) {
				if (value[i + j] != target[j]) {
					found = false;
					break;
				}
			}
			if (found) return i;
		}
		return -1;
	}
}