package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.entities.Valor;
import py.com.tipcsa.eva.jpa.JpaValor;
import py.com.tipcsa.eva.util.JpaUtil;

public class CalificacionView extends CustomComponent implements View  {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout tituloLayout;
	private HorizontalLayout datosLayout;
	private VerticalLayout valorLayout;
	private VerticalLayout botonLayout;
	
	private Label lblTitulo;
	
	private CheckBox chkValor;
	
	private Button btnGuardar;
	
	private Valor valor;
	
	public boolean guardado = false;
	
	public Valor getValor() {
		return valor;
	}



	public void setValor(Valor valor) {
		this.valor = valor;
	}



	private JpaValor jpaVa = new JpaValor(JpaUtil.getEntityManagerFactory());
	
	
	public CalificacionView(Pregunta pregunta) {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		cargarValores();
		
		btnGuardar.addClickListener(e -> guardar());
		lblTitulo.setValue(pregunta.getDescripcion());
		
		
	}



	private void guardar() {
		
		if (!verificarDatos()) {
			Notification.show("No se puede guardar la nota sin ningun valor", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		valor = traerValor();
		guardado = true;
		Window w = this.findAncestor(Window.class);
		w.close();
	
	}



	private Valor traerValor() {
		
		Valor val = null;
		for (int i = 0; i < valorLayout.getComponentCount(); i++) {
			CheckBox chk = (CheckBox) valorLayout.getComponent(i);
			if (chk.getValue()) {
				valor = jpaVa.findValor(Integer.valueOf(chk.getId()));
				
				
			}
		}
		
		return valor;
	}



	private boolean verificarDatos() {
		
		for (int i = 0; i < valorLayout.getComponentCount() ; i++) {
			
			CheckBox chk = (CheckBox) valorLayout.getComponent(i);
			
			if (chk.getValue()) {
				return true;
			}
		}
		
		return false;
	}



	private void cargarValores() {
		
		for (Valor valor : jpaVa.findValorEntities()) {
			
			chkValor = new CheckBox();
			chkValor.setCaption(valor.getDescripcion());
			chkValor.setId(String.valueOf(valor.getValor()));
			chkValor.addValueChangeListener(e -> limpiarOtros(e.getComponent()));
			valorLayout.addComponent(chkValor);
			
			
		}
		
	}



	private void limpiarOtros(Component component) {
		
		CheckBox ch = (CheckBox) component;
		
		if (ch.getValue()) {
			
			int comp = Integer.valueOf(component.getId());
			
			for (int i = 1; i <= valorLayout.getComponentCount(); i++) {
				
				if (i != comp) {
					
					CheckBox chk = (CheckBox) valorLayout.getComponent(i-1);
					chk.clear();
				}else {
					/*CheckBox chk = (CheckBox) valorLayout.getComponent(i-1);
					chk.setValue(true);*/
				}
				//Notification.show(String.valueOf(valorLayout.getComponentCount()));
					
				
			}
		}
		
		
		
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
		
		
		return formLayout;
	}



	private HorizontalLayout buildDatosLayout() {
		
		datosLayout = new HorizontalLayout();
		datosLayout.setWidth("100%");
		
		valorLayout = buildValorLayout();
		datosLayout.addComponent(valorLayout);
		
		botonLayout = buildBotonLayout();
		datosLayout.addComponent(botonLayout);
		
		return datosLayout;
	}



	private VerticalLayout buildBotonLayout() {
		
		botonLayout = new VerticalLayout();
		botonLayout.setWidth("100%");
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnGuardar);
		
		return botonLayout;
	}



	private VerticalLayout buildValorLayout() {
		
		valorLayout = new VerticalLayout();
		valorLayout.setMargin(false);
		
		return valorLayout;
	}



	private VerticalLayout buildTituloLayout() {
		
		tituloLayout = new VerticalLayout();
		tituloLayout.setWidth("100%");
		
		lblTitulo = new Label();
		tituloLayout.addComponent(lblTitulo);
		tituloLayout.setComponentAlignment(lblTitulo, Alignment.TOP_CENTER);
		
		return tituloLayout;
	}
}
