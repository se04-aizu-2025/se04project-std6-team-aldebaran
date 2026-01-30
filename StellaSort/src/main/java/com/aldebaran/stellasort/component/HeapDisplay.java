package com.aldebaran.stellasort.component;

import com.aldebaran.stellasort.service.HeapSort;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class HeapDisplay {
    public static class HeapNode {
        Circle circle;
        Text value;
        Group group;

        public HeapNode(Circle circle, Text value, Group group) {
            this.circle = circle;
            this.value = value;
            this.group = group;
        }
    }

    private final double PADDING = 20;
    private final double VERTICAL_SPACING = 60;
    private final double NODE_RADIUS = 15;

    private final Pane heapPane;

    public HeapDisplay(Pane heapPane) {
        this.heapPane = heapPane;
        heapPane.getChildren().addAll(edgesGroup, nodesGroup);
    }

    //#region Heap State
    int[] heap;

    List<HeapNode> nodes = new ArrayList<>();
    HashMap<Integer, Line> edges = new HashMap<>();

    int lastVisibleIndex = 0;
    //#endregion

    //#region Groups
    Group nodesGroup = new Group();
    Group edgesGroup = new Group();
    //#endregion

    //#region Position
    Point2D computeHeapNodePosition(
            int index,
            double paneWidth,
            double topPadding,
            double verticalSpacing
    ) {
        int level = HeapSort.level(index);
        int levelStart = HeapSort.levelStart(level);
        int indexInLevel = HeapSort.indexInLevel(index);

        int nodesInLevel = 1 << level;
        double gap = paneWidth / (nodesInLevel + 1);

        double x = gap * (indexInLevel + 1);
        double y = topPadding + level * verticalSpacing;

        return new Point2D(x, y);
    }
    //#endregion

    public void renderHeap(int[] heap) {
        this.heap = heap;
        lastVisibleIndex = heap.length - 1;

        // Clear previous state
        nodes.clear();
        edges.clear();
        nodesGroup.getChildren().clear();
        edgesGroup.getChildren().clear();

        // Create nodes
        for (int i = 0; i < heap.length; i++) {
            // Create node
            HeapNode node = new HeapNode(new Circle(NODE_RADIUS), new Text(Integer.toString(heap[i])), new Group());
            node.group.getChildren().addAll(node.circle, node.value);

            // Set position
            Point2D position = computeHeapNodePosition(i, heapPane.getPrefWidth(), PADDING, VERTICAL_SPACING);
            node.group.setLayoutX(position.getX() - NODE_RADIUS);
            node.group.setLayoutY(position.getY() - NODE_RADIUS);
            node.group.setTranslateX(0);
            node.circle.setTranslateY(0);

            // Set the color of circle
            node.circle.setFill(Color.LIGHTBLUE);
            node.circle.setStroke(Color.DARKBLUE);
            node.circle.setStrokeWidth(2);
            node.value.setFill(Color.BLACK);
            node.value.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            // Set the position of value text
            node.value.setTranslateX(-node.value.getLayoutBounds().getWidth() / 2);
            node.value.setTranslateY(node.value.getLayoutBounds().getHeight() / 4);

            // Add to the lists of state
            nodes.add(node);
            nodesGroup.getChildren().add(node.group);
        }

        // Create edges
        for (int i = 1; i < heap.length; i++) {
            int parent = HeapSort.parent(i);

            // Create edge
            Line edge = new Line();

//            Point2D childPosition = computeHeapNodePosition(i, heapPane.getPrefWidth(), PADDING, VERTICAL_SPACING);
//            Point2D parentPosition = computeHeapNodePosition(parent, heapPane.getPrefWidth(), PADDING, VERTICAL_SPACING);
//
//            edge.setStartX(childPosition.getX() - NODE_RADIUS);
//            edge.setStartY(childPosition.getY() - NODE_RADIUS);
//            edge.setEndX(parentPosition.getX() - NODE_RADIUS);
//            edge.setEndY(parentPosition.getY() - NODE_RADIUS);

            edge.startXProperty().bind(nodes.get(i).group.layoutXProperty()
                    .add(nodes.get(i).group.translateXProperty()));
            edge.startYProperty().bind(nodes.get(i).group.layoutYProperty()
                    .add(nodes.get(i).group.translateYProperty()));
            edge.endXProperty().bind(nodes.get(parent).group.layoutXProperty()
                    .add(nodes.get(parent).group.translateXProperty()));
            edge.endYProperty().bind(nodes.get(parent).group.layoutYProperty()
                    .add(nodes.get(parent).group.translateYProperty()));

            // Add to the lists of state
            edges.put(i, edge);
            edgesGroup.getChildren().add(edge);
        }
    }

    public void setNodesVisible(Function<Integer, Boolean> predicate) {
        for (int i = 0; i < heap.length; i++) {
            nodes.get(i).circle.setVisible(predicate.apply(i));
            edges.get(i).setVisible(predicate.apply(i));
        }
    }

    public Animation swapNodesAnimation(int i, int j) {
        HeapNode nodeA = nodes.get(i);
        HeapNode nodeB = nodes.get(j);

        // Current layout positions
        double startAX = nodeA.group.getLayoutX();
        double startAY = nodeA.group.getLayoutY();
        double startBX = nodeB.group.getLayoutX();
        double startBY = nodeB.group.getLayoutY();

        // Timeline for smooth transition
        Timeline timeline = new Timeline();

        // Initial positions
        Point2D positionA = computeHeapNodePosition(i, heapPane.getPrefWidth(), PADDING, VERTICAL_SPACING);
        Point2D positionB = computeHeapNodePosition(j, heapPane.getPrefWidth(), PADDING, VERTICAL_SPACING);

        // Move nodeA to nodeB's position
        KeyValue kvAX = new KeyValue(nodeA.group.layoutXProperty(), startBX);
        KeyValue kvAY = new KeyValue(nodeA.group.layoutYProperty(), startBY);

        // Move nodeB to nodeA's position
        KeyValue kvBX = new KeyValue(nodeB.group.layoutXProperty(), startAX);
        KeyValue kvBY = new KeyValue(nodeB.group.layoutYProperty(), startAY);

        KeyFrame highlightNodes = new KeyFrame(Duration.millis(200), actionEvent -> {
            nodeA.circle.setFill(Color.RED);
            nodeB.circle.setFill(Color.RED);
        });
        KeyFrame swapPosition = new KeyFrame(Duration.millis(500), kvAX, kvAY, kvBX, kvBY);

        KeyFrame resetAndSwapValues = new KeyFrame(Duration.millis(700), actionEvent -> {
            // Reset to initial positions
            nodeA.group.setLayoutX(positionA.getX() - NODE_RADIUS);
            nodeA.group.setLayoutY(positionA.getY() - NODE_RADIUS);
            nodeB.group.setLayoutX(positionB.getX() - NODE_RADIUS);
            nodeB.group.setLayoutY(positionB.getY() - NODE_RADIUS);

            // Swap values
            String textA = nodeA.value.getText();
            String textB = nodeB.value.getText();

            nodeA.value.setText(textB);
            nodeB.value.setText(textA);

            nodeA.circle.setFill(Color.LIGHTBLUE);
            nodeB.circle.setFill(Color.LIGHTBLUE);
        });

        timeline.getKeyFrames().add(highlightNodes);
        timeline.getKeyFrames().add(swapPosition);
        timeline.getKeyFrames().add(resetAndSwapValues);

        return timeline;
    }

    public Animation removeFinalNodeAnimations(int index) {
        Timeline timeline = new Timeline();
        HeapNode lastNode = nodes.get(index);
        //FadeTransition fadeCircleTransition = new FadeTransition(Duration.millis(500), lastNode.circle);
        //FadeTransition fadeEdgeTransition = new FadeTransition(Duration.millis(500), edges.get(index));

        KeyFrame highlightAndDetachNode = new KeyFrame(Duration.millis(200), actionEvent -> {
            lastNode.circle.setFill(Color.LIGHTGRAY);
            edges.get(index).setVisible(false);
        });
        KeyFrame fadeOutNode = new KeyFrame(Duration.millis(500), actionEvent -> {
            lastNode.group.setVisible(false);
            edges.get(index).setVisible(false);
        });

        timeline.getKeyFrames().add(highlightAndDetachNode);
        timeline.getKeyFrames().add(fadeOutNode);
        return timeline;
    }
}
