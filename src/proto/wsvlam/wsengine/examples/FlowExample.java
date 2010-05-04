/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proto.wsvlam.wsengine.examples;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.soton.itinnovation.freefluo.core.flow.Flow;
import uk.ac.soton.itinnovation.freefluo.lang.BadlyFormedDocumentException;
import uk.ac.soton.itinnovation.freefluo.lang.ParsingException;
import uk.ac.soton.itinnovation.freefluo.main.Engine;
import uk.ac.soton.itinnovation.freefluo.main.UnknownWorkflowInstanceException;
import uk.ac.soton.itinnovation.freefluo.main.WorkflowInstance;

/**
 *
 * @author skoulouz
 */
public class FlowExample {

    public static void main(String args[]){
        try {
            FlowExample ex = new FlowExample();
            ex.doIt();
        } catch (ParsingException ex1) {
            Logger.getLogger(FlowExample.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (UnknownWorkflowInstanceException ex1) {
            Logger.getLogger(FlowExample.class.getName()).log(Level.SEVERE, null, ex1);
        }

    }

    private void doIt() throws UnknownWorkflowInstanceException, BadlyFormedDocumentException, ParsingException {
        ExampleEngine engine = new ExampleEngine();
        String wfId = engine.compile(null);
        
//        flow.run();
        engine.run(wfId);



    }
}
