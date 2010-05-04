/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proto.wsvlam.wsengine;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;
import nl.wtcw.vle.wfd.TopologyI;

import uk.ac.soton.itinnovation.freefluo.conf.EngineConfiguration;
import uk.ac.soton.itinnovation.freefluo.event.WorkflowStateListener;
import uk.ac.soton.itinnovation.freefluo.lang.BadlyFormedDocumentException;
import uk.ac.soton.itinnovation.freefluo.lang.ParsingException;
import uk.ac.soton.itinnovation.freefluo.lang.WorkflowParser;
import uk.ac.soton.itinnovation.freefluo.main.Engine;
import uk.ac.soton.itinnovation.freefluo.main.FlowContext;
import uk.ac.soton.itinnovation.freefluo.main.InvalidFlowContextException;
import uk.ac.soton.itinnovation.freefluo.main.InvalidInputException;
import uk.ac.soton.itinnovation.freefluo.main.UnknownProcessorException;
import uk.ac.soton.itinnovation.freefluo.main.UnknownWorkflowInstanceException;
import uk.ac.soton.itinnovation.freefluo.main.WorkflowInstance;

/**
 * 
 * @author S. Koulouzis
 */
public class VlamEngine implements Engine
{

    private HashMap<String, WorkflowInstance> workflowInstanceMap = new HashMap<String, WorkflowInstance>();

    @Override
    public void addWorkflowStateListener(String workflowInstanceId, WorkflowStateListener workflowStateListener)
            throws UnknownWorkflowInstanceException
    {
        WorkflowInstance workflowInstance = getWorkflowInstance(workflowInstanceId);
        workflowInstance.addWorkflowStateListener(workflowStateListener);
    }

    @Override
    public void cancel(String arg0, String arg1) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void cancelExecution(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public boolean changeOutputPortTaskData(String arg0, String arg1, String arg2, Object arg3)
            throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    public String compile(TopologyI topology) throws BadlyFormedDocumentException, ParsingException{
         WorkflowInstance workflowInstance = null;
        VlamWFParser parser;
        // if (isScful(absolutePath))
        // {
        // parser = new MyScuflParser();
        // workflowInstance = parser.parse(this, absolutePath);
        // }
        // else
        // {


        parser = new VlamWFParser();
        //parce workflow
        workflowInstance = parser.parse(this, topology);
        // }

        
        String flowId = workflowInstance.getFlow().getFlowId();

        // Cache it
        workflowInstanceMap.put(flowId, workflowInstance);

        return flowId;
    }

    @Override
    public String compile(String absolutePath) throws BadlyFormedDocumentException, ParsingException
    {
        WorkflowInstance workflowInstance = null;
        WorkflowParser parser;
        // if (isScful(absolutePath))
        // {
        // parser = new MyScuflParser();
        // workflowInstance = parser.parse(this, absolutePath);
        // }
        // else
        // {
        parser = new VlamWFParser();
        workflowInstance = parser.parse(this, absolutePath);
        // }

        String flowId = workflowInstance.getFlow().getFlowId();

        // Cache it
        workflowInstanceMap.put(flowId, workflowInstance);

        return flowId;
    }

    private boolean isVlam(String absolutePath)
    {
        if (absolutePath.contains("scfl"))
        {
            return false;
        }
        return true;
    }

    private boolean isScful(String absolutePath)
    {
        if (absolutePath.contains("scufl"))
        {
            return true;
        }
        return false;
    }

    @Override
    public void destroy(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EngineConfiguration getEngineConfiguration()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getErrorMessage(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FlowContext getFlowContext(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map[] getIntermediateResultsForProcessor(String arg0, String arg1) throws UnknownWorkflowInstanceException,
            UnknownProcessorException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map getOutput(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getProgressReportXML(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getProvenanceXML(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getStatus(String workflowInstanceId) throws UnknownWorkflowInstanceException
    {
        WorkflowInstance workflowInstance = getWorkflowInstance(workflowInstanceId);
        return workflowInstance.getStatus();
    }

    @Override
    public String getVersion()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDataNonVolatile(String arg0, String arg1) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPaused(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pause(String arg0, String arg1) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void pauseExecution(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void removeWorkflowStateListener(String arg0, WorkflowStateListener arg1)
            throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void resume(String arg0, String arg1) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void resumeExecution(String arg0) throws UnknownWorkflowInstanceException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void run(String workflowInstanceId) throws UnknownWorkflowInstanceException
    {
        //get the workflow instance (VlamWorkflowinstance)
        final WorkflowInstance workflowInstance = getWorkflowInstance(workflowInstanceId);

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

    private WorkflowInstance getWorkflowInstance(String workflowInstanceId)
    {
        return (WorkflowInstance) workflowInstanceMap.get(workflowInstanceId);
    }

    @Override
    public void setFlowContext(String arg0, FlowContext arg1) throws UnknownWorkflowInstanceException,
            InvalidFlowContextException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void setInput(String arg0, Map arg1) throws UnknownWorkflowInstanceException, InvalidInputException
    {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void addStdOutput(JTextArea consoleTextArea,String wfID) {
        VlamWorkflowInstance wf = (VlamWorkflowInstance) workflowInstanceMap.get(wfID);
        wf.addStdOutput(consoleTextArea);
    }

}
