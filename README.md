[![Build Status](https://snap-ci.com/chiquitinxx/grooscript-gradle-plugin/branch/master/build_image)](https://snap-ci.com/chiquitinxx/grooscript-gradle-plugin/branch/master)

grooscript-gradle-plugin
===

Gradle plugin to convert your groovy files to javascript using grooscript. Last version published is 0.11.

__Important: plugin requires Gradle 2, grooscript requires Groovy 2__

build.gradle to use the plugin with any gradle version:

<pre>
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'org.grooscript:grooscript-gradle-plugin:0.11'
    }
}

apply plugin: 'org.grooscript.conversion'
</pre>

build.gradle to use the plugin with gradle 2.1+:

<pre>
plugins {
  id "org.grooscript.conversion" version "0.11"
}
</pre>

There are 3 extension, convert files, convert templates and spy changes in files.

<pre>
//Convert files, you can change options
grooscript {
    source = ['src/main/groovy/presenters'] //Sources to be converted(List<String>), default is ['src/main/groovy']
    destination = 'js' //Target directory for js files, default is 'src/main/webapp/js/app'
    classPath = ['src/main/groovy'] //Needed classpath's to compile source files(List<String>), default is ['src/main/groovy']
    customization = null //Customization in files, it's a closure, as for example { -> ast(groovy.transform.TypeChecked) }
    initialText = '//Grooscript converted file'
    initialText = '//End converted file'
    recursive = true //Default is false
    mainContextScope = ['$'] //Variables available in main scope (List<String>), default is null
    addGsLib = 'grooscript' //Include a grooscript js lib in the result, default is null
}

//Templates options
templates {
    templatesPath = 'src/main/webapp/templates'
    templates = ['main.gtpl', 'little/small.tpl']
    destinationFile = 'src/main/webapp/js/lib'
    classPath = ['src/main/groovy']
}

//Spy files changes
spy {
    files = ['anyFolder', 'aFile']
    onChanges = { list ->
        println 'Changes!! ' + list
    }
}
</pre>

There are 7 tasks:

__convert__ - to convert groovy files to javascript

__daemon__ - to run daemon that detect file changes and convert the files. This task blocks execution, so use it alone.

__initStaticWeb__ - create a static web project with index.html in src/main/webabb, to work with grooscript

__thread__ - run daemon to convert files to javascript. Executes daemon in a thread, so is perfect to use with other task

__templatesJs__ - generate javascript file with groovy templates defined

__templatesThread__ - run daemon to convert templates. Executes daemon in a thread, so is perfect to use with other task

__spyChanges__ - listen changes in files

More info about tasks [here](http://grooscript.org/gradle/tasks.html)

Guide about using this plugin [here](http://grooscript.org/starting_gradle.html)