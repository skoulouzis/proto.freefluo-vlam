/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proto.wsvlam.wsengine.examples;

import java.util.HashMap;
import java.util.Map;
import uk.ac.soton.itinnovation.freefluo.conf.EngineConfiguration;
import uk.ac.soton.itinnovation.freefluo.core.flow.Flow;
import uk.ac.soton.itinnovation.freefluo.event.WorkflowStateListener;
import uk.ac.soton.itinnovation.freefluo.lang.BadlyFormedDocumentException;
import uk.ac.soton.itinnovation.freefluo.lang.ParsingException;
import uk.ac.soton.itinnovation.freefluo.main.Engine;
import uk.ac.soton.itinnovation.freefluo.main.FlowContext;
import uk.ac.soton.itinnovation.freefluo.main.InvalidFlowContextException;
import uk.ac.soton.itinnovation.freefluo.main.InvalidInputException;
import uk.ac.soton.itinnovation.freefluo.main.UnknownProcessorException;
import uk.ac.soton.itinnovation.freefluo.main.UnknownWorkflowInstanceException;
import uk.ac.soton.itinnovation.freefluo.main.WorkflowInstance;

/**
 *
 * @author skoulouz
 */
class ExampleEngine implements Engine {

    private Map<String, WorkflowInstance> wfMap = new HashMap<String, WorkflowInstance>();

    public EngineConfiguration getEngineConfiguration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String compile(String string) throws BadlyFormedDocumentException, ParsingException {
        Flow flow = new Flow("flow0", this);


        //Parse or get tasks from somewhere 
        ExampleTask[] exTask = new ExampleTask[4];
        for (int i = 0; i < exTask.length; i++) {
            exTask[i] = new ExampleTask(String.valueOf(i), flow);
        }

        exTask[0].addChild(exTask[1]);
        exTask[2].addChild(exTask[1]);
        exTask[0].addChild(exTask[3]);


        ExampleWFInstance wfInstance = new ExampleWFInstance(flow);
        wfMap.put("0", wfInstance);
        
        return "0";
    }

    public void setInput(String string, Map map) throws UnknownWorkflowInstanceException, InvalidInputException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFlowContext(String string, FlowContext fc) throws UnknownWorkflowInstanceException, InvalidFlowContextException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FlowContext getFlowContext(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void run(String key) throws UnknownWorkflowInstanceException {

        //get the workflow instance
        final WorkflowInstance workflowInstance = wfMap.get(key);

//        workflowInstance.run();

        //Create thread for workflow instance
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                workflowInstance.run();
            }
        };

        thread.start();

    }

    public String getStatus(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addWorkflowStateListener(String string, WorkflowStateListener wl) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeWorkflowStateListener(String string, WorkflowStateListener wl) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProgressReportXML(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map[] getIntermediateResultsForProcessor(String string, String string1) throws UnknownWorkflowInstanceException, UnknownProcessorException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map getOutput(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getErrorMessage(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProvenanceXML(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pauseExecution(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resumeExecution(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isPaused(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pause(String string, String string1) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDataNonVolatile(String string, String string1) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean changeOutputPortTaskData(String string, String string1, String string2, Object o) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resume(String string, String string1) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelExecution(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancel(String string, String string1) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void destroy(String string) throws UnknownWorkflowInstanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
