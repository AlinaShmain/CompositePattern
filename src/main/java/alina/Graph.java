package alina;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Graph {

    private static Graph instance;
    private DefaultDirectedGraph<Integer, DefaultEdge> g;

    private Graph(){}

    public static Graph getInstance(){
        if(instance == null){
            instance = new Graph();
        }
        return instance;
    }

    public void init(){
        g = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
    }

    public void addV(Integer v){
        g.addVertex(v);
    }

    public void addE(Integer v1, Integer v2){
        g.addEdge(v1, v2);
    }

    public void save() throws IOException {

        JGraphXAdapter<Integer, DefaultEdge> graphAdapter =
                new JGraphXAdapter<Integer, DefaultEdge>(g);

        mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("C:/Users/Alina/IdeaProjects/composite/src/main/java/alina/graph.png");
        ImageIO.write(image, "PNG", imgFile);
    }
}
