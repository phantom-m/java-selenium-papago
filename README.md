## Pre-Requisites
  - Install Java (https://docs.oracle.com/goldengate/1212/gg-winux/GDRAD/java.htm#BGBFHBEA) and be able to run the java command in command line
  - Download the the jar file you want to use or compile the jar yourself by cloning the project to your local workspace
  - Download the chrome driver that matches your chrome version (https://chromedriver.chromium.org/downloads) and add it to your environment variable path

# translate.jar

## Description

Translates the specified file. Has issues with special characters in the filename. In those cases, please use translate_batch.jar.

## Command Line Command:

>java -Dfile.encoding=UTF-8 -jar PATH_TO_JAR PATH_TO_SUBTITLE PATH_TO_SUBTITLE_OUTPUT SOURCE_LANGUAGE TARGET_LANGUAGE WAIT_TIME CHARACTERS

## Examples of valid commands:

>java -Dfile.encoding=UTF-8 -jar "C:\Programs\translate.jar" "C:\User\Documents\subtitle.vtt" "C:\User\Documents\subtitle-translated.vtt"

>java -Dfile.encoding=UTF-8 -jar "C:\Programs\translate.jar" "C:\User\Documents\subtitle.vtt" "C:\User\Documents\subtitle-translated.vtt" japanese english 15

>java -Dfile.encoding=UTF-8 -jar "C:\Programs\translate.jar" "C:\User\Documents\subtitle.vtt" "C:\User\Documents\subtitle-translated.vtt" korean japanese 1 50

# translate_batch.jar

## Description

Translates all the files within a folder. It will create a folder named "papago_output" inside your file source directory to put your translated files.

## Command Line Command:

>java -Dfile.encoding=UTF-8 -jar PATH_TO_JAR PATH_TO_SUBTITLE_FOLDER SOURCE_LANGUAGE TARGET_LANGUAGE WAIT_TIME CHARACTERS

## Examples of valid commands:

>java -Dfile.encoding=UTF-8 -jar "C:\Programs\translate_batch.jar" "C:\User\Documents\subtitles_to_translate"

>java -Dfile.encoding=UTF-8 -jar "C:\Programs\translate_batch.jar" "C:\User\Documents\subtitles_to_translate" japanese english 15

>java -Dfile.encoding=UTF-8 -jar "C:\Programs\translate_batch.jar" "C:\User\Documents\subtitles_to_translate" korean japanese 1 50

## Argument Description

### PATH_TO_SUBTITLE 
  - Full file path to the subtitle you want to translate.


### PATH_TO_SUBTITLE_OUTPUT
  - Full file path to where you want to save the translated subtitle.

### PATH_TO_SUBTITLE_FOLDER
  - Full file path to the folder with the subtitles you want to translate


### SOURCE_LANGUAGE
  - Optional, default = auto
  - valid values: korean, english, japanese, chinese-s, chinese-t, spanish, french, german, russian, portuguese, italian, vietnamese, thai, indonesian, hindi


### TARGET_LANGUAGE
  - Optional, default = english
  - valid values: korean, english, japanese, chinese-s, chinese-t, spanish, french, german, russian, portuguese, italian, vietnamese, thai, indonesian, hindi


### WAIT_TIME
  - Optional, default = 10
  - Wait time to wait for the translation to be complete. Lower wait time has a higher risk of errors in translation.

### CHARACTERS
  - Optional, default = 5000
  - Max number of characters to send at a time to papago. Papago UI only accepts up to 5000. Putting more than that will only result in missing translations.
