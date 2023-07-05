package py.com.tipcsa.eva.view;

import java.io.File;
import java.text.DecimalFormat;


import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.beans.GrupoAcumuladorContador;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.CargoPregunta;
import py.com.tipcsa.eva.entities.Evaluacion;
import py.com.tipcsa.eva.entities.EvaluacionDetalle;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.jpa.JpaCargoPregunta;
import py.com.tipcsa.eva.jpa.JpaEvaluacion;
import py.com.tipcsa.eva.jpa.JpaEvaluacionDetalle;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.jpa.JpaPersonas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.UserUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "evaluacionCabecera", displayName = "Evaluacion")
public class EvaluacionCabeceraView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private	VerticalLayout formLayout;
	private HorizontalLayout maquetaLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout datosPersonaLayout;
	private VerticalLayout personaLayout;
	private VerticalLayout fotoLayout;
	private VerticalLayout gridLayout;
	private HorizontalLayout botonLayout;
	
	private Label lblNombre;
	private Label lblEvaluador;
	private Label lblCargo;
	
	private Button btnEditar;
	private Button btnEvaluacion;
	private Button btnSalir;
	
	private Grid<Evaluacion> gridEvaluacion;
	
	private JpaEvaluacion jpaEvaluacion = new JpaEvaluacion(JpaUtil.getEntityManagerFactory());
	
	private JpaEvaluacionDetalle jpaEvDet = new JpaEvaluacionDetalle(JpaUtil.getEntityManagerFactory());
	
	private JpaCargoPregunta jpaCarPre = new JpaCargoPregunta(JpaUtil.getEntityManagerFactory());
	
	private JpaGrupos jpaGrupo = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	
	private JpaPersonas jpaPersona = new JpaPersonas(JpaUtil.getEntityManagerFactory());
	
	private Window ventana;
	
	private List<GrupoAcumuladorContador> listGrupoAcuCont ;
	
	/*private Persona persona ;
	
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}*/

	public EvaluacionCabeceraView(Persona persona) {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblNombre.setCaption("Nombre:");
		lblNombre.setValue(persona.getNombre() + " "+ persona.getApellido());
		
		lblCargo.setCaption("Cargo:");
		lblCargo.setValue(persona.getCargo().getDescripcion());
		
		
		
		gridEvaluacion.setItems(jpaEvaluacion.findEvaluacionByEvaluado(persona));
		
		btnEvaluacion.addClickListener(e -> Evaluar(persona));
		
		btnSalir.addClickListener(e -> salir());
		
		btnEditar.addClickListener(e -> editar(persona));
		
		cargarEvaluador(persona.getCargo());
		if (persona.getFoto() != null) {
			
			cargarFoto(persona.getFoto());
			
		}else {
			cargarFotoDefault("images/cabeceraImagen.png");
		}
		
		if (persona.getPersona() == UserUtil.getUsuario().getPersona().getPersona()) {
			
			btnEvaluacion.setEnabled(false);
		}
		
		
	}

	private void cargarFotoDefault(String string) {
		
		Image im = new Image();
		im.setSource(new ThemeResource(string));
		//im.setWidth("40%");
		fotoLayout.addComponent(im);
		
	}

	private void cargarFoto(String foto) {
		
		
		File file = new File(foto);
		Image im = new Image();
		im.setSource(new FileResource(file));
		im.setWidth("300px");
		im.setHeight("300px");
		fotoLayout.addComponent(im);
		
	}

	private void cargarEvaluador(Cargo cargo) {
		
		for (Persona perso : jpaPersona.findEvaluadorByCargo(cargo)) {
			
			lblEvaluador = new Label();
			lblEvaluador.setCaption("Evaluador:");
			lblEvaluador.setValue(perso.getNombre() + " "+ perso.getApellido());
			personaLayout.addComponent(lblEvaluador);
			
		}
		
		
		
	}

	private void editar(Persona persona) {
		
		AltaPersonaView eva = new AltaPersonaView(persona);
		ventana = new Window("Alta Persona", eva);
		ventana.center();
		ventana.setSizeFull();
		UI.getCurrent().addWindow(ventana);
		
		
	}

	private void salir() {
		 
		Window w = this.findAncestor(Window.class);
		w.close();
		
	}

	private void Evaluar(Persona persona) {
		
		EvaluacionDetalleView eva = new EvaluacionDetalleView(persona);
		ventana = new Window("Evaluacion", eva);
		ventana.center();
		ventana.setSizeFull();
		UI.getCurrent().addWindow(ventana);
		
		ventana.addCloseListener(e -> {
				
			if (eva.guardado) {
				guardar(persona,eva.getListEvDet());
				
			}
				
				
				
			});
		
	}

	private double calcularNotaTotal(Persona persona, List<EvaluacionDetalle> list) {
		
		Double nota = new Double(0);
		
		double promedioGrupo = new Double(0);
		
		listGrupoAcuCont = new ArrayList<GrupoAcumuladorContador>();
		
		for (Grupo grupoFind : jpaGrupo.findGruposDistinctCargoPreguntaByCargo(persona.getCargo())) {
			
			GrupoAcumuladorContador gruAcuCont = new GrupoAcumuladorContador();
			gruAcuCont.setAcumulador(new Double(0));
			gruAcuCont.setContador(0);
			gruAcuCont.setGrupo(grupoFind);
			
			listGrupoAcuCont.add(gruAcuCont);
			
		}
		
		for (GrupoAcumuladorContador gruAcuCon : listGrupoAcuCont) {
			
			for (EvaluacionDetalle evDet : list) {
				
				Grupo grup = jpaGrupo.findGruposPruguntaAndCargo(persona.getCargo(), evDet.getPregunta()); 
				
				nota = new Double(0);
				
				if (  grup.getGrupo().equals(gruAcuCon.getGrupo().getGrupo())) {
					
					nota = (nota + 5) * 20;
					
					if (nota > 0) {
						
						nota = (((CargoPregunta)jpaCarPre.findCargoPreguntaByCargoPregunta(
								persona.getCargo(),evDet.getPregunta())).getPeso() *
								nota)/100;
						
					}
					
					
					
					gruAcuCon.setAcumulador( gruAcuCon.getAcumulador() + nota);
					
					gruAcuCon.setContador(gruAcuCon.getContador() + 1 );
				}
				
			}
			
		}
		
		for (GrupoAcumuladorContador gruAcuCont : listGrupoAcuCont ) {
			
			double prePro = new Double(0);
			
			if (gruAcuCont.getAcumulador() > 0) {
			
				prePro = (gruAcuCont.getAcumulador()/gruAcuCont.getContador());
				
			}
				
			promedioGrupo = promedioGrupo + prePro;
			
		}
		
		
		if ( promedioGrupo > 0) {
			
			Double promedio = new Double(promedioGrupo/listGrupoAcuCont.size());
			
			return promedio;
			
		}else {
			
			return 0;
			
		}
		
		
		
		
		
		
		
		
	}

	private void guardar(Persona persona, List<EvaluacionDetalle> list) {
		
		Evaluacion ev = new Evaluacion();
		ev.setEvaluacion(1);
		ev.setEvaluador(persona);
		ev.setEvaluado(persona);
		ev.setPuntaje(calcularPuntaje(list, persona));
		ev.setPuntajeTotal(calcularNotaTotal(persona, list));
		ev.setNota(calcularNota(ev.getPuntajeTotal(), ev.getPuntaje()));
		
		try {
			jpaEvaluacion.create(ev);
			Notification.show("Evaluacion calificada correctamente.");
			guardarDetalle(list, ev);
			gridEvaluacion.setItems(jpaEvaluacion.findEvaluacionByEvaluado(persona));
		} catch (Exception e) {
			
		}
		
		
		
	}

	private String calcularNota(double puntajeTotal, double puntaje) {
		
		
		double porcentaje = puntaje * 100 / puntajeTotal;
		
		if ((porcentaje <= 100) && (porcentaje >= 90) ) {
			
			return "A";
		}
		
		if ((porcentaje < 90 ) && (porcentaje >= 80) ) {
			
			return "B";
		}
		
		if ((porcentaje < 80) && (porcentaje >= 70) ) {
			
			return "C";
		}
		
		if ((porcentaje < 70) && (porcentaje >= 60)) {
			
			return "D";
		}
		
		if (porcentaje < 60) {
			
			return "E";
		}
		
		
		return "E";
	}

	private void guardarDetalle(List<EvaluacionDetalle> list, Evaluacion ev) {
		
		for (EvaluacionDetalle evDet : list) {
			
			
			
			evDet.setEvaluacion(ev);
			
			try {
				jpaEvDet.create(evDet);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

	private double calcularPuntaje(List<EvaluacionDetalle> list, Persona persona) {
		
		Double nota = new Double(0);
		
		double promedioGrupo = new Double(0);
		
		listGrupoAcuCont = new ArrayList<GrupoAcumuladorContador>();
		
		for (Grupo grupoFind : jpaGrupo.findGruposDistinctCargoPreguntaByCargo(persona.getCargo())) {
			
			GrupoAcumuladorContador gruAcuCont = new GrupoAcumuladorContador();
			gruAcuCont.setAcumulador(new Double(0));
			gruAcuCont.setContador(0);
			gruAcuCont.setGrupo(grupoFind);
			
			listGrupoAcuCont.add(gruAcuCont);
			
		}
		
		for (GrupoAcumuladorContador gruAcuCon : listGrupoAcuCont) {
			
			for (EvaluacionDetalle evDet : list) {
				
				Grupo grup = jpaGrupo.findGruposPruguntaAndCargo(persona.getCargo(), evDet.getPregunta()); 
				
				nota = new Double(0);
				
				if (  grup.getGrupo().equals(gruAcuCon.getGrupo().getGrupo())) {
					
					nota = (nota + evDet.getValor().getPuntaje()) * 20;
					
					if (nota > 0) {
						
						nota = (((CargoPregunta)jpaCarPre.findCargoPreguntaByCargoPregunta(
								persona.getCargo(),evDet.getPregunta())).getPeso() *
								nota)/100;
						
					}
					
					
					
					gruAcuCon.setAcumulador( gruAcuCon.getAcumulador() + nota);
					
					gruAcuCon.setContador(gruAcuCon.getContador() + 1 );
				}
				
			}
			
		}
		
		
		for (GrupoAcumuladorContador gruAcuCont : listGrupoAcuCont ) {
			
			double prePro = new Double(0);
			
			if (gruAcuCont.getAcumulador() > 0) {
			
				prePro = (gruAcuCont.getAcumulador()/gruAcuCont.getContador());
				
			}
				
			promedioGrupo = promedioGrupo + prePro;
			
		}
		
		
		if ( promedioGrupo > 0) {
			
			DecimalFormat df2 = new DecimalFormat("#.##");
			
			Double promedio = new Double(promedioGrupo/listGrupoAcuCont.size());
			
			df2.format(promedioGrupo);
			
			return promedio;
			
		}else {
			
			return 0;
			
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
		
		maquetaLayout = buildMaquetaLayout();
		formLayout.addComponent(maquetaLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnSalir);
		
		return botonLayout;
	}

	private HorizontalLayout buildMaquetaLayout() {
		
		maquetaLayout = new HorizontalLayout();
		maquetaLayout.setMargin(false);
		
		datosLayout = buildDatosLayout();
		maquetaLayout.addComponent(datosLayout);
		
		gridLayout = buildGridLayout();
		maquetaLayout.addComponent(gridLayout);
		
		return maquetaLayout;
	}

	private VerticalLayout buildGridLayout() {
		
		gridLayout = new VerticalLayout();
		gridLayout.setMargin(false);
		String css = ".layout-with-border {\n" + 
	            "    border: 1px solid black;\n" + 
	            "}";
		Styles styles = Page.getCurrent().getStyles();
		styles.add(css);
		gridEvaluacion = new Grid<Evaluacion>();
		gridEvaluacion.setCaption("Ultima Evaluacion");
		DecimalFormat df1 = new DecimalFormat("#.##");
		DecimalFormat df2 = new DecimalFormat("#.##");
		gridEvaluacion.addColumn(eva -> df1.format(eva.getPuntaje())).setCaption("Puntaje").setId("puntaje");
		//gridEvaluacion.addColumn( String.valueOf(calcularNotaTotal(persona,eva.getListEvDet()))).setCaption("Total").setId("total");
		gridEvaluacion.addColumn(eva -> df2.format(eva.getPuntajeTotal())).setCaption("Puntaje Total").setId("total");
		gridEvaluacion.addColumn(eva -> eva.getNota()).setCaption("Calificacion").setId("nota");
		gridEvaluacion.addColumn(eva -> eva.getEvaluador().getNombre() + " " +
		eva.getEvaluador().getApellido()).setId("evaluador").setCaption("Evaluador");
		
		gridLayout.addComponent(gridEvaluacion);
		
		
		return gridLayout;
	}

	private VerticalLayout buildDatosLayout() {
		
		datosLayout = new VerticalLayout();
		datosLayout.setMargin(false);
		
		datosPersonaLayout = buildDatosPersonaLayout();
		datosLayout.addComponent(datosPersonaLayout);
		
		btnEvaluacion = new Button();
		btnEvaluacion.setCaption("Ir a Evaluacion");
		datosLayout.addComponent(btnEvaluacion);
		
		
		
		
		return datosLayout;
	}

	private HorizontalLayout buildDatosPersonaLayout() {
		
		datosPersonaLayout = new HorizontalLayout();
		datosPersonaLayout.setMargin(false);
		
		fotoLayout = buildFotoLayout();
		datosPersonaLayout.addComponent(fotoLayout);
		
		personaLayout = buildPersonaLayout();
		datosPersonaLayout.addComponent(personaLayout);
		
		
		
		return datosPersonaLayout;
	}

	private VerticalLayout buildPersonaLayout() {
		
		personaLayout = new VerticalLayout();
		personaLayout.setMargin(false);
		
		lblNombre = new Label();
		personaLayout.addComponent(lblNombre);
		
		lblCargo = new Label();
		personaLayout.addComponent(lblCargo);
		
		btnEditar = new Button();
		btnEditar.setCaption("Editar");
		personaLayout.addComponent(btnEditar);
		
		return personaLayout;
	}

	private VerticalLayout buildFotoLayout() {
		
		fotoLayout = new VerticalLayout();
		fotoLayout.setMargin(false);
		/*fotoLayout.setWidth("10%");
		fotoLayout.setHeight("%");*/
		
		return fotoLayout;
	}
	
}
