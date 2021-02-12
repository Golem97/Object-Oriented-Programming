package gameClient;
import Graphics.LoginFrame;
import Graphics.DijkstraAlgo;


public class Ex2 {

//The two args i want to Received.

    private static int id;
    private static int scenario;


    public static void main(String[] args) {

        if(args.length != 0) { // if we give arguments skip login window

            try {
                //The recieved parameter in the main will be uploaded and use in the game :
                //The First Parameter will be The id
                id = Integer.parseInt(args[0]);
                //The Second Parameter will be the Scenario
                scenario = Integer.parseInt(args[1]);

            } catch (Exception e) {
                //If he didn't succeed to load the args the default args will be :

                // -1 For the ID
                id = -1;
                // 0 For the Level
                scenario = 0;
            }


            //Once i received the args to start the game i can uptade the information in my Frame Using a DijkstraALgo
            Thread client = new Thread(new DijkstraAlgo(id,scenario));

            //And Start the Game
            client.start();
        }

        //else go to login frame
        else {
         //If the args were not load in the Ex2 the User have to updat by hand in the LoginFrame
            //SO the Thread Of Login Start
         Thread client = new Thread(new LoginFrame());
         //The Thread is Running :
            client.start();
        }
    }

}


