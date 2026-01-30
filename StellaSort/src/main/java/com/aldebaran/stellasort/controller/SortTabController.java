package com.aldebaran.stellasort.controller;
import com.aldebaran.stellasort.service.SortAlgorithm;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.chart.BarChart;
import java.util.Arrays;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import com.aldebaran.stellasort.component.ArrayBarChart;
import com.aldebaran.stellasort.animation.ArrayBarChartAnimator;
import com.aldebaran.stellasort.service.SortListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import com.aldebaran.stellasort.service.BubbleSort;
import com.aldebaran.stellasort.service.QuickSort;
import com.aldebaran.stellasort.component.PlayButton;

public class SortTabController {

    private SortAlgorithm algorithm;

	@FXML private TextArea inputArea;
    @FXML private BarChart<String, Number> arrayBarChartNode;
	@FXML private Label statusLabel;
	@FXML private Label throughLabel;
	@FXML private Button sortBtn;


	private PlayButton playButton;
	
	private ArrayBarChart arrayBarChart;
	private ArrayBarChartAnimator animator;
	private SequentialTransition queue = new SequentialTransition();


	
	SortListener listener = new SortListener() {

		@Override
		public void onCompare(int i, int j, int a, int b) {
			Platform.runLater(() -> {
				queue.getChildren().add(animator.changeBarValue(i, a, a));
			});
		}

		@Override
		public void onSwap(int i, int j, int a, int b) {
			Platform.runLater(() -> {
				queue.getChildren().add(animator.changeBarValue(i, a, b));
				queue.getChildren().add(animator.changeBarValue(j, b, a));
			});
		}
		
		public void onFinished() {
			Platform.runLater(() -> {
				queue.setOnFinished(e -> throughLabel.setText("Sort complete."));
				
				queue.play();
				queue.setOnFinished(e -> {
				throughLabel.setText("Sort complete.");
				playButton.togglePlayPause();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < array.length; i++) {
					sb.append(array[i]);
					if (i < array.length - 1) sb.append(", ");
				}
				inputArea.setText(sb.toString());
				});
			});
		}
	};
	
	@FXML
	public void initialize() {
		arrayBarChart = new ArrayBarChart(arrayBarChartNode);
		animator = new ArrayBarChartAnimator(arrayBarChart);
		
		arrayBarChart.initializeBarChart();
		
		playButton = new PlayButton(sortBtn);
	}
	
	private int[] array;
	
    public void setAlgorithm(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @FXML
	private void onSort() {
		
		playButton.togglePlayPause();

		if (playButton.isPlaying()) {
			if (!queue.getChildren().isEmpty()) {
				queue.play();
			} else {
				if (array == null || array.length == 0) {
					statusLabel.setText("No array input");
					return;
				}

				queue.getChildren().clear();

				switch (algorithm) {
					case BUBBLE -> runBubble();
					case COUNTING -> runCounting();
					case HEAP -> runHeap();
					case QUICK -> runQuick();
				}
			}
		} else {
			queue.pause();
		}
		
	}
	
	@FXML
	private void onRandomize() {
	
	}
	
	@FXML
	private void onInputArray() {
    try {
        String text = inputArea.getText();
        array = Arrays.stream(text.split("[,\\s]+"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
					  
		statusLabel.setText("");
		throughLabel.setText("Array Primed");
		
		arrayBarChart.setBarChart(Arrays.stream(array).boxed().toList(), ArrayBarChart.NO_RULE);
		
		int max = Arrays.stream(array).max().orElse(1);
		arrayBarChart.setYBounds(0, max);

    } catch (Exception e) {
        statusLabel.setText("Invalid input");
		throughLabel.setText("");
    }
}
	
	private void runBubble() {
		if (array == null || array.length == 0) return;

		queue.stop();
		queue.getChildren().clear();
		throughLabel.setText("Sorting...");
		Service<Void> service = new Service<>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<>() {
					@Override
					protected Void call() {
						BubbleSort.sort(array, listener);
						return null;
					}
				};
			}
		};

		service.setOnSucceeded(e -> {
			queue.play();
		});

		service.start();
		
	}
	
	private void runCounting() {
		
	}
	
	
	private void runHeap() {
		
	}
	
	private void runQuick() {
		if (array == null || array.length == 0) return;

		queue.stop();
		queue.getChildren().clear();
		throughLabel.setText("Sorting...");

		Service<Void> service = new Service<>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<>() {
					@Override
					protected Void call() {
						QuickSort.sort(array, listener);
						return null;
					}
				};
			}
		};

		service.setOnSucceeded(e -> {
			queue.play();
		});

		service.start();
		
		
	}
	
	
}







	