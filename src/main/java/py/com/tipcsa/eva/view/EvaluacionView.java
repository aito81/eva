package py.com.tipcsa.eva.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.jpa.JpaPuestoGrupo;
import py.com.tipcsa.eva.jpa.JpaPersonas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.UserUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "evaluacion", displayName = "Evaluaciones")
public class EvaluacionView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout botonLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout itemContainerLayout;
	private HorizontalLayout itemLayout;
	private VerticalLayout fotoLayout;
	private VerticalLayout contenidoLayout;
	
	private Button btnBoton;
	private Button btnVolver;
	
	private Window ventana;
	
	private Label lblNombre;
	private Label lblCargo;
	private Label lblCorreo;
	
	private JpaPuestoGrupo jpaEvaGru = new JpaPuestoGrupo(JpaUtil.getEntityManagerFactory());
	private JpaPersonas jpaPer = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	private JpaCargo jpaCar = new JpaCargo(JpaUtil.getEntityManagerFactory());
	
	int cantidad = 0;
	
	public EvaluacionView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Evaluaciones.");
		
		cantidad = 13;
		crearItemContainerLayout();
		
		
		
		
		cargarPersonas();
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		
		
		
	}

	
		
	

	private void crearItemContainerLayout() {
		
		itemContainerLayout = new HorizontalLayout();
		itemContainerLayout.setWidth("100%");
		itemContainerLayout.setSpacing(false);
		itemContainerLayout.setHeight("30%");
		
	}





	private void cargarPersonas() {
		int contador = 0;
		
		List<Persona> listPersona = new ArrayList<Persona>();
		
		if (UserUtil.getUsuario().getNivelAcceso().getNivelAcceso() == 1) {
			listPersona = jpaPer.findPersonasActiva();
		}
		
		if (UserUtil.getUsuario().getNivelAcceso().getNivelAcceso() == 2) {
			listPersona = jpaPer.findPersonaWithCargoByEvaluador(UserUtil.getUsuario().getPersona().getCargo());
		}
		
		if (UserUtil.getUsuario().getNivelAcceso().getNivelAcceso() == 3) {
			
			listPersona.add(jpaPer.findPersona(UserUtil.getUsuario().getPersona().getPersona()));
			
		}
		
		
		
		
		
		for (Persona persona : listPersona ) {
			contador = contador +1;
			btnBoton = new Button();
			btnBoton.addStyleName("link");
			//btnBoton.setWidth("60%");
			btnBoton.setCaption("Evaluar...");
			crearContenido();
			
			Image im = new Image();
			if (persona.getFoto() != null) {
//				btnBoton.setIcon(new ThemeResource(persona.getFoto()));
				File foto = new File(persona.getFoto());
				im.setSource(new FileResource(foto));
			}else {
				im.setSource(new ThemeResource("images/cabeceraImagen.png"));
			}
			
			
			fotoLayout.addComponent(im);
			//fotoLayout.setWidth("100%");
			im.setWidth("100%");
			im.setHeight("100%");
			
			//con este funciona
			/*itemLayout.setWidth("100%");
			itemLayout.setHeight("30%");*/
			
			itemLayout.setWidth("300px");
			itemLayout.setHeight("300px");
			
			
			
			
			
			
//			btnBoton.setCaption(persona.getNombre() + " " + persona.getApellido());
			
//			itemContainerLayout.addComponent(btnBoton);
			lblNombre = new Label();
			lblNombre.setValue(persona.getNombre() + " " + persona.getApellido());
			contenidoLayout.addComponent(lblNombre);
			
			lblCargo = new Label();
			lblCargo.setValue(persona.getCargo().getDescripcion());
			contenidoLayout.addComponent(lblCargo);
			
			lblCorreo = new Label();
			lblCorreo.setValue(persona.getCorreoCorporativo());
			contenidoLayout.addComponent(lblCorreo);
			
			btnBoton.setId(String.valueOf(persona.getPersona()));
			btnBoton.addClickListener(e -> empezarEva(persona));
			contenidoLayout.addComponent(btnBoton);
			
			itemContainerLayout.addComponent(itemLayout);
			
			if (contador%3 == 0) {
				datosLayout.addComponent(itemContainerLayout);
				crearItemContainerLayout();
			}
		}
		datosLayout.addComponent(itemContainerLayout);
	}





	private void crearContenido() {
		
		itemLayout = new HorizontalLayout();
		itemLayout.setMargin(false);
		itemLayout.setSpacing(false);
		
		fotoLayout = new VerticalLayout();
		fotoLayout.setMargin(false);
		itemLayout.addComponent(fotoLayout);
		
		fotoLayout.setWidth("100%");
		fotoLayout.setHeight("65%");
		
		
		contenidoLayout = new VerticalLayout();
		itemLayout.addComponent(contenidoLayout);
		contenidoLayout.setWidth("100%");
		//contenidoLayout.setWidth("30%");
		contenidoLayout.setWidth("100%");
		
		
	}





	private void empezarEva(Persona persona) {
		
		//EvaluacionDetalleView eva =  new EvaluacionDetalleView(persona);
		EvaluacionCabeceraView eva = new EvaluacionCabeceraView(persona);
		ventana = new Window("Personas a Evalular", eva);
		ventana.center();
		ventana.setSizeFull();
		
		UI.getCurrent().addWindow(ventana);
		
		//EvaUI.getCurrent().getNavigator().navigateTo("evaluacionCabecera");
		/*ventana.addCloseListener(e -> 
		gridPersona.setItems(jpaPer.findPersonasActiva()));*/
		
		
	}





	private void cargarRecursos(int nro) {
		 
		
		for (int i = 1; i <=  nro; i++) {
			
			btnBoton = new Button();
			btnBoton.setCaption(String.valueOf(i));
			btnBoton.setId(String.valueOf(i));
			itemContainerLayout.addComponent(btnBoton);
			btnBoton.addClickListener(e -> Notification.show(e.getButton().getId()));
			if ( i%3 == 0) {
				datosLayout.addComponent(itemContainerLayout);
				itemContainerLayout = new HorizontalLayout();
			}
			
		}
		datosLayout.addComponent(itemContainerLayout);
		
	}





	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		setWidth("100%");
		setHeight("100%");
		
		formLayout = buildFormLayout();
		mainLayout.addComponent(formLayout);
		
	}

	private VerticalLayout buildFormLayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setMargin(false);
		formLayout.setSpacing(true);
		formLayout.setWidth("100%");
		formLayout.setHeight("100%");
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
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
		
		return botonLayout;
	}





	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setMargin(false);
		//datosLayout.setWidth("100%");
		//datosLayout.setHeight("100%");
		
		
		return datosLayout;
	}
}
