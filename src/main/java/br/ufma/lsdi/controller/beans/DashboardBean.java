package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.util.WebUtil;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.List;

@Controller
@Scope("view")
public class DashboardBean {

    private MapModel mapModel;
    private String centerGeoMap = "-2.497438, -44.278959";
    private LatLng coord1 = new LatLng(-2.483309, -44.262307);
    private Marker marker;
    private Resource resource;
    private String score = "AQI 30";
    @PostConstruct
    public void init() {
        resource = getMakerResource();
        initObjects();
    }

    private void initObjects() {
        mapModel = new DefaultMapModel();
        mapModel.addOverlay(new Marker(coord1,resource.getDescription(),resource,
                "http://icongal.com/gallery/image/447363/map_marker_outside_chartreuse.png"));
    }

    private  Resource getMakerResource(){
        Resource res = new Resource();
        res.setId(5207);
        res.setCreated_at( "2018-11-28T00:39:07.470Z");
        res.setUpdated_at("2018-11-28T00:39:07.470Z");
        res.setLat(-2.498837);
        res.setLon(-44.311237);
        res.setStatus("Ativo");
        res.setDescription("ESTAÇÃO PRAIA PONTA DA AREA");
        res.setUuid("70b8f4fe-3f17-4dcb-beff-cfb586fb344f");
        List<String> capabilities= Arrays.asList(
                "SULFURE_DIOXIDE",
                "NITROGEN_DIOXIDE",
                "OZONE",
                "PM10",
                "PM25"
        );
         res.setCapabilities(capabilities);
        return res;
    }

    public String visualizarRecurso(){
        WebUtil.flashScope().put("resource", resource);
        return "pretty:visualizarRecurso";
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
     * @param event
     */
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        resource = (Resource) marker.getData();
        //System.out.println(res.getDescription());
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
