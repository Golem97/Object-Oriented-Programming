package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

public class NodeData implements node_data {

    //My Node data contain a Key , a Tag ,a HashMap, a weight and an information

    transient HashMap<Integer , node_data> hash_node= new HashMap<>();

    private transient int tag;
    private transient String information ;
    private transient double weight;
    private geo_location pos;
    private int id ;


    //DEFAULT CONSTRUCTOR
    public NodeData(int k) {
        this.id = k;
        this.information = "";
        this.tag = -1;
        this.weight = -1;
        this.hash_node = new HashMap<>();
    }

    //TEST CONSTRUCTOR2
    public NodeData() {

        this.id = -1;
        this.information = "";
        this.tag = -1;
        this.weight = -1;
        this.hash_node = new HashMap<>();
    }

    //TEST CONSTRUCTOR
    public NodeData(int k, geo_location p, int t, String info, double w ) {

        this.id = k;
        this.information = info;
        this.tag = t;
        this.weight = w;
        this.pos = p;
        this.hash_node = new HashMap<>();
    }



    //COPY CONSTRUCTOR
    public NodeData(NodeData n){
        this.information=n.getInfo();
        this.tag= n.getTag();
        this.id= n.getKey();
        this.weight=n.getWeight();
        this.pos = n.getLocation();
        this.hash_node.putAll(n.hash_node);
    }

    public NodeData(int id, GeoLocation p) {
        this.id = id;
        this.pos = p;
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public geo_location getLocation() {
        return this.pos;
    }

    @Override
    public void setLocation(geo_location p) {
        this.pos=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.information;
    }

    @Override
    public void setInfo(String s) {
        this.information=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;

    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ",pos:" + pos +
       //         ", info=" + information + //for tests
                '}';
    }

    public HashMap<Integer, node_data> getNi() {
        return hash_node;
    }

//    public double[] getPos(){
//        double[] coo = {this.getLocation().x(),this.getLocation().y(),this.getLocation().z()};
//        return coo;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tag, information, weight, pos);
    }

    public void setId(int id) {
        this.id = id;
    }
}

    class jsonNode implements JsonSerializer<node_data>, JsonDeserializer<node_data> {
        @Override
        public JsonElement serialize(node_data node, Type type, JsonSerializationContext jsonSerializationContext) {

            JsonObject jObj = new JsonObject();

            jObj.addProperty("id", node.getKey());
            jObj.addProperty("pos", node.getLocation().toString());

            return jObj;
        }

        @Override
        public node_data deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            String location = jsonElement.getAsJsonObject().get("pos").getAsString();
            String[] allLocations = location.split(",");

            GeoLocation pos = new GeoLocation(Double.parseDouble(allLocations[0]), Double.parseDouble(allLocations[1]), Double.parseDouble(allLocations[2]));

            int id = jsonElement.getAsJsonObject().get("id").getAsInt();

            NodeData nd = new NodeData(id, pos);

            return nd;
        }
    }