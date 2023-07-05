package py.com.tipcsa.eva;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import py.com.tipcsa.eva.event.LoginEvent;
import py.com.tipcsa.eva.event.LogoutEvent;
import py.com.tipcsa.eva.event.NavigationEvent;
import py.com.tipcsa.eva.util.UserUtil;
import py.com.tipcsa.eva.view.AltaPersonaView;
import py.com.tipcsa.eva.view.LoginView;
import py.com.tipcsa.eva.view.Main;
import py.com.tipcsa.eva.view.PersonaAbmView;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class EvaUI extends UI {
	
	private EventBus eventBus;
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = EvaUI.class, productionMode = false)
	public static class Servlet extends VaadinServlet {
	}
	
	
	
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {

		setupEventBus();
	if (UserUtil.isLoggedIn()){
			setContent(new Main());
		} else {
			setContent(new LoginView());
		}
		
		//abrirLogin();
	}

	private void setupEventBus() {
		eventBus = new EventBus();
		
		eventBus.register(this);
		
	}
	
	public static EvaUI getCurrent(){
		return (EvaUI) UI.getCurrent();
	}
	
	@Subscribe
	public void userLoggedIn(LoginEvent event){
		UserUtil.set(event.getUser());
		setContent(new Main());
	}
	
	public static EventBus getEventBus(){
		return getCurrent().eventBus;
		
	}
	public void navigateTo(NavigationEvent view) {
        getNavigator().navigateTo(view.getViewName());
    }
	
	@Subscribe
    public void logout(LogoutEvent logoutEvent) {
        // Don't invalidate the underlying HTTP session if you are using it for something else
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();

    } 
	
	

	private void abrirLogin() {
		//LoginView login = new LoginView();
		//AltaFuncionarioView login = new AltaFuncionarioView();
		//VacacionesView login = new VacacionesView();
		/*Window ventana = new Window("", login);
		ventana.center();
		ventana.setResizable(true);
		ventana.setModal(true);
		ventana.setWidth("70%");
		ventana.setHeight("70%");
		//ventana.setClosable(false);
		//ventana.setResizable(true);
		UI.getCurrent().addWindow(ventana);*/
		
		
		PersonaAbmView alta = new PersonaAbmView();
		//AltaPersonaView alta = new AltaPersonaView();
		//AltaCustomerView alta = new AltaCustomerView();
		//AltaFuncionarioView login = new AltaFuncionarioView();
		//VacacionesView login = new VacacionesView();
		Window ventana = new Window("", alta);
		ventana.center();
		ventana.setResizable(true);
		ventana.setModal(true);
		ventana.setWidth("100%");
		ventana.setHeight("100%");
		//ventana.setClosable(false);
		//ventana.setResizable(true);
		UI.getCurrent().addWindow(ventana);
	}

	
}
