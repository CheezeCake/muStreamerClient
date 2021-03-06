// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `speer_serv.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package speeral;

/**
 * \brief obtain the last ASR lattice (if available)
 * NOTES: it works only if the lattice speeral option is setted to "yes"
 **/

public abstract class Callback_Server_getLattice extends Ice.TwowayCallback
{
    public abstract void response(String __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        ServerPrx __proxy = (ServerPrx)__result.getProxy();
        String __ret = null;
        try
        {
            __ret = __proxy.end_getLattice(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
