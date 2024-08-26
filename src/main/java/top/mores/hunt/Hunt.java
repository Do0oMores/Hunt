package top.mores.hunt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.mores.hunt.EventListener.AnimalListener;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Hunt extends JavaPlugin {
    private static Hunt instance;
    public FileConfiguration config;
    public FileConfiguration data;
    private File configFile;
    private File dataFile;

    @Override
    public void onEnable() {
        instance = this;
        initFiles();
        getServer().getPluginManager().registerEvents(new AnimalListener(), this);
        Objects.requireNonNull(getCommand("hunt")).setExecutor(new HuntCommand());
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
}
