package proto.wsvlam.wsengine;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;

import javax.xml.rpc.ServiceException;

import nl.wtcw.vle.wfd.InputPortI;
import nl.wtcw.vle.wfd.ModuleI;
import nl.wtcw.vle.wfd.OutputPortI;
import nl.wtcw.vle.wfd.ParameterI;
import proto.wsvlam.wsengine.utils.AxisCalls;
import uk.ac.soton.itinnovation.freefluo.core.event.RunEvent;
import uk.ac.soton.itinnovation.freefluo.core.event.TaskStateChangedEvent;
import uk.ac.soton.itinnovation.freefluo.core.flow.Flow;
import uk.ac.soton.itinnovation.freefluo.core.task.AbstractTask;
import uk.ac.soton.itinnovation.freefluo.core.task.Task;

public class VlamProcessorTask extends AbstractTask {

    private ModuleI module;
    private boolean debugOn = true;
//    private Map<String, ArrayList<PortDescrI>> connectionMapping;
//    private PortDescrI inputConnections;
//    private VlamWorkflowInstance workflowInstance;
    private String method;
    private URI serviceEndpoint;
    private Object[] args;
    private ArrayList<MyConnectionI> connectionWithData;
    private Object result;
    private JTextArea consoleTextArea;
    private Map<String, String> typeMap = new HashMap<String, String>();

//    private Map<InputPortI, Object> inputMap = new HashMap<InputPortI, Object>();
    public VlamProcessorTask(String taskId, Flow flow, ModuleI module) throws URISyntaxException {
        super(taskId, flow);
        // debug("--------------------Task args in/out etc for: " + taskId +
        // "------------------");
        this.module = module;
        this.method = module.getCategory().get(0).getTaxonomyValue();
        this.serviceEndpoint = new URI(module.getCategory().get(0).getTaxonomyURI());

        connectionWithData = new ArrayList<MyConnectionI>();



        intiTypeMap();


        // List<InputPortI> inpoutPorts = module.getInputPorts();
        // for (int j = 0; j < inpoutPorts.size(); j++)
        // {
        // debug("IN Name(" + j + ") " + inpoutPorts.get(j).getName());
        // debug("IN Type(" + j + ") " + inpoutPorts.get(j).getType());
        // }

        // List<OutputPortI> outpoutPorts = module.getOutputPorts();
        // for (int j = 0; j < outpoutPorts.size(); j++)
        // {
        //
        // debug("OUT Name(" + j + ") " + outpoutPorts.get(j).getName());
        // debug("OUT Type(" + j + ") " + outpoutPorts.get(j).getType());
        // }
    }

    private void debug(String msg) {
        if (this.debugOn) {
            System.err.println(this.getClass().getName() + "." + this.getTaskId() + ": " + msg);
        }

    }

