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

import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.PuestoGrupo;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.jpa.JpaPuestoGrupo;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.jpa.JpaPersonas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "cargoGrupo", displayName = "Asignar evaluador a grupos")
public class CargoGrupoAbmView extends CustomComponent implements View{

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout datosLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private ComboBox<Cargo> cbxCargo;
	private ComboBox<Grupo> cbxGrupo;
	
	private Button btnAgregar;
	private Button btnVolver;
	
	private Grid<PuestoGrupo> gridEvaluadorGrupo;
	
	private JpaPersonas jpaPer = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	private JpaGrupos jpaGru = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	private JpaCargo jpaCar = new JpaCargo(JpaUtil.getEntityManagerFactory());
	//private Jpacar
	private JpaPuestoGrupo jpaEvaGru = new JpaPuestoGrupo(JpaUtil.getEntityManagerFactory());
	
	
	public CargoGrupoAbmView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		cbxCargo.addValueChangeListener(e -> {
			cargarComboGrupo(e.getValue());
			cargarGrilla(e.getValue());
			
		});
		
		btnAgregar.addListener(e -> addEvaGrupo(cbxCargo.getValue(), cbxGrupo.getValue()));
		
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		gridEvaluadorGrupo.addItemClickListener(e -> {
			if (e.getMouseEventDetails().isDoubleClick()) {
				
			}
			sacarGrupo(e.getItem());
		});
	}


	private void sacarGrupo(PuestoGrupo delEvaGru) {
		Cargo car = new Cargo();
		car = delEvaGru.getPuesto();
		try {
			jpaEvaGru.destroy(delEvaGru.getPuestoGrupo());
			cbxGrupo.clear();
			cargarComboGrupo(car);
			cargarGrilla(car);
			Notification.show("Grupo desasignado correctamente", Notification.TYPE_WARNING_MESSAGE);
		} catch (Exception e) {
			Notification.show(e.getMessage() + " Error al desasignar grupo de evaluador.", Notification.TYPE_ERROR_MESSAGE);
		}
		
	}


	private void addEvaGrupo(Cargo value, Grupo value2) {
		
		if (verificarDatos()) {
			return;
		}
		try {
			PuestoGrupo addEvaGru = new PuestoGrupo();
			addEvaGru.setPuestoGrupo(1);
			addEvaGru.setPuesto(cbxCargo.getValue());
			addEvaGru.setGrupo(cbxGrupo.getValue());
			jpaEvaGru.create(addEvaGru);
			cbxGrupo.clear();
			cargarGrilla(addEvaGru.getPuesto());
			cargarComboGrupo(addEvaGru.getPuesto());
			Notification.show("Grupo asignado correctamente a evaluador.");
		} catch (Exception e) {
			Notification.show(e.getMessage() + " Error al asignar grupo a evaluador.", Notification.TYPE_ERROR_MESSAGE);
		}
	}


	private boolean verificarDatos() {
		
		if (cbxCargo.isEmpty()) {
			
			Notification.show("Debe ingresar el cargo evaluador", Notification.TYPE_ERROR_MESSAGE);
			cbxCargo.focus();
			return true;
			
		}
		
		if (cbxGrupo.isEmpty()) {
			
			Notification.show("Debe ingresar el grupo", Notification.TYPE_ERROR_MESSAGE);
			cbxGrupo.focus();
			return true;
		}
		
		return false;
	}


	private void cargarGrilla(Cargo cargo) {
		
		gridEvaluadorGrupo.setItems(jpaEvaGru.findEvaluadorGrupobyCargo(cargo));
		
	}


	private void cargarComboGrupo(Cargo cargo) {
		
		cbxGrupo.setItems(jpaGru.findGruposNotbyCargo(cargo));
		
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
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
		gridLayout = buildGridLayout();
		formLayout.addComponent(gridLayout);
		
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


	private VerticalLayout buildGridLayout() {
		
		gridLayout = new VerticalLayout();
		gridLayout.setMargin(false);
		
		gridEvaluadorGrupo = new Grid<PuestoGrupo>();
		gridEvaluadorGrupo.addColumn(EvaluadorGrupo -> EvaluadorGrupo.getGrupo().getDescripcion()).setCaption("Grupo").setId("grupo");
		gridLayout.addComponent(gridEvaluadorGrupo);
		
		
		
		
		
		return gridLayout;
	}


	private HorizontalLayout buildDatosLayout() {
		
		datosLayout = new HorizontalLayout();
		datosLayout.setSpacing(true);
		datosLayout.setWidth("100%");
		
		cbxCargo =  new ComboBox<Cargo>();
		cbxCargo.setCaption("Cargo");
		cbxCargo.setItems(jpaCar.findCargoEntities());
		cbxCargo.setItemCaptionGenerator(Cargo::getDescripcion);
		cbxCargo.setEmptySelectionAllowed(false);
		datosLayout.addComponent(cbxCargo);
		
		cbxGrupo = new ComboBox<Grupo>();
		cbxGrupo.setCaption("Grupos de Preguntas");
		cbxGrupo.setEmptySelectionAllowed(false);
		cbxGrupo.setItemCaptionGenerator(Grupo::getDescripcion);
		cbxGrupo.setWidth("90%");
		datosLayout.addComponent(cbxGrupo);
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		datosLayout.addComponent(btnAgregar);
		
		datosLayout.setComponentAlignment(btnAgregar, Alignment.BOTTOM_CENTER);
		
		
		return datosLayout;
	}
	
	
	
}
