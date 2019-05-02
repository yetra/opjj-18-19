package coloring.demo;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

import java.util.Arrays;

/**
 * Demonstrates the {@link Coloring} and {@link SubspaceExploreUtil} classes.
 */
public class Bojanje2 {

    /**
     * Main program. Starts the painting GUI.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        FillApp.run(FillApp.OWL, Arrays.asList(bfs, bfsv, dfs));
    }

    /**
     * Creates a bfs-like {@link FillAlgorithm}.
     */
    private static final FillAlgorithm bfs = new FillAlgorithm() {

        @Override
        public String getAlgorithmTitle() {
            return "Moj bfs!";
        }

        @Override
        public void fill(int x, int y, int color, Picture picture) {
            Coloring col = new Coloring(new Pixel(x,y), picture, color);
            SubspaceExploreUtil.bfs(col.s0, col.process, col.succ, col.acceptable);
        }
    };

    /**
     * Creates a dfs-like {@link FillAlgorithm}.
     */
    private static final FillAlgorithm dfs = new FillAlgorithm() {

        @Override
        public String getAlgorithmTitle() {
            return "Moj dfs!";
        }

        @Override
        public void fill(int x, int y, int color, Picture picture) {
            Coloring col = new Coloring(new Pixel(x,y), picture, color);
            SubspaceExploreUtil.dfs(col.s0, col.process, col.succ, col.acceptable);
        }
    };

    /**
     * Creates an optimized bfs-like {@link FillAlgorithm}.
     */
    private static final FillAlgorithm bfsv = new FillAlgorithm() {

        @Override
        public String getAlgorithmTitle() {
            return "Moj bfsv!";
        }

        @Override
        public void fill(int x, int y, int color, Picture picture) {
            Coloring col = new Coloring(new Pixel(x,y), picture, color);
            SubspaceExploreUtil.bfsv(col.s0, col.process, col.succ, col.acceptable);
        }
    };
}
