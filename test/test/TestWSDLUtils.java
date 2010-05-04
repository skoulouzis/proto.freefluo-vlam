package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import uk.ac.soton.itinnovation.freefluo.util.wsdl.SOAPPort;
import uk.ac.soton.itinnovation.freefluo.util.wsdl.WSDLException;
import uk.ac.soton.itinnovation.freefluo.util.wsdl.WSDLReader;

public class TestWSDLUtils
{
    public static void main(String[] args)
    {

        try
        {
            test1();
            // test2();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void test1() throws MalformedURLException, WSDLException
    {
        URL wsdlUrl = new URL("http://elab:7080/axis1.4/services/SimpleService?wsdl");
        uk.ac.soton.itinnovation.freefluo.util.wsdl.WSDLReader reader = new WSDLReader(wsdlUrl);

        List<SOAPPort> portList = reader.getSOAPPorts();
        for (int i = 0; i < portList.size(); i++)
        {
            debug("Service Name: " + portList.get(i).name);
        }

        // Definition definition = reader.getDefinition();

        // Map bindings = definition.getBindings();
        // Set set = bindings.entrySet();
        // Iterator iter = set.iterator();

        // while(iter.hasNext()){
        // Entry entry = (Entry) iter.next();
        // debug("Key: "+entry.getKey()+"---------------\n-----------------------Value: "+entry.getValue());
        // debug("--------------------------------------");
        // }

        //        
        // Map messages = definition.getMessages();
        // set = messages.entrySet();
        // iter = set.iterator();
        //        
        // while(iter.hasNext()){
        // Entry entry = (Entry) iter.next();
        // debug("Key: "+entry.getKey()+"---------------\n-----------------------Value: "+entry.getValue());
        // debug("--------------------------------------");
        // }
    }

    private static void debug(String masg)
    {
        System.err.println(TestWSDLUtils.class.getName() + ": " + masg);

    }

}
