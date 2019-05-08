package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class implements the {@link IFractalProducer} and can be used to generate the
 * data for fractal visualisation on a specified complex plane.
 *
 * @author Bruna Dujmović
 *
 */
public class NewtonProducer implements IFractalProducer {

    /**
     * The maximum number of Newton-Raphson iterations for this producer.
     */
    private static final int MAX_ITERATIONS = 16 * 16 * 16;

    /**
     * The thread pool used for running {@link NewtonJob}s.
     */
    private ExecutorService pool;

    /**
     * The {@link ComplexPolynomial} form of the given polynomial.
     */
    private ComplexPolynomial polynomial;
    /**
     * The {@link ComplexRootedPolynomial} form of the given polynomial.
     */
    private ComplexRootedPolynomial rootedPolynomial;

    /**
     * Constructs a {@link NewtonProducer} for the given rooted polynomial.
     *
     * @param rootedPolynomial the rooted polynomial
     */
    public NewtonProducer(ComplexRootedPolynomial rootedPolynomial) {
        this.polynomial = rootedPolynomial.toComplexPolynom();
        this.rootedPolynomial = rootedPolynomial;

        this.pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                new DaemonicThreadFactory()
        );
    }

    @Override
    public void produce(double reMin, double reMax, double imMin, double imMax,
                        int width, int height, long requestNo,
                        IFractalResultObserver observer, AtomicBoolean cancel) {
        System.out.println("Zapocinjem izracun...");
        short[] data = new short[width * height];
        int ySections = 8 * Runtime.getRuntime().availableProcessors();
        int yPerSection = height / ySections;

        List<Future<Void>> results = new ArrayList<>();

        for (int i = 0; i < ySections; i++) {
            int yMin = i * yPerSection;

            int yMax = (i + 1) * yPerSection - 1;
            if (i == ySections - 1) {
                yMax = height - 1;
            }

            NewtonJob job = new NewtonJob(
                    reMin, reMax, imMin, imMax, width, height, yMin, yMax,
                    MAX_ITERATIONS, data, polynomial, rootedPolynomial, cancel
            );
            results.add(pool.submit(job));
        }

        for (Future<Void> posao : results) {
            try {
                posao.get();
            } catch (InterruptedException | ExecutionException e) {}
        }

        pool.shutdown();

        System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
        observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
    }
}
