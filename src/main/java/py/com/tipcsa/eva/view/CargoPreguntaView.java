package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.CargoPregunta;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.jpa.JpaCargoPregunta;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.jpa.JpaPreguntas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.StringUtils;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "cargoPregunta", displayName = "Asignar Preguntas al Cargo")
public class CargoPreguntaView extends CustomComponent implements View{

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout datosLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private ComboBox<Cargo> cbxCargo;
	private ComboBox<Pregunta> cbxPregunta;
	
	private Button btnAgregar;
	private Button btnVolver;
	
	private Grid<CargoPregunta> gridCargoPregunta;
	
	private TextField txtPeso;
	
	private JpaCargo jpaCar = new JpaCargo(JpaUtil.getEntityManagerFactory());
	private JpaPreguntas jpaPre = new JpaPreguntas(JpaUtil.getEntityManagerFactory());
	private JpaCargoPregunta jpaCargoPregunta = new JpaCargoPregunta(JpaUtil.getEntityManagerFactory());
	private JpaGrupos jpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	
	String css = ".v-grid-header .v-grid-cell{\n" + 
			"  	color: blue;\n" + 
			  
			"  }";
	
	public CargoPreguntaView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Asignar Preguntas a Cargos.");
		
		cbxPregunta.setWidth("500px");
	}


	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		setWidth("100%");
		setHeight("-1px");
		
		formLayout = buildFormlayout();
		mainLayout.addComponent(formLayout);
		
		cbxCargo.addValueChangeListener(e -> {
			
//			cbxPregunta.setItems(jpaCargoPregunta.findPreguntaByNotCargo(e.getValue()));
			
			cbxPregunta.setItems(jpaCargoPregunta.findPreguntaByNotCargoInGrupo(e.getValue()));
			
			gridCargoPregunta.setItems(jpaCargoPregunta.findPreguntaByCargo(e.getValue()));
			
			gridCargoPregunta.sort("grupo");
			
		});
		
		btnAgregar.addClickListener(e -> agregarCargoPregunta(cbxCargo.getValue(), cbxPregunta.getValue()));
		
		btnVolver.addClickListener(e-> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		gridCargoPregunta.addItemClickListener(e -> {
			if (e.getMouseEventDetails().isDoubleClick()) {
		
				sacarPregunta(e.getItem());
			}
		});
		
		
	}


	private void sacarPregunta(CargoPregunta item) {
		
		try {
			jpaCargoPregunta.destroy(item.getCargoPregunta());
//			cbxPregunta.setItems(jpaCargoPregunta.findPreguntaByNotCargo(item.getCargo()));
			cbxPregunta.setItems(jpaCargoPregunta.findPreguntaByNotCargoInGrupo(item.getCargo()));
			
			gridCargoPregunta.setItems(jpaCargoPregunta.findPreguntaByCargo(item.getCargo()));
			gridCargoPregunta.sort("grupo");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}


	private void agregarCargoPregunta(Cargo cargo, Pregunta pregunta) {
		
		if (verificarDatos()) {
			
			return;
		}
		
		CargoPregunta addCarPre = new CargoPregunta();
		addCarPre.setCargoPregunta(1);
		addCarPre.setCargo(cbxCargo.getValue());
		addCarPre.setPregunta(cbxPregunta.getValue());
		String valor = txtPeso.getValue().replace(",", ".");
		addCarPre.setPeso(Double.valueOf(valor));
		
		try {
			jpaCargoPregunta.create(addCarPre);
			Notification.show("Pregunta agregada correctamente");
//			cbxPregunta.setItems(jpaCargoPregunta.findPreguntaByNotCargo(addCarPre.getCargo()));
			cbxPregunta.setItems(jpaCargoPregunta.findPreguntaByNotCargoInGrupo(addCarPre.getCargo()));
			gridCargoPregunta.setItems(jpaCargoPregunta.findPreguntaByCargo(addCarPre.getCargo()));
			gridCargoPregunta.sort("grupo");
			cbxPregunta.clear();
			txtPeso.clear();
		} catch (Exception e) {
			Notification.show("Error al asignar pregunta >> "+ e.getMessage());
		}
		
		
		
	}


	private boolean verificarDatos() {
		
		if (cbxCargo.isEmpty()) {
			
			Notification.show("El cargo no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			cbxCargo.focus();
			return true;
			
		}
		
		if (cbxPregunta.isEmpty()) {
			
			Notification.show("Debe seleccionar una pregunta", Notification.TYPE_ERROR_MESSAGE);
			cbxPregunta.focus();
			return true;
		}
		
		if (txtPeso.isEmpty()) {
			Notification.show("El peso no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			txtPeso.focus();
			return true;
		}
		
		String valor = txtPeso.getValue().replace(",", ".");
		if (!StringUtils.isDoubleNumeric(valor)) {
			
			Notification.show("El valor del peso debe de ser numerico", Notification.TYPE_ERROR_MESSAGE);
			txtPeso.focus();
			return true;
		}
		
		
		return false;
	}


	private VerticalLayout buildFormlayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setMargin(false);
		formLayout.setWidth("100%");
		
		datosLayout = buildDatoslayout();
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
		
		gridCargoPregunta = new Grid<CargoPregunta>();
		//gridCargoPregunta.setStyleName("v-grid-header");
		//gridCargoPregunta.addColumn(cargoPregunta -> cargoPregunta.getCargoPregunta()).setCaption("Nro").setId("nro");
		gridCargoPregunta.addColumn(cargoPregunta -> cargoPregunta.getPregunta().getDescripcion()).setCaption("Pregunta").setId("pregunta");
		gridCargoPregunta.addColumn(cargoPregunta -> {
			
			Grupo gru = jpaGrupo.findGruposPruguntaAndCargo(cargoPregunta.getCargo(), cargoPregunta.getPregunta());
			
			if (gru != null) {
				
				return gru.getDescripcion();
				
			}else {
			
				return null;
			}
			
			
			
		}).setId("grupo").setCaption("Grupo").setWidth(200);
		gridCargoPregunta.addColumn(cargoPregunta -> cargoPregunta.getPeso()).setCaption("Peso").setId("Peso").setWidth(80);
		gridLayout.addComponent(gridCargoPregunta);
		gridCargoPregunta.setWidth("100%");
		
		return gridLayout;
	}


	private HorizontalLayout buildDatoslayout() {
		
		datosLayout = new HorizontalLayout();
//		datosLayout.setWidth("100%");
		datosLayout.setMargin(false);
//		datosLayout.setSpacing(false);
		
		cbxCargo = new ComboBox<Cargo>();
		cbxCargo.setCaption("Cargo");
		cbxCargo.setItemCaptionGenerator(cargo -> cargo.getDescripcion());
		cbxCargo.setEmptySelectionAllowed(false);
		cbxCargo.setItems(jpaCar.findCargoEntities());
//		cbxCargo.setWidth("40%");
		datosLayout.addComponent(cbxCargo);
		
		cbxPregunta = new ComboBox<Pregunta>();
		cbxPregunta.setCaption("Preguntas en Grupo");
		cbxPregunta.setItemCaptionGenerator(pregunta -> pregunta.getDescripcion());
		cbxPregunta.setEmptySelectionAllowed(false);
//		cbxPregunta.setWidth("40%");
		datosLayout.addComponent(cbxPregunta);
		
		txtPeso = new TextField();
		txtPeso.setCaption("Peso");
//		txtPeso.setWidth("20%");
		datosLayout.addComponent(txtPeso);
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
//		btnAgregar.setWidth("20%");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		datosLayout.addComponent(btnAgregar);
		datosLayout.setComponentAlignment(btnAgregar, Alignment.BOTTOM_CENTER);
		
		return datosLayout;
	}
}
