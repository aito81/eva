package py.com.tipcsa.eva.util;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AltaCustomerView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	private VerticalLayout altaLayout;
	private TextField txtNombre;
	private Button btnBoton;
	
	
	public AltaCustomerView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		btnBoton.addClickListener(event -> {
			Notification.show("HOLAAAAAAAAAAA");
		});
		
		
	}
	
	
	
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		altaLayout = buildAltaLayout();
		mainLayout.addComponent(altaLayout);
		
		return mainLayout;
		
	



	
	// busquedaLayout
	
}







@AutoGenerated
private VerticalLayout buildAltaLayout() {
	// common part: create layout
	altaLayout = new VerticalLayout();
	//altaLayout.setImmediate(false);
	altaLayout.setMargin(true);
	altaLayout.setSpacing(true);
	altaLayout.setWidth("100.0%");
	altaLayout.setHeight("-1px");
	altaLayout.setMargin(false);
	altaLayout.setSpacing(true);
	
	txtNombre = new TextField();
	//txtNombre.setCaption("Nombre");
	//txtNombre.setWidth("'1px");
	//txtNombre.setHeight("'1px");
	altaLayout.addComponent(txtNombre);
	
	btnBoton = new Button();
	btnBoton.setCaption("Aceptar");
	//btnBoton.setWidth("-1px");
	//btnBoton.setHeight("-1px");
	altaLayout.addComponent(btnBoton);
	
	return altaLayout;
	
	}
}