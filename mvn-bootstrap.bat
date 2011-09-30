@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

cmd /c ant download-dependencies

cmd /c mvn install:install-file -Dfile=lib/jdbcpool-0.99.jar -DgroupId=com.bitmechanic -DartifactId=jdbcpool -Dversion=0.99 -Dpackaging=jar
cmd /c mvn install:install-file -Dfile=lib/hsqldb.jar -DgroupId=org.hsqldb -DartifactId=hsqldb -Dversion=2.2.5.6-9-2011 -Dpackaging=jar

cmd /c mvn install:install-file -Dfile=lib/commons-httpclient-mcf.jar -DgroupId=commons-httpclient -DartifactId=commons-httpclient-mcf -Dversion=3.1  -Dpackaging=jar
cmd /c mvn install:install-file -Dfile=lib/xercesImpl-mcf.jar -DgroupId=xerces -DartifactId=xercesImpl-mcf -Dversion=2.9.1  -Dpackaging=jar
cmd /c mvn install:install-file -Dfile=connectors/jcifs/jcifs/jcifs.jar -DgroupId=org.samba.jcifs -DartifactId=jcifs -Dversion=1.3.16  -Dpackaging=jar

cmd /c mvn install:install-file -Dfile=lib/chemistry-opencmis-server-inmemory-war-0.5.0-SNAPSHOT.war -DgroupId=org.apache.chemistry.opencmis -DartifactId=chemistry-opencmis-server-inmemory-war -Dversion=0.5.0-SNAPSHOT -Dpackaging=war

echo Dependencies installed
