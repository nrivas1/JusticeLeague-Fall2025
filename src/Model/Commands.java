package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands {

    // ---- Public DTO (record) for each command line ----
    public static record Command(String cmdName, String cmdDescription) {}

    // ---- Cached commands (first two columns only) ----
    private final List<Command> cache;

    // Construct by loading from path once
    public Commands(String path) {
        this.cache = Collections.unmodifiableList(load(path));
    }

    // Accessor for Help/Commands printing
    public List<Command> listAll() {
        return cache;
    }

    // ---- Internal loader ----
    private List<Command> load(String path) {
        // Fallback if no path provided
        if (path == null || path.trim().isEmpty()) {
            return fallback();
        }

        List<Command> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {

            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Optional: skip header if the first row starts with "Command"
                if (!headerSkipped && line.toLowerCase().startsWith("command")) {
                    headerSkipped = true;
                    continue;
                }

                // Expect: Command | Description | (optional third column)
                String[] parts = line.split("\\|");
                if (parts.length < 2) continue;

                String name = parts[0].trim();
                String desc = parts[1].trim();
                if (!name.isEmpty() && !desc.isEmpty()) {
                    list.add(new Command(name, desc));
                }
            }
        } catch (Exception e) {
            System.err.println("[Commands] Failed to read " + path + " : " + e.getMessage());
            return fallback();
        }

        // Optional: sort by name
        list.sort((a, b) -> a.cmdName().compareToIgnoreCase(b.cmdName()));
        return list;
    }

    private List<Command> fallback() {
        List<Command> f = new ArrayList<>();
        f.add(new Command("help", "Show available commands"));
        f.add(new Command("look", "Describe the current room"));
        f.add(new Command("go <dir>", "Move north, south, east, or west"));
        return f;
    }

    public void showCommand(Commands command)
    {
        System.out.println("Available commands:");
        for (Command cmd :  command.listAll())
        {
            System.out.printf( "%-15s — %s%n", cmd.cmdName(), cmd.cmdDescription());
        }

    }
    public String getName() {
        return cache.get(0).cmdName();
    }

    public String getDescription() {
        return cache.get(1).cmdDescription();
    }
}
