package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.NivelAcceso;
import py.com.tipcsa.eva.entities.Perfil;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Usuario;
import py.com.tipcsa.eva.jpa.JpaArea;
import py.com.tipcsa.eva.jpa.JpaNivelAcceso;
import py.com.tipcsa.eva.jpa.JpaPerfil;
import py.com.tipcsa.eva.jpa.JpaPersonas;
import py.com.tipcsa.eva.jpa.JpaUsuarios;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;



@ViewConfig(uri = "altaUsuario", displayName = "AltaUsuario")
public class AltaUsuarioView extends CustomComponent implements View{
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout botonLayout;
	
	private TextField txtUsuario;
	private PasswordField txtClave;
	private PasswordField txtClaveRepetir;
	
	private ComboBox<NivelAcceso> cbxNivel;
	private ComboBox<Persona> cbxPersona;
	private ComboBox<Perfil> cbxPerfil;
	
	private Button btnAgregar;
	private Button btnCancelar;
	
	private JpaUsuarios jpaUsuario = new JpaUsuarios(JpaUtil.getEntityManagerFactory());
	private JpaNivelAcceso jpaNivel = new JpaNivelAcceso(JpaUtil.getEntityManagerFactory());
	private JpaPersonas jpaPersona = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	private JpaPerfil jpaPerfil = new JpaPerfil(JpaUtil.getEntityManagerFactory());
	
	public AltaUsuarioView () {
		
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		mainLayout.setCaption("Alta Usuario");
		formLayout.setCaption("Alta Usuario");
		btnAgregar.addClickListener( event -> {
			if (!verificarDatos()) {
				addUsuario();
			}	
		});
		btnCancelar.addClickListener(event -> mover());
	}


	private void mover() {
		EvaUI.getCurrent().getNavigator().navigateTo("");
		
		
	}


	private boolean verificarDatos() {
		if (txtUsuario.isEmpty()) {
			Notification.show("El usuario no puede quedar vacio.", Notification.TYPE_ERROR_MESSAGE);
			txtUsuario.focus();
			return true;
		}
		if (jpaUsuario.findUsuarioRepetido(txtUsuario.getValue())) {
			Notification.show("El nombre del usuario ya se encuentra utilizado.", Notification.TYPE_ERROR_MESSAGE);
			txtUsuario.clear();
			txtUsuario.focus();
			return true;
		}
		
		if (txtClave.isEmpty()) {
			Notification.show("La clave no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			txtClave.focus();
			return true;
		}
		
		if (txtClaveRepetir.isEmpty()) {
			Notification.show("La clave no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			txtClaveRepetir.focus();
			return true;
		}
		
		if (!txtClave.getValue().equals(txtClaveRepetir.getValue())) {
			Notification.show("Las claves deben de ser iguales", Notification.TYPE_ERROR_MESSAGE);
			return true;
		}
		
		if (cbxNivel.isEmpty()) {
			Notification.show("El nivel de acceso no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			cbxNivel.focus();
			return true;
		}
		
		if (cbxPersona.isEmpty()) {
			Notification.show("Debe seleccionar una persona", Notification.TYPE_ERROR_MESSAGE);
			cbxPersona.focus();
			return true;
		}
		
		if (cbxPerfil.isEmpty()) {
			Notification.show("Debe seleccionar un perfil", Notification.TYPE_ERROR_MESSAGE);
			cbxPerfil.focus();
			return true;
		}
		
		
		
		
		
		
		return false;
	}


	private void addUsuario() {
		
		Usuario addUsu = new Usuario();
		addUsu.setUsuario(1);
		addUsu.setDescripcion(txtUsuario.getValue());
		addUsu.setClave(txtClave.getValue());
		addUsu.setNivelAcceso(cbxNivel.getValue());
		addUsu.setActivo(true);
		addUsu.setPersona(cbxPersona.getValue());
		addUsu.setPerfil(cbxPerfil.getValue());
		try {
			jpaUsuario.create(addUsu);
			Notification.show("Usuario creado correctamente");
			
			txtUsuario.clear();
			txtClave.clear();
			txtClaveRepetir.clear();
			cbxPersona.clear();
			cbxNivel.clear();
			cbxPersona.setItems(jpaPersona.findPersonasSinUser());
			cbxPerfil.clear();
		} catch (Exception e) {
			Notification.show(e.getMessage() +"Error al crear usuario", Notification.TYPE_ERROR_MESSAGE);
		}
		
		
	}


	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		setWidth("100%");
		setHeight("-1px");
		formLayout = buildFormLayout();
		mainLayout.addComponent(formLayout);
		
		
	}


	private VerticalLayout buildFormLayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setMargin(true);
		formLayout.setHeight("-1px");
		formLayout.setWidth("100%");
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		formLayout.setComponentAlignment(botonLayout, new Alignment(0));
		
		
		return formLayout;
	}


	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		botonLayout.setWidth("-1px");
		botonLayout.setHeight("-1px");
		botonLayout.setMargin(true);
		botonLayout.setSpacing(true);
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.addStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
		
		btnCancelar = new Button();
		btnCancelar.setCaption("Cancelar");
		btnCancelar.addStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnCancelar);
		
		return botonLayout;
	}


	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setMargin(true);
		datosLayout.setSpacing(true);
		datosLayout.setHeight("100%");
		datosLayout.setWidth("100%");
		
		txtUsuario = new TextField();
		txtUsuario.setCaption("Nombre Usuario:");
		datosLayout.addComponent(txtUsuario);
		
		txtClave = new PasswordField();
		txtClave.setCaption("Clave:");
		datosLayout.addComponent(txtClave);
		
		txtClaveRepetir = new PasswordField();
		txtClaveRepetir.setCaption("Repetir Clave:");
		datosLayout.addComponent(txtClaveRepetir);
		
		cbxPersona = new ComboBox<Persona>();
		cbxPersona.setCaption("Persona");
		cbxPersona.setItems(jpaPersona.findPersonasSinUser());
		cbxPersona.setItemCaptionGenerator(per -> per.getNombre() + " "+ per.getApellido());
		cbxPersona.setEmptySelectionAllowed(false);
		datosLayout.addComponent(cbxPersona);
		
		
		
		cbxNivel = new ComboBox<NivelAcceso>();
		cbxNivel.setCaption("Nivel de acceso:");
		cbxNivel.setItems(jpaNivel.findNivelAccesoEntities());
		cbxNivel.setEmptySelectionAllowed(false);
		cbxNivel.setItemCaptionGenerator(NivelAcceso::getDescripcion);
		datosLayout.addComponent(cbxNivel);
		
		cbxPerfil = new ComboBox<Perfil>();
		cbxPerfil.setCaption("Perfil");
		cbxPerfil.setItems(jpaPerfil.findPerfilEntities());
		cbxPerfil.setItemCaptionGenerator(perfil -> perfil.getDescripcion());
		cbxPerfil.setEmptySelectionAllowed(false);
		datosLayout.addComponent(cbxPerfil);
		
		
		
		
		
		
		return datosLayout;
	}
	
	
	
}
