
/* 
The "jmaze" utilizes the genetic algorithm, a recursive algorithm inspirted by eugenics and evolution. On a eugenics stance, 
GA hopes to improve the gene pool of the players interacting with the maze by keeping traits that are more "fit". 


GUI setup: idk yet 

Instructions: 
list of POPULATION_COUNT instructions
either: 
    'left'
    'right'
    'up'
    'down'

Simluation

finess function
In order to rank the good from the bad, we define a fitness function. The trajectory of the individuals from the target is mostly a straight line, 
playing into the intuitve and greedy nature that the genetic algorithm induces. The fitness function is denoted simply by: 

f(x,y) = sqrt(x*x + y*y)

inuitively, efficiency shuold also be considered, in order to prevent players from mindlessly running around the map with a given number of steps. 
However, it makes the logic a lot more complicated, so I will keep that wrapped up for now 


selection: 
random_wheel to select the genes 
elitism will keep 3 of the best fitted genomes for direct reproduction * optional 

*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WorldsHardestGame extends JPanel
        implements ActionListener, KeyListener {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
   

    private List<Player> players;

    private static final List<String> CONTROL_OPTIONS = List.of("up", "down", "left", "right");

    private Timer timer;
    private float CROSSOVER_PROBABILITY; 
    private double MUTATION_RATE; 
    private int POPULATION_SIZE; 
    private int STEPS;
    private int generationTick;
    private int generationNumber;
    private int ELITES;


    public WorldsHardestGame(List<Player> players, float CROSSOVER_PROBABILITY, double MUTATION_RATE, int POPULATION_SIZE, int STEPS, int ELITES) {

        this.players = players;
        this.MUTATION_RATE  = MUTATION_RATE; 
        this.CROSSOVER_PROBABILITY = CROSSOVER_PROBABILITY; 
        this.POPULATION_SIZE = POPULATION_SIZE; 
        this.ELITES = ELITES;
        this.STEPS = STEPS;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this);
        timer.start();
        generationTick = 0;
        generationNumber = 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Play area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(50, 50, 700, 500);

        // Goal
        g.setColor(Color.GREEN);
        g.fillRect(650, 250, 75, 100);

        g.setColor(Color.BLACK);
        g.drawString("Generation: " + generationNumber, 60, 30);

        drawPlayers(g);
    }

    private void drawPlayers(Graphics g) {
        if (players == null) {
            return;
        }

        for (Player p : players) {
            p.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updatePlayers();
        repaint();
      
    }
    public double fitnessFunction(int x, int y) {
    int goalX = 687; // center of goal
    int goalY = 300;

    double dist = Math.sqrt(
        (x - goalX) * (x - goalX) +
        (y - goalY) * (y - goalY)
    );
    /*
    When squaring the distance, closer distances are very very encouraged, while farther distances are very very discouraged
     */
    return 1.0 / (1.0 + dist*dist); 
}
    public int binarySearchLeft(int[] arr, int value) {
        int idx = Arrays.binarySearch(arr, value);
        return idx >= 0 ? idx : -idx - 1;
    }// I vibed this idk the complete logic but it's basically binary search 

    public int binarySearchLeft(List<Double> arr, double value) {
        int low = 0;
        int high = arr.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) < value) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public List<List<String>> crossover(List<String> p1, List<String> p2) {
        int steps = Math.min(p1.size(), p2.size());
        int point = new Random().nextInt(steps - 1) + 1; // crossover point in [1, steps-1]

        List<String> c1 = new ArrayList<>();
        List<String> c2 = new ArrayList<>();

        for (int i = 0; i < point; i++) {
            c1.add(p1.get(i));
            c2.add(p2.get(i));
        }
        for (int i = point; i < steps; i++) {
            c1.add(p2.get(i));
            c2.add(p1.get(i));
        }

        return List.of(c1, c2);
    }

    public List<String> mutate(List<String> genome, double mutationRate) {
        List<String> mutated = new ArrayList<>(genome);
        Random rand = new Random();

        for (int i = 0; i < mutated.size(); i++) {
            if (rand.nextDouble() < mutationRate) {
                String newGene = CONTROL_OPTIONS.get(rand.nextInt(CONTROL_OPTIONS.size()));
                mutated.set(i, newGene);
            }
        }

        return mutated;
    }

    public int getCurrentGeneration(){ 
        return generationNumber;
    }
    

    public void restartGeneration(){ 
        generationTick = 0;
        generationNumber++;
        /* 
        There are a couple of things we need to do during this eugenic esc stage 
        1. call fitness functions on all of them (just x and y positions)
        2. Selection process (random wheel selection process)

        Every 5 generations, I will add 10 extra steps for the players 
        */  
       if (generationNumber%15 == 0)STEPS = Math.min(STEPS + 7, 270);
       System.out.println("Initializing Generation " + generationNumber);
       System.out.println("Number of Steps: " + STEPS);
       List<Double> fitness = new ArrayList<>(); 
       Random r = new Random();
       for (Player p: players){ 
        fitness.add(fitnessFunction(p.getX(), p.getY()));

       }
       // Elitism at its finest moments
       List<Integer> eliteIndices = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            eliteIndices.add(i);
        }
        eliteIndices.sort((a,b) -> Double.compare(fitness.get(b), fitness.get(a)));
        List<List<String>> elites = new ArrayList<>();

        for (int i = 0; i < Math.min(ELITES, players.size()); i++) {

            int idx = eliteIndices.get(i);
            elites.add(
                new ArrayList<>(players.get(idx).addControls(STEPS  - players.get(i).getSized()))
            );
        }

        double bestFitness = fitness.get(eliteIndices.get(0));

        System.out.println(
            "Generation " + generationNumber +
            " Best Fitness = " + bestFitness
        );
        

       // random wheel selection
       System.out.println("Roulette Wheel Selection Processs");
       List<Double> P = new ArrayList<>();
       List<List<String>> selected = new ArrayList<>();
        
       double sum = 0;
       double prev = 0;
       for (int i = 0; i < fitness.size(); i++){
           sum += fitness.get(i);
       }
       for (int i = 0; i < fitness.size(); i++){
           P.add(fitness.get(i) / sum + prev);
           prev = P.get(i);
       }
       for (int i = 0; i < fitness.size(); i++){
           double target = r.nextDouble();
           int index = binarySearchLeft(P, target);
           if (index >= players.size()) {
               index = players.size() - 1;
           }
           selected.add(players.get(index).addControls(STEPS - players.get(index).getSized()));
       }
       
       //once you have found them, create the offspring
       System.out.println("Starting Reproduction");
       List<List<String>> offspring = new ArrayList<>(elites);
       // no elitism at first 
       while (offspring.size() < POPULATION_SIZE){
            int p1 = r.nextInt(selected.size());
            int p2 = r.nextInt(selected.size());
           List<List<String>> children;
           if (r.nextFloat() < CROSSOVER_PROBABILITY){
                
               children = crossover(selected.get(p1), selected.get(p2));
               
           } else {
               children = List.of(
                   new ArrayList<>(selected.get(p1)),
                   new ArrayList<>(selected.get(p2))
               );
           }
           
           offspring.add(mutate(children.get(0), MUTATION_RATE));
           if (offspring.size() < POPULATION_SIZE){ 
            offspring.add(mutate(children.get(1), MUTATION_RATE));
           }
       }
       // now that we have offspring, we can continue the cycle
       int limit = Math.min(players.size(), offspring.size());
       for(int j = 0; j < limit; j++){
            players.get(j).setControls(offspring.get(j)); //give 'em new instructions
            players.get(j).resetMoves();
            players.get(j).setPosition(100,100);

       }


    }

    private void updatePlayers() {
        if (players == null || players.isEmpty() || generationTick >= getGenerationLength()) {
            // Please restart the generation, instead of just returning
            restartGeneration();
            return;
        }

        for (Player p : players) {
            p.executeNextMove();
            p.keepInBounds(50, 50, 750, 550);
        }
        generationTick++;

        players.removeIf(p -> {
            boolean reached = foundGoal(p);
            if (reached) {
                System.out.println("Solution found in:"); 
                System.out.println("Genome: " + p.getControls());
                System.out.println("Iterations: " +  generationNumber);
                
                timer.stop();
                System.out.println("Simulation Terminated");
                System.exit(0);


            }
            return reached; 
        });
    }

    
    private int getGenerationLength() {
        return players == null || players.isEmpty() ? 0 : players.get(0).getControls().size();
    }

    private boolean foundGoal(Player player) {
        if (player == null) {
            return false;
        }

        Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
        Rectangle goalRect = new Rectangle(650, 250, 75, 100);

        return playerRect.intersects(goalRect);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                // optional behavior when pressing up
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                // optional behavior when pressing down
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                // optional behavior when pressing left
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                // optional behavior when pressing right
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // optional behavior when a key is released
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    public static void main(String[] args) {

        JFrame frame = new JFrame("World's Genetic Algorithm");
        List<String> options = List.of("up", "down", "left", "right");
    
        int STEPS = 7;
        int POPULATION_SIZE = 100;
        float CROSSOVER_PROBABILITY = 0.5f;
        double MUTATION_RATE = 0.075;
        int ELITISM = 5;
        int SIZE = 25;
        int SPEED = 8;

        List<Player> population = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Player(100,100,SIZE,SPEED,STEPS,options));
        }
        // Multiple players, each player in the list will have it's own list of instructions 
        frame.add(new WorldsHardestGame(population,  CROSSOVER_PROBABILITY, MUTATION_RATE, POPULATION_SIZE, STEPS, ELITISM));
       
        
        
        

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        
    }
}