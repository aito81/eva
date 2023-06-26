package py.com.tipcsa.eva.view;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.util.LazyProvider;
import py.com.tipcsa.eva.util.MyTheme;
import py.com.tipcsa.eva.util.PageTitleUpdater;
import py.com.tipcsa.eva.util.ViewConfig;

public class Main extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	
	private NavigationBar navigationBar;
	private Navigator navigator;
	private Panel content;
	
	
	public Main() {
		addStyleName(MyTheme.MAIN_LAYOUT);
		
		setSizeFull();
		
		initLayouts();
		SetupNavigator();
	}
	
	
	private void initLayouts() {

	 	navigationBar = new NavigationBar();
        // Use panel as main content container to allow it's content to scroll
        content = new Panel();
        content.setSizeFull();
        content.addStyleName(MyTheme.PANEL_BORDERLESS);

        addComponents(navigationBar, content);
        setExpandRatio(content, 1);
	
	}
	
	
	private void registerViews() {
		
		navigationBar.addMenuTitle("Opciones");
			addView(PrincipalView.class);
			addView(PersonaAbmView.class);
		/*addView(VacacionesView.class);
		addHiddenView(AltaFuncionarioView.class);
		addHiddenView(PedidoVacacionesView.class);
		addHiddenView(FeriadosMantenimientoView.class);
		addHiddenView(AprobacionVacacionesView.class);
		addHiddenView(DepartamentoAbm.class);*/
		navigationBar.addMenuTitle("Administracion");
		
	}
	
	private void SetupNavigator() {
		
		navigator = new Navigator(EvaUI.getCurrent(), content);
		
		registerViews();
		
		navigator.addViewChangeListener(navigationBar);
		navigator.addViewChangeListener(new PageTitleUpdater());
		
		navigator.navigateTo(navigator.getState());
		
	}
	
	private void addHiddenView(Class<? extends View> viewClass){
		ViewConfig viewConfig = viewClass.getAnnotation(ViewConfig.class);
		
		switch (viewConfig.createMode()) {
			case ALWAYS_NEW:
				navigator.addView(viewConfig.uri(), viewClass);
				break;
			case LAZY_INIT:
				navigator.addProvider(new LazyProvider(viewConfig.uri(),viewClass));
				break;
			case EAGER_INIT:
				try {
					navigator.addView(viewConfig.uri(), viewClass.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		}
		
	}
	
	private void addView(Class<? extends View> viewClass) {
        ViewConfig viewConfig = viewClass.getAnnotation(ViewConfig.class);

        switch (viewConfig.createMode()) {
            case ALWAYS_NEW:
                navigator.addView(viewConfig.uri(), viewClass);
                break;
            case LAZY_INIT:
                //navigator.addProvider(new LazyProvider(viewConfig.uri(), viewClass));
                break;
            case EAGER_INIT:
//                try {
//                    navigator.addView(viewConfig.uri(), viewClass.newInstance());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
        }
        navigationBar.addView(viewConfig.uri(), viewConfig.displayName());
    }
	
	@AutoGenerated
	private void buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new AbsoluteLayout();
	}
	
	
	
	
	
	
	

}