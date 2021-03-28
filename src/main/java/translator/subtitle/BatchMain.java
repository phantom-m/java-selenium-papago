package translator.subtitle;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class BatchMain {
	static int DEFAULT_WAIT_SECONDS = 10;
	static int DEFAULT_CHARACTER_MAX = 5000;
	static WebDriver driver;
	static Integer wait;
	static Integer maxChar;
    public static void main(String[] args) throws IOException {
    	String srcFolder =  args.length > 0 ? args[0] : null;
    	String languageSource = args.length > 1 ? args[1] : null;
    	String languageTarget = args.length > 2 ? args[2] : null;
    	wait = args.length > 3 ? Integer.getInteger(args[3]) : null;
    	maxChar = args.length > 4 ? Integer.getInteger(args[4]) : null;
    	String SLASH = "/";
    	if(srcFolder == null) {
    		System.err.println("Missing subtitle source folder path");
    		return;
    	}
    	if(wait == null) {
    		wait = DEFAULT_WAIT_SECONDS;
    	}
    	if(maxChar == null) {
    		maxChar = DEFAULT_CHARACTER_MAX;
    	}
    	
        String papagoUrl = getUrl(languageSource, languageTarget);
        driver = new ChromeDriver();
        driver.get(papagoUrl);
        
    	String outputFolder = srcFolder + SLASH + "papago_output";
    	Path outputPath = Paths.get(outputFolder);
    	Files.createDirectories(outputPath);
    	
    	File folder = new File(srcFolder);
		File[] listOfFiles = folder.listFiles();

    	for(int fileIndex = 0; fileIndex < listOfFiles.length; fileIndex++) {
    		if(listOfFiles[fileIndex].isFile()) {
    			File inputFile = listOfFiles[fileIndex];
    			String inputFileName = inputFile.getName();
    			String outputFilePath = outputFolder + SLASH + inputFileName;
    			System.out.println(inputFileName);
    			translateFile(inputFile.getAbsolutePath(),outputFilePath);
    		}
    	}
        driver.quit();
    }
    
    public static void translateFile(String subtitlePath, String translatedSubtitlePath) {
    	int lineTotal = getFileTotalLineNumber(subtitlePath);
    	
    	String outputText = "";
    	String inputText = "";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(subtitlePath), StandardCharsets.UTF_8)) {
        	String line;
        	int lineNum = 0;
			while((line = br.readLine()) != null) {
				lineNum++;
				if((inputText + line +"\n").length() > maxChar) {
					System.out.println("Line: " + lineNum + "/" + lineTotal);
					outputText = outputText + translate(inputText, wait);
					inputText = "\n";
				}
				inputText = inputText+line+"\n";
			}
			System.out.println("Line: " + lineNum + "/" + lineTotal);
			outputText = outputText + translate(inputText, wait);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Failed to translate: "+subtitlePath);
			return;
		}
    	
    	Path subtitleOutputPath = Paths.get(translatedSubtitlePath);
    	try {
			Files.writeString(subtitleOutputPath, outputText, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
    }
    
    public static int getFileTotalLineNumber(String path) {
    	int lineTotal = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
        	while (br.readLine() != null) lineTotal++;
        } catch (Exception e) {
        	System.err.println(e.getLocalizedMessage());
		}
		return lineTotal;
    }
    
    public static String getUrl(String source, String target) {
    	String sourceFormat = "sk=" +getLanguageParam(source);
    	String targetFormat = "tk=" + (getLanguageParam(target).equals("auto") ? getLanguageParam("english") : getLanguageParam(target));
    	String baseUrl = "https://papago.naver.com/?";
    	System.out.println(baseUrl + sourceFormat + "&" + targetFormat);
    	return baseUrl + sourceFormat + "&" + targetFormat;
    }

    public static String getLanguageParam(String language) {
    	String formatted = "auto";
    	if(language == null) {
    		return formatted;
    	}
    	switch (language.toLowerCase()) {
		case "korean": {
			formatted = "ko";
			break;
		}
		case "english": {
			formatted = "en";
			break;
		}
		case "japanese": {
			formatted = "ja";
			break;
		}
		case "chinese-s": {
			formatted = "zh-CN";
			break;
		}
		case "chinese-t": {
			formatted = "zh-TW";
			break;
		}
		case "spanish": {
			formatted = "es";
			break;
		}
		case "french": {
			formatted = "fr";
			break;
		}
		case "german": {
			formatted = "de";
			break;
		}
		case "russian": {
			formatted = "ru";
			break;
		}
		case "portuguese": {
			formatted = "pt";
			break;
		}
		case "italian": {
			formatted = "it";
			break;
		}
		case "vietnamese": {
			formatted = "vi";
			break;
		}
		case "thai": {
			formatted = "th";
			break;
		}
		case "indonesian": {
			formatted = "id";
			break;
		}
		case "hindi": {
			formatted = "hi";
			break;
		}
		default:
			formatted = "auto";
		}
    	return formatted;
    }
    
    public static String translate(String input, Integer waitSeconds) throws InterruptedException {
    	sendTextToTranslate(input);
    	clickTranslateButton();
		TimeUnit.SECONDS.sleep(waitSeconds);
        return getOutputText();
    }
    
    public static void sendTextToTranslate(String input) {
        WebElement inputTextBox = getElementById("txtSource", 1);
        inputTextBox.clear();
        inputTextBox.sendKeys(input);
    }
    
    public static void clickTranslateButton() {
        WebElement button = getElementById("btnTranslate", 1);
        button.click();
    }
    
    public static String getOutputText() {
        WebElement outputTextBox =  getElementById("txtTarget", 1);
        String outputText = outputTextBox.getText();
        return outputText;
    }
    
    public static WebElement getElementById( String id, int wait) {
    	return getWait(wait).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }
    
    public static WebDriverWait getWait(long seconds) {
    	return new WebDriverWait(driver, seconds);
    }
}
