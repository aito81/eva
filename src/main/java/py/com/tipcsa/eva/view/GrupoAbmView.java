package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;


@ViewConfig(uri = "grupo", displayName = "Grupos")
public class GrupoAbmView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private Grid<Grupo> gridGrupo;
	
	private Button btnVolver;
	private Button btnAgregar;
	
	private JpaGrupos jpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	public GrupoAbmView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Mantenimiento de Grupos.");
		
		btnAgregar.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo("altaGrupo"));
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		gridGrupo.addItemClickListener(e -> {
			
			if (e.getMouseEventDetails().isDoubleClick()) {
				
				sacarGrupo(e.getItem());
			}
			
		});
	}

	private void sacarGrupo(Grupo item) {
		
		try {
			jpaGrupo.destroy(item.getGrupo());
			gridGrupo.setItems(jpaGrupo.findGrupoEntities());
		} catch (Exception e) {
			Notification.show("Error al borrar grupo >> "+ e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		
		setWidth("100%");
		setHeight("-1px");
		
		formLayout = buildFormLayout();
		mainLayout.addComponent(formLayout);
		
	}

	private VerticalLayout buildFormLayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setWidth("100%");
		formLayout.setHeight("-1px");
		formLayout.setMargin(false);
		
		gridLayout = buildGridLayout();
		formLayout.addComponent(gridLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		
		return formLayout;
	}

	
	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		
		btnVolver = new Button();
		btnVolver.setCaption("Volver");
		btnVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnVolver);
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
		
		return botonLayout;
	}

	private VerticalLayout buildGridLayout() {
		
		gridLayout = new VerticalLayout();
		gridLayout.setWidth("100%");
		gridLayout.setHeight("100%");
		gridLayout.setMargin(false);
		
		gridGrupo = new Grid<Grupo>();
		gridGrupo.setItems(jpaGrupo.findGrupoEntities());
		gridGrupo.addColumn(Grupo::getGrupo).setCaption("Nro").setId("nro");
		gridGrupo.addColumn(Grupo::getDescripcion).setCaption("Grupo").setId("grupo");
		
		gridLayout.addComponent(gridGrupo);
		
		
		
		return gridLayout;
	}
	
	
	
}
