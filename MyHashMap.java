import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> {
	private K[] keys;
	private V[] values;
	private int size;
	private int maxSize;
	private final int DEFAULT_SIZE = 16;

	public K[] getKeys(){
		return keys;
	}
	public V[] getValues(){
		return values;
	}
	public void print(){
		int index = 0;
		for (K key: keys){
			// if key is not null, print the key and value pair
			if (key != null){
				System.out.printf("%s, %s\n", key.toString(), values[index].toString());
			}
			index++;
		}
	}
	//initialize a hashmap with a default initial size
	public MyHashMap(){
		keys = (K[]) new Comparable[DEFAULT_SIZE];
		values = (V[]) new Comparable[DEFAULT_SIZE];
		maxSize = DEFAULT_SIZE;
		size = 0;
	}

	//initialize a hashmap with a given initial size
	public MyHashMap(int size){
		keys = (K[]) new Comparable[size];
		values = (V[]) new Comparable[size];
		maxSize = size;
		this.size = 0;
	}

	//reconstructor for dynamic expansion
	private void expandHashMap(MyHashMap<K, V> hashMap){
		maxSize *= 2;
		K[] tempKeys = (K[]) new Comparable[maxSize];
		V[] tempValues = (V[]) new Comparable[maxSize];
		//transfer item from keys to temp_keys
		for (int i = 0; i < hashMap.getKeys().length; i++){
			tempKeys[i] = hashMap.getKeys()[i];
		}
		//transfer items from values to temp_values
		for (int i = 0; i < hashMap.getValues().length; i++){
			tempValues[i] = hashMap.getValues()[i];
		}
		keys = tempKeys;
		values = tempValues;
	}
	//get the value a certain key holds
	public V get(K key){
		int hashCode = key.hashCode();
		int keyPos = hashCode % maxSize;
		int finding  = 0; // how many values we have looked through
		while (finding < maxSize){
			keyPos = keyPos % maxSize; //keep the key pos in range of the array size
			K currKey = keys[keyPos];
			//if the current key does not exist
			if (currKey == null){
				return null;
			}
			//if the current key is the key we are looking for (linear probing)
			if (currKey.equals(key)){
				return values[keyPos];
			}
			finding++;
			keyPos++;// otherwise increment the key and keep going
		}
		//if not found, return null
		return null;
	}
	public boolean put(K key, V value){
		int hashCode = key.hashCode();
		//if the list is full, allocate more memory
		if (size == maxSize){
			expandHashMap(this);
		}
		int keyPos = hashCode % maxSize;
		int finding = 0; //are we currently still trying to look for a value
		while (finding < maxSize){
			keyPos = keyPos % maxSize; //keep the key pos in range of the array size
			K currKey = keys[keyPos];
			//if the current key does not exist
			if (currKey == null){
				//if there is nothing in the current space
				//insert at current position
				keys[keyPos] = key;
				values[keyPos] = value;
				size++;
				return true;
			}
			//if the current key is the key we are looking for, replace value
			if (currKey.equals(key)){
				//no need to change the key
				values[keyPos] = value;
				size++;
				return true;
			}
			finding++;
			keyPos++;// otherwise increment the key and keep going
		}
		//if somehow not inserted return false
		return false;
	}
	public V remove(K key){
		int hashCode = key.hashCode();
		int keyPos = hashCode % maxSize;
		int finding = 0; //are we currently still trying to look for a value
		while (finding < maxSize){
			keyPos = keyPos % maxSize; //keep the key pos in range of the array size
			K currKey = keys[keyPos];
			//if the current key does not exist
			if (currKey == null){
				//then key is not found
				return null; // for cannot remove
			}
			//if the current key is the key we are looking for, replace value
			if (currKey.equals(key)){
				//key and value pair needs to be removed
				keys[keyPos] = null;
				V removedValue = values[keyPos];
				values[keyPos] = null;
				size--;
				return removedValue; //for removed
			}
			finding++;
			keyPos++;// otherwise increment the key and keep going
		}
		//if not found, return false
		return null;

	}
	public boolean remove(K key, V value){
		int hashCode = key.hashCode();
		int keyPos = hashCode % maxSize;
		int finding = 0; //are we currently still trying to look for a value
		while (finding < maxSize){
			keyPos = keyPos % maxSize; //keep the key pos in range of the array size
			K currKey = keys[keyPos];
			//if the current key does not exist
			if (currKey == null){
				//then key is not found
				return false; // for cannot remove
			}
			//if the current key is the key we are looking for, replace value
			if (currKey.equals(key)){
				//if the value is not what we are looking for, dont remove
				if (value != values[keyPos]){
					return false;
				}
				//key and value pair needs to be removed
				keys[keyPos] = null;
				values[keyPos] = null;
				size--;
				return true; //for removed
			}
			finding++;
			keyPos++;// otherwise increment the key and keep going
		}
		//if not found, return false
		return false;

	}
	public int size(){
		return this.size;
	}
	public boolean clear(){
		//keys = new ArrayList<K>(maxSize);
		//values = new ArrayList<V>(maxSize);
		keys = (K[]) new Comparable[maxSize];
		values = (V[]) new Comparable[maxSize];
		size = 0;
		return true;
	}
	public boolean isEmpty(){
		return size == 0;
	}
	public boolean containsKey(K key){
		//get the position by % the key's hashcode with the table size
		int keyPos = key.hashCode() % maxSize;
		int finding = 0; //are we currently still trying to look for a value
		while (finding < maxSize){
			keyPos = keyPos % maxSize; //keep the key pos in range of the array size
			K currKey = keys[keyPos];
			//if the current key does not exist
			if (currKey == null){
				//then key is not found
				return false; // for cannot remove
			}
			//if the current key is the key we are looking for, replace value
			if (currKey.equals(key)){
				return true;
			}
			finding++;
			keyPos++;// otherwise increment the key and keep going
		}
		return false; // if not in any position
	}
	public boolean containsValue(V value){
		for (V currValue: values){
			//if the value at the current index of the array is the same as the one
			//we are looking for, return true
			if (value == currValue)
				return true;
		}
		return false;
	}
	public Set<K> keySet(){
		Set<K> keySet = new LinkedHashSet<>();
		for (K currKey: keys){
			//if the current key is not null
			if (currKey != null){
				keySet.add(currKey);
			}
		}
		return keySet;
	}
	public Collection<V> values(){
		List<V> valueList = new ArrayList<>();
		for (V  currValue: values){
			//if the current key is not null
			if (currValue != null){
				valueList.add(currValue);
			}
		}
		return valueList;
	}
	public void putAll(MyHashMap<? extends K,? extends V> map){
		K[] mapKeys = map.getKeys();
		V[] mapValues = map.getValues();
		int index = 0;
		for (K key: mapKeys){
			// if key is not null, put the key and value pair into current map
			if (key != null && mapValues[index] != null){
				put(mapKeys[index], mapValues[index]);
			}
			index++;
		}
	}
}
