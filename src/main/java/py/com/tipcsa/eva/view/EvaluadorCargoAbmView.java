package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.EvaluadorCargo;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.jpa.JpaEvaluadorCargo;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;



@ViewConfig(uri = "evaluadorCargo", displayName = "Asignar evaluador a cargo")
public class EvaluadorCargoAbmView extends CustomComponent implements View  {
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout datosLayout;
	private VerticalLayout grillaLayout;
	private HorizontalLayout botonLayout;
	
	private ComboBox<Cargo>cbxEvaluador;
	private ComboBox<Cargo>cbxCargo;
	
	private Grid<EvaluadorCargo> gridEvaCar;
	
	private Button btnAgregar;
	private Button btnVolver;
	
	
	
	private JpaCargo jpaCar = new JpaCargo(JpaUtil.getEntityManagerFactory());
	private JpaEvaluadorCargo jpaEvaCar = new JpaEvaluadorCargo(JpaUtil.getEntityManagerFactory());
	
	
	public EvaluadorCargoAbmView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Asignar Evaluador a Cargos.");
		
		cbxEvaluador.addValueChangeListener(e -> {
			cbxCargo.clear();
			cargarComboCargo(e.getValue());
			cargarGrilla(e.getValue());
		});
		
		btnAgregar.addClickListener(e -> agregar(cbxEvaluador.getValue(), cbxCargo.getValue() ));
		
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		gridEvaCar.addItemClickListener(e -> {
			if (e.getMouseEventDetails().isDoubleClick()) {
		
				sacarCargo(e.getItem());
			}
		});
	}

	private void sacarCargo(EvaluadorCargo item) {
		
		try {
			jpaEvaCar.destroy(item.getEvaluadorCargo());
			cargarComboCargo(item.getEvaluador());
			cargarGrilla(item.getEvaluador());
			
		} catch (Exception e) {
			Notification.show("Error al eliminar cargo de evaluador >> " + e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

	private void agregar(Cargo eva, Cargo cargo) {
		
		EvaluadorCargo addEvaCar = new EvaluadorCargo();
		addEvaCar.setEvaluadorCargo(1);
		addEvaCar.setCargo(cargo);
		addEvaCar.setEvaluador(eva);
		
		try {
			jpaEvaCar.create(addEvaCar);
			Notification.show("Evaluador asignado correctamente al cargo.");
			cargarGrilla(addEvaCar.getEvaluador());
			cbxCargo.clear();
			cargarComboCargo(addEvaCar.getEvaluador());
		} catch (Exception e) {
			Notification.show("Error al agregar evaluador al cargo >> " + e.getMessage());
		}
	}

	private void cargarGrilla(Cargo value) {
		
		gridEvaCar.setItems(jpaEvaCar.findEvaluadorCargobyCargo(value));
		
	}

	private void cargarComboCargo(Cargo cargo) {
		
		cbxCargo.setItems(jpaCar.findCargoNotInEvaluador(cargo));
		
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
		
		formLayout =  new VerticalLayout();
		formLayout.setMargin(false);
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
		grillaLayout = buildGrillaLayout();
		formLayout.addComponent(grillaLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		botonLayout.setMargin(false);
		
		btnVolver = new Button();
		btnVolver.setCaption("Volver");
		btnVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnVolver);
		
		return botonLayout;
	}

	private VerticalLayout buildGrillaLayout() {
		
		grillaLayout = new VerticalLayout();	
		grillaLayout.setMargin(false);
		
		gridEvaCar = new Grid<EvaluadorCargo>();
		gridEvaCar.addColumn(eva -> eva.getEvaluadorCargo()).setCaption("nro").setId("nro");
		gridEvaCar.addColumn(eva -> eva.getCargo().getDescripcion()).setCaption("Cargo").setId("cargo");
		grillaLayout.addComponent(gridEvaCar);
		
		return grillaLayout;
	}

	private HorizontalLayout buildDatosLayout() {
		
		datosLayout = new HorizontalLayout();
		datosLayout.setMargin(false);
		
		cbxEvaluador = new ComboBox<Cargo>();
		cbxEvaluador.setCaption("Evaluador:");
		cbxEvaluador.setItemCaptionGenerator(cargo -> cargo.getDescripcion());
		cbxEvaluador.setEmptySelectionAllowed(false);
		cbxEvaluador.setItems(jpaCar.findCargoEntities());
		datosLayout.addComponent(cbxEvaluador);
		
		cbxCargo = new ComboBox<Cargo>();
		cbxCargo.setCaption("Cargo a ser evaluado:");
		cbxCargo.setItemCaptionGenerator(cargo -> cargo.getDescripcion());
		cbxCargo.setEmptySelectionAllowed(false);
		datosLayout.addComponent(cbxCargo);
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		datosLayout.addComponent(btnAgregar);
		datosLayout.setComponentAlignment(btnAgregar,Alignment.BOTTOM_CENTER);
		
		
		return datosLayout;
	}

}
