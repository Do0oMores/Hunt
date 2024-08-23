package top.mores.hunt.Animals;

import org.bukkit.configuration.file.FileConfiguration;
import top.mores.hunt.Hunt;

import java.util.List;

public class Level {
    FileConfiguration config = Hunt.getInstance().config;

    public List<String> NORMAL_LIST = config.getStringList("动物级别.普通.生物ID");
    public int NORMAL_VALUE = config.getInt("动物级别.普通.价值");
    public List<String> RARE_LIST = config.getStringList("动物级别.珍稀.生物ID");
    public int RARE_VALUE = config.getInt("动物级别.珍稀.价值");
    public List<String> SCARCE_LIST = config.getStringList("动物级别.少见.生物ID");
    public int SCARCE_VALUE = config.getInt("动物级别.少见.价值");
    public List<String> EPIC_LIST = config.getStringList("动物级别.史诗.生物ID");
    public int EPIC_VALUE = config.getInt("动物级别.史诗.价值");
    public List<String> LEGEND_LIST = config.getStringList("动物级别.传奇.生物ID");
    public int LEGEND_VALUE = config.getInt("动物级别.传奇.价值");
    public List<String> MYTH_LIST = config.getStringList("动物级别.神话.生物ID");
    public int MYTH_VALUE = config.getInt("动物级别.神话.价值");
}
