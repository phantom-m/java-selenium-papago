# java-selenium-papago

Pre-Requisites
-Install Java (https://docs.oracle.com/goldengate/1212/gg-winux/GDRAD/java.htm#BGBFHBEA) and be able to run the java command in command line
-Download the subtitle.jar or compile the jar yourself by cloning the project to your local workspace
-Download the chrome driver that matches your chrome version (https://chromedriver.chromium.org/downloads) and add it to your environment variable path

Command Line Command:
java -Dfile.encoding=UTF-8 -jar PATH_TO_SUBTITLE PATH_TO_SUBTITLE_OUTPUT SOURCE_LANGUAGE TARGET_LANGUAGE WAIT_TIME CHARACTERS

Examples of valid commands:
"C:\User\Documents\subtitle.vtt" "C:\User\Documents\subtitle-translated.vtt"
"C:\User\Documents\subtitle.vtt" "C:\User\Documents\subtitle-translated.vtt" japanese english 15
"C:\User\Documents\subtitle.vtt" "C:\User\Documents\subtitle-translated.vtt" korean japanese 1 50


About the arguments
PATH_TO_SUBTITLE 
  - Full file path to the subtitle you want to translate.
PATH_TO_SUBTITLE_OUTPUT
  - Full file path to where you want to save the translated subtitle.
SOURCE_LANGUAGE
  - Optional, default = auto
  - valid values: korean, english, japanese, chinese-s, chinese-t, spanish, french, german, russian, portuguese, italian, vietnamese, thai, indonesian, hindi
TARGET_LANGUAGE
  - Optional, default = english
  - valid values: korean, english, japanese, chinese-s, chinese-t, spanish, french, german, russian, portuguese, italian, vietnamese, thai, indonesian, hindi
WAIT_TIME
  - Optional, default = 10
  - Wait time to wait for the translation to be complete. Lower wait time has a higher risk of errors in translation.
CHARACTERS
  - Optional, default = 5000
  - Max number of characters to send at a time to papago.
