package py.com.tipcsa.eva.view;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.navigator.View;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.FileDropTarget;

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

public class AltaPersonaView extends CustomComponent implements View {
	
	public VerticalLayout getMainLayout() {
		return mainLayout;
	}



	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}



	public VerticalLayout getAltaLayout() {
		return altaLayout;
	}



	public void setAltaLayout(VerticalLayout altaLayout) {
		this.altaLayout = altaLayout;
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



	public ComboBox<Grupo> getCbxGrupo() {
		return cbxGrupo;
	}



	public void setCbxGrupo(ComboBox<Grupo> cbxGrupo) {
		this.cbxGrupo = cbxGrupo;
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



	



	private VerticalLayout mainLayout;
	private VerticalLayout altaLayout;
	private HorizontalLayout botonLayout;
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField txtNroDoc;
	private TextField txtCorreoPer;
	private TextField txtCorreoCorp;
	private TextField txtTelefono;
	private TextField txtDireccion;
	private TextField txtCodigoHumano;
	private ComboBox<Cargo> cbxCargo;
	private ComboBox<Grupo> cbxGrupo;
	private ComboBox<Barrio> cbxBarrio;
	private ComboBox<Area> cbxArea;
	private ComboBox<Sucursal> cbxSucursal;
	private ComboBox<Ciudad> cbxCiudades;
	private ComboBox<Departamento> cbxDepto;
	private Button btnAgregar;
	private Button btnCancelar;
	final Label infoLabel = new Label();
	final VerticalLayout dropPane = new VerticalLayout(infoLabel);
	
	
	private JpaPersonas jpaPer = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	private JpaSucursales jpaSu = new JpaSucursales(JpaUtil.getEntityManagerFactory());
	private JpaCargo jpaCargo  = new JpaCargo(JpaUtil.getEntityManagerFactory());
	private JpaGrupos JpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	private JpaPaises jpaPais = new JpaPaises(JpaUtil.getEntityManagerFactory());
	private JpaDepartamentos jpaDepto = new JpaDepartamentos(JpaUtil.getEntityManagerFactory());
	private JpaCiudades jpaCiudad = new JpaCiudades(JpaUtil.getEntityManagerFactory());
	private JpaBarrios jpaBarrio = new JpaBarrios(JpaUtil.getEntityManagerFactory());
	
	
	
	public AltaPersonaView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		//cargar imagen
		infoLabel.setWidth(240.0f, Unit.PIXELS);
		dropPane.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropPane.addStyleName("drop-area");
        dropPane.setSizeUndefined();
        ProgressBar progress = new ProgressBar();
        progress.setIndeterminate(true);
        progress.setVisible(false);
        dropPane.addComponent(progress);
        
        
        new FileDropTarget<>(dropPane, fileDropEvent -> {
            final int fileSizeLimit = 2 * 1024 * 1024; // 2MB
 
            fileDropEvent.getFiles().forEach(html5File -> {
                final String fileName = html5File.getFileName();
 
                if (html5File.getFileSize() > fileSizeLimit) {
                    Notification.show(
                            "File rejected. Max 2MB files are accepted by Sampler",
                            Notification.Type.WARNING_MESSAGE);
                } else {
                    final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    final StreamVariable streamVariable = new StreamVariable() {
 
                       /* @Override
                        public OutputStream getOutputStream() {
                            return bas;
                        }*/
 
                        @Override
                        public boolean listenProgress() {
                            return false;
                        }
 
                        @Override
                        public void onProgress(
                                final StreamingProgressEvent event) {
                        }
 
                        @Override
                        public void streamingStarted(
                                final StreamingStartEvent event) {
                        }
 
                        @Override
                        public void streamingFinished(
                                final StreamingEndEvent event) {
                            progress.setVisible(false);
                            showFile(fileName, bas);
                        }
 
                        @Override
                        public void streamingFailed(
                                final StreamingErrorEvent event) {
                            progress.setVisible(false);
                        }
 
                        @Override
                        public boolean isInterrupted() {
                            return false;
                        }

						@Override
						public OutputStream getOutputStream() {
							// TODO Auto-generated method stub
							return bas;
						}
                    };
                    html5File.setStreamVariable(streamVariable);
                    progress.setVisible(true);
                }
            });
        });
		
		
		
		
		
		btnAgregar.addClickListener(event -> agregar());
		
	}
	
	
	 private void showFile(final String name, final ByteArrayOutputStream bas) {
	        // resource for serving the file contents
	        final StreamSource streamSource = () -> {
	            if (bas != null) {
	                final byte[] byteArray = bas.toByteArray();
	                return new ByteArrayInputStream(byteArray);
	            }
	            return null;
	        };
	        final StreamResource resource = new StreamResource(streamSource, name);
	 
	        // show the file contents - images only for now
	        final Embedded embedded = new Embedded(name, resource);
	        showComponent(embedded, name);
	    }
	 
	 
	 
	 
	 
	 private void showComponent(final Component c, final String name) {
	        final VerticalLayout layout = new VerticalLayout();
	        layout.setSizeUndefined();
	        layout.setMargin(true);
	        final Window w = new Window(name, layout);
	        w.addStyleName("dropdisplaywindow");
	        w.setSizeUndefined();
	        w.setResizable(false);
	        c.setSizeUndefined();
	        layout.addComponent(c);
	        UI.getCurrent().addWindow(w);
	    }
	
	
	
	
	
	
	
	public AltaPersonaView(Persona persona) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		cargarDatos(persona);
		btnAgregar.setCaption("Editar");
		
		
		
		
		btnAgregar.addClickListener(e -> editarPersona(persona));
		
		
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
		try {
			jpaPer.edit(persona);
			Notification.show("Persona editada correctamente.");
			Window w = this.findAncestor(Window.class);
			w.close();
			
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
		cbxGrupo.setSelectedItem(persona.getGrupo());
		cbxArea.setSelectedItem(persona.getArea());
		cbxSucursal.setSelectedItem(persona.getSucursal());
		//cbxDepto.setSelectedItem();
		if (persona.getBarrio() != null) {
			Ciudad ciudadPersona = jpaCiudad.findCiudad(persona.getBarrio().getCiudad());
			Departamento deptoPersona = jpaDepto.findDepartamento(ciudadPersona.getDepartamento());
			cbxDepto.setSelectedItem(deptoPersona);
			cbxCiudades.setItems(jpaCiudad.findCiudadesbyDepto(deptoPersona));
			cbxCiudades.setSelectedItem(ciudadPersona);
			cbxBarrio.setItems(jpaBarrio.findBarriosbyCiudad(ciudadPersona));
			cbxBarrio.setSelectedItem(persona.getBarrio());
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
		personaAdd.setGrupo(cbxGrupo.getValue());
		personaAdd.setSucursal(cbxSucursal.getValue());
		personaAdd.setBarrio(cbxBarrio.getValue());
		
		try {
			jpaPer.create(personaAdd);
			Notification.show("Persona agregada exitosamente");
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
		altaLayout = buildAltaLayout();
		mainLayout.addComponent(altaLayout);
		mainLayout.addComponent(dropPane);
		botonLayout = buildBotonLayout();
		mainLayout.addComponent(botonLayout);
		mainLayout.setComponentAlignment(botonLayout, new Alignment(20));
		
		return mainLayout;
		





	// busquedaLayout

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
		botonLayout.addComponent(btnAgregar);
				
				// btnLimpiar
		btnCancelar = new Button();
		btnCancelar.setCaption("Cancelar");
		
		btnCancelar.setWidth("-1px");
		btnCancelar.setHeight("-1px");
		botonLayout.addComponent(btnCancelar);
				
		return botonLayout;
		
	}











	private VerticalLayout buildAltaLayout() {
		altaLayout = new VerticalLayout();
		//altaLayout.setImmediate(false);
		altaLayout.setMargin(true);
		altaLayout.setSpacing(true);
		altaLayout.setWidth("100.0%");
		altaLayout.setHeight("-1px");
	
		
		
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
				txtNroDoc.setValue(txtNroDoc.getValue().replace(".","")); 
					if (jpaPer.findCedulaRepetido(txtNroDoc.getValue())) {
						Notification.show("El numero de documento ya esta utilizado por otra persona", 
								Notification.TYPE_ERROR_MESSAGE);
						txtNroDoc.focus();
					}else {
						txtNroDoc.setValue(StringUtils.colocarFormatoCI(txtNroDoc.getValue()));
					}
			}	
			
			
		});
		
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
		
		txtDireccion = new TextField();
		txtDireccion.setCaption("Dirección");
		
		txtCodigoHumano = new TextField();
		txtCodigoHumano.setCaption("Código Humano");
		txtCodigoHumano.addBlurListener(e -> {
			if (!txtCodigoHumano.getValue().isEmpty()) {
				if (StringUtils.isNumeric(txtCodigoHumano.getValue())) {
					int nro = Integer.valueOf(txtCodigoHumano.getValue());
					if (jpaPer.findCodigoHumanoRepetido(nro)) {
						Notification.show("El codigo humano no puede ser repetido", Notification.TYPE_ERROR_MESSAGE);
						txtCodigoHumano.focus();
					}
				}else {
					Notification.show("El valor del codigo humano debe ser numerico", 
							Notification.TYPE_ERROR_MESSAGE);
					txtCodigoHumano.focus();
				}
				
			}
		});
		
		cbxCargo = new ComboBox();
		cbxCargo.setCaption("Cargo");
		cbxCargo.setItems(jpaCargo.findCargoEntities());
		cbxCargo.setItemCaptionGenerator(cargo -> cargo.getDescripcion());
		cbxCargo.setEmptySelectionAllowed(false);
		
		cbxGrupo = new ComboBox();
		cbxGrupo.setCaption("Grupo");
		cbxGrupo.setEmptySelectionAllowed(false);
		cbxGrupo.setItems(JpaGrupo.findGrupoEntities());
		cbxGrupo.setItemCaptionGenerator(Grupo::getDescripcion);
		
		cbxBarrio = new ComboBox();
		cbxBarrio.setCaption("Barrio");
		cbxBarrio.setEmptySelectionAllowed(false);
		cbxBarrio.setItemCaptionGenerator(ba -> ba.getDescripcion());
		
		
		cbxArea = new ComboBox();
		cbxArea.setCaption("Area");
		
		cbxSucursal = new ComboBox<> ("Sucursal");
		cbxSucursal.setEmptySelectionAllowed(false);
		cbxSucursal.setWidth("20%");
		cbxSucursal.setItemCaptionGenerator(su -> su.getDescripcion());
		cbxSucursal.setCaption("Sucursal");
		cbxSucursal.setItems(jpaSu.findSucursalEntities());
		
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
		
		
		altaLayout.addComponent(txtNombre);
		altaLayout.addComponent(txtApellido);
		altaLayout.addComponent(txtNroDoc);
		altaLayout.addComponent(txtCorreoPer);
		altaLayout.addComponent(txtCorreoCorp);
		altaLayout.addComponent(txtTelefono);
		altaLayout.addComponent(txtDireccion);
		altaLayout.addComponent(txtCodigoHumano);
		altaLayout.addComponent(cbxCargo);
		altaLayout.addComponent(cbxGrupo);
		altaLayout.addComponent(cbxDepto);
		altaLayout.addComponent(cbxCiudades);
		altaLayout.addComponent(cbxBarrio);
		altaLayout.addComponent(cbxArea);
		altaLayout.addComponent(cbxSucursal);
		altaLayout.addComponent(dropPane);

		
		return altaLayout;
	}



	private void cargarCiudad(Departamento depto) {
		
		
	}
	
	
}




