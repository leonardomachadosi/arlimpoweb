package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.GetDataContextResource;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.model.auxiliar.ResourceData;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.ResourceClient;
import br.ufma.lsdi.util.IndexUtil;
import br.ufma.lsdi.util.WebUtil;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@Scope("view")
public class DashboardBean {

    private MapModel mapModel;
    private String centerGeoMap = "-2.497438, -44.278959";
    private Marker marker;
     private ResourceAuxiliar resourceAuxiliar;
    private ResourceAuxiliar resourceSensors;
    private ResourceClient resourceClient;
    private List<String> uuids;
    private List<String> capabilities;
    private ResourceHelper resourceHelper;
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;
    private Catalog catalog;
    private List<ResourceData> resourceDatas;
    private ResourceData resourceData;

    public DashboardBean(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    @PostConstruct
    public void init() {
        resourceData = new ResourceData();
        initObjects();
        carregarRecursosSensores();

    }

    private void carregarRecursosSensores() {
        resourceAuxiliar = resourceClient.getAllResourceSensor();
        if (resourceAuxiliar != null && !resourceAuxiliar.getResources().isEmpty()) {
            for (Resource re : resourceAuxiliar.getResources()) {
                if (re.getLat() != null) {
                    for (String cap : re.getCapabilities()) {
                        if (cap.equals("PM10")) {
                            if (!re.getUuid().equals("907850e5-e6ef-4958-9e83-1e461a535355")) {
                                uuids.add(re.getUuid());
                            }
                        }
                    }
                }
            }
        }

        if (!uuids.isEmpty()) {
            catalog = new Catalog();
            capabilities.addAll(Arrays.asList("PM25","PM25","OZONE", "SULFURE_DIOXIDE", "NITROGEN_DIOXIDE"));
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
        resourceHelper = resourceClient.getLastData(catalog);
        capabilityDataAuxiliars = new ArrayList<>();
        capabilityDataAuxiliars = new ArrayList<>();
        resourceDatas = new ArrayList<>();
        if (resourceHelper != null && resourceHelper.getResources() != null) {

            for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();

                List<Map<String, Object>> dataSensor = capability.get("PM10");
                List<Map<String, Object>> dataSensorPM25 = capability.get("PM25");
                List<Map<String, Object>> dataSensorOzone = capability.get("OZONE");
                List<Map<String, Object>> dataSensorSulfureDioxide = capability.get("SULFURE_DIOXIDE");
                List<Map<String, Object>> dataSensorNitrogenDioxide = capability.get("NITROGEN_DIOXIDE");

                ResourceData resourceData = new ResourceData();
                List<CapabilityDataAuxiliar> dataAuxiliars = new ArrayList<>();

                resourceData.setUuid(getDataContextResource.getUuid());
                if (dataSensor != null) {
                    for (Map<String, Object> cap2 : dataSensor) {
                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                        if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                            if (dataAuxiliar.getValue() != null) {
                                dataAuxiliar.setName("PM10");
                                dataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }
                }

                if (dataSensorPM25 != null) {
                    for (Map<String, Object> cap2 : dataSensorPM25) {
                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                        if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                            if (dataAuxiliar.getValue() != null) {
                                dataAuxiliar.setName("PM25");
                                dataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }
                }

                if (dataSensorOzone != null) {
                    for (Map<String, Object> cap2 : dataSensorOzone) {
                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                        if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                            if (dataAuxiliar.getValue() != null) {
                                dataAuxiliar.setName("OZONE");
                                dataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }
                }

                if (dataSensorSulfureDioxide != null) {
                    for (Map<String, Object> cap2 : dataSensorSulfureDioxide) {
                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                        if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                            if (dataAuxiliar.getValue() != null) {
                                dataAuxiliar.setName("SULFURE_DIOXIDE");
                                dataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }
                }

                if (dataSensorNitrogenDioxide != null) {
                    for (Map<String, Object> cap2 : dataSensorNitrogenDioxide) {
                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                        if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                            if (dataAuxiliar.getValue() != null) {
                                dataAuxiliar.setName("NITROGEN_DIOXIDE");
                                dataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }
                }

                resourceData.getCapabilityDataAuxiliars().addAll(dataAuxiliars);
                resourceDatas.add(resourceData);


            }
            if (!resourceDatas.isEmpty()) {
                for (ResourceData resourceData : resourceDatas) {
                    if (!resourceData.getCapabilityDataAuxiliars().isEmpty()) {
                        IndexUtil.getIndexScore(resourceData.getCapabilityDataAuxiliars());
                        plot(resourceData);
                    }
                }
            }

        }
    }

    private CapabilityDataAuxiliar montarDataSensor(Map<String, List<Map<String, Object>>> capability,
                                                    String tipoSensor){
        List<Map<String, Object>> dataSensor = capability.get(tipoSensor);
        if (dataSensor != null) {
            for (Map<String, Object> cap2 : dataSensor) {
                CapabilityDataAuxiliar dataAuxiliar2 = new CapabilityDataAuxiliar(cap2);
                if (dataAuxiliar2.getResource() != null && dataAuxiliar2.getResource().getLat() != null) {
                    return dataAuxiliar2;
                }
            }
        }
        return null;
    }


    private void initObjects() {
        mapModel = new DefaultMapModel();
        capabilities = new ArrayList<>();
        uuids = new ArrayList<>();
        catalog = new Catalog();
    }


    public void plot(ResourceData resourceData) {

        List<CapabilityDataAuxiliar> lista = resourceData.getCapabilityDataAuxiliars();
        Collections.sort(lista, new Comparator<CapabilityDataAuxiliar>() {
            @Override
            public int compare(CapabilityDataAuxiliar o1, CapabilityDataAuxiliar o2) {
                Long value1;
                Long value2;

                value1 = o1.getIndex();
                value2 = o2.getIndex();
                if (value2 > value1) {
                    return -1;
                }
                if (value2 < value1) {
                    return 1;
                }
                return 0;
            }
        });

        CapabilityDataAuxiliar dataAuxiliar = lista.get(0);
       LatLng latLng = new LatLng(dataAuxiliar.getResource().getLat(), lista.get(0).getResource().getLon());
        String icon = "http://icongal.com/gallery/image/447363/map_marker_outside_chartreuse.png";


       /* String bitmapDescriptor = null;
        if (dataAuxiliar.getLabel().equals(IndexUtil.BAIXO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.baixo1);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.BAIXO2)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.baixo2);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.BAIXO3)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.baixo3);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MODERADO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.moderado1);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MODERADO2)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.moderado2);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MODERADO3)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.moderado3);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.ALTO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.alto1);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.ALTO2)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.alto2);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.ALTO3)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.alto3);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MUITOALTO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.muitoalto);
        }*/



        mapModel.addOverlay(new Marker(latLng,
                dataAuxiliar.getResource().getDescription() + "(" + dataAuxiliar.getLabel() +")",
                resourceData,icon));
    }


    public String visualizarRecurso() {
        WebUtil.flashScope().put("resource", resourceData.getCapabilityDataAuxiliars().get(0).getResource());
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
     *
     * @param event
     */
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        resourceData = (ResourceData) marker.getData();
        //System.out.println(res.getDescription());
    }

    public ResourceData getResourceData() {
        return resourceData;
    }

    public void setResourceData(ResourceData resourceData) {
        this.resourceData = resourceData;
    }
}
