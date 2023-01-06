import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class SortingVisualizer extends JFrame {
    private static final int WIDTH = 1200;
    private static final int CANVAS_WIDTH = 1000;
    private static final int HEIGHT = 900;
    private static final int MIN_BARS = 10;
    private static final int MAX_BARS = 200;
    private static final int INITIAL_BARS = 120;
    private static final int MIN_BAR_LENGTH = HEIGHT / 10;
    private static final int MAX_BAR_LENGTH = HEIGHT - 100;
    private static final int DELAY = 1;

    private int[] array;
    private JPanel panel;
    private JButton start_button;
    private JSlider slider;
    private boolean is_sorted;

    public SortingVisualizer() {
        array = new int[INITIAL_BARS];
        for (int i = 0; i < INITIAL_BARS; i++) {
            array[i] = MIN_BAR_LENGTH + (int)(Math.random() * (MAX_BAR_LENGTH - MIN_BAR_LENGTH + 1));
        }

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = CANVAS_WIDTH / get_number_of_bars();
                for (int i = 0; i < get_number_of_bars(); i++) {
                    int x = i * width;
                    int y = getHeight() - array[i];
                    int height = array[i];
                    g.setColor(new Color(72, 142, 199));
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
                        bubble_sort();
                    }
                }).start();
            }
        });

        slider = new JSlider(JSlider.HORIZONTAL, MIN_BARS, MAX_BARS, INITIAL_BARS);
        slider.addChangeListener(e -> {
            array = new int[get_number_of_bars()];
            for (int i = 0; i < get_number_of_bars(); i++) {
                array[i] = MIN_BAR_LENGTH + (int)(Math.random() * (MAX_BAR_LENGTH - MIN_BAR_LENGTH + 1));
            }
            is_sorted = false;
            panel.repaint();
        });

        setLayout(new BorderLayout());
        add(slider, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(start_button, BorderLayout.SOUTH);

        setTitle("Bubble Sort Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private int get_number_of_bars() {
        return slider.getValue();
    }

    private void bubble_sort() {
        while (!is_sorted) {
            is_sorted = true;
            for (int i = 0; i < get_number_of_bars() - 1; i++) {
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
        new SortingVisualizer();
    }
}