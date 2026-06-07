import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

    private int x;
    private int y;
    private int size;
    private int speed;
    private int steps;
    private int controls;
    private int currentStep;
    private List<String> options;
    private List<String> moves;
    private boolean status = true;
    
    public List<String> generateRandomControls(int length){ 
        Random rand = new Random();
        List<String> otherControls = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            otherControls.add(options.get(rand.nextInt(options.size())));
        }
        return otherControls;

    }

    public Player(int x, int y, int size, int speed, int steps, List<String> options) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.steps = steps;
        this.controls = options.size();
        this.options = options;
        this.currentStep = 0;
        this.moves = new ArrayList<>();
        moves.addAll(generateRandomControls(steps));
    }
    

    public int executeNextMove() {
        if (currentStep >= moves.size()) {
            
            return 1;
        }

        String nextMove = moves.get(currentStep);
        currentStep++;

        switch (nextMove) {
            case "up" -> moveUp();
            case "down" -> moveDown();
            case "left" -> moveLeft();
            case "right" -> moveRight();
            default -> {
            }
        }
        return 0;
    }

    public boolean hasFinishedMoves() {
        return currentStep >= moves.size();
    }

    public void resetMoves() {
        currentStep = 0;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }
    

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x-5, y-5, size +10, size +10);
        g.setColor(Color.RED);
        g.fillRect(x, y, size, size);
        
    }

    public void keepInBounds(int minX, int minY, int maxX, int maxY) {
        if (x < minX) x = minX;
        if (y < minY) y = minY;

        if (x > maxX - size) x = maxX - size;
        if (y > maxY - size) y = maxY - size;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
    public int getSized() {
        return steps;
    }
    public List<String> addControls(int numberToAdd){ 
        moves.addAll(generateRandomControls(numberToAdd));
        steps +=numberToAdd;
        return moves;
    }

    public List<String> getControls(){ 
        return moves;
    }
    public void setControls(List<String> newControls){ 
        moves = newControls;
    }
    // Setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}