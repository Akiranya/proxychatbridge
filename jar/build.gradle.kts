plugins {
    id("cc.mewcraft.deploy-conventions")
}

project.ext.set("name", "ProxyChatBridge")

dependencies {
    runtimeOnly(project(":proxychatbridge:common"))
    runtimeOnly(project(":proxychatbridge:bukkit"))
    runtimeOnly(project(":proxychatbridge:velocity"))
    runtimeOnly(libs.adventure.binaryserializer) {
        exclude("net.kyori")
    }
}
