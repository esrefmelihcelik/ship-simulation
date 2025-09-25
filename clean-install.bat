
@echo off
mvn -f "D:\Intellij IDEA Projects\leetcode-1\leetcode-1\pom.xml" -T 1C -Dmaven.javadoc.skip=true -Dmaven.source.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -DskipTests clean install 