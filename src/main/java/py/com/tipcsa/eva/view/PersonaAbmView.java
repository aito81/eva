package py.com.tipcsa.eva.view;



import java.awt.Dialog;

import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DescriptionGenerator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.jpa.JpaPersonas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.StringUtils;
import py.com.tipcsa.eva.util.ViewConfig;


@ViewConfig(uri = "funcionario", displayName = "Funcionarios")
public class PersonaAbmView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	private VerticalLayout tituloLayout;
	private VerticalLayout grillaLayout;
	private HorizontalLayout botonLayout;
	private Grid<Persona> gridPersona;
	private Button btnAgregar;
	private Button btnCencelar;
	
	private Window ventana;

	
	private JpaPersonas jpaPer = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	
	public PersonaAbmView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		btnAgregar.addClickListener(e -> addPersona());
	}
	
	
	
	
	




	private void addPersona() {
		AltaPersonaView alta = new AltaPersonaView();
		ventana = new Window("Alta Persona", alta);
		ventana.center();
		ventana.setSizeFull();
		UI.getCurrent().addWindow(ventana);
		ventana.addCloseListener(e -> 
		gridPersona.setItems(jpaPer.findPersonasActiva()));
		
	}
	
	
	private void verPersona(Persona per) {
		AltaPersonaView editPersona = new AltaPersonaView(per);
		ventana = new Window("Consulta de Datos", editPersona);
		ventana.center();
		ventana.setSizeFull();
		//editPersona.getAltaLayout().setEnabled(false);
		editPersona.getMainLayout().setEnabled(false);
		editPersona.getBotonLayout().setVisible(false);
		UI.getCurrent().addWindow(ventana);
		//ventana.addCloseListener(e -> gridPersona.setItems(jpaPer.findPersonasActiva()));
		
	}

	private void editarPersona(Persona per) {
		AltaPersonaView editPersona = new AltaPersonaView(per);
		ventana = new Window("Modificion de Datos", editPersona);
		ventana.center();
		ventana.setSizeFull();
		UI.getCurrent().addWindow(ventana);
		ventana.addCloseListener(e -> gridPersona.setItems(jpaPer.findPersonasActiva()));
		
		
		
	}







	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		//tituloLayout = buildTituloLayout();
		//mainLayout.addComponent(tituloLayout);
		
		grillaLayout = buildGrillaLayout();
		mainLayout.addComponent(grillaLayout);
		botonLayout = buildBotonLayout();
		mainLayout.addComponent(botonLayout);
		//mainLayout.setComponentAlignment(botonLayout, new Alignment(0));
		
		return mainLayout;
		





	// busquedaLayout

	}






	private HorizontalLayout buildBotonLayout() {
		botonLayout = new HorizontalLayout();
		
		botonLayout.setWidth("100%");
		botonLayout.setHeight("-1px");
		botonLayout.setMargin(true);
		botonLayout.setSpacing(true);
		
		//componentes
		
		btnCencelar = new Button();
		btnCencelar.setCaption("Volver");
		btnCencelar.addStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnCencelar);
		botonLayout.setComponentAlignment(btnCencelar, new Alignment(0));
		
		btnCencelar.addClickListener(event -> volver());
		
		
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Añadir Persona");
		btnAgregar.setWidth("-1px");
		btnAgregar.setHeight("-1px");
		btnAgregar.addStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
		botonLayout.setComponentAlignment(btnAgregar, new Alignment(190));
		
		
		
		
		
		
		
		return botonLayout;
	}




	private void volver() {
		
		EvaUI.getCurrent().getNavigator().navigateTo("");
	}









	private VerticalLayout buildGrillaLayout() {
		grillaLayout = new VerticalLayout();
		grillaLayout.setMargin(true);
		grillaLayout.setSpacing(true);
		grillaLayout.setWidth("100%");
		grillaLayout.setHeight("-1px");
		
		//componentes
		gridPersona = new Grid<Persona>();
		gridPersona.setItems(jpaPer.findPersonasActiva());
		
		//gridPersona.addColumn()
		/*gridPersona.setWidth("100%");
		gridPersona.setHeight("100%");*/
		gridPersona.setSizeFull();
		gridPersona.setCaption("Mantenimiento de Personas");
		gridPersona.addColumn(persona -> StringUtils.colocarFormatoCI((String.valueOf(persona.getNroDocumento()))).replace(",", ".")
				).setCaption("Documento").setWidth(120).setId("documento");
		gridPersona.addColumn(Persona::getNombre).setCaption("Nombre").setId("nombre");//.setWidth(300);
		gridPersona.addColumn(Persona::getApellido).setCaption("Apellido").setId("apellido");
		gridPersona.addColumn(persona -> {
			if (persona.getCargo() != null) {
				return persona.getCargo().getDescripcion();
			}else {
				return persona.getCargo();
			}
			
		}).setCaption("Cargo").setId("cargo");
		//gridPersona.addColumn(Persona::getCargo, new DateRenderer()).setCaption("Cargo").setId("cargo");
		/*gridPersona.getColumn("cargo").setDescriptionGenerator(new DescriptionGenerator<Persona>() {
			
			@Override
			public String apply(Persona t) {
				if (t.getCargo() == null) {
					return "holaaa";
				}
				return t.getCargo().getDescripcion();
			}
		});*/
		
	
		
		
		
		
		
		//.setWidth(300);
		//gridPersona.addColumn(Persona -> "Editar", new ButtonRenderer<>()).setCaption("Editar");
		
		//gridPersona.addColumn("borrar");
		//gridPersona.addColumn(Persona -> "Borrar", new HtmlRenderer() {
		//} );
		//gridPersona.addColumn(Persona -> "Editar", new HtmlRenderer)
		
		
		
		
		//gridPersona.addColumn(Persona -> "prueba", new Propertyvalu )
		/*gridPersona.addComponentColumn( person ->  {
			Button btnEditar = new Button();
			btnEditar.setCaption("-");
			
			
			btnEditar.addClickListener(event -> editar(person));
			return btnEditar;
		}).setCaption("Borrar");*/
		gridPersona.addColumn(Persona -> FontAwesome.EYE.getHtml(), 
				new HtmlRenderer()).setId("ver").setStyleGenerator(matriz ->
				"align-center").setWidth(70).setCaption("Ver");
		
		gridPersona.addColumn(Persona -> FontAwesome.EDIT.getHtml(),
				new HtmlRenderer()).setId("editar").setStyleGenerator(matriz ->
		"align-center").setWidth(70).setCaption("Editar");
		
		
		gridPersona.addColumn(Persona -> FontAwesome.TRASH.getHtml(),
				new HtmlRenderer()).setId("borrar").setStyleGenerator(matriz ->
		"align-center").setWidth(70).setCaption("Borrar");
		
		
		gridPersona.getDefaultHeaderRow().join("ver","editar","borrar").setText("Opciones");
		
		
		
		gridPersona.addItemClickListener(new ItemClickListener<Persona>() {

			@Override
			public void itemClick(ItemClick<Persona> event) {
				if (event != null ){
					
					
					if (event.getColumn().getId().equals("ver")) {
						verPersona(event.getItem());
					}
				
				
					if (event.getColumn().getId().equals("editar")) {
						editarPersona(event.getItem());
					}
					if (event.getColumn().getId().equals("borrar")) {
						
						desactivarPersona(event.getItem());
					}
				}
				
			}

			

			
		});
		
		
		
		
		grillaLayout.addComponent(gridPersona);
	
		
		return grillaLayout;
		
		
	}

	




	private void desactivarPersona(Persona per) {
		
		per.setVigente(false);
		try {
			jpaPer.edit(per);
			gridPersona.setItems(jpaPer.findPersonasActiva());
			//jpaPer.ActivarDesactivarPersonaJDBC(person, false);
			Notification.show("Persona dada de baja correctamente");
		} catch (Exception e) {
			Notification.show(e.getMessage() +"    Error al dar de baja", Notification.TYPE_WARNING_MESSAGE);
		}
	}




	private VerticalLayout buildTituloLayout() {
		// TODO Auto-generated method stub
		return null;
	}

}
