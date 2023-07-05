package py.com.tipcsa.eva.view;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;

import org.eclipse.persistence.internal.libraries.antlr.runtime.DFA;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.StreamVariable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.FileDropTarget;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Area;
import py.com.tipcsa.eva.entities.Barrio;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Ciudad;
import py.com.tipcsa.eva.entities.Departamento;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Sucursal;
import py.com.tipcsa.eva.jpa.JpaBarrios;
import py.com.tipcsa.eva.jpa.JpaCargo;
import py.com.tipcsa.eva.jpa.JpaCiudades;
import py.com.tipcsa.eva.jpa.JpaDepartamentos;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.jpa.JpaPaises;
import py.com.tipcsa.eva.jpa.JpaPersonas;
import py.com.tipcsa.eva.jpa.JpaSucursales;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.StringUtils;
import py.com.tipcsa.eva.util.UserUtil;
import py.com.tipcsa.eva.util.ViewConfig;


public class AltaPersonaView extends CustomComponent implements View {
	



	private VerticalLayout mainLayout;
	private HorizontalLayout maquetaLayout;
	private VerticalLayout datosPersonalesLayout;
	private VerticalLayout datosLaboralesLayout;
	private VerticalLayout ubicacionLayout;
	private HorizontalLayout botonLayout;
	private VerticalLayout imagenLayout;
	private VerticalLayout componenteLayout;
	private VerticalLayout embeddedLayout;
	
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField txtNroDoc;
	private TextField txtCorreoPer;
	private TextField txtCorreoCorp;
	private TextField txtTelefono;
	private TextField txtDireccion;
	private TextField txtCodigoHumano;
	
	private ComboBox<Cargo> cbxCargo;
	//private ComboBox<Grupo> cbxGrupo;
	private ComboBox<Barrio> cbxBarrio;
	private ComboBox<Area> cbxArea;
	private ComboBox<Sucursal> cbxSucursal;
	private ComboBox<Ciudad> cbxCiudades;
	private ComboBox<Departamento> cbxDepto;
	
	private Button btnAgregar;
	private Button btnCancelar;
	
	final Label infoLabel = new Label();
	
	private DateField dfNacimiento;
	
	final VerticalLayout dropPane = new VerticalLayout(infoLabel);
	
	private DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
	
	private Embedded embedded;
	private Upload upload ;	
	
	private JpaPersonas jpaPer = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	private JpaSucursales jpaSu = new JpaSucursales(JpaUtil.getEntityManagerFactory());
	private JpaCargo jpaCargo  = new JpaCargo(JpaUtil.getEntityManagerFactory());
	private JpaGrupos JpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	private JpaPaises jpaPais = new JpaPaises(JpaUtil.getEntityManagerFactory());
	private JpaDepartamentos jpaDepto = new JpaDepartamentos(JpaUtil.getEntityManagerFactory());
	private JpaCiudades jpaCiudad = new JpaCiudades(JpaUtil.getEntityManagerFactory());
	private JpaBarrios jpaBarrio = new JpaBarrios(JpaUtil.getEntityManagerFactory());
	private Styles styles = Page.getCurrent().getStyles();
	
	private File file;
	
	
	
	String css = ".layout-with-border {\n" + 
            "    border: 1px solid black;\n" + 
            "}";
	private String dir ;
	
	
	public AltaPersonaView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		styles.add(css);
		
		
		btnAgregar.addClickListener(event -> agregar());
		
		
		// Show uploaded file in this placeholder
		Image image = new Image("Uploaded Image");

		// Implement both receiver that saves upload in a file and
		// listener for successful upload
		class ImageUploader implements Receiver, SucceededListener {
		    public File file;

