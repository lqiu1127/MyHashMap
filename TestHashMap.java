import java.util.Collection;
import java.util.Set;

public class TestHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyHashMap<Integer, String> mhm = new MyHashMap<Integer, String>(3);

		assert(mhm.isEmpty()); //test is isEmpty method
		mhm.put(12, "test1"); // test putting a couple elements into the list
		mhm.put(18, "test2");
		assert(mhm.get(12).equals("test1")); //test getting those elements from the list
		assert(mhm.get(18).equals("test2"));
		assert(mhm.get(125).equals(null)); //test getting non existent element
		assert(mhm.size()== 3); //test that the size is increasing

		mhm.put(18, "test2++");
		assert(mhm.get(18).equals("test2++"));//test reinserting of same element

		mhm.remove(18);
		assert(mhm.get(18).equals(null)); // testing removing element

		mhm.put(15, "test3"); //test collision handling (collides with 12)
		assert(mhm.get(12).equals("test1"));
		assert(mhm.get(15).equals("test3"));
		assert(!mhm.isEmpty()); //test is isEmpty method
		mhm.print();//test print
		System.out.println();
		mhm.put(17, "test2");
		mhm.put(19, "test4"); //test expanding size of the list
		assert(mhm.size() == 4); //test the size method (again)
		assert(!mhm.remove(19, "test1000")); //test remove with key and value with wrong value
		assert(mhm.remove(19, "test4")); //test remove with key and value with correct value
		assert(mhm.get(19).equals(null)); //test remove with key and value with correct value
		mhm.print();//test print
		System.out.println();
		//test putAll
		MyHashMap<Integer, String> mhm2 = new MyHashMap<Integer, String>();
		mhm2.putAll(mhm);
		mhm2.print(); //test print
		System.out.println();
		mhm.clear(); //test clear function;
		assert(mhm.size()==0); //check is the size is reset
		assert(mhm.get(17).equals(null));//check if elements are reset

		//test contains key
		assert(mhm2.containsKey(17));
		assert(!mhm2.containsKey(1199));

		//test contains value
		assert(!mhm2.containsValue("tested again"));
		assert(mhm2.containsValue("test1"));

		//test keySet and values
		Set<Integer> keySet = mhm2.keySet();
		Collection<String> valuesCollection = mhm2.values();
		System.out.println(keySet.toString());
		System.out.println(valuesCollection.toString());
	}

}
