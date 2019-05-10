package me.obadiahpcrowe.config.examples;

import me.obadiahpcrowe.config.LoadableConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleConfig extends LoadableConfig<ExampleConfig> {

    private static final ExampleConfig DEFAULT_CONFIG = new ExampleConfig("Example", 0.0D, new ArrayList<>());

    private String exampleText;
    private double someDouble;
    private List<Boolean> booleans;

    /**
     * Represents a configuration file.
     */
    public ExampleConfig() {
        super(ExampleConfig.class, System.out);
    }

    /**
     * Represents a configuration file.
     *
     * @param exampleText Example.
     * @param someDouble Some double.
     * @param booleans A list of booleans.
     */
    public ExampleConfig(String exampleText, double someDouble, List<Boolean> booleans) {
        super(ExampleConfig.class, System.out);

        this.exampleText = exampleText;
        this.someDouble = someDouble;
        this.booleans = booleans;
    }

    public String getExampleText() {
        return this.exampleText;
    }

    public double getSomeDouble() {
        return this.someDouble;
    }

    public List<Boolean> getBooleans() {
        return Collections.unmodifiableList(this.booleans);
    }

    @Override
    public Path getPath() {
        return Paths.get("");
    }

    @Override
    public ExampleConfig getDefaultConfig() {
        return DEFAULT_CONFIG;
    }
}
