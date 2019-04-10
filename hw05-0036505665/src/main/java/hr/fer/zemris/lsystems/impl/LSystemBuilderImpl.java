package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;

public class LSystemBuilderImpl implements LSystemBuilder {

    private Dictionary<Character, Command> commands = new Dictionary<>();
    private Dictionary<Character, String> productions = new Dictionary<>();

    /**
     * The length of a unit turtle move.
     */
    private double unitLength = 0.1;

    /**
     * A scaler for the turtle's unit length that is required in order to keep the
     * fractal drawing dimensions constant.
     */
    private double unitLengthDegreeScaler = 1.0;

    /**
     * The starting point from which the turtle moves.
     */
    private Vector2D origin = new Vector2D(0.0, 0.0);

    /**
     * The angle between the turtle's direction and the positive part of the x-axis.
     */
    private double angle = 0.0;

    /**
     * The initial string of this system.
     */
    private String axiom = "";

    @Override
    public LSystemBuilder setUnitLength(double unitLength) {
        this.unitLength = unitLength;

        return this;
    }

    @Override
    public LSystemBuilder setOrigin(double x, double y) {
        if (x < 0 || x > 1 || y < 0 || y > 1) { // TODO ?
            throw new IllegalArgumentException("Origin coordinates are not in range [0, 1]");
        }
        this.origin = new Vector2D(x, y);

        return this;
    }

    @Override
    public LSystemBuilder setAngle(double angle) {
        this.angle = angle;

        return this;
    }

    @Override
    public LSystemBuilder setAxiom(String axiom) {
        this.axiom = axiom;

        return this;
    }

    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
        this.unitLengthDegreeScaler = unitLengthDegreeScaler;

        return this;
    }

    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        if (productions.get(c) == null) {
            productions.put(c, s);
        }

        return this;
    }

    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        if (commands.get(c) == null) {    // TODO throw exc if entry for c already exists?
            commands.put(c, parseToCommand(s));
        }

        return this;
    }

    @Override
    public LSystemBuilder configureFromText(String[] strings) {
        for (String line : strings) {
            if (line.isEmpty()) {
                continue;
            }

            try {
                parseTextLine(line);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return this;
    }

    @Override
    public LSystem build() {
        return new LSystemImpl();
    }

    private class LSystemImpl implements LSystem {

        @Override
        public String generate(int depth) {
            if (depth < 0) {
                throw new IllegalArgumentException("Depth cannot be negative.");
            }

            String newAxiom = axiom;
            for (int i = 1; i <= depth; i++) {
                StringBuilder sb = new StringBuilder();

                char[] currentChars = newAxiom.toCharArray();
                for (char c : currentChars) {
                    String replacement = productions.get(c);

                    if (replacement != null) {
                        sb.append(replacement);
                    } else {
                        sb.append(c);
                    }
                }

                newAxiom = sb.toString();
            }

            return newAxiom;
        }

        @Override
        public void draw(int depth, Painter painter) {
            Context ctx = new Context();

            TurtleState state = new TurtleState(
                    origin,
                    new Vector2D(1.0, 0.0), // TODO wat
                    Color.BLACK,
                    unitLength * (Math.pow(unitLengthDegreeScaler, depth))
            );
            ctx.pushState(state);

            char[] axiomChars = generate(depth).toCharArray();
            for (char character : axiomChars) {
                Command command = commands.get(character);

                if (command != null) {
                    command.execute(ctx, painter);
                }
            }
        }
    }

    private void checkIfNumberOfArgumnetsIs(int number, String[] parts) {
        if (parts.length != number) {
            throw new IllegalArgumentException(
                    "Invalid number of directive arguments.");
        }
    }

    private void parseTextLine(String line) {
        String[] parts = line.split("\\s+", 2);

        checkIfNumberOfArgumnetsIs(2, parts);

        switch (parts[0]) {
            case "origin":
                String[] coordinates = parts[1].split("\\s+");
                checkIfNumberOfArgumnetsIs(2, coordinates);

                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                setOrigin(x, y);
                break;

            case "angle":
                double angle = Double.parseDouble(parts[1]);
                setAngle(Math.toRadians(angle));
                break;

            case "unitLength":
                double unitLength = Double.parseDouble(parts[1]);
                setUnitLength(unitLength);
                break;

            case "unitLengthDegreeScaler":
                double scaler = parseScaler(parts[1]);
                setUnitLengthDegreeScaler(scaler);
                break;

            case "command":
                String[] commandParts = parts[1].split("\\s+", 2);
                checkIfNumberOfArgumnetsIs(2, commandParts);

                if (commandParts[0].length() != 1) {
                    throw new IllegalArgumentException(
                            "Illegal command character \"" + commandParts[1] + "\".");
                }

                registerCommand(commandParts[0].charAt(0), commandParts[1]);
                break;

            case "axiom":
                setAxiom(parts[1]);
                break;

            case "production":
                String[] productionParts = parts[1].split("\\s+", 2);
                checkIfNumberOfArgumnetsIs(2, productionParts);

                if (productionParts[0].length() != 1) {
                    throw new IllegalArgumentException(
                            "Illegal production character \"" + productionParts[0] + "\".");
                }

                registerProduction(productionParts[0].charAt(0), productionParts[1]);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown directive \"" + parts[0] + "\"");
        }
    }

    private Command parseToCommand(String commandString) {
        String[] parts = commandString.split("\\s+");

        String commandName = parts[0];
        switch (commandName) {
            case "draw":
                checkIfNumberOfArgumnetsIs(2, parts);
                double drawStep = Double.parseDouble(parts[1]);
                return new DrawCommand(drawStep);

            case "skip":
                checkIfNumberOfArgumnetsIs(2, parts);
                double skipStep = Double.parseDouble(parts[1]);
                return new SkipCommand(skipStep);

            case "scale":
                checkIfNumberOfArgumnetsIs(2, parts);
                double factor = Double.parseDouble(parts[1]);
                return new ScaleCommand(factor);

            case "rotate":
                checkIfNumberOfArgumnetsIs(2, parts);
                double degrees = Double.parseDouble(parts[1]);
                double radians = Math.toRadians(degrees);
                return new RotateCommand(radians);

            case "push":
                checkIfNumberOfArgumnetsIs(1, parts);
                return new PushCommand();

            case "pop":
                checkIfNumberOfArgumnetsIs(1, parts);
                return new PushCommand();

            case "color":
                checkIfNumberOfArgumnetsIs(2, parts);
                return new ColorCommand(Color.getColor(parts[1]));

            default:
                throw new IllegalArgumentException(
                        "Invalid command name: \"" + commandName + "\"");
        }
    }

    private double parseScaler(String scalerString) {
        String[] parts = scalerString.split("\\s*\\/\\s*");

        if (parts.length == 1) {
            return Double.parseDouble(parts[0]);
        } else if (parts.length == 2) { // TODO && scalerString.contains("/") ?
            return Double.parseDouble(parts[0])
                    / Double.parseDouble(parts[1]);
        } else {
            throw new IllegalArgumentException(
                    "Invalid scaler: \"" + scalerString + "\".");
        }
    }
}
