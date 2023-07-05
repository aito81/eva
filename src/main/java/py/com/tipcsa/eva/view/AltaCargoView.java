package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "altaCargo", displayName = "Alta Cargo")
public class AltaCargoView extends CustomComponent implements View{
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout botonLayout;
	
	private TextField txtCargo;
	private ComboBox<Grupo> cbxGrupo;
	private Button btnAgregar;
	private Button btnVolver;
	
	private JpaGrupos jpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	private JpaCargo jpaCargo = new JpaCargo(JpaUtil.getEntityManagerFactory());
	
	public AltaCargoView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		btnAgregar.addClickListener(e -> agregar());
		btnVolver.addClickListener(e -> volver());
		
		
	}

	private void volver() {
		
		EvaUI.getCurrent().getNavigator().navigateTo("cargo");
	}

	private void agregar() {
		// TODO Auto-generated method stub
		if (verificarDatos()) {
			return;
		}
		
		Cargo addCargo = new Cargo(); 
		addCargo.setCargo(1);
		addCargo.setDescripcion(txtCargo.getValue());
		//addCargo.setGrupo(cbxGrupo.getValue());
		
		try {
			jpaCargo.create(addCargo);
			Notification.show("Cargo creado exitosamente.");
			txtCargo.clear();
			cbxGrupo.clear();
		} catch (Exception e) {
			Notification.show(e.getMessage() +"Error al crear cargo", Notification.TYPE_ERROR_MESSAGE);
		}
		
		
		
		
	}

	private boolean verificarDatos() {
		
		if (txtCargo.getValue().isEmpty()) {
			Notification.show("El campo descripcion no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			txtCargo.focus();
			return true;
		}
		
		/*if (cbxGrupo.getValue() == null) {
			Notification.show("Debe seleccionar un grupo para el cargo.", Notification.TYPE_ERROR_MESSAGE);
			cbxGrupo.focus();
			return true;
		}*/
		
		
		
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
		formLayout.setMargin(true);
		formLayout.setWidth("100%");
		formLayout.setHeight("-1px");
		
		datosLayout = buildDatosLayout();
		formLayout.addComponent(datosLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
		btnAgregar.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
		
		btnVolver = new Button();
		btnVolver.setCaption("Volver");
		btnVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnVolver);
		
		
		return botonLayout;
	}

	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setMargin(true);
		datosLayout.setHeight("100%");
		datosLayout.setWidth("100%");
		
		txtCargo = new TextField();
		txtCargo.setCaption("Cargo:");
		txtCargo.addValueChangeListener(e -> txtCargo.setValue(txtCargo.getValue().toUpperCase()));
		txtCargo.addBlurListener(e -> {
			if (!txtCargo.getValue().isEmpty()) {
				if (jpaCargo.findCargoRepetido(txtCargo.getValue())) {
					Notification.show("Ya existe un cargo con esa descripcion", Notification.TYPE_ERROR_MESSAGE);
					txtCargo.clear();
					txtCargo.focus();
				}
			}
		});
		datosLayout.addComponent(txtCargo);
		
		cbxGrupo = new ComboBox<Grupo>();
		cbxGrupo.setCaption("Grupo:");
		cbxGrupo.setItems(jpaGrupo.findGrupoEntities());
		cbxGrupo.setEmptySelectionAllowed(false);
		cbxGrupo.setItemCaptionGenerator(Grupo::getDescripcion);
		
		
		
		
		//datosLayout.addComponent(cbxGrupo);
		
		return datosLayout;
	}
	
	
	
	
}
