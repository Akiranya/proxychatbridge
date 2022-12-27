package com.ranull.proxychatbridge.bungee.manager;

import com.google.common.io.ByteStreams;
import com.ranull.proxychatbridge.bungee.ProxyChatBridge;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    private final ProxyChatBridge plugin;
    private Configuration configFile;

    public ConfigManager(ProxyChatBridge plugin) {
        this.plugin = plugin;

        saveConfig();
        loadConfig();
    }

    public Configuration getConfig() {
        return configFile;
    }

    public void saveConfig() {
        if (plugin.getDataFolder().exists() || plugin.getDataFolder().mkdir()) {
            File configFile = new File(plugin.getDataFolder(), "config.yml");

            if (!configFile.exists()) {
                try {
                    if (configFile.createNewFile()) {
                        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yml");

                        if (inputStream != null) {
                            ByteStreams.copy(inputStream, Files.newOutputStream(configFile.toPath()));
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void loadConfig() {
        try {
            configFile = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
