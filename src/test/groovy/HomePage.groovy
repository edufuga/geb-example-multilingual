import geb.Browser
import geb.Page

/**
 * Main page of PONS (online dictionary).
 * 
 * <p>This <code>Page</code> class is parameterized with a
 * localizer object for the messages in the page and another
 * localizer object for the URL of the page.</p>
 * 
 * <p>The language to be used in the automatisation code
 * can be set with
 * <pre>
 *     Localization.setLanguage("es")
 * </pre>
 * </p>
 * 
 * <p>The URL Localizer is just used in the default constructor
 * of the class. It is not needed anywhere else. It just sets
 * the URL of the HomePage.
 * </p>
 * 
 * @author Eduard Fugarolas
 *
 */
class HomePage extends Page {

	/**
	 * Localizer used for obtaining message strings in the current page.
	 * 
	 * <p><strong>Attention: </strong>This localizer is an instance variable
	 * and it can be used inside of the <code>content</code> closure, which is
	 * a <em>static</em> element. The reason for this is that a Closure has local
	 * scope (it remembers it's "birth environment").</p>
	 * 
	 * <p><strong>Background Information: </strong>
	 * More concretely, each <strong>content definition</strong> inside the
	 * static <code>content</code> Closure of a Page class consists of a
	 * <strong>name</strong> and a <strong>definition</strong> (this is the
	 * part between brackets <code>{ }</code>). The name of the content
	 * definition is internally the name of a <em>method call</em>, whereas the
	 * definition is itself a Closure, passed as an <em>argument of the
	 * method call</em>. This is how the Content DSL of Geb works.</p>
	 * 
	 * <p>This means that when a content definition is used in a test,
	 * the Closure of the content definition is evaluated with the
	 * local variables of the Page where it is declared. The content
	 * definition can call methods and use arguments of its Page
	 * instance.</p>
	 * 
	 * <p>This implies that the HomeLocalizer can be used in content definitions
	 * to localize them (the content has some locale specific part) or
	 * to localize a given string in the page (the content definition
	 * is not language specific but the text in it is).</p>
	 */
	HomeLocalizer messages

	/**
	 * 
	 * Customizer of HomePage's Content Definitions.
	 * 
	 * <p><strong>Attention: </strong>The <code>content</code> block
	 * is actually a Closure. Content is defined in Geb by calling
	 * unknown methods with a Closure as a parameter.
	 * This means that in <code>content = { ... }</code> we can
	 * write normal Groovy code. For example we could <em>define</em>
	 * single content definitions based on some boolean condition.
	 * This is what the <code>HomeContentCustomizer</code> does.</p>
	 */
	HomeContentCustomizer customizer

	/**
	 * Default constructor of HomePage.
	 * Initializes its localized URL.
	 */
	HomePage() {
		println "Default constructor of HomePage"
		url = Localization.getHomeURLLocalizer().getURL()
		// Attention: No need to write "static url".
		// It is the same URL (class attribute) of Page.
		messages = Localization.getHomeLocalizer()
		customizer = Localization.getHomeContentCustomizer()
	}

	/**
	 * At blocks are not inherited by Geb.
	 * 
	 * <p>The <code>at</code> attribute is a <em>static</em>
	 * attribute of the Page class. There is just this
	 * one. The same applies to <code>url</code>.</p>
	 */
	static at = {
		println "At checker of HomePage."
		println "The current language is '${Localization.getLanguage()}'."
		println "The URL of the Page is $url."
		println "The current home localizer is $messages"
		assert dictionary
	}

	/**
	 * Content blocks are "inherited" by Geb.
	 * 
	 * <p><strong>Background information: </strong>
	 * Internally it is not really inheritance but a while loop.
	 * The result is that content definitions of a subclass override
	 * the content definitions of the superclass but for example
	 * can't access them with <code>super</code>. Furthermore
	 * content definitions itself are not really attributes nor variables
	 * but method calls, where the method has a name and a Closure as
	 * parameter.</p>
	 * 
	 */
	static content = {

		container { $(".container") }

		locale { container.$(".locale") }

		/**
		 * The newsletter isn't present in every language.
		 * This is maybe nicer than setting "required"
		 * as it explicitly defines a content definition
		 * just when it is supposed to be there.
		 */
		if (customizer.hasNewsLetter()) {
			println "The page should have a newsletter."
			newsletter { container.$(".shop-newsletter") }
		}

		/**
		 * The "action" attribute of the element is language specific.
		 * This content definition is localized.
		 * The name "dictionary" is the same, as it is the same element.
		 */
		dictionary { $("form", action: messages.getActionName()) }

		search { dictionary.$("#q")}	// Query (search) field.
			
		placeholder { search.attr("placeholder") } // "Suchen" | "Consultar"

		words(required: false, wait: true) { $("#typeahead-menu").$("li")*.text() }
	}

}
