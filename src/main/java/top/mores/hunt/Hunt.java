package top.mores.hunt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.mores.hunt.EventListener.AnimalListener;
import top.mores.hunt.Vault.VaultHandle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Hunt extends JavaPlugin {
    private static Hunt instance;
    public FileConfiguration config;
    public FileConfiguration data;
    private File configFile;
    private File dataFile;
    private final Map<String, Integer> entityValueMap = new HashMap<>();
    private final Map<String, String> entityLevelMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        if (!VaultHandle.setupVault()) {
            getLogger().severe("Vault plugin not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        initFiles();
        getServer().getPluginManager().registerEvents(new AnimalListener(), this);
        Objects.requireNonNull(getCommand("hunt")).setExecutor(new HuntCommand());
        loadEntityValues();
        getLogger().info("Hunt enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Hunt disabled");
    }

    public static Hunt getInstance() {
        return instance;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void initFiles() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            boolean isCreateDir = configFile.getParentFile().mkdir();
            if (!isCreateDir) {
                getLogger().warning("mkdir config.yml error");
                return;
            }
            saveResource("config.yml", false);
        }
        reloadConfig();

        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                getLogger().warning("mkdir data.yml error");
            }
        }
        reloadData();
    }

    public FileConfiguration getData() {
        if (data == null) {
            reloadData();
        }
        return data;
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            getLogger().info("save data.yml error");
        }
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    private void loadEntityValues() {
        loadLevel("普通", 100);

        loadLevel("少见", 250);

        loadLevel("珍稀", 500);

        loadLevel("史诗", 800);

        loadLevel("传奇", 1200);

        loadLevel("神话", 1800);
    }

    private void loadLevel(String levelName, int defaultValue) {
        List<String> entities = config.getStringList("动物级别." + levelName + ".生物ID");
        int value = config.getInt("动物级别." + levelName + ".价值", defaultValue);
        for (String entity : entities) {
            entityLevelMap.put(entity, levelName);
            entityValueMap.put(entity, value);
        }
    }

    public Map<String, Integer> getEntityValueMap() {
        return entityValueMap;
    }

    public Map<String, String> getEntityLevelMap() {
        return entityLevelMap;
    }
}
