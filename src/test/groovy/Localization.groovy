/**
 * Class with static factory methods for localization.
 * 
 * @author Eduard Fugarolas
 *
 */
class Localization {

	/**
	 * Language of the whole SUT.
	 */
	static String language = "de"

	/**
	 * Sets the language of the whole SUT.
	 * 
	 * <p>This language can be used for
	 * localizing single Page classes.</p>
	 * 
	 * @param lang Language of the SUT.
	 */
	static void setLanguage(String lang) {
		language = lang
	}

	/**
	 * Obtains the language of the SUT.
	 * 
	 * @return Language
	 */
	static String getLanguage() {
		return language
	}

	/**
	 * Useful for setting the URL of HomePage.
	 * 
	 * @return Localizer for the URL of HomePage.
	 */
	static URLLocalizer getHomeURLLocalizer() {
		println "Obtain a 'URLLocalizer' for language '$language'."
		if (language == "de")
			return new HomeURLLocalizerGerman()
		if (language == "es")
			return new HomeURLLocalizerSpanish()
		return null
	}

	/**
	 * Useful for localizing the content (text) of a Page.
	 * 
	 * @return Localizer for messages in HomePage.
	 */
	static HomeLocalizer getHomeLocalizer() {
		println "Obtain a 'HomeLocalizer' for language '$language'."
		if (language == "de")
			return new HomeLocalizerGerman()
		if (language == "es")
			return new HomeLocalizerSpanish()
		return null
	}
}
