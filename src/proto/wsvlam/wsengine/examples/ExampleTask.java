/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proto.wsvlam.wsengine.examples;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.soton.itinnovation.freefluo.core.event.RunEvent;
import uk.ac.soton.itinnovation.freefluo.core.flow.Flow;
import uk.ac.soton.itinnovation.freefluo.core.task.AbstractTask;
import uk.ac.soton.itinnovation.freefluo.core.task.Task;
import uk.ac.soton.itinnovation.freefluo.main.Engine;

/**
 *
 * @author skoulouz
 */
public class ExampleTask extends AbstractTask {

    public ExampleTask(String taskId, Flow flow) {
        super(taskId, flow);
    }

    public ExampleTask(String taskId, String name, Flow flow, boolean isCritical) {
        super(taskId, name, flow, isCritical);
    }

    @Override
    protected void handleRun(RunEvent re) {

        // Get the workflow instance object
//        Flow flow = getFlow();
//        String flowID = flow.getFlowId();
//        Engine e = flow.getEngine();
//        ExampleWFInstance workflowInstance = new ExampleWFInstance(flow);
//
        for (Iterator i = getChildren().iterator(); i.hasNext();) {
            Task task = (Task) i.next();
        }

        debug("Working....");

        work();
        
        debug("Task " + getTaskId() + " in flow " + getFlow().getFlowId() + " completed successfully");
//        complete();

    }

    @Override
    protected void taskPaused() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void taskCancelled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void taskResumed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void taskComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void debug(String msg) {
        System.err.println(this.getClass().getName() + "." + getTaskId() + ": " + msg);
    }

    private void work() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExampleTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
