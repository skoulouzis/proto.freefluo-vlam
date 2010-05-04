/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proto.wsvlam.wsengine.examples;

import java.util.Map;
import uk.ac.soton.itinnovation.freefluo.core.flow.Flow;
import uk.ac.soton.itinnovation.freefluo.event.WorkflowStateListener;
import uk.ac.soton.itinnovation.freefluo.main.FlowContext;
import uk.ac.soton.itinnovation.freefluo.main.InvalidInputException;
import uk.ac.soton.itinnovation.freefluo.main.UnknownProcessorException;
import uk.ac.soton.itinnovation.freefluo.main.WorkflowInstance;

/**
 *
 * @author skoulouz
 */
class ExampleWFInstance implements WorkflowInstance {

    private Flow flow;

    public ExampleWFInstance(Flow flow) {
        this.flow = flow;
    }

    public Flow getFlow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FlowContext getFlowContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFlowContext(FlowContext fc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setInput(Map map) throws InvalidInputException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addWorkflowStateListener(WorkflowStateListener wl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeWorkflowStateListener(WorkflowStateListener wl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void run() {
        this.flow.run();
    }

    public String getStatus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map[] getIntermediateResultsForProcessor(String string) throws UnknownProcessorException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map getOutput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProvenanceXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProgressReportXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getErrorMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean pause() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean resume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDataNonVolatile(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean changeOutputPortTaskData(String string, String string1, Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
