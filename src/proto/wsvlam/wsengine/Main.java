/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proto.wsvlam.wsengine;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.soton.itinnovation.freefluo.event.WorkflowStateChangedEvent;
import uk.ac.soton.itinnovation.freefluo.event.WorkflowStateListener;
import uk.ac.soton.itinnovation.freefluo.lang.BadlyFormedDocumentException;
import uk.ac.soton.itinnovation.freefluo.lang.ParsingException;
import uk.ac.soton.itinnovation.freefluo.main.UnknownWorkflowInstanceException;

/**
 * 
 * @author S. Koulouzis
 */
public class Main implements WorkflowStateListener
{

    // private static File vlamWFFile = new File("test/testWF/1st.xml");
    // private static File vlamWFFile = new File("test/testWF/2nd.xml");
    // private static File vlamWFFile = new File("test/testWF/3nd.xml");
    // private static File vlamWFFile = new File("test/testWF/4th.xml");
    private static File vlamWFFile = new File("test/testWF/5th.xml");

    // private static File vlamWFFile = new
    // File("test/testWF/simpleLocal.scufl.xml");
    // private static File vlamWFFile = new File("test/testWF/2st.xml");
    private Object completionLock = new Object();

    public static void main(String[] args)
    {
        try
        {
            Main m = new Main();
            m.runWorkflowAndWait();
            // m.runWorkflowAndWait2();

        }
        catch (Exception ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runWorkflowAndWait2() throws BadlyFormedDocumentException, ParsingException,
            UnknownWorkflowInstanceException, InterruptedException
    {
        VlamEngine engine = new VlamEngine();

        // ConfigurationDescription configurationDescription = null;
        // ClassLoader classLoader = null;
        // EngineConfiguration engineConfiguration = null;
        // try {
        // engineConfiguration = new
        // EngineConfigurationImpl(configurationDescription, classLoader);
        // } catch (ConfigurationException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // EngineImpl engine = new EngineImpl(engineConfiguration);

        String flowId = engine.compile(vlamWFFile.getAbsolutePath());
        engine.addWorkflowStateListener(flowId, this);

        synchronized (completionLock)
        {
            engine.run(flowId);
            // completionLock.wait();
        }
    }

    private void runWorkflowAndWait() throws BadlyFormedDocumentException, ParsingException,
            UnknownWorkflowInstanceException, InterruptedException
    {
        VlamEngine engine = new VlamEngine();
        //parse xml file 
        String flowId = engine.compile(vlamWFFile.getAbsolutePath());
        engine.addWorkflowStateListener(flowId, this);
        
        synchronized (completionLock)
        {
            engine.run(flowId);
            // completionLock.wait();
        }
    }

    @Override
    public void workflowStateChanged(WorkflowStateChangedEvent workflowstatechangedevent)
    {
        // TODO Auto-generated method stub

    }

}
