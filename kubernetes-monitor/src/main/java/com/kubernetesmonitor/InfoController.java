package com.kubernetesmonitor;

import com.google.common.collect.Lists;
import com.kubernetesmonitor.events.DeploymentEvent;
import com.kubernetesmonitor.events.ServiceEvent;
import com.kubernetesmonitor.events.Publisher;
import com.kubernetesmonitor.kubernetes.KubernetesConnector;
import com.kubernetesmonitor.watcher.ComponentStatusWatcher;
import com.kubernetesmonitor.watcher.PodWatcher;
import com.kubernetesmonitor.watcher.ServiceWatcher;
import io.kubernetes.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class InfoController {

    private static final String API_ERROR = "Encountered exception while trying to access kubernetes api";
    @Autowired
    private KubernetesConnector kubernetesConnector;
    @Autowired
    Publisher publisher;

    @GetMapping("/publish")
    public void publish() {
        publisher.publishEvent(new DeploymentEvent("FakePod", "RUNNING", "Node#1", "A-Service"));
    }

    @GetMapping("/publish/service")
    public void publishService() {
        publisher.publishEvent(new ServiceEvent("AuthService", Collections.singletonList(8080)));
    }

    @GetMapping("/pods")
    public List<String> getPods() {
        try {
            return kubernetesConnector.getAllPods();
        } catch (ApiException ex) {
            return Lists.newArrayList(API_ERROR, ex.getResponseBody());
        }
    }

    @GetMapping("/podwatch")
    public String watchPods() {
        PodWatcher watcher = new PodWatcher(kubernetesConnector);
        watcher.watch();
        return "Initialized pod watch";
    }

    @GetMapping("/componentstatus")
    public List<String> getComponentStati() {
        try {
            return kubernetesConnector.getAllComponentStati();
        } catch (ApiException ex) {
            return Lists.newArrayList(API_ERROR, ex.getResponseBody());
        }
    }

    @GetMapping("/statuswatch")
    public String watchStatus() {
        ComponentStatusWatcher watcher = new ComponentStatusWatcher(kubernetesConnector);
        watcher.watch();
        return "Initialized component status watcher";
    }

    @GetMapping("/services")
    public List<String> getServices() {
        try {
            return kubernetesConnector.getAllServices();
        } catch (ApiException ex) {
            return Lists.newArrayList(API_ERROR, ex.getResponseBody());
        }
    }

    @GetMapping("/serviceswatch")
    public String watchServices() {
        ServiceWatcher watcher = new ServiceWatcher(kubernetesConnector);
        watcher.watch();
        return "Initialized service watch";
    }

    @GetMapping("/events")
    public List<String> getEvents() {
        try {
            return kubernetesConnector.getAllEvents();
        } catch (ApiException ex) {
            return Lists.newArrayList(API_ERROR, ex.getResponseBody());
        }
    }

    @GetMapping("/resourcequotas")
    public List<String> getResourceQuotas() {
        try {
            return kubernetesConnector.getAllResorceQuotas();
        } catch (ApiException ex) {
            return Lists.newArrayList(API_ERROR, ex.getResponseBody());
        }
    }

    @GetMapping("/nodes")
    public List<String> getNodes() {
        try {
            return kubernetesConnector.getAllNodes();
        } catch (ApiException ex) {
            return Lists.newArrayList(API_ERROR, ex.getResponseBody());
        }
    }
}
