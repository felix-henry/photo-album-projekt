package de.commitmentissues.photoalbum.application;

/**
 * Der {@link ImageHasher} lehnt eine Datei mit Grund ab (ADR-002).
 */
public class ImageRejectedException extends RuntimeException {

    private final ImageProblem problem;

    public ImageRejectedException(ImageProblem problem) {
        super(problem.name());
        this.problem = problem;
    }

    public ImageRejectedException(ImageProblem problem, Throwable cause) {
        super(problem.name(), cause);
        this.problem = problem;
    }

    public ImageProblem getProblem() {
        return problem;
    }
}
