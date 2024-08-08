Copy all of these files and folders from the mdk :
* GRADLE
* SRC (optional)
* build.gradle
* gradle.properties
* gradlew
* gradlew.bat
* settings.gradle

To setup a forge workspace in VSCode, you then need to :
* Make sure you have a CMD terminal open (NOT POWERSHELL)
* Run "gradlew"
* Fix all errors pertaining to the wrong modid and folder structure being used in the src folder.
* Run "gradlew :runClient"