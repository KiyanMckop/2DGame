package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //screen Settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 1920/tileSize;
    public final int maxScreenRow = 1080/tileSize;
    public final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    public final  int screenHeight = tileSize * maxScreenRow;   //576 pixels

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler);

    TileManager baseTiles = new TileManager(this);
    TileManager tileManager = new TileManager(this);

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public AssetSetter assetSetter = new AssetSetter(this);
    public SuperObject[] obj = new SuperObject[10];

    public UI ui = new UI(this);

    Sound music = new Sound();
    Sound soundEffect = new Sound();

    Thread gameThread;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();

        playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 /FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void update(){

        player.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        baseTiles.loadMap("/res/maps/baseMap.txt");
        baseTiles.draw(g2);

        tileManager.loadMap("/res/maps/worldMap.txt");
        tileManager.draw(g2);
        player.draw(g2);
        for (int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
