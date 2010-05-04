package proto.wsvlam.wsengine;

import nl.wtcw.vle.wfd.ConnectionI;
import nl.wtcw.vle.wfd.PortDescrI;


/**
 *
 * @author S. Koulouzis
 */
public class MyConnectionI implements ConnectionI {

    private ConnectionI connection;
    private Object invokationResault;

    public MyConnectionI(ConnectionI connection) {
        this.connection = connection;
    }

    @Override
    public PortDescrI getFrom() {
        return connection.getFrom();
    }

    @Override
    public PortDescrI getTo() {

        return connection.getTo();
    }

    @Override
    public void setFrom(PortDescrI portdescri) {
        connection.setFrom(portdescri);

    }

    @Override
    public void setTo(PortDescrI portdescri) {
        connection.setTo(portdescri);
    }

    @Override
    public Object getXMLObject() throws Exception {
        return connection.getXMLObject();
    }

    @Override
    public void setXMLObject(Object obj) {
        connection.setXMLObject(obj);

    }

    /**
     * @param invokationResult
     *            the invokationResult to set
     */
    public void setInvokationResult(Object invokationResult) {
        invokationResault = (invokationResult);
    }

    private void debug(String msg) {
        System.err.println(this.getClass().getName() + ": " + msg);

    }

    /**
     * @return the invokationResult
     */
    public Object getInvokationResult() {
        return invokationResault;
    }
}
