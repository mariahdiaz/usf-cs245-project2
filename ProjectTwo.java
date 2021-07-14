import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Year;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;




public class ProjectTwo {
	List <String> stateCodes;
	Map< String, List<DataPoint>> allStates;
	static String listType;
	public ProjectTwo(String lType) {
		listType=lType;
		
		allStates=new HashMap<String, List<DataPoint>>();
		//stateCodes= new ArrayList<String>();
		stateCodes= new LinkedList<String>();
		stateCodes.add("AK");
		stateCodes.add("AL");
		stateCodes.add("AZ");
		stateCodes.add("AR");
		stateCodes.add("CA");
		stateCodes.add("CO");
		stateCodes.add("CT");
		stateCodes.add("DE");
		stateCodes.add("FL");
		stateCodes.add("GA");
		stateCodes.add("HI");
		stateCodes.add("ID");
		stateCodes.add("IL");
		stateCodes.add("IN");
		stateCodes.add("IA");
		stateCodes.add("KS");
		stateCodes.add("KY");
		stateCodes.add("LA");
		stateCodes.add("ME");
		stateCodes.add("MD");
		stateCodes.add("MA");
		stateCodes.add("MI");
		stateCodes.add("MN");
		stateCodes.add("MS");
		stateCodes.add("MO");
		stateCodes.add("MT");
		stateCodes.add("NE");
		stateCodes.add("NV");
		stateCodes.add("NH");
		stateCodes.add("NJ");
		stateCodes.add("NM");
		stateCodes.add("NY");
		stateCodes.add("NC");
		stateCodes.add("ND");
		stateCodes.add("OH");
		stateCodes.add("OK");
		stateCodes.add("OR");
		stateCodes.add("PA");
		stateCodes.add("RI");
		stateCodes.add("SC");
		stateCodes.add("SD");
		stateCodes.add("TN");
		stateCodes.add("TX");
		stateCodes.add("UT");
		stateCodes.add("VT");
		stateCodes.add("VA");
		stateCodes.add("WA");
		stateCodes.add("WV");
		stateCodes.add("WI");
		stateCodes.add("WY");

		
	}
	
