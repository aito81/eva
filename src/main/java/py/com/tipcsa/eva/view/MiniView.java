package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.util.StringUtils;

public class MiniView extends CustomComponent implements View{

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout botonLayout;
	
	private TextField txtPeso;
	
	private Button btnGuardar;
	private Button btnCancelar;
	
	private boolean guardado;
	
	private String valor;
	
	
	public boolean isGuardado() {
		return guardado;
	}


	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public MiniView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		btnGuardar.addClickListener(e -> {
			
			guardar(txtPeso.getValue());
		});
		
		btnCancelar.addClickListener(e -> cerrar());
	}


	private void cerrar() {
		
		guardado = false;
		Window w = this.findAncestor(Window.class);
		w.close();
		
	}


	private void guardar(String value) {
		
		if (verificarDatos()) {
			
			return;
		}
		
		guardado = true;
		valor = txtPeso.getValue();
		Window w = this.findAncestor(Window.class);
		w.close();
		
	}


	private boolean verificarDatos() {
		
		if (txtPeso.isEmpty()) {
			Notification.show("El valor del peso no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			txtPeso.focus();
			return true;
		}
		
		String valor = txtPeso.getValue().replace(",", ".");
		if (!StringUtils.isDoubleNumeric(valor)) {
			Notification.show("El peso debe de ser numerico.", Notification.TYPE_ERROR_MESSAGE);
			txtPeso.clear();
			txtPeso.focus();
			return true;
		}
		
		return false;
	}


	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		
		
		formLayout = buildFormLayout();
		mainLayout.addComponent(formLayout);
		
	}


	private VerticalLayout buildFormLayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setWidth("100%");
		formLayout.setHeight("-1px");
		formLayout.setMargin(false);
		
		txtPeso = new TextField();
		txtPeso.setCaption("Peso");		
		formLayout.addComponent(txtPeso);
		
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
