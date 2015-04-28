package emmanuelnicolet.mustreamerclient;

import Ice.Communicator;
import Ice.ObjectPrx;
import IceStorm.TopicPrx;

public class IceData
{
	public static Communicator iceCommunicator = null;
	public static ObjectPrx songMonitorProxy = null;
	public static TopicPrx topic = null;
}
