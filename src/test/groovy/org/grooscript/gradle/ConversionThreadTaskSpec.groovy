package org.grooscript.gradle

import org.apache.tomcat.jni.Thread
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.gradle.daemon.ConversionDaemon
import org.grooscript.gradle.daemon.FilesDaemon
import org.grooscript.util.GsConsole
import spock.lang.Specification
import spock.lang.Unroll

import static org.grooscript.util.Util.SEP

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class ConversionThreadTaskSpec extends Specification {

    static final ANY_SOURCE = ['source']
    static final ANY_DESTINATION = 'destination'
    static final GOOD_SOURCE = ["src${SEP}test${SEP}resources${SEP}groovy"]
    static final GOOD_DESTINATION = "src${SEP}test${SEP}resources${SEP}js${SEP}app"

    Project project
    ConvertThreadTask task

    def setup() {
        GroovySpy(ConversionDaemon, global:true)
        project = ProjectBuilder.builder().build()
        task = project.task('daemon', type: ConvertThreadTask)
        task.project = project
        task.project.extensions.grooscript = [:]
    }

    def 'by default it doesn\'t block execution'() {
        expect:
        task.blockExecution == false
    }

    @Unroll
    def 'need source and destination to start daemon'() {
        when:
        task.source = source
        task.destination = destination
        task.launchDaemon()

        then:
        0 * _
        thrown(GradleException)

        where:
        source  |destination
        ['one'] |null
        null    |null
        null    |'two'
    }

    def 'launch daemon with bad options'() {
        given:
        GroovySpy(GsConsole, global: true)
        task.source = ANY_SOURCE
        task.destination = ANY_DESTINATION

        when:
        task.launchDaemon()
        //Wait until raise error in conversion
        Thread.sleep(450)

        then:
        1 * GsConsole.exception({ it.startsWith('FilesActor Error in file/folder')})
        1 * ConversionDaemon.start([project.file(ANY_SOURCE[0]).path], project.file(ANY_DESTINATION).path, _)
    }

    def 'launch daemon and do conversion'() {
        given:
        GroovySpy(GsConsole, global: true)
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION

        when:
        task.launchDaemon()

        then:
        1 * GsConsole.message({ it.startsWith('Listening file changes in : [')})
    }

    def 'launch daemon with all conversion options'() {
        given:
        GroovySpy(GsConsole, global: true)
        def customization = { -> }
        def filesDaemon = Mock(FilesDaemon)
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION
        task.classpath = null
        task.customization = customization
        task.initialText = 'initial'
        task.finalText = 'final'
        task.recursive = true
        task.mainContextScope = ['$']
        task.addGsLib = 'gs'

        when:
        task.launchDaemon()

        then:
        1 * ConversionDaemon.start([project.file(GOOD_SOURCE[0]).path],project.file(GOOD_DESTINATION).path, [
                classpath : [],
                customization: customization,
                initialText: 'initial',
                finalText: 'final',
                recursive: true,
                mainContextScope: ['$'],
                addGsLib: 'gs',
                requireJsModule: false,
                consoleInfo: false,
                includeDependencies: false,
                nashornConsole: false
        ]) >> filesDaemon
        0 * filesDaemon._
        0 * GsConsole._
    }
}
