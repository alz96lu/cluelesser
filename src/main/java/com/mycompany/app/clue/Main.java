package clue;



class Main {

    public static void main(String[] args){
        // Register players, run game

        Game game = new Game();
        PlayerInterface player0 = new Player();
        PlayerInterface player1 = new Player();
        PlayerInterface player2 = new MachinePlayer();

        game.register(player0);
        game.register(player1);
        game.register(player2);        
        
        game.setup();
        game.start();

    }
}
