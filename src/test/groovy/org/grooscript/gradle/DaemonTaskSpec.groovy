package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.daemon.ConversionDaemon
import org.grooscript.daemon.FilesDaemon
import org.grooscript.util.GsConsole
import spock.lang.Specification
import spock.lang.Unroll

import static org.grooscript.util.Util.SEP

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class DaemonTaskSpec extends Specification {

    static final ANY_SOURCE = ['source']
    static final ANY_DESTINATION = 'destination'
    static final GOOD_SOURCE = ["src${SEP}test${SEP}resources${SEP}groovy"]
    static final GOOD_DESTINATION = "src${SEP}test${SEP}resources${SEP}js${SEP}app"

    Project project
    DaemonTask task

    def setup() {
        GroovySpy(ConversionDaemon, global:true)
        project = ProjectBuilder.builder().build()
        task = project.task('daemon', type: DaemonTask)
        task.project = project
        task.project.extensions.grooscript = [:]
        task.waitInfinite = false
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

    def 'test launch daemon with bad options'() {
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
        1 * GsConsole.info('[0] Conversion daemon has converted files.')
        1 * ConversionDaemon.start([project.file(ANY_SOURCE[0]).path], project.file(ANY_DESTINATION).path, _)
    }

    def 'test launch daemon and do conversion'() {
        given:
        GroovySpy(GsConsole, global: true)
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION

        when:
        task.launchDaemon()

        then:
        1 * GsConsole.message({ it.startsWith('Listening file changes in : [')})
    }

    def 'test launch daemon with all conversion options'() {
        given:
        GroovySpy(GsConsole, global: true)
        def customization = { -> }
        def filesDaemon = Mock(FilesDaemon)
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION
        task.classPath = null
        task.customization = customization
        task.initialText = 'initial'
        task.finalText = 'final'
        task.recursive = true
        task.mainContextScope = ['$']
        task.addGsLib = 'gs'

        when:
        task.launchDaemon()

        then:
        1 * ConversionDaemon.start({ it.size() == 1 && it[0].endsWith(GOOD_SOURCE[0]) },
                { it.endsWith(GOOD_DESTINATION) }, [
                classPath : [],
                customization: customization,
                initialText: 'initial',
                finalText: 'final',
                recursive: true,
                mainContextScope: ['$'],
                addGsLib: 'gs'
        ]) >> filesDaemon
        0 * filesDaemon._
        0 * GsConsole._
    }
}
