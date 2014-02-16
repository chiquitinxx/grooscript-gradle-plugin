package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.gradle.util.InitTools

/**
 * Created by jorge on 15/02/14.
 */
class InitStaticWebTask extends DefaultTask {

    static final WEBAPP_DIR = 'src/main/webapp'
    static final JS_DIR = "${WEBAPP_DIR}/js"
    static final JS_LIB_DIR = "${JS_DIR}/lib"
    static final JS_APP_DIR = "${JS_DIR}/app"
    static final HTML_FILE = "${WEBAPP_DIR}/index.html"
    static final MAIN_REQUIRE_FILE = "${JS_DIR}/main.js"
    static final GROOSCRIPT_JS_NAME = 'grooscript.js'
    static final GROOSCRIPT_BUILDER_JS_NAME = 'grooscript-builder.js'
    static final PLUGIN_BINDER_JS_NAME = 'grooscript-binder.js'
    static final PLUGIN_JQUERY_JS_NAME = 'JQueryUtils.js'
    static final REQUIRE_JS_FILE = "${JS_LIB_DIR}/require.js"
    static final JQUERY_JS_FILE = "${JS_LIB_DIR}/jquery.js"
    static final REQUIRE_JS_REMOTE = 'http://'
    static final JQUERY_JS_REMOTE = 'http://'

    static final HTML_TEXT = '''<html>
<head>
    <title>Initial static web page</title>
    <script data-main="js/main" src="js/lib/require.js"></script>
</head>
<body>
    <p>Name:<input type="text" id="name"/></p>
    <input type="button" id="button" value="Say hello!"/>
</body>'''

    static final MAIN_REQUIRE_TEXT = '''requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});
requirejs(['jquery', 'grooscript', 'JQueryUtils', 'grooscript-binder', 'app/Presenter'], function($) {
    presenter = Presenter();
    var binder = Binder();
    binder.jQueryUtils = JQueryUtils();
    $(document).ready(function() {
        binder.bindAllProperties(presenter);
        binder.bindAllMethods(presenter);
        console.log('All binds done.');
    });
});'''

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
            initTools.saveFile(HTML_FILE, HTML_TEXT) && initTools.saveFile(MAIN_REQUIRE_FILE, MAIN_REQUIRE_TEXT) &&
            initTools.extractGrooscriptJarFile(GROOSCRIPT_JS_NAME, "${JS_LIB_DIR}/${GROOSCRIPT_JS_NAME}") &&
            initTools.extractGrooscriptJarFile(
                    GROOSCRIPT_BUILDER_JS_NAME, "${JS_LIB_DIR}/${GROOSCRIPT_BUILDER_JS_NAME}") &&
            initTools.extractJarFile(PLUGIN_BINDER_JS_NAME, "${JS_LIB_DIR}/${PLUGIN_BINDER_JS_NAME}") &&
            initTools.extractJarFile(PLUGIN_JQUERY_JS_NAME, "${JS_LIB_DIR}/${PLUGIN_JQUERY_JS_NAME}") &&
            initTools.saveRemoteFile(JQUERY_JS_FILE, JQUERY_JS_REMOTE) &&
            initTools.saveRemoteFile(REQUIRE_JS_FILE, REQUIRE_JS_REMOTE)) {
            println 'Generation completed.'
        } else {
            throw new GradleException('Error creating files and dirs.')
        }
    }
}
