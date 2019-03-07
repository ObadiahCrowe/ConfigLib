package me.obadiahpcrowe.config;

import java.lang.reflect.InvocationTargetException;

public final class ConfigHandler {

    /**
     * Gets a loadable config via its class.
     *
     * @param clazz Class to load from.
     *
     * @return Loaded, generified, config.
     */
    public static LoadableConfig<?> getByClass(Class<? extends LoadableConfig> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error loading config, " + clazz.getSimpleName() + ": " + e);
        }
    }
}
