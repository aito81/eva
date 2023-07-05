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
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "cargo", displayName = "Cargos")
public class CargoAbmView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private Grid<Cargo> gridCargo;
	private Button btnAgregar;
	private Button btnVolver;
	
	private JpaCargo jpaCargo = new JpaCargo(JpaUtil.getEntityManagerFactory());
	
	public CargoAbmView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Mantenimiento de Cargos");
		btnVolver.addClickListener(e -> volver());
		btnAgregar.addClickListener(e -> irAgregar());
		gridCargo.addItemClickListener(e -> {
			
			if (e.getMouseEventDetails().isDoubleClick()) {
				
				sacarCargo(e.getItem());
			}
			
		});
	}
	
	private void sacarCargo(Cargo item) {
		
		try {
			jpaCargo.destroy(item.getCargo());
			gridCargo.setItems(jpaCargo.findCargoEntities());
			Notification.show("Cargo eliminado");
		} catch (Exception e) {
			Notification.show("Error al eliminar cargo >> " + e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

	private void irAgregar() {
		
		EvaUI.getCurrent().getNavigator().navigateTo("altaCargo");
		
	}

	private void volver() {
		
		EvaUI.getCurrent().getNavigator().navigateTo("");
		
	}

	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(false);
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
		
		gridLayout = buildGridLayout();
		formLayout.addComponent(gridLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		botonLayout.setWidth("100%");
		botonLayout.setHeight("100%");
		
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
		
		gridCargo = new Grid<Cargo>();
		gridCargo.setItems(jpaCargo.findCargoEntities());
		gridCargo.addColumn(cargo-> cargo.getCargo()).setCaption("Nro").setId("nro");
		gridCargo.addColumn(Cargo::getDescripcion).setCaption("Cargo").setId("Cargo");
		
		gridCargo.setSizeFull();
		
		gridLayout.addComponent(gridCargo);
		
		
		return gridLayout;
	}
	
}
