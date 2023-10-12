import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    alias(libs.plugins.pluginyml.paper)
}

project.ext.set("name", "ProxyChatBridge")

dependencies {
    compileOnly(project(":proxychatbridge:common"))
    compileOnly(libs.server.paper)
    compileOnly(libs.chatchat.api)
    compileOnly(libs.adventure.binaryserializer)
}

paper {
    main = "com.ranull.proxychatbridge.bukkit.ProxyChatBridge"
    name = project.ext.get("name") as String
    version = "${project.version}"
    description = project.description
    apiVersion = "1.19"
    authors = listOf("Nailm")
    serverDependencies {
        register("ChatChat") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.OMIT
        }
    }
}
