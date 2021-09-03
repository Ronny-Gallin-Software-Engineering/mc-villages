package de.rgse.mc.villages.extensions;

import java.util.Map;
import java.util.Optional;

public interface AbstractBlockStateExtension {

    void setProperties(Map<String, Object> source);

    void setProperty(String name, Object value);

    Optional<Object> getProperty(String name);
}
