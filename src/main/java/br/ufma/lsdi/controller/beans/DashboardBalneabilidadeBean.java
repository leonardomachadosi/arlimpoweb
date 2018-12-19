package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.GetDataContextResource;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.ResourceClient;
import br.ufma.lsdi.util.WebUtil;
import com.google.gson.Gson;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@Scope("view")
public class DashboardBalneabilidadeBean {

    private MapModel mapModel;
    private String centerGeoMap = "-2.497438, -44.278959";
    private Marker marker;
    private Resource resource;
    private ResourceAuxiliar resourceAuxiliar;
    private ResourceAuxiliar resourceSensors;
    private ResourceClient resourceClient;
    private List<String> uuids;
    private List<String> capabilities;


    private Catalog catalog;
    private CapabilityDataAuxiliar capabilityDataAuxiliar;

    public DashboardBalneabilidadeBean(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    @PostConstruct
    public void init() {
        resource = new Resource();
        initObjects();
        carregarRecursos();

    }


    private void carregarRecursos() {
        resourceAuxiliar = resourceClient.getAllResourceByCapability();

        if (resourceAuxiliar != null && !resourceAuxiliar.getResources().isEmpty()) {
            for (Resource re : resourceAuxiliar.getResources()) {
                if (re.getLat() != null) {
                    for (String cap : re.getCapabilities()) {
                        if (cap.equals("BALNEABILIDADE")) {
                            uuids.add(re.getUuid());
                        }
                    }
                }
            }
        }

        if (!uuids.isEmpty()) {
            catalog = new Catalog();
            capabilities.addAll(Arrays.asList("BALNEABILIDADE"));
            catalog.setCapabilities(capabilities);
            catalog.setUuids(uuids);
            try {
                getLastData(catalog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getLastData(Catalog catalog) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(catalog));
        ResourceHelper resourceHelper = resourceClient.getLastData(catalog);
        System.out.println(gson.toJson(resourceHelper));
        if (resourceHelper != null && resourceHelper.getResources() != null) {

            for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();

                List<Map<String, Object>> data = capability.get("BALNEABILIDADE");

                if (data != null) {
                    for (Map<String, Object> cap : data) {

                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                        if (dataAuxiliar.getLat() != null) {
                            plot(dataAuxiliar);
                        }
                    }
                }
            }
        }
    }

    private void initObjects() {
        mapModel = new DefaultMapModel();
        capabilities = new ArrayList<>();
        uuids = new ArrayList<>();
        catalog = new Catalog();
    }


    public void plot(CapabilityDataAuxiliar capabilityDataAuxiliar) {

        String icon = "";
        LatLng latLng = new LatLng(capabilityDataAuxiliar.getResource().getLat(), capabilityDataAuxiliar.getResource().getLon());

        if (capabilityDataAuxiliar.getValue().equals("PROPRIO")) {
            icon = "http://icongal.com/gallery/image/447363/map_marker_outside_chartreuse.png";
        } else if (capabilityDataAuxiliar.getValue().equals("IMPROPRIO")) {
            icon = "http://icongal.com/gallery/image/447372/map_marker_outside_pink.png";
        } else {
            icon="http://icongal.com/gallery/image/460192/inside_icons_azure_marker_map_socialize_base.png";
        }

        mapModel.addOverlay(new Marker(latLng,
                capabilityDataAuxiliar.getResource().getDescription(), capabilityDataAuxiliar,
                icon));
    }


    public String visualizarRecurso() {
        WebUtil.flashScope().put("resource", capabilityDataAuxiliar.getResource());
        return "pretty:visualizarBalneabilidade";
    }


    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public Marker getMarker() {
        return marker;
    }



    /**
     * Listener click marker map
     *
     * @param event
     */
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        capabilityDataAuxiliar = (CapabilityDataAuxiliar) marker.getData();
        //System.out.println(res.getDescription());
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public CapabilityDataAuxiliar getCapabilityDataAuxiliar() {
        return capabilityDataAuxiliar;
    }

    public void setCapabilityDataAuxiliar(CapabilityDataAuxiliar capabilityDataAuxiliar) {
        this.capabilityDataAuxiliar = capabilityDataAuxiliar;
    }
}
