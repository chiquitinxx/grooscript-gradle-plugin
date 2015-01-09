package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.gradle.util.InitTools

/**
 * Created by jorge on 15/02/14.
 */
class InitStaticWebTask extends DefaultTask {

    static final GROOVY_DIR = 'src/main/groovy'
    static final PRESENTER_FILE = "${GROOVY_DIR}/Presenter.groovy"
    static final WEBAPP_DIR = 'src/main/webapp'
    static final JS_DIR = "${WEBAPP_DIR}/js"
    static final JS_LIB_DIR = "${JS_DIR}/lib"
    static final JS_APP_DIR = "${JS_DIR}/app"
    static final HTML_FILE = "${WEBAPP_DIR}/index.html"
    static final GROOSCRIPT_MIN_JS_NAME = 'grooscript.min.js'
    static final GROOSCRIPT_TOOLS_JS_NAME = 'grooscript-tools.js'
    static final JQUERY_JS_FILE = "${JS_LIB_DIR}/jquery.min.js"
    static final JQUERY_JS_REMOTE = 'http://code.jquery.com/jquery-1.11.0.min.js'

    static final PRESENTER_TEXT = '''
class Presenter {
    String name
    def buttonClick() {
        if (name) {
            $('#salutes').append("<p>Hello ${name}!</p>")
        }
    }
}'''

    static final HTML_TEXT = '''<html>
<head>
    <title>Initial static web page</title>
    <script type="text/javascript" src="js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="js/lib/grooscript.min.js"></script>
    <script type="text/javascript" src="js/lib/grooscript-tools.js"></script>
    <script type="text/javascript" src="js/app/Presenter.js"></script>
</head>
<body>
    <p>Name:<input type="text" id="name"/></p>
    <input type="button" id="button" value="Say hello!"/>
    <div id="salutes"/>
    <script type="text/javascript">
        presenter = Presenter();
        $(document).ready(function() {
            var gQuery = GQueryImpl();
            gQuery.bindAll(presenter);
            console.log('All binds done.');
        });
    </script>
</body>'''

    InitTools initTools

    @TaskAction
    def initStaticWeb() {
        if (initTools.existsFile(HTML_FILE)) {
            throw new GradleException('Can\'t init static')
        } else {
            init()
        }
    }

    private init() {
        if (initTools.createDirs(JS_LIB_DIR) && initTools.createDirs(JS_APP_DIR) &&
            initTools.createDirs(GROOVY_DIR) &&
            initTools.saveFile(HTML_FILE, HTML_TEXT) &&
            initTools.saveFile(PRESENTER_FILE, PRESENTER_TEXT) &&
            initTools.extractGrooscriptJarFile(GROOSCRIPT_MIN_JS_NAME, "${JS_LIB_DIR}/${GROOSCRIPT_MIN_JS_NAME}") &&
            initTools.extractGrooscriptJarFile(GROOSCRIPT_TOOLS_JS_NAME, "${JS_LIB_DIR}/${GROOSCRIPT_TOOLS_JS_NAME}") &&
            initTools.saveRemoteFile(JQUERY_JS_FILE, JQUERY_JS_REMOTE)) {
            println 'Generation completed.'
        } else {
            throw new GradleException('Error creating changes and dirs.')
        }
    }
}