		    public OutputStream receiveUpload(String filename,
		                                      String mimeType) {
		    	
		    	FileOutputStream fos;
		    	
		    	File fd = new File("pics"+ '/'+ txtNroDoc.getValue());
				if(!fd.exists() && !fd.isDirectory()) { 
					fd.mkdirs();
				}
		    	
		    	
				dir ="pics"+'/'+txtNroDoc.getValue()+'/'+ txtNombre.getValue()+"_"+ txtApellido.getValue() + new Date().getTime()
				    	+".jpg";
				
				file = new File(dir);
		    	
				
				
		    	try {
		    		fos = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					return null;
				}
		    	
		    	 
		    	 return fos;
		    	
		        // Create and return a file output stream
		        
		    }

		    public void uploadSucceeded(SucceededEvent event) {
		        // Show the uploaded file in the image viewer
		        
		    	image.setSource(new FileResource(file));
		        
				
				image.setWidth("100%");
				componenteLayout.addComponent(image);
		    }

			
		};
		ImageUploader receiver = new ImageUploader();
		
		Upload upload = new Upload("subir foto", receiver);
		upload.addSucceededListener(receiver);
		componenteLayout.addComponent(upload);
		
		
		
		
		
				
		
		
		
		
		
		
	}
	
	
	
	 
	 
	 
	 
	
	
	
	
	
	
	public AltaPersonaView(Persona persona) {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		cargarDatos(persona);
		btnAgregar.setCaption("Editar");
		
		styles.add(css);
		
		
		
		
		
		btnAgregar.addClickListener(e -> editarPersona(persona));
		
		Image imagen = new Image();
		
		if (persona.getFoto() != null) {
			
			
			File arch = new File(persona.getFoto());
			imagen.setSource( new FileResource(arch));
			imagen.setWidth("100%");
			
		}
		componenteLayout.addComponent(imagen);
		
		
		Image image = new Image();

		// Implement both receiver that saves upload in a file and
		// listener for successful upload
		class ImageUploader implements Receiver, SucceededListener {
		    public File file;

		    public OutputStream receiveUpload(String filename,
		                                      String mimeType) {
		    	
		    	FileOutputStream fos;
		    	
		    	File fd = new File("pics"+ '/'+ txtNroDoc.getValue());
				if(!fd.exists() && !fd.isDirectory()) { 
					fd.mkdirs();
				}
		    	
		    	dir = "pics"+'/'+txtNroDoc.getValue()+'/'+ txtNombre.getValue()+"_"+ txtApellido.getValue() + new Date().getTime()
				    	+".png";
				
				file = new File(dir);
		    	
				
				
		    	try {
		    		fos = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					return null;
				}
		    	
		    	 
		    	 return fos;
		    	
		        // Create and return a file output stream
		        
		    }

		    public void uploadSucceeded(SucceededEvent event) {
		        // Show the uploaded file in the image viewer
		        
		    	image.setSource(new FileResource(file));
		        
				
				image.setWidth("100%");
				
				componenteLayout.removeComponent(imagen);
				componenteLayout.addComponent(image);
				
		    }

			
		};
		ImageUploader receiver = new ImageUploader();
		
		Upload upload = new Upload("subir foto", receiver);
		upload.addSucceededListener(receiver);
		componenteLayout.addComponent(upload);
		
		
		
		
	}
	


	
	



	private void editarPersona(Persona persona) {
		
		if (controlarDatos()) {
			return;
		}
		
		persona.setNombre(txtNombre.getValue());
		persona.setApellido(txtApellido.getValue());
		persona.setNroDocumento(Integer.valueOf(txtNroDoc.getValue().replace(".","")));
		persona.setCorreoPersonal(txtCorreoPer.getValue());
		persona.setCorreoCorporativo(txtCorreoCorp.getValue());
		persona.setTelefono(txtTelefono.getValue());
		persona.setDireccion(txtDireccion.getValue());
		persona.setCodigoHumano(Integer.valueOf(txtCodigoHumano.getValue()));
		persona.setCargo(cbxCargo.getValue());
		persona.setArea(cbxArea.getValue());
		persona.setSucursal(cbxSucursal.getValue());
		persona.setBarrio(cbxBarrio.getValue());
		
		if (dir == null) {
			dir = persona.getFoto();
		}
		persona.setFoto(dir);
		persona.setFechaNacimiento( java.sql.Date.valueOf(dfNacimiento.getValue()));
		
		try {
			jpaPer.edit(persona);
			Notification.show("Persona editada correctamente.");
			
			if (UserUtil.getUsuario().getPersona().getPersona() ==(persona.getPersona())) {
				
				UserUtil.getUsuario().setPersona(persona);
			}
			
		//cerrar ventana
			Window w = this.findAncestor(Window.class);
			w.close();
		//fin cerrar ventana	
			
		} catch (Exception e) {
			Notification.show("Error al editar persona. "+ e.getMessage(), 
					Notification.TYPE_ERROR_MESSAGE );
		}
	}
	
	

	

	
	


	private void cargarDatos(Persona persona) {
		
		txtNombre.setValue(persona.getNombre());
		txtApellido.setValue(persona.getApellido());
		txtNroDoc.setValue(String.valueOf(persona.getNroDocumento()));
		txtCorreoPer.setValue(persona.getCorreoPersonal());
		txtCorreoCorp.setValue(persona.getCorreoCorporativo());
		txtTelefono.setValue(String.valueOf(persona.getTelefono()));
		txtDireccion.setValue(persona.getDireccion());
		txtCodigoHumano.setValue(String.valueOf(persona.getCodigoHumano()));
		cbxCargo.setSelectedItem(persona.getCargo());
		//cbxGrupo.setSelectedItem(persona.getGrupo());
		cbxArea.setSelectedItem(persona.getArea());
		cbxSucursal.setSelectedItem(persona.getSucursal());
		//cbxDepto.setSelectedItem();
		if (persona.getBarrio() != null) {
			Ciudad ciudadPersona = jpaCiudad.findCiudad(persona.getBarrio().getCiudad().getCiudad());
			Departamento deptoPersona = jpaDepto.findDepartamento(ciudadPersona.getDepartamento().getDepartamento());
			cbxDepto.setSelectedItem(deptoPersona);
			cbxCiudades.setItems(jpaCiudad.findCiudadesbyDepto(deptoPersona));
			cbxCiudades.setSelectedItem(ciudadPersona);
			cbxBarrio.setItems(jpaBarrio.findBarriosbyCiudad(ciudadPersona));
			cbxBarrio.setSelectedItem(persona.getBarrio());
		}
		
		if (persona.getFechaNacimiento() != null) {
			
			Instant instant = Instant.ofEpochMilli(persona.getFechaNacimiento().getTime());
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			LocalDate localDate = localDateTime.toLocalDate();
			
			
			
			dfNacimiento.setValue(localDate);
			
		}
		
		
		
		
		
		
		
	}



	private void agregar() {
		
		
		if (controlarDatos()) {
			return;
		}
		
		
		
		
		// TODO Auto-generated method stub
		Persona personaAdd = new Persona();
		personaAdd.setPersona(1);
		personaAdd.setNombre(txtNombre.getValue());
		personaAdd.setApellido(txtApellido.getValue());
		String nDoc = txtNroDoc.getValue().replace(".","");
		int nroDoc = (Integer.valueOf(nDoc));
		personaAdd.setNroDocumento(nroDoc);
		personaAdd.setCorreoPersonal(txtCorreoPer.getValue());
		personaAdd.setCorreoCorporativo(txtCorreoCorp.getValue());
		personaAdd.setDireccion(txtDireccion.getValue());
		personaAdd.setCodigoHumano( Integer.valueOf( txtCodigoHumano.getValue()));
		personaAdd.setTelefono(txtTelefono.getValue());
		personaAdd.setCargo(cbxCargo.getValue());
		personaAdd.setVigente(true);
		//personaAdd.setGrupo(cbxGrupo.getValue());
		//personaAdd.setGrupo(JpaGrupo.findGrupo(1));
		personaAdd.setSucursal(cbxSucursal.getValue());
		personaAdd.setBarrio(cbxBarrio.getValue());
		personaAdd.setFoto(dir);
		personaAdd.setFechaNacimiento( java.sql.Date.valueOf(dfNacimiento.getValue()) );
		
		try {
			jpaPer.create(personaAdd);
			Notification.show("Persona agregada exitosamente");
			Window w = this.findAncestor(Window.class);
			w.close();
		} catch (Exception e) {
			Notification.show("Error al agregar persona", Notification.TYPE_ERROR_MESSAGE);
		}
		
		
	}











	private boolean controlarDatos() {
		
		if (txtNombre.getValue().isEmpty()) {
			Notification.show(txtNombre.getCaption()+ " debe ser cargado.", Notification.TYPE_ERROR_MESSAGE);
			txtNombre.focus();
			return true;
		}
		
		if (txtApellido.getValue().isEmpty()) {
			Notification.show(txtApellido.getCaption()+ " debe ser cargado.", Notification.TYPE_ERROR_MESSAGE);
			txtApellido.focus();
			return true;
		}
		
		if (txtNroDoc.getValue().isEmpty()) {
			Notification.show(txtNroDoc.getCaption()+ " debe ser cargado.", Notification.TYPE_ERROR_MESSAGE);
			txtNroDoc.focus();
			return true;
		}
		
		if (cbxCargo.getValue() == null) {
			Notification.show(cbxCargo.getCaption()+ " debe ser cargado.", Notification.TYPE_ERROR_MESSAGE);
			cbxCargo.focus();
			return true;
		}
		
		if (txtCodigoHumano.getValue()== null) {
			Notification.show(txtCodigoHumano.getCaption()+ " debe ser cargado.", Notification.TYPE_ERROR_MESSAGE);
			txtCodigoHumano.focus();
			return true;
		}
		
		
		
		if (cbxSucursal.getValue()== null) {
			Notification.show(cbxSucursal.getCaption()+ " debe ser cargado.", Notification.TYPE_ERROR_MESSAGE);
			cbxSucursal.focus();
			return true;
		}
		
		if (dfNacimiento.isEmpty()) {
			
			Notification.show("Se debe cargar la fecha de nacimiento.", Notification.TYPE_ERROR_MESSAGE);
			dfNacimiento.focus();
			return true;
		}
		
		
		return false;
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
		maquetaLayout = buildMaquetaLayout();
		mainLayout.addComponent(maquetaLayout);
		/*altaLayout = buildAltaLayout();
		mainLayout.addComponent(altaLayout);
		mainLayout.addComponent(dropPane);*/
		botonLayout = buildBotonLayout();
		mainLayout.addComponent(botonLayout);
		mainLayout.setComponentAlignment(botonLayout, new Alignment(20));
		
		return mainLayout;
		





	// busquedaLayout

	}











	private HorizontalLayout buildMaquetaLayout() {
		
		maquetaLayout = new HorizontalLayout();
		//altaLayout.setImmediate(false);
		maquetaLayout.setMargin(true);
		maquetaLayout.setSpacing(true);
		maquetaLayout.setWidth("100.0%");
		maquetaLayout.setHeight("-1px");
		
		
		datosPersonalesLayout = buildAltaLayout();
		maquetaLayout.addComponent(datosPersonalesLayout);
		
		datosLaboralesLayout = buildAlta2Layout();
		maquetaLayout.addComponent(datosLaboralesLayout);
		
		ubicacionLayout = buildAlta3Layout();
		maquetaLayout.addComponent(ubicacionLayout);
		
		imagenLayout = buildAlta4Layout();
		maquetaLayout.addComponent(imagenLayout);
		
		
		
		
		
		return maquetaLayout;
	}



	private VerticalLayout buildAlta4Layout() {
		
		imagenLayout = new VerticalLayout();
		imagenLayout.setMargin(false);
		imagenLayout.setSpacing(false);
		imagenLayout.setWidth("100%");
		imagenLayout.setHeight("10%");
		imagenLayout.addStyleName("layout-with-border");
		imagenLayout.setCaption("Imagenes");
		
		componenteLayout = buildComponenteLayout();
		imagenLayout.addComponent(componenteLayout);
		
		embeddedLayout = buildEmbebedLayout();
		imagenLayout.addComponent(embeddedLayout);
		
		
		
		
		return imagenLayout;
	}



	private VerticalLayout buildEmbebedLayout() {
		
		embeddedLayout = new VerticalLayout();
		embeddedLayout.setMargin(false);
		embeddedLayout.setSpacing(false);
		//imagenLayout.setWidth("100%");
		//imagenLayout.setHeight("90%");
		
		return embeddedLayout;
	}



	private VerticalLayout buildComponenteLayout() {
		componenteLayout = new VerticalLayout();
		componenteLayout.setMargin(false);
		componenteLayout.setSpacing(false);
		componenteLayout.setWidth("100%");
		//componenteLayout.setHeight("10%");
		
		/*Upload upLoad = new Upload("Suba la foto aqui", receiver );
		imagenLayout.addComponent(upLoad);*/
		
		
//		dropPane.setCaption("↓↓↓↓↓↓↓ Arrastre una foto hasta aqui ↓↓↓↓↓↓↓ ");
//		componenteLayout.addComponent(dropPane);
		
		
		return componenteLayout;
	}



	private VerticalLayout buildAlta3Layout() {
		
		ubicacionLayout = new VerticalLayout();
		ubicacionLayout.setMargin(true);
		ubicacionLayout.setSpacing(true);
		ubicacionLayout.setWidth("100%");
		ubicacionLayout.setHeight("-1px");
		ubicacionLayout.addStyleName("layout-with-border");
		ubicacionLayout.setCaption("Ubicacion");
		
		
		cbxDepto = new ComboBox();
		cbxDepto.setCaption("Departamento");
		cbxDepto.setItems(jpaDepto.findDepartamentoEntities());
		cbxDepto.setItemCaptionGenerator(depto -> depto.getDescripcion());
		cbxDepto.setEmptySelectionAllowed(false);
		cbxDepto.addValueChangeListener(e -> {
			cbxCiudades.clear();
			cbxBarrio.clear();
			if (cbxDepto.getValue() != null) {
				cbxCiudades.setItems(jpaCiudad.findCiudadesbyDepto(cbxDepto.getValue()));
				cbxBarrio.setItems();
			}
			
			

		});
		
		ubicacionLayout.addComponent(cbxDepto);
		
		
		cbxCiudades = new ComboBox();
		cbxCiudades.setCaption("Ciudades");
		cbxCiudades.setEmptySelectionAllowed(false);
		cbxCiudades.setItemCaptionGenerator(ciu -> ciu.getDescripcion());
		cbxCiudades.addValueChangeListener(e -> {
			cbxBarrio.clear();
			if (cbxCiudades.getValue() != null) {
				cbxBarrio.setItems(jpaBarrio.findBarriosbyCiudad(cbxCiudades.getValue()));
			}
			
		});
		
		ubicacionLayout.addComponent(cbxCiudades);
		
		cbxBarrio = new ComboBox();
		cbxBarrio.setCaption("Barrio");
		cbxBarrio.setEmptySelectionAllowed(false);
		cbxBarrio.setItemCaptionGenerator(ba -> ba.getDescripcion());
		
		ubicacionLayout.addComponent(cbxBarrio);
		
		txtDireccion = new TextField();
		txtDireccion.setCaption("Dirección");
		
		ubicacionLayout.addComponent(txtDireccion);
		
		return ubicacionLayout;
	}



	private VerticalLayout buildAlta2Layout() {
		
		datosLaboralesLayout = new VerticalLayout();
		datosLaboralesLayout.setMargin(true);
		datosLaboralesLayout.setSpacing(true);
		datosLaboralesLayout.setWidth("100%");
		datosLaboralesLayout.setHeight("-1px");
		datosLaboralesLayout.addStyleName("layout-with-border");
		datosLaboralesLayout.setCaption("Datos Laborales");
		
		
		
		
		
		txtCodigoHumano = new TextField();
		txtCodigoHumano.setCaption("Código Humano");
		txtCodigoHumano.addBlurListener(e -> {
			if (!txtCodigoHumano.getValue().isEmpty()) {
				if (StringUtils.isNumeric(txtCodigoHumano.getValue())) {
					if (((TextField) e.getComponent()).getValue() != txtCodigoHumano.getValue()) {
						int nro = Integer.valueOf(txtCodigoHumano.getValue());
						if (jpaPer.findCodigoHumanoRepetido(nro)) {
							Notification.show("El codigo humano no puede ser repetido", Notification.TYPE_ERROR_MESSAGE);
							txtCodigoHumano.focus();
						}
					}
					
				}else {
					Notification.show("El valor del codigo humano debe ser numerico", 
							Notification.TYPE_ERROR_MESSAGE);
					txtCodigoHumano.focus();
				}
				
			}
		});
		
		datosLaboralesLayout.addComponent(txtCodigoHumano);
		
		cbxCargo = new ComboBox();
		cbxCargo.setCaption("Cargo");
		cbxCargo.setItems(jpaCargo.findCargoEntities());
		cbxCargo.setItemCaptionGenerator(cargo -> cargo.getDescripcion());
		cbxCargo.setEmptySelectionAllowed(false);
		
		datosLaboralesLayout.addComponent(cbxCargo);
		
		cbxArea = new ComboBox();
		cbxArea.setCaption("Area");
		
		datosLaboralesLayout.addComponent(cbxArea);
		
		cbxSucursal = new ComboBox<> ("Sucursal");
		cbxSucursal.setEmptySelectionAllowed(false);
		cbxSucursal.setWidth("100%");
		cbxSucursal.setItemCaptionGenerator(su -> su.getDescripcion());
		cbxSucursal.setCaption("Sucursal");
		cbxSucursal.setItems(jpaSu.findSucursalEntities());
		
		datosLaboralesLayout.addComponent(cbxSucursal);
		
		
		
		
		
		
		
		return datosLaboralesLayout;
	}



	private HorizontalLayout buildBotonLayout() {
		// TODO Auto-generated method stub
		botonLayout = new HorizontalLayout();
		
		botonLayout.setWidth("-1px");
		botonLayout.setHeight("-1px");
		botonLayout.setMargin(true);
		botonLayout.setSpacing(true);
		
		//items
		// btnAgregar
		btnAgregar = new Button();
		btnAgregar.setCaption("Agregar");
	
		btnAgregar.setWidth("-1px");
		btnAgregar.setHeight("-1px");
		btnAgregar.addStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnAgregar);
				
				// btnLimpiar
		btnCancelar = new Button();
		btnCancelar.setCaption("Cancelar");
		
		btnCancelar.setWidth("-1px");
		btnCancelar.setHeight("-1px");
		btnCancelar.addStyleName(ValoTheme.BUTTON_DANGER);
		btnCancelar.addClickListener(e ->{
			Window w = this.findAncestor(Window.class);
			w.close();
		});
		botonLayout.addComponent(btnCancelar);
				
		return botonLayout;
		
	}











	private VerticalLayout buildAltaLayout() {
		
		datosPersonalesLayout = new VerticalLayout();
		//altaLayout.setImmediate(false);
		datosPersonalesLayout.setMargin(true);
		datosPersonalesLayout.setSpacing(true);
		datosPersonalesLayout.setWidth("100.0%");
		datosPersonalesLayout.setHeight("-1px");
		datosPersonalesLayout.addStyleName("layout-with-border");
		datosPersonalesLayout.setCaption("Datos Personales");
		
		
		txtNombre = new TextField();
		txtNombre.setCaption("Nombre:");
		txtNombre.addValueChangeListener(e->{
			txtNombre.setValue(txtNombre.getValue().toUpperCase());
		});
		
		txtApellido = new TextField();
		txtApellido.setCaption("Apellido:");
		txtApellido.addValueChangeListener(e ->{
			txtApellido.setValue(txtApellido.getValue().toUpperCase());
		});
		
		txtNroDoc = new TextField();
		txtNroDoc.setCaption("Número de Documento:");
		txtNroDoc.addBlurListener(e -> {
			if (!txtNroDoc.getValue().isEmpty()) {
				if ( ((TextField) e.getComponent()).getValue() != txtNroDoc.getValue() ){
					
					txtNroDoc.setValue(txtNroDoc.getValue().replace(".","")); 
					
					if (jpaPer.findCedulaRepetido(txtNroDoc.getValue())) {
						Notification.show("El numero de documento ya esta utilizado por otra persona", 
								Notification.TYPE_ERROR_MESSAGE);
						txtNroDoc.focus();
					}else {
						txtNroDoc.setValue(StringUtils.colocarFormatoCI(txtNroDoc.getValue()));
					}
				}
					
			}	
			
			
		});
		
		dfNacimiento = new DateField();
		dfNacimiento.setCaption("Fecha de nacimiento;");
		
		
		txtCorreoPer = new TextField();
		txtCorreoPer.setCaption("Correo Personal");
		txtCorreoPer.addBlurListener(e -> {
			if (!txtCorreoPer.getValue().isEmpty()) {
				if (! StringUtils.formatoEmail(txtCorreoPer.getValue())) {
					Notification.show("El formato de correo no es valido.", Notification.TYPE_ERROR_MESSAGE);
					txtCorreoPer.clear();
					txtCorreoPer.focus();
				}
			}
			
		});
		
		txtCorreoCorp = new TextField();
		txtCorreoCorp.setCaption("Correo Corporativo");
		txtCorreoCorp.addBlurListener(e -> {
			if (!txtCorreoCorp.getValue().isEmpty()) {
				if (! StringUtils.formatoEmail(txtCorreoCorp.getValue())) {
					Notification.show("El formato de correo no es valido.", Notification.TYPE_ERROR_MESSAGE);
					txtCorreoCorp.clear();
					txtCorreoCorp.focus();
				}
			}
			
		});
		
		
		txtTelefono = new TextField();
		txtTelefono.setCaption("Telefono");
		
		
		
		
		
		
		
		/*cbxGrupo = new ComboBox();
		cbxGrupo.setCaption("Grupo");
		cbxGrupo.setEmptySelectionAllowed(false);
		cbxGrupo.setItems(JpaGrupo.findGrupoEntities());
		cbxGrupo.setItemCaptionGenerator(Grupo::getDescripcion);*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		datosPersonalesLayout.addComponent(txtNombre);
		datosPersonalesLayout.addComponent(txtApellido);
		datosPersonalesLayout.addComponent(txtNroDoc);
		datosPersonalesLayout.addComponent(dfNacimiento);
		datosPersonalesLayout.addComponent(txtCorreoPer);
		datosPersonalesLayout.addComponent(txtCorreoCorp);
		datosPersonalesLayout.addComponent(txtTelefono);
		/*altaLayout.addComponent(txtDireccion);
		altaLayout.addComponent(txtCodigoHumano);
		altaLayout.addComponent(cbxCargo);
		altaLayout.addComponent(cbxGrupo);
		altaLayout.addComponent(cbxDepto);
		altaLayout.addComponent(cbxCiudades);
		altaLayout.addComponent(cbxBarrio);
		altaLayout.addComponent(cbxArea);
		altaLayout.addComponent(cbxSucursal);
		altaLayout.addComponent(dropPane);*/
		

		
		return datosPersonalesLayout;
	}



	private void cargarCiudad(Departamento depto) {
		
		
	}
	
	
	
	public VerticalLayout getMainLayout() {
		return mainLayout;
	}



	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}



	public VerticalLayout getAltaLayout() {
		return datosPersonalesLayout;
	}



	public void setAltaLayout(VerticalLayout altaLayout) {
		this.datosPersonalesLayout = altaLayout;
	}



	public HorizontalLayout getBotonLayout() {
		return botonLayout;
	}



	public void setBotonLayout(HorizontalLayout botonLayout) {
		this.botonLayout = botonLayout;
	}



	public TextField getTxtNombre() {
		return txtNombre;
	}



	public void setTxtNombre(TextField txtNombre) {
		this.txtNombre = txtNombre;
	}



	public TextField getTxtApellido() {
		return txtApellido;
	}



	public void setTxtApellido(TextField txtApellido) {
		this.txtApellido = txtApellido;
	}



	public TextField getTxtNroDoc() {
		return txtNroDoc;
	}



	public void setTxtNroDoc(TextField txtNroDoc) {
		this.txtNroDoc = txtNroDoc;
	}



	public TextField getTxtCorreoPer() {
		return txtCorreoPer;
	}



	public void setTxtCorreoPer(TextField txtCorreoPer) {
		this.txtCorreoPer = txtCorreoPer;
	}



	public TextField getTxtCorreoCorp() {
		return txtCorreoCorp;
	}



	public void setTxtCorreoCorp(TextField txtCorreoCorp) {
		this.txtCorreoCorp = txtCorreoCorp;
	}



	public TextField getTxtTelefono() {
		return txtTelefono;
	}



	public void setTxtTelefono(TextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}



	public TextField getTxtDireccion() {
		return txtDireccion;
	}



	public void setTxtDireccion(TextField txtDireccion) {
		this.txtDireccion = txtDireccion;
	}



	public TextField getTxtCodigoHumano() {
		return txtCodigoHumano;
	}



	public void setTxtCodigoHumano(TextField txtCodigoHumano) {
		this.txtCodigoHumano = txtCodigoHumano;
	}



	public ComboBox<Cargo> getCbxCargo() {
		return cbxCargo;
	}



	public void setCbxCargo(ComboBox<Cargo> cbxCargo) {
		this.cbxCargo = cbxCargo;
	}



	



	public ComboBox<Barrio> getCbxBarrio() {
		return cbxBarrio;
	}



	public void setCbxBarrio(ComboBox<Barrio> cbxBarrio) {
		this.cbxBarrio = cbxBarrio;
	}



	public ComboBox<Area> getCbxArea() {
		return cbxArea;
	}



	public void setCbxArea(ComboBox<Area> cbxArea) {
		this.cbxArea = cbxArea;
	}



	public ComboBox<Sucursal> getCbxSucursal() {
		return cbxSucursal;
	}



	public void setCbxSucursal(ComboBox<Sucursal> cbxSucursal) {
		this.cbxSucursal = cbxSucursal;
	}



	public ComboBox<Ciudad> getCbxCiudades() {
		return cbxCiudades;
	}



	public void setCbxCiudades(ComboBox<Ciudad> cbxCiudades) {
		this.cbxCiudades = cbxCiudades;
	}



	public ComboBox<Departamento> getCbxDepto() {
		return cbxDepto;
	}



	public void setCbxDepto(ComboBox<Departamento> cbxDepto) {
		this.cbxDepto = cbxDepto;
	}



	public Button getBtnAgregar() {
		return btnAgregar;
	}



	public void setBtnAgregar(Button btnAgregar) {
		this.btnAgregar = btnAgregar;
	}



	public Button getBtnCancelar() {
		return btnCancelar;
	}



	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}


	
	
}




