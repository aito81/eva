package py.com.tipcsa.eva.view;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Usuario;
import py.com.tipcsa.eva.event.EventBus;
import py.com.tipcsa.eva.event.LogoutEvent;
import py.com.tipcsa.eva.event.NavigationEvent;
import py.com.tipcsa.eva.util.Broadcaster;
import py.com.tipcsa.eva.util.Broadcaster.BroadcastListener;
import py.com.tipcsa.eva.util.MyTheme;
import py.com.tipcsa.eva.util.UserUtil;

public class NavigationBar extends CssLayout implements ViewChangeListener, BroadcastListener {
	
	private static final long serialVersionUID = 1L;
	private Map<String, Button> buttonMap = new HashMap<>();
	private MenuItem settingsItem;
	
	
	public NavigationBar() {
		setHeight("100%");
        addStyleName(MyTheme.MENU_ROOT);
        addStyleName(MyTheme.NAVBAR);
        
        addComponent(buildTitle());
        addComponent(buildUserMenu());
        
        addLogoutButton();
        
        Broadcaster.register(this);
	}
	
	public NavigationBar(Component... children) {
		super(children);
	}
	
	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return true; // false blocks navigation, always return true here
	}
	
	@Override
	public void afterViewChange(ViewChangeEvent event) {
		buttonMap.values().forEach(button -> button.removeStyleName(MyTheme.SELECTED));
        Button button = buttonMap.get(event.getViewName());
        if (button != null) button.addStyleName(MyTheme.SELECTED);
	}
	
	
	private void addLogoutButton() {
        Button logout = new Button("Cerrar cuenta", click -> EventBus.post(new LogoutEvent()));
        addComponent(logout);

        logout.addStyleName(MyTheme.BUTTON_LOGOUT);
        logout.addStyleName(MyTheme.BUTTON_BORDERLESS);
        logout.setIcon(FontAwesome.SIGN_OUT);
    }
	
	public void addView(String uri, String displayName) {
        Button viewButton = new Button(displayName, click -> EventBus.post(new NavigationEvent(uri)));
        viewButton.addStyleName(MyTheme.MENU_ITEM);
        viewButton.addStyleName(MyTheme.BUTTON_BORDERLESS);
        buttonMap.put(uri, viewButton);

        addComponent(viewButton, components.size() - 1);
    }
	
	public void addMenuTitle(String title) {
		Label lblTitulo = new Label(title);
		lblTitulo.setWidth("-1px");
		lblTitulo.addStyleName("valo-menu-subtitle");
		lblTitulo.addStyleName("sub-titulo");
		
		addComponent(lblTitulo, components.size() - 1);
	}
	
	private Component buildTitle() {
        Label logo = new Label(" <strong>EVA</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        //logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }
	
	
	private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        Usuario usuario = UserUtil.getUsuario();
        if (usuario.getPersona().getFoto() != null) {
        	File archivo = new File(usuario.getPersona().getFoto());
        	settingsItem = settings.addItem("", new FileResource(archivo), null);
        } else 
        	settingsItem = settings.addItem("", new ThemeResource(
                    "images/cabeceraImagen.png"), null);
        //}
        settingsItem.setText(usuario.getDescripcion());
        
        
        
        settingsItem.addItem("Cambiar clave", new Command() {
            
			private static final long serialVersionUID = 1L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
            	//EventBus.post(new NavigationEvent("perfil"));
				EvaUI.getCurrent().getNavigator().navigateTo("perfil");
            }
        });
//        settingsItem.addItem("Preferencias", new Command() {
//            /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//            public void menuSelected(final MenuItem selectedItem) {
//            	Notification.show("No implementado", Notification.Type.WARNING_MESSAGE);
//            }
//        });
        settingsItem.addSeparator();
        settingsItem.addItem("Cerrar Sesion", new Command() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
            	EventBus.post(new LogoutEvent());
            }
        });
        return settings;
    }
	
	public void setMenuBadge() {
		Iterator<Component> iter = iterator();
		while (iter.hasNext()) {
			Component component = iter.next();
			if (component instanceof Button) {
	            Button button = (Button) component;
	            if (button.getCaption().length() >= 17) {
		            if (button.getCaption().substring(0, 17).equalsIgnoreCase("calendario")) {
		            	String folder = (String) button.getCaption().substring(0, 17);
		            	String badgeText = "0";
		                String captionFormat = badgeText.equals("0") ? ""
		                        : "%s <span class=\"valo-menu-badge\">%s</span>";
		                if (captionFormat.isEmpty()) {
		                    component.setCaption(folder);
		                } else {
		                    component.setCaption(
		                            String.format(captionFormat, folder, badgeText));
		                    ((Button) component).setHtmlContentAllowed(true);
		                   // ((Button) component).setImmediate(true);
		                }
		            }
	            }
	        }
		}
    }

	@Override
	public void receiveBroadcast(String message) {
		// Must lock the session to execute logic safely
        getUI().access(new Runnable() {
            @Override
            public void run() {
            	setMenuBadge();
            }
        });
	}
	
	@Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }
	
	
	
	
}