	public void readAllStates(String directory) {
		for(int i=0; i<stateCodes.size(); i++) {
			String filename=directory+"/"+stateCodes.get(i)+".TXT";
			try {
				List<DataPoint> myData=readData(filename);
				allStates.put(stateCodes.get(i), myData);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	static List<DataPoint> readData(String filename) throws FileNotFoundException {
		/*CA,F,1910,Mary,295
		CA,F,1910,Helen,239
		CA,F,1910,Dorothy,220
		CA,F,1910,Margaret,163
		*/
		List<DataPoint> allData;
		if(listType.equals("ArrayList")) {
			allData= new ArrayList<DataPoint>();
		}
		else {
			allData=new LinkedList<DataPoint>();
		}
		Scanner scanner = new Scanner(new File(filename));
		
		while (scanner.hasNext()){
			String data = scanner.nextLine();
			String[] dataPoints= data.split(",");
			
			DataPoint dp= new DataPoint(dataPoints[0],
					dataPoints[1], 
					Integer.parseInt(dataPoints[2]),
					dataPoints[3],
					Integer.parseInt(dataPoints[4]));
			allData.add(dp);
		}
		return allData;
		
	}
	
	public static int likelyAge(List<DataPoint> stateData, String nameData, String genderData ) {
		int maxCount=0;
		int year=0;
		//for(int i=0; i<stateData.size(); i++) {
		for(DataPoint dataPoint : stateData) {
	
			 //DataPoint dataPoint=stateData.get(i);
			 String gender=dataPoint.getGender();
			 String name=dataPoint.getName();
			 int count=dataPoint.getCount();
			
			 if(count>maxCount &&  name.equalsIgnoreCase(nameData) && gender.equalsIgnoreCase(genderData)) {
				maxCount=count;
				year=dataPoint.getYear();
			 }
			 
			
		}
		
		 Year thisYear = Year.now();
		 return (thisYear.getValue()-year);
		
	}
	
	public static Properties readPropertiesFile(String fileName) throws IOException {
	      FileInputStream fis = null;
	      Properties prop = null;
	      try {
	         fis = new FileInputStream(fileName);
	         prop = new Properties();
	         prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         fis.close();
	      }
	      return prop;
	   }
	
	public static void  main(String args[]) throws IOException{
		//String directory= "/Users/mariahdiaz/Downloads/namesbystate"; 
		Properties myProps=readPropertiesFile(args[0]);
		String directory=myProps.getProperty("Directory");
		String listType=myProps.getProperty("ListType");
		
		ProjectTwo projectTwo=new ProjectTwo(listType);
		projectTwo.readAllStates(directory);
		//List<DataPoint> myData =projectTwo.allStates.get("NY");
		
		Boolean done=false;
		
		while(done==false){
			Scanner sc=new Scanner(System.in);
			System.out.println("Name of the person(or EXIT to quit):");
			String name=sc.next();
			if(name.equalsIgnoreCase("EXIT")) {
				done=true;
			}
			else {
				System.out.println("Gender (M/F):");
				String gender=sc.next();
				Boolean validState=false;
				String state=null;
				while(validState==false) {
					System.out.println("State of birth (two-letter state code):");
					state=sc.next();
					if(projectTwo.stateCodes.contains(state)) {
						validState=true;
					}
				}
				
				List<DataPoint> myData =projectTwo.allStates.get(state);
				int likelyAge=likelyAge(myData, name, gender);
				if(likelyAge==Year.now().getValue()) {
					System.out.println("Nothing found for input enter new input: ");
					
				}
				else {
					System.out.println(name+", born in "+state+" is most likely around "+ likelyAge +" years old");

				}
				
			}
			
			
		}
		
		//System.out.println(likelyAge(myData, "Mariah", "F"));
		//System.out.println(myData.size());
		//System.out.println(myData.get(0));
		
		
		
		
		
		

	}
	

}

	
 class DataPoint {
	 String state;
	 String gender;
	 int year;
	 String name;
	 int count;
	 
	 public DataPoint(String state, String gender, int year, String name, int count) {
		 setState(state);
		 setGender(gender);
		 setYear(year);
		 setName(name);
		 setCount(count);
	 }
	 public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
		public String toString() {
			return (state+ ","+ gender+","+ year+","+ name+","+ count);
		}
		
}
 
 class ArrayList<T> implements List<T>{
	 T[] arr;
	 int size;
	 public ArrayList() {
		arr= (T[]) new Object[10];
		size=0;
		 
	 }

	@Override
	public int size() {
		
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		for(int i=0; i<arr.length;i++) {
			if(o.equals(arr[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int current=0;

			@Override
			public boolean hasNext() {
				
				return current<size();
			}

			@Override
			public T next() {
				if(hasNext()) {
					return arr[current++];
					
				}
				return null;
			}
			
		};
		
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public void doubleArray() {
		T [] doubleArr= (T[]) new Object[size*2];
		for(int i=0; i<arr.length; i++) {
			doubleArr[i]=arr[i];
			
		}
		arr=doubleArr;
		
	}
	@Override
	public boolean add(T e) {
		if(size==arr.length) {
			doubleArray();
		}
		arr[size]=e;
		size++;
		
		
		return true;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public T get(int index) {
		
		return arr[index];
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
	public T remove(int index) {
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
 
 class LinkNode<T> {
	 T data;
	 LinkNode<T> next;
	 public LinkNode(T t){
		 data=t;
	 }
	 
 }

 class LinkedList<T> implements List<T>{
	 
	 LinkNode<T> first;
	 LinkNode<T> last;
	 int size;
	 
	 public LinkedList(){
		 size=0;
		 
	 }

	@Override
	public int size() {
		
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		LinkNode<T> temp=first;
		while(temp!=null) {
			if(temp.data.equals(o)) {
				return true;
			}
			temp=temp.next;
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			LinkNode<T> current=first;

			@Override
			public boolean hasNext() {
				
				return current!=null;
			}

			@Override
			public T next() {
				if(hasNext()) {
					T data=current.data;
					current=current.next;
					return data;
				}
				return null;
			}
			
		};
		
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T e) {
		
		if(size==0) {
			first=new LinkNode<T>(e);
			last=first;
		}
		else {
			LinkNode<T> temp= new LinkNode<T>(e);
			last.next=temp;
			last=temp;
			
		}
		size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T get(int index) {
		LinkNode<T> temp=first;
		for(int i=0; i<index; i++) {
			temp=temp.next;
		}

		
		return temp.data;
	}

	@Override
	public T set(int index, T element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T remove(int index) {
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
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	 
 }
