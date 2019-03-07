package me.obadiahpcrowe.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public abstract class LoadableConfig<T> {

    /**
     * The gson instance utilised in loading / saving.
     */
    private static final Gson GSON_INSTANCE = new GsonBuilder().setLenient().setPrettyPrinting().serializeNulls()
      .addSerializationExclusionStrategy(new ExclusionStrategy() {
          @Override
          public boolean shouldSkipField(FieldAttributes fieldAttributes) {
              return fieldAttributes.getName().equalsIgnoreCase("configurationClass") && fieldAttributes.getDeclaringClass() == LoadableConfig.class;
          }

          @Override
          public boolean shouldSkipClass(Class<?> aClass) {
              return false;
          }
      }).create();

    /**
     * Class of the configuration (for serialisation).
     */
    private final Class<? extends LoadableConfig> configurationClass;

    /**
     * Output stream to log to.
     */
    private final PrintStream printStream;

    /**
     * Represents a configuration file.
     *
     * @param clazz Class of the configuration.
     */
    public LoadableConfig(Class<? extends LoadableConfig> clazz, PrintStream printStream) {
        this.configurationClass = clazz;
        this.printStream = printStream;
    }

    /**
     * @return The path that this config should be saved and loaded from.
     */
    public abstract Path getPath();

    /**
     * @return Default config to write if none can be found.
     */
    public abstract T getDefaultConfig();

    /**
     * Loads the configuration from disk and returns its instantiated object.
     *
     * @return The config instance.
     */
    @SuppressWarnings("unchecked")
    public T load() {
        try {
            this.printStream.println("Attempting to load, config, " + this.getClass().getSimpleName() + "..");
            return (T) GSON_INSTANCE.fromJson(String.join("", Files.readAllLines(this.getPath())), this.configurationClass);
        } catch (IOException | ClassCastException e) {
            if (e instanceof NoSuchFileException) {
                this.printStream.println("Could not find, " + this.getPath().toFile().getName() + ", creating one now..");
            } else {
                e.printStackTrace();
            }

            return this.getDefaultConfig();
        }
    }

    /**
     * Saves the config to disk.
     */
    public void save() {
        try {
            this.printStream.println("Saving config, " + this.getClass().getSimpleName() + "..");
            Files.write(this.getPath(), GSON_INSTANCE.toJson(this).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a generified configuration by it's class.
     *
     * @param clazz Class of the configuration.
     *
     * @return Generified configuration.
     */
    public static LoadableConfig<?> getByClass(Class<? extends LoadableConfig> clazz) {
        return ConfigHandler.getByClass(clazz);
    }
}