package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Area;
import py.com.tipcsa.eva.jpa.JpaArea;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "altaArea", displayName = "Alta Area")
public class AltaAreaView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout botonLayout;
	
	private TextField txtArea;
	
	private Button btnAgregar;
	private Button btnVolver;
	
	
	private JpaArea jpaArea = new JpaArea(JpaUtil.getEntityManagerFactory());
	
	public AltaAreaView () {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		btnAgregar.addClickListener(e -> agregar());
		btnVolver.addClickListener(e ->EvaUI.getCurrent().getNavigator().navigateTo("area"));
	}

	private void agregar() {
		if (validarDatos()) {
			return;
		}
		
		Area addArea = new Area();
		addArea.setArea(1);
		addArea.setDescripcion(txtArea.getValue());
		
		try {
			jpaArea.create(addArea);
			Notification.show("Area agregada correctamente.");
			txtArea.clear();
		} catch (Exception e) {
			Notification.show(e.getMessage() +"Error al agregar area ", Notification.TYPE_ERROR_MESSAGE);
		}
	}

	private boolean validarDatos() {
		
		if (txtArea.isEmpty()) {
			Notification.show("El campo no puede quedar vacio.", Notification.TYPE_ERROR_MESSAGE);
			txtArea.focus();
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
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		//botonLayout.setWidth("100%");
		botonLayout.setHeight("100%");
		
		btnVolver = new Button();
		btnVolver.setCaption("Volver");
		btnVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnVolver);
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
		
		return botonLayout;
	}

	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setWidth("100%");
		datosLayout.setHeight("100%");
		
		txtArea = new TextField();
		txtArea.setCaption("Area:");
		txtArea.addValueChangeListener(e -> 
		txtArea.setValue(txtArea.getValue().toUpperCase()));
		txtArea.addBlurListener(e -> {
			if (!txtArea.isEmpty()) {
				if (jpaArea.findAreaRepetido(txtArea.getValue())) {
					Notification.show("Ya existe un area con esa descripcion", Notification.TYPE_ERROR_MESSAGE);
					txtArea.clear();
					txtArea.focus();
				}
			}
		});
		datosLayout.addComponent(txtArea);
		
		return datosLayout;
	}
}
