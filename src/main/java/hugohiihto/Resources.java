package hugohiihto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Opens a resource by the name stored in the directory.
 * Linux and a case-sensitive Mac refuse a path whose letters do not match the file.
 */
public final class Resources {
    private static final Map<String, File> FOUND = new HashMap<>();

    private Resources() {
    }

    public static File file(String path) {
        File cached = FOUND.get(path);
        if (cached != null) {
            return cached;
        }
        File resolved = resolve(path);
        FOUND.put(path, resolved);
        return resolved;
    }

    private static File resolve(String path) {
        File requested = new File(path);
        File directory = requested.getParentFile() == null ? new File(".") : requested.getParentFile();
        String[] names = directory.list();
        if (names == null) {
            return requested;
        }
        String wanted = requested.getName();
        for (String name : names) {
            if (name.equals(wanted)) {
                return new File(directory, name);
            }
        }
        for (String name : names) {
            if (name.equalsIgnoreCase(wanted)) {
                return new File(directory, name);
            }
        }
        return requested;
    }
}
