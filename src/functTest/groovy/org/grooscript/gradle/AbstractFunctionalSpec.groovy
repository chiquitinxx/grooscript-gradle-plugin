package org.grooscript.gradle

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.grooscript.util.Util.SEP as LS

/**
 * Created by jorge on 22/8/15.
 */
class AbstractFunctionalSpec extends Specification {

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile
    String testResourcesFilesFolder = "src${LS}functTest${LS}resources${LS}"

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')

        def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
        if (pluginClasspathResource == null) {
            throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
        }

        def pluginClasspath = pluginClasspathResource.readLines()
                .collect { it.replace('\\', '\\\\') } // escape backslashes in Windows paths
                .collect { "'$it'" }
                .join(", ")

        // Add the logic under test to the test build
        buildFile << """
            buildscript {
                dependencies {
                    classpath files($pluginClasspath)
                }
            }
        """
    }

    void copyTestResourcesFiles(...files) {
        files.each { nameFile ->
            def file = testProjectDir.newFile(nameFile)
            file.text = new File(testResourcesFilesFolder + nameFile).text
        }
    }
}
