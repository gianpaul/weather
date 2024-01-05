import org.jetbrains.kotlin.konan.properties.Properties
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

object GradleProperties {
    private val properties = mutableMapOf<ProjectProperties, Properties>()

    enum class ProjectProperties(val file: String) {
        DEV("config/properties/dev.properties"),
        PROD("config/properties/dev.properties");

        fun exists(): Boolean {
            return properties.containsKey(this)
        }
    }

    fun init(rootDir: File) {
        for (enum in ProjectProperties.values()) {
            val file = File(rootDir, enum.file)
            if (file.exists() && file.isFile) {
                loadProperties(enum, file)
            }
        }
    }

    private fun loadProperties(propertiesName: ProjectProperties, file: File) {
        val properties = Properties()
        InputStreamReader(FileInputStream(file), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
        this.properties[propertiesName] = properties
    }

    fun has(propertiesName: ProjectProperties): Boolean {
        return properties.containsKey(propertiesName)
    }

    fun getProperty(propertiesName: ProjectProperties, key: String): String? {
        return properties[propertiesName]?.getProperty(key)
    }
}

fun GradleProperties.ProjectProperties.get(key: String): String? {
    return GradleProperties.getProperty(this, key)
}