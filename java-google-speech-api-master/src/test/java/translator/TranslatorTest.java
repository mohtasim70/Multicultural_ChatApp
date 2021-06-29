package translator;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.goxr3plus.speech.translator.GoogleTranslate;

public class TranslatorTest {

	@Test
	public void testString() throws IOException {

		String translatedText = GoogleTranslate.translate("Ola . Buenos días");
		String translatedText2=GoogleTranslate.translate("en","मेरा");
		String translatedText5=GoogleTranslate.translate("es","unos dos tres");
		String translatedText3=GoogleTranslate.detectLanguage("unos dos tres");
		String translatedText4=GoogleTranslate.findLanguage("My name is oil");
		//GoogleTranslate.translate(sourceLanguage, targetLanguage, text)
		String expecetedText = "Hi . Good Morning";

		System.out.println(translatedText5);
		//assertTrue(translatedText.equals(expecetedText));
	}
}
