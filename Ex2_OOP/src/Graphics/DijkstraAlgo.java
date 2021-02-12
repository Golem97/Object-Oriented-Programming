package Graphics;


import Server.Game_Server_Ex2;
import api.*;
import gameClient.Arena;
import gameClient.Agent;
import gameClient.Pokemon;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static gameClient.Arena.EPS1;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.parallelSetAll;

public class DijkstraAlgo implements Runnable{
    private static gameClient.MyFrame _win;
    private static Arena _ar;
    private static long tz;
    private static int scenario;

    public DijkstraAlgo(long tz, int scenario){
        this.tz = tz;
        this.scenario = scenario;

    }

    @Override
    public synchronized void run() {

        // You have [0,23] games
        game_service game = Game_Server_Ex2.getServer(this.scenario);

        //JSON String file representing the graph of a given scenario
        String g = game.getGraph();

        //Saving the Json file into a graph.json file
        try {
            File f = new File("graph.json");
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(g);
            fileWriter.close();
            game.login(this.tz);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Loading it into ag
        dw_graph_algorithms ag = new DWGraph_Algo();
        ag.load("graph.json");
        directed_weighted_graph gg = ag.getGraph();

        // directed_weighted_graph gg= game.getJava_Graph_Not_to_be_used();


        //Init
        init(game,gg);

       //The Game Start Here
        game.startGame();

        //Set the tilt of My Window Game
        _win.setTitle("POKEMON GAME by JORDAN PEREZ & NATHANAEL BENICHOU | Tz: "+tz+" | Scenario number: "+scenario+" ");

        //Time to Sleep of the thread
        int ind=0;
        long dt=100;

        //While the Game is Running all the Agent try to Eat the maximum of Pokemon possible
        while(game.isRunning()) {
            moveAgents(game, gg);

            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph gg) {

        dw_graph_algorithms algo_g = new DWGraph_Algo();
        algo_g.init(gg);

        String lg = game.move(); //get the JSON String with all agent status
        List<Agent> agentList = Arena.getAgents(lg, gg); //turn it into a list of agents
        _ar.setAgents(agentList); //updates arena agents

        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();

        String fs = game.getPokemons(); // get the JSON String with all pokemons status
        List<Pokemon> pokeList = Arena.json2Pokemons(fs); //turn it into a list of pokemons

        //update edge of each pokemon in the pokelist
        for (Pokemon pokemon : pokeList) {
            updateEdge(pokemon, gg);
        }

                _ar.setPokemons(pokeList); // updates arena pokemons as pokelist

        //Init Pokemon as not Targeted
        for  (Pokemon pokemon :pokeList) {
            pokemon.setTargeted(false);
        }




        //Pass over the agent list
        for (Agent agent : agentList) {

            //Pass over the Pokemon list
            for (Pokemon pokemon : pokeList) {


                //Initialise the node to go
                NodeData nodeToGo = new NodeData(pokemon.get_edge().getDest());

                if (pokemon.getType()==1){
                    nodeToGo = new NodeData(pokemon.get_edge().getSrc());
                }

                //Set the distance of my current agent to my current pokemon
                double shortestpath = algo_g.shortestPathDist(agent.getSrcNode(), nodeToGo.getKey());

                //if there is no minimum distance registered from this pokemon to any agent OR
                //if there is a bigger distance registered then keep the smallest
                if(pokemon.getDistFromAgent() != -1 || pokemon.getDistFromAgent() > shortestpath){
                    pokemon.setDistFromAgent(shortestpath);
                }
            }

            wichBest(agent,pokeList,gg);


            for (int i = 0; i < agentList.size(); i++) {
                Agent _agent = agentList.get(i);

                int id = _agent.getID();
                int dest = _agent.getNextNode();
                int src = _agent.getSrcNode();
                double v = _agent.getValue();

                if (dest == -1) {
                    wichBest(agent,pokeList,gg);

                    dest = nextNode(gg, _agent,pokeList);
                    game.chooseNextEdge(_agent.getID(), dest);

                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest+" i= "+i);


                }
            }
            wichBest(agent,pokeList,gg);
        }

    }



    public static void updateEdge(Pokemon fr, directed_weighted_graph g) {
        for (node_data v : g.getV()) {
            for (edge_data e : g.getE(v.getKey())) {
                boolean f = isOnEdge(fr.getLocation(), e, fr.getType(), g);
                if (f) {
                    fr.set_edge(e);
                }
            }
        }
    }

    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p,src,dest);
    }


    private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
        int src = g.getNode(e.getSrc()).getKey();
        int dest = g.getNode(e.getDest()).getKey();
        if(type<0 && dest>src) {return false;}
        if(type>0 && src>dest) {return false;}
        return isOnEdge(p,src, dest, g);
    }



    /**
     * a very simple random walk implementation!
     * @param g
     * @param agent
     * @return
     */
    private static int nextNode(directed_weighted_graph g, Agent agent, List<Pokemon> pokeList) {



        dw_graph_algorithms algo_g = new DWGraph_Algo();
        algo_g.init(g);


        //Initialise the Best Pokemon For each Agent.
        wichBest(agent,pokeList,g);


        int[] arr_help = fromListToInt(algo_g.shortestPath(agent.getSrcNode(), agent.get_curr_fruit().get_edge().getDest()));

//        if (algo_g.shortestPath(agent.getSrcNode(), agent.get_curr_fruit().get_edge().getDest())==null)
//        {
       // wichBest(agent,pokeList,g);

        if (arr_help.length==0){
            //  wichBest(agent,pokeList,g);
            pokeList.remove(agent.get_curr_fruit());
            wichBest(agent,pokeList,g);
        }

        wichBest(agent,pokeList,g);

        int[] arr_path = fromListToInt(algo_g.shortestPath(agent.getSrcNode(), agent.get_curr_fruit().get_edge().getDest()));


        int myKey;


        if (agent.getSrcNode()!=agent.get_curr_fruit().get_edge().getDest()) {
            myKey = arr_path[0];
        }

        else {
            myKey =agent.get_curr_fruit().get_edge().getSrc();
        }


        //   wichBest(agent,pokeList,g);


        return myKey;
    }


    //To transform my List of Node to int arr to update the path of each agent and after each move remove the moved path
    private static int[] fromListToInt(List<node_data> ln){
        //Creat an array that can contain all my node's keys
        int []arr = new int[ln.size()];
        int index= 0;

        //Pass all over the list
        for (node_data nd :ln) {
            //Put the value to form of int
            arr[index]=nd.getKey();

            index++;

        }
        //Return the path without the move already done by ther agent .
        return copyOfRange(arr,1,arr.length);
    }


    //Calcul the Ratio of each pokemon compared to the recievd agent.
    public static double ratioPokemon (Pokemon pokemon , Agent agent, directed_weighted_graph g){
        dw_graph_algorithms algo_g = new DWGraph_Algo();


        //TO Call my Algo Dijkstra
        algo_g.init(g);
        double ratio ;

        //For each initialise the Shortest Dist Frome my Pokemon to my Agent
        double dist = algo_g.shortestPathDist(agent.getSrcNode(),pokemon.get_edge().getDest());

        //Take in cont the value of my Pokemon
        double value = pokemon.getValue();

        //Initiat the ratio take in count the value and the Dist
        ratio = ((4*value)+dist)/5;

        //Return the ratio
        return ratio;
    }

    private static void wichBest(Agent agent, List<Pokemon> pokeList, directed_weighted_graph g) {

        dw_graph_algorithms algo_g = new DWGraph_Algo();
        algo_g.init(g);

        if (!pokeList.isEmpty()) {
            agent.set_curr_fruit(pokeList.get(0));
        }
        agent.get_curr_fruit().setTargeted(true);

        //Initialise the current pokemon of my agent to the first pokemon.
        //Pass all over the pokeList.
        for (Pokemon pokemon : pokeList) {
            //Checks if the pokemon is already targeted.
            //If the distance from my agent to my pokemon is bigger than my current pokemon.
            if (!pokemon.isTargeted()&& ratioPokemon(agent.get_curr_fruit(),agent,g) >ratioPokemon(pokemon,agent,g)) {
                //my current pokemon become the pokemon of my agent.

                agent.get_curr_fruit().setTargeted(false);

                agent.set_curr_fruit(pokemon);
                //Set Pokemon to Targeted.
                pokemon.setTargeted(true);

            }

        }

    }

    private void init(game_service game, directed_weighted_graph graph) {
       // String g = game.getGraph();
        String fs = game.getPokemons();
       // directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);



        _ar = new Arena();
        _ar.setGraph(graph);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new gameClient.MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
          //  System.out.println(info);
          //  System.out.println(game.getPokemons());

            int src_node = 0;  // arbitrary node, you should start at one of the pokemon

            ArrayList<Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());


            for(int a = 0;a<cl_fs.size();a++) {

                Pokemon myPokemon = cl_fs.get(a);
                Arena.updateEdge(myPokemon,graph);


            }



            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
    private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if(dist>d1-EPS1) {ans = true;}
        return ans;
    }
}

