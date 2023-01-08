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
    private static final int DELAY = 5;

    private int[] array;
    private JPanel panel;
    private JComboBox<String> algorithm_selector;
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
    
        algorithm_selector = new JComboBox<>(new String[] {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"});
        algorithm_selector.setSize(200, 50);
        algorithm_selector.addActionListener(e -> {
            is_sorted = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String selected_algorithm = (String)algorithm_selector.getSelectedItem();
                    if (selected_algorithm.equals("Bubble Sort")) {
                        bubble_sort();
                    } else if (selected_algorithm.equals("Selection Sort")) {
                        selection_sort();
                    } else if (selected_algorithm.equals("Insertion Sort")) {
                        insertion_sort();
                    } else if (selected_algorithm.equals("Merge Sort")) {
                        merge_sort(0, get_number_of_bars() - 1);
                    } else if (selected_algorithm.equals("Quick Sort")) {
                        quick_sort(0, get_number_of_bars() - 1);
                    }
                }
            }).start();
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
        JPanel right_panel = new JPanel();
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        right_panel.add(algorithm_selector);
        right_panel.add(slider);
        add(panel, BorderLayout.CENTER);
        add(right_panel, BorderLayout.EAST);
    
        setTitle("Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
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
                    is_sorted = false;
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    panel.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    private void selection_sort() {
        while (!is_sorted) {
            is_sorted = true;
            for (int i = 0; i < get_number_of_bars() - 1; i++) {
                int min_index = i;
                for (int j = i + 1; j < get_number_of_bars(); j++) {
                    if (array[j] < array[min_index]) {
                        min_index = j;
                    }
                }
                if (min_index != i) {
                    int temp = array[i];
                    array[i] = array[min_index];
                    array[min_index] = temp;
                    panel.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void insertion_sort() {
        while (!is_sorted) {
            is_sorted = true;
            for (int i = 1; i < get_number_of_bars(); i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
                panel.repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void merge_sort(int left, int right) {
        while (!is_sorted) {
            is_sorted = true;
            for (int i = 0; i < get_number_of_bars() - 1; i++) {
                if (array[i] > array[i + 1]) {
                    is_sorted = false;
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    panel.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void quick_sort(int left, int right) {
        while (!is_sorted) {
            is_sorted = true;
            for (int i = 0; i < get_number_of_bars() - 1; i++) {
                if (array[i] > array[i + 1]) {
                    is_sorted = false;
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    panel.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new SortingVisualizer();
    }
}

