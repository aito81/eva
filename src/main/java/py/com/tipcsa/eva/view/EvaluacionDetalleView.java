package py.com.tipcsa.eva.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.CargoPregunta;
import py.com.tipcsa.eva.entities.Evaluacion;
import py.com.tipcsa.eva.entities.EvaluacionDetalle;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.entities.Valor;
import py.com.tipcsa.eva.jpa.JpaCargoPregunta;
import py.com.tipcsa.eva.jpa.JpaEvaluacionDetalle;
import py.com.tipcsa.eva.util.JpaUtil;

public class EvaluacionDetalleView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout tituloLayout;
	private HorizontalLayout botonLayout;
	private VerticalLayout imagenLayout;
	private VerticalLayout nombreLayout;
	
	private Label lblNombre;
	private Label lblCargo;
	
	private Button btnPregunta;
	
	private Window ventana;
	
	private JpaCargoPregunta jpaCarPre = new JpaCargoPregunta(JpaUtil.getEntityManagerFactory());
	private JpaEvaluacionDetalle jpaEvaDet = new JpaEvaluacionDetalle(JpaUtil.getEntityManagerFactory());
	
	private Evaluacion evaluacion;
	
	private List<EvaluacionDetalle> listEvDet = new ArrayList<EvaluacionDetalle>();
	
	private int cont = 0;
	
	private Button btnGuardar;
	private Button btnCancelar;
	
	public boolean guardado = false;
	
	
	
	
	public List<EvaluacionDetalle> getListEvDet() {
		return listEvDet;
	}


	public void setListEvDet(List<EvaluacionDetalle> listEvDet) {
		this.listEvDet = listEvDet;
	}


	public EvaluacionDetalleView(Persona persona) {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblNombre.setValue(persona.getNombre() + " "+ persona.getApellido());
		lblCargo.setValue(persona.getCargo().getDescripcion());
		
		cargarPreguntas(persona.getCargo());
		
		evaluacion = new Evaluacion();
		evaluacion.setEvaluacion(1);
		
		btnCancelar.addClickListener(e -> cerrar());
		btnGuardar.addClickListener(e -> guardar());
		
	}


	private void guardar() {
		
		for (int i = 0; i < datosLayout.getComponentCount(); i++) {
			
			if (datosLayout.getComponent(i).isEnabled()) {
				
				Notification.show("No se terminaron de responder las preguntas.");
				
				return;
			}
		}
		guardado = true;
		cerrar();
		
	}


	private void cerrar() {
		// TODO Auto-generated method stub
		Window w = this.findAncestor(Window.class);
		w.close();
	}


	private void cargarPreguntas(Cargo cargo) {
		
		
		for (CargoPregunta cargoPre : jpaCarPre.findPreguntaByCargo(cargo)) {
			
			
			
			btnPregunta = new Button();
			btnPregunta.setCaption(cargoPre.getPregunta().getDescripcion());
			btnPregunta.setId(String.valueOf(cont));
			datosLayout.addComponent(btnPregunta, cont);
			datosLayout.setComponentAlignment(btnPregunta, Alignment.MIDDLE_CENTER);
			btnPregunta.setWidth("100%");
			cont++;
			btnPregunta.addClickListener(e -> abrirValores(cargoPre.getPregunta(), Integer.valueOf(e.getButton().getId())));
			
			
			
		}
		
	}


	private void abrirValores(Pregunta pregunta, int i) {
		
		CalificacionView cal = new CalificacionView(pregunta);
		ventana = new Window("Valores", cal);
		ventana.center();
		//ventana.setSizeFull();
		UI.getCurrent().addWindow(ventana);
		ventana.addCloseListener(e -> {
			if (cal.guardado) {
				CalEvaluacionDetalle(cal.getValor(), pregunta);
				datosLayout.getComponent(i).setEnabled(false);
				datosLayout.getComponent(i).setStyleName(ValoTheme.BUTTON_FRIENDLY);
				
			}
			
		});
		
	}


	private void CalEvaluacionDetalle(Valor valor, Pregunta pregunta) {
		
		EvaluacionDetalle evDet = new EvaluacionDetalle();
		evDet.setEvaluacion(evaluacion);
		evDet.setEvaluacionDetalle(1);
		evDet.setPregunta(pregunta);
		evDet.setValor(valor);
		listEvDet.add(evDet);
		
		
		
		
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
		formLayout.setMargin(false);
		
		tituloLayout = buildTituloLayout();
		formLayout.addComponent(tituloLayout);
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		formLayout.setComponentAlignment(botonLayout, Alignment.BOTTOM_CENTER);
		
		formLayout.setComponentAlignment(botonLayout, new Alignment(20));
		
		return formLayout;
	}


	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		//botonLayout.setWidth("100%");
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnGuardar);
		
		btnCancelar = new Button();
		btnCancelar.setCaption("Cancelar");
		btnCancelar.addStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnCancelar);
		
		return botonLayout;
	}


	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setWidth("100%");
		datosLayout.setHeight("100%");
		datosLayout.setMargin(false);
		return datosLayout;
	}


	private HorizontalLayout buildTituloLayout() {
		
		tituloLayout = new HorizontalLayout();
		tituloLayout.setMargin(false);
		
		imagenLayout = buildImagenLayout();
		tituloLayout.addComponent(imagenLayout);
		
		nombreLayout = buildNombreLayout();
		tituloLayout.addComponent(nombreLayout);
		
		return tituloLayout;
	}


	private VerticalLayout buildNombreLayout() {
		
		nombreLayout = new VerticalLayout();
		nombreLayout.setMargin(false);
		
		lblNombre = new Label();
		lblNombre.setCaption("Nombre:");
		nombreLayout.addComponent(lblNombre);
		
		lblCargo = new Label();
		lblCargo.setCaption("Cargo:");
		nombreLayout.addComponent(lblCargo);
		
		
		
		return nombreLayout;
	}


	private VerticalLayout buildImagenLayout() {
		
		imagenLayout = new VerticalLayout();
		imagenLayout.setMargin(false);
		
		return imagenLayout;
	}
	
}
