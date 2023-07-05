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
import py.com.tipcsa.eva.entities.Area;
import py.com.tipcsa.eva.jpa.JpaArea;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "area", displayName = "area")
public class AreaAbmView extends CustomComponent implements View{
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private Grid<Area> gridArea;
	
	private Button btnAgregar;
	private Button btnVolver;
	
	JpaArea jpaArea = new JpaArea(JpaUtil.getEntityManagerFactory());
	
	
	public AreaAbmView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Mantenimiento de Areas.");
		
		btnAgregar.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo("altaArea"));
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		gridArea.addItemClickListener(e -> {
			
			if (e.getMouseEventDetails().isDoubleClick()) {
				
				sacarArea(e.getItem());
			}
			
		});
		
	}


	private void sacarArea(Area item) {
		
		try {
			jpaArea.destroy(item.getArea());
			Notification.show("Area Eliminada exitosamente.");
			gridArea.setItems(jpaArea.findAreaEntities());
		} catch (Exception e) {
			Notification.show("Error al eliminar Area >> " + e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
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
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
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


	private VerticalLayout buildFormLayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setWidth("100%");
		formLayout.setHeight("-1px");
		formLayout.setMargin(false);
		
		gridLayout = buildGridLayout();
		formLayout.addComponent(gridLayout);
		
		
		return formLayout;
	}


	private VerticalLayout buildGridLayout() {
		
		gridLayout = new VerticalLayout();
		gridLayout.setWidth("100%");
		gridLayout.setHeight("100%");
		gridLayout.setMargin(false);
		
		gridArea = new Grid<Area>();
		gridArea.setItems(jpaArea.findAreaEntities());
		gridArea.addColumn(Area::getArea).setCaption("Nro:").setId("nro");
		gridArea.addColumn(Area::getDescripcion).setCaption("Area").setId("area");
		
		gridLayout.addComponent(gridArea);
		
	
		return gridLayout;
	}
	
	

}
