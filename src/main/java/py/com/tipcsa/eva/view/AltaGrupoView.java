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
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;


@ViewConfig(uri = "altaGrupo", displayName = "Alta Grupo")
public class AltaGrupoView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout botonLayout;
	
	private JpaGrupos jpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	
	private TextField txtGrupo;
	
	private Button btnAgregar;
	private Button btnVolver;
	
	
	public AltaGrupoView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		btnAgregar.addClickListener(e -> agregar());
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo("grupo"));
	}

	private void agregar() {
		
		if (verificarDatos()) {
			return;
		}
		
		try {
			Grupo addGrupo = new Grupo();
			addGrupo.setGrupo(1);
			addGrupo.setDescripcion(txtGrupo.getValue());
			Notification.show("Grupo agregado correctamente");
			txtGrupo.clear();
			jpaGrupo.create(addGrupo);
		} catch (Exception e) {
			Notification.show(e.getMessage() + " " + "Error al dar de alta el grupo", Notification.TYPE_ERROR_MESSAGE );
		}
		
	}

	private boolean verificarDatos() {
		
		if (txtGrupo.isEmpty()) {
			
			Notification.show("El nombre del grupo no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			txtGrupo.focus();
		}
		return false;
	}

	private void buildMainLayout() {
		
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		
		setHeight("-1px");
		setWidth("100%");
		
		formLayout = buildFormLayout();
		mainLayout.addComponent(formLayout);
	}

	private VerticalLayout buildFormLayout() {
		
		formLayout = new VerticalLayout();
		formLayout.setMargin(false);
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
		datosLayout.setMargin(false);
		
		txtGrupo = new TextField();
		txtGrupo.setCaption("Nombre del grupo:");
		txtGrupo.addValueChangeListener(e -> txtGrupo.setValue(txtGrupo.getValue().toUpperCase()));
		txtGrupo.addBlurListener(e -> {
			if (jpaGrupo.findGrupoRepetido(txtGrupo.getValue())) {
				Notification.show("Ya se encuentra un grupo creado con esa descripcion", Notification.TYPE_ERROR_MESSAGE);
				txtGrupo.clear();
				txtGrupo.focus();
			}
		});
		
		datosLayout.addComponent(txtGrupo);
		
		
		
		
		
		
		
		return datosLayout;
	}
	
}
