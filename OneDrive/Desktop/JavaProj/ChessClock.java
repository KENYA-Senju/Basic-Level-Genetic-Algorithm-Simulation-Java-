
class ChessClock {
    // measured in seconds for simplicity sake first 
    //increment can be considered as 0 for none 
    //delay 
    // format (may or may not be provided) 
    //  This class assumes that p1  + p2 have the same time (for now !!!)
    public int initialSeconds; 
    public int increment; 
    public int delay; 
    public boolean autoStart;


    public int whiteTime;
    public int blackTime;
    public int turn; 
    public int delayed = 0;


    public boolean timeIsRunning;


    /* 
    ChessClock
    ------------------------
    + start()
    + pause()
    + resume()
    + switchPlayer()
    + getWhiteTime()
    + getBlackTime()
    + isRunning()
    + isGameOver()    
    */
    
    public ChessClock(int initialSeconds){ 
        this.initialSeconds = initialSeconds;
        this.increment = 0; 
        this.delay = 0; 
        this.autoStart = false;

    }
    public void setIncrement(int i){ 
        increment = i;
    }
    public void setDelay(int d){ 
        delay = d;
    }
    public void setAutoStart(boolean a){ 
        autoStart = a;
    }
    public void start(){ 
        // set the dual_timer_device
        blackTime= initialSeconds;
        whiteTime = initialSeconds;

        // set turn to white (0 ) 
        turn = 0;

        //release "timeIsRunning" 
        timeIsRunning = true; 
    }
    public boolean isGameOver(){ 
        return Math.min(blackTime, whiteTime) <= 0; 
    }
    public boolean isRunning(){ 
        return timeIsRunning;

    }
    public int getWhiteTime(){return whiteTime;}
    public int getBlackTime(){return blackTime;}
    public void pause(){ 
        //just stop the time for heavens sake! 
        timeIsRunning = false; 
    }
    public void resume(){ 
        // inverse
        timeIsRunning = true; 
    }

    public void switchPlayer(){ 
        if(turn ==0){ 
            //adjust white digital clock 
            //freeze (int) delay seconds (idk how to do this)
            whiteTime += increment;
        }else{ 
            blackTime+= increment;
        }
        turn = (turn + 1) %2 ; 
        delayed = 0;
        
        
    }
    public void tick(){ 
        // tick, is a second of time on the dual clock system. Any delays, pauses, or other conflicts are dealt with here before adjusting either clock
        //1st, don't adjust if the timer was paused 
        if(isRunning()){
            // 2nd, a delayed timer will keep track of delayed time if applicable during that tick
            delayed ++;
            if (delayed >  delay){
          
                if (turn == 0){whiteTime -= 1;}
                else{blackTime -= 1;}
            }
            
            
       
            
        }
    }




}
