package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializer;


import java.lang.reflect.Type;


public class DWGraph_Deserializer implements JsonDeserializer<directed_weighted_graph> {

    jsonNode Jnodes = new jsonNode();

    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        directed_weighted_graph mygraph = new DWGraph_DS();

        JsonArray edgestab = jsonElement.getAsJsonObject().getAsJsonArray("Edges");
        JsonArray nodestab = jsonElement.getAsJsonObject().getAsJsonArray("Nodes");

        for (JsonElement na : nodestab) {
            mygraph.addNode(Jnodes.deserialize(na, type, jsonDeserializationContext));
        }

        for (JsonElement ea : edgestab) {

            double weight = ea.getAsJsonObject().get("w").getAsDouble();
            int dest = ea.getAsJsonObject().get("dest").getAsInt();
            int src = ea.getAsJsonObject().get("src").getAsInt();

            if(mygraph != null){
                mygraph.connect(src, dest, weight);
            }
        }
        return mygraph;
    }
}
