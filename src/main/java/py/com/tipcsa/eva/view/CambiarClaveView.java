package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Usuario;
import py.com.tipcsa.eva.jpa.JpaUsuarios;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.UserUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "perfil", displayName = "Cambiar Clave")
public class CambiarClaveView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout botonLayout;
	
	
	private PasswordField txtClaveAnterior;
	private PasswordField txtClaveNueva;
	private PasswordField txtClaveNuevaRepetida;
	
	private Button btnGuardar;
	private Button btnCancelar;
	
	private JpaUsuarios jpaUsu = new JpaUsuarios(JpaUtil.getEntityManagerFactory());
	
	public CambiarClaveView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		btnGuardar.addClickListener( e -> guardar());
		btnCancelar.addClickListener(e -> cancelar());
	}

	private void cancelar() {
		
		EvaUI.getCurrent().getNavigator().navigateTo("");
	}

	private void guardar() {
		
		if (verificarDatos()) {
			return;
		}
		
		try {
			Usuario usu = new Usuario();
			usu = jpaUsu.findUsuario(UserUtil.getUsuario().getUsuario());
			usu.setClave(txtClaveNueva.getValue());
			UserUtil.getUsuario().setClave(usu.getClave());
			jpaUsu.edit(usu);
			Notification.show("Clave cambiada con exito");
			txtClaveAnterior.clear();
			txtClaveNueva.clear();
			txtClaveNuevaRepetida.clear();
		} catch (Exception e) {
			Notification.show("Error al cambiar clave >> "+ Notification.TYPE_ERROR_MESSAGE);
		}
		
		
		
	}

	private boolean verificarDatos() {
		
		if (txtClaveAnterior.getValue().isEmpty()) {
			
			Notification.show("Debe cargar el campo de CLave Anterior", Notification.TYPE_ERROR_MESSAGE);
			txtClaveAnterior.focus();
			return true;
			
		}
		
		if (txtClaveNueva.getValue().isEmpty()) {
			
			Notification.show("Debe cargar el campo Clave nueva", Notification.TYPE_ERROR_MESSAGE);
			txtClaveNueva.focus();
			return true;
			
		}
		
		if (txtClaveNuevaRepetida.getValue().isEmpty()) {
			
			Notification.show("Debe cargar el campo Clave Nueva Repetir", Notification.TYPE_ERROR_MESSAGE);
			txtClaveNueva.focus();
			return true;
			
		}
		
		if (!txtClaveAnterior.getValue().equals(UserUtil.getUsuario().getClave())) {
			
			Notification.show("La Clave cargada no coincide con la clave actual", Notification.TYPE_ERROR_MESSAGE);
			txtClaveAnterior.focus();
			txtClaveAnterior.clear();
			txtClaveNueva.clear();
			txtClaveNuevaRepetida.clear();
			return true;
			
		}
		
		if (! txtClaveNueva.getValue().equals(txtClaveNuevaRepetida.getValue())) {
			
			Notification.show("Las claves no coinciden", Notification.TYPE_ERROR_MESSAGE);
			txtClaveAnterior.clear();
			txtClaveNueva.clear();
			txtClaveNuevaRepetida.clear();
			txtClaveAnterior.focus();
			return true;
		}
		
		
		
		return false;
	}

	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		
		setWidth("100%");
		setHeight("-1px");
		
		formLayout = buildFormlayout();
		mainLayout.addComponent(formLayout);
		
	}

	private VerticalLayout buildFormlayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setMargin(false);
		
		txtClaveAnterior = new PasswordField();
		txtClaveAnterior.setCaption("Clave Anterior:");
		formLayout.addComponent(txtClaveAnterior);
		
		txtClaveNueva = new PasswordField();
		txtClaveNueva.setCaption("Clave Nueva:");
		formLayout.addComponent(txtClaveNueva);
		
		txtClaveNuevaRepetida = new PasswordField();
		txtClaveNuevaRepetida.setCaption("Repetir Clave Nueva:");
		formLayout.addComponent(txtClaveNuevaRepetida);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		
		
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		botonLayout.setMargin(false);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnGuardar);
		
		btnCancelar = new Button();
		btnCancelar.setCaption("Cancelar");
		btnCancelar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnCancelar);
		
		return botonLayout;
	}
}
