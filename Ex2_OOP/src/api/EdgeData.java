package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EdgeData implements edge_data{
    private int src;
    private double w;
    private int dest;
    private transient int tag;

    private transient String information ;

    //DEFAULT CONSTRUCTOR
    public EdgeData(){
        this.src=0;
        this.dest=0;
        this.w=-1;
        this.tag=0;
        this.information="";
    }

    //COPY CONSTRUCTOR
    public EdgeData(EdgeData e) {
        this.src = e.getSrc();
        this.dest = e.getDest();
        this.w = e.getWeight();
        this.information = e.getInfo();
        this.tag = e.getTag();
    }

    public EdgeData(int src, int dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.w = weight;
    }

    //GETTERS AND SETTERS
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.w;
    }

    @Override
    public String getInfo() {
        return this.information;
    }

    @Override
    public void setInfo(String s) {
        this.information=s;
    }

    public void setWeight(double w) {
        this.w=w;
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
        return  "src:" + src +
                ",w:" + w +
                ",dest:" + dest +
                '}';
    }


    public void setSource(int source) {
        this.src = source;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, tag, information, w);
    }
}

class jsonEdge implements JsonSerializer<edge_data>, JsonDeserializer<edge_data> {

    @Override
    public JsonElement serialize(edge_data myedge, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject jObj = new JsonObject();

        jObj.addProperty("src", myedge.getSrc());
        jObj.addProperty("w", myedge.getWeight());
        jObj.addProperty("dest", myedge.getDest());

        return jObj;
    }

    @Override
    public edge_data deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        int src = jsonElement.getAsJsonObject().get("src").getAsInt();
        double w = jsonElement.getAsJsonObject().get("w").getAsDouble();
        int dest = jsonElement.getAsJsonObject().get("dest").getAsInt();

        return new EdgeData(src, dest, w);
    }

}