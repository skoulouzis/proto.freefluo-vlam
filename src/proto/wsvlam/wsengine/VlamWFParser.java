/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proto.wsvlam.wsengine;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.wtcw.vle.wfd.ConnectionI;
import nl.wtcw.vle.wfd.ModuleI;
import nl.wtcw.vle.wfd.PortDescrI;
import nl.wtcw.vle.wfd.TopologyI;
import nl.wtcw.vle.wfd.impl.Axis.CompositeModuleAxis;
import uk.ac.soton.itinnovation.freefluo.core.flow.Flow;
import uk.ac.soton.itinnovation.freefluo.core.task.AbstractTask;
import uk.ac.soton.itinnovation.freefluo.lang.ParsingException;
import uk.ac.soton.itinnovation.freefluo.lang.WorkflowParser;
import uk.ac.soton.itinnovation.freefluo.main.Engine;
import uk.ac.soton.itinnovation.freefluo.main.WorkflowInstance;

/**
 * 
 * @author alogo
 */
public class VlamWFParser implements WorkflowParser {

    private XMLDecoder decoder;
    private TopologyI topology;
    private HashMap<String, AbstractTask> taskMap = new HashMap<String, AbstractTask>();
    private CompositeModuleAxis compositeModuleAxis;

//    private HashMap<String, AbstractTask> sourceMap;
//    private HashMap<String, AbstractTask> sinkMap;
    @Override
    public WorkflowInstance parse(Engine engine, String path) throws ParsingException {


        // some id generator
        String flowId = this.hashCode() + "ID";
        Flow flow = new Flow(flowId, engine);

        // extract Object[] args, String method, URL endpoint
        try {

            topology = parceFromFile(path);
            createTasks(topology, flow);
//            if (obj instanceof TopologyI) {
////                topology = (TopologyI) obj;
//
//            }
//            if (obj instanceof CompositeModuleAxis) {
//                debug("------------instanceof CompositeModuleAxis----------------");
//                this.compositeModuleAxis = (CompositeModuleAxis) obj;
//
//                debug("" + compositeModuleAxis.getInstanceID() + " " + compositeModuleAxis.getName());
//
//                List<ParameterI> params = compositeModuleAxis.getParameters();
//                for (int j = 0; j < params.size(); j++) {
//                    debug("PARAMS Name(" + j + ") " + params.get(j).getName());
//                    debug("PARAMS Type(" + j + ") " + params.get(j).getType());
//
//                    debug("PARAMS Type(" + j + ") " + params.get(j).getValue());
//                }
//
//                LinkedList<InputPortI> inpoutPorts = compositeModuleAxis.getInputPorts();
//                for (int j = 0; j < inpoutPorts.size(); j++) {
//                    debug("IN Name(" + j + ") " + inpoutPorts.get(j).getName());
//                    debug("IN Type(" + j + ") " + inpoutPorts.get(j).getType());
//                }
//            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new VlamWorkflowInstance(flow);
    }

    private void debug(String msg) {
        System.err.println(this.getClass().getName() + ": " + msg);
    }

    private TopologyI parceFromFile(String path) throws FileNotFoundException {
        decoder = new XMLDecoder(new FileInputStream(path));
        Object obj = decoder.readObject();
        debug("Cast from " + obj.getClass().getName());
        return (TopologyI) obj;
    }

    private void createTasks(TopologyI topology, Flow flow) throws URISyntaxException {
        VlamProcessorTask task;
        List<ModuleI> wfModules = topology.getModules();

        debug("------------instanceof TopologyI----------------");

        // Parsing should follow these steps:
        // 1) Get all modules
        // 1.1 Get inputs
        // 1.2 Get outputs
        // 1.3 Get processors
        // 1.4 Get data constrains
        // 1.5 Get concurrency constrains
        // 2)Create task hierarchy (task1->task2-task3 is task1.
        for (int i = 0; i < wfModules.size(); i++) {
            ModuleI module = wfModules.get(i);

            // Somehow this vlam task has to be able to extract the
            // necessary parameters (if WS Object[] args, String method,
            // URL
            // endpoint)
            // Also get connections dependencies etc.
            // debug("Task: "+module.getName()+"-"+module.getInstanceID());
            task = new VlamProcessorTask(module.getName() + "-" + module.getInstanceID(), flow, module);

            // map.get(module.getInstanceID());

            // task.linkTo(childTask)
            // task.addChild(task)
            taskMap.put(module.getInstanceID(), task);
        }

        List<ConnectionI> connections = topology.getConnections();
        String from;
        String to;
        VlamProcessorTask fromTask;
        VlamProcessorTask toTask;
        PortDescrI fromConnection;
        PortDescrI toConnection;

        for (int i = 0; i < connections.size(); i++) {
            // debug("Connection["+i+"]");
            fromConnection = connections.get(i).getFrom();
            toConnection = connections.get(i).getTo();

            from = fromConnection.getInstanceID();
            to = toConnection.getInstanceID();

            fromTask = (VlamProcessorTask) taskMap.get(from);
            toTask = (VlamProcessorTask) taskMap.get(to);

            // debug("\t From Instance:"+fromTask+" to Instance: "+toTask);
            // debug("\t From "+fromConnection.getName()+"->"+toConnection.getName());

            fromTask.addConnection(new MyConnectionI(connections.get(i)));
            toTask.addConnection(new MyConnectionI(connections.get(i)));
            fromTask.linkTo(toTask);
        }
    }

    public WorkflowInstance parse(Engine engine, TopologyI topology) {
        String flowId = this.hashCode() + "ID";
        Flow flow = new Flow(flowId, engine);
        try {
            createTasks(topology, flow);

        } catch (URISyntaxException ex) {
            Logger.getLogger(VlamWFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new VlamWorkflowInstance(flow);
    }
}
