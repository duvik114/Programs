cd ..
javac -d compiled -p ..\..\java-advanced-2022\artifacts;..\..\java-advanced-2022\lib ..\java-solutions\info\kgeorgiy\ja\Beliaev\implementor\Implementor.java module-info.java
cd compiled
jar cmvf ..\generateJar\MANIFEST.MF ..\jar\Implementor.jar info\kgeorgiy\ja\Beliaev\implementor\Implementor.class module-info.class