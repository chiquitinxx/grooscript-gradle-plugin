package org.grooscript.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.gradle.util.InitTools
import spock.lang.Specification
import spock.lang.Unroll

import static org.grooscript.util.Util.SEP

/**
 * Created by jorgefrancoleza on 23/1/15.
 */
class SyncGrooscriptLibsTaskSpec extends Specification {

    Project project
    SyncGrooscriptLibsTask task
    InitTools initTools

    private final static EMPTY_CONTENT = ''

    def setup() {
        initTools = Mock(InitTools)
        project = ProjectBuilder.builder().withProjectDir(new File('.')).build()
        task = project.task('syncGsLibs', type: SyncGrooscriptLibsTask)
        task.initTools = initTools
        task.project = project
    }

    void 'create the task'() {
        expect:
        task instanceof SyncGrooscriptLibsTask
        task.filesToSync == ['grooscript.js', 'grooscript.min.js', 'grooscript-tools.js']
    }

    @Unroll
    void 'sync files'() {
        given:
        createFileEmpty(nameFile)

        when:
        task.sync()

        then:
        1 * initTools.extractGrooscriptJarFile(new File(nameFile).name, { it.endsWith nameFile})

        cleanup:
        new File(nameFile).delete()
        new File('one').deleteDir()

        where:
        nameFile << ['grooscript.js', 'grooscript.min.js', 'grooscript-tools.js',
                "one${SEP}grooscript.js", "one${SEP}grooscript.min.js", "one${SEP}two${SEP}grooscript-tools.js"]
    }

    @Unroll
    void 'files that not to be updated'() {
        given:
        createFileEmpty(nameFile)

        when:
        task.sync()

        then:
        0 * _

        cleanup:
        new File(nameFile).delete()
        new File('one').deleteDir()

        where:
        nameFile << ['pepe.js', 'grooscript.gol', "one${SEP}jquery.min.js", 'grooscript.min']
    }

    private createFileEmpty(name) {
        new File("one${SEP}two").mkdirs()
        new File(name).text = EMPTY_CONTENT
    }
}
