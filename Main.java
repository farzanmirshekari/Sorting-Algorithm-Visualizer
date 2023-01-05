import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Main extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;
    private static final int NUM_BARS = 120;
    private static final int MIN_BAR_LENGTH = HEIGHT / 10;
    private static final int MAX_BAR_LENGTH = HEIGHT - 100;
    private static final int DELAY = 1; 

    private int[] array;
    private JPanel panel;
    private JButton start_button;
    private boolean is_sorted;

    public Main() {
        array = new int[NUM_BARS];
        for (int i = 0; i < NUM_BARS; i++) {
            array[i] = MIN_BAR_LENGTH + (int)(Math.random() * (MAX_BAR_LENGTH - MIN_BAR_LENGTH + 1));
        }

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = (int)((double)getWidth() / NUM_BARS);
                for (int i = 0; i < NUM_BARS; i++) {
                    int x = i * width;
                    int y = getHeight() - array[i];
                    int height = array[i];
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                }
            }
        };
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setBackground(new Color(28, 26, 28));

        start_button = new JButton("Start");
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                is_sorted = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bubbleSort();
                    }
                }).start();
            }
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(start_button, BorderLayout.SOUTH);

        setTitle("Bubble Sort Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void bubbleSort() {
        while (!is_sorted) {
            is_sorted = true;
            for (int i = 0; i < NUM_BARS - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    is_sorted = false;
                }
                panel.repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
