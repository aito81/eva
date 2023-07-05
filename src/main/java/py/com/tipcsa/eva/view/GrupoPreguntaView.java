package py.com.tipcsa.eva.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.tipcsa.eva.EvaUI;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.GrupoPregunta;
import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.jpa.JpaGrupoPregunta;
import py.com.tipcsa.eva.jpa.JpaGrupos;
import py.com.tipcsa.eva.jpa.JpaPreguntas;
import py.com.tipcsa.eva.util.JpaUtil;
import py.com.tipcsa.eva.util.ViewConfig;

@ViewConfig(uri = "grupoPregunta", displayName = "Asignar preguntas a grupos.")
public class GrupoPreguntaView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout maquetaLayout;
	private VerticalLayout datosAgregarLayout;
	private VerticalLayout datosAgregadosLayout;
	private HorizontalLayout botonLayout;
	
	private ComboBox<Grupo> cbxGrupo;
	
	private Grid<Pregunta> gridPreguntaAgregar;
	private Grid<GrupoPregunta> gridPreguntaAgregada;
	
	private Button btnVolver;
	
	private JpaGrupos jpaGru = new JpaGrupos(JpaUtil.getEntityManagerFactory());
	private JpaPreguntas jpaPregunta = new JpaPreguntas(JpaUtil.getEntityManagerFactory());
	private JpaGrupoPregunta jpaPreGru = new JpaGrupoPregunta(JpaUtil.getEntityManagerFactory());
	
	private Window ventana;
	
	private String valor;
	
	private boolean guardado;
	
	public GrupoPreguntaView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		formLayout.setCaption("Asignar Grupos a las preguntas.");
		
		cbxGrupo.addValueChangeListener(e -> {
//			gridPreguntaAgregar.setItems(jpaPregunta.findPreguntasNotbyGrupo(e.getValue()));
			gridPreguntaAgregar.setItems(jpaPregunta.findPreguntasNotInGrupo());
			gridPreguntaAgregada.setItems(jpaPreGru.findGrupoPreguntabyGrupo(e.getValue()));
		});
		
		gridPreguntaAgregar.addItemClickListener(e -> {
			
			if (e.getMouseEventDetails().isDoubleClick()) {
				cargarPregunta(e.getItem());
			}
			
		});
		
		gridPreguntaAgregada.addItemClickListener(e -> {
			if (e.getMouseEventDetails().isDoubleClick()) {
				sacarPregunta(e.getItem());
			}
		});
		
		btnVolver.addClickListener(e -> EvaUI.getCurrent().getNavigator().navigateTo(""));
		
		
		
		
		
	}

	private HorizontalLayout buildBotonLayout() {
		
		botonLayout = new HorizontalLayout();
		
		btnVolver = new Button();
		btnVolver.setCaption("Volver");
		btnVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnVolver);
		
		return botonLayout;
	}

	private void sacarPregunta(GrupoPregunta gruPre) {
		
		Grupo gru = new Grupo();
		gru = gruPre.getGrupo();
		
		try {
			jpaPreGru.destroy(gruPre.getGrupoPregunta());
			Notification.show("La pregunta fue desasignada correctamente.");
//			gridPreguntaAgregar.setItems(jpaPregunta.findPreguntasNotbyGrupo(gru));
			gridPreguntaAgregar.setItems(jpaPregunta.findPreguntasNotInGrupo());
			gridPreguntaAgregada.setItems(jpaPreGru.findGrupoPreguntabyGrupo(gru));
		} catch (Exception e) {
			Notification.show("Error al desasignar pregunta de grupo >> "+ e.getMessage());
		}
	
	}

	private void cargarPregunta(Pregunta pregu) {
		
		
		
		
		
		GrupoPregunta addGruPre = new GrupoPregunta();
		addGruPre.setGrupoPregunta(1);
		addGruPre.setGrupo(cbxGrupo.getValue());
		addGruPre.setPregunta(pregu);
		
		//String valor = txtPeso.getValue().replace(",", ".");
		//addPregu.setPeso(Double.valueOf(valor));
		
		
		
		try {
			jpaPreGru.create(addGruPre);
			Notification.show("Pregunta asignada correctamente.");
			gridPreguntaAgregada.setItems(jpaPreGru.findGrupoPreguntabyGrupo(addGruPre.getGrupo()));
//			gridPreguntaAgregar.setItems(jpaPregunta.findPreguntasNotbyGrupo(addGruPre.getGrupo()));
			gridPreguntaAgregar.setItems(jpaPregunta.findPreguntasNotInGrupo());
		} catch (Exception e1) {
			Notification.show("Error al asignar pregunta al grupo >> "+ e1.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
		
		
		
		
		
	}

	private void abrirVentana(Pregunta pregu) {
		
		MiniView alta = new MiniView();
		ventana = new Window("Peso", alta);
		ventana.center();
		ventana.setWidth("30%");
		ventana.setHeight("50%");
		UI.getCurrent().addWindow(ventana);
		
		
		ventana.addCloseListener(e-> {
			
			if (alta.isGuardado()) {
				
				
				
				
			}
		});
		
		
	
	
		
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

	private HorizontalLayout buildMaquetaLayout() {
		
		maquetaLayout = new HorizontalLayout();
		maquetaLayout.setWidth("100%");
		maquetaLayout.setMargin(false);
		
		datosAgregarLayout = buildDatosAgregarLayout();
		maquetaLayout.addComponent(datosAgregarLayout);
		
		datosAgregadosLayout = buildDatosAgregadosLayout();
		maquetaLayout.addComponent(datosAgregadosLayout);
		maquetaLayout.setComponentAlignment(datosAgregadosLayout, Alignment.BOTTOM_CENTER);
		
		return maquetaLayout;
	}

	private VerticalLayout buildDatosAgregadosLayout() {
		
		datosAgregadosLayout = new VerticalLayout();
		datosAgregadosLayout.setMargin(false);
		
		gridPreguntaAgregada = new Grid<GrupoPregunta>();
		gridPreguntaAgregada.setCaption("Preguntas que ya se encuentran asignadas al grupo.");
		gridPreguntaAgregada.addColumn(grupoPregunta -> grupoPregunta.getPregunta().getDescripcion()
				).setCaption("Preguntas Agregadas").setId("pregunta");
		/*gridPreguntaAgregada.addColumn(grupoPregunta -> String.valueOf(grupoPregunta.getPregunta().getPeso()).replace(".",".")
				).setCaption("Peso").setId("peso");*/
		datosAgregadosLayout.addComponent(gridPreguntaAgregada);
		
		return datosAgregadosLayout;
	}

	private VerticalLayout buildDatosAgregarLayout() {
		
		datosAgregarLayout = new VerticalLayout();
		datosAgregarLayout.setMargin(false);
		datosAgregarLayout.setWidth("100%");
		
		cbxGrupo = new ComboBox<Grupo>();
		cbxGrupo.setCaption("Grupo");
		cbxGrupo.setItemCaptionGenerator(Grupo::getDescripcion);
		cbxGrupo.setItems(jpaGru.findGrupoEntities());
		cbxGrupo.setEmptySelectionAllowed(false);
		cbxGrupo.setWidth("80%");
		datosAgregarLayout.addComponent(cbxGrupo);
		
		gridPreguntaAgregar = new Grid<Pregunta>();
		gridPreguntaAgregar.setCaption("Preguntas que no han sido asignadas al grupo.");
		gridPreguntaAgregar.addColumn(pregunta -> pregunta.getDescripcion()).setCaption("Pregunta").setId("pregunta");
		/*gridPreguntaAgregar.addColumn(pregunta -> (String.valueOf(pregunta.getPeso())).replace(".", ",") ).setCaption(
				"Peso").setId("peso");*/
		datosAgregarLayout.addComponent(gridPreguntaAgregar);
		
		return datosAgregarLayout;
	}
	
}
