package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Usuario;
import py.com.tipcsa.eva.jpa.JpaUsuarios;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;



@ViewConfig(uri = "usuario", displayName = "Usuarios")
public class UsuarioAbmView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout grillaLayout;
	private HorizontalLayout botonLayout;
	
	private Grid<Usuario>gridUsuario;
	private Button btnAgregar;
	private Button btnCancelar;
	
	private JpaUsuarios jpaUsuario = new JpaUsuarios(JpaUtil.getEntityManagerFactory());
	
	
	public UsuarioAbmView() {
		
		BuildMainLayout();
		setCompositionRoot(mainLayout);
		btnCancelar.addClickListener(e -> volver());
		btnAgregar.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo("altaUsuario"));
	}


	private void volver() {
		
		EvaUI.getCurrent().getNavigator().navigateTo("");
		
	}


	private void BuildMainLayout() {
		
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
		//formLayout.setMargin(true);
		//formLayout.setMargin(false);
		formLayout.setWidth("100%");
		formLayout.setHeight("-1px");
		
		grillaLayout = buildGrillaLayout();
		formLayout.addComponent(grillaLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}


	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		botonLayout.setWidth("100%");
		botonLayout.setHeight("100%");
		
		
		btnCancelar = new Button();
		btnCancelar.setCaption("Volver");
		btnCancelar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnCancelar);
		
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
		
		botonLayout.setComponentAlignment(btnCancelar, new Alignment(0));
		botonLayout.setComponentAlignment(btnAgregar, new Alignment(190));
		
		return botonLayout;
	}


	private VerticalLayout buildGrillaLayout() {
		
		grillaLayout = new VerticalLayout();
		grillaLayout.setCaption("Mantenimiento de Usuarios");
		grillaLayout.setMargin(false);
		grillaLayout.setHeight("100%");
		grillaLayout.setHeight("100%");
		
		gridUsuario = new Grid<Usuario>();
		gridUsuario.setItems(jpaUsuario.findUsuarioEntities());
		gridUsuario.addColumn(Usuario::getDescripcion).setCaption("Usuario").setId("usuario");
		gridUsuario.addColumn(Usuario::getActivo).setCaption("Activo").setId("activo");
		gridUsuario.addColumn(Usuario -> Usuario.getNivelAcceso().getDescripcion()).setCaption("Acceso").setId("acceso");
		gridUsuario.addColumn(Usuario -> Usuario.getPersona().getNombre() + " " + Usuario.getPersona().getApellido()
				).setCaption("Nombre").setId("persona");
		gridUsuario.setSizeFull();
		
		grillaLayout.addComponent(gridUsuario);
		
		
		return grillaLayout;
	}

}
