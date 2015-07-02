import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class UnixScript extends DefaultTask {

    def environment = ''
    def mainClass = ''
    def classpath = []
    def baseJarName = ''
    def classPathBaseDir = './libs/'

    def header = '#!/bin/bash'

    @TaskAction
    def getUnixScriptContent() {
        StringBuilder builder = new StringBuilder();
        builder.append(header).append("\n")
        builder.append("java").append(" ")
                .append("-D").append("env").append("=").append(environment).append(" ")
                .append("-classpath").append(" \"").append(classPathBaseDir).append(baseJarName);
        classpath.eachWithIndex { name, index ->
            builder.append(":").append(classPathBaseDir).append(name)
            if (index != (classpath.size() - 1)) {
                builder.append(":")
            }
        }
        builder.append("\" ")
                .append(mainClass).append(" ").append("\"\$@\"");
        builder.toString();
    }

}