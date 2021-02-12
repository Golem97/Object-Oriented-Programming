package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class DWGraph_Serializer implements JsonSerializer<directed_weighted_graph> {

    jsonNode jNodes = new jsonNode();
    jsonEdge jEdges = new jsonEdge();

    @Override
    public JsonElement serialize(directed_weighted_graph mygraph, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonArray nodetab = new JsonArray();
        JsonArray edgetab = new JsonArray();
        Collection<node_data> nodeMap = mygraph.getV();

        for (node_data myNode : nodeMap) {

            nodetab.add(jNodes.serialize(myNode, type, jsonSerializationContext));

            Collection<edge_data> edgeMap = mygraph.getE(myNode.getKey());

            if (edgeMap != null) {
                for (edge_data myEdge : edgeMap) {

                    edgetab.add(jEdges.serialize(myEdge, type, jsonSerializationContext));
                }
            }
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Edges", edgetab);
        jsonObject.add("Nodes", nodetab);

        return jsonObject;
    }
}
