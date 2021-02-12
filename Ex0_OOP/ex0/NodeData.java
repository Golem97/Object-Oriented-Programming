package ex0;
//Import Collection and HashMap
import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data{
	//My Node data contain a Key , a Tag ,a HashMap and information
	//The counter is here to generate automatic node from test
	private int key ;
	private int tag;
	private String information ;
	private HashMap <Integer ,node_data> chahen= new HashMap<>();
    private static int counter_i=0;


    //CONSTRUCTOR
    public NodeData(){
    this.information=getInfo();
    this.tag= getTag();
	this.key= counter_i++;
    }

     //COPY CONSTRUCTOR
	public NodeData(NodeData n){
    this.key= n.getKey();
	this.tag= n.getTag();
	this.information=n.getInfo();
	this.chahen = new HashMap<>();
	this.chahen.putAll(n.getChahen());
    }

	@Override
	//return the key
	public int getKey() {
		// TODO Auto-generated method stub
		return this.key;   //return the Id of my node

	}

	@Override
	//return the Neighbours
	public Collection<node_data> getNi() {
		// TODO Auto-generated method stub
		return this.chahen.values();                    //return the collection(list) of neighbours of my node
	}


	@Override
	//Check if My Node have Neighbours
	public boolean hasNi(int key) {
		// TODO Auto-generated method stub
		return this.chahen.containsKey(key);
	}

	@Override
	//To Add a new neighbours on the Collection
	public void addNi(node_data t) {
	   this.chahen.put(t.getKey(),t);
	}

	@Override
	public void removeNode(node_data node) {
		//Remove the node.
		// TODO Auto-generated method stub
		if (this.chahen.containsValue(node)){
		this.chahen.remove(node);
		}

	}

	@Override
	//Return the information my node_data
	public String getInfo() {

		// TODO Auto-generated method stub
		return this.information;
	}

	@Override
	//To Set the information of my node_data
	public void setInfo(String s) {
		// TODO Auto-generated method stub
		this.information=s;
	}

	@Override
	//Return the Tag my node_data
	public int getTag() {
		// TODO Auto-generated method stub
		return this.tag;
	}

	@Override
	//To Set the Tag of my node_data
	public void setTag(int t) {
		// TODO Auto-generated method stub
		this.tag=t;

	}
	@Override
	public String ToString() {
	// TODO Auto-generated method stub

		return "Node N="+ this.getKey() +"   Neighbours  ="+ this.getNi()+"  ";

       }

	public HashMap<Integer, node_data> getChahen() {
		return chahen;
	}
}
