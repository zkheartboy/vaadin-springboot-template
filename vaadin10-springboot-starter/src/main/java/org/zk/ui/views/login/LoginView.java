package org.zk.ui.views.login;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.zk.ui.components.BakeryCookieConsent;
import org.zk.ui.utils.BakeryConst;

@Tag("login-view")
@HtmlImport("src/views/login/login-view.html")

@Route(value = "login")
@PageTitle("vaadin10-springboot-starter")

@Viewport(BakeryConst.VIEWPORT)
public class LoginView extends PolymerTemplate<LoginView.Model> implements PageConfigurator, AfterNavigationObserver {

	
	public LoginView() {
		getElement().appendChild(new BakeryCookieConsent().getElement());
	}

	@Override
	public void configurePage(InitialPageSettings settings) {
		// Force login page to use Shady DOM to avoid problems with browsers and
		// password managers not supporting shadow DOM
		settings.addInlineWithContents(InitialPageSettings.Position.PREPEND,
				"window.customElements=window.customElements||{};" +
						"window.customElements.forcePolyfill=true;" +
						"window.ShadyDOM={force:true};", InitialPageSettings.WrapMode.JAVASCRIPT);
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		boolean error = event.getLocation().getQueryParameters().getParameters().containsKey("error");
		getModel().setError(error);
	}

	public interface Model extends TemplateModel {
		void setError(boolean error);
	}

}
