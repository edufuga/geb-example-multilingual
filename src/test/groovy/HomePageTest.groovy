import geb.spock.GebReportingSpec
import spock.lang.Unroll

/**
 * Simple Geb + Spock test with localization.
 * 
 * @author Eduard Fugarolas
 *
 */
class HomePageTest extends GebReportingSpec {

	@Unroll
	def "test looking '#word' up in '#language'"(String language, String word) {
		given: "I know the language I want to use"
		Localization.setLanguage(language)

		when: "I open the dictionary in that language"
		to HomePage

		and: "I search for some word in the language"
		search = word
		/**
		 * Same as "[page instance].setSearch(word)".
		 * This is the result of (a) Geb+Spock delegating to
		 * the Browser class and the Browser delegatin to
		 * the Page class and (b) Groovy setter and getter
		 * property calls.
		 */

		then: "the word is recognised by the dictionary"
		println "Found words:"
		words.each { println "\t"+it }
		word in words

		where: "the languages and words are the following"
		language	|	word
		"de"		|	"Kugel"
		"es"		|	"esfera"
	}
}
