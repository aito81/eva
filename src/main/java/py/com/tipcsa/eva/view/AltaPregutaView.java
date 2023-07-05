package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.jpa.JpaPreguntas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.StringUtils;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "altaPregunta", displayName = "Alta Pregunta")
public class AltaPregutaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout datosBotonLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private TextField txtPregunta;
	private TextField txtPeso;
	
	private Button btnVolver;
	private Button btnAgregar;
	
	private Grid<Pregunta> gridPregunta;
	
	private JpaPreguntas jpaPre = new JpaPreguntas(JpaUtil.getEntityManagerFactory());
	
	
	public AltaPregutaView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Mantenimiento de Preguntas.");
		
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		btnAgregar.addClickListener(e -> agregarPregunta());
		
		/*gridPregunta.addItemClickListener(new ItemClickListener<Pregunta>() {

			@Override
			public void itemClick(ItemClick<Pregunta> event) {
				if (event.getColumn().getId().equals("borrar")) {
					
					borrarPregunta(event.getItem());
				
				}
				
				if (event.getColumn().getId().equals("editar")) {
					
					gridPregunta.getEditor().setEnabled(true);
					gridPregunta.getEditor().editRow(event.getRowIndex());
					gridPregunta.getEditor().setSaveCaption("Guardar");
					gridPregunta.getEditor().setCancelCaption("Cancelar");
					
				}
				
			}

			
		});*/
		
		
		gridPregunta.addItemClickListener(e -> {
			
			if (e.getMouseEventDetails().isDoubleClick()) {
				
				sacarPregunta(e.getItem());
			}
			
		});
		
	}

	private void sacarPregunta(Pregunta item) {
		
		try {
			jpaPre.destroy(item.getPregunta());
			Notification.show("Pregunta eliminada correctamente");
			gridPregunta.setItems(jpaPre.findPreguntaEntities());
		} catch (Exception e) {
			Notification.show("Error al eliminar pregunta >> "+ e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

	private void borrarPregunta(Pregunta pregun) {
		
		try {
			jpaPre.destroy(pregun.getPregunta());
			Notification.show("Pregunta eliminada correctamente.");
			gridPregunta.setItems(jpaPre.findPreguntaEntities());
		} catch (Exception e) {
			Notification.show("Error al eliminar pregunta >> " + e.getMessage());
		}
		
	}
	
	

	private void agregarPregunta() {
		
		if (verificarDatos()) {
			return;
		}
		
		Pregunta addPregu = new Pregunta();
		addPregu.setPregunta(1);
		addPregu.setDescripcion(txtPregunta.getValue());
		//String valor = txtPeso.getValue().replace(",", ".");
		//addPregu.setPeso(Double.valueOf(valor));
		addPregu.setActivo(true);
		try {
			jpaPre.create(addPregu);
			Notification.show("Pregunta agregada correctamente.");
			//txtPeso.clear();
			txtPregunta.clear();
			txtPregunta.focus();
			gridPregunta.setItems(jpaPre.findPreguntaEntities());
		} catch (Exception e) {
			Notification.show("Error al agregar pregunta >> " + e.getMessage());
		}
	}


	private boolean verificarDatos() {
		
		if (txtPregunta.isEmpty()) {
			Notification.show("La pregunta no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			txtPregunta.focus();
			return true;
		}
		
/*		if (txtPeso.isEmpty()) {
			Notification.show("El peso no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			txtPeso.focus();
			return true;
		}*/
		
		return false;
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
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
		
		
		gridLayout = buildGridLayout();
		formLayout.addComponent(gridLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		
		
		return formLayout;
	}


	private VerticalLayout buildGridLayout() {
		
		gridLayout = new VerticalLayout();
		gridLayout.setMargin(false);
		
		gridPregunta = new Grid<Pregunta>();
		gridPregunta.setItems(jpaPre.findPreguntaEntities());
		gridPregunta.addColumn(Pregunta::getDescripcion).setCaption("Pregunta").setId("pregunta");
		//gridPregunta.addColumn(Pregunta::getPeso).setCaption("Peso").setId("peso");
		
		
		/*gridPregunta.addColumn(Persona -> FontAwesome.EDIT.getHtml(), new HtmlRenderer()).setId("editar").setStyleGenerator(
				matriz -> "align-center").setWidth(100).setCaption("editar");*/		
		
		
		gridPregunta.addColumn(persona -> FontAwesome.TRASH.getHtml(), new HtmlRenderer()).setId("borrar").setStyleGenerator( 
				matriz -> "align-center").setWidth(100).setCaption("borrar");
		
		
		gridPregunta.setWidth("80%");
		gridLayout.addComponent(gridPregunta);
		
		return gridLayout;
	}


	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		
		btnVolver = new Button();
		btnVolver.setCaption("Volver");
		btnVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnVolver);
		
		
		
		return botonLayout;
	}


	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setMargin(false);
		
		txtPregunta = new TextField();
		txtPregunta.setCaption("Pregunta:");
		txtPregunta.setWidth("80%");
		txtPregunta.addValueChangeListener(e -> txtPregunta.setValue(txtPregunta.getValue().toUpperCase()));
		datosLayout.addComponent(txtPregunta);
		
		
		datosBotonLayout = buildDatosBotonLayout();
		datosLayout.addComponent(datosBotonLayout);
		
		
		return datosLayout;
	}


	private HorizontalLayout buildDatosBotonLayout() {
		
		datosBotonLayout = new HorizontalLayout();
		datosBotonLayout.setMargin(false);
		
		/*txtPeso = new TextField();
		txtPeso.setCaption("Peso de la pregunta.");
		txtPeso.addBlurListener(e -> {
			if (!txtPeso.isEmpty()) {
				String valor = txtPeso.getValue().replace(",", ".");
				if (!StringUtils.isDoubleNumeric(valor)) {
					Notification.show("El peso debe de ser numerico.", Notification.TYPE_ERROR_MESSAGE);
					txtPeso.clear();
					txtPeso.focus();
				}
			}
			
		});
		datosBotonLayout.addComponent(txtPeso);*/
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		datosBotonLayout.addComponent(btnAgregar);
		datosBotonLayout.setComponentAlignment(btnAgregar, Alignment.BOTTOM_CENTER);
		
		return datosBotonLayout;
	}

}
