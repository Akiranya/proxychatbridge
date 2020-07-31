package com.ranull.bungeechatbridge.bungee.config;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class ConfigManager {
    private Configuration configFile;

    public ConfigManager() {
        createConfig();
        loadConfig();
    }

    public Configuration getConfig() {
        return this.configFile;
    }

    public void loadConfig() {
        try {
            configFile = new Configuration();
            configFile = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File("plugins/BungeeChatBridge/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createConfig() {
        File dir = new File("plugins/BungeeChatBridge");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File("plugins/BungeeChatBridge/config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