    /**
     * This is where everything happends.
     * @param runevent
     */
    @Override
    protected void handleRun(RunEvent runevent) {
        try {
//            Flow flow = getFlow();

//            String flowID = flow.getFlowId();
//            uk.ac.soton.itinnovation.freefluo.main.Engine e = flow.getEngine();
//            workflowInstance = new VlamWorkflowInstance(flow);

            //Try to set some state so we can maybe have some high level logic
            flow.taskStateChanged(new TaskStateChangedEvent(this, "Start running"));
//            WorkflowStateChangedEvent evt2 = new WorkflowStateChangedEvent(method, WorkflowState.);
//            int i = -1;

            try {
                //Get the input (args) for this task (set as parameter or output
                //from other task), invoke the WS, and save the result to the
                //appropriate list
                invoke();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //This marks the end
            complete();
        } catch (Exception ex) {
            // eventList.add(new ServiceFailure());
            // ex.printStackTrace();
            // faultCausingException = ex;
            // logger.error(ex);
            // fail("Task " + getTaskId() + " in flow " + getFlow().getFlowId()
            // + " failed.  " + ex.getMessage());
            // Map inputMap = new HashMap();
            // Iterator i = getParents().iterator();
            // while (true)
            // {
            // if (!i.hasNext())
            // break;
            // Task task = (Task) i.next();
            // if (task instanceof PortTask)
            // {
            // PortTask inputPortTask = (PortTask) task;
            // DataThing dataThing = inputPortTask.getData();
            // String portName = inputPortTask.getScuflPort().getName();
            // inputMap.put(portName, dataThing);
            // }
            // }
        }

    }

    private synchronized void invoke() throws Exception {

        Iterator iter = getParents().iterator();
        List<InputPortI> inpoutPorts = module.getInputPorts();
        args = new Object[inpoutPorts.size()];

        while (iter.hasNext()) {
            Task task = (Task) iter.next();
            VlamProcessorTask inputPortTask = (VlamProcessorTask) task;
            ArrayList<MyConnectionI> prevConnection = inputPortTask.connectionWithData;

            for (int i = 0; i < inpoutPorts.size(); i++) {
                for (int j = 0; j < prevConnection.size(); j++) {
                    if (inpoutPorts.get(i).getName().equals(prevConnection.get(j).getTo().getName())) {
                        args[i] = prevConnection.get(j).getInvokationResult();
                    }
                }
            }
        }

        debug("Working....");
        message("Task started");

        setResults();

        debug("Done");
        message("Done");
    }

    private void setResults() throws InterruptedException, MalformedURLException, ServiceException, RemoteException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, IllegalArgumentException, NoSuchMethodException {
        List<ParameterI> parameters = module.getParameters();
        if (parameters != null && !parameters.isEmpty()) {
            Object[] param = new Object[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                //do casting of input modules 
                param[i] = parameters.get(i).getValue();
                debug("WF in (" + i + ") " + parameters.get(i).getName() + "=" + parameters.get(i).getValue());
                param[i] = xsd2JavaType(parameters.get(i).getType(), parameters.get(i).getValue());
                debug("WF in (" + i + ") " + parameters.get(i).getName() + "=" + parameters.get(i).getType());
            }
            if (param.length == 1) {
                result = param[0];
            } else {
                result = param;
            }
        } else {
            debug("Calling: " + serviceEndpoint + "." + method);
            for (int i = 0; i < args.length; i++) {
                debug("ags[" + i + "]=" + "(" + args[i].getClass().getName() + ")" + args[i]);
            }

            AxisCalls axisCall = new AxisCalls();
            result = axisCall.call2(args, this.method, this.serviceEndpoint.toURL(), 5000);
            message("Result is: ");

            if (result != null && result.getClass().isArray()) {
                Object[] tmp = (Object[]) result;
                for (int k = 0; k < tmp.length; k++) {
                    debug("---------------------res[" + k + "]=" + tmp[k]);
                    message("\t" + tmp[k]);
                }
            } else {
                debug("-------------------------res=" + result);
                message("\t" + result);
            }

        }

        List<OutputPortI> outputPorts = module.getOutputPorts();
        for (int i = 0; i < outputPorts.size(); i++) {
            for (int j = 0; j < connectionWithData.size(); j++) {
                if (outputPorts.get(i).getName().equals(connectionWithData.get(j).getFrom().getName())) {
                    connectionWithData.get(j).setInvokationResult(result);
                }
            }
        }

    }

    @Override
    protected void taskCancelled() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    protected void taskComplete() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    protected void taskPaused() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    protected void taskResumed() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void addConnection(MyConnectionI connection) {
        this.connectionWithData.add(connection);
    }

    void addStdOutput(JTextArea consoleTextArea) {
        this.consoleTextArea = consoleTextArea;
    }

    private void message(String string) {
        if (consoleTextArea != null) {
            consoleTextArea.append("\n" + this.getName() + "." + this.getTaskId() + ": " + string + "\n");
        }
    }

    private Object xsd2JavaType(String type, String value) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //        String[] arrayType;
        //        if(type.startsWith("Array")){
        //            arrayType = type.split("_");
        //            Array.newInstance(null, length);
        //        }
        String typeName = typeMap.get(type);
        debug("typeName is ....." + typeName);
        Class<?> c = Class.forName(typeName);
        Object obj = null;
        if (typeName.equals(Object.class.getName())) {
            //only for proxyWS. For now hardcode the types
            obj = value;

        } else {
            Constructor<?> constructor = null;
            try {
                constructor = c.getConstructor(String.class);
                obj = constructor.newInstance(value);
            } catch (NoSuchMethodException ex) {
                obj = value;
            } catch (SecurityException ex) {
                obj = value;
            }
        }
        
        debug("Cast is ....." + obj.getClass().getName());
        debug("Cast is ....." + obj);
        return obj;
    }

    private void intiTypeMap() {
        typeMap.put("anyType", Object.class.getName());
        typeMap.put("string", String.class.getName());
        typeMap.put("boolean", Boolean.class.getName());
        typeMap.put("int", Integer.class.getName());
        typeMap.put("long", Long.class.getName());
        typeMap.put("short", Short.class.getName());
        typeMap.put("float", Float.class.getName());
        typeMap.put("double", Double.class.getName());
        typeMap.put("ArrayOf_xsd_anyType", Array.class.getName());
        typeMap.put("ArrayOf_xsd_string", Array.class.getName());
        typeMap.put("ArrayOf_xsd_boolean", Array.class.getName());
        typeMap.put("ArrayOf_xsd_int", Array.class.getName());
        typeMap.put("ArrayOf_xsd_short", Array.class.getName());
        typeMap.put("ArrayOf_xsd_float", Array.class.getName());
        typeMap.put("ArrayOf_xsd_double", Array.class.getName());

    }
}
